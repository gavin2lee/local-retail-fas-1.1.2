function OrderDtlSummary() {}

// 设置行编辑的参数
var setting = {
	datagridId : "dataGridDiv",
	primaryKey : "id",
	initRow : function(){
		return {};
	}
};

// 创建FasController对象
var _fasController = new FasController();

// OrderDtlSummary原型继承FasController对象
OrderDtlSummary.prototype.super = _fasController;

// 创建OrderDtlSummary对象
var _orderDtlSummary = new OrderDtlSummary();

var startDateOld = null;
var endDateOld = null;

var companyNos = "";
var startDates = "";
var endDates = "";

// 查询
OrderDtlSummary.prototype.search = function() {
	companyNos = "";
	startDates = "";
	endDates = "";
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	//var companyName = $("#companyNameId").val();
	var companyName = $("#companyNameId").combobox("getValue");
	if(!companyName) {
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
	//companyNos = $("#companyNo").val();
	companyNos = $("#companyNameId").combobox("getValue");
	startDates = $("#startDate").val();
	endDates = $("#endDate").val();
	_fasController.search({
		searchFormId : "searchForm",
		dataGridId : "dataGridDiv",
		searchUrl : "/member_order_summary/order_member_list.json?businessType=4"//operate_list.json?businessType=4"
	});
};

OrderDtlSummary.prototype.summarySearch = function() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var companyName = $("#companyNameId").combobox("getValue");
	//var companyName = $("#companyName").val();
	if(!companyName) {
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
	_fasController.search({
		searchFormId : "searchForm",
		dataGridId : "dataGridDiv",
		searchUrl : "/member_order_summary/shop_member_list.json?businessType=4"//operate_list.json?businessType=4"
	});
};

// 清空
OrderDtlSummary.prototype.clear = function() {
	//清空前保存时间，为了进行账务确认
	startDateOld = $("#startDate").val();
	endDateOld = $("#endDate").val();
	_fasController.clear("searchForm");
};

// 导出
OrderDtlSummary.prototype.exportExcel = function() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	//var companyNo = $("#companyNo").val();
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
	
	_fasController.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/member_order_summary/do_fas_export_member.json?businessType=4&type=1&companyNo="+companyNo+"&startDate="+startDate+"&endDate="+endDate,
		exportTitle : "员购内购明细信息",
		exportType : "fix"
	});
};

//汇总导出
OrderDtlSummary.prototype.exportShopExcel = function(){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	//var companyNo = $("#companyNo").val();
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
	
	_fasController.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/member_order_summary/do_fas_export_member.json?businessType=4&type=2&companyNo="+companyNo+"&startDate="+startDate+"&endDate="+endDate,
		exportTitle : "员购内购汇总信息",
		exportType : "fix"
	});
}

//普通发票登记
OrderDtlSummary.prototype.registor = function(){
   	 var checkedRows = $("#"+dataGridDi).datagrid("getChecked");// 获取所有勾选checkbox的行
   		if(checkedRows.length < 1){
   			showWarn("请选择要登记普通发票的数据信息!");
   			return;
   		}
}

// 转换页面数据显示
OrderDtlSummary.prototype.formatFinanceConfirm = function(value) {
	if(value == '1') {
		return "全部确认";
	}else if(value == '0'){
		return "末确认";
	}
	return "部分确认";
};

//结算路径列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	/**var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var companyNo = $("#companyNo").val();*/
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "orderNo=" + rowData.billNo+"&companyNo="+companyNos+"&startDate="+startDates+"&endDate="+endDates;//orderNo;//shopNo=" + rowData.shopNo + "&startDate="
			//+ rowData.startDate + "&endDate=" + rowData.endDate;
};

// 财务确认
OrderDtlSummary.prototype.financeConfirm = function() {
	if(_orderDtlSummary.endEdit()) {
		var checkedRows = $("#dataGridDiv").datagrid("getSelections");// 获取所有勾选checkbox的行
	    if(checkedRows.length < 1){
	        showWarn("请选择要确认的记录!");
	        return;
	    }
	    var dataList = [];
	    $.each(checkedRows, function(index, item) {
			var obj = new Object();
			obj.shopNo = item.shopNo;
			obj.invoiceNo = item.invoiceNo;
			obj.invoiceAmount = item.invoiceAmount;
			obj.companyNo = $("#companyNo").val();
			obj.startDate = $("#startDate").val() == null ? startDateOld : $("#startDate").val();//item.startDate;
			obj.endDate = $("#endDate").val() == null ? endDateOld : $("#endDate").val();//item.endDate;
			dataList.push(obj);
		});     
	    var params = {datas : JSON.stringify(dataList)};
	    ajaxRequest(BasePath + "/member_order_summary/finance_confirm", params, function(result){
	    	if(result) {
	            showSuc("财务确认成功!");
	            _orderDtlSummary.summarySearch();
	        } else {
	            showError("财务确认失败,请联系管理员!");
	        }
	    });
	}
};

//财务反确认
OrderDtlSummary.prototype.financeAntiConfirm = function() {
	var checkedRows = $("#dataGridDiv").datagrid("getSelections");// 获取所有勾选checkbox的行
    if(checkedRows.length < 1){
        showWarn("请选择要反确认的记录!");
        return;
    }
    startDateOld = $("#startDate").val();
	endDateOld = $("#endDate").val();
    
    var dataList = [];
    $.each(checkedRows, function(index, item) {
		var obj = new Object();
		obj.shopNo = item.shopNo;
		obj.id = item.orderId;
		obj.startDate = $("#startDate").val() == null ? startDateOld : $("#startDate").val();//item.startDate;
		obj.endDate = $("#endDate").val() == null ? endDateOld : $("#endDate").val();//item.endDate;
		dataList.push(obj);
	});     
    var params = {datas : JSON.stringify(dataList)};
    ajaxRequest(BasePath + "/member_order_summary/finance_anti_confirm", params, function(result){
    	if(result) {
            showSuc("财务反确认成功!");
            _orderDtlSummary.summarySearch();
        } else {
            showError("财务反确认失败,请联系管理员!");
        }
    });
};

//修改
_orderDtlSummary.edit = function(rowIndex, rowData) {
	if("1" == rowData.financeConfirmFlag){
		showInfo('不能操作已确认的明细!');
		return ;
	}
	if(_orderDtlSummary.endEdit()){
		$('#dataGridDiv').datagrid('beginEdit',rowIndex);
	}
};

//结束编辑
_orderDtlSummary.endEdit = function() {
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#dataGridDiv').datagrid('validateRow',editRowIndex)){
			  $('#dataGridDiv').datagrid('endEdit',editRowIndex);
			  return true;
		}
		return false;
	}
	return true;
};

$(function(){
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	$("#startDate").datebox("setValue", strDate + "-1"); 
	strDate += "-" + currTime.getDate();
	$("#endDate").datebox("setValue", strDate); 
});


_orderDtlSummary.toSelectBuyerName = function() {
    var checkedRows = $("#dataGridDiv").datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要登记普通发票的数据信息!");
		return;
	}
    var _this = this;
    ygDialog({
        title : '请选择客户',
        target : $('#myFormPanel'),
        width : 300,
        height : 150,
        buttons : [{
            text : '确定',
            iconCls : 'icon-confirm',
            handler : function(dialog) {
            	var param = new Object();
            	param.salerNo = $("#companyNo").val(); 
        		param.salerName = $("#companyNameId").combogrid('getValue'); 
        		param.buyerNo = $("#buyerNo").val();
        		param.buyerName = $("#buyerName").val();
        		param.invoiceType = 0;
        		param.balanceType = '9';
        		var amountTotal = 0;
        		var invoiceNoFlag = false;
        		$.each(checkedRows, function(index, item){
        			if(item.invoiceNo != '' && item.invoiceNo != null){
        				showWarn("单据编号为："+item.billNo+"，已经登记过发票！发票号为："+item.invoiceNo);
        				invoiceNoFlag = true;
        			}
        			amountTotal = amountTotal + item.settleAmount;
        		});
        		if(invoiceNoFlag){
        			return;
        		}
        		param.amount = amountTotal;
            	invoiceApplyBalance.invoiceRegisters("dataGridDiv",param,checkedRows);
            	$("#showDataForm").form("clear");
            	dialog.close();
            }
        }]
    });
};