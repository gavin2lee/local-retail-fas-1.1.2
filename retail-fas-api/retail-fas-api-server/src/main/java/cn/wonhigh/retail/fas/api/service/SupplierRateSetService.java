package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;


import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.common.dto.SupplierRateSetDto;


public interface SupplierRateSetService {
	
	List<SupplierRateSetDto> getSupplierRateSetBySupplierNo(Map<String, Object> params)  throws ServiceException;
}
