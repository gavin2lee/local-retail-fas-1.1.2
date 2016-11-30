package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.TicketCollect;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2014-11-13 15:44:38
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
public interface TicketCollectMapper extends BaseCrudMapper {
	
	/**
	 * 根据参数获取
	 * @param params
	 * @return
	 */
	public TicketCollect selectTicketCollectByTicketNo(@Param("params") Map<String, Object> params);
}