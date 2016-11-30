function OrderDtlSummary() {}
//创建FasController对象
var _fasController = new FasController();

// OrderDtlSummary原型继承FasController对象
OrderDtlSummary.prototype.super = _fasController;

//创建OrderDtlSummary对象
var _orderDtlSummary = new OrderDtlSummary();

//var companyNos = "";
//var startDates = "";
//var endDates = "";
//var organNos = "";
//var brandNos = "";
//var categoryNos = "";

//查询
OrderDtlSummary.prototype.search = function() {
//	companyNos = "";
//	startDates = "";
//	endDates = "";
//	organNos = "";
//	brandNos = "";
//	categoryNos = "";
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var companyNo = $("#companyNoId").val();
	if(!companyNo) {
		showWarn("请输入结算公司");
		return;
	}
	if(!startDate) {
		showWarn("请输入开始时间");
		return;
	}
	if(!endDate) {
		showWarn("请输入结束时间");
		return;
	}
//	companyNos = $("#companyNoId").val()
//	startDates = $("#startDate").val();
//	endDates = $("#endDate").val();
//	organNos = $("#organNoId").val();
//	brandNos = $("#brandNoId").val();
//	categoryNos = $("#categoryNoId").combobox('getValues');
	
	
    	var reqParam = $("#searchForm" ).form("getData");
    	var categoryNoStr = $("#categoryNoId").combobox("getValues");
    	if(categoryNoStr){
    		reqParam.categoryNo = categoryNoStr.join();
    	}
    	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	    $("#dtlDataGrid").datagrid('options').url = BasePath + "/groupon_summary/shop_member_list.json";
	    $("#dtlDataGrid").datagrid('load');
//	_fasController.search({
//		searchFormId : "searchForm",
//		dataGridId : "dtlDataGrid",
//		searchUrl : "/groupon_summary/shop_member_list.json"
//	});
};

//清空
OrderDtlSummary.prototype.clear = function() {
	$('#searchForm').form("clear");
	$("#companyNoId").val("");
	$("#organNoId").val("");
	$("#brandNoId").val("");;
};

var dtlDataGridIndex = 0;
//结算路径列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam, index) {
	/**var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var companyNo = $("#companyNo").val();
	if(!companyNo) {
		showWarn("请输入选择结算主体");
		return;
	}
	if(!startDate || !endDate) {
		showWarn("请输入开始时间和结束时间");
		return;
	}*/
	dtlDataGridIndex = index;
	var reqParam = $("#searchForm").form("getData");
	reqParam.shopNo=rowData.shopNo;
	reqParam.typeFlag=rowData.typeFlag;
	return "?"+$.param(reqParam);//shopNo=" + rowData.shopNo+"&companyNo="+companyNos+"&startDate="+startDates+"&endDate="+endDates+"&typeFlag="+rowData.typeFlag;
};

//导出
OrderDtlSummary.prototype.exportExcel = function() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var companyNo = $("#companyNoId").val();
//	var organNos = $("#organNoId").val();
//	var brandNos = $("#brandNoId").val();
//	var categoryNos = $("#categoryNoId").combobox('getValues');
	if(!companyNo) {
		showWarn("请输入结算公司");
		return;
	}
	if(!startDate) {
		showWarn("请输入开始时间");
		return;
	}
	if(!endDate) {
		showWarn("请输入结束时间");
		return;
	}
	var reqParam = $("#searchForm").form("getData");
	reqParam.businessType =3;
//	var params = $.param({businessType:3,companyNo:companyNos,startDate:startDates, endDate:endDates,organNo:organNos,categoryNo:categoryNos,brandNo:brandNos});
	_fasController.exportExcel({
		dataGridId : "dtlDataGrid",
		exportUrl : "/groupon_summary/do_fas_export_member?"+$.param(reqParam),
		exportTitle : "团购销售明细信息",
		exportType : "fix"
	});
};

//财务确认
OrderDtlSummary.prototype.financeConfirm = function() {
	var checkedRows = $("#dtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	var dtlCheckRows = [];
    if(checkedRows.length < 1){
    	dtlCheckRows = $($(".ddv")[dtlDataGridIndex]).datagrid("getChecked");
    	if(dtlCheckRows.length < 1){
	        showWarn("请选择要确认的记录!");
	        return;
    	}
    }
    var shopNoStr = '';
    if(checkedRows.length > 0){
    	$.messager.confirm("确认","确定把选中单据的店铺/客户的所有销售明细进行财务确认吗?",function(r) {
    		if (r) {
		    	$.each(checkedRows, function(index, item) {
		    		shopNoStr += item.shopNo+",";
		    	}); 
		    	var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
			    var params = {companyNo:$("#companyNoId").val(),shopNos:shopNoStr,startDate:startDate, endDate:endDate,datas : JSON.stringify(dtlCheckRows)};
			    ajaxRequest(BasePath + "/groupon_summary/finance_confirm", params, function(result){
			    	if(result) {
			            showSuc("财务确认成功!");
			            _orderDtlSummary.search();
			        } else {
			            showError("财务确认失败,请联系管理员!");
			        }
			    });
    		}
    	});
    }else{
		for(var i = 0; i < dtlCheckRows.length; i++) {
			var item = dtlCheckRows[i];
			if(item.financeConfirmFlag == 1){
				showWarn(item.orderNo+"订单已经确认,不能再次确认!");
				return false;
			}
		}
    }
    if(dtlCheckRows != ''){
	    var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
	    var params = {companyNo:$("#companyNoId").val(),shopNos:shopNoStr,startDate:startDate, endDate:endDate,datas : JSON.stringify(dtlCheckRows)};
	    ajaxRequest(BasePath + "/groupon_summary/finance_confirm", params, function(result){
	    	if(result) {
	            showSuc("财务确认成功!");
	            _orderDtlSummary.search();
	        } else {
	            showError("财务确认失败,请联系管理员!");
	        }
	    });
    }
};

//财务反确认
OrderDtlSummary.prototype.financeAntiConfirm = function() {
	var checkedRows = $("#dtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	var dtlCheckRows = [];
    if(checkedRows.length < 1){
    	dtlCheckRows = $($(".ddv")[dtlDataGridIndex]).datagrid("getChecked");
    	if(dtlCheckRows.length < 1){
	        showWarn("请选择要反确认的记录!");
	        return;
    	}
    }
    var shopNoStr = '';
    if(checkedRows.length > 0){
    	$.messager.confirm("确认","确定把选中单据的店铺/客户下的所有销售明细进行反确认吗?",function(r) {
    		if (r) {
		    	$.each(checkedRows, function(index, item) {
		    		shopNoStr += item.shopNo+",";
		    	}); 
		    	var startDate = $("#startDate").val();
		 		var endDate = $("#endDate").val();
		 	    var params = {companyNo:$("#companyNoId").val(),shopNos:shopNoStr,startDate:startDate, endDate:endDate,datas : JSON.stringify(dtlCheckRows)};
			    ajaxRequest(BasePath + "/groupon_summary/finance_anti_confirm", params, function(result){
			    	if(result) {
			            showSuc("财务反确认成功!");
			            _orderDtlSummary.search();
			        } else {
			            showError("财务反确认失败,请联系管理员!");
			        }
			    });
    		}
    	});
    }else{
		for(var i = 0; i < dtlCheckRows.length; i++) {
			var item = dtlCheckRows[i];
			if(item.financeConfirmFlag == 0){
				showWarn(item.orderNo+"单据未确认,不能反确认!");
				return false;
			}
			if(item.invoiceNo != '' && item.invoiceNo){
				showWarn(item.orderNo+"已开票，不能反确认!");
				return false;
			}
		}
    }
    
    if(dtlCheckRows != ''){
    	var startDate = $("#startDate").val();
 		var endDate = $("#endDate").val();
 	    var params = {companyNo:$("#companyNoId").val(),shopNos:shopNoStr,startDate:startDate, endDate:endDate,datas : JSON.stringify(dtlCheckRows)};
	    ajaxRequest(BasePath + "/groupon_summary/finance_anti_confirm", params, function(result){
	    	if(result) {
	            showSuc("财务反确认成功!");
	            _orderDtlSummary.search();
	        } else {
	            showError("财务反确认失败,请联系管理员!");
	        }
	    });
    }
};


// 初始化
$(function(){
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	$("#startDate").datebox("setValue", strDate + "-1"); 
	strDate += "-" + currTime.getDate();
	$("#endDate").datebox("setValue", strDate); 
//	_orderDtlSummary.initinvoiceType();
//	$('#invoiceType').combobox('setValue', '0');
	
	$('#confirmStatus').combobox({
		width:120,
		data : confirmStatus,
		valueField : 'value',
		textField : 'text',
		editable : false,
	});  
});

//var invoiceTypeformatter =[{'value':'0', 'text': '普通发票'},{'value':'1', 'text':'增值票'}];
//
//_orderDtlSummary.initinvoiceType=function(){
//	$('#invoiceType').combobox({
//		width:160,
//		data : invoiceTypeformatter,
//		valueField : 'value',
//		textField : 'text',
//		editable : false
//	});  
//};

//_orderDtlSummary.toCreateInvoiceReg = function() {
//    var checkedRows = $("#dtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
//	if(checkedRows.length < 1){
//		showWarn("请选择要登记普通发票的数据信息!");
//		return;
//	}
//	var billTypeFlag = false;
//	var invoiceNoFlag = false;
//	var amountTotal = 0;
//	$.each(checkedRows, function(index, item){
//		if(item.invoiceNo != '' && item.invoiceNo != null){
//			showWarn("单据编号为："+item.billNo+"，已经登记过发票！发票号为："+item.invoiceNo);
//			invoiceNoFlag = true;
//		}
//		if(item.billTypeName == '团购出库单'){
//			billTypeFlag = true;
//		}
//		if(item.financeConfirmFlag == 0){
//			showWarn(item.billNo+"单据未确认,不能登记发票!");
//			invoiceNoFlag = true;
//		}
//		amountTotal = amountTotal + item.otherAmount;
//	});
//	if(invoiceNoFlag){
//		return;
//	}
//	if(billTypeFlag){
//		showWarn("团购出库单无需普通发票登记操作!");
//		return;
//	}
//    var _this = this;
//    ygDialog({
//        title : '请选择',
//        target : $('#myFormPanel'),
//        width : 300,
//        height : 180,
//        buttons : [{
//            text : '确定',
//            iconCls : 'icon-confirm',
//            handler : function(dialog) {
//            	var param = new Object();
//            	param.salerNo = $("#companyNo").val(); 
//        		param.salerName = $("#companyNameId").combogrid('getValue'); 
//        		param.buyerNo = $("#buyerNo").val();
//        		param.buyerName = $("#buyerName").val();
//        		param.invoiceType = $('#invoiceType').combobox('getValue');
//        		param.balanceType = '8';
//        		param.amount = amountTotal;
//            	invoiceApplyBalance.invoiceRegisters("dtlDataGrid",param,checkedRows);
//            	$("#showDataForm").form("clear");
//            	dialog.close();
//            }
//        }]
//    });
//};

_orderDtlSummary.formatterDate = function(value){
	return new Date(value).format("yyyy-MM-dd");
}

var confirmStatus =[{'value':'', 'text': '全部'},{'value':'1', 'text': '已确认'},{'value':'0', 'text':'未确认'}];

_orderDtlSummary.confirmStatus= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < confirmStatus.length; i++) {
        if (confirmStatus[i].value == value) {
            return confirmStatus[i].text;
        }
    }
};

