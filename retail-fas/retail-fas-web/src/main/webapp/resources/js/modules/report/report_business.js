var report_business = new Object();

//查询
report_business.search = function() {
	if($('#searchForm').form('validate')){
		var sendDateStart = new Date($("#sendDateStart").val());
		var sendDateEnd = new Date($("#sendDateEnd").val());
		if(sendDateStart.getYear().toString()==sendDateEnd.getYear().toString()){
			var params = $('#searchForm').form('getData');
			var url = BasePath + '/report/report_business.json';
		    $('#dtlDataGrid').datagrid('options').queryParams= params;
		    $('#dtlDataGrid').datagrid('options').url= url;
		    $('#dtlDataGrid').datagrid('load');
		}else{
			alert("请选择同一年的日期期间!");
		}
	}
};

//清空
report_business.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
	common_util.initDate(); 
};

//初始化
$(function(){
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
});