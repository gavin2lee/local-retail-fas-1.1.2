var billBalanceInvoiceBase = new Object();

billBalanceInvoiceBase.modulePath = BasePath + '/bill_balance_invoice_apply';

billBalanceInvoiceBase.invoiceApplyCateDataGrid ='invoiceCateDateGrid';
billBalanceInvoiceBase.invoiceApplyDtlDataGrid ='invoiceDtlDataGrid';

billBalanceInvoiceBase.invoiceApplyCateTabUrl = billBalanceInvoiceBase.modulePath + '/bill_balance_invoice_cate';
billBalanceInvoiceBase.invoiceApplyDtlTabUrl = billBalanceInvoiceBase.modulePath + '/bill_balance_invoice_dtl';

//初始化
$(function() {
	billBalanceInvoiceBase.invoiceAppDtlTab();
});

//初始化明细Tab
billBalanceInvoiceBase.invoiceAppDtlTab = function() {
	billBalanceInvoiceBase.addInvoiceAppDtlTab('按大类显示',billBalanceInvoiceBase.invoiceApplyCateTabUrl);
	billBalanceInvoiceBase.addInvoiceAppDtlTab('按明细显示',billBalanceInvoiceBase.invoiceApplyDtlTabUrl);
	$('#invoiceAppDtlTab').tabs(
			{
				onSelect : function(title) {
					if ('按大类显示' == title) {
						billBalanceInvoiceBase.loadDtlDataGrid(
								'',billBalanceInvoiceBase.invoiceApplyCateDataGrid, billBalanceInvoiceBase.invoiceApplyCateTabUrl,'');
					} else if ('按明细显示' == title) {
						billBalanceInvoiceBase.loadDtlDataGrid(
								'',billBalanceInvoiceBase.invoiceApplyDtlDataGrid,billBalanceInvoiceBase.invoiceApplyDtlTabUrl,'');
					} 
				}
			});
	returnTab('dtlTab', '基本信息');
};

//添加明细Tab
billBalanceInvoiceBase.addInvoiceAppDtlTab = function(title, href) {
	$('#invoiceAppDtlTab').tabs('add', {
		title : title,
		selected : false,
		closable : false,
		href : href
	});
};

billBalanceInvoiceBase.loadDtlDataGrid=function(billNo,dataGrid,url,billType){
	if(billNo==0)
		{
     		return ;
		}
	var params = {billNo : billNo};
	var url = url + '/list.json';
    $( '#'+dataGrid).datagrid( 'options').queryParams= params;
    $( '#'+dataGrid).datagrid( 'options').url=url;
    $( '#'+dataGrid).datagrid( 'load' );
};

billBalanceInvoiceBase.editInvoiceSource = function(){
};

billBalanceInvoiceBase.editInvoiceInfo = function(){
};

