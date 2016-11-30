package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.service.OrganService;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
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
@Service("organManager")
class OrganManagerImpl extends BaseCrudManagerImpl implements OrganManager {
    @Resource
    private OrganService organService;

    @Override
    public BaseCrudService init() {
        return organService;
    }

	@Override
	public Organ getOrganByShopNo(Map<String, Object> params) {
		return organService.getOrganByShopNo(params);
	}
}