package cn.wonhigh.retail.fas.common.enums;

/**
 * 操作日志的枚举类
 * 
 * @author yang.y
 */
public class OperateLogEnums {

	// 操作动作的枚举类
	public enum OperateAction {

		ADD(1, "新增"), MODIFY(2, "修改"), DELETE(3, "删除"), VERIFY(4, "审核");

		/** 操作动作编码 */
		private int operateNo;

		/** 操作动作名称 */
		private String operateName;

		private OperateAction(int operateNo, String operateName) {
			this.operateNo = operateNo;
			this.operateName = operateName;
		}

		public int getOperateNo() {
			return operateNo;
		}

		public void setOperateNo(int operateNo) {
			this.operateNo = operateNo;
		}

		public String getOperateName() {
			return operateName;
		}

		public void setOperateName(String operateName) {
			this.operateName = operateName;
		}
	}

	// 操作模块的枚举类
	public enum OperateModule {

		TEST(1, "测试模块"),

		JSD(2, "结算单"),
		
		QKD(3, "请款单"),
		
		FKD(4, "付款单");
		
		/** 模块编码 */
		private int moduleNo;

		/** 模块名称 */
		private String moduleName;

		private OperateModule(int moduleNo, String moduleName) {
			this.moduleNo = moduleNo;
			this.moduleName = moduleName;
		}

		public int getModuleNo() {
			return moduleNo;
		}

		public void setModuleNo(int moduleNo) {
			this.moduleNo = moduleNo;
		}

		public String getModuleName() {
			return moduleName;
		}

		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}
	}

	// 操作状态的枚举类
	public enum OperateStatus {
		BACK(0, "打回"),
		POST(1, "通过"), 
		SUBMIT_AUDIT(2, "提交审批"), 
		CANCEL(3, "撤销"),
		ONE_LEVEL_AUDIT(4, "一级审批"), 
		TWO_LEVEL_AUDIT(5, "二级审批"),
		THREE_LEVEL_AUDIT(6, "三级审批"),
		BACK_AUDIT(7, "打回");

		/** 操作状态 */
		private int status;

		/** 操作状态名称 */
		private String name;

		private OperateStatus(int status, String name) {
			this.status = status;
			this.name = name;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
