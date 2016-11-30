/**
 * title:OweTheGuestManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-7-1 下午5:35:16
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.OweTheGuestInventoryDto;
import cn.wonhigh.retail.fas.service.OweTheGuestService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("oweTheGuestManager")
public class OweTheGuestManagerImpl extends BaseCrudManagerImpl implements	OweTheGuestManager {
	@Resource
	private OweTheGuestService oweTheGuestService;

	@Override
	protected BaseCrudService init() {
		return oweTheGuestService;
	}

	@Override
	public List<OweTheGuestInventoryDto> findTotalRow(Map<String, Object> params) {
		return oweTheGuestService.findTotalRow(params);
	}

	@Override
	public int findSumCount(Map<String, Object> params) {
		return oweTheGuestService.findSumCount(params);
	}

	@Override
	public List<OweTheGuestInventoryDto> findSumByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return oweTheGuestService.findSumByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<OweTheGuestInventoryDto> findSumTotalRow(
			Map<String, Object> params) {
		return oweTheGuestService.findSumTotalRow(params);
	}

}
