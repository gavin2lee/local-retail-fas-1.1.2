package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSplitDataDtl;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 15:58:32
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
public interface BillSplitDataDtlMapper extends BaseCrudMapper {
	
	public List<BillSplitDataDtl> selectBillAsn(@Param("model")BillSplitDataDtl modelType,
			@Param("params")Map<String,Object> params);
	
	public List<BillSplitDataDtl> selectBillReturn(@Param("model")BillSplitDataDtl modelType,
			@Param("params")Map<String,Object> params);

	public int updateSplitFlag(@Param("params")Map<String, Object> params);
}