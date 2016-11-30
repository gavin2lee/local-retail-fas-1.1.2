package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSplitData;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillSplitDataService extends BaseCrudService {
	
	int selectBillAsnCount(Map<String,Object> params) throws ServiceException;
	
	int selectBillReturnCount(Map<String,Object> params) throws ServiceException;

	List<BillSplitData> findBillReturn(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) 
			throws ServiceException;
}