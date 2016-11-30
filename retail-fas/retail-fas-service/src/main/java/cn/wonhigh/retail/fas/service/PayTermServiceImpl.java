package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.PayTerm;
import cn.wonhigh.retail.fas.dal.database.PayTermMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:07
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
@Service("payTermService")
class PayTermServiceImpl extends BaseCrudServiceImpl implements PayTermService {
    @Resource
    private PayTermMapper payTermMapper;

    @Resource
    private CodingRuleService codingRuleService;
    
    @Override
    public BaseCrudMapper init() {
        return payTermMapper;
    }
    
    @Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			PayTerm obj = (PayTerm) modelType;
			obj.setId(UUIDHexGenerator.generate());
			obj.setTermNo(codingRuleService.getSerialNo("FK"));	
			return payTermMapper.insertSelective(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}