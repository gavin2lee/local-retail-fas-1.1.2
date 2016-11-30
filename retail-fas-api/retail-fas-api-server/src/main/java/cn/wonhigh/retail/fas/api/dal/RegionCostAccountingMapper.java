package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;

public interface RegionCostAccountingMapper {
	/**
	 * 查询商品地区价格
	 * @return 商品的地区价
	 */
	public RegionCostMaintain findRegionCost(Map<String, Object> params);
	
	/**
	 * 批量查询商品地区价格
	 * @return 商品的地区价
	 */
	public List<RegionCostMaintain> findRegionCostList(Map<String, Object> params);
	
	/**
	 * 查询商品地区价格根据订货单位编号
	 * @param params 
	 * @return 商品的地区价
	 */
	public RegionCostMaintain findRegionCostByOrderUnitNo(Map<String,Object> params);

	public Company getCompanyInfoByOrderUnitNo(String orderUnitNo);

	public FinancialAccount getFinancialAccountByCompanyNo(
			@Param("params") Map<String, Object> financialAccountmMap);

	public Company getCompanyModel(@Param("params") Map<String, Object> companyMap);
	
}
