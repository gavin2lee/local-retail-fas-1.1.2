package cn.wonhigh.retail.fas.common.utils;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.SaleOutBizTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

public class BalanceTypeConvert {
	
	/**
	 * 采购类单据根据BillType、BizType及其他条件选择结算类型
	 * @param billBalance
	 * @param salerIsHq 卖方是否为总部
	 * @param buyerIsHq 买方是否为总部
	 * @return
	 */
	public static Integer chooseBalanceType(BillBuyBalance billBalance, boolean salerIsHq, boolean buyerIsHq) {
		
		Integer balanceType = null;
		
		//总部厂商 1
		if(
			//到货单 1301 & 买方：总部
			(BillTypeEnums.ASN.getRequestId().equals(billBalance.getBillType())
				&& buyerIsHq)
			//原残退厂发货单 1333 & 买方：总部
			|| (BillTypeEnums.RETURNOWN.getRequestId().equals(billBalance.getBillType())
				&& buyerIsHq)
		) {
			balanceType = BalanceTypeEnums.HQ_VENDOR.getTypeNo();
		}
		//总部代采 13
		else if(
			//到货单 1301 & (订货 0 | 补货 1) & 不拆单 & 买方：地区
			(BillTypeEnums.ASN.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.FIRST_ORDER.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.REPLENISH_ORDER.getStatus().equals(billBalance.getBizType()))
				&& null == billBalance.getIsSplit()
				&& !buyerIsHq)
			//原残退厂发货单 1333 & 不拆单 & 买方：地区
			|| (BillTypeEnums.RETURNOWN.getRequestId().equals(billBalance.getBillType())
				&& null == billBalance.getIsSplit()
				&& !buyerIsHq)) {
			balanceType = BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo();
		}
		//地区间 5
		else if(
			//调货入库单 1372 & 卖方：地区 & 买方：地区
			(BillTypeEnums.TRANSFER_IN.getRequestId().equals(billBalance.getBillType())
				&& !salerIsHq
				&& !buyerIsHq)) {
			balanceType = BalanceTypeEnums.AREA_AMONG.getTypeNo();
		}
		//地区自购 6
		else if(
			//验收单 1304 & (直接 2 | 收购 33 | 直接差异 12 | 收购差异 34) & 买方：地区
			(BillTypeEnums.RECEIPT.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.DIRECT.getStatus().equals(billBalance.getBizType())
						|| BizTypeEnums.PURCHASE.getStatus().equals(billBalance.getBizType())
						|| BizTypeEnums.DIRECT_DIFF.getStatus().equals(billBalance.getBizType())
						|| BizTypeEnums.PURCHASE_DIFF.getStatus().equals(billBalance.getBizType()))
				&& !buyerIsHq)) {
			balanceType = BalanceTypeEnums.AREA_BUY.getTypeNo();
		}
		// 入库单据 反写结算类型
		
		
		return balanceType;
	}
	
	/**
	 * 销售类单据根据BillType、BizType及其他条件选择结算类型
	 * @param billBalance
	 * @param leadRoleCompanyNos 承担总部职能的公司编号字符集
	 * @return
	 */
	public static Integer chooseBalanceType(BillSaleBalance billBalance, boolean salerIsHq, boolean buyerIsHq) {
		
		Integer balanceType = null;
		
		//总部地区 2
		if(
			//到货单 1301 & (订货 0 | 补货 1) & 拆单 & 卖方：总部 & 买方：地区
			(BillTypeEnums.ASN.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.FIRST_ORDER.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.REPLENISH_ORDER.getStatus().equals(billBalance.getBizType()))
				&& null != billBalance.getIsSplit()
				&& salerIsHq
				&& !buyerIsHq)
			//原残退厂发货单 1333 & 拆单 & 卖方：总部 & 买方：地区
			|| (BillTypeEnums.RETURNOWN.getRequestId().equals(billBalance.getBillType())
				&& null != billBalance.getIsSplit()
				&& salerIsHq
				&& !buyerIsHq)
			//调货出库单 1371 & 卖方：总部 & 买方：地区
			|| (BillTypeEnums.TRANSFER_OUT.getRequestId().equals(billBalance.getBillType())
				&& salerIsHq
				&& !buyerIsHq)
			) {
			balanceType = BalanceTypeEnums.HQ_WHOLESALE.getTypeNo();
		}
		//总部其他出库 14
		else if(
			//总部客残销售出库单 2006 & (36) & 拆单 & 卖方：总部 & 买方：地区
			(BillTypeEnums.SALEOUTHQ.getRequestId().equals(billBalance.getBillType())
				&& BizTypeEnums.HQ_CUSTOMER_SALE.equals(billBalance.getBizType())
				&& salerIsHq
				&& !buyerIsHq)) {
			balanceType = BalanceTypeEnums.HQ_OTHER_STOCK_OUT.getTypeNo();
		}
		//地区间 5
		else if(
			//调货出库单 1371 & 卖方：地区 & 买方：地区
			(BillTypeEnums.TRANSFER_OUT.getRequestId().equals(billBalance.getBillType())
				&& !salerIsHq
				&& !buyerIsHq)) {
			balanceType = BalanceTypeEnums.AREA_AMONG.getTypeNo();
		}
		//地区其他出库 11
		else if(
			//客残销售出库单 1335 & 总部承担 3 & 卖方：地区 & 买方：总部
			(BillTypeEnums.SALEOUT.getRequestId().equals(billBalance.getBillType())
				&& SaleOutBizTypeEnums.HEADQUARTERSBEAR.getStatus().equals(billBalance.getBizType())
				&& !salerIsHq
				&& buyerIsHq)
			//借用出库单 1361 & (公司内部借用 7 | 外部公司借用 25) & 卖方：地区 & 买方：总部
			|| (BillTypeEnums.BORROWOUT.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.BORROW_OUT.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.BORROW_OUT_COMPANY.getStatus().equals(billBalance.getBizType()))
				&& !salerIsHq
				&& buyerIsHq)
			//地区客残销售出库单 2005 & (35) & 卖方：地区 & 买方：总部
			|| (BillTypeEnums.SALEOUTZONE.getRequestId().equals(billBalance.getBillType())
				&& BizTypeEnums.ZONE_CUSTOMER_SALE.equals(billBalance.getBizType())
				&& !salerIsHq
				&& buyerIsHq)) {
			balanceType = BalanceTypeEnums.AREA_OTHER.getTypeNo();
		}
		//地区批发 7
		else if(
			//批发/团购出库单 1335 & (批发销售 21 | 批发退货 22)
			(BillTypeEnums.SALEOUTS.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.WHOLESALE.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.WHOLESALE_RETURN.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.CUSTOMER_RETURN.getStatus().equals(billBalance.getBizType())))) {
			balanceType = BalanceTypeEnums.AREA_WHOLESALE.getTypeNo();
		}
		//GMS团购内购 23
		else if(
			//报废单 1342
			(BillTypeEnums.LOSS.getRequestId().equals(billBalance.getBillType()))
			//客残销售出库单 1335 & 地区承担 2
			||(BillTypeEnums.SALEOUT.getRequestId().equals(billBalance.getBillType())
				&& SaleOutBizTypeEnums.AREABEAR.getStatus().equals(billBalance.getBizType()))
			//批发/团购出库单 1335 & 团购销售 23
			|| (BillTypeEnums.SALEOUTS.getRequestId().equals(billBalance.getBillType())
				&& BizTypeEnums.GROUPSALE.getStatus().equals(billBalance.getBizType()))
			//批发/团购出库单 1335 & 团购退货 24
			|| (BillTypeEnums.SALEOUTS.getRequestId().equals(billBalance.getBillType())
				&& BizTypeEnums.GROUPSALE_RETURN.getStatus().equals(billBalance.getBizType()))
			//索赔单 1355 & (索赔单 8 | 盘差差异 10 | 客残内销 26)
			|| (BillTypeEnums.CLAIM.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.CLAIM.getStatus().equals(billBalance.getBizType())
						|| BizTypeEnums.FIRST_ORDER_DIFF.getStatus().equals(billBalance.getBizType())
						|| SaleOutBizTypeEnums.RETURNCLAIM.getStatus().equals(billBalance.getBizType())))) {
			balanceType = BalanceTypeEnums.AREA_SALEORDER.getTypeNo();
		}
		
		return balanceType;
	}
	
	/**
	 * 判断数量是否取反
	 * @param billType
	 * @param bizType
	 * @return
	 */
	public static boolean isReturnBill(Integer billType, Integer bizType) {
		
		boolean result = false;
		
		if(	//原残退厂发货单 1333
			BillTypeEnums.RETURNOWN.getRequestId().equals(billType)
			//批发/团购出库单 1335 & (过季退货 22/客残退货 30/团购退货 24)
			|| (BillTypeEnums.SALEOUTS.getRequestId().equals(billType) && 
				  (BizTypeEnums.WHOLESALE_RETURN.getStatus().equals(bizType)
					|| BizTypeEnums.CUSTOMER_RETURN.getStatus().equals(bizType) 
					|| BizTypeEnums.GROUPSALE_RETURN.getStatus().equals(bizType)))
			//索赔单 1355 & 盘差差异 10
			|| (BillTypeEnums.CLAIM.getRequestId().equals(billType)
				&& BizTypeEnums.FIRST_ORDER_DIFF.getStatus().equals(bizType))) {
			result = true;
		}
		
		return result;
	}
}
