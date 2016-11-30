/**  
*   
* 项目名称：retail-fas-dal  
* 类名称：InvoiceConfirmService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-3 下午3:10:36  
* @version       
*/ 
package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.InvoiceConfirm;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface InvoiceConfirmMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	int updateInvoiceConfirm(@Param("params")Map<String, Object> params);

	/**
	 * @param billNo
	 */
	InvoiceConfirm selectInvoiceConfirm(@Param("billNo")String billNo,@Param("shardingFlag")String shardingFlag);

	/**
	 * @param param
	 * @return
	 */
	int updateInvoiceNoOfBillBalance(@Param("params")Map<String, Object> params);

}
