
function SaleMonthReport() { 
	this.search = function() {
		$('#searchForm').form('submit', {
			url:BasePath + '/mall_shopsorderd/payWaySum_columns.json',
			success:function(data){
				var result = jQuery.parseJSON(data);
				var columns = new Array();
				$.each(result.headers, function(i, field){
					var column={};
					column["title"]=i;
					column["field"]=field;
					column["width"]=80;
					column["align"]='right';
					column["halign"]='center';
					column["exportType"]='number';
					//column["formatter"]=function(value,row,index){return value.toFixed(3);};
					columns.push(column);
				});
				
				var params = $('#searchForm').form('getData');
				$('#dataGridDiv_month').datagrid('options').queryParams= params;
				
				$('#dataGridDiv_month').datagrid({
					url:BasePath + '/mall_shopsorderd/payWayMonth.json',
					rownumbers:true,
					columns:[
						columns
					]
				});
			}
		});
	};
	
	this.exportExcel = function() {
		$.fas.exportExcel({
			dataGridId : "dataGridDiv_month",
			exportUrl : "/mall_shopsorderd/payWayMonth_export",
			exportTitle : "店铺月报表导出"
		});
	};
}

var dialog = null;
$(function() {
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
	
	$.fas.extend(SaleMonthReport, FasDialogController);
	dialog = new SaleMonthReport();
	dialog.init("/self_shop_month_report", {
		dataGridId : "dataGridDiv_month",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "店铺月报表导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	});
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNoTemp").val(value);
        }
    });
	
	/*$('#dataGridDiv').datagrid({
		onLoadSuccess : function(data){
			var rows = $('#dataGridDiv').datagrid('getRows');
			//隐藏要判断的列
			$('#dataGridDiv').datagrid('hideColumn', 'p01');
			$('#dataGridDiv').datagrid('hideColumn', 'p04');
			$('#dataGridDiv').datagrid('hideColumn', 'p03');
			$('#dataGridDiv').datagrid('hideColumn', 'p05');
			$('#dataGridDiv').datagrid('hideColumn', 'p06');
			$('#dataGridDiv').datagrid('hideColumn', 'p08');
			$('#dataGridDiv').datagrid('hideColumn', 'p09');
			$('#dataGridDiv').datagrid('hideColumn', 'p10');
			$('#dataGridDiv').datagrid('hideColumn', 'p11');
			$('#dataGridDiv').datagrid('hideColumn', 'p12');
			$('#dataGridDiv').datagrid('hideColumn', 'p13');
			$('#dataGridDiv').datagrid('hideColumn', 'p14');
			$('#dataGridDiv').datagrid('hideColumn', 'p15');
			$('#dataGridDiv').datagrid('hideColumn', 'p16');
			$('#dataGridDiv').datagrid('hideColumn', 'p17');
			$('#dataGridDiv').datagrid('hideColumn', 'p18');
			$('#dataGridDiv').datagrid('hideColumn', 'p19');
			$('#dataGridDiv').datagrid('hideColumn', 'p20');
			$('#dataGridDiv').datagrid('hideColumn', 'p21');
			$('#dataGridDiv').datagrid('hideColumn', 'p22');
			$('#dataGridDiv').datagrid('hideColumn', 'p23');
			$('#dataGridDiv').datagrid('hideColumn', 'p24');
			$('#dataGridDiv').datagrid('hideColumn', 'p25');
			$('#dataGridDiv').datagrid('hideColumn', 'p26');
			$('#dataGridDiv').datagrid('hideColumn', 'p27');
			$('#dataGridDiv').datagrid('hideColumn', 'p28');
			$('#dataGridDiv').datagrid('hideColumn', 'p29');
			$('#dataGridDiv').datagrid('hideColumn', 'p30');
			$('#dataGridDiv').datagrid('hideColumn', 'p31');
			$('#dataGridDiv').datagrid('hideColumn', 'p32');
			$('#dataGridDiv').datagrid('hideColumn', 'p35');
			$('#dataGridDiv').datagrid('hideColumn', 'p999');
			//判断是否显示
			$.each(rows, function(index, item) {
				if (item.p01 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p01');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p01')).attr('notexport',false);
				}
				if (item.p04 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p04');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p04')).attr('notexport',false);
				}
				if (item.p03 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p03');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p03')).attr('notexport',false);
				}
				if (item.p05 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p05');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p05')).attr('notexport',false);
				}
				if (item.p06 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p06');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p06')).attr('notexport',false);
				}
				if (item.p08 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p08');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p08')).attr('notexport',false);
				}
				if (item.p09 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p09');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p09')).attr('notexport',false);
				}
				if (item.p10 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p10');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p10')).attr('notexport',false);
				}
				if (item.p11 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p11');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p11')).attr('notexport',false);
				}
				if (item.p12 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p12');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p12')).attr('notexport',false);
				}
				if (item.p13 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p13');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p13')).attr('notexport',false);
				}
				if (item.p14 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p14');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p14')).attr('notexport',false);
				}
				if (item.p15 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p15');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p15')).attr('notexport',false);
				}
				if (item.p16 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p16');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p16')).attr('notexport',false);
				}
				if (item.p17 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p17');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p17')).attr('notexport',false);
				}
				if (item.p18 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p18');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p18')).attr('notexport',false);
				}
				if (item.p19 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p19');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p19')).attr('notexport',false);
				}
				if (item.p20 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p20');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p20')).attr('notexport',false);
				}
				if (item.p21 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p21');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p21')).attr('notexport',false);
				}
				if (item.p22 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p22');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p22')).attr('notexport',false);
				}
				if (item.p23 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p23');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p23')).attr('notexport',false);
				}
				if (item.p24 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p24');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p24')).attr('notexport',false);
				}
				if (item.p25 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p25');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p25')).attr('notexport',false);
				}
				if (item.p26 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p26');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p26')).attr('notexport',false);
				}
				if (item.p27 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p27');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p27')).attr('notexport',false);
				}
				if (item.p28 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p28');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p28')).attr('notexport',false);
				}
				if (item.p29 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p29');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p29')).attr('notexport',false);
				}
				if (item.p30 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p30');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p30')).attr('notexport',false);
				}
				if (item.p31 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p31');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p31')).attr('notexport',false);
				}
				if (item.p32 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p32');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p32')).attr('notexport',false);
				}
				if (item.p35 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p35');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p35')).attr('notexport',false);
				}
				if (item.p999 != 0) {
					$('#dataGridDiv').datagrid('showColumn', 'p999');
					$($('#dataGridDiv').datagrid('getColumnOption', 'p999')).attr('notexport',false);
				}
			});
		}
	});*/

});
