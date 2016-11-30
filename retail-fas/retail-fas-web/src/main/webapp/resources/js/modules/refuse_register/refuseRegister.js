
function refuseRegisterDialog() { 
	var $this = this;
}

function refuseRegisterEditor() {
	var $this = this;
}

var dialog1 = null, editor = null;
$(function() {
	$.fas.extend(refuseRegisterDialog, FasDialogController);
	$.fas.extend(refuseRegisterEditor, FasEditorController);
	dialog1 = new refuseRegisterDialog();
	dialog1.init("/refuse_register", {
		dataGridId : "dtlDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "拒付登记导出",
		exportUrl : "/do_fas_export"
	});
	
	dialog1.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/refuse_register/do_import',
			'templateName' : '拒付登记导入模板.xlsx',
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
	
	editor = new refuseRegisterEditor();
	editor.init("/refuse_register", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'dtlDataGrid',
		saveUrl : "/refuse_register/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			$.fas.setEditorVal({
				dataGridId : 'dtlDataGrid',
				rowIndex : rowIndex,
				field : 'balanceMonth',
				value : rowData.balanceMonth
			});
			var ed = $('#dtlDataGrid').datagrid('getEditor', {index:rowIndex,field:'companyNo'});
			$(ed.target).combobox('disable');
			var en = $('#dtlDataGrid').datagrid('getEditor', {index:rowIndex,field:'supplierNo'});
			$(en.target).combobox('disable');
		},
		keyboard : true
	});
	
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1);
	$('#createTimeStart').datebox('setValue', year + '-' + month + '-01');
	var  day = new Date(year,month,0);
	$('#createTimeEnd').datebox('setValue', year + '-' + month + '-' + day.getDate());

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
