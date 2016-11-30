package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.CreditCardCensusDto;
import cn.wonhigh.retail.fas.common.model.CreditCardTransactionDtl;
import cn.wonhigh.retail.fas.dal.database.CreditCardTransactionDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 11:40:01
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
@Service("creditCardTransactionDtlService")
class CreditCardTransactionDtlServiceImpl extends BaseCrudServiceImpl implements CreditCardTransactionDtlService {
    @Resource
    private CreditCardTransactionDtlMapper creditCardTransactionDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return creditCardTransactionDtlMapper;
    }

	@Override
	public BigDecimal getSumRebateAmount(Map<String, Object> maps)
			throws ServiceException {
		// TODO Auto-generated method stub
		return creditCardTransactionDtlMapper.getSumRebateAmount(maps);
	}

	@Override
	public List<CreditCardCensusDto> getCreditCardCensusList(
			Map<String, Object> maps) throws ServiceException {
		// TODO Auto-generated method stub
		return creditCardTransactionDtlMapper.getCreditCardCensusList(maps);
	}

	@Override
	public Integer getCreditCardCensusCount(Map<String, Object> maps)
			throws ServiceException {
		// TODO Auto-generated method stub
		return creditCardTransactionDtlMapper.getCreditCardCensusCount(maps);
	}

	@Override
	public CreditCardTransactionDtl findReturnInfo(Map<String, Object> params) throws ServiceException {
		try {
			return creditCardTransactionDtlMapper.findReturnInfo(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}