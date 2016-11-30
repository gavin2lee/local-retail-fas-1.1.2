/**
 * title:HqOtherEntryServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-5-8 上午10:50:01
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.AreaOtherStockInEntryMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("areaOtherStockInEntryService")
public class AreaOtherStockInEntryServiceImpl extends BaseCrudServiceImpl implements
		AreaOtherStockInEntryService {
	
	@Resource
	private AreaOtherStockInEntryMapper areaOtherStockInEntryMapper;

	@Override
	public BaseCrudMapper init() {
		return areaOtherStockInEntryMapper;
	}

	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return areaOtherStockInEntryMapper.selectTotalRow(params);
	}

	@Override
	public int selectDtlCount(Map<String, Object> params) {
		return areaOtherStockInEntryMapper.selectDtlCount(params);
	}

	@Override
	public List<BillBuyBalance> selectDtlByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return areaOtherStockInEntryMapper.selectDtlByPage(page, orderByField, orderBy, params);
	}

	@Override
	public List<BillBuyBalance> selectDtlTotalRow(Map<String, Object> params) {
		return areaOtherStockInEntryMapper.selectDtlTotalRow(params);
	}

}
