var multiSelectBalance = new Object();
multiSelectBalance.search = function(){
	var paramsString = $('#params').val();
	var params = $('#multiSelectBalanceForm').form('getData');
	var url = BasePath + '/bill_balance/list.json?' + paramsString;
	$( '#multiSelectBalanceDG').datagrid( 'options' ).queryParams= params;
	$( '#multiSelectBalanceDG').datagrid( 'options' ).url= url;
	$( '#multiSelectBalanceDG').datagrid( 'load' );
}

multiSelectBalance.clear = function(){
	$('#multiSelectBalanceForm').form("clear");
}

multiSelectBalance.rowData;

multiSelectBalance.dbClick = function(rowData){
	multiSelectBalance.rowData = rowData;
	var sure = parent.document.getElementById('sure');
	$(sure).click();
}

function getRowData(){
	return multiSelectBalance.rowData;
}

function getCheckRows(){
	return $('#multiSelectBalanceDG').datagrid('getChecked');
}

$(function(){
	$('#balanceType').combobox('select',1);
});