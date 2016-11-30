var hqInsteadBuy= new BillObj({
	modulePath : BasePath + '/hq_insteadOf_buy_balance',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '13',
	mainDgId : 'mainDataGrid',
	startDateId:'startDateCond',
	endDataId:'endDateCond'
}, [ {
	id : 'transferInDtl',
	title : '入库明细',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/transferInTab.htm',
	listUrl : BasePath + '/hq_insteadOf_buy_balance/transferInList.json',
	queryParams : {billType:1301}
}, {
	id : 'returnDtl',
	title : '退残明细',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/returnInfo.htm',
	listUrl : BasePath + '/hq_insteadOf_buy_balance/transferInList.json',
	queryParams : {billType:1333}
}, {
	id : 'otherDeductionDtl',
	title : '其他扣项',
	tabUrl : BasePath + '/hq_insteadOf_buy_balance/otherDeduction.htm',
	listUrl : BasePath + '/other_deduction/list.json',
	queryParams : {}
} ]);

//add by Ning.ly
function getCurrentNowTime() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var day = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	if (minute < 10) {
		minute = '0' + minute;
	}
	var second = now.getSeconds();
	if (second < 10) {
		second = '0' + second;
	}
	var nowdate = year + "-" + month + "-" + day + " " + hour + ":" + minute
			+ ":" + second + " ";
	return nowdate;
}

function setVal(dialog,data){
	dialog.$('#billNo').append(data.billNo);
	dialog.$('#statusName').append(data.statusName);
	dialog.$('#billName').append(data.billName);
	dialog.$('#buyerName').append(data.buyerName);
	dialog.$('#salerName').append(data.salerName);
	dialog.$('#balanceStartDate').append(data.balanceStartDate);
	dialog.$('#balanceEndDate').append(data.balanceEndDate);
	dialog.$('#balanceDate').append(data.balanceDate);
	dialog.$('#createUser').append($('#createUser').text());
	dialog.$('#auditor').append($('#auditor').text());
	dialog.$('#createTime').append($('#createTime').text());
	dialog.$('#auditTime').append($('#auditTime').text());
	dialog.$('#balanceAmount').append(data.balanceAmount);
	dialog.$('#deductionAmount').append(data.deductionAmount);
	dialog.$('#returnAmount').append(data.returnAmount);
	dialog.$('#entryAmount').append(data.entryAmount);
	var brandUnitName = $("#brandUnitNameId").combogrid('getValues');
	dialog.$('#brandUnitName').append(brandUnitName+' ');
	dialog.$('#remark').append(data.remark);
	if(data.currency=='001'){
		dialog.$('#currencyName').append("人民币");
	}else if(data.currency=='002'){
		dialog.$('#currencyName').append("美元");
	}
	dialog.$('#printTime').append(getCurrentNowTime());
}

function print(){
	var previewUrl = BasePath + '/print/preview?viewName=insteadBuy_balance_print';
	var data = $('#mainDataForm').form('getData');
	var $dg = $('#transferInDtl');
	var columns  = $dg.datagrid('options').columns;
	ygDialog({
		isFrame: true,
		cache : false,
		title : '总部代采结算单',
		modal:true,
		showMask: false,
		fit : true,
		href : previewUrl,
		buttons:[{
			text:'打印',
			iconCls:'icon-print',
			handler:'print_page'
		}],
		onLoad:function(win,dialog){
			var tableHeader = dialog.$('#tableHeader');
			var tableBody  =  dialog.$('#tableBody');
			var columnsLength = columns.length;
			
			//1过滤表头不需要打印的column 静态表头配置了printable=false都将不打印
			var grepColumns = $.grep(columns[columnsLength - 1], function(o, i) {
				if ($(o).attr('printable') == false) {
					return true;
				}
				return false;
			}, true);
			//过滤大类列头
			var filterColumns = [];
			$.each(columns, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumns.push(n);
				}
			});
			/*filterColumns.push(grepColumns);*/
			
			if(columnsLength >= 1){
				//入库明细
				dialog.$("#hiddenDiv").remove();
				dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
				var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
				tableHeader.append(tbody.html());
				setVal(dialog,data);
			}
			
			//组装明细汇总表体
			ajaxRequestAsync(BasePath + '/hq_insteadOf_buy_balance/transferInList.json?billType=1301&print=1&balanceNo='+data.billNo,null, function(result) {
				if(result.rows!=undefined){
					var rows = result.rows;
					var footerRows = result.footer;
					for ( var i = 0; i < rows.length; i++) {
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumns).each(function(i,node){
							var field = $(node).attr('field');
							var value = row[field+''];
							if(value==undefined){
								bodyTdNode+='<td align="center">'+''+'</td>';
							}else{
								bodyTdNode+='<td align="center">'+value+'</td>';
							}
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBody.append(bodyTrNode);
					}
					
					//添加合计项 
					var bodyTrNode2 ='<tr>';
					var bodyTdNode2 = '';
					var footerRow = footerRows[0];
					if(footerRow!=undefined){
						$(grepColumns).each(function(i,node){
							var field = $(node).attr('field');
							var value = footerRow[field+''];
							if(field=='billNo'){
								value='合计';
							}
							if(value==undefined){
								bodyTdNode2+='<td align="center">'+''+'</td>';
							}else{
								bodyTdNode2+='<td align="center">'+value+'</td>';
							}
						});
						bodyTrNode2+=bodyTdNode2+'</tr>';
						tableBody.append(bodyTrNode2);
					}
				}
			});
			dialog.print_page(this);
			win.close();
		}
	});
}



/**
 * 初始化设置
 */
$(function() {
	hqInsteadBuy.setDtlTabs();
	hqInsteadBuy.addMainTab();
	hqInsteadBuy.add();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	hqInsteadBuy.initSettleMethod();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $("#billNoMenu").val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/hq_insteadOf_buy_balance/list.json',{billNo:billNoMenu,balanceType:13},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					hqInsteadBuy.loadDetail(0, obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	//加载预警列表
	loadWarnMessage();
});

/**
 * 加载结算方式
 */
var datas=[];
hqInsteadBuy.initSettleMethod=function(){
	var url=BasePath+'/common_util/getSettleMethod';
	ajaxRequestAsync(url, null, function(data) {
		$.each(data, function(index, item) {
			datas[item.code] = item.name;
		});
	});
};

/**
 * 清空
 */
hqInsteadBuy.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCond,#buyerNoCond,#brandUnitNoCond').val('');
};

/**
 * 批量新增之前的处理
 */
hqInsteadBuy.beforeBatchAdd=function(){
	$("#balanceDateBatch").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#balanceStartDateId").datebox('setValue',hqInsteadBuy.getStartDate().format("yyyy-MM-dd"));
	$("#balanceEndDateId").datebox('setValue',hqInsteadBuy.getEndDate().format("yyyy-MM-dd"));
};

hqInsteadBuy.toBatchAdd=function(){
	hqInsteadBuy.beforeBatchAdd();
	ygDialog({
		title : '批量生成界面',
		target : $('#myFormPanel'),
		width : 400,
		height : 300,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				hqInsteadBuy.batchSave();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
				$('#dataForm').form('clear');
			}
		} ]
	});
};

hqInsteadBuy.batchSave=function(){
	var fromObj = $('#dataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	fromObj.form('submit', {
		url : BasePath + '/hq_insteadOf_buy_balance/check_data',
		onSubmit : function(param) {
		},
		success : function(data) {
			var jsonObj=JSON.parse(data);
			if (jsonObj.count > 0) {
				showInfo('存在单价为0的单据，不能生成结算单！');
				$('#myFormPanel').dialog('close');
			} else {
				hqInsteadBuy.batchSaveBill();
			}
		}
	});
};

/**
 * 批量生成结算单
 */
hqInsteadBuy.batchSaveBill=function(){
	var formObj = $('#dataForm');
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理中...'
	}); 
	formObj.form('submit', {
		url : BasePath + '/hq_insteadOf_buy_balance/batch_save_bill',
		onSubmit : function(param) {
		},
		success : function(data) {
			var jsonObj=JSON.parse(data);
			if (jsonObj.flag==true) {
				$.messager.progress('close');
				$('#myFormPanel').dialog('close');
				$('#dataForm').form('clear');
				$.fas.showMsg('生成成功!',5000);
				hqInsteadBuy.search();
			} else {
				showInfo('没有业务发生,不能生成结算单！');
				$('#myFormPanel').dialog('close');
				$('#dataForm').form('clear');
				$.messager.progress('close');
			}
		}
	});
};

//保存之前的数据校验
hqInsteadBuy.beforeSave=function(){
	var fromObj = $('#mainDataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	var status = $('#statusId').val();
	var billNo = $('#billNo').val();
	var params = fromObj.form('getData');
	var url = BasePath + '/hq_insteadOf_buy_balance/check_data';
	if (billNo != '') {
		if (0 < status && status < 99) {
			showInfo('已经确认,不能修改');
			return;
		}
		hqInsteadBuy.doPut();
	} else {
		ajaxRequestAsync(url, params, function(data) {
			if (data.count > 0) {
				showInfo('存在单价为0的单据，不能生成结算单！');
			} else {
				hqInsteadBuy.saveBill();
			}
		});
	}
};

/**
 * 保存回调函数
 */
hqInsteadBuy.afterSaved = function(dgId, url, params, jsonObj) {
	var billNo = $('#billNo').val();
	params.balanceNo = billNo;
	$('#' + dgId).datagrid('options').queryParams = params;
	$('#' + dgId).datagrid('options').url = url;
	$('#' + dgId).datagrid('load');
};

/**
 * form加载后的样式控制
 */
hqInsteadBuy.setFormClass=function(){
	$('#billNo,#entryAmountId,#deductionAmountId,#statusNameId,#supplierNameId,#buyerNameId,#returnAmountId,#balanceAmount').attr("readonly", true).addClass("readonly");
	$("#supplierNameId,#buyerNameId,#brandUnitNameId").combogrid("disable");
	$('#currencyNameId').combobox('enable');
};

hqInsteadBuy.afterSetClass=function(rowData){
	$("#supplierNameId,#buyerNameId,#brandUnitNameId").combogrid("disable");
	$('#statusNameId').val(hqInsteadBuy.statusFormat(rowData.status));
	$('#currencyNameId').combobox('setValue', rowData.currency);
};

/**
 * 新增初始化
 */
hqInsteadBuy.initAdd = function() {
	$('#billNo,#entryAmountId,#deductionAmountId,#statusNameId,#returnAmountId,#balanceAmount').attr("readonly",true).addClass("readonly");
	$('#id,#statusId,#brandUnitNo').val("");	
	$("#balanceAmount").numberbox("clear");
	$("#supplierNameId,#brandUnitNameId,#buyerNameId").combogrid("enable");
	$("#balanceDateId").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#balanceStartDate").datebox('setValue',hqInsteadBuy.getStartDate().format("yyyy-MM-dd"));
	$("#balanceEndDate").datebox('setValue',hqInsteadBuy.getEndDate().format("yyyy-MM-dd"));
};

/**
 * 扣项调整之前的判断
 */
hqInsteadBuy.adjustJudge = function(){
	if ($('#statusId').val() == "") {
		return '没有可操作的单据!';
	} else if ($('#statusId').val() == 5) {
		return '已请款,不允许再做调整!';
	} else if ($('#statusId').val() == 1) {
		return '总部已经确认,不允许再做调整!';
	}
	return "";
}; 

/**
 * 判断提示
 */
hqInsteadBuy.getTipsMsg = function(currStatus, operStatus) {
	if(currStatus ===""){
		return "没有可以操作的单据";
	}
	
	// 审核判断 非打回
	if((operStatus > 0 && operStatus < 5) && operStatus != 99){
		if(currStatus > operStatus&&currStatus!=99){
			return "不允许反向确认操作!";
		}else if(currStatus == operStatus){
			return "不允许重复操作!";
		}else if(operStatus - currStatus > 1){
			return "不允许越级确认操作!";
		} else if(operStatus==2&&currStatus==99){
			return "不允许越级确认操作!";
		}
	}else if(operStatus == 99){	// 审核判断  打回
		if(!(currStatus == 1 || currStatus == 2)){
			return "只允许打回确认状态的单据!";
		}else if(currStatus==99){
			return "不允许重复操作!";
		} 
	}
	return "";
};

/**
 * 打回判断提示信息
 */
hqInsteadBuy.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo) {
	if (operStatus == 99) {
		if (invoiceNo != '' && invoiceNo != null) {
			return "存在已关联采购发票的单据,不能打回!";
		}
	}
	return "";
};

//导出之前的数据校验
hqInsteadBuy.beforeListExport = function(gridId,exportUrl,exportTitle) {
	$('#searchForm').form('submit', {
		url : BasePath + '/hq_insteadOf_buy_balance/export_check',
		onSubmit : function(param) {
			param.balanceType = '13';
		},
		success : function(data) {
			var jsonObj = JSON.parse(data);
			if (jsonObj.msg == true) {
				hqInsteadBuy.listExport(gridId,exportUrl,exportTitle);
			} else {
				showInfo("存在未确认的单据，不能导出");
				return;
			}
		}
	});
};

//明细导出之前的业务处理
hqInsteadBuy.beforeDtlExport = function(gridId, exportGrid, exportUrl,
		exportTitle) {
    var selectedRows = $("#" + gridId).datagrid('getSelections');
	if (selectedRows.length < 1) {
		showInfo("请选择要操作的记录!");
		return;
	}
	var errMessage = "";
	$.each(selectedRows, function(index, item) {
		if (item.status == 0 || item.status == 99) {
			errMessage = '存在未确认的单据,请重新选择!';
			return false;
		}
	});
	if (errMessage != "") {
		showInfo(errMessage);
		return;
	} else {
		hqInsteadBuy.dtlExport(gridId, exportGrid, exportUrl, exportTitle);
	}
};
