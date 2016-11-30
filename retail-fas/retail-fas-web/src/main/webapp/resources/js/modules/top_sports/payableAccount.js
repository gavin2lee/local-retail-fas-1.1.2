var payableAccount={};

$(function(){
	setDate();
});

//获取当前月的第一天
function getFirstDay() {
	var current = new Date();
	current.setDate(1);
	return current;
}; 

//获取当前月的最后一天
function getLastDay() {
	var current = new Date();
	var currentMonth = current.getMonth();
	var nextMonth = ++currentMonth;
	var nextMonthDayOne = new Date(current.getFullYear(), nextMonth, 1);
	var minusDate = 1000 * 60 * 60 * 24;
	return new Date(nextMonthDayOne.getTime() - minusDate);
}; 

//设置时间
function setDate() {
	$("#sendDateStart").datebox('setValue',getFirstDay().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getLastDay().format("yyyy-MM-dd"));
};

payableAccount.search = function() {
	var reqParam = $("#searchForm").form("getData");
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	if (index == 0) {
		var queryMxURL = BasePath + "/payable_account/list.json";
		$("#dataDGridJG").datagrid('options').queryParams = reqParam;
		$("#dataDGridJG").datagrid('options').url = queryMxURL;
		$("#dataDGridJG").datagrid('load');
	} else {
		var queryMxURL = BasePath + "/payable_account/sum_list.json";
		$("#sumDataGrid").datagrid('options').queryParams = reqParam;
		$("#sumDataGrid").datagrid('options').url = queryMxURL;
		$("#sumDataGrid").datagrid('load');
	}
};

payableAccount.clear=function(){
	$("#searchForm").form("clear");
	$("#buyerNoId,#salerNoId").val("");
	setDate();
};

/**
 * 导出
 */
payableAccount.doExport = function(exportUrl) {
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	//判断导出明细还是汇总
	var params;
	var columns;
	var flag;
	var excelTitle;
	if (index == 0) {
		 var  $dg = $('#dataDGridJG');
		 params = $dg.datagrid('options').queryParams;
		 columns = $dg.datagrid('options').columns;
		 flag='dtlList';
		 excelTitle='明细导出';
	}else{
		 var  $dg = $('#sumDataGrid');
		 params = $dg.datagrid('options').queryParams;
		 columns = $dg.datagrid('options').columns;
		 flag='sumList';
		 excelTitle='汇总导出';
	}

	var columnsNew = [];
	$.each(columns, function(index, item) {
		var dataArray = [];
		var i = 0;
		$.each(item, function(rowIndex, rowData) {
			if (rowData.hidden) {
				return;
			}
			var v_object = {};
			v_object.field = rowData.field;
			v_object.title = rowData.title;
			v_object.width = rowData.width;
			if (rowData.hidden == 'true' || rowData.notexport) {
				return;
			}
			dataArray[i] = v_object;
			i++;
		});
		columnsNew[index] = dataArray;
	});

	var exportColumns = JSON.stringify(columnsNew);
	var url = BasePath + exportUrl;
	var dataRow = $dg.datagrid('getRows');
	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm'  method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				if (params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
					});
				}
				param.exportColumns = exportColumns;
				param.fileName = excelTitle;
				param.flag=flag;
			},
			success : function() {
			}
		});
	} else {
		alert('查询记录为空，不能导出!', 1);
	}
};

