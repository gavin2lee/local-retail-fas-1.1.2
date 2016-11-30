package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.CurrencyManagement;

import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-09-01 17:18:41
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
public interface CurrencyManagementService extends BaseCrudService {
	/**
	 * 查询默认币种
	 * @return
	 */
	public CurrencyManagement findDefaultCurrency();
}