function RegisterInvoice(){
}

var registerInvoice = new RegisterInvoice();


//RegisterInvoice.prototype.commonSetting = {
//		searchFormId : "searchForm",
//		searchBtn : "btn-search",
//		searchUrl : "/register_invoice/list.json",
//		dataGridId : "dataGridDiv",
//		clearBtn : "btn-remove",
//		exportBtn : "btn-export",
//		export_url : "/register_invoice/do_fas_export",
//		export_title : "发票统计导出"
//};

var commonSetting = function(){
	var setting = {
		searchFormId : "searchForm",
		searchBtn : "btn-search",
		searchUrl : "/register_invoice/list.json",
		dataGridId : "dataGridDiv",
		clearBtn : "btn-remove",
		exportBtn : "btn-export",
		export_url : "/register_invoice/do_fas_export",
		export_title : "发票统计导出"
	}
	return setting;
};

//search Event
registerInvoice.searchEvent = function(params){
	
	var fromObj = $('#' + params.formId);
	
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	
	var fromObjStr = convertArray($("#" + params.formId).serializeArray());
	var queryMxURL = BasePath + params.url;
	
	// 2.加载明细 注意请求方式 restful风格 get
	$("#" + params.dataGridId).datagrid('options').queryParams = eval("("
			+ fromObjStr + ")");
	$("#" + params.dataGridId).datagrid('options').url = queryMxURL;
	$("#" + params.dataGridId).datagrid('load');
	$(":checkbox:eq(0)").attr("checked", false);
	
};

//clear Event 
registerInvoice.doSearchClear = function() {
	
	$('#' + commonSetting().searchFormId).form("clear");
	$(':input','#' + commonSetting().searchFormId).not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};

//export Event 
registerInvoice.exportExcel = function(setting) {
	var $dg = $("#" + setting.dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	
	var subGrepColumns = $dg.datagrid('options').subColumns;
	
	var columns = $.grep(grepColumns[0], function(o, i) {
		if ($(o).attr("notexport") == true) {
			return true;
		}
		return false;
	}, true);
	
	var subColumns = [];
	if(typeof subGrepColumns != 'undefined' 
			&& subGrepColumns != null
			&& subGrepColumns != "") {
		subColumns = $.grep(subGrepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}
	
	var exportColumns = JSON.stringify(columns);
	var exportSubColumns = JSON.stringify(subColumns);
	var url = BasePath + setting.url;
	var dataRow = $dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.exportSubColumns = exportSubColumns;
				param.fileName = setting.title;
				param.exportType = setting.exportType || '';
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
					});
				}
			},
			success : function() {

			}
		});
	} else {
		showWarn('查询记录为空，不能导出!');
	}
};

//initialization 
registerInvoice.init = function(){
	
	//binding search button event
	$('#' + commonSetting().searchBtn).on("click",function(){
		registerInvoice.searchEvent(
			{
				formId : commonSetting().searchFormId,
				url : commonSetting().searchUrl,
				dataGridId : commonSetting().dataGridId
			}	
		);
	});
	
	// binding clear button event
	$('#' + commonSetting().clearBtn).on("click",function(){
		registerInvoice.doSearchClear();	
	});
	
	$('#' + commonSetting().exportBtn).on("click",function(){
		registerInvoice.exportExcel({
			dataGridId : commonSetting().dataGridId,
			url : commonSetting().export_url,
			title : commonSetting().export_title,
			exportType : "common"
		});
	});
};

$(function(){
	registerInvoice.init();
	registerInvoice.initTime();
});


registerInvoice.initTime = function(){
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
};