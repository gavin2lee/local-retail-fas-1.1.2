package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSplit;
import cn.wonhigh.retail.fas.dal.database.BillSplitMapper;

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
@Service("billSplitService")
class BillSplitServiceImpl extends BaseCrudServiceImpl implements BillSplitService {
	
    @Resource
    private BillSplitMapper billSplitMapper;

    @Override
    public BaseCrudMapper init() {
        return billSplitMapper;
    }

	@Override
	public int batchAdd(List<BillSplit> list) throws ServiceException {
		return billSplitMapper.batchAdd(list);
	}

	@Override
	public int selectSplitBillCount(Map<String, Object> params) {
		return billSplitMapper.selectSplitBillCount(params);
	}

	@Override
	public List<BillBuyBalance> selectSplitBill(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billSplitMapper.selectSplitBill(page, orderByField, orderBy, params);
	}

	@Override
	public List<SettlePathDto> selectSettlePath(Map<String, Object> params) {
		return billSplitMapper.selectSettlePath(params);
	}

	@Override
	public int selectBuySettleCount(Map<String, Object> params) {
		return billSplitMapper.selectBuySettleCount(params);
	}

	@Override
	public int selectSaleSettleCount(Map<String, Object> params) {
		return billSplitMapper.selectSaleSettleCount(params);
	}
}