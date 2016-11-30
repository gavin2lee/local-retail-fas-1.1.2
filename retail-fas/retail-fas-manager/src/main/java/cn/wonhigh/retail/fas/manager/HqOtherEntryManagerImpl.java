/**
 * title:HqOtherEntryManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-5-8 上午10:48:13
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.service.HqOtherEntryService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("hqOtherEntryManager")
public class HqOtherEntryManagerImpl extends BaseCrudManagerImpl implements
		HqOtherEntryManager {
	
	@Resource
	private HqOtherEntryService hqOtherEntryService;

	@Override
	protected BaseCrudService init() {
		return hqOtherEntryService;
	}

	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return hqOtherEntryService.findTotalRow(params);
	}

}
