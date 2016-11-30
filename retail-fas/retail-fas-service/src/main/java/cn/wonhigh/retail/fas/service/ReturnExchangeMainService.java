package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ItemReturnRecord;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:13:46
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
public interface ReturnExchangeMainService extends BaseCrudService {
	/**
	 *发票号、日期写入销售订单、退换货单接口(仅限团购、员购销售订单)
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public void modifyOrderForInvoiceNoAndDate(Map<String, Object> params) throws ServiceException;
	
	public ItemReturnRecord findItemReturnCount(Map<String, Object> params) throws ServiceException;
	
	public List<ItemReturnRecord> findItemReturnList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ServiceException;
}