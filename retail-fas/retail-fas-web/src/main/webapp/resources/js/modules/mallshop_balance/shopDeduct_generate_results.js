var shopDeductGenerateResult = new Object();

// 主表模块路径
shopDeductGenerateResult.modulePath = BasePath + '/mall_shopbalance/shopdeduct_generate_results';

//查询
shopDeductGenerateResult.search = function() {
	var params = $('#searchForm').form('getData');
	var url = shopDeductGenerateResult.modulePath;
    $('#shopDeductGenerateListDataGrid').datagrid('options').queryParams= params;
    $('#shopDeductGenerateListDataGrid').datagrid('options').url= url;
    $('#shopDeductGenerateListDataGrid').datagrid('load');
};   
    
function loadData(data){
//	alert(JSON.stringify(data));
	$('#shopDeductGenerateListDataGrid').datagrid('loadData',data);
	
}
// 初始化
$(function(){
});
