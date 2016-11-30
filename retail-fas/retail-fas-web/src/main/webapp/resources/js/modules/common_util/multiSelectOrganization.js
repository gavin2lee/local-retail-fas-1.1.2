var multiSelectOrganization = new Object();
multiSelectOrganization.search = function(){
	var paramsString = $('#params').val();
	var params = $('#multiSelectOrganizationForm').form('getData');
	var url = BasePath + '/common_util/getOrganization?' + paramsString;
	$( '#multiSelectOrganizationDG').datagrid( 'options' ).queryParams= params;
	$( '#multiSelectOrganizationDG').datagrid( 'options' ).url= url;
	$( '#multiSelectOrganizationDG').datagrid( 'load' );
}

multiSelectOrganization.clear = function(){
	$('#organizationNo,#organizationName').val("");
}

multiSelectOrganization.rowData;

multiSelectOrganization.dbClick = function(rowData){
	multiSelectOrganization.rowData = rowData;
	var sure = parent.document.getElementById('sure');
	$(sure).click();
}

function getRowData(){
	return multiSelectOrganization.rowData;
}

function getCheckRows(){
	return $('#multiSelectOrganizationDG').datagrid('getChecked');
}

$(function(){
	$('#multiNo,#multiName').keyup(function(){
		multiSelectOrganization.search();
	});
	$('#organizationType').combobox('select',1);
});
