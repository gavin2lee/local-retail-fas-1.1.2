package cn.wonhigh.retail.fas.common.enums;

/**
 * 地区商场相关的枚举类
 * 
 * @author yang.y
 */
public class ShopMallEnums {
	
	// 结算单状态
	public enum BalanceStatus {
		VERIFY(2, "已审核"),
		NO_VERIFY(1, "未审核");
		
		private Integer requestId;
		private String requestName;
		
		private BalanceStatus(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}

	// 结算类型
	public enum BalanceType {
		FORMAL_BALANCE(1, "正式"),
		ESTIMATE_BALANCE(2, "预估");
		
		private Integer requestId;
		private String requestName;
		
		private BalanceType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 费用性质
	public enum CostType {
		
		IN_CONTRACT(1, "合同内"),
		OUT_CONTRACT(2, "合同外");
		
		private Integer requestId;
		private String requestName;
		
		private CostType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 费用扣取方式
	public enum CostDeductType {
		BEFORE_INVOICE(1, "票前"),
		AFTER_INVOICE(2, "票后");
		
		private Integer requestId;
		private String requestName;
		
		private CostDeductType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	//费用类型
	public enum DeductType {
		GD(1, "固定额度"),
		KL(2, "扣率计算额度");
		
		private Integer requestId;
		private String requestName;
		
		private DeductType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 票前费用生成方式
	public enum CostDeductGenerateType {
		SYS_GENERATE(0, "系统自动生成"),
		PAGE_GENERATE(1, "界面上新增");
		
		private Integer requestId;
		private String requestName;
		
		private CostDeductGenerateType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 费用交款方式(1-帐扣,2-现金)
	public enum CostPayType {
		DEBIT(1, "帐扣"),
		CASH(2, "现金");
		
		private Integer requestId;
		private String requestName;
		
		private CostPayType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 扣率类型(rate_basic)
	public enum RateType {
		CONTRACT_ZJK(1, "合同正价扣"),
		contract_JTK(2, "合同阶梯扣");
		
		private Integer requestId;
		private String requestName;
		
		private RateType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 是否使用(rate_basic)
	public enum UseFlag {
		USED(0, "已使用"),
		NO_USE(1, "未使用");
		
		private Integer requestId;
		private String requestName;
		
		private UseFlag(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 结算类型(rate_expense_operate)
	public enum SettlementType {
		BD_ADD_RATE(1, "阶段保底+扣率"),
		RENT(2, "纯租金"),
		MAX_RENT_RATE(3, "租金或扣率的最大值"),
		RENT_ADD_RATE(4, "租金+扣率");
		
		private Integer requestId;
		private String requestName;
		
		private SettlementType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 费用扣取方式(rate_expense_operate)
	public enum DebitedMode {
		BEFORE_INVOICE(1, "票前"),
		AFTER_INVOICE(2, "票后");
		
		private Integer requestId;
		private String requestName;
		
		private DebitedMode(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 费用交款方式(rate_expense_operate)
	public enum PaymentMode {
		account(1, "账款"),
		current_price(2, "现价");
		
		private Integer requestId;
		private String requestName;
		
		private PaymentMode(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 超额统一扣率(rate_expense_operate)
	public enum UnityRateFlag {
		UNIFIED(0, "统一 "),
		NO_UNIFIED(1, "不统一");
		
		private Integer requestId;
		private String requestName;
		
		private UnityRateFlag(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 扣费规则(rate_expense_sporadic)
	public enum DebitedRule {
		MONTHLY_QUOTA(1, "月定额 "),
		RATE(2, "费率");
		
		private Integer requestId;
		private String requestName;
		
		private DebitedRule(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}
		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}
		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 其他零星费用(rate_expense_sporadic)
	public enum SporadicBaseOther {
		vip_sale_amount("0", "商场VIP销售达成金额 "),
		vip_disc_amount("1", "商场VIP折让金额"),
		sale_amount("2", "销售金额");
		
		private String requestId;
		private String requestName;
		
		private SporadicBaseOther(String requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public String getRequestId() {
			return requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 结算差异类型(bill_shop_balance_diff)
	public enum BalanceDiffType {
		DETAIL(1, "按明细"),
		SALES_PROMOTION(2, "按促销活动"),
		SALE(3, "按销售 "),
		CODE(4,"按商场结算码");
		
		private Integer requestId;
		private String requestName;
		
		private BalanceDiffType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}

		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}

		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
	
	// 提示消息类型
	public enum ErrorType {
		SUCCESS(0, "成功"),
		FAIL(1, "失败");
		
		private Integer requestId;
		private String requestName;
		
		private ErrorType(Integer requestId, String requestName) {
			this.requestId = requestId;
			this.requestName = requestName;
		}
		
		public Integer getRequestId() {
			return requestId;
		}

		public void setRequestId(Integer requestId) {
			this.requestId = requestId;
		}

		public String getRequestName() {
			return requestName;
		}
		public void setRequestName(String requestName) {
			this.requestName = requestName;
		}
	}
}
