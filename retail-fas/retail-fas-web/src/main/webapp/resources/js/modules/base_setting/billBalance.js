var billBalance = {};
var tbgrid = {};
/**
 * 查询预收款信息
 */
billBalance.doSearchBillBalance = function() {
	var invoiceTypeStr = $("#invoiceType").val();
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = "";
	queryMxURL = BasePath + "/bill_balance/list.json?status=4";
	$("#searchBillBalanceDG").datagrid('options').queryParams = reqParam;
	$("#searchBillBalanceDG").datagrid('options').url = queryMxURL;
	$("#searchBillBalanceDG").datagrid('load');
};

/**
 * 清空查询条件
 */
billBalance.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchBillBalanceDG').datagrid({
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
			title : '单据号',
			width : 120
		},{
			field : 'balanceAmount',
			title : '单据金额',
			width : 120
		},{
			field : 'buyerNo',
			title : '客户编码',
			width : 80
		},{
			field : 'buyerName',
			title : '客户名称',
			width : 120
		},{
			field : 'salerNo',
			title : '公司编码',
			width : 80
		},{
			field : 'salerName',
			title : '公司名称',
			width : 120
		}] ]
	});
});
