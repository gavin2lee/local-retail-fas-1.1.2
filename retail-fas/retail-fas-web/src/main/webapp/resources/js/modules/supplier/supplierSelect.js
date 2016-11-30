var supplierSelect = new Object();


supplierSelect.clear = function(){
	$('#searchForm').form('clear');
};

supplierSelect.search = function(){
	var params = $('#searchForm').form('getData');
	var url = '/supplier/supplier_select';
	$('#datagrid').datagrid('options').queryParams = params;
	$('#datagrid').datagrid('options').url = url;
	$('#datagrid').datagrid('load');
};

supplierSelect.loadInfo = function(rowIndex,rowData){
	var supplierNo = rowData.supplierNo;
	var fullName = rowData.fullName;
	var obj_supplierNo = window.parent.document.getElementById("supplierNo");
	var obj_fullName = window.parent.document.getElementById("fullName");
	obj_supplierNo.value = supplierNo;
	obj_fullName.value = fullName;
	window.close();
};