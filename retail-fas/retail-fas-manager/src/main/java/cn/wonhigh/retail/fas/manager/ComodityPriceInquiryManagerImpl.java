package cn.wonhigh.retail.fas.manager;



import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.service.ComodityPriceInquiryService;
import cn.wonhigh.retail.fas.service.PurchasePriceService;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("comodityPriceInquiryManager")
public class ComodityPriceInquiryManagerImpl extends BaseCrudManagerImpl implements ComodityPriceInquiryManager{
	
	@Resource
	private ComodityPriceInquiryService comodityPriceInquiryService;
	
	
	@Override
	protected BaseCrudService init() {
		// TODO Auto-generated method stub
		return comodityPriceInquiryService;
	}


	@Override
	public PurchasePrice findBalancePurchasePrice(Map<String,Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return comodityPriceInquiryService.findBalancePurchasePrice(params);
	}
	


}
