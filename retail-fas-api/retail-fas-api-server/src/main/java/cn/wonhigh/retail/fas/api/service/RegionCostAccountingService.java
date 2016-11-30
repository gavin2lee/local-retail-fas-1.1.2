package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

public interface RegionCostAccountingService {
	/**
	 * 查询商品地区价格
	 * @param itemNo 货号
	 * @param zoneNo 地区编码
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public BigDecimal findRegionCost(String itemNo,String zoneNo,Date effectiveDate) throws ServiceException;
	
	/**
	 * 批量查询商品地区价格
	 * @param zoneNo 地区编码
	 * @param itemNos 批量货号
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public List<RegionCostMaintain> findRegionCost(List<String> itemNos,String zoneNo,Date effectiveDate) throws ServiceException;
	
	
	/**
	 * 查询商品地区价格根据订货单位编号
	 * @param itemNo 货号
	 * @param orderUnitNo 订货单位编号
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public BigDecimal findRegionCostByOrderUnitNo(String itemNo,String orderUnitNo) throws ServiceException;

	public Company getCompanyInfoByOrderUnitNo(String orderUnitNo) throws ServiceException;

	public FinancialAccount getFinancialAccountByCompanyNo(
			Map<String, Object> financialAccountmMap) throws ServiceException;

	public Company findCompanyModel(Map<String, Object> companyMap) throws ServiceException;
	
}
