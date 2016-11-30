var supplierTariffSet = new Object();
function SupplierTariffSetDialog() { 
	var $this = this;
}
function SupplierTariffSetEditor() {
	var $this = this;
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/supplier_tariff_set/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
}

function selectChange(id) {
	$("#searchForm").find(':checkbox').each(function () {
        if(this.id != id ){
        	$(this).attr("checked",false);
        }
	});
}

var dialog1 = null;
$(function() {
	$.fas.extend(SupplierTariffSetDialog, FasDialogController);
	$.fas.extend(SupplierTariffSetEditor, FasEditorController);
	dialog1 = new SupplierTariffSetDialog();
	dialog1.init("/supplier_tariff_set", {
		dataGridId : "supplierDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "供应商关税设置导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	});
	dialog1.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/supplier_tariff_set/do_import',
			'templateName' : '供应商关税设置模板.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showInfo('数据导入成功');
						dialog1.search();
					}
				} else {
					showWarn('导入失败,请联系管理员!');
				}
			},
			error : function() {
				$.messager.progress('close');
				showWarn('数据导入失败，请联系管理员');
			}
		});
	};
	
	editor = new SupplierTariffSetEditor();
	editor.init("/supplier_tariff_set", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'supplierDataGrid',
		saveUrl : "/supplier_tariff_set/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			$("#supplierName_").combobox('disable');
		},
		keyboard : true
	});
});
//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if ('0' == obj) {
			return true;
		}
		return false;
	}
	return true;
};