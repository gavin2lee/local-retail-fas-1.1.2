//设置多选数据的函数
function multiSearchDatas(paramsNames, datagridId) {
		var checkedRows = $("#"+datagridId).datagrid("getChecked");
		var dataIds = "", dataTexts = "", dataNos = "";
		var hasNo = false;
		$.each(checkedRows, function(index, item) {
			var id = column_params.id, name = column_params.name, no = column_params.no;
    		dataIds += item[id];
    		dataTexts += item[name];
    		if(typeof no != 'undefined' && no != null) {
    			dataNos += item[no];
    			hasNo = true;
    		}
    		if(index < checkedRows.length - 1) {
    			dataIds += ",";
    			dataTexts += ",";
    		}
    		if(index < checkedRows.length - 1 && hasNo) {
    			dataNos += ",";
    		}
    	});  
    	var idObj = parent.document.getElementById(paramsNames.id);
    	if(typeof idObj != 'undefined' && idObj != null) {
    		idObj.value = dataIds;
    	}
    	var textObj = parent.document.getElementById(paramsNames.text);
    	if(typeof textObj != 'undefined' && textObj != null) {
    		textObj.value = dataTexts;
    	}
    	var noObj = parent.document.getElementById(paramsNames.no);
    	if(typeof noObj != 'undefined' && noObj != null) {
    		noObj.value = dataNos;
    	}
	}