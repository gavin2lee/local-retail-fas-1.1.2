/**
 * Created by admin on 2014/9/25.
 */
function FasDialogController(modulePath, setting) {
	this.modulePath = modulePath;
	this.setting = setting;
}

FasDialogController.prototype.super = new FasController();

FasDialogController.prototype.search = function() {
	this.super.search({
		dataGridId : this.setting.dataGridId,
		searchFormId : this.setting.searchFormId,
		searchUrl : this.modulePath + this.setting.searchUrl
	});
	$(":checkbox:eq(0)").attr("checked", false);
};

FasDialogController.prototype.clear = function() {
	this.super.clear(this.setting.searchFormId);
};

FasDialogController.prototype.toAdd = function() {
    this.dataFormId = this.setting.dataFormId;
    this.dialogWidth = this.setting.dialogWidth;
    if((document.documentElement.clientHeight - 60) <  this.setting.dialogHeight) {
    	this.dialogHeight = document.documentElement.clientHeight - 60;
    } else {
    	this.dialogHeight = this.setting.dialogHeight;
    }
    // 如果需要在新增操作时，进行一些其他的逻辑处理，可自定义extra_operate.initAdd函数
    if(typeof(this.initAdd) == 'function'){
    	this.initAdd();
    }
    var _this = this;
    ygDialog({
        title : '新增',
        target : $('#myFormPanel'),
        width : _this.dialogWidth,
        height : _this.dialogHeight,
        buttons : [{
            text : '保存',
            iconCls : 'icon-save',
            handler : function(dialog) {
                _this.add();
            }
        }, {
            text : '取消',
            iconCls : 'icon-cancel',
            handler : function(dialog) {
                dialog.close();
            }
        }]
    });
    this.addDataFormClass(this.setting.dataFormId);
    this.super.loadedAdd();
};

FasDialogController.prototype.initAdd = function() {
	
}

FasDialogController.prototype.add = function() {
    this.super.add({
    	dataFormId : this.setting.dataFormId,
    	saveUrl : this.modulePath + this.setting.saveUrl,
    	searchBtn : this.setting.searchBtn
    });
};

FasDialogController.prototype.checkInitUpdate = function(rowData) {
	return true;
};

FasDialogController.prototype.toUpdate = function(rowData) {
    this.dataGridId = this.setting.dataGridId;
    this.dataFormId = this.setting.dataFormId;
    this.dialogWidth = this.setting.dialogWidth;
    if((document.documentElement.clientHeight - 60) <  this.setting.dialogHeight) {
    	this.dialogHeight = document.documentElement.clientHeight - 60;
    } else {
    	this.dialogHeight = this.setting.dialogHeight;
    }
    this.updateUrl = this.modulePath + this.setting.updateUrl;
    var _this = this;
    if(rowData) {
    	_this.initUpdate();
    	_this.addDataFormClass();
    	$('#' + _this.dataFormId).form('load', rowData);
    } else {
    	// 获取所有勾选checkbox的行
    	var checkedRows = $("#" + this.dataGridId).datagrid("getChecked");
    	if(checkedRows.length < 1) {
    		showWarn('请选择要修改的记录!', 1);
    		return;
    	}
    	if(checkedRows.length < 1) {
    		showWarn('只能选择一条记录进行修改!');
    		return;
    	}
    	_this.initUpdate();
    	_this.addDataFormClass();
    }
    rowData = rowData || checkedRows[0];
    if(!this.checkInitUpdate(rowData)) {
    	return;
    }
    $('#' + _this.dataFormId).form('load', rowData);
    ygDialog({
        title : '修改',
        target : $('#myFormPanel'),
        width : _this.dialogWidth,
        height :_this.dialogHeight,
        buttons : [{
            text : '保存',
            iconCls : 'icon-save',
            handler : function(dialog) {
                _this.update({
                	dataFormId : _this.dataFormId,
                	updateUrl : _this.updateUrl,
                	searchBtn : _this.setting.searchBtn
                });
            }
        }, {
            text : '取消',
            iconCls : 'icon-cancel',
            handler : function(dialog) {
                dialog.close();
            }
        }]
    });
    _this.loadedUpdate(rowData);
};

FasDialogController.prototype.initUpdate = function() {
	
};

FasDialogController.prototype.loadedUpdate = function(rowData) {
	
};

FasDialogController.prototype.update = function(params) {
	var _this = this;
	_this.super.successUpdateFn = function(result) {
        if(result && (JSON.parse(result).success
            || typeof JSON.parse(result).success == 'undefined')) {
            showSuc('修改成功!');
            $("#myFormPanel").window('close');
            _this.addDataFormClass(params.dataFormId);
            $("#" + params.searchBtn).click();
            return;
        } else {
            showError('修改失败,请联系管理员!');
        }
    };
    _this.super.update(params);
};

//列表批量删除
FasDialogController.prototype.doDel = function() {
	this.super.del({
		dataGridId : this.setting.dataGridId,
		delUrl : this.modulePath + this.setting.delUrl,
		searchBtn : this.setting.searchBtn
	});
};

FasDialogController.prototype.doEnable = function() {
	var _this = this;
	this.super.checkEnable = function(checkedRows) {
		if(checkedRows.length === 1 && checkedRows[0].status == '1') {
			showWarn('数据已启用，无需再启用');
			return false;
		}
		return true;
	};
	this.super.buildEnableData = function(checkedRows) {
		return _this.buildEnableData(checkedRows);
	};
    this.super.doEnable({
    	dataGridId : this.setting.dataGridId,
    	enableUrl : this.modulePath + this.setting.enableUrl,
    	searchBtn : this.setting.searchBtn
    });
};


FasDialogController.prototype.buildEnableData = function(checkedRows) {
	var enableList = [];
	$.each(checkedRows, function(index, item) {
		var obj = new Object();
		obj.id = item.id;
		enableList.push(obj);
	});     
    return enableList;
};


FasDialogController.prototype.doUnable = function() {
	var _this = this;
	this.super.checkUnable = function(checkedRows) {
		return _this.checkUnable(checkedRows);
	};
	this.super.buildUnableData = function(checkedRows) {
		return _this.buildUnableData(checkedRows);
	};
    this.super.doUnable({
    	dataGridId : this.setting.dataGridId,
    	unableUrl : this.modulePath + this.setting.unableUrl,
    	searchBtn : this.setting.searchBtn
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
	if(checkedRows.length === 1 && checkedRows[0].status != '1') {
		showWarn('数据已停用，请勿重复操作');
		return false;
	}
	return true;
};

//审核
FasDialogController.prototype.doAudit = function() {
	var _this = this;
	this.super.checkAudit = function(checkedRows) {
		return _this.checkAudit(checkedRows);
	};
	this.super.doAudit({
		dataGridId : this.setting.dataGridId,
		auditUrl : this.modulePath + this.setting.auditUrl,
		searchBtn : this.setting.searchBtn
	});
};

FasDialogController.prototype.checkAudit = function(checkedRows) {
	if(checkedRows.length === 1 && checkedRows[0].auditStatus == '1') {
		showWarn('数据已审核，无需再审核');
		return false;
	}
	return true;
};

//反审核
FasDialogController.prototype.doAntiAudit = function() {
	var _this = this;
	this.super.checkAntiAudit = function(checkedRows) {
		return _this.checkAntiAudit(checkedRows);
	};
	this.super.doAntiAudit({
		dataGridId : this.setting.dataGridId,
		antiAuditUrl : this.modulePath + this.setting.antiAuditUrl,
		searchBtn : this.setting.searchBtn
	});
};

FasDialogController.prototype.checkAntiAudit = function(checkedRows) {
	if(checkedRows.length === 1 && checkedRows[0].auditStatus != '1') {
		showWarn('数据未审核，请勿重复操作');
		return false;
	}
	return true;
};

FasDialogController.prototype.exportExcel = function() {
    this.super.exportExcel({
    	dataGridId : this.setting.dataGridId,
    	exportUrl : this.modulePath + this.setting.exportUrl,
    	exportTitle : this.setting.exportTitle,
    	exportType : this.setting.exportType
    });
};

FasDialogController.prototype.addDataFormClass = function(dataFormId) {
    $("#" + dataFormId).form("clear");
//    $("#" + dataFormId + " input").removeAttr("readonly").removeClass("disabled");
//    $("#" + dataFormId + " textarea").removeAttr("disabled").removeClass("disabled");
//    $("#" + dataFormId + " input[class*=easyui-combobox]").combobox('enable');
//    $("#" + dataFormId + " select[class*=easyui-combobox]").combobox('enable');
//    $("#" + dataFormId + " input[class*=easyui-search]").parents("div").addClass("ipt-search-box");
//    $("#" + dataFormId + " input[class*=easyui-datebox]").removeAttr("disabled","false");
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