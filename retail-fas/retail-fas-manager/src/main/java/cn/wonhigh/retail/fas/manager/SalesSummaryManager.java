package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.common.model.SalesSummary;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.AuthorityParams;
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
public interface SalesSummaryManager extends BaseCrudManager {
	
	SalesSummary selectSalesSummaryCount(Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;

	List<SalesSummary> selectSalesSummary(SimplePage page, @Param("params") Map<String,Object> params) throws ManagerException ;
	
    int selectCount(Map<String, Object> params) throws ManagerException ;
}