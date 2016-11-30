package cn.wonhigh.retail.fas.service;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
import cn.wonhigh.retail.fas.dal.database.ShopCheckSetMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-21 15:14:36
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
@Service("shopCheckSetService")
class ShopCheckSetServiceImpl extends BaseCrudServiceImpl implements ShopCheckSetService {
    @Resource
    private ShopCheckSetMapper shopCheckSetMapper;

    @Override
    public BaseCrudMapper init() {
        return shopCheckSetMapper;
    }

	@Override
	public List<ShopCheckSet> getSerialNo() {
		return shopCheckSetMapper.getSerialNo();
	}

	@Override
	public List<String> getCheckNo(String shopNo) {
		return shopCheckSetMapper.getCheckNo(shopNo);
	}
}