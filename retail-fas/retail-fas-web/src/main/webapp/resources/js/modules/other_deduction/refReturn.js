var refReturn = new Object();

//查询
refReturn.search = function() {
	var params = $('#searchForm').form('getData');
	var balanceNo = $('#balanceNo').val();
	if(balanceNo!='undefined'  && balanceNo!='null' && balanceNo!=''){
		params.queryCondition = "AND balance_no = '"+params.parentId+"'";
	}else{
		params.queryCondition = "AND (balance_no IS NULL OR balance_no = ''  OR balance_no = '"+params.parentId+"')";
	}
	var url = BasePath + '/bill_balance/hq/enter_list.json';
	$('#dtlDataGrid').datagrid('options').queryParams= params;
	$('#dtlDataGrid').datagrid('options').url= url;
	$('#dtlDataGrid').datagrid('load');
};

//清空
refReturn.clear = function() {
	$('#searchForm').form("clear");
};

function getParams(){
	var params = $('#searchForm').form('getData');
	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
	params.checkedRows = JSON.stringify(checkedRows);
	return params;
}

// 初始化
$(function(){
	setTimeout(function(){
		refReturn.search();
	},500);
	$('#dtlDataGrid').datagrid({
		onLoadSuccess : function(data){
			var rows = $('#dtlDataGrid').datagrid('getRows');
			$.each(rows,function(index, item){
				if(item.balanceNo && item.balanceNo !=""){
					$('#dtlDataGrid').datagrid('checkRow',index);
				}
			});
		}
	});
});
