package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCodeSum;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.model.POSOcGroupRootCategory;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupPromation;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupWildCard;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
public interface OrderMainService extends BaseCrudService {
	
	/**
	 *发票号、日期写入销售订单、退换货单接口(仅限团购、员购销售订单)
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public void modifyOrderForInvoiceNoAndDate(Map<String, Object> params) throws ServiceException;

	/**
	 * 根据参数分页查询销售订单和退换货单
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOrderAndReturnExMainDtl> findOrderBillByPage(SimplePage page, String sortColumn, String sortOrder,Map<String, Object> params) throws ServiceException;

	
	/**
	 * 根据参数查询销售订单和退换货单数量
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int findOrderBillCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 销售订单按大类汇总接口查询
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOcGroupRootCategory> findOcOrderGroupRootCategory(Map<String, Object> params) throws ServiceException;
	
	
	/**
	 * 销售订单按大类汇总接口查询
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOcOcGroupPromation> findOcOrderGroupPromationDto(Map<String, Object> params) throws ServiceException;

	public List<POSOcOcGroupPromation> findOcOrderGroupPromationDtoH(Map<String, Object> params) throws ServiceException;
	/**
	 * 销售订单按大类汇总接口查询
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOcOcGroupWildCard> findListOcOrderGroupWildCard(Map<String, Object> params) throws ServiceException ;

	
	/**
	 * 销售订单每日按支付方式的销售汇总接口总数
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int findOrderPayWayCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 销售订单每日按支付方式的销售汇总接口
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOcGroupOrderPayway> findOrderPayWayList(SimplePage page, String sortColumn, String sortOrder,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 销售订单按支付方式的销售汇总接口
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOcGroupOrderPayway> findOrderPayWayListForShop(String sortColumn, String sortOrder,Map<String, Object> params) throws ServiceException;
	
	/**
     * DESC: 销售订单按商场结算码汇总接口查询
     * @param POSOcOrderParameterDto
      * @return List<OcGroupRootCategoryDto> 
     * @throws ManagerException
     */
	public List<BillShopBalanceCodeSum>   findListOrderGroupCodeSum(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据参数CompanyNo查询销售订单和退换货单数量
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int findOrderBillCountForCompanyNo(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据参数ForCompanyNo分页查询销售订单和退换货单
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<POSOrderAndReturnExMainDtl> findOrderBillByPageForCompanyNo(SimplePage page, String sortColumn, String sortOrder,Map<String, Object> params) throws ServiceException;
	
	/**
	 *发票申请号、日期写入销售订单 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public void modifyOrderForInvoiceApplyNoAndDate(Map<String, Object> params) throws ServiceException;
	
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<BalanceInvoiceApplyGenerator> findApplyGeneratorDetail(SimplePage page, Map<String, Object> params)  throws ServiceException;
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findApplyGeneratorDetailCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记信息
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findPosInnerBuyDetailList(SimplePage page, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findPosInnerBuyDetailCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 根据条件,汇总销售明细的金额、数量
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public POSOrderAndReturnExMainDtl getSumOcOrderByParameter(Map<String, Object> params) throws ServiceException;
	
	
	/**
	 * 查询销售明细商场门店收款日报表支付方式
	 * @param params
	 * @return
	 */
	public int findSaleOrderPaywayCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询销售明细商场门店收款日报表支付方式 
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleOrderPaywayByPage(
			SimplePage page, String sortColumn, String orderBy,Map<String, Object> params) throws ServiceException;
	
	
	public SaleOrderPayway getSumSaleOrderPayway(Map<String, Object> params) throws ServiceException;
	
	
	/**
	 * 查询店铺销售月报表-总数count
	 * @param params   
	 * @return
	 */
	public int findSaleMonthReportCount(Map<String, Object> params) throws ServiceException;

	/**
	 *查询店铺销售月报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleMonthReportByPage(SimplePage page, String sortColumn, String orderBy,Map<String, Object> params) throws ServiceException;
	
	
	/**
	 * 查询店铺销售月报表-sum
	 * @param params
	 * @return
	 */
	public SaleOrderPayway getSumSaleMonthReport(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 查询店铺销售日报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleDayReportByPage(String sortColumn, String orderBy,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 分品牌查询店铺销售日报表 总计-list
	 * @param params
	 * @return
	 */
	public SaleOrderPayway findSaleDayReportForBrandCount(Map<String, Object> params);
	
	/**
	 * 分品牌查询店铺销售日报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleDayReportForBrandByPage(String sortColumn, String orderBy,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 查询店铺销售日报表-实收list
	 */
	public List<SaleOrderPayway> findSaleDayReportByDetail(SimplePage page, String sortColumn, String orderBy,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 查询店铺销售日报表-合计
	 */
	public SaleOrderPayway findShopDailyReportShowCount(Map<String, Object> params);
	
	/**
	 * 查询店铺销售明细
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<ItemSaleDtlDto> findShopSaleDetailList(SimplePage page,Map<String, Object> params)  throws ServiceException;
	
	/**
	 * 查询店铺销售明细－－导出
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public void findShopSaleDetailList(Map<String, Object> params,Function<Object, Boolean> handler)  throws ServiceException;
	
	/**
	 * 店铺销售明细合计及记录数
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public ItemSaleDtlDto findShopSaleDetailCount(Map<String, Object> params)  throws ServiceException;
	
	/**
	 * 获取店铺起止销售日总销售金额
	 * @param params
	 * @return
	 */
	public BigDecimal getSaleAmount(Map<String, Object> params);
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<ItemSaleDtlDto> findOrderDetailByOrderNo(SimplePage page,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findOrderDetailCountByOrderNo(Map<String, Object> params) throws ServiceException;

	public List<OrderMain> findOrderMainInfo(Map<String, Object> params) throws ServiceException;

	public List<Map<String, Object>> findList(Map<String, Object> params) throws ServiceException;
	
	public List<Map<String, Object>> findDayReportForBrandList(Map<String, Object> param) throws ServiceException;
}