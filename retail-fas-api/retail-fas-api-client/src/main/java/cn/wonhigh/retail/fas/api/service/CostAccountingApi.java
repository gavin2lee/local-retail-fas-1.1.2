package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.List;

import cn.wonhigh.retail.fas.api.dto.HQRegionCostDto;
import cn.wonhigh.retail.fas.api.dto.HeadquarterCostDto;
import cn.wonhigh.retail.fas.api.dto.ItemZonePriceDto;
import cn.wonhigh.retail.fas.api.dto.RegionCostDto;
import cn.wonhigh.retail.fas.api.dto.UpdateCostApiDto;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;

import com.yougou.logistics.base.common.exception.RpcException;

public interface CostAccountingApi {
	
	/**
	 * 查询商品地区价格
	 * @param ItemNos 货号
	 * @param companyNo 公司编码/orderUnitNo 订货单位编码
	 * @param effectiveTime 生效时间
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public BigDecimal findRegionCost(RegionCostDto regionCostDto)throws RpcException;
	
	/**
	 * 批量查询商品地区价格
	 * @param companyNo 公司编码/orderUnitNo 订货单位编码
	 * @param itemNos 批量货号
	 * @param effectiveTime 生效时间
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public List<ItemZonePriceDto> findRegionListCost(RegionCostDto regionCostDto)throws RpcException;
	
	/**
	 * 查询商品总部价格
	 * @param itemNo 货号
	 * @param effectiveTime 生效时间
	 * @return 商品的总部价
	 * @throws RpcException
	 */
	public BigDecimal findHeadquarterCost(HeadquarterCostDto headquarterCostDto) throws RpcException;
	
	/**
	 * 查询公司的结账期间
	 * @param companyNo
	 * @return
	 * @throws RpcException
	 */
	CompanySettlePeriod findBalanceDate(String companyNo) throws RpcException;
	
	/**
	 * 根据公司和品牌查询结账日信息
	 * @param companyNo
	 * @param brandNo
	 * @return
	 * @throws RpcException
	 */
	CompanyBrandSettlePeriod findBalanceDateByBrandAndCompany(String companyNo, String brandNo) throws RpcException;
	
	/**
	 * POS根据公司和多个品牌查询结最大结账日
	 * @param companyNo
	 * @param brandNos
	 * @return
	 * @throws RpcException
	 */
	CompanyBrandSettlePeriod findCompanyBrandMaxSettleDate(String companyNo, List<String> brandNos) throws RpcException;
	
	/**
	 * 校验商品厂进价是否可以修改
	 * @param headqarterCostMaintain
	 * @return
	 * @throws RpcException
	 */
	public boolean checkUpdateFactoryPrice(HeadquarterCostMaintain headqarterCostMaintain) throws RpcException;

	/**
	 * 根据订货单位查询地区价
	 * @param itemNo 货号
	 * @param orderUnitNo 订货单位编号
	 * @param effectiveTime 生效时间
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public BigDecimal findRegionCostByOrderUnitDto(RegionCostDto dto) throws RpcException;
	
	/**
	 * 查询商品的地区价、总部价
	 * @param itemNo 货号
	 * @param orderUnitNo 订货单位编号
	 * @param effectiveTime 生效时间
	 * @return 商品的地区价
	 * @throws RpcException
	 */
	public HQRegionCostDto findHQAndRegionCost(RegionCostDto dto) throws RpcException;
	
	/**
	 * 校验公司是否承担总部职能
	 * @param headqarterCostMaintain
	 * @return
	 * @throws RpcException
	 */
	public boolean checkHQFunctionCompany(String companyNo) throws RpcException;

	/**
	 * 查询所有承担总部职能的公司
	 * @param headqarterCostMaintain
	 * @return
	 * @throws RpcException
	 */
	public List<String> findAllHQFunctionCompany() throws RpcException;
	
	/**
	 * 更新FAS所有单据表冗余的成本字段
	 * @param updateCostApiDto
	 * @return
	 * @throws RpcException
	 */
	public int updateCost(UpdateCostApiDto updateCostApiDto) throws RpcException;

	/**
	 * 更新FAS所有单据表冗余的总部价、地区价字段
	 * @param updateCostApiDto
	 * @return
	 * @throws RpcException
	 */
	public int updateHQAndRegionCost(UpdateCostApiDto updateCostApiDto) throws RpcException;
	
}
