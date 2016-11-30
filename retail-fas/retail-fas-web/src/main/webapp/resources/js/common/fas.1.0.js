/**
 * Created by admin on 2014/9/25.
 */
function FasEditorController() { }

FasEditorController.prototype.init = function(modulePath, options_) {
    this.modulePath = modulePath;
    this.options = options_;
    var $this = this;
    // 增加双击空白处，新增行的事件
    if(options_ && options_.dataGridDivId) {
    	$("#" + options_.dataGridDivId).bind("dblclick", function() {
    		$this.insertRow();
    	});
    }
    // 增加全键盘函数
    if(options_.keyboard) {
    	$("#" + options_.dataGridId).datagrid({
    		keyboard : {
    			type: 'grid', 
    			lastFn: function(){
    				$this.insertRow();
    				var firstEditorId = $.fas.getFirstEditorId(options_.dataGridId);
    				if(firstEditorId) {
	    				setFocus($("#" + firstEditorId));
    				}
    			}
    		}
    	});
    }
};

FasEditorController.prototype.insertRow = function() {
    var $this = this;
    if(!$this.checkInsert($this.options)) {
    	return;
    }
    $.fas.addEditorRow({
        dataGridId : $this.options.dataGridId || 'dtlDataGridDiv',
        initRow : $this.options.initRow,
		rowData : $this.options.rowData,
		comboboxData : $this.options.comboboxData,
		beforeAdd : $this.options.beforeAdd,
		buildAddData : $this.options.buildAddData
    });
};

FasEditorController.prototype.checkInsert = function(options) {
	return true;
};

FasEditorController.prototype.editRow = function(rowIndex, rowData) {
    var $this = this;
    if(!$this.checkUpdate($this.options, rowIndex, rowData)) {
    	return;
    }
    $.fas.editEditorRow({
        dataGridId : $this.options.dataGridId || 'dtlDataGridDiv',
        rowIndex : rowIndex,
		row : rowData,
        initRow : $this.options.initRow,
        beforeUpdate : $this.options.beforeUpdate,
        afterUpdate : $this.options.afterUpdate,
        buildUpdateData : $this.options.buildUpdateData
    });
};

FasEditorController.prototype.checkUpdate = function(options, rowIndex, rowData) {
	return true;
};

FasEditorController.prototype.deleteRow = function() {
    var $this = this;
    if(!$this.checkDel($this.options)) {
    	return;
    }
    $.fas.deleteEditorRow({
        dataGridId : $this.options.dataGridId || 'dtlDataGridDiv',
        checkDel : $this.options.checkDel
    });
};

FasEditorController.prototype.checkDel = function(options) {
	return true;
};

//保存
FasEditorController.prototype.save = function() {
	var $this = this;
	if($.fas.endEditing($this.options.dataGridId)) {
		if(!$this.checkSave($this.options)) {
			return;
		}
		var insertedData = $("#"+$this.options.dataGridId).datagrid('getChanges','inserted');
		var updatedData = $("#"+$this.options.dataGridId).datagrid('getChanges','updated');
		var deletedData = $("#"+$this.options.dataGridId).datagrid('getChanges','deleted');
		var data = {
			inserted : JSON.stringify(insertedData),
			updated : JSON.stringify(updatedData),
			deleted : JSON.stringify(deletedData)
		};
		ajaxRequest(BasePath + $this.options.saveUrl, data, function(result){
			if(result) {
				if(result.success) {
					showSuc("操作成功!");
					$this.saveCallback($this.options);
				} else {
					showWarn(result.message);
				}
				return;
			} else {
				showError("操作失败,请联系管理员!");
			}
		});
	}
};

//校验保存数据是否合格
FasEditorController.prototype.checkSave = function(options) {
	return true;
};

// 保存成功的回调函数
FasEditorController.prototype.saveCallback = function(options) {
	$("#"+options.searchBtn).click();
};

function FasDialogController() {}

FasDialogController.prototype.init = function(modulePath, options) {
    this.modulePath = modulePath;
    this.options = options;
};

FasDialogController.prototype.search = function() {
	var $this = this;
    var searchUrl = this.modulePath + this.options.searchUrl;
    $.fas.search({
        searchFormId : $this.options.searchFormId,
        dataGridId : $this.options.dataGridId,
        searchUrl : $this.modulePath + $this.options.searchUrl
    });
    $(":checkbox:eq(0)").attr("checked", false);
};

FasDialogController.prototype.clear = function() {
    $.fas.clear(this.options.searchFormId);
};

FasDialogController.prototype.toAdd = function() {
    var $this = this;
    $.fas.ygDialogAdd({
        dataFormId : $this.options.dataFormId,
        dialogWidth : $this.options.dialogWidth,
        dialogHeight : $this.options.dialogHeight,
        saveFunction : $this.add,
        initAdd : $this.initAdd,
        addDataFormClass : $this.addDataFormClass,
        loadedAdd : $this.loadedAdd,
        obj : $this
    });
};

FasDialogController.prototype.initAdd = function() {

};

FasDialogController.prototype.loadedAdd = function() {

};

FasDialogController.prototype.add = function($this) {
    $.fas.add({
        dataFormId : $this.options.dataFormId,
        url : $this.modulePath + $this.options.saveUrl,
        searchBtn : $this.options.searchBtn,
        validateForm : $this.validateForm,
        checkAdd : $this.checkAdd,
        successAddFn : $this.successAddFn,
        buildAddSubmitParams : $this.buildSaveSubmitParams
    });
};

//检验表单数据是否正确
FasDialogController.prototype.validateForm = function(formObj) {
    var validateForm = formObj.form('validate');
    if(validateForm == false) {
        return false;
    }
    return true;
};

//验证是否可保存
FasDialogController.prototype.checkAdd = function() {
    return true;
};

//修改成功的回调函数
FasDialogController.prototype.successAddFn = function(result, options) {
    if(result && (JSON.parse(result).success
        || typeof JSON.parse(result).success == 'undefined')) {
        showSuc("新增成功!");
        $("#myFormPanel").window('close');
        $("#"+ options.searchBtn).click();
    } else {
        showError("新增失败,请联系管理员!");
    }
};

//组建新增/修改操作的参数数据
FasDialogController.prototype.buildSaveSubmitParams = function() {
    var params = [];
    var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
    var deleted = "";
    var deletedData = $("#dtlDataGridDiv").datagrid('getChanges','deleted');
    var deleteList = [];
    $.each(deletedData, function(index, item){
        var obj = new Object();
        obj.id = item.id;
        deleteList.push(obj);
    });
    if(deleteList.length > 0) {
        deleted = JSON.stringify(deleteList);
    }
    params.push({name : 'inserted', value : effectRow.inserted});
    params.push({name : 'updated', value : effectRow.updated});
    params.push({name : 'deleted', value : deleted});
    return params;
};

FasDialogController.prototype.toUpdate = function(rowData) {
    var $this = this;
    $.fas.ygDialogUpdate({
        dataGridId : $this.options.dataGridId,
        dataFormId : $this.options.dataFormId,
        dialogWidth : $this.options.dialogWidth,
        dialogHeight : $this.options.dialogHeight,
        initUpdate : $this.initUpdate,
        addDataFormClass : $this.addDataFormClass,
        checkInitUpdate : $this.checkInitUpdate,
        loadedUpdate : $this.loadedUpdate,
        updateFunction : $this.update,
        rowData : rowData,
        obj : $this,
        checkUpdateField : $this.options.checkUpdateField
    });
};

FasDialogController.prototype.checkInitUpdate = function(rowData) {
    return true;
};

FasDialogController.prototype.initUpdate = function() {

};

FasDialogController.prototype.loadedUpdate = function(rowData) {

};

FasDialogController.prototype.update = function($this) {
    $.fas.update({
        dataFormId : $this.options.dataFormId,
        url : $this.modulePath + $this.options.updateUrl,
        searchBtn : $this.options.searchBtn,
        validateForm : $this.validateForm,
        checkUpdate : $this.checkUpdate,
        successUpdateFn : $this.successUpdateFn,
        buildUpdateSubmitParams : $this.buildSaveSubmitParams
    });
};

FasDialogController.prototype.checkUpdate = function() {
    return true;
};

//修改成功的回调函数
FasDialogController.prototype.successUpdateFn = function(result, options) {
    if(result && (JSON.parse(result).success
        || typeof JSON.parse(result).success == 'undefined')) {
        showSuc("修改成功!");
        $("#myFormPanel").window('close');
        $("#"+ options.searchBtn).click();
    } else {
        showError("修改失败,请联系管理员!");
    }
};

//删除
FasDialogController.prototype.doDel = function() {
    var $this = this;
    $.fas.del({
        dataGridId : $this.options.dataGridId,
        url : $this.modulePath + $this.options.delUrl,
        searchBtn : $this.options.searchBtn,
        checkDel : $this.checkDel,
        buildDelData : $this.buildDelData,
        delCallback : $this.delCallback
    });
};


//列表批量删除
//如果需要在删除操作时，进行一些其他的逻辑处理，可自定义checkDel函数
FasDialogController.prototype.checkDel = function(checkedRows) {
    return true;
};

//组装删除操作的数据
FasDialogController.prototype.buildDelData = function(checkedRows) {
//    var deleteList = [];
//    $.each(checkedRows, function(index, item) {
//        var obj = new Object();
//        obj.id = item.id;
//        deleteList.push(obj);
//    });
//    return deleteList;
	return checkedRows;
};

//组装后的回调函数
FasDialogController.prototype.delCallback = function(options, result) {
    if(result) {
        showSuc("删除成功!");
        $("#"+ options.searchBtn).click();
    } else {
        showError("删除失败,请联系管理员!");
    }
};

FasDialogController.prototype.doEnable = function() {
    var $this = this;
    $.fas.enable({
        dataGridId : $this.options.dataGridId,
        url : $this.modulePath + $this.options.enableUrl,
        searchBtn : $this.options.searchBtn,
        checkEnable : $this.checkEnable,
        buildEnableData : $this.buildEnableData,
        enableCallback : $this.enableCallback
    });
};

//如果需要在激活操作时，进行一些其他的逻辑处理，可自定义checkEnable函数
FasDialogController.prototype.checkEnable = function(checkedRows) {
	var flag = true;
	$.each(checkedRows, function(index, item) {
       if(item.status == '1'){
    	   showWarn('数据已启用，请勿重复操作');
    	   flag = false;
    	   return false;
       }
    });
    return flag;
};

//组装激活操作的数据
FasDialogController.prototype.buildEnableData = function(checkedRows) {
    var enableList = [];
    $.each(checkedRows, function(index, item) {
        var obj = new Object();
        obj.id = item.id;
        enableList.push(obj);
    });
    return enableList;
};

//组装激活操作后的回调函数
FasDialogController.prototype.enableCallback = function(options, result) {
    if(result) {
        showSuc("启用操作成功!");
        $("#"+ options.searchBtn).click();
    } else {
        showError("启用操作失败,请联系管理员!");
    }
};

FasDialogController.prototype.doUnable = function() {
    var $this = this;
    $.fas.unable({
        dataGridId : $this.options.dataGridId,
        url : $this.modulePath + $this.options.unableUrl,
        searchBtn : $this.options.searchBtn,
        checkUnable : $this.checkUnable,
        buildUnableData : $this.buildUnableData,
        unableCallback : $this.unableCallback
    });
};

FasDialogController.prototype.buildUnableData = function(checkedRows) {
    var unableList = [];
    $.each(checkedRows, function(index, item){
        var obj = new Object();
        obj.id = item.id;
        unableList.push(obj);
    });
    return unableList;
};

FasDialogController.prototype.checkUnable = function(checkedRows) {
	var flag = true;
	$.each(checkedRows, function(index, item) {
       if(item.status == '0'){
    	   showWarn('数据已停用，请勿重复操作');
    	   flag = false;
    	   return false;
       }
    });
    return flag;
};

//组装激活操作后的回调函数
FasDialogController.prototype.unableCallback = function(result, options) {
    if(result) {
        showSuc("停用操作成功!");
        $("#"+ options.searchBtn).click();
    } else {
        showError("停用操作失败,请联系管理员!");
    }
};

//审核
FasDialogController.prototype.doAudit = function() {
	 var $this = this;
	 $.fas.audit({
        dataGridId : $this.options.dataGridId,
        url : $this.modulePath + $this.options.auditUrl,
        searchBtn : $this.options.searchBtn,
        checkAudit : $this.checkAudit,
        buildAuditData : $this.buildVerifyData,
        auditCallback : $this.auditCallback
    });
};

FasDialogController.prototype.checkAudit = function(checkedRows) {
    if(checkedRows.length === 1 && checkedRows[0].auditStatus == '1') {
        showWarn('数据已审核，无需再审核');
        return false;
    }
    return true;
};

//组装审核/反审核操作的数据
FasDialogController.prototype.buildVerifyData = function(checkedRows) {
	var verifyList = [];
	$.each(checkedRows, function(index, item) {
		var obj = new Object();
		obj.id = item.id;
		verifyList.push(obj);
	});     
    return verifyList;
};

//组装审核后的回调函数
FasDialogController.prototype.auditCallback = function(result, options) {
    if(result) {
        showSuc("审核成功!");
        $("#"+ options.searchBtn).click();
    } else {
        showError("审核失败,请联系管理员!");
    }
};

//反审核
FasDialogController.prototype.doAntiAudit = function() {
	var $this = this;
	$.fas.antiAudit({
       dataGridId : $this.options.dataGridId,
       url : $this.modulePath + $this.options.antiAuditUrl,
       searchBtn : $this.options.searchBtn,
       checkAntiAudit : $this.checkAntiAudit,
       buildAntiAuditData : $this.buildVerifyData,
       antiAuditCallback : $this.antiAuditCallback
   });
};

FasDialogController.prototype.checkAntiAudit = function(checkedRows) {
    if(checkedRows.length === 1 && checkedRows[0].auditStatus != '1') {
        showWarn('数据未审核，请勿重复操作');
        return false;
    }
    return true;
};

//组装反审核后的回调函数
FasDialogController.prototype.antiAuditCallback = function(result, options) {
    if(result) {
        showSuc("反审核成功!");
        $("#"+ options.searchBtn).click();
    } else {
        showError("反审核失败,请联系管理员!");
    }
};

FasDialogController.prototype.exportExcel = function() {
    var $this = this;
    $.fas.exportExcel({
        dataGridId : $this.options.dataGridId,
        exportUrl : $this.modulePath + $this.options.exportUrl,
        exportTitle : $this.options.exportTitle,
        exportType : $this.options.exportType
    });
};

FasDialogController.prototype.addDataFormClass = function(dataFormId) {
    $("#" + dataFormId).form("clear");
};

FasDialogController.prototype.clearDataFormClass = function(dataFormId) {
    $("#" + dataFormId + " input").addClass("disabled").attr("readonly", true);
    $("#" + dataFormId + " textarea").addClass("disabled").attr("disabled", "disabled");
    $("#" + dataFormId + " select[class*=easyui-combobox]").combobox('disable');
    $("#" + dataFormId + " input[class*=easyui-combobox]").combobox('disable');
    $("#" + dataFormId + " input[class*=easyui-search]").parents("div").removeClass("ipt-search-box");
    $("#" + dataFormId + " input[class*=easyui-datebox]").attr("disabled","true");
};

//清除样式
FasDialogController.prototype.clearReadOnly = function(params) {
    var inputs = params.input;
    var dates = params.date;
    var searchboxs = params.searchbox;
    var comboboxs = params.combobox;
    if(inputs && inputs.length > 0) {
        for(var i = 0; i < inputs.length; i++) {
            $("#" + inputs[i]).removeAttr("readonly").removeClass("readonly");
        }
    }
    if(dates && dates.length > 0) {
        for(var i = 0; i < dates.length; i++) {
            $("#" + dates[i]).removeClass("readonly").removeAttr("disabled");
        }
    }
    if(searchboxs && searchboxs.length > 0) {
        for(var i = 0; i < searchboxs.length; i++) {
            $("#" + searchboxs[i]).parents("div").addClass("ipt-search-box");
        }
    }
};

//设置样式
FasDialogController.prototype.setReadOnly = function(params) {
    var inputs = params.input;
    var dates = params.date;
    var searchboxs = params.searchbox;
    var comboboxs = params.combobox;
    if(inputs && inputs.length > 0) {
        for(var i = 0; i < inputs.length; i++) {
            $("#" + inputs[i]).addClass("readonly").attr("readonly", true);
        }
    }
    if(dates && dates.length > 0) {
        for(var i = 0; i < dates.length; i++) {
            $("#" + dates[i]).addClass("readonly").attr("disabled", true);
        }
    }
    if(searchboxs && searchboxs.length > 0) {
        for(var i = 0; i < searchboxs.length; i++) {
            $("#" + searchboxs[i]).parents("div").removeClass("ipt-search-box");
        }
    }
};

function FasBillController() { }

FasBillController.prototype.init = function(modulePath, options_) {
    this.modulePath = modulePath;
    this.options = options_;
};

FasBillController.prototype.add = function() {
	var $this = this;
	$("#"+$this.options.dataFormId).form("clear");
	$("#"+$this.options.dataFormId).find("input").val("");
	$("#"+$this.options.dataFormId).find("textarea").val("");
	$("#"+$this.options.dataFormId).find("input").removeAttr("readonly").removeClass("readonly");
	$("#"+$this.options.dataFormId).find("textarea").removeAttr("readonly").removeClass("readonly");
};

FasBillController.prototype.save = function() {
	var $this = this;
	if($('#id').val() != '') {
		if(!$this.checkSave($this.options)) {
			return;
		}
		$("#"+$this.options.dataFormId).form('submit', {
			url : $this.options.updateUrl,
			onSubmit : function(param) {
				param.status = 1;
			},
			success : function(result) {
				$this.successAddFn(result, $this.options);
			}
		});
	} else {
		if(!$this.checkSave($this.options)) {
			return;
		}
		$("#"+$this.options.dataFormId).form('submit', {
			url : $this.options.addUrl,
			onSubmit : function(param) {
				param.status = 1;
			},
			success : function(result) {
				$this.successUpdateFn(result, $this.options);
			}
		});
	}
};

FasBillController.prototype.checkSave = function(options) {
	return true;
}

FasBillController.prototype.buildAddSubmitParams = function() {
	
};

FasBillController.prototype.successAddFn = function(result, options) {
	if(result) {
		showSuc('保存成功');
	} else {
		showError('保存失败');
	}
};

FasBillController.prototype.successUpdateFn = function(result, options) {
	if(result) {
		showSuc('保存成功');
	} else {
		showError('保存失败');
	}
};
