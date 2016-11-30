package cn.wonhigh.retail.fas.dal.database;

import cn.wonhigh.retail.fas.common.model.MallBalanceDiffType;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-24 11:58:32
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
public interface MallBalanceDiffTypeMapper extends BaseCrudMapper {
	
	public String getMaxCode(MallBalanceDiffType  mallBalanceDiffType);
}