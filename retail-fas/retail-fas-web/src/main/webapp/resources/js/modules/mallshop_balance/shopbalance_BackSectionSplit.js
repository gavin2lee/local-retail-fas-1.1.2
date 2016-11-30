var shopBalanceBackSectionSplit = new Object();
var $this = this;
//查询
shopBalanceBackSectionSplit.search = function() {
	if(!$('#searchForm').form('validate')) {
		return;
	}
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/mall_shopbalance/shopbalance_backsectionsplit_list.json';
    $('#shopBalanceBackSplDataGrid').datagrid('options').queryParams= params;
    $('#shopBalanceBackSplDataGrid').datagrid('options').url= url;
    $('#shopBalanceBackSplDataGrid').datagrid('load');
};

//清空
shopBalanceBackSectionSplit.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	shopBalanceBackSectionSplit.initDate(); 
};

shopBalanceBackSectionSplit.initDate = function(){
	
};

shopBalanceBackSectionSplit.initCombobox = function(){
	
};

//导出
shopBalanceBackSectionSplit.exportExcel = function() {
	exportshopBalanceBackSectionSplit();
};

//function CostCategorySetDialog() { 
//	var $this = this;	
//}
//var dialog = null, editor = null;
//$(function() {
//	$.fas.extend(CostCategorySetDialog, FasDialogController);
//	$.fas.extend(CostCategorySetEditor, FasEditorController);
//	dialog = new CostCategorySetDialog();
//	dialog.init("/cost_category", {
//		dataGridId : "costCategoryDataGrid",
//		searchBtn : "btn-search",
//		searchUrl : "/list.json",
//		enableUrl : "/do_enable",
//		unableUrl : "/do_unable",
//		searchFormId : "searchForm",
//		exportTitle : "总账费用类别导出",
//		exportUrl : "/do_fas_export"
//	});
	
// 初始化
$(function(){
	shopBalanceBackSectionSplit.initCombobox();
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
