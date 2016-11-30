/**
 * title:ReceiveAndSendMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:TODO
 * auther:user
 * date:2016-8-11 上午10:38:16
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.TransferingCheckDto;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface ReceiveAndSendMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	int selectReceiveAndSendDtlCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<TransferingCheckDto> selectReceiveAndSendDtl(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param object
	 * @param params
	 * @return
	 */
	List<TransferingCheckDto> selectReceiveAndSendDtlFooter(Object object,
			@Param("params")Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	int selectReceiveAndSendSumCount(@Param("params")Map<String, Object> params);

	/**
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<TransferingCheckDto> selectReceiveAndSendSum(
			@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * @param object
	 * @param params
	 * @return
	 */
	List<TransferingCheckDto> selectReceiveAndSendSumFooter(Object object,
			@Param("params")Map<String, Object> params);

}
