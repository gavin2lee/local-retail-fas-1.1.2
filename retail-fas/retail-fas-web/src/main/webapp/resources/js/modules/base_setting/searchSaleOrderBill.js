var searchSaleOrderBill = {};
var tbgrid = {};
/**
 * 查询客户
 */
searchSaleOrderBill.dosearchSaleOrderBill = function() {
	var reqParam = $("#searchForm").form("getData");
	var queryMxURL = BasePath + "/bill_sale_balance/querySaleOrderBill";
	$("#searchSaleOrderBillDG").datagrid('options').queryParams = reqParam;
	$("#searchSaleOrderBillDG").datagrid('options').url = queryMxURL;
	$("#searchSaleOrderBillDG").datagrid('load');
};

//获取多选页面中，选中的记录集合
function multiSearchDatas() {
	var checkedRows = $("#searchSaleOrderBillDG").datagrid("getChecked");
	
	return checkedRows;
/*	var companyName = $("#companyName").val();
	var companyNo = $("#companyNo").val();
	var obj = {
			checkedRows : checkedRows,
			companyNo : companyNo,
			companyName : companyName
	}
	return obj;*/
};

/**
 * 清空查询条件
 */
searchSaleOrderBill.clearCondition = function() {
	$("#searchForm").form("clear");
	$("#companyNo").val("");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchSaleOrderBillDG').datagrid({
		url : '',
		singleSelect : true,
		pagination:true,
		pageSize:10,
        pageList:[10,20,50,100,200,300,400,500],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 80,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'originalBillNo',
			title : '订单编码',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'cost',
			title : '金额',
			width : 90,
			align : 'left',
			halign : 'center'
		},{
			field : 'salerNo',
			title : '卖方编码',
			width : 90,
			align : 'left',
			halign : 'center'
		},{
			field : 'salerName',
			title : '卖方名称',
			width : 90,
			align : 'left',
			halign : 'center'
		},{
			field : 'buyerNo',
			title : '买方编码',
			width : 90,
			align : 'left',
			halign : 'center'
		},{
			field : 'buyerName',
			title : '买方名称',
			width : 90,
			align : 'left',
			halign : 'center'
		} ] ]
	});

});
