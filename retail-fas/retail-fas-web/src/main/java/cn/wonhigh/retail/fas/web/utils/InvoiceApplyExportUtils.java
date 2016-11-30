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
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.CollectionUtils;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

/**
 * 
 * 导出开票申请数据到Excel表中
 */
@SuppressWarnings({"rawtypes",})
public class InvoiceApplyExportUtils {

	/**
	 * 导出
	 * @param fileName 导出文件名
	 * @param exportColumns 导出列名
	 * @param dataList 导出数据
	 * @param response 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(String fileName, String exportColumns, List dataList, HttpServletResponse response,List thList) throws Exception {
		List<Map> ColumnsList =  getColumnList(exportColumns);
		List<Map> dataMapList = getDataList(dataList);
		List<Map> thMapList = getDataList(thList);
		ExportData(fileName, ColumnsList, dataMapList, response,thMapList);
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
	public static void ExportData(String fileName, List<Map> columnsList, List<Map> dataMapList,
			HttpServletResponse response,List<Map> thMapList) throws Exception {

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
			List<Map> thMap = new ArrayList<Map>();
			thMap.addAll(thMapList.subList(0, 1));
			Sheet sheet1 = wb.createSheet();
			sheet1.setDefaultRowHeightInPoints(20);
			sheet1.setDefaultColumnWidth((short) 18);
			//设置页脚
			Footer footer = sheet1.getFooter();
			footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
			
			DataFormat format= wb.createDataFormat();
	
			//设置样式 表头
			CellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			Font font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 11);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style1.setFont(font1);
			// 设置单元格样式
			CellStyle numberCellStyle = wb.createCellStyle();
			numberCellStyle.setFont(font1);
			numberCellStyle.setWrapText(false);
			numberCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			numberCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			CellStyle BaiCellStyle = wb.createCellStyle();
			BaiCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			BaiCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			BaiCellStyle.setDataFormat(format.getFormat("0.00%")); //设置百分比
			BaiCellStyle.setFont(font1);
			BaiCellStyle.setWrapText(false);
			
			CellStyle QianCellStyle = wb.createCellStyle();
			QianCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			QianCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			QianCellStyle.setDataFormat(format.getFormat("#,##0.00")); //千位数格式设置
			QianCellStyle.setFont(font1);
			QianCellStyle.setWrapText(false);
			
			CellStyle stringCellStyle = wb.createCellStyle();
			stringCellStyle.setFont(font1);
			stringCellStyle.setWrapText(false);//设置自动换行
			stringCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			stringCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//合并
			CellRangeAddress rg1 = new CellRangeAddress(0, (short) 0, 0, (short) 8);
			sheet1.addMergedRegion(rg1);
			//合并
			CellRangeAddress rg2 = new CellRangeAddress(1, (short) 1, 0, (short) 1);
			sheet1.addMergedRegion(rg2);
			//合并
			CellRangeAddress rg3 = new CellRangeAddress(1, (short) 1, 2, (short) 4);
			sheet1.addMergedRegion(rg3);
			//合并
			CellRangeAddress rg4 = new CellRangeAddress(1, (short) 1, 5, (short) (columnsList.size() - 1));
			sheet1.addMergedRegion(rg4);
			//设置样式 表头
			CellStyle style3 = wb.createCellStyle();
			style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			Font font3 = wb.createFont();
			font3.setFontHeightInPoints((short) 16);
			font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style3.setFont(font3);
			Row row0 = sheet1.createRow(0);
			row0.setHeightInPoints(30);
			//第一行 提示长
			Cell cell0 = row0.createCell((short) 0);
			cell0.setCellValue(fileName.toString());
			cell0.setCellStyle(style3);
			
			//增加一行开票申请日期
			//设置开票申请样式 表头
			CellStyle style4 = wb.createCellStyle();
			style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			Font font4 = wb.createFont();
			font4.setFontHeightInPoints((short) 13);
			font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style4.setFont(font4);
			
			Row row3 = sheet1.createRow(1);
			row3.setHeightInPoints(30);
			
			//给一列值
			Map InvoiceApplyDataMap = thMap.get(0);
			Cell cell2 = row3.createCell((short) 0);
			cell2.setCellValue("申请开票日期:"+InvoiceApplyDataMap.get("buyerName"));
			cell2.setCellStyle(style4);
			cell2 = row3.createCell((short) 2);
			cell2.setCellValue("商场交票日期:"+InvoiceApplyDataMap.get("categoryName"));
			cell2.setCellStyle(style4);
			cell2 = row3.createCell((short) 5);
			cell2.setCellValue("纳税人识别号:"+InvoiceApplyDataMap.get("taxRegistryNo"));
			cell2.setCellStyle(style4);
			
			int index = 2;
			
			//设置列头
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
						if(columnsList.get(i).get("field").equals("categoryName")){
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
							if(cellValue.indexOf("-")!=-1&cellValue.indexOf("-")>0){
								cellValue=cellValue.replace("-", "");
								StringBuilder sb=new StringBuilder(cellValue);
								sb.insert(8, "-");
								cellValue=sb.toString();
							}
						}else{
							cellValue = String.valueOf(cellDataMap.get(fieldString)==null?"":cellDataMap.get(fieldString));
						}
					}
					if(NumberValidationUtils.isRealNumber(cellValue)&& Double.parseDouble(cellValue) - Integer.MAX_VALUE < 0){
						if(columnsList.get(i).get("field").equals("contractRate")){
							cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
							cell.setCellStyle(BaiCellStyle);
							if(cellValue!=""){
								cell.setCellValue(Double.parseDouble(cellValue)/100);
							}
						}else if(columnsList.get(i).get("field").equals("actualRate")){
							cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
							if(cellValue!=""){
								cell.setCellValue(Double.parseDouble(cellValue)/100);
							}
							cell.setCellStyle(BaiCellStyle);
						}else if(columnsList.get(i).get("field").equals("amount")){
							cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
							cell.setCellStyle(QianCellStyle);
						}else if(columnsList.get(i).get("field").equals("posEarningAmount")){
							cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
							cell.setCellStyle(QianCellStyle);
						}else if(columnsList.get(i).get("field").equals("estimatedAmount")){
							cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
							cell.setCellStyle(QianCellStyle);
						}else{
							cell.setCellValue(Double.parseDouble(cellValue==""?"0.00":cellValue));
							cell.setCellStyle(numberCellStyle);
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
	}
	
}