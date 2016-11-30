var bill_balance_zone = {};

var bill_extra_operate = {
	setting : {
		dataGridId : "balanceZoneDataGrid",
		dataFormId: "mainDataForm",
		searchFormId : "searchForm",
		searchBtn : "bill-btn-search",
		searchUrl : "/bill_balance/list.json?balanceType=7",
		clearBtn : "bill-btn-clear",
		addBtn : "bill-btn-add",
		saveBtn : "bill-btn-save",
		saveUrl : "/bill_balance_zone/insert",
		updateUrl : "/bill_balance_zone/update",
		delBtn : "bill-btn-del",
		delUrl : "/bill_balance_zone/batch_delete",
		exportBtn : "bill-btn-export",
		exportUrl : "/bill_balance_zone/do_fas_export",
		exportTitle : "地区批发结算单",
		prevBtn : "bill-btn-prev",
		nextBtn : "bill-btn-next"
	},
	initAdd : function() {
		$("#id").val("");
		bill_balance_zone.clearReadOnly();
		bill_balance_zone.initSearchBox();
		//清空明细数据
		$('#balanceDtlDataGrid').datagrid('loadData', {total: 0, rows: []});
	},
	initLoadDetail : function(rowData) {
		if(rowData.auditStatus && rowData.auditStatus != '0') {
			bill_balance_zone.setReadOnly();
			//明细页面保存
			$("#" + initSetting.saveBtn).live("click", function() {
				alert("不允许修改!");
				return;
			});
		} else {
			bill_balance_zone.clearReadOnly();
			bill_balance_zone.initSearchBox();
		}
		//加载明细数据
		var url = BasePath + "/bill_sale_out_dtl/list.json";
		var params = {
			bizType : '2', 
			balanceNo:rowData.billNo
		};
	    $("#balanceDtlDataGrid").datagrid("options").queryParams= params;
	    $("#balanceDtlDataGrid").datagrid("options").url=  url;
	    $("#balanceDtlDataGrid").datagrid("load");
		returnTab('mainTab', '单据明细');
	}
};

//设置样式
bill_balance_zone.setReadOnly = function() {
	$("#billNo, #billName").attr("readonly", true).addClass("readonly").addClass("disabled");
	$("#balanceStartDate, #balanceEndDate").addClass("readonly").attr("readonly", true);
};

//清除样式
bill_balance_zone.clearReadOnly = function() {
	$("#billNo, #billName").removeAttr("readonly").removeClass("readonly").removeClass("disabled");
	$("#balanceStartDate, #balanceEndDate").removeClass("readonly").removeAttr("readonly");
};

//初始化查询框组件
bill_balance_zone.initSearchBox = function() {
	fas_common.initIptSearch({
		id : "companyName",
		title : "选择结算主体",
		url : "/base_setting/company/toSearchCompany",
		callback : function(data) {
			$("#companyNo").val(data.companyNo);
			$("#companyName").val(data.name);
			var queryMarginUrl = BasePath+"/bill_balance_zone/select_amount";
			var queryMarginParams = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val()};
			ajaxRequest(queryMarginUrl, queryMarginParams, function(result) {
				if(result) {
					$("#openOrderAmount").val(result.totalOrderAmount - result.totalSendOutAmount);
					$("#openPrePayment").val(result.totalPrePayment - result.totalSendOutAmount);
					$("#saleOutAmount").val(result.totalSendOutAmount);
				}
			});
		}
	});
	
	fas_common.initIptSearch({
		id : "customerName",
		title : "选择客户",
		url : "/base_setting/customer/toSearchCustomer",
		callback : function(data) {
			$("#customerNo").val(data.customerNo);
			$("#customerName").val(data.fullName);
			var queryMarginUrl = BasePath+"/bill_balance_zone/select_amount";
			var queryMarginParams = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val()};
			ajaxRequest(queryMarginUrl, queryMarginParams, function(result) {
				if(result) {
					$("#openOrderAmount").val(result.totalOrderAmount - result.totalSendOutAmount);
					$("#openPrePayment").val(result.totalPrePayment - result.totalSendOutAmount);
					$("#saleOutAmount").val(result.totalSendOutAmount);
				}
			});
		}
	});
};

//批量新增
bill_balance_zone.toBatchAdd = function(){
	ygDialog({
		title : '批量生成结算单',
		target : $('#myFormPanel'),
		width : 600,
		height : 300,
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				bill_balance_zone.batchAdd(dialog);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
};

//批量新增的逻辑处理方法
bill_balance_zone.batchAdd = function(dialog) {
	fas_common.doSave({
		dataFormId : "dataForm",
		saveUrl : "/bill_balance_zone/batch_add",
	});
};

//删除
bill_balance_zone.batchDel = function() {
	fas_common.doDel({
		primaryKey : "id",
		datagridId : "balanceZoneDataGrid",
		delUrl : "/bill_balance_zone/batch_delete",
		searchBtn : "bill-btn-search"
	});
};

//删除、审核等操作时，定义的前置函数
var extra_operate = {
	checkDel : function() {
		var url = BasePath + "/bill_balance_zone/check_del";
		var checkData = {};
		var checkResult = fas_common_ajax.ajaxRequestAsync(url, checkData, function(flag){
			if(!flag) {
				showError('数据不能删除!');
				return false;
			}
			return true;
		});
		return checkResult;
	}
};

//在列表页面实现删除、注销等操作时，自定义组装数据的函数
var list_build_data = {
	buildDelData : function(checkedRows) {
		var deleteList = [];
		$.each(checkedRows, function(index, item){
			var obj = new Object();
			obj.id = item.id;
			obj.billNo = item.billNo;
			deleteList.push(obj);
		});    
		return deleteList;
	}	
};

//数据加载成功后的处理方法
var data_onload_success = {
	bill_dtl : function() {
		var rows = $("#balanceDtlDataGrid").datagrid("getRows");
		if(rows && rows.length > 0) {
			var totalSendOutQty = 0, totalCostAmount = 0.00, totalQuoteAmount = 0.00;
			$.each(rows, function(index, item) {
				totalSendOutQty += item.sendOutQty;
				totalCostAmount += item.costAmount;
				totalQuoteAmount += item.quoteAmount;
	    	});
			$('#balanceDtlDataGrid').datagrid('appendRow',{
				billNo : "合计", 
				sendOutQty : totalSendOutQty,
				costAmount : totalCostAmount,
				quoteAmount : totalQuoteAmount
			});
		}
	}
};

$(function() {
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : false,
		closable : false,
		href : BasePath + "/bill_balance_zone/listTab",
		onLoad : function(panel) {
			// 这里需要在重写在加载完后做对应的事件
		}
	});
});