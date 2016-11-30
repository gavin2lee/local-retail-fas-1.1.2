var select = {};
var tbgrid = {};

select.search = function() {
	var reqParam = $("#searchForm").form("getData");
	var queryMxURL = BasePath + "/wholesale_rece_term/list.json";
	$("#dataGridDiv").datagrid('options').queryParams = reqParam;
	$("#dataGridDiv").datagrid('options').url = queryMxURL;
	$("#dataGridDiv").datagrid('load');
};

select.clear = function() {
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
			field : 'termNo',
			title : '条款编码',
			width : 100
		},{
			field : 'name',
			title : '条款名称',
			width : 250
		}]]
	});
});
