/**
 * title:AreaAmongStorageServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:TODO
 * auther:user
 * date:2015-5-6 下午4:56:09
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.dal.database.AreaAmongStorageMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("areaAmongStorageService")
public class AreaAmongStorageServiceImpl extends BaseCrudServiceImpl implements
		AreaAmongStorageService {
	@Resource 
	private AreaAmongStorageMapper areaAmongStorageMapper;
	@Override
	public BaseCrudMapper init() {
		return areaAmongStorageMapper;
	}
	
	@Override
	public List<BillBuyBalance> findTotalRow(Map<String, Object> params) {
		return areaAmongStorageMapper.selectTotalRow(params);
	}

}
