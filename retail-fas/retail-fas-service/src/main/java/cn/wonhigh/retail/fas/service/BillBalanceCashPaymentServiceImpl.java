package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBalanceCashPayment;
import cn.wonhigh.retail.fas.dal.database.BillBalanceCashPaymentMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@Service("billBalanceCashPaymentService")
class BillBalanceCashPaymentServiceImpl extends BaseCrudServiceImpl implements BillBalanceCashPaymentService {
    @Resource
    private BillBalanceCashPaymentMapper billBalanceCashPaymentMapper;
    @Resource
    private CodingRuleService codingRuleService;  
    @Override
    public BaseCrudMapper init() {
        return billBalanceCashPaymentMapper;
    }
    
    @Override
	public <ModelType> int add(ModelType modelType) throws ServiceException {
    	try {
   			validate(modelType);
   			String billNo = "";
   			billNo = codingRuleService.getSerialNo("RC");
   			BillBalanceCashPayment cashPayment=(BillBalanceCashPayment)modelType;
   			cashPayment.setBillNo(billNo);
   			return billBalanceCashPaymentMapper.insertSelective(modelType);
   		} catch (Exception e) {
   			throw new ServiceException("",e);
   		}
    };
}