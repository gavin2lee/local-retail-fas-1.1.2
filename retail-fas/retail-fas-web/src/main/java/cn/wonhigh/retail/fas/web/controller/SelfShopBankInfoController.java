package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.SelfShopBankInfo;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.manager.SelfShopBankInfoManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-10 11:20:13
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * R
 */
@Controller
@RequestMapping("/self_shop_bank_info")
@ModuleVerify("30170002")
public class SelfShopBankInfoController extends ExcelImportController<SelfShopBankInfo> {
    @Resource
    private SelfShopBankInfoManager selfShopBankInfoManager;
    
    @Resource
    private ShopManager shopManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/shop_info_manage/",selfShopBankInfoManager);
    }

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return new String[]{
				"shopNo","shopAccount","depositAccount","terminalNumber",
				"creditCardBank","creditCardAccount"};
	}
	
//	@RequestMapping("/upload")
//	@ResponseBody
//	public List<UploadMessageModel> upload(@RequestParam("fileData") MultipartFile file,HttpServletRequest request) {
//		List<UploadMessageModel> msgList = null;
//		try{
//			
//			msgList = this.doUpload(file.getInputStream(), SelfShopBankInfo.class, request);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return msgList;
//	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	
    	List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), SelfShopBankInfo.class, request);
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
	public Map<String, Boolean> insert(SelfShopBankInfo model, HttpServletRequest request) throws ManagerException {
		// TODO Auto-generated method stub
		
		try{
			if(model != null){
				String shopNo = model.getShopNo();
				if(shopNo != null && !"".equals(shopNo)){
					//get the company info by shopNo
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("shopNo", shopNo.trim());
					List<Shop> shopList = shopManager.findByBiz(new Shop(), params);
					if(shopList != null && shopList.size() > 0){
						model.setCompanyNo(shopList.get(0).getCompanyNo());
						model.setCompanyName(shopList.get(0).getCompanyName());
						model.setMallNo(shopList.get(0).getMallNo());
						model.setMallName(shopList.get(0).getMallName());
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);		
		}
		
		return super.insert(model, request);
	}
	
	// when do the import operation , batch add 
	@Override
	protected boolean doBatchAdd(List<SelfShopBankInfo> list) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			if(list == null || list.size() < 1){
				return false;
			}
			initDefaultInfo(list);
			
			selfShopBankInfoManager.addListShopBankInfo(list);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
	}
	
	public void initDefaultInfo(List<SelfShopBankInfo> list){
		
		for(SelfShopBankInfo shopBankInfo : list){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("shopNo", shopBankInfo.getShopNo());
			Shop shop = shopManager.initSubsiInfo(params);
			
			shopBankInfo.setShopName(shop.getShortName());
			shopBankInfo.setCompanyNo(shop.getCompanyNo());
			shopBankInfo.setCompanyName(shop.getCompanyName());
			shopBankInfo.setMallName(shop.getMallName());
			shopBankInfo.setMallNo(shop.getMallNo());
		}
	}
	
	@RequestMapping("/validateShopUnique")
	@ResponseBody
	public ResponseEntity<Map<String,String>> validateShopUnique(@RequestParam("shopNo") String shopNo) throws ManagerException{
		
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,String> flag = new HashMap<String,String>();
		
		params.put("shopNo", shopNo);
		Integer count = null;
		try {
			count = selfShopBankInfoManager.findCount(params);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		
		if(count > 0){
			flag.put("success", "当前操作的店铺已经维护");
		}else{
			flag.put("success", null);
		}
		
		return new ResponseEntity<Map<String,String>>(flag,HttpStatus.OK);
	}
	
	
	
	@Override
	protected UploadMessageModel validateModel(SelfShopBankInfo bankInfo,int rowIndex) throws ManagerException {
		// TODO Auto-generated method stub
		
		//basic validation
		rowIndex = rowIndex + 1;
		UploadMessageModel messageModel = basicValidation(bankInfo,rowIndex);
		
		if(messageModel == null){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("shopNo", bankInfo.getShopNo());
			int count = 0;
			try {
				/**
				 * validation the shop exist or not in the shop table
				 */
				count = shopManager.findCount(params);
				if(count < 1){
					messageModel = new UploadMessageModel();
					messageModel.setFlag(false);
					messageModel.setMessage("第"+rowIndex+"行 当前操作的店铺无效,请录入正确的店铺编码");
					return messageModel;
				}
				
				/**
				 * validation the shop exist or not in the self_shop_bank_info table
				 */
				count = selfShopBankInfoManager.findCount(params);
			} catch (ManagerException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
			
			if(count > 0){
				messageModel = new UploadMessageModel();
				messageModel.setFlag(false);
				messageModel.setMessage(" 第"+rowIndex+"行 当前操作的店铺已经维护");
			}
		}
		return messageModel;
	}
	
	public UploadMessageModel basicValidation(SelfShopBankInfo bankInfo,int rowIndex){
		UploadMessageModel model = null;
		StringBuffer sbf = new StringBuffer();
		
		String creditCardAccount = bankInfo.getCreditCardAccount() != null ? bankInfo.getCreditCardAccount().trim() : null;
		String shopAccount = bankInfo.getShopAccount() != null ? bankInfo.getShopAccount().trim() : bankInfo.getShopAccount();
		String depositAccount = bankInfo.getDepositAccount() != null ? bankInfo.getDepositAccount() : bankInfo.getDepositAccount();
		String terminalNo = bankInfo.getTerminalNumber() != null ? bankInfo.getTerminalNumber() : bankInfo.getTerminalNumber();
		
		if(creditCardAccount == null || "".equals(creditCardAccount)){
			sbf.append(" 第"+rowIndex+"行 刷卡账号不能为空").append("<br />");
		}
		
		if(shopAccount == null || "".equals(shopAccount)){
			sbf.append(" 第"+rowIndex+"行 店铺账号不能为空").append("<br />");
		}
		
		if(depositAccount == null || "".equals(depositAccount)){
			sbf.append(" 第"+rowIndex+"行 存现账号不能为空").append("<br />");
		}
		
		if(terminalNo == null || "".equals(terminalNo)){
			sbf.append(" 第 "+rowIndex+"行 终端号不能为空").append("<br />");
		}
		
		if(!"".equals(sbf.toString())){
			model = new UploadMessageModel();
			model.setMessage(sbf.toString());
			model.setFlag(false);
		}
		return model;
	}
	
}