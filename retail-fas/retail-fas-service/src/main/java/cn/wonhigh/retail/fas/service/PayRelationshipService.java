package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ContractDiscount;
import cn.wonhigh.retail.fas.common.model.PayRelationship;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:06
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
public interface PayRelationshipService extends BaseCrudService {

	/**
	 * 更新buy总部-厂商到货单价格
	 * @param payRelationship
	 * @return
	 * @throws ServiceException
	 */
	int updateBuyBalanceCost(PayRelationship payRelationship) throws ServiceException;

	/**
	 * 更新sale总部-地区到货单价格
	 * @param payRelationship
	 * @return
	 * @throws ServiceException
	 */
	int updateSaleBalanceCost(PayRelationship payRelationship) throws ServiceException;

	/**
	 * 更新buy验收单价格
	 * @param payRelationship
	 * @return
	 * @throws ServiceException
	 */
	int updateBuyReceiptBalanceCost(PayRelationship payRelationship) throws ServiceException;

	/**
	 * 更新buy总部-地区到货单价格
	 * @param payRelationship
	 * @return
	 * @throws ServiceException
	 */
	int updateBuyZoneBalanceCost(PayRelationship payRelationship) throws ServiceException;
	
	/**
	 * 更新buy差异到货单价格
	 * @param payRelationship
	 * @return
	 * @throws ServiceException
	 */
	int updateAsnDiffBalanceCost(PayRelationship payRelationship) throws ServiceException;

	/**
	 * 获取厂商折扣
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	ContractDiscount getBuyDiscount(PayRelationship payRelationship) throws ServiceException;
	
	/**
	 * 获取公司折扣
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	ContractDiscount getSaleDiscount(PayRelationship payRelationship) throws ServiceException;

	/**
	 * 查询商品明细数量
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int findItemCount(Map<String, Object> params)throws ServiceException;

	/**
	 * 查询fotter列
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	List<PayRelationship> findFooter(Map<String, Object> params)throws ServiceException;

	/**
	 * 查询商品明细集合
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	List<PayRelationship> findItemList(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws ServiceException;

	/**
	 * 更新折扣
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int updateDiscount(PayRelationship payRelationship)throws ServiceException;
	
	/**
	 * 更新价格
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int updateCost(PayRelationship payRelationship)throws ServiceException;


	/**
	 * 更新对账标记
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int updateBalanceFlag(PayRelationship payRelationship)throws ServiceException;

	/**
	 * 更新到期日
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int updateDueDate(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 查询返利总计
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	PayRelationship findReturnProfitCount(Map<String,Object> params) throws ServiceException;

	/**
	 * 分页查询返利总明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	List<PayRelationship> findReturnProfitList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ServiceException;

	/**
	 * 查询需要更新折扣的单据
	 * @param page
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	List<PayRelationship> selectUpdateDiscountList(SimplePage page,Map<String, Object> params)throws ServiceException;

	/**
	 * 查询需要更新价格的单据
	 * @param page
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	List<PayRelationship> selectUpdateCostList(SimplePage page,Map<String, Object> params)throws ServiceException;

	/**
	 * 生成结算单
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	int generateBalanceNew(Map<String, Object> params)throws ServiceException;

	/**
	 * 导入牌价
	 * @param ship
	 * @return
	 * @throws ServiceException
	 */
	int importTagPrice(PayRelationship ship)throws ServiceException;

	/**
	 * 更新收购价格
	 * @param newShip
	 * @return
	 * @throws ServiceException
	 */
	int updateCostBuy(PayRelationship newShip)throws ServiceException;

}