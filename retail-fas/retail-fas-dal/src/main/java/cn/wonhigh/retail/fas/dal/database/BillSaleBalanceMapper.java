package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-17 14:43:51
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
public interface BillSaleBalanceMapper extends BaseCrudMapper {

	int findSplitCount(@Param("params") Map<String, Object> params);

	List<Object> selectEnterByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params) ;

	int selectEnterCount(@Param("params") Map<String,Object> params) ;

	public int batchUpdateBalanceNoById(@Param("params") Map<String, Object> params);
	
	List<BillBalance> generateOtherStockOutBalance(@Param("params") Map<String,Object> params);
	
	void updateOtherStockOutBalanceNo(BillBalance balance);
	
	int updateBalanceInfoById(BillSaleBalance bill);

	List<Object> selectEnterFooter(@Param("params") Map<String, Object> params);

	/**
	 * 根据查询条件汇总单据金额
	 * @param params 查询条件
	 * @return 汇总的单据金额
	 * @throws Exception 异常
	 */
	BillBalance sumBillCostAndQty(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 查询地区批发结算数据(批量生成结算单)
	 * @param params 查询条件
	 * @return 数据集合
	 * @throws Exception 异常
	 */
	List<WholesaleBalanceDto> findMulitZoneBalance(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 查询批发订单的数量
	 * @param params 查询参数
	 * @return 订单的数据
	 * @throws Exception 异常
	 */
	int findSaleOrderCount(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws Exception 异常
	 */
	List<SaleOrderDto> findSaleOrderByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params")Map<String,Object> params) throws Exception;
	
	/**
	 * 获取所有地区批发团购订单
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectBillSaleBalanceByNo(@Param("params")Map<String,Object> params);
	
    List<BillSaleBalance> selectSaleOrder(@Param("page") SimplePage page, @Param("params")Map<String,Object> params);
    
    int selectSaleOrderCounts(@Param("params") Map<String,Object> params);
    
	/**
	 * 查询批发订单的数量
	 * @param params 查询参数
	 * @return 订单的数据
	 * @throws Exception 异常
	 */
	int selectSaleOrderCount(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws Exception 异常
	 */
	List<SaleOrderDto> selectSaleOrderByPage(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params")Map<String,Object> params) throws Exception;
	
	/**
	 * 更新含税金额
	 * @param params billNo:单据编码     skuNo:商品SKU 
	 */
	int updateSaleBalanceCost(Map<String,Object> params);
	
	/**
	 * 更新已做开票申请的单据状态
	 * @param params 
	 */
	void updateSaleBalanceIsBillInvoice(Map<String,Object> params);
	
	/**
	 * 查询将要被做生成开票申请的单据数量
	 * @param params
	 * @return
	 */
	Integer selectBillSaleBalanceListForGeneratorInvoiceCount(Map<String,Object> params);
	
	/**
	 * 查询将要被做生成开票申请的单据
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectBillSaleBalanceListForGeneratorInvoice(Map<String,Object> params);
	
	/**
	 * 根据查询条件查询GMS 内购销售信息: 团购出库、报废、差异索赔、客残销售、盘差索赔
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findGmsInnerBuyDetailList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params")Map<String,Object> params) ;
	
	/**
	 * 根据查询条件查询GMS 内购销售记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findGmsInnerBuyDetailCount(@Param("params")Map<String,Object> params);
	
	Integer selectBrandCategoryDeductionCount(@Param("params")Map<String, Object> params);
	
	List<Object> selectBrandCategoryDeductionFooter(@Param("params")Map<String, Object> params);
	
	List<BillSaleBalance> selectBrandCategoryDeductionByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	List<BillSaleBalance> selectSendAmountDeductByBrand(@Param("balanceNo") String balanceNo);
	
	BillSaleBalance selectSendAmountByBalanceNo (@Param("balanceNo") String balanceNo);
	
	int resetOtherDeductionByBalanceNo(@Param("balanceNo") String balanceNo);
	
	
	/**
	 * 批量解冻批发退货
	 * @param params
	 * @return
	 */
	int batchUpdateFrozenBill(@Param("list") List<BillSaleBalance> list) ;
	
	/**
	 * 批量修改订单余额
	 * @param params
	 * @return
	 */
	int batchUpdateOrderRemaining(@Param("list") List<CustomerOrderRemain> list) ;
	
	/**
	 * 批量修改客户余额
	 * @param params
	 * @return
	 */
	int batchUpdateRemainingSum(@Param("list") List<WholesaleCustomerRemainingSum> list) ;
	
}