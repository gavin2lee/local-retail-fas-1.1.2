package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillAskPayment;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-28 13:49:07
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
public interface BillAskPaymentMapper extends BaseCrudMapper {

	int clearBalanceAskPaymentNo(@Param("askPaymentNo") String askPaymentNo ,@Param("balanceType") int balanceType);
	
	void updateBalanceAskPaymentNo(BillAskPayment obj);

	int verify(BillAskPayment obj);
	
	List<BillAskPayment> selectFooter(@Param("params")Map<String, Object> params);

}