package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ItemReturnRecord;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:13:46
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface ReturnExchangeMainMapper extends BaseCrudMapper {
	
	/**
	 * 修改销售订单orderMainDto
	 * @param orderMainDto
	 * @return
	 */
	public void updateOrderForInvoiceNoAndDate(@Param("params")Map<String, Object> params);
	
	public ItemReturnRecord findItemReturnCount(@Param("params")Map<String, Object> params);
	
	public List<ItemReturnRecord> findItemReturnList(@Param("page")SimplePage page,@Param("orderBy")String orderBy,@Param("orderByField")String orderByField,@Param("params")Map<String, Object> params);
}