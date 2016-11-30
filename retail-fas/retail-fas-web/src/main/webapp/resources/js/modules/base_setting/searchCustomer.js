var searchCustomer = {};
var tbgrid = {};
/**
 * 查询客户
 */
searchCustomer.dosearchCustomer = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/base_setting/customer/list.json";
	$("#searchCustomerDG").datagrid('options').queryParams = reqParam;
	$("#searchCustomerDG").datagrid('options').url = queryMxURL;
	$("#searchCustomerDG").datagrid('load');
};

//获取多选页面中，选中的记录集合
function multiSearchDatas() {
	var checkedRows = $("#searchCustomerDG").datagrid("getChecked");
	return checkedRows;
};

/**
 * 清空查询条件
 */
searchCustomer.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchCustomerDG').datagrid({
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
			field : 'customerNo',
			title : '客户编码',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'fullName',
			title : '客户名称',
			width : 200,
			align : 'left',
			halign : 'center'
		}, {
			field : 'shortName',
			title : '客户简称',
			width : 100,
			align : 'left',
			halign : 'center'
		} ] ]
	});

});
