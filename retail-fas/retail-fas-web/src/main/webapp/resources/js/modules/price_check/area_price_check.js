var priceCheck={};

$(function(){
	setDate();
	
	$.fas.tooltip({
	  id : 'columnDsc',
	  content : '当公司是总部公司 选择出库单据页签 可更新总部到地区出库类单据的地区价；'
			   +' 当公司是地区公司 选择入库单据页签 可更新总部到地区、地区到地区入库类单据的地区价'
	});
});

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

function getEndDate() {
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

//设置时间
function setDate() {
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
};

priceCheck.search = function() {
	var fromObj = $('#searchForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	var reqParam = $("#searchForm").form("getData");
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	if (index == 0) {
		var queryMxURL = BasePath + "/area_price_check/sale_list.json";
		$("#saleDataGrid").datagrid('options').queryParams = reqParam;
		$("#saleDataGrid").datagrid('options').url = queryMxURL;
		$("#saleDataGrid").datagrid('load');
	} else {
		var queryMxURL = BasePath + "/area_price_check/buy_list.json";
		$("#buyDataGrid").datagrid('options').queryParams = reqParam;
		$("#buyDataGrid").datagrid('options').url = queryMxURL;
		$("#buyDataGrid").datagrid('load');
	}
};

priceCheck.clear=function(){
	$("#searchForm").form("clear");
	$("#saleCompanyNoId,#brandUnitNoId,#itemCodeCondition").val("");
	$("#TypeOneId").attr("checked","checked");
	setDate();
};

priceCheck.costUpdate = function(){
	var fromObj = $('#searchForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	};
	
	var selected=$('input:radio:checked').val();
	if (selected == '0') {
		showInfo("没有维护地区价,不能更新！");
		return;
	};
	
	$.messager.confirm("确认", "确定更新单据价格?", function(r) {
		if (r) {
			var tab = $('#mainTab').tabs('getSelected');
			var index = $('#mainTab').tabs('getTabIndex', tab);
			if (index == 0) {
				fromObj.form('submit', {
					url : BasePath + "/area_price_check/salePrice_update",
					onSubmit : function(param) {
					},
					success : function(data) {
						$.fas.showMsg('更新完成！', 5000);
						priceCheck.search();
					}
				});
			}else{
				fromObj.form('submit', {
					url : BasePath + "/area_price_check/buyPrice_update",
					onSubmit : function(param) {
					},
					success : function(data) {
						$.fas.showMsg('更新完成！', 5000);
						priceCheck.search();
					}
				});
			}
		}
	});
};

/**
 * 导出
 */
priceCheck.doExport = function(exportUrl) {
	var tab = $('#mainTab').tabs('getSelected');
	var index = $('#mainTab').tabs('getTabIndex', tab);
	//判断导出明细还是汇总
	var params;
	var columns;
	var flag;
	var excelTitle;
	if (index == 0) {
		 var  $dg = $('#saleDataGrid');
		 params = $dg.datagrid('options').queryParams;
		 columns = $dg.datagrid('options').columns;
		 flag='saleGrid';
		 excelTitle='出库单据';
	}else{
		 var  $dg = $('#buyDataGrid');
		 params = $dg.datagrid('options').queryParams;
		 columns = $dg.datagrid('options').columns;
		 flag='buyGrid';
		 excelTitle='入库单据';
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

