var searchBsgroups = {};
var tbgrid = {};
/**
 * 查询商业集团
 */
searchBsgroups.dosearchBsgroups = function() {
	//alert(zoneNo.length);
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/bsgroups/list.json";
	$("#searchBsgroupsDG").datagrid('options').queryParams = reqParam;
	$("#searchBsgroupsDG").datagrid('options').url = queryMxURL;
	$("#searchBsgroupsDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchBsgroups.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchBsgroupsDG').datagrid({
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
			field : 'bsgroupsNo',
			title : '编码',
			width : 150
		}, {
			field : 'name',
			title : '名称',
			width : 250
		} ] ]
	});

});
