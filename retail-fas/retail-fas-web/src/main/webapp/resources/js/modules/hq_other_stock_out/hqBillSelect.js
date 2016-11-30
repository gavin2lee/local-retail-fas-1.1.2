var billSelect= new BillObj({
	startDateId:'sendDateStart',
	endDataId:'sendDateEnd'}, {});

$(function(){
	$("#balanceDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#sendDateStart").datebox('setValue',billSelect.getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',billSelect.getEndDate().format("yyyy-MM-dd"));
	billSelect.setCurrency();
});

//设置币种默认值
billSelect.setCurrency=function(){
	$("#currencyNameId").combobox({
		onLoadSuccess:function(){
			var currencyData = $("#currencyNameId").combobox("getData");
			for ( var i = 0; i < currencyData.length; i++) {
				if(currencyData[i]["standardMoney"]==1){
					$("#currencyNameId").combobox("setValue",currencyData[i]["currencyName"]);
					$("#currencyId").val(currencyData[i]["currencyCode"]);
				}
			}
		}
	});
};

billSelect.search=function(){
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var $balanceNo=$("#balanceNo").val();
		var $brandNo=$("#brandNoId").val();
		params["balanceNoFlag"]=$balanceNo;
		params["brandNoFlag"]=$brandNo;
		var url = BasePath + '/hq_other_stock_out_dtl/list.json?params=groupLeadRole';
		$('#selectBillDG').datagrid('options').queryParams = params;
		$('#selectBillDG').datagrid('options').url = url;
		$('#selectBillDG').datagrid('load');
		
		$("#searchForm").find("input[class*=easyui-combobox]").combobox('disable');
		$("#searchForm").find("input[class*=easyui-datebox]").datebox('disable');
		$("#salerNameId,#buyerNameId").combogrid("disable");
	}
};

billSelect.clear=function(){
	if ($('#selectBillDG').datagrid('getRows').length > 0) {
		$.messager.confirm("确认", "清空表单将会清空明细内容,确定继续?", function(r) {
			if (r) {
				billSelect.setFormClass();
				$('#selectBillDG').datagrid('loadData', {
					total : 0,
					rows : []
				});
			}
		});
	} else {
		billSelect.setFormClass();
	}
};

/** 
 * 设置表单样式 
 */
billSelect.setFormClass = function() {
	$('#searchForm').form('clear');
	$("#searchForm").find("input[class*=easyui-combobox]").combobox('enable');
	$("#searchForm").find("input[class*=easyui-datebox]").datebox('enable');
	$("#salerNameId,#buyerNameId").combogrid("enable");
	$("#balanceDate").datebox('setValue', new Date().format("yyyy-MM-dd"));
	billSelect.setCurrency();
};

function validateBalance(){
	if($('#searchForm').form('validate')){
		var checkedRows = getCheckBill();
		if(checkedRows.length > 0){
			return true;
		}else{
			showInfo("请先勾选单据!");
			return false;
		}
		
	}
	return false;
};

function getCheckBill() {
	return $('#selectBillDG').datagrid('getChecked');
};

function getMainData(){
	var params=$('#searchForm').form('getData');
	params.brandName=$('#brandNameTemp').val();
	return params;
};
