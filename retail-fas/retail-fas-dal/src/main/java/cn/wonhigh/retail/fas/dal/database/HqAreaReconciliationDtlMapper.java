/**  
 *   
 * 项目名称：retail-fas-dal  
 * 类名称：HqAreaReconciliationDtlMapper  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2015-3-9 下午1:49:27  
 * @version       
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.HqAreaReconciliationDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface HqAreaReconciliationDtlMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	int selectShipmentCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> selectShipmentByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 查询合计行
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> selectTotalRow(@Param("params")Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<HqAreaReconciliationDto> selectShipmentTotalRow(
			@Param("params")Map<String, Object> params);

}
