var orderUnit = {};

//查询
orderUnit.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dataGridLU").datagrid('options').queryParams = reqParam;
	$("#dataGridLU").datagrid('options').url = 'list.json';
	$("#dataGridLU").datagrid('load');
};

//清空
orderUnit.searchClear = function() {
	$('#searchForm').form("clear");
	$("#companyNo,#organNoId").val("");
};

//导出
orderUnit.exportExcel = function() {
	exportExcelBaseInfo('dataGridLU', '/order_unit/do_export.htm', '货管单位导出');
};