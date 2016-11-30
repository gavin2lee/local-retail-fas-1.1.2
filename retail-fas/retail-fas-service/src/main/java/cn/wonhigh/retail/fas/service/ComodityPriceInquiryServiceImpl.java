package cn.wonhigh.retail.fas.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.dal.database.ComodityPriceInquiryMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("comdityPriceInquiryService")
public class ComodityPriceInquiryServiceImpl extends BaseCrudServiceImpl implements ComodityPriceInquiryService{
	
	@Resource
    private ComodityPriceInquiryMapper comodityPriceInquiryMapper;
	
	@Override
	public BaseCrudMapper init() {
		// TODO Auto-generated method stub
		return comodityPriceInquiryMapper;
	}

	@Override
	public PurchasePrice findBalancePurchasePrice(Map<String,Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return comodityPriceInquiryMapper.getBalancePurchasePrice(params);
	}

}
