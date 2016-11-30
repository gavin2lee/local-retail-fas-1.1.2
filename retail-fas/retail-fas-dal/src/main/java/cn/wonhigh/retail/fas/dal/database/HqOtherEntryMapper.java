/**
 * title:HqOtherEntryMapper.java
 * package:cn.wonhigh.retail.fas.dal.database
 * description:TODO
 * auther:user
 * date:2015-5-8 上午10:50:55
 */
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface HqOtherEntryMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectTotalRow(@Param("params")Map<String, Object> params);

}
