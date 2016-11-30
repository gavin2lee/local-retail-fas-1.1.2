var areaOtherStockInBalance= new BillObj({
	modulePath : BasePath + '/area_other_stock_in_balance',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '14',
	mainDgId : 'mainDataGrid'
}, [ {
	id : 'transferOutDtl',
	title : '出库明细',
	tabUrl : BasePath + '/area_other_stock_in_balance/transferOutTab.htm',
	listUrl :BasePath + '/area_other_stock_in_balance/transferOutList.json',
	queryParams : {}
}, {
	id : 'invoiceInfoDtl',
	title : '发票信息',
	tabUrl : BasePath + '/area_other_stock_in_balance/invoiceInfo.htm',
	listUrl : BasePath + '/bill_invoice/query_balance_invoice.json',
	queryParams : {}
}, {
	id : 'receivableInfoDtl',
	title : '请款信息',
	tabUrl : BasePath + '/area_other_stock_in_balance/receivableInfo.htm',
	listUrl : BasePath + '/bill_ask_payment/list.json',
	queryParams : {}
}]);

/**
 * 初始化设置
 */
$(function() {
	areaOtherStockInBalance.setDtlTabs();
	areaOtherStockInBalance.disableMainInfo();
	areaOtherStockInBalance.addMainTab();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	areaOtherStockInBalance.initSettleMethod();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/area_other_stock_in_balance/list.json',{billNo:billNoMenu, balanceType:14},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					areaOtherStockInBalance.loadDetail(0, obj);
				},500);
			}else {
				alert("单号:"+ billNoMenu +"有误！");
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
areaOtherStockInBalance.initSettleMethod=function(){
	var url=BasePath+'/common_util/getSettleMethod';
	ajaxRequestAsync(url, null, function(data) {
		$.each(data, function(index, item) {
			datas[item.code] = item.name;
		});
	});
};

/**
 * 清空
 */
areaOtherStockInBalance.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCon,#buyerNoCon,#brandCondId').val('');
};

areaOtherStockInBalance.setFormClass=function(){
	$('#mainDataForm input').attr("readonly", true).addClass("readonly");
	$("#mainDataForm").find("input[class*=easyui-combobox]").combobox('disable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
};

areaOtherStockInBalance.afterSetClass=function(rowData){
	$('#statusNameId').val(areaOtherStockInBalance.statusFormat(rowData.extendStatus));
	$('#currencyNameId').combobox('setValue',rowData.currency);
};

/**
 * 生成请款单
 */
areaOtherStockInBalance.BatchCreatePaymentBill = function(status) {
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = areaOtherStockInBalance.getMsgOfBatch(checkRows,status);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	var message = "";
	if (checkRows.length > 0) {
		$.each(checkRows, function(index, row) {
			if(!(row.extendStatus == 4 && (row.askPaymentNo =='' || row.askPaymentNo==null))){
				message = "地区财务确认且未请款才能生成请款单!";
				return false;
			}
//			if(row.balanceAmount <= 0){
//				message = "应付金额不大于零,不允许生成请款单!";
//				return false;
//			}
		});
		if (message != "") {
			showInfo(message);
			return;
		}
		var url = BasePath + "/bill_ask_payment/generate_bill_by_balance";
		var params = {
			checkRows : JSON.stringify(checkRows),
			balanceType : 14
		};
		ajaxRequestAsync(url, params, function(data) {
			if (data > 0) {
				showSuc('成功生成' + data + '条请款单');
				areaOtherStockInBalance.search();
			} else {
				showError('生成失败');
			}
		});
	}
};

/**
 * 判断提示
 */
areaOtherStockInBalance.getTipsMsg = function(currStatus, operStatus) {
	if (currStatus === "") {
		return "没有可以操作的单据";
	}

	if ((currStatus == 99 || currStatus == 0) && operStatus > 1
			&& operStatus != 99) {
		return "不允许越级确认操作!";
	}
	
	if (currStatus == operStatus) {
		return "不允许重复操作!";
	}
	
	if (currStatus == 5 && operStatus > 0 && operStatus < 5) {
		return "已请款，不能反确认!";
	}
	return "";
};

/**
 * 打回判断提示信息
 */
areaOtherStockInBalance.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo) {
	if (operStatus == 99) {
		if (askPaymentNo != '' && askPaymentNo != null) {
			return "存在已关联请款单的单据，不能打回!";
		} else if (invoiceApplyNo != '' && invoiceApplyNo != null) {
			return "存在已开票的单据，不能打回!";
		}
	}
	return "";
}
