package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SupplierRateSet;
import cn.wonhigh.retail.fas.dal.database.SupplierRateSetMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-05-05 09:10:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("supplierRateSetService")
class SupplierRateSetServiceImpl extends BaseCrudServiceImpl implements SupplierRateSetService {
    @Resource
    private SupplierRateSetMapper supplierRateSetMapper;

    @Override
    public BaseCrudMapper init() {
        return supplierRateSetMapper;
    }
    
    @Override
    public List<SupplierRateSet> selectByParams(Map<String, Object> params) 
    		throws ServiceException{
    	return this.supplierRateSetMapper.selectByParams(params);
    }

	@Override
	public Integer findCount() throws ServiceException {
		// TODO Auto-generated method stub
		return this.supplierRateSetMapper.findCount();
	}

	@Override
	public List<SupplierRateSet> findSupplierByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String,Object> params){
		return this.supplierRateSetMapper.findSupplierByPage(page, sortColumn, sortOrder, params);
	}

	@Override
	public boolean exist(String supplierNo) throws ServiceException {
		return this.supplierRateSetMapper.exist(supplierNo)>0;
	}
}