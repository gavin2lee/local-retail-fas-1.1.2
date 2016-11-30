var list_no_group = {};

function getChecked(){
	return $('#dataGridDiv').datagrid('getChecked');
}

// 初始化
$(function(){
	setTimeout(function(){
		var queryParams = $("#dataGridDiv").datagrid('options').queryParams;
		queryParams.page = 1;
		queryParams.rows = 1000000;
		$("#dataGridDiv").datagrid('options').url = BasePath + '/supplier_group/supplierNoGroup.json';
		$("#dataGridDiv").datagrid('load');
	},500);
});
