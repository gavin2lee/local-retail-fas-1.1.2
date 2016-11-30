package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.dal.database.CustomerReceRelDtlMapper;
import cn.wonhigh.retail.fas.dal.database.CustomerReceRelMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-04 13:40:23
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
@Service("customerReceRelService")
class CustomerReceRelServiceImpl extends BaseCrudServiceImpl implements CustomerReceRelService {
    @Resource
    private CustomerReceRelMapper customerReceRelMapper;

    @Resource
    private CustomerReceRelDtlMapper customerReceRelDtlMapper;
    
    @Override
    public BaseCrudMapper init() {
        return customerReceRelMapper;
    }
    
	@Override
	public <ModelType> int deleteById(ModelType modelType) throws ServiceException {
		try {
			super.deleteById(modelType);
			CustomerReceRel obj = (CustomerReceRel)modelType;
			customerReceRelDtlMapper.deleteByRefId(obj.getId());
			return 1;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
}