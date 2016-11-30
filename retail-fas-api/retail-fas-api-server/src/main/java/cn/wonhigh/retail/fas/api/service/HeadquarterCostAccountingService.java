package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.Date;

import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

public interface HeadquarterCostAccountingService {
	/**
	 * 查询商品总部价格
	 * @param itemNo 货号
	 * @return 商品的总部价
	 * @throws RpcException
	 */
	public BigDecimal findHeadquarterCost(String itemNo,Date effectiveDate) throws ServiceException;
	
	/**
	 * 校验商品供应商的总部成本是否存在
	 * @param headqarterCostMaintain
	 * @return
	 * @throws ServiceException
	 */
	public int qryHeadquarterCostExist(HeadquarterCostMaintain headqarterCostMaintain) throws ServiceException;

	/**
	 * 校验商品厂进价是否生效
	 * @param headqarterCostMaintain
	 * @return
	 * @throws ServiceException
	 */
	public int qryHeadquarterCostIsEffective(HeadquarterCostMaintain headqarterCostMaintain) throws ServiceException; 
}
