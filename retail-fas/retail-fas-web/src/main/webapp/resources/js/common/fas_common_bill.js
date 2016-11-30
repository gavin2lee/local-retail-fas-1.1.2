var fas_common_bill = {};

//当前编辑行
fas_common_bill.editRowIndex = -1;

//单据查询当前选中行的索引
fas_common_bill.curRowIndex = -1;

var initSetting = $.extend({
	dataGridId : "",
	dataFormId: "",
	searchFormId : "",
	searchBtn : "bill-btn-search",
	searchUrl : "",
	clearBtn : "bill-btn-clear",
	addBtn : "bill-btn-add",
	saveBtn : "bill-btn-save",
	saveUrl : "",
	delBtn : "bill-btn-del",
	delUrl : "",
	auditBtn : "bill-btn-audit",
	auditUrl : "",
	antiAuditBtn : "bill-btn-antiAudit",
	antiAuditUrl : "",
	enableBtn : "bill-btn-enable",
	enableUrl : "",
	unableBtn : "bill-btn-unable",
	unableUrl : "",
	exportBtn : "bill-btn-export",
	exportUrl : "",
	exportTitle : "",
	prevBtn : "bill-btn-prev",
	nextBtn : "bill-btn-next"
}, bill_extra_operate.setting || {});

//上单
fas_common_bill.upBill = function(params) {
	if (fas_common_bill.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	fas_common_bill.type = 1;
	preBill(params.dataGridId, fas_common_bill.curRowIndex,
			fas_common_bill.type, fas_common_bill.callBackBill);
};

// 下单
fas_common_bill.downBill = function(params) {
	if (fas_common_bill.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	fas_common_bill.type = 2;
	preBill(params.dataGridId, fas_common_bill.curRowIndex,
			fas_common_bill.type, fas_common_bill.callBackBill);
};

// 上下单回调
fas_common_bill.callBackBill = function(curRowIndex, rowData) {
	if (fas_common_bill.type == 1 || fas_common_bill.type == 2) {
		if (rowData != null && rowData != '' && rowData != []) {
			fas_common_bill.loadDetail(curRowIndex, rowData);
			fas_common_bill.type = 0;
		} else {
			if (fas_common_bill.type == 1) {
				showInfo('已经是第一单');
			} else {
				showInfo('已经是最后一单');
			}
		}
	}
};

//新增
fas_common_bill.add = function(params){
	$("#" + params.dataFormId).form("clear");
	if(typeof bill_extra_operate != 'undefined'
	    && typeof bill_extra_operate.initAdd != 'undefined' 
	    && bill_extra_operate.initAdd != null
		&& typeof bill_extra_operate.initAdd == 'function') {
		bill_extra_operate.initAdd();
	}
};

//点击切换到明细
fas_common_bill.loadDetail = function(rowIndex, rowData) {
	fas_common_bill.curRowIndex = rowIndex;
	$("#" + initSetting.dataFormId).form('load', rowData);
	fas_common_bill.setBillBottom(rowData);
	if(typeof bill_extra_operate != 'undefined'
	    && typeof bill_extra_operate.initLoadDetail != 'undefined' 
	    && bill_extra_operate.initLoadDetail != null
		&& typeof bill_extra_operate.initLoadDetail == 'function') {
		bill_extra_operate.initLoadDetail(rowData);
	}
};

// 设置明细页面底部数据
fas_common_bill.setBillBottom = function(rowData) {
	$("#createUser").html(rowData.createUser);
	$("#createTime").html(rowData.createTime);
	$("#auditor").html(rowData.auditor);
	$("#auditTime").html(rowData.auditTime);
};

var extra_operate = {
	buildDelData : function(checkedRows) {
		if($("#id").val() == null || $("#id").val() == '') {
			if(typeof list_build_data != 'undefined'
			    && typeof list_build_data.buildDelData != 'undefined' 
			    && list_build_data.buildDelData != null
				&& typeof list_build_data.buildDelData == 'function') {
				return list_build_data.buildDelData(checkedRows);
			}
		}
		return fas_common_bill.getDetailRows();
	},
	buildAuditData : function(checkedRows) {
		if($("#id").val() == null || $("#id").val() == '') {
			if(typeof list_build_data != 'undefined'
			    && typeof list_build_data.buildAuditData != 'undefined' 
			    && list_build_data.buildAuditData != null
				&& typeof list_build_data.buildAuditData == 'function') {
				return list_build_data.buildAuditData(checkedRows);
			}
		}
		return fas_common_bill.getDetailRows();
	},
	buildAntiAuditData : function(checkedRows) {
		if($("#id").val() == null || $("#id").val() == '') {
			if(typeof list_build_data != 'undefined'
			    && typeof list_build_data.buildAntiAuditData != 'undefined' 
			    && list_build_data.buildAntiAuditData != null
				&& typeof list_build_data.buildAntiAuditData == 'function') {
				return list_build_data.buildAntiAuditData(checkedRows);
			}
		}
		return fas_common_bill.getDetailRows();
	},
	buildEnableData : function(checkedRows) {
		if($("#id").val() == null || $("#id").val() == '') {
			if(typeof list_build_data != 'undefined'
			    && typeof list_build_data.buildEnableData != 'undefined' 
			    && list_build_data.buildEnableData != null
				&& typeof list_build_data.buildEnableData == 'function') {
				return list_build_data.buildEnableData(checkedRows);
			}
		}
		return fas_common_bill.getDetailRows();
	},
	buildUnableData : function(checkedRows) {
		if($("#id").val() == null || $("#id").val() == '') {
			if(typeof list_build_data != 'undefined'
			    && typeof list_build_data.buildUnableData != 'undefined' 
			    && list_build_data.buildUnableData != null
				&& typeof list_build_data.buildUnableData == 'function') {
				return list_build_data.buildUnableData(checkedRows);
			}
		}
		return fas_common_bill.getDetailRows();
	},
	auditCallback : function() {
		
	},
	initSubmitParams : function() {
		if(typeof list_build_data != 'undefined'
		    && typeof list_build_data.initSubmitParams != 'undefined' 
		    && list_build_data.initSubmitParams != null
			&& typeof list_build_data.initSubmitParams == 'function') {
			return list_build_data.initSubmitParams();
		}
		return [];
	}
};

//获取明细页中的数据
fas_common_bill.getDetailRows = function() {
	var rows = [];
	var obj = new Object();
	obj["id"] = $("#id").val();
	obj["companyNo"] = $("#companyNo").val();
	obj["companyName"] = $("#companyName").val();
	obj["customerNo"] = $("#customerNo").val();
	obj["customerName"] = $("#customerName").val();
	obj["paidMarginAmount"] = $("#paidMarginAmount").val();
	obj["fineAmount"] = $("#fineAmount").val();
	obj["saleOrderNo"] = $("#saleOrderNo").val();
	obj["orderAmount"] = $("#orderAmount").val();
	obj["prePayment"] = $("#prePayment").val();
	obj["paidAmount"] = $("#paidAmount").val();
	obj["reversalAmount"] = $("#reversalAmount").val();
	rows.push(obj);
	return rows;
};

// 初始化
$(function() {
	//列表页面查询
	$("#" + initSetting.searchBtn).live("click", function() {
		fas_common.search({
			dataGridId : initSetting.dataGridId,
			formId : initSetting.searchFormId,
			url : initSetting.searchUrl
		});
	});
	//列表页面清空
	$("#" + initSetting.clearBtn).live("click", function() {
		$("#" + initSetting.searchFormId).form("clear");
	});
	//列表页面导出
	$("#" + initSetting.exportBtn).live("click", function() {
		fas_common.exportExcel({
			dataGridId : initSetting.dataGridId,
			url : initSetting.exportUrl,
			title : initSetting.exportTitle
		});
	});
	
	//明细页面新增
	$("#" + initSetting.addBtn).live("click", function() {
		fas_common_bill.add({
			dataFormId : initSetting.dataFormId
		});
	});
	//明细页面保存
	$("#" + initSetting.saveBtn).live("click", function() {
		var id = $("#id").val();
		if(id) {
			fas_common.doUpdate({
				dataFormId : initSetting.dataFormId,
				updateUrl : initSetting.updateUrl
			});
		} else {
			fas_common.doSave({
				dataFormId : initSetting.dataFormId,
				saveUrl : initSetting.saveUrl
			});
		}
	});
	//明细页面删除
	$("#" + initSetting.delBtn).live("click", function() {
		fas_common.deleteFn({
			delUrl : initSetting.delUrl
		});
	});
	//明细页面上单、下单操作
	$("#" + initSetting.prevBtn).live("click", function() {
		fas_common_bill.upBill({
			dataGridId : initSetting.dataGridId
		});
	});
	$("#" + initSetting.nextBtn).live("click", function() {
		fas_common_bill.downBill({
			dataGridId : initSetting.dataGridId
		});
	});
	//明细页面审核/反审核
	$("#" + initSetting.auditBtn).live("click", function() {
		fas_common.auditFn({
			primaryKey : "id",
			auditUrl : initSetting.auditUrl
		});
	});
	$("#" + initSetting.antiAuditBtn).live("click", function() {
		fas_common.antiAuditFn({
			primaryKey : "id",
			antiAuditUrl : initSetting.antiAuditUrl
		});
	});
	//明细页面注销/激活
	$("#" + initSetting.unableBtn).live("click", function() {
		fas_common.unableFn({
			primaryKey : "id",
			unableUrl : initSetting.unableUrl
		});
	});
	$("#" + initSetting.enableBtn).live("click", function() {
		fas_common.enableFn({
			primaryKey : "id",
			enableUrl : initSetting.enableUrl
		});
	});
});