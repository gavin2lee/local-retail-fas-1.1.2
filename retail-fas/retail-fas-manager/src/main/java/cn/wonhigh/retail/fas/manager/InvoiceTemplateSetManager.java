package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
public interface InvoiceTemplateSetManager extends BaseCrudManager {
	
	int save(InvoiceTemplateSet invoiceTemplateSet, Map<CommonOperatorEnum, List<InvoiceTemplateSetDtl>> params) 
			throws ManagerException;
	
	public int remove(String[] ids) throws ManagerException ;
	
	public <InvoiceTemplateSet> int deleteByModel(InvoiceTemplateSet  invoiceTemplateSet);
	
	public  int checkIsUseData(String invoiceTempNo);
}