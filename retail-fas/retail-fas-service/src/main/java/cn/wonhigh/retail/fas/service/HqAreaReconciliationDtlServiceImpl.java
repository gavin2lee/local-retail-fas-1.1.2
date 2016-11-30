/**  
 *   
 * 项目名称：retail-fas-service  
 * 类名称：HqAreaReconciliationDtlServiceImpl  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2015-3-9 下午12:11:42  
 * @version       
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.HqAreaReconciliationDto;
import cn.wonhigh.retail.fas.dal.database.HqAreaReconciliationDtlMapper;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("hqAreaReconciliationDtlService")
public class HqAreaReconciliationDtlServiceImpl extends BaseCrudServiceImpl
		implements HqAreaReconciliationDtlService {
	@Resource
	private HqAreaReconciliationDtlMapper hqAreaReconciliationDtlMapper;

	@Override
	public BaseCrudMapper init() {
		return hqAreaReconciliationDtlMapper;
	}

	@Override
	public int findShipmentCount(Map<String, Object> params) {
		return hqAreaReconciliationDtlMapper.selectShipmentCount(params);
	}

	@Override
	public List<HqAreaReconciliationDto> findShipmentByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return hqAreaReconciliationDtlMapper.selectShipmentByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public List<HqAreaReconciliationDto> findTotalRow(Map<String, Object> params) {
		return hqAreaReconciliationDtlMapper.selectTotalRow(params);
	}

	@Override
	public List<HqAreaReconciliationDto> findShipmentTotalRow(
			Map<String, Object> params) {
		return hqAreaReconciliationDtlMapper.selectShipmentTotalRow(params);
	}
}
