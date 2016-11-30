var bill_invoice_apply = {};

//行编辑
var setting = {
	datagridId : "dtlDataGridDiv",
	primaryKey : "id",
	saveUrl : "",
	saveCallback : function(){
		
	},
	initRow : function(){
		return {billNo : $("#billNo").val()};
	},
	initData : {
		combobox : {
			values : ["brandNo,categoryNo"],
			texts : ["brandName,categoryName"]
		}
	}
};

var bill_extra_operate = {
	setting : {
		dataGridId : "dataGridDiv",
		dataFormId: "mainDataForm",
		searchFormId : "searchForm",
		searchBtn : "bill-btn-search",
		searchUrl : "/bill_invoice_apply/list.json",
		clearBtn : "bill-btn-clear",
		addBtn : "bill-btn-add",
		saveBtn : "bill-btn-save",
		saveUrl : "/bill_invoice_apply/save_all",
		updateUrl : "/bill_invoice_apply/save_all",
		auditBtn : "bill-btn-audit",
		auditUrl : "/bill_invoice_apply/do_aduit?auditVal=1",
		antiAuditBtn : "bill-btn-antiAudit",
		antiAuditUrl : "/bill_invoice_apply/do_aduit?auditVal=0",
		exportBtn : "bill-btn-export",
		exportUrl : "/bill_invoice_apply/do_fas_export",
		exportTitle : "开票申请单信息",
		prevBtn : "bill-btn-prev",
		nextBtn : "bill-btn-next"
	},
	initAdd : function() {
		$("#id").val("");
		bill_invoice_apply.clearReadOnly();
		//清空明细数据
		$('#dtlDataGridDiv').datagrid('loadData', {total: 0, rows: []});
	},
	initLoadDetail : function(rowData) {
		if(rowData.auditStatus && rowData.auditStatus != '0') {
			bill_invoice_apply.setReadOnly();
			//明细页面保存
			$("#" + initSetting.saveBtn).live("click", function() {
				alert("不允许修改!");
			});
		} else {
			bill_invoice_apply.clearReadOnly();
			bill_invoice_apply.initSearchBox();
		}
		fas_common.search({
			dataGridId : "dtlDataGridDiv",
			url : "/bill_invoice_apply_dtl/list.json?billNo="+$("#billNo").val()
		});
		fas_common_editor.editIndex = undefined;
		returnTab('mainTab', '单据明细');
	}
};

//设置样式
bill_invoice_apply.clearReadOnly = function() {
	$("#billNo, #invoiceNo, #mailingAddress, #deliveryNo, #express, #remark").removeClass("readonly").removeAttr("readonly");
	$("#invoiceApplyTime, #payTime, #deliveryTime, #receiveTime").removeClass("readonly").removeAttr("disabled");
	$("#preInvoice, #invoiceType").combobox("enable");
	$("#companyName, #customerNo").parents("div").addClass("ipt-search-box");
	bill_invoice_apply.initSearchBox();
};

//清除样式
bill_invoice_apply.setReadOnly = function() {
	$("#billNo, #invoiceNo, #mailingAddress, #deliveryNo, #express, #remark").addClass("readonly").attr("readonly", true);
	$("#invoiceApplyTime, #payTime, #deliveryTime, #receiveTime").addClass("readonly").attr("disabled", true);
	$("#companyName, #customerNo").parents("div").removeClass("ipt-search-box");
	$("#preInvoice, #invoiceType").combobox('disable');
};

//在列表页面实现删除、注销等操作时，自定义组装数据的函数
var list_build_data = {
	initSubmitParams : function() {
		var params = [];
		var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
		var deleted = "";
		var deletedData = $("#dtlDataGridDiv").datagrid('getChanges','deleted');
		var deleteList = [];
    	$.each(deletedData, function(index, item){
    		var obj = new Object();
    		obj.id = item.id;
    		deleteList.push(obj);
    	});
    	if(deleteList.length > 0) {
            deleted = JSON.stringify(deleteList);
    	}
		params.push({name : 'inserted', value : effectRow.inserted});
		params.push({name : 'updated', value : effectRow.updated});
		params.push({name : 'deleted', value : deleted});
		return params;
	}
};

//反审核
bill_invoice_apply.doAntiAudit = function() {
	// 给审核按钮绑定函数事件btn_audit
	$("#btn-anti-audit").live("click", function() {
		fas_common.doDel({
			primaryKey : "id",
			datagridId : "dataGridDiv",
			delUrl : "/bill_invoice_apply/do_anti_audit",
			operaterName : "反审核",
			searchBtn : "btn-search"
		});
	});
};

//初始化查询组件
bill_invoice_apply.initSearchBox = function() {
	fas_common.initIptSearch({
		id : "companyName",
		title : "选择开票方",
		url : "/base_setting/company/toSearchCompany",
		callback : function(data) {
			$("#companyNo").val(data.companyNo);
			$("#companyName").val(data.name);
		}
	});
	fas_common.initIptSearch({
		id : "customerNo",
		title : "选择客户",
		url : "/base_setting/customer/toSearchCustomer",
		callback : function(data) {
			$("#customerNo").val(data.customerNo);
			$("#customerName").val(data.shortName);
			$("#payUnit").val(data.fullName);
			$("#bankName").val(data.bankName);
			$("#bankAccount").val(data.bankAccount);
			$("#taxRegistryNo").val(data.taxRegistryNo);
			$("#address").val(data.address);
			$("#contactName").val(data.contactName);
			$("#tel").val(data.tel);
		}
	});
};

$(function() {
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : false,
		closable : false,
		href : BasePath + "/bill_invoice_apply/bill_tab"
	});
	bill_invoice_apply.initSearchBox();
});