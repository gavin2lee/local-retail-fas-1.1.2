package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerOrderRemainDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSumDto;
import cn.wonhigh.retail.fas.service.CustomerOrderRemainService;
import cn.wonhigh.retail.gms.api.service.CustomerCreditApi;
import cn.wonhigh.retail.gms.api.vo.CustomerCreditDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *批发订单余额
 * 
 * @author user
 * @date 2016-06-08 18:09:42
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("customerOrderRemainManager")
class CustomerOrderRemainManagerImpl extends BaseCrudManagerImpl implements CustomerOrderRemainManager {
	@Resource
	private CustomerOrderRemainService customerOrderRemainService;

	@Resource
	private CustomerCreditApi customerCreditApi;

	@Override
	public BaseCrudService init() {
		return customerOrderRemainService;
	}

	@Override
	public List<WholesaleCustomerOrderRemainDto> queryWholesaleCustomerOrderRemain(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params) throws ManagerException {
		try {

			List<String> customerNos = new ArrayList<String>();
			Map<String, CustomerCreditDto> customerMap = new HashMap<String, CustomerCreditDto>();
			List<WholesaleCustomerOrderRemainDto> resultList = customerOrderRemainService
					.queryWholesaleCustomerOrderRemain(page, sortColumn, sortOrder, params);
			for (WholesaleCustomerOrderRemainDto wholesaleCustomerOrderRemainDto : resultList) {
				customerNos.add(wholesaleCustomerOrderRemainDto.getCustomerNo());
			}
			// 调GMS接口,根据客户编号查询余额和年度信贷次数
			if (customerNos.size() > 0) {
				List<CustomerCreditDto> CustomerCreditList = customerCreditApi.queryCustomerCredit(customerNos);
				for (CustomerCreditDto customerCreditDto : CustomerCreditList) {
					customerMap.put(customerCreditDto.getCustomerNo(), customerCreditDto);
				}
				for (WholesaleCustomerOrderRemainDto wholesaleCustomerOrderRemainDto : resultList) {
//					wholesaleCustomerOrderRemainDto.setOrderRemainingBookAmount(wholesaleCustomerOrderRemainDto
//							.getRemainingAmount().add(wholesaleCustomerOrderRemainDto.getFrozenOrderAmount()));
					CustomerCreditDto customerCreditDto = customerMap.get(wholesaleCustomerOrderRemainDto
							.getCustomerNo());
					if (null == customerCreditDto) {
						continue;
					}
					wholesaleCustomerOrderRemainDto.setCreditAmount(customerCreditDto.getCreditAmount());

				}
			}
			return resultList;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}

	}
}