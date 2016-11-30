package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleUnionBuyBalance;
import cn.wonhigh.retail.fas.dal.database.BillBuyBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
@Service("billBuyBalanceService")
class BillBuyBalanceServiceImpl extends BaseCrudServiceImpl implements BillBuyBalanceService {
    @Resource
    private BillBuyBalanceMapper billBuyBalanceMapper;

    @Override
    public BaseCrudMapper init() {
        return billBuyBalanceMapper;
    }

	@Override
	public int findSplitCount(Map<String, Object> params) {
		return billBuyBalanceMapper.findSplitCount(params);
	}

	@Override
	public int updateBuyBalanceCost(Map<String, Object> obj)
			throws ServiceException {
		// TODO Auto-generated method stub
		return billBuyBalanceMapper.updateBuyBalanceCost(obj);
	}

	@Override
	public List<BillSaleUnionBuyBalance> selectUnionBalances(
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return billBuyBalanceMapper.selectUnionBalances(params);
	}

	@Override
	public Integer selectUnionBalancesCount(Map<String, Object> params)
			throws ServiceException {
		return billBuyBalanceMapper.selectUnionBalancesCount(params);
	}

	@Override
	public List<BillBuyBalance> selectSalerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
		return billBuyBalanceMapper.selectSalerCompany(page, orderByField, orderBy, params);
	}

	@Override
	public List<BillBuyBalance> selectBuyerCompany(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
		return billBuyBalanceMapper.selectBuyerCompany(page, orderByField, orderBy, params);
	}

	@Override
	public int selectSalerCompanyCount(Map<String, Object> params) {
		return billBuyBalanceMapper.selectSalerCompanyCount(params);
	}

	@Override
	public int selectBuyerCompanyCount(Map<String, Object> params) {
		return billBuyBalanceMapper.selectBuyerCompanyCount(params);
	}

	@Override
	public List<BillBuyBalance> selectPurchaseRegionCost(
			Map<String, Object> params) throws ServiceException {
		return billBuyBalanceMapper.selectPurchaseRegionCost(params);
	}

	@Override
	public List<BillBuyBalance> selectSpecifyItemCost(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billBuyBalanceMapper.selectSpecifyItemCost(page, orderByField, orderBy, params);
	}

	@Override
	public BillBuyBalance selectSpecifyItemCostCount(Map<String, Object> params) {
		return billBuyBalanceMapper.selectSpecifyItemCostCount(params);
	}

	@Override
	public void updateBuyBalanceNo(BillBalance obj) {
		 this.billBuyBalanceMapper.updateBuyBalanceNo(obj);
	}
	
	@Override
	public void clearBuyBalanceNo(BillBalance obj) {
		 this.billBuyBalanceMapper.clearBuyBalanceNo(obj);
	}

	@Override
	public int deleteByBillNo(String billNo) throws ServiceException {
		// TODO Auto-generated method stub
		return billBuyBalanceMapper.deleteByBillNo(billNo);
	}
}