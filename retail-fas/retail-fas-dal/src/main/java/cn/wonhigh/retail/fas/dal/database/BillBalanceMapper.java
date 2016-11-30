package cn.wonhigh.retail.fas.dal.database;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 14:50:55
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
public interface BillBalanceMapper extends BaseCrudMapper {
	
	/**
	 * 更新入库结算单号
	 * @param bill
	 * @return
	 */
	public int updateBuyBalanceNo(BillBalance bill);
	
	/**
	 * 更新出库结算单号
	 * @param bill
	 * @return
	 */
	public int updateSaleBalanceNo(BillBalance bill);
	
	/**
	 * 更新扣项结算单号(单品牌部)
	 * @param bill
	 * @return
	 */
	public int updateDeductionBalanceNo(BillBalance bill);
	
	/**
	 * 更新客残明细结算单号
	 * @param bill
	 * @return
	 */
	public int updateImperfectBalanceNo(BillBalance bill);
	
	/**
	 * 回写结算单号到其他扣项(多品牌)
	 * @param bill
	 * @return
	 */
	public int updateDeductionWithMultiBrandNo(BillBalance bill);
	
	/**
	 * 回写结算单号到其他扣项(多品牌) 地区间、总部代采、地区自购
	 * @param bill
	 * @return
	 */
	public int updateBalanceNoForMultiBrand(BillBalance bill);
	/**
	 * 选单结算回写结算单号到其他扣项(多品牌) 地区间、总部代采、地区自购
	 * @param bill
	 * @return
	 */
	public int updateChoiceDeductionWithMultiBrandNo(BillBalance bill);
	
	/**
	 * 清除入库结算单号
	 * @param balanceNo
	 * @return
	 */
	public int clearBuyBalanceNo(@Param("balanceNo")String balanceNo);
	
	/**
	 * 清除出库结算单号
	 * @param balanceNo
	 * @return
	 */
	public int clearSaleBalanceNo(@Param("balanceNo")String balanceNo);
	
	/**
	 * 清除客残明细结算单号
	 * @param balanceNo
	 * @return
	 */
	public int clearImperfectBalanceNo(@Param("balanceNo")String balanceNo);
	
	/**
	 * 清除扣项结算单号
	 * @param balanceNo
	 * @return
	 */
	public int clearDeductionBalanceNo(@Param("balanceNo")String balanceNo);
	
	/**
	 * 重置分摊后价格
	 */
	public int resetSaleBalanceActualCost(@Param("balanceNo")String balanceNo);
	
	/**
	 * (根据结算单号)更新入库结算状态
	 * @param bill
	 * @return
	 */
	public int updateBuyBalanceStatus(BillBalance bill);

	/**
	 * (根据结算单号)更新出库结算状态
	 * @param bill
	 * @return
	 */
	public int updateSaleBalanceStatus(BillBalance bill);
	
	/**
	 * (根据结算单号)更新扣项结算状态
	 * @param bill
	 * @return
	 */
	public int updateDeductionBalanceStatus(BillBalance bill);
	
	/**
	 * (根据结算单号)更新残鞋结算状态
	 * @param bill
	 * @return
	 */
	public int updateImperfectBalanceStatus(BillBalance bill);
	
	/**
	 * (根据结算单号)更新结算单状态
	 * @param bill
	 * @return
	 */
	public int updateStatus(BillBalance bill);
	
	/**
	 * (根据结算单号)审核结算单
	 * @param bill
	 * @return
	 */
	public int verify(BillBalance bill);

	/**
	 * (根据结算单号)反写采购价
	 * @param bill
	 * @return
	 */
	public void updateBuyPurchasePrice(BillBalance bill);
	
	/**
	 * 分摊票前返利至销售价
	 * @param bill
	 * @return
	 */
	public int updateApportionRebate(BillBalance bill);
	
	/**
	 * 分摊票前返利至销售价
	 * @param bill
	 * @return
	 */
	public int updateApportionRebateAgain(BillBalance bill);

	/**
	 * 清除关系表记录
	 * @param bill
	 * @return
	 */
	public int clearPayRelationShip(@Param("balanceNo")String balanceNo);

	/**
	 * 清除采购入库调整单记录
	 * @param bill
	 * @return
	 */
	public int clearBuyAdjustBalanceNo(@Param("balanceNo")String balanceNo);

	/**
	 * 清除采购入库调整单记录
	 * @param bill
	 * @return
	 */
	public int clearSaleAdjustBalanceNo(@Param("balanceNo")String balanceNo);
	
	/**
	 * 更新采购入库调整单结算号
	 * @param bill
	 * @return
	 */
	public int updateSaleAdjustBalanceNo(BillBalance bill);

}