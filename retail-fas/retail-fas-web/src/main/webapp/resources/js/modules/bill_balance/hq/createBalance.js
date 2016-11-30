var createBalance = new Object();

// 加载明细
createBalance.loadDtl = function() {
	if ($('#createBalanceForm').form('validate')) {
		var params = $('#createBalanceForm').form('getData');
		var url = BasePath + '/bill_balance/hq/enter_list.json';
		params.queryCondition = " AND (balance_no IS NULL OR balance_no='') AND cost != 0";
		if(params.balanceType == '1'){
			ajaxRequestAsync(BasePath + '/bill_balance/hq/get_pricing',null,function(result){
				var pricingObj = result.obj;
				if(pricingObj){
					if(pricingObj.isStart ==1){
						params.priceRangeCondition  = pricingObj.queryCondition; 
					}
				}
			});
		}
		$('#createBalanceDG').datagrid('options').queryParams = params;
		$('#createBalanceDG').datagrid('options').url = url;
		$('#createBalanceDG').datagrid('load');
	}
};

// 清空表单
createBalance.clear = function() {
	if ($('#createBalanceDG').datagrid('getRows').length > 0) {
		$.messager.confirm("确认", "清空表单将会清空明细内容,确定继续?", function(r) {
			if (r) {
				$('#createBalanceForm').find("input[class*=disableEdit]").attr("readonly", false).removeClass("readonly");
				$('#balanceStartDate,#balanceEndDate').datebox('enable');
				$("#createBalanceForm").find("input[singleSearch]").combogrid('enable');
				$('#createBalanceForm').form('clear');
				$('#createBalanceFrom').find("input[name=brandUnitNo]").val("");
				$('#createBalanceFrom').find("input[name=multiOrganNo]").val("");
				$('#createBalanceFrom').find("input[name=multiOrderUnitNo]").val("");
				$('#createBalanceFrom').find("input[name=brandUnitName]").combogrid('clear');
				common_util.initDate();
				$('#createBalanceDG').datagrid('loadData', {total : 0,rows : []});
			}
		});
	} else {
		$('#createBalanceForm').form('clear');
		$('#createBalanceFrom').find("input[name=brandUnitNo]").val("");
		$('#createBalanceFrom').find("input[name=multiOrganNo]").val("");
		$('#createBalanceFrom').find("input[name=multiOrderUnitNo]").val("");
		$('#createBalanceFrom').find("input[name=brandUnitName]").combogrid('clear');
		common_util.initDate();
	}
};

// 表单验证
function validateBalance() {
	if ($('#createBalanceForm').form('validate')) {
		var checkedRows = getCheckBill();
		if (checkedRows.length > 0) {
			return true;
		} else {
			showWarn("请选择关联单据!");
			return false;
		}

	}
	return false;
}

// 获取表单信息
function getMainData() {
	return $('#createBalanceForm').form('getData');
};

// 获取选择数据
function getCheckBill() {
	return $('#createBalanceDG').datagrid('getChecked');
};

// 初始化
$(function() {
	$('#currency').combobox('setValue', 0);
	$('#createBalanceDG').datagrid({
		onLoadSuccess : function(data) {
			if ($('#createBalanceDG').datagrid('getRows').length > 0) {
				$('#createBalanceForm').find("input[class*=disableEdit]").attr("readonly",true).addClass("readonly");
				$('#balanceStartDate,#balanceEndDate').datebox('disable');
				$("#createBalanceForm").find("input[singleSearch]").combogrid('disable');
			}
		}
	});
});
