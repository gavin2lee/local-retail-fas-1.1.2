/**  
 *   
 * 项目名称：retail-fas-manager  
 * 类名称：HqAreaReconciliationDtlManagerImpl  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2015-3-9 下午12:04:40  
 * @version       
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.HqAreaReconciliationDto;
import cn.wonhigh.retail.fas.service.HqAreaReconciliationDtlService;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("hqAreaReconciliationDtlManager")
public class HqAreaReconciliationDtlManagerImpl extends BaseCrudManagerImpl
		implements HqAreaReconciliationDtlManager {
	@Resource
	private HqAreaReconciliationDtlService hqAreaReconciliationDtlService;

	@Override
	protected BaseCrudService init() {
		return hqAreaReconciliationDtlService;
	}

	@Override
	public int findShipmentCount(Map<String, Object> params) {
		return hqAreaReconciliationDtlService.findShipmentCount(params);
	}

	@Override
	public List<HqAreaReconciliationDto> findShipmentByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return hqAreaReconciliationDtlService.findShipmentByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<HqAreaReconciliationDto> findTotalRow(Map<String, Object> params) {
		return hqAreaReconciliationDtlService.findTotalRow(params);
	}

	@Override
	public List<HqAreaReconciliationDto> findShipmentTotalRow(
			Map<String, Object> params) {
		return hqAreaReconciliationDtlService.findShipmentTotalRow(params);
	}
}
