// 弹出框
function SettleCategoryDialog() {
	var $this = this;
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	var  organTypeNo =  currentUser.organTypeNo;
	$("#organTypeNo").val(organTypeNo);	
	this.initAdd = function() {
		$("#settleCategoryNo").removeClass("readonly").removeAttr("readonly", true);
		$("#settleCategoryId").val('');
		// 清空明细数据
		$("#dtlDataGridDiv").clearDataGrid(); 
	};
	//检查新增的数据是否正确
	this.checkAdd = function() {
		return $this.validateData();
	};
	this.checkInitUpdate = function(rowData) {
//		if(rowData.status == '1') {
//			showWarn("数据已启用，不允许修改!");
//			return false;
//		}
		return true;
	};
	this.checkUpdate = function() {
		return $this.validateData();
	};
	this.loadedUpdate = function(rowData) {
		// 查看
		if(rowData.status == '1') {
			$.fas.search({
				dataGridId : "dtlDataGridDivView",
				searchUrl : "/settle_category_dtl/get_biz?settleCategoryNo="+rowData.settleCategoryNo
			});
		} else { // 修改
			$("#settleCategoryNo").addClass("readonly").attr("readonly", true);
			var rows = $("#dataGridDiv").datagrid("getRows");
			$.fas.search({
				dataGridId : "dtlDataGridDiv",
				searchUrl : "/settle_category_dtl/get_biz?settleCategoryNo="+rowData.settleCategoryNo,
				checkboxIndex : rows.length
			});
			$.fas.editIndex = undefined;
		}
	};
	this.checkDel = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1') {
				showWarn("数据已启用，不允许删除!", 1);
				return false;
			}
		}
		var url = BasePath + "/settle_category/check_del";
		var check_data = new Object();
		check_data["deleted"] = JSON.stringify(checkedRows);
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	this.checkUnable = function(checkedRows) {
		var flag = $this.super.checkUnable.call($this, checkedRows);
		if(!flag) {
			return false;
		}
		var url = BasePath + "/settle_category/check_unable";
		var check_data = new Object();
		check_data["unabled"] = JSON.stringify(checkedRows);
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	//校验
	this.validateData = function() {
		if($.fas.endEditing()) {
			var rowUnique = $.fas.checkRowUnique({dataGridId:"dtlDataGridDiv",uniqueField:"categoryNo"});
			if(!rowUnique) {
				return rowUnique;
			}
		}
		var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
		var check_data = {id : $("#settleCategoryId").val(),
						   settleCategoryNo : $("#settleCategoryNo").val(), 
						   name : $("#name").val(),
						   inserted : effectRow.inserted, updated : effectRow.updated};
		var url = BasePath + "/settle_category/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	// tab查询页面中的查询方法
	this.querySearch = function() {
		$.fas.search({
	        searchFormId : "querySearchForm",
	        dataGridId : "queryDataGridDiv",
	        searchUrl : "/settle_category_query/query.json"
	    });
	};
	// tab查询页面进行清空操作
	this.queryClear = function() {
		$.fas.clear("querySearchForm");
	};
	this.queryExport = function() {
		 $.fas.exportExcel({
	        dataGridId : "queryDataGridDiv",
	        exportUrl : "/settle_category_query/do_fas_export",
	        exportTitle : "结算大类明细信息导出",
	        exportType : "common"
	    });
	}
};

//结算大类列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "settleCategoryNo=" + rowData.settleCategoryNo;
};

// 行编辑
function SettleCategoryEditor() {}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(SettleCategoryDialog, FasDialogController);
	$.fas.extend(SettleCategoryEditor, FasEditorController);
	dialog = new SettleCategoryDialog();
	dialog.init("/settle_category", fas_common_setting);
	
	editor = new SettleCategoryEditor();
	editor.init("/settle_category", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dtlDataGridDiv',
		buildAddData : function() {
			return {settleCategoryNo : $("#settleCategoryNo").val()};
		}
	});
	
	$('#mainTab').tabs('add', {
		title : '大类查询',
		selected : false,
		closable : false,
		href : BasePath + "/settle_category_query/list"
	});
	
});