/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaAmongTransferServiceImpl  
* 类描述：处理地区间调货明细
* 创建人：ouyang.zm  
* 创建时间：2014-10-24 下午3:20:57  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.AreaAmongTransferMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("areaAmongTransferService")
public class AreaAmongTransferServiceImpl extends BaseCrudServiceImpl implements
		AreaAmongTransferService {
	@Resource 
	private AreaAmongTransferMapper areaAmongTransferMapper;
	@Override
	public BaseCrudMapper init() {
		return areaAmongTransferMapper;
	}
	@Override
	public List<TotalDto> findTotalRow(Map<String, Object> params) {
		return areaAmongTransferMapper.selectTotalRow(params);
	}
	@Override
	public List<BillSaleBalance> findGatherListByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return areaAmongTransferMapper.selectGatherListByPage(page,sortColumn,sortOrder,params);
	}
	@Override
	public int findGatherCount(Map<String, Object> params) {
		return areaAmongTransferMapper.selectGatherCount(params);
	}
	
	@Override
	public int findSummaryCount(Map<String, Object> params) {
		return areaAmongTransferMapper.selectSummaryCount(params);
	}
	@Override
	public List<BillSaleBalance> findSummaryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return areaAmongTransferMapper.selectSummaryByPage(page,sortColumn,sortOrder,params);
	}
	@Override
	public List<TotalDto> findSummaryTotalRow(Map<String, Object> params) {
		return areaAmongTransferMapper.selectSummaryTotalRow(params);
	}

}
