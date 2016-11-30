package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.PrintBalanceDiffDto;
import cn.wonhigh.retail.fas.common.dto.PrintBalanceDto;


/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-23 09:29:50
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
public interface PrintMapper {

	List<PrintBalanceDto> selectSendColumnByBalanceNo(@Param("balanceNo")String balanceNo);

	List<PrintBalanceDto> selectSendRowByBalanceNo(@Param("balanceNo")String balanceNo);
	
	List<PrintBalanceDto> selectSendQtyByBalanceNo(@Param("balanceNo")String balanceNo);

	List<PrintBalanceDto> selectCustomReturnQtyByBalanceNo(@Param("balanceNo")String balanceNo);

	List<PrintBalanceDto> selectReturnQtyByBalanceNo(@Param("balanceNo")String balanceNo);

	BigDecimal selectDeductionAmountByBalanceNo(@Param("balanceNo")String balanceNo);

	List<PrintBalanceDto> selectBalanceGatherList(@Param("balanceNo")String balanceNo);

	List<PrintBalanceDto> selectBalanceGatherFooter(@Param("balanceNo")String balanceNo);

	List<PrintBalanceDto> selectYearBalanceGatherFooter(@Param("params")Map<String, Object> params);

	String selectWorkShoesBalanceNo(@Param("params")Map<String, Object> params);

	List<PrintBalanceDiffDto> selectPeriodInList(@Param("params")Map<String, Object> params);
	
	List<PrintBalanceDiffDto> selectPeriodOutList(@Param("params")Map<String, Object> params);
	
	List<PrintBalanceDiffDto> selectPeriodBalanceList(@Param("params")Map<String, Object> params);
	
	List<PrintBalanceDiffDto> selectOweOffListByBill(@Param("params")Map<String, Object> params);

	List<PrintBalanceDiffDto> selectOweOffList(@Param("params")Map<String, Object> params);
	
}