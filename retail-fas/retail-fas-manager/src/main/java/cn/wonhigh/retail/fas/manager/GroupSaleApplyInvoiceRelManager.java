package cn.wonhigh.retail.fas.manager;

import cn.wonhigh.retail.fas.common.model.GroupSaleApplyInvoiceRel;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-29 11:56:15
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
public interface GroupSaleApplyInvoiceRelManager extends BaseCrudManager {
	
	/**
	 * 发票登记时或删除时，需要根据开票申请号回写或清空发票号
	 * @param groupSaleApplyInvoiceRel
	 * @throws Exception
	 */
	public void updateByInvoiceApplyNo(GroupSaleApplyInvoiceRel groupSaleApplyInvoiceRel)throws ManagerException;
}