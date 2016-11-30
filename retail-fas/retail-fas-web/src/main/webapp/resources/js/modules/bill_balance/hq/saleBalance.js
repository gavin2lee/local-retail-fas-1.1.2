var saleBalance ;

var balanceParams = {
		mainTabUrl : BasePath + '/bill_balance/type2/sale_tabMain.htm',
		listUrl : BasePath + '/bill_balance/hq/list.json' ,
		addUrl : BasePath + '/bill_balance/hq/add',	
		updateUrl : BasePath + '/bill_balance/hq/update',	
		deleteUrl : BasePath + '/bill_balance/hq/delete',
		verifyUrl : BasePath + '/bill_balance/verify',
		balanceType : 2,
		dtlDataGrids : [
		    {id:'saleOutDataGrid',
			 title:'出库明细', 
			 tabUrl:BasePath + '/bill_balance/hq/sale_tab_out.htm',
			 listUrl:BasePath + '/bill_balance/hq/sale_tab_out.json',
			 queryParams:{multiBillType:'(1301,1371)'}
		    },
		    {id:'saleReturnDataGrid',
			 title:'退残明细', 
			 tabUrl:BasePath + '/bill_balance/hq/sale_tab_return.htm',
			 listUrl:BasePath + '/bill_balance/hq/sale_tab_return.json',
			 queryParams:{multiBillType:'(1333)'}
			},
		    {id:'deductionDataGrid',
			 title:'其他扣项', 
			 tabUrl:BasePath + '/bill_balance/hq/sale_tab_deduction.htm',
			 listUrl:BasePath + '/bill_balance/hq/sale_tab_deduction.json'
			},
			{id:'saleInvoiceDataGrid',
			 title:'发票明细', 
			 tabUrl:BasePath + '/bill_balance/hq/sale_tab_invoice.htm',
			 listUrl:BasePath + '/bill_balance/hq/sale_tab_invoice.json',
		    },
			{id:'saleGatherDataGrid',
			 title:'明细汇总', 
			 tabUrl:BasePath + '/bill_balance/hq/to_item_sale_gather.htm',
			 listUrl:BasePath + '/bill_balance/hq/item_gather.json',
			},
			{id:'peAdjustDataGrid',
				 title:'采购调整单', 
				 tabUrl:BasePath + '/pe_balance/pe_tab_return.htm',
				 listUrl:BasePath + '/bill_purchase_adjust/dtl_list.json'
			}
		]
	};

function generateAskPaymentBill(){
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkRows.length == 0){
		showInfo("请选中需要生成请款单的结算单据(未经过请款且财务确认)!");
		return ; 
	}
	var flag = false;
	var errorMessage = "";
	$.each(checkRows,function(index, row){
		if(!((row.status == 4 || row.status == 6) && (row.askPaymentNo =='' || row.askPaymentNo==null))){
			errorMessage = "请选择未请款并且财务确认的结算单!";
			return false;
		}
	});
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	$.messager.confirm("确认","确认根据选中的结算单生成请款单?",function(r) {
		if (r) {
			var url = BasePath +  "/bill_ask_payment/generate_bill_by_balance";
			var params = {checkRows : JSON.stringify(checkRows),balanceType : 1};
			ajaxRequestAsync(url, params, function(data){
				if(data > 0){
					saleBalance.search();
					showSuc('成功生成'+data+'条请款单');
				}else{
					showError('生成失败');
				}
			});
		}
	});
};

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

function setVal(dialog){
	var data = $('#mainDataForm').form('getData');
	dialog.$('#billNo').append(data.billNo);
	dialog.$('#statusName').append(data.statusName);
	dialog.$('#billName').append(data.billName);
	var currency=data.currency;
	if(currency=='0'){
		dialog.$('#currency').append("人民币");
	}else if(currency=='1'){
		dialog.$('#currency').append("美元");
	}else if(currency=='2'){
		dialog.$('#currency').append("欧元");
	}else if(currency=='3'){
		dialog.$('#currency').append("日元");
	}else if(currency=='4'){
		dialog.$('#currency').append("英镑");
	}else if(currency=='5'){
		dialog.$('#currency').append("港币");
	}
	dialog.$('#buyerName').append(data.buyerName);
	dialog.$('#salerName').append(data.salerName);
	dialog.$('#brandUnitName').append(data.brandUnitName);
	dialog.$('#categoryName').append(data.categoryName);
	dialog.$('#balanceDate').append(data.balanceDate);
	dialog.$('#outQty').append(data.outQty);
	dialog.$('#returnQty').append(data.returnQty);
	dialog.$('#deductionQty').append(data.deductionQty);
	dialog.$('#balanceQty').append(data.balanceQty);
	dialog.$('#outAmount').append(data.outAmount);
	dialog.$('#returnAmount').append(data.returnAmount);
	dialog.$('#deductionAmount').append(data.deductionAmount);
	dialog.$('#balanceAmount').append(data.balanceAmount);
	dialog.$('#extendCategoryName').append(data.extendCategoryName);
	dialog.$('#remark').append(data.remark);
	dialog.$('#createUser').append($('#createUser').text());
	dialog.$('#auditor').append($('#auditor').text());
	dialog.$('#createTime').append($('#createTime').text());
	dialog.$('#auditTime').append($('#auditTime').text());
	dialog.$('#balanceStartDate').append(data.balanceStartDate);
	dialog.$('#balanceEndDate').append(data.balanceEndDate);
	dialog.$('#printTime').append(getCurrentNowTime());
}

function print(){
	var previewUrl = BasePath + '/print/preview?viewName=headquarters_area_print';
	var $dg = $('#exportExcelDG');
	var columns  = $dg.datagrid('options').columns;
	var billNo = $('#billNo').val();
	ygDialog({
		isFrame: true,
		cache : false,
		title : '总部厂商结算单',
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
				setVal(dialog);
			}
			//组装明细汇总表体
			ajaxRequestAsync(BasePath + '/bill_balance/hq/item_gather.json?balanceNo='+billNo+'&balanceType=2&print=1',null, function(result) {
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

// 初始化
$(function(){
	saleBalance = new BillBalanceController();
	if($("#isArea").val() =="true"){// 地区
		balanceParams.mainTabUrl = BasePath + '/bill_balance/type4/sale_tabMain.htm';
		saleBalance.init(balanceParams);
		saleBalance.initPage();
		$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
		$("#mainDataForm").find("input[combobox]").combobox("disable");
		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
	}else{
		saleBalance.init(balanceParams);
		saleBalance.initPage();
	}
	
	//以超链接方式直接访问详细页面
	var isArea = $('#isArea').val();
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		var params = {billNo:billNoMenu};
		if(isArea == 'true'){
			params = {billNo:billNoMenu,isArea:true};
		}
		ajaxRequestAsync( BasePath + '/bill_balance/hq/list.json',params,function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				saleBalance.loadMainData(obj);
				saleBalance.loadDtlDataGrid(balanceParams.dtlDataGrids[0]);
	    		returnTab('dtlTab', '出库明细');
	    		saleBalance.resetEditClass();
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	//加载预警列表
	loadWarnMessage();
	
});
