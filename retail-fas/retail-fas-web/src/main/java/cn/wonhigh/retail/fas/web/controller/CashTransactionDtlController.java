package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.CashTransactionDtl;
import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.CashTransactionDtlManager;
import cn.wonhigh.retail.fas.manager.SelfShopDepositAccountManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/cash_transaction_dtl")
@ModuleVerify("30170005")
public class CashTransactionDtlController extends ExcelImportController<CashTransactionDtl> {
    @Resource
    private CashTransactionDtlManager cashTransactionDtlManager;
    
    @Resource
    private SelfShopDepositAccountManager selfShopDepositAccountManager;
    
    @Resource
    private ShopManager shopManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/deal_dtl/",cashTransactionDtlManager);
    }

	@Override
	protected String[] getImportProperties() {
		return new String[]{"cardNumber","depositCashTime","depositAmount","shopNo","depositSite"};
	}
	
	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> result = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		int total = cashTransactionDtlManager.findCount(params);
		List<CashTransactionDtl> list = new ArrayList<CashTransactionDtl>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = cashTransactionDtlManager.findByPage(page, sortColumn, sortOrder, params);
		}
		result.put("total", total);
		result.put("rows", this.setMallProperty(list));

		return result;
		
	}
	
	private List<CashTransactionDtl> setMallProperty(List<CashTransactionDtl> cashTransactionDtlList) throws ManagerException {
		Map<String, List<SelfShopDepositAccount>> map = new HashMap<String, List<SelfShopDepositAccount>>();
		for (CashTransactionDtl cashTransactionDtl : cashTransactionDtlList) {
			List<SelfShopDepositAccount> list = null;
			if(map.containsKey(cashTransactionDtl.getCardNumber())){
				list = map.get(cashTransactionDtl.getCardNumber());
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("depositAccount", cashTransactionDtl.getCardNumber());
				list = selfShopDepositAccountManager.findByBiz(null, params);
				map.put(cashTransactionDtl.getCardNumber(), list);
			}
			
			List<String> mallNames = new ArrayList<String>();
			for(SelfShopDepositAccount self:list){
				if(self.getMallName() != null && !mallNames.contains(self.getMallNo())){
					mallNames.add(self.getMallName());
				}
			}
			cashTransactionDtl.setMallName(mallNames.toString());
		}
		return cashTransactionDtlList;
	}

	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	
    	List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), CashTransactionDtl.class, request);
    	StringBuilder errorBuilder = new StringBuilder();
    	if(msgList != null && msgList.size() > 0){
	    	for(UploadMessageModel message : msgList){
	    		errorBuilder.append(message.getMessage() + "<br/>");
	    	}
	    	String errorInfo = " 以下错误行信息导入失败  <br />" + errorBuilder;
	    	mv.addObject("error", errorInfo);
    	}else{
    		mv.addObject("success","成功导入");
    	}
    	return mv;
	}
	
	@Override
	protected boolean checkImportType(){
		return true;
	}

	@Override
	protected boolean doBatchAdd(List<CashTransactionDtl> list) throws ManagerException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean doBatchAdd(List<CashTransactionDtl> list, List<UploadMessageModel> msgList) throws ManagerException {
		try {
			if(msgList == null || msgList.size() <= 0){
				if(list != null && list.size() > 0){
					return cashTransactionDtlManager.uploadListTransactionDtl(list);
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return false;
	}
	
	@Override
	protected UploadMessageModel validateModel(CashTransactionDtl model, int rowIndex) throws ManagerException, IOException {
		UploadMessageModel messageModel = null;
		try {
			messageModel = basicValidation(model,rowIndex);
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		
		if(messageModel != null){
			return messageModel;
		}
		
		return super.validateModel(model, rowIndex);
	}
	
	
	public UploadMessageModel basicValidation(CashTransactionDtl transactionDtl,int rowIndex) throws ManagerException{
		
		UploadMessageModel uploadMessageModel = null;
		StringBuffer sbf = new StringBuffer();
		
		rowIndex = rowIndex + 1;
		String cardNumber = transactionDtl.getCardNumber() != null ? transactionDtl.getCardNumber().trim() : null;
		BigDecimal depositAmount = transactionDtl.getDepositAmount();
		Date depositTime = transactionDtl.getDepositCashTime();
		String shopNo = transactionDtl.getShopNo();
		
		if(cardNumber != null && !"".equals(cardNumber)){
		
			Pattern p = Pattern.compile("[0-9]{16,21}");
	        //进行匹配，并将匹配结果放在Matcher对象中
	        Matcher m = p.matcher(cardNumber);
	        if(!m.matches()){
	        	sbf.append(" 第"+rowIndex+"行 账号不合法").append("<br />");
	        }else{
	        	
	        	boolean flag = checkDepositCardExist(transactionDtl);
	        	if(!flag){
		        	sbf.append(" 第"+rowIndex+"行 存现账号没有在店铺存现账号管理中维护").append("<br />");
	        	}
	        }
		}else{
			sbf.append(" 第"+rowIndex+"行 账号不能为空").append("<br />");
		}
		
		if(depositAmount != null){
			
			if(depositAmount.compareTo(new BigDecimal(0)) <= 0){
//				sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	        	sbf.append(" 第"+rowIndex+"行请正确录入存现金额").append("<br />");
			}
			
		}else{
			sbf.append(" 第"+rowIndex+"行 存现金额不能为空").append("<br />");
		}
		
		if(depositTime == null || "".equals(depositTime)){
			sbf.append(" 第"+rowIndex+"行 存现日期不能为空").append("<br />");
		}else{
			if(!DateUtil.validateDate(DateUtil.formatDate(depositTime))){
				sbf.append(" 第"+rowIndex+"行 存现日期未识别").append("<br />");
			}
		}
		
		if(!StringUtils.isBlank(shopNo)){
			if(selectShopInfo(shopNo).size() <= 0){
				sbf.append(" 第"+rowIndex+"行 店铺未维护!").append("<br />");
			}
		}else{
			sbf.append(" 第"+rowIndex+"行 存现店铺不能为空").append("<br />");
		}
		
		if(!"".equals(sbf.toString())){
			uploadMessageModel = new UploadMessageModel();
			uploadMessageModel.setFlag(false);
			uploadMessageModel.setMessage(sbf.toString());
		}
		return uploadMessageModel;
	}

	//检查导入的存现账号是否已经维护
	public boolean checkDepositCardExist(CashTransactionDtl transactionDtl) throws ManagerException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("depositAccount", transactionDtl.getCardNumber());
		
		int count = selfShopDepositAccountManager.findCount(params);
		
		if(count > 0){
			return true;
		}
		return false;
	}
	
	private List<Shop> selectShopInfo(String shopNo){
		List<Shop> shops = new ArrayList<Shop>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("queryCondition", " and s.shop_no = '" + shopNo + "' ");
			shops = shopManager.selectShopInfoListByShopNos(params);
			return shops;
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return shops;
	}
}