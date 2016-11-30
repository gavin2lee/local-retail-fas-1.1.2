package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.dal.database.BrandUnitMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2014-12-05 16:36:16
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
@Service("brandUnitService")
class BrandUnitServiceImpl extends BaseCrudServiceImpl implements BrandUnitService {
    @Resource
    private BrandUnitMapper brandUnitMapper;

    @Override
    public BaseCrudMapper init() {
        return brandUnitMapper;
    }

	@Override
	public String findBrands(Map<String, Object> params) {
		return brandUnitMapper.selectBrandNos(params);
	}

	@Override
	public BrandUnit getBrandUnitByBrand(String brandNo) throws ServiceException {
		if(brandNo == null) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brandNo", brandNo);
		return brandUnitMapper.getBrandUnitByBrand(params);
	}
}