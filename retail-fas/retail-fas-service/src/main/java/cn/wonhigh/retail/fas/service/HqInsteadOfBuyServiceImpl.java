/**
 * title:HqInsteadOfBuyServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-4-11 下午4:26:53
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.dal.database.HqInsteadOfBuyMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("hqInsteadOfBuyService")
public class HqInsteadOfBuyServiceImpl extends BaseCrudServiceImpl implements
		HqInsteadOfBuyService {
	@Resource
	private HqInsteadOfBuyMapper hqInsteadOfBuyMapper;

	@Override
	public BaseCrudMapper init() {
		return hqInsteadOfBuyMapper;
	}

	/**
	 * 查询明细合计行
	 * @param params
	 * @return
	 */
	@Override
	public List<BillBuyBalance> findTotalRow(
			Map<String, Object> params) {
		return hqInsteadOfBuyMapper.selectTotalRow(params);
	}

	@Override
	public int findEntryCount(Map<String, Object> params) {
		return hqInsteadOfBuyMapper.selectEntryCount(params);
	}

	@Override
	public List<BillBuyBalance> findEntryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return hqInsteadOfBuyMapper.selectEntryByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<BillBuyBalance> findEntryTotalRow(Map<String, Object> params) {
		return hqInsteadOfBuyMapper.selectEntryTotalRow(params);
	}

}
