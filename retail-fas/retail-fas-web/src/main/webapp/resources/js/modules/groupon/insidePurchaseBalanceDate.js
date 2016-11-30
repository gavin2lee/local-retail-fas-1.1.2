
function InsideBalanceDateDialog() { 
	var $this = this;
}

function InsideBalanceDateEditor() {
	var $this = this;
	
	//校验数据
	this.checkSave = function(options, data) {
		
		var url = BasePath + "/inside_purchase_balance_date/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
	this.checkUpdate = function(options, rowIndex, rowData) {
		if(rowData.invoiceFlag == 1){
			showWarn("结算期已生成开票申请，不能修改！");
			return false;
 	    }
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.invoiceFlag == 1){
				showWarn("结算期已生成开票申请，不能删除！");
				delFlag = false;
				return false;
	 	    }
		});
		return delFlag;
	};

	this.invoiceFlagformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '未开票'}, {'value':'1', 'text':'已开票'}];
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
	$.fas.extend(InsideBalanceDateDialog, FasDialogController);
	$.fas.extend(InsideBalanceDateEditor, FasEditorController);
	dialog = new InsideBalanceDateDialog();
	dialog.init("/inside_purchase_balance_date", {
		dataGridId : "balanceDateDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "内购结算期导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new InsideBalanceDateEditor();
	editor.init("/inside_purchase_balance_date", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'balanceDateDataGrid',
		saveUrl : "/inside_purchase_balance_date/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			$.fas.setEditorVal({
				dataGridId : 'balanceDateDataGrid',
				rowIndex : rowIndex,
				field : 'balanceMonth',
				value : rowData.balanceMonth
			});
			var ed = $('#balanceDateDataGrid').datagrid('getEditor', {index:rowIndex,field:'companyNo'});
			$(ed.target).combobox('disable');
		},
		keyboard : true
	});
});
