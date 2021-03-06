package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:14
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface BillInvCostAdjustDtlMapper extends BaseCrudMapper {
	
	int deleteByBillNo(@Param("billNo")String billNo) throws Exception;

	BillInvCostAdjustDtl findLastJoinHeaderByParams(@Param("params")Map<String, Object> params) throws Exception;

	public List<BillInvCostAdjustDtl> findListBLK(@Param("page")SimplePage page, @Param("orderByField")String orderByField,@Param("orderBy")String orderBy, @Param("params")Map<String, Object> params);

	public int findListBLKcount(@Param("params")Map<String, Object> params);
}