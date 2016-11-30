/**
 * title:LeisureBrandServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2016-3-15 上午11:12:37
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.LeisureBrandMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 
 */
@Service("leisureBrandService")
public class LeisureBrandServiceImpl extends BaseCrudServiceImpl implements
		LeisureBrandService {
	@Resource
    private LeisureBrandMapper leisureBrandMapper;
	
	@Override
	public BaseCrudMapper init() {
		return leisureBrandMapper;
	}

	@Override
	public List<Map<String, Object>> findDynColumns(Map<String, Object> params) {
		return leisureBrandMapper.selectDynColumns(params);
	}

	@Override
	public List<Map<String, Object>> findSumByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return leisureBrandMapper.selectSumByPage(page,orderByField,orderBy,params);
	}

	@Override
	public List<Map<String, Object>> findTotalRow(Map<String, Object> params) {
		return leisureBrandMapper.selectTotalRow(params);
	}

}
