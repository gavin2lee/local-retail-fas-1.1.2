package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.BillStatus;

import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-26 17:45:45
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
public interface BillStatusService extends BaseCrudService {
	/**
	 * 根据单据编码查询对象
	 * @param billNo
	 * @return
	 */
	public BillStatus findByBillNo(String billNo,String payName);
}