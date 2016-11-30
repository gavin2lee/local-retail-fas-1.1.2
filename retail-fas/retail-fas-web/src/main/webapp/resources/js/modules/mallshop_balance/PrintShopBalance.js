// add by Ning.ly
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
	return ((arg1 * m + arg2 * m) / m);       
} 

//js 加法计算  
//调用：accAdd(arg1,arg2)  
//返回值：arg1加arg2的精确结果   
//function accAdd(arg1,arg2){   
//	var r1,r2,m;   
//	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}   
//	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}   
//	m=Math.pow(10,Math.max(r1,r2))   
//	return ((arg1*m+arg2*m)/m).toFixed(2);   
//} 

function setval(dialog){
	dialog.$('#printTime').append(getCurrentNowTime());
	dialog.$('#actualRateName').append($('#actualRateName').val());
	dialog.$('#mallNumberAmount').append($('#mallNumberAmount').val());
	dialog.$('#contractRateName').append($('#contractRateName').val());
	dialog.$('#salesDiffamount').append($('#salesDiffamount').val());
	dialog.$('#systemSalesAmount').append($('#systemSalesAmount').val());
	dialog.$('#mallBillingAmount').append($('#mallBillingAmount').val());
	dialog.$('#balanceDesc').append($('#balanceDesc').val());
	dialog.$('#startEndDate').append($('#startEndDate').val());
	var usedPrepaymentAmount = accAdd($('#prepaymentAmount').val(),$('#usedPrepaymentAmount').val());
	dialog.$('#usedPrepaymentAmount').append(usedPrepaymentAmount.toFixed(2));
	findBalanceNo($('#balanceNo').val(),dialog);
}

/*
 * 查询结算单打印链表数据
 */
function findBalanceNo(balanceNo,dialog){
	var b= null;
	if(balanceNo!=null && balanceNo!= ''){
		ajaxRequestAsync( BasePath + '/mall_shopbalance/shopbalance_list_main.json',{balanceNo:balanceNo},function(data){
			if(data.rows.length > 0){
				dialog.$('#mallName').append(data.rows[0].mallName);
				dialog.$('#shopNo').append(data.rows[0].shortNo);
				dialog.$('#shortName').append(data.rows[0].shortName);
				dialog.$('#brandName').append(data.rows[0].brandName);
				dialog.$('#invoiceNo').append(data.rows[0].invoiceNo);
				dialog.$('#invoiceApplyNo').append(data.rows[0].invoiceApplyNo);
				dialog.$('#invoiceApplyDate').append(data.rows[0].invoiceApplyDate);
				dialog.$('#companyName').append(data.rows[0].companyName);
				dialog.$('#remark').append(data.rows[0].remark);
				dialog.$('#taxpayerNo').append(data.rows[0].taxpayerNo);
				dialog.$('#billStatusName').append(data.rows[0].billStatusName);
				/*dialog.$('#bsgroupsName').append(data.rows[0].bsgroupsName);*/
				dialog.$('#invoiceName').append(data.rows[0].invoiceName);
				dialog.$('#createUser').append(data.rows[0].createUser);
				dialog.$('#updateUser').append(data.rows[0].updateUser);
				dialog.$('#auditor').append(data.rows[0].auditor);
			}
		});
	}
}

function printDialog(winOpts) {
	var previewUrl = BasePath + '/print/preview?viewName='+winOpts.viewName;
	var $dg = $('#' + winOpts.dataGridId);
	var $dgSecond = $('#' + winOpts.sencondDateGrid);
	var $dgDown = $('#' + winOpts.downDateGrid);
	var columns  = $dg.datagrid('options').columns;
	var columnsSecond  = $dgSecond.datagrid('options').columns;
	var columnsDown  = $dgDown.datagrid('options').columns;
	var mallBillingAmount = $('#mallBillingAmount').val();
	
	//打开待打印数据页面
	ygDialog({
		isFrame: true,
		cache : false,
		title : winOpts.title,
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
			var tableHeaderRight = dialog.$('#tableHeaderSecond');
			var tableBodyRight  =  dialog.$('#tableBodySecond');
			var tableHeaderDown = dialog.$('#tableHeaderDown');
			var tableBodyDown  =  dialog.$('#tableBodyDown');
			var columnsLength = columns.length;
			var columnsDownLength = columnsDown.length;
			var columnsSecondLength = columnsSecond.length;
			
			//1过滤表头不需要打印的column 静态表头配置了printable=false都将不打印
			var grepColumns = $.grep(columns[columnsLength - 1], function(o, i) {
				if ($(o).attr('printable') == false) {
					return true;
				}
				return false;
			}, true);
			//过滤大类列头
			var filterColumns = [];
			/*$.each(columns, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumns.push(n);
				}
			});*/
			filterColumns.push(grepColumns);
			
			//过滤活动列头
			var grepColumnsSecond = $.grep(columnsSecond[columnsLength - 1], function(o, i) {
				if ($(o).attr('printable') == false) {
					return true;
				}
				return false;
			}, true);
			//过滤列头
			var filterColumnsSecond = [];
			/*$.each(columnsSecond, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumnsSecond.push(n);
				}
			});*/
			filterColumnsSecond.push(grepColumnsSecond);
			
			//过滤票前列头
			var grepColumnsDown = $.grep(columnsDown[columnsDownLength - 1], function(o, i) {
				if ($(o).attr('printable') == false) {
					return true;
				}
				return false;
			}, true);
			//过滤列头
			var filterColumnsDown = [];
			/*$.each(columnsDown, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumnsDown.push(n);
				}
			});*/
			filterColumnsDown.push(grepColumnsDown);
			
			//组装列大类,促销活动 表头
			if(columnsLength >= 1){
				//大类
				dialog.$("#hiddenDiv").remove();
				dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
				var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
				tableHeader.append(tbody.html());
				
//			}
//			if(columnsSecondLength >=1) {
				//活动
				dialog.$("#hiddenDivRight").remove();
				dialog.$("<div id='hiddenDivRight' style='display:none;'><table id='exportExcelDGRight' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDGRight").datagrid({columns : filterColumnsSecond});
				dialog.$('#exportExcelDGRight').datagrid('showColumn','seqNo');
				dialog.$('#exportExcelDGRight').datagrid('showColumn','actualAmount');
				dialog.$('#exportExcelDGRight').datagrid('showColumn','balanceDiffAmount');
				setval(dialog);
				var tbodySecond = dialog.$("#exportExcelDGRight").prev(0).find('.datagrid-htable').find('tbody');
				tableHeaderRight.append(tbodySecond.html());
//			}
//			if(columnsDownLength >=1) {
				//综合店
				dialog.$("#hiddenDivDown").remove();
				dialog.$("<div id='hiddenDivDown' style='display:none;'><table id='exportExcelDGDown' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDGDown").datagrid({columns : filterColumnsDown});
				var tbodyDown = dialog.$("#exportExcelDGDown").prev(0).find('.datagrid-htable').find('tbody');
				tableHeaderDown.append(tbodyDown.html());
			}
			//组装大类表体
			ajaxRequestAsync(BasePath + winOpts.queryDataUrl, winOpts.params+"&print=1", function(result) {
				if(result.rows!=undefined){
					var rows = result.rows;
					dialog.params = rows;
					dialog.params.title = winOpts.title;
					dialog.params.intOrient = winOpts.intOrient;
					if('' != winOpts.barcode){
						dialog.params.barcode = winOpts.barcode;
					}
					var number = 0;
					var saleMoney =0;
					var invoiceMoney =0;
					for ( var i = 0; i < rows.length; i++) {
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumns).each(function(i,node){
							var field=$(node).attr('field');
							var value = row[field+''];
							if(field =='saleQty'){
								number+=value;
								bodyTdNode+='<td align="right">'+value+'</td>';
							}else if(field =='saleAmount'){
								var s = accAdd(saleMoney, value);
								saleMoney = s;
								bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
							}else if(field =='billingAmount'){
								var s = accAdd(invoiceMoney, value);
								invoiceMoney = s;
								bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
							}else if (typeof(value) == 'undefined' || null == value) {
								value = '';
								bodyTdNode+='<td align="left">'+value+'</td>';
							}else{
								bodyTdNode+='<td align="center">'+value+'</td>';
							}	
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBody.append(bodyTrNode);
					}
					//add by Ning.ly 添加合计项 2014/1/15
					var bodyTrNode2 ='<tr>';
					var bodyTdNode2 = '';
					for(j=0;j<grepColumns.length;j++){
							var value = "";
							var field=grepColumns[j].field;
							if(field!=null && field!=""){
								if(field=='brandName'){
									value='合计：';
								}
								if(field =='saleQty'){
									value = number;
								}
								if(field =='saleAmount'){
									var s = saleMoney.toFixed(2)+"";
									value=s;
									if(s.indexOf(".")>0){
										value =  s.substring(0,s.indexOf(".") + 3);
									}
									value=formatNumber(value);
								}
								if(field =='billingAmount'){
									value=mallBillingAmount;
									value=formatNumber(value);
								}
								bodyTdNode2+='<td align="right">'+value+'</td>';
							}
					}
					bodyTrNode2+=bodyTdNode2+'</tr>';
					//填充表体
					tableBody.append(bodyTrNode2);
				}
			});
			//组装活动表体
			ajaxRequestAsync(BasePath + winOpts.selectProSum, winOpts.params+"&print=1", function(result) {
				if(result.rows!=undefined){
					var rows = result.rows;
					var q = 0;
					var saleMoneys =0;
					var costMoney =0;
					var invoiceMoney =0;
					var actualAmount =0;
					var systemBillingAmount=0;
					var balanceDiffAmount=0;
					for (var i = 0; i < rows.length; i++) {
						q = i;
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumnsSecond).each(function(i,node){
							var field = $(node).attr('field');
							var value = row[field+''];
							if(field == 'seqNo'){
								value = q+1;
								bodyTdNode+='<td align="left">'+value+'</td>';
							}else if(field =='deductAmount'){
								var s = accAdd(costMoney, value);
								costMoney = s;
								bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
							}else if(field =='saleAmount'){
								var s = accAdd(saleMoneys, value);
								saleMoneys = s;
								bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
							}else if(field =='systemBillingAmount'){
								var s = accAdd(systemBillingAmount, value);
								systemBillingAmount = s;
								bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
							}else if(field =='billingAmount'){
								var s = accAdd(invoiceMoney, value);
								invoiceMoney = s;
								bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
							}else if (typeof(value) == 'undefined' || null == value) {
								value = '';
								bodyTdNode+='<td align="left">'+value+'</td>';
							}else{
								bodyTdNode+='<td align="center">'+value+'</td>';
							}	
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBodyRight.append(bodyTrNode);
					}
					
					//组装票前费用的实际扣费金额
					ajaxRequestAsync(BasePath + winOpts.findDiffUrl, winOpts.params, function(result) {
						if(result.rows!=undefined){
							var rows = result.rows;
							for (var i = 0; i < rows.length; i++) {
								var row = rows[i];
								$.each(grepColumnsSecond, function(i, node) {
									var field = $(node).attr('field');
									var value = row[field+''];
									if(field == 'actualAmount'){
										var s = accAdd(actualAmount, value);
										actualAmount = s;
									}
								});
							}
							
							//add by Duan.cw添加票前费用实际扣费金额2015/7/7
							var bodyTrNodeTicket ='<tr>';
							var bodyTdNodeTicket = '';
							for(c=0;c<grepColumnsSecond.length;c++){
								var value ="";
								var field=grepColumnsSecond[c].field;
								if(field!=null && field!=""){
									if(field =='actualAmount'){
										var s = actualAmount.toFixed(2)+"";
										value=s;
										value=formatNumber(value);
									}
									if(field =='balanceDiffAmount'){
										balanceDiffAmount =$('#balanceDiffAmount').val();
										value=balanceDiffAmount;
										value=formatNumber(value);
									}
									if(field =='billingAmount'){
										var g=0;
										var s=g.toFixed(2)+"";
										value=s;
										/*invoiceMoney = accAdd(invoiceMoney, value);*/
										value=formatNumber(value);
									}
									bodyTdNodeTicket+='<td align="right">'+value+'</td>';
								}
							}
							bodyTrNodeTicket+=bodyTdNodeTicket+'</tr>';
							//填充表体
							tableBodyRight.append(bodyTrNodeTicket);
						}
					});
					
					//add by Ning.ly 添加合计项 2014/1/15
					var bodyTrNodeSencod ='<tr>';
					var bodyTdNodeSecond = '';
					for(c=0;c<grepColumnsSecond.length;c++){
						var value ="";
						var field=grepColumnsSecond[c].field;
						if(field!=null && field!=""){
							if(field=='seqNo'){
								value='合计：';
							}
							if(field =='saleAmount'){
								var s = saleMoneys.toFixed(2)+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
								value=formatNumber(value);
							}
							if(field =='balanceDiffAmount'){
								value=$('#balanceDiffAmount').val();
								value=formatNumber(value);
							}
							if(field =='deductAmount'){
								var s = costMoney.toFixed(2)+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
								value=formatNumber(value);
							}
							if(field =='actualAmount'){
								var s = actualAmount.toFixed(2)+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
								value=formatNumber(value);
							}if(field =='systemBillingAmount'){
								var s = systemBillingAmount.toFixed(2)+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
								value=formatNumber(value);
							}
							if(field =='billingAmount'){
								var s = invoiceMoney.toFixed(2)+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
								value=formatNumber(value);
							}
							bodyTdNodeSecond+='<td align="right">'+value+'</td>';
						}
					}
					bodyTrNodeSencod+=bodyTdNodeSecond+'</tr>';
					//填充表体
					tableBodyRight.append(bodyTrNodeSencod);
				}
			});
			
			//组装综合店表体
			ajaxRequestAsync(BasePath + '/bill_shop_balance_brand/list.json',winOpts.params+"&print=1", function(result) {
				if(result.rows!=undefined && result.rows.length>0){
					var rows = result.rows;
					var footerRows = result.footer;
					for ( var i = 0; i < rows.length; i++) {
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumnsDown).each(function(i,node){
							var field = $(node).attr('field');
							var value = row[field+''];
							if(field == 'remark'){
								if(value==undefined){
									value='';
								}
								bodyTdNode+='<td align="center">'+value+'</td>';
							}else if(field == 'reason'){
								if(value==undefined){
									value='';
								}
								bodyTdNode+='<td align="center">'+value+'</td>';
							}else{
								if(value==undefined || null == value){
									value='';
								}
								bodyTdNode+='<td align="right">'+value+'</td>';
							}
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableHeaderDown.append(bodyTrNode);
					}
					
					//添加合计项 
					var bodyTrNode2 ='<tr>';
					var bodyTdNode2 = '';
					var footerRow = footerRows[0];
					if(footerRow!=undefined){
						$(grepColumnsDown).each(function(i,node){
							var field = $(node).attr('field');
							var value = footerRow[field+''];
							if(value==undefined){
								bodyTdNode2+='<td align="center">'+''+'</td>';
							}else{
								bodyTdNode2+='<td align="right">'+value+'</td>';
							}
						});
						bodyTrNode2+=bodyTdNode2+'</tr>';
						tableHeaderDown.append(bodyTrNode2);
					}
				}
			});
			
			/*//组装票前表体
			ajaxRequestAsync(BasePath + winOpts.findDiffUrl, winOpts.params, function(result) {
				if(result.rows!=undefined){
					var rows = result.rows;
					var q = 0;
					var deductAmount =0;
					var actualAmount =0;
					var diffAmount =0;
					for (var i = 0; i < rows.length; i++) {
						q = i;
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumnsDown).each(function(i,node){
							var field = $(node).attr('field');
							var value = row[field+''];
							if(field == 'costType'){
								if(value=='1'){
									value="合同内";
								}
								if(value=='2'){
									value="合同外";
								}
							}
							if(field == 'processStatus'){
								if(value=='1'){
									value="未完成";
								}
								if(value=='2'){
									value="已完成";
								}
							}
							if(field == 'deductAmount'){
								var s = accAdd(deductAmount, value);
								deductAmount = s;
							}
							if(field == 'actualAmount'){
								var s = accAdd(actualAmount, value);
								actualAmount = s;					
							}
							if(field == 'diffAmount'){
								var s = accAdd(diffAmount, value);
								diffAmount = s;
							}
							if (typeof(value) == 'undefined' || null == value) {
								value = '';
							}
							bodyTdNode+='<td>'+value+'</td>';
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBodyDown.append(bodyTrNode);
					}
					//add by Duan.cw 添加合计项 2015/6/20
					var bodyTrNodeDown ='<tr>';
					var bodyTdNodeDown = '';
					for(c=0;c<grepColumnsDown.length;c++){
						var value ="";
						var field=grepColumnsDown[c].field;
						if(field!=null && field!=""){
							if(field=='month'){
								value='合计：';
							}
							if(field =='deductAmount'){
								var s = deductAmount+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
							}
							if(field =='actualAmount'){
								var s = actualAmount+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
							}
							if(field =='diffAmount'){
								var s = diffAmount+"";
								value=s;
								if(s.indexOf(".")>0){
									value =  s.substring(0,s.indexOf(".") + 3);
								}
							}
							bodyTdNodeDown+='<td>'+value+'</td>';
						}
					}
					bodyTrNodeDown+=bodyTdNodeDown+'</tr>';
					//填充表体
					tableBodyDown.append(bodyTrNodeDown);
				}
			});*/
			dialog.print_page(this);
			/*win.close();*/
		}
	});
};

function batchSetval(dialog,object){
	dialog.$('#printTime').append(getCurrentNowTime());
	dialog.$('#actualRateName').append(object.actualRateName);
	dialog.$('#mallNumberAmount').append(object.mallNumberAmount);
	dialog.$('#contractRateName').append(object.contractRateName);
	dialog.$('#salesDiffamount').append(object.salesDiffamount);
	dialog.$('#systemSalesAmount').append(object.systemSalesAmount);
	dialog.$('#mallBillingAmount').append(object.mallBillingAmount);
	dialog.$('#balanceDesc').append(object.balanceDesc);
	dialog.$('#startEndDate').append(object.balanceStartDate+"-"+object.balanceEndDate);
	var usedPrepaymentAmount = accAdd(object.prepaymentAmount,object.usedPrepaymentAmount);
	dialog.$('#usedPrepaymentAmount').append(usedPrepaymentAmount.toFixed(2));
	findBalanceNo(object.balanceNo,dialog);
}

function batchClear(dialog){
	dialog.$('#printTime').html('');
	dialog.$('#actualRateName').html('');
	dialog.$('#mallNumberAmount').html('');
	dialog.$('#contractRateName').html('');
	dialog.$('#salesDiffamount').html('');
	dialog.$('#systemSalesAmount').html('');
	dialog.$('#mallBillingAmount').html('');
	dialog.$('#balanceDesc').html('');
	dialog.$('#startEndDate').html('');
	dialog.$('#mallName').html('');
	dialog.$('#shopNo').html('');
	dialog.$('#shortName').html('');
	dialog.$('#brandName').html('');
	dialog.$('#invoiceNo').html('');
	dialog.$('#invoiceApplyNo').html('');
	dialog.$('#invoiceApplyDate').html('');
	dialog.$('#companyName').html('');
	dialog.$('#remark').html('');
	dialog.$('#taxpayerNo').html('');
	dialog.$('#billStatusName').html('');
	dialog.$('#invoiceName').html('');
	dialog.$('#createUser').html('');
	dialog.$('#updateUser').html('');
	dialog.$('#tableHeader').html('');
	dialog.$('#tableBody').html('');
	dialog.$('#tableHeaderSecond').html('');
	dialog.$('#tableBodySecond').html('');
	dialog.$('#tableHeaderDown').html('');
	dialog.$('#tableBodyDown').html('');
	dialog.$('#usedPrepaymentAmount').html('');
	dialog.$('#auditor').html('');
}


//门店结算单打印
function batchPrintBalanceOrder() {	
	var mainDataGrid = $('#shopBalanceDataGrid').datagrid('getChecked');
	var dgt=$("#shopBalanceDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据为空!");
		return;
	}
	if(mainDataGrid.length==0){
		alert("请选择要批量打印的数据");
	}else{
		var printCol = [{
			text : "默认_A4纸",
			value : "Default-A4",
			label : 1
		},{
			text : "默认_A5纸",
			value : "Default-A5",
			label : 2
		},{
			text : "华北模板",
			value : "HB",
			label : 3
		}];
		$.messager.confirm("提示","是否打印选中的单据?",function(r) {
			if(r){
//				if(mainDataGrid.length>1){
//					showSuc('已在打印，请查看打印机。');
//				}
		 //初始化框架数据
		$('#printHBSelect').empty();
		$.each(printCol,function(index,item){
			$('#printHBForm #printHBSelect').append("<option value="+item.value+">"+item.text+"</option>");
		});
		ygDialog({
			title : '门店结算单打印',
			target : $('#printHBContrPanel'),
			width : 225,
			height : 300,
			buttons : [{
						text : '打印',
						iconCls : 'icon-print',
						handler : function(dialog) {
							var selected = $('#printHBSelect').val();
							if(selected =="Default-A4"){
								var winOpts = {
										'dataGridId' : "categoryDataGrid",
										'sencondDateGrid' :"promotionsDataGrid",
										'downDateGrid' :"balanceBrandDataGrid",
										'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
										'type' : 0, 		// 0：简单打印，1：动态打印
										'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
										'queryDataUrl' : '/bill_shop_balance_cate_sum/list.json'+"?rows=50",
										'selectProSum' : '/bill_shop_balance_pro_sum/list.json'+"?rows=100",
										'findDiffUrl' : '/bill_shop_balance_deduct/list.json'+"?rows=100",
										'title' : "门店结算单",
										'viewName' : "shop_balance_print"
								};
								batchPrintDialog(winOpts,mainDataGrid);
							}
							if(selected =="Default-A5"){
								var winOpts = {
										'dataGridId' : "categoryDataGrid",
										'sencondDateGrid' :"promotionsDataGrid",
										'downDateGrid' :"balanceBrandDataGrid",
										'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
										'type' : 0, 		// 0：简单打印，1：动态打印
										'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
										'queryDataUrl' : '/bill_shop_balance_cate_sum/list.json'+"?rows=50",
										'selectProSum' : '/bill_shop_balance_pro_sum/list.json'+"?rows=100",
										'findDiffUrl' : '/bill_shop_balance_deduct/list.json'+"?rows=100",
										'title' : "门店结算单",
										'viewName' : "shop_balance_print_A5"
								};
								batchPrintDialog(winOpts,mainDataGrid);
							}
							//华北模板导出
							if(selected =="HB"){
								var winOpts = {
										'dataGridId' : "balanceDiffDataGrid",
										'sencondDateGrid' :"balanceDeductDataGrid",
										'downDateGrid' :"balanceDeductAfterDataGrid",
										'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
										'type' : 0, 		// 0：简单打印，1：动态打印
										'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
										'queryDataUrl' : '/bill_shop_balance_diff/list.json'+"?rows=50",
										'selectProSum' : '/bill_shop_balance_deduct/list.json'+"?costDeductType=1&rows=100",
										'findDiffUrl' : '/bill_shop_balance_deduct/list.json'+"?costDeductType=2&rows=100",
										'title' : "门店结算单",
										'viewName' : "shop_balance_print_hb"
								};
								batchPrintDialogHb(winOpts,mainDataGrid);
							}
						}
		        	}]
		    	});
	         }
         })
   	}
}

//门店结算单批量打印
function batchPrintBalanceOrderOld() {
	var mainDataGrid = $('#shopBalanceDataGrid').datagrid('getChecked');
	if(mainDataGrid.length==0){
		alert("请选择要批量打印的数据");
	}else{
		$.messager.confirm("提示","是否打印选中的单据?",function(r) {
			if(r){
				if(mainDataGrid.length>1){
					showSuc('已在打印，请查看打印机。');
				}
				var winOpts = {
						'dataGridId' : "categoryDataGrid",
						'sencondDateGrid' :"promotionsDataGrid",
						'downDateGrid' :"balanceBrandDataGrid",
						'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
						'type' : 0, 		// 0：简单打印，1：动态打印
						'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
						'queryDataUrl' : '/bill_shop_balance_cate_sum/list.json?rows=50',
						'selectProSum' : '/bill_shop_balance_pro_sum/list.json?rows=100',
						'findDiffUrl' : '/bill_shop_balance_deduct/list.json?rows=100',
						'title' : "门店结算单",
						'viewName' : "shop_balance_print"
				};
				batchPrintDialog(winOpts,mainDataGrid);
			}
		});
	}
	
}


function batchPrintDialog(winOpts,mainDataGrid) {
	var previewUrl = BasePath + '/print/preview?viewName='+winOpts.viewName;
	var $dg = $('#' + winOpts.dataGridId);
	var $dgSecond = $('#' + winOpts.sencondDateGrid);
	var $dgDown = $('#' + winOpts.downDateGrid);
	var columns  = $dg.datagrid('options').columns;
	var columnsSecond  = $dgSecond.datagrid('options').columns;
	var columnsDown  = $dgDown.datagrid('options').columns;
	//打开待打印数据页面
	ygDialog({
		isFrame: true,
		cache : false,
		title : winOpts.title,
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
			for(var i=0;i<mainDataGrid.length;i++){
				batchClear(dialog);
				var object=mainDataGrid[i];
				var params = "?shopNo="+object.shopNo+"&startDate="+object.balanceStartDate+"&endDate="+object.balanceEndDate+"&balanceNo="+object.balanceNo+"&costDeductType=1";
				var mallBillingAmount = object.mallBillingAmount;
				var tableHeader = dialog.$('#tableHeader');
				var tableBody  =  dialog.$('#tableBody');
				var tableHeaderRight = dialog.$('#tableHeaderSecond');
				var tableBodyRight  =  dialog.$('#tableBodySecond');
				var tableHeaderDown = dialog.$('#tableHeaderDown');
				var tableBodyDown  =  dialog.$('#tableBodyDown');
				var columnsLength = columns.length;
				var columnsDownLength = columnsDown.length;
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
				filterColumns.push(grepColumns);
				
				//过滤活动列头
				var grepColumnsSecond = $.grep(columnsSecond[columnsLength - 1], function(o, i) {
					if ($(o).attr('printable') == false) {
						return true;
					}
					return false;
				}, true);
				//过滤列头
				var filterColumnsSecond = [];
				$.each(columnsSecond, function(i, n) {
					if ($(n).attr("printable")== undefined) {
						filterColumnsSecond.push(n);
					}
				});
				filterColumnsSecond.push(grepColumnsSecond);
				
				//过滤票前列头
				var grepColumnsDown = $.grep(columnsDown[columnsDownLength - 1], function(o, i) {
					if ($(o).attr('printable') == false) {
						return true;
					}
					return false;
				}, true);
				//过滤列头
				var filterColumnsDown = [];
				$.each(columnsDown, function(i, n) {
					if ($(n).attr("printable")== undefined) {
						filterColumnsDown.push(n);
					}
				});
				filterColumnsDown.push(grepColumnsDown);
				
				//组装列大类,促销活动 表头
				if(columnsLength >= 1){
					//大类
					dialog.$("#hiddenDiv").remove();
					dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
					dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
					var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
					tableHeader.append(tbody.html());
					
					//活动
					dialog.$("#hiddenDivRight").remove();
					dialog.$("<div id='hiddenDivRight' style='display:none;'><table id='exportExcelDGRight' ></table><div>").appendTo("body");
					dialog.$("#exportExcelDGRight").datagrid({columns : filterColumnsSecond});
					dialog.$('#exportExcelDGRight').datagrid('showColumn','seqNo');
					dialog.$('#exportExcelDGRight').datagrid('showColumn','actualAmount');
					dialog.$('#exportExcelDGRight').datagrid('showColumn','balanceDiffAmount');
					batchSetval(dialog,object);
					var tbodySecond = dialog.$("#exportExcelDGRight").prev(0).find('.datagrid-htable').find('tbody');
					tableHeaderRight.append(tbodySecond.html());
					
					//综合店
					dialog.$("#hiddenDivDown").remove();
					dialog.$("<div id='hiddenDivDown' style='display:none;'><table id='exportExcelDGDown' ></table><div>").appendTo("body");
					dialog.$("#exportExcelDGDown").datagrid({columns : filterColumnsDown});
					var tbodyDown = dialog.$("#exportExcelDGDown").prev(0).find('.datagrid-htable').find('tbody');
					tableHeaderDown.append(tbodyDown.html());
				}
				//组装大类表体
				ajaxRequestAsync(BasePath + winOpts.queryDataUrl, params+"&print=1", function(result) {
					if(result.rows!=undefined){
						var rows = result.rows;
						dialog.params = rows;
						dialog.params.title = winOpts.title;
						dialog.params.intOrient = winOpts.intOrient;
						if('' != winOpts.barcode){
							dialog.params.barcode = winOpts.barcode;
						}
						var number = 0;
						var saleMoney =0;
						var invoiceMoney =0;
						for ( var i = 0; i < rows.length; i++) {
							var row = rows[i];
							//拼接表体信息
							var bodyTrNode ='<tr>';
							var bodyTdNode = '';
							$(grepColumns).each(function(i,node){
								var field=$(node).attr('field');
								var value = row[field+''];
								if(field =='saleQty'){
									number+=value;
									bodyTdNode+='<td align="right">'+value+'</td>';
								}else if(field =='saleAmount'){
									var s = accAdd(saleMoney, value);
									saleMoney = s;
									bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
								}else if(field =='billingAmount'){
									var s = accAdd(invoiceMoney, value);
									invoiceMoney = s;
									bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
								}else if (typeof(value) == 'undefined' || null == value) {
									value = '';
									bodyTdNode+='<td align="left">'+value+'</td>';
								}else{
									bodyTdNode+='<td align="center">'+value+'</td>';
								}	
							});
							bodyTrNode+=bodyTdNode+'</tr>';
							//填充表体
							tableBody.append(bodyTrNode);
						}
						//add by Ning.ly 添加合计项 2014/1/15
						var bodyTrNode2 ='<tr>';
						var bodyTdNode2 = '';
						for(j=0;j<grepColumns.length;j++){
								var value = "";
								var field=grepColumns[j].field;
								if(field!=null && field!=""){
									if(field=='brandName'){
										value='合计：';
									}
									if(field =='saleQty'){
										value = number;
									}
									if(field =='saleAmount'){
										var s = saleMoney.toFixed(2)+"";
										value=s;
										if(s.indexOf(".")>0){
											value =  s.substring(0,s.indexOf(".") + 3);
										}
										value=formatNumber(value);
									}
									if(field =='billingAmount'){
										value=mallBillingAmount;
										value=formatNumber(value);
									}
									bodyTdNode2+='<td align="right">'+value+'</td>';
								}
						}
						bodyTrNode2+=bodyTdNode2+'</tr>';
						//填充表体
						tableBody.append(bodyTrNode2);
					}
				});
				//组装活动表体
				ajaxRequestAsync(BasePath + winOpts.selectProSum, params+"&print=1", function(result) {
					if(result.rows!=undefined){
						var rows = result.rows;
						var q = 0;
						var saleMoneys =0;
						var costMoney =0;
						var invoiceMoney =0;
						var actualAmount =0;
						var systemBillingAmount=0;
						var xiaoshouMoney=0;
						var koufeiMoney=0;
						for (var i = 0; i < rows.length; i++) {
							q = i;
							var row = rows[i];
							//拼接表体信息
							var bodyTrNode ='<tr>';
							var bodyTdNode = '';
							$(grepColumnsSecond).each(function(i,node){
								var field = $(node).attr('field');
								var value = row[field+''];
								if(field == 'seqNo'){
									value = q+1;
									bodyTdNode+='<td align="right">'+value+'</td>';
								}else if(field =='deductAmount'){
									var s = accAdd(costMoney, value);
									costMoney = s;
									bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
								}else if(field =='saleAmount'){
									var s = accAdd(saleMoneys, value);
									saleMoneys = s;
									bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
								}else if(field =='systemBillingAmount'){
									var s = accAdd(systemBillingAmount, value);
									systemBillingAmount = s;
									bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
								}else if(field =='billingAmount'){
									var s = accAdd(invoiceMoney, value);
									invoiceMoney = s;
									bodyTdNode+='<td align="right">'+formatNumber(value)+'</td>';
								}else if (typeof(value) == 'undefined' || null == value) {
									value = '';
									bodyTdNode+='<td align="right">'+value+'</td>';
								}else{
									bodyTdNode+='<td align="center">'+value+'</td>';
								}	
							});
							bodyTrNode+=bodyTdNode+'</tr>';
							//填充表体
							tableBodyRight.append(bodyTrNode);
						}
						
						//组装票前费用的实际扣费金额
						ajaxRequestAsync(BasePath + winOpts.findDiffUrl, params, function(result) {
							if(result.rows!=undefined){
								var rows = result.rows;
								for (var i = 0; i < rows.length; i++) {
									var row = rows[i];
									$.each(grepColumnsSecond, function(i, node) {
										var field = $(node).attr('field');
										var value = row[field+''];
										if(field == 'actualAmount'){
											var s = accAdd(actualAmount, value);
											actualAmount = s;
										}
									});
								}
								
								//add by Duan.cw添加票前费用实际扣费金额2015/7/7
								var bodyTrNodeTicket ='<tr>';
								var bodyTdNodeTicket = '';
								for(c=0;c<grepColumnsSecond.length;c++){
									var value ="";
									var field=grepColumnsSecond[c].field;
									if(field!=null && field!=""){
										if(field =='actualAmount'){
											var s = actualAmount.toFixed(2)+"";
											value=s;
											value=formatNumber(value);
										}
										if(field =='balanceDiffAmount'){
											value=object.balanceDiffAmount;
											value=formatNumber(value);
										}
										if(field =='billingAmount'){
											var g=0;
											var s=g.toFixed(2)+"";
											value=s;
											/*invoiceMoney = accAdd(invoiceMoney, value);*/
											value=formatNumber(value);
										}
										bodyTdNodeTicket+='<td align="right">'+value+'</td>';
									}
								}
								bodyTrNodeTicket+=bodyTdNodeTicket+'</tr>';
								//填充表体
								tableBodyRight.append(bodyTrNodeTicket);
							}
						});
						
						//add by Ning.ly 添加合计项 2014/1/15
						var bodyTrNodeSencod ='<tr>';
						var bodyTdNodeSecond = '';
						for(c=0;c<grepColumnsSecond.length;c++){
							var value ="";
							var field=grepColumnsSecond[c].field;
							if(field!=null && field!=""){
								if(field=='seqNo'){
									value='合计：';
								}
								if(field =='saleAmount'){
									var s = saleMoneys.toFixed(2)+"";
									value=s;
									if(s.indexOf(".")>0){
										value =  s.substring(0,s.indexOf(".") + 3);
									}
									value=formatNumber(value);
								}
								if(field =='balanceDiffAmount'){
									value=object.balanceDiffAmount;
									value=formatNumber(value);
								}
								if(field =='deductAmount'){
									var s = costMoney.toFixed(2)+"";
									value=s;
									if(s.indexOf(".")>0){
										value =  s.substring(0,s.indexOf(".") + 3);
									}
									value=formatNumber(value);
								}
								if(field =='actualAmount'){
									var s = actualAmount.toFixed(2)+"";
									value=s;
									if(s.indexOf(".")>0){
										value =  s.substring(0,s.indexOf(".") + 3);
									}
									value=formatNumber(value);
								}if(field =='systemBillingAmount'){
									var s = systemBillingAmount.toFixed(2)+"";
									value=s;
									if(s.indexOf(".")>0){
										value =  s.substring(0,s.indexOf(".") + 3);
									}
									value=formatNumber(value);
								}
								if(field =='billingAmount'){
									var s = invoiceMoney.toFixed(2)+"";
									value=s;
									if(s.indexOf(".")>0){
										value =  s.substring(0,s.indexOf(".") + 3);
									}
									value=formatNumber(value);
								}
								bodyTdNodeSecond+='<td align="right">'+value+'</td>';
							}
						}
						bodyTrNodeSencod+=bodyTdNodeSecond+'</tr>';
						//填充表体
						tableBodyRight.append(bodyTrNodeSencod);
					}
				});
				//组装综合店表体
				ajaxRequestAsync(BasePath + '/bill_shop_balance_brand/list.json',params+"&print=1", function(result) {
					if(result.rows!=undefined && result.rows.length>0){
						var rows = result.rows;
						var footerRows = result.footer;
						for ( var i = 0; i < rows.length; i++) {
							var row = rows[i];
							//拼接表体信息
							var bodyTrNode ='<tr>';
							var bodyTdNode = '';
							$(grepColumnsDown).each(function(i,node){
								var field = $(node).attr('field');
								var value = row[field+''];
								if(field == 'remark'){
									if(value==undefined){
										value='';
									}
									bodyTdNode+='<td align="center">'+value+'</td>';
								}else if(field == 'reason'){
									if(value==undefined){
										value='';
									}
									bodyTdNode+='<td align="center">'+value+'</td>';
								}else{
									if(value==undefined || null == value){
										value='';
									}
									bodyTdNode+='<td align="right">'+value+'</td>';
								}
							});
							bodyTrNode+=bodyTdNode+'</tr>';
							//填充表体
							tableHeaderDown.append(bodyTrNode);
						}
						
						//添加合计项 
						var bodyTrNode2 ='<tr>';
						var bodyTdNode2 = '';
						var footerRow = footerRows[0];
						if(footerRow!=undefined){
							$(grepColumnsDown).each(function(i,node){
								var field = $(node).attr('field');
								var value = footerRow[field+''];
								if(value==undefined){
									bodyTdNode2+='<td align="center">'+''+'</td>';
								}else{
									bodyTdNode2+='<td align="right">'+value+'</td>';
								}
							});
							bodyTrNode2+=bodyTdNode2+'</tr>';
							tableHeaderDown.append(bodyTrNode2);
						}
					}
				});
				if(mainDataGrid.length>1){
					dialog.print_page(this,true);
				}else{
					dialog.print_page(this,false);
				}
			}
			win.close();
		}
	});
};




function formatNumber(num) {
    var source = String(num).split(".");//按小数点分成2部分
        source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");//只将整数部分进行都好分割
    return source.join(".");//再将小数部分合并进来
}


	


