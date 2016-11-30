function StoreBalanceReportDialog() { 
	var $this = this;
	
	this.monthData = [{"value":"1","text":"1"},{"value":"2","text":"2"},
	                  {"value":"3","text":"3"},{"value":"4","text":"4"},
	                  {"value":"5","text":"5"},{"value":"6","text":"6"},
	                  {"value":"7","text":"7"},{"value":"8","text":"8"},
	                  {"value":"9","text":"9"},{"value":"10","text":"10"},
	                  {"value":"11","text":"11"},{"value":"12","text":"12"}];
	
	this.exportExcel = function() {
		var async = true;
		var url = "/store_balance_report/" + ( async?"async_exports":"do_fas_export");
	    var $this = this;
	    $.fas.exportExcel({
			dataGridId : "dataGridDiv",
			exportUrl : url,
			exportTitle : "店铺进销存报表导出",
			async:async
		});
	};
}

var dialog = null;
$(function() {
	$.fas.extend(StoreBalanceReportDialog, FasDialogController);
	dialog = new StoreBalanceReportDialog();
	dialog.init("/store_balance_report", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/store_balance_report.json",
		searchFormId : "searchForm"
		//exportTitle : "店铺进销存报表导出",
		//exportUrl : "/async_exports",
		//exportType : "common"
	});
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear();
	var currentMonth = currentDate.getMonth() + 1;
	$("#monthCondition").initCombox({
		data:dialog.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:false,
		required:true,
		value: currentMonth
	});
	$('#yearCondition').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 130,
		editable:false,
		required:true,
		value:currentYear
	});

});
