/**
 * title:AreaAmongStorageMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:TODO
 * auther:user
 * date:2015-5-6 下午4:58:42
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface AreaAmongStorageMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectTotalRow(@Param("params")Map<String, Object> params);

}
