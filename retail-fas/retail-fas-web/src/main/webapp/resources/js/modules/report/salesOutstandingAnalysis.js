var salesOutstandingAnalysis = new Object();
var $this = this;
//查询
salesOutstandingAnalysis.search = function() {
	if($('#searchForm').form('validate')) {
		var outDateStart = $('#outDateStart').val();
		var outDateEnd = $('#outDateEnd').val();
		var startTime = new Date(Date.parse(outDateStart.replace(/-/g,   "/"))).getTime();     
	    var endTime = new Date(Date.parse(outDateEnd.replace(/-/g,   "/"))).getTime();     
	    var dates = Math.abs((startTime - endTime))/(1000*60*60*24); 
	    if(dates > 31){
	    	showWarn("销售日期的选择范围不能超过一个月.");
	    	return;
	    }
		var zoneNo = $("#zoneNo").combobox('getValues'); //地区
		var businessType = $("#businessType").combobox("getValues");
//		if(!zoneNo){
//			showWarn("地区不能为空, 请选择!");
//			return;
//		}
//		if(!outDateStart) {
//			showWarn("销售日期开始时间为空, 请选择!");
//			return;
//		}
//		if(!outDateEnd){
//			showWarn("销售日期结束时间为空, 请选择!");
//			return;
//		}
		var params = $('#searchForm').form('getData');
		
		var categoryNoStr = $("#categoryNo").combobox("getValues");
		if(categoryNoStr){
			params.categoryNo = categoryNoStr.join();
	    }
		if(zoneNo){
			params.zoneNo = zoneNo.join();
		}
		if(businessType){
			params.businessType = businessType.join();
		}
		var url = BasePath + '/bill_sales_outstanding_analysis/list.json';
		$('#salesOutstandingAnalysisDataGrid').datagrid('options').queryParams= params;
	    $('#salesOutstandingAnalysisDataGrid').datagrid('options').url= url;
	    $('#salesOutstandingAnalysisDataGrid').datagrid('load');
	}
};

//查询汇总
salesOutstandingAnalysis.searchCollect = function() {
	if($("#searchForm1").form('validate')) {
//		var zoneNo = $("#zoneNo_").combobox('getValue'); //地区
		var businessType = $("#businessType1").combobox("getValues");
		var params = $('#searchForm1').form('getData');
//		params.zoneNo=zoneNo;
		if(businessType){
			params.businessType = businessType.join();
	    }
		var categoryNoStr = $("#categoryNo1").combobox("getValues");
		if(categoryNoStr){
			params.categoryNo = categoryNoStr.join();
	    }
		var zoneNo = $("#zoneNo_").combobox('getValues'); //地区
		if(zoneNo){
			params.zoneNo = zoneNo.join();
	    }
		
		var url = BasePath + '/bill_sales_outstanding_analysis/findCollectList';
	    $('#salesOutstandingCollectDataGrid').datagrid('options').queryParams= params;
	    $('#salesOutstandingCollectDataGrid').datagrid('options').url= url;
	    $('#salesOutstandingCollectDataGrid').datagrid('load');
	}
};


//清空
salesOutstandingAnalysis.clearCollect = function() {
	$('#searchForm1').form("clear");
	$('#searchForm1').find("input[name!=type]").val('');
	$('#outDateStart1').datebox('enable');
	$('#outDateEnd1').datebox('enable');
//	salesOutstandingAnalysis.initDate(); 
};


//清空
salesOutstandingAnalysis.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	$('#outDateStart').datebox('enable');
	$('#outDateEnd').datebox('enable');
//	salesOutstandingAnalysis.initDate(); 
};

salesOutstandingAnalysis.initCombobox = function(){
	var businessTypeArray = 
		[
		 {'value' : '1' , 'text' : '门店'},
		 {'value' : '2' , 'text' : '批发'},
		 {'value' : '3' , 'text' : '调货'},
		 {'value' : '4' , 'text' : '内购'},
		 {'value' : '99' , 'text' : '其他'}
       ];
//	var discountCodeArray = 
//		[
//		 {'value' : '1' , 'text' : 'A'},
//		 {'value' : '2' , 'text' : 'B'},
//		 {'value' : '3' , 'text' : 'C'}
//       ];
	
	var actionCategoryArray = 
		[
         {'value' : '0' , 'text' : '0-全部'},
		 {'value' : '1' , 'text' : '1-打折'},
		 {'value' : '2' , 'text' : '2-买减'},
		 {'value' : '3' , 'text' : '3-买送换购'},
		 {'value' : '4' , 'text' : '4-会员'},
		 {'value' : '5' , 'text' : '5-一口价'},
		 {'value' : '6' , 'text' : '6-调价'},
		 {'value' : '7' , 'text' : '7-送券'},
		 {'value' : '8' , 'text' : '8-跨柜累计'},
		 {'value' : '9' , 'text' : '8-其他'},
       ];
	
	$('#businessType').combobox({
		data : businessTypeArray,
		valueField : 'value',
		textField : 'text'
	});
//	$('#discountCode').combobox({
//		data : discountCodeArray,
//		valueField : 'value',
//		textField : 'text'
//	});
	$('#actionCategory').combobox({
		data : actionCategoryArray,
		valueField : 'value',
		textField : 'text'
	});
};


//导出
salesOutstandingAnalysis.exportExcel = function() {
	var senda = $("#senda").val();
	if(senda == "true"){
		$.fas.exportExcel({
			dataGridId : "salesOutstandingAnalysisDataGrid",
			exportUrl : "/bill_sales_outstanding_analysis/async_exports?exportType=1",
			exportTitle : "销售回款分析明细",
			async:true
		});
	}else{
		exportSalesOutstandingReport();
	}
};

//导出
salesOutstandingAnalysis.exportExcelCollect = function() {
	var senda = $("#senda").val();
	if(senda == "true"){
		$.fas.exportExcel({
			dataGridId : "salesOutstandingCollectDataGrid",
			exportUrl : "/bill_sales_outstanding_analysis/do_sale_export?exportType=2",
			exportTitle : "销售回款分析汇总"
		});
	}else{
		exportSalesOutstandingCollectReport();
	}
};


// 初始化
$(function(){
	salesOutstandingAnalysis.initCombobox();
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1);
	$('#outDateStart').datebox('setValue', year + '-' + month + '-01');
	var  day = new Date(year,month,0);
	$('#outDateEnd').datebox('setValue', year + '-' + month + '-' + day.getDate());
	$('#mainTab').tabs('add', {
		title : '销售回款汇总',
		selected : false,
		closable : false,
		href : BasePath + "/bill_sales_outstanding_analysis/collect_list?sendas="+$("#senda").val(),
		onLoad : function(panel) {
			
			var businessTypeArray = 
				[
				 {'value' : '1' , 'text' : '门店'},
				 {'value' : '2' , 'text' : '批发'},
				 {'value' : '3' , 'text' : '调货'},
				 {'value' : '4' , 'text' : '内购'},
				 {'value' : '99' , 'text' : '其他'}
		       ];
			$('#businessType1').combobox({
				data : businessTypeArray,
				valueField : 'value',
				textField : 'text'
			});
			$('#outDateStart1').datebox('setValue', year + '-' + month + '-01');
			$('#outDateEnd1').datebox('setValue', year + '-' + month + '-' + day.getDate());
			salesOutstandingAnalysis.collectChecked();
		}
	});
	
	var propertiesTypeData = [{'value' : '1','text' : '满送'}, 
	      					{'value' : '2','text' : '满减'},
	      					{'value' : '3','text' : '折扣'},
	                        {'value' : '4','text' : '其他'}];
	$('#properties').combobox({
		data : propertiesTypeData,
		valueField : 'value',
		textField : 'text',
		editable : false
	});
	
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	var  organTypeNo =  currentUser.organTypeNo;
	$("#organTypeNo").val(organTypeNo);
});


salesOutstandingAnalysis.collectChecked = function (){
	$('#isCheckShopNo').bind("click", function() {
		if(!$('#isCheckShopNo').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"shopNo");
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"shortName");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"shopNo");
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"shortName");
		}
    });
	$('#isCheckRate').bind("click", function() {
		if(!$('#isCheckRate').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"rate");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"rate");
		}
    });
	$('#isCheckRateCode').bind("click", function() {
		if(!$('#isCheckRateCode').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"rateCode");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"rateCode");
		}
    });
	$('#isCheckGender').bind("click", function() {
		if(!$('#isCheckGender').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"gender");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"gender");
		}
    });
	$('#isCheckYear').bind("click", function() {
		if(!$('#isCheckYear').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"year");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"year");
		}
    });
	$('#isCheckSellSeason').bind("click", function() {
		if(!$('#isCheckSellSeason').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"sellSeason");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"sellSeason");
		}
    });
	$('#isCheckBrandUnit').bind("click", function() {
		if(!$('#isCheckBrandUnit').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"brandUnitName");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"brandUnitName");
		}
    });
	$('#isCheckCategory').bind("click", function() {
		if(!$('#isCheckCategory').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"categoryName");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"categoryName");
		}
    });
	$('#isCheckRootCategory').bind("click", function() {
		if(!$('#isCheckRootCategory').is(':checked')){
			$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"rootCategoryName");
		}else{
			$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"rootCategoryName");
		}
    });
	
	var  organTypeNo =  currentUser.organTypeNo;
	if("U010105" == organTypeNo){
		$('#isStyleNo').bind("click", function() {
			if(!$('#isStyleNo').is(':checked')){
				$("#salesOutstandingCollectDataGrid").datagrid('hideColumn',"styleNo");
			}else{
				$("#salesOutstandingCollectDataGrid").datagrid('showColumn',"styleNo");
			}
	    });
	}
};
