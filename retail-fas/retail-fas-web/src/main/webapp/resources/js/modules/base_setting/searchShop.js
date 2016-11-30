var searchShop = {};
var tbgrid = {};
/**
 * 查询店铺
 */
searchShop.dosearchShop = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/shop/list.json";          //"/common_util/getShop";
	$("#searchShopDG").datagrid('options').queryParams = reqParam;
	$("#searchShopDG").datagrid('options').url = queryMxURL;
	$("#searchShopDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchShop.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
	$('#subForm').find('input[name=companyNo]').val($('#companyNo').val());
	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchShopDG').datagrid({
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
			field : 'shopNo',
			title : '编码',
			width : 150
		}, {
			field : 'shortName',
			title : '店铺简称',
			width : 250
		} ,{
			field : 'fullName',
			title : '店铺全称',
			width : 100
		} ] ]
	});

	
	$('#shopNoLike').bind('blur',function(val){
		$('#shopNoLike').val(val.target.value.trim());
	});
	
	$('#shortNameLike').bind('blur',function(val){
		$('#shortNameLike').val(val.target.value.trim());
	});
});
