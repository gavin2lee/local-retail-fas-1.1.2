var searchAllCustomer = {};
var tbgrid = {};
/**
 * 查询客户
 */
searchAllCustomer.dosearchAllCustomer = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/bill_balance_invoice_register/listAll.json";
	$("#searchAllCustomerDG").datagrid('options').queryParams = reqParam;
	$("#searchAllCustomerDG").datagrid('options').url = queryMxURL;
	$("#searchAllCustomerDG").datagrid('load');
};

//获取多选页面中，选中的记录集合
function multiSearchDatas() {
	var checkedRows = $("#searchAllCustomerDG").datagrid("getChecked");
	return checkedRows;
};

/**
 * 清空查询条件
 */
searchAllCustomer.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchAllCustomerDG').datagrid({
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
			field : 'code',
			title : '客户编码',
			width : 150
		}, {
			field : 'name',
			title : '客户名称',
			width : 200
		}] ]
	});

});
