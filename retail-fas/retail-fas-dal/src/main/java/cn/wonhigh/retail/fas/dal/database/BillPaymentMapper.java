package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.BillPayment;
import cn.wonhigh.retail.fas.common.model.PayRelationship;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 11:02:16
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
public interface BillPaymentMapper extends BaseCrudMapper {

	void updateInvoicePayment(BillInvoice invoice);

	int verify(BillPayment obj);

	List<BillPayment> selectFooter(@Param("params")Map<String, Object> params);

	void clearRelationPaymentInfo(@Param("params")Map<String, Object> params);
	
	void clearDeductionPaymentInfo(@Param("params")Map<String, Object> params);

	void clearAdjustPaymentInfo(@Param("params")Map<String, Object> params);
	
	void updateRelationPaymentInfo(@Param("params")Map<String, Object> params);

	void updateDeductionPaymentInfo(@Param("params")Map<String, Object> params);

	void updateAdjustPaymentInfo(@Param("params")Map<String, Object> params);
	
	List<PayRelationship> selectPayRealtionList(@Param("params")Map<String, Object> params);

	

}