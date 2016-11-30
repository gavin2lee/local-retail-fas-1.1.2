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

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.service.AreaOtherStockInEntryService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("areaOtherStockInEntryManager")
public class AreaOtherStockInEntryManagerImpl extends BaseCrudManagerImpl implements
		AreaOtherStockInEntryManager {
	
	@Resource
	private AreaOtherStockInEntryService areaOtherStockInEntryService;

	@Override
	protected BaseCrudService init() {
		return areaOtherStockInEntryService;
	}

	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return areaOtherStockInEntryService.findTotalRow(params);
	}

	@Override
	public int selectDtlCount(Map<String, Object> params) {
		return areaOtherStockInEntryService.selectDtlCount(params);
	}

	@Override
	public List<BillBuyBalance> selectDtlByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return areaOtherStockInEntryService.selectDtlByPage(page, orderByField, orderBy, params);
	}

	@Override
	public List<BillBuyBalance> selectDtlTotalRow(Map<String, Object> params) {
		return areaOtherStockInEntryService.selectDtlTotalRow(params);
	}

}
