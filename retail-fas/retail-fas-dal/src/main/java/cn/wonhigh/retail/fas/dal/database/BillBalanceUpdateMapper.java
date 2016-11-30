package cn.wonhigh.retail.fas.dal.database;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;


/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 14:50:55
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
public interface BillBalanceUpdateMapper{

	int updateDetailBalanceNo(BillBalance bill);

	int updateDeductionBalanceNo(BillBalance bill);

	int clearDetailBalanceNo(BillBalance bill);

	int clearDeductionBalanceNo(@Param("balanceNo")String balanceNo);

	int updateDetailBalanceById(BalanceDetailDto bill);

	int updateDeductionBalanceById(OtherDeduction deduction);

	
}