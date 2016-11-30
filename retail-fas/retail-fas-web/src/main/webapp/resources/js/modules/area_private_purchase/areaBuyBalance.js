var areaBuyBalance= new BillObj({
	modulePath : BasePath + '/area_private_purchase_balance',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '6',
	mainDgId : 'mainDataGrid'
}, [ {
	id : 'transferInDtl',
	title : '入库明细',
	tabUrl : BasePath + '/area_private_purchase_balance/transferInTab.htm',
	listUrl : BasePath + '/area_private_purchase_balance/transferInList.json',
	queryParams : {}
}, {
	id : 'otherDeductionDtl',
	title : '其他扣项',
	tabUrl : BasePath + '/area_private_purchase_balance/otherDeduction.htm',
	listUrl : BasePath + '/other_deduction/list.json',
	queryParams : {}
}, {
	id : 'invoiceInfoDtl',
	title : '发票信息',
	tabUrl : BasePath + '/area_private_purchase_balance/invoiceInfo.htm',
	listUrl : BasePath + '/bill_invoice/query_balance_invoice.json',
	queryParams : {}
}, {
	id : 'paymentInfoDtl',
	title : '请款信息',
	tabUrl : BasePath + '/area_private_purchase_balance/paymentInfo.htm',
	listUrl : BasePath + '/bill_ask_payment/list.json',
	queryParams : {}
}]);

/**
 * 初始化
 */
$(function() {
	areaBuyBalance.setDtlTabs();
	areaBuyBalance.addMainTab();
	areaBuyBalance.add();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	areaBuyBalance.initSettleMethod();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/area_private_purchase_balance/list.json',{billNo:billNoMenu},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					areaBuyBalance.loadDetail(0, obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	//加载预警列表
	loadWarnMessage();
});

/**
 * 加载结算方式
 */
var datas=[];
areaBuyBalance.initSettleMethod=function(){
	var url=BasePath+'/common_util/getSettleMethod';
	ajaxRequestAsync(url, null, function(data) {
		$.each(data, function(index, item) {
			datas[item.code] = item.name;
		});
	});
};

/**
 * 新增初始化
 */
areaBuyBalance.initAdd=function(){
	$('#billNo,#entryAmountId,#deductionAmountId,#statusNameId,#balanceAmount').attr("readonly",true).addClass("readonly");
	$('#id,#statusId').val("");
	$("#balanceAmount").numberbox("clear");
	$("#supplierNameId,#buyerNameId,#brandUnitNameId").combogrid("enable");
	$("#balanceDateId").datebox('setValue',new Date().format("yyyy-MM-dd"));
};

/**
 * 清空
 */
areaBuyBalance.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCond,#buyerNoCond,#brandUnitNoCond').val("");
};

/**
 * 批量新增之前的处理
 */
areaBuyBalance.beforeBatchAdd=function(){
	$("#balanceDateBatch").datebox('setValue',new Date().format("yyyy-MM-dd"));
};

/**
 * 保存回调函数
 */
areaBuyBalance.afterSaved = function(dgId, url, params, jsonObj) {
	$('#billName,#remarkId').removeClass('readonly').removeAttr("readonly");
	var billNo = $('#billNo').val();
	params.balanceNo = billNo;
	$('#' + dgId).datagrid('options').queryParams = params;
	$('#' + dgId).datagrid('options').url = url;
	$('#' + dgId).datagrid('load');
};

/**
 * 设置表单样式
 */
areaBuyBalance.setFormClass=function(){
	$('#billNo,#entryAmountId,#deductionAmountId,#statusNameId,#supplierNameId,#buyerNameId,#balanceAmount').attr("readonly", true).addClass("readonly");
	$("#supplierNameId,#buyerNameId,#brandUnitNameId").combogrid("disable");
	$('#currencyNameId').combobox('enable');
};

areaBuyBalance.afterSetClass=function(rowData){
	$("#supplierNameId,#buyerNameId,#brandUnitNameId").combogrid("disable");
	$('#statusNameId').val(areaBuyBalance.statusFormat(rowData.status));
	$('#currencyNameId').combobox('setValue', rowData.currency);
};

/**
 * 扣项调整之前的判断
 */
areaBuyBalance.adjustJudge = function(){
	if ($('#statusId').val() == "") {
		return '没有可操作的单据!';
	}
	if ($('#statusId').val() == 5) {
		return '已请款,不允许再做调整!';
	} else if ($('#statusId').val() == 2) {
		return '财务已经确认,不允许再做调整!';
	}
	return "";
}; 

/**
 * 批量新增之前的判断
 */
areaBuyBalance.beforeBatchAdd=function(){
	$("#balanceDateBatch").datebox('setValue',new Date().format("yyyy-MM-dd"));
};

/**
 * 生成请款单
 */
areaBuyBalance.BatchCreatePaymentBill = function(status) {
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = areaBuyBalance.getMsgOfBatch(checkRows,status);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	var message = "";
	if (checkRows.length > 0) {
		$.each(checkRows, function(index, row) {
			if(!(row.status == 2 && (row.askPaymentNo =='' || row.askPaymentNo==null))){
				message = "财务确认且未请款才能生成请款单!";
				return false;
			}
		});
		if (message != "") {
			showInfo(message);
			return;
		}
		var url = BasePath + "/bill_ask_payment/generate_bill_by_balance";
		var params = {
			checkRows : JSON.stringify(checkRows),
			balanceType : 6
		};
		ajaxRequestAsync(url, params, function(data) {
			if (data > 0) {
				showSuc('成功生成' + data + '条请款单');
				areaBuyBalance.search();
			} else {
				showError('生成失败');
			}
		});
	}
};

/**
 * 审核、删除操作判断提示
 */
areaBuyBalance.getTipsMsg = function(currStatus, operStatus) {
	if(currStatus ===""){
		return "没有可以操作的单据";
	}
	
	// 审核判断 非打回
	if((operStatus > 0 && operStatus < 5) && operStatus != 99){
		if (currStatus > operStatus && currStatus != 99) {
			return "不允许反向确认操作!";
		} else if (currStatus == operStatus) {
			return "不允许重复操作!";
		} else if (operStatus - currStatus > 1) {
			return "不允许越级确认操作!";
		} else if (operStatus == 2 && currStatus == 99) {
			return "不允许越级确认操作!";
		}
	}else if(operStatus == 99){	// 审核判断  打回
		if(!(currStatus == 1 || currStatus == 2)){
			return "只允许打回确认状态的单据!";
		}else if(currStatus==99){
			return "不允许重复操作!";
		} 
	}
	return "";
};

/**
 * 打回判断提示
 */
areaBuyBalance.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo) {
	if (operStatus == 99) {
		if (invoiceNo != '' && invoiceNo != null) {
			return "存在已关联采购发票的单据,不能打回!";
		}
	}
	return "";
}