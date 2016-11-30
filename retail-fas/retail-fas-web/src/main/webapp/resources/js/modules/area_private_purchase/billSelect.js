var billSelect={};

$(function(){
	$("#balanceDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
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
		var $diff=$("#withoutDiff").val();
		params["balanceNoFlag"]=$balanceNo;
		params["withoutDiff"]=$diff;
		var url = BasePath + '/area_private_purchase/list.json?';
		$('#selectBillDG').datagrid('options').queryParams = params;
		$('#selectBillDG').datagrid('options').url = url;
		$('#selectBillDG').datagrid('load');
		
		$("#searchForm").find("input[class*=easyui-combobox]").combobox('disable');
		$("#searchForm").find("input[class*=easyui-datebox]").datebox('disable');
		$("#companyNameId,#supplierNameId,#brandUnitNameId,#organNameId,#orderUnitNameId").combogrid("disable");
	}
};

billSelect.clear = function() {
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
	$("#companyNameId,#supplierNameId,#brandUnitNameId,#organNameId,#orderUnitNameId").combogrid("enable");
	$("#balanceDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
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
	return $('#searchForm').form('getData');
};
