
var creditCardDealDtl = new Object();

var commonSetting = function(){
	var setting = {searchBtn : "btn-search", clearBtn : "btn-remove", exportBtn : "btn-export"};
	var panel = $('#mainTab').tabs('getSelected');  
	var tab = panel.panel('options').tab[0];
	if(tab.innerText == "银联刷卡交易明细"){
		setting.searchFormId = "creditCardSearchForm";
		setting.searchUrl = "/self_shop_credit_card_deal_dtl/list.json";
		setting.dataGridId = "creditCardDealDataGridDiv";
		setting.exportUrl = "/self_shop_credit_card_deal_dtl/do_fas_export";
		setting.exportTitle = "银联交易明细导出";
	}else if(tab.innerText== "现金交易明细"){
		setting.searchFormId = "cashDealSearchForm";
		setting.searchUrl = "/cash_transaction_dtl/list.json";
		setting.dataGridId = "cashDealDataGridDiv";
		setting.exportUrl = "/cash_transaction_dtl/do_fas_export";
		setting.exportTitle = "现金交易明细导出";
	}
	return setting;
};

//查询
creditCardDealDtl.search = function(){
	var params = {formId : commonSetting().searchFormId,url : commonSetting().searchUrl, dataGridId : commonSetting().dataGridId};
	var formObj = $("#" + params.formId);
	var validateForm = formObj.form('validate');
	//校验必填项
	if(validateForm == false){
		return;
	}
	var formObjStr = convertArray($("#" + params.formId).serializeArray());
	var queryMxURL = BasePath + params.url;
	
	// 2.加载明细 注意请求方式 restful风格 get
	$("#" + params.dataGridId).datagrid('options').queryParams = eval("(" + formObjStr + ")");
	$("#" + params.dataGridId).datagrid('options').url = queryMxURL;
	$("#" + params.dataGridId).datagrid('load');
	$(":checkbox:eq(0)").attr("checked", false);//标题栏复选框不可用
};

//清空
creditCardDealDtl.clear = function(){
	$('#' + commonSetting().searchFormId).form("clear");
	$(':input','#' + commonSetting().searchFormId).not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};

//删除
creditCardDealDtl.del = function(){
	var delFlag = false;
	var checkedRows = $('#'+ commonSetting().dataGridId).datagrid('getChecked');

	if (checkedRows.length == 0) {
		showWarn('请选择要删除的行..');
		return;
	}

	var rowIndex;
	$.each(checkedRows, function(index, row) {
		rowIndex = $('#'+ commonSetting().dataGridId).datagrid('getRowIndex', row);
		$('#'+ commonSetting().dataGridId).datagrid('deleteRow', rowIndex);
		if (creditCardDealDtl.editRowIndex == rowIndex) {
			creditCardDealDtl.editRowIndex = -1;
		}
	});
	
	var url = BasePath + '/self_shop_credit_card_deal_dtl/save';
	var deletedRows = $('#'+ commonSetting().dataGridId).datagrid('getChanges','deleted');
	var changeRows = {deleted : JSON.stringify(deletedRows)};
	saveToDB(url, changeRows);
};

creditCardDealDtl.importOperation = function(){
	$.importExcel.open({
		'submitUrl' : BasePath + '/self_shop_credit_card_deal_dtl/do_import',
		'templateName' : '银联交易明细导入.xlsx',
		success : function(data) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data.error)) {
					showError(data.error);
				} else {
					$.importExcel.colse();
					showSuc('数据导入成功');
					$("#" + commonSetting().dataGridId).datagrid('reload');
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

creditCardDealDtl.exportOperation = function (){
	var params = {
			dataGridId : commonSetting().dataGridId,
			exportUrl : commonSetting().exportUrl,
			exportTitle : commonSetting().exportTitle,
			exportType : commonSetting().exportType
	};
	$.fas.exportExcel(params);
};

creditCardDealDtl.init = function()
{
	
};

//保存到DB
function saveToDB(url, changeRows) {
	commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('删除成功');
			creditCardDealDtl.search();
		} else {
			showSuc('删除失败');
		}
	});
};

function commonAjaxRequest(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async : false,
		dataType : 'json',
		success : callback
	});
};

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};

$(function(){
	
	creditCardDealDtl.init();
	
	console.log('initialzation successful ....');
});
