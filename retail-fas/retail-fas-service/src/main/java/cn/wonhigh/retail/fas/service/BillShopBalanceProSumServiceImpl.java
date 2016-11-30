package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceProSumMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@Service("billShopBalanceProSumService")
class BillShopBalanceProSumServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceProSumService {
    @Resource
    private BillShopBalanceProSumMapper billShopBalanceProSumMapper;

    @Override
    public BaseCrudMapper init() {
        return billShopBalanceProSumMapper;
    }

	@Override
	public List<BillShopBalanceProSum> getSumAmount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billShopBalanceProSumMapper.getSumAmount(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}