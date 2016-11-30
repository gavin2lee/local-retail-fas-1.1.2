/**
 * 地区自购入库明细表
 */
function purchaseEntryDtl() {
};
purchaseEntryDtl.prototype = new areaDetail();
var entryDtl = new purchaseEntryDtl();

$(function() {
	entryDtl.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/area_private_purchase/list.json?',
		exportUrl : '/area_private_purchase/export',
		excelTitle : '自购入库明细表'
	});
	toolSearch({
		appendTo:$('#toolbar'), 
		target:$('#subLayout'), 
		collapsible:true 
	});
	setDefaultDate();
	
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	var  organTypeNo =  currentUser.organTypeNo;
});

entryDtl.clear = function() {
	$("#searchForm").form("clear");
	$("input:hidden").val("");
	setDefaultDate();
};

entryDtl.doImport = function (){
	$.importExcel.open({
		'submitUrl' : BasePath + '/area_private_purchase/do_import',
		'templateName' : '地区自购明细价格导入.xlsx',
		success : function(data) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data)) {
					showInfo(data);
					$.importExcel.colse();
					showSuc('数据导入成功');
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});
};

function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};

function setDefaultDate() {
	$("#sendDateStart").datebox('setValue',getCurrentMonthFirstDay().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getCurrentMonthLastDay().format("yyyy-MM-dd"));
}