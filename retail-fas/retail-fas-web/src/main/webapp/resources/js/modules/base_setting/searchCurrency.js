var searchCurrency = {};
var tbgrid = {};
/**
 * 查询币种
 */
searchCurrency.dosearchCurrency = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/base_setting/currency_management/list.json?status=1";
	$("#searchCurrencyDG").datagrid('options').queryParams = reqParam;
	$("#searchCurrencyDG").datagrid('options').url = queryMxURL;
	$("#searchCurrencyDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchCurrency.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchCurrencyDG').datagrid({
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
			field : 'currencyCode',
			title : '币种编码',
			width : 150
		}, {
			field : 'currencyName',
			title : '币种名称',
			width : 200
		}, {
			field : 'currencySymbol',
			title : '币种标识',
			width : 100
		} ] ]
	});

});
