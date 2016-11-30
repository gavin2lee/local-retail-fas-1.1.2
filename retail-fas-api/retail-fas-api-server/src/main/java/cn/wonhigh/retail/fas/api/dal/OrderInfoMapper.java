package cn.wonhigh.retail.fas.api.dal;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.dto.OrderInfoDto;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-23 14:37:32
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
public interface OrderInfoMapper extends BaseCrudMapper {
	/**
	 * 获取销售订单的状态
	 * @param orderInfoDto-订单实体
	 * @return
	 */
	public Integer getStatus(@Param("orderInfoDto")OrderInfoDto orderInfoDto) ;
	
	/**
	 * 根据订单号查询POS内购订单是否已做了财务确认
	 * @param orderInfoDto
	 * @return 确认状态
	 */
	public Integer findInnerBuyConfrigStatus(@Param("orderInfoDto")OrderInfoDto orderInfoDto) ;
}