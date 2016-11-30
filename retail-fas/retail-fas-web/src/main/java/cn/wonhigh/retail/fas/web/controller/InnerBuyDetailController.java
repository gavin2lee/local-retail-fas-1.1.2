package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.InvoiceApplyConfirmDtlManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * gms、pos 内购明细查询
 * @author wang.yj
 * @date  2015-06-15 10:33:45
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
@RequestMapping("/inner_buy_detail")
@ModuleVerify("30190020")
public class InnerBuyDetailController extends BaseController<OrderDtlDto> {

	protected static final XLogger logger = XLoggerFactory.getXLogger(InnerBuyDetailController.class);


	@Resource
	private BillSaleBalanceManager billSaleBalanceManager;

	@Resource
	private OrderMainManager orderMainManager;
	
	@Resource
	private InvoiceApplyConfirmDtlManager invoiceApplyConfirmDtlManager;


	@Override
	public CrudInfo init() {
		return new CrudInfo("inner_buy_detail/", billSaleBalanceManager);
	}

//	/**
//	 * 根据客户方编号查询GMS 相关销售单据信息（客殘销售单、索赔单(退库差异，其他差异)、团购出库单、报废单）
//	 * @param params
//	 * @param page
//	 * @throws ManagerException
//	 */
//	private List<OrderDtlDto> getOrderDtlList(Map<String, Object> params,SimplePage page) throws ManagerException {
//		List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
//		
//		dtlList = orderMainManager.findBillSaleDetailByCondition(page, params);
//		if (CollectionUtils.isNotEmpty(dtlList)) {
//			for (int j = 0; j < dtlList.size(); j++) {
//				OrderDtlDto dtl = dtlList.get(j);
//				// 根据订单号获取发票号信息
//				params.put("orderNo", dtl.getOrderNo());
//				List<GroupSaleApplyInvoiceRel> groupSaleApplyInvoiceRelList = groupSaleApplyInvoiceRelManager.findByBiz(null, params);
//				if (CollectionUtils.isNotEmpty(groupSaleApplyInvoiceRelList)) {
//					GroupSaleApplyInvoiceRel groupSaleApplyInvoiceRel = groupSaleApplyInvoiceRelList.get(0);
//					dtl.setInvoiceNo(groupSaleApplyInvoiceRel.getInvoiceApplyNo());
//					dtl.setInvoiceDate(groupSaleApplyInvoiceRel.getUpdateTime());
//				} else {
//					dtl.setInvoiceNo("");
//					dtl.setInvoiceDate(null);
//				}
////				params.put("organCode", dtl.getOrganName());//查询时，把管理城市编号设置到organName 属性中
////				List<Organ> organList = organManager.findByBiz(null, params);
////				if(CollectionUtils.isNotEmpty(organList)){
////					dtl.setOrganName(organList.get(0).getName());
////				}
//			}
//		}
//		return dtlList;
//	}
//
//	/**
//	 * 根据店铺编号及其他条件查询POS 的内购销售单
//	 * @param params
//	 * @param page
//	 * @return
//	 * @throws ManagerException
//	 */
//	private List<OrderDtlDto> getOrderDtlList2(Map<String, Object> params,SimplePage page) throws ManagerException {
//		List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
//		
//		dtlList = orderMainManager.findInnerBuyDetailByCondition(page, params);
//		for (OrderDtlDto orderDtlDto : dtlList) {
//			//查询现金券
//			OcOrderTicketDto dto = new OcOrderTicketDto();
//			dto.setOrderNo(orderDtlDto.getOrderNo());
//			dto.setTicketFlag(TICKETFLAG);
//			dto.setTicketType(TICKETTYPE);
//
//			List<OcOrderTicketDto> ticketAmount = new ArrayList<OcOrderTicketDto>();
//			try {
//				ticketAmount = orderTicketApi.findGroupTickeAmount(dto);
//			} catch (RpcException e) {
//				e.printStackTrace();
//			}
//
//			if (null != ticketAmount && ticketAmount.size() > 0) {
//				orderDtlDto.setCouponPrice(ticketAmount.get(0).getAvailableAmount());
//				if (orderDtlDto.getAmount() != null) {
//					orderDtlDto.setPrefPrice(orderDtlDto.getAmount().subtract(orderDtlDto.getCouponPrice()));
//				}
//			}else{// 没有现金券金额时，现金就是总金额
//				orderDtlDto.setPrefPrice(orderDtlDto.getAmount());
//			}
//
//		}
//		return dtlList;
//	}
//	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/to_list")
	public ModelAndView toList(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		String detailType = req.getParameter("detailType");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("detailType", detailType);
		}
		mav.setViewName("groupon_dtl/inner_buy_list");
		return mav;
	}
	
	@RequestMapping("/to_pos_detail")
	public String toPosDetail() {
		return "groupon_dtl/pos_detail_list";
	}

	/**
	 * 根据查询条件查询gms 的内购明细信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/gms_detail.json")
	@ResponseBody
	public Map<String, Object> queryShopMemberList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		//根据店铺编号进行汇总（查看汇总时）
		Map<String, Object> params = builderParams(req, model);
		setCommonParams(params);
		OrderDtlDto orderDtlDto = billSaleBalanceManager.findGmsInnerBuyDetailCount(params);
		List<OrderDtlDto> gmsDetailList = new ArrayList<OrderDtlDto>();
		
		List<OrderDtlDto> footer = new ArrayList<OrderDtlDto>();
		if(null != orderDtlDto && orderDtlDto.getTotal() > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, orderDtlDto.getTotal());
			gmsDetailList = billSaleBalanceManager.findGmsInnerBuyDetailList(page, null, null, params);
			
			if(null != orderDtlDto){
				orderDtlDto.setCategoryName("合计");
				footer.add(orderDtlDto);
			}
			gmsDetailList=this.detailListByBrand(gmsDetailList);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", orderDtlDto.getTotal() );
		obj.put("rows", gmsDetailList);
		obj.put("footer", footer);
		return obj;
	}
	
	
	/**
	 * 根据查询条件查询gms 的内购明细信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/pos_detail.json")
	@ResponseBody
	public Map<String, Object> findPosInnerBuyDetailList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		//根据店铺编号进行汇总（查看汇总时）
		Map<String, Object> params = builderParams(req, model);
		setCommonParams(params);
		OrderDtlDto orderDtlDto = orderMainManager.findPosInnerBuyDetailCount(params);
		List<OrderDtlDto> posDetailList = new ArrayList<OrderDtlDto>();
		
		List<OrderDtlDto> footer = new ArrayList<OrderDtlDto>();
		if(null != orderDtlDto && orderDtlDto.getTotal() > 0){
			SimplePage page = new SimplePage(pageNo, pageSize,orderDtlDto.getTotal());
			posDetailList = orderMainManager.findPosInnerBuyDetailList(page, params);
			String orderNo = "";
			for (OrderDtlDto orderDtlDto2 : posDetailList) {
				if(!orderDtlDto2.getOrderNo().equals(orderNo)){
					orderNo = orderDtlDto2.getOrderNo();
				}else{//相同单据的明细记录，清空表头的备注信息，免得重复显示
					orderDtlDto2.setRemark("");
				}
			}
			if(null != orderDtlDto){
				orderDtlDto.setCategoryName("合计");
				footer.add(orderDtlDto);
			}
			//按管理城市、品牌分类进行统计
			posDetailList=this.detailListByBrand(posDetailList);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", orderDtlDto.getTotal());
		obj.put("rows", posDetailList);
		obj.put("footer", footer);
		return obj;
	}
	

	/**
	 * 设置多选项的参数
	 * @param params
	 */
	private void setCommonParams(Map<String,Object> params){
		//品牌
		String brandNoStr = params.get("brandNo") == null ? "" : params.get("brandNo").toString();
		List<String> brandNoList = null;
		if (StringUtils.isNotBlank(brandNoStr)) {
			brandNoList = new ArrayList<String>();
			String brandNos[] = brandNoStr.split(",");
			for (String brandNo : brandNos) {
				brandNoList.add(brandNo);
			}
			params.put("brandNoList", brandNoList);
		}

		//管理城市
		String organNoStr = params.get("organNo") == null ? "" : params.get("organNo").toString();
		List<String> organNoList = null;
		if (StringUtils.isNotBlank(organNoStr)) {
			organNoList = new ArrayList<String>();
			String organNos[] = organNoStr.split(",");
			for (String organNo : organNos) {
				organNoList.add(organNo);
			}
			params.put("organNoList", organNoList);
		}

		//大类
		String categoryNoStr = params.get("categoryNo") == null ? "" : params.get("categoryNo").toString();
		List<String> categoryNoList = null;
		if (StringUtils.isNotBlank(categoryNoStr)) {
			categoryNoList = new ArrayList<String>();
			String categoryNos[] = categoryNoStr.split(",");
			for (String categoryNo : categoryNos) {
				categoryNoList.add(categoryNo);
			}
			params.put("categoryNoList", categoryNoList);
		}
	}
	
	
	@RequestMapping(value = "/finance_confirm")
	@ResponseBody
	public Boolean financeConfirm(HttpServletRequest req, Model model) throws ManagerException {
		int count = 0 ;
		try {
			SystemUser currentUser = CurrentUser.getCurrentUser(req);
			String dataList = req.getParameter("datas") == null ? "" : req.getParameter("datas");
			if (StringUtils.isNotBlank(dataList)) {
				JsonUtil<OrderDtlDto> util = new JsonUtil<OrderDtlDto>();
				List<OrderDtlDto> list = util.convertListWithTypeReference(dataList, req, OrderDtlDto.class);
				count = invoiceApplyConfirmDtlManager.addInvoiceApplyConfirmDtl(list,currentUser.getUsername());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return count > 0 ? true : false;
	}

	@RequestMapping(value = "/finance_anti_confirm")
	@ResponseBody
	public Boolean financeAntiConfirm(HttpServletRequest req, Model model) throws ManagerException {
		int count = 0 ;
		try {
			SystemUser currentUser = CurrentUser.getCurrentUser(req);
			String dataList = req.getParameter("datas") == null ? "" : req.getParameter("datas");
			if (StringUtils.isNotBlank(dataList)) {
				JsonUtil<OrderDtlDto> util = new JsonUtil<OrderDtlDto>();
				List<OrderDtlDto> list = util.convertListWithTypeReference(dataList, req, OrderDtlDto.class);
				count = invoiceApplyConfirmDtlManager.updateByOrderNo(list,currentUser.getUsername());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return count > 0 ? true : false;
	}
	
	 /**
   	 * 导出
   	 * @param modelType 实体对象
   	 * @param req HttpServletRequest
   	 * @param model Model
   	 * @param response HttpServletResponse
   	 * @throws ManagerException 异常
   	 */
    protected List<OrderDtlDto> queryExportData(Map<String, Object> params) throws ManagerException {
    	List<OrderDtlDto> list = new ArrayList<OrderDtlDto>();
    	try {
		
		String exportType = params.get("exportType") == null ?"":params.get("exportType").toString();
		setCommonParams(params);
		if("1".equals(exportType)){
			SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
			list = orderMainManager.findPosInnerBuyDetailList(page, params);
		}else{ //GMS
			SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
			list = billSaleBalanceManager.findGmsInnerBuyDetailList(page,null,null, params);
		}
		list = this.detailListByBrand(list);
   		} catch (Exception e) {
   			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
   		}
   		return list;
   	}
    
	/**
	 * 初始化统计对象
	 * @param billSalesSum
	 */
	private void initOrderDtlDto(OrderDtlDto orderDtlDto) {
		orderDtlDto.setQty(0);
		/**
		 * 
		 
		orderDtlDto.setTagPrice(new BigDecimal(0d));
		orderDtlDto.setSettlePrice(new BigDecimal(0d));
		*/
		orderDtlDto.setDiscAmount(new BigDecimal(0d));
		orderDtlDto.setDiscPrice(new BigDecimal(0d));
		orderDtlDto.setAmount(new BigDecimal(0d));
		orderDtlDto.setSellAmount(new BigDecimal(0d));
		/**
		 * 
		orderDtlDto.setTicketAmount(new BigDecimal(0d));
		orderDtlDto.setTicketPrice(new BigDecimal(0d));
		orderDtlDto.setDiffAmount(new BigDecimal(0d));
		orderDtlDto.setSellAmount(new BigDecimal(0d));
		 */
		
		orderDtlDto.setUnitCost(new BigDecimal(0d));
		orderDtlDto.setRegionCost(new BigDecimal(0d));
		orderDtlDto.setHeadquarterCost(new BigDecimal(0d));
	}
	
	/**
	 * 将第二个对象的值累加至第一个对象中
	 * @param mainBillSalesSum
	 * @param addBillSalesSum
	 */
	private void sumToOrderDtlDto(OrderDtlDto mainOrderDtlDto, OrderDtlDto addOrderDtlDto) {
		if(null != mainOrderDtlDto.getQty() && null != addOrderDtlDto.getQty()){
			mainOrderDtlDto.setQty(mainOrderDtlDto.getQty() + addOrderDtlDto.getQty());
		}
		/**
		 * 
		if(null != mainOrderDtlDto.getTagPrice() && null != addOrderDtlDto.getTagPrice()){
			mainOrderDtlDto.setTagPrice(mainOrderDtlDto.getTagPrice().add(addOrderDtlDto.getTagPrice()));
		}
		if(null != mainOrderDtlDto.getSettlePrice() && null != addOrderDtlDto.getSettlePrice()){
			mainOrderDtlDto.setSettlePrice(mainOrderDtlDto.getSettlePrice().add(addOrderDtlDto.getSettlePrice()));
		}
		 */
		if(null != mainOrderDtlDto.getDiscAmount() && null != addOrderDtlDto.getDiscAmount()){
			mainOrderDtlDto.setDiscAmount(mainOrderDtlDto.getDiscAmount().add(addOrderDtlDto.getDiscAmount()));
		}
		if(null != mainOrderDtlDto.getDiscPrice() && null != addOrderDtlDto.getDiscPrice()){
			mainOrderDtlDto.setDiscPrice(mainOrderDtlDto.getDiscPrice().add(addOrderDtlDto.getDiscPrice()));
		}
		if(null != mainOrderDtlDto.getAmount() && null != addOrderDtlDto.getAmount()){
			mainOrderDtlDto.setAmount(mainOrderDtlDto.getAmount().add(addOrderDtlDto.getAmount()));
		}
		if(null != mainOrderDtlDto.getSellAmount() && null != addOrderDtlDto.getSellAmount()){
			mainOrderDtlDto.setSellAmount(mainOrderDtlDto.getSellAmount().add(addOrderDtlDto.getSellAmount()));
		}
		/**
		 * 

		if(null != mainOrderDtlDto.getTicketAmount() && null != addOrderDtlDto.getTicketAmount()){
			mainOrderDtlDto.setTicketAmount(mainOrderDtlDto.getTicketAmount().add(addOrderDtlDto.getTicketAmount()));
		}
		if(null != mainOrderDtlDto.getTicketPrice() && null != addOrderDtlDto.getTicketPrice()){
			mainOrderDtlDto.setTicketPrice(mainOrderDtlDto.getTicketPrice().add(addOrderDtlDto.getTicketPrice()));
		}
		if(null != mainOrderDtlDto.getDiffAmount() && null != addOrderDtlDto.getDiffAmount()){
			mainOrderDtlDto.setDiffAmount(mainOrderDtlDto.getDiffAmount().add(addOrderDtlDto.getDiffAmount()));
		}
		if(null != mainOrderDtlDto.getSellAmount() && null != addOrderDtlDto.getSellAmount()){
			mainOrderDtlDto.setTagPrice(mainOrderDtlDto.getTagPrice().add(addOrderDtlDto.getTagPrice()));
		}
		 */
		
		if(null != mainOrderDtlDto.getUnitCost() && null != addOrderDtlDto.getUnitCost()){
			mainOrderDtlDto.setUnitCost(mainOrderDtlDto.getUnitCost().add(addOrderDtlDto.getUnitCost()));
		}
		if(null != mainOrderDtlDto.getRegionCost() && null != addOrderDtlDto.getRegionCost()){
			mainOrderDtlDto.setRegionCost(mainOrderDtlDto.getRegionCost().add(addOrderDtlDto.getRegionCost()));
		}
		if(null != mainOrderDtlDto.getHeadquarterCost() && null != addOrderDtlDto.getHeadquarterCost()){
			mainOrderDtlDto.setHeadquarterCost(mainOrderDtlDto.getHeadquarterCost().add(addOrderDtlDto.getHeadquarterCost()));
		}
	}
    
	/**
	 * 按管理城市、品牌分类进行统计
	 */
	private List<OrderDtlDto> detailListByBrand(List<OrderDtlDto> detailList) {
		OrderDtlDto temp = null;
		Map<String, OrderDtlDto> map = new LinkedHashMap<String, OrderDtlDto>();//品牌
		for (OrderDtlDto o : detailList) {
			if (null == map.get(o.getOrganName() + o.getBrandName())) {
				temp = new OrderDtlDto();
				this.initOrderDtlDto(temp);
				temp.setCompanyName("小计：");
				temp.setOrganName(o.getOrganName());//设置管理城市
				temp.setBrandName(o.getBrandName());//设置品牌名称
				map.put(o.getOrganName() + o.getBrandName(), temp);
			}
			this.sumToOrderDtlDto(map.get(o.getOrganName() + o.getBrandName()), o);

		}
		//按管理城市按品牌汇总到列表底部
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			OrderDtlDto orderDtlDtoObject = map.get(key);
			String brandName = orderDtlDtoObject.getBrandName();
			String organName = orderDtlDtoObject.getOrganName();
			int index = 0;//某品牌最后一个下标
			for (OrderDtlDto odd : detailList) {
				if (StringUtils.isNotBlank(brandName) && brandName.equals(odd.getBrandName()) &&
						StringUtils.isNotBlank(organName) && organName.equals(odd.getOrganName())) {
					index = detailList.indexOf(odd);
				}
			}
			if (index > -1) {
				detailList.add(index + 1, orderDtlDtoObject);
			}
		}
		return detailList;
	}
}