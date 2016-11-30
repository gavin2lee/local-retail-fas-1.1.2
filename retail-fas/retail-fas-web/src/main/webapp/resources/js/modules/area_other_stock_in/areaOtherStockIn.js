/**
 * 地区其他入库明细表
 */
function otherStockInDtl() {
};
otherStockInDtl.prototype = new areaDetail();
var areaOtherStockInDtl = new otherStockInDtl();

$(function(){
	areaOtherStockInDtl.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/area_other_stock_in_dtl/list.json?params=notGroupLeadRole',
		exportUrl : '/area_other_stock_in_dtl/export',
		excelTitle : '地区其他入库明细表'
	});
	toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'), 
        collapsible:true 
	});
	setDefaultDate();
});

areaOtherStockInDtl.clear = function() {
	$("#searchForm").form("clear");
	$("input:hidden").val("");
	setDefaultDate();
};

function setDefaultDate() {
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}