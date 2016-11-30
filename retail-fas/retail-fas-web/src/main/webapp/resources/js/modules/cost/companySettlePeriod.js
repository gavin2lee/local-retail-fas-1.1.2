
function CompanySettlePeriodDialog() { 
	var $this = this;
}

function CompanySettlePeriodEditor() {
	var $this = this;
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/company_settle_period/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
//	this.checkDel = function(options) {
//		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
//		var delFlag = true;
//		$.each(checkedRows, function(index,row) {
//			if(row.status == '1'){
//				showWarn("开票规则已启用，不能删除！");
//				delFlag = false;
//				return false;
//	 	    }
//		});
//		return delFlag;
//	};
}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(CompanySettlePeriodDialog, FasDialogController);
	$.fas.extend(CompanySettlePeriodEditor, FasEditorController);
	dialog = new CompanySettlePeriodDialog();
	dialog.init("/company_settle_period", {
		dataGridId : "settlePeriodDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "公司结账日导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new CompanySettlePeriodEditor();
	editor.init("/company_settle_period", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'settlePeriodDataGrid',
		saveUrl : "/company_settle_period/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
//			var ed = $('#settlePeriodDataGrid').datagrid('getEditor', {index:rowIndex,field:'companyNo'});
			
			$("#companyNo_").combobox('disable');
		},
		keyboard : true
	});
});