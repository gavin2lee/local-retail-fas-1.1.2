package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.CreditCardCensusDto;
import cn.wonhigh.retail.fas.common.model.CreditCardTransactionDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface CreditCardTransactionDtlService extends BaseCrudService {
	
	public List<CreditCardCensusDto> getCreditCardCensusList(Map<String,Object> maps) throws ServiceException;
	
	public Integer getCreditCardCensusCount(Map<String,Object> maps) throws ServiceException;
		
	public BigDecimal getSumRebateAmount(Map<String,Object> maps) throws ServiceException;

	public CreditCardTransactionDtl findReturnInfo(Map<String, Object> params) throws ServiceException;
	
}