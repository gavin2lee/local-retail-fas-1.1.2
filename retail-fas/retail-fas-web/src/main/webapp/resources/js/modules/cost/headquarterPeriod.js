var headquarterPeriod = {};
function HeadquarterPeriodDialog() { 
	var $this = this;
}

function HeadquarterPeriodEditor() {
	var $this = this;
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/headquarter_period/validate_data";
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
headquarterPeriod.toBatchAdd = function(){
	$('#dataForm').form('clear');
	ygDialog({
		title : '批量生成总部结账日',
		target : $('#myFormPanel'),
		width : 300,
		height : 200,
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				headquarterPeriod.batchAdd(dialog);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
};
//批量新增的逻辑处理方法
headquarterPeriod.batchAdd = function(dialog) {
	var fromObj = $('#myFormPanel');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	$('#dataForm').form('submit',{
		url : BasePath + '/headquarter_period/batch_add',
		success:function(result){
			var jsonObj = JSON.parse(result);
			if(jsonObj && jsonObj.success==true) {
				showSuc('总部结账日生成成功!');
				$("#btn-search").click();
				$("#myFormPanel").window('close');
			}else{
				showWarn(jsonObj.message);
			}
		}
	});
};
var dialog = null, editor = null;
$(function() {
	$.fas.extend(HeadquarterPeriodDialog, FasDialogController);
	$.fas.extend(HeadquarterPeriodEditor, FasEditorController);
	dialog = new HeadquarterPeriodDialog();
	dialog.init("/headquarter_period", {
		dataGridId : "settlePeriodDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "总部结账日导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new HeadquarterPeriodEditor();
	editor.init("/headquarter_period", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'settlePeriodDataGrid',
		saveUrl : "/headquarter_period/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			$("#companyNo_").combobox('disable');
			$("#brandUnitName_").combobox('disable');
		},
		keyboard : true
	});
});