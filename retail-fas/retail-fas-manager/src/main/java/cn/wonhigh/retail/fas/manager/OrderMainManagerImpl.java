package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.POSPublicConstans;
import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSDepositCashDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupRootCategoryDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOcGroupPromationDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOcGroupWildCardDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderInvoiceParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterParentDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSRegisterInvoiceDto;
import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCodeSum;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.model.POSOcGroupRootCategory;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupPromation;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupWildCard;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.model.RegisterInvoice;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.service.DepositCashService;
import cn.wonhigh.retail.fas.service.OrderMainService;
import cn.wonhigh.retail.fas.service.RegisterInvoiceService;
import cn.wonhigh.retail.fas.service.ReturnExchangeMainService;
import cn.wonhigh.retail.pos.api.client.utils.CommonUtils;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
@Service("orderMainManager")
class OrderMainManagerImpl extends BaseCrudManagerImpl implements OrderMainManager {
	
	protected static final XLogger logger = XLoggerFactory.getXLogger(OrderMainManagerImpl.class);
    @Resource
    private OrderMainService orderMainService;
    
	@Resource
	private OrderMainInfoDataSet orderMainInfoDataSet;
	
	@Resource
	private GroupPayWayDataSet groupPayWayDataSet;
	
	@Resource
	private RegisterInvoiceService registerInvoiceService;
	
	@Resource
	private DepositCashService depositCashService;
	
	@Resource
	private ReturnExchangeMainService returnExchangeMainService;
	

    @Override
    public BaseCrudService init() {
        return orderMainService;
    }
    
    /**
	* 发票号、日期写入销售订单、退换货单接口(仅限团购、员购销售订单)
	* @Title: modifyOrderForInvoice  
	* @Description: TODO 
	* @param @param orderNo订单号,type 类型:0-正常 1-换货 2-退货,invoiceNo发票号, invoiceDate 发票日期  ,	 
	 * @param @return
	* @param @throws RpcException 
	 * @throws
	*/
	@Override
	public void  modifyOrderForInvoice(List<POSOcOrderInvoiceParameterDto> ocOrderInvoiceParameterDtoList , String invoiceNo, Date invoiceDate)
			throws ManagerException {
		if (!CommonUtils.hasValue(ocOrderInvoiceParameterDtoList)) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME);
		}
		List<String> orderMainOrderNoList=new ArrayList();
		List<String> returnExMainBusinessNoList=new ArrayList<String>();
		for(POSOcOrderInvoiceParameterDto ocOrderInvoiceParameterDto:ocOrderInvoiceParameterDtoList){
			int orderBillType=ocOrderInvoiceParameterDto.getOrderBillType();
			String orderNo=ocOrderInvoiceParameterDto.getOrderNo();
 			if(orderBillType==POSPublicConstans.ORDER_TYPE_NORMAL&&CommonUtils.hasValue(orderNo)){
				orderMainOrderNoList.add(orderNo);
 			}else if (orderBillType!=POSPublicConstans.ORDER_TYPE_NORMAL&&CommonUtils.hasValue(orderNo)){
 				returnExMainBusinessNoList.add(orderNo);
 			}
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("paramInvoiceNo", invoiceNo);
			params.put("paramInvoiceDate", invoiceDate);
			if (CommonUtils.hasValue(orderMainOrderNoList)) {
				params.put("orderNoList", orderMainOrderNoList);
				orderMainService.modifyOrderForInvoiceNoAndDate(params);
			}
			if (CommonUtils.hasValue(returnExMainBusinessNoList)) {
				params.put("businessNoList", returnExMainBusinessNoList);
				returnExchangeMainService.modifyOrderForInvoiceNoAndDate(params);
			}
   		} catch (Exception e) {
			logger.error("发票号、日期写入销售订单、退换货单接口失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"发票号、日期写入销售订单、退换货单接口失败!",e);
		}
	}
    
    /**
	 * DESC: 销售订单每日按支付方式的销售汇总接口
	 * @param POSOcOrderParameterDto	
	 * @return OcGroupOrderPaywayDto
	 * @throws ManagerException
	 */
	@Override
	public POSOcPagingDto<POSOcGroupOrderPaywayDto> findList4OcGroupOrderPayway(POSOcSimplePageDto pageDto,
			List<String> shopNoList, POSOcOrderParameterDto orderParameterDtoList) throws ManagerException {
		try {
			Map<String, Object> params = orderParameterDtoList.getParams();
			if (CommonUtils.hasValue(shopNoList)) {
				params.put("shopNoList", shopNoList);
			}
			POSOcPagingDto<POSOcGroupOrderPaywayDto> pagingDto = new POSOcPagingDto<POSOcGroupOrderPaywayDto>();
			//获取总行数 
			int total = orderMainService.findOrderPayWayCount(params);
			pagingDto.setTotal(total);
			if (total == 0) {
				return pagingDto;
			}
			SimplePage page = new SimplePage(pageDto.getPageNo(), pageDto.getPageSize(), total);
			//取得分页结果
			List<POSOcGroupOrderPayway> result = orderMainService.findOrderPayWayList(page,
					POSPublicConstans.ORDER_BY_FIELD_OUT_DATE, POSPublicConstans.ORDER_BY_DESC, params);
			List<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayDtoList = groupPayWayDataSet.convertListDto(result,
					POSOcGroupOrderPaywayDto.class);
			pagingDto.setResult(ocGroupOrderPaywayDtoList);
			return pagingDto;
		} catch (Exception e) {
			logger.error("销售订单每日按支付方式的销售汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单每日按支付方式的销售汇总接口查询失败!", e);
		}
	}
	
	/**
	 * DESC: 销售订单按大类汇总接口查询
	 * @param POSOcOrderParameterDto
	  * @return List<OcGroupRootCategoryDto> 
	 * @throws ManagerException
	 */
	@Override
	public List<POSOcGroupRootCategoryDto> findList4OcOrderGroupRootCategory(POSOcOrderParameterDto orderParameterDtoList)
			throws ManagerException {
		if (!CommonUtils.hasValue(orderParameterDtoList.getShopNo())) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+
					POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			Map<String, Object> params = orderParameterDtoList.getParams();
			List<POSOcGroupRootCategory> result = orderMainService.findOcOrderGroupRootCategory(params);
			List<POSOcGroupRootCategoryDto> ocGroupOrderPaywayDtoList = GroupOderDataSet
					.convertOcGroupRootCategoryList(result);
			return ocGroupOrderPaywayDtoList;
		} catch (Exception e) {
			logger.error("销售订单按大类汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单按大类汇总接口查询失败!", e);
		}
	}
	
	/**
	 * DESC: 销售订单按活动方式汇总接口
	 * @param simplePageDto
	 * @param OrderCategoryParameterDto
	 * @return PagingDto<OrderSalePromationDto>
	 * @throws ManagerException
	 */
	@Override
	public List<POSOcOcGroupPromationDto> findList4OcOrderGroupPromation(POSOcOrderParameterDto orderParameterDtoList)
			throws ManagerException {
		if (!CommonUtils.hasValue(orderParameterDtoList.getShopNo())) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+1002+
					POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			Map<String, Object> params = orderParameterDtoList.getParams();
			List<POSOcOcGroupPromation> result = orderMainService.findOcOrderGroupPromationDto(params);
			List<POSOcOcGroupPromationDto> ocOcGroupPromationDtoList = GroupOderDataSet
					.convertOcOcGroupPromationList(result);
			return ocOcGroupPromationDtoList;
		} catch (Exception e) {
			logger.error("销售订单按活动方式汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单按活动方式汇总接口查询失败!", e);
		}
	}
	
	/**
     * DESC: 销售订单按外卡每日销售汇总接口
     * @param ocOrderParameterDto
     * @param findList4OcOrderGroupWildCard
     * @return List<OcOcGroupWildCardDto>
     * @throws ManagerException
     */
	@Override
	public List<POSOcOcGroupWildCardDto> findList4OcOrderGroupWildCard(POSOcOrderParameterDto ocOrderParameterDto,List<String> shopNoList)
			throws ManagerException {
		Map<String, Object> params = ocOrderParameterDto.getParams();
 		try {
 			if (CommonUtils.hasValue(shopNoList)) {
 				params.put("shopNoList", shopNoList);
 			}
 			List<POSOcOcGroupWildCard> result = orderMainService.findListOcOrderGroupWildCard(params);
			List<POSOcOcGroupWildCardDto> ocOcGroupWildCardDtoList = GroupOderDataSet.convertOcOcGroupWildCardList(result);
			return ocOcGroupWildCardDtoList;
		} catch (Exception e) {
			logger.error("销售订单按外卡每日销售汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"根据销售订单按外卡每日销售汇总接口查询失败!", e);
		}
	}
	
	/**
	 * DESC: 销售订单分页查询
	 * @param simplePageDto,shopNoList多个shop_No
	 * @param OrderMainParamsDto
	 * @return PagingDto<OrderMainDto>
	 * @throws ManagerException
	 */
	@Override
	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto> findList4OrderMain(POSOcSimplePageDto pageDto, List<String> shopNoList,
			POSOcOrderParameterParentDto ocOrderParameterParentNoDto, int invoiceNoFlag,String invoiceNo) throws ManagerException {
		try {
			POSOcPagingDto<POSOrderAndReturnExMainDtlDto> pagingDto = new POSOcPagingDto<POSOrderAndReturnExMainDtlDto>();
			Map<String, Object> params = new HashMap<String, Object>();
			if (ocOrderParameterParentNoDto!=null){
				  params = ocOrderParameterParentNoDto.getParams();
			}
 			if (CommonUtils.hasValue(shopNoList)) {
				params.put("shopNoList", shopNoList);
			}
			if (CommonUtils.hasValue(invoiceNo)) {
 				params.put("invoiceNo", invoiceNo);
 			}
			params.put("invoiceNoFlag_4", invoiceNoFlag);
  			// 获取总行数 
			int total = orderMainService.findOrderBillCount(params);
			pagingDto.setTotal(total);
			
			
			if (total == 0) {
				return pagingDto;
			}
			SimplePage page = new SimplePage(pageDto.getPageNo(), pageDto.getPageSize(), total);
			//取得分页结果
			List<POSOrderAndReturnExMainDtl> result = orderMainService.findOrderBillByPage(page,
					POSPublicConstans.ORDER_BY_FIELD_OUT_DATE, POSPublicConstans.ORDER_BY_DESC, params);
			//对象转化  
			List<POSOrderAndReturnExMainDtlDto> orderDtoList = orderMainInfoDataSet.convertListDto(result,
					POSOrderAndReturnExMainDtlDto.class);
			pagingDto.setResult(orderDtoList);
			return pagingDto;
		} catch (Exception e) {
			logger.error("销售订单分页查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单分页查询失败!", e);
		}
	}

	@Override
	public POSOcPagingDto<POSDepositCashDto> findList4OrderDepositCash(
			POSOcSimplePageDto pageDto, List<String> shopNoList,
			POSOrderParameterDto orderParameterDto) throws ManagerException {
		if (!CommonUtils.hasValue(shopNoList)) {
			logger.error("现金存入记录查询 findList4OrderDepositCash，传入的参数shopNoList为空");
			throw new ManagerException("现金存入记录查询findList4OrderDepositCash，传入的参数shopNoList为空");
		}
		try {
			POSOcPagingDto<POSDepositCashDto> pagingDto = new POSOcPagingDto<POSDepositCashDto>();
			Map<String, Object> params = orderParameterDto.getParams();
			params.put(POSPublicConstans.COMMON_SHOPLIST, shopNoList);
			params.put(POSPublicConstans.COMMON_STATUS, POSPublicConstans.COMMON_ZERO);
  			//获取总行数
			int total = depositCashService.findCount(params);
			pagingDto.setTotal(total);
			if (total == 0) {
				return pagingDto;
			}
			SimplePage page = new SimplePage(pageDto.getPageNo(), pageDto.getPageSize(), total);
			// 取得分页结果
			List<DepositCash> result = depositCashService.findByPage(page, POSPublicConstans.ORDER_BY_FIELD_DEPOSIT_DATE, POSPublicConstans.ORDER_BY_DESC, params);
			List<POSDepositCashDto> depositCashDtoList = OrderMainDataSet.convertDepositCashDto(result);
			pagingDto.setResult(depositCashDtoList);
 			return pagingDto;
 		} catch (Exception e) {
 			logger.error("现金存入记录查询失败!", e);
			throw new ManagerException("现金存入记录查询失败!", e);
		}
	}

	@Override
	public List<POSOcGroupOrderPaywayDto> findList4OcGroupOrderPaywayForShop(
			List<String> shopNoList,
			POSOcOrderParameterDto orderParameterDtoList)
			throws ManagerException {
		if (!CommonUtils.hasValue(orderParameterDtoList.getShopNo()) && !CommonUtils.hasValue(shopNoList)) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+
					POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			Map<String, Object> params = orderParameterDtoList.getParams();
			if (CommonUtils.hasValue(shopNoList)) {
				params.put("shopNoList", shopNoList);
			}
 			List<POSOcGroupOrderPayway> result = orderMainService.findOrderPayWayListForShop(
					POSPublicConstans.ORDER_BY_FIELD_OUT_DATE, POSPublicConstans.ORDER_BY_DESC, params);
			List<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayDtoList = groupPayWayDataSet.convertListDto(result,
					POSOcGroupOrderPaywayDto.class);
			return ocGroupOrderPaywayDtoList;
 		} catch (Exception e) {
			logger.error("销售订单按支付方式的销售汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单按支付方式的销售汇总接口查询失败!", e);
		}
	}

	@Override
	public POSOcPagingDto<POSRegisterInvoiceDto> findList4OrderRegisterInvoiceDto(
			POSOcSimplePageDto pageDto, List<String> shopNoList,
			POSOrderParameterDto orderParameterDto) throws ManagerException {
		if (!CommonUtils.hasValue(shopNoList)) {
			logger.error("发票登记查询接口 findList4OrderRegisterInvoiceDto，传入的参数shopNoList为空");
			throw new ManagerException("发票登记查询接口 findList4OrderRegisterInvoiceDto，传入的参数shopNoList为空");
		}
		try {
			POSOcPagingDto<POSRegisterInvoiceDto> pagingDto = new POSOcPagingDto<POSRegisterInvoiceDto>();
			Map<String, Object> params = orderParameterDto.getParams();
			params.put(POSPublicConstans.COMMON_SHOPLIST, shopNoList);
			params.put(POSPublicConstans.COMMON_STATUS, POSPublicConstans.COMMON_ZERO);
			params.put("confirmFlag", POSPublicConstans.COMMON_ONE);
			params.put("groupByField", "bill_no");
  			//获取总行数
			int total = registerInvoiceService.findCount(params);
			pagingDto.setTotal(total);
			if (total == 0) {
				return pagingDto;
			}
			SimplePage page = new SimplePage(pageDto.getPageNo(), pageDto.getPageSize(), total);
			//取得分页结果
			List<RegisterInvoice> result = registerInvoiceService.findByPage(page, POSPublicConstans.ORDER_BY_FIELD_OUT_DATE, POSPublicConstans.ORDER_BY_DESC, params);
			List<POSRegisterInvoiceDto> registerInvoiceList = OrderMainDataSet.convertRegisterInvoiceDto(result);
			pagingDto.setResult(registerInvoiceList);
 			return pagingDto;
 		} catch (Exception e) {
 			logger.error("发票登记查询接口失败!", e);
  			throw new ManagerException("发票登记查询接口失败!", e);
		}
		
		
	}

	@Override
	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto> findList4OrderMainForCompany(
			POSOcSimplePageDto pageDto, String companyNo,
			POSOcOrderParameterParentDto ocOrderParameterParentNoDto,
			int invoiceNoFlag, String invoiceNo, String invoiceApplyNo,
			List<String> shopNoList) throws ManagerException {
		if (!CommonUtils.hasValue(ocOrderParameterParentNoDto.getBusinessTypeList())) {
			throw new ManagerException(POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			POSOcPagingDto<POSOrderAndReturnExMainDtlDto> pagingDto = new POSOcPagingDto<POSOrderAndReturnExMainDtlDto>();
			Map<String, Object> params = new HashMap<String, Object>();
 			if (ocOrderParameterParentNoDto!=null){
				  params = ocOrderParameterParentNoDto.getParams();
			}
 			if (CommonUtils.hasValue(companyNo)) {
				params.put("companyNo", companyNo);
			}
 			if (CommonUtils.hasValue(shopNoList)) {
				params.put("shopNoList", shopNoList);
			}
 			if (CommonUtils.hasValue(invoiceNo)) {
				params.put("invoiceNo", invoiceNo);
			}
			if (CommonUtils.hasValue(invoiceApplyNo)) {
				params.put("invoiceApplyNo", invoiceApplyNo);
			}
			if (ocOrderParameterParentNoDto!=null){
				int businessType=ocOrderParameterParentNoDto.getBusinessTypeList().get(0);
				if(businessType==POSPublicConstans.ORDER_BUSINESS_TYPE_COMPANY){
					params.put("invoiceNoFlag_3", invoiceNoFlag);
				}else if(businessType==POSPublicConstans.ORDER_BUSINESS_TYPE_ASSISTANT){
					params.put("invoiceNoFlag_4", invoiceNoFlag);
				}
 			}
			// 获取总行数 
			params.put("invoiceNoFlag", invoiceNoFlag);
			int total = orderMainService.findOrderBillCountForCompanyNo(params);
			pagingDto.setTotal(total);
			if (total == 0) {
				return pagingDto;
			}
			SimplePage page = new SimplePage(pageDto.getPageNo(), pageDto.getPageSize(), total);
			//取得分页结果
			List<POSOrderAndReturnExMainDtl> result = orderMainService.findOrderBillByPageForCompanyNo(page,
					POSPublicConstans.ORDER_BY_FIELD_OUT_DATE, POSPublicConstans.ORDER_BY_DESC, params);
			//对象转化  
			List<POSOrderAndReturnExMainDtlDto> orderDtoList = orderMainInfoDataSet.convertListDto(result,
					POSOrderAndReturnExMainDtlDto.class);
			pagingDto.setResult(orderDtoList);
			return pagingDto;
		} catch (Exception e) {
			logger.error("销售订单按companyNo分页查询失败!", e);
			throw new ManagerException("销售订单按companyNo分页查询失败!");
		}
	}

	@Override
	public void modifyOrderForInvoiceApply(List<String> orderNoList,
			String invoiceApplyNo, Date invoiceApplyDate)
			throws ManagerException {
		if (!CommonUtils.hasValue(orderNoList)) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+
					POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("paramInvoiceApplyNo", invoiceApplyNo);
			params.put("paramInvoiceApplyDate", invoiceApplyDate);
			params.put("orderNoList", orderNoList);
			orderMainService.modifyOrderForInvoiceApplyNoAndDate(params);
  		} catch (Exception e) {
			logger.error("发票申请号、日期写入销售订单接口失败!", e);
			throw new ManagerException( "发票申请号、日期写入销售订单接口失败!", e);
		}
	}
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<BalanceInvoiceApplyGenerator> findApplyGeneratorDetail(SimplePage page, Map<String, Object> params)  throws ManagerException {
		try{
			return orderMainService.findApplyGeneratorDetail(page,params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findApplyGeneratorDetailCount(Map<String, Object> params) throws ManagerException {
		try{
			return orderMainService.findApplyGeneratorDetailCount(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}

//	@Override
//	public int findSaleOrderDtlCount(Map<String, Object> params)
//			throws ManagerException {
//		try {
//			return orderMainService.findOrderBillCount(params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//	}

	@Override
//	public List<POSOrderAndReturnExMainDtl> findSaleOrderDtlByPage(
//			SimplePage page, String sortColumn, String sortOrder,
//			Map<String, Object> params) throws ManagerException {
//		try {
//			return orderMainService.findOrderBillByPage(page, sortColumn, sortOrder, params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//	}
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记信息
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findPosInnerBuyDetailList(SimplePage page, Map<String, Object> params) throws ManagerException{
		try {
			return orderMainService.findPosInnerBuyDetailList(page, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findPosInnerBuyDetailCount(Map<String, Object> params) throws ManagerException{
		try {
			return orderMainService.findPosInnerBuyDetailCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceCodeSum> findListOrderGroupCodeSum(
			POSOcOrderParameterDto orderParameterDto) throws ManagerException {
		if (!CommonUtils.hasValue(orderParameterDto.getShopNo())) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+
					POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			Map<String, Object> params = orderParameterDto.getParams();
			List<BillShopBalanceCodeSum> result = orderMainService.findListOrderGroupCodeSum(params);
//			List<POSOcGroupRootCategoryDto> ocGroupOrderPaywayDtoList = GroupOderDataSet
//					.convertOcGroupRootCategoryList(result);
			return result;
		} catch (Exception e) {
			logger.error("销售订单按商场结算码汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单按商场结算码汇总接口查询失败!", e);
		}
	}

	@Override
	public int findSaleOrderPaywayCount(Map<String, Object> params)
			throws ManagerException {
		try{
			return orderMainService.findSaleOrderPaywayCount(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}

	@Override
	public List<SaleOrderPayway> findSaleOrderPaywayByPage(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.findSaleOrderPaywayByPage(page, sortColumn, orderBy, params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}

	@Override
	public SaleOrderPayway getSumSaleOrderPayway(Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.getSumSaleOrderPayway(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
		
	}

	@Override
	public int findSaleMonthReportCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.findSaleMonthReportCount(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}

	@Override
	public List<SaleOrderPayway> findSaleMonthReportByPage(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.findSaleMonthReportByPage(page, sortColumn, orderBy, params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}

	@Override
	public SaleOrderPayway getSumSaleMonthReport(Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.getSumSaleMonthReport(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}
	
	
	/**
	 * 查询店铺销售明细
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public List<ItemSaleDtlDto> findShopSaleDetailList(SimplePage page,Map<String, Object> params)  throws ManagerException{
		try {
			return orderMainService.findShopSaleDetailList(page,params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 查询店铺销售明细－－导出
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public void findShopSaleDetailList(Map<String, Object> params,Function<Object, Boolean> handler)  throws ManagerException{
		try {
			orderMainService.findShopSaleDetailList(params,handler);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}
	
	
	/**
	 * 店铺销售明细合计及记录数
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public ItemSaleDtlDto findShopSaleDetailCount(Map<String, Object> params)  throws ManagerException{
		try{
			return orderMainService.findShopSaleDetailCount(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}

	@Override
	public List<POSOcOcGroupPromationDto> findList4OcOrderGroupPromationH(
			POSOcOrderParameterDto orderParameterDtoList) throws ManagerException {
		if (!CommonUtils.hasValue(orderParameterDtoList.getShopNo())) {
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+1002+
					POSPublicConstans.COMMON_ERROR_MSG);
		}
		try {
			Map<String, Object> params = orderParameterDtoList.getParams();
			List<POSOcOcGroupPromation> result = orderMainService.findOcOrderGroupPromationDtoH(params);
			List<POSOcOcGroupPromationDto> ocOcGroupPromationDtoList = GroupOderDataSet
					.convertOcOcGroupPromationList(result);
			return ocOcGroupPromationDtoList;
		} catch (Exception e) {
			logger.error("销售订单按活动方式汇总接口查询失败!", e);
			throw new ManagerException(POSPublicConstans.PROJECT_NAME+"销售订单按活动方式汇总接口查询失败!", e);
		}
	}
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<ItemSaleDtlDto> findOrderDetailByOrderNo(SimplePage page,Map<String, Object> params) throws ManagerException {
		try{
			return orderMainService.findOrderDetailByOrderNo(page,params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findOrderDetailCountByOrderNo(Map<String, Object> params) throws ManagerException {
		try{
			return orderMainService.findOrderDetailCountByOrderNo(params);
		} catch(Exception e){
			throw new ManagerException(e);
		}
	}
}