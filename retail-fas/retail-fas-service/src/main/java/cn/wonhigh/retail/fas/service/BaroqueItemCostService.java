/**
 * 
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;
import cn.wonhigh.retail.fas.common.dto.BaroqueItemCostDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * @author user
 *
 */
public interface BaroqueItemCostService  extends BaseCrudService{

	List<BaroqueItemCostDto> getBaroqueHeadquarterCostList(SimplePage page,String orderByField,
			String orderBy,Map<String, Object> params) throws ServiceException;
	
	Integer getBaroqueHeadquarterCostListCount(Map<String, Object> params) throws ServiceException;
	
	List<BaroqueItemCostDto> generateBaroqueHeadquarterCost (Map<String, Object> params) throws ServiceException;

	List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCost(Map<String, Object> params) throws ServiceException;

	List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCostForFF(Map<String, Object> params) throws ServiceException;
	
	List<BaroqueItemCostDto> getDiffHeadquarterCostForFF(Map<String, Object> params) throws ServiceException;
	
	void updateHeadquarterCost(BaroqueItemCostDto item) throws ServiceException;
}
