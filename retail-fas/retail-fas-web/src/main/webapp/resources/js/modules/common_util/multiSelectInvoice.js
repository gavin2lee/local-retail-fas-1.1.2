var multiSelectInvoice = new Object();
multiSelectInvoice.search = function(){
	var paramsString = $('#params').val();
	var params = $('#multiSelectInvoiceForm').form('getData');
	var url = BasePath + '/bill_invoice/list.json?' + paramsString;
	$( '#multiSelectInvoiceDG').datagrid( 'options' ).queryParams= params;
	$( '#multiSelectInvoiceDG').datagrid( 'options' ).url= url;
	$( '#multiSelectInvoiceDG').datagrid( 'load' );
}

multiSelectInvoice.clear = function(){
	$('#multiSelectInvoiceForm').form("clear");
}

multiSelectInvoice.rowData;

multiSelectInvoice.dbClick = function(rowData){
	multiSelectInvoice.rowData = rowData;
	var sure = parent.document.getElementById('sure');
	$(sure).click();
}

function getRowData(){
	return multiSelectInvoice.rowData;
}

function getCheckRows(){
	return $('#multiSelectInvoiceDG').datagrid('getChecked');
}
