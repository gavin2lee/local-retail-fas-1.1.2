package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjustDtl;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
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
public interface BillPurchaseAdjustDtlMapper extends BaseCrudMapper {
	
	int deleteByBillNo(@Param("billNo")String billNo) throws Exception;

	int findDtlCount(@Param("params")Map<String, Object> params)throws Exception;

	List<BillPurchaseAdjustDtl> findDtlList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<BillPurchaseAdjustDtl> findDtlFooter(@Param("params")Map<String, Object> params);

}