var category = {};

var tbgrid = {};

category.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/category/list.json';
	$("#dataGridJG").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#dataGridJG").datagrid('options').url = queryMxURL;
	$("#dataGridJG").datagrid('load');
};

category.clear = function() {
	$("#searchForm").form("clear");
};

$(function(){
	tbgrid = $('#dataGridJG').datagrid({
		url : '',
		singleSelect : true,
		pageSize:10,
        pageList:[10,20,50,100,200,300,400,500],
		columns : [[
		{
			title : '请选择',
			field : 'col1',
			width : 50,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'categoryNo',
			title : '大类编码',
			width : 140
		}, {
			field : 'name',
			title : '大类名称',
			width : 160
		}]]
	});
//	category.search();
});