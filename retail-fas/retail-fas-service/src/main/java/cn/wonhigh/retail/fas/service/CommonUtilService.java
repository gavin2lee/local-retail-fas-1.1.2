package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ShopVo;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

public interface CommonUtilService {

	/**
	 * 获取已维护总部价格的商品
	 * @return
	 */
	public List<String> queryHeadquarterMaintainedItems() throws ServiceException;
	
	/**
	 * 获取已维护地区价格的商品
	 * @return
	 */
	public List<String> queryRegionMaintainedItems(String zoneNo) throws ServiceException;
	
	/**
	 * 需要生成总部价格的商品数量
	 * @return
	 */
	public int countHQNeedRuleMatchItems(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 需要生成地区价格的商品数量
	 * @return
	 */
	public int countRegionNeedRuleMatchItems(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 需要生成总部价格的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryHQNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;
	
	/**
	 * 需要生成地区价格的商品列表
	 * @return
	 */
	public List<RegionCostMaintain> queryRegionNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据结算公司No获取名称
	 * @return
	 */
	public String getCompanyNameByNo(String companyNo)throws ServiceException;
	
	/**
	 * 根据供应商No获取名称
	 * @return
	 */
	public String getSupplierNameByNo(String supplierNo)throws ServiceException;
	
	/**
	 * 根据品牌No获取名称
	 * @return
	 */
	public String getbrandNameByNo(String brandNo)throws ServiceException;
	
	/**
	 * 根据类别No获取名称
	 * @return
	 */
	public String getCategoryNameByNo(String categoryNo)throws ServiceException;
	
	
	/**
	 * 根据商品No获取名称
	 * @return
	 */
	public String getItemNameByNo(String itemNo)throws ServiceException;
	
	/**
	 * 根据结算类型生成单据编号
	 * @return
	 */
	public String generateBillNo(int billType) throws ServiceException;

	String generateBillNo(String no, String tableName, String prefix)
			throws ServiceException;

	public int findShopCount(Map<String, Object> params)throws ServiceException;

	public List<ShopVo> findShopByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws ServiceException;

	public int selectPageOrganizationCount(Map<String, Object> params)throws ServiceException;

	public List<LookupVo> selectPageOrganizationList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;
	
	/**
	 * 根据公司编号，查询大区编码，再把大区编码、公司编号及codingRule 产生的编号拼接成单据编号
	 * @param companyNo 公司编号
	 * @param requestId coding_rule 表的requestId字段值
	 * @return 大区编码 + 公司编号 + codingRule 生成的序列（年份由参数的4位截取后面2位：2015 取15）
	 * @throws ServiceException
	 * @author wangyj
	 */
	public String getNewBillNoCompanyNo(String companyNo, String requestId) throws ServiceException ;
	
	/**
	 * 根据公司编号，查询大区编码，再把大区编码、公司编号及codingRule 产生的编号拼接成单据编号
	 * @param companyNo 公司编号
	 * @param requestId coding_rule 表的requestId字段值
	 * @return 大区编码 + 公司编号 + codingRule 生成的序列（年份由参数的4位截取后面2位：2015 取15）
	 * @throws ServiceException
	 * @author wangyj
	 */
	public String getNewBillNoCompanyNo(String companyNo,String shopNo,String requestId) throws ServiceException ;
	
	public int selectItemExtendsCount(Map<String, Object> params)throws ServiceException ;

	public List<ItemDto> selectItemExtendsList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException ;
	
	/**
	 * 获取批量生成的首增总部价总数
	 */
	public int countHQFirstNewNeedRuleMatchItems(Map<String, Object> params)throws ServiceException ;
	
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryHQFirstNewNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	/**
	 * 获取批量生成的首增总部价总数
	 */
	public int countQuotationRuleMatchItems(Map<String, Object> params)throws ServiceException ;
	
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryQuotationRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	public List<ZoneInfo> findPriceZones(Map<String, Object> params) throws ServiceException;

	public List<RegionCostMaintain> getRegionNeedRuleMatchItems(SimplePage page, Map<String, Object> queryParams)throws ServiceException;

	public List<HeadquarterCostMaintain> getHQNeedRuleMatchItems(SimplePage page, Map<String, Object> queryParams)throws ServiceException;
	
}
