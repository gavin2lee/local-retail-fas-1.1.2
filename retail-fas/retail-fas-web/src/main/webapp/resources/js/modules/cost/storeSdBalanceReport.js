function StoreBalanceReportDialog() { 
	var $this = this;
	
	this.exportExcel = function() {
	    var $this = this;
	    $.fas.exportExcel({
			dataGridId : "dataGridDiv",
			exportUrl : "/store_sd_balance_report/async_exports",
			exportTitle : "店铺进销存报表导出",
			async:true
		});
	};
}

var dialog = null;
$(function() {
	$.fas.extend(StoreBalanceReportDialog, FasDialogController);
	dialog = new StoreBalanceReportDialog();
	dialog.init("/store_sd_balance_report", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/store_sd_balance_report.json",
		searchFormId : "searchForm"
		//exportTitle : "店铺进销存报表导出",
		//exportUrl : "/async_exports",
		//exportType : "common"
	});
});
