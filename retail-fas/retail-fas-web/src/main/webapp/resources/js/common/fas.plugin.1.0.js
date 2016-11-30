/*************************************  easyui-editor扩展 ******************************************/
// jquery类级插件
$.fas = {
	// 扩展$.extend() 方法
	extend : function(Child, Parent) {
		Child.prototype = new Parent();
	    Child.prototype.super = Parent.prototype;
	    Child.prototype.constructor = Child;
	    return Child;
	},
	//公共ajax请求（不是异步，ajax方法执行完之后，再执行后面的js的代码）
	ajaxRequestAsync : function(url, reqParam, callback) {
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
	},
	// 查询方法
	search : function(options) {
		var defaults = {
			searchFormId : "searchForm",
			dataGridId : "dataGridDiv",
			searchUrl : "",
			hasSearchForm : true
        };
        options = $.extend(defaults, options);
		if(options.hasSearchForm) {
			var reqParam = $("#" + options.searchFormId).form("getData");
        	//组装请求参数
        	$("#" + options.dataGridId).datagrid('options').queryParams = reqParam;
        }
        var queryMxURL = BasePath + options.searchUrl;
        $("#" + options.dataGridId).datagrid('options').url = queryMxURL;
        //Reload the rows. Same as the 'load' method but stay on current page.
        $("#" + options.dataGridId).datagrid('reload');
//      if(params.checkboxIndex) {
//         $(":checkbox:eq("+(params.checkboxIndex + 1)+")").attr("checked", false);
//      }
	},
	//清空
	clear : function(formId) {
		formId = formId || "searchForm";
		$("#" + formId).form("clear");
	},
	// 弹出框新增页面
	ygDialogAdd : function(options) {
	    if((document.documentElement.clientHeight - 60) <  options.dialogHeight) {
	    	options.dialogHeight = document.documentElement.clientHeight - 60;
	    }
	    // 如果需要在新增操作时，进行一些其他的逻辑处理，可自定义extra_operate.initAdd函数
	    if(typeof options.initAdd === 'function') {
	    	options.initAdd();
	    }
		ygDialog({
	        title : '新增',
	        target : $('#myFormPanel'),
	        width : options.dialogWidth,
	        height : options.dialogHeight,
	        buttons : [{
	            text : '保存',
	            iconCls : 'icon-save',
	            handler : function(dialog) {
	            	if(typeof options.saveFunction === 'function') {
	            		options.saveFunction(options.obj);
	            	}
	            }
	        }, {
	            text : '取消',
	            iconCls : 'icon-cancel',
	            handler : function(dialog) {
	                dialog.close();
	            }
	        }]
	    });
		if(typeof options.addDataFormClass === 'function') {
			options.addDataFormClass(options.dataFormId);
		}
		if(typeof options.loadedAdd === 'function') {
			options.loadedAdd();
		}
	},
	// 弹出框修改页面
	ygDialogUpdate : function(options) {
	    if((document.documentElement.clientHeight - 60) <  options.dialogHeight) {
	    	options.dialogHeight = document.documentElement.clientHeight - 60;
	    }
	    var rowData = options.rowData;
	    if(!rowData) {
	    	// 获取所有勾选checkbox的行
	    	var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
	    	if(checkedRows.length < 1) {
	    		showWarn('请选择要修改的记录!', 1);
	    		return;
	    	}
	    	if(checkedRows.length < 1) {
	    		showWarn('只能选择一条记录进行修改!');
	    		return;
	    	}
	    	rowData = rowData || checkedRows[0];
	    }
	    if(typeof options.initUpdate === 'function') {
	    	options.initUpdate();
	    }
	    if(typeof options.addDataFormClass === 'function') {
	    	options.addDataFormClass();
	    }
	    var initUpdateFlag = true;
	    if(typeof options.checkInitUpdate === 'function') {
	    	initUpdateFlag = options.checkInitUpdate(rowData);
	    }
	    if(!initUpdateFlag) {
	    	return;
	    }
	    $('#' + options.dataFormId).form('load', rowData);
	    ygDialog({
	        title : '修改',
	        target : $('#myFormPanel'),
	        width : options.dialogWidth,
	        height :options.dialogHeight,
	        buttons : [{
	            text : '保存',
	            iconCls : 'icon-save',
	            handler : function(dialog) {
	            	options.updateFunction(options.obj);
	            }
	        }, {
	            text : '取消',
	            iconCls : 'icon-cancel',
	            handler : function(dialog) {
	                dialog.close();
	            }
	        }]
	    });
	    if(typeof options.loadedUpdate === 'function') {
	    	options.loadedUpdate(rowData);
	    }
	},
	// 新增操作
	add : function(options) {
		var url = BasePath + options.url;
	    var formObj = $("#" + options.dataFormId);
	    var validateFlag = true, addFlag = true;
	    if(typeof options.validateForm === 'function') {
	    	validateFlag = options.validateForm(formObj);
	    }
	    if(!validateFlag) {
	        return;
	    }
	    if(typeof options.checkAdd === 'function') {
	    	addFlag = options.checkAdd();
	    }
	    if(!addFlag) {
	    	return;
	    }
	    formObj.form('submit', {
	        url : url,
	        dataType : 'json',
	        onSubmit : function(extraParams) {
	        	if(typeof options.buildAddSubmitParams === 'function') {
		        	var submitParams = options.buildAddSubmitParams();
		    	    if(submitParams != null && submitParams.length > 0) {
		    	        for(var i = 0; i < submitParams.length; i++) {
		    	            var paramName = submitParams[i].name;
		    	            extraParams[paramName] = submitParams[i].value;
		    	        }
		    	    }
	        	}
	        },
	        success : function(result) {
	        	options.successAddFn(result, options);
	        },
	        error : function() {
	            showError('新增失败,请联系管理员!');
	        }
	    });
	},
	// 修改操作
	update : function(options) {
		var url = BasePath + options.url;
	    var formObj = $("#" + options.dataFormId);
	    var validateFlag = true, updateFlag = true;
	    if(typeof options.validateForm === 'function') {
	    	validateFlag = options.validateForm(formObj);
	    }
	    if(!validateFlag) {
	        return;
	    }
	    if(typeof options.checkUpdate === 'function') {
	    	updateFlag = options.checkUpdate();
	    }
	    if(!updateFlag) {
	    	return;
	    }
	    formObj.form('submit', {
	        url : url,
	        dataType : 'json',
	        onSubmit : function(extraParams) {
	        	if(typeof options.buildUpdateSubmitParams === 'function') {
		        	var submitParams = options.buildUpdateSubmitParams();
		    	    if(submitParams != null && submitParams.length > 0) {
		    	        for(var i = 0; i < submitParams.length; i++) {
		    	            var paramName = submitParams[i].name;
		    	            extraParams[paramName] = submitParams[i].value;
		    	        }
		    	    }
	        	}
	        },
	        success : function(result) {
	        	options.successUpdateFn(result, options);
	        },
	        error : function() {
	            showError('修改失败,请联系管理员!');
	        }
	    });
	},
	// 删除
	del : function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
	    if(checkedRows.length < 1){
	        showWarn("请选择要删除的记录!");
	        return;
	    }
	    var url = BasePath + options.url, delFlag = true, deleteLen = "";
	    // 校验数据是否可删除
	    if(typeof options.checkDel === 'function') {
	    	delFlag = options.checkDel(checkedRows);
	    }
	    if(!delFlag) {
	        return;
	    }
	    if(typeof checkedRows != "undefined" && checkedRows != null) {
	        deleteLen = checkedRows.length;
	    }
	    $.messager.confirm("确认","你确定要删除这"+deleteLen+"条数据", function(r) {
	        if(r) {
	            //如果需要自己组装删除数据，则可自定义
	            var deleteList = options.buildDelData(checkedRows);
	            if(deleteList.length > 0) {
	                var effectRow = new Object();
	                effectRow["deleted"] = JSON.stringify(deleteList);
	                ajaxRequest(url, effectRow, function(result){
	                    options.delCallback(options, result);
	                });
	            } else {
	                showError("删除失败!");
	            }
	        }
	    });
	},
	// 启用
	enable : function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1) {
			showWarn("请选择要启用的记录!");
			return;
		}
		var url = BasePath + options.url, enableFlag = true, enableLen = "";
		// 校验数据是否可删除
	    if(typeof options.checkEnable === 'function') {
	    	enableFlag = options.checkEnable(checkedRows);
	    }
		 // 校验数据是否可激活
	    if(!enableFlag) {
	        return;
	    }
		if(typeof checkedRows != "undefined" && checkedRows != null) {
			enableLen = checkedRows.length;
		}
		$.messager.confirm("确认","你确定要启用这"+enableLen+"条数据", function(r) {  
	        if(r) {
	        	var enableList = options.buildEnableData(checkedRows);
	        	if(enableList.length > 0) {
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(enableList);
		            ajaxRequest(url, effectRow, function(result) {
		        		options.enableCallback(options, result);
		        	}); 
	        	} else {
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	},
	// 停用
	unable : function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1) {
			showWarn("请选择要停用的记录!");
			return;
		}
		var url = BasePath + options.url, unableFlag = true, unableLen = "";
		// 校验数据是否可删除
	    if(typeof options.checkUnable === 'function') {
	    	unableFlag = options.checkUnable(checkedRows);
	    }
		 // 校验数据是否可注销
	    if(!unableFlag) {
	        return;
	    }
		if(typeof checkedRows != "undefined" && checkedRows != null) {
			unableLen = checkedRows.length;
		}
		$.messager.confirm("确认","你确定要停用这"+unableLen+"条数据", function(r) {  
	        if(r) {
	        	var unableList = options.buildUnableData(checkedRows);
	        	if(unableList.length > 0) {
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(unableList);
		            ajaxRequest(url, effectRow, function(result) {
		            	options.unableCallback(result, options);
		        	}); 
	        	} else {
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	},
	// 审核
	audit : function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1) {
			showWarn("请选择要审核的记录!");
			return;
		}
		var url = BasePath + options.url, auditFlag = true, auditLen = "";
		// 校验数据是否可审核
	    if(typeof options.checkAudit === 'function') {
	    	auditFlag = options.checkAudit(checkedRows);
	    }
		 // 校验数据是否可审核
	    if(!auditFlag) {
	        return;
	    }
		if(typeof checkedRows != "undefined" && checkedRows != null) {
			auditLen = checkedRows.length;
		}
		$.messager.confirm("确认","你确定要审核这"+auditLen+"条数据", function(r) {  
	        if(r) {
	        	var auditList = options.buildAuditData(checkedRows);
	        	if(auditList.length > 0) {
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(auditList);
		            ajaxRequest(url, effectRow, function(result) {
		            	options.auditCallback(result, options);
		        	}); 
	        	} else {
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	},
	// 反审核
	antiAudit : function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1) {
			showWarn("请选择要反审核的记录!");
			return;
		}
		var url = BasePath + options.url, antiAuditFlag = true, antiAuditLen = "";
		// 校验数据是否可反审核
	    if(typeof options.checkAntiAudit === 'function') {
	    	antiAuditFlag = options.checkAntiAudit(checkedRows);
	    }
		 // 校验数据是否可反审核
	    if(!antiAuditFlag) {
	        return;
	    }
		if(typeof checkedRows != "undefined" && checkedRows != null) {
			antiAuditLen = checkedRows.length;
		}
		$.messager.confirm("确认","你确定要反审核这"+antiAuditLen+"条数据", function(r) {  
	        if(r) {
	        	var antiAuditList = options.buildAntiAuditData(checkedRows);
	        	if(antiAuditList.length > 0) {
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(antiAuditList);
		            ajaxRequest(url, effectRow, function(result) {
		            	options.antiAuditCallback(result, options);
		        	}); 
	        	} else {
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	},
	//导出
	exportExcel : function(options) {
		var defaults = {
			searchFormId : "searchForm",
			dataGridId : "dataGridDiv",
			exportUrl : "/do_fas_export",
			exportTitle : "导出",
			exportType : "common"
        };
		options = $.extend(defaults, options);
    	var $dg = $("#" + options.dataGridId);
	    var queryParams = $dg.datagrid('options').queryParams;
	    var grepColumns = $dg.datagrid('options').columns;
	    var subGrepColumns = $dg.datagrid('options').subColumns;
	    var columns = $.grep(grepColumns[0], function(o, i) {
	        if ($(o).attr("notexport") == true) {
	            return true;
	        }
	        return false;
	    }, true);
	    // 获取排序字段，由于sortName只能获取field字段，所以需要转换
	    var sortName = $dg.datagrid('options').sortName;
	    var sortField = "", sortOrder = $dg.datagrid('options').sortOrder;
	    if(sortName && columns) {
	    	for(var i = 0; i < columns.length; i++) {
	    		if(sortName == columns[i].field) {
	    			sortField = columns[i].sortField;
	    			break;
	    		}
	    	}
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
	    var dataRow = $dg.datagrid('getRows');
	    $("#exportExcelForm").remove();
	    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	    if(dataRow.length > 0) {
	    	$('#exportExcelForm').form('submit', {
	            url : BasePath + options.exportUrl,
	            onSubmit : function(params) {
	            	params.exportColumns = exportColumns;
	            	params.exportSubColumns = exportSubColumns;
	            	params.fileName = options.exportTitle;
	            	params.exportType = options.exportType;
	            	params.orderByField = sortField;
	            	params.orderBy = sortOrder;
	                if(queryParams != null && queryParams != {}) {
	                    $.each(queryParams, function(i) {
	                    	params[i] = queryParams[i];
	                    });
	                }
	            },
	            success : function() {

	            }
	        });
	    } else {
	        showWarn('查询记录为空，不能导出!');
	    }
	},
	editIndex : undefined,
	// 新增行
	addEditorRow :function(options) {
		var defaults = {
			dataGridId : "dtlDataGridDiv",
			initRow : {},
			rowData : {},
			comboboxData : {}
		};
		options = $.extend(defaults, options);
		$("#" + options.dataGridId).addEditorRow(options);
	},
	// 修改行
	editEditorRow : function(options) {
		var defaults = {
			dataGridId : "dtlDataGridDiv",
			rowIndex : 0,
			row : {},
			initRow : {}
		};
		options = $.extend(defaults, options);
		$("#" + options.dataGridId).editEditorRow(options);
	},
	// 删除行
	deleteEditorRow : function(options) {
		var defaults = {
			dataGridId : "dtlDataGridDiv",
			checkDel : function(checkedRows) {
				return true;
			}
		};
		options = $.extend(defaults, options);
		$("#" + options.dataGridId).deleteEditorRow(options);
	},
	// 结束行编辑
	endEditing : function(dataGridId) {
		dataGridId = dataGridId || 'dtlDataGridDiv'
		var tempObj = $('#'+dataGridId);
		tempObj.datagrid("unselectAll");
		var rowArr = tempObj.datagrid('getRows');
	    for (var i = 0; i < rowArr.length; i++) {
	    	if(tempObj.datagrid('validateRow', i)) {
	    		tempObj.datagrid('endEdit', i);
	    	} else {
	    		return false;
	    	}
	    }
	    return true;
	},
	//获取editor的值
	getEditorVal : function(options) {
		var editor = $("#" + options.dataGrid).datagrid('getEditor', {
	        'index': options.rowIndex,
	        'field': options.field
	    });
		var editorVal = "";
	    var target = editor.target;
	    var ed = $.fn.datagrid.defaults.editors[editor.type];
	    if (ed) {
	    	editorVal = ed.getValue(target, options.field);
	    }
	    return editorVal;
	},
	//设置editor的值
	setEditorVal : function(options) {
		var editor = $("#" + options.dataGridId).datagrid('getEditor', {
		     'index': options.rowIndex,
		     'field': options.field
	    });
	    if (editor) {
	    	editor.target.val(options.value);
	    }
	},
	// 校验行数据是否重复
	checkRowUnique : function(options) {
		var defaults = {
			dataGridId : "dtlDataGridDiv",
			uniqueField : ""	
		};
		options = $.extend(defaults, options);
		var rows = $("#"+options.dataGridId).datagrid("getRows");
		if(rows.length == 0) {
			return true;
		}
		var fieldNos = [];
		$.each(rows, function(index, row){
			fieldNos.push(row[options.uniqueField]);
		});
		var fieldNoLen = fieldNos.length;
		$.unique(fieldNos);
		var uniqueFieldNoLen = fieldNos.length;
		if(fieldNoLen != uniqueFieldNoLen) {
			showWarn("有重复数据，请修改后再保存！");
			return false;
		}
		return true;
	}
};
// jquery对象级插件
(function($) {
	// 插入一行数据
	$.fn["addEditorRow"] = function(options) {
		var defaults = {
			initRow : {},
			rowData : {},
			comboboxData : {},
			beforeAdd : function(rowIndex) {},
			buildAddData : {}
		};
		options = $.extend(defaults, options);
		this.each(function() {
			var _this = $(this);
			var dataGridId = _this.attr("id");
			if(endEditing(dataGridId)) {
				$(_this).siblings("div[class='datagrid-empty-msg']").hide();
//				$(".datagrid-empty-msg").hide();
//				var rowArr = _this.datagrid('getRows');
//				var rowLen = 0;
//				if(!rowArr || rowArr.length < 0) {
//					rowLen = 0;
//				}
				var rowLen = _this.datagrid('getRows').length;
				if(typeof rowLen == 'undefined' || rowLen < 0) {
					rowLen = 0;
				}
				options.beforeAdd(rowLen);
				var rowObj = options.rowData || options.initRow;
				if(typeof options.buildAddData === 'function') {
					rowObj = options.buildAddData(rowLen);
				}
				$.fas.editIndex = rowLen;
				_this.datagrid('insertRow', {
					index : rowLen,
					row : rowObj
				});
				_this.datagrid('selectRow', rowLen);
				_this.datagrid('beginEdit', rowLen);
			}
        });
		return this;
	};
	
	// 修改一行数据
	$.fn["editEditorRow"] = function(options) {
		var defaults = {
			rowIndex : 0,
			row : {},
			initRow : {},
			beforeUpdate : function(rowIndex, row) {},
			buildUpdateData : {}
		};
		options = $.extend(defaults, options);
		this.each(function() {
			var _this = $(this);
			var dataGridId = _this.attr("id");
			if(endEditing(dataGridId)) {
				options.beforeUpdate(options.rowIndex, options.row);
				$.fas.editIndex = options.rowIndex;
				var rowObj = options.initRow;
				if(typeof options.buildUpdateData === 'function') {
					rowObj = options.buildUpdateData(options.rowIndex, options.row);
				}
				_this.datagrid('updateRow', {
					index : options.rowIndex,
					row : rowObj
				});
				_this.datagrid('clearSelections');
				_this.datagrid('selectRow',options.rowIndex);
				_this.datagrid('beginEdit', options.rowIndex);
			}
        });
		return this;
	};
	
	// 删除一行数据
	$.fn["deleteEditorRow"] = function(options) {
		var defaults = {
			checkDel : function(checkedRows) {
				return true;
			}
		};
		options = $.extend(defaults, options);
		this.each(function() {
			var _this = $(this);
			var checkedRows = _this.datagrid('getChecked');
			if(!options.checkDel(checkedRows)) {
				return;
			}
			$.each(checkedRows, function(index, row) {
				var rowIndex = _this.datagrid('getRowIndex', row);
				_this.datagrid('deleteRow',rowIndex);
				if(undefined != $.fas.editIndex && $.fas.editIndex == rowIndex){
					editIndex = undefined;
				}
			});
        });
		return this;
	};
	
	// 结束编辑
	$.fn["endEditing"] = function() {
		this.each(function() {
			var dataGridId = $(this).attr("id");
			return endEditing(dataGridId);
        });
		return this;
	};
	
	function endEditing(dataGridId){
		var tempObj = $('#'+dataGridId);
		var rowArr = tempObj.datagrid('getRows');
		tempObj.datagrid("unselectAll");
	    for (var i = 0; rowArr && i < rowArr.length; i++) {
	    	if(tempObj.datagrid('validateRow', i)) {
	    		tempObj.datagrid('endEdit', i);
	    	} else {
	    		tempObj.datagrid('selectRow', i);
	    		return false;
	    	}
	    }
	    return true;
	};
})(jQuery);

/************************************************* 表单验证 ********************************************************/
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

/******************************************** 扩展easyui editor **************************************************/
$.extend(
    $.fn.datagrid.defaults.editors, {
    	// 可编辑文本的editor
    	fastextbox: {
    		init: function (container, options) {
	    		var id = options.id, name = options.name, width = options.width || 140;
	            var textbox = $("<input type='text' name='"+name+"' class='ipt' id='"+id+"' width='"+width+"'/>");
	            textbox = textbox.appendTo(container);
	            textbox.validatebox(options);
	            return textbox;
    		},
    		destroy: function (target) {
                 
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
                
            }
    	},
    	// 搜索框editor
        searchbox: {
            init: function (container, options) {
            	var id = options.id, name = options.name, textId = options.textId;
            	var valueField = options.valueField, textField = options.textField;
                var idbox = $("<input type='text' name='"+name+"' class='ipt' id='"+id+"' />").appendTo(container);
                idbox.validatebox(options);
                var isFrame = true;
				if(typeof options.isFrame != 'undefined') {
					isFrame = options.isFrame;
				}
				$("#" + id).initIptSearch({
					title : options.title,
					href : BasePath + options.url,
					queryUrl : BasePath + options.queryUrl,
					inputWidth : options.width || 140,
					height : options.height || 'auto',
					isFrame :  isFrame,
					fn : options.callback || function(data) {
						$("#"+id).val(data[valueField]);
						$("#"+textId).val(data[textField]);
					}
				});
                return idbox;
            },
            destroy: function (target) {
               
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
               
            }
        },
        // 只读文本editor
        searchboxname: {
            init: function (container, options) {
            	var id = options.id, name = options.name, readonly = options.readonly, width = options.width || 140;
                var namebox = "";
                if(typeof readonly!='undefined' && readonly) {
                	namebox = $("<input type='text' name='"+name+"' style='width:"+width+"' class='disabled ipt' id='"+id+"' readonly='"+readonly+"'/>");
                } else {
                	namebox = $("<input type='text' name='"+name+"' style='width:"+width+"' class='ipt' id='"+id+"'/>");
                }
                namebox = namebox.appendTo(container);
                return namebox;
            },
            destroy: function (target) {
                
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
              
            }
        },
        // 隐藏文本editor
        hiddenbox: {
            init: function (container, options) {
            	var id = options.id, name = options.name;
                var hiddenbox = $("<input type='text' name='"+name+"' class='ipt' id='"+id+"' hidden='true'/>");
                hiddenbox = hiddenbox.appendTo(container);
                return hiddenbox;
            },
            destroy: function (target) {
                
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
              
            }
        },
        // 下拉框editor
        fascombobox: {
            init: function (container, options) {
	        	var defaults = {
					id : "",
	       			name : "",
	       			valueField : "value",
	       			textField : "text",
					url : "",
					width : 140
	       		};
	   			options = $.extend(defaults, options);
	   			var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
	   			idbox.validatebox(options);
	   			if(!options.onSelect) {
	   				options.onSelect = function(data) {
	   					$("#"+options.id).val(data[options.valueField]);
	   				}
	   			}
	   			$("#" + options.id).initCombox(options);
       			return idbox;
            },
            destroy: function (target) {
                
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
         	   $(target).val(value);
         	   $(target).combobox("setValue", value);
            },
            resize: function (target, width) {
               
            }
        },
        // 大类editor
        categorybox : {
        	init: function (container, options) {
        		var defaults = {
    				title : "选择大类",
        			id : "categoryNo",
        			name : "categoryNo",
					href : BasePath + "/category/select?levelid=1",
					queryUrl : BasePath + "/category/list.json",
					inputWidth : 140,
					width : 500,
					height : 'auto',
					isFrame :  false
        		};
        		options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
                idbox.validatebox(options);
                if(!options.callback) {
	       			options.fn = function(data) {
	       				$("#categoryNo").val(data.categoryNo);
						$("#categoryName").val(data.name);
					} 
	       		} else {
	       			options.fn = options.callback;
	       		}
            	$("#" + options.id).initIptSearch(options);
                return idbox;
            },
            destroy: function (target) {
            	
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
            	$(target).val(value);
            },
            resize: function (target, width) {
               
            }
       },
       // 大类下拉editor
       categorycombobox : {
    	   init: function (container, options) {
    		   var defaults = {
    				id : "categoryNo",
	       			name : "categoryNo",
	       			valueField : "categoryNo",
	       			textField : "name",
					url : BasePath + "/category/get_biz.json?levelid=1",
					inputWidth : 140
	       		};
       			options = $.extend(defaults, options);
       			var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
       			idbox.validatebox(options);
       			if(options && !options.onSelect) {
       				options.onSelect = function(data) {
						$('#categoryNo').val(data[options.valueField]);
						$('#categoryName').val(data[options.textField]);
					}
       			}
       			$("#" + options.id).initCombox(options);
       			return idbox;
           },
           destroy: function (target) {
              
           },
           getValue: function (target) {
               return $(target).val();
           },
           setValue: function (target, value) {
        	   $(target).val(value);
        	   $(target).combobox("setValue", value);
           },
           resize: function (target, width) {
              
           }
      },
       // 品牌editor
       brandbox : {
    	   init: function (container, options) {
    		   var defaults = {
       				title : "选择品牌",
    				id : "brandNo",
    				name : "brandNo",
				    href : BasePath + '/plugin_page/searchBrand',
				    queryUrl: BasePath + '/brand/list.json',
					url: BasePath + '/brand/get_biz?status=1',
		        	inputWidth : 140,
					width : 500,
					height : 'auto',
					isFrame :  false
       		   };
	       	   options = $.extend(defaults, options);
               var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
               idbox.validatebox(options);
               if(!options.callback) {
	       			options.fn = function(data) {
	       				$("#brandNo").val(data.brandNo);
						$("#brandName").val(data.name);
					} 
	       		} else {
	       			options.fn = options.callback;
	       		}
        	   $("#" + options.id).initIptSearch(options);
               return idbox;
           },
           destroy: function (target) {
              
           },
           getValue: function (target) {
               return $(target).val();
           },
           setValue: function (target, value) {
               $(target).val(value);
           },
           resize: function (target, width) {
              
           }
      },
      // 品牌部editor
      brandunitbox : {
    	  init: function (container, options) {
    		  var defaults = {
				  boxType : "combobox",
				  title : "选择品牌部",
				  id : "brandUnitNo",
				  name : "brandUnitNo",
				  href : BasePath + '/plugin_page/searchBrandUnit',
				  queryUrl: BasePath + '/brand_unit/list.json',
				  url: BasePath + '/brand_unit/get_biz?status=1',
				  valueField: 'brandUnitNo',
				  textField: 'name',
				  inputWidth : 140,
				  width : 500,
				  height : 'auto',
				  isFrame :  false,
				  fn : function(data) {
					$("#brandUnitNo").val(data.brandNo);
					$("#brandUnitName").val(data.name);
				  } 
    		  };
       	   	  options = $.extend(defaults, options);
	          var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
	          idbox.validatebox(options);
	          if(options.boxType == 'iptSearch') {
	        	  $("#" + options.id).initIptSearch(options);
              } else if(options.boxType == 'combobox') {
            	  $("#" + options.id).initCombox(options);
              }
	          return idbox;
          },
          destroy: function (target) {
             
          },
          getValue: function (target) {
              return $(target).val();
          },
          setValue: function (target, value) {
              $(target).val(value);
          },
          resize: function (target, width) {
             
          }
      },
      // 商品editor
      itembox : {
    	   init: function (container, options) {
    		   var defaults = {
	   				title : "选择商品",
	       			id : "itemNo",
	       			name : "itemNo",
	       			href : BasePath + '/plugin_page/searchItem',
	       			queryUrl: BasePath + '/base_setting/item/list.json',
					inputWidth : 140,
					width : 650,
					height : 'auto',
					isFrame : false
       			};
	       		options = $.extend(defaults, options);
	       		var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
	       		idbox.validatebox(options);
	       		if(!options.callback) {
	       			options.fn = function(data) {
	       				$("#itemNo").val(data.itemNo);
						$("#itemName").val(data.name);
					} 
	       		} else {
	       			options.fn = options.callback;
	       		}
	       		$("#" + options.id).initIptSearch(options);
	       		return idbox;
           },
           destroy: function (target) {
              
           },
           getValue: function (target) {
               return $(target).val();
           },
           setValue: function (target, value) {
               $(target).val(value);
           },
           resize: function (target, width) {
              
           }
       },
       // 供应商editor
       supplierbox : {
    	   init: function (container, options) {
    		   var defaults = {
	   				title : "选择供应商",
	       			id : "supplierNo",
	       			name : "supplierNo",
	       			href : BasePath + '/plugin_page/searchSupplier',
	       			queryUrl: BasePath + '/base_setting/supplier/list.json',
					inputWidth : 140,
					width : 500,
					height : 'auto',
					isFrame : false
       			};
	       		options = $.extend(defaults, options);
	       		var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
	       		idbox.validatebox(options);
	       		if(!options.callback) {
	       			options.fn = function(data) {
						$("#supplierNo").val(data.supplierNo);
						$("#supplierName").val(data.shortName);
					} 
	       		} else {
	       			options.fn = options.callback;
	       		}
	       		$("#" + options.id).initIptSearch(options);
	       		return idbox;
           },
           destroy: function (target) {
              
           },
           getValue: function (target) {
               return $(target).val();
           },
           setValue: function (target, value) {
               $(target).val(value);
           },
           resize: function (target, width) {
              
           }
       },
       // 结算公司editor
       companybox : {
    	   init: function (container, options) {
    		   var defaults = {
	   				title : "选择公司",
	       			id : "companyNo",
	       			name : "companyNo",
	       			href : BasePath + '/plugin_page/searchCompany',
	       			queryUrl: BasePath + '/base_setting/company/list.json',
					inputWidth : 140,
					width : 500,
					height : 'auto',
					isFrame : false
       			};
	       		options = $.extend(defaults, options);
	       		var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
	       		idbox.validatebox(options);
	       		if(!options.callback) {
	       			options.fn = function(data) {
						$("#companyNo").val(data.companyNo);
						$("#companyName").val(data.name);
					} 
	       		} else {
	       			options.fn = options.callback;
	       		}
	       		$("#" + options.id).initIptSearch(options);
	       		return idbox;
           },
           destroy: function (target) {
              
           },
           getValue: function (target) {
               return $(target).val();
           },
           setValue: function (target, value) {
               $(target).val(value);
           },
           resize: function (target, width) {
              
           }
       },
       // 供应商editor
       shopbox : {
    	   init: function (container, options) {
    		   var defaults = {
	   				title : "选择店铺",
	       			id : "shopNo",
	       			name : "shopNo",
	       			href : BasePath + '/plugin_page/searchShop',
	       			queryUrl: BasePath + '/shop/list.json',
					inputWidth : 140,
					width : 550,
					height : 'auto',
					isFrame : false,
					relationData : false // 是否关联公司等数据
       			};
	       		options = $.extend(defaults, options);
	       		var idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' />").appendTo(container);
	       		idbox.validatebox(options);
       			options.fn = function(data) {
       				$("#shopNo").val(data.shopNo);
					$("#shortName").val(data.shortName);
					$("#fullName").val(data.fullName);
					$("#retailType").val(data.retailType);
					if(options.relationData) {
						$.ajax({
						   url: BasePath + "/shop/initSubInfo",
						   data: {shopNo:data.shopNo},
						   async:false,
						   success: function(result){
								if(result!=null) {
									$("#companyNo").val(result.companyNo);
									$("#companyName").val(result.companyName);
								} else {
									showWarn('获取店铺关联数据失败,请联系管理员!');
								}
							},
							error:function(result){
								showWarn('获取店铺关联数据失败,请联系管理员!');
							}
						});
	       			}
	       		}
	       		$("#" + options.id).initIptSearch(options);
	       		return idbox;
           },
           destroy: function (target) {
              
           },
           getValue: function (target) {
               return $(target).val();
           },
           setValue: function (target, value) {
               $(target).val(value);
           },
           resize: function (target, width) {
              
           }
       },
       // 季节editor
       seasonbox : {
    	   init: function (container, options) {
    		   var defaults = {
				   valueField: 'itemvalue',
				   textField: 'itemname',
				   width : 130,
	   			   title : "选择公司",
	   			   id : "seasonNo",
	   			   name : "seasonNo",
	   			   url : BasePath + '/initCache/getLookupDtlsList?lookupcode=SEASON',
	   			   readonly : true,
	   			   onSelect : function(data) {
	   				   $('#seasonNo').val(data.itemvalue);
	   				   $('#seasonName').val(data.itemname);
	   			   } 
       			};
    		   	var idbox = null;
	       		options = $.extend(defaults, options);
	       		if(options.required) {
                	idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"' required='true'/>").appendTo(container);
                } else {
                	idbox = $("<input type='text' name='"+options.name+"' class='ipt' id='"+options.id+"'/>").appendTo(container);
                }
	       		idbox.validatebox(options);
	       		$("#" + options.id).initCombox(options);
	       		return idbox;
           },
           destroy: function (target) {
        	   $(target).combobox("destroy");
           },
           getValue: function (target) {
        	   return $(target).combobox('getValue');
           },
           setValue: function (target, value) {
        	   $(target).val(value);
        	   $(target).combobox('setValue', value);
           },
           resize: function (target, width) {
              
           }
       }
});

/*************************************  jquery对象级插件 ********************************************/
(function($) {
	// 初始化search弹出框组件
	$.fn.initIptSearch = function(options) {
		var defaults = {
			title : "查询",
			href : "",
			queryUrl : "",
			inputWidth : 140,
			width : 500,
			height : 'auto',
			readonly : true,
			disabled : false,
			isFrame : false,
			enableCloseButton : false,
			fn : function(data) {}
        };
        options = $.extend(defaults, options);
        this.each(function() {
        	var $this = $(this);
        	$this.iptSearch({
        		width : options.inputWidth,
        		readonly : options.readonly,
        		disabled : options.disabled,
        		enableCloseButton : options.enableCloseButton,
        		clickFn : function() {
        			dgSelector(options);
        		}
        	});
        });
        return this;
	};
	
	// 初始化combox组件
	$.fn.initCombox = function(options) {
		var defaults = {
			url : "",
			data : "",	
			readonly : true,
			width : 140,
			panelHeight : 'auto',
			valueField : "value",
			textField : "text",
			onChange : function(data) {},
		    onSelect : function(data) {},
		    multiple : false
        };
        options = $.extend(defaults, options);
        this.each(function() {
        	$(this).combobox(options);
        });
        return this;
	};
	// 添加tab页签
	$.fn.addTab = function(options) {
		var defaults = {
			title : "",
			selected : false,
			closable : false,
			href : "",
			onLoad : function(panel) {}
        };
        options = $.extend(defaults, options);
        this.each(function() {
        	$(this).tabs('add', options);
        });
        return this;
	};
	// 清空datagrid的数据
	$.fn.clearDataGrid = function() {
		this.each(function() {
        	$(this).datagrid({
        		url : ""
        	});
        	$(this).datagrid('loadData',{total:0,rows:[]});
        });
        return this;
	};
})(jQuery);


/*************************************  easyui插件 ************************************************/
/** 商品弹出框插件 */
(function($) {
	var _name = 'item';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.itemNo;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.itemNo = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择商品',
	     inputWidth : 140,
	     panelWidth: 650,
	     panelHeight: 'auto',
	     valueFeild: 'itemNo',//
	     textFeild: 'name',// 数据表中的name
	     inputValueFeild: 'itemNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'itemName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchItem',
	     multipleHref : BasePath + '/plugin_page/multiSearchItem',
	     queryUrl: BasePath + '/base_setting/item/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 结算公司弹出框插件 */
(function($) {
	var _name = 'company';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				readonly : options.readonly,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				readonly : options.readonly,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.companyNo;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.companyNo = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择公司',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'companyNo',//
	     textFeild: 'name',// store表中的name
	     inputValueFeild: 'companyNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'companyName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchCompany',
	     multipleHref : BasePath + '/plugin_page/multiSearchCompany',
	     queryUrl: BasePath + '/base_setting/company/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false,
	     readonly : true
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 结算公司和客户弹出框插件 */
(function($) {
	var _name = 'customerAndCompany';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				readonly : options.readonly,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				readonly : options.readonly,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.code;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.code = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择客户',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'code',//
	     textFeild: 'name',// store表中的name
	     inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchCustomerAndCompany',
	     multipleHref : BasePath + '/plugin_page/multiSearchCustomerAndCompany',
	     queryUrl: BasePath + '/bill_balance_invoice_register/listAll.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false,
	     readonly : true
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 供应商弹出框插件 */
(function($) {
	var _name = 'supplier';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.supplierNo;
						    		dataNames += item.shortName;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.supplierNo = dataNos;
								data.shortName = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择供应商',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'supplierNo',//
	     textFeild: 'shortName',// 数据表中的name
	     inputValueFeild: 'supplierNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'supplierName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchSupplier',
	     multipleHref : BasePath + '/plugin_page/multiSearchSupplier',
	     queryUrl: BasePath + '/base_setting/supplier/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 客户弹出框插件 */
(function($) {
	var _name = 'customer';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.customerNo;
						    		dataNames += item.shortName;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.customerNo = dataNos;
								data.shortName = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择客户',
	     inputWidth : 140,
	     panelWidth: 600,
	     panelHeight: 'auto',
	     valueFeild: 'customerNo',//
	     textFeild: 'shortName',// store表中的name
	     inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchCustomer',
	     multipleHref : BasePath + '/plugin_page/multiSearchCustomer',
	     queryUrl: BasePath + '/base_setting/customer/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 商场弹出框插件 */
(function($) {
	var _name = 'mall';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.mallNo;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.mallNo = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择商场',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'mallNo',//
	     textFeild: 'name',// 数据表中的name
	     inputValueFeild: 'mallNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'mallName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchMall',
	     multipleHref : BasePath + '/plugin_page/multiSearchMall',
	     queryUrl: BasePath + '/mall/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 店铺弹出框插件 */
(function($) {
	var _name = 'shop';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.shopNo;
						    		dataNames += item.shortName;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.shopNo = dataNos;
								data.shortName = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择店铺',
	     inputWidth : 140,
	     panelWidth: 650,
	     panelHeight: 'auto',
	     valueFeild: 'shopNo',//
	     textFeild: 'shortName',// 数据表中的name
	     inputValueFeild: 'shopNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'shopName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchShop',
	     multipleHref : BasePath + '/plugin_page/multiSearchShop',
	     queryUrl: BasePath + '/shop/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 品牌部弹出框插件 */
(function($) {
	var _name = 'brandunitsearch';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.brandUnitNo;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.brandUnitNo = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择品牌部',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'brandUnitNo',//
	     textFeild: 'name',// store表中的name
	     inputValueFeild: 'brandUnitNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'brandUnitName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchBrandUnit',
	     multipleHref : BasePath + '/plugin_page/multiSearchBrandUnit',
	     queryUrl: BasePath + '/brand_unit/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 品牌弹出框插件 */
(function($) {
	var _name = 'brandsearch';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.brandNo;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.brandNo = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择品牌',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'brandNo',//
	     textFeild: 'name',// store表中的name
	     inputValueFeild: 'brandNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'brandName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchBrand',
	     multipleHref : BasePath + '/plugin_page/multiSearchBrand',
	     queryUrl: BasePath + '/brand/list.json',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 品牌下拉框插件 */
(function($) {
	var _name = 'brandbox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
		readonly : true,
        width: 130,
        url: BasePath + '/brand/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'brandNo',
    	textField: 'name',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple: false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 地区下拉框插件 */
(function($) {
	var _name = 'zonebox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
		readonly : true,
        width: 130,
        url: BasePath + '/zone_info/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'zoneNo',
    	textField: 'name',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple: false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 季节下拉框插件 */
(function($) {
	var _name = 'seasonbox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        if(opts.addAll) {
	        	opts.url += "&addAllFlag=true";
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
		readonly : true,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SEASON',
        panelHeight : "auto",
        valueField : 'itemvalue',
    	textField : 'itemname',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple : false,
		addAll : false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 季节下拉框插件 */
(function($) {
	var _name = 'yearbox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
		readonly : true,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=YEAR',
        panelHeight : "auto",
        valueField : 'itemvalue',
    	textField : 'itemname',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple : false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 大类下拉框插件 */
(function($) {
	var _name = 'categorybox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
		readonly : true,
        width: 130,
        url: BasePath + '/category/get_biz?levelid=1&status=1',
        panelHeight : "auto",
        valueField : 'categoryNo',
    	textField : 'name',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple : false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 结算大类下拉框插件 */
(function($) {
	var _name = 'settlecategorybox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
        width: 130,
        readonly : true,
        url: BasePath + '/settle_category/get_biz?status=1',
        panelHeight : "auto",
        valueField : 'settleCategoryNo',
    	textField : 'name',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple : false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 新旧款下拉框插件 */
(function($) {
	var _name = 'newstylebox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
        width: 130,
        readonly : true,
        url: BasePath + '/settle_new_style/get_biz?status=1',
        panelHeight : "auto",
        valueField : 'styleNo',
    	textField : 'name',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple : false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 供应商组下拉框插件 */
(function($) {
	var _name = 'suppliergroupbox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
        width: 130,
        readonly : true,
        url: BasePath + '/supplier_group/get_biz?status=1',
        panelHeight : "auto",
        valueField : 'groupNo',
    	textField : 'groupName',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple : false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 操作状态下拉框插件 */
(function($) {
	var _name = 'statusbox';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	
	// 默认的初始化参数
	$.fn[_name].defaults = {
        width: 130,
        readonly : true,
        data : [{"value":"1","text":"已启用"},{"value":"0","text":"已停用"}],
        panelHeight: "auto",
        valueField: 'value',
    	textField: 'text',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple: false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 币别下拉框插件 */
(function($) {
	var _name = 'currency';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	    	return $.fn[_name].methods[options](this, param);
	    }
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        $(this).combobox(opts);
	    });
	};
	// 默认的初始化参数
	$.fn[_name].defaults = {
        width: 130,
        readonly : true,
        data : [{"value":"0","text":"人民币"},{"value":"1","text":"美元"},{"value":"2","text":"欧元"},
                {"value":"3","text":"日元"},{"value":"4","text":"英镑"},{"value":"5","text":"港币"}
        ],
        panelHeight: "auto",
        valueField: 'value',
    	textField: 'text',
    	onChange : function(data) {},
	    onSelect : function(data) {},
		multiple: false
    };

   // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };
    
    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("disable");
 		   });
 	   },
 	   enable: function (jq) {
 		   $.each(jq, function (index, item) {
 			  $(jq).attr("readonly", true).combobox("enable");
 		   });
 	   },
 	   clear : function(jq) {
 		  $(jq[0]).combobox("clear");
 	   }
    };
    //将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 结算公司或者客户弹出框插件 */
(function($) {
	var _name = 'customerOrCompany';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		$(jq).iptSearch({
			width : options.inputWidth,
			clickFn: function () {
				ygDialog({
					title : options.title,
					href : options.href,
					width : options.panelWidth,
					height : options.panelHeight,
					isFrame : options.isFrame,
					enableCloseButton : options.enableCloseButton,
					onLoad : function(win, content) {
						var _this = $(this);
						$("#dataSourceTypeConditioin").initCombox({
							data : [{text:"客户", value:"customer"}, {text:"公司", value:"company"}],
							width : 80,
							value : "customer", // 设置默认值
							onSelect : function(data) {
								if("customer" == data.value) {
									$("#dialog_SearchDataGrid").datagrid({"columns" : [[
                                        {field : 'customerNo',title : '客户编码',width : 150,align:'left'},
				                  		{field : 'fullName',title : '客户名称',width : 200,align:'left'},
				                  		{field : 'shortName',title : '客户简称',width : 150,align:'left'}
   									]],
   									url : "",
   									onDblClickRow : function(index, row) {
   										_setValue(jq, row);
   										win.close();
   									}});
									$("#noCondition").html("客户编码：");
									$("#nameCondition").html("客户名称：");
									$("#inputNoCondition").attr("name", "customerNo");
									$("#inputNameCondition").attr("name", "fullName");
									options.queryUrl = BasePath + '/base_setting/customer/list.json';
								} else if("company" == data.value) {
									$("#dialog_SearchDataGrid").datagrid({"columns" : [[
                                         {field : 'companyNo',title : '结算公司编码',width : 150, halign : 'center', align : 'left'},
                                         {field : 'name',title : '结算公司名称',width : 260, halign : 'center', align : 'left'}
									]],
									url : "",
									onDblClickRow : function(index, row) {
									   if(options.callback) {
										   options.callback();
									   } else {
										   $("#"+options.inputValueFeild).val(row.companyNo);
										   $("#"+options.inputNameFeild).val(row.name);
									   }
									   win.close();
   									}});
									$("#noCondition").html("公司编码：");
									$("#nameCondition").html("公司名称：");
									$("#inputNoCondition").attr("name", "companyNo");
									$("#inputNameCondition").attr("name", "name");
									options.queryUrl = BasePath + '/base_setting/company/list.json';
								}
								$('#dialog_SearchDataGrid').datagrid('loadData',{total:0,rows:[]}); 
							}
						});
						// 默认页面表进来时，双击行操作后，设置回调函数
						setDefaultDblclick(jq, win);
						// 默认页面进入时，设置url地址为查询客户的，否则当选择公司数据后，再一次进入组件页面，则会查询公司数据
						options.queryUrl = BasePath + '/base_setting/customer/list.json';
						
						$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
							var reqParam = $("#dialog_SarchForm").form("getData");
					    	//组装请求参数
					    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
						    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
						    $("#dialog_SearchDataGrid").datagrid('load');
						});
						$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
							 var dataSourceType = $("#dataSourceTypeConditioin").combobox("getValue");
							 $("#dialog_SarchForm").form("clear");
							 $("#dataSourceTypeConditioin").combobox("setValue", dataSourceType);
						});
					}
				});
			}
		});
    };
    
    // 默认页面表进来时，双击行操作后，设置回调函数
    function setDefaultDblclick(jq, win) {
    	$("#dialog_SearchDataGrid").datagrid({
    		onDblClickRow : function(index, row) {
    			_setValue(jq, row);
    			win.close();
			}
    	});
    }
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择客户或公司',
	     inputWidth : 140,
	     panelWidth: 600,
	     panelHeight: 'auto',
	     valueFeild: 'customerNo',//
	     textFeild: 'shortName',// store表中的name
	     inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchCustomerOrCompany',
	     queryUrl: BasePath + '/base_setting/customer/list.json',
	     enableCloseButton : false,
	     isFrame: false,
	     readonly : true
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   if(op.callback) {
		   op.callback();
	   } else {
		   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
		   $("#"+op.inputNameFeild).val(data[op.textFeild]);
	   }
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

/** 结算公司和客户弹出框插件 */
(function($) {
	var _name = 'customerMultiDataSource';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		if(!options.multiple) {
			$(jq).iptSearch({
				width : options.inputWidth,
				readonly : options.readonly,
				clickFn: function () {
					dgSelector({
						title : options.title,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						href : options.href,
						queryUrl : options.queryUrl,
						enableCloseButton : options.enableCloseButton,
						fn : function(data) {
							if(options.callback) {
								options.callback(data);
							} else {
								_setValue(jq, data);
							}
						}
					});
				}
			});
		} else {
			$(jq).iptSearch({
				width : options.inputWidth,
				readonly : options.readonly,
				clickFn: function () {
					ygDialog({
						title : options.title,
						href : options.multipleHref,
						width : options.panelWidth,
						height : options.panelHeight,
						isFrame : options.isFrame,
						enableCloseButton : options.enableCloseButton,
						onLoad : function(win, content) {
							var _this = $(this);
							$("#dialog_SearchDataGrid").datagrid({onDblClickRow : function(index, row) {
								if(options.callback) {
								   options.callback();
								} else {
									_setValue(jq, row);
								}
								win.close();
							}});
							$("#dgSelectorSearchBtn", _this.contents()).on("click", function() {
								var reqParam = $("#dialog_SarchForm").form("getData");
						    	//组装请求参数
						    	$("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
							    $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
							    $("#dialog_SearchDataGrid").datagrid('load');
							});
							$("#dgSelectorClearBtn", _this.contents()).on("click", function() {
								 $("#dialog_SarchForm").form("clear");
							});
							$("#dgSelectorSureBtn", _this.contents()).on("click", function() {
								var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
								var dataNos = "", dataNames = "", data = {};
								$.each(checkedRows, function(index, item) {
									dataNos += item.code;
						    		dataNames += item.name;
						    		if(index < checkedRows.length - 1) {
						    			dataNos += ",";
						    			dataNames += ",";
						    		}
						    	});  
								data.code = dataNos;
								data.name = dataNames;
								if(options.callback) {
									options.callback(data);
								} else {
									_setValue(jq, data);
								}
								win.close();
							});
						}
					});
				}
			});
		}
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     title: '选择客户',
	     inputWidth : 140,
	     panelWidth: 500,
	     panelHeight: 'auto',
	     valueFeild: 'code',//
	     textFeild: 'name',// store表中的name
	     inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
	     inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
	     href : BasePath + '/plugin_page/searchCustomerMultiDataSource',
	     multipleHref : BasePath + '/plugin_page/multiSearchCustomerAndCompany',
	     queryUrl: BasePath + '/base_setting/customer/query_multi_data_source',
	     enableCloseButton : false,
	     multiple : false,
	     isFrame: false,
	     readonly : true
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val(data[op.valueFeild]);
	   $("#"+op.inputNameFeild).val(data[op.textFeild]);
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $("#"+op.valueFeild).val();
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).iptSearch("disable");
   };

   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).iptSearch("enable");
   };
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $("#"+op.inputValueFeild).val("");
	   $("#"+op.inputNameFeild).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);