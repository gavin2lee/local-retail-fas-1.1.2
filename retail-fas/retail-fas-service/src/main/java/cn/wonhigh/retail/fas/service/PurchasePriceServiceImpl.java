package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.dal.database.PurchasePriceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-29 18:03:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("purchasePriceService")
class PurchasePriceServiceImpl extends BaseCrudServiceImpl implements PurchasePriceService {
    @Resource
    private PurchasePriceMapper purchasePriceMapper;

    @Override
    public BaseCrudMapper init() {
        return purchasePriceMapper;
    }

	@Override
	public PurchasePrice findBalancePurchasePrice(String itemNo,
			String supplierNo, Date billDate) throws ServiceException {
		try {
			if (StringUtils.isEmpty(itemNo) || StringUtils.isEmpty(supplierNo) || null == billDate) {
				return null;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemNo", itemNo);
			params.put("supplierNo", supplierNo);
			params.put("effectiveDate", billDate);
			
			return purchasePriceMapper.getBalancePurchasePrice(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public PurchasePrice findBalancePurchasePriceByItemNo(String itemNo,
			Date billDate) throws ServiceException {
		try {
			if (StringUtils.isEmpty(itemNo) || null == billDate) {
				return null;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemNo", itemNo);
			params.put("effectiveDate", billDate);
			return purchasePriceMapper.getBalancePurchasePriceByItemNo(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}