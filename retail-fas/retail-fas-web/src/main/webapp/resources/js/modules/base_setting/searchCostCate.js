var searchCostCate = {};
var tbgrid = {};
/**
 * 查询费用类别
 */
searchCostCate.dosearchCostCate = function() {
	if($('#subForm').form('validate')){
		var companyName = $('#companyName').val();
		var companyNo = $('#companyNo').val();
		if(companyName < 1){
			showWarn("公司为空,请选择的公司!");
			return;
		}
		var reqParam = $("#subForm").form("getData");
		var queryMxURL = BasePath + "/cost_category/list.json?status=1";
		$("#searchCostCateDG").datagrid('options').queryParams = reqParam;
		$("#searchCostCateDG").datagrid('options').url = queryMxURL;
		$("#searchCostCateDG").datagrid('load');
	}
};

/**
 * 清空查询条件
 */
searchCostCate.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchCostCateDG').datagrid({
		url : '',
		singleSelect : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50, 100, 200, 300, 400, 500 ],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 80,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'code',
			title : '编码',
			width : 80
		},{
			field : 'name',
			title : '名称',
			width : 120
		}, {
			field : 'accountsNo',
			title : '会计科目',
			width : 70
		} ] ]
	});
});
