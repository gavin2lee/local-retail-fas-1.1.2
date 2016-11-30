package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CostCategorySetting;
import cn.wonhigh.retail.fas.service.CostCategorySettingService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-08-28 10:48:25
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
@Service("costCategorySettingManager")
class CostCategorySettingManagerImpl extends BaseCrudManagerImpl implements CostCategorySettingManager {
    @Resource
    private CostCategorySettingService costCategorySettingService;


	 @Resource
    private CodingRuleService codingRuleService;  
	 
    @Override
    public BaseCrudService init() {
        return costCategorySettingService;
    }
    
    
    @Override
	public <ModelType> int add(ModelType modelType) throws ManagerException {
		try {
			return this.costCategorySettingService.add(modelType);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}

	}
    
    public int save(CostCategorySetting costCategorySet)  throws ManagerException {
		try {
			String billNo = "";
			billNo = codingRuleService.getSerialNo(CostCategorySetting.class.getSimpleName());
			costCategorySet.setCostCateNo(billNo);
			costCategorySet.setCode("001");
			return this.costCategorySettingService.add(costCategorySet);   
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}