
function costComputeRuleDialog() { 
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

function costComputeRuleEditor() {
	var $this = this;
	
	this.checkUpdate = function(options, rowIndex, rowData) {
		var status = rowData.status;
		if(status == '1' ){
			showWarn("已启用,不能修改");
			return false;
		}
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("已启用，不能删除！");
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
	
	this.computeRuleformatter = function(value, rowData, rowIndex) {
		var dataStatusType = [{'value':'1', 'text': '乘(*)'}, {'value':'2', 'text':'加(+)'}];
		for(var i = 0; i < dataStatusType.length; i++) {
			if(dataStatusType[i].value == value) {
				return dataStatusType[i].text;
			}
		}
		return "";
	};
}

var ruleSet = null, editor = null;
$(function() {
	$.fas.extend(costComputeRuleDialog, FasDialogController);
	$.fas.extend(costComputeRuleEditor, FasEditorController);
	ruleSet = new costComputeRuleDialog();
	ruleSet.init("/base_setting/cost_compute_rule", {
		dataGridId : "costComputeRuleDg",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable"
	});
	
	editor = new costComputeRuleEditor();
	editor.init("/base_setting/cost_compute_rule", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'costComputeRuleDg',
		saveUrl : "/base_setting/cost_compute_rule/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
		},
		keyboard : true
	});
});
