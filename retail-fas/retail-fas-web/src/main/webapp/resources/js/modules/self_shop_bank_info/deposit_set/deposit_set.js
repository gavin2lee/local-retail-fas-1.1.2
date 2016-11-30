var depositSet = new Object();
//当前用户
var currentUser;

depositSet.editRowIndex = -1;

/**
 * 页面加载完后执行
 */
$(function() {
	$.extend($.fn.validatebox.defaults.rules, {
		onlyNumbers : {// 验证数字
			validator : function(value) {
				return /^[+]?[0-9]+\d*$/i.test(value);
			},
			message : '请输入数字'
		},
		interval : {
			validator : function(value) {
				return /^(?:0|[1-2]?\d|30)$/i.test(value);
			},
			message : '存现间隔日不能超过30天'
		}
	});
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	
	//新增按钮绑定事件
	$("#btn-insert").on('click', function() {
		depositSet.add();
	});
	
	//保存按钮绑定事件
	$("#btn-save").on('click', function() {
		depositSet.save();
	});
	
	//删除按钮绑定事件
	$("#btn-delete").on('click', function() {
		depositSet.del();
	});

	//给导入按钮绑定函数事件
	$("#btn-import-main").on('click', function() {
		depositSet.importExcel();
	});
	//给导出按钮绑定函数事件
	$("#btn-export").on('click', function() {
		depositSet.exportExcel();
	});

});

//新增
depositSet.add = function() {

	var paramTemp = {
		shopNo : 'shopNo',
		shopName : 'shopName',
		prepareCash : 'prepareCash',
		startAmount : 'startAmount',
		beyondLastDepositDate : 'beyondLastDepositDate',
		amount : 'amount',
		depositDiff : 'depositDiff'
	};

	$("#dataGridDiv").datagrid("removeEditor", "shopName");
	$("#dataGridDiv").datagrid(
			"addEditor",
			{
				field : "shopName",
				editor : {
					type : 'shop',
					options : {
						id : 'shopName_',
						name : 'shopName_',
						required : true,
						relationData : true,
						callback : function(row) {
							depositSet.selectorShopCallBack(row,
									paramTemp);
						}
					}
				}
			});

	if (depositSet.endEdit()) {
		$('#dataGridDiv').datagrid('insertRow', {
			index : 0,
			row : {}
		});
		$('#dataGridDiv').datagrid('beginEdit', 0);
		depositSet.editRowIndex = 0;
	}
};

//删除
depositSet.del = function() {
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');

	if (checkedRows.length == 0) {
		showWarn('请选择要删除的行..');
		return;
	}

	var rowIndex;
	$.each(checkedRows, function(index, row) {
		rowIndex = $('#dataGridDiv').datagrid('getRowIndex', row);
		$('#dataGridDiv').datagrid('deleteRow', rowIndex);
		if (depositSet.editRowIndex == rowIndex) {
			depositSet.editRowIndex = -1;
		}
	});
}

//编辑
depositSet.edit = function(rowIndex, data) {
	if (depositSet.endEdit()) {
		$('#dataGridDiv').datagrid('beginEdit', rowIndex);
		depositSet.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
}

//保存
depositSet.save = function() {

	if (depositSet.endEdit()) {
		if (!depositSet.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/deposit_set/save';
		var insertRows = $('#dataGridDiv').datagrid('getChanges',
				'inserted');
		var updatedRows = $('#dataGridDiv').datagrid('getChanges',
				'updated');
		var deletedRows = $('#dataGridDiv').datagrid('getChanges',
				'deleted');
		var changeRows = {
			inserted : JSON.stringify(insertRows),
			updated : JSON.stringify(updatedRows),
			deleted : JSON.stringify(deletedRows)
		};

		if (insertRows.length > 0) {
			$.each(insertRows, function(i, row) {
				row.id = depositSet.uuid(18,16);
			});
		}
		depositSet.saveToDB(url, changeRows);
	}
};

//导入
depositSet.importExcel = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/deposit_set/do_import',
		'templateName' : '自收银店铺存现设置导入.xlsx',
		success : function(data) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data.error)) {
					showError(data.error);
				} else {
					$.importExcel.close();
					$('#dataGridDiv').datagrid('reload');
					showSuc('数据导入成功');
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});
}

//导出
depositSet.exportExcel = function() {
	var $dg = $("#dataGridDiv");
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
	var url = BasePath + "/deposit_set/do_fas_export";
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
				param.fileName = "自收银店铺存现设置导出";
				param.exportType = 'common' || '';
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

depositSet.saveToDB = function(url, changeRows) {
	depositSet.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
			$('#dataGridDiv').datagrid('reload');
		} else {
			showSuc('保存失败');
		}
	});
};

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};

//common ajax request 
depositSet.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async : false,
		dataType : 'json',
		success : callback
	});
};

depositSet.validationBeforeSave = function() {
	var flag = true;
	var insertRows = $('#dataGridDiv').datagrid('getChanges',
			'inserted');
	var updatedRows = $('#dataGridDiv').datagrid('getChanges',
			'updated');

	var dataRows;
	if (insertRows.length > 0 || updatedRows.length > 0) {
		if (insertRows.length > 0) {
			dataRows = insertRows;
		} else {
			dataRows = updatedRows;
		}

		$.each(dataRows, function(i, row) {
			if (!flag) {
				$('#dataGridDiv').datagrid('beginEdit',
						depositSet.editRowIndex);
				depositSet.editRowIndex = $('#dataGridDiv')
						.datagrid('getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

depositSet.selectorShopCallBack = function(data, paramTemp) {
	var valueEd = $('#dataGridDiv').datagrid('getEditor', {
		'index' : depositSet.editRowIndex,
		'field' : paramTemp.shopNo
	});

	var textEd = $('#dataGridDiv').datagrid('getEditor', {
		'index' : depositSet.editRowIndex,
		'field' : 'shopName'
	});
	valueEd.target.val(data[paramTemp.shopNo]);
	textEd.target.val(data[paramTemp.shopName]);
};


//end the current edit row
depositSet.endEdit = function() {
	if ($('#dataGridDiv').datagrid('validateRow', depositSet.editRowIndex)) {
		$('#dataGridDiv').datagrid('endEdit', depositSet.editRowIndex);
		return true;
	}
	return false;
};

//生成UUID,参数：长度,基数
depositSet.uuid = function(len, radix) {
    var chars = '0123456789abcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
 
    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;
 
      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
 
    return uuid.join('');
}