var createBalance = new Object();

//加载明细
createBalance.loadDtl = function() {
	if($('#createBalanceForm').form('validate')){
		var params = $('#createBalanceForm').form('getData');
		var url = BasePath + '/bill_balance/hq/enter_list.json';
		params.queryCondition = " AND (balance_no IS NULL OR balance_no = '')";
	    $('#createBalanceDG').datagrid('options').queryParams= params;
	    $('#createBalanceDG').datagrid('options').url= url;
	    $('#createBalanceDG').datagrid('load');
	}
};

//清空表单
createBalance.clear = function() {
	if($('#createBalanceDG').datagrid('getRows').length > 0){
		$.messager.confirm("确认","清空表单将会清空明细内容,确定继续?",function(r) {
			if(r){
				$('#createBalanceForm').find("input[class*=disableEdit]").attr("readonly",false).removeClass("readonly");
				$('#balanceStartDate,#balanceEndDate').datebox('enable');
	    		$("#createBalanceForm").find("input[singleSearch]").combogrid('enable');
				$('#createBalanceForm').form('clear');
				$('#createBalanceDG').datagrid('loadData',{total:0,rows:[]}); 
			}
		});
	}else{
		$('#createBalanceForm').form('clear');
	}
};

// 表单验证
function validateBalance(){
	if($('#createBalanceForm').form('validate')){
		var checkedRows = getCheckBill();
		if(checkedRows.length > 0){
			return true;
		}else{
			showWarn("请选择关联单据!");
			return false;
		}
		
	}
	return false;
}

//获取表单信息
function getMainData() {
	var name = $('#multiBrandUnitName').combogrid('getText');
	var no = $('#createBalanceForm').find('input[name=multiBrandUnitNo]').val();
	if(no!=null && no!=""){
		no = no.replaceAll("(", "").replaceAll(")","");
	}
	$('#brandUnitNo').val(no);
	$('#brandUnitName').val(name);
	return $('#createBalanceForm').form('getData');
};

//获取选择数据
function getCheckBill() { 
	return $('#createBalanceDG').datagrid('getChecked');
};

// 初始化
$(function(){
	$('#currency').combobox('setValue',0);
	$("#balanceDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$('#createBalanceDG').datagrid({
		onLoadSuccess : function(data){
			if($('#createBalanceDG').datagrid('getRows').length > 0){
				$('#createBalanceForm').find("input[class*=disableEdit]").attr("readonly",true).addClass("readonly");
				$('#balanceStartDate,#balanceEndDate').datebox('disable');
	    		$("#createBalanceForm").find("input[singleSearch]").combogrid('disable');
			}
		}
	});
});
