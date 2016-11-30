
function InvoiceRuleSetDialog() { 
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

function InvoiceRuleSetEditor() {
	var $this = this;
	
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/invoice_rule_set/validate_data";
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
			showWarn("开票规则已启用，不能修改！");
			return false;
 	    }
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("开票规则已启用，不能删除！");
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
	
	this.changeBillingMethod = function(data) {
		var edBsgroups = $('#' + $this.options.dataGridId).datagrid('getEditor', {index:$.fas.editIndex,field:'bsgroupsName'});
		var edMalls = $('#' + $this.options.dataGridId).datagrid('getEditor', {index:$.fas.editIndex,field:'mallName'});
		
		if(data.value == '1') {
			$(edBsgroups.target).combobox('enable');
			$(edMalls.target).combobox('disable');
			$(edMalls.target).combobox('clear');
			$("#mallNo_").val('');
		} else if (data.value == '2' || data.value == '3' ) {
			$(edMalls.target).combobox('enable');
			$(edBsgroups.target).combobox('disable');
			$(edBsgroups.target).combobox('clear');
			$("#bsgroupsNo").val('');
		} 
	};
	
	this.dataBillingMethod = function(value, rowData, rowIndex) {
		var billingMethod = [{"value" : "1","text" : "按商业集团开票"}, {"value" : "2","text" : "按商场开票"}, {"value" : "3","text" : "按商场+店铺开票"}];
		for(var i = 0; i < billingMethod.length; i++) {
	        if(billingMethod[i].value == value) {
	            return billingMethod[i].text;
	        }
	    }
	    return "";
	};
}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(InvoiceRuleSetDialog, FasDialogController);
	$.fas.extend(InvoiceRuleSetEditor, FasEditorController);
	dialog = new InvoiceRuleSetDialog();
	dialog.init("/invoice_rule_set", {
		dataGridId : "invoiceRuleSetDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable",
		searchFormId : "searchForm",
		exportTitle : "开票申请规则导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new InvoiceRuleSetEditor();
	editor.init("/invoice_rule_set", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'invoiceRuleSetDataGrid',
		saveUrl : "/invoice_rule_set/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			var edBsgroups = $('#invoiceRuleSetDataGrid').datagrid('getEditor', {index:rowIndex,field:'bsgroupsName'});
			var edMalls = $('#invoiceRuleSetDataGrid').datagrid('getEditor', {index:rowIndex,field:'mallName'});
			
			if(rowData.billingMethod == '1') {
				$(edBsgroups.target).combobox('enable');
				$(edMalls.target).combobox('disable');
				$(edMalls.target).combobox('clear');
				$("#mallNo_").val('');
			} else if (rowData.billingMethod == '2' || rowData.billingMethod == '3' ) {
				$(edMalls.target).combobox('enable');
				$(edBsgroups.target).combobox('disable');
				$(edBsgroups.target).combobox('clear');
				$("#bsgroupsNo").val('');
			} 
		},
		keyboard : true
	});
});
