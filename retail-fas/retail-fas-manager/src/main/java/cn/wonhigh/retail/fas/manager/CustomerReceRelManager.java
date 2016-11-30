package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.common.model.CustomerReceRelDtl;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-04 13:40:23
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
public interface CustomerReceRelManager extends BaseCrudManager {

	void add(CustomerReceRel model,
			Map<CommonOperatorEnum, List<CustomerReceRelDtl>> params)throws ManagerException;

	void update(CustomerReceRel model,
			Map<CommonOperatorEnum, List<CustomerReceRelDtl>> params)throws ManagerException;
}