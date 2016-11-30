/**
 * Created by admin on 2014/9/26.
 */
function FasBillController(modulePath, params) {
	this.modulePath = modulePath;
	this.setting = params;
};

FasBillController.prototype.super = new FasController();

//当前编辑行
var editRowIndex = -1;

//单据查询当前选中行的索引
var curRowIndex = -1;

//上单
FasBillController.prototype.upBill = function() {
	var _this = this;
    if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
    type = 1;
    preBill(_this.setting.dataGridId, curRowIndex, type, _this.callBackBill);
};

// 下单
FasBillController.prototype.downBill = function() {
	var _this = this;
    if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
    type = 2;
    preBill(_this.setting.dataGridId, curRowIndex, type, _this.callBackBill);
};

// 上下单回调
FasBillController.prototype.callBackBill = function(curRowIndex, rowData) {
    if(type == 1 || type == 2) {
        if(rowData != null && rowData != '' && rowData != []) {
            this.loadDetail(curRowIndex, rowData);
            type = 0;
        } else {
            if(type == 1) {
                showInfo('已经是第一单');
            } else {
                showInfo('已经是最后一单');
            }
        }
    }
};

//新增
FasBillController.prototype.toAdd = function() {
    $("#" + this.setting.dataFormId).form("clear");
    this.initAdd();
};

//初始化新增操作
FasBillController.prototype.initAdd = function() {
	
};

//保存操作
FasBillController.prototype.save = function() {
	var _this = this;
	var id = $("#id").val();
	if(id) {
		this.super.checkUpdate = function() {
			var auditStatus = $("#auditStatus").val();
			if(auditStatus && auditStatus != '0') {
				alert("不允许修改!");
				return false;
			}
			return true;
		};
		this.super.update({
			dataFormId : _this.setting.dataFormId,
			updateUrl : _this.modulePath + _this.setting.updateUrl
		});
	} else {
		this.super.add({
			dataFormId : _this.setting.dataFormId,
			saveUrl : _this.modulePath + _this.setting.saveUrl
		});
	}
	this.super.successAddFn = function(result) {
		var resultData = JSON.parse(result);
		$("#" + _this.setting.dataFormId).form('load', resultData);
		$.fas.search({
			dataGridId : "dtlTab",
			searchUrl : "/bill_sale_balance/list.json?balanceNo="+resultData.billNo+"&balanceType=7",
			hasSearchForm : false
		})
	};
};

//初始化查看操作
FasBillController.prototype.initDetail = function(rowData) {

};

//点击切换到明细
FasBillController.prototype.loadDetail = function(rowIndex, rowData) {
    curRowIndex = rowIndex;
    $("#" + this.setting.dataFormId).form('load', rowData);
    this.setBillBottom(rowData);
    this.initDetail(rowData);
};

// 设置明细页面底部数据
FasBillController.prototype.setBillBottom = function(rowData) {
    $("#createUser").html(rowData.createUser);
    $("#createTime").html(rowData.createTime);
    $("#auditor").html(rowData.auditor);
    $("#auditTime").html(rowData.auditTime);
};

//查询
FasBillController.prototype.search = function() {
	this.super.search({
		searchFormId : this.setting.searchFormId,
		searchUrl : this.modulePath + this.setting.searchUrl,
		dataGridId : this.setting.dataGridId
	});
};

//清空
FasBillController.prototype.clear = function() {
	this.super.clear(this.setting.searchFormId);
};

// 列表批量删除
FasBillController.prototype.batchDel = function() {
	this.super.del({
		dataGridId : this.setting.dataGridId,
		delUrl : this.modulePath + this.setting.delUrl,
		searchBtn : this.setting.searchBtn
	});
};

//明细页面删除
FasBillController.prototype.del = function() {
	if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
	this.super.buildDelData = function() {
		var rows = [];
		var obj = new Object();
		obj["id"] = $("#id").val();
		rows.push(obj);
		return rows;
	};
	this.super.delFn({
		delUrl : this.modulePath + this.setting.delUrl,
		searchBtn : this.setting.searchBtn
	});
};

//审核
FasBillController.prototype.doAudit = function() {
	if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
	this.super.checkAudit = function() {
		if($("#auditStatus").val() == "1") {
			showInfo('该单据已审核');
			return false;
		}
		return true;
	};
	this.super.auditCallback = function(params, result) {
		if(result) {
	        showSuc("审核成功!");
	    } else {
	        showError("审核失败,请联系管理员!");
	    }
	};
	this.super.auditFn({
		dataGridId : this.setting.dataGridId,
		auditUrl : this.modulePath + this.setting.auditUrl,
		searchBtn : this.setting.searchBtn
	});
};

//反审核
FasBillController.prototype.doAntiAudit = function() {
	if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
	this.super.checkAntiAudit = function() {
		if($("#auditStatus").val() != "1") {
			showInfo('该单据已处于未审核状态');
			return false;
		}
		return true;
	};
	this.super.antiAuditCallback = function(params, result) {
		if(result) {
	        showSuc("反审核成功!");
	    } else {
	        showError("反审核失败,请联系管理员!");
	    }
	};
	this.super.antiAuditFn({
		dataGridId : this.setting.dataGridId,
		antiAuditUrl : this.modulePath + this.setting.antiAuditUrl,
		searchBtn : this.setting.searchBtn
	});
};

//激活
FasBillController.prototype.doEnable = function() {
	if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
	this.super.buildEnableData = function() {
		var rows = [];
		var obj = new Object();
		obj["id"] = $("#id").val();
		rows.push(obj);
		return rows;
	};
	this.super.enableFn({
		dataGridId : this.setting.dataGridId,
		enableUrl : this.modulePath + this.setting.enableUrl,
		searchBtn : this.setting.searchBtn
	});
};

//反审核
FasBillController.prototype.doUnable = function() {
	if(curRowIndex < 0) {
        showInfo('不存在当前单据');
        return;
    }
	this.super.buildUnableData = function() {
		var rows = [];
		var obj = new Object();
		obj["id"] = $("#id").val();
		rows.push(obj);
		return rows;
	};
	this.super.unableFn({
		dataGridId : this.setting.dataGridId,
		unableUrl : this.modulePath + this.setting.unableUrl,
		searchBtn : this.setting.searchBtn
	});
};

//导出
FasBillController.prototype.exportExcel = function() {
	this.super.exportExcel({
		dataGridId : this.setting.dataGridId,
		exportTitle : this.setting.exportTitle,
		exportUrl : this.modulePath + this.setting.exportUrl
	});
};

//清除样式
FasBillController.prototype.clearReadOnly = function(params) {
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
FasBillController.prototype.setReadOnly = function(params) {
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