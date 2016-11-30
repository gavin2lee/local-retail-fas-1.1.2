package cn.wonhigh.retail.fas.common.utils;

import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import cn.wonhigh.retail.fas.common.annotation.excel.Excel;
import cn.wonhigh.retail.fas.common.annotation.excel.Excel.Struct;
import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * excel导入工具类
 * 
 * @author dong.j
 * @date 2014-1-7 下午5:16:25
 * @version 0.1.0
 * @copyright yougou.com
 */
public class ExcelImporterUtils {

	public ExcelImporterUtils() {

	}

	public static final XLogger logger = XLoggerFactory.getXLogger(ExcelImporterUtils.class);

	private final static String FIELD_CACHE_PREFIX = "f_";

	private final static String FIELD_COLUMN_CACHE_PREFIX = "x_";

	private static Cache<String, Map<String, String>> fieldCache = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.HOURS).build();

	private static Cache<String, Map<String, String>> fieldColumnCache = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.HOURS).build();

	/**
	 * 解析第一个sheet
	 * 
	 * @param fileName
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <ModelType> List<ModelType> importSheet(String fileName, Class<ModelType> clazz, String bizName) throws Exception {
		return importSheet(new FileInputStream(fileName), clazz, bizName);
	}

	/**
	 * 解析第一个sheet
	 * 
	 * @param in
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <ModelType> List<ModelType> importSheet(InputStream in, Class<ModelType> clazz, String bizName) throws Exception {
		XSSFReader r = open(in);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = getXMLReader();
		SheetHandler<ModelType> handler = getHandler(clazz, sst, bizName);
		parser.setContentHandler(handler);
		InputStream sheet1 = r.getSheet("rId1");
		InputSource sheetSource = new InputSource(sheet1);
		parser.parse(sheetSource);
		sheet1.close();
		return handler.getData();
	}

	/**
	 * 解析所有sheet
	 * 
	 * @param fileName
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <ModelType> List<ModelType> importSheets(String fileName, Class<ModelType> clazz, String bizName) throws Exception {
		return importSheets(new FileInputStream(fileName), clazz, bizName);
	}

	/**
	 * 解析所有sheet
	 * 
	 * @param in
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <ModelType> List<ModelType> importSheets(InputStream in, Class<ModelType> clazz, String bizName) throws Exception {
		XSSFReader r = open(in);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = getXMLReader();
		SheetHandler<ModelType> handler = getHandler(clazz, sst, bizName);
		parser.setContentHandler(handler);
		Iterator<InputStream> sheets = r.getSheetsData();// 获取excel中所有sheet
		while (sheets.hasNext()) {
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
		return handler.getData();
	}

	static XSSFReader open(InputStream in) throws Exception {
		OPCPackage pkg = OPCPackage.open(in);
		return new XSSFReader(pkg);
	}

	static XMLReader getXMLReader() throws SAXException {
		return XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
	}

	static <ModelType> SheetHandler<ModelType> getHandler(Class<ModelType> clazz, SharedStringsTable sst, String bizName) throws SAXException {
		return new SheetHandler<ModelType>(clazz, sst, bizName);
	}

	/**
	 * 配置解析（注解，xml支持）
	 */
	public interface Configuration {
		Map<String, String> parse(Class<?> clazz, String bizName) throws Exception;
	}

	
	
	
	//根据xml读取配置
	static class XmlConfiguration implements Configuration {

		@Override
		public Map<String, String> parse(final Class<?> clazz, final String bizName) throws Exception {
			return fieldColumnCache.get(FIELD_COLUMN_CACHE_PREFIX + clazz.getSimpleName()+bizName, new Callable<Map<String, String>>() {
				@Override
				public Map<String, String> call() throws Exception {
					Map<String, String> confMap = Maps.newHashMap();
					XMLConfiguration config = new XMLConfiguration("excel/import/" + clazz.getSimpleName() + ".xml");
					HierarchicalConfiguration hierarchicalConfiguration = config;
					// 判断是导入尺码横排的就要去读取SizeHorizontalDTO.xml配置并根据bizName做相应的处理
					if (StringUtils.isNotBlank(bizName) && clazz.getSimpleName().equals("SizeHorizontalDTO")) {
						List<HierarchicalConfiguration> roots = config.configurationsAt("bizName");
						for (HierarchicalConfiguration hierarchicalConfigurationTemp : roots) {
							if (StringUtils.isNotBlank(hierarchicalConfigurationTemp.getString("[@name]"))
									&& hierarchicalConfigurationTemp.getString("[@name]").equals(bizName)) {
								hierarchicalConfiguration = hierarchicalConfigurationTemp;
								break;
							}
						}
					}
					List<HierarchicalConfiguration> nodes = hierarchicalConfiguration.configurationsAt("mapping");
					for (int i = 0; i < nodes.size(); i++) {
						HierarchicalConfiguration node = nodes.get(i);
						String column = node.getString("[@column]");
						String fieldName = node.getString("[@field]");
						confMap.put(fieldName, column);
					}
					return confMap;
				}
			});
		}

	}

	
	
	//根据注解读取配置
	static class AnnotationConfiguration implements Configuration {
		
		@Override
		public Map<String, String> parse(Class<?> clazz, String bizName) throws Exception {
			Map<String, String> confMap = Maps.newHashMap();
			PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Field field = ReflectionUtils.findField(clazz, propertyDescriptor.getName());
				if (null != field && field.isAnnotationPresent(ExcelCell.class)) {
					ExcelCell cell = field.getAnnotation(ExcelCell.class);
					confMap.put(field.getName(), cell.value());
				}
			}
			return confMap;
		}

	}

	public static <ModelType> Excel getExcel(Class<ModelType> clazz) {
		return clazz.getAnnotation(Excel.class);
	}

	/**
	 * 获取excel从对应的多少行才开始算有数据
	 * 
	 * @param clazz
	 * @return
	 */
	public static <ModelType> int getExcelDataStart(Class<ModelType> clazz) {
		int start = 2;
		Excel excel = getExcel(clazz);
		if (null != excel) {
			start = excel.start();
		}
		return start;
	}

	/**
	 * 获取excel采用何种方式配置
	 * 
	 * @param clazz
	 * @return
	 */
	public static <ModelType> Struct getExcelStruct(Class<ModelType> clazz) {
		Struct struct = Struct.ANNOTATION;
		Excel excel = getExcel(clazz);
		if (null != excel) {
			struct = excel.struct();
		}
		return struct;
	}

	/**
	 * 获取clazz所有{@link ExcelCell }注解的字段
	 * 
	 * @param clazz
	 * @return
	 * @throws ExecutionException
	 */
	public static <ModelType> Map<String, String> getExcelCells(final Class<ModelType> clazz, final String bizName) {
		try {
			return fieldCache.get(FIELD_CACHE_PREFIX + clazz.getSimpleName()+bizName, new Callable<Map<String, String>>() {
				@Override
				public Map<String, String> call() throws Exception {
					Struct struct = getExcelStruct(clazz);
					Configuration configuration = null;
					if (Struct.ANNOTATION == struct) {
						configuration = new AnnotationConfiguration();
					} else {
						configuration = new XmlConfiguration();
					}
					return configuration.parse(clazz, bizName);
				}
			});
		} catch (ExecutionException e) {
			logger.error(e.getMessage());
		}
		return Maps.newHashMap();
	}

	/**
	 * 自定义数据校验
	 */
	public interface DataValidator {

		<ModelType> String validate(List<ModelType> list);
	}

	/**
	 * 验证，返回所有不合法的数据信息
	 * 
	 * @param datas
	 * @return
	 */
	public static <ModelType> String validate(List<ModelType> datas, DataValidator... dataValidators) {
		StringBuilder errorBuilder = new StringBuilder("");
		if (null != dataValidators && dataValidators.length > 0) {
			for (DataValidator dataValidator : dataValidators) {
				errorBuilder.append(dataValidator.validate(datas));
			}
		}
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		for (int i = 0; i < datas.size(); i++) {
			ModelType type = datas.get(i);
			int start = getExcelDataStart(type.getClass());
			int pos = start + i;
			// 做数据校验,如果有校验不合格会有错误信息放到set集合中
			Set<ConstraintViolation<ModelType>> constraintViolations = validator.validate(type);
			if (constraintViolations.size() > 0) {
				errorBuilder.append("第" + pos + "行数据不合法:");
				for (ConstraintViolation<ModelType> constraintViolation : constraintViolations) {
					errorBuilder.append(constraintViolation.getMessage()).append(" ");
				}
				errorBuilder.append("\r\n");
			}
		}
		return errorBuilder.toString();
	}

	
	
	
	
	
	/**
	 * 解析excel内部结构（xml）
	 * 
	 * @param <ModelType>
	 */
	private static class SheetHandler<ModelType> extends DefaultHandler {
		private int start = 2;// 开多少行才开始算有用数据
		private int line = 1;// 当前读取第几行
		private int curCell;// 标示当前位于一行的哪个单个格
		private String lastContents;// 单元格的值
		private boolean nextIsString;
		private ModelType modelType;
		private Class<ModelType> clazz;
		private List<ModelType> dataList;// 解析后返回的model
		private Map<Integer, String> fieldmap;
		private SharedStringsTable sst;
		private String bizName;

		public SheetHandler(Class<ModelType> clazz, SharedStringsTable sst, String bizName) {
			this.dataList = Lists.newArrayList();
			this.fieldmap = Maps.newHashMap();
			this.clazz = clazz;
			this.sst = sst;
			this.bizName = bizName;
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if (name.equals("row")) { // row => new line
				start = getExcelDataStart(clazz);
				if (!isTitleLine()) {
					try {
						this.modelType = clazz.newInstance();
						this.dataList.add(modelType);
					} catch (InstantiationException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					}
				}
				curCell = 0;
				line++;
			} else if (name.equals("c")) { // c => cell
				if (!isTitleLine()) {
					String cellStr = attributes.getValue("r");
					String key = cellStr.replaceAll("[0-9]", "");
					Map<String, String> confMap = getExcelCells(clazz, bizName);
					for (Entry<String, String> entry : confMap.entrySet()) {
						if (StringUtils.endsWithIgnoreCase(key, entry.getValue())) {
							fieldmap.put(curCell, entry.getKey());
						}
					}
					String cellType = attributes.getValue("t");
					if (cellType != null && cellType.equals("s")) {
						nextIsString = true;
					} else {
						nextIsString = false;
					}
				}
			}
			lastContents = "";
		}

		public void endElement(String uri, String localName, String name) throws SAXException {
			if (line > start) {
				if (nextIsString) {
					int idx = Integer.parseInt(lastContents);
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString().trim();
					nextIsString = false;
				}

				if (name.equals("v")) {
					String fieldName = fieldmap.get(curCell);
					if (StringUtils.isNotBlank(fieldName)) {
						String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						try {
							Field field = FieldUtils.getField(clazz, fieldName, true);
							Method setMethod = clazz.getMethod(setMethodName, new Class[] { field.getType() });
							Type[] ts = setMethod.getGenericParameterTypes();
							// 只要一个参数,判断类型
							String xclass = ts[0].toString();
							setValue(xclass, modelType, setMethod, lastContents);
						} catch (Exception e) {
							logger.error(e.getMessage(),e);
						}
					}
					curCell++;
				}
			}
		}

		public void setValue(String xclass, ModelType tObject, Method setMethod, String value) throws Exception {
			if (xclass.equals("class java.lang.String")) {
				setMethod.invoke(tObject, value);
			} else if (xclass.equals("class java.util.Date")) {
				setMethod.invoke(tObject, DateUtil.getJavaDate(new Double(value), false));
			} else if (xclass.equals("class java.lang.Boolean")) {
				Boolean boolname = true;
				if (value.equals("否")) {
					boolname = false;
				}
				setMethod.invoke(tObject, boolname);
			} else if (xclass.equals("class java.lang.Integer")) {
				setMethod.invoke(tObject, new Integer(value));
			} else if (xclass.equals("class java.lang.Long")) {
				setMethod.invoke(tObject, new Long(value));
			} else if (xclass.equals("class java.lang.Double")) {
				setMethod.invoke(tObject, new Double(value));
			} else if (xclass.equals("class java.math.BigDecimal")) {
				setMethod.invoke(tObject, new BigDecimal(value));
			} else {
				setMethod.invoke(tObject, value);
			}
		}

		public boolean isTitleLine() {
			return line < start;
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			lastContents += new String(ch, start, length);
		}

		public List<ModelType> getData() {
			return dataList;
		}

	}
}
