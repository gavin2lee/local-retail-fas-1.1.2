package cn.wonhigh.retail.fas.dal.database;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-11 11:15:11
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
public interface OfficialItemMapper extends BaseCrudMapper {
	
	int deleteByItemCodeAndQuotoNo(@Param("itemCode") String itemCode, @Param("quoteNo") String quoteNo);

	int deleteAll();
	
}