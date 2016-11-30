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
// 弹出框
function SupplierRateSetDialog() {
	var $this = this;
	$this.doImport = function(){
		$.importExcel.open({
			'submitUrl' : BasePath + '/supplier_rate_set/do_import',
			'templateName' : '供应商设置导入模板.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
						$this.search();
					}
				} else {
					showInfo('导入失败,请联系管理员!');
				}
			},
			error : function() {
				$.messager.progress('close');
				showWarn('数据导入失败，请联系管理员');
			}
		});
		this.exportExcel = function() {
			$.fas.exportExcel( {
				dataGridId : "dataGridDiv",
				exportTitle : "供应商设置导出",
				exportUrl : "/export"
			});
		};
	};
}

//Import


// 行编辑
function SupplierRateSetSetEditor() {
	this.checkSave = function(options) {
		return $.fas.checkRowUnique({dataGridId:options.dataGridId,uniqueField:"supplierNo"});
	}
}

var supplierRateSetDialog = null, 
	supplierRateSetEditor = null;

$(function() {
	$.fas.extend(SupplierRateSetDialog, FasDialogController);
	$.fas.extend(SupplierRateSetSetEditor, FasEditorController);
	
	supplierRateSetDialog = new SupplierRateSetDialog();
	supplierRateSetDialog.init("/supplier_rate_set", {
		searchFormId : "searchForm",
		dataGridId : "dataGridDiv",
		searchUrl : "/list.json",
		exportTitle : "供应商设置导出",
		exportUrl : "/export"
	});
	
	supplierRateSetEditor = new SupplierRateSetSetEditor();
	supplierRateSetEditor.init("/supplier_rate_set", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dataGridDiv',
		saveUrl : '/supplier_rate_set/save_all',
		searchBtn : 'btn-search'
	});
	
	//绑定店铺通用查询
//	$("#shopName").filterbuilder({
//        type:'organ',
//        organFlag: 2,
//        roleType:'bizCity', 
//        onSave : function(result) { 
//        	var value = $(this).filterbuilder('getValue');
//        	$("#shopNo").val(value);
//        }
//    });
});