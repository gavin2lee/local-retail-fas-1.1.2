var currency_management = {};
//币种路径
currency_management.currencyModulePath=BasePath+ '/base_setting/currency_management/';
//汇率路径
currency_management.exchangeModulePath=BasePath+'/base_setting/exchange_rate_setup/';

$(function() {
	currency_management.addMainTab();
});

// 初始化汇率设置
currency_management.addMainTab = function() {
	$('#mainTab').tabs('add',
		{
			title : '汇率设置',
			selected : false,
			closable : false,
			href : currency_management.currencyModulePath+'list_exchange_rate.htm',
			onLoad : function(panel) {
				currency_management.initSearchCompany();
				currency_management.initSearchSourceCurrency();
				currency_management.initSearchTargetCurrency();
			}
	});
};

var exchangeSetting ={
		dataFormId:"exchangeForm",
		saveUrl:"/base_setting/exchange_rate_setup/insert",
		updateUrl:"/base_setting/exchange_rate_setup/update",
		delUrl:"/base_setting/exchange_rate_setup/save",
	    enableUrl:"/base_setting/exchange_rate_setup/do_enable",
	    unableUrl:"/base_setting/exchange_rate_setup/do_unable",
		datagridId:"exchangeDG",
		dialogWidth:580,
		dialogHeight:220,
		primaryKey:"id"
		
};

//汇率查询
currency_management.searchExchange=function(){
	var params = $('#searchForm').form('getData');
	var url = currency_management.exchangeModulePath + '/list.json';
	$('#exchangeDG').datagrid('options').queryParams = params;
	$('#exchangeDG').datagrid('options').url = url;
	$('#exchangeDG').datagrid('load');
};

//清空
currency_management.clearExchange=function(){
	$('#searchForm').form('clear');
	$('#companyNo').val("");
};

//汇率新增
currency_management.addExchange=function(){
	ygDialog({
		title : '新增',
		target : $('#formPanel'),
		width : exchangeSetting.dialogWidth,
		height : exchangeSetting.dialogHeight,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				currency_management.doSave(exchangeSetting);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				$("#exchangeForm").form('clear');
				$("#conversionFactorId").numberbox("clear");
				dialog.close();
			}
		}]
	});
};

//保存
currency_management.doSave=function(exchangeSetting){
	var fromObj = $('#' + exchangeSetting.dataFormId);
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	//验证编码是否重复
	if(!checkSave()){
		return;
	}
	
	var url = BasePath + exchangeSetting.saveUrl;
	fromObj.form('submit',
	{
		url : url,
		dataType : 'json',
		onSubmit : function(params) {
		},
		success : function(result) {
			if (result && (JSON.parse(result).success || typeof JSON.parse(result).success == 'undefined')) {
				showSuc('新增成功!');
				$("#formPanel").window('close');
				$("#exchangeId").attr("value", "");
				$("#conversionFactorId").numberbox("clear");
				$("#exchangeForm").form('clear');
				currency_management.searchExchange();
			} else {
				showError('新增失败,请联系管理员!');
			}
		},
		error : function() {
			showError('新增失败,请联系管理员!');
		}
	});
};

//进入修改页面的方法
currency_management.showEditDialog = function() {
	var checkedRows = $("#" + exchangeSetting.datagridId).datagrid("getChecked");
	if(checkedRows.length < 1) {
		showWarn('请选择要修改的记录!');
		return;
	} else {
		if (checkedRows.length > 1) {
			showWarn("只能对一条记录进行修改！");
			return;
		} else {
			if (checkedRows[0].status == '1') {
				showWarn("包含启用记录，不能修改！");
				return;
			} else {
				currency_management.editDialog(checkedRows[0], exchangeSetting);
			}
		}
	}
};

//修改
currency_management.editDialog=function(rowData,exchangeSetting){
	$('#' + exchangeSetting.dataFormId).form('load', rowData);
	//编码只读
	$("#exchangeRateCodeId").attr("readonly", true).addClass("disabled");
	
	ygDialog({
		title : '修改',
		target : $('#formPanel'),
		width : exchangeSetting.dialogWidth,
		height : exchangeSetting.dialogHeight,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				currency_management.doUpdate(exchangeSetting);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
				$("#exchangeForm").form('clear');
				$("#exchangeRateCodeId").removeAttr("readonly").removeClass("disabled");
				$("#conversionFactorId").numberbox("clear");
			}
		} ]
	});
};

//修改保存
currency_management.doUpdate = function(exchangeSetting) {
	var fromObj = $('#' + exchangeSetting.dataFormId);
	// 1.校验必填项
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	if(!checkUpdate()){
		return;
	}
	// 3. 保存
	var url = BasePath + exchangeSetting.updateUrl;
	fromObj.form('submit',
			{
				url : url,
				dataType : 'json',
				onSubmit : function(params) {
				},
				success : function(result) {
					if (result
							&& (JSON.parse(result).success || typeof JSON
									.parse(result).success == 'undefined')) {
						showSuc('修改成功!');
						$("#formPanel").window('close');
						$("#exchangeForm").form('clear');
						$("#exchangeRateCodeId").removeAttr("readonly").removeClass("disabled");
						$("#exchangeId").attr("value", "");
						currency_management.searchExchange();
						$("#conversionFactorId").numberbox("clear");
					} else {
						showError("修改失败,请联系管理员!");
					}
				},
				error : function() {
					showError("修改失败,请联系管理员!");
				}
			});
};

//删除
currency_management.delExchange=function(){
	var checkedRows = $("#" + exchangeSetting.datagridId).datagrid("getChecked");
	if (checkedRows.length < 1) {
		showWarn("请选择要删除的记录!");
		return;
	} 
	for(var i = 0; i < checkedRows.length; i++) {
		if(checkedRows[i].status == '1'){
			 showWarn("包含启用记录，不能删除！");
			 return;
		 }
	}
	currency_management.deleteFn(exchangeSetting, checkedRows);
};

currency_management.deleteFn=function(exchangeSetting, checkedRows){
	var primaryKey = exchangeSetting.primaryKey;
	var deleteLen = "";
	if (typeof checkedRows != "undefined" && checkedRows != null) {
		deleteLen = checkedRows.length;
	}
	$.messager.confirm("确认", "你确定要删除这" + deleteLen + "条数据", function(r) {
		if (r) {
			var deleteList = [];
			$.each(checkedRows, function(index, item) {
				var obj = new Object();
				obj[primaryKey] = item[primaryKey];
				deleteList.push(obj);
			});
			if (deleteList.length > 0) {
				var url = BasePath + exchangeSetting.delUrl;
				var effectRow = new Object();
				effectRow["deleted"] = JSON.stringify(deleteList);
				fas_common.ajaxRequest(url, effectRow, function(result) {
					if (result) {
						showSuc("删除成功!");
						currency_management.searchExchange();
						$("#exchangeForm").form('clear');
					} else {
						showError(operater_name + "失败,请联系管理员!");
					}
				});
			} else {
				showError(operater_name + "失败!");
			}
		}
	});  
};

//启用
currency_management.doEnable=function(){
	var primaryKey = exchangeSetting.primaryKey;
	var checkedRows = $("#" + exchangeSetting.datagridId).datagrid("getChecked");
	if(checkedRows.length < 1){
		showWarn("请选择要启用的记录!");
		return;
	}else{
		var errMessage = "";
		$.each(checkedRows, function(index, item) {
			if (item.status == 1) {
				errMessage = '包含启用记录，不能重复操作！';
				return false;
			}
		});
		if (errMessage != "") {
			showWarn(errMessage);
			return;
		}
		
		$.messager.confirm("确认","你确定要启用这"+checkedRows.length+"条数据", function (r){  
	        if(r) {
				var enableList = [];
				$.each(checkedRows, function(index, item) {
					var obj = new Object();
					obj[primaryKey] = item[primaryKey];
					enableList.push(obj);
				});     
	        	
	        	if(enableList.length > 0) {
	        		var url = BasePath + exchangeSetting.enableUrl;
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(enableList);
		            fas_common.ajaxRequest(url, effectRow, function(result){
		        		 if(result){
		        			 showSuc("操作成功!");
		        			 currency_management.searchExchange();
		        		 }else{
		        			 showError("操作失败,请联系管理员!");
		        		 }
		        	}); 
	        	} else{
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	}
};

//停用
currency_management.doUnable=function(){
	var primaryKey = exchangeSetting.primaryKey;
	var checkedRows = $("#" + exchangeSetting.datagridId).datagrid("getChecked");
	if(checkedRows.length < 1){
		showWarn("请选择要停用的记录!");
		return;
	}else{
		var errMessage = "";
		$.each(checkedRows, function(index, item) {
			if (item.status == 0) {
				errMessage = '包含停用记录，不能重复操作！';
				return false;
			}
		});
		if (errMessage != "") {
			showWarn(errMessage);
			return;
		}
		
		$.messager.confirm("确认","你确定要停用这"+checkedRows.length+"条数据", function (r){  
	        if(r) {
				var enableList = [];
				$.each(checkedRows, function(index, item) {
					var obj = new Object();
					obj[primaryKey] = item[primaryKey];
					enableList.push(obj);
				});     
	        	
	        	if(enableList.length > 0) {
	        		var url = BasePath + exchangeSetting.unableUrl;
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(enableList);
		            fas_common.ajaxRequest(url, effectRow, function(result){
		        		 if(result){
		        			 showSuc("操作成功!");
		        			 currency_management.searchExchange();
		        		 }else{
		        			 showError("操作失败,请联系管理员!");
		        		 }
		        	}); 
	        	} else{
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	}
};


/**
 * 初始化搜索公司
 */
currency_management.initSearchCompany = function() {
	$("#companyNameId").iptSearch({
		disabled : false,
		clickFn : function() {
			dgSelector({
				title : '选择结算公司',
				href : BasePath + '/base_setting/company/toSearchCompany',
				width : 600,
				height : 500,
				fn : function(data) {
					$("#companyNoId").val(data.companyNo);
					$("#companyNameId").val(data.name);
				}
			});
		}
	});
};

/**
 * 初始化选择源货币
 */
currency_management.initSearchSourceCurrency = function() {
	$("#sourceCurrencyNameId").iptSearch(
	{
		disabled : false,
		clickFn : function() {
			dgSelector({
				title : '选择源货币',
				href : BasePath
						+ '/base_setting/currency_management/toSearchCurrency',
				width : 600,
				height : 500,
				fn : function(data) {
					$("#sourceCurrencyId").val(
							data.currencyCode);
					$("#sourceCurrencyNameId").val(
							data.currencyName);
				}
			});
		}
	});
};

/**
 * 初始化选择目标货币
 */
currency_management.initSearchTargetCurrency = function() {
	$("#targetCurrencyNameId").iptSearch(
	{
		disabled : false,
		clickFn : function() {
			dgSelector({
				title : '选择目标货币',
				href : BasePath
						+ '/base_setting/currency_management/toSearchCurrency',
				width : 600,
				height : 500,
				fn : function(data) {
					$("#targetCurrencyId").val(
							data.currencyCode);
					$("#targetCurrencyNameId").val(
							data.currencyName);
				}
			});
		}
	});
};


//验证汇率编码是否重复
checkSave = function() {
	var repeatFlag = false;
	var $codeId = $("#exchangeRateCodeId").val();
	var $source = $("#sourceCurrencyId").val();
	var $target = $("#targetCurrencyId").val();
	var $effectiveDate = $("#effectiveDateId").val();
	var $companyNo = $("#companyNoId").val();
	$.ajax({
		type : "POST",
		url : BasePath + "/base_setting/exchange_rate_setup/check_Repect",
		dataType : 'json',
		async : false,
		data : {
			exchangeRateCode : $codeId,
			sourceCurrency : $source,
			targetCurrency : $target,
			effectiveDate : $effectiveDate,
			companyNo : $companyNo
		},
		success : function(msg) {
			if (msg == true) {
				repeatFlag = true;
			}
		}
	});
	// 如果存在，则提示
	if (repeatFlag) {
		showWarn('编码重复或者已经存在源货币、目标货币、生效日期、公司名称一样的数据，请重新输入!');
	}
	return !repeatFlag;
};

//验证汇率编码是否重复
checkUpdate = function() {
	var repeatFlag = false;
	var $id = $("#exchangeId").val();
	var $source = $("#sourceCurrencyId").val();
	var $target = $("#targetCurrencyId").val();
	var $effectiveDate = $("#effectiveDateId").val();
	var $companyNo = $("#companyNoId").val();
	$.ajax({
		type : "POST",
		url : BasePath + "/base_setting/exchange_rate_setup/check_Repect",
		dataType : 'json',
		async : false,
		data : {
			id:$id,
			sourceCurrency : $source,
			targetCurrency : $target,
			effectiveDate : $effectiveDate,
			companyNo : $companyNo
		},
		success : function(msg) {
			if (msg == true) {
				repeatFlag = true;
			}
		}
	});
	// 如果存在，则提示
	if (repeatFlag) {
		showWarn('已经存在源货币、目标货币、生效日期、公司名称一样的数据，请重新输入!');
	}
	return !repeatFlag;
};

/**
 * 验证编码是否重复
 */
var extra_operate = {
	// 新增初始化
	initAdd : function() {
		$("#currencyCodeId").removeAttr("readonly").removeClass("disabled");
	},
	
	checkSave : function() {
		var repeatFlag = false;
		var msg="";
		var $codeId = $("#currencyCodeId").val();
		var $name = $("#currencyNameId").val();
		var $standardMoney=$("input[name='standardMoney']:checked").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/currency_management/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				currencyCode : $codeId,
				currencyName : $name,
				standardMoney: $standardMoney
			},
			success : function(data) {
				if (data.flag == true) {
					repeatFlag = true;
					msg=data.msg;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn(msg);
		}
		return !repeatFlag;
	},
	
	// 修改初始化
	initUpdate : function(rowData) {
	},

	// 修改之前的逻辑处理
	loadedUpdate : function(rowData) {
		$("#currencyCodeId").attr("readonly", true).addClass("disabled");
	},

	//修改验证
	checkUpdate:function(){
		var repeatFlag = false;
		var $id = $("#id").val();
		var $name = $("#currencyNameId").val();
		var $standardMoney=$("input[name='standardMoney']:checked").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/currency_management/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				id : $id,
				currencyName : $name,
				standardMoney: $standardMoney
			},
			success : function(data) {
				if (data.flag == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('币种名称或本位币重复，请重新输入!');
		}
		return !repeatFlag;
	},
	
	// 保存成功之后
	saveCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},
	
	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},

	// 删除之前的逻辑处理
	checkDel : function() {
		var delFlag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1'){
				 showWarn("包含启用记录，不能删除！");
				 return;
			 }
		}
		return !delFlag;
	},
	
	checkEnable:function(){
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1'){
				 showWarn("包含启用记录，不能重复操作！");
				 return;
			 }
		}
		return !Flag;
	},
	
	checkUnable:function(){
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '0'){
				 showWarn("包含停用记录，不能重复操作！");
				 return;
			 }
		}
		return !Flag;
	}
};
