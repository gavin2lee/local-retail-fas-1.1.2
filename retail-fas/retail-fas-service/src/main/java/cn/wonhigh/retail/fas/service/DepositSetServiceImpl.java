package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.DepositSet;
import cn.wonhigh.retail.fas.dal.database.DepositSetMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-10 16:41:24
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
@Service("depositSetService")
class DepositSetServiceImpl extends BaseCrudServiceImpl implements DepositSetService {
    @Resource
    private DepositSetMapper depositSetMapper;

    @Override
    public BaseCrudMapper init() {
        return depositSetMapper;
    }
    
	@Override
	public DepositSet getDepositSet(Map<String, Object> params) {
		return depositSetMapper.getDepositSet(params);
	}
}