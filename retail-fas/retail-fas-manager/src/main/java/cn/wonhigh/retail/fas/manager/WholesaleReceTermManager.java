package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.WholesaleReceTermDtlDto;
import cn.wonhigh.retail.fas.common.model.WholesaleReceTerm;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-17 18:00:36
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
public interface WholesaleReceTermManager extends BaseCrudManager {

	int save(WholesaleReceTerm model, Map<CommonOperatorEnum, List<WholesaleReceTermDtlDto>> params)
			throws ManagerException;
	
	int checkOnlyByTermNoOrTermName(@Param("params") Map<String, Object> params) throws ManagerException; 
	
	List<WholesaleReceTerm> selectByCompanyNoAndCustomerNo(@Param("params") Map<String, Object> params) throws ManagerException;
	
}