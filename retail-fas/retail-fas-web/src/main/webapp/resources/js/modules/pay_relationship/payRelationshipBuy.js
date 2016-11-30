var payRelationshipBuy = new Object();

//查询
payRelationshipBuy.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		params.searchType = 'item';
		var url = BasePath + '/pay_relationship/list.json';
		$('#dtlDataGrid').datagrid('options').queryParams= params;
		$('#dtlDataGrid').datagrid('options').url= url;
		$('#dtlDataGrid').datagrid('load');
	}
};

//导入
payRelationshipBuy.doImportBuy = function(type) {
	if(type == 1){
		pe_util.doImport('按单据导入.xlsx','/pay_relationship/do_import_discount_buy?type=1',1,payRelationshipBuy.importCallBack);
	}else if(type == 2){
		pe_util.doImport('按货号导入.xlsx','/pay_relationship/do_import_discount_buy?type=2',1,payRelationshipBuy.importCallBack);
	}
};

//导入回调
payRelationshipBuy.importCallBack = function(data){
	if(data.indexOf('"success":true')!=-1){
		showSuc("操作完成！");
		payRelationshipBuy.search();
	}else{
		showError("数据不合法,导入失败!");
	}
};

//导出
payRelationshipBuy.doExport = function(){
	pe_util.doExport('dtlDataGrid', '/pay_relationship/export_data', '总部收购核对',{searchType:'item'});
};

//清空
payRelationshipBuy.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("tbody").find("input").val("");
};

$(function(){
	
});
