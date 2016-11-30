
function paymentTermDialog() { 
	var $this = this;
	this.checkEnable = function(checkedRows) {
		var enableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("数据已启用，不允许重复操作！");
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
				showWarn("数据已停用，不允许重复操作！");
				unableFlag = false;
				return false;
	 	    }
		});
		return unableFlag;
	};
}

function paymentTermEditorEditor() {
	var $this = this;
	
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/base_setting/payment_term/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showWarn(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
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
	
	this.paymentTermEditorTypeFormatter = function(value, rowData, rowIndex) {
		for(var i = 0; i < clientData.length; i++) {
			if(clientData[i].id == value) {
				return clientData[i].typeName;
			}
		}
		return "";
	}
}

var dialog = null, editor = null;
var clientData=new Object();
$(function() {
	$.fas.extend(paymentTermDialog, FasDialogController);
	$.fas.extend(paymentTermEditorEditor, FasEditorController);
	dialog = new paymentTermDialog();
	dialog.init("/base_setting/payment_term", {
		dataGridId : "dataGridJG",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable"
	});
	
	editor = new paymentTermEditorEditor();
	editor.init("/base_setting/payment_term", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'dataGridJG',
		saveUrl : "/base_setting/payment_term/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
		},
		keyboard : true
	});
});
