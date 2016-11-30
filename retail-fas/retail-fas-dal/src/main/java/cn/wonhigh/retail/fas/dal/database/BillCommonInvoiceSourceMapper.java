package cn.wonhigh.retail.fas.dal.database;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-05 15:30:06
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
public interface BillCommonInvoiceSourceMapper extends BaseCrudMapper {
	
	public void deleteByBillNo(@Param("billNo") String billNo);
}