var balanceAdjust = new Object();

//查询
balanceAdjust.search = function() {
	var params = $('#searchForm').form('getData');
	var deudctionUrl = BasePath + '/other_deduction/list.json';
	var imperfectUrl = BasePath + '/custom_imperfect/list.json';
	var balanceNo = window.parent.document.getElementById("billNo").value;
	params.queryCondition = "AND (balance_no IS NULL OR balance_no = '' OR balance_no = '"+balanceNo+"')";
    $('#balanceAdjustDG').datagrid('options').queryParams= params;
    $('#balanceAdjustDG').datagrid('options').url= deudctionUrl;
    $('#balanceAdjustDG').datagrid('load');
    if(params.categoryNo && params.categoryNo!=''){
        params.oneLevelCategoryNo = params.categoryNo.substring(0,2);
        params.categoryNo = '';
    }
    $('#balanceAdjustDG1').datagrid('options').queryParams= params;
    $('#balanceAdjustDG1').datagrid('options').url= imperfectUrl;
    $('#balanceAdjustDG1').datagrid('load');
};

//清空
balanceAdjust.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val('');
	var parentForm = window.parent.document.getElementById("mainDataForm");
	var salerNo = $(parentForm).find("input[name=salerNo]").val();
	var buyerNo = $(parentForm).find("input[name=buyerNo]").val();
	var salerName = $(parentForm).find("input[name=salerName]").val();
	var buyerName = $(parentForm).find("input[name=buyerName]").val();
	$('#salerNo').val(salerNo);
	$('#buyerNo').val(buyerNo);
	$('#salerName').val(salerName);
	$('#buyerName').val(buyerName);
};

//获取选择数据
function getCheckBill() {
	var result = {deductionRows:$('#balanceAdjustDG').datagrid('getChecked')
			,imperfectRows:$('#balanceAdjustDG1').datagrid('getChecked')};
	return result;
};

// 初始化
$(function(){
	var parentForm = window.parent.document.getElementById("mainDataForm");
	var parentData = $(parentForm).form('getData');
	$('#searchForm').form('load',parentData);
	setTimeout(function(){
		balanceAdjust.search();
	},500);
	$('#balanceAdjustDG').datagrid({
		onLoadSuccess : function(data){
			var rows = $('#balanceAdjustDG').datagrid('getRows');
			$.each(rows,function(index, item){
				if(item.balanceNo && item.balanceNo !=""){
					$('#balanceAdjustDG').datagrid('checkRow',index);
				}
			});
		}
	});
	$('#balanceAdjustDG1').datagrid({
		onLoadSuccess : function(data){
			var rows = $('#balanceAdjustDG1').datagrid('getRows');
			$.each(rows,function(index, item){
				if(item.balanceNo && item.balanceNo !=""){
					$('#balanceAdjustDG1').datagrid('checkRow',index);
				}
			});
		}
	});
});
