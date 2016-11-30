var fas_common = {};

//单据是否注销的状态值
fas_common.statusVal = {
	enable : "1",
	unable : "0"
};


//单据的审批状态值
fas_common.auditVal = {
	unaudit : "0",
	audit : "1"
};


//公用的数据转换对象
fas_common.formatter = {
	audit : function(value) {
		if(value == fas_common.auditVal.unaudit) {
			return "未审核";
		}
		if(value == fas_common.auditVal.audit) {
			return "已审核";
		}
		return "";
	},
	status : function(value) {
		if(value == fas_common.statusVal.enable) {
			return "已启用";
		}
		if(value == fas_common.statusVal.unable) {
			return "已停用";
		}
		return "";
	}	
};

var _fas_common_setting = function() {
	var setting = {
		searchUrl : "",
		saveUrl : "",
		updateUrl : "",
		delUrl : "",
		datagridId : "dataGridDiv",
		searchFormId : "searchForm",
		formPanelId : "uploadForm",
		dataFormId : "dataForm",
		dialogWidth : 800,
		dialogHeight : 330,
		primaryKey : "id",
		searchBtn : "btn-search",
		clearBtn : "btn-remove",
		addBtn : "btn-add",
		editBtn : "btn-edit",
		delBtn : "btn-del",
		exportBtn : "btn-export",
		importBtn : "btn-import",
		enableBtn : "btn-enable",
		unableBtn : "btn-unable",
		auditBtn : "btn-audit",
		antiAuditBtn : "btn-anti-audit"
	};
	if (typeof fas_common_setting == 'undefined') {
		return setting;
	}
	return fas_common_setting || setting;
};

// 2.清空查询区域
fas_common.doSearchClear = function() {
	
	$('#' + _fas_common_setting().searchFormId).form("clear");
	$(':input','#' + _fas_common_setting().searchFormId).not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};

// 进入新增页面(弹出页面形式)
fas_common.showAddDialog = function(setting) {
	// 如果需要在新增操作时，进行一些其他的逻辑处理，可自定义extra_operate.initAdd函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.initAdd != 'undefined' && extra_operate.initAdd != null
			&& typeof extra_operate.initAdd == 'function') {
		extra_operate.initAdd();
	}
	var dialogWidth = _fas_common_setting().dialogWidth;
	var dialogHeight = 0;
    if((document.documentElement.clientHeight - 50) <  _fas_common_setting().dialogHeight) {
    	dialogHeight = document.documentElement.clientHeight - 50;
    } else {
    	dialogHeight = _fas_common_setting().dialogHeight;
    }
	ygDialog({
		title : '新增',
		target : $('#myFormPanel'),
		width : dialogWidth,
		height : dialogHeight,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				fas_common.doSave(setting);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
	$("#" + setting.dataFormId).form("clear");
//	fas_common.setDataFormClass(0);
	$("#id").val("");
	if(typeof extra_operate != 'undefined'
	    && typeof extra_operate.loadedAdd != 'undefined' && extra_operate.loadedAdd != null
		&& typeof extra_operate.loadedAdd == 'function') {
		extra_operate.loadedAdd();
	}
};

// 设置弹出页面的样式
fas_common.setDataFormClass = function(flag, rowData) {
	if (flag === 0) {
		fas_common.addDataFormClass(_fas_common_setting().dataFormId);
	}  else if (flag === 1) {
		$("#" + _fas_common_setting().dataFormId).form('load', rowData);
		fas_common.clearDataFormClass(_fas_common_setting().dataFormId, rowData);
	}
};

fas_common.addDataFormClass = function(dataFormId) {
	$("#" + dataFormId).form("clear");
	$("#" + dataFormId + " input").removeAttr("readonly").removeClass("disabled");
	$("#" + dataFormId + " textarea").removeAttr("disabled").removeClass("disabled");
	$("#" + dataFormId + " input[class*=easyui-combobox]").combobox('enable');
	$("#" + dataFormId + " select[class*=easyui-combobox]").combobox('enable');
	$("#" + dataFormId + " input[class*=easyui-search]").parents("div").addClass("ipt-search-box");
	$("#" + dataFormId + " input[class*=easyui-datebox]").removeAttr("disabled","false");
};

fas_common.clearDataFormClass = function(dataFormId) {
	$("#" + dataFormId + " input").addClass("disabled").attr("readonly", true);
	$("#" + dataFormId + " textarea").addClass("disabled").attr("disabled", "disabled");
	$("#" + dataFormId + " select[class*=easyui-combobox]").combobox('disable');
	$("#" + dataFormId + " input[class*=easyui-combobox]").combobox('disable');
	$("#" + dataFormId + " input[class*=easyui-search]").parents("div").removeClass("ipt-search-box");
	$("#" + dataFormId + " input[class*=easyui-datebox]").attr("disabled","true");
};


// 新增数据方法
fas_common.doSave = function(setting) {
	var fromObj = $('#' + setting.dataFormId);
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	// 如果需要在新增保存操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkSave函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkSave != 'undefined' && extra_operate.checkSave != null
			&& typeof extra_operate.checkSave == 'function') {
		if (!extra_operate.checkSave()) {
			return;
		}
	}
	// 3.保存
	var saveFn = function(returnData) {
		var url = BasePath + setting.saveUrl;
		var submitParams = [];
		if(typeof extra_operate != 'undefined'
			&& typeof extra_operate.initSubmitParams != 'undefined' && extra_operate.initSubmitParams != null
			&& typeof extra_operate.initSubmitParams == 'function') {
			submitParams = extra_operate.initSubmitParams();
		}
		fromObj.form('submit', {
			url : url,
			dataType : 'json',
			onSubmit : function(params) {
				if(submitParams != null && submitParams.length > 0) {
					for(var i = 0; i < submitParams.length; i++) {
						var paramName = submitParams[i].name;
						params[paramName] = submitParams[i].value;
					}
				}
			},
			success : function(result) {
				if(result && (JSON.parse(result).success 
						|| typeof JSON.parse(result).success == 'undefined')) {
					showSuc('新增成功!');
					$("#myFormPanel").window('close');
					if(typeof extra_operate != 'undefined'
						&& typeof extra_operate.saveCallback != 'undefined' 
						&& extra_operate.saveCallback != null 
						&& typeof extra_operate.saveCallback == 'function') {
						extra_operate.saveCallback(result, setting);
					} else {
						$("#"+setting.searchBtn).click();
					}
					return;
				} else {
					showError('新增失败,请联系管理员!');
				}
			},
			error : function() {
				showError('新增失败,请联系管理员!');
			}
		});
	};
	saveFn();
};

// 查看明细
fas_common.loadDetail = function(rowData) {
	var setting = {
		dataFormId : _fas_common_setting().dataFormId,
		updateUrl : _fas_common_setting().updateUrl,
		searchBtn : _fas_common_setting().searchBtn
	};
	fas_common.editDialog(rowData, setting);
};

// 删除数据
fas_common.doDel = function(setting){
	var operater_name = setting.operaterName || "删除";
	var checkedRows = $("#" + setting.datagridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要"+operater_name+"的记录!");
		return;
	}
	fas_common.deleteFn(setting, checkedRows);
};

//删除操作逻辑处理部分
fas_common.deleteFn = function(setting, checkedRows) {
	var operater_name = setting.operaterName || "删除";
	var primaryKey = setting.primaryKey;
	// 如果需要在删除操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkDel函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkDel != 'undefined' && extra_operate.checkDel != null
			&& typeof extra_operate.checkDel == 'function') {
		if (!extra_operate.checkDel(checkedRows)) {
			return;
		}
	}
	var deleteLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		deleteLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要"+operater_name+"这"+deleteLen+"条数据", function (r){  
        if (r) {
        	var deleteList = [];
        	//如果需要自己组装删除数据，则可自定义
        	if(typeof extra_operate != 'undefined'
    		    && typeof extra_operate.buildDelData != 'undefined' && extra_operate.buildDelData != null
    			&& typeof extra_operate.buildDelData == 'function') {
        		deleteList = extra_operate.buildDelData(checkedRows);
        	} else {
        		$.each(checkedRows, function(index, item){
        			var obj = new Object();
        			obj[primaryKey] = item[primaryKey];
        			deleteList.push(obj);
        		});     
        	}
        	
        	if(deleteList.length > 0) {
        		var url = BasePath + setting.delUrl;
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(deleteList);
	            fas_common.ajaxRequest(url, effectRow, function(result){
	        		 if(result){
	        			 showSuc(operater_name+"成功!");
	        			 if(typeof extra_operate != 'undefined'
	        				&& typeof extra_operate.delCallback != 'undefined' 
	     					&& extra_operate.delCallback != null 
	     					&& typeof extra_operate.delCallback == 'function') {
	        				 extra_operate.delCallback(result, setting);
	     				} else {
	 						$("#"+setting.searchBtn).click();
	 					}
	        		 }else{
	        			 showError(operater_name+"失败,请联系管理员!");
	        		 }
	        	}); 
        	} else{
        		showError(operater_name+"失败!");
        	}
        }  
    });  
};

// 公共异步请求
fas_common.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		dataType : 'json',
		success : callback
	});
};

// 进入修改页面的方法
fas_common.showEditDialog = function(setting) {
	var checkedRows = $("#" + _fas_common_setting().datagridId).datagrid(
			"getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1) {
		showWarn('请选择要修改的记录!', 1);
		return;
	} 
	if(checkedRows.length > 1) {
		showWarn('只能选择一条记录进行修改!');
		return;
	}
	fas_common.editDialog(checkedRows[0], setting);
};

fas_common.editDialog = function(rowData, setting) {
	// 如果需要在修改操作时，进行一些其他的逻辑处理，可自定义extra_operate.initUpdate函数
	var can_update_flag = true;
	if(typeof extra_operate != 'undefined'
		    && typeof extra_operate.initUpdate != 'undefined' && extra_operate.initUpdate != null
			&& typeof extra_operate.initUpdate == 'function') {
		can_update_flag = extra_operate.initUpdate(rowData);
		if(typeof can_update_flag == 'undefined' || can_update_flag == null) {
			can_update_flag = true;
		}
	}
	if(!can_update_flag) {
		return;
	}
//	fas_common.setDataFormClass(0);
	var dialogWidth = _fas_common_setting().dialogWidth;
	var dialogHeight = 0;
    if((document.documentElement.clientHeight - 50) <  _fas_common_setting().dialogHeight) {
    	dialogHeight = document.documentElement.clientHeight - 50;
    } else {
    	dialogHeight = _fas_common_setting().dialogHeight;
    }
    //$('#' + _fas_common_setting().dataFormId + "View").form('clear');
    if(rowData.auditStatus == '1' && $("#myFormPanelView").length > 0) {
    	$('#' + _fas_common_setting().dataFormId + "View").form('load', rowData);
    	ygDialog({
    		title : '查看',
    		target : $('#myFormPanelView'),
    		width : dialogWidth,
    		height : dialogHeight,
    		buttons : [{
    			text : '取消',
    			iconCls : 'icon-cancel',
    			handler : function(dialog) {
    				dialog.close();
    			}
    		}]
    	});
    }else if(rowData.status == '1' ){
    	$('#' + _fas_common_setting().dataFormId).form('load', rowData);
    	ygDialog({
    		title : '查看',
    		target : $('#myFormPanel'),
    		width : dialogWidth,
    		height : dialogHeight,
    		buttons : [{
    			text : '取消',
    			iconCls : 'icon-cancel',
    			handler : function(dialog) {
    				dialog.close();
    			}
    		}]
    	});
    }
    else {
    	$('#' + _fas_common_setting().dataFormId).form('load', rowData);
    	ygDialog({
    		title : '修改',
    		target : $('#myFormPanel'),
    		width : dialogWidth,
    		height : dialogHeight,
    		buttons : [{
    			text : '保存',
    			iconCls : 'icon-save',
    			handler : function(dialog) {
    				fas_common.doUpdate(setting);
    			}
    		}, {
    			text : '取消',
    			iconCls : 'icon-cancel',
    			handler : function(dialog) {
    				dialog.close();
    			}
    		}]
    	});
    }
	$("#" + _fas_common_setting().dataFormId + " input[class*=easyui-combobox]").combobox('enable');
	$("#" + _fas_common_setting().dataFormId + " select[class*=easyui-combobox]").combobox('enable');
	if(typeof extra_operate != 'undefined'
	    && typeof extra_operate.loadedUpdate != 'undefined' && extra_operate.loadedUpdate != null
		&& typeof extra_operate.loadedUpdate == 'function') {
		extra_operate.loadedUpdate(rowData);
	}
};

// 修改数据的方法
fas_common.doUpdate = function(setting) {
	var fromObj = $('#' + setting.dataFormId);
	// 1.校验必填项
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	// 如果需要在新增保存操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkUpdate函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkUpdate != 'undefined' && extra_operate.checkUpdate != null
			&& typeof extra_operate.checkUpdate == 'function') {
		if (!extra_operate.checkUpdate()) {
			return;
		}
	}
	// 3. 保存
	var updateFn = function(returnData) {
		var submitParams = "";
		if(typeof extra_operate != 'undefined'
			&& typeof extra_operate.initSubmitParams != 'undefined' && extra_operate.initSubmitParams != null
			&& typeof extra_operate.initSubmitParams == 'function') {
			submitParams = extra_operate.initSubmitParams();
		}
		var url = BasePath + setting.updateUrl;
		fromObj.form('submit', {
			url : url,
			dataType : 'json',
			onSubmit : function(params) {
				if(submitParams != null && submitParams.length > 0) {
					for(var i = 0; i < submitParams.length; i++) {	
						var paramName = submitParams[i].name;
						params[paramName] = submitParams[i].value;
					}
				}
			},
			success : function(result) {
				if(result) {
					showSuc('修改成功!');
					$("#myFormPanel").window('close');
					if(typeof extra_operate != 'undefined'
						&& typeof extra_operate.updateCallback != 'undefined' 
						&& extra_operate.updateCallback != null 
						&& typeof extra_operate.updateCallback == 'function') {
						extra_operate.updateCallback(result, setting);
					} else {
						fas_common.setDataFormClass(0);
						$("#"+setting.searchBtn).click();
					}
					return;
				} else {
					showError("修改失败,请联系管理员!");
				}
			},
			error : function() {
				showError("修改失败,请联系管理员!");
			}
		});
	};
	updateFn();
};

// 初始化combox组件
fas_common.initCombox = function(params) {
	$("#" + params.id).combobox({
		readonly : params.readonly || false,
		url : BasePath + params.url,
		width : params.width || 140,
	    panelHeight : params.height || 200,
	    valueField : params.valueId,    
	    textField : params.textId,
	    onChange : params.changeFun || function() {},
	    onSelect : params.selectFun || function() {},
	    onUnselect : params.unSelectFun || function() {},
	    editable : params.editable || false,
	    multiple : params.multiple || false
	});
};

// 通用格式化combobox加载的数据显示到datagrid
fas_common.formatterCombox = function(params) {
	var combobox = $("#" + params.id);
	var value = combobox.combobox("options").valueField;
	var text = combobox.combobox("options").textField;
	var datas = combobox.combobox("getData");
	var redColorFlag = (typeof params.redColorVal != 'undefined' && params.redColorVal != "");
	for(var i = 0; i < datas.length; i++) {
		if(datas[i][value] == params.formatVal 
				&& redColorFlag && params.redColorVal == datas[i][value]) {
			return "<font color='red'>" + datas[i][text] + "</font>";
		}
		if(datas[i][value] == params.formatVal) {
			return datas[i][text];
		}
	}
	return "";
};

// 初始化search弹出框组件
fas_common.initIptSearch = function(params, ids) {
	$("#" + params.id).iptSearch({
		width : params.inputWidth || 140,
		readonly : (params.readonly != null && params.readonly != 'undefined') ? params.readonly : true,
		disabled : false,
		clickFn : function() {
			var url_params = "";
			// 组装查询参数
			if (typeof ids != 'undefined' && ids != null) {
				$.each(ids, function(n, value) {
					if (n === 0) {
						url_params += "?" + value + "=" + $("#" + value).val();
					} else {
						url_params += "&" + value + "=" + $("#" + value).val();
					}
				});
			}
			var isFrame = true;
			if(typeof params.isFrame != 'undefined') {
				isFrame = params.isFrame;
			}
			dgSelector({
				title: params.title, 
				href: BasePath + params.url + url_params, 
				queryUrl: BasePath + params.queryUrl || "",
				width: params.width || 600, 
				height:  params.height || 500, 
				isFrame: isFrame, 
				fn : params.callback || function() {}
			});
		}
	});
};

//多选组件
fas_common.multiSearch = function(setting) {
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

//多选组件
//fas_common.initIptSearchMulti = function(params, ids) {
//	$("#" + params.id).iptSearch({
//		disabled : false,
//		clickFn : function() {
//			var url_params = "";
//			// 组装查询参数
//			if (typeof ids != 'undefined' && ids != null) {
//				$.each(ids, function(n, value) {
//					if (n === 0) {
//						url_params += "?" + value + "=" + $("#" + value).val();
//					} else {
//						url_params += "&" + value + "=" + $("#" + value).val();
//					}
//				});
//			}
//			fas_common.multiDgSelector({
//				title : params.title,
//				href : BasePath + params.url + url_params,
//				queryUrl : params.queryUrl || "",
//				width : params.width || 600,
//				height : params.height || 500,
//				datagridId : params.datagridId,
//				searchFormId : params.searchFormId,
//				inputId : params.inputId || "",
//				inputNameId : params.inputNameId || "",
//				inputNoId : params.inputNoId || ""
//			});
//		}
//	});
//};

// 查询
fas_common.search = function(params) {
	var formId = params.formId || "searchForm";
	var fromObjStr = convertArray($("#" + formId).serializeArray());
	var queryMxURL = BasePath + params.url;
	var valid = $("#" + formId).form('validate');
	if(valid == false){
		return;
	}
	// 2.加载明细 注意请求方式 restful风格 get
	$("#" + params.dataGridId).datagrid('options').queryParams = eval("("
			+ fromObjStr + ")");
	$("#" + params.dataGridId).datagrid('options').url = queryMxURL;
	$("#" + params.dataGridId).datagrid('load');
	$(":checkbox:eq(0)").attr("checked", false);
};

// 导出
fas_common.exportExcel = function(setting) {
	var $dg = $("#" + setting.dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	var subGrepColumns = $dg.datagrid('options').subColumns;
	
	var columns = [], firstHeaderColumns = [];
	
	if(grepColumns && grepColumns.length > 1) {
		columns = $.grep(grepColumns[1], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
		firstHeaderColumns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	} else {
		columns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}

	
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
	var url = BasePath + setting.url;
	var dataRow = $dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
				param.exportSubColumns = exportSubColumns;
				param.fileName = setting.title;
				param.exportType = setting.exportType || '';
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
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

// 导入
fas_common.importExcel = function(setting) {
	ygDialog({
		title : "导入",
		target : $("#" + setting.formPanelId),
		width : setting.width || 400,
		height : setting.height || 160,
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				fas_common.upload(setting, dialog);
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



//点击导入页面中的"确定"按钮时，触发的函数
fas_common.upload = function(setting, dialog) {
	
	var formObj = $("#" + setting.formId);
	var url = BasePath + setting.url;
	formObj.form('submit', {
		url : url,
		success : function(result) {
			if(result == '[]' || result == ''){
				showSuc('导入成功!');
			}else{
				var messList = JSON.parse(result);
				var mess_show = '<br /><br /><br />';
				$.each(messList, function(i,val){     
					mess_show = mess_show + val.message;
				});
				alert(mess_show);
			}
			$("#" + setting.formPanelId).window('close');
			$("#" + setting.searchBtn).click();
		},
		error : function() {
			showError('导入失败,请联系管理员!');
		}
	});
};


////弹出页面组件，支持多选操作
//fas_common.multiDgSelector = function(setting){
//	var dialogParams = {id : setting.inputId, text : setting.inputNameId, no : setting.inputNoId};
//	ygDialog({
//		title : setting.title,
//		href : setting.href || '',
//		width : setting.width || null,
//		height : setting.height || null,
//		isFrame : setting.isFrame || true,
//		modal : true,
//		showMask : true,
//		onLoad : function(win, content) {
//			var _this = $(this);
//			$("#save_search_btn", _this.contents()).on("click", function() {
//				contentWindow.multiSearchDatas(dialogParams,setting.datagridId);
//				win.close();
//			});
//			$("#cancel_btn", _this.contents()).on("click", function() {
//				win.close();
//			});
//		}
//	});
//	return false;
//};

//审核操作
fas_common.doAudit = function(setting) {
	var checkedRows = $("#" + setting.datagridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要审核的记录!");
		return;
	}
	fas_common.auditFn(setting, checkedRows);
};

//审核操作逻辑处理部分
fas_common.auditFn = function(setting, checkedRows) {
	var primaryKey = setting.primaryKey;
	// 如果需要在审核操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkAudit函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkAudit != 'undefined' && extra_operate.checkAudit != null
			&& typeof extra_operate.checkAudit == 'function') {
		if (!extra_operate.checkAudit(checkedRows)) {
			return;
		}
	}
	var auditLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		auditLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要审核这"+auditLen+"条数据", function (r){  
        if(r) {
        	var auditList = [];
        	//如果需要自己组装审核数据，则可自定义
        	if(typeof extra_operate != 'undefined'
    		    && typeof extra_operate.buildAuditData != 'undefined' && extra_operate.buildAuditData != null
    			&& typeof extra_operate.buildAuditData == 'function') {
        		auditList = extra_operate.buildAuditData(checkedRows);
        	} else {
        		$.each(checkedRows, function(index, item){
        			var obj = new Object();
        			obj[primaryKey] = item[primaryKey];
        			auditList.push(obj);
        		});     
        	}
        	
        	if(auditList.length > 0) {
        		var url = BasePath + setting.auditUrl;
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(auditList);
	            fas_common.ajaxRequest(url, effectRow, function(result){
	        		 if(result){
	        			 showSuc("审核成功!");
	        			 if(typeof extra_operate != 'undefined'
	        				&& typeof extra_operate.auditCallback != 'undefined' 
	     					&& extra_operate.auditCallback != null 
	     					&& typeof extra_operate.auditCallback == 'function') {
	        				 extra_operate.auditCallback(result, setting);
	     				} else {
	 						$("#"+setting.searchBtn).click();
	 					}
	        		 }else{
	        			 showError("审核失败,请联系管理员!");
	        		 }
	        	}); 
        	} else{
        		showError("审核失败!");
        	}
        }  
    });  
};

//反审核操作
fas_common.doAntiAudit = function(setting) {
	var checkedRows = $("#" + setting.datagridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要反审核的记录!");
		return;
	}
	fas_common.antiAuditFn(setting, checkedRows);
};

//反审核操作逻辑处理部分
fas_common.antiAuditFn = function(setting, checkedRows) {
	var primaryKey = setting.primaryKey;
	// 如果需要在反审核操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkAntiAudit函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkAntiAudit != 'undefined' && extra_operate.checkAntiAudit != null
			&& typeof extra_operate.checkAntiAudit == 'function') {
		if (!extra_operate.checkAntiAudit(checkedRows)) {
			return;
		}
	}
	var antiAuditLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		antiAuditLen = checkedRows.length;
	}
	$.messager.confirm("确认","你确定要反审核这"+antiAuditLen+"条数据", function (r){  
        if(r) {
        	var antiAuditList = [];
        	//如果需要自己组装反审核数据，则可自定义
        	if(typeof extra_operate != 'undefined'
    		    && typeof extra_operate.buildAntiAuditData != 'undefined' && extra_operate.buildAntiAuditData != null
    			&& typeof extra_operate.buildAntiAuditData == 'function') {
        		antiAuditList = extra_operate.buildAntiAuditData(checkedRows);
        	} else {
        		$.each(checkedRows, function(index, item){
        			var obj = new Object();
        			obj[primaryKey] = item[primaryKey];
        			antiAuditList.push(obj);
        		});     
        	}
        	
        	if(antiAuditList.length > 0) {
        		var url = BasePath + setting.antiAuditUrl;
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(antiAuditList);
	            fas_common.ajaxRequest(url, effectRow, function(result){
	        		 if(result){
	        			 showSuc("反审核成功!");
	        			 if(typeof extra_operate != 'undefined'
	        				&& typeof extra_operate.antiAuditCallback != 'undefined' 
	     					&& extra_operate.antiAuditCallback != null 
	     					&& typeof extra_operate.antiAuditCallback == 'function') {
	        				 extra_operate.antiAuditCallback(result, setting);
	     				} else {
	 						$("#"+setting.searchBtn).click();
	 					}
	        		 }else{
	        			 showError("反审核失败,请联系管理员!");
	        		 }
	        	}); 
        	} else{
        		showError("反审核失败!");
        	}
        }  
    });  
};

//激活操作
fas_common.doEnable = function(setting) {
	var checkedRows = $("#" + setting.datagridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要启用的记录!");
		return;
	}
	
	fas_common.enableFn(setting, checkedRows);
};

//激活操作逻辑处理部分
fas_common.enableFn = function(setting, checkedRows) {
	var primaryKey = setting.primaryKey;
	// 如果需要在激活操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkEnable函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkEnable != 'undefined' && extra_operate.checkEnable != null
			&& typeof extra_operate.checkEnable == 'function') {
		if (!extra_operate.checkEnable(checkedRows)) {
			return;
		}
	}
	var enableLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		enableLen = checkedRows.length;
	}

	var flag = true;
	$.each(checkedRows, function(index,row) {		
		if(row.systemInit == '0' ){
			showWarn("系统初始参数设置,不能操作");
			flag = false;
		   return false;
		}
		
		if(row.status == '1'){
			showWarn("已是启用状态,不能在操作！");
			flag = false;
			return false;
 	    }	    
	});
	
	if(flag == true){
	$.messager.confirm("确认","你确定要启用这"+enableLen+"条数据", function (r){  
        if(r) {
        	var enableList = [];
        	//如果需要自己组装激活数据，则可自定义
        	if(typeof extra_operate != 'undefined'
    		    && typeof extra_operate.buildEnableData != 'undefined' && extra_operate.buildEnableData != null
    			&& typeof extra_operate.buildEnableData == 'function') {
        		enableList = extra_operate.buildEnableData(checkedRows);
        	} else {
        		$.each(checkedRows, function(index, item){
        			var obj = new Object();
        			obj[primaryKey] = item[primaryKey];
        			enableList.push(obj);
        		});     
        	}
        	
        	if(enableList.length > 0) {
        		var url = BasePath + setting.enableUrl;
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(enableList);
	            fas_common.ajaxRequest(url, effectRow, function(result){
	        		 if(result){
	        			 showSuc("操作成功!");
	        			 if(typeof extra_operate != 'undefined'
	        				&& typeof extra_operate.enableCallback != 'undefined' 
	     					&& extra_operate.enableCallback != null 
	     					&& typeof extra_operate.enableCallback == 'function') {
	        				 extra_operate.enableCallback(result, setting);
	     				} else {
	 						$("#"+setting.searchBtn).click();
	 					}
	        		 }else{
	        			 showError("操作失败,请联系管理员!");
	        		 }
	        	}); 
        	} else{
        		showError("操作失败!");
        	}
        }  
    })};  
};

//注销操作
fas_common.doUnable = function(setting) {
	var checkedRows = $("#" + setting.datagridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要停用的记录!");
		return;
	}
	fas_common.unableFn(setting, checkedRows);
};

//注销操作逻辑处理部分
fas_common.unableFn = function(setting, checkedRows) {
	var primaryKey = setting.primaryKey;
	// 如果需要在注销操作时，进行一些其他的逻辑处理，可自定义extra_operate.checkUnable函数
	if (typeof extra_operate != 'undefined'
		    && typeof extra_operate.checkUnable != 'undefined' && extra_operate.checkUnable != null
			&& typeof extra_operate.checkUnable == 'function') {
		if (!extra_operate.checkUnable(checkedRows)) {
			return;
		}
	}
	var unableLen = "";
	if(typeof checkedRows != "undefined" && checkedRows != null) {
		unableLen = checkedRows.length;
	}
	
	var flag = true;
	$.each(checkedRows, function(index,row) {		
		if(row.systemInit == '0' ){
			showWarn("系统初始参数设置,不能操作");
			flag = false;
		   return false;
		}
		
		if(row.status == '0'){
			showWarn("已是停用状态,不能在操作！");
			flag = false;
			return false;
 	    }	    
	});
	
	if(flag == true){
	$.messager.confirm("确认","你确定要停用这"+unableLen+"条数据", function (r){  
        if(r) {
        	var unableList = [];
        	//如果需要自己组装注销数据，则可自定义
        	if(typeof extra_operate != 'undefined'
    		    && typeof extra_operate.buildUnableData != 'undefined' && extra_operate.buildUnableData != null
    			&& typeof extra_operate.buildUnableData == 'function') {
        		unableList = extra_operate.buildUnableData(checkedRows);
        	} else {
        		$.each(checkedRows, function(index, item){
        			var obj = new Object();
        			obj[primaryKey] = item[primaryKey];
        			unableList.push(obj);
        		});     
        	}
        	
        	if(unableList.length > 0) {
        		var url = BasePath + setting.unableUrl;
	            var effectRow = new Object();
	            effectRow["deleted"] = JSON.stringify(unableList);
	            fas_common.ajaxRequest(url, effectRow, function(result){
	        		 if(result){
	        			 showSuc("操作成功!");
	        			 if(typeof extra_operate != 'undefined'
	        				&& typeof extra_operate.unableCallback != 'undefined' 
	     					&& extra_operate.unableCallback != null 
	     					&& typeof extra_operate.unableCallback == 'function') {
	        				 extra_operate.unableCallback(result, setting);
	     				} else {
	 						$("#"+setting.searchBtn).click();
	 					}
	        		 }else{
	        			 showError("操作失败,请联系管理员!");
	        		 }
	        	}); 
        	} else{
        		showError("操作失败!");
        	}
        }  
    })};  
};

// 初始化
$(function() {
	// 查询
	$("#"+_fas_common_setting().searchBtn).on("click", function() {
		fas_common.search({
			formId : _fas_common_setting().searchFormId,
			url : _fas_common_setting().searchUrl,
			dataGridId : _fas_common_setting().datagridId
		});
	});

	// 给清空按钮绑定函数事件
	$("#"+_fas_common_setting().clearBtn).on("click", function() {
		fas_common.doSearchClear();
	});
	// 给新增按钮绑定函数事件
	$("#"+_fas_common_setting().addBtn).on("click", function() {
		fas_common.showAddDialog({
			dataFormId : _fas_common_setting().dataFormId,
			saveUrl :  _fas_common_setting().saveUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	// 给修改按钮绑定函数事件
	$("#"+_fas_common_setting().editBtn).on("click", function() {
		fas_common.showEditDialog({
			dataFormId : _fas_common_setting().dataFormId,
			updateUrl : _fas_common_setting().updateUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	// 给删除按钮绑定函数事件
	$("#"+_fas_common_setting().delBtn).on("click", function() {
		fas_common.doDel({
			primaryKey : _fas_common_setting().primaryKey,
			datagridId : _fas_common_setting().datagridId,
			delUrl : _fas_common_setting().delUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	
	// 给导出按钮绑定函数事件
	$("#"+_fas_common_setting().exportBtn).on("click", function() {
		fas_common.exportExcel({
			dataGridId : _fas_common_setting().datagridId,
			url : _fas_common_setting().exportUrl,
			title : _fas_common_setting().exportTitle,
			exportType : _fas_common_setting().exportType
		});
	});
	
	// 给导入按钮绑定函数事件
	$("#"+_fas_common_setting().importBtn).on("click", function() {
		
		fas_common.importExcel({
			formId : _fas_common_setting().formId,
			url : _fas_common_setting().importUrl,
			formPanelId : "uploadForm",
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	
	// 给启用按钮绑定函数事件
	$("#"+_fas_common_setting().enableBtn).on("click", function() {
		fas_common.doEnable({
			primaryKey : _fas_common_setting().primaryKey,
			datagridId : _fas_common_setting().datagridId,
			enableUrl : _fas_common_setting().enableUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	
	// 给停用按钮绑定函数事件
	$("#"+_fas_common_setting().unableBtn).on("click", function() {
		fas_common.doUnable({
			primaryKey : _fas_common_setting().primaryKey,
			datagridId : _fas_common_setting().datagridId,
			unableUrl : _fas_common_setting().unableUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	
	// 给审核按钮绑定函数事件btn_audit
	$("#"+_fas_common_setting().auditBtn).on("click", function() {
		fas_common.doAudit({
			primaryKey :  _fas_common_setting().primaryKey,
			datagridId : _fas_common_setting().datagridId,
			auditUrl : _fas_common_setting().auditUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
	
	// 给反审核按钮绑定函数事件btn_anti_audit
	$("#"+_fas_common_setting().antiAuditBtn).on("click", function() {
		fas_common.doAntiAudit({
			primaryKey :  _fas_common_setting().primaryKey,
			datagridId : _fas_common_setting().datagridId,
			antiAuditUrl : _fas_common_setting().antiAuditUrl,
			searchBtn : _fas_common_setting().searchBtn
		});
	});
});

//表单验证
$.extend($.fn.validatebox.defaults.rules, {
	unNormalData : {
		validator : function(value, param) {
//			return /^[-+]?\$/.test(value);
			return /^[a-zA-z0-9\u4E00-\u9FA5]*$/.test(value);
		},
		message : '不能输入空格和非法字符'
	},
	
	decimalData: {
		validator : function(value, param) {
			return /^(0(\.\d+)?|1)$/ .test(value) || (value <= 1);
		},
		message : '只能输入0到1之间的小数'
	}
});