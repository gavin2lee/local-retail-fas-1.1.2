package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
public interface BillBacksectionSplitDtlManager extends BaseCrudManager {
	
	/**
	 * 查询合计
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	BillBacksectionSplitDtl selectTotalSum(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 批量新增
	 * @param list
	 * @throws ServiceException 
	 */
	int addBacksectionSplitDtlByBatch(BillShopBalance balance) throws ServiceException;
	
	int selectShopBlanaceAccountCount(Map<String, Object> params) throws ManagerException;

	List<BillBacksectionSplitDtl> selectShopBlanaceAccountByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	/**
	 * 批量新增明细数据
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	int selectAddInsertDtl(Map<String,Object> params) throws ManagerException;

	/**
	 * 查询出新增明细数据
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<BillBacksectionSplitDtl> selectAddDtl(Map<String,Object> params) throws ManagerException;
	
	int batchAdd(List<BillBacksectionSplitDtl> list,BillShopBalance balance) throws ManagerException;
	
	int batchAdds(List<BillBacksectionSplitDtl> list,BillShopBalance balance) throws ManagerException;
}