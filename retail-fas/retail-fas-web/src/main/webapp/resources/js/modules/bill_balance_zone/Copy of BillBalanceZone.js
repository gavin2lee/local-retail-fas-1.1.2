function BillBalanceZone() {}

var construct = {
	dataGridId : "balanceZoneDataGrid",
	dataFormId : "mainDataForm",
	searchFormId : "searchForm",
	searchBtn : "bill-btn-search",
	searchUrl : "/list.json?balanceType=7",
	saveUrl : "/addBill",
	updateUrl : "/put",
	delUrl :  "/delBill",
	exportUrl : "/do_export?balanceType=7",
	exportTitle : "地区批发结算单信息"
};

var _fasBillController = new FasBillController("/bill_balance_zone", construct);

BillBalanceZone.prototype.super = _fasBillController;

var _billBalanceZone = new BillBalanceZone();

BillBalanceZone.prototype.formatterStatus = function(value) {
	if(value == '2') {
		return "业务已确认";
	}
	if(value == '3') {
		return "财务已确认";
	}
	if(value == '99') {
		return "已作废";
	}
	return "制单";
};

//点击"新增"按钮时，触发的操作
BillBalanceZone.prototype.toAdd = function() {
	this.super.initAdd = function() {
		$("#id").val("");
		_billBalanceZone.clearReadOnly();
		_billBalanceZone.initSearchBox();
		//清空明细数据
		$('#balanceDtlDataGrid').datagrid('loadData', {total: 0, rows: []});
	};
	this.super.toAdd();
};

//双击行数据，进入明细tab页面
BillBalanceZone.prototype.loadDetail = function(rowIndex, rowData) {
	this.super.initDetail = function(rowData) {
		if(rowData.auditStatus && rowData.auditStatus != '0') {
			_billBalanceZone.setReadOnly();
		} else {
			_billBalanceZone.clearReadOnly();
			_billBalanceZone.initSearchBox();
		}
		//加载明细数据
		var url = BasePath + "/bill_sale_out_dtl/list.json";
		var params = {
			bizType : '2', 
			balanceNo:rowData.billNo
		};
	    $("#balanceDtlDataGrid").datagrid("options").queryParams= params;
	    $("#balanceDtlDataGrid").datagrid("options").url=  url;
	    $("#balanceDtlDataGrid").datagrid("load");
	    
	    $("#invoiceIframe").attr("src", BasePath + "/bill_balance_zone/invoice_tab?balanceNo="+$("#billNo").val());
		returnTab('mainTab', '单据明细');
	};
	this.super.loadDetail(rowIndex, rowData);
};

//组装设置或清空样式的字段
BillBalanceZone.prototype.buildStyleFields = function() {
	return {
		input : ["billNo", "billName"],
		date : ["balanceStartDate", "balanceEndDate"]
	};
};

//设置样式
BillBalanceZone.prototype.setReadOnly = function() {
	var fields = _billBalanceZone.buildStyleFields();
	this.super.setReadOnly(fields);
};

//清除样式
BillBalanceZone.prototype.clearReadOnly = function() {
	var fields = _billBalanceZone.buildStyleFields();
	this.super.clearReadOnly(fields);
	this.initSearchBox();
};

//初始化查询框组件
BillBalanceZone.prototype.initSearchBox = function() {
	_fasBillController.super.initIptSearch({
		id : "companyName",
		title : "选择结算主体",
		url : "/base_setting/company/toSearchCompany",
		callback : function(data) {
			$("#companyNo").val(data.companyNo);
			$("#companyName").val(data.name);
			var queryMarginUrl = BasePath+"/bill_balance_zone/select_amount";
			var queryMarginParams = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val()};
			ajaxRequest(queryMarginUrl, queryMarginParams, function(result) {
				if(result) {
					$("#openOrderAmount").val(result.totalOrderAmount - result.totalSendOutAmount);
					$("#openPrePayment").val(result.totalPrePayment - result.totalSendOutAmount);
					$("#saleOutAmount").val(result.totalSendOutAmount);
				}
			});
		}
	});
	
	_fasBillController.super.initIptSearch({
		id : "customerName",
		title : "选择客户",
		url : "/base_setting/customer/toSearchCustomer",
		callback : function(data) {
			$("#customerNo").val(data.customerNo);
			$("#customerName").val(data.fullName);
			var queryMarginUrl = BasePath+"/bill_balance_zone/select_amount";
			var queryMarginParams = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val()};
			ajaxRequest(queryMarginUrl, queryMarginParams, function(result) {
				if(result) {
					$("#openOrderAmount").val(result.totalOrderAmount - result.totalSendOutAmount);
					$("#openPrePayment").val(result.totalPrePayment - result.totalSendOutAmount);
					$("#saleOutAmount").val(result.totalSendOutAmount);
				}
			});
		}
	});
};

//批量新增
BillBalanceZone.prototype.toBatchAdd = function(){
	var _this = this;
	_fasBillController.super.multiSearch({
		id : "multiCompanyName",
		title : "选择结算公司",
		href : "/base_setting/company/toMultiSearch",
		inputNo : {self : "multiCompanyNo", child : "companyNo"},
		inputName : {self : "multiCompanyName", child : "name"}
	});
	_fasBillController.super.multiSearch({
		id : "multiCustomerName",
		title : "选择客户",
		href : "/base_setting/customer/toMultiSearch",
		inputNo : {self : "multiCustomerNo", child : "customerNo"},
		inputName : {self : "multiCustomerName", child : "fullName"}
	});
	ygDialog({
		title : '批量生成结算单',
		target : $('#myFormPanel'),
		width : 400,
		height : 300,
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				_this.batchAdd(dialog);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
};

//批量新增的逻辑处理方法
BillBalanceZone.prototype.batchAdd = function(dialog) {
	_fasBillController.super.add({
		dataFormId : "dataForm",
		saveUrl : "/bill_balance_zone/batch_add"
	});
};

//删除
BillBalanceZone.prototype.batchDel = function() {
	_fasBillController.super.del({
		dataGridId : "balanceZoneDataGrid",
		delUrl : "/bill_balance_zone/delBill",
		searchBtn : "bill-btn-search"
	});
	_fasBillController.super.buildDelData = function(checkedRows) {
		var deleteList = [];
		$.each(checkedRows, function(index, item){
			var obj = new Object();
			obj.id = item.id;
			obj.billNo = item.billNo;
			deleteList.push(obj);
		});    
		return deleteList;
	};
	_fasBillController.super.checkDel = function() {
		var url = BasePath + "/bill_balance_zone/check_del";
		var checkData = {};
		var checkResult = _fasBillController.super.ajaxRequestAsync(url, checkData, function(flag){
			if(!flag) {
				showError('数据不能删除!');
				return false;
			}
			return true;
		});
		return checkResult;
	}
};

//业务确认\财务确认操作
BillBalanceZone.prototype.confirmOperate = function(status) {
	var url = BasePath + "/bill_balance_zone" + construct.updateUrl;
	var params = {id : $("#id").val(), status : status};
	if(status === 2) { // 业务确认
		params.businessAuditor = "admin";
//		params.businessAuditTime = new Date();
	} else if(status == 3) { //财务确认
		params.financeAuditor = "admin";
//		params.financeAuditTime = new Date();
	}
	$.messager.confirm("确认","你确定要操作这条数据吗?", function(r) {
		if(r) {
			$.post(url, params, function(result){
				if(result) {
					showSuc("确认成功!");
				} else {
			        showError("确认失败,请联系管理员!");
			    }
			});
		}
	});
};

//数据加载成功后的处理方法
var data_onload_success = {
	bill_dtl : function() {
		var rows = $("#balanceDtlDataGrid").datagrid("getRows");
		if(rows && rows.length > 0) {
			var totalSendOutQty = 0, totalCostAmount = 0.00, totalQuoteAmount = 0.00;
			$.each(rows, function(index, item) {
				totalSendOutQty += item.sendOutQty;
				totalCostAmount += item.costAmount;
				totalQuoteAmount += item.quoteAmount;
	    	});
			$('#balanceDtlDataGrid').datagrid('appendRow',{
				billNo : "合计", 
				sendOutQty : totalSendOutQty,
				costAmount : totalCostAmount,
				quoteAmount : totalQuoteAmount
			});
		}
	}
};

$(function() {
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : false,
		closable : false,
		href : BasePath + "/bill_balance_zone/list_tab",
		onLoad : function(panel) {
			// 这里需要在重写在加载完后做对应的事件
		}
	});
	$('#mainTab').tabs('hideHeader');
});