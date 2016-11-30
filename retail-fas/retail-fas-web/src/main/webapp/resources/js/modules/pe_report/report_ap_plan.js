var report_ap_plan = new Object();

//查询
report_ap_plan.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		report_ap_plan.loadColumn(params);
		report_ap_plan.loadResult(params);
	}
};

report_ap_plan.loadColumn = function(params) {
	ajaxRequestAsync( BasePath + '/pe_report/report_ap_plan_column.json',params,function(data){
		if(data){
			var columnsResult = [];
			var columnsNew = [];
			columnsNew.push({
				field:'buyerName',
				title:'公司',
				width:180
			});
			columnsNew.push({
				field:'salerName',
				title:'供应商',
				width:180
			});
			columnsNew.push({
				field:'allAmount',
				title:'应付金额',
				width:100
			});
			$.each(data,function(index,item){
				var v_object = new Object();
				v_object.field = item.field;
				v_object.title = item.title;
				v_object.width = 100;
				columnsNew.push(v_object);
			});
			columnsResult[0] = columnsNew;
			report_ap_plan.selectDg.datagrid({
				url:'',
				columns : columnsResult
			});
		}
	});
};


report_ap_plan.loadResult = function(params) {
	setTimeout(function(){
		report_ap_plan.selectDg.datagrid('options').queryParams= params;
	    report_ap_plan.selectDg.datagrid('options').url= BasePath + '/pe_report/report_ap_plan_List.json';
	    report_ap_plan.selectDg.datagrid('load');
	},300);
};

//清空
report_ap_plan.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[id!=sumType]").val("");
	$('#isBalance').attr("checked","checked");
};

report_ap_plan.doExport = function(){
	var sumType = $('#sumType').val();
	if(sumType == 'day'){
		pe_util.doExport('dtlDataGrid', '/pe_report/export_data?exportType=plan', '付款计划-按天汇总');
	}else if(sumType == 'week'){
		pe_util.doExport('dtlDataGrid1', '/pe_report/export_data?exportType=plan', '付款计划-按周汇总');
	}else if(sumType == 'month'){
		pe_util.doExport('dtlDataGrid2', '/pe_report/export_data?exportType=plan', '付款计划-按月汇总');
	}
	
}

report_ap_plan.importCallBack = function(data){
	if(data.indexOf('"success":true')!=-1){
		showSuc("操作成功!");
	}else{
		showError("数据不合法,导入失败!");
	}
}

report_ap_plan.selectDg;

// 初始化
$(function(){
	report_ap_plan.selectDg = $('#dtlDataGrid');
	$('#sumType').val('day');
	$('#dtlTab').tabs({    
	    onSelect:function(title){
	    	if(title == '按天汇总'){
	    		report_ap_plan.selectDg = $('#dtlDataGrid');
	    		$('#sumType').val('day');
	    	}else if(title == '按周汇总'){
	    		report_ap_plan.selectDg = $('#dtlDataGrid1');
	    		$('#sumType').val('week');
	    	}else if(title == '按月汇总'){
	    		report_ap_plan.selectDg = $('#dtlDataGrid2');
	    		$('#sumType').val('month');
	    	}
	    }    
	}); 
	var endDate = $('#dueDateEnd').val();
	var endYear = endDate.substring(0,4);
	var endMonth = endDate.substring(5,7);
	var endDay = endDate.substring(8,10);
	var startYear;
	var startMonth;
	if(endMonth>6){
		startYear = endYear;
		startMonth = parseInt(endMonth)-6;
	}else{
		startYear = endYear-1;
		startMonth = 12 + parseInt(endMonth) - 6;
	}
	$('#dueDateStart').val(startYear+"-"+startMonth+"-"+1);
	$('#isBalance').attr("checked","checked");
});
