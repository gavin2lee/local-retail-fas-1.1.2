var billOrder = {};
var tbgrid = {};
/**
 * 查询预收款信息
 */
billOrder.dosearchBillOrder = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/bill_sale_balance/query_sale_order";
	$("#searchBillOrderDG").datagrid('options').queryParams = reqParam;
	$("#searchBillOrderDG").datagrid('options').url = queryMxURL;
	$("#searchBillOrderDG").datagrid('load');
};

/**
 * 清空查询条件
 */
billOrder.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchBillOrderDG').datagrid({
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
			title : '订单号',
			width : 150
		},{
			field : 'amount',
			title : '订单金额',
			width : 100
		},{
			field : 'billDate',
			title : '单据日期',
			width : 150
		}] ]
/*		onDblClickRow : function(rowIndex, rowData){
			var saleOrderNo= window.parent.document.getElementById("saleOrderNo");
			var orderAmount= window.parent.document.getElementById("orderAmount");
			var billDate= window.parent.document.getElementById("billDate");
			$(saleOrderNo).val(rowData.billNo);
			$(orderAmount).val(rowData.amount);
			$(billDate).datebox('setValue',rowData.billDate);
			var parentClose = window.parent.closeDialog;
			parentClose();
		}*/
	});
});
