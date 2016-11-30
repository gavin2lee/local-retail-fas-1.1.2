package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.SelfShopDepositAccountManager;
import cn.wonhigh.retail.fas.manager.ShopBrandManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-11 14:55:07
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
@RequestMapping("/self_shop_deposit_account")
@ModuleVerify("30170002")
public class SelfShopDepositAccountController extends ExcelImportController<SelfShopDepositAccount> {
	@Resource
	private SelfShopDepositAccountManager selfShopDepositAccountManager;
	@Resource
	private ShopManager shopManager;
	@Resource
	private ShopBrandManager shopBrandManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SelfShopDepositAccount.class);

	@Override
	public CrudInfo init() {
		return new CrudInfo("self_shop_deposit_account/", selfShopDepositAccountManager);
	}

	/**
	 * 店铺存现账号查询
	 */
	@Override
	@RequestMapping(value = "/depositAccountList.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String orderByField = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String orderBy = StringUtils.isEmpty(req.getParameter("order")) ? "" : String
				.valueOf(req.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		String organNo = params.get("organNos") == null ? null : params.get("organNos").toString();//根据管理城市查询店铺
		if(StringUtils.isNotEmpty(organNo)){
			params.put("shopNoList", shopBrandManager.queryShopNoByOrganNo(FasUtil.formatInQueryCondition(organNo)));
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.selfShopDepositAccountManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SelfShopDepositAccount> list = this.selfShopDepositAccountManager.findByPage(page, orderByField, orderBy,
				params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@Override
	protected List<SelfShopDepositAccount> queryExportData(Map<String, Object> params) throws ManagerException {
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		String organNo = params.get("organNos") == null ? null : params.get("organNos").toString();//根据管理城市查询店铺
		if(StringUtils.isNotEmpty(organNo)){
			params.put("shopNoList", shopBrandManager.queryShopNoByOrganNo(FasUtil.formatInQueryCondition(organNo)));
		}
		
		int total = this.selfShopDepositAccountManager.findCount(params);
		SimplePage page = new SimplePage(1, total, total);
		List<SelfShopDepositAccount> list = this.selfShopDepositAccountManager.findByPage(page, null, null, params);
		return list;
	}

	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");

		List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), SelfShopDepositAccount.class, request);
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
	
	@Override
	protected UploadMessageModel validateModel(SelfShopDepositAccount selfShopDepositAccount, int rowIndex) {
		UploadMessageModel uploadMessageModel = null;
		try {
			if(null!=selfShopDepositAccount){
				//判断店铺是否存在
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", selfShopDepositAccount.getShopNo());
				Shop shop = shopManager.initSubsiInfo(params);
				if(shop == null){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 店铺不存在！");
					return uploadMessageModel;
				}
				//判断记录是否存在
				params.put("depositAccount", selfShopDepositAccount.getDepositAccount());
				params.put("shopNoLists", FasUtil.formatInQueryCondition(selfShopDepositAccount.getShopNo()));
				int count = selfShopDepositAccountManager.findCount(params);
				if(count>0){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 "+selfShopDepositAccount.getShopNo()+"店铺存现账号已存在！");
					return uploadMessageModel;
				}
				if(selfShopDepositAccount.getDepositAccount()!=null&&selfShopDepositAccount.getDepositAccount().length()>25){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 存现账号非法！");
					return uploadMessageModel;
				}
				return uploadMessageModel;
			}else{
				return super.validateModel(selfShopDepositAccount, rowIndex);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			LOGGER.error(" validate the model values : "+selfShopDepositAccount.toString() + " , index : "+(rowIndex+1));
			uploadMessageModel = getErrorMessageObject(" validation SelfShopDepositAccount failed , "+selfShopDepositAccount.toString());
			return uploadMessageModel;
		}
	}
	
	public UploadMessageModel getErrorMessageObject(String message){
		UploadMessageModel uploadMessageModel = new UploadMessageModel();
		uploadMessageModel.setFlag(false);
		uploadMessageModel.setMessage(message);
		return uploadMessageModel;
	}

	@RequestMapping({ "/check_deposit_account" })
	public ResponseEntity<Map<String, String>> checkDepositAccount(HttpServletRequest req) throws Exception{
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		
		Map<String,String> respInfo = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			if(StringUtils.isNotEmpty(upadtedList)) {
				List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					SelfShopDepositAccount selfShopDepositAccount = mapper.readValue(mapper.writeValueAsString(map), SelfShopDepositAccount.class);
					setDefaulValues(selfShopDepositAccount,req);
					Map<String,String> _errorInfo = selfShopDepositAccountManager.validationDepositAccount(selfShopDepositAccount);
					if(_errorInfo.get("error") != null){
						respInfo.put("error", _errorInfo.get("error") + "<br />" );
						continue;
					}
				}
				
			}
			if(StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					SelfShopDepositAccount selfShopDepositAccount = mapper.readValue(mapper.writeValueAsString(map), SelfShopDepositAccount.class);
					setDefaulValues(selfShopDepositAccount,req);
					Map<String,String> _errorInfo = selfShopDepositAccountManager.validationDepositAccount(selfShopDepositAccount);
					if(_errorInfo.get("error") != null){
						respInfo.put("error", _errorInfo.get("error") + "<br />" );
						continue;
					}
				}
				
			}
		} catch (Exception e) {
			
		}
		
		return new ResponseEntity<Map<String, String>>(respInfo, HttpStatus.OK);
	}


	@Override
	protected String[] getImportProperties() {
		return new String[] { "shopNo","depositAccount","bank"};
	}

	@Override
	protected boolean doBatchAdd(List<SelfShopDepositAccount> list) throws ManagerException {
		try {
			if (list == null || list.size() < 1) {
				return false;
			}
			initDefaultInfo(list);//店铺信息初始化

			selfShopDepositAccountManager.addListShopDepositAccount(list);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
	}

	private void initDefaultInfo(List<SelfShopDepositAccount> list) {
		for (SelfShopDepositAccount selfShopDepositAccount : list) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", selfShopDepositAccount.getShopNo());
			Shop shop = shopManager.initSubsiInfo(params);

			selfShopDepositAccount.setShopName(shop.getShortName());
			selfShopDepositAccount.setCompanyNo(shop.getCompanyNo());
			selfShopDepositAccount.setCompanyName(shop.getCompanyName());
			selfShopDepositAccount.setMallName(shop.getMallName());
			selfShopDepositAccount.setMallNo(shop.getMallNo());
		}

	}

}