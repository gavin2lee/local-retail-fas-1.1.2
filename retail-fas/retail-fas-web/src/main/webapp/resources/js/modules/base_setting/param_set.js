
function ParamSetDialog() {
	var $this = this;
	this.initAdd = function() {
		$("#paramCode").removeClass("readonly").removeAttr("readonly", true);
		$("#paramCode").val('');
		$("#dtlDataGridDiv").clearDataGrid(); // 清空明细数据
	};
	
	this.loadedUpdate = function(rowData) {
		// 查看
		if(rowData.status == '1') {
			$.fas.search({
				dataGridId : "dtlDataGridDivView",
				searchUrl : "/param_dtl/get_biz?paramCode="+rowData.paramCode,
			});
		} else { // 修改
			$("#paramCode").addClass("readonly").attr("readonly", true);
			var rows = $("#dataGridDiv").datagrid("getRows");
			$.fas.search({
				dataGridId : "dtlDataGridDiv",
				searchUrl : "/param_dtl/get_biz?paramCode="+rowData.paramCode,
				checkboxIndex : rows.length
			});
			$.fas.editIndex = undefined;
		}
	};
	
	// tab查询页面中的查询方法
	this.querySearch = function() {
		$.fas.search({
	        searchFormId : "querySearchForm",
	        dataGridId : "queryDataGridDiv",
	        searchUrl : "/base_setting/digital_dict/query.json"
	    });
	};
	
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

//组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "paramCode=" + rowData.paramCode;
};

function controlFormatter(value, rowData, rowIndex) {
    var controlLevel = [{'value':'0', 'text': '通用'}, {'value':'1', 'text':'大区'}, {'value':'2', 'text':'公司'}, {'value':'3', 'text':'店铺'}];
    for(var i = 0; i < controlLevel.length; i++) {
        if(controlLevel[i].value == value) {
            return controlLevel[i].text;
        }
    }
    return "";
};

function isvalidFormatter(value, rowData, rowIndex) {
    var controlLevel = [{'value':'0', 'text': '无效'}, {'value':'1', 'text':'有效'}];
    for(var i = 0; i < controlLevel.length; i++) {
        if(controlLevel[i].value == value) {
            return controlLevel[i].text;
        }
    }
    return "";
};

// 行编辑
function ParamSetEditor() {}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(ParamSetDialog, FasDialogController);
	$.fas.extend(ParamSetEditor, FasEditorController);
	dialog = new ParamSetDialog();
	dialog.init("/base_setting/digital_dict", fas_common_setting);
	
	editor = new ParamSetEditor();
	editor.init("/base_setting/digital_dict", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dtlDataGridDiv',
		buildAddData : function() {
			return {settleCategoryNo : $("#settleCategoryNo").val()};
		}
	});
	
	$('#mainTab').tabs('add', {
		title : '参数查询',
		selected : false,
		closable : false,
		href : BasePath + "//base_setting/digital_dict/list_tabMain"
	});
});