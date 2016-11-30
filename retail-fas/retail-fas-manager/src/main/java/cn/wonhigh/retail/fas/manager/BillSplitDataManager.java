package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSplitData;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 15:58:32
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
public interface BillSplitDataManager extends BaseCrudManager {
	
//	int billSplit(BillSplitData model, Map<String, Object> params) throws ManagerException;
//	
//	int selectBillAsnCount(Map<String,Object> params) throws ManagerException;
//	
	int selectBillReturnCount(Map<String,Object> params) throws ManagerException;
	
	List<BillSplitData> findBillReturn(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) 
			throws ManagerException;
//	
//	BillSplitLog findLog(BillSplitData model) throws ManagerException;
//	
//	void processLog(BillSplitLog log, BillSplitData model, int status, String failureReason) 
//			throws ManagerException;
	
	int billSplit(BillSplitData model) throws ManagerException;
}