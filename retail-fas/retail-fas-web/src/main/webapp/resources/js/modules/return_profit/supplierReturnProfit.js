
var supplierReturnProfit = new Object();

datasource ={};

datasource.returnProfitTypes = [{"value":"0","text":"结算金额"},{"value":"1","text":"厂商金额"},{"value":"2","text":"牌价额"}];

var commonSetting = function(){
	var setting = {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "返利导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	};
	return setting;
};

function SupplierReturnProfitDialog(){
	var $this = this;
	this.add = function(){
		$('#genarateDataForm').form('clear');
		$("input[type='hidden']", '#genarateFormPanel').val('');
		ygDialog({
			title : '返利新增',
			target : $('#genarateFormPanel'),
			width : 550,
			height : 330,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.generateSave(dialog);
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
	//删除
	this.del = function(){
		var checkedRows = $('#dataGridDiv').datagrid('getChecked');
		if (checkedRows.length == 0) {
			showWarn('请选择要删除的行..');
			return;
		}
		var rowIndex;
		$.each(checkedRows, function(index, row) {
			rowIndex = $('#dataGridDiv').datagrid('getRowIndex', row);
			$('#dataGridDiv').datagrid('deleteRow', rowIndex);
			if (supplierReturnProfit.editRowIndex == rowIndex) {
				supplierReturnProfit.editRowIndex = -1;
			}
		});
	};
	//新增返利
	this.generateSave = function(dialog){
		if($("#genarateDataForm").form('validate') == false){
			return;
		}
		var url = BasePath + "/supplier_return_profit/add_return_profit";
		var params = $('#genarateDataForm').form('getData');
		supplierReturnProfit.commonAjaxRequest(url, params,function(result){
			if(result && result.success){
				showSuc('添加成功!');
				dialog.close();
				return;
			} else if(result){
				showError(result.message);
			}
		});
	};
	//保存
	this.save = function() {
		var url = BasePath + '/supplier_return_profit/save';
		var deletedRows = $('#dataGridDiv').datagrid('getChanges', 'deleted');
		var changeRows = {deleted : JSON.stringify(deletedRows)};
		
		supplierReturnProfit.commonAjaxRequest(url, changeRows, function(result) {
			if (result) {
				showSuc('保存成功');
				$('#dataGridDiv').datagrid('reload');
			} else {
				showSuc('保存失败');
			}
		});
	};
	//导入
	this.importExcel = function(){
		var url = BasePath +  "/supplier_return_profit/import";
		$.importExcel.open({
			'submitUrl' : url,
			'templateName' : '返利表导入.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (data.error != undefined) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
						$('#dataGridDiv').datagrid('reload');
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
	};
};

//ajax请求
supplierReturnProfit.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async : false,
		dataType : 'json',
		success : callback
	});
};

var selfDialog = null;
$(function() {
	$.fas.extend(SupplierReturnProfitDialog, FasDialogController);
	selfDialog = new SupplierReturnProfitDialog();
	selfDialog.init("/supplier_return_profit", commonSetting());
	
});

