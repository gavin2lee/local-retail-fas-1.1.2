package cn.wonhigh.retail.fas.web.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 
 * 导出查询数据到Excel表中
 * 
 * @author 王孟
 * @date 2014-10-31 下午4:27:03
 * @version 0.1.0 
 * @copyright yougou.com
 */
@SuppressWarnings({"rawtypes",})
public class ImportUtils {	
	
	/**
	 * 获取导入数据
	 * @param inputStream
	 * @param modelClazz
	 * @param fieldNames
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getDataList(InputStream inputStream, Class modelClazz, String[] fieldNames) throws Exception {
		List<Object> list = new ArrayList<Object>();
		if(null != inputStream && null != modelClazz && !ArrayUtils.isEmpty(fieldNames)){
			//创建Excel工作薄  
			XSSFWorkbook hwb = new XSSFWorkbook(inputStream);  
			//得到第一个工作表  
			XSSFSheet sheet = hwb.getSheetAt(0); 
			int rowCount = sheet.getPhysicalNumberOfRows();
			for(int i = 2; i < rowCount; i++) {
				XSSFRow row = sheet.getRow(i);
				if(null == row){
					return list;
				}
				Object model = modelClazz.newInstance();
				Object validateCell = row.getCell(0);
				if(validateCell == null || StringUtils.isBlank(String.valueOf(validateCell).trim())){
					continue;
				}
				for(int j = 0; j < row.getLastCellNum(); j++) {
					Object value = getCellValue(row.getCell(j));
					if(value == null || StringUtils.isBlank(String.valueOf(value).trim())) {
						continue;
					}
					setValue(model, fieldNames[j], value);
				}
				list.add(model);
			}
		}
		return list;
	}
	
	/**
	 * 设置属性值
	 * @param obj
	 * @param propertyName
	 * @param value
	 * @throws Exception
	 */
	private static void setValue(Object obj, String propertyName, Object value) throws Exception {
		Field field = obj.getClass().getDeclaredField(propertyName);
		value = convertValue(field, value);
		Method method = obj.getClass().getMethod("set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1), field.getType());
		method.invoke(obj, value);
	}
	
	/**
	 * 值类型转换
	 * @param obj
	 * @param propertyName
	 * @param value
	 * @throws Exception
	 */
	private static Object convertValue(Field field, Object value) throws Exception {
		String typeClassName = field.getType().getName();
		if(typeClassName.equals(Integer.class.getName())) {
			value = Integer.parseInt(value.toString());
		} else if(typeClassName.equals(Date.class.getName())) {
			JsonSerialize js = field.getAnnotation(JsonSerialize.class);
	    	if(js.using().getName().equals(JsonDateSerializer$10.class.getName())) {
	    		value = DateUtil.parseToDateWithThrowException(value.toString(), "yyyy-MM-dd");
	    	} else if(js.using().getName().equals(JsonDateSerializer$19.class.getName())) {
	    		value = DateUtil.parseToDateWithThrowException(value.toString(), "yyyy-MM-dd HH:mm:ss");
	    	}
		} else if(typeClassName.equals(Double.class.getName())) {
			value = Double.parseDouble(value.toString());
		} else if(typeClassName.equals(Float.class.getName())) {
			value = Float.parseFloat(value.toString());
		} else if(typeClassName.equals(BigDecimal.class.getName())){
			if(!StringUtils.isBlank(value.toString())){
				value = new BigDecimal(value.toString());
			}
		} else {
			value = value.toString().trim();
		}
		return value;
	}
	
	 /**
	  * 判断从Excel文件中解析出来数据的格式  
	  * 
	  * @param cell 每个单元格
	  * @return 单元格的值
	  */
   private static Object getCellValue(Cell cell){  
       Object value = null;  
       //简单的查检列类型  
       if(null != cell){
    	   switch(cell.getCellType()) {  
           case HSSFCell.CELL_TYPE_STRING://字符串  
               value = cell.getRichStringCellValue().getString();  
               break;  
           case HSSFCell.CELL_TYPE_NUMERIC://数字  
        	   if(HSSFDateUtil.isCellDateFormatted(cell)){
   					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   					value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
   				}else{
   					if(cell.getNumericCellValue() == (long)cell.getNumericCellValue()){
   						DecimalFormat df = new DecimalFormat("0");  
    					value = df.format(cell.getNumericCellValue());  
   					}else{
   						value = cell.getNumericCellValue();
   					}
   				   
   				}
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
       }
       return value;  
   } 
}