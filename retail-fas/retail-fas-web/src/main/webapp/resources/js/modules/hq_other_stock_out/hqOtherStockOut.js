/**
 * 总部其他出库明细表
 */
function otherStockOutDtl() {
};
otherStockOutDtl.prototype = new areaDetail();
var otherOut = new otherStockOutDtl();

$(function(){
	otherOut.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/hq_other_stock_out_dtl/list.json?params=groupLeadRole',
		exportUrl : '/hq_other_stock_out_dtl/export',
		excelTitle : '总部其他出库明细表'
	});
	toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'), 
        collapsible:true 
	});
	setDefaultDate();
});

otherOut.clear = function() {
	$("#searchForm").form("clear");
	$("input:hidden").val("");
	setDefaultDate();
};

function setDefaultDate() {
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}