var oweTheGuest={};

$(function(){
	oweTheGuest.addMain();
	setDefaultDate();
	toolSearch({
		appendTo:$('#toolbar'), //添加位置，默认为$('#toolbar')
		target:$('#subLayout'), //控制对象，默认为$('#subLayout')
		collapsible:true //是否显示下拉箭头
	});
	
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
});

oweTheGuest.addMain = function() {
	$('#mainTab').tabs('add', {
		title : '前期欠客本期发出明细',
		selected : false,
		closable : false,
		href : BasePath + '/owe_the_guest/list_tabMain.htm',
		onLoad : function(panel) {
			setDate();
			toolSearch({
				appendTo:$('#subToolbar'), //添加位置，默认为$('#toolbar')
				target:$('#mainLayout'), //控制对象，默认为$('#subLayout')
				collapsible:true //是否显示下拉箭头
			});
			
			//绑定店铺通用查询
			$("#shopNameId").filterbuilder({
		        type:'organ',
		        organFlag: 2,
		        roleType:'bizCity', 
		        onSave : function(result) { 
		        	var value = $(this).filterbuilder('getValue');
		        	$("#shopNoId").val(value);
		        }
		    });
		}
	});
};

/**
 * 查询本期欠客明细
 */
oweTheGuest.search=function(){
	if ($('#mainForm').form('validate')) {
		var params = $('#mainForm').form('getData');
		var url = BasePath + '/owe_the_guest/list.json?resultFlag=current';
		$('#dataGridJG').datagrid('options').queryParams = params;
		$('#dataGridJG').datagrid('options').url = url;
		$('#dataGridJG').datagrid('load');
	}
};

oweTheGuest.clear=function(){
	$('#mainForm').form('clear');
	$("#shopNo,#brandNo,#companyNo,#itemCodeId").val("");
	setDefaultDate();
};

/**
 * 查询前期欠客本期发出
 */
oweTheGuest.searchSum=function(){
	if ($('#searchForm').form('validate')) {
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/owe_the_guest/list.json?resultFlag=earlier';
		$('#contrastDg').datagrid('options').queryParams = params;
		$('#contrastDg').datagrid('options').url = url;
		$('#contrastDg').datagrid('load');
	}
};

oweTheGuest.clearData=function(){
	$('#searchForm').form('clear');
	$("#shopNoId,#brandNoId,#companyNoId,#itemCodeCond").val("");
	setDate();
};

//获取当前月的第一天
function getCurrentMonthFirstDay() {
	var current = new Date();
	current.setDate(1);
	return current;
}; 

// 获取当前月的最后一天
function getCurrentMonthLastDay() {
	var current = new Date();
	var currentMonth = current.getMonth();
	var nextMonth = ++currentMonth;
	var nextMonthDayOne = new Date(current.getFullYear(), nextMonth, 1);
	var minusDate = 1000 * 60 * 60 * 24;
	return new Date(nextMonthDayOne.getTime() - minusDate);
}; 

function setDefaultDate(){
	$("#sendDateStartCond").datebox('setValue',getCurrentMonthFirstDay().format("yyyy-MM-dd"));
	$("#sendDateEndCond").datebox('setValue',getCurrentMonthLastDay().format("yyyy-MM-dd"));
};

function setDate(){
	$("#sendDateStart").datebox('setValue',getCurrentMonthFirstDay().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getCurrentMonthLastDay().format("yyyy-MM-dd"));
};

oweTheGuest.doExport = function(dataGridId,exportUrl,excelTitle,flag) {
	var $dg = $('#' + dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var columns = $dg.datagrid('options').columns;

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