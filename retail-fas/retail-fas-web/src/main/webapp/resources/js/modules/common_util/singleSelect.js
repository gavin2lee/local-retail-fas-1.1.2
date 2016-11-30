var singleSelect = new Object();
var tbgrid = new Object();
singleSelect.search = function() {
	var selectUrl = $('#selectUrl').val();
	var params = $('#singleSelectForm').form('getData');
	var url = BasePath + '/common_util/' + selectUrl;
	$( '#singleSelectDG').datagrid( 'options' ).queryParams= params;
	$( '#singleSelectDG').datagrid( 'options' ).url= url;
	$( '#singleSelectDG').datagrid( 'load' );
};

singleSelect.clear = function() {
	$('#singleSelectForm').form('clear');
};

$(function(){
	$('#multiNo,#multiName').keyup(function(){
		singleSelect.search();
	});
	tbgrid = $('#singleSelectDG').datagrid({
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
			field : 'code',
			title : '编码',
			width : 120
		}, {
			field : 'name',
			title : '名称',
			width : 250,
			align : 'left'
		}]]
	});
});