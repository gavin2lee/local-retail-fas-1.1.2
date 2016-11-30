var selfShopDepositAccount = new Object();

selfShopDepositAccount.editRowIndex = -1;

/**
 * 页面加载完后执行
 */
$(function() {
	$.extend($.fn.validatebox.defaults.rules, {
		onlyNumbers : {// 验证数字
			validator : function(value) {
				return /^[0-9]+\d*[-]?[0-9]+\d*$/i.test(value);
			},
			message : '请输入正确存现账号(包含数字或横线)'
		},
		//验证终端绑定账号
		account : {
			validator : function(value) {
				return /^[A-Z0-9]+$/.test(value);
			},
			message : '请输入正确终端绑定账号(包含数字或大写字母)'
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
	//给导入按钮绑定函数事件
	$("#btn-import-main").on('click', function() {
		selfShopDepositAccount.importExcel();
	});
	//给导出按钮绑定函数事件
	$("#btn-export").on('click', function() {
		selfShopDepositAccount.exportExcel();
	});

});

//查询
selfShopDepositAccount.search = function() {
	var params = $('#mainForm').form('getData');
	var url = BasePath + '/self_shop_deposit_account/depositAccountList.json';
	$('#depositAccountDataGrid').datagrid('options').queryParams = params;
	$('#depositAccountDataGrid').datagrid('options').url = url;
	$('#depositAccountDataGrid').datagrid('load');
};

//新增
selfShopDepositAccount.add = function() {

	var paramTemp = {
		shopNo : 'shopNo',
		shopName : 'shortName',
		companyNo : 'companyNo',
		companyName : 'companyName',
		mallNo : 'mallNo',
		mallName : 'mallName',
		depositAccount : 'depositAccount',
		bank : 'bank'
	};

	$("#depositAccountDataGrid").datagrid("removeEditor", "shopName");
	$("#depositAccountDataGrid").datagrid(
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
							selfShopDepositAccount.selectorShopCallBack(row,
									paramTemp);
						}
					}
				}
			});

	if (selfShopDepositAccount.endEdit()) {

		$('#depositAccountDataGrid').datagrid('insertRow', {
			index : 0,
			row : {}
		});
		$('#depositAccountDataGrid').datagrid('beginEdit', 0);
		selfShopDepositAccount.editRowIndex = 0;

	}

};
//删除
selfShopDepositAccount.del = function() {
	var delFlag = false;
	var checkedRows = $('#depositAccountDataGrid').datagrid('getChecked');

	if (checkedRows.length == 0) {
		showWarn('请选择要删除的行..');
		return;
	}
	var delFlag = true;
	$.each(checkedRows, function(index, row) {
		if(row.status==0){
			delFlag = false;
			return;
		}
	});
	
	if(!delFlag){
		showWarn("启用状态存现账号不能删除!");
		return;
	}

	var rowIndex;
	$.each(checkedRows, function(index, row) {
		rowIndex = $('#depositAccountDataGrid').datagrid('getRowIndex', row);
		$('#depositAccountDataGrid').datagrid('deleteRow', rowIndex);
		if (selfShopDepositAccount.editRowIndex == rowIndex) {
			selfShopDepositAccount.editRowIndex = -1;
		}
	});
};

//编辑
selfShopDepositAccount.edit = function(rowIndex, data) {
	if (selfShopDepositAccount.endEdit()) {
		//验证店铺编码+店铺存现账号唯一性
		checkDepositAccount();
		$('#depositAccountDataGrid').datagrid('beginEdit', rowIndex);
		selfShopDepositAccount.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
};

//保存
selfShopDepositAccount.save = function() {

	if (selfShopDepositAccount.endEdit()) {
		if (!selfShopDepositAccount.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/self_shop_deposit_account/save';
		var insertRows = $('#depositAccountDataGrid').datagrid('getChanges',
				'inserted');
		var updatedRows = $('#depositAccountDataGrid').datagrid('getChanges',
				'updated');
		var deletedRows = $('#depositAccountDataGrid').datagrid('getChanges',
				'deleted');
		var changeRows = {
			inserted : JSON.stringify(insertRows),
			updated : JSON.stringify(updatedRows),
			deleted : JSON.stringify(deletedRows)
		};

		if (insertRows.length > 0) {
			var reParam = {
				shopNo : insertRows[0].shopNo
			};
		}
		//验证店铺编码+店铺存现账号唯一性
		if(!checkDepositAccount()){
			selfShopDepositAccount.saveToDB(url, changeRows);
		}
		
	}

};

//清空参数
//clear the search form
selfShopDepositAccount.clear = function() {
	$('#mainForm').form("clear");
	$('#mainForm').find("input[type='hidden']").val('');
	$(':input', '#mainForm').not(':button, :submit, :reset').val('')
			.removeAttr('checked').removeAttr('selected');
};

//导入
selfShopDepositAccount.importExcel = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/self_shop_deposit_account/do_import',
		'templateName' : '店铺存现账号导入.xlsx',
		success : function(data) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data.error)) {
					showError(data.error);
				} else {
					$.importExcel.colse();
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
selfShopDepositAccount.exportExcel = function() {
	var $dg = $("#depositAccountDataGrid");
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
	var url = BasePath + "/self_shop_deposit_account/do_fas_export";
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
				param.fileName = "店铺存现账号导出";
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

//批量进行确认或反确认操作
selfShopDepositAccount.batchOperate = function(status){
	var checkedRows = $('#depositAccountDataGrid').datagrid('getChecked');
	if(checkedRows.length == 0){
		showWarn("请选择操作的记录");
		return ;
	}
	var message = "";
	for(var i=0;i<checkedRows.length;i++){
		var row=checkedRows[i];
		if(status==row.status&&status==0){
			showWarn("不能启用已启用记录!!");
			return ;
		}else if(status==row.status&&status==1){
			showWarn("不能停用已停用记录!!");
			return ;
		}
	}
	$.messager.confirm("确认","确认"+(status == 0 ? "启用":"停用")+"选中记录吗?",function(r) {
		if (r) {
			$.each(checkedRows, function(index, row) {
				row.status = status;
			});
			var data = {
				updated : JSON.stringify(checkedRows),
			};
			ajaxRequestAsync(BasePath + '/self_shop_deposit_account/save',data,function(result){
				if(result){
					showSuc('操作成功');
					$('#depositAccountDataGrid').datagrid('reload');
				}else{
					showError('操作失败');
				}
			});
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

selfShopDepositAccount.validationBeforeSave = function() {

	var flag = true;

	var insertRows = $('#depositAccountDataGrid').datagrid('getChanges',
			'inserted');
	var updatedRows = $('#depositAccountDataGrid').datagrid('getChanges',
			'updated');

	var dataRows;
	if (insertRows.length > 0 || updatedRows.length > 0) {
		if (insertRows.length > 0) {
			dataRows = insertRows;
		} else {
			dataRows = updatedRows;
		}

		$.each(dataRows, function(i, row) {
			if (row.companyNo == null || row.companyNo == "") {
				flag = false;
				showWarn('当前添加的数据中,公司数据不完整,请完善后 重试.');
			}
			if (!flag) {
				$('#depositAccountDataGrid').datagrid('beginEdit',
						selfShopDepositAccount.editRowIndex);
				selfShopDepositAccount.editRowIndex = $('#depositAccountDataGrid')
						.datagrid('getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

selfShopDepositAccount.saveToDB = function(url, changeRows) {
	selfShopDepositAccount.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
			selfShopDepositAccount.search();
		} else {
			showSuc('保存失败');
		}
	});
};

function checkDepositAccount(){
	selfShopDepositAccount.endEdit();

	var flag = false;
 	var url = BasePath + '/self_shop_deposit_account/check_deposit_account';
 	
 	var rows = $('#depositAccountDataGrid').datagrid('getChanges');
 	if(rows.length < 1){
 		return false;
 	}
 	
    var insertRows = $('#depositAccountDataGrid').datagrid('getChanges','inserted');
    var updatedRows = $('#depositAccountDataGrid').datagrid('getChanges','updated');
    var changeRows = {
    		inserted : JSON.stringify(insertRows),
    		updated : JSON.stringify(updatedRows)
    };

	if(insertRows.length == 0 && updatedRows.length == 0){
	   return false;
	}
	
    ajaxRequestAsync(url, changeRows, function(result){
    	if(typeof result.error == 'undefined' || result.error == null){
    		
    	}else{
    		flag = true;
    		alert(result.error);
    	}
    });
    return flag;
}
//common ajax request 
selfShopDepositAccount.commonAjaxRequest = function(url, reqParam, callback) {
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

//end the current edit row
selfShopDepositAccount.endEdit = function() {
	if ($('#depositAccountDataGrid').datagrid('validateRow',
			selfShopDepositAccount.editRowIndex)) {
		$('#depositAccountDataGrid').datagrid('endEdit',
				selfShopDepositAccount.editRowIndex);
		return true;
	}
	return false;
};

selfShopDepositAccount.selectShop = {// 明细行选择选择门店
	validatebox : {
		options : {
			required : true
		}
	},
	clickFn : function() {
		dgSelector({
			width : 580,
			height : 450,
			title : "选择门店",
			href : BasePath + "/common_util/toSearchShop",
			fn : function(data, rowIndex) {
				var paramTemp = {
					shopNo : 'shopNo',
					shopName : 'shortName',
					companyNo : 'companyNo',
					companyName : 'companyName',
					mallNo : 'mallNo',
					mallName : 'mallName',
					depositAccount : 'depositAccount',
					bank : 'bank'
				};
				selfShopDepositAccount.selectorShopCallBack(data, paramTemp);
			}
		});
	}
};

selfShopDepositAccount.selectorShopCallBack = function(data, paramTemp) {

	selfShopDepositAccount.clearinitlalizationCompanyAndMall();

	var valueEd = $('#depositAccountDataGrid').datagrid('getEditor', {
		'index' : selfShopDepositAccount.editRowIndex,
		'field' : paramTemp.shopNo
	});

	var textEd = $('#depositAccountDataGrid').datagrid('getEditor', {
		'index' : selfShopDepositAccount.editRowIndex,
		'field' : 'shopName'
	});
	valueEd.target.val(data[paramTemp.shopNo]);
	textEd.target.val(data[paramTemp.shopName]);

	var url = BasePath + "/shop/initSubInfo";
	var reqParam = {
		shopNo : data[paramTemp.shopNo]
	};

	selfShopDepositAccount.commonAjaxRequest(url, reqParam, function(result) {

		if (typeof result == 'undefined' || result == null) {
			showWarn('店铺信息不完整,请配置..');
			return;
		}

		if (typeof result.companyNo == 'undefined' || result.companyNo == null
				|| result.companyNo == "") {
			showWarn('当前店铺的公司信息不完整,请完善后重试');
			return;
		} else {
			var valueEd = $('#depositAccountDataGrid').datagrid('getEditor', {
				'index' : selfShopDepositAccount.editRowIndex,
				'field' : 'companyNo'
			});

			var textEd = $('#depositAccountDataGrid').datagrid('getEditor', {
				'index' : selfShopDepositAccount.editRowIndex,
				'field' : 'companyName'
			});
			valueEd.target.val(result.companyNo);
			textEd.target.val(result.companyName);
		}

		var valueEd = $('#depositAccountDataGrid').datagrid('getEditor', {
			'index' : selfShopDepositAccount.editRowIndex,
			'field' : 'mallNo'
		});

		var textEd = $('#depositAccountDataGrid').datagrid('getEditor', {
			'index' : selfShopDepositAccount.editRowIndex,
			'field' : 'mallName'
		});
		valueEd.target.val(result.mallNo);
		textEd.target.val(result.mallName);

	});
};

selfShopDepositAccount.clearinitlalizationCompanyAndMall = function() {

	var valueEd = $('#depositAccountDataGrid').datagrid('getEditor', {
		'index' : selfShopDepositAccount.editRowIndex,
		'field' : 'companyNo'
	});

	var textEd = $('#depositAccountDataGrid').datagrid('getEditor', {
		'index' : selfShopDepositAccount.editRowIndex,
		'field' : 'companyName'
	});
	valueEd.target.val(null);
	textEd.target.val(null);

	var valueEd = $('#depositAccountDataGrid').datagrid('getEditor', {
		'index' : selfShopDepositAccount.editRowIndex,
		'field' : 'mallNo'
	});

	var textEd = $('#depositAccountDataGrid').datagrid('getEditor', {
		'index' : selfShopDepositAccount.editRowIndex,
		'field' : 'mallName'
	});
	valueEd.target.val(null);
	textEd.target.val(null);
};
