package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.InvoiceCategoryCommonDto;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-14 13:59:47
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
public interface InvoiceTemplateSetDtlMapper extends BaseCrudMapper {
	
	/**
	 * 查询当前时间最新单号(用于生成单号)
	 * @param invoiceTemplateSetDtl  查询条件
	 * @return String 最新单号
	 */
	public String selectInvoiceTemplateDtlMaxBillNo(InvoiceTemplateSetDtl  invoiceTemplateSetDtl);
	
	/**
	 * 根据店铺分组获取大类信息
	 * @param params
	 * @return
	 */
	List<InvoiceCategoryCommonDto> selectCateInfo(@Param("params")Map<String, Object> params);
}