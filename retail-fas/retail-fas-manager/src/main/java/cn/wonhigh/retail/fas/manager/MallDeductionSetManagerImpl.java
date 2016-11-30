package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.MallDeductionSet;
import cn.wonhigh.retail.fas.service.MallDeductionSetService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-30 09:49:24
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
@Service("mallDeductionSetManager")
class MallDeductionSetManagerImpl extends BaseCrudManagerImpl implements MallDeductionSetManager {
    @Resource
    private MallDeductionSetService mallDeductionSetService;

    @Override
    public BaseCrudService init() {
        return mallDeductionSetService;
    }

	@Override
	public List<MallDeductionSet> selectCostByParams(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
		return mallDeductionSetService.selectCostByParams(page, orderByField, orderBy, params);
	}

	@Override
	public int selectCostByCount(Map<String, Object> params) {
		return mallDeductionSetService.selectCostByCount(params);
	}
}