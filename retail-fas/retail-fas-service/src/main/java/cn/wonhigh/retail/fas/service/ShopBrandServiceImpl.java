package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.dal.database.ShopBrandMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
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
@Service("shopBrandService")
class ShopBrandServiceImpl extends BaseCrudServiceImpl implements ShopBrandService {
    @Resource
    private ShopBrandMapper shopBrandMapper;

    @Override
    public BaseCrudMapper init() {
        return shopBrandMapper;
    }

	@Override
	public List<ShopBrand> selectByOwnCondition(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shopBrandMapper.selectByOwnCondition(params);
	}

	@Override
	public List<String> queryShopNoByOrganNo(String organNos) {
		return shopBrandMapper.queryShopNoByOrganNo(organNos);
	}
}