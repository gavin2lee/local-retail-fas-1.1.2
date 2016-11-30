function BLKStoreBalanceReportDialog() {
	this.monthData = [{"value":"1","text":"1"},{"value":"2","text":"2"},
	                  {"value":"3","text":"3"},{"value":"4","text":"4"},
	                  {"value":"5","text":"5"},{"value":"6","text":"6"},
	                  {"value":"7","text":"7"},{"value":"8","text":"8"},
	                  {"value":"9","text":"9"},{"value":"10","text":"10"},
	                  {"value":"11","text":"11"},{"value":"12","text":"12"}];
	
	this.exportExcel = function() {
	    $.fas.exportExcel({
			dataGridId : "dataGridDiv",
			exportUrl : "/blk_store_balance/do_fas_export",
			exportTitle : "店铺结存报表导出"
		});
	};
}

var dialog = null;
$(function() {
	$.fas.extend(BLKStoreBalanceReportDialog, FasDialogController);
	dialog = new BLKStoreBalanceReportDialog();
	dialog.init("/blk_store_balance", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/store_balance.json",
		searchFormId : "searchForm"
	});
	var date = new Date();
	
	$("#startYearMonth").val(date.format("yyyyMM"));
	$("#endYearMonth").val(date.format("yyyyMM"));

});