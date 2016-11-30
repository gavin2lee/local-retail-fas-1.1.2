var searchOrderBillBalance = {};
var tbgrid = {};
/**
 * 查询客户
 */
searchOrderBillBalance.dosearchOrderBillBalance = function() {
//	var startDate = $("#startDate").val();
//	var endDate = $("#endDate").val();
//	var companyNo = $("#searchCompanyNo").val();
//	if(!companyNo || !startDate || !endDate) {
//		showWarn("请输入结算公司，开始时间和结束时间");
//		return;
//	}
	
	var validateForm = $("#searchForm").form('validate');
	// 校验必填项
	if (validateForm == false) {
		return;
	}
	var reqParam = $("#searchForm").form("getData");
	var queryMxURL = BasePath + "/bill_balance_invoice_apply/getOrderBillBalance";
	$("#searchOrderBillBalanceDG").datagrid('options').queryParams = reqParam;
	$("#searchOrderBillBalanceDG").datagrid('options').url = queryMxURL;
	$("#searchOrderBillBalanceDG").datagrid('load');
};

//获取多选页面中，选中的记录集合
function multiSearchDatas() {
	var checkedRows = $("#searchOrderBillBalanceDG").datagrid("getChecked");
//	var companyName = $("#companyName").company("getValue");
//	var companyNo = $("#searchCompanyNo").val();
//	var obj = {
//			checkedRows : checkedRows,
//			companyNo : companyNo,
//			companyName : companyName
//	}
	return checkedRows;
};

/**
 * 清空查询条件
 */
searchOrderBillBalance.clearCondition = function() {
	$("#searchForm").form("clear");
	$("#searchCompanyNo").val("");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchOrderBillBalanceDG').datagrid({
		url : '',
		singleSelect : false,
		pagination:true,
		pageSize:10,
        pageList:[10,20,50,100,200,300,400,500],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 60,
			checkbox:'true'
		}, {
			field : 'dtlId',
			title : '流水号',
			width : 130,
			align : 'left',
			hidden : 'true'
		}, {
			field : 'billNo',
			title : '单据编号',
			width : 130,
			align : 'left',
			halign : 'center'
		}, {
			field : 'balanceTypeName',
			title : '单据类型',
			width : 80,
			align : 'left',
			halign : 'center'
		}, {
			field : 'balanceMonth',
			title : '结算月',
			width : 80,
			align : 'left',
			halign : 'center'
		}, {
			field : 'buyerNo',
			title : '结算买方编号',
			hidden : 'true',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'buyerName',
			title : '结算买方',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'salerNo',
			title : '结算卖方编号',
			hidden : 'true',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'salerName',
			title : '结算卖方',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'shopNo',
			title : '店铺编码',
			width : 120,
			align:'left',
			halign:'center'
		}, {
			field : 'shopName',
			title : '店铺名称',
			width : 120,
			align:'left',
			halign:'center'
		}, {
			field : 'itemCode',
			title : '商品编码',
			width : 120,
			align:'left',
			halign:'center'
		},{
			field : 'itemName',
			title : '商品名称',
			width : 170,
			align:'left',
			halign:'center'
		},{
			field : 'brandName',
			title : '品牌',
			width : 80,
			align:'left',
			halign:'center'
		},{
			field : 'categoryName',
			title : '大类',
			width : 50,
			align:'left',
			halign:'center'
		},{
			field : 'qty',
			title : '数量',
			width : 60,
			align : 'left',
			halign : 'center'
		}, {
			field : 'cost',
			title : '单价',
			width : 50,
			align:'right',
			halign:'center'
		},{
			field : 'amount',
			title : '金额',
			width : 90,
			align : 'left',
			halign : 'center'
		} ] ]
	});

});
