
function shopReplaceDialog() { 
	var $this = this;
	
	this.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/base_setting/shop_name_replace/do_import',
			'templateName' : '店铺名称替换导入.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
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
	}
}

function shopReplaceEditor() {
	var $this = this;
	//校验数据
	$this.checkSave = function(options, data) {
		var url = BasePath + "/base_setting/shop_name_replace/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showWarn(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	$this.checkUpdate = function(options, rowIndex, rowData) {
		return true;
	};
	
	$this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		if(checkedRows.length<1){
			showInfo("请先选择要删除的记录！");
			return;
		}
		var delFlag = true;
		return delFlag;
	};
	
}

var shopReplace = null, editor = null;
var BizTypeData=new Object();
$(function() {
	$.fas.extend(shopReplaceDialog, FasDialogController);
	$.fas.extend(shopReplaceEditor, FasEditorController);
	shopReplace = new shopReplaceDialog();
	shopReplace.init("/base_setting/shop_name_replace", {
		dataGridId : "shopReplaceDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable"
	});
	
	editor = new shopReplaceEditor();
	editor.init("/base_setting/shop_name_replace", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'shopReplaceDataGrid',
		saveUrl : "/base_setting/shop_name_replace/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
		},
		keyboard : true
	});
});

