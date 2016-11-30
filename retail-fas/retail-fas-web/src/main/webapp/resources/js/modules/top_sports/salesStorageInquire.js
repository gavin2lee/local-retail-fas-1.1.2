var salesInquire={};

salesInquire.search = function() {
	var reqParam = $("#searchForm").form("getData");
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	if (index == 0) {
		var queryMxURL = BasePath + "/sales_storage_inquire/list.json";
		$("#dataDGridJG").datagrid('options').queryParams = reqParam;
		$("#dataDGridJG").datagrid('options').url = queryMxURL;
		$("#dataDGridJG").datagrid('load');
	} else {
		var queryMxURL = BasePath + "/sales_storage_inquire/sum_list.json";
		$("#sumDataGrid").datagrid('options').queryParams = reqParam;
		$("#sumDataGrid").datagrid('options').url = queryMxURL;
		$("#sumDataGrid").datagrid('load');
	}
};

salesInquire.clear=function(){
	$("#searchForm").form("clear");
	$("#supplierNoId").val("");
};

