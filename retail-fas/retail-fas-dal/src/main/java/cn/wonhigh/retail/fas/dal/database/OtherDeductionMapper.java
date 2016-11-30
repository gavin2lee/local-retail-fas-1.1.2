package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-24 12:07:35
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
public interface OtherDeductionMapper extends BaseCrudMapper {

	List<OtherDeduction> findFooter(@Param("params") Map<String, Object> params);
	
	/**
	 * (单品牌)根据结算单查询扣项
	 * @param bill
	 * @return
	 */
	OtherDeduction selectOtherDeductionByBillBalance(BillBalance bill);
	
	/**
	 * (多品牌)根据结算单查询扣项
	 * @param bill
	 * @return
	 */
	OtherDeduction selectOtherDeductionOfMultiBrand(BillBalance bill);
	
	/**
	 * 根据结算单查询扣项
	 * @param bill
	 * @return
	 */
	OtherDeduction selectByBillBalance(BillBalance obj);
	
	
	/**
	 * 回写结算单号
	 * @param bill
	 * @return
	 */
	void updateBalanceNo(BillBalance obj);
	
	/**
	 * 回写结算单号
	 * @param bill
	 * @return
	 */
	void clearBalanceNo(BillBalance obj);
	
	/**
	 * 结算查询扣项,返利,其他费用
	 * @param bill
	 * @return
	 */
	OtherDeduction findOtherDeductionBanlance(@Param("params") Map<String, Object> params);
	
	/**
	 * 选单结算根据出库单号修改结算单号
	 * @param bill
	 * @return
	 */
	int updateBalanceNoByBillNos(@Param("params") Map<String, Object> params);
	
	/**
	 * 选单结算修改扣项结算单号
	 * @param bill
	 * @return
	 */
	int updateChoiceBalanceNo(@Param("params") Map<String, Object> params);
	
	/**
	 * 选单结算查询返利,其他费用
	 * @param bill
	 * @return
	 */
	OtherDeduction findChoiceRebateOtherPrice(@Param("billNos") String billNos);
}