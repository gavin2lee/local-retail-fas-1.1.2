package cn.wonhigh.retail.fas.common.enums;


/**
 * 
 * 单据类型(配送/退仓/收货/退货/盘点)
 * 
 * @author dong.j
 * @date 2013-11-29 下午2:17:35
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum BillTypeEnums {
	DEDUCTION(1999, "其他扣项"), 
	
	PRE_PAY_AMOUNT(2002, "预收款"), 
	//-------采购入库管理-----
	TRANSPORT(1300, "货运单"), 
	ASN(1301, "到货单"), 
	RECEIPT(1304, "验收单"),
	
	//---------仓出店管理---------------------	
	DELIVERYNT(1325, "配货单", "bill_delivery_nt"), 
	DELIVERYNT_OUT(1318, "仓出店单", "bill_delivery", DELIVERYNT), 
	
	//--------------退厂管理-------------------
	RETURNCUSTOMERZONECONFIRM(1331, "地区客残鉴定处理","bill_return"),
	RETURNCUSTOMERCONFIRM(1332, "客残退厂结果鉴定处理","bill_return"),
	RETURNOWN(1333, "原残退厂发货单","bill_return"),
	RETURNCUSTOMER(1334, "客残退厂发货单","bill_return"),
	SALEOUT(1335, "客残销售出库单","bill_sale_out"),
	RETURNO_CONFIRM(1338, "原残退厂收货单","bill_return"),
	REPAIRIN(1340, "返修入库","bill_repair_in"),
	
	//-------------店退残-----------------
	DELIVERY_RETURN_NT(1326, "店退残通知单", "bill_transfer_nt"), 
	DELIVERY_RETURN(1324, "店退残单", "bill_delivery_return", DELIVERY_RETURN_NT), 
	
	//----------------调货------------------------
	BILL_TRANSFER_NT(1321, "调货通知单", "bill_transfer_nt"), 
	BillTransferCargoNt(1323, "跨区调货通知单", "bill_transfer_nt"), 
	
	//从调货通知单生成的仓出店
	DELIVERYNT_OUT_FROM_TRANSFER_NT(1318, "仓出店单", "bill_delivery", BILL_TRANSFER_NT),
		
	STOR_STORE(1341, "移仓出（入）库单（仓到仓）", "bill_transfer"), 
	SHOP_STORE(1319, "移仓出（入）库单（店到仓）", "bill_transfer"), 
	transfer(1317, "店转货单", "bill_transfer", BILL_TRANSFER_NT), 
	REGION_SHOP_SHOP(1320, "调货出（入）库单（店到店）", "bill_transfer", BillTransferCargoNt), 
	REGION_STORE_STORE(1327, "调货出（入）库单（仓到仓）", "bill_transfer", BillTransferCargoNt), 
	REGION_SHOP_STORE(1322, "调货出（入）库单（店到仓）", "bill_transfer", BillTransferCargoNt), 
	REGION_STORE_SHOP(1328, "调货出（入）库单（仓到店）", "bill_transfer", BillTransferCargoNt), 
	
	TRANSFER_OUT(1371, "调货出库单", "bill_transfer", BillTransferCargoNt), //用于生成单据编号和记账
	TRANSFER_IN(1372, "调货入库单", "bill_transfer",BillTransferCargoNt), //用于生成单据编号和记账
	TRANSFERWAREHOUSE(1373, "移仓单", "bill_transfer", BillTransferCargoNt), //用于生成单据编号和记账
	SALEOUTZONE(2005, "地区客残销售出库单","bill_transfer"),
	SALEOUTHQ(2006, "总部客残销售出库单","bill_transfer"),
	
	//-------------批发销售管理------------------------------
	SALEOUTS(1335, "批发/团购出库单", "bill_sale_out"),
	SALEQUOTATION(1336, "报价单"),
	SALEORDER(1337, "批发订单", "bill_sale_order"),
	WHOLESALENT(1339, "批发通知单", "bill_wholesale_nt"),
	GROUP_ORDER(1378, "团购订单", "bill_sale_order"),
	GROUP_PURCHASE_OUT_NT(1329, "团购发货通知单", "bill_wholesale_nt"),
	
	
	//----------------报废管理------------------------
	LOSS(1342, "报废单","bill_sale_out"), 
	
	//---------------库存管理---------------------------------
	INVENTORYYADJUSTNT(1343, "库存调整通知单"),
	//用于生成单据编号
	INVENTORY_ADJUST_CHECK(1370, "区内差异调整审批单"),	
	INVENTORY_ADJUST(1379, "库存调整单"),
	//用于生成单据编号	
	WHOLESALE_OUT(1376, "批发出库单", "bill_sale_out"),
	GROUP_PURCHASE_OUT(1377, "团购出库单", "bill_sale_out"),
	
	RETAIL_BACKUP(1344, "正品转备货"),
	BACKUP_RETAIL(1346, "备货转正品"),
	INVENTORYYADJUST(1345, "库存冻结解冻单"),
	GUEST_QUALITY(1381,"客残转正品"),
	ORIGINAL_QUALITY(1382, "原残转正品"),
	QUALITY_ORIGINAL(1383, "正品转原残"),
	ORDERUNITADJUST(1347, "订货单位调整单"),
	INVENTORY_NUM_ADJUST(1348, "库存数量调整"),
	
	//--------------盘点管理------------------------------
	CHECK(1349, "盘点单"),
	CHECKDIFF(1350, "盘差单"), 
	
	//----------------差异管理--------------------------------
	DiFFRECEIPT(1353,"厂入库差异单"),
	DIFFTRANSFER(1354,"调货差异单"),
	CLAIM(1355,"索赔单"),
	
	//--------------借用管理-----------------------------------
	BORROW(1360, "借用单","bill_borrow"),
	BORROWOUT(1361, "借用出库单","bill_sale_out"),
	BORROWRETURN(1362, "借用单归还单","bill_borrow"),
	BORROWCONFIRM(1363, "借用单归还处理","bill_borrow"),

	RETURNNT(1312, "退货申请单"),
	RETURN(1313, "退货单"),

	salesOrder(1375, "销售预占"),
	salesReturn(1374, "销售退货入库"),
	INVENTORY_COST_ADJUST(2000, "库存成本调整单","bill_inv_cost_adjust"),
	SALE_MEND(2001,"维修单");//用于生成pos 维修单 单据编号

	/**
	 * SERIALNO_CONFIG 表 REQUESTID 的值
	 */
	private Integer requestId;
	/**
	 * 模块描述
	 */
	private String desc;
	/**
	 * 建议枚举名就是表名 ,这样就可以不用tableName
	 */
	private String tableName;

	/**
	 * 所属通知单的BillType
	 */
	private BillTypeEnums ntBillType;

	private BillTypeEnums(Integer requestId) {
		this.requestId = requestId;
	}

	private BillTypeEnums(Integer requestId, String desc) {
		this.requestId = requestId;
		this.desc = desc;
	}

	/**
	 * @param requestId
	 * @param desc
	 * @param tableName
	 */
	private BillTypeEnums(Integer requestId, String desc, String tableName) {
		this.requestId = requestId;
		this.desc = desc;
		this.tableName = tableName;
	}

	/**
	 * @param requestId
	 * @param desc
	 * @param tableName
	 * @param ntBillType
	 */
	private BillTypeEnums(Integer requestId, String desc, String tableName, BillTypeEnums ntBillType) {
		this.requestId = requestId;
		this.desc = desc;
		this.tableName = tableName;
		this.ntBillType = ntBillType;
	}

	/**
	 * 如果没有表明，使用枚举名。建议枚举名就是表名
	 * 
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName == null ? this.toString() : tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * @return the ntBillType
	 */
	public BillTypeEnums getNtBillType() {
		return ntBillType;
	}

	public static String getNameByNo(int no){
		for (BillTypeEnums item : BillTypeEnums.values()) {
			if(item.getRequestId().intValue() == no){
				return item.getDesc();
			}
		}
		return "";
	}
//	public static void main(String[] args) {
////		System.out.println(BillTypeEnums.ALLOT.toString());
//		BillTypeEnums[] values = BillTypeEnums.values();
//		Arrays.sort(values,new Comparator<BillTypeEnums>(){
//
//			@Override
//			public int compare(BillTypeEnums o1, BillTypeEnums o2) {
//				return o1.getRequestId()-o2.getRequestId().intValue();
//			}});
//		for (BillTypeEnums billTypeEnums : values) {
//			System.out.println(billTypeEnums.getRequestId()+":"+billTypeEnums.getDesc());
//		}
//	}
}
