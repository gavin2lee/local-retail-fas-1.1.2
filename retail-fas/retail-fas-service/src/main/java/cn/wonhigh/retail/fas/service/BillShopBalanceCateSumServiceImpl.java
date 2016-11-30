package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceCateSumMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
@Service("billShopBalanceCateSumService")
class BillShopBalanceCateSumServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceCateSumService {
	
    @Resource
    private BillShopBalanceCateSumMapper billShopBalanceCateSumMapper;

    @Override
    public BaseCrudMapper init() {
        return billShopBalanceCateSumMapper;
    }

	@Override
	public BigDecimal getSumAmount(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.getSumAmount(params);
	}

	@Override
	public List<BillShopBalanceCateSum> findShopCateSummary(Map<String, Object> params)
			throws ServiceException {
		try {
			return billShopBalanceCateSumMapper.getShopCateSummary(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 获取页脚对象
	 * @param params 查询参数
	 * @return 页脚对象
	 */
	@Override
	public BillShopBalanceCateSumFooterDto getFootDto(Map<String, Object> params) {
		List<BillShopBalanceCateSumFooterDto> list = billShopBalanceCateSumMapper.getFootDto(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<BillShopBalanceCateSum> findBrandShopCate(
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return billShopBalanceCateSumMapper.findBrandShopCate(params);
	}

	@Override
	public List<BillShopBalanceCateSum> findBalanceShopBrand(
			Map<String, Object> params) throws ServiceException {
		return billShopBalanceCateSumMapper.findBalanceShopBrand(params);
	}

	@Override
	public int modifyInvoiceAmountShareByNo(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.modifyInvoiceAmountShareByNo(params);
	}

	@Override
	public int modifySubInvoiceAmountByBalance(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.modifySubInvoiceAmountByBalance(params);
	}

	@Override
	public int modifySubInvoiceAmountByBrand(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.modifySubInvoiceAmountByBrand(params);
	}

	@Override
	public int modifySubInvoiceAmountByRound(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.modifySubInvoiceAmountByRound(params);
	}

	@Override
	public int modifyBrandBillingAmountByRound(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.modifyBrandBillingAmountByRound(params);
	}

	@Override
	public int modifyBrandBillingAmountByBrand(Map<String, Object> params) {
		return billShopBalanceCateSumMapper.modifyBrandBillingAmountByBrand(params);
	}

	@Override
	public int modifyCateSumAmountDiff(Map<String, Object> params) {
			return billShopBalanceCateSumMapper.modifyCateSumAmountDiff(params);
	}

	@Override
	public List<BillShopBalanceCateSum> findBrandSalesCategory3(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
		return billShopBalanceCateSumMapper.findBrandSalesCategory3(page, orderByField, orderBy, params);
	}
}