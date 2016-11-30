
function exportInvoiceApplyTaxList(){
	var dgt=$("#invoiceApplyDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据位空!");
		return;
	}
	var printCol = [ {
		text : "华东税控模板",
		value : "HD",
		label : 1
	}, {
		text : "华北税控模板",
		value : "HB",
		label : 2
	}, {
		text : "鲁豫税控模板",
		value : "LY",
		label : 3
	}];
	
	 //初始化框架数据
	$('#printTaxModelSelect').empty();
	$.each(printCol,function(index,item){
		$('#printTaxModelForm #printTaxModelSelect').append("<option value="+item.value+">"+item.text+"</option>");
	});
	var async = false;
	var url = "/bill_balance_invoice_apply/do_fas_export";
	ygDialog({
		title : '税控模板导出',
		target : $('#printTaxModeltContrPanel'),
		width : 225,
		height : 300,
		buttons : [{
					text : '导出',
					iconCls : 'icon-print',
					handler : function(dialog) {
						var selected = $('#printTaxModelSelect').val();
						dialog.close();
//						if(selected =="Default"){
//							$.fas.exportExcel({
//								dataGridId : "invoiceApplyDataGrid",
//								//exportUrl : "/bill_sales_outstanding_analysis/do_sale_export?exportType=1",
//								exportUrl : url + "?exportType=1",
//								exportTitle : "税控模板导出",
//								async:async
//							});
//						}
						//华东模板导出
						if(selected =="HD"){
							var columns = [[
							                {field : 'id', title : '序号', width : 60},   
							       			{field : 'zoneName', title : '地区', width : 60},                                                                                                                                                                                                                                       
											{field : 'retailType', title : '店铺类别 ', width : 200},                                                                                                                                                                                                                                    
											{field : 'invoiceTypeStr', title : '发票类别 ', width : 80},                                                                                                                                                                 
											{field : 'balanceDate', title : '结算期', width : 80},
											{field : 'invoiceApplyDate', title : '开票日期 ', width : 150,align:'left',halign:'center'},
											{field : 'shopNo', title : '店铺代码 ', width : 80,align:'left',halign:'center'}, 
											{field : 'shopName', title : '系统名称 ', width : 80,align:'left',halign:'center'}, 
											{field : 'invoiceName', title : '开票名称 ', width : 80}, 
											{field : 'brandName', title : '品牌', width : 80},      
											{field : 'categoryName', title : '系统类别', width : 80},
											{field : 'name', title : '货物开票名称', width : 80},
											{field : 'typeModel', title : '规格型号', width : 80},
											{field : 'unit', title : '单位', width : 80},                                                                                                                                                                                                                                
											{field : 'qty', title : '开票数量', width : 120,align:'left',halign:'center'},   
											{field : 'price', title : '单价', width : 120,align:'left',halign:'center'},
											{field : 'noTaxAmount', title : '不含税金额', width : 90},  
											{field : 'taxAmount', title : '税金', width : 90},                                                                                                                                                                                                                           
											{field : 'containTaxAmount', title : '含税金额 ', width : 90}, 
											{field : 'invoiceRegisterNo', title : '发票号码 ', width : 90}, 
											{field : 'remark', title : '商场开票要求', width : 60},                                                                                                                                                                                   
											{field : 'mailingAddress', title : '发票邮寄地区 ', width : 60},      							                                                                                                                                                                                                                                       
											{field : 'arriveTime', title : '发票到达地区期限', width : 80}
								    		]];
							exportTempletExcel({
								dataGridId : "invoiceApplyDataGrid",
								exportUrl : url + "?exportType=1",
								title : "华东税控模板导出",
								async:async,
								grepColumns:columns
							});
						}else if (selected =="HB"){
							var columns = [[
											{field : 'zoneName', title : '地区', width : 60},                                                                                                                                                                                                                                       
											{field : 'buyerName', title : '客户名称', width : 100},                                                                                                                                                                                                                                    
											{field : 'invoiceTypeStr', title : '发票类别 ', width : 80},
											{field : 'invoiceName', title : '开票名称 ', width : 80},
											{field : 'ncClientNo', title : 'NC客户编码 ', width : 90}, 
											{field : 'ncClientName', title : 'NC客户档案 ', width : 90}, 
											{field : 'taxRegistryNo', title : '纳税人识别号 ', width : 90}, 
											{field : 'bankName', title : '开户行 ', width : 90}, 
											{field : 'bankAccount', title : '账号 ', width : 80}, 
											{field : 'buyerAddress', title : '地址 ', width : 100}, 
											{field : 'buyerTel', title : '电话 ', width : 80}, 
											{field : 'mailingAddress', title : '发票邮寄地址 ', width : 100}, 
											{field : 'contactName', title : '收票联系人 ', width : 80}, 
											{field : 'tel', title : '联系电话 ', width : 80}, 
											{field : 'getWay', title : '结账单领取方式 ', width : 80}, 
											{field : 'billDate', title : '商场下结账单日期', width : 80},
											{field : 'invoiceApplyDate', title : '交票时间 ', width : 80,align:'left',halign:'center'},
											{field : 'remark', title : '开票要求 ', width : 150,align:'left',halign:'center'},
											{field : 'shopNo', title : '店铺编码 ', width : 80,align:'left',halign:'center'}, 
											{field : 'shopName', title : '店铺名称 ', width : 80,align:'left',halign:'center'}, 
											{field : 'brandName', title : '品牌', width : 80},      
											{field : 'qty', title : '数量', width : 120,align:'left',halign:'center'}, 
											{field : 'categoryName', title : '类别', width : 80},
											{field : 'sendAmount', title : '开票金额', width : 90}
							    		]];
							exportTempletExcel({
								dataGridId : "invoiceApplyDataGrid",
								exportUrl : url + "?exportType=2",
								title : "华北税控模板导出",
								async:async,
								grepColumns:columns
							});
						}else if (selected =="LY"){
							var columns = [[
							                {field : 'id', title : '序号', width : 60},   
							                {field : 'unit', title : '单位', width : 80}, 
											{field : 'zoneName', title : '地区', width : 60},
											{field : 'retailType', title : '店铺类别 ', width : 200},  
											{field : 'invoiceTypeStr', title : '发票类别 ', width : 80},
											{field : 'invoiceApplyDate', title : '开票日期 ', width : 150,align:'left',halign:'center'},
											{field : 'shopNo', title : '店铺代码 ', width : 80,align:'left',halign:'center'}, 
											{field : 'buyerName', title : '名称', width : 100},  
											{field : 'brandName', title : '品牌', width : 80},      
											{field : 'categoryName', title : '产品分类', width : 80},
											{field : 'name', title : '货物开票名称', width : 80},
											{field : 'qty', title : '开票数量', width : 120,align:'left',halign:'center'},   
											{field : 'price', title : '单价', width : 120,align:'left',halign:'center'},
											{field : 'noTaxAmount', title : '不含税金额', width : 90},  
											{field : 'taxAmount', title : '税金', width : 90},                                                                                                                                                                                                                           
											{field : 'containTaxAmount', title : '含税金额 ', width : 90},
											{field : 'invoiceRegisterNo', title : '号码 ', width : 90},
											{field : 'remark', title : '备注 ', width : 150},
											{field : 'balanceDate', title : '结算期', width : 80},
											{field : 'remark', title : '商场开票要求', width : 60}, 
											{field : 'mailingAddress', title : '发票邮寄地区 ', width : 60},      							                                                                                                                                                                                                                                       
											{field : 'arriveTime', title : '发票到达地区期限', width : 80},
											{field : 'typeModel', title : '属性', width : 80},
											{field : 'unit', title : '含税/不含税', width : 80}, 
											{field : 'shopName', title : '系统名称 ', width : 80,align:'left',halign:'center'}, 
											                                                                                                                                                                                  
											{field : 'qty', title : '我方数量', width : 120,align:'left',halign:'center'},   
											{field : 'price', title : '销售金额', width : 120,align:'left',halign:'center'},
											{field : 'noTaxAmount', title : '我方应收金额', width : 90} 
							    		]];
							exportTempletExcel({
								dataGridId : "invoiceApplyDataGrid",
								exportUrl : url + "?exportType=2",
								title : "鲁豫税控模板导出",
								async:async,
								grepColumns:columns
							});
						}
					}
				},
				{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function(dialog) {
						dialog.close();
					}
				} ]
	});
}


//导出
function exportTempletExcel(setting){
	var $dg = $("#" + setting.dataGridId);
	var count = 0;
	if( $dg.datagrid('getData') ){
		count = $dg.datagrid('getData').total;
	}
	var params = $dg.datagrid('options').queryParams;
	var columns = [], firstHeaderColumns = [];
	var grepColumns = setting.grepColumns;
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
	var exportColumns = JSON.stringify(columns);
	var url = BasePath + setting.exportUrl;
	
	var ps = $.extend({},params);
	ps.exportColumns = exportColumns;
	ps.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
	ps.fileName = setting.title;
	ps.exportType = setting.exportType;
	ps.exportUrl = url;
	ps.count = count;
	if(!setting.async){
		$("#exportExcelForm").remove();
		$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
		var fromObj = $('#exportExcelForm');
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				 $.extend(param,ps);
			},
			success : function() {

			}
		});
	}
	else
	{
		var panel = new $.fas.ExportPanel(setting,ps);
			panel.open();
	}

}
