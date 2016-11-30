function BillBalanceZoneDialog() {
	
}

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

function formatNumber(num) {
    var source = String(num).split(".");//按小数点分成2部分
        source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");//只将整数部分进行都好分割
    return source.join(".");//再将小数部分合并进来
}

//浮点型计算
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = (1 * arg1).toString().split(".")[1].length;
	}catch (e) {
		r1 = 0;
	}
	try {
		r2 = (1 * arg2).toString().split(".")[1].length;
	}catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
} 

function setVal(dialog){
	var dtlData = $('#balanceDtlDataGrid').datagrid('getFooterRows');
	dialog.$('#sendQty').append(dtlData[0].sendQty);
/*	dialog.$('#sendAmount').append(dtlData[0].sendAmount);*/
	dialog.$('#printTime').append(getCurrentNowTime());
	dialog.$('#balanceStart').append($("#balanceStartDate").val()+"/"+$("#balanceEndDate").val());
	dialog.$('#salerName').append($("#salerName").combogrid('getValue'));
	dialog.$('#buyerName').append($("#buyerName").combogrid('getValue'));
	dialog.$('#createUser').append($("#createUser").text());
	dialog.$('#auditor').append($("#auditor").text());
	dialog.$('#outQty').append(formatNumber($("#outQty").val()));
	dialog.$('#outAmount').append(formatNumber($("#outAmount").val()));
	dialog.$('#balanceAmount').append(formatNumber($("#balanceAmount").val()));
	dialog.$('#rebateAmount').append(formatNumber($("#rebateAmount").val()));
	//上月余额
	ajaxRequestAsync( BasePath + '/wholesale_customer_remaining_dtl/selectRemainingAmount.json',{companyNo:$("#salerNo").val(),customerNo:$("#buyerNo").val(),createTime:$("#balanceStartDate").val()+ ' 00:00:00',flag:1},function(data){
		if(data.remainingAmount==undefined || data.remainingAmount==null){
			dialog.$('#lastMonthBalance').append("0");
		}else{
			dialog.$('#lastMonthBalance').append(formatNumber(data.remainingAmount));
		}
	});
	//月末余额
	ajaxRequestAsync( BasePath + '/wholesale_customer_remaining_dtl/selectRemainingAmount.json',{companyNo:$("#salerNo").val(),customerNo:$("#buyerNo").val(),createTime:$("#balanceEndDate").val()+' 23:59:59'},function(data){
		if(data.remainingAmount==undefined || data.remainingAmount==null){
			dialog.$('#endBalance').append("0");
		}else{
			dialog.$('#endBalance').append(formatNumber(data.remainingAmount));
		}
	});
	//本月汇款
	ajaxRequestAsync( BasePath + '/bill_pre_payment_nt/calcPrePaymentTotal.json',{companyNo:$("#salerNo").val(),customerNo:$("#buyerNo").val(),startTime:$("#balanceStartDate").val()+' 00:00:00',endTime:$("#balanceEndDate").val()+' 23:59:59'},function(data){
		dialog.$('#remittanceThisMonth').append(formatNumber(data));
	});
	//保证金
	ajaxRequestAsync( BasePath + '/bill_pre_payment_nt/calcPrePaymentTotal.json',{companyNo:$("#salerNo").val(),customerNo:$("#buyerNo").val(),startTime:$("#balanceStartDate").val()+' 00:00:00',endTime:$("#balanceEndDate").val()+' 23:59:59',paidType:1},function(data){
		dialog.$('#Bond').append(formatNumber(data));
	});
}

function print(){
	var previewUrl = BasePath + '/print/preview?viewName=wholesale_settlement_print';
	var $dg = $('#brandCategoryDeductionDataGrid');
	/*var $dgSecond = $('#deductionDataGrid');*/
	var columns  = $dg.datagrid('options').columns;
	/*var columnsSecond  = $dgSecond.datagrid('options').columns;*/
	var billNo=$("#billNo").val();
	
	//打开待打印数据页面
	ygDialog({
		isFrame: true,
		cache : false,
		title : '批发结算单打印',
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
/*			var tableHeaderRight = dialog.$('#tableHeaderSecond');
			var tableBodyRight  =  dialog.$('#tableBodySecond');
			var columnsSecondLength = columnsSecond.length;*/
			
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
			
			/*//1过滤表头不需要打印的column 静态表头配置了printable=false都将不打印
			var grepColumnsSecond = $.grep(columnsSecond[columnsSecondLength - 1], function(o, i) {
				if ($(o).attr('printable') == false) {
					return true;
				}
				return false;
			}, true);
			
			//过滤大类列头
			var filterColumnsSecond = [];
			$.each(columnsSecond, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumnsSecond.push(n);
				}
			});
			filterColumnsSecond.push(grepColumnsSecond);*/
			
			//组装列大类,促销活动 表头
			if(columnsLength >= 1){
				//品牌大类汇总
				dialog.$("#hiddenDiv").remove();
				dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
				var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
				tableHeader.append(tbody.html());
				setVal(dialog);
				/*//其它扣项
				dialog.$("#hiddenDivRight").remove();
				dialog.$("<div id='hiddenDivRight' style='display:none;'><table id='exportExcelDGRight' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDGRight").datagrid({columns : filterColumnsSecond});
				var tbodySecond = dialog.$("#exportExcelDGRight").prev(0).find('.datagrid-htable').find('tbody');
				tableHeaderRight.append(tbodySecond.html());*/
			}
			
			//组装品牌大类表体
			ajaxRequestAsync(BasePath + '/bill_sale_balance/brandCategoryDeduction.json?balanceNo='+billNo+"&print=1", null, function(result) {
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
							if(field=='sendAmount'){
								value=formatNumber(value);
							}else if(field=='deductionAmount'){
								value=formatNumber(value);
							}else if(field=='rebateAmount'){
								value=formatNumber(value);
							}
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
					$(grepColumns).each(function(i,node){
						var field = $(node).attr('field');
						var value = footerRow[field+''];
						if(field=='sendAmount'){
							value=formatNumber(value);
						}else if(field=='deductionAmount'){
							value=formatNumber(value);
							dialog.$('#DebitAmount').append(formatNumber(value));
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
			});
			
			/*//其它扣项表体
			ajaxRequestAsync(BasePath + "/other_deduction/list.json?balanceNo="+billNo, null, function(result) {
				if(result.rows!=undefined){
					var rows = result.rows;
					var q = 0;
					for (var i = 0; i < rows.length; i++) {
						q = i;
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumnsSecond).each(function(i,node){
							var field = $(node).attr('field');
							var value = row[field+''];
							if(field=="sendAmount"){
								var s=row['deductionAmount'+''];
								value=s.toFixed(2)+"";
							}
							bodyTdNode+='<td align="center">'+value+'</td>';
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBodyRight.append(bodyTrNode);
					}
				}
			});*/
			dialog.print_page(this);
			win.close();
		}
	});
}

function BillBalanceZoneBill() {
	var $this = this;
	
	// 检查是否可进行保存操作
	this.delBillFn = function(options, result) {
		if(result) {
			showSuc('删除成功');
			bill.toAddBill();
			$("#" + options.searchBtn).click();
		} else {
			showError('删除失败');
		}
	}
	// 检查是否可进行保存操作
	this.checkUpdate = function(options) {
		var flag = $this.super.checkUpdate.call(this, options);
		if(!flag) {
			return false;
		}
		var billStatus = $("#status").val();
		if(billStatus != '0' && billStatus != '99') {
			showWarn("只能保存制单或打回状态的单据！");
			return false;
		}
		return true;
	};
	
	// 检查是否可进行批量删除操作
	this.checkBatchDel = function(checkedRows) {
		var delFlag = $this.super.checkBatchDel.call(this, checkedRows);
		if(!delFlag) {
			return false;
		}
		var url = BasePath + "/bill_balance_zone/checkDel";
		var checkData = {};
		var checkResult = $.fas.ajaxRequestAsync(url, checkData, function(flag){
			if(!flag) {
				showError('数据不能删除!');
				return false;
			}
			return true;
		});
		return checkResult;
	};
	// 检查是否可进行确认操作
	this.checkConfirm = function(type, status) {
		var confirmFlag = $this.super.checkConfirm.call(this, type, status);
		if(!confirmFlag) {
			return false;
		}
		if(type == '0') {
			var billStatus = $("#status").val();
			if( billStatus == '6' || billStatus == '7') {
				showWarn("该单据已开票申请或者已开票,请勿操作！");
				return false;
			}
			if(status == billStatus && status == '3') {
				showWarn("该单据业务已确认,请勿重复操作！");
				return false;
			}
			if(status == billStatus && status == '4') {
				showWarn("该单据财务已确认,请勿重复操作！");
				return false;
			}
			if(status == '3' && billStatus == '4') {
				showWarn("该单据已财务确认,请选择其他单据！");
				return false;
			}
			if(status == '4' && (billStatus == '0' || billStatus == '99')) {
				showWarn("请先业务确认后，再进行财务确认操作！");
				return false;
			}
			return true;
		}else{
			var checkedRows = $("#balanceZoneDataGrid").datagrid("getChecked");
			var confirmFlag = true;
			$.each(checkedRows, function(index, item){
				if(status == '99' && item.status != '3' && item.status != '4') {
					showWarn("只允许打回已确认的单据！");
					confirmFlag = false;
					return false;
				}else if(status == '3' && item.status != '0' && item.status != '99'){
					showWarn("只允许业务确认制单或打回的单据！");
					confirmFlag = false;
					return false;
				}else if(status == '4' && item.status != '3'){
					showWarn("只允许确认业务确认过的单据！");
					confirmFlag = false;
					return false;
				}
			});
			return confirmFlag;
		}
		return true;
	};
	// 双击明细行
	this.loadDetail = function(rowIndex, rowData) {
		//清除历史数据
		$("#brandUnitName").combogrid("setValue","");
		$("#organNameFrom").combogrid("setValue","");
		$('#salerName').combogrid("grid").datagrid("reload");
		$('#brandUnitName').combogrid("grid").datagrid("reload");
		$('#organNameFrom').combogrid("grid").datagrid("reload");
		$this.super.loadDetail.call(this, rowIndex, rowData);
		if(rowData.organNameFrom != null && $.trim(rowData.organNameFrom) != '') {
			$("#organNameFrom").combogrid("setValues", rowData['organNameFrom'].split(','));
		}
		if(rowData.brandUnitName != null && $.trim(rowData.brandUnitName) != '') {
			$("#brandUnitName").combogrid("setValues", rowData['brandUnitName'].split(','));
		}
		$.fas.search({
			dataGridId : "balanceDtlDataGrid",
			searchUrl : "/bill_sale_balance/list.json?balanceNo="+rowData.billNo,
			hasSearchForm : false
		});
		if ($("#invoiceRebateAmount").val() <= 0) {
			$("#sumRebateAmount").val($("#rebateAmount").val());
			$("#isAfterBill").attr("checked",false);
		}else{
			$("#sumRebateAmount").val($("#invoiceRebateAmount").val());
			$("#isAfterBill").attr("checked",true);
		}
		
		
	};
	//清空生成结算单页面表单
	this.clearBatchForm = function(){
		$("#dataForm").form("clear");
		$("#batchBuyerNo").val("");
		$("#batchBrandUnitNo").val("");
		$("#batchBuyerName").combogrid("setValue","");
		$("#batchBrandUnitName").combogrid("setValue","");
		$("input:radio").eq(0).attr("checked",true);
		common_util.initDate();
	};
	// 批量生成结算单页面
	this.toBatchAddBill = function(){
		ygDialog({
			title : '批量生成结算单',
			target : $('#myFormPanel'),
			width : 580,
			height : 320,
			buttons : [{
				text : '确定',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.batchAddBill(dialog);
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					$this.clearBatchForm();
					dialog.close();
				}
			}]
		});
	};

	//批量新增的逻辑处理方法
	this.batchAddBill = function(dialog) {
		$.fas.add({
			dataFormId : "dataForm",
			url : "/bill_balance_zone/batchAddBill",
			validateForm : function(formObj) {
				var validateForm = formObj.form('validate');
			    if(validateForm == false) {
			        return false;
			    }
			    return true;
			},
			successAddFn : function(data, options) {
				$this.clearBatchForm();
				dialog.close();
				var resultData = JSON.parse(data);
				if(resultData.count > 0){
					showSuc("批量生成结算单成功!");
				}else{
					showWarn("当前没有业务发生!");
				}
			}
		});
	};
	//清空选单页面表单
	this.clearCreateBalanceForm = function(){
		$("#createBalanceForm").form("clear");
		$('#createBalanceFrom').find("input[name=brandUnitNo]").val("");
		$('#createBalanceFrom').find("input[name=multiOrganNo]").val("");
		$('#createBalanceFrom').find("input[name=organNoFrom]").val("");
		$('#createBalanceFrom').find("input[name=multiOrderUnitNo]").val("");
		$('#createBalanceFrom').find("input[name=brandUnitName]").combogrid('clear');
		$('#createBalanceFrom').find("input[name=organNameFrom]").combogrid('clear');
		common_util.initDate();
	};
	//选单
	this.toCreateBalance = function(dialog) {
		var isHq = $('#isHq').val();
		var url = BasePath + '/bill_balance_zone/to_custom_create_balance?isHq='+isHq;
		ygDialog({
			title : '选择关联单据',
			href : url,
			width : 1000,
			modal : true,
			showMask : true,
			isFrame : true,
			maximized : true,
			top : 0,
			buttons: [{
				id:'sure',
	            text: '生成结算单',
	            handler: function(dialog) {
	            	if($this.validateBalance()){
		            	var params = $this.getMainData();
		            	var checkedRows = $this.getCheckBill();
		            	params.isUserDefined = 1;
		            	params.checkedRows = JSON.stringify(checkedRows);
		            	ajaxRequestAsync( BasePath + '/bill_balance_zone/custom_create_balance', params ,function(data){
		            		if(data){
		            			$this.clearCreateBalanceForm();
				            	dialog.close();
								showSuc('保存成功');
								$this.addBillFn(JSON.stringify(data), $this.options);
							}else{
								$this.clearCreateBalanceForm();
				            	dialog.close();
								showError('保存失败');
							}
		            	});
	            	}
	            }
	        },
	        {
	            text: '取消',
	            handler: function(dialog) {
	            	$this.clearCreateBalanceForm();
	                dialog.close();
	            }
	        }],
			onLoad : function(win, content) {
				$this.getCheckBill = content.getCheckBill;
				$this.getMainData = content.getMainData;
				$this.validateBalance = content.validateBalance;
			}
		});
	};
	
	this.toBalanceAdjust = function(){
		var isHq = $('#isHq').val();
		if($('#billNo').val() == ""){
			showInfo('不存在当前单据!');
			return ;
		}
		if($('#status').val() == 6 || $('#status').val() == 4){
			showInfo('该单据财务确认或已开票,不允许在做调整!');
			return ;
		}
		var url = BasePath + '/bill_balance_zone/to_balance_adjust?isHq='+isHq;
		ygDialog({
			title : '扣项调整',
			href : url,
			width : 1000,
			height : 'auto',
			isFrame : true,
			modal : true,
			showMask : true,
			buttons: [{
				id:'sure',
	            text: '确认',
	            handler: function(dialog) {
	            	var checkedRows = $this.getCheckBill();
	            	var params = $('#mainDataForm').form('getData');
	            	params.checkedRows = JSON.stringify(checkedRows);
	            	ajaxRequestAsync( BasePath + '/bill_balance_zone/balance_adjust?isHq='+isHq, params, function(data){
	            		if(data){
			            	dialog.close();
							showSuc('保存成功');
							$this.addBillFn(JSON.stringify(data), $this.options);
						}else{
							 dialog.close();
							showError('保存失败');
						}
	            	});
	            }
	        },
	        {
	            text: '取消',
	            handler: function(dialog) {
	                dialog.close();
	            }
	        }],
			onLoad : function(win, content) {
				$this.getCheckBill = content.getCheckBill;
			}
		});
	}; 
	
	this.addBillFn = function(result, options){
		var jsonResult = JSON.parse(result);
		var errorInfo =  jsonResult.errorInfo;
		if(!errorInfo){
			showSuc('保存成功');
			$this.super.addBillFn.call(this, result, options);
			var resultData = JSON.parse(result);
			$('#brandUnitName').combogrid("grid").datagrid("reload");
			$('#organNameFrom').combogrid("grid").datagrid("reload");
			if(resultData.organNameFrom != null && $.trim(resultData.organNameFrom) != '') {
				$("#organNameFrom").combogrid("setValues", resultData['organNameFrom'].split(','));
			}
			if(resultData.brandUnitName != null && $.trim(resultData.brandUnitName) != '') {
				$("#brandUnitName").combogrid("setValues", resultData['brandUnitName'].split(','));
			}
			$.fas.search({
				dataGridId : "balanceDtlDataGrid",
				searchUrl : "/bill_sale_balance/list.json?balanceNo="+resultData.billNo,
				hasSearchForm : false
			});
			$this.setBillBottom(resultData);
			return true;
		}else{
			showError(errorInfo);
			return false;
		}
	}
	
	this.updateBillFn = function(result, options){
		var jsonResult = JSON.parse(result);
		var errorInfo =  jsonResult.errorInfo;
		if(!errorInfo){
			showSuc('保存成功');
			$.fas.search({
				dataGridId : "balanceDtlDataGrid",
				searchUrl : "/bill_sale_balance/list.json?balanceNo="+jsonResult.billNo,
				hasSearchForm : false
			});
			return true;
		}else{
			showError(errorInfo);
			return false;
		}
	}
	
	this.toAddBill = function() {
		$this.super.toAddBill.call($this);
		common_util.initDate();
		$('#currency').combobox('setValue', 0);
	}
}

function companyCallback(rows) {
	var salerNo = $("#salerNo").val();
	var buyerNo = $("#buyerNo").val();
	if(salerNo && buyerNo) {
		ajaxRequest(BasePath + "/bill_balance_zone/selectAmount", {salerNo : salerNo, buyerNo : buyerNo}, function(result) {
			if(result) {
				$("#openOrderAmount").val(parseFloat(result.totalOrderAmount - result.totalSendOutAmount).toFixed(2));
				$("#openPrePayment").val(parseFloat(result.totalPrePayment - result.totalSendOutAmount).toFixed(2));
				$("#outAmount").val(parseFloat(result.totalSendOutAmount).toFixed(2));
			}
		});
	}
};

var dialog = null, bill = null;

$(function() {
	var isHq = $('#isHq').val();
	$.fas.extend(BillBalanceZoneDialog, FasDialogController);
	$.fas.extend(BillBalanceZoneBill, FasBillController);
	dialog = new BillBalanceZoneDialog();
	dialog.init("/bill_balance_zone", {
		dataGridId : "balanceZoneDataGrid",
		searchFormId : "searchForm",
		searchUrl : "/list.json?balanceType=7&isHq="+isHq,
		delUrl :  "/delBill",
		exportUrl : "/export?balanceType=7&isHq="+isHq,
		exportTitle : "批发结算单信息"
	});
	
	bill = new BillBalanceZoneBill();
	bill.init("/bill_balance_zone", {
		listDataGridId : "balanceZoneDataGrid",
		dataFormId : 'mainDataForm',
		dtlDataGridIds : ["brandCategoryDataGrid","balanceDtlDataGrid", "invoiceDataGrid"],
		addUrl : "/addBill",
		updateUrl : "/updateBill",
		delUrl : "/delBill",
		confirmUrl : "/confirm",
		searchBtn : "bill-btn-search",
		dataFormFields : {
			input : ["billNo", "statusName", "billName", "openOrderAmount", "openPrePayment", "outAmount", "outQty", "remark", "sumRebateAmount", "invoiceRebateAmount","balanceDate","otherPrice"],
			date : ["balanceStartDate", "balanceEndDate","balanceDate"],
			combogrid : ["salerName", "buyerName", "organNameFrom", "brandUnitName"],
			combobox : ["currency"]
		},
		readOnlyFields : {
			input : ["billNo", "statusName", "openOrderAmount", "openPrePayment", "outAmount","outQty","balanceAmount","deductionAmount","sumRebateAmount","otherPrice"]
		}
	});
	
//	bill.toAddBill= function(){
//		$this.toAddBill();
//		common_util.initDate();
//		$('#currency').combobox('setValue', 0);
//	}
	
	// 加载单据列表页签
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : true,
		closable : false,
		href : BasePath + "/bill_balance_zone/listTab?isHq="+isHq,
		onLoad : function(panel) {
			// 这里需要在重写在加载完后做对应的事件
			common_util.initDate();
		}
	});
//	$('#dtlTab').tabs('add',{
//		title : '其他扣项',
//		selected : false,
//		closable : false,
//		href : BasePath + "/bill_balance_zone/deductionTab",
//		onLoad : function(panel) {
//			// 这里需要在重写在加载完后做对应的事件
//		}
//	});
	$('#dtlTab').tabs({
		onSelect : function(title) {
			var billNo = $("#billNo").val();
			if(typeof billNo == 'undefined' || $.trim(billNo) == '') {
				if($("#balanceDtlDataGrid").length > 0){
					deleteAllGridCommon("balanceDtlDataGrid");
				}
				if($("#invoiceDataGrid").length > 0){
					deleteAllGridCommon("invoiceDataGrid");
				}
				if($("#deductionDataGrid").length > 0){
					deleteAllGridCommon("deductionDataGrid");
				}
				if($("#brandCategoryDataGrid").length > 0){
					deleteAllGridCommon("brandCategoryDataGrid");
				}
				
			} else{
				if('出库明细' == title) {
					$.fas.search({
						dataGridId : "balanceDtlDataGrid",
						searchUrl : "/bill_sale_balance/list.json?balanceNo="+billNo,
						hasSearchForm : false
					});
				}else if('发票信息' == title) {
					$.fas.search({
						dataGridId : "invoiceDataGrid",
						searchUrl : "/bill_balance_invoice_apply/getByBalanceNo.json?balanceNo="+billNo,
						hasSearchForm : false
					});
				}else if('其他扣项' == title) {
					setTimeout(
						function(){
							$.fas.search({
								dataGridId : "deductionDataGrid",
								searchUrl : "/other_deduction/list.json?balanceNo="+billNo,
								hasSearchForm : false
							});	
						},200);
				}else if ('品牌大类扣项汇总') {
					$.fas.search({
						dataGridId : "brandCategoryDeductionDataGrid",
						searchUrl : "/bill_sale_balance/brandCategoryDeduction.json?balanceNo="+billNo,
						hasSearchForm : false
					});
				}
			}
		}
	});
	bill.toAddBill();
	$('#mainTab').tabs('hideHeader');
	
	/*$('#rebateAmount').bind('blur',function(){
		var outAmount = $('#outAmount').val() == '' ? 0: $('#outAmount').val();
		var deductionAmount = $('#deductionAmount').val() == '' ? 0: $('#deductionAmount').val();
		var sumRebateAmount = $('#sumRebateAmount').val() == '' ? 0: $('#sumRebateAmount').val();
		var balanceAmount = parseFloat(outAmount) -  parseFloat(deductionAmount) -  parseFloat(sumRebateAmount);
		$('#balanceAmount').val(balanceAmount);
	});*/
	$.fas.tooltip({
		id : 'systemBillingAmountTooltip',
		content : '应收金额=出库金额+其他费用-其他扣项-票前返利'
	});
	
	//以超链接方式直接访问详细页面
	var isHq = $('#isHq').val();
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/bill_balance_zone/list.json',{billNo:billNoMenu,balanceType:7,isHq:isHq},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					bill.loadDetail(0, obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	//加载预警列表
	loadWarnMessageList();
});

function selectChange() {
	var outAmount = $('#outAmount').val() == '' ? 0: $('#outAmount').val();
	var otherPrice = $('#otherPrice').val() == '' ? 0: $('#otherPrice').val();
	var deductionAmount = $('#deductionAmount').val() == '' ? 0: $('#deductionAmount').val();
	var sumRebateAmount = $('#sumRebateAmount').val() == '' ? 0: $('#sumRebateAmount').val();
	//如果勾选中则为票后返利
	if ($('#isAfterBill').attr("checked")) {
		var balanceAmount = parseFloat(outAmount) -  parseFloat(deductionAmount) + parseFloat(otherPrice);
		$('#rebateAmount').val(0);
		$('#invoiceRebateAmount').val(sumRebateAmount);
	}else{
		var balanceAmount = parseFloat(outAmount) -  parseFloat(deductionAmount) -  parseFloat(sumRebateAmount) + parseFloat(otherPrice);
		$('#rebateAmount').val(sumRebateAmount);
		$('#invoiceRebateAmount').val(0);
	}		
	$('#balanceAmount').val(balanceAmount);
}

function loadWarnMessageList(){
	var warnPostUrl = $("#warnPostUrl").val();
	if(warnPostUrl != null && warnPostUrl != ''){
		warnPostUrl = warnPostUrl.replaceAll(":", "&");
		if($('#balanceZoneDataGrid').length>0){
			$('#balanceZoneDataGrid').datagrid('options').url= BasePath + warnPostUrl;
			$('#balanceZoneDataGrid').datagrid('load');
		}else{
			setTimeout("loadWarnMessageList()", 3000);
		}
	}
}

