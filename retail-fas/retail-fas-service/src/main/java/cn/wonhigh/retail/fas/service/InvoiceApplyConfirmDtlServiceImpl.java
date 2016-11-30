package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.InvoiceApplyConfirmDtl;
import cn.wonhigh.retail.fas.dal.database.InvoiceApplyConfirmDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
@Service("invoiceApplyConfirmDtlService")
class InvoiceApplyConfirmDtlServiceImpl extends BaseCrudServiceImpl implements InvoiceApplyConfirmDtlService {
    @Resource
    private InvoiceApplyConfirmDtlMapper invoiceApplyConfirmDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return invoiceApplyConfirmDtlMapper;
    }
    
    
    /**
	 * 根据单据号及产品编号，修改状态或开票申请号
	 * @param invoiceApplyConfirmDtl
	 * @return
	 */
	public int updateByOrderNo(InvoiceApplyConfirmDtl invoiceApplyConfirmDtl) throws ServiceException{
		try{
			return invoiceApplyConfirmDtlMapper.updateByOrderNo(invoiceApplyConfirmDtl);
		}catch(Exception e){
			throw new ServiceException();
		}
	}
	
	public int updateByInvoiceApplyNo(String invoiceApplyNo) throws ServiceException {
		try{
			return invoiceApplyConfirmDtlMapper.updateByInvoiceApplyNo(invoiceApplyNo);
		}catch(Exception e){
			throw new ServiceException();
		}
	}
	
	/**
	 * 根据开票申请号信息开票日期及金额
	 * @param invoiceApplyConfirmDtl
	 * @return
	 */
	public int updateConfirmDtlByApplyNo(InvoiceApplyConfirmDtl invoiceApplyConfirmDtl)throws ServiceException {
		try{
			return invoiceApplyConfirmDtlMapper.updateConfirmDtlByApplyNo(invoiceApplyConfirmDtl);
		}catch(Exception e){
			throw new ServiceException();
		}
	}
}