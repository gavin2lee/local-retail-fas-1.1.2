var multiSelect = new Object();
multiSelect.search = function(){
	var selectUrl = $('#selectUrl').val();
	var queryCondition = $('#queryCondition').val();
	var params = $('#multiSelectForm').form('getData');
	var url = BasePath + '/common_util/' + selectUrl + '?queryCondition=' +queryCondition;
	//var url = BasePath + '/common_util/' + selectUrl + queryCondition;
	$( '#multiSelectDG').datagrid( 'options' ).queryParams= params;
	$( '#multiSelectDG').datagrid( 'options' ).url= url;
	$( '#multiSelectDG').datagrid( 'load' );
}

multiSelect.clear = function(){
	$('#multiSelectForm').form("clear");
}





multiSelect.rowData;

multiSelect.dbClick = function(rowData){
	multiSelect.rowData = rowData;
	var sure = parent.document.getElementById('sure');
	$(sure).click();
}

function getRowData(){
	return multiSelect.rowData;
}

function getCheckRows(){
	return $('#multiSelectDG').datagrid('getChecked');
}

//初始化
$(function(){
	$('#multiNo,#multiName').keyup(function(){
		multiSelect.search();
	});
});