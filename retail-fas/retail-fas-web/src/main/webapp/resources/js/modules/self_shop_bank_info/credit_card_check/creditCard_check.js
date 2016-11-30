var check = new Object();

check.searchEventBtn = function(){
	var tab = $('#mainTab').tabs("getSelected");
	var tabTitle = tab.panel('options').title;
	var fromObj = $('#searchForm');
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	var fromObjStr = convertArray($("#searchForm").serializeArray());
	if(tabTitle == '银联刷卡核对') {
		$("#creditCardCheckDataGridDiv").datagrid({
			url : BasePath + '/credit_card_check/list.json',
			queryParams : eval("(" + fromObjStr + ")"),
			onLoadSuccess : function(data){
//				showColumn(data,'creditCardCheckDataGridDiv');
			}
		});
		$(":checkbox:eq(0)").attr("checked", false);
	}else if(tabTitle == '现金核对'){
		$("#cashCheckDataGridDiv").datagrid({
			url : BasePath + '/cash_check/list.json',
			queryParams : eval("(" + fromObjStr + ")"),
			onLoadSuccess : function(data){
//				showColumn(data,'cashCheckDataGridDiv');
			}
		});
		$(":checkbox:eq(0)").attr("checked", false);
	}
};

check.exportEventBtn = function (){
	var tab = $('#mainTab').tabs("getSelected");
	var tabTitle = tab.panel('options').title;
	
	if(tabTitle == '银联刷卡核对') {
		check.exportExcel(creditCardCheckExportParams);
	}else if(tabTitle == '现金核对'){
		check.exportExcel(cashCheckExportParams);
	}
	
};

//导出
check.exportExcel = function(setting) {
	var $dg = $("#" + setting.dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	var subGrepColumns = $dg.datagrid('options').subColumns;
	
	var columns = [], firstHeaderColumns = [];
	
	if(grepColumns && grepColumns.length > 1) {
		columns = $.grep(grepColumns[1], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
		firstHeaderColumns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	} else {
		columns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}

	
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
				param.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
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


//组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	var tab = $('#mainTab').tabs("getSelected");
	var tabTitle = tab.panel('options').title;
	
	if(tabTitle == '银联刷卡核对') {
		return params + "terminalNumber=" + rowData.terminalNumber+"&outDate=" + rowData.outDate;
	}else if(tabTitle == '现金核对'){
		return params + "depositAccount=" + rowData.depositAccount+"&depositDate=" + rowData.depositDate;
	}
	
	
};

var changeColor = function(value,row,index){
	return 'color:red';
};

var creditCardCheckSearchParams = {
		formId : "creditCardCheckSearchForm",
		url : "/credit_card_check/list.json",
		dataGridId : "creditCardCheckDataGridDiv"
	};

var creditCardCheckExportParams = {
		dataGridId : "creditCardCheckDataGridDiv",
		url : "/credit_card_check/do_fas_export",
		title : "银联刷卡核对导出",
		exportType : "common"
	};

var cashCheckSearchParams = {
	formId : "cashCheckSearchForm",
	url : "/cash_check/list.json",
	dataGridId : "cashCheckDataGridDiv"
};

var cashCheckExportParams = {
	dataGridId : "cashCheckDataGridDiv",
	url : "/cash_check/do_fas_export",
	title : "现金核对导出",
	exportType : "common"
};


$(function(){
	$('#mainTab').tabs({
	    border:false,
	    onSelect:function(title,index){
	    	if(title=="银联刷卡核对"){
	    		$("#searchForm").find("th").eq(2)[0].hidden=true;
	    		$("#searchForm").find("td").eq(2)[0].hidden=true;
	    	}else if(title=="现金核对"){
	    		$("#searchForm").find("th").eq(2)[0].hidden=false;
	    		$("#searchForm").find("td").eq(2)[0].hidden=false;
	    	}
	    }
	});
	// 初始化页签
	$('#mainTab').addTab({
		title : "银联刷卡核对",
		href : BasePath + "/credit_card_check/credit_card_check_list",
		selected : true,
		onLoad : function(panel) {
			
		}
	});
	$('#mainTab').addTab({
		title : "现金核对",
		href : BasePath + "/credit_card_check/cash_check_list",
		onLoad : function(panel) {
			
		}
	});
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#startOutDate").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutDate").val(lastDay.format("yyyy-MM-dd"));
});
