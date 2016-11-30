package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.OrderPaywayManager;
import cn.wonhigh.retail.fas.manager.SelfShopTerminalAccountManager;
import cn.wonhigh.retail.fas.manager.ShopBrandManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-12 15:53:14
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
@RequestMapping("/self_shop_terminal_account")
@ModuleVerify("30170002")
public class SelfShopTerminalAccountController extends ExcelImportController<SelfShopTerminalAccount> {
	@Resource
	private SelfShopTerminalAccountManager selfShopTerminalAccountManager;
	@Resource
	private ShopManager shopManager;
	@Resource
	private OrderPaywayManager orderPaywayManager;
	@Resource
	private ShopBrandManager shopBrandManager;

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SelfShopTerminalAccount.class);

	@Override
	public CrudInfo init() {
		return new CrudInfo("self_shop_terminal_account/", selfShopTerminalAccountManager);
	}

	/**
	 * 转到店铺终端账号设置页面
	 * @return
	 */
	@RequestMapping(value = "/list_tabMain")
	public ModelAndView areaAmongListTabMain(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("IndepShop_management/shop_info_manage/terminal_list");
		return mav;
	}

	/**
	 * 店铺终端账号查询
	 */
	@Override
	@RequestMapping(value = "/terminalAccountList.json")
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
		String organNo = params.get("organNos1") == null ? null : params.get("organNos1").toString();//根据管理城市查询店铺
		if(StringUtils.isNotEmpty(organNo)){
			params.put("shopNoList", shopBrandManager.queryShopNoByOrganNo(FasUtil.formatInQueryCondition(organNo)));
		}
		
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.selfShopTerminalAccountManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SelfShopTerminalAccount> list = this.selfShopTerminalAccountManager.findByPage(page, orderByField,
				orderBy, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@Override
	protected List<SelfShopTerminalAccount> queryExportData(Map<String, Object> params) throws ManagerException {
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		String organNo = params.get("organNos1") == null ? null : params.get("organNos1").toString();//根据管理城市查询店铺
		if(StringUtils.isNotEmpty(organNo)){
			params.put("shopNoList", shopBrandManager.queryShopNoByOrganNo(FasUtil.formatInQueryCondition(organNo)));
		}
		
		int total = this.selfShopTerminalAccountManager.findCount(params);
		SimplePage page = new SimplePage(1, total, total);
		List<SelfShopTerminalAccount> list = this.selfShopTerminalAccountManager.findByPage(page, null, null, params);
		return list;
	}

	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");

		List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), SelfShopTerminalAccount.class, request);
		StringBuilder errorBuilder = new StringBuilder();
		if (msgList != null && msgList.size() > 0) {
			for (UploadMessageModel message : msgList) {
				errorBuilder.append(message.getMessage() + "<br/>");
			}
			mv.addObject("error", errorBuilder);
		} else {
			mv.addObject("success", "成功导入");
		}
		return mv;
	}
	
	/**
	 * 费率变动修改销售订单的手续费
	 * @throws Exception 
	 */
	@RequestMapping({ "/update_poundage" })
	public ResponseEntity<Map<String, Boolean>> updatePoundage(HttpServletRequest req) throws Exception{
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<SelfShopTerminalAccount>> params = this.convertToMap(req);
		List<SelfShopTerminalAccount> addList = new ArrayList<SelfShopTerminalAccount>();
		List<SelfShopTerminalAccount> deleteList = new ArrayList<SelfShopTerminalAccount>();

		for (Entry<CommonOperatorEnum, List<SelfShopTerminalAccount>> param : params.entrySet()) {
			if(param.getKey().equals(CommonOperatorEnum.INSERTED)){
				addList.addAll(params.get(CommonOperatorEnum.INSERTED));
			}
			if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
				addList.addAll(params.get(CommonOperatorEnum.UPDATED));
			}
			if (param.getKey().equals(CommonOperatorEnum.DELETED)) {
				deleteList.addAll(params.get(CommonOperatorEnum.DELETED));
			}
		}
		if(addList.size()>0){
			orderPaywayManager.updatePoundage(addList);
		}
		if(deleteList.size()>0){
			orderPaywayManager.updatePoundageForDel(deleteList);
		}
		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}

	@Override
	protected UploadMessageModel validateModel(SelfShopTerminalAccount selfShopTerminalAccount, int rowIndex) {
		UploadMessageModel uploadMessageModel = null;
		try {
			if (null != selfShopTerminalAccount) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", selfShopTerminalAccount.getShopNo());
				Pattern p=Pattern.compile("[\\u4e00-\u9fa5]");
				String accout=selfShopTerminalAccount.getCreditCardAccount();
			    Matcher m=p.matcher(accout);
			    if(m.find()){
				      uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 数据错误，终端绑定账号存在中文请检查该行数据！");
					  return uploadMessageModel;
			    }
				Shop shop = shopManager.initSubsiInfo(params);
				if(shop == null){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 店铺不存在！");
					return uploadMessageModel;
				}
				//判断记录是否存在
				params.put("shopNoLists", FasUtil.formatInQueryCondition(selfShopTerminalAccount.getShopNo()));
				params.put("terminalNumber", selfShopTerminalAccount.getTerminalNumber());
				int count = selfShopTerminalAccountManager.findCount(params);
				if(count>1){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 店铺终端账号已存在！");
					return uploadMessageModel;
				}
				if ((selfShopTerminalAccount.getTerminalNumber()!=null&&selfShopTerminalAccount.getTerminalNumber().length() > 25)
						|| (selfShopTerminalAccount.getCreditCardAccount()!=null&&selfShopTerminalAccount.getCreditCardAccount().length() > 25)
						|| (selfShopTerminalAccount.getMerchantsNo()!=null&&selfShopTerminalAccount.getMerchantsNo().length() > 18)) {
					uploadMessageModel = getErrorMessageObject("第" + (rowIndex + 1) + "行 数据错误，请检查该行数据！");
					return uploadMessageModel;
				}
				return uploadMessageModel;
			} else {
				return super.validateModel(selfShopTerminalAccount, rowIndex);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error(" validate the model values : " + selfShopTerminalAccount.toString() + " , index : "
					+ (rowIndex + 1));
			uploadMessageModel = getErrorMessageObject(" validation SelfShopTerminalAccount failed , "
					+ selfShopTerminalAccount.toString());
			return uploadMessageModel;
		}
	}

	public UploadMessageModel getErrorMessageObject(String message) {
		UploadMessageModel uploadMessageModel = new UploadMessageModel();
		uploadMessageModel.setFlag(false);
		uploadMessageModel.setMessage(message);
		return uploadMessageModel;
	}

	@RequestMapping({ "/check_terminal_account" })
	public ResponseEntity<Map<String, String>> checkTerminalAccount(HttpServletRequest req) throws Exception {
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");

		Map<String, String> respInfo = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			if (StringUtils.isNotEmpty(upadtedList)) {
				List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {
				});
				for (Map map : list) {
					SelfShopTerminalAccount selfShopTerminalAccount = mapper.readValue(mapper.writeValueAsString(map),
							SelfShopTerminalAccount.class);
					setDefaulValues(selfShopTerminalAccount, req);
					Map<String, String> _errorInfo = selfShopTerminalAccountManager
							.validationTerminalAccount(selfShopTerminalAccount);
					if (_errorInfo.get("error") != null) {
						respInfo.put("error", _errorInfo.get("error") + "<br />");
						continue;
					}
				}

			}
			if (StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
				});
				for (Map map : list) {
					SelfShopTerminalAccount selfShopTerminalAccount = mapper.readValue(mapper.writeValueAsString(map),
							SelfShopTerminalAccount.class);
					setDefaulValues(selfShopTerminalAccount, req);
					Map<String, String> _errorInfo = selfShopTerminalAccountManager
							.validationTerminalAccount(selfShopTerminalAccount);
					if (_errorInfo.get("error") != null) {
						respInfo.put("error", _errorInfo.get("error") + "<br />");
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
		return new String[] { "shopNo", "merchantsNo", "terminalNumber", "creditCardBank", "creditCardAccount",
				"creditCardType", "creditCardRate" };
	}

	@Override
	protected boolean doBatchAdd(List<SelfShopTerminalAccount> list) throws ManagerException {
		try {
			if (list == null || list.size() < 1) {
				return false;
			}
			initDefaultInfo(list);

			selfShopTerminalAccountManager.addListShopTerminalAccount(list);//店铺信息初始化
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
	}

	private void initDefaultInfo(List<SelfShopTerminalAccount> list) {
		for (SelfShopTerminalAccount selfShopTerminalAccount : list) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", selfShopTerminalAccount.getShopNo());
			Shop shop = shopManager.initSubsiInfo(params);

			selfShopTerminalAccount.setShopName(shop.getShortName());
			selfShopTerminalAccount.setCompanyNo(shop.getCompanyNo());
			selfShopTerminalAccount.setCompanyName(shop.getCompanyName());
			selfShopTerminalAccount.setMallName(shop.getMallName());
			selfShopTerminalAccount.setMallNo(shop.getMallNo());
		}

	}
}