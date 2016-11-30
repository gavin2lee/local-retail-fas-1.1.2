/**
 * title:AreaAmongStorageManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-5-6 下午4:53:51
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.service.AreaAmongStorageService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("areaAmongStorageManager")
public class AreaAmongStorageManagerImpl extends BaseCrudManagerImpl implements
		AreaAmongStorageManager {
	@Resource
	private AreaAmongStorageService areaAmongStorageService;
	@Override
	protected BaseCrudService init() {
		return areaAmongStorageService;
	}
	
	@Override
	public List<BillBuyBalance> findTotalRow(Map<String, Object> params) {
		return areaAmongStorageService.findTotalRow(params);
	}

}
