package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.dal.database.OrganMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-13 14:01:15
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
@Service("organService")
class OrganServiceImpl extends BaseCrudServiceImpl implements OrganService {
    @Resource
    private OrganMapper organMapper;

    @Override
    public BaseCrudMapper init() {
        return organMapper;
    }

	@Override
	public Organ getOrganByShopNo(Map<String, Object> params) {
		return organMapper.getOrganByShopNo(params);
	}

	@Override
	public Organ getOrganInfoByShopNo(Map<String, Object> params) {
		return organMapper.getOrganInfoByShopNo(params);
	}
}