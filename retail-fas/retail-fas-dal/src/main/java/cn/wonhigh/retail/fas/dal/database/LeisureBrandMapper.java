/**
 * title:LeisureBrandMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:TODO
 * auther:user
 * date:2016-3-15 上午11:14:12
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface LeisureBrandMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectDynColumns(@Param("params")Map<String, Object> params);
	
	List<Map<String, Object>> selectSumByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	List<Map<String, Object>> selectTotalRow(@Param("params") Map<String, Object> params);
}
