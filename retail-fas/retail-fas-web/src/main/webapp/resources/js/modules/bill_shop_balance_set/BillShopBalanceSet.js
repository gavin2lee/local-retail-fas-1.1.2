
// 弹出框
function BillShopBalanceSetDialog() {
	var $this = this;
	//导入
	this.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/bill_shop_balance_set/do_import',
			'templateName' : '结算差异方式列表导入.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if ($this.isNotBlank(JSON.parse(result).error)) {
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
	//检查对象是否为空
	this.isNotBlank = function(obj) {
		if (!obj || typeof obj == 'undefined' || obj == '') {
			if('0' == obj){
				return true;
			}
			return false;
		}
		return true;
	};
	//复制
	this.copyOneLine = function(){
		var checkedRows = $('#dataGridDiv').datagrid('getChecked');
		if(checkedRows.length < 1){
			showWarn("请选择要复制的结算差异设置方式!");
			return;
		}
		$.each(checkedRows,function(index,row){
			$('#dataGridDiv').datagrid('appendRow',{
				shopNo : row.shopNo,
				shortName : row.shortName,
				fullName : row.fullName,
				companyNo : row.companyNo,
				companyName : row.companyName,
				balanceDiffType : row.balanceDiffType,
				balanceDiffTypeName : row.balanceDiffTypeName
			});
			var length = $('#dataGridDiv').datagrid('getRows').length;
			$('#dataGridDiv').datagrid('beginEdit',length-1);
		});
	}
}



// 行编辑
function BillShopBalanceSetEditor() {
	$this = this;
	this.checkSave = function(options) {
		return $.fas.checkRowUnique({dataGridId:options.dataGridId,uniqueField:"shopNo"});
	}
	
}

var billDialog = null, editor = null;
$(function() {
	$.fas.extend(BillShopBalanceSetDialog, FasDialogController);
	$.fas.extend(BillShopBalanceSetEditor, FasEditorController);
	
	billDialog = new BillShopBalanceSetDialog();
	billDialog.init("/bill_shop_balance_set", {
		searchFormId : "searchForm",
		dataGridId : "dataGridDiv",
		searchUrl : "/list.json",
		exportTitle : "结算方式差异设置导入",
		exportUrl : "/do_fas_export"
	});
	
	editor = new BillShopBalanceSetEditor();
	editor.init("/bill_shop_balance_set", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dataGridDiv',
		saveUrl : '/bill_shop_balance_set/save_all',
		searchBtn : 'btn-search'
	});
	
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
});