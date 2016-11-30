package cn.wonhigh.retail.fas.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;
import cn.wonhigh.retail.fas.service.SettlePathBrandRelService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@Service("settlePathBrandRelManager")
class SettlePathBrandRelManagerImpl extends BaseCrudManagerImpl implements SettlePathBrandRelManager {
   
	@Resource
    private SettlePathBrandRelService settlePathBrandRelService;

    @Override
    public BaseCrudService init() {
        return settlePathBrandRelService;
    }
    
    @Override
   	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ManagerException.class)
   	public int save(String pathNo, List<SettlePathBrandRel> list) 
   			throws ManagerException {
   		try {
   			return this.settlePathBrandRelService.save(pathNo, list);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
   	}
}