var report_ap_aging = new Object();

//查询
report_ap_aging.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/pe_report/report_ap_aging.json';
	    $('#dtlDataGrid').datagrid('options').queryParams= params;
	    $('#dtlDataGrid').datagrid('options').url= url;
	    $('#dtlDataGrid').datagrid('load');
	}
};

//清空
report_ap_aging.clear = function() {
	$('#searchForm').form("clear");
	$('#isBalance').attr("checked","checked");
};

//初始化
$(function(){
	$('#isBalance').attr("checked","checked");
});
