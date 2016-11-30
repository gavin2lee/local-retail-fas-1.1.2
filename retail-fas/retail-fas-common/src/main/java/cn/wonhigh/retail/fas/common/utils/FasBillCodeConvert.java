package cn.wonhigh.retail.fas.common.utils;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.SaleOutBizTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;

public class FasBillCodeConvert {
	
	/**
	 * bill_buy_balance表单据根据BillType、BizType及买卖方确定FAS单据类型
	 * @param billBalance
	 * @param salerIsHq 卖方是否为总部
	 * @param buyerIsHq 买方是否为总部
	 * @return
	 */
	public static String chooseFasBillCode(BillBuyBalance billBalance, boolean salerIsHq, boolean buyerIsHq) {
		
		String fasBillCode = "unknown";
		
		//1301到货单
		if(BillTypeEnums.ASN.getRequestId().equals(billBalance.getBillType())) {
			//0订货
			if(BizTypeEnums.FIRST_ORDER.getStatus().equals(billBalance.getBizType())) {
				if(buyerIsHq) {
					fasBillCode = "FG13010001";
				}
				else if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13010003";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13010004";
				}
			}
			//1补货
			else if(BizTypeEnums.REPLENISH_ORDER.getStatus().equals(billBalance.getBizType())) {
				if(buyerIsHq) {
					fasBillCode = "FG13010101";
				}
				else if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13010103";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13010104";
				}
			}
			//2直接
			else if(BizTypeEnums.DIRECT.getStatus().equals(billBalance.getBizType())) {
				if(buyerIsHq) {
					fasBillCode = "FG13010201";
				}
				else if(!buyerIsHq) {
					fasBillCode = "FG13010202";
				}
			}
			//33收购
			else if(BizTypeEnums.PURCHASE.getStatus().equals(billBalance.getBizType())) {
				if(buyerIsHq) {
					fasBillCode = "FG13013301";
				}
				else if(!buyerIsHq) {
					fasBillCode = "FG13013302";
				}
			}
		}
		//1304验收单
		else if (BillTypeEnums.RECEIPT.getRequestId().equals(billBalance.getBillType())) {
			//0订货
			if(BizTypeEnums.FIRST_ORDER.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13040001";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13040002";
				}
			}
			//1补货
			else if(BizTypeEnums.REPLENISH_ORDER.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13040101";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13040102";
				}
			}
			//2直接
			else if(BizTypeEnums.DIRECT.getStatus().equals(billBalance.getBizType())) {
				if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13040201";
				}
			}
			//12直接差异
			else if(BizTypeEnums.DIRECT_DIFF.getStatus().equals(billBalance.getBizType())) {
				if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13041201";
				}
			}
			//33收购
			else if(BizTypeEnums.PURCHASE.getStatus().equals(billBalance.getBizType())) {
				if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13043301";
				}
			}
			//34收购差异
			else if(BizTypeEnums.PURCHASE_DIFF.getStatus().equals(billBalance.getBizType())) {
				if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13043401";
				}
			}
		}
		//1333原残退厂发货单
		else if (BillTypeEnums.RETURNOWN.getRequestId().equals(billBalance.getBillType())) {
			if(buyerIsHq) {
				fasBillCode = "FG13330001";
			}
			else if(salerIsHq && !buyerIsHq) {
				fasBillCode = "FG13330003";
			}
			else if(!salerIsHq && !buyerIsHq) {
				fasBillCode = "FG13330004";
			}
		}
		//1372调货入库单
		else if (BillTypeEnums.TRANSFER_IN.getRequestId().equals(billBalance.getBillType())) {
			//4正常
			if(BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13720401";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13720402";
				}
				else if(!salerIsHq && buyerIsHq) {
					fasBillCode = "FG13720403";
				}
			}
			//5差异
			else if(BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13720501";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13720502";
				}
				else if(!salerIsHq && buyerIsHq) {
					fasBillCode = "FG13720503";
				}
			}
			//40残次跨区
			else if(BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13724001";
				}
			}
			//40残次跨区差异
			else if(BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13724101";
				}
			}
		}
		//2006总部客残销售入库单
		else if (BillTypeEnums.SALEOUTHQ.getRequestId().equals(billBalance.getBillType())) {
			//36总部客残销售
			if(BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG20063602";
			}
		}
		
		return fasBillCode;
	}
	
	/**
	 * bill_sale_balance表单据根据BillType、BizType及买卖方确定FAS单据类型
	 * @param billBalance
	 * @param salerIsHq 卖方是否为总部
	 * @param buyerIsHq 买方是否为总部
	 * @return
	 */
	public static String chooseFasBillCode(BillSaleBalance billBalance, boolean salerIsHq, boolean buyerIsHq) {
		
		String fasBillCode = "unknown";
		
		//1301到货单
		if(BillTypeEnums.ASN.getRequestId().equals(billBalance.getBillType())) {
			//0订货
			if(BizTypeEnums.FIRST_ORDER.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13010002";
				}
			}
			//1补货
			else if(BizTypeEnums.REPLENISH_ORDER.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13010102";
				}
			}
		}
		//1333原残退厂发货单
		else if (BillTypeEnums.RETURNOWN.getRequestId().equals(billBalance.getBillType())) {
			if(salerIsHq && !buyerIsHq) {
				fasBillCode = "FG13330002";
			}
		}
		//1335客残销售出库单|领用出库单|批发出库单|团购出库单
		else if (BillTypeEnums.SALEOUT.getRequestId().equals(billBalance.getBillType())) {
			//2地区承担
			if(SaleOutBizTypeEnums.AREABEAR.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13350201";
			}
			//3总部承担
			else if(SaleOutBizTypeEnums.HEADQUARTERSBEAR.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13350301";
			}
			//13物料领用出库
			else if(SaleOutBizTypeEnums.MATERIAL_SALE.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13351301";
			}
			//21批发销售
			else if(BizTypeEnums.WHOLESALE.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13352101";
			}
			//22过季退货
			else if(BizTypeEnums.WHOLESALE_RETURN.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13352201";
			}
			//29批发出库-店出
			else if(BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().equals(billBalance.getBizType())){
				fasBillCode = "FG13352901";
			}
			//30客残退货
			else if(BizTypeEnums.CUSTOMER_RETURN.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13353001";
			}
			//23团购销售
			else if(SaleOutBizTypeEnums.GROUP_SALE.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13352301";
			}
			//24团购退货
			else if(SaleOutBizTypeEnums.GROUP_RETURN.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13352401";
			}
		}
		//1342报废单
		else if (BillTypeEnums.LOSS.getRequestId().equals(billBalance.getBillType())) {
			fasBillCode = "FG13420001";
		}
		//1355内销单
		else if (BillTypeEnums.CLAIM.getRequestId().equals(billBalance.getBillType())) {
			//8索赔
			if(SaleOutBizTypeEnums.CLAIM.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13550801";
			}
			//10盘差
			else if(10 == billBalance.getBizType().intValue()) {
				fasBillCode = "FG13551001";
			}
			//26客残内销
			else if(SaleOutBizTypeEnums.RETURNCLAIM.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13552601";
			}
		}
		//1361借用出库单
		else if (BillTypeEnums.BORROWOUT.getRequestId().equals(billBalance.getBillType())) {
			//7公司内部借用
			if(BizTypeEnums.BORROW_OUT.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13610701";
			}
			//25外部公司借用
			else if(BizTypeEnums.BORROW_OUT_COMPANY.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG13612501";
			}
		}
		//1371调货出库单
		else if (BillTypeEnums.TRANSFER_OUT.getRequestId().equals(billBalance.getBillType())) {
			//4正常
			if(BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13710401";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13710402";
				}
				else if(!salerIsHq && buyerIsHq) {
					fasBillCode = "FG13710403";
				}
			}
			//5差异
			else if(BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13710501";
				}
				else if(!salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13710502";
				}
				else if(!salerIsHq && buyerIsHq) {
					fasBillCode = "FG13710503";
				}
			}
			//40残次跨区
			else if(BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13714001";
				}
			}
			//40残次跨区差异
			else if(BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())) {
				if(salerIsHq && !buyerIsHq) {
					fasBillCode = "FG13714101";
				}
			}
		}
		//2005地区客残销售出库单
		else if (BillTypeEnums.SALEOUTZONE.getRequestId().equals(billBalance.getBillType())) {
			//35地区客残销售
			if(BizTypeEnums.ZONE_CUSTOMER_SALE.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG20053501";
			}
		}
		//2006总部客残销售出库单
		else if (BillTypeEnums.SALEOUTHQ.getRequestId().equals(billBalance.getBillType())) {
			//36总部客残销售
			if(BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())) {
				fasBillCode = "FG20063601";
			}
		}
		
		return fasBillCode;
	}
	
	/**
	 * order_main表单据根据business_mode、business_type确定FAS单据类型
	 * @param billBalance
	 * @param salerIsHq 卖方是否为总部
	 * @param buyerIsHq 买方是否为总部
	 * @return
	 */
	public static String chooseFasBillCode(OrderMain billBalance) {
		
		String fasBillCode = "unknown";
		
		if(billBalance.getBusinessType().intValue() == 0) {
			fasBillCode = "FP10010000";
		}
		else if(billBalance.getBusinessType().intValue() == 1) {
			fasBillCode = "FP10010001";
		}
		else if(billBalance.getBusinessType().intValue() == 2) {
			fasBillCode = "FP10010002";
		}
		else if(billBalance.getBusinessType().intValue() == 3) {
			fasBillCode = "FP10010003";
		}
		else if(billBalance.getBusinessType().intValue() == 6) {
			fasBillCode = "FP10010006";
		}
		
		return fasBillCode;
	}
	
	/**
	 * return_exchange_main表单据根据business_mode、business_type确定FAS单据类型
	 * @param billBalance
	 * @param salerIsHq 卖方是否为总部
	 * @param buyerIsHq 买方是否为总部
	 * @return
	 */
	public static String chooseFasBillCode(ReturnExchangeMain billBalance) {
		
		String fasBillCode = "unknown";
		
		//换货
		if(billBalance.getBusinessMode().intValue() == 1){
			if(billBalance.getBusinessType().intValue() == 0) {
				fasBillCode = "FP10020100";
			}
			else if(billBalance.getBusinessType().intValue() == 1) {
				fasBillCode = "FP10020101";
			}
			else if(billBalance.getBusinessType().intValue() == 2) {
				fasBillCode = "FP10020102";
			}
			else if(billBalance.getBusinessType().intValue() == 3) {
				fasBillCode = "FP10020103";
			}
			else if(billBalance.getBusinessType().intValue() == 6) {
				fasBillCode = "FP10020106";
			}
		}
		//退货
		else if(billBalance.getBusinessMode().intValue() == 2) {
			if(billBalance.getBusinessType().intValue() == 0) {
				fasBillCode = "FP10020200";
			}
			else if(billBalance.getBusinessType().intValue() == 1) {
				fasBillCode = "FP10020201";
			}
			else if(billBalance.getBusinessType().intValue() == 2) {
				fasBillCode = "FP10020202";
			}
			else if(billBalance.getBusinessType().intValue() == 3) {
				fasBillCode = "FP10020203";
			}
			else if(billBalance.getBusinessType().intValue() == 6) {
				fasBillCode = "FP10020206";
			}
		}
		
		return fasBillCode;
	}
}
