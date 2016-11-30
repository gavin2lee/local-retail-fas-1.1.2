/**
 * title:PriceEffectiveDateApiManagerImpl.java
 * package:cn.wonhigh.retail.fas.api.manager
 * description:TODO
 * auther:user
 * date:2016-3-10 下午2:56:57
 */
package cn.wonhigh.retail.fas.api.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.service.PriceEffectiveDateApi;
import cn.wonhigh.retail.fas.api.service.PriceEffectiveDateApiService;

/**
 * 
 */
@Service("priceEffectiveDateApiManagerImpl")
public class PriceEffectiveDateApiManagerImpl implements PriceEffectiveDateApi {
	@Resource
    private PriceEffectiveDateApiService priceEffectiveDateApiService;
	@Override
	public String findPurchasePriceEffectiveDate() {
		return priceEffectiveDateApiService.findPurchasePriceEffectiveDate();
	}

}
