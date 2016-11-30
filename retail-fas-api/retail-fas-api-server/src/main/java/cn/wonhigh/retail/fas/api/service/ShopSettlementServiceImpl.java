package cn.wonhigh.retail.fas.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.ShopSettlementMapper;
import cn.wonhigh.retail.fas.common.model.MallDeductionSetting;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("shopSettlementServiceImpl")
public class ShopSettlementServiceImpl implements ShopSettlementService {
	
	@Resource
	private ShopSettlementMapper shopSettlementMapper;
	
	@Override
	public List<ShopBalanceDate> getAllShopBalanceDateInfo(String shopNo)
			throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("shopNo", shopNo);
		return shopSettlementMapper.getAllShopBalanceDateInfo(params);
	}

	@Override
	public List<MallDeductionSetting> getAllMallDeductionSettingInfo(String shopNo)
			throws ServiceException {
		return shopSettlementMapper.getAllMallDeductionSettingInfo(shopNo);
	}

	@Override
	public Date getShopBalanceEndDate(String shopNo) throws ServiceException {
		return shopSettlementMapper.getShopBalanceEndDate(shopNo);
	}

	@Override
	public Date getInsidePurchaseBalanceEndDate(String shopNo)
			throws ServiceException {
		return shopSettlementMapper.getInsidePurchaseBalanceEndDate(shopNo);
	}

}
