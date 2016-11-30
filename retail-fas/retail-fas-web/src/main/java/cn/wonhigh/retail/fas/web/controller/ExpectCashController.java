package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.ExpectCash;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.ExpectCashManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.pos.api.client.dto.finance.ExpectCashDto;
import cn.wonhigh.retail.pos.api.client.service.finance.ExpectCashApi;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * ExpectCashController
 * 
 * @author tang.yc
 * @date 2014-9-4 下午3:45:41
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */

@Controller
@RequestMapping("/expect_cash")
@ModuleVerify("30140032")
public class ExpectCashController extends BaseController<ExpectCash> {
	protected static final XLogger logger = XLoggerFactory.getXLogger(ExpectCashController.class);

//	@Override
//	protected com.yougou.logistics.base.web.controller.BaseCrudController.CrudInfo init() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Resource
	private ExpectCashManager expectCashManager;
	
	@Resource
	private ExpectCashApi expectCashApi;
	
	@Resource
	private ShopManager shopManager ;

	@Override
	public CrudInfo init() {
		return new CrudInfo("expect_cash/", expectCashManager);
	}
	
	
    @RequestMapping("/list")
	public String list() {
	 return "expect_cash/list";
    }  
	 
	@RequestMapping(value = "/promotion", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryPromotionList(HttpServletRequest req, Model model) throws ManagerException {
		return null;
	}
	
	/**
     * 查询
     */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
//	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
	    	obj.put("rows", new ArrayList<SaleOrderPayway>());
			obj.put("total", 0);
			
	    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
			int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
			String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
			String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
	    	Map<String, Object> params = builderParams(req, model);
	    	
	    	String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
			String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
			StringBuilder condidtions = new StringBuilder("and ");
			//是否小计
//			String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
			//页面选择了特定的店铺
			if(StringUtils.isNotEmpty(shopNo)){
//				params.put("shopNoLists",  Arrays.asList(shopNo.split(",")));
				params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
				condidtions.append("where ");
			}else {
				//根据公司和管理城市查询店铺
				Map<String, Object> shopParamMap = new HashMap<String, Object>();
				shopParamMap.put("companyNo", companyNo);
				if(StringUtils.isNotEmpty(organNo)){
					shopParamMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
				}
				//需要查询的店铺列表
				List<String> shopNoList = new ArrayList<String>();
				//页面选择了公司和管理城市，根据提交来查询店铺
				List<Shop>  shopList =  shopManager.findByBiz(null, shopParamMap);
				if (!CollectionUtils.isEmpty(shopList)) {
					for(Shop shop : shopList) {
						shopNoList.add(shop.getShopNo());
					 }
				}
				if (!CollectionUtils.isEmpty(shopNoList)) {
					params.put("shopNoList",  shopNoList);
				}else{
					params.put("shopNo", "01");
				}
			}
			
			List<Integer> statusList = new ArrayList<Integer>();
			//0-有效
			statusList.add(new Integer(0));
			params.put("statusList", statusList);

			List<Integer> confirmFlagList = new ArrayList<Integer>();
			//1-业务已确认  0-未确定 1-已确定 2-财务确认 默认为0'
			confirmFlagList.add(new Integer(1));
			confirmFlagList.add(new Integer(2));
			params.put("confirmFlagList", confirmFlagList);
			
			int total = expectCashManager.countExpectCashByParams(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<ExpectCash> list = expectCashManager.findExpectCashByParams(page, sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);
		} catch (Exception e) {
			logger.error(">>>>>>查询预收款出错.", e);
			throw new ManagerException(e);
		}
		return obj;
	}
	
	
	@Override
    public List<ExpectCash> queryExportData(Map<String, Object> params) throws ManagerException {
   		
		String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		StringBuilder condidtions = new StringBuilder("and ");
		//是否小计
//		String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
//			params.put("shopNoLists",  Arrays.asList(shopNo.split(",")));
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
			condidtions.append("where ");
		}else {
			//根据公司和管理城市查询店铺
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);
			if(StringUtils.isNotEmpty(organNo)){
				shopParamMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
			}
			//需要查询的店铺列表
			List<String> shopNoList = new ArrayList<String>();
			//页面选择了公司和管理城市，根据提交来查询店铺
			List<Shop>  shopList =  shopManager.findByBiz(null, shopParamMap);
			if (!CollectionUtils.isEmpty(shopList)) {
				for(Shop shop : shopList) {
					shopNoList.add(shop.getShopNo());
				 }
			}
			if (!CollectionUtils.isEmpty(shopNoList)) {
				params.put("shopNoList",  shopNoList);
			}else{
				params.put("shopNo", "01");
			}
		}
		
		List<Integer> statusList = new ArrayList<Integer>();
		//0-有效
		statusList.add(new Integer(0));
		params.put("statusList", statusList);

		List<Integer> confirmFlagList = new ArrayList<Integer>();
		//1-业务已确认  0-未确定 1-已确定 2-财务确认 默认为0'
		confirmFlagList.add(new Integer(1));
		confirmFlagList.add(new Integer(2));
		params.put("confirmFlagList", confirmFlagList);
		
		int total = expectCashManager.countExpectCashByParams(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		List<ExpectCash> list = expectCashManager.findExpectCashByParams(page, orderByField, orderBy, params);
		for(ExpectCash expectCash:list){
			if(expectCash.getConfirmFlag()==1){
				expectCash.setConfirmFlagStr("业务已确认");
			}else if(expectCash.getConfirmFlag()==2){
				expectCash.setConfirmFlagStr("财务已确认");
			}else{
				expectCash.setConfirmFlagStr("未确认");
			}
			
			if(expectCash.getBalanceStatus()==2){
				expectCash.setBalanceStatusStr("已结算");
			}else{
				expectCash.setBalanceStatusStr("未结算");
			}
			
			if("1".equals(expectCash.getBusinessType())){
				expectCash.setBusinessTypeExpectStr("商场预收");
			}else{
				expectCash.setBusinessTypeExpectStr("定金预收");
			}
			
			if("1".equals(expectCash.getBusinessFlag())){
				expectCash.setBusinessFlagStr("收款");
			}else{
				expectCash.setBusinessFlagStr("退款");
			}
		
			expectCash.setCurrencyTypeStr("人民币");
	
		}
		return list;
	}
	
	/**
	 * 新增预收款信息
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Boolean> insert(ExpectCash expectCash, HttpServletRequest req) {
		String flag = PublicConstans.COMMON_SUCCESS;
		try {
//			SystemUser user = getSystemUser(req);
//			CurrentShopVo currentShopVo = getCurrentShopVo(req);	
//			expectCash.setShopNo(currentShopVo.getShopNo());
			
			//验证结算编码是否唯一
			if(expectCash.getBusinessFlag().equals(PublicConstans.EXPECT_CASH_RECEIPT) && !expectCashManager.verifyTheOnly(expectCash)){
				return (Map<String, Boolean>) new ResponseEntity<Object>(PublicConstans.COMMON_FALSE, HttpStatus.OK);
			}
			
			//验证退款金额是否大于收款金额
			if(expectCash.getBusinessFlag().equals(PublicConstans.EXPECT_CASH_REFUND) && expectCashManager.verifyPrice(expectCash)){
				return (Map<String, Boolean>) new ResponseEntity<Object>(PublicConstans.COMMON_TRUE, HttpStatus.OK);
			}
			//expectCashManager.add(expectCash, null, null);
		} catch (ManagerException e) {
            flag = PublicConstans.COMMON_FAIL;;
			logger.error(">>>>>>新增预收款出错.", e);
		}
		return (Map<String, Boolean>) new ResponseEntity<Object>(flag, HttpStatus.OK);
	}

	/**
	 * 修改预收款信息
	 */
	@Override
	@RequestMapping(method = RequestMethod.PUT)	
	public Map<String, Boolean> update(ExpectCash expectCash, HttpServletRequest req) {
		String flag = PublicConstans.COMMON_SUCCESS;
		try {
//			SystemUser user = getSystemUser(req);
//			CurrentShopVo currentShopVo = getCurrentShopVo(req);	
//			expectCash.setShopNo(currentShopVo.getShopNo());

			//验证结算编码是否唯一
			if(expectCash.getBusinessFlag().equals(PublicConstans.EXPECT_CASH_RECEIPT) && !expectCashManager.verifyTheOnly(expectCash)){
				return (Map<String, Boolean>) new ResponseEntity<String>(PublicConstans.COMMON_FALSE, HttpStatus.OK);
			}
			 
			//验证退款金额是否大于收款金额
			if(expectCash.getBusinessFlag().equals(PublicConstans.EXPECT_CASH_REFUND) && expectCashManager.verifyRefundPrice(expectCash)){
				return (Map<String, Boolean>) new ResponseEntity<String>(PublicConstans.COMMON_TRUE, HttpStatus.OK);
			}
			
			expectCashManager.modify(expectCash, null, null);
		} catch (ManagerException e) {
			flag = PublicConstans.COMMON_FAIL;
			logger.error(">>>>>>修改预收款出错.", e);
		}
		return (Map<String, Boolean>) new ResponseEntity<String>(flag, HttpStatus.OK);
	}

	/**
	 * 物理删除
	 * @param deleteIds
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> delete(String deleteIds) {
		String flag = PublicConstans.COMMON_SUCCESS;
		try {
			String[] ids = null;
			if (StringUtils.isNotEmpty(deleteIds)) {
				ids = deleteIds.split(",");
				if (null != ids && ids.length > 0) {
					expectCashManager.deleteByIds(ids);
				} else {
					flag = PublicConstans.COMMON_FAIL;
				}
			} else {
				flag = PublicConstans.COMMON_FAIL;
			}
		} catch (ManagerException e) {
			flag = PublicConstans.COMMON_FAIL;
			logger.error(">>>>>>删除预收款出错.", e);
		}
		return new ResponseEntity<String>(flag, HttpStatus.OK);
	}
	
	/**
	 * 确认预收款信息
	 * @param verifyIds
	 * @param req
	 * @return
	 * @throws ManagerException  flag 1-审核  2 反审核
	 */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> verify(String verifyIds, HttpServletRequest req) {
		Map<String,String> message = new HashMap<String, String>();
		try {
			SystemUser currUser = CurrentUser.getCurrentUser();
			String flag = req.getParameter("flag");
			String userName  = currUser.getUsername();				
			String[] ids = null;
			if (StringUtils.isNotEmpty(verifyIds)) {
				ids = verifyIds.split(",");
				if (null != ids && ids.length > 0) {
					message = expectCashManager.verifyByIds(ids,flag,currUser,userName);
					message.put("flag", PublicConstans.COMMON_SUCCESS);
				} else {
					message.put("flag", PublicConstans.COMMON_FAIL);
				}
			} else {
				message.put("flag", PublicConstans.COMMON_FAIL);
			}
		}  catch (ManagerException e) {
			message.put("flag", PublicConstans.COMMON_FAIL);
			logger.error(">>>>>>确认预收款出错.", e);
		}
		return new ResponseEntity<Map<String,String>>(message, HttpStatus.OK);
	}
    /**
     * 打印
     * @Title: printTicket  
     * @param @param request
     * @param @param model
     * @param @return 
     * @return String 
     * @author: zhou.q1 
     * @throws
     */
    @RequestMapping(value = "/printer", method = RequestMethod.GET)
	public String printTicket(HttpServletRequest request, Model model) {
    	String billNo = StringUtils.isEmpty(request.getParameter("billNo")) ? "" : String.valueOf(request.getParameter("billNo"));
		Map<String, Object> params = new HashMap<String, Object>();
    	try {
    		params.put("businessTypeExpectLookupCode", PublicConstans.STANDARD_BUSINESS_TYPE_EXPECT);
			params.put("businessFlagLookupCode", PublicConstans.STANDARD_BUSINESS_FLAG);
			if (StringUtils.isNotEmpty(billNo)) {
				params.put("billNo", billNo);
			}
			List<ExpectCash> list = expectCashManager.findExpectCashByParams(params);
 			model.addAttribute("expectCashs", list);
		} catch (Exception e) {
			logger.error("打印预收款信息时出错.", e);
		}
		return "expect_cash/printExpectCash";
	}


}