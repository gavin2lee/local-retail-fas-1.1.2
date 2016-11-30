package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

public interface ComodityPriceInquiryService extends BaseCrudService{
	PurchasePrice findBalancePurchasePrice(Map<String,Object> params) throws ServiceException;
}
