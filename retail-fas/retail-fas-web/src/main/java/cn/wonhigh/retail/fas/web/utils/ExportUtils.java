package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ReflectUtil;

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
public class ExportUtils {

	/**
	 * 导出
	 * @param fileName 导出文件名
	 * @param exportColumns 导出列名
	 * @param dataList 导出数据
	 * @param response 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(String fileName, String exportColumns, List dataList, HttpServletResponse response) throws Exception {
		List<Map> ColumnsList =  getColumnList(exportColumns);
		List<Map> dataMapList = getDataList(dataList);
		ExportData(fileName, ColumnsList, dataMapList, response);
	}
	
	/**
	 * 获取导出列名map
	 * @param exportColumns 导出列名
	 * @return list
	 * @throws Exception
	 */
	public static List<Map> getColumnList(String exportColumns) throws Exception {
		if(StringUtils.isBlank(exportColumns)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		exportColumns = exportColumns.replace("[", "");
		exportColumns = exportColumns.replace("]", "");
		exportColumns = "[" + exportColumns + "]";
		return mapper.readValue(exportColumns, new TypeReference<List<Map>>() {});
	}

	/**
	 * 获取导出列名map
	 * @param exportColumns 导出列名
	 * @return list
	 * @throws Exception
	 */
	public static List<List<Map>> getMainColumnList(String exportColumns) throws Exception {
		if(StringUtils.isBlank(exportColumns)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(exportColumns, new TypeReference<List<List<Map>>>() {});
	}
	
	/**
	 * 获取导出数据map
	 * @param dataList 导出数据
	 * @return List<Map>
	 * @throws Exception
	 */
	public static List<Map> getDataList(List<Object> dataList) throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		if(!CollectionUtils.isEmpty(dataList)){
			if(dataList.get(0) instanceof Map){
				for (Object object : dataList) {
					Map map = (Map)object;
					dataMapList.add(map);
				}
			}else{
				
				for (Object object : dataList) {
					Map map = new HashMap();
					ReflectUtil.object2MapWithoutNull(object, map);
					dataMapList.add(map);
				}
			}
		}
		return dataMapList;
	}
	
	/**
	 * 导出数据
	 * @param fileName 导出文件名
	 * @param columnsList 导出列名map 
	 * @param dataMapList 导出数据map
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void ExportMainData(String fileName, List<Map> columnsList,
			List<Map> dataList, HttpServletResponse response) throws Exception {


		if(CollectionUtils.isEmpty(columnsList) || CollectionUtils.isEmpty(dataList)){
			return ;
		}
		if(StringUtils.isBlank(fileName)){
			fileName = "导出信息";
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");

		SXSSFWorkbook wb = new SXSSFWorkbook(1);
		try {
			for (Map map : dataList) {
				Sheet sheet1 = wb.createSheet();
				sheet1.setDefaultRowHeightInPoints(20);
				sheet1.setDefaultColumnWidth((short) 18);
				//设置页脚
				Footer footer = sheet1.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
	
				//设置样式 表头
				CellStyle style1 = wb.createCellStyle();
				style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font1 = wb.createFont();
				font1.setFontHeightInPoints((short) 11);
				font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				style1.setFont(font1);
				// 设置单元格样式
				CellStyle floatCellStyle = wb.createCellStyle();
				floatCellStyle.setFont(font1);
				floatCellStyle.setWrapText(false);
				floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
				CellStyle intCellStyle = wb.createCellStyle();
				intCellStyle.setFont(font1);
				intCellStyle.setWrapText(false);
				intCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				CellStyle numberCellStyle = wb.createCellStyle();
				numberCellStyle.setFont(font1);
				numberCellStyle.setWrapText(false);
				numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				DataFormat  format = wb.createDataFormat();
				numberCellStyle.setDataFormat(format.getFormat("@"));
				CellStyle stringCellStyle = wb.createCellStyle();
				stringCellStyle.setFont(font1);
				stringCellStyle.setWrapText(false);
				stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				//合并
				CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0, (short) (columnsList.size() - 1));
				sheet1.addMergedRegion(rg1);
				//设置样式 表头
				CellStyle style3 = wb.createCellStyle();
				style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font3 = wb.createFont();
				font3.setFontHeightInPoints((short) 13);
				font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style3.setFont(font3);
				style3.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
				Row row0 = sheet1.createRow(0);
				row0.setHeightInPoints(30);
				//第一行 提示长
				Cell cell0 = row0.createCell((short) 0);
				String currDate = DateUtil.getCurrentDateTimeToStr2();
				cell0.setCellValue(map.get("title").toString().concat("(").concat(currDate).concat(")"));
				cell0.setCellStyle(style3);
				int index = 1;
				//设置表头
				Row row1 = sheet1.createRow(index);
				row1.setHeightInPoints(20);
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell1 = row1.createCell(i);
					cell1.setCellType(HSSFCell.ENCODING_UTF_16);
					cell1.setCellValue(String.valueOf(columnsList.get(i).get("title")==null?"":columnsList.get(i).get("title")));
					cell1.setCellStyle(style1);
					if(null != columnsList.get(i).get("width")){
						sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(columnsList.get(i).get("width")))*44);
					}else{
						sheet1.setColumnWidth(i, 120*44);
					}
				}
	
				List<Map>  dataMapList = (List<Map>)map.get("dtl");
				//填充数据
				for (int j = 0; j < dataMapList.size(); j++) {
					Row row2 = sheet1.createRow((j + index + 1)); // 第三行开始填充数据 
					Map cellDataMap = dataMapList.get(j);
					for (int i = 0; i < columnsList.size(); i++) {
						Cell cell = row2.createCell(i);
						String cellValue = StringUtils.EMPTY;
						if (columnsList.get(i).get("field") != null) {
							String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
						}
						if(NumberValidationUtils.isRealNumber(cellValue)){
							if(null != columnsList.get(i).get("exportType") && "string".equals(String.valueOf(columnsList.get(i).get("exportType")))){
								cell.setCellValue(cellValue);
								cell.setCellStyle(stringCellStyle);
							}else{
								cell.setCellValue(Double.parseDouble(cellValue));
								if(NumberValidationUtils.isInteger(cellValue)){
									cell.setCellStyle(intCellStyle); 
								}else{
									cell.setCellStyle(floatCellStyle);   
								}
							}
						}else{
							cell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
							cell.setCellStyle(stringCellStyle);
						}
					}
	
				}
				
			}
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			wb.dispose();
		}catch(Exception e){
			if( wb != null)
				wb.dispose();
			wb = null;
			if(response != null)
				response.getOutputStream().close();
			response = null;
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 导出数据
	 * @param fileName 导出文件名
	 * @param columnsList 导出列名map 
	 * @param dataMapList 导出数据map
	 * @param response
	 * @throws Exception
	 */
	public static void ExportComplexHeadData(String fileName, List<Map> headColumnsList, List<Map> columnsList, List<Map> dataMapList,
			HttpServletResponse response) throws Exception {

		if(CollectionUtils.isEmpty(columnsList) || CollectionUtils.isEmpty(dataMapList)){
			return ;
		}
		if(StringUtils.isBlank(fileName)){
			fileName = "导出信息";
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");

		SXSSFWorkbook wb = new SXSSFWorkbook(1);
		try {
			int size = dataMapList.size();
			int count = Integer.MAX_VALUE;
			int times = size / count;
			int startIndex = 0;
			int endIndex = 0;
			if(size % count != 0){
				times = times + 1;
			}
			int lastIndex = (times-1) * count + (size % count);
			for (int t = 0 ; t< times; t++) {
				startIndex = t*count;
				endIndex = (t+1) == times ? lastIndex : (t+1)*count-1;
				List<Map> newMap = new ArrayList<Map>();
				newMap.addAll(dataMapList.subList(startIndex, endIndex));
				Sheet sheet1 = wb.createSheet();
				sheet1.setDefaultRowHeightInPoints(20);
				sheet1.setDefaultColumnWidth((short) 18);
				//设置页脚
				Footer footer = sheet1.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		
				//设置样式 表头
				Font font1 = wb.createFont();
				font1.setFontHeightInPoints((short) 10);
				font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 设置单元格样式
				CellStyle floatCellStyle = wb.createCellStyle();
				floatCellStyle.setFont(font1);
				floatCellStyle.setWrapText(false);
				floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
				CellStyle intCellStyle = wb.createCellStyle();
				intCellStyle.setFont(font1);
				intCellStyle.setWrapText(false);
				intCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				CellStyle stringCellStyle = wb.createCellStyle();
				stringCellStyle.setFont(font1);
				stringCellStyle.setWrapText(false);
				stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				
				//合并
				CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0, (short) (columnsList.size() - 1));
				sheet1.addMergedRegion(rg1);
				
				//设置样式 表头
				CellStyle style3 = wb.createCellStyle();
				style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font3 = wb.createFont();
				font3.setFontHeightInPoints((short) 15);
				font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style3.setFont(font3);
				
				CellStyle titleStyle=wb.createCellStyle();
				titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font4 = wb.createFont();
				font4.setFontName("宋体");
				font4.setFontHeightInPoints((short) 11);
				font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(font4);
				
				CellStyle headStyle=wb.createCellStyle();
				headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				headStyle.setFont(font4);
				
				Row row0 = sheet1.createRow(0);
				row0.setHeightInPoints(30);
				//第一行 提示长
				Cell cell0 = row0.createCell((short) 0);
				String currDate = DateUtil.getCurrentDateTimeToStr2();
				cell0.setCellValue(fileName.concat("(").concat(currDate).concat(")"));
				cell0.setCellStyle(style3);
				int index = 1;
				//设置表头
				if(!CollectionUtils.isEmpty(headColumnsList)){
					Row row1 = sheet1.createRow(index);
					row1.setHeightInPoints(20);
					int startNum =0;
					int endNum = 0;
					for (int i = 0; i < headColumnsList.size(); i++) {
						Map obj = headColumnsList.get(i);
						int colspan = Integer.parseInt(String.valueOf(obj.get("colspan")));
						endNum += colspan;
						sheet1.addMergedRegion(new CellRangeAddress(1,1,startNum,endNum-1));
						Cell cell1 = row1.createCell(startNum);
						cell1.setCellType(HSSFCell.ENCODING_UTF_16);
						cell1.setCellValue(String.valueOf(obj.get("title")==null?"":obj.get("title")));
						cell1.setCellStyle(headStyle);
						startNum += colspan;
					}
					index++;
				}
				Row row2 = sheet1.createRow(index);
				row2.setHeightInPoints(20);
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell1 = row2.createCell(i);
					cell1.setCellType(HSSFCell.ENCODING_UTF_16);
					cell1.setCellValue(String.valueOf(columnsList.get(i).get("title")==null?"":columnsList.get(i).get("title")));
					cell1.setCellStyle(titleStyle);
					if(null != columnsList.get(i).get("width")){
						sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(columnsList.get(i).get("width")))*44);
					}else{
						sheet1.setColumnWidth(i, 120*44);
					}
				}
		
				//填充数据
				for (int j = 0; j < newMap.size(); j++) {
					Row row3 = sheet1.createRow((j + index + 1)); // 第三行开始填充数据 
					Map cellDataMap = newMap.get(j);
					for (int i = 0; i < columnsList.size(); i++) {
						Cell cell = row3.createCell(i);
						String cellValue = StringUtils.EMPTY;
						if (columnsList.get(i).get("field") != null) {
							String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
						}
						if(NumberValidationUtils.isRealNumber(cellValue)){
							if(null != columnsList.get(i).get("exportType") && "string".equals(String.valueOf(columnsList.get(i).get("exportType")))){
								cell.setCellValue(cellValue);
								cell.setCellStyle(stringCellStyle);
							}else{
								cell.setCellValue(Double.parseDouble(cellValue));
								if(NumberValidationUtils.isInteger(cellValue)){
									cell.setCellStyle(intCellStyle); 
								}else{
									cell.setCellStyle(floatCellStyle);   
								}
							}
						}else{
							cell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
							cell.setCellStyle(stringCellStyle);
						}
					}
				}
			}	
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			wb.dispose();
		}catch(Exception e){
			if( wb != null)
				wb.dispose();
			wb = null;
			if(response != null)
				response.getOutputStream().close();
			response = null;
			throw new Exception(e);
		}
	}

	
	/**
	 * 导出数据
	 * @param fileName 导出文件名
	 * @param columnsList 导出列名map 
	 * @param dataMapList 导出数据map
	 * @param response
	 * @throws Exception
	 */
	public static void ExportData(String fileName, List<Map> columnsList, List<Map> dataMapList,
			HttpServletResponse response) throws Exception {

		if(CollectionUtils.isEmpty(columnsList) || CollectionUtils.isEmpty(dataMapList)){
			return ;
		}
		if(StringUtils.isBlank(fileName)){
			fileName = "导出信息";
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");

		SXSSFWorkbook wb = new SXSSFWorkbook(1);
		try {
			int size = dataMapList.size();
			int count = Integer.MAX_VALUE;
			int times = size / count;
			int startIndex = 0;
			int endIndex = 0;
			if(size % count != 0){
				times = times + 1;
			}
			int lastIndex = (times-1) * count + (size % count);
			for (int t = 0 ; t< times; t++) {
				startIndex = t*count;
				endIndex = (t+1) == times ? lastIndex : (t+1)*count-1;
				List<Map> newMap = new ArrayList<Map>();
				newMap.addAll(dataMapList.subList(startIndex, endIndex));
				Sheet sheet1 = wb.createSheet();
				sheet1.setDefaultRowHeightInPoints(20);
				sheet1.setDefaultColumnWidth((short) 18);
				//设置页脚
				Footer footer = sheet1.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		
				//设置样式 表头
				Font font1 = wb.createFont();
				font1.setFontHeightInPoints((short) 10);
				font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 设置单元格样式
				CellStyle floatCellStyle = wb.createCellStyle();
				floatCellStyle.setFont(font1);
				floatCellStyle.setWrapText(false);
				floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
				
				CellStyle floatCellStyle4 = wb.createCellStyle();
				floatCellStyle4.setFont(font1);
				floatCellStyle4.setWrapText(false);
				floatCellStyle4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				floatCellStyle4.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.0000"));  
				
				CellStyle intCellStyle = wb.createCellStyle();
				intCellStyle.setFont(font1);
				intCellStyle.setWrapText(false);
				intCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				CellStyle stringCellStyle = wb.createCellStyle();
				stringCellStyle.setFont(font1);
				stringCellStyle.setWrapText(false);
				stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				
				//合并
				CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0, (short) (columnsList.size() - 1));
				sheet1.addMergedRegion(rg1);
				
				//设置样式 表头
				CellStyle style3 = wb.createCellStyle();
				style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font3 = wb.createFont();
				font3.setFontHeightInPoints((short) 15);
				font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style3.setFont(font3);
				
				CellStyle titleStyle=wb.createCellStyle();
				titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font4 = wb.createFont();
				font4.setFontName("宋体");
				font4.setFontHeightInPoints((short) 11);
				font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(font4);
				
				Row row0 = sheet1.createRow(0);
				row0.setHeightInPoints(30);
				//第一行 提示长
				Cell cell0 = row0.createCell((short) 0);
				String currDate = DateUtil.getCurrentDateTimeToStr2();
				cell0.setCellValue(fileName.concat("(").concat(currDate).concat(")"));
				cell0.setCellStyle(style3);
				int index = 1;
				//设置表头
				Row row1 = sheet1.createRow(index);
				row1.setHeightInPoints(20);
				
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell1 = row1.createCell(i);
					cell1.setCellType(HSSFCell.ENCODING_UTF_16);
					cell1.setCellValue(String.valueOf(columnsList.get(i).get("title")==null?"":columnsList.get(i).get("title")));
					cell1.setCellStyle(titleStyle);
					if(null != columnsList.get(i).get("width")){
						sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(columnsList.get(i).get("width")))*44);
					}else{
						sheet1.setColumnWidth(i, 120*44);
					}
				}
		
				//填充数据
				for (int j = 0; j < newMap.size(); j++) {
					Row row2 = sheet1.createRow((j + index + 1)); // 第三行开始填充数据 
					Map cellDataMap = newMap.get(j);
					for (int i = 0; i < columnsList.size(); i++) {
						Cell cell = row2.createCell(i);
						String cellValue = StringUtils.EMPTY;
						if (columnsList.get(i).get("field") != null) {
							String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
						}
						if(NumberValidationUtils.isRealNumber(cellValue)){
							if(null != columnsList.get(i).get("exportType") && "string".equals(String.valueOf(columnsList.get(i).get("exportType")))){
								cell.setCellValue(cellValue);
								cell.setCellStyle(stringCellStyle);
							}else{
								cell.setCellValue(Double.parseDouble(cellValue));
								if(NumberValidationUtils.isInteger(cellValue)){
									cell.setCellStyle(intCellStyle); 
								}else if (NumberValidationUtils.fourEffectiveNum(cellValue)){
									cell.setCellStyle(floatCellStyle4);   
								}else{
									cell.setCellStyle(floatCellStyle);   
								}
							}
						}else{
							cell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
							cell.setCellStyle(stringCellStyle);
						}
					}
				}
			}	
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			wb.dispose();
		}catch(Exception e){
			if( wb != null)
				wb.dispose();
			wb = null;
			if(response != null)
				response.getOutputStream().close();
			response = null;
			throw new Exception(e);
		}
	}

	/**
	 * 导出数据
	 * @param fileName 导出文件名
	 * @param columnsList 导出列名map 
	 * @param dataMapList 导出数据map
	 * @param response
	 * @throws Exception
	 */
	public static void ExportBillData(String fileName, List<Map> fieldsList, List<Map> mainMapList, List<Map> columnsList, List<Map> dataMapList,
			HttpServletResponse response) throws Exception {

		if(CollectionUtils.isEmpty(columnsList) || CollectionUtils.isEmpty(dataMapList)){
			return ;
		}
		if(StringUtils.isBlank(fileName)){
			fileName = "导出信息";
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");
		SXSSFWorkbook wb = new SXSSFWorkbook(1);
		try {
			Sheet sheet1 = wb.createSheet();
			wb.setSheetName(0, fileName);
			sheet1.setDefaultRowHeightInPoints(20);
			sheet1.setDefaultColumnWidth((short) 18);
			//设置页脚
			Footer footer = sheet1.getFooter();
			footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
	
			//设置样式 表头
			CellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			Font font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 11);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style1.setFont(font1);
			// 设置单元格样式
			CellStyle floatCellStyle = wb.createCellStyle();
			floatCellStyle.setFont(font1);
			floatCellStyle.setWrapText(false);
			floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
			CellStyle intCellStyle = wb.createCellStyle();
			intCellStyle.setFont(font1);
			intCellStyle.setWrapText(false);
			intCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			CellStyle numberCellStyle = wb.createCellStyle();
			numberCellStyle.setFont(font1);
			numberCellStyle.setWrapText(false);
			numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			CellStyle stringCellStyle = wb.createCellStyle();
			stringCellStyle.setFont(font1);
			stringCellStyle.setWrapText(false);
			stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			//合并
			CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0, (short) (columnsList.size() - 1));
			sheet1.addMergedRegion(rg1);
			//设置样式 表头
			CellStyle style3 = wb.createCellStyle();
			style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			Font font3 = wb.createFont();
			font3.setFontHeightInPoints((short) 15);
			font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style3.setFont(font3);
			Row row0 = sheet1.createRow(0);
			row0.setHeightInPoints(30);
			//第一行 提示长
			Cell cell0 = row0.createCell((short) 0);
			String currDate = DateUtil.getCurrentDateTimeToStr2();
			cell0.setCellValue(fileName.concat("(").concat(currDate).concat(")"));
			cell0.setCellStyle(style3);
			int index = 1;
			//表头
			int fieldSize = fieldsList.size();
			if(fieldSize > 0){
				Row row = null;
				int cellIndex = 0;
				for(int j=0; j < fieldSize; j++){
					if(j % 4==0){
						row = sheet1.createRow(index++);
						cellIndex = 0;
					}
					String title = String.valueOf(fieldsList.get(j).get("title"));
					String field = String.valueOf(fieldsList.get(j).get("field"));
					Cell firstCell = row.createCell(cellIndex++);
					firstCell.setCellValue(title);
					Cell nextCell = row.createCell(cellIndex++);
					String cellValue = String.valueOf(mainMapList.get(0).get(field));
					if(NumberValidationUtils.isRealNumber(cellValue)){
						if(null != columnsList.get(0).get("exportType") && "string".equals(String.valueOf(columnsList.get(0).get("exportType")))){
							nextCell.setCellValue(cellValue);
							nextCell.setCellStyle(stringCellStyle);
						}else{
							nextCell.setCellValue(Double.parseDouble(cellValue));
							if(NumberValidationUtils.isInteger(cellValue)){
								nextCell.setCellStyle(intCellStyle); 
							}else{
								nextCell.setCellStyle(floatCellStyle);   
							}
						}
					}else{
						nextCell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
						nextCell.setCellStyle(stringCellStyle);
					}
				}
			}
			
			
			
			//明细
			Row row1 = sheet1.createRow(index);
			row1.setHeightInPoints(20);
			for (int i = 0; i < columnsList.size(); i++) {
				Cell cell1 = row1.createCell(i);
				cell1.setCellType(HSSFCell.ENCODING_UTF_16);
				cell1.setCellValue(String.valueOf(columnsList.get(i).get("title")==null?"":columnsList.get(i).get("title")));
				cell1.setCellStyle(style1);
				if(null != columnsList.get(i).get("width")){
					sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(columnsList.get(i).get("width")))*44);
				}else{
					sheet1.setColumnWidth(i, 120*44);
				}
			}
	
			//填充数据
			for (int j = 0; j < dataMapList.size(); j++) {
				Row row2 = sheet1.createRow((j + index + 1)); // 第三行开始填充数据 
				Map cellDataMap = dataMapList.get(j);
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell = row2.createCell(i);
					String cellValue = StringUtils.EMPTY;
					if (columnsList.get(i).get("field") != null) {
						String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
						cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
					}
					if(NumberValidationUtils.isRealNumber(cellValue)){
						if(null != columnsList.get(i).get("exportType") && "string".equals(String.valueOf(columnsList.get(i).get("exportType")))){
							cell.setCellValue(cellValue);
							cell.setCellStyle(stringCellStyle);
						}else{
							cell.setCellValue(Double.parseDouble(cellValue));
							if(NumberValidationUtils.isInteger(cellValue)){
								cell.setCellStyle(intCellStyle); 
							}else{
								cell.setCellStyle(floatCellStyle);   
							}
						}
					}else{
						cell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
						cell.setCellStyle(stringCellStyle);
					}
				}
	
			}
			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			wb.dispose();
		}catch(Exception e){
			if( wb != null)
				wb.dispose();
			wb = null;
			if(response != null)
				response.getOutputStream().close();
			response = null;
			throw new Exception(e);
		}
	}
}