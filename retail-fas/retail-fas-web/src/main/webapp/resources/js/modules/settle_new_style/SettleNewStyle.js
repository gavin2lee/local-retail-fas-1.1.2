// 弹出框
function SettleNewStyleDialog() {
	var $this = this;
	this.initAdd = function() {
		var  organTypeNo =  currentUser.organTypeNo;
		$("#organTypeNo").val(organTypeNo);	
		$("#styleNo").removeClass("readonly").removeAttr("readonly", true);
		$("#settleNewStyleId").val('');
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
				searchUrl : "/settle_new_style_dtl/get_biz?styleNo="+rowData.styleNo
			});
			$.fas.editIndex = undefined;
		} else { // 修改
			$("#styleNo").addClass("readonly").attr("readonly", true);
			var rows = $("#dtlDataGridDiv").datagrid("getRows");
			$.fas.search({
				dataGridId : "dtlDataGridDiv",
				searchUrl : "/settle_new_style_dtl/get_biz?styleNo="+rowData.styleNo,
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
		var url = BasePath + "/settle_new_style/check_del";
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
		var flag = $this.super.checkUnable.call($this,checkedRows);
		if(!flag) {
			return false;
		}
		var url = BasePath + "/settle_new_style/check_unable";
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
			var rowUnique = $this.checkRowUnique();
			if(!rowUnique) {
				return false;
			}
		}
		var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
		var check_data = {id : $("#settleNewStyleId").val(), 
				          styleNo : $("#styleNo").val(), 
				          name : $("#name").val(),
				          inserted : effectRow.inserted, updated : effectRow.updated};
		var url = BasePath + "/settle_new_style/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	this.checkRowUnique = function() {
		var rows = $("#dtlDataGridDiv").datagrid("getRows");
		if(rows.length == 0) {
			 return true;
		}
		var flag = true;
		$.each(rows, function(index, row){
			for(var i = index + 1; i < rows.length; i++) {
				if((row.seasonNo == rows[i].seasonNo || row.seasonNo == 'ALL' || rows[i].seasonNo == 'ALL') 
						&& row.year == rows[i].year) {
					flag = false;
					return false;
				}
			}
		});
		if(!flag){
			showWarn("行数据不能重复!");
			return false;
		}
		return true;
	};
	// tab查询页面中的查询方法
	this.querySearch = function() {
		$.fas.search({
	        searchFormId : "querySearchForm",
	        dataGridId : "queryDataGridDiv",
	        searchUrl : "/settle_new_style_query/query.json"
	    });
	};
	// tab查询页面进行清空操作
	this.queryClear = function() {
		$.fas.clear("querySearchForm");
	};
	this.queryExport = function() {
		$.fas.exportExcel({
	        dataGridId : "queryDataGridDiv",
	        exportUrl : "/settle_new_style_query/do_fas_export",
	        exportTitle : "新旧款明细信息导出",
	        exportType : "common"
	    });
	}
};

//新旧款明细列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "styleNo=" + rowData.styleNo;
};

// 行编辑
function SettleNewStyleEditor() {}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(SettleNewStyleDialog, FasDialogController);
	$.fas.extend(SettleNewStyleEditor, FasEditorController);
	dialog = new SettleNewStyleDialog();
	dialog.init("/settle_new_style", fas_common_setting);
	
	editor = new SettleNewStyleEditor();
	editor.init("/settle_new_style", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dtlDataGridDiv',
		buildAddData : function() {
			return {styleNo : $("#styleNo").val()};
		}
	});
	
	$('#mainTab').tabs('add', {
		title : '新旧款查询',
		selected : false,
		closable : false,
		href : BasePath + "/settle_new_style_query/list"
	});
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	
	
});