package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
public interface BillAskPaymentService extends BaseCrudService {

	public int generateBillBybalance(List<BillBalance> list, String loginName)throws ServiceException;

	public int verify(BillAskPayment obj)throws ServiceException;

	public List<BillAskPayment> selectFooter(Map<String, Object> params)throws ServiceException;

	/**
	 * @param bill
	 * @return
	 * @throws ServiceException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public BillAskPayment addMainForm(BillAskPayment bill) throws ServiceException;
}