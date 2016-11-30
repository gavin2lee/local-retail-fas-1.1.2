var areaBalance= new BillObj({
	modulePath : BasePath + '/area_among_pay',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '5',
	mainDgId : 'mainDataGrid',
	startDateId:'startDateCond',
	endDataId:'endDateCond'
}, [ {
	id : 'transferOutDtl',
	title : '出库明细',
	tabUrl : BasePath + '/area_among_balance/transferOutTab.htm',	
	listUrl : BasePath + '/area_among_balance/transferOutList.json',
	queryParams : {}
}, {
	id : 'itemDataDtl',
	title : '明细汇总',
	tabUrl : BasePath + '/area_among_balance/itemDataInfo.htm',
	listUrl : BasePath + '/area_among_balance/itemDataList.json',
	queryParams : {}
}, {
	id : 'otherDeductionDtl',
	title : '其他扣项',
	tabUrl : BasePath + '/area_among_balance/otherDeduction.htm',
	listUrl : BasePath + '/other_deduction/list.json',
	queryParams : {}
}, {
	id : 'invoiceInfoDtl',
	title : '发票信息',
	tabUrl : BasePath + '/area_among_pay/invoiceInfo.htm',
	listUrl : BasePath + '/bill_invoice/query_balance_invoice.json',
	queryParams : {}
}, {
	id : 'receivableInfoDtl',
	title : '请款信息',
	tabUrl : BasePath + '/area_among_pay/receivableInfo.htm',
	listUrl : BasePath + '/bill_ask_payment/list.json',
	queryParams : {}
} ]);

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
	dialog.$('#buyerName').append(data.buyerName);
	dialog.$('#salerName').append(data.salerName);
	dialog.$('#outAmount').append(data.outAmount);
	dialog.$('#deductionAmount').append(data.deductionAmount);
	dialog.$('#balanceAmount').append(data.balanceAmount);
	dialog.$('#balanceStartDate').append(data.balanceStartDate);
	dialog.$('#balanceEndDate').append(data.balanceEndDate);
	dialog.$('#balanceDate').append(data.balanceDate);
	dialog.$('#brandUnitName').append(data.brandUnitName);
	dialog.$('#remark').append(data.remark);
	dialog.$('#createUser').append($('#createUser').text());
	dialog.$('#auditor').append($('#auditor').text());
	dialog.$('#createTime').append($('#createTime').text());
	dialog.$('#auditTime').append($('#auditTime').text());
	dialog.$('#printTime').append(getCurrentNowTime());
}

function print(){
	var previewUrl = BasePath + '/print/preview?viewName=regional_settlement_payable_print';
	var data = $('#mainDataForm').form('getData');
	var $dg = $('#exportExcelDG');
	var columns  = $dg.datagrid('options').columns;
	ygDialog({
		isFrame: true,
		cache : false,
		title : '地区间(应付)结算单确认',
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
				//品牌大类汇总
				dialog.$("#hiddenDiv").remove();
				dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
				var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
				tableHeader.append(tbody.html());
				setVal(dialog,data);
			}
			
			//组装明细汇总表体
			ajaxRequestAsync(BasePath + '/area_among_balance/itemDataList.json?balanceNo='+data.billNo+'&print=1',null, function(result) {
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
	areaBalance.setDtlTabs();
	areaBalance.disableMainInfo();
	areaBalance.addMainTab();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	areaBalance.initSettleMethod();
	
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/area_among_pay/list.json',{billNo:billNoMenu},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					areaBalance.loadDetail(0, obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	//加载预警列表
	loadWarnMessage();
});

var datas=[];
areaBalance.initSettleMethod=function(){
	var url=BasePath+'/common_util/getSettleMethod';
	ajaxRequestAsync(url, null, function(data) {
		$.each(data, function(index, item) {
			datas[item.code] = item.name;
		});
	});
};

areaBalance.clear = function() {
	$('#searchForm').form("clear");
	$('#salerNoCon,#buyerNoCon,#brandUnitNoCond').val('');
};

areaBalance.setFormClass=function(){
	$('#mainDataForm input').attr("readonly", true).addClass("readonly");
	$("#mainDataForm").find("input[class*=easyui-combobox]").combobox('disable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
};

areaBalance.afterSetClass=function(rowData){
	$('#statusNameId').val(areaBalance.statusFormat(rowData.extendStatus));
	$('#currencyNameId').combobox('setValue',rowData.currency);
};

/**
 * 生成请款单
 */
areaBalance.BatchCreatePaymentBill = function(status) {
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = areaBalance.getMsgOfBatch(checkRows,status);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	var message = "";
	if (checkRows.length > 0) {
		$.each(checkRows, function(index, row) {
			if(!(row.extendStatus == 4 && (row.askPaymentNo =='' || row.askPaymentNo==null))){
				message = "财务确认且未请款才能生成请款单!";
				return false;
			}
		});
		if (message != "") {
			showInfo(message);
			return;
		}
		var url = BasePath + "/bill_ask_payment/generate_bill_by_balance";
		var params = {
			checkRows : JSON.stringify(checkRows),
			balanceType : 5
		};
		ajaxRequestAsync(url, params, function(data) {
			if (data > 0) {
				showSuc('成功生成' + data + '条请款单');
				areaBalance.search();
			} else {
				showError('生成失败');
			}
		});
	}
};

/**
 * 判断提示
 */
areaBalance.getTipsMsg = function(currStatus, operStatus) {
	if (currStatus === "") {
		return "没有可以操作的单据";
	}

	if ((currStatus == 99 || currStatus == 0) && operStatus > 1
			&& operStatus != 99) {
		return "不允许越级确认操作!";
	}
	
	if (currStatus == operStatus) {
		return "不允许重复操作!";
	}
	
	if (currStatus == 5 && operStatus > 0 && operStatus < 5) {
		return "已请款，不能反确认!";
	}
	return "";
};

/**
 * 打回判断提示信息
 */
areaBalance.getRedoMsg = function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo) {
	if (operStatus == 99) {
		if (askPaymentNo != '' && askPaymentNo != null) {
			return "存在已关联请款单的单据，不能打回!";
		} else if (invoiceApplyNo != '' && invoiceApplyNo != null) {
			return "存在已开票的单据，不能打回!";
		}
	}
	return "";
}

