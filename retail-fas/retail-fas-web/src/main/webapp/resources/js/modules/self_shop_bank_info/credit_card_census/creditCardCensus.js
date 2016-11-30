$(function(){
	var setting = {
			searchFormId : "searchForm",
			searchBtn : "btn-search",
			searchUrl : "/credit_card_census/creditCard_census",
			dataGridId : "dataGridDiv",
			clearBtn : "btn-remove",
			exportBtn : "btn-export",
			export_url : "/credit_card_census/do_fas_export",
			export_title : "银联交易核对导出"
	}
	$.BasicDeal(setting);
	
	initTime();
});

function initTime(){
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
	
};