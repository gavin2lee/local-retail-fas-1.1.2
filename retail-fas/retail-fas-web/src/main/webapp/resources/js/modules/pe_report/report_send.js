var report_send = new Object();

//查询
report_send.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/pe_report/report_send.json';
	    $('#dtlDataGrid').datagrid('options').queryParams= params;
	    $('#dtlDataGrid').datagrid('options').url= url;
	    $('#dtlDataGrid').datagrid('load');
	}
};

//清空
report_send.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
};

//初始化
$(function(){
	$('#gatherBy').combobox({
		data:[{code:'brand', name:'按品牌汇总'}, {code:'brandUnit', name:'按品牌部汇总'}],
		valueField:'code',
		textField:'name',
		width : 140,
		editable:true
	});
});
