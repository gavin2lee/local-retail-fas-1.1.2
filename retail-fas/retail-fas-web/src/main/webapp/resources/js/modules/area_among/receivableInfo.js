
function receivableInfoDialog() { 
	var $this = this;
}

function receivableInfoEditor() {
	var $this = this;

	//选择结算单号后的回调函数
	this.chooseCallBack = function(data) {
		if (data) {
			$('#balanceNoId').combogrid('setValue', data.billNo);
			$('#balanceAmountId').val(data.balanceAmount);
			$('#receivableAmountId').val(data.balanceAmount);
			$('#overageAmountId').val(0);
		}
	};
	
	//给单元格绑定事件
	this.bindGridEvent = function(rowIndex){
		var objGrid = $("#dataGrid"); //表格对象
		var bAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'balanceAmount'}); //应收金额
		var rAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'receivableAmount'}); //收款金额
		var oAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'overageAmount'}); //余额
		var balanceNoEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'balanceNo'}); //结算单号
		
		//收款金额改变绑定事件
		$(rAmoutEdt.target).bind("keyup",function(){
			var tpAmoutValue = $(bAmoutEdt.target).val();
			var nrAmoutValue = $(rAmoutEdt.target).val();
			$(oAmoutEdt.target).val((tpAmoutValue - nrAmoutValue).toFixed(2));
		});
	};
}

var dialog = null, editor = null;
var clientData=new Object();
$(function() {
	$.fas.extend(receivableInfoDialog, FasDialogController);
	$.fas.extend(receivableInfoEditor, FasEditorController);
	dialog = new receivableInfoDialog();
	dialog.init("/receivable_info", {
		dataGridId : "dataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm"
	});
	
	editor = new receivableInfoEditor();
	editor.init("/receivable_info", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'dataGrid',
		saveUrl : "/receivable_info/save",
		searchBtn : "btn-search",
		afterAdd : function(rowIndex) {
			editor.bindGridEvent(rowIndex);//绑定事件
		},
		afterUpdate : function(rowIndex) {
			editor.bindGridEvent(rowIndex);//绑定事件
		},
		keyboard : true
	});
});
