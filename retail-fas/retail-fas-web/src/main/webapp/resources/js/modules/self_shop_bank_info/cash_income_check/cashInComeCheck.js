
$(function(){
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
	
	var setting = {
			searchFormId : "searchForm",
			searchBtn : "btn-search",
			searchUrl : "/cash_income_check/list.json",
			dataGridId : "dataGridDiv",
			clearBtn : "btn-remove",
			exportBtn : "btn-export",
			exportUrl : "/cash_income_check/do_fas_export",
			exportTitle : "现金进账核对导出"
	};
	
	$.BasicDeal(setting);
});


//组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "id=" + rowData.id;
};

var changeColor = function(value,row,index){
	if(value != '' && value != 0){
		return 'color:red;';
	}
}