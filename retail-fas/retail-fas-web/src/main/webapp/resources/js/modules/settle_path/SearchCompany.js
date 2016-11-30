var SearchCompany = {};
var tbgrid = {};

SearchCompany.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/settle_path/search_company';

	// 2.加载明细 注意请求方式 restful风格 get
	$("#dataGridJG").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#dataGridJG").datagrid('options').url = queryMxURL;
	$("#dataGridJG").datagrid('load');
};

SearchCompany.clear = function() {
	$("#dataNo").val("");
	$("#dataName").val("");
};

$(function(){
	tbgrid = $('#dataGridJG').datagrid({
		url : '',
		singleSelect : true,
		pageSize : 10, 
		columns : [[
		 {
			field : 'companyNo',
			title : '公司编码',
			width : 100
		}, {
			field : 'name',
			title : '公司名称',
			width : 260
		}]]
	});
});