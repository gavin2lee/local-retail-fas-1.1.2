function InventoryClassificationSummaryDialog() { 
	var $this = this;
	
}


//导出excel
function  exportExcel() {
	var $dg = $("#dataGridDiv");
	var queryParams = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	var subGrepColumns = $dg.datagrid('options').subColumns;
	var Fcolumns= $dg.datagrid('options').frozenColumns;
	
	//添加冻结列
	if(Fcolumns.length >0){
		for(var i=Fcolumns[0].length-1;i>=0;i--){
			if(!Fcolumns[0][i]['expander']) {
				grepColumns[0].unshift(Fcolumns[0][i]);
			}
		}
	}
	//添加冻结列(第二行) 参照期间结存排版
	if(Fcolumns.length >1){
		for(var i=Fcolumns[1].length-1;i>=0;i--){
			if(!Fcolumns[1][i]['expander']) {
				grepColumns[1].unshift(Fcolumns[1][i]);
			}
		}
	}
	
	var columns = [], firstHeaderColumns = [],lastHeaderColumns = [];
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
		if(grepColumns && grepColumns.length > 2) {
			lastHeaderColumns = $.grep(grepColumns[2], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
		}
	} else {
		columns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}
	
	//移除冻结列
	if(Fcolumns.length >0){
		for(var i=Fcolumns[0].length-1;i>=0;i--){
			if(!Fcolumns[0][i]['expander']) {
				grepColumns[0].splice($.inArray(Fcolumns[0][i],grepColumns[0]),1);
			}
		}
	}
	//移除冻结列(第二行)
	if(Fcolumns.length >1){
		for(var i=Fcolumns[1].length-1;i>=0;i--){
			if(!Fcolumns[1][i]['expander']) {
				grepColumns[1].splice($.inArray(Fcolumns[1][i],grepColumns[1]),1);
			}
		}
	}
	
	// 获取排序字段，由于sortName只能获取field字段，所以需要转换
	var sortName = $dg.datagrid('options').sortName;
	var sortField = "", sortOrder = $dg.datagrid('options').sortOrder;
	if(sortName && columns) {
		for(var i = 0; i < columns.length; i++) {
			if(sortName == columns[i].field) {
				sortField = columns[i].sortField;
				break;
			}
		}
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
    var dataRow = $dg.datagrid('getRows');
    $("#exportExcelForm").remove();
    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
    if(dataRow.length > 0) {
    	$('#exportExcelForm').form('submit', {
            url : BasePath + "/inventory_classification_summary/do_exports",
            onSubmit : function(params) {
            	params.exportColumns = exportColumns;
            	params.exportSubColumns = exportSubColumns;
            	params.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
            	params.lastHeaderColumns = JSON.stringify(lastHeaderColumns);
            	params.fileName = "库存货分类汇总导出"
            	params.exportType = "common";
            	params.orderByField = sortField;
            	params.orderBy = sortOrder;
                if(queryParams != null && queryParams != {}) {
                    $.each(queryParams, function(i) {
                    	params[i] = queryParams[i];
                    });
                }
            },
            success : function() {

            }
        });
    } else {
        showWarn('查询记录为空，不能导出!');
    }
}

//查询
function search(){
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var categoryNo = $("#categoryNo").combobox('getValues');
		var categoryNos = '';
		$.each(categoryNo, function(index, row) {
			categoryNos = categoryNos + row + ",";
		});
		params.categoryNo = categoryNos;
		loadColumn(params);
		/*loadResult(params);*/
	}
};

function clear(){
	$('#searchForm').form('clear');
}

/*function loadResult(params) {
	setTimeout(function(){
		$('#dataGridDiv').datagrid('options').queryParams= params;
		$('#dataGridDiv').datagrid('options').url= BasePath + '/inventory_classification_summary/list.json';
		$('#dataGridDiv').datagrid('load');
	},300);
};*/

function loadColumn(params) {
	ajaxRequestAsync( BasePath + '/inventory_classification_summary/column_list.json',params,function(data){
	if(data.CateGoryColumn){
		var columnsNew = [];
		var dataCateGory=[];
		var dataOrderFrom=[];
		var dataArrayNumber=[];
		dataCateGory.push({
			field:'brand_name',
			title:'品牌部',
			width:100,
			rowspan:'3',
			align:'left'
		});
		dataCateGory.push({
			field:'year',
			title:'年份',
			width:80,
			rowspan:'3',
			exportType:'number',
			align:'left'
		});
		dataCateGory.push({
			field:'month',
			title:'月份',
			width:80,
			rowspan:'3',
			align:'left'
		});
		dataCateGory.push({
			field:'years',
			title:'商品年份',
			width:80,
			rowspan:'3',
			align:'left'
		});
		dataCateGory.push({
			field:'season_name',
			title:'商品季节',
			width:80,
			rowspan:'3',
			align:'left'
		});
		$.each(data.CateGoryColumn,function(index,item){
			dataCateGory.push({
				title:item.categoryName,
				align:'center',
				colspan:(item.openingQty+1)*2
			});
			$.each(data.OrderFromColumn,function(index,it){
				dataOrderFrom.push({
					title:it.gender,
					width:80,
					colspan:'2',
					align:'center'
				});
				dataArrayNumber.push({
					field:item.categoryNo+"_"+it.orderfrom+"_qty",
					title:'数量',
					width:80,
					exportType:'number',
					align:'center'
				});
				dataArrayNumber.push({
					field:item.categoryNo+"_"+it.orderfrom+"_amount",
					title:'成本额',
					width:100,
					exportType:'number',
					align:'center'
				});
			});
			dataOrderFrom.push({
				title:'小计',
				width:80,
				colspan:'2',
				align:'center'
			});
			dataArrayNumber.push({
				field:item.categoryNo+"_qty",
				title:'数量',
				width:80,
				exportType:'number',
				align:'center'
			});
			dataArrayNumber.push({
				field:item.categoryNo+'_amount',
				title:'成本额',
				width:100,
				exportType:'number',
				align:'center'
			});
		});
		columnsNew[0] = dataCateGory;
		columnsNew[1] = dataOrderFrom;
		columnsNew[2] = dataArrayNumber;
		$('#dataGridDiv').datagrid({
				url:BasePath + '/inventory_classification_summary/list.json',
				queryParams:params,
				columns : columnsNew
			});
		}else{
			showSuc("暂无数据!");
			$('#dataGridDiv').datagrid('loadData',{total:0,rows:[],footer:[]}); 
		}
	});
};

var monthData = [{"value":"1","text":"1"},{"value":"2","text":"2"},
	                  {"value":"3","text":"3"},{"value":"4","text":"4"},
	                  {"value":"5","text":"5"},{"value":"6","text":"6"},
	                  {"value":"7","text":"7"},{"value":"8","text":"8"},
	                  {"value":"9","text":"9"},{"value":"10","text":"10"},
	                  {"value":"11","text":"11"},{"value":"12","text":"12"}];
$(function(){
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear();
	var currentMonth = currentDate.getMonth() + 1;
	$("#monthCondition").initCombox({
		data:monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:false,
		required:true,
		value: currentMonth
	});
	$('#yearCondition').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 130,
		editable:false,
		required:true,
		value:currentYear
	});
	$('#yearCondition1').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 130,
		editable:false
	});
});
/*var dialog = null;
$(function() {
	$.fas.extend(InventoryClassificationSummaryDialog, FasDialogController);
	dialog = new InventoryClassificationSummaryDialog();
	dialog.init("/inventory_classification_summary", {
		dataGridId : "dataGridDiv",
		exportTitle : "库存货分类汇总导出",
		exportUrl : "/do_exports",
		exportType : "common"
	});
});*/