var searchTemplate = {};
var tbgrid = {};
/**
 * 查询发票模板
 */
searchTemplate.doSearchTemplate = function() {
	var zoneNo = $(window.parent.document.getElementById("zoneNo"));
	//alert(zoneNo.length);
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/invoice_template_set/list.json";
	$("#searchTemplateDG").datagrid('options').queryParams = reqParam;
	$("#searchTemplateDG").datagrid('options').url = queryMxURL;
	$("#searchTemplateDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchTemplate.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchTemplateDG').datagrid({
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
		}, {
			field : 'invoiceTempNo',
			title : '发票模版编号',
			width : 150
		}, {
			field : 'name',
			title : '模板名称',
			width : 200
		}, {
			field : 'companyNo',
			title : '结算公司编码',
			width : 150
		} ] ]
	});

});
