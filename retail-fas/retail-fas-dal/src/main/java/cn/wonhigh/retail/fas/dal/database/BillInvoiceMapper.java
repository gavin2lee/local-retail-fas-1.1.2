package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillInvoice;

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
public interface BillInvoiceMapper extends BaseCrudMapper {

	void updateBalanceInvoiceNo(BillInvoice obj);

	void clearBalanceInvoiceNo(@Param("invoiceNo") String invoiceNo);

	int verify(BillInvoice obj);

	List<BillInvoice> selectFooter(@Param("params")Map<String, Object> params);

	/**
	 * @param billNo
	 */
	void clearInvoiceNoFlag(@Param("invoiceNoFlag")String invoiceNoFlag);

	void updateDeductionDueDate(@Param("params")Map<String, Object> params);

	void updatePayRelationDueDate(@Param("params")Map<String, Object> params);
}