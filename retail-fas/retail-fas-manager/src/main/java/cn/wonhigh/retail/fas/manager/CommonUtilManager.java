package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ShopVo;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;


/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
public interface CommonUtilManager {
	
	/**
	 * 获取已维护总部价格的商品
	 * @return
	 */
	public List<String> queryHeadquarterMaintainedItems() throws ManagerException;
	
	/**
	 * 获取已维护地区价格的商品
	 * @return
	 */
	public List<String> queryRegionMaintainedItems(String zoneNo) throws ManagerException;
	
	/**
	 * 需要生成总部价格的商品列表
	 * @return
	 */
	public int countHQNeedRuleMatchItems(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 需要生成地区价格的商品列表
	 * @return
	 */
	public int countRegionNeedRuleMatchItems(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 需要生成总部价格的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryHQNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	/**
	 * 需要生成地区价格的商品列表
	 * @return
	 */
	public List<RegionCostMaintain> queryRegionNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	/**
	 * 返回结算方式名称
	 * @param settleMentMethodNo
	 * @return
	 * @throws ManagerException 
	 */
	public String getSettleName(String settleMentMethodNo) throws ManagerException;
	
	/**
	 * 返回币种名称
	 * @param currencyCode
	 * @return
	 * @throws ManagerException 
	 */
	public String getCurrencyName(String currencyCode) throws ManagerException;
	
	/**
	 * 返回单据状态名称
	 * @param statusNo
	 * @return
	 * @throws ManagerException 
	 */
	public String getStatusName(String statusNo) throws ManagerException ;
	
	/**
	 * 返回货品单据状态名称
	 * @param statusNo
	 * @return
	 * @throws ManagerException 
	 */
	public String getBillStatusName(String statusNo) throws ManagerException ;
	
	/**
	 * 返回单据类型名称
	 * @param statusNo
	 * @return
	 * @throws ManagerException 
	 */
	public String getBillTypeName(String typeNo) throws ManagerException ;
	
	/**
	 * 返回请款单单据状态名称
	 * @param statusNo
	 * @return
	 * @throws ManagerException 
	 */
	public String getAskPaymentStatusName(String statusNo) throws ManagerException ;
	
	public String generateBillNo(int typeNo)throws ManagerException ;

	public int findShopCount(Map<String, Object> params)throws ManagerException ;

	public List<ShopVo> findShopByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws ManagerException ;

	public int selectPageOrganizationCount(Map<String, Object> params)throws ManagerException ;

	public List<LookupVo> selectPageOrganizationList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException ;
	
	public int selectItemExtendsCount(Map<String, Object> params)throws ManagerException ;

	public List<ItemDto> selectItemExtendsList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException ;
	
	/**
	 * 获取批量生成的首增总部价总数
	 */
	public int countHQFirstNewNeedRuleMatchItems(Map<String, Object> params)throws ManagerException ;
	
	/**
	 * 需要生成变价部分的的商品列表
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryHQFirstNewNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	
	/**
	 * 获取依据牌价来批量生成的总部价商品
	 */
	public int countQuotationRuleMatchItems(Map<String, Object> params)throws ManagerException ;
	
	/**
	 * 获取依据牌价来批量生成的总部价商品
	 * @return
	 */
	public List<HeadquarterCostMaintain> queryQuotationRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	public List<ZoneInfo> findPriceZones(Map<String, Object> params)throws ManagerException ;
	
}