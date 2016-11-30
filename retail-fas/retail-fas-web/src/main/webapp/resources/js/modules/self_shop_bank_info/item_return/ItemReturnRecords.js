$(function(){
	var setting = {
			searchFormId : "searchForm",
			searchBtn : "btn-search",
			searchUrl : "/item_return/list.json",
			dataGridId : "dataGridDiv",
			clearBtn : "btn-remove",
			exportBtn : "btn-export",
			exportUrl : "/item_return/do_fas_export",
			exportTitle : "退款汇总导出"
	};
	
	$.BasicDeal(setting);
	
	initTime();
});

function initTime(){
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#startOutDate").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutDate").val(lastDay.format("yyyy-MM-dd"));
	
};