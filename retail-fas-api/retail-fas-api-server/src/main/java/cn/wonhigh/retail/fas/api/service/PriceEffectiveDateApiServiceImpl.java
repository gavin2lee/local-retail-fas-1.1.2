/**
 * title:PriceEffectiveDateApiServiceImpl.java
 * package:cn.wonhigh.retail.fas.api.service
 * description:价格生效日期
 * auther:user
 * date:2016-3-10 下午3:12:14
 */
package cn.wonhigh.retail.fas.api.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.PriceEffectiveDateMapper;
import cn.wonhigh.retail.fas.api.vo.PriceEffectiveDate;

/**
 * 
 */
@Service("priceEffectiveDateApiService")
public class PriceEffectiveDateApiServiceImpl implements
		PriceEffectiveDateApiService {
	@Resource
    private PriceEffectiveDateMapper priceEffectiveDateMapper;
	
	@Override
	public String findPurchasePriceEffectiveDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		PriceEffectiveDate ped = priceEffectiveDateMapper.selectPurchaseEffectiveDate(params);
		String PurchaseDate = null;
		SimpleDateFormat  dateformat=new SimpleDateFormat("yyyy-MM-dd");
		if (ped != null) {
			PurchaseDate = dateformat.format(ped.getPurPriceEffDate());
		}
		return PurchaseDate;
	}

}
