package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
public interface BillSaleBalanceService extends BaseCrudService {

	int findSplitCount(Map<String, Object> params);
	
	int batchUpdateBalanceNoById(Map<String, Object> params) throws ServiceException;
	
	List<BillBalance> generateOtherStockOutBalance(Map<String, Object> params ) throws ServiceException;
	
	void saveBatchGenerateOtherStockOut(List<BillBalance> balances, BillBalance billBalance) throws ServiceException;
	
	/**
	 * 根据查询条件汇总单据金额
	 * @param params 查询条件
	 * @return 汇总的单据金额
	 * @throws ServiceException 异常
	 */
	BillBalance sumBillCostAndQty(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 查询地区批发结算数据(批量生成结算单)
	 * @param params 查询条件
	 * @return 数据集合
	 * @throws ServiceException 异常
	 */
	List<WholesaleBalanceDto> findMulitZoneBalance(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询批发订单的数量
	 * @param params 查询参数
	 * @return 订单的数据
	 * @throws ServiceException 异常
	 */
	int findSaleOrderCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws ServiceException 异常
	 */
	List<SaleOrderDto> findSaleOrderByPage(SimplePage page, String sortColumn, String sortOrder, 
			Map<String, Object> params) throws ServiceException;
	
	/**
	 * 获取地区团购订单信息
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectBillSaleBalanceByNo(Map<String, Object> params);
	
	List<BillSaleBalance> selectSaleOrder(SimplePage page, Map<String, Object> params);
	
	int selectSaleOrderCounts(Map<String, Object> params);

	int selectSaleOrderCount(Map<String, Object> params)throws ServiceException;

	List<SaleOrderDto> selectSaleOrderByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;
	
	int updateSaleBalanceCost(Map<String,Object> params)throws ServiceException;
	
	void updateSaleBalanceIsBillInvoice(Map<String,Object> params) throws ServiceException;
	
	Integer queryBillSaleBalanceListForGeneratorInvoiceCount(Map<String,Object> params) throws ServiceException;
	
	List<BillSaleBalance> queryBillSaleBalanceListForGeneratorInvoice(Map<String, Object> params)throws ServiceException;
	
	List<Object> selectEnterFooter(Map<String, Object> params)throws ServiceException;
	
	/**
	 * 根据查询条件查询GMS 内购销售信息: 团购出库、报废、差异索赔、客残销售、盘差索赔
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findGmsInnerBuyDetailList(SimplePage page, String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
	/**
	 * 根据查询条件查询GMS 内购销售记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findGmsInnerBuyDetailCount(Map<String,Object> params)throws ServiceException;
	
	Integer selectBrandCategoryDeductionCount(Map<String, Object> params)throws ServiceException;
	
	List<Object> selectBrandCategoryDeductionFooter(Map<String, Object> params)throws ServiceException;
	
	List<BillSaleBalance> selectBrandCategoryDeductionByPage(SimplePage page, String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
	List<BillSaleBalance> selectSendAmountDeductByBrand(String balanceNo)throws ServiceException;
	
	BillSaleBalance selectSendAmountByBalanceNo (String balanceNo)throws ServiceException;
	
	int resetOtherDeductionByBalanceNo(String balanceNo)throws ServiceException;

}