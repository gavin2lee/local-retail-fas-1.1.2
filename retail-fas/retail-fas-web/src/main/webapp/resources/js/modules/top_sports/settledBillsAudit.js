var billsAudit={};

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

/**
 * 查询
 */
billsAudit.search = function() {
	var reqParam = $("#searchForm").form("getData");
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	if (index == 0) {
		var queryMxURL = BasePath + "/settledBills_audit/sum_list.json";
		$("#sumDataGrid").datagrid('options').queryParams = reqParam;
		$("#sumDataGrid").datagrid('options').url = queryMxURL;
		$("#sumDataGrid").datagrid('load');
	} else {
		var queryMxURL = BasePath + "/settledBills_audit/list.json";
		$("#dataDGridJG").datagrid('options').queryParams = reqParam;
		$("#dataDGridJG").datagrid('options').url = queryMxURL;
		$("#dataDGridJG").datagrid('load');
	}
};

billsAudit.clear=function(){
	$("#searchForm").form("clear");
	$("#buyerNoId,#salerNoId").val("");
	setDate();
};

billsAudit.audit=function(){
	var checkedRows = $('#sumDataGrid').datagrid('getChecked');
	if(checkedRows.length<1){
		showInfo("请先勾选要审核的记录！");
		return;
	}
	if (checkedRows) {
	  $.messager.confirm("确认", "确定审核通过该结算单?", function(r) {
		if (r) {
			var url = BasePath + "/settledBills_audit/bills_audit";
			var ids_data = "";
			$.each(checkedRows, function(index, row) {
				ids_data += row.billNo + ",";
			});
			var params = {
				billNos :  ids_data.substring(0, ids_data.length - 1),
			};
			ajaxRequestAsync(url, params, function(data) {
				if (typeof data !="undifined") {
					$.fas.showMsg('操作成功',5000);
					billsAudit.search();
				} else {
					showError('操作失败');
				}
			});
		}
	});
   }
};

billsAudit.unAudit=function(){
	var checkedRows = $('#sumDataGrid').datagrid('getChecked');
	if(checkedRows.length<1){
		showInfo("请先勾选要反审核的记录！");
		return;
	}
	if (checkedRows) {
	  $.messager.confirm("确认", "确定反审核该结算单?", function(r) {
		if (r) {
			var url = BasePath + "/settledBills_audit/bills_unAudit";
			var ids_data = "";
			$.each(checkedRows, function(index, row) {
				ids_data += row.billNo + ",";
			});
			var params = {
				billNos :  ids_data.substring(0, ids_data.length - 1),
			};
			ajaxRequestAsync(url, params, function(data) {
				if (typeof data !="undifined") {
					$.fas.showMsg('操作成功',5000);
					billsAudit.search();
				} else {
					showError('操作失败');
				}
			});
		}
	});
   }
};

/**
 * 导出
 */
billsAudit.doExport = function(exportUrl) {
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	//判断导出明细还是汇总
	var params;
	var columns;
	var flag;
	var excelTitle;
	if (index == 0) {
		var  $dg = $('#sumDataGrid');
		params = $dg.datagrid('options').queryParams;
		columns = $dg.datagrid('options').columns;
		flag='sumList';
		excelTitle='汇总导出';
	}else{
		var  $dg = $('#dataDGridJG');
		params = $dg.datagrid('options').queryParams;
		columns = $dg.datagrid('options').columns;
		flag='dtlList';
		excelTitle='明细导出';
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

