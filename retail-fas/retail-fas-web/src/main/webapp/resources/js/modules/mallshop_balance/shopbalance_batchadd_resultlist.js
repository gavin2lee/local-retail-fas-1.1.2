var shopBalanceBatchAddResult = new Object();


// 主表模块路径
shopBalanceBatchAddResult.modulePath = BasePath + '/mall_shopbalance/shopbalance_batchadd_resultlist';


//查询
shopBalanceBatchAddResult.search = function() {
	var params = $('#searchForm').form('getData');
	var url = shopBalanceBatchAddResultListDataGrid.modulePath;
    $('#shopBalanceBatchAddResultListDataGrid').datagrid('options').queryParams= params;
    $('#shopBalanceBatchAddResultListDataGrid').datagrid('options').url= url;
    $('#shopBalanceBatchAddResultListDataGrid').datagrid('load');
};   
    
function loadData(data){
//	alert(JSON.stringify(data));
	$('#shopBalanceBatchAddResultListDataGrid').datagrid('loadData',data);
	
}
// 初始化
$(function(){
});
