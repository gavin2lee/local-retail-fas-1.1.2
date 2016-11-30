/*-----用于地区间、地区自购、总部代采、地区其他出库明细表查询-------*/

function areaDetail() {
}

// 初始化属性
areaDetail.prototype.options = {
	formId : 'formId',
	dataGridId : 'datagridId',
	queryUrl : '',
	exportUrl : '',
	excelTitle : '明细表'
};

// 初始化设置
areaDetail.prototype.init = function(options) {
	this.options = options;
};

// 查询
areaDetail.prototype.search = function() {
	if ($('#'+this.options.formId).form('validate')) {
		var params = $('#' + this.options.formId).form('getData');
		var url = BasePath + this.options.queryUrl;
		$('#' + this.options.dataGridId).datagrid('options').queryParams = params;
		$('#' + this.options.dataGridId).datagrid('options').url = url;
		$('#' + this.options.dataGridId).datagrid('load');
	}
};

//价格异常检查
areaDetail.prototype.costCheck = function() {
	$("#btn-direct").on('click',function(){
		var url = BasePath + "/exception_price_check/list"
		openNewPane(url,'','价格异常检查');
	});
};

// 开始日期
function getStartDate() {
	var d = new Date();
	var year = d.getFullYear();
	var cmonth = d.getMonth();
	var day = d.getDate();
	var startDate = new Date();
	startDate.setDate(1);
	if (cmonth === 0 && day <= 10) {
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(10);
	} else if (cmonth === 0 && day > 10) {
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(11);
	} else {
		if (day <= 10) {
			startDate.setMonth(cmonth - 2);
		} else {
			startDate.setMonth(cmonth - 1);
		}
	}
	startDate.setDate(26);
	return startDate;
};

// 结束日期
function getEndDate(){
	var d = new Date();
	var year = d.getFullYear();
	var cmonth = d.getMonth();
	var day = d.getDate();
	var endDate = new Date();
	if (cmonth === 0 && day <= 10) {
		endDate.setFullYear(d.getFullYear() - 1);
		endDate.setMonth(11);
	} else if (cmonth === 0 && day > 10) {
		endDate.setFullYear(d.getFullYear());
		endDate.setMonth(0);
	} else {
		if (day <= 10) {
			endDate.setMonth(cmonth - 1);
		} else {
			endDate.setMonth(cmonth);
		}
	}
	endDate.setDate(25);
	return endDate;
};

// 获取当前月的第一天
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

// 导出
areaDetail.prototype.doExport = function() {
	var $this = this;
	var $dg = $('#' + this.options.dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var columns = $dg.datagrid('options').columns;
	var Fcolumns= $dg.datagrid('options').frozenColumns;
	if(Fcolumns[0].length>0){
		columns=Fcolumns.concat(columns);
	}
	// var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
	// var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录

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
	var url = BasePath + this.options.exportUrl;
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
				param.fileName = $this.options.excelTitle;
				// param.pageNumber = v_pageNumber;
				// param.pageSize = v_pageSize;
			},
			success : function() {

			}
		});
	} else {
		alert('查询记录为空，不能导出!', 1);
	}
};
