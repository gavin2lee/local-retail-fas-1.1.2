package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillInvoiceApplyDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-16 14:05:12
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
public interface BillInvoiceApplyManager extends BaseCrudManager {

	int save(BillInvoiceApply model, Map<CommonOperatorEnum, List<BillInvoiceApplyDtl>> params)
			throws ManagerException;
}