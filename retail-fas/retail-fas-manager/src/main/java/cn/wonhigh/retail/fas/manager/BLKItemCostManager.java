package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.ItemCost;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface BLKItemCostManager extends BaseCrudManager  {

	/**
	 * 巴洛克生成成本
	 * @param itemCost
	 * @throws ManagerException 
	 */
	public boolean generateBLKItemCost(ItemCost itemCost) throws ManagerException;
	
	/**
	 * 巴洛克生成成本与器件结存
	 * @param dto
	 * @param params
	 * @param itemCost
	 * @throws ManagerException
	 */
	public void generateCostAndPeriodCase(ItemCostConditionDto dto, ItemCost itemCost)throws ManagerException;
	
	/**
	 * 巴洛克查询总计
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	public int findBLKItemCostCount(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 巴洛克查询明细
	 * @param page
	 * @param orderBy
	 * @param orderByField
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	public List<ItemCost> findBLKItemCostList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ManagerException;

}
