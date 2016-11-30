/**
 * title:OweTheGuestMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:TODO
 * auther:user
 * date:2015-7-1 下午5:38:21
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.OweTheGuestInventoryDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface OweTheGuestMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<OweTheGuestInventoryDto> selectTotalRow(
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectSumCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<OweTheGuestInventoryDto> selectSumByPage(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<OweTheGuestInventoryDto> selectSumTotalRow(@Param("params")Map<String, Object> params);

}
