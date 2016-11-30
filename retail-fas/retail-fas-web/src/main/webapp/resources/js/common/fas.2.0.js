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
		buildAddData : $this.options.buildAddData,
		afterAdd : $this.options.afterAdd
    });
};

FasEditorController.prototype.checkInsert = function(options) {
	if(options.billDtlEditor) {
		var id = $("#id").val();
		if(id == null || $.trim(id) == '') {
			if(typeof options.billSaveFn === 'function' && options.billSaveFn()) {
				return true;
			}
			return false;
		}
		return true;
	}
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
    
    var checkedRows = $("#" + $this.options.dataGridId).datagrid("getChecked");
    if(checkedRows.length < 1){
        showWarn("请选择要删除的记录!");
        return;
    }
    
	$.messager.confirm("确认","你确定要删除选中的记录?",function(r) {
		if (r) {
			$.fas.deleteEditorRow({
				dataGridId : $this.options.dataGridId || 'dtlDataGridDiv',
				checkDel : $this.options.checkDel
			});
		}
	});
};

FasEditorController.prototype.checkDel = function(options) {
	return true;
};

//保存
FasEditorController.prototype.save = function() {	
	var $this = this;
	if($.fas.endEditing($this.options.dataGridId)) {
		var insertedData = $("#"+$this.options.dataGridId).datagrid('getChanges','inserted');
		var updatedData = $("#"+$this.options.dataGridId).datagrid('getChanges','updated');
		var deletedData = $("#"+$this.options.dataGridId).datagrid('getChanges','deleted');
		var data = {
			inserted : JSON.stringify(insertedData),
			updated : JSON.stringify(updatedData),
			deleted : JSON.stringify(deletedData)
		};
		if(!$this.checkSave($this.options, data)) {
			$("#"+$this.options.dataGridId).datagrid('selectRow',$.fas.editIndex);
			$("#"+$this.options.dataGridId).datagrid('beginEdit', $.fas.editIndex);
			return;
		}
		$.messager.progress({
			title:'请稍后',
			msg:'正在处理中...'
		});
		ajaxRequest(BasePath + $this.options.saveUrl, data, function(result){
			if(result) {
				if(result.success) {
					showSuc("操作成功!");
					$.messager.progress('close');
					if($this.options.saveCallback) {
						$this.options.saveCallback();
					} else {
						$this.saveCallback($this.options);
					}
				} else {
                    if(result.message){
                        showWarn(result.message);
                    }else{
                        showWarn("操作失败,请检查数据或联系管理员!");
                    }
				}
				return;
			} else {
				showError("操作失败,请联系管理员!");
				$.messager.progress('close');
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
    $.fas.search({
        searchFormId : $this.options.searchFormId,
        dataGridId : $this.options.dataGridId,
        searchUrl : $this.modulePath + $this.options.searchUrl
    });
    $(".datagrid-header").find("input").attr("checked", false);
    //  $(":checkbox:eq(0)").attr("checked", false);
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
	var count = 0;
	$.each(checkedRows, function(index, item){
		if(item.status == '1') {
			count++;
		}
	});
	if(count == checkedRows.length) {
		showWarn('数据已启用，请勿重复操作');
        return false;
	}
	if(count > 0) {
		showWarn('只能启用已停用状态的数据，请重新选择');
        return false;
	}
    return true;
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
	var count = 0;
	$.each(checkedRows, function(index, item){
		if(item.status != '1') {
			count++;
		}
	});
	if(count == checkedRows.length) {
		showWarn('数据已停用，请勿重复操作');
        return false;
	}
	if(count > 0) {
		showWarn('只能停用已启用状态的数据，请重新选择');
        return false;
	}
    return true;
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

// 校验是否可进行审核操作
FasDialogController.prototype.checkAudit = function(checkedRows) {
	var count = 0;
	$.each(checkedRows, function(index, item){
		if(item.auditStatus == '1') {
			count++;
		}
	});
	if(count == checkedRows.length) {
		showWarn('数据已审核，无需再审核');
        return false;
	}
	if(count > 0) {
		showWarn('只能审核未审核状态的数据，请重新选择');
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

// 校验是否可进行反审核操作
FasDialogController.prototype.checkAntiAudit = function(checkedRows) {
	var count = 0;
	$.each(checkedRows, function(index, item){
		if(item.auditStatus != '1') {
			count++;
		}
	});
	if(count == checkedRows.length) {
		showWarn('数据未审核，请勿重复操作');
        return false;
	}
	if(count > 0) {
		showWarn('只能反审核已审核的数据，请重新选择');
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

// 导出excel
FasDialogController.prototype.exportExcel = function() {
    var $this = this;
    $.fas.exportExcel({
        dataGridId : $this.options.dataGridId,
        exportUrl : $this.modulePath + $this.options.exportUrl,
        exportTitle : $this.options.exportTitle,
        exportType : $this.options.exportType
    });
};

// 清空表单数据--隐藏域的值不能清空
FasDialogController.prototype.addDataFormClass = function(dataFormId) {
    $("#" + dataFormId).form("clear");
};

// 清空表单样式
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

/****************************** 单据(前面定义的add、update函数有些问题，尽量用后面那些toAdd。。。) *************************************************/
function FasBillController() { }

// 初始化单据
FasBillController.prototype.init = function(modulePath, options_) {
    this.modulePath = modulePath;
    this.options = options_;
    //单据查询当前选中行的索引
    this.curRowIndex = -1;
    if(options_.dataFormFields) {
    	this.setReadOnly(options_.dataFormFields);
    }
    // 单据明细增加折叠功能
    toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
    });
};

// 新增单据
FasBillController.prototype.add = function() {
	var $this = this;
	$("#"+$this.options.dataFormId).form("clear");
	$("#"+$this.options.dataFormId).find("input").val("");
	$("#"+$this.options.dataFormId).find("textarea").val("");
	$("#"+$this.options.dataFormId).find("input").removeAttr("readonly").removeClass("readonly");
	$("#"+$this.options.dataFormId).find("textarea").removeAttr("readonly").removeClass("readonly");
	// 清空明细tab页面的数据
	if($this.options.dtlDataGridIds) {
		$.each($this.options.dtlDataGridIds, function(index, item) {
			$("#" + item).clearDataGrid();
		});
	}
};

// 保存单据
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

// 检查是否能保存
FasBillController.prototype.checkSave = function(options) {
	return true;
};

// 组装新增操作的参数
FasBillController.prototype.buildAddSubmitParams = function() {
	
};

// 新增操作后的回调函数
FasBillController.prototype.successAddFn = function(result, options) {
	if(result) {
		showSuc('保存成功');
	} else {
		showError('保存失败');
	}
};

// 修改操作后的回调函数
FasBillController.prototype.successUpdateFn = function(result, options) {
	if(result) {
		showSuc('保存成功');
	} else {
		showError('保存失败');
	}
};

/*******************************************  单据类start **********************************************/
// 点击"新增"按钮的函数
FasBillController.prototype.toAddBill = function() {
	var $this = this;
	$("#" + $this.options.dataFormId).form("clear");
	$("#" + $this.options.dataFormId).find("input").val("");
	$("#" + $this.options.dataFormId).find("textarea").val("");
	$("#" + $this.options.dataFormId).find("input").removeAttr("readonly").removeClass("readonly");
	$("#" + $this.options.dataFormId).find("textarea").removeAttr("readonly").removeClass("readonly");
	// 清空明细tab页面的数据
	if($this.options.dtlDataGridIds) {
		$.each($this.options.dtlDataGridIds, function(index, item) {
			$("#" + item).clearDataGrid();
		});
	}
	// 清除样式
	if($this.options.dataFormFields) {
		$this.clearReadOnly($this.options.dataFormFields);
	}
	// 设置一直处于只读状态的字段
	if($this.options.readOnlyFields) {
		$this.setReadOnly($this.options.readOnlyFields);
	}
	// 设置默认的单据状态
	$("#status").val("0");
	$("#statusName").val("制单");
	$("#dtlTab").tabs("select", 0);
	$this.clearBillBottom();
};

// 保存单据
FasBillController.prototype.saveBill = function() {
	var $this = this;
	var id = $('#id').val();
	if(typeof id != 'undefined' && $.trim(id) != '') {
		return $this.updateBill();
	} else {
		return $this.addBill();
	}
};

// 新增单据
FasBillController.prototype.addBill = function() {
	var $this = this;
	if(!$this.checkAdd($this.options)) {
		return;
	}
	return $("#" + $this.options.dataFormId).form('submit', {
		url : BasePath + $this.modulePath + $this.options.addUrl,
		onSubmit : function(param) {
			
		},
		success : function(result) {
			return $this.addBillFn(result, $this.options);
		}
	});
};

// 检查单据是否可新增
FasBillController.prototype.checkAdd = function(options) {
	var validateForm = $("#" + options.dataFormId).form('validate');
    if(validateForm == false) {
        return false;
    }
	return true;
};

// 新增操作后的回调函数
FasBillController.prototype.addBillFn = function(result, options) {
	var $this = this;
	var flag = false;
	if(result) {
		showSuc('新增成功');
		var resultData = JSON.parse(result);
		$("#" + options.dataFormId).form('load', resultData);
		// 设置只读样式
		if(options.dataFormFields) {
			$this.setReadOnly(options.dataFormFields);
		}
		// 清除单据名称和备注字段的只读样式
		$("#billName,#balanceDate,#rebateAmount,#invoiceRebateAmount,#remark").removeClass("readonly").removeAttr("readonly");
		$("#dtlTab").tabs("select", 0);
		return true;
	} else {
		showError('新增失败');
		return false;
	}
};

// 修改操作后的回调函数
FasBillController.prototype.updateBill = function() {
	var $this = this;
	if(!$this.checkUpdate($this.options)) {
		return;
	}
	return $("#" + $this.options.dataFormId).form('submit', {
		url : BasePath + $this.modulePath + $this.options.updateUrl,
		onSubmit : function(param) {
			
		},
		 success : function(result) {
			 return $this.updateBillFn(result, $this.options);
	    }
	})
};

// 检查是否可修改单据
FasBillController.prototype.checkUpdate = function(options) {
	var validateForm = $("#" + options.dataFormId).form('validate');
    if(validateForm == false) {
        return false;
    }
	return true;
};

// 修改操作后的回调函数
FasBillController.prototype.updateBillFn = function(result, options) {
	var $this = this;
	if(result) {
		showSuc('修改成功');
		var resultData = JSON.parse(result);
		$("#" + options.dataFormId).form('load', resultData);
		// 设置只读样式
		if(options.dataFormFields) {
			$this.setReadOnly($this.options.dataFormFields);
		}
		// 清除单据名称和备注字段的只读样式
		
		$("#billName,#balanceDate,#rebateAmount,#invoiceRebateAmount,#remark").removeClass("readonly").removeAttr("readonly");
		$("#dtlTab").tabs("select", 0);
		return true;
	} else {
		showError('修改失败');
		return false;
	}
};

// 新增页面删除单据
FasBillController.prototype.delBill = function() {
	var $this = this;
	if($("#id").val() && $("#id").val()=="") {
        showInfo('不存在当前单据');
        return;
    }
	$.fas.delBill({
		url : $this.modulePath + $this.options.delUrl,
		checkDel : $this.checkDel,
		buildDelData : $this.buildDelData,
		delCallback : $this.delBillFn
	});
};

//检查新增页面的单据是否可删除
FasBillController.prototype.checkDel = function(options) {
	var id = $("#id").val();
	if(typeof id == 'undefined' || $.trim(id) == '') {
		showWarn("请选择您要删除的单据!");
		return;
	}
	var billStatus = $("#status").val();
	if(billStatus != '0' && billStatus != '99') {
		showWarn("请选择制单或打回状态的单据进行删除！");
		return false;
	}
	return true;
} 

//组装新增页面中删除操作时，所需的数据
FasBillController.prototype.buildDelData = function(options) {
	var deleteList = [];
	var obj = new Object();
	obj.id = $("#id").val();
	obj.billNo = $("#billNo").val();
	deleteList.push(obj);
	return deleteList;
};

// 新增页面删除后的回调方法
FasBillController.prototype.delBillFn = function(options, result) {
	if(result) {
		showSuc('删除成功');
		$("#" + options.searchBtn).click();
	} else {
		showError('删除失败');
	}
};

// 列表页面批量删除
FasBillController.prototype.batchDelBill = function() {
	var $this = this;
	$.fas.del({
		dataGridId : $this.options.listDataGridId,
		url : $this.modulePath + $this.options.delUrl,
		searchBtn : $this.options.searchBtn,
		checkDel : $this.checkBatchDel,
		buildDelData : $this.buildBatchDelData,
		delCallback : $this.batchDelBillFn
	});
};

//检查是否可进行批量删除
FasBillController.prototype.checkBatchDel = function(checkedRows) {
	var delFlag = true;
	$.each(checkedRows, function(index, item){
		if(item.status != '0' && item.status != '99') {
			showWarn("单据[" + item.billNo + "]不是制单或打回状态，不能删除！");
			delFlag = false;
			return false;
		}
	});
	return delFlag;
};

// 组装批量删除的数据集
FasBillController.prototype.buildBatchDelData = function(checkedRows) {
	var deleteList = [];
	$.each(checkedRows, function(index, item){
		var obj = new Object();
		obj.id = item.id;
		obj.billNo = item.billNo;
		deleteList.push(obj);
	});    
	return deleteList;
};

// 批量删除后的回调方法
FasBillController.prototype.batchDelBillFn = function(options, result) {
	if(result) {
		showSuc('删除成功');
		$("#" + options.searchBtn).click();
	} else {
		showError('删除失败');
	}
};

//单据确认操作: type->0 新增页面操作，type->1 列表页面操作 
FasBillController.prototype.confirm = function(type, status) {
	var $this = this;
	if(!$this.checkConfirm(type, status)) {
		return;
	}
	var confirmList = $this.buildConfirmData(type, status);
	if(confirmList.length < 1) {
		showWarn("请选择您要操作的数据！");
		return;
	}
	var confirmMessage = status == '99' ? "打回" : "确认";
	$.messager.confirm("确认","你确定要"+confirmMessage+"这"+confirmList.length+"条数据", function(r) {
        if(r) {
            //如果需要自己组装删除数据，则可自定义
            var effectRow = new Object();
            effectRow["confirm"] = JSON.stringify(confirmList);
            var url = BasePath + $this.modulePath + $this.options.confirmUrl;
            ajaxRequest(url, effectRow, function(result){
                $this.confirmCallback(result, type, status, $this.options);
            });
        }
    });
};

// 检查是否可进行确认操作
FasBillController.prototype.checkConfirm = function(type, status) {
	var $this = this;
	if(type == '0') {
		var billStatus = $("#status").val();
		if(billStatus == "") {
			showWarn("请选择您要操作的单据！");
			return false;
		}
		if(status == '99' && (billStatus != '3' && billStatus != '4')) {
			showWarn("只允许打回已确认的单据！");
			return false;
		}
	} else {
		var checkedRows = $("#" + $this.options.listDataGridId).datagrid("getChecked");
		if(status == '99') {
			var confirmFlag = true;
			$.each(checkedRows, function(index, item){
				if(item.status != '3' && item.status != '4') {
					showWarn("只允许打回已确认的单据！");
					confirmFlag = false;
					return false;
				}
			});
			return confirmFlag;
		}
	}
	return true;
};

// 组装确认操作的数据
FasBillController.prototype.buildConfirmData = function(type, status) {
	var $this = this;
	var confirmList = [];
	if(type == '0') {
		var obj = new Object();
		obj.id = $("#id").val();
		obj.billNo = $("#billNo").val();
		obj.status = status;
		confirmList.push(obj);
	} else {
		var checkedRows = $("#" + $this.options.listDataGridId).datagrid("getChecked");
		$.each(checkedRows, function(index, item){
			var obj = new Object();
			obj.id = item.id;
			obj.billNo = item.billNo;
			obj.status = status;
			confirmList.push(obj);
		});    
	}
	return confirmList;
};

// 确认操作的回调方法
FasBillController.prototype.confirmCallback = function(result, type, status, options) {
	var $this = this;
	if(result) {
		if(type == '0') {
			showSuc('确认成功');
			$this.loadDetail($this.curRowIndex, result);
		} else {
			$("#" + options.searchBtn).click();
		}
	} else {
		showWarn("操作失败，请联系管理员！");
	}
};

//上单
FasBillController.prototype.upBill = function() {
	var $this = this;
    if($this.curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
    type = 1;
    preBill($this.options.listDataGridId, $this.curRowIndex, type, function(curRowIndex, rowData) {
		if(type == 1 || type == 2) {
	        if(rowData != null && rowData != '' && rowData != []) {
	        	$this.loadDetail(curRowIndex, rowData);
	        	type = 0;
	        } else {
	            if(type == 1) {
	                showInfo('已经是第一单');
	            } else {
	                showInfo('已经是最后一单');
	            }
	        }
	    }
	});
};

// 下单
FasBillController.prototype.downBill = function() {
	var $this = this;
	if($this.curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
	var type = 2;
	preBill($this.options.listDataGridId, $this.curRowIndex, type, function(curRowIndex, rowData) {
		if(type == 1 || type == 2) {
	        if(rowData != null && rowData != '' && rowData != []) {
	        	$this.loadDetail(curRowIndex, rowData);
	        	type = 0;
	        } else {
	            if(type == 1) {
	                showInfo('已经是第一单');
	            } else {
	                showInfo('已经是最后一单');
	            }
	        }
	    }
	});
};

// 列表页面，双击明细数据时，加载明细数据
FasBillController.prototype.loadDetail = function(rowIndex, rowData) {
	var $this = this;
	$this.curRowIndex = rowIndex;
	$("#" + $this.options.dataFormId).form('load', rowData);
	// 制单状态的单据可修改
	if(rowData.status == '0' || rowData.status == '99') {
		// 设置只读样式
		if($this.options.dataFormFields) {
			$this.setReadOnly($this.options.dataFormFields);
		}
		// 清除单据名称和备注字段的只读样式
		$("#billName,#balanceDate,#rebateAmount,#invoiceRebateAmount,#remark").removeClass("readonly").removeAttr("readonly");
	} else {
		if($this.options.dataFormFields) {
			$this.setReadOnly($this.options.dataFormFields);
		}
	}
	$("#dtlTab").tabs("select", 0);
	returnTab('mainTab', '单据明细');
	$this.setBillBottom(rowData);
};

// 设置单据底部审核数据
FasBillController.prototype.setBillBottom = function(rowData) {
	if(rowData) {
		$('#status').html(rowData.status);
		$('#createUser').html(rowData.createUser);
		$('#createTime').html(rowData.createTime);
		$('#auditor').html(rowData.auditor);
		$('#auditTime').html(rowData.auditTime);
	}
};

//清空单据底部审核数据
FasBillController.prototype.clearBillBottom = function() {
	$('#status').html("");
	$('#createUser').html("");
	$('#createTime').html("");
	$('#auditor').html("");
	$('#auditTime').html("");
};

//清除样式
FasBillController.prototype.clearReadOnly = function(params) {
	var inputs = params.input;
	var dates = params.date;
	var comboboxs = params.combobox;
	var combogrids = params.combogrid;
	var dgSelectors = params.dgSelector;
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
	if(comboboxs && comboboxs.length > 0) {
		for(var i = 0; i < comboboxs.length; i++) {
			$("#" + comboboxs[i]).combobox("enable");
		}
	}
	if(combogrids && combogrids.length > 0) {
		for(var i = 0; i < combogrids.length; i++) {
			$("#" + combogrids[i]).combogrid("enable");
		}
	}
	if(dgSelectors && dgSelectors.length > 0) {
		for(var i = 0; i < dgSelectors.length; i++) {
			$("#" + dgSelectors[i]).iptSearch("enable");
		}
	}
};

//设置样式
FasBillController.prototype.setReadOnly = function(params) {
	var inputs = params.input;
	var dates = params.date;
	var comboboxs = params.combobox;
	var combogrids = params.combogrid;
	var dgSelectors = params.dgSelector;
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
	if(comboboxs && comboboxs.length > 0) {
		for(var i = 0; i < comboboxs.length; i++) {
			$("#" + comboboxs[i]).combobox("disable");
		}
	}
	if(combogrids && combogrids.length > 0) {
		for(var i = 0; i < combogrids.length; i++) {
			$("#" + combogrids[i]).combogrid("disable");
		}
	}
	if(dgSelectors && dgSelectors.length > 0) {
		for(var i = 0; i < dgSelectors.length; i++) {
			$("#" + dgSelectors[i]).iptSearch("disable");
		}
	}
};
/************************************************* 单据类end *********************************************/