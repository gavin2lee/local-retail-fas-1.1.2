package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.TransferingCheckDto;
import cn.wonhigh.retail.fas.dal.database.ReceiveAndSendMapper;
import cn.wonhigh.retail.fas.dal.database.TransferingCheckMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 收发货在途对账报表
 * @author ning.ly
 * @date  2015-04-24 10:10:28
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
@Service("transferingCheckService")
class TransferingCheckServiceImpl extends BaseCrudServiceImpl implements TransferingCheckService {
    @Resource
    private TransferingCheckMapper transferingCheckMapper;

    @Resource
    private ReceiveAndSendMapper receiveAndSendMapper;
    
    @Override
    public BaseCrudMapper init() {
        return transferingCheckMapper;
    }

	@Override
	public List<TransferingCheckDto> selectTransferingCheckDtl(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		return transferingCheckMapper.selectTransferingCheckDtl(page, sortColumn, sortOrder, params);
	}

	@Override
	public List<TransferingCheckDto> selectTransferingCheckTotal(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		return transferingCheckMapper.selectTransferingCheckTotal(page, sortColumn, sortOrder, params);
	}

	@Override
	public int selectTransferingCheckDtlCount(Map<String, Object> params)
			throws ServiceException {
		return transferingCheckMapper.selectTransferingCheckDtlCount(params);
	}

	@Override
	public List<TransferingCheckDto> selectTransferingCheckGatherFooter(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		return transferingCheckMapper.selectTransferingCheckGatherFooter(page,params);
	}

	@Override
	public List<TransferingCheckDto> selectTransferingCheckDtlFooter(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		return transferingCheckMapper.selectTransferingCheckDtlFooter(page,params);
	}

	@Override
	public int findReceiveAndSendDtlCount(Map<String, Object> params) {
		return receiveAndSendMapper.selectReceiveAndSendDtlCount(params);
	}

	@Override
	public List<TransferingCheckDto> findReceiveAndSendDtl(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return receiveAndSendMapper.selectReceiveAndSendDtl(page, sortColumn, sortOrder, params);
	}

	@Override
	public List<TransferingCheckDto> findReceiveAndSendDtlFooter(Object object,
			Map<String, Object> params) {
		return receiveAndSendMapper.selectReceiveAndSendDtlFooter(object,params);
	}

	@Override
	public int findReceiveAndSendSumCount(Map<String, Object> params) {
		return receiveAndSendMapper.selectReceiveAndSendSumCount(params);
	}

	@Override
	public List<TransferingCheckDto> findReceiveAndSendSum(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return receiveAndSendMapper.selectReceiveAndSendSum(page, sortColumn, sortOrder, params);
	}

	@Override
	public List<TransferingCheckDto> findReceiveAndSendSumFooter(Object object,
			Map<String, Object> params) {
		return receiveAndSendMapper.selectReceiveAndSendSumFooter(object,params);
	}
}