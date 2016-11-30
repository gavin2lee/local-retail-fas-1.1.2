/**
 * 导出
 */
function exportSalesSumReport(){
	var dgt=$("#salesSumDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据位空!");
		return;
	}
	var printCol = [ {
		text : "默认",
		value : "Default",
		label : 1
	}, {
		text : "华北模板",
		value : "HB",
		label : 2
	}, {
		text : "西北模板",
		value : "XB",
		label : 3
	}, {
		text : "华南模板",
		value : "HN",
		label : 4
	}];
	
	 //初始化框架数据
	$('#printSelect').empty();
	$.each(printCol,function(index,item){
		$('#printForm #printSelect').append("<option value="+item.value+">"+item.text+"</option>");
	});
	ygDialog({
		title : '销售汇总导出',
		target : $('#printContrPanel'),
		width : 225,
		height : 300,
		buttons : [{
					text : '导出',
					iconCls : 'icon-print',
					handler : function(dialog) {
						var selected = $('#printSelect').val();
						if(selected =="Default"){
							fas_common.exportExcel({
								dataGridId : "salesSumDataGrid",
								url : "/bill_sales_sum/do_fas_export",
								title : "销售汇总表"
							});
						}
						//华北模板导出
						if(selected =="HB"){
							var columns = [
								       			[
								       			{field : 'zoneNo', title : '地区编码', width : 60,rowspan:'2'},
								       			{field : 'zoneName', title : '地区', width : 60,rowspan:'2'},
								       			{field : 'provinceName', title : '省份 ', width : 60,rowspan:'2'},
								       			{field : 'organName', title : '管理城市 ', width : 80,rowspan:'2'},
								       			{field : 'organName2', title : '经营城市', width : 80,rowspan:'2'},
								       			
								       			{field : 'hb_multi1', title : '店铺细类', width : 80,rowspan:'2'},
								    			{field : 'shopNo', title : '店铺/客户编码', width : 100,rowspan:'2'},
								    			{field : 'brandUnitNo', title : '品牌部编号', width : 80,rowspan:'2'},
								    			{field : 'brandUnitName', title : '品牌部名称', width : 80,rowspan:'2'},
								    			{field : 'shortName', title : '店铺/客户名称', width : 150,align:'left',halign:'center',rowspan:'2'},
								    			{field : 'openDate', title : '成立日期', width : 80,rowspan:'2'},
												{field : 'employeAmount', title : '店员人数', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},
											    {field : 'areaTotal', title : '总面积 ', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},  
												{field : 'channelName', title : '销售渠道', rowspan:'2', width : 80},
								    			{field : 'contractRate', title : '合同扣率', width : 80, align:'right',halign:'center',exportType:'number',rowspan:'2'},
								    			{field : 'categoryName', title : '类别名称', width : 60,rowspan:'2'},
								    			{field : 'saleMonth', title : '结算期 ', width : 100,rowspan:'2'},

								    			{title : '上月结算期后',colspan:'5',},
								                {title : '本月结算期内',colspan:'5'},
								                {title : '结算期内合计',colspan:'9'},
								                {title : '本月结算期后',colspan:'9'},
								                {title : '本月合计',colspan:'7'}],
								                
								                [{field : 'lmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								                {field : 'lmaPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'lmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'lmaPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'lmaPeriodSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'lmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},                                                                                                                                                                                                                      
								    			{field : 'lmaPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			                                                                                                                                                                                                                     
								    			{field : 'tmiPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmiPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmiPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'tmiPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmiPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmiPeriodSalesdeductions', title : '扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmiPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			
								    			{field : 'biPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'biPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'biPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'biPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'biPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'biPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'biPeriodBalanceamount', title : '结算收入 ', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'qty', title : '开票数量 ', width : 80, align:'right',rowspan:'2',halign:'center',exportType:'number'},
								    			{field : 'sendAmount', title : '开票金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
								    			{field : 'biPeriodNonTaxBalanceamount', title : '无税金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
								    			{field : 'invoiceApplyDate', title : '开票日期 ', width : 80,rowspan:'2'},
								    			{field : 'salerName', title : '开票单位', width : 80,rowspan:'2',hidden:true,notexport:true},
								    			                                                                                                                                 
								    			{field : 'tmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodSalesamount', title : '销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'tmaPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodBalanceamount', title : '结算收入  ', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'tmaPeriodSalesnum', title : '发票量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodBalanceamount', title : '发票额  ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodNonTaxBalanceamount', title : '无税金额', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'invoiceApplyDate', title : '发票日', width : 80},
								    			
								    			{field : 'tmSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmSalesamount', title : '销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmSalesdeductions', title : '扣费  ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'nonTaxSalesamount', title : '无税收入', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'taxCost', title : '含税成本', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'noTaxCosts', title : '无税成本', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true},
								    			{field : 'headquarterCost', title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number',hidden:true,notexport:true}
								    		]];
							exportTempletExcel({
								dataGridId : "salesSumDataGrid",
								url : "/bill_sales_sum/do_fas_export",
								title : "销售汇总表",
								grepColumns:columns
							});
						}
						
						//西北模板导出
						if(selected =="XB"){
							var columns = [
								       			[
								       			{field : 'zoneName', title : '地区', width : 60,rowspan:'2'},
												{field : 'provinceName', title : '省份 ', width : 60,rowspan:'2'},
												{field : 'companyName', title : '公司', width : 180,align:'left',halign:'center',rowspan:'2'},
												{field : 'organName', title : '管理城市 ', width : 80,rowspan:'2'},
												{field : 'organName2', title : '经营城市', width : 80,rowspan:'2'},
												
												{field : 'shopNo', title : '店铺/客户编码', width : 100,rowspan:'2'},
												{field : 'xb_shortName', title : '店铺/客户名称', width : 150,align:'left',halign:'center',rowspan:'2'},
												{field : 'saleType', hidden:true,title : '销售类别', width : 80,rowspan:'2',notexport:true},
												{field : 'bizTypeName', title : '业务类型', width : 80,rowspan:'2'},
												
												{field : 'year',hidden:true, title : '年', width : 60,rowspan:'2',notexport:true},
												{field : 'month', title : '年月', width : 60,rowspan:'2'},
												{field : 'billMonth', title : '开票月份', width : 60,rowspan:'2'},
												{field : 'saleMonth', title : '结算期 ', width : 100,rowspan:'2'},
												{field : 'contractRate', title : '合同扣率', width : 80, align:'right',halign:'center',exportType:'number',rowspan:'2'},
												
												{field : 'brandUnitNo', title : '品牌部编号', width : 80,rowspan:'2'},
												{field : 'brandUnitName', title : '品牌部名称', width : 80,rowspan:'2'},
												{field : 'categoryName', title : '类别名称', width : 60,rowspan:'2'},
												
												{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center',rowspan:'2'},
												{field : 'saleMode1', title : '店铺大类', width : 80,rowspan:'2'},
												{field : 'retailType1', title : '店铺小类', width : 80,rowspan:'2'},
												{field : 'multi1', title : '店铺细类', width : 80,rowspan:'2'},
												{field : 'levelName1', title : '店铺级别', width : 80,rowspan:'2'},
												{field : 'levelName', hidden:true,title : '店铺类别', width : 70,rowspan:'2',notexport:true},
												{field : 'statusName', title : '店铺状态', width : 80,rowspan:'2'},
												{field : 'openDate', title : '成立日期', width : 80,rowspan:'2'},
												{field : 'employeAmount', title : '店员人数', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},
												{field : 'area', title : '卖场面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'areaLeft', title : '仓库面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'areaTotal', title : '总面积 ', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},  
												{field : 'channelName', title : '销售渠道', rowspan:'2', width : 80},
												
												{title : '上月结算期后',colspan:'7',},
								                {title : '本月结算期内',colspan:'7'},
								                {title : '结算期内合计',colspan:'11'},
								                {title : '本月结算期后',colspan:'7'},
								                {title : '本月合计',colspan:'16'}],
								                
									            [{field : 'lmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
									            {field : 'lmaPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
									            {field : 'lmaPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},                                                                                                                                                                                                                      
												{field : 'lmaPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
												                                                                                                                                                                                                                     
												{field : 'tmiPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalesdeductions', title : '扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
												
												{field : 'biPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodBalanceamount', title : '结算收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'qty', title : '开票数量 ', width : 80, align:'right',rowspan:'2',halign:'center',exportType:'number'},
												{field : 'sendAmount', title : '开票金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
												{field : 'invoiceApplyDate', title : '开票日期 ', width : 80,rowspan:'2'},
												{field : 'salerName', title : '开票单位', width : 80,rowspan:'2'},
												                                                                                                                                 
												{field : 'tmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodRealamount', title : '终端销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalesamount', title : '销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodBalanceamount', title : '结算收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
												
												{field : 'tmSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmRealamount', title : '终端销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalesamount', title : '销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalesdeductions', title : '扣费  ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'actualRate', title : '实际扣率', width : 80, align:'right',halign:'center',exportType:'number'},
												
												{field : 'systemDeductAmount', title : '系统扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'balanceDeductAmount', title : '票前费用 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'balanceDiffAmount', title : '结算差异 ', width : 90, align:'right',halign:'center',exportType:'number'},
												
												{field : 'nonTaxSalesamount', title : '无税收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'taxCost', title : '含税成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'noTaxCosts', title : '无税成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'regionCost', title : '地区成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'headquarterCost', title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number'}
								    		]];
							exportTempletExcel({
								dataGridId : "salesSumDataGrid",
								url : "/bill_sales_sum/do_fas_export",
								title : "销售汇总表",
								grepColumns:columns
							});
						}
						
						//华南模板导出
						if(selected =="HN"){
							var columns = [
								       			[
								       			{field : 'zoneName', title : '地区', width : 60,rowspan:'2'},
												{field : 'provinceName', title : '省份 ', width : 60,rowspan:'2'},
												{field : 'companyName', title : '公司', width : 180,align:'left',halign:'center',rowspan:'2'},
												{field : 'organName', title : '管理城市 ', width : 80,rowspan:'2'},
												{field : 'organName2', title : '经营城市', width : 80,rowspan:'2'},
												
												{field : 'shopNo', title : '店铺/客户编码', width : 100,rowspan:'2'},
												{field : 'xb_shortName', title : '店铺/客户名称', width : 150,align:'left',halign:'center',rowspan:'2'},
												{field : 'saleType', hidden:true,title : '销售类别', width : 80,rowspan:'2',notexport:true},
												{field : 'bizTypeName', title : '业务类型', width : 80,rowspan:'2'},
												
												{field : 'year',hidden:true, title : '年', width : 60,rowspan:'2',notexport:true},
												{field : 'month', title : '年月', width : 60,rowspan:'2'},
												{field : 'billMonth', title : '开票月份', width : 60,rowspan:'2'},
												{field : 'saleMonth', title : '结算期 ', width : 100,rowspan:'2'},
												{field : 'contractRate', title : '合同扣率', width : 80, align:'right',halign:'center',exportType:'number',rowspan:'2'},
												
												{field : 'brandUnitNo', title : '品牌部编号', width : 80,rowspan:'2'},
												{field : 'brandUnitName', title : '品牌部名称', width : 80,rowspan:'2'},
												{field : 'categoryName', title : '类别名称', width : 60,rowspan:'2'},
												
												{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center',rowspan:'2'},
												{field : 'saleMode1', title : '店铺大类', width : 80,rowspan:'2'},
												{field : 'retailType1', title : '店铺小类', width : 80,rowspan:'2'},
												{field : 'multi1', title : '店铺细类', width : 80,rowspan:'2'},
												{field : 'levelName1', title : '店铺级别', width : 80,rowspan:'2'},
												{field : 'levelName', hidden:true,title : '店铺类别', width : 70,rowspan:'2',notexport:true},
												{field : 'statusName', title : '店铺状态', width : 80,rowspan:'2'},
												{field : 'openDate', title : '成立日期', width : 80,rowspan:'2'},
												{field : 'employeAmount', title : '店员人数', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},
												{field : 'area', title : '卖场面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'areaLeft', title : '仓库面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
												{field : 'areaTotal', title : '总面积 ', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},  
												{field : 'channelName', title : '销售渠道', rowspan:'2', width : 80},
												
												{title : '上月结算期后',colspan:'6',},
								                {title : '本月结算期内',colspan:'6'},
								                {title : '结算期内合计',colspan:'12'},
								                {title : '本月结算期后',colspan:'11'},
								                {title : '本月合计',colspan:'11'}],
								                
									            [{field : 'lmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
									            {field : 'lmaPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'lmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
												                                                                                                                                                                                                                     
												{field : 'tmiPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmiPeriodSalesdeductions', title : '扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
												
												{field : 'biPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'biPeriodBalanceamount', title : '结算收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'qty', title : '开票数量 ', width : 80, align:'right',rowspan:'2',halign:'center',exportType:'number'},
												{field : 'sendAmount', title : '开票金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
												{field : 'biPeriodNonTaxBalanceamount', title : '无税金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
												{field : 'invoiceApplyDate', title : '开票日期 ', width : 80,rowspan:'2'},
												{field : 'salerName', title : '开票单位', width : 80,rowspan:'2'},
												                                                                                                                                 
												{field : 'tmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalesamount', title : '销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodBalanceamount', title : '结算收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmaPeriodSalesnum', title : '发票量', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodBalanceamount', title : '发票额  ', width : 90, align:'right',halign:'center',exportType:'number'},
								    			{field : 'tmaPeriodNonTaxBalanceamount', title : '无税金额', width : 60, align:'right',halign:'center',exportType:'number'},
								    			{field : 'invoiceApplyDate', title : '发票日', width : 80},
												
												{field : 'tmSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalesamount', title : '销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalesnum', title : '成本销售量', width : 60, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmSalesdeductions', title : '扣费  ', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'tmBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
												
												{field : 'nonTaxSalesamount', title : '无税收入', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'taxCost', title : '含税成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'noTaxCosts', title : '无税成本', width : 90, align:'right',halign:'center',exportType:'number'},
												{field : 'headquarterCost', title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number'}
								    		]];
							exportTempletExcel({
								dataGridId : "salesSumDataGrid",
								url : "/bill_sales_sum/do_fas_export",
								title : "销售汇总表",
								grepColumns:columns
							});
						}
						dialog.close();
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
	var url = BasePath + setting.url;
	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	fromObj.form('submit', {
		url : url,
		onSubmit : function(param) {
			param.exportColumns = exportColumns;
			param.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
			param.fileName = setting.title;
			param.exportType = setting.exportType || '';
			if(params != null && params != {}) {
				$.each(params, function(i) {
					param[i] = params[i];
				});
			}
		},
		success : function() {

		}
	});
};
