package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ExceptionPriceCheckDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-06-05 16:02:08
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface ExceptionPriceBillManager extends BaseCrudManager {

	public int batchUpdatePriceSchedule() throws ManagerException;
	
	//后台脚本，批量检查异常价格单据
	public int findRegionPriceExceptionsCount(Map<String, Object> params) throws ManagerException;

	public List<ExceptionPriceCheckDto> findRegionPriceExceptionsByPage(SimplePage page,
				String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;

	public int findPurchasePriceExceptionsCount(Map<String, Object> params) throws ManagerException;

	public List<ExceptionPriceCheckDto> findPurchasePriceExceptionsByPage(SimplePage page,
				String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	public int findFasAllPriceExceptionsCount(Map<String, Object> params) throws ManagerException;

	public List<ExceptionPriceCheckDto> findFasAllPriceExceptionsByPage(SimplePage page,
				String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;

	public int findTagPriceExceptionsCount(Map<String, Object> params) throws ManagerException;

	public List<ExceptionPriceCheckDto> findTagPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	public int updateBuyBillRegionPrice(Map<String, Object> params) throws ManagerException;
	public int updateSaleBillRegionPrice(Map<String, Object> params) throws ManagerException;
	public int updateBuyBillPurchasePrice(Map<String, Object> params) throws ManagerException;

}