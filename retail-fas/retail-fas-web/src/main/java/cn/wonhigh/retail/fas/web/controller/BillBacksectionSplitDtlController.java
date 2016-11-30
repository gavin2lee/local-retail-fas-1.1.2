package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.ShopMallEnums;
import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BillBacksectionSplitDtlManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
@RequestMapping("/bill_backsection_split_dtl")
public class BillBacksectionSplitDtlController extends BaseController<BillBacksectionSplitDtl> {
    @Resource
    private BillBacksectionSplitDtlManager billBacksectionSplitDtlManager;
    
	@Resource
	private ShopManager shopManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_backsection_split_dtl/",billBacksectionSplitDtlManager);
    }
    
    @RequestMapping("/bill_backsection_split")
	public String bill_backsection_split() {
		return "mallshop_balance/bill_backsection_split";
	}
    
    @Override
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
    	int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 20 : Integer.parseInt(req.getParameter("rows"));
    	String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
    	String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		
    	Map<String, Object> params = builderParams(req, model);
    	//格式化参数
		//params = this.formatParams(params);
		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<BillBacksectionSplitDtl> list = new ArrayList<BillBacksectionSplitDtl>();
		List<BillBacksectionSplitDtl> listSum = new ArrayList<BillBacksectionSplitDtl>();
		total = billBacksectionSplitDtlManager.findCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = billBacksectionSplitDtlManager.findByPage(page,sortColumn, sortOrder, params);
			BillBacksectionSplitDtl totalSum = billBacksectionSplitDtlManager.selectTotalSum(params);
			totalSum.setCompanyName("合计：");
			listSum.add(totalSum);
		}
		
		map.put("total", total);
		map.put("rows", list);
		map.put("footer", listSum);
		return map;
	}
    
    protected List<BillBacksectionSplitDtl> queryExportData(Map<String, Object> params) throws ManagerException {
    	int total = billBacksectionSplitDtlManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		List<BillBacksectionSplitDtl> list = billBacksectionSplitDtlManager.findByPage(page,orderByField, orderBy, params);
		BillBacksectionSplitDtl totalSum = billBacksectionSplitDtlManager.selectTotalSum(params);
		totalSum.setCompanyName("合计：");
		list.add(totalSum);
		return list;
    }
    
    /**
     * 批量生成结算单
	 * @throws Exception 
     */
	@RequestMapping(value = "/batchSaves")
	@ResponseBody
	public Integer batchSaves(HttpServletRequest req,BillBacksectionSplitDtl billBacksectionSplitDtl)throws ManagerException, Exception {
		int n = 0;
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户

		BillShopBalance balance = new BillShopBalance();
		balance.setCreateUser(loginUser.getUsername());
		balance.setOrganNo(req.getParameter("organNo"));
		balance.setMallNo(req.getParameter("mallNo"));
		balance.setBsgroupsNo(req.getParameter("bsgroupsNo"));	
		balance.setShopNo(req.getParameter("shopNo"));
		balance.setMonth(req.getParameter("month"));
		balance.setBalanceNo(req.getParameter("balanceNo"));		
		n = billBacksectionSplitDtlManager.addBacksectionSplitDtlByBatch(balance);
		return n;
	}
	
	
	@RequestMapping(value = "/shopblanacelist.json")
	@ResponseBody
	public Map<String, Object> selectShopBlanaceAccount(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = billBacksectionSplitDtlManager.selectShopBlanaceAccountCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBacksectionSplitDtl> list = billBacksectionSplitDtlManager.selectShopBlanaceAccountByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 批量查询出新增明细数据
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/add_insert_dtl.json")
	@ResponseBody
	public  Map<String, Object> addInsertDtl(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		int count = billBacksectionSplitDtlManager.selectAddInsertDtl(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("count", count);
		return obj;
	}
	
	/**
	 * 查询出新增明细数据
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/select_add_dtl.json")
	@ResponseBody
	public  Map<String, Object> selectAddDtl(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		List<BillBacksectionSplitDtl> list = billBacksectionSplitDtlManager.selectAddDtl(params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("rows", list);
		return obj;
	}
	
	/**
   	 * 批量保存
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value = "/batchAdd")
   	@ResponseBody
   	public int batchAdd(@ModelAttribute("billBacksectionSplitDtl")BillBacksectionSplitDtl billBacksectionSplitDtl, HttpServletRequest request) 
   			throws Exception{
//   		var shopBalanceObj = companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";"+balanceType+";
//   		"+organNo+";"+bsgroupsNo+";"+mallNo+";"+payType+";";
		
//   		String[] shopBalances = strshopBalanceObjs.split(";");
//   		BillShopBalance billShopBalance = new BillShopBalance();
   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
   		
//   		String companyNo = shopBalances[0];
//		String organNo = shopBalances[6];
//		String bsgroupsNo = null;
//		 if(shopBalances[7] != null && shopBalances[7].length() > 0) {
//			 bsgroupsNo= shopBalances[7]; 
//		 }
//		String mallNo = null;
//		 if(shopBalances[8] != null && shopBalances[8].length() > 0) {
//			 mallNo= shopBalances[8]; 
//		 }
//		 String payType =null;
//		 if(shopBalances[9] != null && shopBalances[9].length() > 0) {
//			 payType= shopBalances[9]; 
//		 }
		 
//		 根据管理城市获取经营城市
//		 Map<String, Object> organParams = new HashMap<String, Object>();
//		 organParams.put("parentNo", organNo);
//		 organParams.put("organLevel", 2);
//		 List<Organ> organList=  organManager.findByBiz(null, organParams);
//		 if(organList != null && organList.size() > 0) {
//				for(Organ organData : organList) {
//					shop.setOrganNo(organData.getOrganNo());  //一个管理城市对于多个经营城市
//				}
//		 }
   		
   		BillShopBalance balance = new BillShopBalance();
		balance.setCreateUser(loginUser.getUsername());
		balance.setCompanyNo(request.getParameter("companyNo_batchAdd"));
		balance.setCompanyName(request.getParameter("companyName_batchAdd"));
		balance.setOrganNo(request.getParameter("organNo_batchAdd"));
		balance.setMallNo(request.getParameter("mallNo_batchAdd"));
		balance.setBsgroupsNo(request.getParameter("bsgroupsNo_batchAdd"));	
		balance.setShopNo(request.getParameter("shopNo_batchAdd"));
		balance.setMonth(request.getParameter("month_batchAdd"));
//		balance.setBalanceNo(request.getParameter("balanceNo"));
		balance.setCreateTime(DateUtil.getCurrentDateTime());
		
		Map<String, Object> params = new HashMap<String, Object>();
		 params.put("companyNo", request.getParameter("companyNo_batchAdd"));
//		 params.put("parentOrganNo", request.getParameter("bsgroupsNo"));//sql特殊处理   根据管理城市来获取经营城市
//		 params.put("bsgroupsNo", request.getParameter("bsgroupsNo"));
//		 params.put("mallNo", request.getParameter("mallNo"));
//		 params.put("payType", payType);
//		 String[] shopnos = billShopBalance.getShopNo().split(",");//{"CA01BS","CA02BS","CA01BL"};
		if(billBacksectionSplitDtl.getShopNo() != null && !"".equals(billBacksectionSplitDtl.getShopNo())){
			params.put("shopNos", Arrays.asList(billBacksectionSplitDtl.getShopNo().split(",")));
		}
		 params.put("month",request.getParameter("month_batchAdd"));
		 BillBacksectionSplitDtl bill = null;
		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
		 /**
		 List<Shop> shopList= shopManager.findByBiz(null, params);
		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
		 BillBacksectionSplitDtl bill = null;
		 if(shopList != null && shopList.size() > 0) {
			 for(Shop shop : shopList) {
				 bill = new BillBacksectionSplitDtl();
				 bill.setCompanyNo(billBacksectionSplitDtl.getCompanyNo());
				 bill.setCompanyName(billBacksectionSplitDtl.getCompanyName());
				 bill.setMonth(billBacksectionSplitDtl.getMonth());
				 bill.setShopNo(shop.getShopNo());
				 bill.setShortName(shop.getShortName());
				 
				 bill.setId(null);
				 bill.setCreateUser(loginUser.getUsername());
				 bill.setCreateTime(DateUtil.getCurrentDateTime());
				 bill.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
				 bill.setStatus(ShopMallEnums.BalanceStatus.NO_VERIFY.getRequestId());
				 billBacksectionSplitDtlList.add(bill);
			 }
		 }
		 */
		 
//		 SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户

//		 List<Map<String, String>> mapResult
		int count = billBacksectionSplitDtlManager.batchAdd(billBacksectionSplitDtlList,balance);
   		return count;
   	}
   	
   	/**
   	 * 批量保存
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value = "/batchAdds")
   	@ResponseBody
   	public int batchAdds(@ModelAttribute("billBacksectionSplitDtl")BillBacksectionSplitDtl billBacksectionSplitDtl, HttpServletRequest request) 
   			throws Exception{
   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
   		BillShopBalance balance = new BillShopBalance();
		balance.setCreateUser(loginUser.getUsername());
		balance.setCompanyNo(request.getParameter("companyNo_batchAdd"));
		balance.setCompanyName(request.getParameter("companyName_batchAdd"));
		balance.setOrganNo(request.getParameter("organNo_batchAdd"));
		balance.setMallNo(request.getParameter("mallNo_batchAdd"));
		balance.setBsgroupsNo(request.getParameter("bsgroupsNo_batchAdd"));	
		balance.setShopNo(request.getParameter("shopNo_batchAdd"));
		balance.setMonth(request.getParameter("month_batchAdd"));
		balance.setCreateTime(DateUtil.getCurrentDateTime());
		
		Map<String, Object> params = new HashMap<String, Object>();
		 params.put("companyNo", request.getParameter("companyNo_batchAdd"));
//		 params.put("parentOrganNo", request.getParameter("bsgroupsNo"));//sql特殊处理   根据管理城市来获取经营城市
//		 params.put("bsgroupsNo", request.getParameter("bsgroupsNo"));
//		 params.put("mallNo", request.getParameter("mallNo"));
//		 params.put("payType", payType);
//		 String[] shopnos = billShopBalance.getShopNo().split(",");//{"CA01BS","CA02BS","CA01BL"};
		if(billBacksectionSplitDtl.getShopNo() != null && !"".equals(billBacksectionSplitDtl.getShopNo())){
			params.put("shopNos", Arrays.asList(billBacksectionSplitDtl.getShopNo().split(",")));
		}
		 params.put("month",request.getParameter("month_batchAdd"));
		 BillBacksectionSplitDtl bill = null;
		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
		 /**
		 List<Shop> shopList= shopManager.findByBiz(null, params);
		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
		 BillBacksectionSplitDtl bill = null;
		 if(shopList != null && shopList.size() > 0) {
			 for(Shop shop : shopList) {
				 bill = new BillBacksectionSplitDtl();
				 bill.setCompanyNo(billBacksectionSplitDtl.getCompanyNo());
				 bill.setCompanyName(billBacksectionSplitDtl.getCompanyName());
				 bill.setMonth(billBacksectionSplitDtl.getMonth());
				 bill.setShopNo(shop.getShopNo());
				 bill.setShortName(shop.getShortName());
				 
				 bill.setId(null);
				 bill.setCreateUser(loginUser.getUsername());
				 bill.setCreateTime(DateUtil.getCurrentDateTime());
				 bill.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
				 bill.setStatus(ShopMallEnums.BalanceStatus.NO_VERIFY.getRequestId());
				 billBacksectionSplitDtlList.add(bill);
			 }
		 }
		 */
		 
//		 SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户

//		 List<Map<String, String>> mapResult
		int count = billBacksectionSplitDtlManager.batchAdds(billBacksectionSplitDtlList,balance);
//	    logger.info("count--------------"+count);   
   		return count;
   	}
	
	/**
   	 * 批量保存
   	 * @return
   	 * @throws Exception 
   	 */
//   	@RequestMapping(value = "/batchAdd")
//   	@ResponseBody
//   	public int batchAdd(@ModelAttribute("billBacksectionSplitDtl")BillBacksectionSplitDtl billBacksectionSplitDtl, HttpServletRequest request) 
//   			throws Exception{
////   		var shopBalanceObj = companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";"+balanceType+";
////   		"+organNo+";"+bsgroupsNo+";"+mallNo+";"+payType+";";
//		
////   		String[] shopBalances = strshopBalanceObjs.split(";");
////   		BillShopBalance billShopBalance = new BillShopBalance();
//   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
//   		
////   		String companyNo = shopBalances[0];
////		String organNo = shopBalances[6];
////		String bsgroupsNo = null;
////		 if(shopBalances[7] != null && shopBalances[7].length() > 0) {
////			 bsgroupsNo= shopBalances[7]; 
////		 }
////		String mallNo = null;
////		 if(shopBalances[8] != null && shopBalances[8].length() > 0) {
////			 mallNo= shopBalances[8]; 
////		 }
////		 String payType =null;
////		 if(shopBalances[9] != null && shopBalances[9].length() > 0) {
////			 payType= shopBalances[9]; 
////		 }
//		 
////		 根据管理城市获取经营城市
////		 Map<String, Object> organParams = new HashMap<String, Object>();
////		 organParams.put("parentNo", organNo);
////		 organParams.put("organLevel", 2);
////		 List<Organ> organList=  organManager.findByBiz(null, organParams);
////		 if(organList != null && organList.size() > 0) {
////				for(Organ organData : organList) {
////					shop.setOrganNo(organData.getOrganNo());  //一个管理城市对于多个经营城市
////				}
////		 }
//   		
//   		BillShopBalance balance = new BillShopBalance();
//		balance.setCreateUser(loginUser.getUsername());
//		balance.setCompanyNo(request.getParameter("companyNo_batchAdd"));
//		balance.setCompanyName(request.getParameter("companyName_batchAdd"));
//		balance.setOrganNo(request.getParameter("organNo_batchAdd"));
//		balance.setMallNo(request.getParameter("mallNo_batchAdd"));
//		balance.setBsgroupsNo(request.getParameter("bsgroupsNo_batchAdd"));	
//		balance.setShopNo(request.getParameter("shopNo_batchAdd"));
//		balance.setMonth(request.getParameter("month_batchAdd"));
////		balance.setBalanceNo(request.getParameter("balanceNo"));
//		balance.setCreateTime(DateUtil.getCurrentDateTime());
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		 params.put("companyNo", request.getParameter("companyNo_batchAdd"));
////		 params.put("parentOrganNo", request.getParameter("bsgroupsNo"));//sql特殊处理   根据管理城市来获取经营城市
////		 params.put("bsgroupsNo", request.getParameter("bsgroupsNo"));
////		 params.put("mallNo", request.getParameter("mallNo"));
////		 params.put("payType", payType);
////		 String[] shopnos = billShopBalance.getShopNo().split(",");//{"CA01BS","CA02BS","CA01BL"};
//		if(billBacksectionSplitDtl.getShopNo() != null && !"".equals(billBacksectionSplitDtl.getShopNo())){
//			params.put("shopNos", Arrays.asList(billBacksectionSplitDtl.getShopNo().split(",")));
//		}
//		 params.put("month",request.getParameter("month_batchAdd"));
//		 BillBacksectionSplitDtl bill = null;
//		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
//		 /**
//		 List<Shop> shopList= shopManager.findByBiz(null, params);
//		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
//		 BillBacksectionSplitDtl bill = null;
//		 if(shopList != null && shopList.size() > 0) {
//			 for(Shop shop : shopList) {
//				 bill = new BillBacksectionSplitDtl();
//				 bill.setCompanyNo(billBacksectionSplitDtl.getCompanyNo());
//				 bill.setCompanyName(billBacksectionSplitDtl.getCompanyName());
//				 bill.setMonth(billBacksectionSplitDtl.getMonth());
//				 bill.setShopNo(shop.getShopNo());
//				 bill.setShortName(shop.getShortName());
//				 
//				 bill.setId(null);
//				 bill.setCreateUser(loginUser.getUsername());
//				 bill.setCreateTime(DateUtil.getCurrentDateTime());
//				 bill.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
//				 bill.setStatus(ShopMallEnums.BalanceStatus.NO_VERIFY.getRequestId());
//				 billBacksectionSplitDtlList.add(bill);
//			 }
//		 }
//		 */
//		 
////		 SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户
//
////		 List<Map<String, String>> mapResult
//		int count = billBacksectionSplitDtlManager.batchAdd(billBacksectionSplitDtlList,balance);
//   		return count;
//   	}
//   	
//   	/**
//   	 * 批量保存
//   	 * @return
//   	 * @throws Exception 
//   	 */
//   	@RequestMapping(value = "/batchAdds")
//   	@ResponseBody
//   	public int batchAdds(@ModelAttribute("billBacksectionSplitDtl")BillBacksectionSplitDtl billBacksectionSplitDtl, HttpServletRequest request) 
//   			throws Exception{
//   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
//   		BillShopBalance balance = new BillShopBalance();
//		balance.setCreateUser(loginUser.getUsername());
//		balance.setCompanyNo(request.getParameter("companyNo_batchAdd"));
//		balance.setCompanyName(request.getParameter("companyName_batchAdd"));
//		balance.setOrganNo(request.getParameter("organNo_batchAdd"));
//		balance.setMallNo(request.getParameter("mallNo_batchAdd"));
//		balance.setBsgroupsNo(request.getParameter("bsgroupsNo_batchAdd"));	
//		balance.setShopNo(request.getParameter("shopNo_batchAdd"));
//		balance.setMonth(request.getParameter("month_batchAdd"));
//		balance.setCreateTime(DateUtil.getCurrentDateTime());
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		 params.put("companyNo", request.getParameter("companyNo_batchAdd"));
////		 params.put("parentOrganNo", request.getParameter("bsgroupsNo"));//sql特殊处理   根据管理城市来获取经营城市
////		 params.put("bsgroupsNo", request.getParameter("bsgroupsNo"));
////		 params.put("mallNo", request.getParameter("mallNo"));
////		 params.put("payType", payType);
////		 String[] shopnos = billShopBalance.getShopNo().split(",");//{"CA01BS","CA02BS","CA01BL"};
//		if(billBacksectionSplitDtl.getShopNo() != null && !"".equals(billBacksectionSplitDtl.getShopNo())){
//			params.put("shopNos", Arrays.asList(billBacksectionSplitDtl.getShopNo().split(",")));
//		}
//		 params.put("month",request.getParameter("month_batchAdd"));
//		 BillBacksectionSplitDtl bill = null;
//		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
//		 /**
//		 List<Shop> shopList= shopManager.findByBiz(null, params);
//		 List<BillBacksectionSplitDtl> billBacksectionSplitDtlList  = new ArrayList<BillBacksectionSplitDtl>();
//		 BillBacksectionSplitDtl bill = null;
//		 if(shopList != null && shopList.size() > 0) {
//			 for(Shop shop : shopList) {
//				 bill = new BillBacksectionSplitDtl();
//				 bill.setCompanyNo(billBacksectionSplitDtl.getCompanyNo());
//				 bill.setCompanyName(billBacksectionSplitDtl.getCompanyName());
//				 bill.setMonth(billBacksectionSplitDtl.getMonth());
//				 bill.setShopNo(shop.getShopNo());
//				 bill.setShortName(shop.getShortName());
//				 
//				 bill.setId(null);
//				 bill.setCreateUser(loginUser.getUsername());
//				 bill.setCreateTime(DateUtil.getCurrentDateTime());
//				 bill.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
//				 bill.setStatus(ShopMallEnums.BalanceStatus.NO_VERIFY.getRequestId());
//				 billBacksectionSplitDtlList.add(bill);
//			 }
//		 }
//		 */
//		 
////		 SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户
//
////		 List<Map<String, String>> mapResult
//		int count = billBacksectionSplitDtlManager.batchAdds(billBacksectionSplitDtlList,balance);
//   		return count;
//   	}
}