package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface OrderMainMapper extends BaseCrudMapper {
	
	/**
	 * 修改销售订单发票号和发票日期
	 * @param orderMainDto
	 * @return
	 */
	public void updateByInvoiceNoAndDate(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据参数CompanyNo分页查询销售订单和退换货单
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<POSOrderAndReturnExMainDtl> findOrderBillByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);

	
	
	/**
	 * 根据参数查询销售订单和退换货单数量
	 * @param params
	 * @return
	 */
	public int findOrderBillCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 销售订单按大类汇总接口查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<POSOcGroupRootCategory> findOcOrderGroupRootCategory(@Param("params")Map<String, Object> params);
	
	
	/**
	 * 销售订单按活动汇总接口查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<POSOcOcGroupPromation> findOcOrderGroupPromation( @Param("params")Map<String, Object> params);
	
	public List<POSOcOcGroupPromation> findOcOrderGroupPromationH( @Param("params")Map<String, Object> params);
	/**
	 * 销售订单按外卡每日销售汇总接口
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 */
	public List<POSOcOcGroupWildCard> selectListOcOrderGroupWildCard(@Param("params")Map<String, Object> params);
	
	
	/**
	 * 销售订单按商场结算码汇总接口查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<BillShopBalanceCodeSum> findListOrderGroupCodeSum( @Param("params")Map<String, Object> params);
	
	
	/**
	 *销售订单每日按支付方式的销售汇总接口总数
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public int findOrderPayWayCount(@Param("params")Map<String, Object> params);
	
	
	/**
	 *销售订单每日按支付方式的销售汇总接口
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<POSOcGroupOrderPayway> findOcGroupOrderPayway(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);


	/**
	 *销售订单每日按支付方式的销售汇总接口
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<POSOcGroupOrderPayway> findOrderPayWayListForShop(@Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
 	
	
	/**
	 * 根据参数查询销售订单和退换货单数量
	 * @param params
	 * @return
	 */
	public int findOrderBillCountForCompanyNo(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据参数CompanyNo分页查询销售订单和退换货单
	 * @param pageO
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<POSOrderAndReturnExMainDtl> findOrderBillByPageForCompanyNo(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	
	/**
	 * 修改销售订单发票申请号和发票日期
	 * @param orderMainDto
	 * @return
	 */
	public void updateByInvoiceApplyNoAndDate(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<BalanceInvoiceApplyGenerator> findApplyGeneratorDetail(@Param("page") SimplePage page, @Param("params")Map<String, Object> params);
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findApplyGeneratorDetailCount(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询销售汇总
	 * @param page
	 * @param params
	 * @return
	 * @author wang.xy
	 */
	public POSOrderAndReturnExMainDtl selectSumOcOrderByParameter(@Param("params") Map<String, Object> params);
	
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记信息
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findPosInnerBuyDetailList(@Param("page") SimplePage page, @Param("params")Map<String, Object> params);
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findPosInnerBuyDetailCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询销售明细商场门店收款日报表支付方式
	 * @param params
	 * @return
	 */
	public int findSaleOrderPaywayCount(@Param("params")Map<String, Object> params);

	/**
	 * 查询销售明细商场门店收款日报表支付方式 
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleOrderPaywayByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	public SaleOrderPayway getSumSaleOrderPayway(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询店铺销售月报表-总数count
	 * @param params   
	 * @return
	 */
	public int findSaleMonthReportCount(@Param("params")Map<String, Object> params);

	/**
	 *查询店铺销售月报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleMonthReportByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	
	/**
	 * 查询店铺销售月报表-sum
	 * @param params
	 * @return
	 */
	public SaleOrderPayway getSumSaleMonthReport(@Param("params")Map<String, Object> params);
	
	/**
	 *查询店铺销售日报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleDayReportByPage(@Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	
	/**
	 *分品牌查询店铺销售日报表总计-list
	 * @param params
	 * @return
	 */
	public SaleOrderPayway findSaleDayReportForBrandCount(@Param("params")Map<String, Object> params);
	
	/**
	 *分品牌查询店铺销售日报表-list
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleDayReportForBrandByPage(@Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 *查询店铺销售日报表-实收数据
	 * @param params
	 * @return
	 */
	public List<SaleOrderPayway> findSaleDayReportByDetail(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy,@Param("page")Map<String, Object> params );
	
	/**
	 * 查询店铺销售日报表-合计对象
	 *  @param params
	 * @return
	 */
	public SaleOrderPayway findShopDailyReportShowCount(@Param("params")Map<String, Object> params);
	
	
	
	/**
	 * 查询店铺销售明细
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public List<ItemSaleDtlDto> findShopSaleDetailList(@Param("page") SimplePage page,@Param("params")Map<String, Object> params);
	
	/**
	 * 店铺销售明细合计及记录数
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	public ItemSaleDtlDto findShopSaleDetailCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 店铺起止销售日期之内总销售金额
	 * @param params
	 * @return
	 */
	public BigDecimal getSaleAmount(@Param("params")Map<String, Object> params);
	

	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<ItemSaleDtlDto> findOrderDetailByOrderNo(@Param("page") SimplePage page, @Param("params")Map<String, Object> params);
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findOrderDetailCountByOrderNo(@Param("params")Map<String, Object> params);
	
	public List<OrderMain> findOrderMainInfo(@Param("params")Map<String, Object> params);
	
	public List<Map<String, Object>> findList(@Param("params")Map<String, Object> params);
	
	public List<Map<String, Object>> findDayReportForBrandList(@Param("params")Map<String, Object> param);
	
}