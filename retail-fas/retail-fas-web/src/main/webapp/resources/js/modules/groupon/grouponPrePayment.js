function GrouponPrePayment() {}

var _fasBillController = new FasDialogController("/groupon_prepayment", fas_common_setting);

GrouponPrePayment.prototype.super = _fasBillController;

var _GrouponPrePayment = new GrouponPrePayment();

var customerNoStr = "";
var companyNoStr = "";

//点击"新增"按钮时，触发的操作
GrouponPrePayment.prototype.toAdd = function() {
	this.super.initAdd = function() {
		$("#id").val("");
		_GrouponPrePayment.clearReadOnly();
	};
	this.super.toAdd();
	_GrouponPrePayment.loadInvoice();
	$('#invoiceNo').iptSearch('disable');
	$('#saleOrderNo').iptSearch('disable');
	$("#customerName").customerInvoiceInfo('disable');
	$("#billNo").attr("readonly", true).addClass("disabled");
	$("#orderAmount").attr("readonly", true).addClass("disabled");
	$("#saleOrderNo").attr("readonly", true).addClass("disabled");
};

//进入修改页面
GrouponPrePayment.prototype.toUpdate = function(rowData) {
	this.super.checkInitUpdate = function(rowData) {
		if(rowData.auditStatus == '1') {
			 showWarn("数据已审核,不允许修改");
			 return false;
		 }
		return true;
	};
	_fasBillController.super.initUpdate = function() {
		_GrouponPrePayment.setReadOnly();
	};
	_fasBillController.toUpdate(rowData);
	_GrouponPrePayment.loadInvoice();
};

//清除样式
GrouponPrePayment.prototype.clearReadOnly = function() {
	var fields = _GrouponPrePayment.buildStyleFields();
	this.super.clearReadOnly(fields);
};

//设置样式
GrouponPrePayment.prototype.setReadOnly = function() {
	var fields = _GrouponPrePayment.buildStyleFields();
	this.super.setReadOnly(fields);
};

//组装设置或清空样式的字段
GrouponPrePayment.prototype.buildStyleFields = function() {
	return {
		input : ["billNo", "remark", "saleOrderNo", "receivableAmount", "reversalAmount", 
		         "orderAmount", "prePaymentOver", "paidAmount"],
		date : ["billDate"],
		searchbox : ["customerNo", "companyName"]
	};
};

//删除
GrouponPrePayment.prototype.doDel = function() {
	_fasBillController.super.checkDel = function(checkedRows) {
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
	_fasBillController.doDel();
};

//审核
GrouponPrePayment.prototype.doAudit = function() {
	this.super.super.buildAuditData = function() {
	    return _GrouponPrePayment.buildRequestData();
	};
	this.super.super.auditCallback = function(params, result) {
		showSuc("审核成功!");
	};
	this.super.doAudit();
};

//反审核
GrouponPrePayment.prototype.doAntiAudit = function() {
	this.super.super.checkAntiAudit = function() {
		if($("#auditStatus").val() != "1") {
			showInfo('该单据已处于未审核状态');
			return false;
		}
		return true;
	};
	this.super.super.buildAntiAuditData = function() {
		return _GrouponPrePayment.buildRequestData();
	};
	this.super.super.antiAuditCallback = function(params, result) {
		showSuc("反审核成功!");
	};
	this.super.doAntiAudit();
};

//组装请求的参数(审核/反审核)
GrouponPrePayment.prototype.buildRequestData = function() {
	var oList = [];
	var checkedRows = $("#dataGridJG").datagrid("getChecked");
	for(var i = 0; i < checkedRows.length; i++) {
		var obj = new Object();
		obj.id = checkedRows[i].id;
		obj.companyNo = checkedRows[i].companyNo;
		obj.companyName = checkedRows[i].companyName;
		obj.customerNo = checkedRows[i].customerNo;
		obj.customerName = checkedRows[i].customerName;
		obj.saleOrderNo = checkedRows[i].saleOrderNo;
		obj.orderAmount = checkedRows[i].orderAmount;
		obj.prePayment = checkedRows[i].prePayment;
		obj.paidAmount = checkedRows[i].paidAmount;
		obj.reversalAmount = checkedRows[i].reversalAmount;
		oList.push(obj);
	}
    return oList;
};

//初始化查询框组件
GrouponPrePayment.prototype.initSearchBox = function() {
	this.super.super.initIptSearch({
		id : "companyName",
		title : "选择结算主体",
		url : "/base_setting/company/toSearchCompany",
		inputWidth : 135,
		callback : function(data) {
			$("#companyNo").val(data.companyNo);
			$("#companyName").val(data.name);
			companyNoStr = data.companyNo;
//			$("#invoiceType").combobox('enable');
			$('#invoiceNo').iptSearch('enable');
			$('#saleOrderNo').iptSearch('enable');
		}
	});
	
	this.super.super.initIptSearch({
		id : "customerNo",
		title : "选择客户",
		url : "/base_setting/customer/toSearchCustomer",
		inputWidth : 135,
		callback : function(data) {
			$("#customerNo").val(data.customerNo);
			$("#customerName").val(data.fullName);
			customerNoStr = data.customerNo;
			$('#companyName').iptSearch('enable');
		}
	});
	this.super.super.initIptSearch({
		id : "companyNames",
		title : "选择结算主体",
		url : "/base_setting/company/toSearchCompany",
		inputWidth : 135,
		callback : function(data) {
			$("#companyNos").val(data.companyNo);
			$("#companyNames").val(data.name);
		}
	});
	
	this.super.super.initIptSearch({
		id : "customerNos",
		title : "选择客户",
		url : "/base_setting/customer/toSearchCustomer",
		inputWidth : 135,
		callback : function(data) {
			$("#customerNos").val(data.customerNo);
			$("#customerNames").val(data.fullName);
		}
	});
};

_GrouponPrePayment.loadInvoice=function(){
	$("#invoiceNo").iptSearch({
		disabled : false,
		readonly : true,
		clickFn : function() {
			dgSelector({
				title : '选择开票申请号',
				href : BasePath + '/bill_pre_payment_nt/toSearchPrePaymentNt?buyerNo='+$('#customerNo').val()+"&salerNo="+$('#companyNo').val(),
				width : 500,
				height : 500,
				fn : function(data) {
					$("#invoiceNo").val(data.billNo);
					$("#invoiceDate").val(data.invoiceApplyDate);
				}
			});
		}
	});
};

var  dialog;

function closeDialog(){
	 dialog.close();
}

_GrouponPrePayment.initBillOrder=function(){
	$("#saleOrderNo").iptSearch({
		disabled : false,
		readonly : true,
		clickFn : function() {
			dgSelector({
					title : '选择团购订单',
					href : BasePath + '/bill_pre_payment_nt/toSearchBillOrder?buyerNo='+$("#customerNo").val()+'&salerNo='+$("#companyNo").val(),
					width : 500,
					height : 450,
					fn : function(data) {
						$("#saleOrderNo").val(data.billNo);
						$("#orderAmount").val(data.amount);
						$("#billDate").val(data.billDate);
					}
				});
		}
	});
};

/*var statusData = [ {
	"value" : "1",
	"text" : "开票申请"
}, {
	"value" : "0",
	"text" : "发票登记"
} ];*/

//var statusData = [{
//	"value" : "0",
//	"text" : "发票登记"
//}];
//
//_GrouponPrePayment.initStopFlag=function(){
//	$('#invoiceType').combobox({
//		data : statusData,
//		valueField : 'value',
//		textField : 'text',
//		editable : false,
//	    onChange : function(newValue, oldValue) {
//	    	$('#invoiceNo').iptSearch('enable');
//	    	invoiceTypeStr = newValue;
//	    	$('#invoiceNo').val("");
//		}
//	});  
//};

_GrouponPrePayment.clear=function(){
	$("#searchForm").form("clear"); 
/*	$("#companyNo").val("");
	$("#customerName").val("");*/
	$("#companyNos").val("");
	$("#customerNames").val("");
};

$(function() {
//	_GrouponPrePayment.initSearchBox();
//	_GrouponPrePayment.initStopFlag();
	_GrouponPrePayment.initBillOrder();
});


function selectCompany(data){
	$("#customerName").customerInvoiceInfo("enable");
}

function selectCustomer(data){
	$('#invoiceNo').iptSearch('enable');
	$('#saleOrderNo').iptSearch('enable');
}