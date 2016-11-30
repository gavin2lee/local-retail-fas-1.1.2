package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.PayRelationship;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface PayRelationshipMapper extends BaseCrudMapper {

	/**
	 * 查询明细数量
	 * @param params
	 * @return
	 */
	int findItemCount(@Param("params")Map<String, Object> params);

	/**
	 * 查询明细集合
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	List<PayRelationship> findItemList(@Param("page")SimplePage page, @Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params);
	
	/**
	 * 查询footer
	 * @param params
	 * @return
	 */
	List<PayRelationship> findFooter(@Param("params")Map<String, Object> params);

	/**
	 * 汇总其他扣项
	 * @param params
	 * @return
	 */
	BigDecimal selectSumNoBalanceDeduction(@Param("params")Map<String, Object> params);

	/**
	 * 汇总采购调整金额
	 * @param params
	 * @return
	 */
	BigDecimal selectSumNoBalanceReturn(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询buy表需要更新的明细
	 * @param payRelationship
	 * @return
	 */
	List<PayRelationship> selectBuyBalanceList(PayRelationship payRelationship);

	/**
	 * 查询sale表需要更新的明细
	 * @param payRelationship
	 * @return
	 */
	List<PayRelationship> selectSaleBalanceList(PayRelationship payRelationship);
	
	/**
	 * 查询buy表验收单需要更新的明细
	 * @param billNo
	 * @return
	 */
	List<PayRelationship> selectBuyReceiptList(@Param("businessBillNo")String billNo);

	/**
	 * 查询buy表地区采购需要更新的明细
	 * @param billNo
	 * @return
	 */
	List<PayRelationship> selectBuyZoneList(@Param("businessBillNo")String billNo);

	/**
	 * 查询到货差异的单据
	 * @param billNo
	 * @return
	 */
	List<PayRelationship> selectAsnDiffList(@Param("businessBillNo")String billNo);

	/**
	 * 更新buy单价，牌价
	 * @param id
	 * @param cost
	 * @param tagPrice
	 * @return
	 */
	int updateBuyCostById(@Param("id")String id, @Param("cost")BigDecimal cost, @Param("tagPrice")BigDecimal tagPrice, @Param("itemName")String itemName, @Param("categoryNo")String categoryNo, @Param("categoryName")String categoryName);
	
	/**
	 * 更新sale单价，牌价
	 * @param id
	 * @param cost
	 * @param tagPrice
	 * @return
	 */
	int updateSaleCostById(@Param("id")String id, @Param("cost")BigDecimal cost, @Param("tagPrice")BigDecimal tagPrice, @Param("itemName")String itemName, @Param("categoryNo")String categoryNo, @Param("categoryName")String categoryName);

	/**
	 * 更新buy单价，牌价
	 * @param params
	 * @param cost
	 * @param tagPrice
	 * @return
	 */
	int updateBuyCostByParams(@Param("billNo")String billNo,@Param("itemCode")String itemCode, @Param("cost")BigDecimal cost, @Param("tagPrice")BigDecimal tagPrice);

	/**
	 * 更新sale单价，牌价
	 * @param params
	 * @param cost
	 * @param tagPrice
	 * @return
	 */
	int updateSaleCostByParams(@Param("billNo")String billNo,@Param("itemCode")String itemCode,  @Param("cost")BigDecimal cost, @Param("tagPrice")BigDecimal tagPrice);
	
	/**
	 * 更新到期日
	 * @param params
	 * @return
	 */
	int updateDueDate(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询返利总计
	 * @param params
	 * @return
	 */
	PayRelationship findReturnProfitCount(@Param("params")Map<String,Object> params);

	/**
	 * 分页查询返利总明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<PayRelationship> findReturnProfitList(@Param("page")SimplePage page,@Param("orderByField")String orderByField,@Param("orderBy")String orderBy,@Param("params")Map<String,Object> params);

	/**
	 * 更新折扣
	 * @param payRelationship
	 * @return
	 */
	int updateBalanceDiscount(PayRelationship payRelationship);
	
	/**
	 * 更新金额
	 * @param payRelationship
	 * @return
	 */
	int updateBalanceAmount(PayRelationship payRelationship);

	/**
	 * 更新对账标记
	 * @param payRelationship
	 * @return
	 */
	int updateBalanceFlag(PayRelationship payRelationship);

	/**
	 * 查询需要更新折扣的单据
	 * @param page
	 * @param params
	 * @return
	 */
	List<PayRelationship> selectUpdateDiscountList(@Param("page")SimplePage page,@Param("params")Map<String,Object> params);

	/**
	 * 查询需要更新价格的单据
	 * @param page
	 * @param params
	 * @return
	 */
	List<PayRelationship> selectUpdateCostList(@Param("page")SimplePage page,@Param("params")Map<String,Object> params);

	/**
	 * 查询待结算单据
	 * @param params
	 * @return
	 */
	List<BillBalance> queryBalanceList(@Param("params")Map<String, Object> params);

	/**
	 * 回写关系表结算单号
	 * @param billBalance
	 * @return
	 */
	int updateShipBalanceNo(@Param("params")Map<String, Object> params);

	/**
	 * 回写buy表结算单号
	 * @param billBalance
	 * @return
	 */
	int updateBuyBalanceNo(@Param("params")Map<String, Object> params);

	/**
	 * 回写其他扣项表结算单号
	 * @param billBalance
	 * @return
	 */
	int updateDeductionBalanceNo(BillBalance billBalance);

	/**
	 * 回写采购调整单结算单号
	 * @param billBalance
	 * @return
	 */
	int updateAdjustBalanceNo(BillBalance billBalance);

	/**
	 * 查询更新buy表差异单价格
	 * @param businessBillNo
	 * @return
	 */
	List<PayRelationship> selectBuyAsnDiffList(PayRelationship payRelationship);

	/**
	 * 查询更新sale表差异单价格
	 * @param businessBillNo
	 * @return
	 */
	List<PayRelationship> selectSaleAsnDiffList(PayRelationship payRelationship);

	/**
	 * 查询不含税金额
	 * @param businessBillNo
	 * @return
	 */
	BigDecimal selectNotTaxAmount(PayRelationship payRelationship);
	
}