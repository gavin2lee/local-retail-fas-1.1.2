package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BaroqueItemCostDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 * @author user
 * 
 */
public interface BaroqueItemCostMapper extends BaseCrudMapper {

	List<BaroqueItemCostDto> getBaroqueHeadquarterCostList(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	Integer getBaroqueHeadquarterCostListCount(@Param("params") Map<String, Object> params);
	
	List<BaroqueItemCostDto> generateBaroqueHeadquarterCost (@Param("params") Map<String, Object> params);

	List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCost(@Param("params") Map<String, Object> params);

	List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCostForFF(@Param("params") Map<String, Object> params);
	
	List<BaroqueItemCostDto> getDiffHeadquarterCostForFF(@Param("params") Map<String, Object> params);
	
	void updateHeadquarterCost(BaroqueItemCostDto item);
}
