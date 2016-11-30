function priceSetDialog() { 
	var $this = this;
	
}

function priceSetEditor() {
	var $this = this;
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		if (checkedRows.length < 1) {
			showInfo("请先选择要删除的记录！");
			return;
		}else{
			return true;
		}
	};
}

var priceSet = null, editor = null;
$(function() {
	$.fas.extend(priceSetDialog, FasDialogController);
	$.fas.extend(priceSetEditor, FasEditorController);
	priceSet = new priceSetDialog();
	priceSet.init("/price_control/effective_date", {
		dataGridId : "priceDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json?",
		searchFormId : "searchForm"
	});
	
	editor = new priceSetEditor();
	editor.init("/price_control/effective_date", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'priceDataGrid',
		saveUrl : "/price_control/effective_date/save",
		searchBtn : "btn-search",
		keyboard : true
	});
});
