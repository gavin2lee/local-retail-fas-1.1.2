var singleSelectBalance = new Object();
var tbgrid = new Object();

singleSelectBalance.clear = function(){
	$('#billNo').val("");
	$('#balanceStartDate').datebox("setValue","");
	$('#balanceEndDate').datebox("setValue","");
}

singleSelectBalance.search = function() {
	var paramsString = $('#params').val();
	var balanceType = $('#balanceType').combobox('getValue');
	if(balanceType == 1){
		paramsString += '&status=2'
	}else if(balanceType == 5){
		paramsString += '&multiStatus=(4,6)'
	}else if(balanceType == 6){
		paramsString += '&status=2'
	}
	var params = $('#singleSelectBalanceForm').form('getData');
	var url = BasePath + '/bill_balance/list.json?' + paramsString;
	$( '#singleSelectBalanceDG').datagrid( 'options' ).queryParams= params;
	$( '#singleSelectBalanceDG').datagrid( 'options' ).url= url;
	$( '#singleSelectBalanceDG').datagrid( 'load' );
};

$(function(){
	tbgrid = $('#singleSelectBalanceDG').datagrid({
		pageSize : 20,
		singleSelect : true,
		columns : [[
		{
			title : '',
			field : 'col1',
			width : 30,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'billNo',
			title : '单据编号',
			width : 120
		}, {
			field : 'buyerName',
			title : '公司',
			width : 180
		}, {
			field : 'salerName',
			title : '供应商',
			width : 180,
			align : 'left'
		}, {
			field : 'balanceStartDate',
			title : '结算开始日期',
			width : 120
		}, {
			field : 'balanceEndDate',
			title : '结算结束日期',
			width : 120,
			align : 'left'
		}, {
			field : 'balanceAmount',
			title : '应付金额',
			width : 100,
			align : 'left'
		}]]
	});
	$('#balanceType').combobox('select',1);
});