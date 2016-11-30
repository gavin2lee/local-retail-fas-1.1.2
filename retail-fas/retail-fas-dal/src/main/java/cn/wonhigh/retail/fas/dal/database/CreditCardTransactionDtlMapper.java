package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.CreditCardCensusDto;
import cn.wonhigh.retail.fas.common.model.CreditCardTransactionDtl;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:35:01
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
public interface CreditCardTransactionDtlMapper extends BaseCrudMapper {
	
	public List<CreditCardCensusDto> getCreditCardCensusList(Map<String,Object> maps);
	
	public Integer getCreditCardCensusCount(Map<String,Object> maps);
	
	public BigDecimal getSumRebateAmount(Map<String,Object> maps);
	
	public CreditCardTransactionDtl findReturnInfo(@Param("params")Map<String, Object> params);
	
}