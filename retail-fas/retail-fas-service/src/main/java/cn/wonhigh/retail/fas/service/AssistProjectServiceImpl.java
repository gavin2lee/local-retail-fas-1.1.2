package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.AssistProjectMapper;

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
@Service("assistProjectService")
class AssistProjectServiceImpl extends BaseServiceImpl implements AssistProjectService {
    @Resource
    private AssistProjectMapper assistProjectMapper;

    @Override
    public BaseCrudMapper init() {
        return assistProjectMapper;
    }

	@Override
	public int findAssistProjects(String[] code_datas) {
		Map<String, Object> params = new HashMap<String, Object>();
		int n = 0;
		for (int i = 0; i < code_datas.length; i++) {
			params.put("type", code_datas[i]);
			n = assistProjectMapper.selectAssistProject(params);
		}
		return n;
	}
}