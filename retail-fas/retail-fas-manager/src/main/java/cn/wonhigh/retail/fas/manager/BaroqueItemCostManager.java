/**
 * 
 */
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;
import cn.wonhigh.retail.fas.common.dto.BaroqueItemCostDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * @author user
 *
 */
public interface BaroqueItemCostManager{
	
	List<BaroqueItemCostDto> getBaroqueHeadquarterCostList(SimplePage page,String orderByField,
			String orderBy,Map<String, Object> params) throws ManagerException;
	
	Integer getBaroqueHeadquarterCostListCount(Map<String, Object> params) throws ManagerException;
	
	List<BaroqueItemCostDto> generateBaroqueHeadquarterCost (Map<String, Object> params) throws ManagerException;
	
    List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCost(Map<String, Object> params) throws ManagerException;
    
    List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCostForFF(Map<String, Object> params) throws ManagerException;
    
    List<BaroqueItemCostDto> getDiffHeadquarterCostForFF(Map<String, Object> params) throws ManagerException;
	
	void updateHeadquarterCost(List<BaroqueItemCostDto> items) throws ManagerException;
}
