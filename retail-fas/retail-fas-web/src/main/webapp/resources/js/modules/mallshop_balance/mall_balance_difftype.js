function MallBalanceDifftypeDialog(){
	var $this = this;
	// 状态
	this.statusFlagformatter = function(value) {
		for ( var i = 0; i < $this.statusFlag_status.length; i++) {
			if ($this.statusFlag_status[i].value == value) {
				return $this.statusFlag_status[i].text;
			}
		}
	};
	//状态
	this.statusFlag_status = [ {"value" : "0", "text" : "停用"}, {"value" : "1", "text" : "启用"} ];

	this.initStatusFlag=function(){
		$('#statusCondition').combobox({
			data : $this.statusFlag_status,
			valueField : 'value',
			textField : 'text',
			editable : false
		});  
	};
	
	this.checkEnable = function(checkedRows) {
		var enableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("存在类别状态为启用的记录，请重新选择！");
				enableFlag = false;
				return false;
	 	    }
		});
		return enableFlag;
	};
	
	this.checkUnable = function(checkedRows) {
		var unableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '0'){
				showWarn("存在类别状态为停用的记录，请重新选择！");
				unableFlag = false;
				return false;
	 	    }
		});
		return unableFlag;
	};
}

function MallBalanceDifftypeEditor() {
	var $this = this;


	this.checkUpdate = function(options, rowIndex, rowData) {
		if(rowData.status == '1'){
			showWarn("差异类型已启用，不能修改！");
			return false;
 	    }
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("差异类型已启用，不能删除！");
				delFlag = false;
				return false;
	 	    }
		});
		return delFlag;
	};

}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(MallBalanceDifftypeDialog, FasDialogController);
	$.fas.extend(MallBalanceDifftypeEditor, FasEditorController);
	dialog = new MallBalanceDifftypeDialog();
	dialog.initStatusFlag();
	dialog.init("/mall_shopbalancedifftype", {
		dataGridId : "mallBalanceDiffTypeDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable",
		searchFormId : "searchForm",
		exportTitle:'差异类型导出',
		exportUrl:"/do_fas_export"
	});
	
	editor = new MallBalanceDifftypeEditor();
	editor.init("/mall_shopbalancedifftype", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'mallBalanceDiffTypeDataGrid',
		saveUrl : "/mall_shopbalancedifftype/save",
		searchBtn : "btn-search",
		keyboard : true
	});
});