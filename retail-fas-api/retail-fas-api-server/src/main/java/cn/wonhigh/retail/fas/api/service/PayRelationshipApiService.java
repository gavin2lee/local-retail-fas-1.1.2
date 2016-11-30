package cn.wonhigh.retail.fas.api.service;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;

import com.yougou.logistics.base.common.exception.ServiceException;

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
public interface PayRelationshipApiService  {

	void handerPEValence (BillBalanceApiDto dto) throws ServiceException;

	void invalid(BillHeaderApiDto billHeaderApiDto)throws ServiceException;

	void changePE(BillBalanceApiDto dto)throws ServiceException;

	int selectBalanceCountByBillNo(String billNo)throws ServiceException;

}