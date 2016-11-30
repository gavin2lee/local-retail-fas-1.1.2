package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BrandUnit;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2014-12-05 16:36:16
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
public interface BrandUnitService extends BaseCrudService {

	/**
	 * @param params
	 * @return
	 */
	String findBrands(Map<String, Object> params);
	
	/**
	 * 根据品牌编号查询其所属品牌部
	 * @param brandNo
	 * @return
	 */
	BrandUnit getBrandUnitByBrand(String brandNo) throws ServiceException;
}