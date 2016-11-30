/**
 * title:LeisureBrandManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2016-3-15 上午11:05:39
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.LeisureBrandService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("leisureBrandManager")
public class LeisureBrandManagerImpl extends BaseCrudManagerImpl implements
		LeisureBrandManager {
	@Resource
	private LeisureBrandService leisureBrandService;
	
	@Override
	protected BaseCrudService init() {
		return leisureBrandService;
	}

	@Override
	public List<Map<String, Object>> findDynColumns(Map<String, Object> params) {
		return leisureBrandService.findDynColumns(params);
	}

	@Override
	public List<Map<String, Object>> findSumByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return leisureBrandService.findSumByPage(page, orderByField, orderBy, params);
	}

	@Override
	public List<Map<String, Object>> findTotalRow(Map<String, Object> params) {
		return leisureBrandService.findTotalRow(params);
	}

}
