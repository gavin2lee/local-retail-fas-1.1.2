var hqInsteadBuy= new BillObj({
	modulePath : BasePath + '/area_insteadOf_buy_balance',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '13',
	mainDgId : 'mainDataGrid',
	startDateId:'startDateCond',
	endDataId:'endDateCond'
}, [ {
	id : 'transferInDtl',
	title : '入库明细',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/transferInTab.htm',
	listUrl : BasePath + '/hq_insteadOf_buy_balance/transferInList.json',
	queryParams : {billType:1301}
}, {
	id : 'returnDtl',
	title : '退残明细',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/returnInfo.htm',
	listUrl : BasePath + '/hq_insteadOf_buy_balance/transferInList.json',
	queryParams : {billType:1333}
}, {
	id : 'otherDeductionDtl',
	title : '其他扣项',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/otherDeduction.htm',
	listUrl : BasePath + '/other_deduction/list.json',
	queryParams : {}
}, {
	id : 'invoiceInfoDtl',
	title : '发票信息',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/invoiceInfo.htm',
	listUrl : BasePath + '/bill_invoice/query_balance_invoice.json',
	queryParams : {}
}, {
	id : 'paymentInfoDtl',
	title : '请款信息',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/paymentInfo.htm',
	listUrl : BasePath + '/bill_ask_payment/list.json',
	queryParams : {}
} ]);

/**
 * 初始化设置
 */
$(function() {
	hqInsteadBuy.setDtlTabs();
	hqInsteadBuy.addMainTab();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	hqInsteadBuy.disableMainInfo();
	hqInsteadBuy.initSettleMethod();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/area_insteadOf_buy_balance/list.json',{billNo:billNoMenu},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					hqInsteadBuy.loadDetail(0, obj);
				},500);	
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
});

/**
 * 加载结算方式
 */
var datas=[];
hqInsteadBuy.initSettleMethod=function(){
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
hqInsteadBuy.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCond,#buyerNoCond,#brandUnitNoCond').val("");
};

hqInsteadBuy.setFormClass=function(){
	$('#mainDataForm input').attr("readonly", true).addClass("readonly");
	$("#mainDataForm").find("input[class*=easyui-combobox]").combobox('disable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
};

hqInsteadBuy.afterSetClass=function(rowData){
	$('#statusNameId').val(hqInsteadBuy.statusFormat(rowData.status));
	$('#currencyNameId').combobox('setValue',rowData.currency);
};

/**
 * 生成请款单
 */
hqInsteadBuy.BatchCreatePaymentBill = function(status) {
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = hqInsteadBuy.getMsgOfBatch(checkRows,status);
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
			balanceType : 13
		};
		ajaxRequestAsync(url, params, function(data) {
			if (data > 0) {
				showSuc('成功生成' + data + '条请款单');
				hqInsteadBuy.search();
			} else {
				showError('生成失败');
			}
		});
	}
};

/**
 * 判断提示
 */
hqInsteadBuy.getTipsMsg = function(currStatus, operStatus) {
	if(currStatus ===""){
		return "没有可以操作的单据";
	}
	
	//删除判断
	if(typeof operStatus == 'undefined'){
		if(currStatus != 0 && currStatus!=99){
			return "只允许删除制单、打回状态的单据!";
		}
	}
	
	// 审核判断 非打回
	if((operStatus > 0 && operStatus < 5) && operStatus != 99){
		if(currStatus > operStatus&&currStatus!=99){
			return "不允许反向确认操作!";
		}else if(currStatus == operStatus){
			return "不允许重复操作!";
		}else if(operStatus - currStatus > 1){
			return "不允许越级确认操作!";
		} else if(operStatus==2&&currStatus==99){
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
 * 打回判断提示信息
 */
hqInsteadBuy.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo) {
	if (operStatus == 99) {
		if (invoiceNo != '' && invoiceNo != null) {
			return "存在已关联采购发票的单据,不能打回!";
		}
	}
	return "";
}
