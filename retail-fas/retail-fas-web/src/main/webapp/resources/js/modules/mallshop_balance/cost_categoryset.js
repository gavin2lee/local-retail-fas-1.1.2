function CostCategorySetDialog() { 
	var $this = this;

	this.checkEnable = function(checkedRows) {
		var enableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("存在类别状态为启用的记录，请重新选择！");
				enableFlag = false;
				return false;
	 	    }
		});
		return enableFlag;
	};
	
	this.checkUnable = function(checkedRows) {
		var unableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '0'){
				showWarn("存在类别状态为停用的记录，请重新选择！");
				unableFlag = false;
				return false;
	 	    }
		});
		return unableFlag;
	};
	
}

function CostCategorySetEditor() {
	var $this = this;
	
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/cost_category/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
	this.checkUpdate = function(options, rowIndex, rowData) {
		if(rowData.status == '1'){
			showWarn("总账费用类别已启用，不能修改！");
			return false;
 	    }
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("总账费用类别已启用，不能删除！");
				delFlag = false;
				return false;
	 	    }
		});
		return delFlag;
	};

	this.statusformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '停用'}, {'value':'1', 'text':'启用'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
	
}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(CostCategorySetDialog, FasDialogController);
	$.fas.extend(CostCategorySetEditor, FasEditorController);
	dialog = new CostCategorySetDialog();
	dialog.init("/cost_category", {
		dataGridId : "costCategoryDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable",
		searchFormId : "searchForm",
		exportTitle : "总账费用类别导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new CostCategorySetEditor();
	editor.init("/cost_category", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'costCategoryDataGrid',
		saveUrl : "/cost_category/save",
		searchBtn : "btn-search",
		keyboard : true
	});
});