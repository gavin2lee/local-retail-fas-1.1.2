package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.CodingRuleMapper;
import com.yougou.logistics.base.service.CodingRuleServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-27 16:17:53
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
@Service("codingRuleService")
class BalanceCodingRuleServiceImpl extends CodingRuleServiceImpl{
    @Resource
    private CodingRuleMapper codingRuleMapper;
     
    @Override
    public CodingRuleMapper init() {
        return codingRuleMapper;
    }
  
    /* 
     * 获取编号
     * 使用自己单独的事务REQUIRES_NEW，防止被加入外部事务，被外部事务的锁或响应时间影响本接口的性能
     * @return String 
     * 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public String getSerialNo(String requestId) throws ServiceException {
    	String serialNo = super.getSerialNo(requestId);
		return serialNo;
	}
    
}