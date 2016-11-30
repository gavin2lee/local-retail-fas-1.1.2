package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.api.dal.BillSplitLogApiMapper;
import cn.wonhigh.retail.fas.api.model.BillSplitLogModel;

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
@Service("billSplitLogApiService")
class BillSplitLogApiServiceImpl implements BillSplitLogApiService {
    
	@Resource
    private BillSplitLogApiMapper billSplitLogApiMapper;

	@Override
	public List<BillSplitLogModel> selectByBillNo(String billNo)
			throws ServiceException {
		try {
			return billSplitLogApiMapper.selectByBillNo(billNo);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int insert(BillSplitLogModel model) throws ServiceException {
		try {
			return billSplitLogApiMapper.insert(model);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int update(BillSplitLogModel model) throws ServiceException {
		try {
			return billSplitLogApiMapper.update(model);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}