
function MallShopOrderDialog() { 
	var $this = this;
	
	this.search = function() {
		var tab = $('#mainTab').tabs("getSelected");
		var tabIndex = $('#mainTab').tabs('getTabIndex',tab);
		//明细单据
		if(tabIndex == 0) {
			$.fas.search({
				searchFormId : $this.options.searchFormId,
				dataGridId : "shopSaleOrderDataGrid",
				searchUrl : "/mall_shopsorderd/list.json",
			});
		}
		//扣率汇总
		else if(tabIndex == 1) {
			
			$('#searchForm').form('submit', {
				url:BasePath + '/mall_shopsorderd/discount_columns.json',
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
					$('#discountSumDataGrid').datagrid('options').queryParams= params;
					
					$('#discountSumDataGrid').datagrid({
						url:BasePath + '/mall_shopsorderd/discount_sum.json',
						rownumbers:true,
						columns:[
							columns
						]
					});
				}
			});
			
		} 
	};
	
	this.exportExcel = function() {
		var tab = $('#mainTab').tabs("getSelected");
		var tabIndex = $('#mainTab').tabs('getTabIndex',tab);
		//明细单据
		if(tabIndex == 0) {
			$.fas.exportExcel({
		    	dataGridId : "shopSaleOrderDataGrid",
		    	exportUrl : "/mall_shopsorderd/do_fas_exports",
		    	exportTitle : "销售明细导出",
		    	exportType : "common"
		    });
		}
		//扣率汇总
		else if(tabIndex == 1) {
			$.fas.exportExcel({
		    	dataGridId : "discountSumDataGrid",
		    	exportUrl : "/mall_shopsorderd/discount_sum_export",
		    	exportTitle : "销售收入（按扣率统计）导出",
		    	exportType : "common"
		    });
		}
		

	}
}

function initCombobox(){
	var OrderBillTypeArray = 
		[
		 {'value' : '-1' , 'text' : '请选择...'},
		 {'value' : '0' , 'text' : '正常销售'},
		 {'value' : '1' , 'text' : '换货'},
		 {'value' : '2' , 'text' : '退货'}
       ];
	
	$('#orderBillType').combobox({
		data : OrderBillTypeArray,
		valueField : 'value',
		textField : 'text'
	});
}

var dialog = null;
$(function() {
	initCombobox();
	$.fas.extend(MallShopOrderDialog, FasDialogController);
	dialog = new MallShopOrderDialog();
	dialog.init("/mall_shopsorderd", {
		dataGridId : "shopSaleOrderDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm"//,
//		exportTitle : "销售明细导出",
//		exportUrl : "/do_fas_export",
//		exportType : "common"
	});
	
});