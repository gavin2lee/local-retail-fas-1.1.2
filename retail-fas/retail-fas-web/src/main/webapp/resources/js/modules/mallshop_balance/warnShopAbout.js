
$(function(){
	setTimeout(function(){
		var warnPostUrl = $("#warnPostUrl").val();
		if(warnPostUrl != null && warnPostUrl != '') {
			warnPostUrl = warnPostUrl.replaceAll(":", "&");
			$("#mainDataGrid").datagrid("options").url=  BasePath + warnPostUrl;
			$('#mainDataGrid').datagrid("load");
		}
	}, 500);
});