package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.dal.database.CurrencyManagementMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-09-01 17:18:41
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
@Service("currencyManagementService")
class CurrencyManagementServiceImpl extends BaseServiceImpl implements CurrencyManagementService {
    @Resource
    private CurrencyManagementMapper currencyManagementMapper;

    @Override
    public BaseCrudMapper init() {
        return currencyManagementMapper;
    }

	@Override
	public CurrencyManagement findDefaultCurrency() {
		return currencyManagementMapper.selectDefaultCurrency();
	}
}