var supplier={};

//获取多选页面中，选中的记录集合
function multiSearchDatas() {
	var checkedRows = $("#dataGridJG").datagrid("getChecked");
	return checkedRows;
};