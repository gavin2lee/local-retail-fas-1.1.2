package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-07-06 12:15:58
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
public interface WholesaleCustomerRemainingDtlService extends BaseCrudService {
	
	WholesaleCustomerRemainingDtl selectCustomerRemainingTotalByDate(Map<String, Object> params) throws ServiceException;
	
	WholesaleCustomerRemainingDtl setDtlPosition(WholesaleCustomerRemainingDtl remainingDtl) throws ServiceException, NumberFormatException, Exception;
	
	List<WholesaleCustomerRemainingDtl> selectDtlByPage(SimplePage page, Map<String, Object> params) throws ServiceException;
	
	/**
	 * 批量插入客户余额明细
	 * @param params
	 * @return
	 */
	int batchInsertRemainingDtl(List<WholesaleCustomerRemainingDtl> list) throws ServiceException;
}