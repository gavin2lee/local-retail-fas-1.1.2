package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ContractDiscount;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-24 14:59:22
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
public interface PEUtilMapper {
	
	 BigDecimal selectTagPriceByItemNo(@Param("itemNo") String itemNo);
	
	 ContractDiscount selectLastContractDiscount(@Param("params") Map<String, Object> params);

	Date selectAsnDueDateByBillNo(@Param("billNo")String billNo);

	Date selectAdjustDueDateByBillNo(@Param("billNo")String billNo);
	
}