package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.model.SupplierReturnProfit;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BillInvoiceDtlManager;
import cn.wonhigh.retail.fas.manager.SupplierReturnProfitManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;


/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-19 10:35:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/supplier_return_profit")
@ModuleVerify("40001023")
public class SupplierReturnProfitController extends ExcelImportController<SupplierReturnProfit> {
    @Resource
    private SupplierReturnProfitManager supplierReturnProfitManager;
    
    @Resource
    private BillInvoiceDtlManager billInvoiceDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("supplier_return_profit/",supplierReturnProfitManager);
    }
    
    @RequestMapping(value = "/add_return_profit")
	@ResponseBody
    public Map<String, Object> addReturnProfit(HttpServletRequest request) throws ManagerException{
    	Map<String, Object> obj = new HashMap<String, Object>();
    	Map<String, Object> params = this.getQueryParams(request);
    	List<PayRelationship> list = supplierReturnProfitManager.findReturnProfit(params);
    	if(list.size() < 1){
    		obj.put("message","公司与供应商没有商品付款关系或者买卖记录");
    		obj.put("success", false);
    		return obj;
    	}
    	BigDecimal rate = new BigDecimal(request.getParameter("returnProfitRate"));//利率
    	String type = request.getParameter("returnProfitType");//返利基数
    	for(PayRelationship payRelationShip : list){
			//删除已生成的返利
			supplierReturnProfitManager.deleteGenerateReturnProfit(payRelationShip, null);
			rate = rate.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
			//重新生成返利
			supplierReturnProfitManager.addSupplierReturnProfit(payRelationShip, rate, type, 1);
		}
    	
    	obj.put("success", true);
		return obj;
    	
    }

	private Map<String, Object> getQueryParams(HttpServletRequest req) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerNo", req.getParameter("companyNo"));
		params.put("supplier", req.getParameter("supplierNo"));
		params.put("sendDateStart", req.getParameter("sendDateStart"));//公司收货日期
		params.put("sendDateEnd", req.getParameter("sendDateEnd"));
		params.put("itemNo", req.getParameter("itemNo"));
		params.put("brandNo", req.getParameter("brandNo"));
		params.put("returnProfitRate", req.getParameter("returnProfitRate"));//利率
		params.put("returnProfitType", req.getParameter("returnProfitType"));//返利基数
		params.put("remark", req.getParameter("remark"));
		
		return params;
	}
	
	@RequestMapping(value = "/import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request) throws IOException{
		ModelAndView mv = new ModelAndView("/print/import");
		List<UploadMessageModel> msgList = new ArrayList<UploadMessageModel>();
		List<SupplierReturnProfit> list = new ArrayList<SupplierReturnProfit>(20);
		int effectiveCount = 0, totalCount = 0;
		try {
			InputStream inputStream = file.getInputStream();
			//创建Excel工作薄  
			Workbook wb = create(inputStream);  
			//得到第一个工作表  
			Sheet sheet = wb.getSheetAt(0); 
			SupplierReturnProfit model = null;
			int rowCount = sheet.getPhysicalNumberOfRows();
			for(int i = 2; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				model = SupplierReturnProfit.class.newInstance();
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
				if(effectiveCount % 20 == 0 || totalCount == (rowCount - 2)) {
					doAdd(list);
					list = new ArrayList<SupplierReturnProfit>(20);
				}
			}
		} catch(Exception e) {
			UploadMessageModel errorModel = new UploadMessageModel();
			errorModel.setFlag(false);
			errorModel.setMessage("导入失败,请联系管理员 ...");
			msgList.add(errorModel);
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
		StringBuilder errorBuilder = new StringBuilder();
		if (msgList != null && msgList.size() > 0) {
			for (UploadMessageModel message : msgList) {
				errorBuilder.append(message.getMessage() + "<br/>");
			}
			mv.addObject("error", " 以下错误行信息导入失败  <br />"+errorBuilder);
		} else {
			mv.addObject("success", "成功导入");
		}
		return mv;
	}
	
	private void doAdd(List<SupplierReturnProfit> list) throws IOException {
		try {
			initDefaultInfo(list);//店铺信息初始化
			for(SupplierReturnProfit supplierReturnProfit:list){
				supplierReturnProfitManager.deleteGenerateReturnProfit(null, supplierReturnProfit);
				this.add(supplierReturnProfit);
			}
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
	}

	private void initDefaultInfo(List<SupplierReturnProfit> list) throws ManagerException {
		for(SupplierReturnProfit supplierReturnProfit:list){
			supplierReturnProfit.setId(UUIDGenerator.getUUID());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", supplierReturnProfit.getBillNo());
			//根据结算单查询发票信息
			List<BillInvoiceDtl> invoiceList = billInvoiceDtlManager.findByBiz(null, params);
			params = this.getParams(supplierReturnProfit);
			List<PayRelationship> l = supplierReturnProfitManager.findReturnProfit(params);
			PayRelationship payRelationship = l.get(0);
			supplierReturnProfit.setCompanyName(payRelationship.getBuyerName());
			supplierReturnProfit.setSupplierName(payRelationship.getSupplierName());
			supplierReturnProfit.setItemNo(payRelationship.getItemNo());
			supplierReturnProfit.setGenerateDate(new Date(new Date().getTime()));
			if(invoiceList.size()>0){
				BillInvoiceDtl dtl = invoiceList.get(0);
				supplierReturnProfit.setIsInvoiced(0);//已开票
				supplierReturnProfit.setInvoiceNo(dtl.getInvoiceNo());
				supplierReturnProfit.setInvoiceDate(dtl.getInvoiceDate());
			}else{
				supplierReturnProfit.setIsInvoiced(1);//未开票
			}
			
			SystemUser user = CurrentUser.getCurrentUser();
			supplierReturnProfit.setUpdateUser(user.getLoginName());
			supplierReturnProfit.setUpdateTime(new Date(new Date().getTime()));
			supplierReturnProfit.setReturnType(2);//返利生成方式
		}
	}

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
	
	private void setValue(SupplierReturnProfit obj, String propertyName, Object value) throws Exception {
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

	@Override
	protected UploadMessageModel validateModel(SupplierReturnProfit supplierReturnProfit, int rowIndex) throws IOException {
		UploadMessageModel uploadMessageModel = null;
		try {
			if(null != supplierReturnProfit){
				Map<String, Object> params = getParams(supplierReturnProfit);
				List<PayRelationship> list = supplierReturnProfitManager.findReturnProfit(params);//验证到货单是否存在
				if(list.size()<=0){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 未取到到货单或付款关系，生成返利失败!");
					return uploadMessageModel;
				}
			}
			
		} catch (Exception e) {
			uploadMessageModel = getErrorMessageObject(" validation SupplierReturnProfit failed");
			logger.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
		return uploadMessageModel;
	
	}

	private Map<String, Object> getParams(SupplierReturnProfit supplierReturnProfit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerNo", supplierReturnProfit.getCompanyNo());
		params.put("supplierNo", supplierReturnProfit.getSupplierNo());
		params.put("sendDateStart", supplierReturnProfit.getSendDate());//公司发货日期
		params.put("sendDateEnd", supplierReturnProfit.getSendDate());
		params.put("itemCode", supplierReturnProfit.getItemCode());
		params.put("brandNo", supplierReturnProfit.getBrandNo());
		return params;
	}
	
	@Override
	protected String[] getImportProperties() {
		return new String[] { "companyNo","supplierNo","sendDate","billNo","itemCode","brandNo","amount","remark"};
	}

	@Override
	protected boolean doBatchAdd(List<SupplierReturnProfit> list) {
		return false;
	}
}