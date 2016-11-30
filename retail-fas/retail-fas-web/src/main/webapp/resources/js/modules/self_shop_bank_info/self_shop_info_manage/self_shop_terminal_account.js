/**
 * 店铺存现账号设置
 */
function shopDepositAccount() {
};
shopDepositAccount.prototype = new areaDetail();
var depositAccount = new shopDepositAccount();

$(function() {
	depositAccount.init({
		formId : 'mainForm',
		dataGridId : 'terminalAccountDataGrid',
		queryUrl : '/self_shop_deposit_account/depositAccountList.json',
		exportUrl : '',
		excelTitle : '店铺存现账号设置'
	});
	toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'), 
        collapsible:true 
	});
	
	terminalAccount.addMainTab();
});


depositAccount.clear = function() {
	$("#mainForm").form("clear");
};

/**
 * 店铺终端账号设置
 */
function shopTerminalAccount() {
};
shopTerminalAccount.prototype = new areaDetail();
var terminalAccount = new shopTerminalAccount();

terminalAccount.addMainTab = function() {
	$('#mainTab').tabs('add',
		{
			title : '店铺终端账号设置',
			selected : false,
			closable : false,
			href : BasePath + '/self_shop_terminal_account/list_tabMain.htm',
			onLoad : function(panel) {
				//初始化属性
				terminalAccount.init({
					formId : 'terminalForm',
					dataGridId : 'terminalAccountDataGrid',
					queryUrl : '/self_shop_terminal_account/terminalAccountList.json',
					exportUrl : '',
					excelTitle : '店铺终端账号设置'
				});
				toolSearch({
			        appendTo:$('#terminalToolbar'), 
			        target:$('#terminalSubLayout'), 
			        collapsible:true 
				});
				$("#shopName1").filterbuilder({
			        type:'organ',
			        organFlag: 2,
			        roleType:'bizCity', 
			        onSave : function(result) { 
			        	var value = $(this).filterbuilder('getValue');
			        	$("#shopNo1").val(value);
			        }
			    });
			}
		}
	);
};

terminalAccount.clear = function() {
	$("#terminalForm").form("clear");
	$('#terminalForm').find("input[type='hidden']").val('');
	$(':input', '#terminalForm').not(':button, :submit, :reset').val('')
			.removeAttr('checked').removeAttr('selected');
};

//查询
terminalAccount.searchData = function() {
	var params = $('#terminalForm').form('getData');
	var url = BasePath + '/self_shop_terminal_account/terminalAccountList.json';
	$('#terminalAccountDataGrid').datagrid('options').queryParams = params;
	$('#terminalAccountDataGrid').datagrid('options').url = url;
	$('#terminalAccountDataGrid').datagrid('load');
};

//新增
terminalAccount.add = function() {

	var paramTemp = {
		shopNo : 'shopNo',
		shopName : 'shortName',
		companyNo : 'companyNo',
		companyName : 'companyName',
		mallNo : 'mallNo',
		mallName : 'mallName',
		terminalNumber : 'terminalNumber',
		creditCardBank : 'creditCardBank',
		creditCardAccount : 'creditCardAccount'
	};

	$("#terminalAccountDataGrid").datagrid("removeEditor", "shopName");
	$("#terminalAccountDataGrid").datagrid(
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
							terminalAccount.selectorShopCallBack(row,
									paramTemp);
						}
					}
				}
			});

	if (terminalAccount.endEdit()) {

		$('#terminalAccountDataGrid').datagrid('insertRow', {
			index : 0,
			row : {}
		});
		$('#terminalAccountDataGrid').datagrid('beginEdit', 0);
		terminalAccount.editRowIndex = 0;
	}
};

//删除
terminalAccount.del = function() {
	var delFlag = false;
	var checkedRows = $('#terminalAccountDataGrid').datagrid('getChecked');

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
		showWarn("启用状态终端账号不能删除!");
		return;
	}

	var rowIndex;
	$.each(checkedRows, function(index, row) {
		rowIndex = $('#terminalAccountDataGrid').datagrid('getRowIndex', row);
		$('#terminalAccountDataGrid').datagrid('deleteRow', rowIndex);
		if (terminalAccount.editRowIndex == rowIndex) {
			terminalAccount.editRowIndex = -1;
		}
	});
}

//编辑
terminalAccount.edit = function(rowIndex, data) {
	if (terminalAccount.endEdit()) {
		checkTerminalAccount();
		$('#terminalAccountDataGrid').datagrid('beginEdit', rowIndex);
		terminalAccount.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
}

//保存
terminalAccount.save = function() {
	if (terminalAccount.endEdit()) {
		if (!terminalAccount.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/self_shop_terminal_account/save';
		var insertRows = $('#terminalAccountDataGrid').datagrid('getChanges', 'inserted');
		var updatedRows = $('#terminalAccountDataGrid').datagrid('getChanges', 'updated');
		var deletedRows = $('#terminalAccountDataGrid').datagrid('getChanges', 'deleted');
		var updatedRowsForColumn = $('#terminalAccountDataGrid').datagrid('getChangesForColumn', 'column');
		
		var changeRows = {
			inserted : JSON.stringify(insertRows),
			updated : JSON.stringify(updatedRows),
			deleted : JSON.stringify(deletedRows)
		};
		
		var changeRowsForRate = {
			inserted : JSON.stringify(insertRows),
			updated : JSON.stringify(updatedRowsForColumn),
			deleted : JSON.stringify(deletedRows)	
		}

		if (insertRows.length > 0) {
			var reParam = {
				shopNo : insertRows[0].shopNo
			};
		}
		//验证店铺编码+店铺终端账号唯一性
		if(!checkTerminalAccount()){
			terminalAccount.saveToDB(url, changeRows);
			terminalAccount.saveToDB(BasePath + '/self_shop_terminal_account/update_poundage', changeRowsForRate);
		}
	}

};

//导入
terminalAccount.importExcel =  function(){
	$.importExcel.open({
		'submitUrl' : BasePath + '/self_shop_terminal_account/do_import',
		'templateName' : '店铺终端账号导入.xlsx',
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
terminalAccount.exportExcel = function(){
	var $dg = $("#terminalAccountDataGrid");
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
	var url = BasePath + "/self_shop_terminal_account/do_fas_export";
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
				param.fileName = "店铺终端账号导出";
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
}

//批量进行启用或停用操作
terminalAccount.batchOperate = function(status){
	var checkedRows = $('#terminalAccountDataGrid').datagrid('getChecked');
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
			ajaxRequestAsync(BasePath + '/self_shop_terminal_account/save',data,function(result){
				if(result){
					showSuc('操作成功');
					$('#terminalAccountDataGrid').datagrid('reload');
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

terminalAccount.selectorShopCallBack = function(data, paramTemp) {

	terminalAccount.clearinitlalizationCompanyAndMall();

	var valueEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
		'index' : terminalAccount.editRowIndex,
		'field' : paramTemp.shopNo
	});

	var textEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
		'index' : terminalAccount.editRowIndex,
		'field' : 'shopName'
	});
	valueEd.target.val(data[paramTemp.shopNo]);
	textEd.target.val(data[paramTemp.shopName]);

	var url = BasePath + "/shop/initSubInfo";
	var reqParam = {
		shopNo : data[paramTemp.shopNo]
	};

	terminalAccount.commonAjaxRequest(url, reqParam, function(result) {

		if (typeof result == 'undefined' || result == null) {
			showWarn('店铺信息不完整,请配置..');
			return;
		}

		if (typeof result.companyNo == 'undefined' || result.companyNo == null
				|| result.companyNo == "") {
			showWarn('当前店铺的公司信息不完整,请完善后重试');
			return;
		} else {
			var valueEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
				'index' : terminalAccount.editRowIndex,
				'field' : 'companyNo'
			});

			var textEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
				'index' : terminalAccount.editRowIndex,
				'field' : 'companyName'
			});
			valueEd.target.val(result.companyNo);
			textEd.target.val(result.companyName);
		}

		var valueEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
			'index' : terminalAccount.editRowIndex,
			'field' : 'mallNo'
		});

		var textEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
			'index' : terminalAccount.editRowIndex,
			'field' : 'mallName'
		});
		valueEd.target.val(result.mallNo);
		textEd.target.val(result.mallName);

	});
};

//end the current edit row
terminalAccount.endEdit = function() {
	if ($('#terminalAccountDataGrid').datagrid('validateRow',
			terminalAccount.editRowIndex)) {
		$('#terminalAccountDataGrid').datagrid('endEdit',
				terminalAccount.editRowIndex);
		return true;
	}
	return false;
};

terminalAccount.clearinitlalizationCompanyAndMall = function() {

	var valueEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
		'index' : terminalAccount.editRowIndex,
		'field' : 'companyNo'
	});

	var textEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
		'index' : terminalAccount.editRowIndex,
		'field' : 'companyName'
	});
	valueEd.target.val(null);
	textEd.target.val(null);

	var valueEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
		'index' : terminalAccount.editRowIndex,
		'field' : 'mallNo'
	});

	var textEd = $('#terminalAccountDataGrid').datagrid('getEditor', {
		'index' : terminalAccount.editRowIndex,
		'field' : 'mallName'
	});
	valueEd.target.val(null);
	textEd.target.val(null);
};

terminalAccount.validationBeforeSave = function() {

	var flag = true;

	var insertRows = $('#terminalAccountDataGrid').datagrid('getChanges',
			'inserted');
	var updatedRows = $('#terminalAccountDataGrid').datagrid('getChanges',
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
				$('#terminalAccountDataGrid').datagrid('beginEdit',
						selfShopBankInfo.editRowIndex);
				selfShopBankInfo.editRowIndex = $('#terminalAccountDataGrid')
						.datagrid('getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

terminalAccount.saveToDB = function(url, changeRows) {
	terminalAccount.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
			terminalAccount.searchData();
		} else {
			showSuc('保存失败');
		}
	});
};

function checkTerminalAccount(){
	terminalAccount.endEdit();

	var flag = false;
 	var url = BasePath + '/self_shop_terminal_account/check_terminal_account';
 	
 	var rows = $('#terminalAccountDataGrid').datagrid('getChanges');
 	if(rows.length < 1){
 		return false;
 	}
 	
    var insertRows = $('#terminalAccountDataGrid').datagrid('getChanges','inserted');
    var updatedRows = $('#terminalAccountDataGrid').datagrid('getChanges','updated');
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
terminalAccount.commonAjaxRequest = function(url, reqParam, callback) {
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

/**
 * 扩展方法，修改费率，获取该费率所在row
 */
$.extend($.fn.datagrid.methods, {
	getChangesForColumn:function(jq,type){
		var originalRows = $.data(jq[0], "datagrid").originalRows;//获取修改前的数据
		var updatedRows = $.data(jq[0], "datagrid").updatedRows;//修改的所有行
		if (type == "column") {
			var rows = [];
			for(var i=0;i<updatedRows.length;i++){
				var id = updatedRows[i].id;
				for(var j=0;j<originalRows.length;j++){
					if(updatedRows[i].id==originalRows[j].id && updatedRows[i].creditCardRate!=originalRows[j].creditCardRate){
						rows.push(updatedRows[i]);
					}
				}
			}
			return rows;
		}
		return [];
	}
});
