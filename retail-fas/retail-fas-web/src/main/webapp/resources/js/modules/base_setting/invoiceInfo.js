var invoiceInfo = {};
var tbgrid = {};
var clientData=new Object();
/**
 * 查询预收款信息
 */
invoiceInfo.doSearchInvoiceInfo = function() {
	var companyNo = $("#companyNo").val();
	if(!companyNo) {
		showWarn("请先选择开票方!");
		return;
	}
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = "";
	queryMxURL = BasePath + "/base_setting/invoice_info_set/list.json?status=1";
	$("#searchInvoiceInfoDG").datagrid('options').queryParams = reqParam;
	$("#searchInvoiceInfoDG").datagrid('options').url = queryMxURL;
	$("#searchInvoiceInfoDG").datagrid('load');
};

/**
 * 清空查询条件
 */
invoiceInfo.clearCondition = function() {
	$("#subForm").form("clear");
};

invoiceInfo.clientTypeFormatter = function(value, rowData, rowIndex) {
	for(var i = 0; i < clientData.length; i++) {
		if(clientData[i].typeNo == value) {
			return clientData[i].typeName;
		}
	}
	return "";
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchInvoiceInfoDG').datagrid({
		url : '',
		singleSelect : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50, 100, 200, 300, 400, 500 ],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 60,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		},{
			field : 'clientNo',
			title : '客户编码',
			width : 120
		},{
			field : 'clientName',
			title : '客户名称',
			width : 200
		},{
			field : 'clientType',
			title : '客户类型',
			width : 100,
			formatter:invoiceInfo.clientTypeFormatter
		}] ]
	});
	
	// 加载客户类型
	$('#clientTypeCond').combobox({
		url : BasePath + '/base_setting/invoice_info_set/getClientType',
		valueField : 'typeNo',
		textField : 'typeName',
		onLoadSuccess : function(data) {
			clientData = data;
		}
	}); 
});
