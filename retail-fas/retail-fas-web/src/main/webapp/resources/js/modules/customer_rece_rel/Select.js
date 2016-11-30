var Select = {};
var tbgrid = {};

Select.search = function() {
	var reqParam = $("#searchForm").form("getData");
	var queryMxURL = BasePath + "/customer_rece_rel/list_customer";
	$("#dataGridDiv").datagrid('options').queryParams = reqParam;
	$("#dataGridDiv").datagrid('options').url = queryMxURL;
	$("#dataGridDiv").datagrid('load');
};

Select.clear = function() {
	$("#searchForm").form("clear");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#dataGridDiv').datagrid({
		singleSelect : true,
		pagination:true,
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 80,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'customerNo',
			title : '客户编码',
			width : 100
		},{
			field : 'customerName',
			title : '客户名称',
			width : 250
		},
		{
			field : 'marginAmount',
			title : '保证金额度',
			width : 100
		}]]
	});
});
