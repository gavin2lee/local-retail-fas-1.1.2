package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
public interface BillCommonRegisterDtlManager extends BaseCrudManager {
	
	public int saveAll(Map<CommonOperatorEnum, List<BillCommonRegisterDtl>> params) throws ManagerException;
	
	/**
	 * 根据发票号获取发票明细
	 * @param invoiceNo
	 * @return
	 * @throws ManagerException
	 */
	public BillCommonRegisterDtl getInvoiceAmount(String invoiceNo)throws ManagerException;
}