package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillSaleBalanceManager extends BaseCrudManager {

	/**
	 * 查询批发订单的数量
	 * @param params 查询参数
	 * @return 订单的数据
	 * @throws ManagerException 异常
	 */
	int findSaleOrderCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws ManagerException 异常
	 */
	List<SaleOrderDto> findSaleOrderByPage(SimplePage page, String sortColumn, String sortOrder, 
			Map<String, Object> params) throws ManagerException;
	
	/**
	 * 获取地区团购订单信息
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectBillSaleBalanceByNo(Map<String, Object> params);
	
	List<BillSaleBalance> selectSaleOrder(SimplePage page, Map<String, Object> params);
	
	int selectSaleOrderCounts(Map<String, Object> params);
	
	int selectSaleOrderCount(Map<String, Object> params)throws ManagerException;

	List<SaleOrderDto> selectSaleOrderByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;
	
	/**
	 * 根据查询条件查询GMS 内购销售信息: 团购出库、报废、差异索赔、客残销售、盘差索赔
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findGmsInnerBuyDetailList(SimplePage page, String orderByField,String orderBy,Map<String,Object> params)throws ManagerException;
	
	/**
	 * 根据查询条件查询GMS 内购销售记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findGmsInnerBuyDetailCount(Map<String,Object> params)throws ManagerException;
	
	//对象其他属性赋值
	public List<BillSaleBalance> setExtendsBillSaleBalanceProperties(List<BillSaleBalance> list);
	
	//合计
	public List<Object> selectEnterFooter(Map<String, Object> params) throws ManagerException;
	
	
	Integer selectBrandCategoryDeductionCount(Map<String, Object> params)throws ManagerException;
	
	List<Object> selectBrandCategoryDeductionFooter(Map<String, Object> params)throws ManagerException;
	
	List<BillSaleBalance> selectBrandCategoryDeductionByPage(SimplePage page, String orderByField,String orderBy,Map<String,Object> params)throws ManagerException;
	
//	public void apportionDeduction(BillBalance billBalance) throws ManagerException;
}