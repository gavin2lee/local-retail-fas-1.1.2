package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.dal.database.BrandMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@Service("brandService")
class BrandServiceImpl extends BaseCrudServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Override
    public BaseCrudMapper init() {
        return brandMapper;
    }

	@Override
	public String queryBrandNameByNo(String brandNo) throws ServiceException {
		try {
			if (StringUtils.isEmpty(brandNo)) {
				return null;
			}
			return brandMapper.queryBrandNameByNo(brandNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Brand> findAllBrandWithoutPermission() throws ServiceException {
		try {
			return brandMapper.findAllBrandWithoutPermission();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Brand> selectBrandWithBrandUnit(
			Map<String, Object> params) throws ServiceException {
		try {
			return brandMapper.selectBrandWithBrandUnit(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}