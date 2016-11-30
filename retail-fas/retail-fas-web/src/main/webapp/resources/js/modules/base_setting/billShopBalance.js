var billShopBalance = {};
var tbgrid = {};
/**
 * 查询预收款信息
 */
billShopBalance.doSearchBillBalance = function() {
	var invoiceTypeStr = $("#invoiceType").val();
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = "";
	queryMxURL = BasePath + "/mall_shopbalance/getBillShopBalance.json?status=2";
	$("#searchBillBalanceDG").datagrid('options').queryParams = reqParam;
	$("#searchBillBalanceDG").datagrid('options').url = queryMxURL;
	$("#searchBillBalanceDG").datagrid('load');
};

/**
 * 清空查询条件
 */
billShopBalance.clearCondition = function() {
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
			field : 'balanceNo',
			title : '单据号',
			width : 120
		},{
			field : 'mallBillingAmount',
			title : '商场开票金额',
			width : 120
		},{
			field : 'shopNo',
			title : '店铺编码',
			width : 80
		},{
			field : 'shortName',
			title : '店铺名称',
			width : 150
		},{
			field : 'companyNo',
			title : '公司编码',
			width : 100
		},{
			field : 'companyName',
			title : '公司名称',
			width : 150
		},{
			field : 'mallNo',
			title : '商场编码',
			width : 100
		},{
			field : 'mallName',
			title : '商场名称',
			width : 120
		},{
			field : 'bsgroupsNo',
			title : '商业集团编码',
			width : 100
		},{
			field : 'bsgroupsName',
			title : '商业集团名称',
			width : 120
		},{
			field : 'systemSalesAmount',
			title : '系统收入',
			width : 150,
			hidden:true
		}] ]
	});
});
