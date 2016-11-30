package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.DataHqAreaExchangeMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
@Service("dataHqAreaExchangeService")
class DataHqAreaExchangeServiceImpl extends BaseCrudServiceImpl implements DataHqAreaExchangeService {
	
    @Resource
    private DataHqAreaExchangeMapper dataHqAreaExchangeMapper;

    @Override
    public BaseCrudMapper init() {
        return dataHqAreaExchangeMapper;
    }

	@Override
	public int selectSaleTransferBillCount(Map<String, Object> params) throws ServiceException {
		return dataHqAreaExchangeMapper.selectSaleTransferBillCount(params);
	}

	@Override
	public List<BillBuyBalance> selectSaleTransferBill(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ServiceException {
		return dataHqAreaExchangeMapper.selectSaleTransferBill(page, orderByField, orderBy, params);
	}
	
	@Override
	public int selectBuyTransferBillCount(Map<String, Object> params) throws ServiceException {
		return dataHqAreaExchangeMapper.selectBuyTransferBillCount(params);
	}
	
	@Override
	public List<BillBuyBalance> selectBuyTransferBill(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ServiceException {
		return dataHqAreaExchangeMapper.selectBuyTransferBill(page, orderByField, orderBy, params);
	}

	@Override
	public int updateSaleTransferBill(BillSaleBalance billSaleBalance)
			throws ServiceException {
		return dataHqAreaExchangeMapper.updateSaleTransferBill(billSaleBalance);
	}

	@Override
	public int updateBuyTransferBill(BillBuyBalance billBuyBalance)
			throws ServiceException {
		return dataHqAreaExchangeMapper.updateBuyTransferBill(billBuyBalance);
	}

	@Override
	public List<BillBuyBalance> findTotalRow(Map<String, Object> params) {
		return dataHqAreaExchangeMapper.selectTotalRow(params);
	}

	@Override
	public List<BillBuyBalance> findBuyTotalRow(Map<String, Object> params) {
		return dataHqAreaExchangeMapper.selectBuyTotalRow(params);
	}

}