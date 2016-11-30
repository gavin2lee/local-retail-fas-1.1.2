package cn.wonhigh.retail.fas.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.PurchasePriceApiMapper;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("purchasePriceApiService")
public class PurchasePriceServiceApiImpl implements PurchasePriceApiService {
	
	@Resource
	private PurchasePriceApiMapper purchasePriceApiMapper;
	
	@Override
	public PurchasePrice findBillPurchasePrice(String itemNo,
			String supplierNo, Date billDate) throws ServiceException {
		try {
			if (StringUtils.isEmpty(itemNo) || StringUtils.isEmpty(supplierNo) || null == billDate) {
				return null;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemNo", itemNo);
			params.put("supplierNo", supplierNo);
			params.put("effectiveDate", billDate);
			
			return purchasePriceApiMapper.getBillPurchasePrice(params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
}
