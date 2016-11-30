package cn.wonhigh.retail.fas.dal.database;

import cn.wonhigh.retail.fas.common.model.InsideBizTypeDetail;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-12 16:39:33
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
public interface InsideBizTypeDetailMapper extends BaseCrudMapper {
	
	public void deleteByTypeNo(InsideBizTypeDetail insideBizTypeDetail);
}