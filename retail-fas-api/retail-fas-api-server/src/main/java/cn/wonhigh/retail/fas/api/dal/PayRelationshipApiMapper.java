package cn.wonhigh.retail.fas.api.dal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ContractDiscount;
import cn.wonhigh.retail.fas.common.model.PayRelationship;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-04-07 12:00:45
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
public interface PayRelationshipApiMapper {

	PayRelationship selectSumBuyBalanceAmount(@Param("params")Map<String, Object> params);

	PayRelationship selectSumSaleBalanceAmount(@Param("businessBillNo")String businessBillNo);

	ContractDiscount selectLastContractDiscount(@Param("params")Map<String, Object> params);

	Date selectAsnDueDateByBillNo(PayRelationship payRelationship);
	
	int insertSelective(PayRelationship ship);

	List<PayRelationship> selectByBiz(@Param("params")Map<String, Object> params);

	int deleteByBillNo(@Param("businessBillNo")String businessBillNo);

	List<PayRelationship> selectBuyBalanceList(PayRelationship payRelationship);
	
	List<PayRelationship> selectSaleBalanceList(PayRelationship payRelationship);

	List<PayRelationship> selectBuyReceiptList(@Param("businessBillNo")String businessBillNo);
	
	List<PayRelationship> selectBuyZoneList(@Param("businessBillNo")String businessBillNo);
	
	int updateSaleCostById(@Param("id")String id, @Param("cost")BigDecimal cost, @Param("tagPrice")BigDecimal tagPrice);

	int updateBuyCostById(@Param("id")String id, @Param("cost")BigDecimal cost, @Param("tagPrice")BigDecimal tagPrice);

	int updateBuyReturnCostByBillNo(@Param("businessBillNo")String businessBillNo);

	int updateSaleReturnCostByBillNo(@Param("businessBillNo")String businessBillNo);

	int swapBuySaleByBillNo(@Param("businessBillNo")String businessBillNo);

	PayRelationship selectRefShip(@Param("businessBillNo")String businessBillNo);

	List<PayRelationship> selectBuyAsnDiffList(PayRelationship payRelationship);

	List<PayRelationship> selectSaleAsnDiffList(PayRelationship payRelationship);

	int selectBalanceCountByBillNo(@Param("businessBillNo")String  billNo);

}