var searchItem={};
var tbgrid = {};
/**
 * 查询
 */
searchItem.dosearchItem = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/base_setting/item/list.json";
	$("#searchItemDG").datagrid('options').queryParams = reqParam;
	$("#searchItemDG").datagrid('options').url = queryMxURL;
	$("#searchItemDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchItem.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchItemDG').datagrid({
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
			field : 'itemNo',
			title : '商品ID',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'code',
			title : '商品编码',
			width : 150,
			align : 'left',
			halign : 'center'
		}, {
			field : 'name',
			title : '商品名称',
			width : 200,
			align : 'left',
			halign : 'center'
		}, {
			field : 'fullName',
			title : '商品全称',
			width : 200,
			align : 'left',
			halign : 'center'
		}] ]
	});

});
