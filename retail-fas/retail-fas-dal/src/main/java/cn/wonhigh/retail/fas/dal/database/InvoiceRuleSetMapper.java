package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.InvoiceRuleSet;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-14 10:48:39
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
public interface InvoiceRuleSetMapper extends BaseCrudMapper {
	
	/**
	 * 查询当前时间最新结算单号(用于生成结算单号)
	 * @param invoiceRuleSet  查询条件
	 * @return String 最新结算单号
	 */
	public String selectInvoiceRuleMaxBillNo(InvoiceRuleSet invoiceRuleSet);
	
	/**
	 * 根据店铺编码获取开票名称
	 * @param params
	 * @return
	 */
	public InvoiceRuleSet selectInvoiceNameByShopNo(@Param("params")Map<String,Object> params);
}