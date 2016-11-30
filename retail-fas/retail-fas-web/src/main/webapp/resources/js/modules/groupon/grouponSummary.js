function GrouponSummary() {}

var _fasController = new FasController();

GrouponSummary.prototype.super = _fasController;

var _grouponSummary = new GrouponSummary();

//查询
GrouponSummary.prototype.search = function() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var ticketStartDate = $("#ticketStartDate").val();
	var ticketEndDate = $("#ticketEndDate").val();
	if(!startDate || !endDate || !ticketStartDate || !ticketEndDate) {
		showWarn("请输入开始和结束时间");
		return;
	}
	_fasController.search({
		searchFormId : "searchForm",
		dataGridId : "dataGridDiv",
		searchUrl : "/groupon_summary/order_total_lists.json"
	});
};

//清空
GrouponSummary.prototype.clear = function() {
	_fasController.clear("searchForm");
};

// 导出
GrouponSummary.prototype.exportExcel = function() {
	_fasController.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/groupon_summary/do_groupon_export",
		exportTitle : "团购活动跟踪信息",
		exportType : "common"
	});
};

$(function() {
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	$("#startDate").datebox("setValue", strDate + "-1");
	$("#ticketStartDate").datebox("setValue", strDate + "-1"); 
	strDate += "-" + currTime.getDate();
	$("#endDate").datebox("setValue", strDate);
	$("#ticketEndDate").datebox("setValue", strDate); 
});