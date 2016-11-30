package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-06-03 16:57:11
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
public interface InvoiceApplyConfirmDtlManager extends BaseCrudManager {
	
	/**
     * 新增信息
     * @param list
     * @param createUser
     * @return
     * @throws ServiceException
     */
    public int addInvoiceApplyConfirmDtl(List<OrderDtlDto> list,String createUser) throws ManagerException ;
    
    /**
     * 根据单据号及产品编号，修改状态或开票申请号
     * @param list
     * @param createUser
     * @return
     * @throws ManagerException
     */
    public int updateByOrderNo(List<OrderDtlDto> list,String createUser) throws ManagerException ;
}