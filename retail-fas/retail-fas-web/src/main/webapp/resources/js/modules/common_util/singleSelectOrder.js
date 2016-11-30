var singleSelectOrder = new Object();
var tbgrid = new Object();

singleSelectOrder.clear = function(){
	$('#singleSelectOrderForm').form("clear");
}

singleSelectOrder.search = function() {
	var params = $('#singleSelectOrderForm').form("getData");
	var url = BasePath + '/bill_sale_balance/select_sale_order';
	$( '#singleSelectOrderDG').datagrid( 'options' ).queryParams= params;
	$( '#singleSelectOrderDG').datagrid( 'options' ).url= url;
	$( '#singleSelectOrderDG').datagrid( 'load' );
};

$(function(){
	tbgrid = $('#singleSelectOrderDG').datagrid({
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
		},{
			field : 'billDate',
			title : '单据日期',
			width : 100
		}, {
			field : 'sendQty',
			title : '数量',
			width : 100,
			align : 'right'
		}, {
			field : 'amount',
			title : '金额',
			width : 100,
			align : 'right'
		}, {
			field : 'rebateAmount',
			title : '返利金额',
			width : 100,
			align : 'right'
		}, {
			field : 'termName',
			title : '条款',
			width : 120,
			align : 'right'
		}, {
			field : 'preOrderAmount',
			title : '订货预收款',
			width : 100,
			align : 'right'
		}, {
			field : 'preSendAmount',
			title : '发货预收款',
			width : 100,
			align : 'right'
		}, {
			field : 'salerName',
			title : '公司',
			width : 180
		}, {
			field : 'buyerName',
			title : '客户',
			width : 180
		}]]
	});
});