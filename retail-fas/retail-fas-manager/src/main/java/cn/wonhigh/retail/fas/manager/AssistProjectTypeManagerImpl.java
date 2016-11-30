package cn.wonhigh.retail.fas.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.AssistProjectTypeService;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-27 09:17:07
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
@Service("assistProjectTypeManager")
class AssistProjectTypeManagerImpl extends BaseCrudManagerImpl implements AssistProjectTypeManager {
    @Resource
    private AssistProjectTypeService assistProjectTypeService;

    @Override
    public BaseCrudService init() {
        return assistProjectTypeService;
    }

	@Override
	public int removeAssistProjectType(String[] codeList) throws ServiceException {
		return assistProjectTypeService.removeAssistProjectType(codeList);
	}
}