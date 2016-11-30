var gatherList = new Object();

//查询
gatherList.search = function() {
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/bill_balance/hq/gather_list.json';
    $('#dtlDataGrid').datagrid('options').queryParams= params;
    $('#dtlDataGrid').datagrid('options').url= url;
    $('#dtlDataGrid').datagrid('load');
};

//导出
gatherList.doExport = function() {
	common_util.doExport('dtlDataGrid','/bill_balance/hq/export','汇总报表',{type:'gather'},BasePath + '/bill_balance/hq/gather_count');
};

//清空
gatherList.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType][name!=isArea]").val("");
};
