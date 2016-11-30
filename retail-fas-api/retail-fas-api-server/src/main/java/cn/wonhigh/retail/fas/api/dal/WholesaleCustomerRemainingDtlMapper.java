package cn.wonhigh.retail.fas.api.dal;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-20 09:51:28
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
public interface WholesaleCustomerRemainingDtlMapper extends BaseCrudMapper {
	
	int updateTypeByBillNo(@Param("params") Map<String,Object> params);
}