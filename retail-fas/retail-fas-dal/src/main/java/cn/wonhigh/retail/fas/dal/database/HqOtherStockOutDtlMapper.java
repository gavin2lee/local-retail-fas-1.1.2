/**  
*   
* 项目名称：retail-fas-dal  
* 类名称：AreaOtherStockOutDtlMapper  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午2:19:25  
* @version       
*/ 
package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface HqOtherStockOutDtlMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	List<BillSaleBalance> selectTotalRow(@Param("params")Map<String, Object> params);

}
