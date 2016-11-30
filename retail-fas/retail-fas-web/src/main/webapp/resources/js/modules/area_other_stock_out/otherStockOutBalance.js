var areaOtherBalance= new BillObj({
	modulePath : BasePath + '/area_other_stock_out_balance',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '11',
	mainDgId : 'mainDataGrid'
}, [ {
	id : 'transferOutDtl',
	title : '出库明细',
	tabUrl : BasePath + '/area_other_stock_out_balance/transferOutTab.htm',
	listUrl :BasePath + '/area_other_stock_out_balance/transferOutList.json',
	queryParams : {}
}, {
	id : 'invoiceInfoDtl',
	title : '开票信息',
	tabUrl : BasePath + '/area_other_stock_out_balance/invoiceInfo.htm',
	listUrl : BasePath + '/area_other_stock_out_balance/invoiceInfoList.json',
	queryParams : {}
}]);

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
	var currency=data.currency;
	if(currency=='001'){
		dialog.$('#currency').append("人民币");
	}
	dialog.$('#salerName').append(data.salerName);
	dialog.$('#outAmount').append(data.outAmount);
	dialog.$('#balanceAmount').append(data.balanceAmount);
	dialog.$('#buyerName').append(data.buyerName);
	dialog.$('#balanceStartDate').append(data.balanceStartDate);
	dialog.$('#balanceEndDate').append(data.balanceEndDate);
	dialog.$('#balanceDate').append(data.balanceDate);
	var brandUnitName = $("#brandUnitNameId").combogrid('getValues');
	dialog.$('#brandUnitName').append(brandUnitName+' ');
	dialog.$('#invoiceApplyNo').append(data.invoiceApplyNo);
	dialog.$('#remark').append(data.remark);
	dialog.$('#createUser').append($('#createUser').text());
	dialog.$('#auditor').append($('#auditor').text());
	dialog.$('#createTime').append($('#createTime').text());
	dialog.$('#auditTime').append($('#auditTime').text());
	dialog.$('#printTime').append(getCurrentNowTime());
}

function print(){
	var previewUrl = BasePath + '/print/preview?viewName=other_outgoing_settlement_print';
	var data = $('#mainDataForm').form('getData');
	var $dg = $('#transferOutDtl');
	var columns  = $dg.datagrid('options').columns;
	ygDialog({
		isFrame: true,
		cache : false,
		title : '地区其他出库结算单',
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
			//过滤列头
			var filterColumns = [];
			$.each(columns, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumns.push(n);
				}
			});
			/*filterColumns.push(grepColumns);*/
			
			if(columnsLength >= 1){
				//出库明细汇总
				dialog.$("#hiddenDiv").remove();
				dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
				var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
				tableHeader.append(tbody.html());
				setVal(dialog,data);
			}
			//组装出库明细表体
			ajaxRequestAsync(BasePath + '/area_other_stock_out_balance/transferOutList.json?balanceNo='+data.billNo+'&print=1',null, function(result) {
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
							if(field=="billNo"){
								value="合计";
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
	areaOtherBalance.setDtlTabs();
	areaOtherBalance.addMainTab();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	areaOtherBalance.add();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/area_other_stock_out_balance/list.json',{billNo:billNoMenu, balanceType:11},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					areaOtherBalance.loadDetail(0, obj);
				},500);
			}else {
				alert("单号:"+ billNoMenu +"有误！");
			}
		});
	}
	
	//加载预警列表
	loadWarnMessage();
});

/**
 * 清空
 */
areaOtherBalance.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCon,#buyerNoCon,#brandUnitNoCond').val('');
};

/**
 * mainForm加载完毕之后的处理
 */
areaOtherBalance.afterLoadForm=function(rowData){
	if (rowData.brandUnitName != null && $.trim(rowData.brandUnitName) != '') {
		$("#brandUnitNameId").combogrid("setValues", rowData['brandUnitName'].split(','));
	}
};

/**
 * 批量新增之前的处理
 */
areaOtherBalance.beforeBatchAdd=function(){
	$("#balanceDateBatch").datebox('setValue',new Date().format("yyyy-MM-dd"));
};

/**
 * 保存回调函数
 */
areaOtherBalance.afterSaved = function(dgId, url, params, jsonObj) {
	if (jsonObj.brandUnitName != null && $.trim(jsonObj.brandUnitName) != '') {
		$("#brandUnitNameId").combogrid("setValues", jsonObj['brandUnitName'].split(','));
	}
	$('#billName,#remarkId').removeClass('readonly').removeAttr("readonly");
	var billNo = $('#billNo').val();
	params.balanceNo = billNo;
	$('#' + dgId).datagrid('options').queryParams = params;
	$('#' + dgId).datagrid('options').url = url;
	$('#' + dgId).datagrid('load');
};

/**
 * form加载后的样式控制
 */
areaOtherBalance.setFormClass=function(){
	$('#billNo,#salerNameId,#buyerNameId,#brandUnitNameId,#invoiceApplyNo,#balanceAmount,#outAmount,#statusNameId').attr("readonly", true).addClass("readonly");
	$("#salerNameId,#buyerNameId,#brandUnitNameId").combogrid("disable");
	$('#currencyNameId').combobox('enable');
};

areaOtherBalance.afterSetClass=function(rowData){
	$("#salerNameId,#buyerNameId,#brandUnitNameId").combogrid("disable");
	$('#statusNameId').val(areaOtherBalance.statusFormat(rowData.status));
	$('#currencyNameId').combobox('setValue', rowData.currency);
};

/**
 * 新增初始化
 */
areaOtherBalance.initAdd = function() {
	$('#brandUnitNameId').attr("readonly",true);
	$('#billNo,#outAmount,#statusNameId,#balanceAmount,#invoiceApplyNo').attr("readonly",true).addClass("readonly");
	$('#id,#statusId,#brandUnitNoId').val("");	
	$("#balanceAmount").numberbox("clear");
	$("#salerNameId,#buyerNameId,#brandUnitNameId").combogrid("enable");
	$("#balanceDateId").datebox('setValue',new Date().format("yyyy-MM-dd"));
};

/**
 * 判断提示
 */
areaOtherBalance.getTipsMsg = function(currStatus, operStatus) {
	if (currStatus === "") {
		return "没有可以操作的单据";
	}

	if (currStatus == 99 && operStatus > 2 && operStatus != 99) {
		return "不允许越级确认操作!";
	}

	if (currStatus == 6&& operStatus > 0 && operStatus < 5) {
		return "已开票,不能反确认!";
	}

	if (operStatus > 0 && operStatus < 5) {
		// 重复操作
		if (currStatus == operStatus) {
			return "不允许重复操作!";
		}

		// 反向判断
		if (currStatus == 4 && operStatus < 4) {
			return "不允许反向确认操作!";
		}

		// 越级判断
		if (currStatus == 0 && operStatus > 2) {
			return "不允许越级确认操作!";
		}
	} else if (operStatus == 99) {
		if (currStatus == 0 || currStatus >= 5) {
			return "只允许打回确认状态的单据!";
		} else if (currStatus == 99) {
			return "不允许重复操作!";
		}
	}
	return "";
};

/**
 * 打回判断提示信息
 */
areaOtherBalance.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo,currStatus) {
	if (operStatus == 99) {
		if (askPaymentNo != '' && askPaymentNo != null) {
			return "存在已关联请款单的单据，不能打回!";
		} else if (invoiceApplyNo != '' && invoiceApplyNo != null) {
			return "存在已开票的单据，不能打回!";
		} else if (currStatus == 4) {
			return "总部财务已经确认，不能打回!";
		}
	}
	return "";
}
