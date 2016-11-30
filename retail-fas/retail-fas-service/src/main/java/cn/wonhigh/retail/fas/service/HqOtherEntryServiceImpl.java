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

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.HqOtherEntryMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("hqOtherEntryService")
public class HqOtherEntryServiceImpl extends BaseCrudServiceImpl implements
		HqOtherEntryService {
	
	@Resource
	private HqOtherEntryMapper hqOtherEntryMapper;

	@Override
	public BaseCrudMapper init() {
		return hqOtherEntryMapper;
	}

	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return hqOtherEntryMapper.selectTotalRow(params);
	}

}
