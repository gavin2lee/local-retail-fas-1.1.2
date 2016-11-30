package cn.wonhigh.retail.fas.api.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.model.BillSplitLogModel;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
public interface BillSplitLogApiMapper {

	List<BillSplitLogModel> selectByBillNo(@Param("refBillNo")String refBillNo);

	int insert(BillSplitLogModel model);

	int update(BillSplitLogModel model);
	
}