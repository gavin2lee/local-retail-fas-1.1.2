package cn.wonhigh.retail.fas.api.dal;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.SupplierRateSetDto;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-05-03 14:31:13
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
public interface SupplierRateSetMapper extends BaseCrudMapper {
	
	List<SupplierRateSetDto> getSupplierRateSetBySupplierNo(@Param("params")Map<String, Object> params);

}