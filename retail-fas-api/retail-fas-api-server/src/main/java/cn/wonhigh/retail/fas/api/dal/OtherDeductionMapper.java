package cn.wonhigh.retail.fas.api.dal;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-30 14:25:20
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
public interface OtherDeductionMapper extends BaseCrudMapper {
	
	/**
	 * 根据出库单号 查询返利,其他费用 
	 * @param bill
	 * @return
	 */
	OtherDeduction findRebateOtherPrcie(@Param("billNo") String billNo);

	
	/**
	 * 根据出库单号删除扣项
	 * @param bill
	 * @return
	 */
	int deleteByBillNo(@Param("billNo") String billNo);

	
	
}