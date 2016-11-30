package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.dal.database.PreWarnTemplateMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
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
@Service("preWarnTemplateService")
class PreWarnTemplateServiceImpl extends BaseCrudServiceImpl implements PreWarnTemplateService {
    @Resource
    private PreWarnTemplateMapper preWarnTemplateMapper;

    @Override
    public BaseCrudMapper init() {
        return preWarnTemplateMapper;
    }

	@Override
	public Integer querySqlStatement(String sqlStr) throws ServiceException {
		return preWarnTemplateMapper.querySqlStatement(sqlStr);
	}
}