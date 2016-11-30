package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.service.SupplierService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@Service("supplierManager")
class SupplierManagerImpl extends BaseCrudManagerImpl implements SupplierManager {
    @Resource
    private SupplierService supplierService;

    @Override
    public BaseCrudService init() {
        return supplierService;
    }

	@Override
	public List<Supplier> findByNoGroup(SimplePage page,Map<String, Object> params) {
		// TODO Auto-generated method stub
		return supplierService.findByNoGroup(page,params);
	}

	@Override
	public int findByNoGroupCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return supplierService.findByNoGroupCount(params);
	}
}