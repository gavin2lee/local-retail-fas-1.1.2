package cn.wonhigh.retail.fas.manager;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.service.PurchasePriceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("purchasePriceManager")
class PurchasePriceManagerImpl extends BaseCrudManagerImpl implements PurchasePriceManager {
    @Resource
    private PurchasePriceService purchasePriceService;

    @Override
    public BaseCrudService init() {
        return purchasePriceService;
    }

	@Override
	public PurchasePrice findBalancePurchasePrice(String itemNo,
			String supplierNo, Date billDate) throws ManagerException {
		try {
			return purchasePriceService.findBalancePurchasePrice(itemNo, supplierNo, billDate);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public PurchasePrice findBalancePurchasePriceByItemNo(String itemNo,
			Date billDate) throws ManagerException {
		try {
			return purchasePriceService.findBalancePurchasePriceByItemNo(itemNo, billDate);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}