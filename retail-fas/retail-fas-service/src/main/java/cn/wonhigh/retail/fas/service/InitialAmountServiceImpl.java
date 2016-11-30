package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.InitialAmount;
import cn.wonhigh.retail.fas.dal.database.InitialAmountMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-18 16:38:06
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
@Service("initialAmountService")
public class InitialAmountServiceImpl extends BaseCrudServiceImpl implements InitialAmountService{

	@Resource
	private InitialAmountMapper InitialAmountMapper;
	
	@Override
	public BaseCrudMapper init() {
		return InitialAmountMapper;
	}

	@Override
	public List<InitialAmount> findFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return InitialAmountMapper.findFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}