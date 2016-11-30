<div class="easyui-layout" data-options="fit:true">
	<#--工具栏   -->  
	<div data-options="region:'north',border:false">
		<@p.billToolBar type="shop_balance_deduct_costafter_listBar"/>
	</div>
                   
	<#--列表-->
	<div data-options="region:'center',border:false" id="afterDataGridDiv">
		<@p.datagrid id="shopBalanceDeductAfterDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true"  showFooter="true"
			checkOnSelect="true" rownumbers="true" singleSelect="false"  
			frozenColumns="[
				{field : 't',checkbox:true,width : 30,notexport:true},								
				{field : 'id',hidden : 'true',align:'center',notexport:true},		
				{field : 'rateId',hidden : 'true',align:'center',notexport:true},		 
				{field : 'organNo', hidden:true,notexport:true,title : '管理城市', align:'left',width : 100, editor:{type:'hiddenbox',options:{id:'organNo_',name:'organNo'}}},
				{field : 'organName',title : '管理城市', align:'left',halign:'center',width : 60, editor:{type:'searchboxname',options:{id:'organName_',name:'organName',readonly:true}}},	                 
				{field : 'shortName', title : '店铺名称', align:'left',width : 150,halign:'center', 
					editor:{
						type:'shop',
						options:{
							id:'shortName_',
							name:'shortName',
							inputNoField:'shopNo_',
							required:true,
							relationData:true,
							callback : function(row) {
								if(row) {
			       					$('#shortName_').val(row.shortName);
			       					$('#shopNo_').val(row.shopNo);
		       						$.ajax({
		       			                url: BasePath + '/shop/initSubInfo?'+jQuery.param({shopNo:row.shopNo}),
		       			                async:false,
		       			                cache: true
		       			            }).then(function(result) {
		       			                $('#shopNo_').val(result.shopNo);	
		       			                $('#companyNo_').val('');
									    $('#companyName_').val('');									
										$('#bsgroupsNo_').val(result.bsgroupsNo);
										$('#bsgroupsName_').val(result.bsgroupsName);
										$('#mallNo_').val(result.mallNo);
										$('#mallName_').val(result.mallName);
										$('#organNo_').val(result.organNo);
										$('#organName_').val(result.organName);
										$('#brandNo_').val(result.brandNo);
										$('#brandName_').combogrid('setValue',result.brandName);
										$('#companyNo_').val(result.companyNo);
								 	    $('#companyName_').val(result.companyName);
										if(result.companyNo.substring(0,1) == 'K'){
											$(deductAmount).numberbox('disable');
											$(deductAmount).numberbox('clear');
										}
										else {
											$(deductAmount).numberbox('enable');
										}
		       			            });
				                } else {
				                	$('#shortName_').combogrid('clear');
		                	   	   	$('#shopNo_').val('');
				                }
							}
						}
					}
				},
				{field : 'shopNo', hidden:true, title : '店铺编码', align:'left',width : 80, notexport:true,editor:{type:'hiddenbox',options:{id:'shopNo_',name:'shopNo'}}}, 
				
				{field : 'brandNo',hidden : true,title : '品牌', align:'left',width : 80, notexport:true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandNo_',
												name:'brandNo'
											}
										}
				                  },
				{field : 'brandName', title : '品牌部名称', width : 100,halign:'center',
					editor:{
						type:'combogrid',
						options:{
							id:'brandName_',
							name:'brandName',
							inputNoField:'brandNo_',
							required:true,
							idField:'brandName',
							textField:'brandName',
							noField:'brandNo',
							columns:[[
								{field : 'brandNo',title : '品牌部编码',width : 100, halign : 'center', align : 'left'},
								{field : 'brandName',title : '品牌部名称',width : 150, halign : 'center', align : 'left'}
							]],
							onShowPanel:function(){
								if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
									var url = BasePath+'/shop_brand/list.json?' 
				 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
				 	            	$(this).combogrid('grid').datagrid('options').url= url;
				 	            	$(this).combogrid('grid').datagrid('load');
								}
								else {
									showWarn('请先选择店铺！');
									return;
								}
								
							},
						}
					}
				},				
			    {field : 'month', title : '结算月', align:'center',halign:'center',width : 70,
					editor:{
						type:'combogrid',
						options:{
							required:true,
							id:'month_',
							name:'month_',
							inputWidth:60,
							idField:'month',
							textField:'month',
							noField:'month_',
							inputNoField:'month',
							url:'',
							paramMap:[{name:'shopNo', field:'shopNo_'}],
							columns:[[ 
							    {field : 'month',title : '结算月',width : 80, align:'center',halign:'center'},
								{field:'balanceStartDate',title:'起始日期',width:100,align:'center',halign:'center'},
								{field:'balanceEndDate',title:'终止日期',width:100,align:'center',halign:'center'},
							]],
							onShowPanel:function(){
								if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
									var url = BasePath+'/shop_balance_date/list.json?' 
				 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
				 	            	$(this).combogrid('grid').datagrid('options').url= url;
				 	            	$(this).combogrid('grid').datagrid('load');
								}
								else {
									showWarn('请先选择店铺！');
									return;
								}
								
							},
							callback:function(data){
								if(data){
									$('#month_').val(data.month);
									$('#balanceStartDate').val(data.balanceStartDate);
									$('#balanceEndDate').val(data.balanceEndDate);
									$('#companyNo_').val(data.companyNo);
									$('#companyName_').val(data.companyName);
								}
							}
						}
					}
				},
			]"
			columnsJsonList="[
				{field : 'mallDeductionName',title : '商场扣费名称',width : 150,align:'left',halign:'center',
					editor:{
						type:'validatebox'
					}
				},
				{field : 'deductionName', title : '扣费名称', align:'left',halign:'center',width : 100,
					editor:{
						type:'combogrid',
						options:{
							required:true,
							id:'deductionName',
							name:'deductionName',
							inputWidth:60,
							idField:'name',
							textField:'name',
							noField:'code',
							inputNoField:'deductionNo',
							url:'',
							paramMap:[{name:'shopNo', field:'shopNo_'}],
							columns:[[ 
								{field : 'code',title : '扣费编码',width : 30, halign : 'center', align : 'left'},
								{field : 'name',title : '扣费名称',width : 150, halign : 'center', align : 'left'}
							]],
							onShowPanel:function(){
								if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
									var url = BasePath+'/mall_deduction_set/select_deduct_cost?status=1&' 
				 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
				 	            	$(this).combogrid('grid').datagrid('options').url= url;
				 	            	$(this).combogrid('grid').datagrid('load');
								}
								else {
									showWarn('请先选择店铺！');
									return;
								}
								
							},
							callback:function(data){
								if(data){
									$('#deductionCode').val(data.code);
									$('#costCateCode').val(data.costCode);
									$('#costCateName').val(data.costName);
									$('#accountsNo').val(data.accountsNo);
								}
							}
						}
					}
				},
				{field : 'deductionCode', hidden:true,title : '扣费编码', align:'left',width : 80, notexport:true,
					editor:{
						type:'hiddenbox',
						options:{
							id:'deductionCode',
							name:'deductionCode'
						}
					}
				},  
			{field : 'deductAmount',title : '系统扣费金额',width : 110,align:'right',halign:'center',exportType:'number',
	                	editor:{
	                		type:'fasnumberbox',
	                		options:{
	                			id:'deductAmount',
	                			name:'deductAmount',
	                			required:false,
	                  			precision:2,
	                  			validType:['maxLength[12]']
	                		}	
	                	}
	                },

				{field : 'actualAmount',title : '实际扣费金额',width : 90,align:'right',exportType:'number',
					editor:{
						type:'fasnumberbox',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				},  
				{field : 'diffAmount',title : '扣费差异金额',width : 90,align:'right',exportType:'number',
					editor:{
						type:'readonlytext',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				}, 	
				{field : 'diffReason',title : '差异原因',width : 100,align:'center',								
					editor:{
						type:'validatebox',
						options:{
							required:false
						}
					}
				},  
				{field : 'costType',title : '费用性质 ',width : 70,align:'center',
					formatter: aftereditor.dataCostType, 
					editor:{
						type:'combobox',
							options:{
							   	data: [{'value':'1', 'text': '合同内'},
							   	 {'value':'2', 'text':'合同外'}
							   	 ], 
								valueField: 'value', textField: 'text',
								required:true
							}
					}
				}, 
				{field : 'processStatus',title : '处理状态',width : 60,align:'center', formatter: aftereditor.processStatus,
					editor:{
						type:'combobox',
						options:{
							valueField: 'value',  
							textField: 'text',
							required:true,
							data: [{'value':'1', 'text': '未完成'}, {'value':'2', 'text':'已完成'}]
						}
					}
				},  
				{field : 'balanceStartDate',title : '结算起始日期', align:'center',width : 100, 
					editor:{
							type:'searchboxname',
							options:{id:'balanceStartDate',name:'balanceStartDate',readonly:true}
					}
				},
				{field : 'balanceEndDate', title : '结算终止日期', align:'center',width : 100, 
					editor:{
							type:'searchboxname',
							options:{id:'balanceEndDate',name:'balanceEndDate',readonly:true}
					}
				}, 
				{field : 'deductDate',hidden : 'true',title : '扣费日期',width : 80,align:'left',
					editor:{
					type:'datebox',
						options:{
							required:false
						}
					}
				},
				{field : 'costCateCode',hidden:true,notexport:true, title : '总账费用编码', align:'left',width : 50, editor:{type:'hiddenbox',options:{id:'costCateCode',name:'costCateCode'}}},
				{field : 'costCateName', title : '总账费用名称', align:'left',width : 90, editor:{type:'searchboxname',options:{id:'costCateName',name:'costCateName',readonly:true}}},
				{field : 'accountsNo', title : '会计科目编码', width : 100,align:'center',
					editor:{type:'readonlybox',options:{id:'accountsNo',required:false}}},
				{field : 'costDeductType',title : '扣取方式 ',width : 70,align:'center',
					formatter: aftereditor.dataCostDeductType, 
					editor:{
						type:'combobox',
						options:{
							data: [{'value':'1', 'text': '票前 '},{'value':'2', 'text':'票后'}], 
							valueField: 'value', textField: 'text',
							required:false
						}
					}
				},
				{field : 'costPayType',title : '交款方式 ',width : 70,align:'center', formatter: aftereditor.dataCostPayType,
					editor:{
						type:'combobox',
						options:{
							valueField: 'value', 
							textField: 'text',
							required:true,
							data: [{'value':'0', 'text': '  '},{'value':'1', 'text': '帐扣'},{'value':'2', 'text':'现金'}]
						}
					}
				}, 
				{field : 'invoiceAmount',title : '发票金额',width : 70,align:'right',halign:'center',exportType:'number',
					editor:{
						type:'numberbox',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				},  
				{field : 'invoiceNo', title : '发票号', width : 100,align:'center',
					editor:{type:'validatebox',options:{required:false}}},
				{field : 'accountDeductAmount',title : '费用余额',width : 70,align:'right',halign:'center',exportType:'number',
					editor:{
						type:'readonlytext',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				},
				{field : 'basePayCode',title : '支付方式',width : 100,align:'center',
					formatter: aftereditor.dataBasePayCode,								
					editor:{
						type:'readonlybox',
						options:{
							required:false
						}
					}
				},
				{field : 'baseOther',title : '其他',width : 100,align:'center',	
					formatter: aftereditor.dataBaseOther,							
					editor:{
						type:'readonlybox',
						options:{
							required:false
						}
					}
				},
				{field : 'rate',title : '费率%',width : 70,align:'right',halign:'center',exportType:'number',
					editor:{
						type:'readonlybox',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				}, 
				{field : 'balanceRate',title : '补差费率%',width : 70,align:'right',halign:'center',exportType:'number',
					editor:{
						type:'readonlybox',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				}, 
				{field : 'rateAmount',title : '费率金额',width : 70,align:'right',halign:'center',exportType:'number',
					editor:{
						type:'readonlybox',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				}, 
				{field : 'balanceRateAmount',title : '补差费率金额',width : 90,align:'right',halign:'center',exportType:'number',
					editor:{
						type:'readonlybox',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				},
				{field : 'companyNo',hidden:true,notexport:true, title : '公司编码', align:'left',width : 80, editor:{type:'hiddenbox',options:{id:'companyNo_',name:'companyNo'}}},
				{field : 'companyName', title : '公司', align:'left',halign:'center',width : 180, editor:{type:'searchboxname',options:{id:'companyName_',name:'companyName',readonly:true}}},
							                  
				{field : 'bsgroupsNo',hidden:true, title : '商场体系', align:'left',width : 80, editor:{type:'hiddenbox',options:{id:'bsgroupsNo_',name:'bsgroupsNo'}}},
				{field : 'bsgroupsName', title : '商业集团', align:'left',halign:'center',width : 90, editor:{type:'searchboxname',options:{id:'bsgroupsName_',name:'bsgroupsName',readonly:true}}}, 				                 
				{field : 'mallNo',hidden:true,notexport:true, title : '商场编码', align:'left',width : 80, editor:{type:'hiddenbox',options:{id:'mallNo_',name:'mallNo'}}},
				{field : 'mallName', title : '商场', align:'left',halign:'center',width : 150, editor:{type:'searchboxname',options:{id:'mallName_',name:'mallName',readonly:true}}},
				{field : 'generateType',title : '生成方式',width : 100,align:'center',notexport:true,		
					formatter: aftereditor.generateType, 						
					editor:{
						type:'combobox',
						options:{
						valueField: 'value',  
						textField: 'text',
						required:false,
						data: [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}]
						}
					}
				}, 
				{field : 'remark',title : '备注',width : 150,align:'left',halign:'center',
					editor:{
						type:'validatebox'
					}
				},
				{field : 'balanceNo', title : '结算单号', width : 150,align:'left',halign:'center'},
				 	
				{field : 'deductType',hidden:true,title : 'deduct_type',width : 90,align:'right',notexport:true,
					editor:{
						type:'readonlytext',
						options:{
							required:false,precision:2,validType:['maxLength[12]']
						}
					}
				}, 
				{field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				{field : 'createTime',title : '建档时间',width : 150,align:'center'},
				{field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				{field : 'updateTime',title : '修改时间',width : 150,align:'center'}	 
			]" 
			jsonExtend="{
				onDblClickRow:function(rowIndex, rowData){
					//双击方法
					aftereditor.editRow(rowIndex, rowData);
				}
			}"
		/>
	</div>
</div>
