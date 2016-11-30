package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author wangm
 * @date 2016-03-18 16:58:06
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface BAReceiptService {

	int findReceiptCount(Map<String, Object> params) throws ServiceException;

	List<BAReceiptDto> findReceiptList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException;

	List<BAReceiptDto> findReceiptFooter(Map<String, Object> params) throws ServiceException;

	int updateCost(BAReceiptDto baReceiptDto) throws ServiceException;

	int updateRate(BAReceiptDto baReceiptDto) throws ServiceException;

	int updateBuyBalanceNo(Map<String, Object> params) throws ServiceException;

	String[] getItemNos(String originalBillNos) throws ServiceException;

	void updateBuyBalanceNoByItem(Map<String, Object> params) throws ServiceException;
	
	List<BAReceiptDto> getReceiptForUpdate(Map<String, Object> params) throws ServiceException;
	
	void updateBuyBalanceTagPrice(Map<String, Object> params) throws ServiceException;
}