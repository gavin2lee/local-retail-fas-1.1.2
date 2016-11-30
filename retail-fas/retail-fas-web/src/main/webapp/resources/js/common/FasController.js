/**
 * Created by admin on 2014/9/25.
 */
function FasController() { }

//查询
FasController.prototype.search = function(params) {
    if(params.searchFormId) {
    	var reqParam = $("#" + params.searchFormId).form("getData");
    	//组装请求参数
    	$("#" + params.dataGridId).datagrid('options').queryParams = reqParam;
    }
    var queryMxURL = BasePath + params.searchUrl;
    $("#" + params.dataGridId).datagrid('options').url = queryMxURL;
    $("#" + params.dataGridId).datagrid('load');
    if(params.checkboxIndex) {
    	$(":checkbox:eq("+(params.checkboxIndex + 1)+")").attr("checked", false);
    }
};

//清空
FasController.prototype.clear = function(formId) {
    $("#" + formId).form("clear");
};

//新增页面未展现是的操作
FasController.prototype.initAdd = function() {
	
};

//新增页面展现后的操作
FasController.prototype.loadedAdd = function() {
	
};

//进入新增页面
FasController.prototype.toAdd = function(params) {
	this.initAdd();
	this.loadedAdd();
};

//检验表单数据是否正确
FasController.prototype.validateForm = function(formObj) {
    var validateForm = formObj.form('validate');
    if(validateForm == false) {
        return false;
    }
    return true;
};

//验证是否可保存
FasController.prototype.checkSave = function() {
	return true;
};

//组建新增/修改操作的参数数据
FasController.prototype.initSubmitParams = function() {
    return [];
};

//新增成功的回调函数
FasController.prototype.successAddFn = function(result, params) {
	if(result && (JSON.parse(result).success
        || typeof JSON.parse(result).success == 'undefined')) {
        showSuc('新增成功!');
        $("#myFormPanel").window('close');
        $("#"+ params.searchBtn).click();
    } else {
        showError('新增失败,请联系管理员!');
    }
};

//新增数据的操作
FasController.prototype.add = function(params) {
    this.saveUrl = BasePath + params.saveUrl;
    this.formObj = $("#" + params.dataFormId);
    var _this = this;
    if(!_this.validateForm(_this.formObj)) {
        return;
    }
    if(!_this.checkSave()) {
    	return;
    }
    _this.formObj.form('submit', {
        url : _this.saveUrl,
        dataType : 'json',
        onSubmit : function(extraParams) {
        	var submitParams = _this.initSubmitParams();
    	    if(submitParams != null && submitParams.length > 0) {
    	        for(var i = 0; i < submitParams.length; i++) {
    	            var paramName = submitParams[i].name;
    	            extraParams[paramName] = submitParams[i].value;
    	        }
    	    }
        },
        success : function(result) {
            _this.successAddFn(result, params);
        },
        error : function() {
            showError('新增失败,请联系管理员!');
        }
    });
};

//进入修改页面的操作
FasController.prototype.toUpdate = function(rowData, params) {
	this.initUpdate();
	this.loadedUpdate();
};

//修改页面未展现是的操作
FasController.prototype.initUpdate = function() {
	
};

//修改页面展现后的操作
FasController.prototype.loadedUpdate = function() {
	
};

//验证是否可修改
FasController.prototype.checkUpdate = function() {
	return true;
};

// 修改成功的回调函数
FasController.prototype.successUpdateFn = function(result, params) {
	if(result && (JSON.parse(result).success
            || typeof JSON.parse(result).success == 'undefined')) {
        showSuc("修改成功!");
        $("#myFormPanel").window('close');
        $("#"+ params.searchBtn).click();
    } else {
        showError("修改失败,请联系管理员!");
    }
};

//修改操作
FasController.prototype.update = function(params) {
    this.updateUrl = BasePath + params.updateUrl;
    this.formObj = $("#" + params.dataFormId);
    var _this = this;
    // 1.校验必填项
    if(!_this.validateForm(_this.formObj)) {
        return;
    }
    if(!_this.checkUpdate()) {
    	return;
    }
    // 修改
    this.formObj.form('submit', {
        url : _this.updateUrl,
        dataType : 'json',
        onSubmit : function(extraParams) {
        	var submitParams = _this.initSubmitParams();
    	    if(submitParams != null && submitParams.length > 0) {
    	        for(var i = 0; i < submitParams.length; i++) {
    	            var paramName = submitParams[i].name;
    	            extraParams[paramName] = submitParams[i].value;
    	        }
    	    }
        },
        success : function(result) {
            _this.successUpdateFn(result, params);
        },
        error : function() {
            showError("修改失败,请联系管理员!");
        }
    });
};

//如果需要在删除操作时，进行一些其他的逻辑处理，可自定义checkDel函数
FasController.prototype.checkDel = function(checkedRows) {
    return true;
};

//组装删除操作的数据
FasController.prototype.buildDelData = function(checkedRows) {
    var deleteList = [];
    $.each(checkedRows, function(index, item) {
        var obj = new Object();
        obj.id = item.id;
        deleteList.push(obj);
    });
    return deleteList;
};

//组装后的回调函数
FasController.prototype.delCallback = function(params, result) {
    if(result) {
        showSuc("删除成功!");
        $("#"+ params.searchBtn).click();
    } else {
        showError("删除失败,请联系管理员!");
    }
};

//删除
FasController.prototype.del = function(params) {
    var _this = this;
    var checkedRows = $("#" + params.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
    if(checkedRows.length < 1){
        showWarn("请选择要删除的记录!");
        return;
    }
    _this.delFn(params, checkedRows);
};

//删除操作逻辑处理部分
FasController.prototype.delFn = function(params, checkedRows) {
	var _this = this;
	_this.delUrl = BasePath + params.delUrl;
    // 校验数据是否可删除
    if(!_this.checkDel(checkedRows)) {
        return;
    }
    var deleteLen = "";
    if(typeof checkedRows != "undefined" && checkedRows != null) {
        deleteLen = checkedRows.length;
    }
    $.messager.confirm("确认","你确定要删除这"+deleteLen+"条数据", function(r) {
        if(r) {
            //如果需要自己组装删除数据，则可自定义
            var deleteList = _this.buildDelData(checkedRows);
            if(deleteList.length > 0) {
                var effectRow = new Object();
                effectRow["deleted"] = JSON.stringify(deleteList);
                ajaxRequest(_this.delUrl, effectRow, function(result){
                    _this.delCallback(params, result);
                });
            } else {
                showError("删除失败!");
            }
        }
    });
};

//审核操作
FasController.prototype.doAudit = function(params) {
	 this.dataGridId = params.dataGridId;
	 var _this = this;
	 var checkedRows = $("#" + this.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	 if(checkedRows.length < 1) {
		showWarn("请选择要审核的记录!");
		return;
	}
	_this.auditFn(params, checkedRows);
};

//审核操作逻辑处理部分
FasController.prototype.auditFn = function(params, checkedRows) {
	var _this = this;
	_this.auditUrl = BasePath + params.auditUrl;
	 // 校验数据是否可审核
    if(!_this.checkAudit(checkedRows)) {
        return;
    }
	var auditLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		auditLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要审核这"+auditLen+"条数据", function(r) {  
        if(r) {
        	var auditList = _this.buildAuditData(checkedRows);
        	if(auditList.length > 0) {
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(auditList);
	            $.messager.progress({
        			title:'请稍后',
        			msg:'正在处理中...'
        		});
	            ajaxRequest(_this.auditUrl, effectRow, function(result) {
	            	$.messager.progress('close');
        			 _this.auditCallback(params, result);
	        	}); 
        	} else{
        		showError("审核失败!");
        	}
        }  
    });  
};

//如果需要在审核操作时，进行一些其他的逻辑处理，可自定义checkAudit函数
FasController.prototype.checkAudit = function(checkedRows) {
    return true;
};

//组装审核操作的数据
FasController.prototype.buildAuditData = function(checkedRows) {
	var auditList = [];
	$.each(checkedRows, function(index, item) {
		var obj = new Object();
		obj.id = item.id;
		auditList.push(obj);
	});     
    return auditList;
};

//组装审核后的回调函数
FasController.prototype.auditCallback = function(params, result) {
    if(result) {
        showSuc("审核成功!");
        $("#"+ params.searchBtn).click();
    } else {
        showError("审核失败,请联系管理员!");
    }
};

//反审核操作
FasController.prototype.doAntiAudit = function(params) {
	this.dataGridId = params.dataGridId;
	var _this = this;
	var checkedRows = $("#" + this.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1) {
		showWarn("请选择要反审核的记录!");
		return;
	}
	_this.antiAuditFn(params, checkedRows);
};

//反审核操作逻辑处理部分
FasController.prototype.antiAuditFn = function(params, checkedRows) {
	var _this = this;
	_this.antiAuditUrl = BasePath + params.antiAuditUrl;
	 // 校验数据是否可审核
    if(!_this.checkAntiAudit(checkedRows)) {
        return;
    }
	var antiAuditLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		antiAuditLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要反审核这"+antiAuditLen+"条数据", function(r) {  
        if(r) {
        	var antiAuditList = _this.buildAntiAuditData(checkedRows);
        	if(antiAuditList.length > 0) {
	            var effectRow = new Object();
	            $.messager.progress({
        			title:'请稍后',
        			msg:'正在处理中...'
        		});
	            effectRow["deleted"] = JSON.stringify(antiAuditList);
	            ajaxRequest(_this.antiAuditUrl, effectRow, function(result){
	            	$.messager.progress('close');
        			_this.antiAuditCallback(params, result);
	        	}); 
        	} else {
        		showError("反审核失败!");
        	}
        }  
    });  
};

//如果需要在审核操作时，进行一些其他的逻辑处理，可自定义checkAntiAudit函数
FasController.prototype.checkAntiAudit = function(checkedRows) {
    return true;
};

//组装反审核操作的数据
FasController.prototype.buildAntiAuditData = function(checkedRows) {
	var antiAuditList = [];
	$.each(checkedRows, function(index, item) {
		var obj = new Object();
		obj.id = item.id;
		antiAuditList.push(obj);
	});     
    return antiAuditList;
};

//组装反审核后的回调函数
FasController.prototype.antiAuditCallback = function(params, result) {
    if(result) {
        showSuc("反审核成功!");
        $("#"+ params.searchBtn).click();
    } else {
        showError("反审核失败,请联系管理员!");
    }
};

//激活操作
FasController.prototype.doEnable = function(params) {
	var _this = this;
	var checkedRows = $("#" + params.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1) {
		showWarn("请选择要启用的记录!");
		return;
	}
	_this.enableFn(params, checkedRows);
};


//激活操作逻辑处理部分
FasController.prototype.enableFn = function(params, checkedRows) {
	var _this = this;
	_this.enableUrl = BasePath + params.enableUrl;
	 // 校验数据是否可激活
    if(!_this.checkEnable(checkedRows)) {
        return;
    }
	var enableLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		enableLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要启用这"+enableLen+"条数据", function(r) {  
        if(r) {
        	var enableList = _this.buildEnableData(checkedRows);
        	if(enableList.length > 0) {
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(enableList);
	            ajaxRequest(_this.enableUrl, effectRow, function(result) {
	        		_this.enableCallback(params, result);
	        	}); 
        	} else {
        		showError("操作失败!");
        	}
        }  
    });  
};

//如果需要在激活操作时，进行一些其他的逻辑处理，可自定义checkEnable函数
FasController.prototype.checkEnable = function(checkedRows) {
    return true;
};

//组装激活操作的数据
FasController.prototype.buildEnableData = function(checkedRows) {
	var enableList = [];
	$.each(checkedRows, function(index, item) {
		var obj = new Object();
		obj.id = item.id;
		enableList.push(obj);
	});     
    return enableList;
};

//组装激活操作后的回调函数
FasController.prototype.enableCallback = function(params, result) {
    if(result) {
        showSuc("启用操作成功!");
        $("#"+ params.searchBtn).click();
    } else {
        showError("启用操作失败,请联系管理员!");
    }
};

//注销操作
FasController.prototype.doUnable = function(params) {
	var _this = this;
	var checkedRows = $("#" + params.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1) {
		showWarn("请选择要停用的记录!");
		return;
	}
	_this.unableFn(params, checkedRows);
};

//注销操作逻辑处理部分
FasController.prototype.unableFn = function(params, checkedRows) {
	var _this = this;
	_this.unableUrl = BasePath + params.unableUrl;
	 // 校验数据是否可注销
    if(!_this.checkUnable(checkedRows)) {
        return;
    }
	var unableLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		unableLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要停用这"+unableLen+"条数据", function(r) {  
        if(r) {
        	var unableList = _this.buildUnableData(checkedRows);
        	if(unableList.length > 0) {
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(unableList);
	            ajaxRequest(_this.unableUrl, effectRow, function(result) {
	        		 _this.unableCallback(result, params);
	        	}); 
        	} else {
        		showError("操作失败!");
        	}
        }  
    });  
};

//如果需要在注销操作时，进行一些其他的逻辑处理，可自定义checkEnable函数
FasController.prototype.checkUnable = function(checkedRows) {
    return true;
};

//组装注销操作的数据
FasController.prototype.buildUnableData = function(checkedRows) {
	var unableList = [];
	$.each(checkedRows, function(index, item){
		var obj = new Object();
		obj.id = item.id;
		unableList.push(obj);
	});     
    return unableList;
};

//组装激活操作后的回调函数
FasController.prototype.unableCallback = function(result, params) {
    if(result) {
        showSuc("停用操作成功!");
        $("#"+ params.searchBtn).click();
    } else {
        showError("停用操作失败,请联系管理员!");
    }
};

//导出
FasController.prototype.exportExcel = function(params) {
    var $dg = $("#" + params.dataGridId);
    var queryParams = $dg.datagrid('options').queryParams;
    var grepColumns = $dg.datagrid('options').columns;

    var subGrepColumns = $dg.datagrid('options').subColumns;

    var columns = $.grep(grepColumns[0], function(o, i) {
        if ($(o).attr("notexport") == true) {
            return true;
        }
        return false;
    }, true);

    var subColumns = [];
    if(typeof subGrepColumns != 'undefined'
        && subGrepColumns != null
        && subGrepColumns != "") {
        subColumns = $.grep(subGrepColumns[0], function(o, i) {
            if ($(o).attr("notexport") == true) {
                return true;
            }
            return false;
        }, true);
    }
    var exportColumns = JSON.stringify(columns);
    var exportSubColumns = JSON.stringify(subColumns);
    var url = BasePath + params.exportUrl;
    var dataRow = $dg.datagrid('getRows');

    $("#exportExcelForm").remove();
    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
    var fromObj = $('#exportExcelForm');
    if (dataRow.length > 0) {
        fromObj.form('submit', {
            url : url,
            onSubmit : function(subParam) {
                subParam.exportColumns = exportColumns;
                subParam.exportSubColumns = exportSubColumns;
                subParam.fileName = params.exportTitle;
                subParam.exportType = params.exportType || '';
                if(queryParams != null && queryParams != {}) {
                    $.each(queryParams, function(i) {
                        subParam[i] = queryParams[i];
                    });
                }
            },
            success : function() {

            }
        });
    } else {
        showWarn('查询记录为空，不能导出!');
    }
};

//导入
FasController.prototype.importExcel = function(params) {
	var _this = this;
	ygDialog({
		title : "导入",
		target : $("#" + params.formPanelId),
		width : params.width || 400,
		height : params.height || 160,
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				_this.upload(params, dialog);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
};

// 点击导入页面中的"确定"按钮时，触发的函数
FasController.prototype.upload = function(params, dialog) {
	var fromObj = $("#" + params.formId);
	var url = BasePath + params.url;
	fromObj.form('submit', {
		url : url,
		dataType : 'json',
		onSubmit : function(params) {

		},
		success : function(result) {
			if (result) {
				showSuc('导入成功!');
				$("#" + params.formPanelId).window('close');
			}
		},
		error : function() {
			showError('导入失败,请联系管理员!');
		}
	});
};

//初始化search弹出框组件
FasController.prototype.initIptSearch = function(params, ids) {
	$("#" + params.id).iptSearch({
		width : params.inputWidth || 140,
		readonly : (params.readonly != null && params.readonly != 'undefined') ? params.readonly : true,
		disabled : false,
		clickFn : function() {
			var urlParams = "";
			// 组装查询参数
			if (typeof ids != 'undefined' && ids != null) {
				$.each(ids, function(n, value) {
					if (n === 0) {
						urlParams += "?" + value + "=" + $("#" + value).val();
					} else {
						urlParams += "&" + value + "=" + $("#" + value).val();
					}
				});
			}
			var isFrame = true;
			if(typeof params.isFrame != 'undefined') {
				isFrame = params.isFrame;
			}
			dgSelector({
//				title : params.title,
//				href : BasePath + params.url + urlParams,
//				queryUrl : params.queryUrl || "",
//				width : params.width || 600,
//				height : params.height || 500,
//				isFrame: params.isFrame || true,
//				fn : params.callback || function() {}
				title: params.title, 
				href: BasePath + params.url + urlParams, 
				queryUrl: BasePath + params.queryUrl,
				width: params.width || 600, 
				height:  params.height || 500, 
				isFrame: isFrame, 
				fn : params.callback || function() {}
			});
		}
	});
};

//初始化combox组件
FasController.prototype.initCombox = function(params) {
	 if(params.data) {
		 $("#" + params.id).combobox({
			readonly : true,
			data : params.data,
			width : params.width || 140,
		    panelHeight : params.height || 200,
		    valueField : params.valueId,    
		    textField : params.textId,
		    onChange : params.changeFun || function() {},
		    onSelect : params.selectFun || function() {},
		    multiple : params.multiple || false
		});
     } else {
		$("#" + params.id).combobox({
			readonly : true,
			url : BasePath + params.url,
			width : params.width || 140,
		    panelHeight : params.height || 200,
		    valueField : params.valueId,    
		    textField : params.textId,
		    onChange : params.changeFun || function() {},
		    onSelect : params.selectFun || function() {},
		    multiple : params.multiple || false
		});
     }
};

//公共ajax请求（不是异步，ajax方法执行完之后，再执行后面的js的代码）
FasController.prototype.ajaxRequestAsync = function(url, reqParam, callback) {
	var returlVal = "";
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  async : false,
		  dataType : 'json',
		  success: function(data) {
			  returlVal = callback(data);
		  }
	});
	return returlVal;
};

//多选组件
FasController.prototype.multiSearch = function(setting) {
	$("#" + setting.id).iptSearch({
		readonly : (setting.readonly != null && setting.readonly != 'undefined') ? setting.readonly : true,
		disabled : false,
		enableCloseButton: false,
		clickFn : function() {
			var url_params = "";
			var params = setting.params;
			// 组装查询参数
			if (typeof params != 'undefined' && params != null) {
				$.each(params, function(n, item) {
					if (n === 0) {
						url_params += "?" + item.name + "=" + item.value;
					} else {
						url_params += "&" + item.name + "=" + item.value;
					}
				});
			}
			ygDialog({
				title : setting.title,
				href : BasePath + setting.href,
				width : setting.width || 600,
				height : setting.height || 500,
				isFrame : setting.isFrame || true,
				modal : true,
				showMask : true,
				onLoad : function(win, content) {
					var _this = $(this);
					$("#btn-sure", _this.contents()).on("click", function() {
						var checkedRows = content.multiSearchDatas();
						var dataNos = "", dataNames = "";
						$.each(checkedRows, function(index, item) {
							var no = setting.inputNo.child, name = setting.inputName.child;
							dataNos += item[no];
				    		dataNames += item[name];
				    		if(index < checkedRows.length - 1) {
				    			dataNos += ",";
				    			dataNames += ",";
				    		}
				    	});  
				    	$("#" + setting.inputNo.self).val(dataNos);
				    	$("#" + setting.inputName.self).val(dataNames);
						win.close();
					});
					$("#btn-cancel", _this.contents()).on("click", function() {
						win.close();
					});
				}
			});
		}
	});
};

$.extend($.fn.validatebox.defaults.rules, {
	//不能输入非法字符
	unNormalData : {
		validator : function(value, param) {
			return /^[a-zA-z0-9\u4E00-\u9FA5]*$/.test(value);
		},
		message : '不能输入空格或非法字符'
	}
});