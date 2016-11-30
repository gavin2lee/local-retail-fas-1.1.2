package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PriceQuotationList;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-10-27 16:10:00
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
public interface PriceQuotationListMapper extends BaseCrudMapper {
	
	/**
	 * 根据机构编号、货号查询牌价
	 * @param params
	 * @return
	 */
	PriceQuotationList getPriceQuotationList(@Param("params") Map<String, Object> params);
}