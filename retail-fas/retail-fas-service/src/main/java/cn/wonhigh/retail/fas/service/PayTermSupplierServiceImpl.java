package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.PayTermSupplier;
import cn.wonhigh.retail.fas.dal.database.PayTermSupplierMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("payTermSupplierService")
class PayTermSupplierServiceImpl extends BaseCrudServiceImpl implements PayTermSupplierService {
    @Resource
    private PayTermSupplierMapper payTermSupplierMapper;

    @Override
    public BaseCrudMapper init() {
        return payTermSupplierMapper;
    }
    
    @Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			PayTermSupplier obj = (PayTermSupplier) modelType;
			obj.setId(UUIDHexGenerator.generate());
			return payTermSupplierMapper.insertSelective(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}