/**
 *  预付款
 */
var expect_cash = {};
expect_cash.queryMxURL = BasePath + '/expect_cash/query';

function expectCashEditor() {
	var $this = this;
	
	$this.checkInsert = function(options){return false};
	$this.checkUpdate = function(options, rowIndex, rowData) {
		return rowData.balanceStatus == 1;
	};
}

// 查询操作
expect_cash.doSearch = function() {
	var validateForm = $("#searchForm").form('validate');
	if (!validateForm) {
		return;
	}
	clearAllValidateHintMsg();
	var $dg = $("#dataGridDiv");
	$dg.datagrid('options').queryParams = $("#searchForm").form('getData');
	$dg.datagrid('options').url = expect_cash.queryMxURL;
	$dg.datagrid('options').method = "get";
	$dg.datagrid('load');	
};
//保留2位小数
expect_cash.editAmount = function(){
	var amount =  $('#amount').val();
	amount = pointFormatter(Math.round(amount*100)/100);
	$('#amount').val(amount);
};
// 清空查询区域
expect_cash.doSearchClear = function() {
	$('#searchForm').form("clear");
	expect_cash.clearAll();
	$('#searchForm').find("input").val("");
	$('#searchForm').find("textarea").val("");
	$('input[type=checkbox]').removeAttr('checked');
};

// 弹出新增页面
expect_cash.showAdd = function() {
	expect_cash.clearAll();
	var $dg = $("#dataGridDiv");
	
	$('#searchForm').form("clear");
	$dg.datagrid('clearSelections');
	var formObj = $("#dataForm");
	formObj.form("enableValidation");

//	selectFromDic4Combox('#businessFlag', 'BUSINESS_FLAG',1);
//	selectFromDic4Combox('#businessType', 'BUSINESS_TYPE_EXPECT');
	$('#businessFlag').combobox('disable');
	$('#businessType').combobox('enable');
	$('#settleCode').attr('readonly',false);
	$('#businessName').attr('readonly',false);
	$('#customerName').attr('readonly',false);
	$('#contactName').attr('readonly',false);
	$('#tel').attr('readonly',false);
	$('#proName').combobox('enable');
	$('#brandName').combogrid('enable');
//	$('#categoryNo').combotree('enable');
	expect_cash.initCategoryNo();
	expect_cash.initExpectCash();
	expect_cash.initAssistant();
	
	$("#info_update").hide();
	$("#info_save").show();
	$('#showDialog').show();
	$('#showDialog').window('open');
};

//单击工具条的退款操作
expect_cash.showAddRefund = function() {
	expect_cash.clearAll();
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要退款的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能修改一行数据!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {
			if(2 == item.businessFlag){
				alert('类别为退款的单据不可以退款!');
				return;
			}
			expect_cash.showRefund(item);
		});
	}
};
//获取树的值
expect_cash.getCategoryData = function(){
	var statusTree = $('#categoryNo').combotree('tree');	// 获取树对象
	var nodes = statusTree.tree('getChecked');		// 获取选择的节点
//	var statusList = [];
//	if (null != nodes && nodes.length > 0) {
//		$.each(nodes, function(index, row) {
//			statusList.push(row.id);
//		});
//	}
//	return statusList.join(',');
	
};
//弹出修改页面,也可以双击直接进去
expect_cash.showRefund = function(rowData) {
	$('#searchForm').form("clear");
	// 已确认的记录无法修改
	if (rowData.confirmFlag != '1') {
		alert("未确认的数据不能进行退款!");
		return;
	}

	expect_cash.clearAll();	
	$('#dataForm').form("enableValidation");
//	selectFromDic4Combox('#businessFlag', 'BUSINESS_FLAG');
//	selectFromDic4Combox('#businessType', 'BUSINESS_TYPE_EXPECT');
	$('#businessType').combobox('disable');
	$('#businessFlag').combobox('disable');
	$('#settleCode').attr('readonly',true);
	$('#businessName').attr('readonly',true);
	$('#customerName').attr('readonly',true);
	$('#contactName').attr('readonly',true);
	$('#tel').attr('readonly',true);
	expect_cash.initAssistant();
	expect_cash.initCategoryNo();
	expect_cash.initExpectCash();
	$('#proName').combobox('disable');
	$('#brandName').combogrid('disable');
	$('#categoryNo').combotree('disable');
	// 设置标题
	$('#showDialog').window({
		title : "新增退款信息"
	});
	
	//金额为可使用金额
	rowData.amount = rowData.unUsedAmount;
	
	// 设置信息
	$('#dataForm').form('load', rowData);
	$('#amount').val(pointFormatter(rowData.amount));
	var businessType = $("#businessType").combobox("getValue");
	if ('2' == businessType) {
		$('#settleCode').validatebox({    
			required: false,
			validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
		});
		$('#span_settleCode').hide();
		$('#settleCode').removeAttr('required');
		$('#settleCode').validatebox('validate');
	} else {
		$('#settleCode').validatebox({    
			required: true,    
			validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
		});
		$('#settleCode').attr('required', true);
		$('#span_settleCode').show();
	}
	
	// 弹窗
	$("#info_update").hide();
	$("#info_save").show();
	$("#businessFlag").combobox("select",  2);
	
	$('#showDialog').show();
	$('#showDialog').window('open');
};

// 新增保存
expect_cash.add = function() {
	var fromObj = $('#dataForm');
	// 1.校验必填项
	var validateForm = fromObj.form('validate');
	if (!validateForm) {
		return;
	}
	var businessType = $("#businessType").combobox("getValue");
	if('' == businessType){
		alert("请选择新增信息的类别！");
		return;
	}
	
	var businessFlag = $("#businessFlag").combobox("getValue");
	var unUsedAmount = $("#unUsedAmount").val()*1;
	var amount =  $("#amount").val()*1;
	if(businessFlag == 2 && unUsedAmount < amount){
		alert("填写的退款金额不能大于未使用金额");
		$("#amount").val(pointFormatter(unUsedAmount));
		$("input[name=amount]").val(pointFormatter(unUsedAmount));
		$("input[name=amount]").focus();
		return;
	}

	// 2. 保存
	var url = BasePath + '/expect_cash';
	fromObj.form('submit', {
		url : url,
		onSubmit : function(param) {
		},
		success : function(returnMsg) {
			if (returnMsg == 'success') {
				alert('新增成功!');
				$('#showDialog').window('close');
				// 3.保存成功,清空表单
				expect_cash.doSearch();
				return;
			}else if(returnMsg == "false"){
				if(2 == businessFlag){
					alert('该单据已添加了退款单据');
				}else{
					alert('该凭证编码已存在，请重新输入!');
				}
			}else if(returnMsg == "true"){
				alert('填写的退款金额不能大于未使用金额');
			}else {
				printExceptionMsg(returnMsg, 2, '新增异常,请联系管理员!');
			}
		},
		error : function() {
			showWarn('新增失败,请联系管理员!', 2);
		}
	});

};

// 单击工具条的编辑操作
expect_cash.showEditButton = function() {
	expect_cash.clearAll();
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要修改的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能修改一行数据!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {
			expect_cash.showEdit(item);
		});
	}
};

// 弹出修改页面,也可以双击直接进去
expect_cash.showEdit = function(rowData) {
	$('#searchForm').form("clear");
	// 已确认的记录无法修改
	if (rowData.confirmFlag == '1' && rowData.businessFlag == '2') {
		alert("已确认的退款数据不能进行修改!");
		return;
	}
	if(rowData.unUsedAmount == 0){
		alert('未使用金额为0的预收款不能修改!');
		return;
	}
	expect_cash.clearAll();
	$('#dataForm').form("enableValidation");
//	selectFromDic4Combox('#businessFlag', 'BUSINESS_FLAG');
//	selectFromDic4Combox('#businessType', 'BUSINESS_TYPE_EXPECT');
	$('#businessFlag').combobox('disable');
	expect_cash.initCategoryNo();
	expect_cash.initExpectCash();
	expect_cash.initAssistant();
	// 设置标题
	$('#showDialog').window({
		title : "修改信息"
	});
	$("#info_update").show();
	$("#info_save").hide();
	// 设置信息
	$('#dataForm').form('load', rowData);
	$('#amount').val(pointFormatter(rowData.amount));
	$('#usedAmount').val(pointFormatter(rowData.amount - rowData.unUsedAmount));
	if(rowData.confirmFlag == '1'){
		$('#businessType').combobox('disable');
		$('#settleCode').attr('readonly',true);
		$('#customerName').attr('readonly',true);
		$('#contactName').attr('readonly',true);
		$('#tel').attr('readonly',true);
		$('#proName').combobox('disable');
		$('#brandName').combogrid('disable');
		$('#categoryNo').combotree('disable');
		if ('2' == rowData.businessType) {
			$('#span_settleCode').hide();
		} else {
			$('#span_settleCode').show();
		}
	}else{
		
		//设置品牌显示值
	//	if($('#brandName').val() != null && $('#brandNo').val() != null){
	//		$('#brandNameCombogrid').combogrid('textbox').val(rowData.brandNo+'-'+rowData.brandName);
	//	}
		if(rowData.businessFlag == '2'){
			$('#businessType').combobox('disable');
			$('#settleCode').attr('readonly',true);
			$('#customerName').attr('readonly',true);
			$('#contactName').attr('readonly',true);
			$('#tel').attr('readonly',true);
			$('#proName').combobox('disable');
			$('#brandName').combogrid('disable');
			$('#categoryNo').combotree('disable');
		}else{
			$('#businessType').combobox('enable');
			$('#settleCode').attr('readonly',false);
			$('#customerName').attr('readonly',false);
			$('#contactName').attr('readonly',false);
			$('#tel').attr('readonly',false);
		}
		
		var businessType = $("#businessType").combobox("getValue");
		if ('2' == businessType) {
			$('#settleCode').validatebox({    
				required: false,
				validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
			});
			$('#span_settleCode').hide();
			$('#settleCode').removeAttr('required');
			$('#settleCode').validatebox('validate');
		} else {
			$('#settleCode').validatebox({    
				required: true,    
				validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
			});
			$('#settleCode').attr('required', true);
			$('#span_settleCode').show();
		}
	}

	// 弹窗
	$('#showDialog').show();
	$("#showDialog").window('open');
};

// 修改
expect_cash.modify = function() {
	$('#searchForm').form("clear");
	var fromObj = $('#dataForm');
	// 1.校验必填项
	var validateForm = fromObj.form('validate');
	if (!validateForm) {
		return;
	}
	var unUsedAmount = $("#unUsedAmount").val()*1;
	var amount =  $("#amount").val()*1;
	var usedAmount = $('#usedAmount').val()*1;
	if(amount < usedAmount){
		alert("收款金额不能小于已使用金额");
		$("#amount").val(pointFormatter(usedAmount));
		$("input[name=amount]").val(pointFormatter(usedAmount));
		$("#amount").focus();
		return;
	}
	var url = BasePath + '/expect_cash';

	fromObj.form('submit', {
		url : url,
		onSubmit : function(param) {
			param._method = 'put';
		},
		success : function(returnMsg) {
			if (returnMsg == "success") {
				alert('修改成功!');
				$("#showDialog").window('close');
				expect_cash.doSearch();
				return;
			}else if(returnMsg == "false"){
				alert('该凭证编码已存在，请重新输入!');
			}else if(returnMsg == "true"){
				showWarn('退款金额不能大于未使用金额');
				$("#amount").val(pointFormatter(unUsedAmount));
				$("input[name=amount]").val(pointFormatter(unUsedAmount));
				$("#amount").focus();
			}else {
				printExceptionMsg(returnMsg, 2, '修改失败,请联系管理员!');
			}
		},
		error : function() {
			showWarn('修改失败,请联系管理员!', 2);
		}
	});
};

//确认
expect_cash.verify = function() {
//	editor.save();
//	$('#searchForm').form("clear");
	// 获取所有勾选checkbox的行
	var $dg1 = $("#dataGridDiv");
	var checkedRows = $dg1.datagrid("getChecked");

	if (checkedRows.length < 1) {
		alert('请选择需要确认的数据!');
		return;
	}

	for ( var index = 0; index < checkedRows.length; index++) {
		if (checkedRows[index].confirmFlag != '1') {
			alert("当前选中项有未被确认的数据,财务已确认,请重新选择!");
			return;
		}
//		var month = $('#month').combobox('getValue'); 
//		alert("month:"+month);
//		var month1 = checkedRows[index].month.combobox('getValue'); 
//		alert("month1:"+month1);
//		if (checkedRows[index].month == '') {
//			alert("当前选中项结算月为空，请编辑重新选择!");
//			return;
//		}
//		if (checkedRows[index].brandUnitNo == '') {
//			alert("当前选中项品牌部数据为空，请编辑重新选择!");
//			return;
//		}
	}

	$.messager.confirm("确认", "确认预收款之后将无法再修改,您确定吗?",
			function(r) {
				if (r) {
					var verifyIds = [];
					$.each(checkedRows, function(index, item) {
						verifyIds.push(item.id);
					});

					// 绑定数据
					var data = {
						"verifyIds" : verifyIds.join(",")
					};

					// 确认
					var url = BasePath + '/expect_cash/verify?flag=1';
					ajaxRequest(url, data, function(result, returnMsg) {
						if (result.flag == 'success') {
							expect_cash.doSearch();
							if(result.fail == '[]'){
								alert("确认成功:"+result.success);
							}else if (result.success == '[]') {
								alert("确认失败:"+result.fail);
							}else{
								alert("确认成功:"+result.success+", 确认失败:"+result.fail);
							}
						} else {
							showWarn('确认失败,请联系管理员!', 2);
						}
					});
				}
			});
};

//导出
expect_cash.exportExcel = function() {
	var defaults = {
		searchFormId : "searchForm",
		dataGridId : "dataGridDiv",
		exportUrl : "/expect_cash/do_fas_export",
		exportTitle : "导出",
		exportType : "common"
    };
	options = $.extend(defaults, options);
	var $dg = $("#" + options.dataGridId);
    var queryParams = $dg.datagrid('options').queryParams;
    var grepColumns = $dg.datagrid('options').columns;
    var subGrepColumns = $dg.datagrid('options').subColumns;
    var Fcolumns= $dg.datagrid('options').frozenColumns;
    
    //添加冻结列
    if(Fcolumns.length >0){
    	for(var i=Fcolumns[0].length-1;i>=0;i--){
    		if(!Fcolumns[0][i]['expander']) {
    			grepColumns[0].unshift(Fcolumns[0][i]);
    		}
    	}
    }
    //添加冻结列(第二行) 参照期间结存排版
    if(Fcolumns.length >1){
    	for(var i=Fcolumns[1].length-1;i>=0;i--){
    		if(!Fcolumns[1][i]['expander']) {
    			grepColumns[1].unshift(Fcolumns[1][i]);
    		}
    	}
    }
    
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
	
	//移除冻结列
    if(Fcolumns.length >0){
    	for(var i=Fcolumns[0].length-1;i>=0;i--){
    		if(!Fcolumns[0][i]['expander']) {
    			grepColumns[0].splice($.inArray(Fcolumns[0][i],grepColumns[0]),1);
    		}
    	}
    }
    //移除冻结列(第二行)
    if(Fcolumns.length >1){
    	for(var i=Fcolumns[1].length-1;i>=0;i--){
    		if(!Fcolumns[1][i]['expander']) {
    			grepColumns[1].splice($.inArray(Fcolumns[1][i],grepColumns[1]),1);
    		}
    	}
    }
	
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
            	params.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
            	params.fileName = "预收款单导出";
            	params.exportType = "common";
            	params.orderByField = null;
            	params.orderBy = null;
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
};

expect_cash.check = function(rowIndex,data){
	var getconfirmFlagValue=data.confirmFlag;
	var getbalanceStatusValue=data.balanceStatus;
	
	if(data.confirmFlag == 2){
		 showWarn("财务已确认，不能修改！");
		 return true;
	 }
	if(data.balanceStatus == 2){
		showWarn("结算单已生成,不能修改");
		return true;
	}
	return false;
};

//反审核
expect_cash.doAntiAudit = function() {
	$('#searchForm').form("clear");
	// 获取所有勾选checkbox的行
	var $dg = $("#dataGridDiv");
	var checkedRows = $dg.datagrid("getChecked");

	if (checkedRows.length < 1) {
		alert('请选择需要反审核的数据!');
		return;
	}

	for ( var index = 0; index < checkedRows.length; index++) {
		if (checkedRows[index].confirmFlag != '2') {
			alert("当前选中项有未被反审核的数据，请重新选择!");
			return;
		}   
//		alert("balanceStatus:"+checkedRows[index].balanceStatus);
		if (checkedRows[index].balanceStatus == '2') {
			alert("当前选中项有已结算的数据，请重新选择!");
			return;
		} 
//		alert("balanceNo:"+checkedRows[index].balanceNo);
//		if (checkedRows[index].balanceNo != '' || checkedRows[index].balanceNo != null) {
//			alert("当前选中项有已结算的数据，请重新选择!");
//			return;
//		} 
	}

	$.messager.confirm("反审核", "确认反审核预收款,您确定吗?",
			function(r) {
				if (r) {
					var verifyIds = [];
					$.each(checkedRows, function(index, item) {
						verifyIds.push(item.id);
					});

					// 绑定数据
					var data = {
						"verifyIds" : verifyIds.join(",")
					};

					// 确认
					var url = BasePath + '/expect_cash/verify?flag=2';
					ajaxRequest(url, data, function(result, returnMsg) {
						if (result.flag == 'success') {
							expect_cash.doSearch();
							if(result.fail == '[]'){
								alert("反审核成功:"+result.success);
							}else if (result.success == '[]') {
								alert("反审核失败:"+result.fail);
							}else{
								alert("反审核成功:"+result.success+", 反审核失败:"+result.fail);
							}
						} else {
							showWarn('反审核失败,请联系管理员!', 2);
						}
					});
				}
			});
};

//打印
var searchKey = null;
expect_cash.print = function(){
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要打印的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能打印一行数据!');
		return;
	} else if(rows[0].confirmFlag == !'1'){
		alert('只能打印已确认的预收款单!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {
			printerMxURL = BasePath + "/expect_cash/printer?";
			printer.doLoadInfo();
			//是否设置默认的打印机啊
			printer.initPrinter(printer.otherTicket);
		});
	}
	
};         
function createPrintPage(){
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	var rowData = rows[0];
	LODOP.PRINT_INIT("预收款信息");
	var context = "";
	$.ajax({
		url : printerMxURL,
		data: {
			"billNo" : rowData.billNo
		},
		type : 'get',
		async : false,
		success : function(data) {
			context = data;
		}
	});
 	LODOP.SET_PRINT_PAGESIZE(1,"100mm","138mm","");
	LODOP.ADD_PRINT_HTM("5mm", 0, "100%", "100%", context);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	LODOP.SET_PRINT_MODE("CATCH_PRINT_STATUS", true);
	LODOP.SHOW_CHART();
}
// 删除
expect_cash.deleteRecord = function() {
	$('#searchForm').form("clear");
	// 获取所有勾选checkbox的行
	var $dg = $("#dataGridDiv");
	var checkedRows = $dg.datagrid("getChecked");
	
	if (checkedRows.length < 1) {
		alert('请选择需要删除的数据!');
		return;
	}else{
		for ( var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].confirmFlag == 1){
				alert('已确认的单据不能删除!');
				return;
			}
		}
	}

	$.messager.confirm("确认", "删除后将无法恢复，您确定要删除这" + checkedRows.length + "条数据吗?",
			function(r) {
				if (r) {
					var deleteIds = [];
					$.each(checkedRows, function(index, item) {
						deleteIds.push(item.id);
					});

					// 绑定数据
					var data = {
						"deleteIds" : deleteIds.join(",")
					};

					// 删除
					var url = BasePath + '/expect_cash/delete';
					ajaxRequest(url, data, function(result, returnMsg) {
						if (result == 'success') {
							// 4.删除成功,清空表单
							expect_cash.doSearch();
							alert('删除成功!');
						} else {
							showWarn('删除失败,请联系管理员!', 2);
						}
					});
				}
			});
};

//初始化营业员
expect_cash.initAssistant = function() {
	$("#assistantName").iptSearch({
		readonly : false,
		width : 130,
		clickFn : function() {
			dgSelector({
 				hkid: 'dataGridDiv',
				enableCloseButton: false,
				autoQuery: true,
				title : '店员选择列表',
				href : BasePath
						+ '/common_data/search/list_assistant?assistant='
						+ $("#assistantName").val(),
				width : 450,
				height : 400,
				queryUrl : BasePath 
						+ '/common_data/assistant_list.json',
				method : 'get',
				isFrame : false,
				fn : function(rowData) {
					$('#assistantId').val(rowData.id);
					$('#assistantNo').val(rowData.assistantNo);
					$('#assistantName').val(rowData.assistantName);
				}
			});
		}
	});
};
//初始化大类
expect_cash.initCategoryNo = function() {
	$('#categoryNo').combotree({    
		 panelWidth:156,
		 panelHeight:150,
		 checkbox:true,
		 multiple:false,
		 url: BasePath + "/common_data/getCategoryTreeForExpectCash",
		 toolbar: [{
      		iconCls: 'icon-remove',
      		text : '清空已选活动',
      		handler: function(){
//      			expect_cash.clearPro();
//      			$("#proName").combogrid('hidePanel');
      		}
      	}],
		 onClick: function(node){
			 var no = node.attributes.no;
			 var name = node.text;
			 $("#name").val(name);
		 },
		 onHidePanel:function(){
			 var allRows = $('#categoryNo').combotree('getValues');
			 if($('#categoryNo').combotree('getText') == ''){
				 $("#name").val('');
				 $('#categoryNo').combotree('setValue','');
			 }
		 }
	});
};
//清空促销活动相关值
expect_cash.clearPro = function() {
	$("#proName").combogrid('setValue','');
	$("#proNo").val('');
	$("#rateCode").val('');
	$("#rate").val('');
};
//初始化促销活动
expect_cash.initExpectCash = function() {
	$("#proName").combogrid({
		prompt: "",
		url : BasePath + '/common_data/expect_cash/promotion',
		width : 156, 
		panelWidth : 300,
		panelHeight : 290,
		idField : 'proName',
		textField : 'proName',
		pagination : true,
		autoShowPanel:true,
		editable : false,
//		readonly : true,
//		hasDownArrow : false,
//		selectOnNavigation : true,
//		toolbar: "#expectCashToolBar",
		columns : [[    
					{field : 'proNo',title : '促销活动编号',width : 130,halign:'center', align:'center'},
				    {field : 'proName',title : '活动名称',width : 140,halign:'center', align:'left'}
	                 ]],
	    toolbar: [{
	         		iconCls: 'icon-remove',
	         		text : '清空已选活动',
	         		handler: function(){
	         			expect_cash.clearPro();
	         			$("#proName").combogrid('hidePanel');
	         		}
	         	}],
	    onClickRow : function(){
	    	var g = $("#proName").combogrid('grid');	// 获取数据表格对象
			var rowData = g.datagrid('getSelected');	// 获取选择的行
			if(rowData){
//				$("#proName").combogrid('setValue',rowData.proName);
				$("#proNo").val(rowData.proNo);
				$("#rateCode").val(rowData.rateCode);
				$("#rate").val(rowData.rate);
				
	        }else if($("#proName").combogrid("getValue")){
				showWarn("请输入正确的促销活动编号");
			}
	    }
	}); 
};

//小数格式化
//function pointFormatter(value){
// 
//	if(value){
//      //数字转换为字符串
//      value += "";
//      var index = value.indexOf(".");
//      if(index < 0){
//          return value + ".00";
//      }else if(value.length - index == 2){ //value类似123.4，需要转换为123.40
//          return value + "0";
//      }
//	}
//      return value;
//}
//金额验证框正浮点数
$.extend($.fn.validatebox.defaults.rules, {
	positiveNum: {//正浮点数
        validator: function (value) {
            var reg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
            return reg.test(value);
        },
        message: '金额格式不准确，必须大于0'
    }
});
// 清理form
expect_cash.clearAll = function() {
	$("#searchForm").form("clear");
	$("#dataForm").form("clear");
	$("#id").val('');
	$("#billNo").val('');
	$("#refBillNo").val('');
	$("#amount").val('');
};

// 关闭窗体
expect_cash.closeWindow = function() {
	$('#dataForm').form("disableValidation");
	$('#showDialog').window('close');
};
var editor;
// 初始化
$(document).ready(function() {
	
	$.fas.extend(expectCashEditor, FasEditorController);
	dialog = new expectCashEditor();
	dialog.init("/expect_cash", {
		dataGridId : "divDataGrid",
		searchFormId : "searchForm",
		exportUrl : "/do_fas_export",
		exportTitle : "预收款单列表导出",
		searchBtn : "btn-search1"
	});
	
	editor = new expectCashEditor();	
	editor.init("/expect_cash", {
		dataGridDivId : 'divDataGrid',
		dataGridId : 'dataGridDiv',
		saveUrl : "/expect_cash/save",
		searchBtn : "btn-search",
		exportUrl : "/do_fas_export",
		afterUpdate : function(rowIndex, rowData) {
		},
		keyboard : true
	});
	$('#showDialog').window("center");
//	selectFromDic4Combox('#confirmFlagSearch', 'CONFIRM_FLAG');

	$("#businessType").combobox({
		onSelect: function(record) {
			if (record) {
				if (record.itemValue == 1) {
					$('#settleCode').validatebox({    
						required: true,    
						validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
					});
					$('#settleCode').attr('required', true);
					$('#span_settleCode').show();
				} else if (record.itemValue == 2) {
					$('#settleCode').validatebox({    
						required: false,
						validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
					});
					$('#span_settleCode').hide();
					$('#settleCode').removeAttr('required');
					$('#settleCode').validatebox('validate');
				} 
			} else {
				$('#settleCode').validatebox({    
					required: true,    
					validType:['numberAndEnglish', 'vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']
				});
				$('#settleCode').attr('required', true);
				$('#span_settleCode').show();
			}
		}
	});
	
	$("#assistantNameSearch").iptSearch({
		readonly : false,
		width : 100,
		clickFn : function() {
			dgSelector({
 				hkid: 'dataGridDiv',
				enableCloseButton: false,
				autoQuery: true,
				title : '店员选择列表',
				href : BasePath
						+ '/common_data/search/list_assistant?assistant='
						+ $("#assistantName").val(),
				width : 450,
				height : 400,
				queryUrl : BasePath 
						+ '/common_data/assistant_list.json',
				method : 'get',
				isFrame : false,
				fn : function(rowData) {
					$('#assistantNameSearch').val(rowData.assistantName);
				}
			});
		}
	});
});
