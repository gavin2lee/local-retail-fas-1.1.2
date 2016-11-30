var searchPro = {};
var tbgrid = {};
/**
 * 查询费用类别
 */
searchPro.dosearchPro = function() {
	var reqParam = $("#subForm").form("getData");
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
	var startDate = $('#balanceStartDate').val();
	var endDate = $('#balanceEndDate').val();
	var balanceNo = $('#balanceNo').val();
	var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo};
	var queryMxURL = BasePath + "/bill_shop_balance_pro_sum/list.json";
	$("#searchProDG").datagrid('options').queryParams =params;// reqParam;
	$("#searchProDG").datagrid('options').url = queryMxURL;
	$("#searchProDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchPro.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchProDG').datagrid({
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
			field : 'proNo',
			title : '编码',
			width : 150
		},{
			field : 'proName',
			title : '名称',
			width : 250
		},{
			field : 'proStartDate',
			title : '促销开始时间',
			width : 100
		},{
			field : 'proEndDate',
			title : '促销结束时间',
			width : 100
		},{
			field : 'discount',
			title : '扣率',
			width : 100
		},{
			field : 'saleAmount',
			title : '销售收入',
			width : 100
		}
		] ]
	});
});
