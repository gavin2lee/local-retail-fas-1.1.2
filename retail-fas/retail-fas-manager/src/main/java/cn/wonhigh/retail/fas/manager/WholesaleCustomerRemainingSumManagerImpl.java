package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingOccurDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSendaDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSumDto;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingSumService;
import cn.wonhigh.retail.gms.api.service.CustomerCreditApi;
import cn.wonhigh.retail.gms.api.vo.CustomerCreditDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author Administrator
 * @date 2015-07-06 15:41:34
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("wholesaleCustomerRemainingSumManager")
class WholesaleCustomerRemainingSumManagerImpl extends BaseCrudManagerImpl implements
		WholesaleCustomerRemainingSumManager {
	@Resource
	private WholesaleCustomerRemainingSumService wholesaleCustomerRemainingSumService;

	@Resource
	private CustomerCreditApi customerCreditApi;

	@Override
	public BaseCrudService init() {
		return wholesaleCustomerRemainingSumService;
	}

	@Override
	public List<WholesaleCustomerRemainingSumDto> findCustomerRemainningByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			List<String> customerNos = new ArrayList<String>();
			Map<String, CustomerCreditDto> customerMap = new HashMap<String, CustomerCreditDto>();
			List<WholesaleCustomerRemainingSumDto> resultList = wholesaleCustomerRemainingSumService
					.findCustomerRemainningByPage(page, orderByField, orderBy, params);
			for (WholesaleCustomerRemainingSumDto wholesaleCustomerRemainingSumDto : resultList) {
				customerNos.add(wholesaleCustomerRemainingSumDto.getCustomerNo());
			}
			// 调GMS接口,根据客户编号查询余额和年度信贷次数
			if (customerNos.size() > 0) {
				List<CustomerCreditDto> CustomerCreditList = customerCreditApi.queryCustomerCredit(customerNos);
				for (CustomerCreditDto customerCreditDto : CustomerCreditList) {
					customerMap.put(customerCreditDto.getCustomerNo(), customerCreditDto);
				}
				for (WholesaleCustomerRemainingSumDto wholesaleCustomerRemainingSumDto : resultList) {
					CustomerCreditDto customerCreditDto = customerMap.get(wholesaleCustomerRemainingSumDto
							.getCustomerNo());
					if (null == customerCreditDto) {
						continue;
					}
					wholesaleCustomerRemainingSumDto.setRebateAmount(customerCreditDto.getRebateAmount());
					wholesaleCustomerRemainingSumDto.setCreditAmount(customerCreditDto.getCreditAmount());
					wholesaleCustomerRemainingSumDto.setCreditLimit(customerCreditDto.getCreditCount());
					wholesaleCustomerRemainingSumDto.setCreditPerYear(customerCreditDto.getCreditCount()
							- wholesaleCustomerRemainingSumDto.getUsedCreditCount());
				}
			}
			return resultList;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<WholesaleCustomerRemainingOccurDto> findCustomerRemainningOccurByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ManagerException {
		try {

			List<String> customerNos = new ArrayList<String>();
			Map<String, CustomerCreditDto> customerMap = new HashMap<String, CustomerCreditDto>();
			Map<String, String> brandsMap = new HashMap<String, String>();
			List<WholesaleCustomerRemainingOccurDto> resultList = wholesaleCustomerRemainingSumService
					.findCustomerRemainningOccurByPage(page, orderByField, orderBy, params);
			for (WholesaleCustomerRemainingOccurDto dto : resultList) {				
				dto.setPeriodFirstBookAmount(dto.getPeriodFirstAmount().add(dto.getPeriodFirstFrozen()));
				dto.setPeriodLastBookAmount(dto.getPeriodLastAmount().add(dto.getPeriodLastFrozen()));
				customerNos.add(dto.getCustomerNo());
			}
			//查询客户品牌部
			if (customerNos.size() > 0) {
				List<WholesaleCustomerRemainingSumDto> resultBrands =  wholesaleCustomerRemainingSumService.findBrandsByCustomerNos(customerNos);  
				for (WholesaleCustomerRemainingSumDto wholesaleCustomerRemainingSumDto : resultBrands) {
					brandsMap.put(wholesaleCustomerRemainingSumDto.getCustomerNo(), wholesaleCustomerRemainingSumDto.getBrandUnitNames());
				}
			}
			// 调GMS接口,根据客户编号查询余额和年度信贷次数
			if (customerNos.size() > 0) {
				List<CustomerCreditDto> CustomerCreditList = customerCreditApi.queryCustomerCredit(customerNos);
				for (CustomerCreditDto customerCreditDto : CustomerCreditList) {
					customerMap.put(customerCreditDto.getCustomerNo(), customerCreditDto);
				}
				for (WholesaleCustomerRemainingOccurDto wholesaleCustomerRemainingOccurDto : resultList) {
					wholesaleCustomerRemainingOccurDto.setBrandUnitNames(brandsMap.get(wholesaleCustomerRemainingOccurDto.getCustomerNo()));
					CustomerCreditDto customerCreditDto = customerMap
							.get(wholesaleCustomerRemainingOccurDto.getCustomerNo());
					if (null == customerCreditDto) {
						continue;
					}
					wholesaleCustomerRemainingOccurDto.setPeriodLastCredit(customerCreditDto.getCreditCount()
							- wholesaleCustomerRemainingOccurDto.getTotalCreditCounts());
				}
			}
			return resultList;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int updateByCustomerNo(Map<String, Object> params) throws ManagerException {
		try {
			return wholesaleCustomerRemainingSumService.updateByCustomerNo(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<WholesaleCustomerRemainingSendaDto> findSendaListByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return wholesaleCustomerRemainingSumService.findSendaListByPage(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		
	}

	@Override
	public int findSendaCount(Map<String, Object> params) throws ManagerException {
		try {
			return wholesaleCustomerRemainingSumService.findSendaCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		
	}


}