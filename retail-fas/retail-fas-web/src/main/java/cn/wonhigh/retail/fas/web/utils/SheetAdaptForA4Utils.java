package cn.wonhigh.retail.fas.web.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;

/** 
* @author 王仕秒
* @version 创建时间：2016-3-31 下午2:25:31 
* 注意：使用本类时，创建new Workbook(int rowAccessWindowSize)工作簿对象 时参数设置为-1
*/
public class SheetAdaptForA4Utils {
	public final static int A4_WIDTH = 33862;//A4纸张长度
	public final static int A4_HEIGHT = 9800;//A4纸张高度
	
	/**
	 * sheet适应A4纸张大小(297*210)
	 * @param sheet
	 * @return
	 * @author wang.sm
	 * 注意：使用本方法时，创建工作workbook 时参数rowAccessWindowSize设置为-1
	 */
	public static void adaptForA4(Sheet sheet){
		/**打印设置*/
		XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();//打印设置
		printSetup.setLandscape(true);//横向打印
		printSetup.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		//调整第一行内容宽度适应A4纸长度
		Row row = sheet.getRow(0);
		int widths = sheetOperateWidth(sheet,row,0,0);//获取总宽度，标志0
		sheet.setFitToPage(true);//页面缩放选择第二种方式
		printSetup.setFitWidth((short) 1);
		printSetup.setFitHeight((short) 1);
	}
	
	private static int sheetOperateWidth(Sheet sheet,Row row, int flag,int widths) {
		int width = 0;
		for(Cell c:row){
			boolean isMerge = isMergedRegion(sheet, 0, c.getColumnIndex());
			if(isMerge){
				width = width + operateMergedRegionWidth(sheet, row.getRowNum(), c.getColumnIndex(),flag,widths);
			}else{
				if(flag==0){
					width = width + sheet.getColumnWidth(c.getColumnIndex());
				}else if(flag == 1){
					int w = sheet.getColumnWidth(c.getColumnIndex())*A4_WIDTH;
					sheet.setColumnWidth(c.getColumnIndex(), w/widths);
				}
			}
		}
		return width;
	}

	private static int operateMergedRegionWidth(Sheet sheet ,int row , int column, int flag, int widths){    
	    int sheetMergeCount = sheet.getNumMergedRegions();    
	    int width = 0;
	    for(int i = 0 ; i < sheetMergeCount ; i++){
	        CellRangeAddress ca = sheet.getMergedRegion(i);    
	        int firstColumn = ca.getFirstColumn();    
	        int lastColumn = ca.getLastColumn();    
	        int firstRow = ca.getFirstRow();    
	        int lastRow = ca.getLastRow();    
	            
	        if(row >= firstRow && row <= lastRow){
	            if(column >= firstColumn && column <= lastColumn){
	            	for (int j = firstColumn; j <= lastColumn; j++) {
						if(flag==0){
							width = width + sheet.getColumnWidth(j);
						}else if(flag==1){
							int w = sheet.getColumnWidth(j)*A4_WIDTH;
							sheet.setColumnWidth(j, w/widths);
						}
					}
	            }    
	        }    
	    }    
	        
	    return width ;    
	}  
	
	private static int sheetOperateHeight(Sheet sheet, int heights, int flag){
		int startRow = sheet.getFirstRowNum();//逻辑首行
		int endRow = sheet.getLastRowNum();//逻辑尾行
		int height = 0;
		for (int i = startRow; i <= endRow; i++) {
			Row row = sheet.getRow(startRow);
			if(flag==0){
				height = height + row.getHeight();
			}else if(flag==1){
				int h = row.getHeight();
				row.setHeight((short) (h*A4_HEIGHT/heights));
			}
		}
		return height;
	}
	
	private static boolean isMergedRegion(Sheet sheet, int row, int column) {
			int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
}
