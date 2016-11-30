package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterParentDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;
import cn.wonhigh.retail.fas.common.model.OrderMainExport;
import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.manager.BillCommonInvoiceRegisterManager;
import cn.wonhigh.retail.fas.manager.CategoryManager;
import cn.wonhigh.retail.fas.manager.MemberOrderDtlManager;
import cn.wonhigh.retail.fas.manager.OrderDtlManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.manager.OrganManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderParameterParentDto;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderTicketDto;
import cn.wonhigh.retail.oc.common.api.dto.OrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.oc.common.api.service.OrderMainApi;
import cn.wonhigh.retail.oc.common.api.service.OrderTicketApi;
import cn.wonhigh.retail.oc.common.api.util.OcPagingDto;
import cn.wonhigh.retail.oc.common.api.util.OcSimplePageDto;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 15:21:34
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
@RequestMapping("/member_order_summary")
@Deprecated
public class MemberOrderSummaryController extends BaseController<MemberOrderSummary> {

	@Resource
	private OrderDtlManager orderDtlManager;

	@Resource
	private OrderMainApi orderMainApi;
	
	
	@Resource
	private OrderMainManager orderMainManager;
	@Resource
	private OrderTicketApi orderTicketApi;
	
	@Resource
	private MemberOrderDtlManager memberOrderDtlManager;
	
	@Resource
	private BillCommonInvoiceRegisterManager billCommonInvoiceRegisterManager;
	
	@Resource
	private CategoryManager categoryManager;
	
	@Resource
	private OrganManager organManager;

	private static final int MEMBER_ORDER_TYPE = 1;

	private static final int MEMBER_SHOP_TYPE = 2;
	
	private static final int BILLSTATUS= 30;
	private static final int BILLSTATUS1= 41;
	private static final int TICKETTYPE = 2;
	private static final int TICKETFLAG = 2;

	@Override
	public CrudInfo init() {
		return new CrudInfo("member_order_summary/", orderDtlManager);
	}

	@RequestMapping("/list")
	public String list() {
		return "member_order_summary/list";
	}

	//员购值
	private static final int BUSINESSTYPE = 4;
	private static final int BUSINESSTYPES = 3;
	//导出列表
	private List<OrderMainExport> shopSummaryExportList = null;
	private List<OrderMainExport> orderSummaryExportList = null;

	/**
	 * 调用pos接口获取员购数据
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	private List<POSOrderAndReturnExMainDtlDto> getPosDate(Map<String, Object> params) throws ManagerException {
		POSOcOrderParameterParentDto ocOrderParameterParentNoDto = new POSOcOrderParameterParentDto();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Integer> businessTypeList = new ArrayList<Integer>();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(BILLSTATUS);
		statusList.add(BILLSTATUS1);
		businessTypeList.add(BUSINESSTYPE);
		businessTypeList.add(BUSINESSTYPES);
		ocOrderParameterParentNoDto.setBusinessTypeList(businessTypeList);
		ocOrderParameterParentNoDto.setStatusList(statusList);
		try {
			ocOrderParameterParentNoDto.setStartOutDate(format.parse(params.get("startDate").toString()));
			ocOrderParameterParentNoDto.setEndOutDate(format.parse(params.get("endDate").toString()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDto = null;
		try {
			POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
			pageDto.setPageNo(1);
			pageDto.setPageSize(100000);
			//调用pos的接口,要按照店铺进行汇总，数据不能分页查询，拿到所有的订单
			orderAndReturnExMainDtlDto = this.orderMainManager.findList4OrderMainForCompany(pageDto, params
					.get("companyNo").toString(), ocOrderParameterParentNoDto, 2, "", "",null);
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}

		if (null == orderAndReturnExMainDtlDto) {
			return null;
		}

		List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = orderAndReturnExMainDtlDto.getResult();

		return ocOrderDtlList;
	}

	/**
	 * 查询明细时按订单号进行汇总
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/order_member_list.json")
	@ResponseBody
	public Map<String, Object> queryOrderMemberList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		//根据订单编号进行汇总（查看明细时）
		List<MemberOrderSummary> orderSummaryList = new ArrayList<MemberOrderSummary>();

		Map<String, Object> params = builderParams(req, model);
		List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = getPosDate(params);

		if(null != ocOrderDtlList && ocOrderDtlList.size() > 0){
			for (int i = 0; i < ocOrderDtlList.size(); i++) {
				POSOrderAndReturnExMainDtlDto detail = ocOrderDtlList.get(i);
/*				if (i != 0 && StringUtils.equals(detail.getOrderNo(), ocOrderDtlList.get(i - 1).getOrderNo())) {
					continue;
				}*/

				MemberOrderSummary sum = new MemberOrderSummary();
				
				if(StringUtils.isNotEmpty(detail.getItemCode())){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("code", detail.getItemCode());
					Category category = categoryManager.getCategoryByItemCode(map);
					
					if(null != category){
						sum.setCategoryName(category.getName());
					}
				}
				
				if(StringUtils.isNotEmpty(detail.getShopNo())){
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("shopNo", detail.getShopNo());
					Organ organ = organManager.getOrganByShopNo(map1);
					
					if(null != organ){
						sum.setOrganName(organ.getName());
					}
				}
				sum.setShopNo(detail.getShopNo());
				sum.setShopName(detail.getShopName());
				sum.setBillNo(detail.getOrderNo());
				sum.setOutDate(detail.getOutDate());
				sum.setCouponAmount(detail.getCouponAmount());
				sum.setTotalQty(detail.getQty());
				sum.setTagPriceAmount(detail.getTagPriceAmount());
				sum.setSalePriceAmount(detail.getSalePriceAmount());
				sum.setReducePriceAmount(detail.getReduceAmount());
				sum.setSettleAmount(detail.getSettleAmount());
				sum.setAllAmount(detail.getAllAmount());
				sum.setCashAmount((detail.getAllAmount() == null ? new BigDecimal(0) : detail.getAllAmount())
						.subtract(detail.getCouponAmount() == null ? new BigDecimal(0) : detail.getCouponAmount()));
				sum.setInvoiceNo(detail.getInvoiceNo());
				sum.setInvoiceAmount(detail.getInvoiceAmount());
				sum.setBrandName(detail.getBrandName());
				sum.setBillTypeName("员购订单");
				sum.setInvoiceDate(detail.getInvoiceDate());
				
				//订单类型：0-正常 1-换货 员购生成发票回写发票号到pos 时，需要这个类型
				sum.setOrderType(detail.getOrderBillType());

				orderSummaryList.add(sum);
			}
		}

		Map<String, MemberOrderSummary> listMap = new HashMap<String, MemberOrderSummary>();
		for (MemberOrderSummary memberOrderSummary : orderSummaryList) {
			listMap.put(memberOrderSummary.getBillNo(), memberOrderSummary);
		}
		orderSummaryList.clear();
		orderSummaryList.addAll(listMap.values());
		
		if(CollectionUtils.isNotEmpty(orderSummaryList)){
			for (int i = 0; i < orderSummaryList.size(); i++) {
				if(StringUtils.isNotEmpty(orderSummaryList.get(i).getInvoiceNo())){
					BillCommonInvoiceRegister dtl = new BillCommonInvoiceRegister();
					dtl.setBillNo(orderSummaryList.get(i).getInvoiceNo());
					dtl = billCommonInvoiceRegisterManager.findById(dtl);
					if(null != dtl){
						orderSummaryList.get(i).setInvoiceDate(dtl.getBillDate());
					}
				}
			}
		}
		
		int total = orderSummaryList.size();
		List<MemberOrderSummary> showOrderSummaryList = new ArrayList<MemberOrderSummary>();
		if(null != orderSummaryList && orderSummaryList.size() > 0){
			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
				showOrderSummaryList.add(orderSummaryList.get(i));
			}
		}
		
		
		for (int i = 0; i < showOrderSummaryList.size(); i++) {
			OcOrderTicketDto dto = new OcOrderTicketDto();
			dto.setOrderNo(showOrderSummaryList.get(i).getBillNo());
//			dto.setTicketFlag(TICKETFLAG);
//			dto.setTicketType(TICKETTYPE);
			
			List<OcOrderTicketDto> ticketAmount = new ArrayList<OcOrderTicketDto>();
			try {
				ticketAmount = orderTicketApi.findGroupTickeAmount(dto);
			} catch (RpcException e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
			
			if(null != ticketAmount && ticketAmount.size() > 0){
				if(null != ticketAmount.get(0).getAvailableAmount()){
					showOrderSummaryList.get(i).setCouponAmount(ticketAmount.get(0).getAvailableAmount());
					showOrderSummaryList.get(i).setCashAmount((showOrderSummaryList.get(i).getAllAmount() == null ? 
							new BigDecimal(0) : showOrderSummaryList.get(i).getAllAmount()).subtract(showOrderSummaryList.get(i).getCouponAmount()));
				}
			}
		}
		
		MemberOrderSummary dtl = new MemberOrderSummary();
		List<MemberOrderSummary> footer = new ArrayList<MemberOrderSummary>();
   		BigDecimal allAmount = new BigDecimal(0);
   		int totalQty = 0;
   		
   		if(CollectionUtils.isNotEmpty(showOrderSummaryList)){
   			for (MemberOrderSummary mos : showOrderSummaryList) {
   				allAmount = allAmount.add(mos.getAllAmount());
   				totalQty += mos.getTotalQty();
   				
			}
   			dtl.setBillNo("合计");
   			dtl.setTotalQty(totalQty);
   			dtl.setAllAmount(allAmount);
   			footer.add(dtl);
   		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", showOrderSummaryList);
		obj.put("footer", footer);
		return obj;
	}

	/**
	 * 根据接口查询订单明细数据
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	private Map<String, List<OrderDtlDto>> getOrderDtlList(Map<String, Object> params) throws ManagerException {
		Map<String, List<OrderDtlDto>> orderSummaryMap = new HashMap<String, List<OrderDtlDto>>();
		orderSummaryExportList = new ArrayList<OrderMainExport>();

		List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = getPosDate(params);
		
		if(null != ocOrderDtlList && ocOrderDtlList.size() > 0){
			for (int i = 0; i < ocOrderDtlList.size(); i++) {
				POSOrderAndReturnExMainDtlDto detail = ocOrderDtlList.get(i);
				if (!(i != 0 && StringUtils.equals(detail.getOrderNo(), ocOrderDtlList.get(i - 1).getOrderNo()))) {
					List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
					orderSummaryMap.put(detail.getOrderNo(), dtlList);
				}

				OrderDtlDto dtl = new OrderDtlDto();
				copyProperties(dtl, detail, MEMBER_ORDER_TYPE);
				orderSummaryMap.get(detail.getOrderNo()).add(dtl);
			}
		}

		return orderSummaryMap;
	}

	@RequestMapping("/order_query")
	@ResponseBody
	public Map<String, Object> queryByOrderNo(@RequestParam("orderNo") String orderNo,
			@RequestParam("companyNo") String companyNo, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", companyNo);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("businessType", BUSINESSTYPE);
		//Map<String, List<OrderDtlDto>> orderSummaryMap = getOrderDtlList(params);
		
		List<OrderDtlDto> list = new ArrayList<OrderDtlDto>();
		
		List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = getPosDate(params);
		
		if(CollectionUtils.isNotEmpty(ocOrderDtlList)){
			for (POSOrderAndReturnExMainDtlDto detail : ocOrderDtlList) {
				if(detail.getOrderNo().equals(orderNo)){
					OrderDtlDto dto = new OrderDtlDto();
					dto.setItemCode(detail.getItemCode());
					dto.setItemName(detail.getItemName());
					dto.setQty(detail.getDtlQty());
					dto.setTagPrice(detail.getTagPrice());
					dto.setSalePrice(detail.getSalePrice());
					dto.setSettlePrice(detail.getSettlePrice());
					dto.setReducePrice(detail.getReducePrice());
					dto.setAmount(detail.getDtlQty() == null ? new BigDecimal(0) : new BigDecimal(detail.getDtlQty()).multiply(detail.getTagPrice()));
					list.add(dto);
				}

			}
		}
		
/*		if(null != orderSummaryMap && orderSummaryMap.size() > 0){
			list = orderSummaryMap.get(orderNo);
		}*/

		int total = list.size();
		List<OrderDtlDto> showList = new ArrayList<OrderDtlDto>();
		if(null != list && list.size() > 0){
			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
				showList.add(list.get(i));
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", showList);
		return obj;
	}
	
	public MemberOrderSummary setValue(POSOrderAndReturnExMainDtlDto oc){
		MemberOrderSummary sum = new MemberOrderSummary();
		sum.setShopNo(oc.getShopNo());
		sum.setShopName(oc.getShopName());
		BigDecimal tagPrice = new BigDecimal(0);
		BigDecimal salePrice = new BigDecimal(0);
		BigDecimal settlePrice = new BigDecimal(0);
		BigDecimal reducePrice = new BigDecimal(0);
		BigDecimal invoicePrice = new BigDecimal(0);
		BigDecimal allAmountPrice = new BigDecimal(0);
		int qty = 0;
		String invoicenos = "";
		invoicenos = invoicenos
				+ (StringUtils.isEmpty(oc.getInvoiceNo()) ? "" : ("," + oc.getInvoiceNo()));
		tagPrice = oc.getTagPriceAmount();
		salePrice = oc.getSalePriceAmount();
		settlePrice = oc.getSettleAmount();
		reducePrice = oc.getReduceAmount();
		allAmountPrice = oc.getAllAmount();
		qty = oc.getQty();
		sum.setTotalQty(qty);
		sum.setTagPriceAmount(tagPrice);
		sum.setSalePriceAmount(salePrice);
		sum.setReducePriceAmount(reducePrice);
		sum.setSettleAmount(settlePrice);
		sum.setAllAmount(allAmountPrice);
		if(invoicenos.length() > 0){
			sum.setInvoiceNo(invoicenos.substring(1, invoicenos.length()));
		}
		sum.setInvoiceAmount(invoicePrice);
		
		return sum;
	}

	/**
	 * 根据接口查询出所有的店铺汇总列表
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	private List<MemberOrderSummary> getShopList(Map<String, Object> params) throws ManagerException {
		List<MemberOrderSummary> shopSummaryList = new ArrayList<MemberOrderSummary>();
		Map<String, List<OrderDtlDto>> shopSummaryMap = new HashMap<String, List<OrderDtlDto>>();
		shopSummaryExportList = new ArrayList<OrderMainExport>();

		List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = getPosDate(params);
		
		if (null == ocOrderDtlList || ocOrderDtlList.size() == 0) {
			return shopSummaryList;
		}
		
		Map<String, POSOrderAndReturnExMainDtlDto> listMap = new HashMap<String, POSOrderAndReturnExMainDtlDto>();
		for (POSOrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : ocOrderDtlList) {
			listMap.put(orderAndReturnExMainDtlDto.getOrderNo(), orderAndReturnExMainDtlDto);
		}
		ocOrderDtlList.clear();
		ocOrderDtlList.addAll(listMap.values());
		
		Map<String, MemberOrderSummary> map = new HashMap<String, MemberOrderSummary>();
		for(POSOrderAndReturnExMainDtlDto oc : ocOrderDtlList) {
			if(map.containsKey(oc.getShopNo())) {
				MemberOrderSummary sm = map.get(oc.getShopNo());
				BigDecimal tagPrice = new BigDecimal(0);
				BigDecimal salePrice = new BigDecimal(0);
				BigDecimal settlePrice = new BigDecimal(0);
				BigDecimal reducePrice = new BigDecimal(0);
				BigDecimal allAmountPrice = new BigDecimal(0);
				int qty = 0;
				String invoicenos = "";
				invoicenos = invoicenos
						+ (StringUtils.isEmpty(oc.getInvoiceNo()) ? "" : ("," + oc.getInvoiceNo()));
				//invoicenos += StringUtils.isEmpty(dto.getInvoiceNo()) ? "" : dto.getInvoiceNo()+",";
				tagPrice = BigDecimalUtil.add(sm.getTagPriceAmount(), oc.getTagPriceAmount());
				salePrice = BigDecimalUtil.add(sm.getSalePriceAmount(), oc.getSalePriceAmount());
				settlePrice = BigDecimalUtil.add(sm.getSettleAmount(), oc.getSettleAmount());
				reducePrice = BigDecimalUtil.add(sm.getReducePriceAmount(), oc.getReduceAmount());
				allAmountPrice = BigDecimalUtil.add(sm.getAllAmount(), oc.getAllAmount());
				qty = sm.getTotalQty()+oc.getQty();
/*				tagPrice = sm.getTagPriceAmount() == null ? new BigDecimal(0) : sm.getTagPriceAmount().add((oc.getTagPrice() == null ? new BigDecimal(0) : oc.getTagPrice().multiply(oc.getQty() == null ? new BigDecimal(0) : new BigDecimal(oc.getQty()))));
				salePrice = sm.getSalePriceAmount() == null ? new BigDecimal(0) : sm.getSalePriceAmount().add(oc.getSalePrice() == null ? new BigDecimal(0) : oc.getSalePrice().multiply(oc.getQty() == null ? new BigDecimal(0) : new BigDecimal(oc.getQty())));
				settlePrice = sm.getSettleAmount() == null ? new BigDecimal(0) : sm.getSettleAmount().add(oc.getSettlePrice() == null ? new BigDecimal(0) : oc.getSettlePrice().multiply(oc.getQty() == null ? new BigDecimal(0) : new BigDecimal(oc.getQty())));
				reducePrice = sm.getReducePriceAmount() == null ? new BigDecimal(0) : sm.getReducePriceAmount().add(oc.getReducePrice() == null ? new BigDecimal(0) : oc.getReducePrice());
				allAmountPrice = sm.getAllAmount() == null ? new BigDecimal(0) : sm.getAllAmount().add(oc.getAmount() == null ? new BigDecimal(0) : oc.getAmount());
				qty = sm.getTotalQty() == null ? 0 : sm.getTotalQty() + oc.getQty();*/
				sm.setTotalQty(qty);
				sm.setTagPriceAmount(tagPrice);
				sm.setSalePriceAmount(salePrice);
				sm.setReducePriceAmount(reducePrice);
				sm.setSettleAmount(settlePrice);
				sm.setAllAmount(allAmountPrice);
				if(invoicenos.length() > 0){
					sm.setInvoiceNo(invoicenos.substring(1, invoicenos.length()));
				}
				sm.setShopNo(oc.getShopNo());
				sm.setShopName(oc.getShopName());
				map.put(oc.getShopNo(), sm);
			} else {
				map.put(oc.getShopNo(), setValue(oc));
			}
		}
		
		shopSummaryList.addAll(map.values());
		/*Map<String, OrderAndReturnExMainDtlDto> listMap = new HashMap<String, OrderAndReturnExMainDtlDto>();
		Map<String, OrderAndReturnExMainDtlDto> paramsMap = new HashMap<String, OrderAndReturnExMainDtlDto>();
		for (OrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : ocOrderDtlList) {
			listMap.put(orderAndReturnExMainDtlDto.getOrderNo(), orderAndReturnExMainDtlDto);
			paramsMap.put(orderAndReturnExMainDtlDto.getShopNo(), orderAndReturnExMainDtlDto);
		}
		ocOrderDtlList.clear();
		ocOrderDtlList.addAll(listMap.values());
		
		for (OrderAndReturnExMainDtlDto oc : ocOrderDtlList) {
			List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
			if(paramsMap.containsKey(oc.getShopNo())){
				OrderDtlDto dtl = new OrderDtlDto();
				copyProperties(dtl, oc, MEMBER_SHOP_TYPE);
				shopSummaryMap.put(oc.getShopNo(), dtlList);
			}else{
				OrderDtlDto dtl = new OrderDtlDto();
				copyProperties(dtl, oc, MEMBER_SHOP_TYPE);
				shopSummaryMap.put(oc.getShopNo(), dtlList);
			}
		}*/


/*		for (int i = 0; i < ocOrderDtlList.size(); i++) {
			OrderAndReturnExMainDtlDto detail = ocOrderDtlList.get(i);
			if (!(i != 0 && StringUtils.equals(detail.getShopNo(), ocOrderDtlList.get(i - 1).getShopNo()))) {
				List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
				shopSummaryMap.put(detail.getShopNo(), dtlList);
			}

			OrderDtlDto dtl = new OrderDtlDto();
			copyProperties(dtl, detail, MEMBER_SHOP_TYPE);
			shopSummaryMap.get(detail.getShopNo()).add(dtl);
		}*/

/*		for (int i = 0; i < ocOrderDtlList.size(); i++) {
			OrderAndReturnExMainDtlDto detail = ocOrderDtlList.get(i);
			if (i != 0 && StringUtils.equals(detail.getShopNo(), ocOrderDtlList.get(i - 1).getShopNo())) {
				continue;
			}

			MemberOrderSummary sum = new MemberOrderSummary();
			sum.setShopNo(detail.getShopNo());
			sum.setShopName(detail.getShopName());
			List<OrderDtlDto> dtlList = shopSummaryMap.get(detail.getShopNo());
			BigDecimal tagPrice = new BigDecimal(0);
			BigDecimal salePrice = new BigDecimal(0);
			BigDecimal discPrice = new BigDecimal(0);
			BigDecimal settlePrice = new BigDecimal(0);
			BigDecimal reducePrice = new BigDecimal(0);
			BigDecimal invoicePrice = new BigDecimal(0);
			BigDecimal allAmountPrice = new BigDecimal(0);
			BigDecimal couponAmount = new BigDecimal(0);
			int qty = 0;
			String invoicenos = "";
			int isConfirm = 0;
			for (int j = 0; j < dtlList.size(); j++) {
				OrderDtlDto dto = dtlList.get(j);
				//现金券，发票号，发票金额都属于订单，要将所有订单的数据相加才可以，不能将明细的数据相加
				if (!(j != 0 && StringUtils.equals(dto.getOrderNo(), dtlList.get(j - 1).getOrderNo()))) {
					couponAmount = couponAmount.add(dto.getCouponAmount() == null ? new BigDecimal(0) : dto
							.getCouponAmount());
					invoicePrice = invoicePrice.add(dto.getInvoiceAmount() == null ? new BigDecimal(0) : dto
							.getInvoiceAmount());
					invoicenos = invoicenos
							+ (StringUtils.isEmpty(dto.getInvoiceNo()) ? "" : ("," + dto.getInvoiceNo()));
					invoicenos += StringUtils.isEmpty(dto.getInvoiceNo()) ? "" : dto.getInvoiceNo()+",";
				}
				tagPrice = tagPrice.add(dto.getTagPrice() == null ? new BigDecimal(0) : dto.getTagPrice().multiply(dto.getQty() == null ? new BigDecimal(0) : new BigDecimal(dto.getQty())));
				salePrice = salePrice.add(dto.getSalePrice() == null ? new BigDecimal(0) : dto.getSalePrice().multiply(dto.getQty() == null ? new BigDecimal(0) : new BigDecimal(dto.getQty())));
				discPrice = discPrice.add(dto.getDiscPrice() == null ? new BigDecimal(0) : dto.getDiscPrice());
				settlePrice = settlePrice.add(dto.getSettlePrice() == null ? new BigDecimal(0) : dto.getSettlePrice().multiply(dto.getQty() == null ? new BigDecimal(0) : new BigDecimal(dto.getQty())));
				reducePrice = reducePrice.add(dto.getReducePrice() == null ? new BigDecimal(0) : dto.getReducePrice());
				allAmountPrice = allAmountPrice.add(dto.getAmount() == null ? new BigDecimal(0) : dto.getAmount());
				qty = qty + dto.getQty();
				isConfirm = isConfirm + (dto.getFinanceConfirmFlag() == null ? 0 : dto.getFinanceConfirmFlag());
			}
			sum.setTotalQty(qty);
			sum.setTagPriceAmount(tagPrice);
			sum.setSalePriceAmount(salePrice);
			sum.setReducePriceAmount(reducePrice);
			sum.setSettleAmount(settlePrice);
			sum.setCouponAmount(couponAmount);
			sum.setAllAmount(allAmountPrice);
			sum.setCashAmount(allAmountPrice.subtract(couponAmount));
			if(invoicenos.length() > 0){
				sum.setInvoiceNo(invoicenos.substring(0, invoicenos.length()-1));
			}
			sum.setInvoiceAmount(invoicePrice);
			//汇总里面有财务确认消息
			sum.setFinanceConfirmFlag(isConfirm == dtlList.size() ? 1 : isConfirm);
			shopSummaryList.add(sum);

			//将汇总信息保存到导出列表 
			OrderMainExport export = new OrderMainExport();
			export.setShopNo(detail.getShopNo());
			export.setShopName(detail.getShopName());
			export.setTotalQty(qty);
			export.setTagPriceAmount(tagPrice);
			export.setSalePriceAmount(salePrice);
			export.setReducePriceAmount(reducePrice);
			export.setSettleAmount(settlePrice);
			export.setAllAmount(allAmountPrice);
			export.setCouponAmount(couponAmount);
			export.setCashAmount(allAmountPrice.subtract(couponAmount));
			export.setInvoiceNo(invoicenos);
			export.setInvoiceAmount(invoicePrice);
			//汇总里面有财务确认消息
			export.setFinanceConfirmFlag(isConfirm == dtlList.size() ? 1 : isConfirm);
			shopSummaryExportList.add(export);
		}*/

		return shopSummaryList;
	}

	/**
	 * 查询汇总时，按店铺编号进行汇总
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/shop_member_list.json")
	@ResponseBody
	public Map<String, Object> queryShopMemberList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
/*		String balanceStartDate = StringUtils.isEmpty(req.getParameter("startDate")) ? "" : req
				.getParameter("startDate");
		String balanceEndDate = StringUtils.isEmpty(req.getParameter("endDate")) ? "" : req.getParameter("endDate");
		String companyNo = StringUtils.isEmpty(req.getParameter("companyNo")) ? "" : req.getParameter("companyNo");*/

		//根据店铺编号进行汇总（查看汇总时）
		Map<String, Object> params = builderParams(req, model);
		List<MemberOrderSummary> shopSummaryList = getShopList(params);
		
		List<MemberOrderSummary> orderSummaryList = new ArrayList<MemberOrderSummary>();

		int total = shopSummaryList.size();
		List<MemberOrderSummary> showShopSummaryList = new ArrayList<MemberOrderSummary>();
		if(null != shopSummaryList && shopSummaryList.size() > 0){
			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
				showShopSummaryList.add(shopSummaryList.get(i));
			}
		}
		
		List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = getPosDate(params);

		if(null != ocOrderDtlList && ocOrderDtlList.size() > 0){
/*			for (int i = 0; i < ocOrderDtlList.size(); i++) {
				OrderAndReturnExMainDtlDto detail = ocOrderDtlList.get(i);
				if (i != 0 && StringUtils.equals(detail.getOrderNo(), ocOrderDtlList.get(i - 1).getOrderNo())) {
					continue;
				}

				MemberOrderSummary sum = new MemberOrderSummary();
				sum.setShopNo(detail.getShopNo());
				sum.setBillNo(detail.getOrderNo());
				orderSummaryList.add(sum);
			}*/
			Map<String, POSOrderAndReturnExMainDtlDto> listMap = new HashMap<String, POSOrderAndReturnExMainDtlDto>();
			for (POSOrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : ocOrderDtlList) {
				listMap.put(orderAndReturnExMainDtlDto.getOrderNo(), orderAndReturnExMainDtlDto);
			}
			ocOrderDtlList.clear();
			ocOrderDtlList.addAll(listMap.values());
			for (int i = 0; i < ocOrderDtlList.size(); i++) {
				MemberOrderSummary sum = new MemberOrderSummary();
				sum.setShopNo(ocOrderDtlList.get(i).getShopNo());
				sum.setBillNo(ocOrderDtlList.get(i).getOrderNo());
				orderSummaryList.add(sum);
			}
		}
		
		if(null != orderSummaryList && orderSummaryList.size() > 0 && null != showShopSummaryList && showShopSummaryList.size() > 0){
			for (int i = 0; i < showShopSummaryList.size(); i++) {
				BigDecimal cAmount = new BigDecimal(0);   //现金券金额
				for (int j = 0; j < orderSummaryList.size(); j++) {
					if(showShopSummaryList.get(i).getShopNo().equals(orderSummaryList.get(j).getShopNo())){
						OcOrderTicketDto dto = new OcOrderTicketDto();
						dto.setOrderNo(orderSummaryList.get(j).getBillNo());
//						dto.setTicketFlag(TICKETFLAG);
//						dto.setTicketType(TICKETTYPE);
						
						List<OcOrderTicketDto> ticketAmount = new ArrayList<OcOrderTicketDto>();
						try {
							ticketAmount = orderTicketApi.findGroupTickeAmount(dto);
						} catch (RpcException e) {
							logger.error(e.getMessage(), e);
							throw new ManagerException(e.getMessage(), e);
						}
						
						if(null != ticketAmount && ticketAmount.size() > 0){
							cAmount=cAmount.add(ticketAmount.get(0).getAvailableAmount());
						}
					}
				}
				showShopSummaryList.get(i).setCouponAmount(cAmount);
				if(null != showShopSummaryList.get(i).getCouponAmount()){
					showShopSummaryList.get(i).setCashAmount((showShopSummaryList.get(i).getAllAmount() == null ? 
							new BigDecimal(0) : showShopSummaryList.get(i).getAllAmount()).subtract(showShopSummaryList.get(i).getCouponAmount()));
				}
			}
		}
		
		//获取发票金额
		if(CollectionUtils.isNotEmpty(showShopSummaryList)){
			for (int i = 0; i < showShopSummaryList.size(); i++) {
				if(StringUtils.isNotEmpty(showShopSummaryList.get(i).getInvoiceNo())){
					String[] invoiceNos = showShopSummaryList.get(i).getInvoiceNo().split(",");
					
					if(null != invoiceNos){
						BigDecimal invoiceAmount = new BigDecimal(0);
						for (String string : invoiceNos) {
							BillCommonInvoiceRegister billCommonInvoiceRegister = new BillCommonInvoiceRegister();
							billCommonInvoiceRegister.setBillNo(string);
							billCommonInvoiceRegister = billCommonInvoiceRegisterManager.findById(billCommonInvoiceRegister);
							
							if(null != billCommonInvoiceRegister){
								invoiceAmount = invoiceAmount.add(billCommonInvoiceRegister.getAmount() == null ? new BigDecimal(0) : billCommonInvoiceRegister.getAmount());
							}
						}
						showShopSummaryList.get(i).setInvoiceAmount(invoiceAmount);
					}
				}
			}
		}
		
		
		MemberOrderSummary dtl = new MemberOrderSummary();
		List<MemberOrderSummary> footer = new ArrayList<MemberOrderSummary>();
   		BigDecimal allAmount = new BigDecimal(0);
   		int totalQty = 0;
   		
   		if(CollectionUtils.isNotEmpty(showShopSummaryList)){
   			for (MemberOrderSummary mos : showShopSummaryList) {
   				allAmount = allAmount.add(mos.getAllAmount());
   				totalQty += mos.getTotalQty();
   				
			}
   			dtl.setBillNo("合计");
   			dtl.setTotalQty(totalQty);
   			dtl.setAllAmount(allAmount);
   			footer.add(dtl);
   		}
		/*if(null != showShopSummaryList && showShopSummaryList.size() > 0){
			for (int i = 0; i < showShopSummaryList.size(); i++) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("outStartDate", balanceStartDate);
				paramsMap.put("outEndDate", balanceEndDate);
				paramsMap.put("companyNo", companyNo);
				paramsMap.put("shop_no", showShopSummaryList.get(i).getShopNo());
				List<MemberOrderDtl> dtlList = memberOrderDtlManager.findByBiz(null, paramsMap);
				
				if(null != dtlList && dtlList.size()>0){
					showShopSummaryList.get(i).setOrderId(dtlList.get(0).getId());
					showShopSummaryList.get(i).setInvoiceNo(dtlList.get(0).getInvoiceNo());
					showShopSummaryList.get(i).setInvoiceAmount(dtlList.get(0).getInvoiceAmount());
					showShopSummaryList.get(i).setFinanceConfirmFlag(dtlList.get(0).getStatus().intValue());
				}
			}
		}*/

		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", showShopSummaryList);
		obj.put("footer", footer);
		return obj;
	}

	private void copyProperties(OrderDtlDto dtl, POSOrderAndReturnExMainDtlDto detail, int type) {
		dtl.setOrderNo(detail.getOrderNo());
		dtl.setShopName(detail.getShopName());
		dtl.setShopNo(detail.getShopNo());
		dtl.setItemCode(detail.getItemCode());
		dtl.setItemName(detail.getItemName());
		dtl.setTagPrice(detail.getTagPrice());
		dtl.setSalePrice(detail.getSalePrice());
		dtl.setSettlePrice(detail.getSettlePrice());
		dtl.setReducePrice(detail.getReducePrice());
		dtl.setQty(detail.getDtlQty());
		dtl.setAmount(detail.getSettlePrice().multiply(detail.getDtlQty() == null ? new BigDecimal(0) : new BigDecimal(detail.getDtlQty())));
		dtl.setDiscount(detail.getDiscount());
		dtl.setFinanceConfirmFlag(detail.getMonthlyFlag());
		dtl.setInvoiceNo(detail.getInvoiceNo());
		dtl.setInvoiceDate(detail.getInvoiceDate());
		dtl.setInvoiceAmount(detail.getInvoiceAmount());
//		dtl.setCouponAmount(detail.getCouponAmount());

		//员购明细时，将要导出的列数据存入导出列表
		if (MEMBER_ORDER_TYPE == type) {
			OrderMainExport export = new OrderMainExport();
			export.setBillNo(detail.getOrderNo());
			export.setShopNo(detail.getShopNo());
			export.setShopName(detail.getShopName());
			export.setOutDate(detail.getOutDate());
			export.setTotalQty(detail.getQty());
			export.setTagPriceAmount(detail.getTagPriceAmount());
			export.setSalePriceAmount(detail.getSalePriceAmount());
			export.setReducePriceAmount(detail.getReduceAmount());
			export.setSettleAmount(detail.getSettleAmount());
			export.setAllAmount(detail.getAllAmount());
			export.setCouponAmount(detail.getCouponAmount());
			export.setCashAmount((detail.getAllAmount() == null ? new BigDecimal(0) : detail.getAllAmount())
					.subtract(detail.getCouponAmount() == null ? new BigDecimal(0) : detail.getCouponAmount()));
			export.setItemCode(detail.getItemCode());
			export.setItemName(detail.getItemName());
			export.setQty(detail.getDtlQty());
			export.setTagPrice(detail.getTagPrice());
			export.setSalePrice(detail.getSalePrice());
			export.setSettlePrice(detail.getSettlePrice());
			export.setReducePrice(detail.getReducePrice());
			export.setAmount(detail.getSettlePrice().multiply(detail.getDtlQty() == null ? new BigDecimal(0) : new BigDecimal(detail.getDtlQty())));
			orderSummaryExportList.add(export);
		}
	}

	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.orderDtlManager.selectOrderSummaryCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<MemberOrderSummary> list = this.orderDtlManager.selectOrderSummaryByPage(page, sortColumn, sortOrder,
				params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	@RequestMapping("/operate_list")
	public String operateList() {
		return "member_order_summary/operate_list";
	}

	@RequestMapping(value = "/operate_list.json")
	@ResponseBody
	public Map<String, Object> queryOperateList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.orderDtlManager.selectOrderSummaryCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<MemberOrderSummary> list = this.orderDtlManager.selectOrderSummaryOperateByPage(page, sortColumn,
				sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

//	@RequestMapping(value = "/finance_confirm")
//	@ResponseBody
//	public Boolean financeConfirm(HttpServletRequest req, Model model) throws ManagerException {
//		try {
//			String dataList = StringUtils.isEmpty(req.getParameter("datas")) ? "" : req.getParameter("datas");
//			JsonUtil<MemberOrderSummary> util = new JsonUtil<MemberOrderSummary>();
//			List<MemberOrderSummary> list = util.convertListWithTypeReference(dataList, req, MemberOrderSummary.class);
//			orderDtlManager.financeConfirm(list);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	@RequestMapping(value = "/finance_anti_confirm")
//	@ResponseBody
//	public Boolean financeAntiConfirm(HttpServletRequest req, Model model) throws ManagerException {
//		try {
//			String dataList = StringUtils.isEmpty(req.getParameter("datas")) ? "" : req.getParameter("datas");
//			JsonUtil<MemberOrderSummary> util = new JsonUtil<MemberOrderSummary>();
//			List<MemberOrderSummary> list = util.convertListWithTypeReference(dataList, req, MemberOrderSummary.class);
//			orderDtlManager.financeAntiConfirm(list);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	/**
	 * 导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/do_fas_export_member")
	public void doFasExport(OrderMainExport modelType, HttpServletRequest request, Model model,
			HttpServletResponse response) throws ManagerException {
		Map<String, Object> params = builderParams(request, model);
		String exportColumns = (String) params.get("exportColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String) params.get("exportType");
		String type = request.getParameter("type");
		String businessType = request.getParameter("businessType");
		String companyNo = request.getParameter("companyNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		
		orderSummaryExportList = new ArrayList<OrderMainExport>();
		shopSummaryExportList = new ArrayList<OrderMainExport>();
		if (StringUtils.isNotEmpty(exportColumns)) {
			try {
				//字段名列表
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
				});

				List<Map> subColumnsList = mapper.readValue(subExportColumns, new TypeReference<List<Map>>() {
				});
				//如果是混合表头，则将subColumnsList加入columnsList集合
				if (ExportTypeEnum.FIX_HEADER.getType().equalsIgnoreCase(exportType)) {
					columnsList.addAll(subColumnsList);
					subColumnsList = new ArrayList<Map>(1);
				}
				columnsList = this.sortExportColumns(columnsList);
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(columnsList);
				exportVo.setSubColumnsMapList(subColumnsList);

				List<OrderMainExport> list = new ArrayList<OrderMainExport>();
				Map<String, Object> paramss = new HashMap<String, Object>();
				paramss.put("companyNo", companyNo);
				paramss.put("startDate", startDate);
				paramss.put("endDate", endDate);
				paramss.put("businessType", businessType);

				if (StringUtils.endsWithIgnoreCase(type, "1")) {
					//导出明细
					
					List<OrderMainExport> ome = new ArrayList<OrderMainExport>();
					List<POSOrderAndReturnExMainDtlDto> ocOrderDtlList = getPosDate(paramss);
					
					if(CollectionUtils.isNotEmpty(ocOrderDtlList)){
						
						for (POSOrderAndReturnExMainDtlDto detail : ocOrderDtlList) {
							
							OcOrderTicketDto dto = new OcOrderTicketDto();
							dto.setOrderNo(detail.getOrderNo());
//							dto.setTicketFlag(TICKETFLAG);
//							dto.setTicketType(TICKETTYPE);
							
							List<OcOrderTicketDto> ticketAmount = new ArrayList<OcOrderTicketDto>();
							try {
								ticketAmount = orderTicketApi.findGroupTickeAmount(dto);
							} catch (RpcException e) {
								logger.error(e.getMessage(), e);
								throw new ManagerException(e.getMessage(), e);
							}
							
							if(null != ticketAmount && ticketAmount.size() > 0){
								if(null != ticketAmount.get(0).getAvailableAmount()){
									detail.setCouponAmount(ticketAmount.get(0).getAvailableAmount());
								}
							}
							
							OrderMainExport export = new OrderMainExport();
							export.setInvoiceNo(detail.getInvoiceNo());
							if(StringUtils.isNotEmpty(detail.getInvoiceNo())){
								BillCommonInvoiceRegister dtl = new BillCommonInvoiceRegister();
								dtl.setBillNo(detail.getInvoiceNo());
								dtl = billCommonInvoiceRegisterManager.findById(dtl);
								if(null != dtl){
									export.setInvoiceDate(dtl.getBillDate());
								}
							}
							
							if(StringUtils.isNotEmpty(detail.getItemCode())){
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("code", detail.getItemCode());
								Category category = categoryManager.getCategoryByItemCode(map);
								
								if(null != category){
									export.setCategoryName(category.getName());
								}
							}
							
							if(StringUtils.isNotEmpty(detail.getShopNo())){
								Map<String, Object> map1 = new HashMap<String, Object>();
								map1.put("shopNo", detail.getShopNo());
								Organ organ = organManager.getOrganByShopNo(map1);
								
								if(null != organ){
									export.setOrganName(organ.getName());
								}
							}
							export.setBrandName(detail.getBrandName());
							export.setBillNo(detail.getOrderNo());
							export.setShopNo(detail.getShopNo());
							export.setShopName(detail.getShopName());
							export.setOutDate(detail.getOutDate());
							export.setTotalQty(detail.getQty());
							export.setTagPriceAmount(detail.getTagPriceAmount());
							export.setSalePriceAmount(detail.getSalePriceAmount());
							export.setReducePriceAmount(detail.getReduceAmount());
							export.setSettleAmount(detail.getSettleAmount());
							export.setAllAmount(detail.getAllAmount());
							export.setCouponAmount(detail.getCouponAmount());
							export.setCashAmount((detail.getAllAmount() == null ? new BigDecimal(0) : detail.getAllAmount())
									.subtract(detail.getCouponAmount() == null ? new BigDecimal(0) : detail.getCouponAmount()));
							export.setItemCode(detail.getItemCode());
							export.setItemName(detail.getItemName());
							export.setQty(detail.getDtlQty());
							export.setTagPrice(detail.getTagPrice());
							export.setSalePrice(detail.getSalePrice());
							export.setSettlePrice(detail.getSettlePrice());
							export.setReducePrice(detail.getReducePrice());
							export.setAmount(detail.getSettlePrice().multiply(detail.getDtlQty() == null ? new BigDecimal(0) : new BigDecimal(detail.getDtlQty())));
							orderSummaryExportList.add(export);
						}
					}

					//getOrderDtlList(paramss);
					list = this.orderSummaryExportList;
				} else {
					Map<String, Object> map = queryShopMemberList(request, model);
					
					
					Set<String> set = map.keySet();

					Object obj = null;
					
					List<MemberOrderSummary> orderSummaryList = new ArrayList<MemberOrderSummary>();

					for (String string : set) {
						obj = map.get(string);
					}

					orderSummaryList = (List<MemberOrderSummary>) obj;
					
					if(CollectionUtils.isNotEmpty(orderSummaryList)){
						for (int i = 0; i < orderSummaryList.size(); i++) {
							OrderMainExport export = new OrderMainExport();
							export.setShopNo(orderSummaryList.get(i).getShopNo());
							export.setShopName(orderSummaryList.get(i).getShopName());
							export.setTotalQty(orderSummaryList.get(i).getTotalQty());
							export.setTagPriceAmount(orderSummaryList.get(i).getTagPriceAmount());
							export.setSalePriceAmount(orderSummaryList.get(i).getSalePriceAmount());
							export.setReducePriceAmount(orderSummaryList.get(i).getReducePriceAmount());
							export.setSettleAmount(orderSummaryList.get(i).getSettleAmount());
							export.setAllAmount(orderSummaryList.get(i).getAllAmount());
							export.setCouponAmount(orderSummaryList.get(i).getCouponAmount());
							export.setCashAmount(orderSummaryList.get(i).getCashAmount());
							export.setInvoiceNo(orderSummaryList.get(i).getInvoiceNo());
							export.setInvoiceAmount(orderSummaryList.get(i).getInvoiceAmount());
							shopSummaryExportList.add(export);
						}
					}
					//getShopList(paramss);
					list = this.shopSummaryExportList;
				}

				List<Map> listArrayList = new ArrayList<Map>();
				if (list != null && list.size() > 0) {
					List<String> fields = new ArrayList<String>();
					for (Map map : columnsList) {
						fields.add(map.get("field").toString());
					}
					boolean flag = true;
					ExportFormat formatAnnotation = null;
					AbstractExportFormat<OrderMainExport> format = null;
					for (OrderMainExport vo : list) {
						Map map = null;
						if (flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if (formatAnnotation != null) {
							format = (AbstractExportFormat<OrderMainExport>) formatAnnotation.className().newInstance();
							map = format.format(fields, vo);
						} else {
							map = new HashMap();
							BeanUtilsCommon.object2MapWithoutNull(vo, map);
						}
						if (subColumnsList != null && subColumnsList.size() > 0) {
							List<Map> subExportData = this.findComplexHeaderData(vo);
							map.put("subExportData", subExportData);
						} else {
							map.put("subExportData", new ArrayList<Map>(1));
						}
						listArrayList.add(map);
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(listArrayList);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportData(exportVo, response);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	}

	/**
	 * 查询符合表头的数据
	 * @param vo 查询参数
	 * @return List<Map>
	 */
	protected List<Map> findComplexHeaderData(OrderMainExport vo) {
		return new ArrayList<Map>();
	}

}