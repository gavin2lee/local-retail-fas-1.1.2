var shopBalanceResult = new Object();
var $this = this;
//查询
shopBalanceResult.search = function() {
	if(!$('#searchForm').form('validate')) {
		return;
	}
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/mall_shopbalance/shopbalance_resultlist.json';
    $('#shopBalanceResultDataGrid').datagrid('options').queryParams= params;
    $('#shopBalanceResultDataGrid').datagrid('options').url= url;
    $('#shopBalanceResultDataGrid').datagrid('load');
};

//清空
shopBalanceResult.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
//	shopBalanceResult.initDate(); 
};

shopBalanceResult.initCombobox = function(){
	
};

//导出
shopBalanceResult.exportExcel = function() {
	exportshopBalanceResult();
};

// 初始化
$(function(){
	shopBalanceResult.initCombobox();
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	if(month == 0) {
		year = year - 1;
		month = 12;
	}
	var month = month < 10 ? '0' + month : month;
	$('#saleStartDate').datebox('setValue', year + '-' + month + '-01');
	var  day = new Date(year,month,0);
	$('#saleEndDate').datebox('setValue', year + '-' + month + '-' + day.getDate()); 
});