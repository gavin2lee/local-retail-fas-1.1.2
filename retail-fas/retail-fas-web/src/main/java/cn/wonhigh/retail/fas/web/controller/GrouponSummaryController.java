package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.OrderTotalDTO;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.enums.FinanceConfirmFlagEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillPrePaymentNt;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.GroupSaleApplyInvoiceRel;
import cn.wonhigh.retail.fas.common.model.MemberOrderDtl;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;
import cn.wonhigh.retail.fas.common.model.OrderMainExport;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceApplyManager;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceManager;
import cn.wonhigh.retail.fas.manager.BillPrePaymentNtManager;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.GroupSaleApplyInvoiceRelManager;
import cn.wonhigh.retail.fas.manager.MemberOrderDtlManager;
import cn.wonhigh.retail.fas.manager.OrderDtlManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.mps.api.client.dto.coupon.Ticket2Dto;
import cn.wonhigh.retail.mps.api.client.dto.coupon.TicketCollectDtlDto;
import cn.wonhigh.retail.mps.api.client.dto.coupon.TicketCollectDto;
import cn.wonhigh.retail.mps.api.client.dto.coupon.TicketCollectParamDto;
import cn.wonhigh.retail.mps.api.client.dto.pageable.PaginationDto;
import cn.wonhigh.retail.mps.api.client.service.coupon.TicketApi;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderTicketDto;
import cn.wonhigh.retail.oc.common.api.service.OrderTicketApi;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 地区单位团购-团购销售汇总
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@RequestMapping("/groupon_summary")
@ModuleVerify("30190050")
public class GrouponSummaryController extends BaseCrudController<OrderTotalDTO> {

	protected static final XLogger logger = XLoggerFactory.getXLogger(GrouponSummaryController.class);

	@Resource
	private BillBalanceInvoiceManager billBalanceInvoiceManager;

	@Resource
	private BillSaleBalanceManager billSaleBalanceManager;

	@Resource
	private BillPrePaymentNtManager billPrePaymentNtManager;
	
	@Resource
	private BillBalanceInvoiceApplyManager billBalanceInvoiceApplyManager;
	
//	@Resource
//	private CategoryManager categoryManager;
	
//	@Resource
//	private OrganManager organManager;

	@Resource
	private TicketApi ticketApi;

//	@Resource
//	private OrderMainApi orderMainApi;
	
	@Resource
	private OrderTicketApi orderTicketApi;
	
	@Resource
	private MemberOrderDtlManager memberOrderDtlManager;
	
	@Resource
	private GroupSaleApplyInvoiceRelManager groupSaleApplyInvoiceRelManager;
	
	@Resource
	private OrderDtlManager orderDtlManager;

//	private static final int MEMBER_ORDER_TYPE = 1;
//	private static final int BILLSTATUS= 30;
//	private static final int BILLSTATUS1= 41;
//	private static final int BUSINESSTYPE = 3;
//	private static final int BUSINESSTYPES = 4;
	private static final short TICKETTYPE = 2;
	private static final short TICKETFLAG = 2;
//	private List<OrderMainExport> orderSummaryExportList = null;

	@Override
	public CrudInfo init() {
		return new CrudInfo("groupon_summary/", billBalanceInvoiceManager);
	}

	/**
	 * 按客户进行团购跟踪汇总
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/order_total_lists.json")
	@ResponseBody
	public Map<String, Object> queryOrderTotalLists(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		//团购活动开始时间
		String startDate = StringUtils.isEmpty(req.getParameter("startDate")) ? "" : req.getParameter("startDate");

		//团购活动结束时间
		String endDate = StringUtils.isEmpty(req.getParameter("endDate")) ? "" : req.getParameter("endDate");

		//用券开始时间
		String ticketStartDate = StringUtils.isEmpty(req.getParameter("ticketStartDate")) ? "" : req
				.getParameter("ticketStartDate");

		//用券结束时间
		String ticketEndDate = StringUtils.isEmpty(req.getParameter("endDate")) ? "" : req
				.getParameter("ticketEndDate");
		String companyNo = StringUtils.isEmpty(req.getParameter("companyNo")) ? "" : req.getParameter("companyNo");
		String balanceStartDate = StringUtils.isEmpty(req.getParameter("startDate")) ? "" : req
				.getParameter("startDate");

		//本地获取数据数量
		//int pageSizes = 0;

		//记录数量
		//int total = 0;

		List<OrderTotalDTO> orderTotalDtoList = new ArrayList<OrderTotalDTO>();

		//分页查询时不再调用接口，只是条件查询时才调用 
		//if (pageNo == 1) {

		//组织查询券用的参数
		TicketCollectParamDto ticketCollectParamDto = new TicketCollectParamDto();
		ticketCollectParamDto.setCompanyNo(companyNo);
		String shardingFlag = ShardingUtil.getShardingFlag();
		ticketCollectParamDto.setShardingFlag(shardingFlag);
		try {
			//对页面传递的时间参数进行转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ticketCollectParamDto.setStartTime(sdf.parse(startDate));
			ticketCollectParamDto.setEndTime(sdf.parse(endDate));
			ticketCollectParamDto.setPageNo(1);
			ticketCollectParamDto.setPageSize(999999999);
		} catch (Exception e) {
			logger.error("团购活动时间转换异常:" + e.getMessage());
		}

		PaginationDto<TicketCollectDto> findCollects = null;
		try {

			//获取券信息

			findCollects = ticketApi.findCollects(ticketCollectParamDto);
		} catch (RpcException e) {
			logger.error("获取券信息异常:" + e.getMessage());
		}

		if (null != findCollects) {
			List<TicketCollectDto> ticketCollectDtoList = findCollects.getList();

			//记录数量
			//total = findCollects.getTotalCount();

/*			if (pageSize > findCollects.getList().size()) {
				pageSizes = pageSize - findCollects.getList().size();
			}*/

			if (null != ticketCollectDtoList && ticketCollectDtoList.size() > 0) {
				for (int i = 0; i < ticketCollectDtoList.size(); i++) {
					Short ticketStatus = 2; //券使用标志
					int useTicketNum = 0; //劵使用数量
					int useTicketNums = 0; //累计劵使用数量
					int ticketType = 2; //券类型(团购券)

					OrderTotalDTO orderTotalDto = new OrderTotalDTO();
					orderTotalDto.setCustomerNo(ticketCollectDtoList.get(i).getCustomerNo()); //客户编码
					orderTotalDto.setCustomerName(ticketCollectDtoList.get(i).getCustomerName()); //客户名称
					orderTotalDto.setTicketName(ticketCollectDtoList.get(i).getTicketName()); //券名称
					orderTotalDto.setSellQty(ticketCollectDtoList.get(i).getSellQty()); //购券数量
					orderTotalDto.setAmount(ticketCollectDtoList.get(i).getAmount()); //票面金额
					orderTotalDto.setTotalAmount(ticketCollectDtoList.get(i).getAmount()
							.multiply(ticketCollectDtoList.get(i).getSellQty() == null ? new BigDecimal(0) 
							:new BigDecimal(ticketCollectDtoList.get(i).getSellQty()))); //券总金额
					orderTotalDto.setBuyAmount(ticketCollectDtoList.get(i).getBuyAmount() == null ? new BigDecimal(0) : ticketCollectDtoList.get(i).getBuyAmount()); //实际购买价格
					orderTotalDto.setBuyTotalAmount(ticketCollectDtoList.get(i).getBuyAmount() == null ? new BigDecimal(0) : ticketCollectDtoList.get(i).getBuyAmount()
							.multiply(ticketCollectDtoList.get(i).getSellQty() == null ? new BigDecimal(0) :
							new BigDecimal(ticketCollectDtoList.get(i).getSellQty()))); //实收金额

					//通过发票号获取券的发票登记的开票金额
/*					BillCommonRegisterDtl billCommonRegisterDtl = new BillCommonRegisterDtl();
					billCommonRegisterDtl = billCommonRegisterDtlManager.getInvoiceAmount(ticketCollectDtoList.get(i).getInvoiceNo());*/
					BillBalanceInvoiceApply billBalanceInvoiceApply = new BillBalanceInvoiceApply();
					if(StringUtils.isNotEmpty(ticketCollectDtoList.get(i).getInvoiceNo())){
						billBalanceInvoiceApply.setBillNo(ticketCollectDtoList.get(i).getInvoiceNo());
						billBalanceInvoiceApply = billBalanceInvoiceApplyManager.findById(billBalanceInvoiceApply);
					}
					
					
					
					//通过发票号获取券的开票申请的开票金额
					/*BillBalanceInvoiceInfo billBalanceInvoiceInfo = new BillBalanceInvoiceInfo();
					billBalanceInvoiceInfo = billBalanceInvoiceInfoManager.getInvoiceAmount(ticketCollectDtoList.get(i).getInvoiceNo());*/
					
					//发票登记的开票金额
					if (null != billBalanceInvoiceApply) {
						if (null != billBalanceInvoiceApply.getAmount()) {
							orderTotalDto.setTicketAmount(billBalanceInvoiceApply.getAmount()); //开票金额
						} else {
							orderTotalDto.setTicketAmount(new BigDecimal(0)); //开票金额
						}
					} else {
						orderTotalDto.setTicketAmount(new BigDecimal(0)); //开票金额
					}
					/*else if (null != billBalanceInvoiceInfo) {
						if (null != billBalanceInvoiceInfo.getAmount()) {
							orderTotalDto.setTicketAmount(billBalanceInvoiceInfo.getAmount()); //开票申请的开票金额
						} else {
							orderTotalDto.setTicketAmount(new BigDecimal(0)); //开票申请的开票金额
						}
					}*/ 
					
					/**
					 * 获取周期内的用券数量and累计的用券数量
					 */

					//先获取当前收款登记下面的券信息
					List<TicketCollectDtlDto> ticketCollectDtlDtoList = ticketCollectDtoList.get(i)
							.getTicketCollectDtls();

					//获取券管理信息
					List<Ticket2Dto> ticket2DtoList = ticketCollectDtoList.get(i).getTickets();

					if ((null != ticketCollectDtlDtoList && ticketCollectDtlDtoList.size() > 0)
							&& (null != ticket2DtoList && ticket2DtoList.size() > 0)) {
						for (int j = 0; j < ticketCollectDtlDtoList.size(); j++) {
							for (int j2 = 0; j2 < ticket2DtoList.size(); j2++) {

								Date ticketStartDates = null;
								Date ticketEndDates = null;
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									ticketStartDates = sdf.parse(ticketStartDate);
									ticketEndDates = sdf.parse(ticketEndDate);
								} catch (Exception e) {
								}
								if(null != ticket2DtoList.get(j2).getUseTime()){
									if (ticketCollectDtlDtoList.get(j).getTicketNo()
											.equals(ticket2DtoList.get(j2).getTicketNo())
											&& ticketType == ticket2DtoList.get(j2).getTicketType()
											&& ticketStatus.equals(ticket2DtoList.get(j2).getStatus())
											&& (ticketStartDates.getTime() <= ticket2DtoList.get(j2).getUseTime().getTime() && ticketEndDates
													.getTime() >= ticket2DtoList.get(j2).getUseTime().getTime())) {
										useTicketNum = useTicketNum+1; //累加周期内的用券数量
									}
									if (ticketCollectDtlDtoList.get(j).getTicketNo()
											.equals(ticket2DtoList.get(j2).getTicketNo())
											&& ticketType == ticket2DtoList.get(j2).getTicketType()
											&& ticketStatus.equals(ticket2DtoList.get(j2).getStatus())
											&& ticketEndDates.getTime() >= ticket2DtoList.get(j2).getUseTime().getTime()) {
										useTicketNums = useTicketNums+1; //累加周期内的用券数量
									}
								}
							}
						}
					}
					orderTotalDto.setUseQty(useTicketNum); //本期使用券数量
					orderTotalDto.setUseTicketAmount(ticketCollectDtoList.get(i).getAmount()
							.multiply(new BigDecimal(useTicketNum))); //本期用券金额
					orderTotalDto.setUsedAmount(orderTotalDto.getBuyAmount().multiply(new BigDecimal(useTicketNum))); //本期实际使用金额
					orderTotalDto.setUseTotalAmount(ticketCollectDtoList.get(i).getAmount()
							.multiply(new BigDecimal(useTicketNums))); //累计用券金额
					orderTotalDto.setUsedTotalAmount(ticketCollectDtoList.get(i).getBuyAmount() == null ? new BigDecimal(0) : ticketCollectDtoList.get(i).getBuyAmount()
							.multiply(new BigDecimal(useTicketNums))); //累计实际用券金额
					orderTotalDto.setResidueAmount(orderTotalDto.getTotalAmount().subtract(
							orderTotalDto.getUseTotalAmount())); //剩余券面额
					orderTotalDto.setResidueReciveAmount((orderTotalDto.getBuyTotalAmount().subtract(orderTotalDto
							.getUsedTotalAmount()))); //剩余实收金额
					orderTotalDtoList.add(orderTotalDto);
				}
			}
		}
		//获取所有的销售订单
//		Map<String, Object> param = new HashMap<String, Object>();
		//构建查询条件
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupBy", "ref_bill_no");
		param.put("billType", BillTypeEnums.SALEOUT.getRequestId());
		param.put("bizType", BizTypeEnums.GROUPSALE.getStatus());
		//param.put("", startDate);
		//param.put("", endDate);
		List<BillSaleBalance> billStrList = billSaleBalanceManager.findByBiz(null, param);

		if (null != billStrList && billStrList.size() > 0) {
			for (BillSaleBalance billSaleBalance1 : billStrList) {
				if (StringUtils.isNotEmpty(billSaleBalance1.getRefBillNo())) {
					Map<String, Object> paramsMap = builderParams(req, model);
					paramsMap.put("salerNo", companyNo);
					paramsMap.put("billType", BillTypeEnums.SALEOUT.getRequestId());
					paramsMap.put("bizType", BizTypeEnums.GROUPSALE.getStatus());
					paramsMap.put("refBillNo", billSaleBalance1.getRefBillNo());
					paramsMap.put("balanceStartDate", balanceStartDate);
					paramsMap.put("balanceEndDate", endDate);
					
					List<BillSaleBalance> billSaleBalanceList = billSaleBalanceManager.findByBiz(null,paramsMap);
					if (null != billSaleBalanceList && billSaleBalanceList.size() > 0) {
						BigDecimal receivableAmount = new BigDecimal(0); //预收金额
						BigDecimal ticketAmount = new BigDecimal(0); //开票金额
						BigDecimal useTicketAmount = new BigDecimal(0); //本期出库金额
						BigDecimal usedTotalAmount = new BigDecimal(0); //累计出库金额
						OrderTotalDTO orderTotalDto = new OrderTotalDTO();
						orderTotalDto.setCustomerNo(billSaleBalanceList.get(0).getBuyerNo()); //客户编码
						orderTotalDto.setCustomerName(billSaleBalanceList.get(0).getBuyerName()); //客户名称
						orderTotalDto.setTicketName(billSaleBalanceList.get(0).getRefBillNo()); //团购订单号
						
						Map<String, BillSaleBalance> map = new HashMap<String, BillSaleBalance>();
						for (BillSaleBalance billSaleBalance : billSaleBalanceList) {
							// 把销售单号作为key 放map里，因为billSaleBalanceList 是明细，同一个销售单号可能会有多笔,所以需要以销售编号作为key 来把明细集合分组
							map.put(billSaleBalance.getRefBillNo(), billSaleBalance);
							useTicketAmount = useTicketAmount.add(billSaleBalance.getCost() == null ? new BigDecimal(0)
									: billSaleBalance.getCost().multiply(
											new BigDecimal(billSaleBalance.getSendQty() == null ? 0 : billSaleBalance
													.getSendQty()))); //本期出库金额

						}
						// 查询预收金额时，是根据一个销售单号只查一次
						for (Map.Entry<String, BillSaleBalance> entry:map.entrySet()) {
							BillSaleBalance billSaleBalance = entry.getValue();
							//通过客户编码和团购订单获取预收金额
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("customerNo", billSaleBalance.getBuyerNo());
							params.put("saleOrderNo", billSaleBalance.getRefBillNo());
							List<BillPrePaymentNt> billPrePaymentNtList = billPrePaymentNtManager.findByBiz(null,params);
							if (null != billPrePaymentNtList && billPrePaymentNtList.size() > 0) {
								for (BillPrePaymentNt billPrePaymentNt : billPrePaymentNtList) {

									//累计当前订单、客户下的预收金额
									receivableAmount = receivableAmount
											.add(billPrePaymentNt.getPaidAmount() == null ? new BigDecimal(0)
													: billPrePaymentNt.getPaidAmount());
									if(StringUtils.isNotEmpty(billPrePaymentNt.getInvoiceNo())){
										BillBalanceInvoiceApply billBalanceInvoiceApply = new BillBalanceInvoiceApply();
										billBalanceInvoiceApply.setBillNo(billPrePaymentNt.getInvoiceNo());
										billBalanceInvoiceApply = billBalanceInvoiceApplyManager.findById(billBalanceInvoiceApply);

										if (null != billBalanceInvoiceApply) {
											//累计开票金额
											ticketAmount = ticketAmount.add(billBalanceInvoiceApply.getAmount() == null ? new BigDecimal(0)
													: billBalanceInvoiceApply.getAmount());
										}
									}
								}
							}
						}
						
						Map<String, Object> obj = new HashMap<String, Object>();
						obj.put("salerNo", companyNo);
						obj.put("billType", BillTypeEnums.SALEOUT.getRequestId());
						obj.put("bizType", BizTypeEnums.GROUPSALE.getStatus());
						obj.put("refBillNo", billSaleBalance1.getRefBillNo());
						obj.put("balanceEndDate", endDate);
						List<BillSaleBalance> saleBalanceList = billSaleBalanceManager.findByBiz(null, obj);

						if (null != saleBalanceList && saleBalanceList.size() > 0) {
							for (BillSaleBalance billSaleBalance2 : saleBalanceList) {
								usedTotalAmount = usedTotalAmount.add(billSaleBalance2.getCost() == null ? new BigDecimal(0)
										: billSaleBalance2.getCost().multiply(
												new BigDecimal(billSaleBalance2.getSendQty() == null ? 0
														: billSaleBalance2.getSendQty()))); //本期出库金额
							}
						}

						orderTotalDto.setBuyTotalAmount(receivableAmount); //预收金额
						orderTotalDto.setTicketAmount(ticketAmount); //开票金额
						orderTotalDto.setUsedTotalAmount(usedTotalAmount); //累计出库金额
						orderTotalDto.setResidueReciveAmount((orderTotalDto.getBuyTotalAmount().subtract(orderTotalDto
								.getUsedTotalAmount()))); //剩余实收金额

						orderTotalDtoList.add(orderTotalDto);
					}

				}
			}
		}
		//}
		int total = orderTotalDtoList.size();
		List<OrderTotalDTO> totalList = new ArrayList<OrderTotalDTO>();
		if(total > 0){
			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
				totalList.add(orderTotalDtoList.get(i));
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", totalList);
		return obj;
	}

	/**
	 * 导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_groupon_export")
	public void doFasExport(HttpServletRequest request, Model model, HttpServletResponse response)
			throws ManagerException {
		Map<String, Object> params = builderParams(request, model);
		String exportColumns = (String) params.get("exportColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String) params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
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

				Map<String, Object> maps = new HashMap<String, Object>();

				maps = this.queryOrderTotalLists(request, model);

				Set<String> set = maps.keySet();

				List<OrderTotalDTO> list = new ArrayList<OrderTotalDTO>();

				Object obj = null;

				for (String string : set) {
					obj = maps.get(string);
				}

				list = (List<OrderTotalDTO>) obj;
				List<Map> listArrayList = new ArrayList<Map>();
				if (list != null && list.size() > 0) {
					List<String> fields = new ArrayList<String>();
					for (Map map : columnsList) {
						fields.add(map.get("field").toString());
					}
					boolean flag = true;
					ExportFormat formatAnnotation = null;
					AbstractExportFormat<OrderTotalDTO> format = null;
					for (OrderTotalDTO vo : list) {
						Map map = null;
						if (flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if (formatAnnotation != null) {
							format = (AbstractExportFormat<OrderTotalDTO>) formatAnnotation.className().newInstance();
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
	 * 对导出字段进行排序
	 * @param columns 原导出字段集合
	 * @return 排序后的导出字段
	 */
	private List<Map> sortExportColumns(List<Map> columns) {
		List<Map> results = new ArrayList<Map>();
		for (int i = 0; i < columns.size(); i++) {
			Map map = null;
			if (columns.get(i).get("seq") != null && !"".equals(columns.get(i).get("seq").toString().trim())) {
				for (Map entry : columns) {
					Integer seq = Integer.parseInt(entry.get("seq").toString());
					if (seq.intValue() == i + 1) {
						map = entry;
						break;
					}
				}
			} else {
				map = columns.get(i);
			}
			results.add(map);
		}
		return results;
	}

	/**
	 * 查询符合表头的数据
	 * @param vo 查询参数
	 * @return List<Map>
	 */
	protected List<Map> findComplexHeaderData(OrderTotalDTO vo) {
		return new ArrayList<Map>();
	}

	/**
	 * 转换成整型
	 * @param rowAccessWindowSizeStr String
	 * @return Integer
	 */
	private Integer getRowAccessWindowSizeValue(String rowAccessWindowSizeStr) {
		Integer rowAccessWindowSize = 1;
		if (!StringUtils.isEmpty(rowAccessWindowSizeStr)) {
			try {
				rowAccessWindowSize = Integer.parseInt(rowAccessWindowSizeStr);
			} catch (NumberFormatException e) {
				rowAccessWindowSize = 1;
			}
		}
		return rowAccessWindowSize;
	}

	/**
	 * 根据客户方编号查询GMS 相关销售单据信息（客殘销售单、索赔单(退库差异，其他差异)、团购出库单、报废单）
	 * @param params
	 * @param page
	 * @throws ManagerException
	 */
	private List<OrderDtlDto> getOrderDtlList(Map<String, Object> params,SimplePage page) throws ManagerException {
		List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
		
		dtlList = null;//orderMainManager.findBillSaleDetailByCondition(page, params);
		if (CollectionUtils.isNotEmpty(dtlList)) {
			for (int j = 0; j < dtlList.size(); j++) {
				OrderDtlDto dtl = dtlList.get(j);
				// 根据订单号获取发票号信息
				params.put("orderNo", dtl.getOrderNo());
				List<GroupSaleApplyInvoiceRel> groupSaleApplyInvoiceRelList = groupSaleApplyInvoiceRelManager.findByBiz(null, params);
				if (CollectionUtils.isNotEmpty(groupSaleApplyInvoiceRelList)) {
					GroupSaleApplyInvoiceRel groupSaleApplyInvoiceRel = groupSaleApplyInvoiceRelList.get(0);
					dtl.setInvoiceNo(groupSaleApplyInvoiceRel.getInvoiceApplyNo());
					dtl.setInvoiceDate(groupSaleApplyInvoiceRel.getUpdateTime());
				} else {
					dtl.setInvoiceNo("");
					dtl.setInvoiceDate(null);
				}
//				params.put("organCode", dtl.getOrganName());//查询时，把管理城市编号设置到organName 属性中
//				List<Organ> organList = organManager.findByBiz(null, params);
//				if(CollectionUtils.isNotEmpty(organList)){
//					dtl.setOrganName(organList.get(0).getName());
//				}
			}
		}
		return dtlList;
	}

	/**
	 * 根据店铺编号及其他条件查询POS 的内购销售单
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
	private List<OrderDtlDto> getOrderDtlList2(Map<String, Object> params,SimplePage page) throws ManagerException {
		List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
		
		dtlList = null;//orderMainManager.findInnerBuyDetailByCondition(page, params);
		for (OrderDtlDto orderDtlDto : dtlList) {
			//查询现金券
			OcOrderTicketDto dto = new OcOrderTicketDto();
			dto.setOrderNo(orderDtlDto.getOrderNo());
			dto.setTicketFlag(TICKETFLAG);
			dto.setTicketType(TICKETTYPE);

			List<OcOrderTicketDto> ticketAmount = new ArrayList<OcOrderTicketDto>();
			try {
				ticketAmount = orderTicketApi.findGroupTickeAmount(dto);
			} catch (RpcException e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}

			if (null != ticketAmount && ticketAmount.size() > 0) {
				orderDtlDto.setCouponPrice(ticketAmount.get(0).getAvailableAmount());
				if (orderDtlDto.getAmount() != null) {
					orderDtlDto.setPrefPrice(orderDtlDto.getAmount().subtract(orderDtlDto.getCouponPrice()));
				}
			}else{// 没有现金券金额时，现金就是总金额
				orderDtlDto.setPrefPrice(orderDtlDto.getAmount());
			}

		}
		return dtlList;
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

		//根据店铺编号进行汇总（查看汇总时）
		Map<String, Object> params = builderParams(req, model);
		setCommonParams(params);
		int total = 0;//orderMainManager.findInnerBuyCollectCountByCondtion(params);
		List<MemberOrderSummary> footer = new ArrayList<MemberOrderSummary>();
		List<MemberOrderSummary> orderSummaryList = new ArrayList<MemberOrderSummary>();
		
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			orderSummaryList = null;//orderMainManager.findInnerBuyCollectByCondtionList(page, null, null, params);
			
	//		if(null != orderSummaryList && orderSummaryList.size() > 0 && null != showShopSummaryList && showShopSummaryList.size() > 0){
			if(CollectionUtils.isNotEmpty(orderSummaryList)){
				for (int i = 0; i < orderSummaryList.size(); i++) {
					MemberOrderSummary memberOrderSummary = orderSummaryList.get(i);
					//只有pos 销售单才有现金券金额
					if("2".equals(memberOrderSummary.getTypeFlag())){
						BigDecimal cAmount = new BigDecimal(0);   //现金券金额
						params.put("shopNo", memberOrderSummary.getShopNo());
						SimplePage detailPage = new SimplePage(pageNo, 999999999, 999999999);
						List<OrderDtlDto> dtlList = getOrderDtlList2(params,detailPage);
						for (int j = 0; j < dtlList.size(); j++) {
//							OcOrderTicketDto dto = new OcOrderTicketDto();
//							dto.setOrderNo(memberOrderSummary.getBillNo());
//							dto.setTicketFlag(TICKETFLAG);
//							dto.setTicketType(TICKETTYPE);
//							
//							List<OcOrderTicketDto> ticketAmount = new ArrayList<OcOrderTicketDto>();
//							try {
//								ticketAmount = orderTicketApi.findGroupTickeAmount(dto);
//							} catch (RpcException e) {
//								e.printStackTrace();
//							}
//							
//							if(null != ticketAmount && ticketAmount.size() > 0){
							cAmount=cAmount.add(dtlList.get(j).getCouponPrice());
//							}
						}
						orderSummaryList.get(i).setCouponAmount(cAmount);
						if(cAmount.intValue() > 0){
							orderSummaryList.get(i).setCashAmount((orderSummaryList.get(i).getAllAmount() == null ? 
									new BigDecimal(0) : orderSummaryList.get(i).getAllAmount()).subtract(cAmount));
						}else{
							orderSummaryList.get(i).setCashAmount(orderSummaryList.get(i).getAllAmount());
						}
					}
				}
			}
			
	//		//获取发票金额
	//		if(CollectionUtils.isNotEmpty(showShopSummaryList)){
	//			for (int i = 0; i < showShopSummaryList.size(); i++) {
	//				if(StringUtils.isNotEmpty(showShopSummaryList.get(i).getInvoiceNo())){
	//					String[] invoiceNos = showShopSummaryList.get(i).getInvoiceNo().split(",");
	//					
	//					if(null != invoiceNos){
	//						BigDecimal invoiceAmount = new BigDecimal(0);
	//						for (String string : invoiceNos) {
	//							BillCommonInvoiceRegister billCommonInvoiceRegister = new BillCommonInvoiceRegister();
	//							billCommonInvoiceRegister.setBillNo(string);
	//							billCommonInvoiceRegister = billCommonInvoiceRegisterManager.findById(billCommonInvoiceRegister);
	//							
	//							if(null != billCommonInvoiceRegister){
	//								invoiceAmount = invoiceAmount.add(billCommonInvoiceRegister.getAmount() == null ? new BigDecimal(0) : billCommonInvoiceRegister.getAmount());
	//							}
	//						}
	//						showShopSummaryList.get(i).setInvoiceAmount(invoiceAmount);
	//					}
	//				}
	//			}
	//		}
			
			MemberOrderSummary dtl = new MemberOrderSummary();
	   		BigDecimal allAmount = new BigDecimal(0);
	   		int totalQty = 0;
	   		
	   		if(CollectionUtils.isNotEmpty(orderSummaryList)){
	   			for (MemberOrderSummary mos : orderSummaryList) {
	   				allAmount = allAmount.add(mos.getAllAmount());
	   				totalQty += mos.getTotalQty();
	   				
				}
	   			dtl.setBillNo("合计");
	   			dtl.setTotalQty(totalQty);
	   			dtl.setAllAmount(allAmount);
	   			footer.add(dtl);
	   		}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", orderSummaryList);
		obj.put("footer", footer);
		return obj;
	}
	

	@RequestMapping(value = "/order_query")
	@ResponseBody
	public Map<String, Object> queryByOrderNo(HttpServletRequest req, Model model) throws Exception {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String typeFlag = StringUtils.isEmpty(req.getParameter("typeFlag")) ? "" : req.getParameter("typeFlag");

		Map<String, Object> params = builderParams(req, model);//new HashMap<String, Object>();

		setCommonParams(params);
		List<OrderDtlDto> dtlList = new ArrayList<OrderDtlDto>();
		int total = 0 ;
		List<OrderDtlDto> showList = new ArrayList<OrderDtlDto>();
		String shopNo = params.get("shopNo") == null ?"":params.get("shopNo").toString();
		if(StringUtils.isNotBlank(typeFlag)){
			if("1".equals(typeFlag)){ //typeFlag = 1 为GMS 销售汇总，查询GMS 销售明细
				//构建查询条件
//				params.put("startDate", startDate);
//				params.put("endDate", endDate);
//				params.put("companyNo", companyNo);
				params.put("buyerNo", shopNo);
				total = 0;//orderMainManager.findBillSaleDetailCountByCondition(params);
				if(total > 0){
					SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
					dtlList = getOrderDtlList(params,page);
				}
			}else{ // 反之查询POS 销售明细
//				params.put("companyNo", companyNo);
//				params.put("startDate", startDate);
//				params.put("endDate", endDate);
				params.put("businessType", 3);
//				params.put("shopNo", shopNo);
				total = 0;//orderMainManager.findInnerBuyDetailCountByCondition(params);
				if(total > 0 ){
					SimplePage page = new SimplePage(pageNo, pageSize, total);
					dtlList = getOrderDtlList2(params,page);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(dtlList)){
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for (OrderDtlDto orderDtlDto : dtlList) {
				if(orderDtlDto.getShopNo().equals(shopNo)){
//					dtlList.get(j).setAmount(dtlList.get(j).getAmount());
					paramsMap.put("companyNo", params.get("companyNo"));
					paramsMap.put("shopNo", orderDtlDto.getShopNo());
					paramsMap.put("orderNo", orderDtlDto.getOrderNo());
					List<MemberOrderDtl> membereOrderDtlList = memberOrderDtlManager.findByBiz(null, paramsMap);
					
					if(null != membereOrderDtlList && membereOrderDtlList.size()>0){
						orderDtlDto.setFinanceConfirmFlag(membereOrderDtlList.get(0).getStatus().intValue());
					}else{
						orderDtlDto.setFinanceConfirmFlag(FinanceConfirmFlagEnums.NOT_CONFIRM.getStatus());
					}
					showList.add(orderDtlDto);
				}
			}
		}
		
//		int total = showList.size();
//		List<OrderDtlDto> showLists = new ArrayList<OrderDtlDto>();
//		if(null != showList && showList.size()>0){
//			for (int i = (pageNo - 1) * pageSize; i < (total > pageNo * pageSize ? pageNo * pageSize : total); i++) {
//				showLists.add(showList.get(i));
//			}
//		}
//		
//		Collections.sort(showLists, new Comparator<OrderDtlDto>() {
//            public int compare(OrderDtlDto arg0, OrderDtlDto arg1) {
//            	if(null != arg1.getCreateTime()){
//            		return arg1.getCreateTime().compareTo(arg0.getCreateTime());
//            	}else{
//            		return 0;
//            	}
//            }
//        });
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", showList);
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

		//品牌
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

		//品牌
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

	/**
	 * 导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_fas_export_member")
	public void doFasExports(HttpServletRequest request, Model model, HttpServletResponse response)
			throws ManagerException {
		Map<String, Object> params = builderParams(request, model);
		String exportColumns = (String) params.get("exportColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String) params.get("exportType");
//		String businessType = StringUtils.isEmpty(request.getParameter("businessType")) ? "" : request.getParameter("businessType");
		String companyNo = StringUtils.isEmpty(request.getParameter("companyNo")) ? "" : request.getParameter("companyNo");
//		String startDate = StringUtils.isEmpty(request.getParameter("startDate")) ? "" : request.getParameter("startDate");
//		String endDate = StringUtils.isEmpty(request.getParameter("endDate")) ? "" : request.getParameter("endDate");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
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

				Map<String, Object> maps = new HashMap<String, Object>();

				maps = queryShopMemberList(request, model);

				Set<String> set = maps.keySet();

				List<MemberOrderSummary> orderSummaryList = new ArrayList<MemberOrderSummary>();

				Object obj = null;

				for (String string : set) {
					obj = maps.get(string);
				}

				orderSummaryList = (List<MemberOrderSummary>) obj;
				
				List<OrderMainExport> list = new ArrayList<OrderMainExport>();
				
//				Map<String,Object> orderParams = new HashMap<String,Object>();
//				orderParams.put("companyNo", companyNo);
//				orderParams.put("startDate", startDate);
//				orderParams.put("endDate", endDate);
//				orderParams.put("businessType", businessType);
				
//				List<OrderDtlDto> orderDtlList = new ArrayList<OrderDtlDto>();
				List<OrderMainExport> orderSummaryExportList = new ArrayList<OrderMainExport>();
				setCommonParams(params);
				for (int j = 0; j < orderSummaryList.size(); j++) {
					MemberOrderSummary memberOrderSummary = orderSummaryList.get(j);
					params.put("buyerNo", memberOrderSummary.getShopNo());
					params.put("shopNo", memberOrderSummary.getShopNo());
					OrderMainExport export = new OrderMainExport();
					copyPropertyExportForOrderSummary(memberOrderSummary,export);
					if("1".equals(memberOrderSummary.getTypeFlag())){
						int total1 = 0;//orderMainManager.findBillSaleDetailCountByCondition(params);
						if(total1 > 0){
							SimplePage page1 = new SimplePage(1, total1, total1);
							List<OrderDtlDto> dtlList = getOrderDtlList(params,page1);
							int i = 0;
							for (OrderDtlDto orderDtlDto : dtlList) {
								if(i > 0){
									export = new OrderMainExport();
								}
								i++;
								copyPropertyExportForOrderSummary(orderSummaryExportList,orderDtlDto,companyNo,export);
							}
						}
					}else{
						int total1 = 0;//orderMainManager.findInnerBuyDetailCountByCondition(params);
						if(total1 > 0 ){
							SimplePage page1 = new SimplePage(1, total1, total1);
							List<OrderDtlDto> dtlList = getOrderDtlList2(params,page1);
							int i = 0;
							for (OrderDtlDto orderDtlDto : dtlList) {
								if(i > 0){
									export = new OrderMainExport();
								}
								i++;
								copyPropertyExportForOrderSummary(orderSummaryExportList,orderDtlDto,companyNo,export);
							}
						}
					}
					
				}
				list = orderSummaryExportList;

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
							List<Map> subExportData = this.findComplexHeaderDatas(vo);
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
	
	private void copyPropertyExportForOrderSummary(MemberOrderSummary detail,OrderMainExport export) throws ManagerException{
		export.setBillNo(detail.getBillNo());
		export.setShopNo(detail.getShopNo());
		export.setShopName(detail.getShopName());
		export.setTagPriceAmount(detail.getTagPriceAmount());
		export.setSalePriceAmount(detail.getSalePriceAmount());
		export.setSettleAmount(detail.getSettleAmount());
		export.setCashAmount(detail.getCashAmount());
		export.setTotalQty(detail.getTotalQty());
		export.setAllAmount(detail.getAllAmount());
		export.setCouponAmount(detail.getCouponAmount());
//		list.add(export);
	}
	
	private void copyPropertyExportForOrderSummary(List<OrderMainExport> list ,OrderDtlDto dtlDto,String companyNo,OrderMainExport export) throws ManagerException{
		export.setOrderNo(dtlDto.getOrderNo());
		export.setItemName(dtlDto.getItemName());
		export.setItemCode(dtlDto.getItemCode());
		export.setTagPrice(dtlDto.getTagPrice());
		export.setSalePrice(dtlDto.getSalePrice());
		export.setSettlePrice(dtlDto.getSettlePrice());
		export.setQty(dtlDto.getQty());
		export.setAmount(dtlDto.getAmount());
		export.setCouponPrice(dtlDto.getCouponPrice());
		export.setPrefPrice(dtlDto.getPrefPrice());
		export.setBrandName(dtlDto.getBrandName());
		export.setInvoiceNo(dtlDto.getInvoiceNo());
		export.setInvoiceDate(dtlDto.getInvoiceDate());
		export.setCategoryName(dtlDto.getCategoryName());
		export.setOrganName(dtlDto.getOrganName());
		export.setCreateTime(dtlDto.getCreateTime());
		export.setCreateUser(dtlDto.getCreateUser());
		export.setBizTypeName(dtlDto.getBizTypeName());
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("companyNo", companyNo);
		paramsMap.put("shopNo", dtlDto.getShopNo());
		paramsMap.put("orderNo", dtlDto.getOrderNo());
		List<MemberOrderDtl> membereOrderDtlList = memberOrderDtlManager.findByBiz(null, paramsMap);
		
		if(null != membereOrderDtlList && membereOrderDtlList.size()>0){
			export.setFinanceConfirmFlagStr("已确认");
		}else{
			export.setFinanceConfirmFlagStr("未确认");
		}
		list.add(export);
	}

	/**
	 * 查询符合表头的数据
	 * @param vo 查询参数
	 * @return List<Map>
	 */
	protected List<Map> findComplexHeaderDatas(OrderMainExport vo) {
		return new ArrayList<Map>();
	}
	
	
	@RequestMapping(value = "/finance_confirm")
	@ResponseBody
	public Boolean financeConfirm(HttpServletRequest req, Model model) throws ManagerException {
		int count = 0 ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String companyNo = req.getParameter("companyNo");
			String shopNoStr = req.getParameter("shopNos");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
			memberOrderDtl.setCompanyNo(companyNo);
			memberOrderDtl.setOutEndDate(sdf.parse(endDate));
			memberOrderDtl.setOutStartDate(sdf.parse(startDate));
			memberOrderDtl.setStatus((byte) 1);
			if(StringUtils.isNotBlank(shopNoStr)){
				count = orderDtlManager.financeConfirms(shopNoStr, memberOrderDtl,startDate,endDate);
			}else{
				String dataList = req.getParameter("datas") == null ? "" : req.getParameter("datas");
				if(StringUtils.isNotBlank(dataList)){
					JsonUtil<OrderDtlDto> util = new JsonUtil<OrderDtlDto>();
					List<OrderDtlDto> list = util.convertListWithTypeReference(dataList, req, OrderDtlDto.class);
					count = orderDtlManager.financeConfirm(list,memberOrderDtl);
				}
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
		int count = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String companyNo = req.getParameter("companyNo");
			String shopNoStr = req.getParameter("shopNos");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
			memberOrderDtl.setCompanyNo(companyNo);
			memberOrderDtl.setOutEndDate(sdf.parse(endDate));
			memberOrderDtl.setOutStartDate(sdf.parse(startDate));
			memberOrderDtl.setStatus((byte) 0);
			if(StringUtils.isNotBlank(shopNoStr)){
				count = orderDtlManager.financeConfirms(shopNoStr, memberOrderDtl,startDate,endDate);
			}else{
				String dataList = StringUtils.isEmpty(req.getParameter("datas")) ? "" : req.getParameter("datas");
				JsonUtil<OrderDtlDto> util = new JsonUtil<OrderDtlDto>();
				List<OrderDtlDto> list = util.convertListWithTypeReference(dataList, req, OrderDtlDto.class);
				count = orderDtlManager.financeAntiConfirm(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return count > 0 ? true : false;
	}
}