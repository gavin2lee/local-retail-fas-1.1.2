var report = new Object();

//查询
report.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/report/report_all_finance.json';
	    $('#dtlDataGrid').datagrid('options').queryParams= params;
	    $('#dtlDataGrid').datagrid('options').url= url;
	    $('#dtlDataGrid').datagrid('load');
	}
};

//清空
report.clear = function() {
	$('#searchForm').form("clear");
	common_util.initDate(); 
};

report.exportFlag = false;

report.doExport = function(){
	var dataRow=$('#dtlDataGrid').datagrid('getRows');
	if(dataRow.length>0){
		$.messager.progress({
			title:'请稍后',
			msg:'正在导出，请耐心等候...',
			text:''
		}); 
		common_util.doExport('dtlDataGrid','/report/do_exports','财务核对总表',{type:'all_finance'});
		var interval = setInterval(function(){
			ajaxRequestAsync(BasePath + '/report/getExportFlag',null,function(data){
				if(data && data.flag && data.flag == 'true'){
					$.messager.progress('close');
					window.clearInterval(interval);
				}
			});
		},3000);
		setTimeout(function(){
			if(interval){
				messager.progress('close');
				window.clearInterval(interval);
			}
		},300000);
	}else{
		alert('查询记录为空，不能导出!',1);
	}
	
}

//初始化
$(function(){
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
});