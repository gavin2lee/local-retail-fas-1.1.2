package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.CreditCardTransactionDtl;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.manager.CreditCardTransactionDtlManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 11:40:01
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
@RequestMapping("/self_shop_credit_card_deal_dtl")
@ModuleVerify("30170006")
public class CreditCardTransactionDtlController extends ExcelImportController<CreditCardTransactionDtl> {
    @Resource
    private CreditCardTransactionDtlManager creditCardTransactionDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/deal_dtl/",creditCardTransactionDtlManager);
    }

	@Override
	protected String[] getImportProperties() {
		return new String[]{"terminalNumber", "cardNumber", "dealTime", "seqNo","remark","amount","rebateAmount","givenBank","realityDealTime"};
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	
    	List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), CreditCardTransactionDtl.class, request);
    	StringBuilder errorBuilder = new StringBuilder();
    	if(msgList != null && msgList.size() > 0){
	    	for(UploadMessageModel message : msgList){
	    		errorBuilder.append(message.getMessage() + "<br/>");
	    	}
	    	mv.addObject("error", errorBuilder);
    	}else{
    		mv.addObject("success","成功导入");
    	}
    	return mv;
	}
	

	@Override
	protected boolean doBatchAdd(List<CreditCardTransactionDtl> list) throws ManagerException {
		try {
			
			if(list != null && list.size() > 0){
				for(CreditCardTransactionDtl dtl : list){
					dtl.setActualIncomeAmount(dtl.getAmount().subtract(dtl.getRebateAmount()));
				}
			}else{
				return false;
			}
			return creditCardTransactionDtlManager.uploadListTransactionDtl(list);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@RequestMapping("/creditCard_init")
	public String initializtionCreditCardCensusPage(){
		return "IndepShop_management/creditCard_census/list";
	}
	
	@RequestMapping("/creditCard_census")
	@ResponseBody
	public Map<String,Object> creditCardCensus(HttpServletRequest req, Model model) throws ManagerException{
		
		Map<String, Object> params = builderParams(req, model);
		
		try {
			return creditCardTransactionDtlManager.getCreditCardCensus(params);
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	protected List<CreditCardTransactionDtl> queryExportData(Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		List<CreditCardTransactionDtl> creditCardTransactionDtlList = null;
		
		try {
			SimplePage page = new SimplePage(1, Integer.MAX_VALUE, 0);
			creditCardTransactionDtlList = this.creditCardTransactionDtlManager.findByPage(page, "", "", params);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return creditCardTransactionDtlList;
	}
	
	@Override
	protected UploadMessageModel validateModel(CreditCardTransactionDtl model, int rowIndex) throws ManagerException {
		// TODO Auto-generated method stub
		
		UploadMessageModel messageModel = basicValidation(model,rowIndex);
		
		rowIndex = rowIndex + 1;
		if(messageModel == null){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("seqNo", model.getSeqNo() != null ? model.getSeqNo().trim() : model.getSeqNo());
			
			try {
				int count = creditCardTransactionDtlManager.findCount(params);
				if(count > 0){
					messageModel = new UploadMessageModel();
					messageModel.setFlag(false);
					messageModel.setMessage(" 第"+rowIndex+"行 当前行的流水号已存在,不能重复");
				}
			} catch (ManagerException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
		}
		return messageModel;
	}
	
	
	public UploadMessageModel basicValidation(CreditCardTransactionDtl model,int rowIndex){
		
		UploadMessageModel uploadMessageModel = null;
		StringBuffer sbf = new StringBuffer();

		String terminalNo = model.getTerminalNumber() != null ? model.getTerminalNumber().trim() : null;
		String cardNumber = model.getCardNumber() != null ? model.getCardNumber().trim() : null;
		Date dealTime = model.getDealTime();
		Date actualDealTime = model.getRealityDealTime();
		BigDecimal amount = model.getAmount();
		String givenBank = model.getGivenBank();
		String seqNo = model.getSeqNo();
		BigDecimal rebateAmount = model.getRebateAmount();
		
		if(terminalNo != null && !"".equals(terminalNo)){
			int terLength = terminalNo.trim().length();
			
			if(terLength > 21 || terLength < 5){
				sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sbf.append(" 第"+rowIndex+"行 终端号长度不对,建议(5-21位)").append("<br />");
			}
			
		}else{
			sbf.append(" 第 "+rowIndex+"行 终端号不能为空").append("<br />");
		}
		
		if(cardNumber != null && !"".equals(cardNumber)){
			
			Pattern p = Pattern.compile("[0-9]{12,21}");
	        //进行匹配，并将匹配结果放在Matcher对象中
	        Matcher m = p.matcher(cardNumber);
	        if(!m.matches()){
	        	sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	        	sbf.append(" 第"+rowIndex+"行 账号不合法").append("<br />");
	        }
		}else{
			sbf.append(" 第"+rowIndex+"行 账号不能为空").append("<br />");
		}
		
		if(actualDealTime != null && dealTime != null){
			
			if(actualDealTime.getTime() < dealTime.getTime()){
				sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sbf.append(" 第"+rowIndex+"行 请正确录入实际交易时间").append("<br />");
			}
			
		}else{
			if(actualDealTime == null){
				sbf.append(" 第 "+rowIndex+"行 实际交易时间不能为空").append("<br />");
			}
			
			if(dealTime == null){
				sbf.append(" 第 "+rowIndex+"行 交易时间不能为空").append("<br />");
			}
		}
		
		if(amount == null){
			sbf.append(" 第"+rowIndex+"行 金额列不能为空").append("<br />");
		}
		
		if(rebateAmount == null){
			//initialization default value
			model.setRebateAmount(new BigDecimal(0));
		}
		
		if(givenBank != null && !"".equals(givenBank)){
			
			int lengthStr = givenBank.length();
			if(lengthStr > 30){
				sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sbf.append(" 第"+rowIndex+"行 请正确录入发卡行").append("<br />");
			}
			
		}else{
			sbf.append(" 第"+rowIndex+"行 发卡行不能为空").append("<br />");
		}
		
		if(seqNo != null && !"".equals(seqNo)){
			
			int lengthOfseqNo = seqNo.length();
			if(lengthOfseqNo >= 23){
				sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sbf.append(" 第"+rowIndex+"行 流水号过长,最长23位").append("<br />");
			}
			
		}else{
			sbf.append(" 第"+rowIndex+"行 流水号不能为空").append("<br />");
		}
		
		
		if(!"".equals(sbf.toString())){
			uploadMessageModel = new UploadMessageModel();
			uploadMessageModel.setFlag(false);
			uploadMessageModel.setMessage(sbf.toString());
		}
		
		return uploadMessageModel;
	}
	
}