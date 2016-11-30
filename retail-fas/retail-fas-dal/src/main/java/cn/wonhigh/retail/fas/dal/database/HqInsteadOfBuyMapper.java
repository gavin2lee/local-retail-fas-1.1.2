/**
 * title:HqInsteadOfBuyMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:总部代采入库明细
 * auther:user
 * date:2015-4-11 下午4:28:18
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface HqInsteadOfBuyMapper extends BaseCrudMapper {

	/**
	 * 查询明细合计行
	 * 
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectTotalRow(
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectEntryCount(@Param("params") Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectEntryByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectEntryTotalRow(
			@Param("params") Map<String, Object> params);
}
