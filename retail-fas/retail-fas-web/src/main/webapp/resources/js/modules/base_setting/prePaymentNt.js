var searchPrePaymentNt = {};
var tbgrid = {};
/**
 * 查询预收款信息
 */
searchPrePaymentNt.dosearchPrePaymentNt = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = "";
//	if("0" == invoiceTypeStr){
//		queryMxURL = BasePath + "/bill_balance_invoice_register/getBillCommonRegister";
//	}else{
//		queryMxURL = BasePath + "/bill_balance_invoice_info/getBillBalanceInvoiceInfo";
//	}
	queryMxURL = BasePath + "/bill_balance_invoice_apply/getBillInvoiceApplyNo";
	//var queryMxURL = BasePath + "/bill_balance_invoice_register/getBillCommonRegister";
	$("#searchPrePaymentNtDG").datagrid('options').queryParams = reqParam;
	$("#searchPrePaymentNtDG").datagrid('options').url = queryMxURL;
	$("#searchPrePaymentNtDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchPrePaymentNt.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchPrePaymentNtDG').datagrid({
		url : '',
		singleSelect : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50, 100, 200, 300, 400, 500 ],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 60,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		},{
			field : 'billNo',
			title : '开票申请号',
			width : 120
		},{
			field : 'amount',
			title : '开票总金额',
			width : 150
		},{
			field : 'invoiceApplyDate',
			title : '开票日期',
			width : 150
		}] ]
	});
});
