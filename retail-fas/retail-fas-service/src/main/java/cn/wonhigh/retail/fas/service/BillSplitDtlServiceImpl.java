package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSplitDtl;
import cn.wonhigh.retail.fas.dal.database.BillSplitDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
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
@Service("billSplitDtlService")
class BillSplitDtlServiceImpl extends BaseCrudServiceImpl implements BillSplitDtlService {
	
    @Resource
    private BillSplitDtlMapper billSplitDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billSplitDtlMapper;
    }

	@Override
	public int batchAdd(List<BillSplitDtl> list) throws ServiceException {
		return billSplitDtlMapper.batchAdd(list);
	}

	@Override
	public int batchUpdateBalanceNoById(Map<String, Object> params)
			throws ServiceException {
		return billSplitDtlMapper.batchUpdateBalanceNoById(params);
	}

	@Override
	public int findSplitCount(Map<String, Object> params) {
		return billSplitDtlMapper.findSplitCount(params);
	}
}