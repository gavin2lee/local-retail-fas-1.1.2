package cn.wonhigh.retail.fas.web.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.yougou.logistics.base.common.exception.ManagerException;

import cn.wonhigh.retail.fas.web.controller.BaseController;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.fas.web.vo.HeaderFormDataVo;
import cn.wonhigh.retail.fas.web.vo.MutliSheetExportVo;

/**
 * 
 * 导出查询数据到Excel表中(符合表头，数据跨行显示)
 * 
 * @author 杨勇
 * @date 2014-7-8 下午4:27:03
 * @version 0.1.0 
 * @copyright yougou.com
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HSSFExportComplex implements AutoCloseable {
	protected static final XLogger logger = XLoggerFactory.getXLogger(HSSFExportComplex.class);

	SXSSFWorkbook wb;
	Sheet sheet;
	private int rowIndex = 0;
	private List<Map> columns;
	Map<Integer, Integer> columnLenMap;
	//设置样式 表头
	CellStyle contentStyle;
	CellStyle floatContentStyle;
	CellStyle numberContentStyle;
	ExportComplexVo exportVo;
	final static int offset = 2;
	String fileName;
	SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public HSSFExportComplex(ExportComplexVo exportVo) {
		this.exportVo = exportVo;
		this.fileName = this.exportVo.getFileName();
		init();
	}
	
	public Integer getRowIndex(){
		return rowIndex - offset;
	}
	
	void init() {
		//response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");		 
		//String fileName = exportVo.getFileName();
		//文件名
		//response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		//response.setHeader("Pragma", "no-cache");

		Integer rowAccessWindowSize = exportVo.getRowAccessWindowSize();
		if (rowAccessWindowSize == null) {
			rowAccessWindowSize = 1;
		}

		wb = new SXSSFWorkbook(rowAccessWindowSize.intValue());
		sheet = wb.createSheet();
		((SXSSFSheet) sheet).setRandomAccessWindowSize(10);
		wb.setSheetName(0, exportVo.getFileName());
		sheet.setDefaultColumnWidth((short) 18);

		//设置样式 表头
		CellStyle headerStyle = null;

		columns = new ArrayList<Map>(exportVo.getColumnsMapList());
		if (exportVo.getSubColumnsMapList() != null && exportVo.getSubColumnsMapList().size() > 0) {
			columns.addAll(exportVo.getSubColumnsMapList());
		}

		List<Map> firstHeaderColumns = null;
		int rangeCellCount = columns.size() - 1;
		if (exportVo.getHeaderColumnsMapList() != null && exportVo.getHeaderColumnsMapList().size() > 0) {
			firstHeaderColumns = new ArrayList<Map>(exportVo.getHeaderColumnsMapList());
			// TODO 待修改
			rangeCellCount += firstHeaderColumns.size() - 5;
			headerStyle = getHeaderStyle(wb, false);
		} else {
			headerStyle = getHeaderStyle(wb, true);
		}
		// 存放每列的最大字符数
		columnLenMap = initMap(columns.size());
		// 第一行 提示长:设置表头-合并
		createCellRange(0, rangeCellCount, exportVo.getFileName() + "" + simdf.format(new Date()), wb, sheet);

		contentStyle = getContentStyle(wb, false);
		numberContentStyle = getContentStyle(wb, true);
		floatContentStyle = getContentFloatStyle(wb);
		//设置表头
		Row headerRow = sheet.createRow(1);
		//默认从第三行开始填充数据
		rowIndex = offset;
		int columnIndex = 0;
		// 定义跨行的列集合
		List<Map> lstRowspanColumn = null;
		if (firstHeaderColumns != null && firstHeaderColumns.size() > 0) {
			//设置复合表头样式
			CellStyle fixHeaderStyle = getFixHeaderStyle(wb);
			lstRowspanColumn = new ArrayList<Map>();
			int colspanIndex = 0, colspanCount = 0, maxRowspan = 2;
			for (int i = 0; i < firstHeaderColumns.size(); i++) {
				int rowspan = firstHeaderColumns.get(i).get("rowspan") == null ? 1 : Integer
						.parseInt(firstHeaderColumns.get(i).get("rowspan").toString());
				int colspan = firstHeaderColumns.get(i).get("colspan") == null ? 0 : (Integer
						.parseInt(firstHeaderColumns.get(i).get("colspan").toString()) - 1);
				if (maxRowspan < rowspan) {
					maxRowspan = rowspan;
				}
				Cell headerCell = headerRow.createCell(colspanIndex);
				headerCell.setCellType(HSSFCell.ENCODING_UTF_16);
				headerCell.setCellValue(firstHeaderColumns.get(i).get("title").toString());
				headerCell.setCellStyle(fixHeaderStyle);
				//合并单元格
				if (rowspan > 1) {
					lstRowspanColumn.add(firstHeaderColumns.get(i));
				}
				CellRangeAddress rg = new CellRangeAddress(1, rowspan, colspanIndex, colspanIndex + colspan);
				sheet.addMergedRegion(rg);
				// 如果当前列是跨行了或者没跨行也没跨列，则将colspanCount加1
				if (colspan > 0 || (rowspan == 1 && colspan == 0)) {
					colspanCount++;
				}
				colspanIndex += (colspan + 1);
			}
			// 获取第二行或以下行中，列的开始索引，如期间结存中，前几列是跨行了，那么在设置第二列表头时，需要知道从第几列设置表头
			columnIndex = firstHeaderColumns.size() - colspanCount;
			headerRow = sheet.createRow(maxRowspan);
			rowIndex = maxRowspan + 1;
		}
		// 设置表头列
		for (int i = 0; i < columns.size(); i++) {
			Cell cell1 = headerRow.createCell(i + columnIndex);
			cell1.setCellType(HSSFCell.ENCODING_UTF_16);
			String title = columns.get(i).get("title").toString();
			int columnSize = columnLenMap.get(i);
			if (columnSize < title.length()) {
				columnLenMap.put(i, title.length());
			}
			cell1.setCellValue(title);
			cell1.setCellStyle(headerStyle);
		}

		// 判断是否有跨行的列，如果有，则需要将其加入到columns集合中，取出其中的值显示在excel中，如期间结存
		if (lstRowspanColumn != null && lstRowspanColumn.size() > 0) {
			List<Map> lstColumns = new ArrayList<Map>();
			lstColumns.addAll(lstRowspanColumn);
			lstColumns.addAll(columns);
			columns = lstColumns;
		}
	}

	public void write(Map cellDataMap) {
		Row row = sheet.createRow(rowIndex); // 第三行开始填充数据 
		for (int i = 0; i < columns.size(); i++) {
			Cell cell = row.createCell(i);
			String cellValue = StringUtils.EMPTY;
			if (columns.get(i).get("field") != null) {
				String fieldString = String.valueOf(columns.get(i).get("field"));
				// 复合表头-期末结存查询
				if (exportVo.getHeaderColumnsMapList() != null && exportVo.getHeaderColumnsMapList().size() > 0) {
					cellValue = String.valueOf(cellDataMap.get(fieldString));
				} else {
					if (i < exportVo.getColumnsMapList().size()) {
						cellValue = String.valueOf(cellDataMap.get(fieldString));
					}
				}
			}
			if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
				cellValue = StringUtils.EMPTY;
			}
			// 设置单元格样式
			Object exportType = columns.get(i).get("exportType");
			if (exportType != null && "number".equalsIgnoreCase(exportType.toString())) {
				cell.setCellStyle(numberContentStyle);
			} else {
				if (NumberValidationUtils.isRealNumber(cellValue)) {
					if (NumberValidationUtils.isInteger(cellValue)) {
						cell.setCellStyle(numberContentStyle);
					} else {
						cell.setCellStyle(floatContentStyle);
					}
				} else {
					cell.setCellStyle(contentStyle);
				}
			}
			// 设置单元格的值
			setCellValue(cell, cellValue, columns.get(i));
			Integer columnSize = columnLenMap.get(i);
			if (columnSize != null && columnSize < cellValue.length()) {
				columnLenMap.put(i, cellValue.length());
			}
		}
		rowIndex++;
		if( rowIndex % 10000 == 1 )
			logger.info("export excel file " + fileName + " at " + rowIndex);
	}

	public void write(List<Map> dataMapList) {
		//循环主表的数据集合，填充数据
		for (int j = 0; j < dataMapList.size(); j++) {
			Map cellDataMap = dataMapList.get(j);
			write(cellDataMap);
		}
	}

	public void flush(OutputStream stream) throws IOException {
		for (int i = 0; i < columnLenMap.size(); i++) {
			int width = columnLenMap.get(i);
			if (width > 0) {
				sheet.setColumnWidth(i, (columnLenMap.get(i) + 5) * 256);
			}
		}
		wb.write(stream);
		stream.flush();
	}

	public void flush(HttpServletResponse response) throws IOException, ManagerException {
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		try {
			String fileName = new String(exportVo.getFileName().getBytes("gb2312"), "iso-8859-1");
			//文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		
		response.setHeader("Pragma", "no-cache");
		flush(response.getOutputStream());
	}

	public void close() throws Exception {
		sheet = null;
		columnLenMap = null;
		//设置样式 表头
		contentStyle = null;
		numberContentStyle = null;
		if (wb != null)
			wb.dispose();
		wb = null;
		logger.info("export excel file " + fileName + " finished.");
	}

	/**
	 * 导出数据到Excel, 自动获取easyui的表头信息 与 查询条件, 支持合并的表头
	 * 
	 * @param exportVo 封装导出符合表头属性的对象
	 * @param response HttpServletResponse
	 * @throws Exception 异常
	 */
	public static void commonExportData(ExportComplexVo exportVo, HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fileName = new String(exportVo.getFileName().getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		response.setHeader("Pragma", "no-cache");

		Integer rowAccessWindowSize = exportVo.getRowAccessWindowSize();
		if (rowAccessWindowSize == null) {
			rowAccessWindowSize = 1;
		}
		SXSSFWorkbook wb = new SXSSFWorkbook(rowAccessWindowSize.intValue());
		Sheet sheet = wb.createSheet();
		((SXSSFSheet) sheet).setRandomAccessWindowSize(10);
		wb.setSheetName(0, exportVo.getFileName());
		sheet.setDefaultColumnWidth((short) 18);
		//设置页脚
		Footer footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());

		//设置样式 表头
		CellStyle contentStyle = getContentStyle(wb, false);

		//设置样式 表头
		CellStyle numberContentStyle = getContentStyle(wb, true);

		List<Map> columns = new ArrayList<Map>(exportVo.getColumnsMapList());
		if (exportVo.getSubColumnsMapList() != null && exportVo.getSubColumnsMapList().size() > 0) {
			columns.addAll(exportVo.getSubColumnsMapList());
		}

		//设置样式 表头
		CellStyle headerStyle = null;

		List<Map> firstHeaderColumns = null;
		int rangeCellCount = columns.size() - 1;
		if (exportVo.getHeaderColumnsMapList() != null && exportVo.getHeaderColumnsMapList().size() > 0) {
			firstHeaderColumns = new ArrayList<Map>(exportVo.getHeaderColumnsMapList());
			// TODO 待修改
			rangeCellCount += firstHeaderColumns.size() - 5;
			headerStyle = getHeaderStyle(wb, false);
		} else {
			headerStyle = getHeaderStyle(wb, true);
		}
		// 存放每列的最大字符数
		Map<Integer, Integer> columnLenMap = initMap(columns.size());
		// 第一行 提示长:设置表头-合并
		createCellRange(0, rangeCellCount, exportVo.getFileName() + "" + simdf.format(new Date()), wb, sheet);

		//设置表头
		Row headerRow = sheet.createRow(1);
		//默认从第三行开始填充数据
		int rowIndex = 2;
		int columnIndex = 0;
		// 定义跨行的列集合
		List<Map> lstRowspanColumn = null;
		if (firstHeaderColumns != null && firstHeaderColumns.size() > 0) {
			//设置复合表头样式
			CellStyle fixHeaderStyle = getFixHeaderStyle(wb);
			lstRowspanColumn = new ArrayList<Map>();
			int colspanIndex = 0, colspanCount = 0, maxRowspan = 2;
			for (int i = 0; i < firstHeaderColumns.size(); i++) {
				int rowspan = firstHeaderColumns.get(i).get("rowspan") == null ? 1 : Integer
						.parseInt(firstHeaderColumns.get(i).get("rowspan").toString());
				int colspan = firstHeaderColumns.get(i).get("colspan") == null ? 0 : (Integer
						.parseInt(firstHeaderColumns.get(i).get("colspan").toString()) - 1);
				if (maxRowspan < rowspan) {
					maxRowspan = rowspan;
				}
				Cell headerCell = headerRow.createCell(colspanIndex);
				headerCell.setCellType(HSSFCell.ENCODING_UTF_16);
				headerCell.setCellValue(firstHeaderColumns.get(i).get("title").toString());
				headerCell.setCellStyle(fixHeaderStyle);
				//合并单元格
				if (rowspan > 1) {
					lstRowspanColumn.add(firstHeaderColumns.get(i));
				}
				CellRangeAddress rg = new CellRangeAddress(1, rowspan, colspanIndex, colspanIndex + colspan);
				sheet.addMergedRegion(rg);
				// 如果当前列是跨行了或者没跨行也没跨列，则将colspanCount加1
				if (colspan > 0 || (rowspan == 1 && colspan == 0)) {
					colspanCount++;
				}
				colspanIndex += (colspan + 1);
			}
			// 获取第二行或以下行中，列的开始索引，如期间结存中，前几列是跨行了，那么在设置第二列表头时，需要知道从第几列设置表头
			columnIndex = firstHeaderColumns.size() - colspanCount;
			headerRow = sheet.createRow(maxRowspan);
			rowIndex = maxRowspan + 1;
		}
		// 设置表头列
		for (int i = 0; i < columns.size(); i++) {
			Cell cell1 = headerRow.createCell(i + columnIndex);
			cell1.setCellType(HSSFCell.ENCODING_UTF_16);
			String title = columns.get(i).get("title").toString();
			int columnSize = columnLenMap.get(i);
			if (columnSize < title.length()) {
				columnLenMap.put(i, title.length());
			}
			cell1.setCellValue(title);
			cell1.setCellStyle(headerStyle);
		}
		// 判断是否有跨行的列，如果有，则需要将其加入到columns集合中，取出其中的值显示在excel中，如期间结存
		if (lstRowspanColumn != null && lstRowspanColumn.size() > 0) {
			List<Map> lstColumns = new ArrayList<Map>();
			lstColumns.addAll(lstRowspanColumn);
			lstColumns.addAll(columns);
			columns = lstColumns;
		}

		//循环主表的数据集合，填充数据
		for (int j = 0; j < exportVo.getDataMapList().size(); j++) {
			Map cellDataMap = exportVo.getDataMapList().get(j);
			//获取该行主表数据对应的子表数据集合
			List<Map> subCellDataMap = (List<Map>) cellDataMap.get("subExportData");
			//是否已经合并的标识
			boolean isMergedRegion = false;
			//如果子表数据量大于1，则需进行合并行
			if (subCellDataMap.size() > 1) {
				for (int index = 0; index < subCellDataMap.size(); index++) {
					Row row = sheet.createRow(rowIndex); // 创建execl的行对象
					//列循环的开始位置和结束位置，子表的数据第一行和后面行的设置有所不一致，第一行不需要跨行，后面的数据需要跨行
					int startIndex = 0, endIndex = columns.size();
					boolean isFirstIndex = false;
					if (index == 0) {
						isFirstIndex = true;
					} else if (index > 0 && !isMergedRegion) {
						//合并单元格
						CellRangeAddress rg_ = new CellRangeAddress(rowIndex, rowIndex + subCellDataMap.size() - 2, 0,
								exportVo.getColumnsMapList().size() - 1);
						sheet.addMergedRegion(rg_);
						isMergedRegion = true;
						startIndex = exportVo.getColumnsMapList().size();
					}
					//循环数据，并填充到对应的单元格中
					for (int i = startIndex; i < endIndex; i++) {
						Cell cell = row.createCell(i);
						String cellValue = StringUtils.EMPTY;
						if (columns.get(i).get("field") != null) {
							String fieldString = String.valueOf(columns.get(i).get("field"));
							if (isFirstIndex && i < exportVo.getColumnsMapList().size()) {
								cellValue = String.valueOf(cellDataMap.get(fieldString));
							} else {
								cellValue = String.valueOf(subCellDataMap.get(index).get(fieldString));
							}
						}
						if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
							cellValue = StringUtils.EMPTY;
						}
						// 设置单元格的值
						setCellValue(cell, cellValue, columns.get(i));
						cell.setCellStyle(contentStyle);
						int columnSize = columnLenMap.get(i);
						if (columnSize < cellValue.length()) {
							columnLenMap.put(i, cellValue.length());
						}
					}
					rowIndex++;
				}
			} else {
				Row row = sheet.createRow(rowIndex); // 第三行开始填充数据 
				for (int i = 0; i < columns.size(); i++) {
					Cell cell = row.createCell(i);
					String cellValue = StringUtils.EMPTY;
					if (columns.get(i).get("field") != null) {
						String fieldString = String.valueOf(columns.get(i).get("field"));
						// 复合表头-期末结存查询
						if (exportVo.getHeaderColumnsMapList() != null && exportVo.getHeaderColumnsMapList().size() > 0) {
							cellValue = String.valueOf(cellDataMap.get(fieldString));
						} else {
							if (i < exportVo.getColumnsMapList().size()) {
								cellValue = String.valueOf(cellDataMap.get(fieldString));
							} else if (subCellDataMap != null && subCellDataMap.size() > 0) {
								cellValue = String.valueOf(subCellDataMap.get(0).get(fieldString));
							}
						}
					}
					if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
						cellValue = StringUtils.EMPTY;
					}
					// 设置单元格样式
					Object exportType = columns.get(i).get("exportType");
					if (exportType != null && "number".equalsIgnoreCase(exportType.toString())) {
						cell.setCellStyle(numberContentStyle);
					} else {
						cell.setCellStyle(contentStyle);
					}
					// 设置单元格的值
					setCellValue(cell, cellValue, columns.get(i));
					Integer columnSize = columnLenMap.get(i);
					if (columnSize != null && columnSize < cellValue.length()) {
						columnLenMap.put(i, cellValue.length());
					}
				}
				rowIndex++;
			}
		}
		for (int i = 0; i < columnLenMap.size(); i++) {
			int width = columnLenMap.get(i);
			if (width > 200) {
				sheet.setColumnWidth(i, (200 + 5) * 256);
			} else if (width > 0) {
				sheet.setColumnWidth(i, (columnLenMap.get(i) + 5) * 256);
			}
		}
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
	}

	/**
	 * 导出数据到Excel, 自动获取easyui的表头信息 与 查询条件, 支持三行合并的表头
	 * 
	 * @param exportVo 封装导出符合表头属性的对象
	 * @param response HttpServletResponse
	 * @throws Exception 异常
	 */
	public static void commonExportDataThreeLine(ExportComplexVo exportVo, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fileName = new String(exportVo.getFileName().getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		response.setHeader("Pragma", "no-cache");

		Integer rowAccessWindowSize = exportVo.getRowAccessWindowSize();
		if (rowAccessWindowSize == null) {
			rowAccessWindowSize = 1;
		}
		SXSSFWorkbook wb = new SXSSFWorkbook(rowAccessWindowSize.intValue());
		Sheet sheet = wb.createSheet();
		((SXSSFSheet) sheet).setRandomAccessWindowSize(10);
		wb.setSheetName(0, exportVo.getFileName());
		sheet.setDefaultColumnWidth((short) 18);
		((SXSSFSheet) sheet).setRandomAccessWindowSize(100);
		//设置页脚
		Footer footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());

		//设置样式 表头
		CellStyle contentStyle = getContentStyle(wb, false);

		//设置样式 表头
		CellStyle numberContentStyle = getContentStyle(wb, true);

		List<Map> columns = new ArrayList<Map>(exportVo.getColumnsMapList());

		List<Map> footerColumns = new ArrayList<Map>(exportVo.getFooterColumnsMapList());
		if (exportVo.getSubColumnsMapList() != null && exportVo.getSubColumnsMapList().size() > 0) {
			columns.addAll(exportVo.getSubColumnsMapList());
		}

		//设置样式 表头
		CellStyle headerStyle = null;

		List<Map> firstHeaderColumns = null;
		int rangeCellCount = footerColumns.size() - 1;
		if (exportVo.getHeaderColumnsMapList() != null && exportVo.getHeaderColumnsMapList().size() > 0) {
			firstHeaderColumns = new ArrayList<Map>(exportVo.getHeaderColumnsMapList());
			// TODO 待修改
			rangeCellCount += firstHeaderColumns.size() - 5;
			headerStyle = getHeaderStyle(wb, false);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		} else {
			headerStyle = getHeaderStyle(wb, true);
		}
		// 存放每列的最大字符数
		Map<Integer, Integer> columnLenMap = initMap(footerColumns.size());

		// 第一行 提示长:设置表头-合并
		createCellRange(0, rangeCellCount, exportVo.getFileName() + "" + simdf.format(new Date()), wb, sheet);

		//设置表头
		Row headerRow = sheet.createRow(1);
		//默认从第三行开始填充数据
		int rowIndex = 2;
		int columnIndex = 0;
		// 定义跨行的列集合
		List<Map> lstRowspanColumn = null;

		// 定义跨行的列集合
		List<Map> lstRowspanThreeLineColumn = null;
		if (firstHeaderColumns != null && firstHeaderColumns.size() > 0) {
			//设置复合表头样式
			CellStyle fixHeaderStyle = getFixHeaderStyle(wb);
			lstRowspanColumn = new ArrayList<Map>();
			lstRowspanThreeLineColumn = new ArrayList<Map>();
			int colspanIndex = 0, colspanCount = 0, maxRowspan = 2;
			for (int i = 0; i < firstHeaderColumns.size(); i++) {
				int rowspan = firstHeaderColumns.get(i).get("rowspan") == null ? 1 : Integer
						.parseInt(firstHeaderColumns.get(i).get("rowspan").toString());
				int colspan = firstHeaderColumns.get(i).get("colspan") == null ? 0 : (Integer
						.parseInt(firstHeaderColumns.get(i).get("colspan").toString()) - 1);
				if (maxRowspan < rowspan) {
					maxRowspan = rowspan;
				}
				Cell headerCell = headerRow.createCell(colspanIndex);
				headerCell.setCellType(HSSFCell.ENCODING_UTF_16);
				headerCell.setCellValue(firstHeaderColumns.get(i).get("title").toString());
				headerCell.setCellStyle(fixHeaderStyle);
				//合并单元格
				if (rowspan > 1) {
					lstRowspanColumn.add(firstHeaderColumns.get(i));
				}
				if (rowspan > 2) {
					lstRowspanThreeLineColumn.add(firstHeaderColumns.get(i));
				}
				CellRangeAddress rg = new CellRangeAddress(1, rowspan, colspanIndex, colspanIndex + colspan);
				sheet.addMergedRegion(rg);
				// 如果当前列是跨行了或者没跨行也没跨列，则将colspanCount加1
				if (colspan > 0 || (rowspan == 1 && colspan == 0)) {
					colspanCount++;
				}
				colspanIndex += (colspan + 1);
			}
			// 获取第二行或以下行中，列的开始索引，如期间结存中，前几列是跨行了，那么在设置第二列表头时，需要知道从第几列设置表头
			columnIndex = firstHeaderColumns.size() - colspanCount;
			headerRow = sheet.createRow(maxRowspan - 1);
			rowIndex = maxRowspan + 1;
		}
		// 设置表头列
		int colspanIndex = 0;
		for (int i = 0; i < columns.size(); i++) {
			Cell cell1 = headerRow.createCell(columnIndex + colspanIndex);
			cell1.setCellType(HSSFCell.ENCODING_UTF_16);
			String title = columns.get(i).get("title").toString();
			int colspan = columns.get(i).get("colspan") == null ? 0 : (Integer.parseInt(columns.get(i).get("colspan")
					.toString()) - 1);
			CellRangeAddress rg = new CellRangeAddress(2, 2, (colspanIndex + columnIndex), (colspanIndex + columnIndex)
					+ colspan);
			sheet.addMergedRegion(rg);
			cell1.setCellValue(title);
			cell1.setCellStyle(headerStyle);
			colspanIndex += (colspan + 1);
		}
		headerRow = sheet.createRow(3);
		// 设置表头列
		for (int i = 0; i < footerColumns.size(); i++) {
			Cell cell2 = headerRow.createCell(i + columnIndex);
			cell2.setCellType(HSSFCell.ENCODING_UTF_16);
			String title = footerColumns.get(i).get("title").toString();
			int columnSize = columnLenMap.get(i);
			if (columnSize < title.length()) {
				columnLenMap.put(i, title.length());
			}
			cell2.setCellValue(title);
			cell2.setCellStyle(headerStyle);
		}
		// 判断是否有跨两行的列，如果有，则需要将其加入到columns集合中，取出其中的值显示在excel中，如期间结存
		if (lstRowspanColumn != null && lstRowspanColumn.size() > 0) {
			List<Map> lstColumns = new ArrayList<Map>();
			lstColumns.addAll(lstRowspanColumn);
			lstColumns.addAll(columns);
			columns = lstColumns;
		}
		// 判断是否有跨三行的列，如果有，则需要将其加入到columns集合中，取出其中的值显示在excel中，如期间结存
		if (lstRowspanThreeLineColumn != null && lstRowspanThreeLineColumn.size() > 0) {
			List<Map> lstColumns = new ArrayList<Map>();
			lstColumns.addAll(lstRowspanThreeLineColumn);
			lstColumns.addAll(footerColumns);
			footerColumns = lstColumns;
		}

		//循环主表的数据集合，填充数据
		for (int j = 0; j < exportVo.getDataMapList().size(); j++) {
			Map cellDataMap = exportVo.getDataMapList().get(j);
			//获取该行主表数据对应的子表数据集合
			List<Map> subCellDataMap = (List<Map>) cellDataMap.get("subExportData");
			//是否已经合并的标识
			boolean isMergedRegion = false;
			//如果子表数据量大于1，则需进行合并行
			if (subCellDataMap.size() > 1) {
				for (int index = 0; index < subCellDataMap.size(); index++) {
					Row row = sheet.createRow(rowIndex); // 创建execl的行对象
					//列循环的开始位置和结束位置，子表的数据第一行和后面行的设置有所不一致，第一行不需要跨行，后面的数据需要跨行
					int startIndex = 0, endIndex = columns.size();
					boolean isFirstIndex = false;
					if (index == 0) {
						isFirstIndex = true;
					} else if (index > 0 && !isMergedRegion) {
						//合并单元格
						CellRangeAddress rg_ = new CellRangeAddress(rowIndex, rowIndex + subCellDataMap.size() - 2, 0,
								exportVo.getColumnsMapList().size() - 1);
						sheet.addMergedRegion(rg_);
						isMergedRegion = true;
						startIndex = exportVo.getColumnsMapList().size();
					}
					//循环数据，并填充到对应的单元格中
					for (int i = startIndex; i < endIndex; i++) {
						Cell cell = row.createCell(i);
						String cellValue = StringUtils.EMPTY;
						if (columns.get(i).get("field") != null) {
							String fieldString = String.valueOf(columns.get(i).get("field"));
							if (isFirstIndex && i < exportVo.getColumnsMapList().size()) {
								cellValue = String.valueOf(cellDataMap.get(fieldString));
							} else {
								cellValue = String.valueOf(subCellDataMap.get(index).get(fieldString));
							}
						}
						if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
							cellValue = StringUtils.EMPTY;
						}
						// 设置单元格的值
						setCellValue(cell, cellValue, columns.get(i));
						cell.setCellStyle(contentStyle);
						int columnSize = columnLenMap.get(i);
						if (columnSize < cellValue.length()) {
							columnLenMap.put(i, cellValue.length());
						}
					}
					rowIndex++;
				}
			} else {
				Row row = sheet.createRow(rowIndex); // 第三行开始填充数据 
				for (int i = 0; i < footerColumns.size(); i++) {
					Cell cell = row.createCell(i);
					String cellValue = StringUtils.EMPTY;
					if (footerColumns.get(i).get("field") != null) {
						String fieldString = String.valueOf(footerColumns.get(i).get("field"));
						// 复合表头-期末结存查询
						if (exportVo.getHeaderColumnsMapList() != null && exportVo.getHeaderColumnsMapList().size() > 0) {
							cellValue = String.valueOf(cellDataMap.get(fieldString));
						} else {
							if (i < exportVo.getColumnsMapList().size()) {
								cellValue = String.valueOf(cellDataMap.get(fieldString));
							} else if (subCellDataMap != null && subCellDataMap.size() > 0) {
								cellValue = String.valueOf(subCellDataMap.get(0).get(fieldString));
							}
						}
					}
					if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
						cellValue = StringUtils.EMPTY;
					}
					// 设置单元格样式
					Object exportType = footerColumns.get(i).get("exportType");
					if (exportType != null && "number".equalsIgnoreCase(exportType.toString())) {
						cell.setCellStyle(numberContentStyle);
					} else {
						cell.setCellStyle(contentStyle);
					}
					// 设置单元格的值
					setCellValue(cell, cellValue, footerColumns.get(i));
					Integer columnSize = columnLenMap.get(i);
					if (columnSize != null && columnSize < cellValue.length()) {
						columnLenMap.put(i, cellValue.length());
					}
				}
				rowIndex++;
			}
		}
		for (int i = 0; i < columnLenMap.size(); i++) {
			int width = columnLenMap.get(i);
			if (width > 200) {
				sheet.setColumnWidth(i, (200 + 5) * 256);
			} else if (width > 0) {
				sheet.setColumnWidth(i, (columnLenMap.get(i) + 5) * 256);
			}
		}
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
	}

	/**
	 * 导出数据到Excel, 自动获取easyui的表头信息 与 查询条件, 支持合并的表头
	 * 
	 * @param exportVo 封装导出符合表头属性的对象
	 * @param response HttpServletResponse
	 * @throws Exception 异常
	 */
	public static void multiSheetExportData(String fileName, List<MutliSheetExportVo> lstVo,
			HttpServletResponse response, Integer rowAccessWindowSize) throws Exception {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String fileName2 = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName2 + ".xlsx");
		response.setHeader("Pragma", "no-cache");
		if (rowAccessWindowSize == null) {
			rowAccessWindowSize = 1;
		}
		SXSSFWorkbook wb = new SXSSFWorkbook(rowAccessWindowSize.intValue());
		for (int ix = 0; ix < lstVo.size(); ix++) {
			Sheet sheet = wb.createSheet();
			((SXSSFSheet) sheet).setRandomAccessWindowSize(10);
			// 组装sheet的名称
			if (lstVo.get(ix).getChildren() != null && lstVo.get(ix).getChildren().size() > 0) {
				StringBuffer sb = new StringBuffer(lstVo.get(ix).getSheetName()).append("&");
				for (int i = 0; i < lstVo.get(ix).getChildren().size(); i++) {
					MutliSheetExportVo childExportVo = lstVo.get(ix).getChildren().get(i);
					if (i < lstVo.get(ix).getChildren().size() - 1) {
						sb.append(childExportVo.getSheetName()).append("&");
					} else {
						sb.append(childExportVo.getSheetName());
					}
				}
				wb.setSheetName(ix, sb.toString());
			} else {
				wb.setSheetName(ix, lstVo.get(ix).getSheetName());
			}
			//设置页脚
			Footer footer = sheet.getFooter();
			footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());

			//设置样式 表头
			CellStyle headerStyle = getHeaderStyle(wb, true);

			// 设置内容样式
			CellStyle contentStyle = getContentStyle(wb, false);

			// 设置数字内容样式
			CellStyle numberContentStyle = getContentStyle(wb, true);

			// 获取该sheet中的最大列数
			int maxColumnLen = getMaxColumnLen(lstVo.get(ix));
			if (lstVo.get(ix).isContainHeaderForm() && maxColumnLen < lstVo.get(ix).getLstHeaderFormDataVo().size()) {
				maxColumnLen = lstVo.get(ix).getLstHeaderFormDataVo().size();
			}
			// 存放每列的最大字符数
			Map<Integer, Integer> columnLenMap = initMap(maxColumnLen);

			//合并:设置样式 -标题
			createCellRange(0, maxColumnLen - 1, fileName, wb, sheet);

			int startRowIndex = 1;
			// 需要填充表头数据
			if (lstVo.get(ix).isContainHeaderForm()) {
				int cellIndex = 0;
				List<HeaderFormDataVo> lstHeaderFormDataVo = lstVo.get(ix).getLstHeaderFormDataVo();
				Row headerRow = null;
				for (int t = 0; lstHeaderFormDataVo != null && t < lstHeaderFormDataVo.size(); t++) {
					HeaderFormDataVo headerFormDataVo = lstHeaderFormDataVo.get(t);
					if (t % headerFormDataVo.getRowSize() == 0) {
						headerRow = sheet.createRow((startRowIndex)); // 第二行开始填充数据 
						cellIndex = 0;
						++startRowIndex;
					}
					if (headerRow != null) {
						//设置样式 表头的标题
						Cell cell = headerRow.createCell(cellIndex);
						cell.setCellType(HSSFCell.ENCODING_UTF_16);
						String cellName = lstHeaderFormDataVo.get(t).getName().replaceFirst("\\*", "");
						int columnSize = columnLenMap.get(cellIndex);
						if (columnSize < cellName.length()) {
							columnLenMap.put(cellIndex, cellName.length());
						}
						cell.setCellValue(cellName);
						cell.setCellStyle(headerStyle);
						++cellIndex;
						cell = headerRow.createCell(cellIndex);
						String cellValue = headerFormDataVo.getValue();
						cell.setCellValue(cellValue);
						cell.setCellStyle(contentStyle);
						columnSize = columnLenMap.get(cellIndex);
						if (columnSize < cellValue.length()) {
							columnLenMap.put(cellIndex, cellValue.length());
						}
						++cellIndex;
					}
				}
				createCellRange(startRowIndex, lstVo.get(ix).getColumnsMapList().size() - 1, lstVo.get(ix)
						.getSheetName(), wb, sheet);
				startRowIndex++;
			}
			//设置表头
			Row row1 = sheet.createRow(startRowIndex);
			for (int i = 0; i < lstVo.get(ix).getColumnsMapList().size(); i++) {
				Cell cell1 = row1.createCell(i);
				cell1.setCellType(HSSFCell.ENCODING_UTF_16);
				String title = lstVo.get(ix).getColumnsMapList().get(i).get("title").toString();
				cell1.setCellValue(title);
				cell1.setCellStyle(headerStyle);
				int columnSize = columnLenMap.get(i);
				if (columnSize < title.length()) {
					columnLenMap.put(i, title.length());
				}
			}
			startRowIndex++;
			//填充数据
			for (int j = 0; j < lstVo.get(ix).getDataMapList().size(); j++) {
				Row row2 = sheet.createRow(startRowIndex + j); // 第三行开始填充数据 
				Map cellDataMap = lstVo.get(ix).getDataMapList().get(j);
				for (int i = 0; i < lstVo.get(ix).getColumnsMapList().size(); i++) {
					Cell cell = row2.createCell(i);
					String cellValue = StringUtils.EMPTY;
					if (lstVo.get(ix).getColumnsMapList().get(i).get("field") != null) {
						String fieldString = String.valueOf(lstVo.get(ix).getColumnsMapList().get(i).get("field"));
						cellValue = String.valueOf(cellDataMap.get(fieldString));
					}
					if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
						cellValue = StringUtils.EMPTY;
					}
					Object exportType = lstVo.get(ix).getColumnsMapList().get(i).get("exportType");
					if (exportType != null && "number".equalsIgnoreCase(exportType.toString())) {
						cell.setCellStyle(numberContentStyle);
					} else {
						cell.setCellStyle(contentStyle);
					}
					// 设置单元格的值
					setCellValue(cell, cellValue, lstVo.get(ix).getColumnsMapList().get(i));
					int columnSize = columnLenMap.get(i);
					if (columnSize < cellValue.length()) {
						columnLenMap.put(i, cellValue.length());
					}
				}
			}
			// 判断是否有子数据
			if (lstVo.get(ix).getChildren() != null && lstVo.get(ix).getChildren().size() > 0) {
				if (lstVo.get(ix).getDataMapList().size() > 0) {
					startRowIndex += lstVo.get(ix).getDataMapList().size();
				}
				for (MutliSheetExportVo exportVo : lstVo.get(ix).getChildren()) {
					createCellRange(startRowIndex, exportVo.getColumnsMapList().size() - 1, exportVo.getSheetName(),
							wb, sheet);
					startRowIndex++;
					// 绘制表头
					Row childRow = sheet.createRow(startRowIndex);
					for (int childIndex0 = 0; childIndex0 < exportVo.getColumnsMapList().size(); childIndex0++) {
						Cell childCell = childRow.createCell(childIndex0);
						childCell.setCellType(HSSFCell.ENCODING_UTF_16);
						String childTitle = exportVo.getColumnsMapList().get(childIndex0).get("title").toString();
						childCell.setCellValue(childTitle);
						childCell.setCellStyle(headerStyle);
						int columnSize = columnLenMap.get(childIndex0);
						if (columnSize < childTitle.length()) {
							columnLenMap.put(childIndex0, childTitle.length());
						}
					}
					startRowIndex++;
					// 填充数据
					for (int childIndex1 = 0; childIndex1 < exportVo.getDataMapList().size(); childIndex1++) {
						Row childRow1 = sheet.createRow(startRowIndex); // 第三行开始填充数据 
						Map childCellDataMap = exportVo.getDataMapList().get(childIndex1);
						for (int childIndex2 = 0; childIndex2 < exportVo.getColumnsMapList().size(); childIndex2++) {
							Cell cell = childRow1.createCell(childIndex2);
							String cellValue = StringUtils.EMPTY;
							if (exportVo.getColumnsMapList().get(childIndex2).get("field") != null) {
								String fieldString = String.valueOf(exportVo.getColumnsMapList().get(childIndex2)
										.get("field"));
								cellValue = String.valueOf(childCellDataMap.get(fieldString));
							}
							if (StringUtils.isEmpty(cellValue) || "null".equalsIgnoreCase(cellValue)) {
								cellValue = StringUtils.EMPTY;
							}
							Object exportType = exportVo.getColumnsMapList().get(childIndex2).get("exportType");
							if (exportType != null && "number".equalsIgnoreCase(exportType.toString())) {
								cell.setCellStyle(numberContentStyle);
							} else {
								cell.setCellStyle(contentStyle);
							}
							cell.setCellValue(cellValue);
							// 设置单元格的值
							setCellValue(cell, cellValue, exportVo.getColumnsMapList().get(childIndex2));
							int columnSize = columnLenMap.get(childIndex2);
							if (columnSize < cellValue.length()) {
								columnLenMap.put(childIndex2, cellValue.length());
							}
						}
						startRowIndex++;
					}
				}
			}
			for (int i = 0; i < columnLenMap.size(); i++) {
				int width = columnLenMap.get(i);
				if (width > 200) {
					sheet.setColumnWidth(i, (200 + 5) * 256);
				} else if (width > 0) {
					sheet.setColumnWidth(i, (columnLenMap.get(i) + 5) * 256);
				}
			}
		}
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();

	}

	/**
	 * 获取单元格的类型
	 * @param cell
	 * @param value
	 * @param column
	 * @return
	 */
	private static void setCellValue(Cell cell, String value, Map<String, String> column) {
		if (StringUtils.isEmpty(value) || "null".equalsIgnoreCase(value)) {
			cell.setCellValue(StringUtils.EMPTY);
		} else {
			Object exportType = column.get("exportType");
			if (exportType != null && "number".equalsIgnoreCase(exportType.toString())) {
				cell.setCellValue(Double.parseDouble(value));
			} else if (exportType != null && "text".equalsIgnoreCase(exportType.toString())) {
				cell.setCellValue(value);
			} else {
				if (NumberValidationUtils.isRealNumber(value) && Double.parseDouble(value) - Integer.MAX_VALUE < 0) {
					cell.setCellValue(Double.parseDouble(value));
				} else {
					cell.setCellValue(value);
				}
			}
		}
	}

	/**
	  * 清楚多余的小数点和0
	  * @param str 原始字符串
	  * @return
	  */
	public static String trim(String str) {
		if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
			return trim(str.substring(0, str.length() - 1));
		} else {
			return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
		}
	}

	/**
	 * 获取复合表头样式
	 * @param wb
	 * @return
	 */
	private static CellStyle getFixHeaderStyle(SXSSFWorkbook wb) {
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName("宋体");
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//		headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		//		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(false);
		return headerStyle;
	}

	/**
	 * 获取表头样式
	 * @param wb
	 * @param hasForeground 是否设置背景色
	 * @return
	 */
	private static CellStyle getHeaderStyle(SXSSFWorkbook wb, boolean hasForeground) {
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName("宋体");
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		if (hasForeground) {
			headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
			headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(false);
		return headerStyle;
	}

	/**
	 * 获取内容样式
	 * @param wb
	 * @return
	 */
	private static CellStyle getContentStyle(SXSSFWorkbook wb, Boolean isNumber) {
		Font bodyFont = wb.createFont();
		bodyFont.setFontHeightInPoints((short) 10);
		bodyFont.setFontName("宋体");
		CellStyle bodyStyle = wb.createCellStyle();
		if (isNumber) {
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		} else {
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}
		//		bodyStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		//		bodyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(false);
		return bodyStyle;
	}

	/**
	 * 获取内容样式
	 * @param wb
	 * @return
	 */
	private static CellStyle getContentFloatStyle(SXSSFWorkbook wb) {
		Font bodyFont = wb.createFont();
		bodyFont.setFontHeightInPoints((short) 10);
		bodyFont.setFontName("宋体");
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(false);
		return bodyStyle;
	}

	/**
	 * 获取标题样式
	 * @param wb
	 * @return
	 */
	private static CellStyle getTitleStyle(SXSSFWorkbook wb) {
		//设置样式 -标题
		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return titleStyle;
	}

	/**
	 * 获取最大的列数
	 * @param exportVo
	 * @return
	 */
	private static int getMaxColumnLen(MutliSheetExportVo exportVo) {
		int maxColumnLen = 0;
		if (exportVo.getChildren() != null && exportVo.getChildren().size() > 0) {
			for (MutliSheetExportVo childExportVo : exportVo.getChildren()) {
				if (maxColumnLen < childExportVo.getColumnsMapList().size()) {
					maxColumnLen = childExportVo.getColumnsMapList().size();
				}
			}
		}
		if (maxColumnLen < exportVo.getColumnsMapList().size()) {
			maxColumnLen = exportVo.getColumnsMapList().size();
		}
		return maxColumnLen;
	}

	/**
	 * 初始化map
	 * @param maxColumnLen
	 * @return
	 */
	private static Map<Integer, Integer> initMap(int maxColumnLen) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>(maxColumnLen);
		for (int i = 0; i < maxColumnLen; i++) {
			map.put(i, 0);
		}
		return map;
	}

	/**
	 * 合并列
	 * @param startRowIndex
	 * @param lastCol
	 * @param title
	 * @param wb
	 * @param sheet
	 */
	private static void createCellRange(int startRowIndex, int lastCol, String title, SXSSFWorkbook wb, Sheet sheet) {
		// 新增标题行，且合并
		CellRangeAddress gridRg = new CellRangeAddress(startRowIndex, startRowIndex, 0, lastCol);
		sheet.addMergedRegion(gridRg);
		//设置样式 -标题
		CellStyle gridRgStyle = getTitleStyle(wb);
		Row gridRgRow = sheet.createRow(startRowIndex);
		gridRgRow.setHeightInPoints(20);
		//第一行 提示长
		Cell gridRgCell = gridRgRow.createCell((short) 0);
		gridRgCell.setCellValue(title);
		gridRgCell.setCellStyle(gridRgStyle);
	}
}