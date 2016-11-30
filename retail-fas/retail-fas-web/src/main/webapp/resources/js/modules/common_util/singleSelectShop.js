var singleSelectShop = new Object();
var tbgrid = new Object();

singleSelectShop.clear = function(){
	$('#billNo').val("");
	$('#balanceStartDate').datebox("setValue","");
	$('#balanceEndDate').datebox("setValue","");
}

singleSelectShop.search = function() {
	var params = $('#singleSelectShopForm').form('getData');
	var url = BasePath + '/common_util/getPageShop';
	$( '#singleSelectShopDG').datagrid( 'options' ).queryParams= params;
	$( '#singleSelectShopDG').datagrid( 'options' ).url= url;
	$( '#singleSelectShopDG').datagrid( 'load' );
};

$(function(){
	tbgrid = $('#singleSelectShopDG').datagrid({
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
			field : 'companyName',
			title : '结算公司',
			width : 120
		}, {
			field : 'bizCityName',
			title : '经营城市',
			width : 180
		}, {
			field : 'mallName',
			title : '商场名称',
			width : 180,
			align : 'left'
		}, {
			field : 'shopName',
			title : '店铺名称',
			width : 120
		}, {
			field : 'address',
			title : '地址',
			width : 120,
			align : 'left'
		}]]
	});
});