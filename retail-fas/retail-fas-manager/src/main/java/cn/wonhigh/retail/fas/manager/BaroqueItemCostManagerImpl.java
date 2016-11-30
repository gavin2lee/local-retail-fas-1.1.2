/**
 * 
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BaroqueItemCostDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.service.BaroqueItemCostService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * @author user
 * 
 */
@Service("baroqueItemCostManager")
public class BaroqueItemCostManagerImpl implements BaroqueItemCostManager {

	@Resource
	private BaroqueItemCostService baroqueItemCostService;

	@Override
	public List<BaroqueItemCostDto> getBaroqueHeadquarterCostList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueItemCostService.getBaroqueHeadquarterCostList(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Integer getBaroqueHeadquarterCostListCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueItemCostService.getBaroqueHeadquarterCostListCount(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BaroqueItemCostDto> generateBaroqueHeadquarterCost(Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueItemCostService.generateBaroqueHeadquarterCost(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCost(Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueItemCostService.generateBaroqueRegionHeadquarterCost(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCostForFF(Map<String, Object> params)
			throws ManagerException {
		try {
			return this.baroqueItemCostService.generateBaroqueRegionHeadquarterCostForFF(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}	
	}

	@Override
	public List<BaroqueItemCostDto> getDiffHeadquarterCostForFF(Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueItemCostService.getDiffHeadquarterCostForFF(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateHeadquarterCost(List<BaroqueItemCostDto> items) throws ManagerException {
		try {
			if(null!=items){
				for(BaroqueItemCostDto item : items){
					this.baroqueItemCostService.updateHeadquarterCost(item);
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
}
