
function SpecialZoneInfoDialog() { 
	var $this = this;

	this.checkEnable = function(checkedRows) {
		var enableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("存在状态为启用的记录，请重新选择！");
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
				showWarn("存在状态为停用的记录，请重新选择！");
				unableFlag = false;
				return false;
	 	    }
		});
		return unableFlag;
	};
}

function SpecialZoneInfoEditor() {
	var $this = this;
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/special_zone_info/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};

	this.statusformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '停用'}, {'value':'1', 'text':'启用'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(SpecialZoneInfoDialog, FasDialogController);
	$.fas.extend(SpecialZoneInfoEditor, FasEditorController);
	dialog = new SpecialZoneInfoDialog();
	dialog.init("/special_zone_info", {
		dataGridId : "specialZoneInfoDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable",
		searchFormId : "searchForm",
		exportTitle : "特殊大区导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new SpecialZoneInfoEditor();
	editor.init("/special_zone_info", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'specialZoneInfoDataGrid',
		saveUrl : "/special_zone_info/save",
		searchBtn : "btn-search",
		afterAdd : function(rowIndex) {
			var edZoneNo = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'zoneNo'});
			$(edZoneNo.target).on("blur",function(){
				var edZoneCode = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'zoneCode'});
				$(edZoneCode.target).val($(edZoneNo.target).val());
			});
		},
		afterUpdate : function(rowIndex, rowData) {
			var edZoneNo = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'zoneNo'});
			var edZoneName = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'name'});
			$(edZoneNo.target).attr("readonly", true).addClass("disabled");
			$(edZoneName.target).attr("readonly", true).addClass("disabled");
		},
		keyboard : true
	});
});
