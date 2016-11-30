/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaAmongTransferManagerImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-10-24 下午3:17:55  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.service.AreaAmongTransferService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
@Service("areaAmongTransferManager")
public class AreaAmongTransferManagerImpl extends BaseCrudManagerImpl implements AreaAmongTransferManager {
	@Resource
	private AreaAmongTransferService areaAmongTransferService;
	
	@Override
	protected BaseCrudService init() {
		return areaAmongTransferService;
	}
	@Override
	public List<TotalDto> findTotalRow(Map<String, Object> params) {
		return areaAmongTransferService.findTotalRow(params);
	}
	@Override
	public List<BillSaleBalance> findGatherListByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return areaAmongTransferService.findGatherListByPage(page,sortColumn,sortOrder,params);
	}
	@Override
	public int findGatherCount(Map<String, Object> params) {
		return areaAmongTransferService.findGatherCount(params);
	}
	
	@Override
	public int findSummaryCount(Map<String, Object> params) {
		return areaAmongTransferService.findSummaryCount(params);
	}
	@Override
	public List<BillSaleBalance> findSummaryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return areaAmongTransferService.findSummaryByPage(page,sortColumn,sortOrder,params);
	}
	@Override
	public List<TotalDto> findSummaryTotalRow(Map<String, Object> params) {
		return areaAmongTransferService.findSummaryTotalRow(params);
	}

}
