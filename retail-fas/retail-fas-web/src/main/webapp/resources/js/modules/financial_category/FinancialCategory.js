// 弹出框
function FinancialCategoryDialog() {
	var $this = this;
	this.initAdd = function() {
		$("#financialCategoryNo").removeClass("readonly").removeAttr("readonly", true);
		$("#financialCategoryId").val('');
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
				searchUrl : "/financial_category_dtl/get_biz?financialCategoryNo="+rowData.financialCategoryNo
			});
			$("#viewCompamyName").combogrid("disable");
		} else { // 修改
			$("#financialCategoryNo").addClass("readonly").attr("readonly", true);
			var rows = $("#dtlDataGridDiv").datagrid("getRows");
			$.fas.search({
				dataGridId : "dtlDataGridDiv",
				searchUrl : "/financial_category_dtl/get_biz?financialCategoryNo="+rowData.financialCategoryNo,
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
		var url = BasePath + "/financial_category/check_del";
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
		var url = BasePath + "/financial_category/check_unable";
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
	// 组装启用、停用操作的数据
//	this.buildUnableData = function(checkedRows) {
//		return $this.buildUnableAndAbleData(checkedRows);
//	};
//	this.buildEnableData = function(checkedRows) {
//		return $this.buildUnableAndAbleData(checkedRows);
//	};
//	this.buildUnableAndAbleData = function(checkedRows) {
//		var list = [];
//	    $.each(checkedRows, function(index, item){
//	        var obj = new Object();
//	        obj.id = item.id;
//	        obj.isDefault = item.isDefault;
//	        list.push(obj);
//	    });
//	    return list;
//	};
	
	//校验
	this.validateData = function() {
		if($.fas.endEditing()) {
			var rowUnique = $.fas.checkRowUnique({dataGridId:"dtlDataGridDiv",uniqueField:"categoryNo"});
			if(!rowUnique) {
				return rowUnique;
			}
		}
		var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
		var check_data = {id : $("#financialCategoryId").val(),
						   financialCategoryNo : $("#financialCategoryNo").val(), 
						   name : $("#name").val(),
						   companyNo : $("#companyNo").val(),
						   inserted : effectRow.inserted, updated : effectRow.updated};
		var url = BasePath + "/financial_category/validate_data";
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
	        searchUrl : "/financial_category_query/query.json"
	    });
	};
	// tab查询页面进行清空操作
	this.queryClear = function() {
		$.fas.clear("querySearchForm");
	};
	// tab查询页面进行导出操作
	this.queryExport = function() {
		 $.fas.exportExcel({
			dataGridId : "queryDataGridDiv",
	    	exportUrl : "/financial_category_query/do_fas_export",
	    	exportTitle : "财务大类明细信息导出",
	    	exportType : "common"
	    });
	}
};

//财务结算大类列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "financialCategoryNo=" + rowData.financialCategoryNo;
};

// 行编辑
function FinancialCategoryEditor() {
	// 批量新增财务大类明细
	this.batchAddDtl = function() {
		if($.fas.endEditing()) {
			var rows = $("#dtlDataGridDiv").datagrid("getRows");
			var existCategoryNos = "";
			$.each(rows, function(index, item) {
				if(index == rows.length - 1) {
					existCategoryNos += item.categoryNo;
				} else {
					existCategoryNos += item.categoryNo + ",";
				}
			});
			ygDialog({
				title : "选择大类",
				href : BasePath + "/category/multi_select?existCategoryNos=" + existCategoryNos,
				width : 500,
				height : 500,
				isFrame : false,
				enableCloseButton: false,
				onLoad : function(win, content) {
					var _this = $(this);
					var _this = $(this);
					$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
						var reqParam = $("#dialog_SarchForm").form("getData");
						var queryUrl = BasePath + "/category/filter_exist_category";
				    	//组装请求参数
				    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
					    $("#dialog_SearchDataGrid").datagrid('options').url = queryUrl;
					    $("#dialog_SearchDataGrid").datagrid('load');
					});
					$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
						 $("#dialog_SarchForm").form("clear");
					});
					$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
						var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
						$.each(checkedRows, function(index, item){
							var rowObj = $.extend({} , {financialCategoryNo : $("#financialCategoryNo").val()});
							rowObj.categoryNo = item.categoryNo;
							rowObj.name = item.name;
							$.fas.addEditorRow({rowData : rowObj});
						});
						win.close();
					});
					$("#dgSelectorCancelBtn", _this.contents()).on("click", function() {
						win.close();
					});
				}
			});
		}
	};
	this.formatQtyControl = function(value) {
		if(value == '1') {
			return "是";
		}
		return "否";
	};
}

function beforeAddUpdateEditor(rowIndex, rowData) {
	var rows = $("#dtlDataGridDiv").datagrid("getRows");
	var existCategoryNos = "";
	$.each(rows, function(index, item) {
		if(index == rows.length - 1) {
			existCategoryNos += item.categoryNo;
		} else {
			existCategoryNos += item.categoryNo + ",";
		}
	});
	$("#dtlDataGridDiv").datagrid("removeEditor", "name");
	$("#dtlDataGridDiv").datagrid("addEditor", {field : "name", 
		editor:{type:'category',
			 options:{
			  	 url : BasePath + '/category/filter_exist_category?existCategoryNos='+existCategoryNos,
			  	 required : true
			 }
		}
	});
};

var dialog = null, editor = null;
$(function() {
	$.fas.extend(FinancialCategoryDialog, FasDialogController);
	$.fas.extend(FinancialCategoryEditor, FasEditorController);
	dialog = new FinancialCategoryDialog();
	dialog.init("/financial_category", fas_common_setting);
	
	editor = new FinancialCategoryEditor();
	editor.init("/financial_category", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dtlDataGridDiv',
		buildAddData : function() {
			return {financialCategoryNo : $("#financialCategoryNo").val()};
		},
		beforeAdd : beforeAddUpdateEditor,
		beforeUpdate : beforeAddUpdateEditor,
		afterAdd : function(rowIndex) {
			$.fas.setEditorVal({
				dataGridId : "dtlDataGridDiv",
				rowIndex : rowIndex,
				field : "qtyControlFlag",
				dataType : "combobox",
				value : "0"
			});
		},
		afterUpdate : function(rowIndex, row) {
			var qtyControlFlag = row.qtyControlFlag ? row.qtyControlFlag : "0";
			$.fas.setEditorVal({
				dataGridId : "dtlDataGridDiv",
				rowIndex : rowIndex,
				field : "qtyControlFlag",
				dataType : "combobox",
				value : qtyControlFlag
			});
		}
	});
	
	$('#mainTab').tabs('add', {
		title : '财务大类查询',
		selected : false,
		closable : false,
		href : BasePath + "/financial_category_query/list"
	});
});