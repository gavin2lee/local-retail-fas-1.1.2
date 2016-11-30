function InventoryClassificationSummaryDialog() { 
	var $this = this;
	
}


//导出excel
function  exportExcel() {
	var $dg = $("#dataGridDivPe");
    var dataRow = $dg.datagrid('getRows');
    if(dataRow.length > 0) {
    	$.fas.exportExcel({
			dataGridId : "dataGridDivPe",
			exportUrl : "/pe_inventory_classify/do_pe_exports",
			exportTitle : "存货分类汇总",
			async:false
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
	}
};

function clear(){
	$('#searchForm').form('clear');
}

function loadColumn(params) {
	ajaxRequestAsync( BasePath + '/pe_inventory_classify/pe_column_list.json',params,function(data){
	if(data.CateGoryColumn){
		var columnsNew = [];
		var dataCateGory=[];
		var dataArrayNumber=[];
		dataCateGory.push({
			field:'brand_name',
			title:'品牌部',
			width:100,
			rowspan:'2',
			align:'left'
		});
		dataCateGory.push({
			field:'year',
			title:'年份',
			width:80,
			rowspan:'2',
			exportType:'number',
			align:'left'
		});
		dataCateGory.push({
			field:'month',
			title:'月份',
			width:80,
			rowspan:'2',
			align:'left'
		});
		dataCateGory.push({
			field:'years',
			title:'商品年份',
			width:80,
			rowspan:'2',
			align:'left'
		});
		dataCateGory.push({
			field:'season_name',
			title:'商品季节',
			width:80,
			rowspan:'2',
			align:'left'
		});
		$.each(data.CateGoryColumn,function(index,item){
			dataCateGory.push({
				title:item.categoryName,
				align:'center',
				colspan:2
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
		columnsNew[1] = dataArrayNumber;
		$('#dataGridDivPe').datagrid({
				url:BasePath + '/pe_inventory_classify/pe_list.json',
				queryParams:params,
				columns : columnsNew
			});
		}else{
			showSuc("暂无数据!");
			$('#dataGridDivPe').datagrid('loadData',{total:0,rows:[],footer:[]}); 
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