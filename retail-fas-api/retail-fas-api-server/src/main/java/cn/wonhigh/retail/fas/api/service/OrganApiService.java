package cn.wonhigh.retail.fas.api.service;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 组织机构 
 * @author yang.y
 * @date  2014-10-13 14:01:15
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
public interface OrganApiService {
	
	/**
	 * 通过订货单位查询管理机构
	 * @param orderUnitNo 订货单位编号
	 * @return 管理机构对象
	 * @throws ServiceException 异常
	 */
	Organ selectByOrderUnitNo(String orderUnitNo) throws ServiceException;
	
	/**
	 * 通过结算公司编码,获取公司所在的大区信息
	 * @param companyNo 结算公司编码
	 * @return 大区
	 */
	ZoneInfo getZoneInfoByCompanyNo(@Param("companyNo")String companyNo);
}