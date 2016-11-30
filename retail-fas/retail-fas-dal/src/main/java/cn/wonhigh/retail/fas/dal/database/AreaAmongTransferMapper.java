/**  
 *   
 * 项目名称：retail-fas-dal  
 * 类名称：AreaAmongTransferMapper  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2014-10-24 下午3:22:27  
 * @version       
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface AreaAmongTransferMapper extends BaseCrudMapper {

	/**
	 * 查询调拨明细合计行
	 * @param params
	 * @return
	 */
	List<TotalDto> selectTotalRow(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectGatherListByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectGatherCount(@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectSummaryCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectSummaryByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 查询收发汇总合计行
	 * @param params
	 * @return
	 */
	List<TotalDto> selectSummaryTotalRow(
			@Param("params") Map<String, Object> params);

}
