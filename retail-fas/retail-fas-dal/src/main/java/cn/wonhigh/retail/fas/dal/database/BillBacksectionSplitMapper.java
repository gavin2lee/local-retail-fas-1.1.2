package cn.wonhigh.retail.fas.dal.database;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplit;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-13 15:10:37
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
public interface BillBacksectionSplitMapper extends BaseCrudMapper {
	/**
	 * 查询当前时间最新结算单号(用于生成结算单号)
	 * @param backsectionSplit  查询条件
	 * @return String 最新结算单号
	 */
	public String selectBacksectionMaxBillNo(BillBacksectionSplit  backsectionSplit);
	
	/**
	 * 统计晚于此回款单创建的回款单条数
	 * @param id
	 * @return
	 */
	public int selectAfterCount(String id);
}