var hqOtherStockOutBalance= new BillObj({
	modulePath : BasePath + '/hq_other_stock_out_balance',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '14',
	mainDgId : 'mainDataGrid',
	startDateId:'startDateCond',
	endDataId:'endDateCond'
}, [ {
	id : 'transferOutDtl',
	title : '出库明细',
	tabUrl : BasePath + '/hq_other_stock_out_balance/transferOutTab.htm',
	listUrl :BasePath + '/hq_other_stock_out_balance/transferOutList.json',
	queryParams : {}
}, {
	id : 'invoiceInfoDtl',
	title : '开票信息',
	tabUrl : BasePath + '/hq_other_stock_out_balance/invoiceInfo.htm',
	listUrl : BasePath + '/hq_other_stock_out_balance/invoiceInfoList.json',
	queryParams : {}
}]);

/**
 * 初始化设置
 */
$(function() {
	hqOtherStockOutBalance.setDtlTabs();
	hqOtherStockOutBalance.addMainTab();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	hqOtherStockOutBalance.add();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/hq_other_stock_out_balance/list.json',{billNo:billNoMenu, balanceType:14},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					hqOtherStockOutBalance.loadDetail(0, obj);
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
 * 清空
 */
hqOtherStockOutBalance.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCon,#buyerNoCon,#brandCondId').val('');
};

/**
 * mainForm加载完毕之后的处理
 */
hqOtherStockOutBalance.afterLoadForm=function(rowData){
	if(rowData.brandName != null && $.trim(rowData.brandName) != '') {
		$("#brandNameId").combotree("setValues", rowData['brandName'].split(','));
	}
};

/**
 * 批量新增之前的处理
 */
hqOtherStockOutBalance.beforeBatchAdd=function(){
	$("#balanceDateBatch").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#balanceStartDateBatch").datebox('setValue',hqOtherStockOutBalance.getStartDate().format("yyyy-MM-dd"));
	$("#balanceEndDateBatch").datebox('setValue',hqOtherStockOutBalance.getEndDate().format("yyyy-MM-dd"));
};

/**
 * 保存回调函数
 */
hqOtherStockOutBalance.afterSaved = function(dgId, url, params, jsonObj) {
	if(jsonObj.brandName != null && $.trim(jsonObj.brandName) != '') {
		$("#brandNameId").combotree("setValues", jsonObj['brandName'].split(','));
	}
	$('#billName,#remarkId').removeClass('readonly').removeAttr("readonly");
	var billNo = $('#billNo').val();
	params.balanceNo = billNo;
	$('#' + dgId).datagrid('options').queryParams = params;
	$('#' + dgId).datagrid('options').url = url;
	$('#' + dgId).datagrid('load');
};

/**
 * form加载后的样式控制
 */
hqOtherStockOutBalance.setFormClass=function(){
	$('#billNo,#salerNameId,#buyerNameId,#brandNameId,#invoiceApplyNo,#balanceAmount,#outAmount,#statusNameId').attr("readonly", true).addClass("readonly");
	$("#salerNameId,#buyerNameId,#brandNameId").combogrid("disable");
	$('#currencyNameId').combobox('enable');
};

hqOtherStockOutBalance.afterSetClass=function(rowData){
	$("#salerNameId,#buyerNameId,#brandNameId").combogrid("disable");
	$('#statusNameId').val(hqOtherStockOutBalance.statusFormat(rowData.status));
	$('#currencyNameId').combobox('setValue', rowData.currency);
};

/**
 * 新增初始化
 */
hqOtherStockOutBalance.initAdd = function() {
	$('#brandNameId').attr("readonly",true);
	$('#billNo,#outAmount,#statusNameId,#balanceAmount,#invoiceApplyNo').attr("readonly",true).addClass("readonly");
	$('#id,#statusId,#brandNoId').val("");	
	$("#balanceAmount").numberbox("clear");
	$("#salerNameId,#buyerNameId,#brandNameId").combogrid("enable");
	$("#balanceDateId").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#balanceStartDate").datebox('setValue',hqOtherStockOutBalance.getStartDate().format("yyyy-MM-dd"));
	$("#balanceEndDate").datebox('setValue',hqOtherStockOutBalance.getEndDate().format("yyyy-MM-dd"));
};

/**
 * 判断提示
 */
hqOtherStockOutBalance.getTipsMsg = function(currStatus, operStatus) {
	if (currStatus === "") {
		return "没有可以操作的单据";
	}

	if (currStatus == 99 && operStatus > 2 && operStatus != 99) {
		return "不允许越级确认操作!";
	}

	if (currStatus == 6&& operStatus > 0 && operStatus < 5) {
		return "已开票,不能反确认!";
	}

	if (operStatus > 0 && operStatus < 5) {
		// 重复操作
		if (currStatus == operStatus) {
			return "不允许重复操作!";
		}

		// 反向判断
		if (currStatus == 4 && operStatus < 4) {
			return "不允许反向确认操作!";
		}

		// 越级判断
		if (currStatus == 0 && operStatus > 2) {
			return "不允许越级确认操作!";
		}
	} else if (operStatus == 99) {
		if (currStatus == 0 || currStatus >= 5) {
			return "只允许打回确认状态的单据!";
		} else if (currStatus == 99) {
			return "不允许重复操作!";
		}
	}
	return "";
};

/**
 * 打回判断提示信息
 */
hqOtherStockOutBalance.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo,currStatus) {
	if (operStatus == 99) {
		if (askPaymentNo != '' && askPaymentNo != null) {
			return "存在已关联请款单的单据，不能打回!";
		} else if (invoiceApplyNo != '' && invoiceApplyNo != null) {
			return "存在已开票的单据，不能打回!";
		} else if (currStatus == 4) {
			return "地区财务已经确认，不能打回!";
		}
	}
	return "";
}
