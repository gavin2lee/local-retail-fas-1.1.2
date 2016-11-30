package cn.wonhigh.retail.fas.api.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemDto;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 货号model
 * @author ouyang.zm
 * @date  2014-10-22 15:09:15
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
public interface ItemApiMapper extends BaseCrudMapper {

	List<ItemDto> findByItemNo(@Param("itemNo")String itemNo) throws Exception;
	
}