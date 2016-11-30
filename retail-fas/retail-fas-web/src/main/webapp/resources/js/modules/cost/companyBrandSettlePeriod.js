
function CompanyBrandSettlePeriodDialog() { 
	var $this = this;
	
	//转到批量生成界面
	this.toBatchAdds=function(){
		$(':input','#companyBrand').not(
		':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');

		ygDialog({
			title : '公司品牌结账日',
			target : $('#companyBrand'),
			width : 520,
			height : 280,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.batchSave(dialog);
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			} ]
		});
	};
	
	this.batchSave = function(dialog){
		var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm == false) {
			return;
		}
		// 3.保存
		var url = BasePath + "/company_brand_settle_period/check_generate_condition";
		fromObj.form('submit', {
			url : url,
			dataType : 'json',
			success : function(result) {
				if(result && JSON.parse(result).success){
					  showSuc('正在批量生成公司品牌结账日，请稍后查看结果!');
					  dialog.close();
					  $this.search();
					  return;
				 } else if(result){
					  showError(JSON.parse(result).message);
				 }
			},
			error : function() {
				showError('新增失败,请联系管理员!');
			}
		});
	};
	
}

function CompanyBrandSettlePeriodEditor() {
	var $this = this;
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/company_brand_settle_period/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
};
var dialog = null, editor = null;
$(function() {
	$.fas.extend(CompanyBrandSettlePeriodDialog, FasDialogController);
	$.fas.extend(CompanyBrandSettlePeriodEditor, FasEditorController);
	dialog = new CompanyBrandSettlePeriodDialog();
	dialog.init("/company_brand_settle_period", {
		dataGridId : "settlePeriodDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "公司品牌结账日导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new CompanyBrandSettlePeriodEditor();
	editor.init("/company_brand_settle_period", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'settlePeriodDataGrid',
		saveUrl : "/company_brand_settle_period/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
//			var ed = $('#settlePeriodDataGrid').datagrid('getEditor', {index:rowIndex,field:'companyNo'});			
			
			$("#companyNo_").combobox('disable');
			
			$("#brandName").combobox('disable');

		},
		keyboard : true
	});
});