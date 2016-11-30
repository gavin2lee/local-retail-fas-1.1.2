
function MallStoreSaleDayReportDialog() { 
	var $this = this;	
	this.search = function() {
		var tab = $('#mainTab').tabs("getSelected");
		var tabTitle = tab.panel('options').title;
		if(tabTitle == '销售订单') {
			$('#searchForm').form('submit', {
				url:BasePath + '/mall_shopsorderd/payWaySum_columns.json',
				onSubmit:function(param) {
					param.type = $('#type').val();
					return $(this).form('validate');
				},
				success:function(data){
					var result = jQuery.parseJSON(data);
					var columns = new Array();
					columns.push({field:'tag_price',title:'牌价额',width:80,align:'right',halign:'center',exportType:'number'});
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
					params.type = $('#type').val();
					$('#dataGridDiv_order_brand').datagrid('options').queryParams= params;
					
					$('#dataGridDiv_order_brand').datagrid({
						url:BasePath + '/mall_shopsorderd/payWayOrderBrand.json',
						rownumbers:true,
						columns:[
							columns
						]
					});
				}
			});
		} else if(tabTitle == '扣率统计') {
			$('#searchForm').form('submit', {
				url:BasePath + '/mall_shopsorderd/payWaySum_columns.json',
				onSubmit:function(param) {
					param.type = $('#type').val();
					return $(this).form('validate');
				},
				success:function(data){
					var result = jQuery.parseJSON(data);
					var columns = new Array();
					columns.push({field:'tag_price',title:'牌价额',width:80,align:'right',halign:'center',exportType:'number'});
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
					
					var columnTotal = {};
					columnTotal["title"]='总计';
					columnTotal["field"]='total';
					columnTotal["width"]=80;
					columnTotal["align"]='right';
					columnTotal["halign"]='center';
					columnTotal["exportType"]='number';
					columns.push(columnTotal);
					
					var params = $('#searchForm').form('getData');
					params.type = $('#type').val();
					$('#dataGridDiv_discount').datagrid('options').queryParams= params;
					
					$('#dataGridDiv_discount').datagrid({
						url:BasePath + '/mall_shopsorderd/payWaySum_sum.json',
						rownumbers:true,
						columns:[
							columns
						]
					});
				}
			});
		} else if(tabTitle == '日统计') {
			$('#searchForm').form('submit', {
				url:BasePath + '/mall_shopsorderd/payWaySum_columns.json',
				onSubmit:function(param) {
					param.type = $('#type').val();
					return $(this).form('validate');
				},
				success:function(data){
					var result = jQuery.parseJSON(data);
					var columns = new Array();
					columns.push({field:'tag_price',title:'牌价额',width:80,align:'right',halign:'center',exportType:'number'});
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
					params.type = $('#type').val();
					$('#dataGridDiv_day').datagrid('options').queryParams= params;
					
					$('#dataGridDiv_day').datagrid({
						url:BasePath + '/mall_shopsorderd/payWayDay.json',
						rownumbers:true,
						columns:[
							columns
						]
					});
				}
			});
		} else if(tabTitle == '月统计') {
			$('#searchForm').form('submit', {
				url:BasePath + '/mall_shopsorderd/payWaySum_columns.json',
				onSubmit:function(param) {
					param.type = $('#type').val();
					return $(this).form('validate');
				},
				success:function(data){
					var result = jQuery.parseJSON(data);
					var columns = new Array();
					columns.push({field:'tag_price',title:'牌价额',width:80,align:'right',halign:'center',exportType:'number'});
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
					params.type = $('#type').val();
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
		}
	};
	
	this.exportExcel = function() {
		var tab = $('#mainTab').tabs("getSelected");
		var tabTitle = tab.panel('options').title;
		if(tabTitle == '销售订单') {
			$.fas.exportExcel({
				dataGridId : "dataGridDiv_order_brand",
				exportUrl : "/mall_shopsorderd/payWayOrderBrand_export",
				exportTitle : "销售订单支付方式明细表导出"
			});
		} else if(tabTitle == '扣率统计') {
			$.fas.exportExcel({
				dataGridId : "dataGridDiv_discount",
				exportUrl : "/mall_shopsorderd/payWaySum_sum_export",
				exportTitle : "店铺收银按活动代码、扣率统计"
			});
		} else if(tabTitle == '日统计') {
			$.fas.exportExcel({
				dataGridId : "dataGridDiv_day",
				exportUrl : "/mall_shopsorderd/payWayDay_export",
				exportTitle : "商场门店日报表导出"
			});
		} else if(tabTitle == '月统计') {
			$.fas.exportExcel({
				dataGridId : "dataGridDiv_month",
				exportUrl : "/mall_shopsorderd/payWayMonth_export",
				exportTitle : "店铺月报表导出"
			});
		}
	};
	
}

var dialog = null;
$(function() {
	
	// 初始化页签
	$('#mainTab').addTab({
		title : "销售订单",
		href : BasePath + "/bill_shop_balance_daysale_sum/mall_saleDayReport_order_brand",
		selected : true,
		onLoad : function(panel) {
			
		}
	});
	$('#mainTab').addTab({
		title : "扣率统计",
		href : BasePath + "/bill_shop_balance_daysale_sum/mall_saleDayReport_discount",
		onLoad : function(panel) {
			
		}
	});
	$('#mainTab').addTab({
		title : "日统计",
		href : BasePath + "/bill_shop_balance_daysale_sum/mall_saleDayReport_day",
		onLoad : function(panel) {
		}
	});
	$('#mainTab').addTab({
		title : "月统计",
		href : BasePath + "/bill_shop_balance_daysale_sum/mall_saleDayReport_month",
		onLoad : function(panel) {
		}
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
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
	
	$.fas.extend(MallStoreSaleDayReportDialog, FasDialogController);
	dialog = new MallStoreSaleDayReportDialog();
	dialog.init("/bill_shop_balance_daysale_sum", {
		dataGridId : "dataGridDiv_day",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "商场门店日报表导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	});

});
