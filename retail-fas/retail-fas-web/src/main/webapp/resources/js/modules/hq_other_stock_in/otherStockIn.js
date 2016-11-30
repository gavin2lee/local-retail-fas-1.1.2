/**
 * 总部其他入库明细表
 */
function otherStockOutDtl() {
};
otherStockOutDtl.prototype = new areaDetail();
var otherOut = new otherStockOutDtl();
var organTypeNo = null;
$(function(){
	otherOut.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/hq_other_stock_in_dtl/list.json?',
		exportUrl : '/hq_other_stock_in_dtl/export',
		excelTitle : '总部其他入库明细表'
	});
	toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'), 
        collapsible:true 
	});
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	organTypeNo =  currentUser.organTypeNo;
	setDefaultDate();
});

otherOut.clear = function() {
	$("#searchForm").form("clear");
	$("input:hidden").val("");
	setDefaultDate();
};

function setDefaultDate() {
	$("#sendDateStart").datebox('setValue',getCurrentMonthFirstDay().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getCurrentMonthLastDay().format("yyyy-MM-dd"));
};