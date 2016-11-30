package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.dal.database.SettleBrandGroupRelMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 品牌组与品牌的关联
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
@Service("settleBrandGroupRelService")
class SettleBrandGroupRelServiceImpl extends BaseServiceImpl implements SettleBrandGroupRelService {
    
	@Resource
    private SettleBrandGroupRelMapper settleBrandGroupRelMapper;

    @Override
    public BaseCrudMapper init() {
        return settleBrandGroupRelMapper;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int deleteByGroupNo(String groupNo) {
		// TODO Auto-generated method stub
		return settleBrandGroupRelMapper.deleteByGroupNo(groupNo);
	}
}