var report = new Object();

//查询
report.search = function() {
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/report/list.json';
    $('#dtlDataGrid').datagrid('options').queryParams= params;
    $('#dtlDataGrid').datagrid('options').url= url;
    $('#dtlDataGrid').datagrid('load');
};

//清空
report.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	report.initDate(); 
};

report.initDate = function(){
	var currDate = new Date();
	var year = currDate.getFullYear();
	var month = currDate.getMonth();
	var nextMonth = currDate.getMonth()+1;
	if (month === 0) {
		year = year-1;
		month = 12;
		nextMonth = 1;
	}
	if(month < 10){
		month = '0' + month;
	}
	if(nextMonth < 10){
		nextMonth = '0' + nextMonth;
	}
	$('#sendStartDate').val(year +'-'+ month +'-'+ 26);
	$('#sendEndDate').val(year +'-'+ nextMonth +'-'+ 25);
	$('#totalSendStartDate').val(year +'-1-1');
	$('#totalSendEndDate').val(year +'-12-31');
}

// 初始化
$(function(){
	report.initDate(); 
});
