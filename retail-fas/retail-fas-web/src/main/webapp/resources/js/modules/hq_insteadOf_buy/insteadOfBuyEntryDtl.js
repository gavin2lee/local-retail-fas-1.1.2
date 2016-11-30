/**
 * 总部代采购入库明细表
 */
function insteadOfBuyEntryDtl() {
};
insteadOfBuyEntryDtl.prototype = new areaDetail();
var entryDtl = new insteadOfBuyEntryDtl();

$(function() {
	entryDtl.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/hq_insteadOf_buy/entry_list.json',
		exportUrl : '/hq_insteadOf_buy/entry_export',
		excelTitle : '采购入库明细表'
	});
	toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'), 
        collapsible:true 
	});
	setDefaultDate();
});

entryDtl.clear = function() {
	$("#searchForm").form("clear");
	$("input:hidden[name!='sign']").val("");
	setDefaultDate();
};

function setDefaultDate(){
	$("#receiveDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#receiveDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}