/**
 * title:HqInsteadOfBuyManagerImpl.java
 * package:cn.wonhigh.retail.fas.manager
 * description:TODO
 * auther:user
 * date:2015-4-11 下午4:23:13
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.service.HqInsteadOfBuyService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("hqInsteadOfBuyManager")
public class HqInsteadOfBuyManagerImpl extends BaseCrudManagerImpl implements
		HqInsteadOfBuyManager {
	@Resource
	private HqInsteadOfBuyService hqInsteadOfBuyService;

	@Override
	protected BaseCrudService init() {
		return hqInsteadOfBuyService;
	}

	/**
	 * 查询明细合计行
	 * @param params
	 * @return
	 */
	@Override
	public List<BillBuyBalance> findTotalRow(
			Map<String, Object> params) {
		return hqInsteadOfBuyService.findTotalRow(params);
	}

	@Override
	public int findEntryCount(Map<String, Object> params) {
		return hqInsteadOfBuyService.findEntryCount(params);
	}

	@Override
	public List<BillBuyBalance> findEntryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return hqInsteadOfBuyService.findEntryByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<BillBuyBalance> findEntryTotalRow(Map<String, Object> params) {
		return hqInsteadOfBuyService.findEntryTotalRow(params);
	}
}
