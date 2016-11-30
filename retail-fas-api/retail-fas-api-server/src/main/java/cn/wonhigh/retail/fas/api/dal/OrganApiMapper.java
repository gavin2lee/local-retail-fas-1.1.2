package cn.wonhigh.retail.fas.api.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.common.dto.OrganDto;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;

/**
 * 管理城市 
 * @author yang.y
 * @date  2014-10-30 10:22:59
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
public interface OrganApiMapper {

	/**
	 * 通过订货单位查询管理机构
	 * @param orderUnitNo 订货单位编号
	 * @return 管理机构对象
	 * @throws ServiceException 异常
	 */
	List<OrganDto> selectByOrderUnitNo(@Param("orderUnitNo")String orderUnitNo) throws Exception;
	
	/**
	 * 通过结算公司编码,获取公司所在的大区信息
	 * @param companyNo 结算公司编码
	 * @return 大区
	 */
	List<ZoneInfo> getZoneInfoByCompanyNo(@Param("companyNo")String companyNo);
}