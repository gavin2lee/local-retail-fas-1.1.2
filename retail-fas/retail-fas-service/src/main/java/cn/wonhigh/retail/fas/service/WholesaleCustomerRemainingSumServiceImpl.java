package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingOccurDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSendaDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSumDto;
import cn.wonhigh.retail.fas.dal.database.WholesaleCustomerRemainingSumMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 批发订单余额
 * @author Administrator
 * @date  2015-07-06 15:41:34
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
@Service("wholesaleCustomerRemainingSumService")
class WholesaleCustomerRemainingSumServiceImpl extends BaseServiceImpl implements WholesaleCustomerRemainingSumService {
    @Resource
    private WholesaleCustomerRemainingSumMapper wholesaleCustomerRemainingSumMapper;

    @Override
    public BaseCrudMapper init() {
        return wholesaleCustomerRemainingSumMapper;
    }

	@Override
	public List<WholesaleCustomerRemainingSumDto> findCustomerRemainningByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.findCustomerRemainningByPage(page, orderByField, orderBy, params);
	}

	@Override
	public WholesaleCustomerRemainingSumDto selectCalcPaidAmountByParams(Map<String, Object> params) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.selectCalcPaidAmountByParams(params);
	}

	@Override
	public WholesaleCustomerRemainingSumDto selectCalcSendAmountByParams(Map<String, Object> params) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.selectCalcSendAmountByParams(params);
	}

	@Override
	public List<WholesaleCustomerRemainingOccurDto> findCustomerRemainningOccurByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.findCustomerRemainningOccurByPage(page, orderByField, orderBy, params);
	}

	@Override
	public int updateByCustomerNo(Map<String, Object> params) throws ServiceException{
		return wholesaleCustomerRemainingSumMapper.updateByCustomerNo(params);
	}

	@Override
	public List<WholesaleCustomerRemainingSumDto> findBrandsByCustomerNos(
			List<String> customerNos) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.findBrandsByCustomerNos(customerNos);
	}

	@Override
	public List<WholesaleCustomerRemainingSendaDto> findSendaListByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.findSendaListByPage(page, orderByField, orderBy, params);
	}

	@Override
	public int findSendaCount(Map<String, Object> params) throws ServiceException {
		return wholesaleCustomerRemainingSumMapper.findSendaCount(params);
	}

	
}