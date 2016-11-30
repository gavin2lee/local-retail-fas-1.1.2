package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.PriceQuotationList;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-10-27 16:11:04
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
public interface PriceQuotationListService extends BaseCrudService {
	
	/**
	 * 根据机构编号、货号查询牌价
	 * @param organId
	 * @param itemNo
	 * @return
	 * @throws ServiceException
	 */
	PriceQuotationList getPriceQuotationList(String organId, String itemNo) throws ServiceException;
}