package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface OrderMainManager extends BaseCrudManager {
	
	/**
	 * 发票号、日期写入销售订单、退换货单接口(仅限团购、员购销售订单)
	 * @Title: modifyOrderForInvoice  
	 * @Description: TODO 
	 * @param @param invoiceNo发票号, invoiceDate,发票日期  ,	 
  	 * @param @return
	 * @param @throws ManagerException 
  	 * @throws
	 */
	public void modifyOrderForInvoice(List<POSOcOrderInvoiceParameterDto> ocOrderInvoiceParameterDtoList,String invoiceNo,Date invoiceDate) throws ManagerException;
	
	
 	/**
     * DESC:现金存入记录查询接口
     * @param simplePageDto
     * @param OrderCategoryParameterDto
     * @return PagingDto<DepositCashDto>
     * @throws ManagerException
     */
	public POSOcPagingDto<POSDepositCashDto>  findList4OrderDepositCash(POSOcSimplePageDto pageDto,List<String>shopNoList, POSOrderParameterDto orderParameterDto) throws ManagerException;


	
	/**
     * DESC: 查询销售明细接口(多门店)
     * @param simplePageDto,shopNoList多个shop_No
     * @param OrderMainParamsDto
     * @return PagingDto<OrderAndReturnExMainDtlDto>
     * @throws ManagerException
     */
	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto>  findList4OrderMain(POSOcSimplePageDto pageDto,List<String>shopNoList, POSOcOrderParameterParentDto ocOrderParameterParentNoDto,int invoiceNoFlag,String invoiceNo) throws ManagerException;
	
	
	/**
     * DESC: 销售订单每日按支付方式的销售汇总接口
     * @param POSOcOrderParameterDto	
     * @return OcGroupOrderPaywayDto
     * @throws ManagerException
     */
	public POSOcPagingDto<POSOcGroupOrderPaywayDto>   findList4OcGroupOrderPayway(POSOcSimplePageDto pageDto,List<String>shopNoList, POSOcOrderParameterDto orderParameterDtoList) throws ManagerException;

	
	/**
     * DESC: 销售订单按大类汇总接口查询
     * @param POSOcOrderParameterDto
      * @return List<OcGroupRootCategoryDto> 
     * @throws ManagerException
     */
	public List<POSOcGroupRootCategoryDto>   findList4OcOrderGroupRootCategory(POSOcOrderParameterDto orderParameterDto) throws ManagerException;
	
	/**
     * DESC: 销售订单按商场结算码汇总接口查询
     * @param POSOcOrderParameterDto
      * @return List<OcGroupRootCategoryDto> 
     * @throws ManagerException
     */
	public List<BillShopBalanceCodeSum>   findListOrderGroupCodeSum(POSOcOrderParameterDto orderParameterDto) throws ManagerException;
	
 	/**
     * DESC: 销售订单按活动方式汇总接口
     * @param simplePageDto
     * @param OrderCategoryParameterDto
     * @return PagingDto<OrderSalePromationDto>
     * @throws ManagerException
     */
	public List<POSOcOcGroupPromationDto> findList4OcOrderGroupPromation(POSOcOrderParameterDto ocOrderParameterDto) throws ManagerException;

	public List<POSOcOcGroupPromationDto> findList4OcOrderGroupPromationH(POSOcOrderParameterDto ocOrderParameterDto) throws ManagerException;
	
 	/**
     * DESC: 销售订单按外卡每日销售汇总接口
     * @param ocOrderParameterDto
     * @param findList4OcOrderGroupWildCard
     * @return List<OcOcGroupWildCardDto>
     * @throws ManagerException
     */
	public List<POSOcOcGroupWildCardDto> findList4OcOrderGroupWildCard(POSOcOrderParameterDto ocOrderParameterDto,List<String> shopNoList) throws ManagerException;

	/**
     * DESC: 销售订单按支付方式的销售汇总接口
     * @param OcOrderParameterDto	
     * @return OcGroupOrderPaywayDto
     * @throws ManagerException
     */
	public List<POSOcGroupOrderPaywayDto> findList4OcGroupOrderPaywayForShop(
			List<String> shopNoList, POSOcOrderParameterDto orderParameterDtoList) throws ManagerException;

	/**
     * DESC:发票登记查询接口
     * @param simplePageDto,shopList
     * @param OrderCategoryParameterDto
     * @return PagingDto<RegisterInvoiceDto>
     * @throws ManagerException
     */
	public POSOcPagingDto<POSRegisterInvoiceDto>  findList4OrderRegisterInvoiceDto(POSOcSimplePageDto pageDto,List<String>shopNoList, POSOrderParameterDto orderParameterDto) throws ManagerException;

	/**
     * DESC: 查询销售明细接口（团购、员购）,按companyNo去查询
     * @param simplePageDto,companyNo,invoiceApplyNo发票号
     * @param OrderMainParamsDto
     * @return PagingDto<OrderAndReturnExMainDtlDto>
     * @throws ManagerException
     */
	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto>  findList4OrderMainForCompany(POSOcSimplePageDto pageDto,String companyNo,
			POSOcOrderParameterParentDto ocOrderParameterParentNoDto,int invoiceNoFlag,String invoiceNo,String invoiceApplyNo,List<String>shopNoList) throws ManagerException;
	
//	/**
//	 * 根据条件查询内购汇总信息
//	 * @param page
//	 * @param orderByField
//	 * @param orderBy
//	 * @param params
//	 * @return
//	 * @author wangyj
//	 * @throws ServiceException 
//	 */
//	public List<MemberOrderSummary> findInnerBuyCollectByCondtionList(SimplePage page, String sortColumn, String orderBy,Map<String, Object> params) throws ManagerException;
//	
//	/**
//	 * 根据条件查询内购汇总的记录数
//	 * @param params
//	 * @return
//	 * @throws ServiceException
//	 * @author wangyj
//	 */
//	public int findInnerBuyCollectCountByCondtion(Map<String, Object> params) throws ManagerException;
//	
//	/**
//	 * 根据查询条件及店铺查询pos 销售明细信息
//	 * @param page
//	 * @param params
//	 * @return
//	 * @author wangyj
//	 */
//	public List<OrderDtlDto> findInnerBuyDetailByCondition(SimplePage page, Map<String, Object> params) throws ManagerException;
//
//	/**
//	 * 根据查询条件及店铺查询内购销售明细记录数
//	 * @param params
//	 * @return
//	 * @author wangyj
//	 */
//	public int findInnerBuyDetailCountByCondition(Map<String, Object> params) throws ManagerException;
//	
//	/**
//	 * 根据查询条件及店铺查询GMS(bill_sale_balance) 销售明细信息
//	 * @param page
//	 * @param params
//	 * @return
//	 * @author wangyj
//	 */
//	public List<OrderDtlDto> findBillSaleDetailByCondition(SimplePage page, Map<String, Object> params) throws ManagerException;
//
//	/**
//	 * 根据查询条件及店铺查询内购销售明细记录数
//	 * @param params
//	 * @return
//	 * @author wangyj
//	 */
//	public int findBillSaleDetailCountByCondition(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 发票申请号、日期写入销售订单(仅限团购、员购销售订单)
	 * @Title: modifyOrderForInvoiceApply  
	 * @Description: TODO      
	 * @param @param   invoiceApplyNo开票申请单号   , invoiceApplyDate,开票申请日期
     * @param @return
	 * @param @throws ManagerException 
  	 * @throws
	 */
	public void modifyOrderForInvoiceApply(List<String> orderNoList,String invoiceApplyNo,Date invoiceApplyDate) throws ManagerException;
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<BalanceInvoiceApplyGenerator> findApplyGeneratorDetail(SimplePage page, Map<String, Object> params)  throws ManagerException ;
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findApplyGeneratorDetailCount(Map<String, Object> params) throws ManagerException;


//	/**
//	 * 查询销售明细总数
//	 * @param params
//	 * @return
//	 */
//	public int findSaleOrderDtlCount(Map<String, Object> params) throws ManagerException;

//	/**
//	 * 查询销售明细数据
//	 * @param params
//	 * @return
//	 */
//	public List<POSOrderAndReturnExMainDtl> findSaleOrderDtlByPage(
//			SimplePage page, String sortColumn, String sortOrder,
//			Map<String, Object> params) throws ManagerException;


	/**
	 * 根据查询条件，查询pos 内购销售明细记信息
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findPosInnerBuyDetailList(SimplePage page, Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findPosInnerBuyDetailCount(Map<String, Object> params) throws ManagerException;
	
	
	/**
	 * 查询销售明细商场门店收款日报表支付方式
	 * @param params
	 * @return
	 */
	public int findSaleOrderPaywayCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询销售明细商场门店收款日报表支付方式 
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleOrderPaywayByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	
	public SaleOrderPayway getSumSaleOrderPayway(Map<String, Object> params) throws ManagerException;
	
	
	/**
	 * 查询店铺销售月报表-总数count
	 * @param params   
	 * @return
	 */
	public int findSaleMonthReportCount(Map<String, Object> params) throws ManagerException;

	/**
	 *查询店铺销售月报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleMonthReportByPage(SimplePage page, String sortColumn, String orderBy,Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询店铺销售月报表-sum
	 * @param params
	 * @return
	 */
	public SaleOrderPayway getSumSaleMonthReport(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询店铺销售明细
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<ItemSaleDtlDto> findShopSaleDetailList(SimplePage page,Map<String, Object> params)  throws ManagerException;
	
	/**
	 * 查询店铺销售明细 －－ 导出
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public void findShopSaleDetailList(Map<String, Object> params,Function<Object, Boolean> handler)  throws ManagerException;
	
	/**
	 * 店铺销售明细合计及记录数
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public ItemSaleDtlDto findShopSaleDetailCount(Map<String, Object> params)  throws ManagerException ;
	
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<ItemSaleDtlDto> findOrderDetailByOrderNo(SimplePage page,Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findOrderDetailCountByOrderNo(Map<String, Object> params) throws ManagerException;
	
}