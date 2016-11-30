package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-21 14:30:24
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
@Service("otherDeductionService")
class OtherDeductionServiceImpl extends BaseServiceImpl implements OtherDeductionService {
    @Resource
    private OtherDeductionMapper otherDeductionMapper;

    @Override
    public BaseCrudMapper init() {
        return otherDeductionMapper;
    }

	@Override
	public List<OtherDeduction> findFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return otherDeductionMapper.findFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void updateBalanceNo(BillBalance obj) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			this.otherDeductionMapper.updateBalanceNo(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	@Override
	public void clearBalanceNo(BillBalance obj) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			this.otherDeductionMapper.clearBalanceNo(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public OtherDeduction findOtherDeductionBanlance(Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return this.otherDeductionMapper.findOtherDeductionBanlance(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
}