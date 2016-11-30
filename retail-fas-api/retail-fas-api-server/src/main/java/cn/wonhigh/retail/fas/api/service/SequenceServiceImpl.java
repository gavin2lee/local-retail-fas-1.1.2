package cn.wonhigh.retail.fas.api.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.CodingRuleMapper;
import com.yougou.logistics.base.service.CodingRuleServiceImpl;

@Service("sequenceServiceImpl")
public class SequenceServiceImpl extends CodingRuleServiceImpl implements SequenceService {
	
	@Resource
	protected CodingRuleMapper codingRuleMapper;
	
	@Override
	public CodingRuleMapper init() {
		return codingRuleMapper;
	}
	
	@Override
	public int getId()throws ServiceException{
		try {
			String serialNo = super.getSerialNo("public").substring(2);
			return Integer.parseInt(serialNo);
		} catch (ServiceException e) {
			throw new ServiceException("",e);
		}
	}
}
