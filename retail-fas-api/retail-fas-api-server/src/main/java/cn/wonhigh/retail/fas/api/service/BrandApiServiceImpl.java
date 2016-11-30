package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.BrandApiMapper;
import cn.wonhigh.retail.fas.common.model.Brand;

import com.yougou.logistics.base.common.exception.ServiceException;

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
class BrandApiServiceImpl implements BrandApiService {
    
	@Resource
    private BrandApiMapper brandApiMapper;

	@Override
	public Brand findByBrandNo(String brandNo) throws ServiceException {
		try {
			List<Brand> list = brandApiMapper.findByBrandNo(brandNo);
			if(list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}