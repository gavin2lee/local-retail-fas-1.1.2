package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-07-11 17:43:44
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
public interface PricingRangeMapper extends BaseCrudMapper {

	Integer getPricingItemCount(@Param("params")Map<String,Object> params);
}