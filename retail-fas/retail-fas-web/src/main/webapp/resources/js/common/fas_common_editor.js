var fas_common_editor = {};

$.extend(
    $.fn.datagrid.defaults.editors, {
        searchbox: {
            init: function (container, options) {
            	var id = options.id, name = options.name, textId = options.textId;
            	var valueField = options.valueField, textField = options.textField;
                var idbox = $("<input type='text' name='"+name+"' class='ipt' id='"+id+"' />").appendTo(container);
                idbox.validatebox(options);
                var url = fas_common_editor.addDefaultParams(options.url);
				if(typeof convert_url != 'undefined'){
					url = convert_url(options.url);
				}
				var isFrame = true;
				if(typeof options.isFrame != 'undefined') {
					isFrame = options.isFrame;
				}
				fas_common.initIptSearch({
					id : id,
					title : options.title,
					url : url,
					queryUrl : options.queryUrl || "",
					inputWidth : options.width || 140,
					height : options.height || 400,
					isFrame :  isFrame,
					callback : options.callback || function(data) {
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
        searchboxname: {
            init: function (container, options) {
            	var id = options.id, name = options.name, readonly = options.readonly, width = options.width || 140;
                var namebox = "";
                if(typeof readonly!='undefined' && readonly) {
                	namebox = $("<input type='text' name='"+name+"' style='width:"+width+"' class='disabled ipt' id='"+id+"' readonly='"+readonly+"'/>");
                } else {
                	namebox = $("<input type='text' name='"+name+"' style='width:"+width+"'  class='ipt' id='"+id+"'/>");
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
        fascombobox: {
            init: function (container, options) {
            	var id = options.id, name = options.name || options.id, required = options.required, comboboxDatas = options.data;
                var idbox = null;
                if(required) {
                	idbox = $("<input type='text' name='"+name+"' class='easyui-combobox ipt' id='"+id+"' required='true'/>").appendTo(container);
                } else {
                	idbox = $("<input type='text' name='"+name+"' class='easyui-combobox ipt' id='"+id+"'/>").appendTo(container);
                }
                if(comboboxDatas) {
                	$("#"+id).combobox({
            			data : comboboxDatas,
            			valueField : options.valueField,
            			textField : options.textField,
            			width : options.width || 140,
            			onSelect : options.onSelect || function() {}
            		});
                } else {
                	fas_common_editor.ajaxRequest(BasePath + options.url, {}, function(datas) {
                		$("#"+id).combobox({
                			data : datas,
                			valueField : options.valueField,
                			textField : options.textField,
                			width : options.width || 140,
                			onSelect : options.onSelect || function() {}
                		});
                	});
                }
                return idbox;
            },
            destroy: function (target) {
            	try {
	            	$(target).combobox("destroy");
            	} catch(e) {
            		
            	}
            },
            getValue: function (target) {
            	//如果从结算主体复制中进入，则使用combobox方法会报异常
            	try {
            		return $(target).combobox('getValue');
            	} catch(e) {
            		return $(target).val();
            	}
            },
            setValue: function (target, value) {
            	$(target).val(value);
            },
            resize: function (target, width) {

            }
        },
        fasdatebox: {
            init: function (container, options) {
            	var id = options.id, name = options.name;
                var hiddenbox = $("<input type='text' name='"+name+"' style='width:78px' id='"+id+"'  />");
                hiddenbox = hiddenbox.appendTo(container);
                
                $("#"+id).addClass("easyui-datebox");
                $("#"+id).datebox({
                    required:true,
                    dateFmt:'yyyyMM'
                });
               $("#"+id).on("blur",options.blur);
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
        }
});

fas_common_editor.addDefaultParams = function(url) {
//	var len = $("#"+setting.datagridId).datagrid('getRows').length;
	var hasParams = url.indexOf("?") > -1;
	var split = hasParams ? "&" : "?";
	if(typeof fas_common_editor.editIndex == 'undefined' || 
			fas_common_editor.editIndex == 0) {
		return url + split + "noFirstRow=true";
	}
	return url;
};

//查询
fas_common_editor.search=function(){
	var params = $('#'+setting.searchFormId).form('getData');
	var url = BasePath + setting.searchUrl;
	$('#'+setting.datagridId).datagrid('options').queryParams = params;
	$('#'+setting.datagridId).datagrid('options').url = url;
	$('#'+setting.datagridId).datagrid('load');
};

//清空
fas_common_editor.clear=function(){
	$('#'+setting.searchFormId).form('clear');
};

fas_common_editor.endEditing = function(comboboxData){
//    if(fas_common_editor.editIndex == undefined){
//    	return true;
//    }
//    if($("#"+setting.datagridId).datagrid('validateRow', fas_common_editor.editIndex)){
//    	var comboboxs = {};
//    	if(typeof setting.initData!='undefined') {
//    		comboboxs = setting.initData.combobox || {};
//    	}
//    	var rowData = setting.initRow() || {};
//    	if(typeof comboboxs.values != 'undefined' && comboboxs.values.length > 0) {
//    		for(var i = 0; i < comboboxs.values.length; i++) {
//    			var boxValue = comboboxs.values[i];
//    			var boxText = comboboxs.texts[i];
//    			if(typeof comboboxData != 'undefined' && comboboxData != null) {
//    				var text = comboboxData.texts[i];
//    				var value = comboboxData.values[i];
//    				rowData[boxValue] = value;
//    				rowData[boxText] = text;
//    			}
//    		}
//    	}
//    	$("#"+setting.datagridId).datagrid('endEdit', fas_common_editor.editIndex);
//    	$("#"+setting.datagridId).datagrid('updateRow', {
//    		index:fas_common_editor.editIndex,
//    		row:rowData
//    	});
//	    return true;
//    } else {
//    	return false;
//	}
	var tempObj = $('#'+setting.datagridId);
	var rowArr = tempObj.datagrid('getRows');
    for (var i = 0; i < rowArr.length; i++) {
    	if(tempObj.datagrid('validateRow', i)){
    		tempObj.datagrid('endEdit', i);
    	}else{
    		return false;
    	}
    }
    return true;
};

//参数用于结算路径中，复制结算主体功能，其中rowData表示行数据，comboboxData表示下拉框中的文本值和数值，
//数据格式为var comboboxData = {values : [...], texts : [...]};
fas_common_editor.insertRow = function(rowData, comboboxData) {
	if(fas_common_editor.endEditing(comboboxData)) {
		$(".datagrid-empty-msg").hide();
		var rowLen = $("#"+setting.datagridId).datagrid('getRows').length;
		if(typeof rowLen == 'undefined' || rowLen < 0) {
			rowLen = 0;
		}
		if(typeof setting.checkInsertOrUpdate != 'undefined') {
			setting.checkInsertOrUpdate(rowLen);
		}
		var rowObj = {};
		if(typeof rowData != 'undefined') {
			rowObj = rowData;
		} else {
			rowObj = setting.initRow() || {};
		}
		fas_common_editor.editIndex = rowLen;
		$("#"+setting.datagridId).datagrid('insertRow', {
			index : rowLen,
			row : rowObj
		});
//		$("#"+setting.datagridId).datagrid('selectRow', rowLen);
		$("#"+setting.datagridId).datagrid('beginEdit', rowLen);
		var rows = $("#"+setting.datagridId).datagrid('getRows');
		if(rows != null && rows.length > 0) {
			$("#"+setting.datagridId).datagrid("unselectAll");
//			$("#"+setting.datagridId).datagrid('selectRow', rows.length-1);
		}
	}
};

//修改行
fas_common_editor.editRow = function(rowIndex, row) {
	var flag = false;
	if(undefined != row){
		if(fas_common_editor.endEditing()) {
			flag = true;
		}
	} else {
		var checkedRows = $("#"+setting.datagridId).datagrid('getChecked');
		if(checkedRows.length == 1){
			if(fas_common_editor.endEditing()) {
				row = checkedRows[0];
				rowIndex = $("#"+setting.datagridId).datagrid('getRowIndex',row);
				flag = true;
			}
		} else {
			showWarn('请选择一条记录进行修改!',1);
		}
	}
	if(flag) {
		if(typeof setting.checkInsertOrUpdate != 'undefined') {
			setting.checkInsertOrUpdate(rowIndex, row);
		}
		fas_common_editor.editIndex = rowIndex;
		var rowObj = setting.initRow() || {};
		$("#"+setting.datagridId).datagrid('updateRow', {
			index : rowIndex,
			row : rowObj
		});
		$("#"+setting.datagridId).datagrid('clearSelections');
		$("#"+setting.datagridId).datagrid('selectRow',rowIndex);
		$("#"+setting.datagridId).datagrid('beginEdit', rowIndex);
	}
};

//删除行
fas_common_editor.deleteRow = function() {
	var checkedRows = $("#"+setting.datagridId).datagrid('getChecked');
	$.each(checkedRows, function(index, row) {
		var rowIndex = $("#"+setting.datagridId).datagrid('getRowIndex',row);
		$("#"+setting.datagridId).datagrid('deleteRow',rowIndex);
//		fas_common_editor.endEditing();
		if(undefined != fas_common_editor.editIndex && fas_common_editor.editIndex == rowIndex){
			fas_common_editor.editIndex = undefined;
		}
	});
};

//保存
fas_common_editor.save = function(win){
	if(fas_common_editor.endEditing()) {
		var insertedData = $("#"+setting.datagridId).datagrid('getChanges','inserted');
		var updatedData = $("#"+setting.datagridId).datagrid('getChanges','updated');
		var deletedData = $("#"+setting.datagridId).datagrid('getChanges','deleted');
		var data = {
			inserted : JSON.stringify(insertedData),
			updated : JSON.stringify(updatedData)
		};
		var deleteList = [];
    	$.each(deletedData, function(index, item){
    		var obj = new Object();
    		obj[setting.primaryKey] = item[setting.primaryKey];
    		deleteList.push(obj);
    	});
    	if(deleteList.length > 0) {
            data.deleted = JSON.stringify(deleteList);
    	}
    	fas_common_editor.ajaxRequest(BasePath+setting.saveUrl, data, function(result){
			if(result) {
				showSuc("操作成功!");
				if(typeof setting.saveCallback != 'undefined'
					&& typeof setting.saveCallback == 'function') {
					setting.saveCallback();
				}
				//如果是弹出页面，则关闭页面
				if(win) {
					win.close();
				}
				return;
			} else {
				showError("操作失败,请联系管理员!");
			}
		});
	}
};

//公共异步请求
fas_common_editor.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		dataType : 'json',
		success : callback
	});
};

//初始化combox组件
fas_common_editor.initCombox = function(params) {
	$("#" + params.id).combobox({
		url : BasePath + params.url,
		width : params.width || 140,
	    panelHeight : params.height || 200,
	    valueField : params.valueId,    
	    textField : params.textId,
	    onChange : params.changeFun || function() {},
	    onSelect : params.selectFun || function() {},
	    editable : params.editable || false,
	    multiple : params.multiple || false
	});
};

//通用格式化combobox加载的数据显示到datagrid
fas_common_editor.formatterCombox = function(params) {
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
