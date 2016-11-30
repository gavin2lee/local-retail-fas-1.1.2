function BillCustFineNt() {}

var _fasDialogController = new FasDialogController("/bill_cust_fine_nt", fas_common_setting);

BillCustFineNt.prototype.super = _fasDialogController;

var _billCustFineNt = new BillCustFineNt();

// 点击"新增"按钮时，触发的操作
BillCustFineNt.prototype.toAdd = function() {
	_fasDialogController.super.loadedAdd = function() {
		$("#id").val("");
		_billCustFineNt.setReadOnly();
	};
	//检查新增的数据是否正确
	_fasDialogController.super.checkSave = function() {
		return _billCustFineNt.validateData();
	};
	_fasDialogController.toAdd();
};

//进入修改页面
BillCustFineNt.prototype.toUpdate = function(rowData) {
	_fasDialogController.super.initUpdate = function() {
		_billCustMarginNt.setReadOnly();
	};
	this.super.checkInitUpdate = function(rowData) {
		if(rowData.status == '0') {
			showWarn("数据已停用，不允许修改!");
			return false;
		}
		if(rowData.auditStatus == '1') {
			showWarn("数据已审核，不允许修改!");
			return false;
		}
		return true;
	};
	//检查新增的数据是否正确
	_fasDialogController.super.checkUpdate = function() {
		return _billCustFineNt.validateData();
	};
	_fasDialogController.toUpdate(rowData);
};

//删除
BillCustFineNt.prototype.doDel = function() {
	_fasDialogController.super.checkDel = function(checkedRows) {
		if(checkedRows && checkedRows.length == 1 && checkedRows[0].auditStatus == '1') {
			showWarn("数据已审核，不允许删除!");
			return false;
		}
		if(checkedRows && checkedRows.length > 1) {
			for(var i = 0; i < checkedRows.length; i++) {
				if(checkedRows[i].auditStatus == '1') {
					showWarn("您选择的数据中存在已审核状态的数据，不允许删除!");
					return false;
				}
			}
		}
		return true;
	};
	_fasDialogController.doDel();
};

//校验数据
BillCustFineNt.prototype.validateData = function() {
	var recedMarginAmount = $("#recedMarginAmount").val();
	var fineAmount = $("#fineAmount").numberbox('getValue');
	if(fineAmount > recedMarginAmount) {
		showWarn("罚没金额不能大于保证金余额");
		return false;
	}
	var check_data = {billNo : $("#billNo").val(), id : $("#id").val()};
	var url = BasePath + "/bill_cust_fine_nt/validate_data";
	var validate_data = _fasDialogController.super.ajaxRequestAsync(url, check_data, function(result){
		if(result && !result.success) {
			showError(result.message);
			return false;
		}
		return true;
	});
	return validate_data;
};

//设置样式
BillCustFineNt.prototype.setReadOnly = function() {
	var fields = _billCustFineNt.buildStyleFields();
	this.super.setReadOnly(fields);
};

//组装设置或清空样式的字段
BillCustFineNt.prototype.buildStyleFields = function() {
	return {
		input : ["customerName", "marginAmount", "recedMarginAmount"]
	};
};

//审核
BillCustFineNt.prototype.doAudit = function() {
	this.super.super.buildAuditData = function() {
		return _billCustFineNt.buildRequestData();
	};
	_fasDialogController.checkAudit = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '0') {
				showWarn("单据【" + checkedRows[i].billNo + "】已停用，不允许审核!");
				return false;
			}
		}
		if(checkedRows.length === 1 && checkedRows[0].auditStatus == '1') {
			showWarn('数据已审核，无需再审核');
			return false;
		}
		return true;
	};
	this.super.doAudit();
};

//反审核
BillCustFineNt.prototype.doAntiAudit = function() {
	this.super.super.buildAntiAuditData = function() {
		return _billCustFineNt.buildRequestData();
	};
	_fasDialogController.checkAntiAudit = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '0') {
				showWarn("单据【" + checkedRows[i].billNo + "】已停用，不允许反审核!");
				return false;
			}
		}
		if(checkedRows.length === 1 && checkedRows[0].auditStatus != '1') {
			showWarn('数据未审核，请勿重复操作');
			return false;
		}
		return true;
	};
	this.super.doAntiAudit();
};

//组装请求的参数(审核/反审核)
BillCustFineNt.prototype.buildRequestData = function() {
	var oList = [];
	var checkedRows = $("#dataGridDiv").datagrid("getChecked");
	for(var i = 0; i < checkedRows.length; i++) {
		var obj = new Object();
		obj.id = checkedRows[i].id;
		obj.companyNo = checkedRows[i].companyNo;
		obj.companyName = checkedRows[i].companyName;
		obj.customerNo = checkedRows[i].customerNo;
		obj.customerName = checkedRows[i].customerName;
		obj.fineAmount = checkedRows[i].fineAmount;
		oList.push(obj);
	}
  return oList;
};

//初始化查询组件
BillCustFineNt.prototype.initSearchBox = function() {
	this.super.super.initIptSearch({
		id : "companyName",
		title : "选择公司",
		url : "/base_setting/company/toSearchCompany",
		inputWidth : 135,
		callback : function(data) {
			$("#companyName").val(data.name);
			$("#companyNo").val(data.companyNo);
			var queryMarginUrl = BasePath+"/wholesale_prepay_warn/select_margin_amount";
			var queryMarginParams = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val()};
			ajaxRequest(queryMarginUrl, queryMarginParams, function(result) {
				$("#recedMarginAmount").val(result.recedMarginAmount);
			});
		}
	});

	this.super.super.initIptSearch({
		id : "customerNo",
		title : "选择客户",
		url : "/customer_rece_rel/select_customer",
		inputWidth : 135,
		callback : function(data) {
			$("#customerNo").val(data.customerNo);
			$("#customerName").val(data.customerName);
			$("#marginAmount").val(data.marginAmount);
			var queryRecedMarginUrl = BasePath+"/wholesale_prepay_warn/select_margin_amount";
			var queryRecedMarginParams = {companyNo : $("#companyNo").val(), customerNo : data.customerNo};
			ajaxRequest(queryRecedMarginUrl, queryRecedMarginParams, function(result) {
				$("#recedMarginAmount").val(result.recedMarginAmount);
			});
		}
	});
};

$(function() {
	_billCustFineNt.initSearchBox();
});
