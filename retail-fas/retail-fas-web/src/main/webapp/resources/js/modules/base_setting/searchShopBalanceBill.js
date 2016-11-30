var searchShopBalanceBill = {};
var tbgrid = {};
/**
 * 查询商场
 */
searchShopBalanceBill.searchShopBalance = function() {
	var zoneNo = $(window.parent.document.getElementById("zoneNo"));
	//alert(zoneNo.length);
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/mall_shopbalance/shop_list.json";
	$("#shopBalanceBillDG").datagrid('options').queryParams = reqParam;
	$("#shopBalanceBillDG").datagrid('options').url = queryMxURL;
	$("#shopBalanceBillDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchShopBalanceBill.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#shopBalanceBillDG').datagrid({
		url : '',
		singleSelect : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50, 100, 200, 300, 400, 500 ],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 80,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'balanceNo',
			title : '单据编号',
			width : 150
		}, {
			field : 'billingAmount',
			title : '开票金额',
			width : 250
		} ] ]
	});

});
