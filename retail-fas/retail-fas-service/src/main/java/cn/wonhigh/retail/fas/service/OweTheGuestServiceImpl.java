/**
 * title:OweTheGuestServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-7-1 下午5:37:18
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.OweTheGuestInventoryDto;
import cn.wonhigh.retail.fas.dal.database.OweTheGuestMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("oweTheGuestService")
public class OweTheGuestServiceImpl extends BaseCrudServiceImpl implements OweTheGuestService {
	@Resource
	private OweTheGuestMapper oweTheGuestMapper;

	@Override
	public BaseCrudMapper init() {
		return oweTheGuestMapper;
	}

	@Override
	public List<OweTheGuestInventoryDto> findTotalRow(Map<String, Object> params) {
		return oweTheGuestMapper.selectTotalRow(params);
	}

	@Override
	public int findSumCount(Map<String, Object> params) {
		return oweTheGuestMapper.selectSumCount(params);
	}

	@Override
	public List<OweTheGuestInventoryDto> findSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return oweTheGuestMapper.selectSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<OweTheGuestInventoryDto> findSumTotalRow(
			Map<String, Object> params) {
		return oweTheGuestMapper.selectSumTotalRow(params);
	}

}
