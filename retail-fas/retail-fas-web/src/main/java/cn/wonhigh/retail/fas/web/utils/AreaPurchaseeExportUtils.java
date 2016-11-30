/**  
*   
* 项目名称：retail-fas-web  
* 类名称：AreaPurchaseeExportUtils  
* 类描述：地区自购结算单导出工具类
* 创建人：ouyang.zm  
* 创建时间：2014-11-29 下午2:54:25  
* @version       
*/ 
package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.AreaPurchaseBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.web.controller.BaseController;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
@SuppressWarnings({"rawtypes",})
public class AreaPurchaseeExportUtils {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);


	/**
	 * 导出
	 * @param fileName 导出文件名
	 * @param exportColumns 导出列名
	 * @param dataList 导出数据
	 * @param response 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(String formData,String fileName, String exportColumns, List dataList, HttpServletResponse response,int signFlag) throws Exception {
		List<Map> ColumnsList =  getColumnList(exportColumns);
		List<Map> dataMapList = getDataList(dataList);
		if (signFlag == 0) {//明细导出
			Map formMap = toHashMap(formData);
			ExportData(formMap, fileName, ColumnsList, dataMapList, response);
		} else if (signFlag == 1) {//列表导出
			ExportData(fileName, ColumnsList, dataMapList, response);
		}
	}
	
	/**
	 * 明细导出
	 * @param fileName
	 * @param exportColumns
	 * @param dataList
	 * @param response
	 * @param signFlag
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void doFasExport(String fileName, String exportColumns, List dataList, HttpServletResponse response) throws Exception {
		List<Map> ColumnsList =  getColumnList(exportColumns);
		ExportDtlData(fileName, ColumnsList, dataList, response);
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
	 * json字符串转map
	 * @param jsonData
	 * @return
	 * @throws ManagerException 
	 */
	public static Map toHashMap(String jsonData) throws ManagerException {
		Map map=new HashMap<String, Object>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			 map = mapper.readValue(jsonData, new TypeReference<Map>() {});
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 获取导出数据map
	 * @param dataList 导出数据
	 * @return List<Map>
	 * @throws Exception
	 */
	public static List<Map> getDataList(List<Object> dataList) throws Exception {
		if(CollectionUtils.isEmpty(dataList)){
			return null;
		}
		List<Map> dataMapList = new ArrayList<Map>();
		for (Object object : dataList) {
			Map map = new HashMap();
			BeanUtilsCommon.object2MapWithoutNull(object, map);
			dataMapList.add(map);
		}
		return dataMapList;
	}
	
	/**
	 * 导出明细
	 * @param fileName 导出文件名
	 * @param ColumnsMapList 导出列名map 
	 * @param dataMapList 导出数据map
	 * @param response
	 * @throws Exception
	 */
	public static void ExportData(Map formMap,String fileName, List<Map> ColumnsMapList, List<Map> dataMapList,
			HttpServletResponse response) throws Exception {
		if (CollectionUtils.isEmpty(ColumnsMapList)
				|| CollectionUtils.isEmpty(dataMapList)) {
			return;
		}
		if (StringUtils.isBlank(fileName)) {
			fileName = "导出信息";
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		// 文件名
		response.setHeader("Content-Disposition", "attachment;filename="+ fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");
		
		SXSSFWorkbook wb = new SXSSFWorkbook(1);
		Sheet sheet1 = wb.createSheet();
		wb.setSheetName(0, fileName);
		sheet1.setDefaultRowHeightInPoints(20);
		sheet1.setDefaultColumnWidth((short) 18);
		// 设置页脚
		Footer footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "+ HSSFFooter.numPages());
		// 设置标题 合并
		CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0,(short) (ColumnsMapList.size() - 1));
		sheet1.addMergedRegion(rg1);
		Row row0 = sheet1.createRow(0);
		CellStyle style3 = wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		Font font3 = wb.createFont();
		font3.setFontHeightInPoints((short) 20);
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style3.setFont(font3);
		Cell cell0 = row0.createCell((short) 0);
		row0.setHeightInPoints(30);
		cell0.setCellValue(formMap.get("balanceStartDate")+"至"+formMap.get("balanceEndDate")+" "+fileName.toString());
		cell0.setCellStyle(style3);
		
		//设置第二行数据(主档数据)
		Row mainRow=sheet1.createRow(1);
		Cell balanceCell=mainRow.createCell((short)0);
		balanceCell.setCellValue("结算单号："+formMap.get("billNo"));
		
		Row secondRow=sheet1.createRow(2);
		Cell salerCell=secondRow.createCell((short)0);
		salerCell.setCellValue("供应商："+formMap.get("salerName").toString());
		Cell buyerCell=secondRow.createCell((short)1);
		buyerCell.setCellValue("公司："+formMap.get("buyerName").toString());
		
		Row thirdRow=sheet1.createRow(3);
		Cell balanceDate=thirdRow.createCell((short)0);
		balanceDate.setCellValue("结算期："+formMap.get("balanceStartDate")+"至"+formMap.get("balanceEndDate"));
		Cell brandNo=thirdRow.createCell((short)1);
		brandNo.setCellValue("品牌部："+formMap.get("brandUnitName").toString());
		
		Row fourthRow=sheet1.createRow(4);
		Cell outAmount=fourthRow.createCell((short)0);
		outAmount.setCellValue("入库金额："+formMap.get("entryAmount").toString());
		Cell deductionAmount=fourthRow.createCell((short)1);
		deductionAmount.setCellValue("扣项金额："+formMap.get("deductionAmount").toString());
		Cell balanceAmount=fourthRow.createCell((short)2);
		balanceAmount.setCellValue("应付金额："+formMap.get("balanceAmount").toString());
		
		// 设置 表格标题样式
		CellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		Font font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style1.setFont(font1);
	
		// 文本样式 
		CellStyle textStyle = wb.createCellStyle();
		textStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		textStyle.setFont(font1);
		textStyle.setWrapText(true);
		
		//数字样式
		CellStyle numStyle = wb.createCellStyle();
		numStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		numStyle.setFont(font1);
		numStyle.setWrapText(true);
		
		int index = 6;
		// 设置表格title
		Row row1 = sheet1.createRow(index);
		row1.setHeightInPoints(20);
		for (int i = 0; i < ColumnsMapList.size(); i++) {
			Cell cell1 = row1.createCell(i);
			cell1.setCellType(HSSFCell.ENCODING_UTF_16);
			cell1.setCellValue(String.valueOf(ColumnsMapList.get(i).get("title") == null ? "" : ColumnsMapList.get(i).get("title")));
			cell1.setCellStyle(style1);
			if (null != ColumnsMapList.get(i).get("width")) {
				sheet1.setColumnWidth(i, Integer.parseInt(String.valueOf(ColumnsMapList.get(i).get("width"))) * 44);
			} else {
				sheet1.setColumnWidth(i, 120 * 44);
			}
		}

		// 获取datagrid填充数据
		for (int j = 0; j < dataMapList.size(); j++) {
			Row row2 = sheet1.createRow((j + index + 1)); // 第三行开始填充数据
			Map cellDataMap = dataMapList.get(j);
			for (int i = 0; i < ColumnsMapList.size(); i++) {
				Cell cell = row2.createCell(i);
				String cellValue = StringUtils.EMPTY;
				if (ColumnsMapList.get(i).get("field") != null) {
					String fieldString = String.valueOf(ColumnsMapList.get(i).get("field") == null ? "" : ColumnsMapList.get(i).get("field"));
					cellValue = String.valueOf(cellDataMap.get(fieldString) == null ? "": cellDataMap.get(fieldString));
				}
				if(NumberValidationUtils.isRealNumber(cellValue)){
					cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
					cell.setCellStyle(numStyle);
				}else{
					cell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
					cell.setCellStyle(textStyle);
				}
			}

		}
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		wb.dispose();
	}
	
	/**
	 * 列表导出
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
		int size = dataMapList.size();
		int count = 60000;
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
			CellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			Font font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 11);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style1.setFont(font1);
			// 设置单元格样式
			CellStyle numberCellStyle = wb.createCellStyle();
			numberCellStyle.setFont(font1);
			numberCellStyle.setWrapText(true);
			numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			CellStyle stringCellStyle = wb.createCellStyle();
			stringCellStyle.setFont(font1);
			stringCellStyle.setWrapText(true);
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
			fileName = fileName.concat("[").concat(currDate).concat("]");
			cell0.setCellValue(fileName.toString());
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
						if(fieldString.equals("status")){
							cellValue=AreaPurchaseBalanceStatusEnums.getTypeName(Integer.parseInt(cellValue));
						}else{
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
						}
					}
					if(NumberValidationUtils.isRealNumber(cellValue)){
						cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
						cell.setCellStyle(numberCellStyle);
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
	}

	/**
	 * 明细导出
	 * @param fileName
	 * @param columnsList
	 * @param dataMapList
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public static void ExportDtlData(String fileName, List<Map> ColumnsMapList, List<Map> dataList,
			HttpServletResponse response) throws Exception {
		if (CollectionUtils.isEmpty(ColumnsMapList)
				|| CollectionUtils.isEmpty(dataList)) {
			return;
		}
		if (StringUtils.isBlank(fileName)) {
			fileName = "导出信息";
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		// 文件名
		response.setHeader("Content-Disposition", "attachment;filename="+ fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");
		
		String currDate = DateUtil.getCurrentDateTimeToStr2();
		fileName = fileName.concat("[").concat(currDate).concat("]");
		
		SXSSFWorkbook wb = new SXSSFWorkbook(1);
		int sheetIndex=0;
		for (Map map : dataList) {
			Sheet sheet1 = wb.createSheet();
			sheet1.setDefaultRowHeightInPoints(20);
			sheet1.setDefaultColumnWidth((short) 18);
			// 设置页脚
			Footer footer = sheet1.getFooter();
			footer.setRight("Page " + HSSFFooter.page() + " of "+ HSSFFooter.numPages());
			// // 设置标题 合并
			CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0,(short) (ColumnsMapList.size() - 1));
			sheet1.addMergedRegion(rg1);
			Row row0 = sheet1.createRow(0);
			CellStyle style3 = wb.createCellStyle();
			style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			Font font3 = wb.createFont();
			font3.setFontHeightInPoints((short) 20);
			font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style3.setFont(font3);
			
			BillBalance bill=(BillBalance) map.get("bill");
			wb.setSheetName(sheetIndex++, bill.getBillNo());
			Cell cell0 = row0.createCell((short) 0);
			row0.setHeightInPoints(30);
			cell0.setCellValue(fileName.toString());
			cell0.setCellStyle(style3);
			
			//设置第二行数据(主档数据)
			Row mainRow=sheet1.createRow(1);
			Cell balanceCell=mainRow.createCell((short)0);
			balanceCell.setCellValue("结算单号："+bill.getBillNo());
			
			Row secondRow=sheet1.createRow(2);
			Cell salerCell=secondRow.createCell((short)0);
			salerCell.setCellValue("供应商："+bill.getSalerName());
			Cell buyerCell=secondRow.createCell((short)1);
			buyerCell.setCellValue("公司："+bill.getBuyerName());
			
			Row thirdRow=sheet1.createRow(3);
			Cell balanceDate=thirdRow.createCell((short)0);
			balanceDate.setCellValue("结算期："+DateUtil.formatDate(bill.getBalanceStartDate())+"至"+DateUtil.formatDate(bill.getBalanceEndDate()));
			Cell brandNo=thirdRow.createCell((short)1);
			brandNo.setCellValue("品牌部："+bill.getBrandUnitName());
			
			Row FourthRow=sheet1.createRow(4);
			Cell outAmount=FourthRow.createCell((short)0);
			outAmount.setCellValue("入库金额："+bill.getEntryAmount());
			Cell returnAmount=FourthRow.createCell((short)1);
			returnAmount.setCellValue("扣项金额："+bill.getDeductionAmount());
			Cell deductionAmount=FourthRow.createCell((short)2);
			deductionAmount.setCellValue("应付金额："+bill.getBalanceAmount());
			
			// 设置 表格标题样式
			CellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			Font font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 11);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style1.setFont(font1);
			
			// 文本样式 
			CellStyle textStyle = wb.createCellStyle();
			textStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			textStyle.setFont(font1);
			textStyle.setWrapText(true);
			
			//数字样式
			CellStyle numStyle = wb.createCellStyle();
			numStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			numStyle.setFont(font1);
			numStyle.setWrapText(true);
			
			int index = 5;
			// 设置表格title
			Row row1 = sheet1.createRow(index);
			row1.setHeightInPoints(20);
			for (int i = 0; i < ColumnsMapList.size(); i++) {
				Cell cell1 = row1.createCell(i);
				cell1.setCellType(HSSFCell.ENCODING_UTF_16);
				cell1.setCellValue(String.valueOf(ColumnsMapList.get(i).get("title") == null ? "" : ColumnsMapList.get(i).get(
						"title")));
				cell1.setCellStyle(style1);
				if (null != ColumnsMapList.get(i).get("width")) {
					sheet1.setColumnWidth(i, Integer.parseInt(String
							.valueOf(ColumnsMapList.get(i).get("width"))) * 44);
				} else {
					sheet1.setColumnWidth(i, 120 * 44);
				}
			}
	
			// 获取datagrid填充数据
			List<Map> dataMapList=(List<Map>) map.get("dtlList");
			for (int j = 0; j < dataMapList.size(); j++) {
				Row row2 = sheet1.createRow((j + index + 1)); // 第三行开始填充数据
				Map cellDataMap = dataMapList.get(j);
				for (int i = 0; i < ColumnsMapList.size(); i++) {
					Cell cell = row2.createCell(i);
					String cellValue = StringUtils.EMPTY;
					if (ColumnsMapList.get(i).get("field") != null) {
						String fieldString = String.valueOf(ColumnsMapList.get(i)
								.get("field") == null ? "" : ColumnsMapList.get(i)
								.get("field"));
						cellValue = String
								.valueOf(cellDataMap.get(fieldString) == null ? ""
										: cellDataMap.get(fieldString));
					}
					if(NumberValidationUtils.isRealNumber(cellValue)){
						cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
						cell.setCellStyle(numStyle);
					}else{
						cell.setCellValue(cellValue.replaceAll(" 00:00:00", ""));
						cell.setCellStyle(textStyle);
					}
				}
	
			}
		}
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		wb.dispose();
	}
}
