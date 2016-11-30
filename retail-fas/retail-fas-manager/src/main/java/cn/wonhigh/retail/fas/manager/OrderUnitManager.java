package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.OrderUnit;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author huang.xb1
 * @date  2014-07-28 14:19:21
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
public interface OrderUnitManager  extends BaseCrudManager {
	/**
	 * 查询订货单位详情
	 * @param queryStore
	 * @return
	 * @throws ManagerException
	 */
	public OrderUnit getDetail(OrderUnit orderUnit)throws ManagerException;
	
	/**
	 * 新增订货单位
	 * @param 
	 * @return
	 * @throws ManagerException
	 */
//	public int add(List<OrderBrandStoreRel> list ,OrderUnit OrderUnit)throws ManagerException;
	
	/**
	 * 删除订货单位
	 * @param queryStore
	 * @return
	 * @throws ManagerException
	 */
	public int delOrderUnit(Map<CommonOperatorEnum, List<OrderUnit>> params ) throws ManagerException;

	/**
	 * 新增订货单位
	 * @param queryStore
	 * @return
	 * @throws ManagerException
	 */
//	public  int saveAll(Map<CommonOperatorEnum, List<OrderBrandStoreRel>> params,OrderUnit orderUnit) throws ManagerException;
	/**
	 * 修改订货单位信息
	 * @param queryStore
	 * @return
	 * @throws ManagerException
	 */
	public int editOrderUnit(OrderUnit OrderUnit)throws ManagerException;
}