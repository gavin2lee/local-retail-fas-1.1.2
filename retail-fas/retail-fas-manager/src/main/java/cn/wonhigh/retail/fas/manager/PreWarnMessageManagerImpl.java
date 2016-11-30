package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.PreWarnMessage;
import cn.wonhigh.retail.fas.service.PreWarnMessageService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:49:31
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
@Service("preWarnMessageManager")
class PreWarnMessageManagerImpl extends BaseCrudManagerImpl implements PreWarnMessageManager {
    @Resource
    private PreWarnMessageService preWarnMessageService;

    @Override
    public BaseCrudService init() {
        return preWarnMessageService;
    }

	@Override
	public List<PreWarnMessage> selectWarnInfoByType(String type)throws ManagerException {
		try {
			return preWarnMessageService.selectWarnInfoByType(type);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}