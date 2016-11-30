package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.service.ShopBrandService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("shopBrandManager")
class ShopBrandManagerImpl extends BaseCrudManagerImpl implements ShopBrandManager {
    @Resource
    private ShopBrandService shopBrandService;

    @Override
    public BaseCrudService init() {
        return shopBrandService;
    }

	@Override
	public List<String> queryShopNoByOrganNo(String organNos) {
		return shopBrandService.queryShopNoByOrganNo(organNos);
	}
    
    
}