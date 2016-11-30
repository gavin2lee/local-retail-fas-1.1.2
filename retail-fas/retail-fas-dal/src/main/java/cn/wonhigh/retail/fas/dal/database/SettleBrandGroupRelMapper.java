package cn.wonhigh.retail.fas.dal.database;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 品牌组与品牌的关联
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
public interface SettleBrandGroupRelMapper extends BaseCrudMapper {
	
	int deleteByGroupNo(String groupNo);
}