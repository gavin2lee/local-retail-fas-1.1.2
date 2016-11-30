/**
 * 导出
 */
function exportSalesOutstandingReport(){
	var dgt=$("#salesOutstandingAnalysisDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据位空!");
		return;
	}
	var printCol = [ {
		text : "默认",
		value : "Default",
		label : 1
	}, {
		text : "鲁豫模板",
		value : "LY",
		label : 2
	}, {
		text : "华中模板",
		value : "HZ",
		label : 3
	}];
	
	 //初始化框架数据
	$('#printSelect').empty();
	$.each(printCol,function(index,item){
		$('#printForm #printSelect').append("<option value="+item.value+">"+item.text+"</option>");
	});
	var async = true;
	var url = "/bill_sales_outstanding_analysis/" + ( async?"async_exports":"export");
	ygDialog({
		title : '销售回款分析明细导出',
		target : $('#printContrPanel'),
		width : 225,
		height : 300,
		buttons : [{
					text : '导出',
					iconCls : 'icon-print',
					handler : function(dialog) {
						var selected = $('#printSelect').val();
						dialog.close();
						if(selected =="Default"){
							$.fas.exportExcel({
								dataGridId : "salesOutstandingAnalysisDataGrid",
								//exportUrl : "/bill_sales_outstanding_analysis/do_sale_export?exportType=1",
								exportUrl : url + "?exportType=1",
								exportTitle : "销售回款分析明细",
								async:async
							});
						}
						//鲁豫模板导出
						else if(selected =="LY"){
							var columns = [
								       			[
								       			{field : 'zoneName', title : '地区', width : 60},                                                                                                                                                                                                                                       
												{field : 'organName1', title : '管理城市 ', width : 80},                                                                                                                                                                 
												{field : 'organName2', title : '经营城市', width : 80},
												{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center'},
												{field : 'mallName', title : '商场 ', width : 80,align:'left',halign:'center'}, 
												{field : 'cmcdistName', title : '商圈 ', width : 80,align:'left',halign:'center'}, 
												{field : 'regionName', title : '片区 ', width : 80}, 
												{field : 'bizType', title : '业务类型', width : 80},      
												{field : 'shopLevel', title : '店铺级别', width : 80},
												{field : 'saleMode', title : '店铺大类', width : 80},
												{field : 'retailType', title : '店铺小类', width : 80},
												{field : 'multi', title : '店铺细类', width : 80},   
												{field : 'shopNoReplace', title : '店铺替换编码', width : 100},
												{field : 'shopNo', title : '店铺编码/客户编号', width : 120,align:'left',halign:'center'},   
												{field : 'shortName', title : '店铺名称/客户名称', width : 120,align:'left',halign:'center'}, 
												//{field : 'assistantNo', title : '营业员编码', width : 90,align:'left',halign:'center'},  
												//{field : 'assistantName', title : '营业员名称', width : 90}, 
												{field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},                                                                                                                                                                                                                               
												{field : 'itemName', title : '商品名称 ', width : 180,align:'left',halign:'center'}, 
												{field : 'brandUnitName', title : '品牌部', width : 90},  
												{field : 'brandName', title : '品牌', width : 90},                                                                                                                                                                                                                           
												{field : 'categoryName', title : '大类 ', width : 90},                                                                                                                                                                                                                           
												{field : 'rootCategoryName', title : '中类 ', width : 90},                                                                                                                                                                                                                          
												{field : 'subCategoruName', title : '小类', width : 90}, 
												{field : 'year', title : '年份 ', width : 60},      							                                                                                                                                                                                                                                       
												{field : 'sellSeason', title : '货品季节 ', width : 60},                                                                                                                                                                                                                                             
												{field : 'gender', title : '性别', width : 80},                                                                                                                                                                                                                                        
												{field : 'orderfrom', title : '订货形式 ', width : 80},                                                                                                                                                                                                                             
												{field : 'style', title : '风格', width : 60},                                                                                                                                                                                                                                        
												{field : 'colorName', title : '颜色', width : 60}, 
												{field : 'wildcardNo', title : '外卡编号', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'wildcardName', title : '外卡名称', width : 90, align:'right'}, 
												{field : 'launchTypeStr', title : '活动来源', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'activityTypeStr', title : '活动类别', width : 90, align:'right'}, 
												{field : 'proNo', title : '活动编码', width : 90, align:'right'},                                                                                                                                                                                                                        
												{field : 'proName', title : '活动名称', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'proStartDate', title : '活动起始日期', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'proEndDate', title : '活动终止日期 ', width : 90, align:'right'},
												{field : 'strength', title : '活动力度', width : 60, align:'right'},                                                                                                                                                                                                                         
												{field : 'virtualFlagStr', title : '虚实属性', width : 90, align:'right'},
						                        {field : 'propertiesStr', title : '活动属性', width : 90, align:'right'},    
						                        {field : 'rateTypeStr', title : '扣率类型', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'rateCode', title : '扣率代码', width : 90, align:'right'}, 
												{field : 'rateName', title : '扣率名称', width : 90, align:'right'},   
												{field : 'rateDiscount', title : '活动折扣', width : 90, align:'right',exportType:'number'},
												{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'},	
												{field : 'billingCode', title : '商场结算码 ', width : 90, align:'right'},					 	  		  	  	  	  	  	 	                                                                                                                                                                                                                                      
												{field : 'outDate', title : '销售日期', width : 90}, 
												{field : 'orderNo', title : '单据编号', width : 130},
												{field : 'balanceBaseName', title : '结算基数 ', width : 90, align:'right'},    
												{field : 'qty', title : '数量', width : 90, align:'right',exportType:'number'},   
												{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'},
												{field : 'saleAmount', title : ' 现价额', width : 90, align:'right',exportType:'number'},  
												{field : 'discPrice', title : '折后价收入', width : 90, align:'right',exportType:'number'}, 
												{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'},
												{field : 'virtualAmount', title : '虚数活动实际金额', width : 90, align:'right',exportType:'number'}, 
												{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},   
												{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},
												{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                 
												{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},
												{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
												//{field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'},
												{field : 'backAmount', title : '回款额 ', width : 90, align:'right',exportType:'number'},
												{field : 'discountRate', title : ' 折扣率', width : 90, align:'right',exportType:'number'},
												{field : 'grossMarginRate', title : '毛利率 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                     
												{field : 'deductionRate', title : '扣费率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                   
												{field : 'conpriceDeductRate', title : '合同正价扣费率', width : 100, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'conpriceLadderRate', title : '合同阶梯扣加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'promPlusbuckleRate', title : '促销活动加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'theDiscountRate', title : '净折扣率', width : 80,exportType:'number'},                                                                                                                                                                                                
												{field : 'theMarginRate', title : '净毛利率 ', width : 90, align:'right',exportType:'number'}, 
												{field : 'synthesizeReturnRate', title : '综合回款扣率 ', width : 90, align:'right',exportType:'number'},
												{field : 'reimbursementRate', title : '回款率 ', width : 90, align:'right',exportType:'number'}
								    		]];
							exportTempletExcel({
								dataGridId : "salesOutstandingAnalysisDataGrid",
								exportUrl : url + "?exportType=1",
								title : "销售回款分析明细",
								async:async,
								grepColumns:columns
							});
						}else if (selected =="HZ"){
							var columns = [
							       			[
											{field : 'organName1', title : '管理城市 ', width : 80},                                                                                                                                                                 
											{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center'},
											{field : 'mallName', title : '商场 ', width : 80,align:'left',halign:'center'}, 
											{field : 'cmcdistName', title : '商圈 ', width : 80,align:'left',halign:'center'}, 
											{field : 'bizType', title : '业务类型', width : 80}, 
											{field : 'shopNoReplace', title : '店铺替换编码', width : 100},
											{field : 'shopNo', title : '店铺编码/客户编号', width : 120,align:'left',halign:'center'},   
											{field : 'shortName', title : '店铺名称/客户名称', width : 120,align:'left',halign:'center'}, 
											//{field : 'assistantNo', title : '营业员编码', width : 90,align:'left',halign:'center'},  
											//{field : 'assistantName', title : '营业员名称', width : 90}, 
											{field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},                                                                                                                                                                                                                               
											{field : 'brandUnitName', title : '品牌部', width : 90},  
											{field : 'categoryName', title : '大类 ', width : 90},                                                                                                                                                                                                                           
											{field : 'wildcardNo', title : '外卡编号', width : 90, align:'right'},                                                                                                                                                                                                                      
											{field : 'wildcardName', title : '外卡名称', width : 90, align:'right'}, 
											{field : 'virtualFlagStr', title : '虚实属性', width : 90, align:'right'},
					                        {field : 'rateTypeStr', title : '扣率类型', width : 90, align:'right'},                                                                                                                                                                                                                      
											{field : 'rateCode', title : '扣率代码', width : 90, align:'right'}, 
											{field : 'rateName', title : '扣率名称', width : 90, align:'right'},   
											{field : 'rateDiscount', title : '活动折扣', width : 90, align:'right',exportType:'number'},
											{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'},	
											{field : 'billingCode', title : '商场结算码 ', width : 90, align:'right'},					 	  		  	  	  	  	  	 	                                                                                                                                                                                                                                      
											{field : 'outDate', title : '销售日期', width : 90}, 
											{field : 'orderNo', title : '单据编号', width : 130},
											{field : 'qty', title : '数量', width : 90, align:'right',exportType:'number'},   
											{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'},
											{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'},
											{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},   
											{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},
											{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                 
											{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
											{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
											{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
											{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},
											{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
											//{field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'},
											{field : 'backAmount', title : '回款额 ', width : 90, align:'right',exportType:'number'},
											{field : 'discountRate', title : ' 折扣率', width : 90, align:'right',exportType:'number'},
											{field : 'grossMarginRate', title : '毛利率 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                     
											{field : 'deductionRate', title : '扣费率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                   
											{field : 'conpriceDeductRate', title : '合同正价扣费率', width : 100, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
											{field : 'conpriceLadderRate', title : '合同阶梯扣加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
											{field : 'promPlusbuckleRate', title : '促销活动加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
											{field : 'theDiscountRate', title : '净折扣率', width : 80,exportType:'number'},                                                                                                                                                                                                
											{field : 'theMarginRate', title : '净毛利率 ', width : 90, align:'right',exportType:'number'}, 
											{field : 'reimbursementRate', title : '回款率 ', width : 90, align:'right',exportType:'number'}
							    		]];
						exportTempletExcel({
							dataGridId : "salesOutstandingAnalysisDataGrid",
							//url : "/bill_sales_outstanding_analysis/export?exportType=1",
							exportUrl : url + "?exportType=1",
							title : "销售回款分析明细",
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


//导出华北
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


//////////////////////////////////汇总导出 ////////////////////////////////////////////////

function exportSalesOutstandingCollectReport(){
	var dgt=$("#salesOutstandingCollectDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据位空!");
		return;
	}
	var printCol = [ {
		text : "默认",
		value : "Default",
		label : 1
	}, {
		text : "西北模板",
		value : "XB",
		label : 2
	}];
	
	 //初始化框架数据
	$('#printCollectSelect').empty();
	$.each(printCol,function(index,item){
		$('#printCollectForm #printCollectSelect').append("<option value="+item.value+">"+item.text+"</option>");
	});
	var async = true;
	var url = "/bill_sales_outstanding_analysis/" + ( async?"async_exports":"do_fas_exports");
	ygDialog({
		title : '销售回款分析汇总导出',
		target : $('#printCollectContrPanel'),
		width : 225,
		height : 300,
		buttons : [{
					text : '导出',
					iconCls : 'icon-print',
					handler : function(dialog) {
						var selected = $('#printCollectSelect').val();
						dialog.close();
						if(selected =="Default"){
							$.fas.exportExcel({
								dataGridId : "salesOutstandingCollectDataGrid",
								//exportUrl : "/bill_sales_outstanding_analysis/do_sale_export?exportType=1",
								exportUrl : url + "?exportType=0",
								exportTitle : "销售回款分析汇总",
								async:async
							});
						}
						//西北模板导出
						else if(selected =="XB"){
							var columns = [
								       			[
								       			{field : 'zoneName', title : '地区', width : 60},                                                                                                                                                                                                                                       
												//{field : 'provinceName', title : '省份 ', width : 60},    
												{field : 'companyName', title : '公司名称 ', width : 200},                                                                                                                                                                                                                                    
												{field : 'organName1', title : '管理城市 ', width : 80},                                                                                                                                                                 
												{field : 'organName2', title : '经营城市', width : 80},
												{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center'},
												{field : 'mallName', title : '商场 ', width : 80,align:'left',halign:'center'}, 
												{field : 'cmcdistName', title : '商圈 ', width : 80,align:'left',halign:'center'}, 
												{field : 'regionName', title : '片区 ', width : 80}, 
												{field : 'bizType', title : '业务类型', width : 80},      
												{field : 'shopLevel', title : '店铺级别', width : 80},
												{field : 'saleMode', title : '店铺大类', width : 80},
												{field : 'retailType', title : '店铺小类', width : 80},
												{field : 'multi', title : '店铺细类', width : 80},   
												{field : 'shopNoReplace', title : '店铺替换编码', width : 100},
												{field : 'shopNo', title : '店铺编码/客户编号', width : 120,align:'left',halign:'center'},   
												{field : 'xb_shortName', title : '店铺名称/客户名称', width : 120,align:'left',halign:'center'},
												{field : 'brandUnitName', title : '品牌部', width : 90},  
												{field : 'categoryName', title : '大类 ', width : 90}, 
												{field : 'rootCategoryName', title : '中类 ', width : 90}, 
												{field : 'sellSeason', title : '季节 ', width : 60},                                                                                                                                                                                   
												{field : 'year', title : '年份 ', width : 60},      							                                                                                                                                                                                                                                       
												{field : 'gender', title : '性别', width : 80}, 
												{field : 'launchTypeStr', title : '活动来源', width : 90, align:'right'}, 
												{field : 'activityTypeStr', title : '活动类别', width : 90, align:'right'}, 
												{field : 'proNo', title : '活动编码', width : 90, align:'right'},                                                                                                                                                                                                                        
												{field : 'proName', title : '活动名称', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'proStartDate', title : '活动起始日期', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'proEndDate', title : '活动终止日期 ', width : 90, align:'right'},
												{field : 'strength', title : '活动力度', width : 60, align:'right'},                                                                                                                                                                                                                         
												{field : 'virtualFlagStr', title : '虚实属性', width : 90, align:'right'},
						                        {field : 'propertiesStr', title : '活动属性', width : 90, align:'right'},  
						                        {field : 'rateTypeStr', title : '扣率类型', width : 90, align:'right'},                                                                                                                                                                                                                        
												{field : 'rateCode', title : '扣率代码', width : 90, align:'right'},                                                                                                                                                                                                                      
												{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'qty', title : '数量', width : 90, align:'right',exportType:'number'},   
												{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                           
												{field : 'discPrice', title : '折后价收入', width : 90, align:'right',exportType:'number'}, 
												{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                            
												{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},  
												{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                 
												{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                              
												{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},  								  	  	  	  	  	 	                                                                                                                                                                                                                                  
												{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
												//{field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'},
												{field : 'backAmount', title : '回款额 ', width : 90, align:'right',exportType:'number'},
												{field : 'discountRate', title : ' 折扣率', width : 90, align:'right',exportType:'number'},
												{field : 'grossMarginRate', title : '毛利率 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                     
												{field : 'deductionRate', title : '扣费率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                   
												{field : 'conpriceDeductRate', title : '合同正价扣费率', width : 100, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'conpriceLadderRate', title : '合同阶梯扣加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'promPlusbuckleRate', title : '促销活动加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
												{field : 'theDiscountRate', title : '净折扣率', width : 80,exportType:'number'},                                                                                                                                                                                                
												{field : 'theMarginRate', title : '净毛利率 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'reimbursementRate', title : '回款率 ', width : 90, align:'right',exportType:'number'}
								    		]];
							exportTempletExcel({
								dataGridId : "salesOutstandingCollectDataGrid",
								exportUrl : url + "?exportType=0",
								title : "销售回款分析汇总",
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
