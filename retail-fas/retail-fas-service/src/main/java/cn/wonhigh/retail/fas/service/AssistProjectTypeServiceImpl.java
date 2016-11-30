package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.dal.database.AssistProjectTypeMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
@Service("assistProjectTypeService")
class AssistProjectTypeServiceImpl extends BaseServiceImpl implements AssistProjectTypeService {
    @Resource
    private AssistProjectTypeMapper assistProjectTypeMapper;

    @Override
    public BaseCrudMapper init() {
        return assistProjectTypeMapper;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int removeAssistProjectType(String[] codeList) throws ServiceException {
		try {
			int iCount = 0;
			for (int i = 0; i < codeList.length; i++) {
				// 删除辅助项目类型
				iCount = assistProjectTypeMapper.deleteAssistProjectType(codeList[i]);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}