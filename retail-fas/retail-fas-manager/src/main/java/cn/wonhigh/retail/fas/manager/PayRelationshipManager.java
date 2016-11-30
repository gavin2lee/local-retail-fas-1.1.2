package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface PayRelationshipManager extends BaseCrudManager {

	/**
	 * 查询单据明细数量
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	int findItemCount(Map<String, Object> params)throws ManagerException;

	/**
	 * 查询单据明细集合
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<PayRelationship> findItemList(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)throws ManagerException;
	
	/**
	 * 查询单据footer
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<PayRelationship> findFooter(Map<String, Object> params)throws ManagerException;

	/**
	 * 更新到期日
	 * @param params
	 * @throws ManagerException
	 */
	int updateDueDate(Map<String, Object> params)throws ManagerException;
	
	/**
	 * 查询返利总计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	PayRelationship findReturnProfitCount(Map<String,Object> params) throws ManagerException;

	/**
	 * 分页查询返利明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<PayRelationship> findReturnProfitList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;

	/**
	 * 更新折扣
	 * @param params
	 * @throws ManagerException
	 */
	int updateDiscountNew(Map<String, Object> params)throws ManagerException, InterruptedException;

	/**
	 * 更新价格
	 * @param params
	 * @throws ManagerException
	 */
	int updateCostNew(Map<String, Object> params)throws ManagerException, InterruptedException;
	
	/**
	 * 导入金额
	 * @param params
	 * @throws ManagerException
	 */
	List<PayRelationship> doImportCostNew(List<ValidateResultVo> listValidate)throws Exception;

	/**
	 * 导入折扣
	 * @param params
	 * @throws ManagerException
	 */
	List<PayRelationship> doImportDiscountNew(List<ValidateResultVo> listValidate)throws Exception;

	/**
	 * 导入牌价
	 * @param listValidate
	 * @return
	 */
	List<PayRelationship> doImportTagPriceNew(List<ValidateResultVo> listValidate)throws ManagerException;
	
	/**
	 * 生成结算单
	 * @param params
	 * @return
	 */
	int generateBalanceNew(Map<String, Object> params)throws ManagerException;

	/**
	 * 导入收购折扣
	 * @param listValidate
	 * @return
	 * @throws ManagerException
	 */
	List<PayRelationship> doImportDiscountBuyNew(List<ValidateResultVo> listValidate)throws Exception;


	
}