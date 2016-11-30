var singleSelectInvoice = new Object();
var tbgrid = new Object();

singleSelectInvoice.clear = function(){
	$('#singleSelectInvoiceForm').form("clear");
}

singleSelectInvoice.search = function() {
	var paramsString = $('#params').val();
	var params = $('#singleSelectInvoiceForm').form('getData');
	var url = BasePath + '/bill_invoice/list.json?' + paramsString;
	$( '#singleSelectInvoiceDG').datagrid( 'options' ).queryParams= params;
	$( '#singleSelectInvoiceDG').datagrid( 'options' ).url= url;
	$( '#singleSelectInvoiceDG').datagrid( 'load' );
};

$(function(){
	tbgrid = $('#singleSelectInvoiceDG').datagrid({
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
			field : 'statusName',
			title : '单据状态',
			width : 100,
			align : 'left'
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
			field : 'billDate',
			title : '单据日期',
			width : 100,
			align : 'left'
		}, {
			field : 'amount',
			title : '发票金额',
			width : 100
		}]]
	});
});