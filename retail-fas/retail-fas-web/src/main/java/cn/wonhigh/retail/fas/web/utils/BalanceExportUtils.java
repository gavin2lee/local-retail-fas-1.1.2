package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.util.HSSFColor;
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

import cn.wonhigh.retail.fas.common.dto.PrintBalanceEntry;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

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
public class BalanceExportUtils {

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
		ExportBalanceData(fileName, ColumnsList, dataMapList, response);
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
					BeanUtilsCommon.object2MapWithoutNull(object, map);
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
	public static void ExportBalanceData(String fileName, List<Map> columnsList,
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
							cell.setCellValue(Double.parseDouble(cellValue));
							if(NumberValidationUtils.isInteger(cellValue)){
								cell.setCellStyle(intCellStyle); 
							}else{
								cell.setCellStyle(floatCellStyle);   
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

	@SuppressWarnings("unchecked")
	public static void ExportBalanceData(String fileName, List<Map<String, Object>> balanceData,
			HttpServletResponse response) throws Exception{
		if(CollectionUtils.isEmpty(balanceData)){
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

		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		try {
			int sheetIndex = 0;
			for (Map map : balanceData) {
				BillBalance balance = (BillBalance) map.get("balance");
				List<Map> columnsList = (List<Map>) map.get("columnList");
				List<Map> rowList = (List<Map>) map.get("rowList");
				List<Map> footerList = (List<Map>) map.get("footerList");
				Sheet sheet1 = wb.createSheet();
				wb.setSheetName(sheetIndex, balance.getSalerNo()+"_"+ ++sheetIndex);
				sheet1.setDefaultRowHeightInPoints(20);
				sheet1.setDefaultColumnWidth((short) 18);
				//设置页脚
				Footer footer = sheet1.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
				
				//设置样式 表头
				CellStyle style1 = wb.createCellStyle();
				style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font font1 = wb.createFont();
				font1.setFontHeightInPoints((short) 8);
				font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				style1.setFont(font1);
				//设置样式 表头
				CellStyle style2 = wb.createCellStyle();
				style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font2 = wb.createFont();
				font2.setFontHeightInPoints((short) 10);
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				style2.setFont(font2);
				// 设置单元格样式
				CellStyle floatCellStyle = wb.createCellStyle();
				floatCellStyle.setFont(font1);
				floatCellStyle.setWrapText(false);
				floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
				CellStyle intCellStyle = wb.createCellStyle();
				intCellStyle.setFont(font1);
				intCellStyle.setWrapText(false);
				intCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				CellStyle numberCellStyle = wb.createCellStyle();
				numberCellStyle.setFont(font1);
				numberCellStyle.setWrapText(false);
				numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				DataFormat  format = wb.createDataFormat();
				numberCellStyle.setDataFormat(format.getFormat("@"));
				CellStyle stringCellStyle = wb.createCellStyle();
				stringCellStyle.setFont(font1);
				stringCellStyle.setWrapText(false);
				stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				//第一行 标题
				Row row0 = sheet1.createRow(0);
				row0.setHeightInPoints(40);
				Cell cell0 = row0.createCell((short) 0);
				cell0.setCellValue(map.get("title").toString());
				CellStyle firstRowStyle = wb.createCellStyle();
				firstRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font firstRowsFont3 = wb.createFont();
				firstRowsFont3.setFontHeightInPoints((short) 20);
				firstRowsFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				firstRowStyle.setFont(firstRowsFont3);
				cell0.setCellStyle(firstRowStyle);
				//合并单元格
				CellRangeAddress rg0 = new CellRangeAddress(0, (short) 0, 0, (short) (columnsList.size()));
				sheet1.addMergedRegion(rg0);
				
				// 第二行 供应商
				Row row1 = sheet1.createRow(1);
				row1.setHeightInPoints(15);
				Cell cell1 = row1.createCell((short) 0);
				cell1.setCellValue("供应商："+balance.getSalerName());
				CellStyle secondRowStyle = wb.createCellStyle();
				secondRowStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font secondRowsFont3 = wb.createFont();
				secondRowsFont3.setFontHeightInPoints((short) 13);
				secondRowsFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				secondRowStyle.setFont(secondRowsFont3);
				secondRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				secondRowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell1.setCellStyle(style2);
				
				//合并单元格
				CellRangeAddress rg1 = new CellRangeAddress(1, (short) 1, 0, (short) (columnsList.size()));
				sheet1.addMergedRegion(rg1);
				// 第三行 合并列
				Row row2 = sheet1.createRow(2);
				row2.setHeightInPoints(15);
				Cell cell2 = row2.createCell((short) 0);
				cell2.setCellValue("发货地区、数量、金额");
				CellStyle threeRowStyle = wb.createCellStyle();
				threeRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font threeRowsFont3 = wb.createFont();
				threeRowsFont3.setFontHeightInPoints((short) 13);
				threeRowsFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				threeRowStyle.setFont(threeRowsFont3);
				threeRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				threeRowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell2.setCellStyle(style1);
				CellRangeAddress rg2 = new CellRangeAddress(2, (short) 2, 0, (short) (columnsList.size()-12));
				sheet1.addMergedRegion(rg2);
				Cell cell3 = row2.createCell(columnsList.size()-11);
				cell3.setCellValue("残鞋");
				cell3.setCellStyle(style1);
				CellRangeAddress rg3 = new CellRangeAddress(2, (short) 2, columnsList.size()-11, columnsList.size()-8);
				sheet1.addMergedRegion(rg3);
				Cell cell4 = row2.createCell(columnsList.size()-7);
				cell4.setCellValue("退货");
				cell4.setCellStyle(style1);
				CellRangeAddress rg4 = new CellRangeAddress(2, (short) 2, columnsList.size()-7, columnsList.size()-1);
				sheet1.addMergedRegion(rg4);
				Cell cell5 = row2.createCell(columnsList.size());
				cell5.setCellValue("其他扣项");
				CellStyle cs = wb.createCellStyle();
				cs.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				cs.setFont(font1);
				cell5.setCellStyle(cs);
				sheet1.setColumnWidth(columnsList.size(), 40*44);
				//填充表头
				int index = 3;
				Row row3 = sheet1.createRow(index);
				row3.setHeightInPoints(20);
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell = row3.createCell(i);
					cell.setCellType(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(String.valueOf(columnsList.get(i).get("title")==null?"":columnsList.get(i).get("title")));
					cell.setCellStyle(style1);
					if(null != columnsList.get(i).get("width")){
						sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(columnsList.get(i).get("width")))*44);
					}else{
						sheet1.setColumnWidth(i, 120*44);
					}
				}
	
				//填充数据
				for (int j = 0; j < rowList.size(); j++) {
					index ++;
					Row row = sheet1.createRow(index); 
					Map cellDataMap = rowList.get(j);
					for (int i = 0; i < columnsList.size(); i++) {
						Cell cell = row.createCell(i);
						String cellValue = StringUtils.EMPTY;
						if (columnsList.get(i).get("field") != null && !String.valueOf(columnsList.get(i).get("field")).equals("ck")) {
							String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
						}else{
							cellValue = j+1+"";
						}
//						if(NumberValidationUtils.isRealNumber(cellValue)){
//							cell.setCellValue(Double.parseDouble(cellValue));
//							if(NumberValidationUtils.isInteger(cellValue)){
//								cell.setCellStyle(intCellStyle); 
//							}else{
//								cell.setCellStyle(floatCellStyle);   
//							}
//						}else{
							cell.setCellValue(cellValue);
							cell.setCellStyle(stringCellStyle);
//						}
					}
	
				}
				//填充合计行
				Row rowFooter = sheet1.createRow(index+1); 
				Map footerDataMap = footerList.get(0);
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell = rowFooter.createCell(i);
					if(i == 0){
						CellRangeAddress rg5 = new CellRangeAddress(index+1, index+1, 0, 2);
						sheet1.addMergedRegion(rg5);
						cell.setCellValue("合计");
						cell.setCellStyle(stringCellStyle);
					}else if(i>2){
						String cellValue = StringUtils.EMPTY;
						if (columnsList.get(i).get("field") != null) {
							String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
							cellValue = String.valueOf(footerDataMap.get(fieldString)==null?"":footerDataMap.get(fieldString));
						}
//						if(NumberValidationUtils.isRealNumber(cellValue)){
//							cell.setCellValue(Double.parseDouble(cellValue));
//							if(NumberValidationUtils.isInteger(cellValue)){
//								cell.setCellStyle(intCellStyle); 
//							}else{
//								cell.setCellStyle(floatCellStyle);   
//							}
//						}else{
							cell.setCellValue(cellValue);
							cell.setCellStyle(stringCellStyle);
//						}
					}
				}
				Cell deductionCell = rowFooter.createCell(columnsList.size());
				deductionCell.setCellValue(balance.getDeductionAmount().doubleValue());
				deductionCell.setCellStyle(floatCellStyle);
				//填充总合计行
				Row lastRow = sheet1.createRow(index+2);
				Map lastRowDataMap = footerList.get(1);
				Cell lastCell1 = lastRow.createCell(0);
				lastCell1.setCellValue("应收/付账款");
				lastCell1.setCellStyle(style1);
				CellRangeAddress rg6 = new CellRangeAddress(index+2, index+2, 0, 2);
				sheet1.addMergedRegion(rg6);
				Cell lastCell2 = lastRow.createCell(3);
				lastCell2.setCellValue(String.valueOf(lastRowDataMap.get("balanceAmount")));
				lastCell2.setCellStyle(floatCellStyle);
				CellRangeAddress rg7 = new CellRangeAddress(index+2, index+2, 3, columnsList.size());
				sheet1.addMergedRegion(rg7);
				SheetAdaptForA4Utils.adaptForA4(sheet1);//自适应A4纸大小
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

	@SuppressWarnings("unchecked")
	public static void ExportBalanceGatherData(String fileName, Map<String, Object> balanceGatherData,
			HttpServletResponse response) throws Exception{
		if(CollectionUtils.isEmpty(balanceGatherData)){
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

		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		try {
			int sheetIndex = 0;
			for (Entry<String, Object> entry : balanceGatherData.entrySet()) {
				String brandUnitNo = entry.getKey();
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				
				List<Map> columnsList = (List<Map>) map.get("columnList");
				List<Map> yearColumnsList = (List<Map>) map.get("yearColumnList");
				List<Map> rowList = (List<Map>) map.get("rowList");
				Sheet sheet1 = wb.createSheet();
				wb.setSheetName(sheetIndex ++, brandUnitNo);
				sheet1.setDefaultRowHeightInPoints(20);
				sheet1.setDefaultColumnWidth((short) 18);
				//设置页脚
				Footer footer = sheet1.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
				
				
				
				//设置样式 表头
				CellStyle style1 = wb.createCellStyle();
				style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font font1 = wb.createFont();
				font1.setFontHeightInPoints((short) 10);
				font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				style1.setFont(font1);
				//设置样式 表头
				CellStyle style2 = wb.createCellStyle();
				style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font font2 = wb.createFont();
				font2.setFontHeightInPoints((short) 10);
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				style2.setFont(font2);
				// 设置单元格样式
				CellStyle floatCellStyle = wb.createCellStyle();
				floatCellStyle.setFont(font1);
				floatCellStyle.setWrapText(false);
				floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
				CellStyle intCellStyle = wb.createCellStyle();
				intCellStyle.setFont(font1);
				intCellStyle.setWrapText(false);
				intCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				CellStyle numberCellStyle = wb.createCellStyle();
				numberCellStyle.setFont(font1);
				numberCellStyle.setWrapText(false);
				numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				DataFormat  format = wb.createDataFormat();
				numberCellStyle.setDataFormat(format.getFormat("@"));
				CellStyle stringCellStyle = wb.createCellStyle();
				stringCellStyle.setFont(font1);
				stringCellStyle.setWrapText(false);
				stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				stringCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				//第一行 标题
				Row row0 = sheet1.createRow(0);
				row0.setHeightInPoints(40);
				Cell cell0 = row0.createCell((short) 0);
				cell0.setCellValue(map.get("title").toString());
				CellStyle firstRowStyle = wb.createCellStyle();
				firstRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font firstRowsFont3 = wb.createFont();
				firstRowsFont3.setFontHeightInPoints((short) 20);
				firstRowsFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				firstRowStyle.setFont(firstRowsFont3);
				cell0.setCellStyle(firstRowStyle);
				CellStyle secondRowStyle = wb.createCellStyle();
				secondRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font secondRowsFont3 = wb.createFont();
				secondRowsFont3.setFontHeightInPoints((short) 15);
				secondRowsFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				secondRowStyle.setFont(secondRowsFont3);
				//合并单元格
				CellRangeAddress rg0 = new CellRangeAddress(0, (short) 0, 0, (short) (columnsList.size()-1));
				sheet1.addMergedRegion(rg0);
				
				// 分类汇总
				int rowIndex = 1;
				for (Map<String, Object> dataMap : rowList) {
					
					String title = (String) dataMap.get("title");
					List<Map> rowMapList = getDataList((List<Object>) dataMap.get("rowList"));
					// 第二行 进货汇总
					Row titleRow = sheet1.createRow(rowIndex);
					titleRow.setHeightInPoints(30);
					Cell titleCell = titleRow.createCell((short) 0);
					titleCell.setCellValue(title);
					titleCell.setCellStyle(secondRowStyle);
					//合并单元格
					CellRangeAddress rg = new CellRangeAddress(rowIndex, rowIndex, 0, (short) (columnsList.size()-1));
					sheet1.addMergedRegion(rg);
					
					// 第三行 列名
					rowIndex ++;
					Row columnRow = sheet1.createRow(rowIndex);
					if(title.indexOf("累计")!=-1){
						columnsList = yearColumnsList;
					}
					for (int i = 0; i < columnsList.size(); i++) {
						Cell columnCell = columnRow.createCell(i);
						columnCell.setCellType(HSSFCell.ENCODING_UTF_16);
						columnCell.setCellValue(String.valueOf(columnsList.get(i).get("title")==null?"":columnsList.get(i).get("title")));
						columnCell.setCellStyle(style1);
						if(null != columnsList.get(i).get("width")){
							sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(columnsList.get(i).get("width")))*44);
						}else{
							sheet1.setColumnWidth(i, 60*44);
						}
					}
					
					// 数据填充
					int ckIndex =0;
					int endRowIndex = rowIndex+1;
					for (int j = 0; j < rowMapList.size(); j++) {
						rowIndex ++;
						Row dataRow = sheet1.createRow(rowIndex); 
						Map cellDataMap = rowMapList.get(j);
						for (int i = 0; i < columnsList.size(); i++) {
							Cell cell = dataRow.createCell(i);
							String cellValue = StringUtils.EMPTY;
							if(null != cellDataMap.get("salerName") && 
									(String.valueOf(cellDataMap.get("salerName")).equals("小计") || String.valueOf(cellDataMap.get("salerName")).equals("合计"))){
								if(i <= 3){
									cell.setCellStyle(stringCellStyle);
									continue;
								}
							}
							
							
							if (columnsList.get(i).get("field") != null && !String.valueOf(columnsList.get(i).get("field")).equals("ck")) {// 非序号字段
								String fieldString = String.valueOf(columnsList.get(i).get("field")==null?"":columnsList.get(i).get("field"));
								cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
							}else{
								cellValue = ++ckIndex+"";
							}
							if(NumberValidationUtils.isRealNumber(cellValue)){
								cell.setCellValue(Double.parseDouble(cellValue));
								if(NumberValidationUtils.isInteger(cellValue)){
									cell.setCellStyle(intCellStyle); 
								}else{
									cell.setCellStyle(floatCellStyle);   
								}
							}else{
								cell.setCellValue(cellValue);
								cell.setCellStyle(stringCellStyle);
							}
						}
						endRowIndex ++;
						if(null != cellDataMap.get("salerName") && String.valueOf(cellDataMap.get("salerName")).equals("小计")){
							//合并单元格
							CellRangeAddress rg1 = new CellRangeAddress(rowIndex, rowIndex, 1, 3);
							sheet1.addMergedRegion(rg1);
							//合并单元格
							CellRangeAddress rg2 = new CellRangeAddress(endRowIndex-1-(Integer)cellDataMap.get("iCount"), endRowIndex-1, 0, 0);
							sheet1.addMergedRegion(rg2);
							dataRow.getCell(1).setCellValue("小计");
							ckIndex =0;
						}
						if(null != cellDataMap.get("salerName") && String.valueOf(cellDataMap.get("salerName")).equals("合计")){
							//合并单元格
							CellRangeAddress rg1 = new CellRangeAddress(rowIndex, rowIndex, 0, 3);
							sheet1.addMergedRegion(rg1);
							dataRow.getCell(0).setCellValue("合计");
							sheet1.createRow(++rowIndex);
							ckIndex =0;
						}
					}
				}
				SheetAdaptForA4Utils.adaptForA4(sheet1);//自适应A4纸大小
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

	@SuppressWarnings("unchecked")
	public static void ExportBalanceDiffData(String fileName,
			Map<String, Map<String, Object>> lstData, HttpServletResponse response) throws Exception {
		if(CollectionUtils.isEmpty(lstData)){
			return ;
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");
		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		int sheetIndex = 0;
		try {
			
			for (Entry<String, Map<String, Object>> entry : lstData.entrySet()) {
				Sheet sheet1 = wb.createSheet();
				sheet1.setDefaultRowHeightInPoints(20);
				sheet1.setDefaultColumnWidth((short) 18);
				//设置页脚
				Footer footer = sheet1.getFooter();
				footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
 				wb.setSheetName(sheetIndex ++, entry.getKey());
 				Map<String, Object> valMap = entry.getValue();
 				String title = valMap.get("title").toString();
 				String companyName = valMap.get("companyName").toString();
 				String brandName = valMap.get("brandUnitName").toString();

 				//第一行 标题
				Row row0 = sheet1.createRow(0);
				row0.setHeightInPoints(25);
				Cell cell0 = row0.createCell((short) 0);
				cell0.setCellValue(title);
				CellStyle firstRowStyle = wb.createCellStyle();
				firstRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				Font firstRowsFont3 = wb.createFont();
				firstRowsFont3.setFontHeightInPoints((short) 18);
				firstRowsFont3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				firstRowStyle.setFont(firstRowsFont3);
				cell0.setCellStyle(firstRowStyle);
				//合并单元格
				CellRangeAddress rg0 = new CellRangeAddress(0, 0, 0, 4);
				sheet1.addMergedRegion(rg0);
				// 第二行 供应商
				Row row1 = sheet1.createRow(1);
				row1.setHeightInPoints(16);
				Cell cell1 = row1.createCell((short) 0);
				cell1.setCellValue("  核对公司："+companyName +" / 核对品牌："+brandName);
				CellStyle secondRowStyle = wb.createCellStyle();
				secondRowStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font secondRowsFont3 = wb.createFont();
				secondRowsFont3.setFontHeightInPoints((short) 11);
				secondRowStyle.setFont(secondRowsFont3);
				cell1.setCellStyle(secondRowStyle);
				CellRangeAddress rg1 = new CellRangeAddress(1, 1, 0, 4);
				sheet1.addMergedRegion(rg1);
				// title样式
				CellStyle titleStyle = wb.createCellStyle();
				titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				Font titleRowFont = wb.createFont();
				titleRowFont.setFontHeightInPoints((short) 11);
				titleRowFont.setColor(HSSFColor.RED.index);
				titleStyle.setFont(titleRowFont);
				// 差异样式
				CellStyle diffStyle = wb.createCellStyle();
				Font diffRowFont = wb.createFont();
				diffRowFont.setFontHeightInPoints((short) 10);
				diffStyle.setFont(diffRowFont);
				diffStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				diffStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				// 单元格样式
				CellStyle cellStyle = wb.createCellStyle();
				Font cellFont = wb.createFont();
				cellFont.setFontHeightInPoints((short) 10);
				cellStyle.setFont(diffRowFont);
				Integer rowIndex = 2;
				int sortIndex = 1;
				while(true){
					for (Entry<String, Object> valEntry : valMap.entrySet()) {
						String key = valEntry.getKey();
						if(valEntry.getValue() instanceof String){
							continue;
						}
						List<PrintBalanceEntry> lstEntry = (List<PrintBalanceEntry>) valEntry.getValue();
						if(key.equals("purchase") && sortIndex == 1){
							String rowTitle  = "  采购核对：期间结存的采购入库+采购退回+外区调入 = 地区采购  + 总部代采 + 地区自购 + 跨区调入";
							if(ShardingUtil.isPE()){
								rowTitle  = "  采购核对：期间结存的采购入库+采购退回+外区调入 = 地区采购  + 总部收购 + 地区自购 + 跨区调入";
							}
							rowIndex = setExcelBalanceVal(lstEntry,sheet1,rowIndex,rowTitle,titleStyle,diffStyle,cellStyle);
							sortIndex ++;
						}else if(key.equals("sales") && sortIndex == 2){
							rowIndex = setExcelBalanceVal(lstEntry,sheet1,rowIndex,"  销售核对：期间结存的销售出库-前期欠客本期发出+本期欠客+外区调出+其他出库=门店销售+批发销售+内购销售+其他出库+跨区调出",titleStyle,diffStyle,cellStyle);
							sortIndex ++;
						}else if(key.equals("transfer") && sortIndex == 3){
							rowIndex = setExcelBalanceVal(lstEntry,sheet1,rowIndex,"  跨区调货核对:期间结存的外区调出 = 跨区调出;期间结存的外区调入 = 跨区调入",titleStyle,diffStyle,cellStyle);
							sortIndex ++;
						}else if(key.equals("oweOff") && sortIndex == 4){
							rowIndex = setExcelBalanceVal(lstEntry,sheet1,rowIndex,"  累计欠客核对:期间结存期初欠客 - 前期欠客本期发出 + 本期欠客 = 期间结存期末欠客",titleStyle,diffStyle,cellStyle);
							sortIndex ++;
						}
	 				}
					if(sortIndex == 5){
						break;
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
	
	private static Integer setExcelBalanceVal(List<PrintBalanceEntry> lstEntry, Sheet sheet1, Integer rowIndex, 
			String title,CellStyle titleStyle,CellStyle diffStyle,CellStyle cellStyle){
		Row row2 = sheet1.createRow(rowIndex ++);
		row2.setHeightInPoints(16);
		Cell cell = row2.createCell(0);
		cell.setCellStyle(titleStyle);
		cell.setCellValue(title);
		Row row3 = sheet1.createRow(rowIndex ++);
		Cell cell30 =  row3.createCell(0);
		Cell cell31 =  row3.createCell(1);
		Cell cell32 =  row3.createCell(2);
		Cell cell33 =  row3.createCell(3);
		Cell cell34 =  row3.createCell(4);
		cell30.setCellValue("报表");
		cell31.setCellValue("值");
		cell32.setCellValue("数量");
		cell33.setCellValue("金额");
		cell34.setCellValue("成本金额");
		sheet1.setColumnWidth(0, 160*44);
		sheet1.setColumnWidth(1, 160*44);
		sheet1.setColumnWidth(2, 160*44);
		sheet1.setColumnWidth(3, 160*44);
		sheet1.setColumnWidth(4, 160*44);
		cell30.setCellStyle(cellStyle);
		cell31.setCellStyle(cellStyle);
		cell32.setCellStyle(cellStyle);
		cell33.setCellStyle(cellStyle);
		cell34.setCellStyle(cellStyle);
		for (PrintBalanceEntry balanceEntry : lstEntry) {
			Row row4 = sheet1.createRow(rowIndex ++);
			row4.setHeightInPoints(16);
			Cell cell40 =  row4.createCell(0);
			Cell cell41 =  row4.createCell(1);
			Cell cell42 =  row4.createCell(2);
			Cell cell43 =  row4.createCell(3);
			Cell cell44 =  row4.createCell(4);
			cell40.setCellValue(balanceEntry.getSourse());
			cell41.setCellValue(balanceEntry.getTitle());
			cell42.setCellValue(balanceEntry.getQty());
			cell43.setCellValue(balanceEntry.getAmount().doubleValue());
			cell44.setCellValue(balanceEntry.getCost().doubleValue());
			if(balanceEntry.getTitle().indexOf("差异") != -1){
				cell40.setCellStyle(diffStyle);
				cell41.setCellStyle(diffStyle);
				cell42.setCellStyle(diffStyle);
				cell43.setCellStyle(diffStyle);
				cell44.setCellStyle(diffStyle);
			}else{
				cell40.setCellStyle(cellStyle);
				cell41.setCellStyle(cellStyle);
				cell42.setCellStyle(cellStyle);
				cell43.setCellStyle(cellStyle);
				cell44.setCellStyle(cellStyle);
			}
		}
		rowIndex ++;
		return rowIndex;
	}
}