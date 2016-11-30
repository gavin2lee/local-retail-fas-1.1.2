package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ShopVo;

import com.yougou.logistics.base.common.exception.DaoException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;

public interface CommonUtilMapper {

	/**
	 * 获取已维护总部价格的商品
	 * @return
	 */
	public List<String> queryHeadquarterMaintainedItems() throws DaoException;
	
	/**
	 * 获取已维护地区价格的商品
	 * @return
	 */
	public List<String> queryRegionMaintainedItems(String zoneNo) throws DaoException;
	
	/**
	 * 需要生成总部价格的商品数量
	 * @return
	 */
	public int countHQNeedRuleMatchItems(@Param("params") Map<String, Object> params) throws DaoException;
	
	/**
	 * 需要生成地区价格的商品数量
	 * @return
	 */
	public int countRegionNeedRuleMatchItems(@Param("params") Map<String, Object> params) throws DaoException;
	
	/**
	 * 需要生成总部价格的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryHQNeedRuleMatchItems(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams) throws DaoException;
	
	/**
	 * 需要生成地区价格的商品列表
	 * @return
	 */
	public List<RegionCostMaintain> queryRegionNeedRuleMatchItems(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams) throws DaoException;

	
	public String selectHqMaxBillNo(@Param("params") Map<String, Object> params)throws DaoException;

	public String selectMaxBillNo(@Param("params") Map<String, Object> params)throws DaoException;

	public int findShopCount(@Param("params")Map<String, Object> params)throws DaoException;

	public List<ShopVo> findShopByPage(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params)throws DaoException;

	public int selectPageOrganizationCount(@Param("params")Map<String, Object> params)throws DaoException;

	public List<LookupVo> selectPageOrganizationList(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params)throws DaoException;

	/**
	 * @return
	 */
	public List<LookupVo> selectYears();
	
	public int selectItemExtendsCount(@Param("params")Map<String, Object> params)throws DaoException;

	public List<ItemDto> selectItemExtendsList(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params)throws DaoException;
	
	/**
	 * 获取批量生成的首增总部价总数
	 */
	public int countHQFirstNewNeedRuleMatchItems(@Param("params") Map<String, Object> params) throws DaoException;
	
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryHQFirstNewNeedRuleMatchItems(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams) throws DaoException;

	/**
	 * 获取批量生成的首增总部价总数
	 */
	public int countQuotationRuleMatchItems(@Param("params") Map<String, Object> params) throws DaoException;
	
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryQuotationRuleMatchItems(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams) throws DaoException;

	public List<ZoneInfo> findPriceZones(@Param("params")Map<String, Object> params) throws DaoException;

	/**
	 * 需要按厂进价生成变价部分的的商品总数
	 * @return
	 */
	public int countRegionNeedRuleFactoryMatchItems(@Param("params") Map<String, Object> params);

	/**
	 * 需要按厂进价生成变价部分的的商品列表
	 * @return
	 */
	public List<RegionCostMaintain> queryRegionNeedRuleFactoryMatchItems(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);

	public List<RegionCostMaintain> getRegionNeedRuleMatchItems(@Param("page")SimplePage page, @Param("params")Map<String, Object> queryParams);

	public List<HeadquarterCostMaintain> getHQNeedRuleMatchItems(@Param("page")SimplePage page, @Param("params")Map<String, Object> queryParams);

}
