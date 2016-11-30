package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;


/**
 * 导入功能的controller
 * 
 * @author 杨勇
 * @date 2014-4-29 上午11:03:43
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public abstract class ExcelImportController<T extends FasBaseModel> extends BaseController<T>{
	
	//批量插入的数量
	private final static int BATCH_ADD_COUNT = 20;

	/**
	 * 设置execl文件的列和model对象属性的对应关系
	 * 
	 * @return model属性的集合
	 */
	protected abstract String[] getImportProperties();
	
	/**
	 * 批量新增数据
	 * 
	 * @param list List<T>
	 * @return true or false
	 * @throws ManagerException 
	 */
	protected abstract boolean doBatchAdd(List<T> list) throws ManagerException;
	
	/**
	 * 通过反射技术，将指定属性名称的指设置到指定model对象中
	 * 
	 * @param obj model对象
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @throws Exception 异常
	 */
	private void setValue(T obj, String propertyName, Object value) throws Exception {
		Field field = obj.getClass().getDeclaredField(propertyName);
		value = this.convertValue(field, value);
		Method method = obj.getClass().getMethod("set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1), field.getType());
		method.invoke(obj, value);
	}
	
	private Object convertValue(Field field, Object value) {
		String typeClassName = field.getType().getName();
		if(typeClassName.equals(Integer.class.getName())) {
			if(value instanceof Double) {
				value = ((Double)value).intValue(); 
			}else {
				value = Integer.parseInt(value.toString());
			}
		} else if(typeClassName.equals(Date.class.getName())) {
			JsonSerialize js = field.getAnnotation(JsonSerialize.class);
	    	if(js.using().getName().equals(JsonDateSerializer$10.class.getName())) {
	    		value = DateUtil.parseToDate(value.toString(), "yyyy-MM-dd");
	    	} else if(js.using().getName().equals(JsonDateSerializer$19.class.getName())) {
	    		value = DateUtil.parseToDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
	    	}
		} else if(typeClassName.equals(Double.class.getName())) {
			value = Integer.parseInt(value.toString());
		} else if(typeClassName.equals(Float.class.getName())) {
			value = Float.parseFloat(value.toString());
		}else if(typeClassName.equals(BigDecimal.class.getName())){
			value = new BigDecimal(value.toString());
		} else {
			value = value.toString().trim();
		}
		return value;
	}
	
	/**
	 * 通过文件名和Class名称，组装model对象的集合，默认从execl文件的第3行开始读取数据
	 * 
	 * @param filePath 文件路径
	 * @param modelClazz Class名称
	 * @return List<UploadMessageModel> 封装错误信息的集合对象
	 */
	protected List<UploadMessageModel> doUpload(InputStream inputStream, Class<T> modelClazz, HttpServletRequest request) {
		return this.doUpload(inputStream, modelClazz, 3, request);
	}
	
	public static Workbook create(InputStream in) throws IOException,InvalidFormatException {
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(in)) {
            return new HSSFWorkbook(in);
        }
        if (POIXMLDocument.hasOOXMLHeader(in)) {
            return new XSSFWorkbook(OPCPackage.open(in));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");

    }
	
	/**
	 * 通过文件名和Class名称以及从execl文件的第几行开始，组装model对象的集合
	 * 
	 * @param filePath 文件路径
	 * @param modelClazz Class名称
	 * @param rowFormIndex 从execl文件的第几行开始读取数据
	 * @return List<UploadMessageModel> 封装错误信息的集合对象
	 */
	protected List<UploadMessageModel> doUpload(InputStream inputStream, Class<T> modelClazz, int rowFormIndex,
			HttpServletRequest request) {
		List<UploadMessageModel> msgList = new ArrayList<UploadMessageModel>();
		List<T> list = new ArrayList<T>(BATCH_ADD_COUNT);
		int effectiveCount = 0, totalCount = 0;
		try {
			//创建Excel工作薄  
			Workbook wb = create(inputStream);  
			//得到第一个工作表  
			Sheet sheet = wb.getSheetAt(0); 
			T model = null;
			int rowCount = sheet.getPhysicalNumberOfRows();
			for(int i = rowFormIndex - 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if(row!=null){
					model = modelClazz.newInstance();
					for(int j = 0; j < row.getLastCellNum(); j++) {
						Object value = getCellValue(row.getCell(j));
						if(value == null || "".equals(String.valueOf(value).trim())) {
							continue;
						}
						setValue(model, getImportProperties()[j], value);
						this.setDefaulValues(model, request);
					}
					//如果该行的数据已在list中存在，则忽略
					if(!this.validateRepeart(model, list)) {
						UploadMessageModel msgModel = this.validateModel(model,i);
						//如果数据满足要求，则将其加入待批量新增的集合中
						if(msgModel == null || msgModel.isFlag()) {
							effectiveCount++; // 有效记录数+1
							list.add(model);
						} else { //否则将其加入记录错误信息的集合中
							msgList.add(msgModel);
						}
					}
					totalCount++;
					//批量 插入
					if(!checkImportType()){
						if(effectiveCount % BATCH_ADD_COUNT == 0 || totalCount == (rowCount - rowFormIndex + 1)) {
							this.doBatchAdd(list);
							list = new ArrayList<T>(BATCH_ADD_COUNT);
						}
					}
				}
			}
			if(checkImportType()){
				this.doBatchAdd(list,msgList);//现金交易明细导入
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			UploadMessageModel errorModel = new UploadMessageModel();
			errorModel.setFlag(false);
			errorModel.setMessage("导入失败,请联系管理员 ...");
			msgList.add(errorModel);
			return msgList;
		}
		return msgList;
	}
	
	/**
	 * 判断model是否在list中已存在(重写T对象的equals和hashCode方法)
	 * 
	 * @param model T 
	 * @param list List<T>
	 * @return true or false
	 */
	protected boolean validateRepeart(T model, List<T> list) {
		if(model == null || (list == null || list.size() == 0)) {
			return false;
		}
		if(list.contains(model)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证组装的model是否合法
	 * 
	 * @param model 组装的model
	 * @return 导入execl时，记录每行数据是否正确的对象
	 */
	protected UploadMessageModel validateModel(T model) {
		return null;
	}
	
	/**
	 * 验证组装的model是否合法
	 * 
	 * @param model 组装的model
	 * @param rowIndex 当前行号
	 * @return 导入execl时，记录每行数据是否正确的对象
	 * @throws ManagerException 
	 * @throws IOException 
	 */
	protected UploadMessageModel validateModel(T model, int rowIndex) throws ManagerException, IOException {
		return null;
	}
	
	protected boolean checkImportType(){
		return false;
	}
	
	/**
	 * 现金交易明细导入定制
	 * @param list
	 * @param msgList
	 * @return
	 * @throws ManagerException
	 */
	protected boolean doBatchAdd(List<T> list,List<UploadMessageModel> msgList) throws ManagerException{
		return false;
	}
	
	 /**
	  * 判断从Excel文件中解析出来数据的格式  
	  * 
	  * @param cell 每个单元格
	  * @return 单元格的值
	  */
    private Object getCellValue(Cell cell){  
        Object value = null; 
        if(cell==null){
        	  return "";  
		}
        //简单的查检列类型  
        switch(cell.getCellType()) {  
            case HSSFCell.CELL_TYPE_STRING://字符串  
                value = cell.getRichStringCellValue().getString();  
                break;  
            case HSSFCell.CELL_TYPE_NUMERIC://数字  
            	value = cell.getNumericCellValue();  
                break;  
            case HSSFCell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case HSSFCell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值  
                value = cell.getBooleanCellValue();  
                break;  
            case HSSFCell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;  
    }
    

	//封装校验提示信息
	protected UploadMessageModel getErrorMessageObject(String message){
		UploadMessageModel uploadMessageModel = new UploadMessageModel();
		uploadMessageModel.setFlag(false);
		uploadMessageModel.setMessage(message);
		return uploadMessageModel;
	}
	
}
