/**
 * 总部地区明细对账表
 */
function detail() {};
detail.prototype = new areaDetail();
var hqAreaDtl = new detail();

//初始化属性
$(function() {
	hqAreaDtl.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/hq_area_reconciliation_dtl/list.json',
		exportUrl : '/hq_area_reconciliation_dtl/export',
		excelTitle : '总部地区明细对账表'
	});
	setDefaultDate();
});

//清空
hqAreaDtl.clear=function(){
	$("#searchForm").form("clear");
	$('#salerNoCondition,#brandUnitNoId,#itemCodeCondition,#buyerNoCondition').val('');
	setDefaultDate();
};

//设置时间
function setDefaultDate(){
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}

/**
 * 出货明细表
 */
function shipMent() {};
shipMent.prototype = new areaDetail();
var shipMentDtl = new shipMent();

//初始化属性
$(function() {
	shipMentDtl.init({
		formId : 'dataForm',
		dataGridId : 'shipmentDG',
		queryUrl : '/hq_area_reconciliation_dtl/shipment_list.json',
		exportUrl : '/hq_area_reconciliation_dtl/export_list',
		excelTitle : '出货明细表'
	});
	setDefaultDate();
});

//清空
shipMentDtl.clear=function(){
	$("#dataForm").form("clear");
	$('#salerNoCondition,#brandUnitNoId,#itemCodeCondition,#buyerNoCondition').val('');
	setDefaultDate();
};