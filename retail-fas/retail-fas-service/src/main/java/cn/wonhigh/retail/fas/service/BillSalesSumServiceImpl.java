package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.dal.database.BillSalesSumMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-11 15:51:08
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
@Service("billSalesSumService")
class BillSalesSumServiceImpl extends BaseCrudServiceImpl implements BillSalesSumService {
    @Resource
    private BillSalesSumMapper billSalesSumMapper;

    @Override
    public BaseCrudMapper init() {
        return billSalesSumMapper;
    }

	@Override
	public <ModelType> List<ModelType> findSalesSum(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billSalesSumMapper.findSalesSum(page, orderByField, orderBy, params, null);
	}

	@Override
	public <ModelType> List<ModelType> findSaleGoodsGms(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billSalesSumMapper.findSaleGoodsGms(page, orderByField, orderBy, params, null);
	}

	@Override
	public List<BillSalesSum> selectSubsiInfoList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billSalesSumMapper.selectSubsiInfoList(page, orderByField, orderBy, params);
	}

	@Override
	public BillSalesSum selectSubsiInfo(Map<String, Object> params) {
		return billSalesSumMapper.selectSubsiInfo(params);
	}

	@Override
	public BillSalesSum selectSalesSumPosCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return billSalesSumMapper.selectSalesSumPosCount(null, params, authorityParams);
	}

	@Override
	public List<BillSalesSum> selectSalesSumPos(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return billSalesSumMapper.selectSalesSumPos(page, orderByField, orderBy, params, authorityParams);
	}

	@Override
	public BillSalesSum selectSalesSumGmsCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return billSalesSumMapper.selectSalesSumGmsCount(null, params, authorityParams);
	}

	@Override
	public List<BillSalesSum> selectSalesSumGms(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return billSalesSumMapper.selectSalesSumGms(page, orderByField, orderBy, params, authorityParams);
	}

	@Override
	public BillSalesSum selectSalesSumOtherCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return billSalesSumMapper.selectSalesSumOtherCount(null, params, authorityParams);
	}

	@Override
	public List<BillSalesSum> selectSalesSumOther(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params,
			AuthorityParams authorityParams) throws ServiceException {
		return billSalesSumMapper.selectSalesSumOther(page, orderByField, orderBy, params, authorityParams);
	}
	
	

	
	
	
	
	
	@Override
	public <ModelType> List<ModelType> checkShopBalanceDate(
			Map<String, Object> params) throws ServiceException {
		return billSalesSumMapper.checkShopBalanceDate(params);
	}

	@Override
	public BigDecimal getPredictionDeductions(String shopNo,
			Date invoiceApplyDate, String brandUnitNo, String categoryNo)
			throws ServiceException {
		return billSalesSumMapper.getPredictionDeductions(shopNo, invoiceApplyDate, brandUnitNo, categoryNo);
	}
	
	/**
	 * 检查有内购销售但未设置内购结算期的公司
	 * @param params
	 * @return
	 * @author wangyj
	 */
	@Override
	public <ModelType> List<ModelType> checkInnerBuyBalanceDate(Map<String, Object> params) throws ServiceException {
		return billSalesSumMapper.checkInnerBuyBalanceDate(params);
	}
}