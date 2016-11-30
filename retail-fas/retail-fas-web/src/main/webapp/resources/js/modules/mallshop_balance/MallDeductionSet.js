function MallDeductionSetDialog() { 
	var $this = this;

	this.checkEnable = function(checkedRows) {
		var enableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("存在扣费类别状态为启用的记录，请重新选择！");
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
				showWarn("存在扣费类别状态为停用的记录，请重新选择！");
				unableFlag = false;
				return false;
	 	    }
		});
		return unableFlag;
	};
}

function MallDeductionSetEditor() {
	var $this = this;
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/mall_deduction_set/validate_data";
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
		var systemInit = rowData.systemInit;
	    if(systemInit == '0' ){
			showWarn("系统初始参数设置,不能修改");
		   return false;
		}
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
		    if(row.systemInit == '0' ){
				showWarn("系统初始参数设置,不能删除");
				delFlag = false;
			   return false;
			}
			if(row.status == '1'){
	      		showWarn("已启用，不能删除！");
	      		delFlag = false;
	      		return false;
      	    }
		});
		return delFlag;
	};
	this.statusformatter = function(value, rowData, rowIndex) {
		var status = [{"value" : 0, "text" : "停用"}, {"value" : 1, "text" : "启用"}];
		for(var i = 0; i < status.length; i++) {
			if(status[i].value == value) {
				return status[i].text;
			}
		}
		return "";
	}
	this.debitedRentalformatter = function(value, rowData, rowIndex) {
		var debitedRental = [{"value" : 0, "text" : "否"}, {"value" : 1, "text" : "是"}];
		for(var i = 0; i < debitedRental.length; i++) {
			if(debitedRental[i].value == value) {
				return debitedRental[i].text;
			}
		}
		return "";
	}
	
}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(MallDeductionSetDialog, FasDialogController);
	$.fas.extend(MallDeductionSetEditor, FasEditorController);
	dialog = new MallDeductionSetDialog();
	dialog.init("/mall_deduction_set", {
		dataGridId : "mallDeductionDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable",
		exportTitle : "商场扣费类别列表导出",
		exportUrl : "/do_fas_export"
		
	});
	
	editor = new MallDeductionSetEditor();
	editor.init("/mall_deduction_set", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'mallDeductionDataGrid',
		saveUrl : "/mall_deduction_set/save",
		searchBtn : "btn-search",
		keyboard : true
	});
});
