<div class="easyui-layout" id ="difffDiv" data-options="fit:true,plain:true">  
	<div data-options="region:'north',border:false">
    	<@p.billToolBar type="shop_balance_balance_diff_operaBar"/>
    </div>
		
   <div data-options="region:'center',border:false" id="balanceDiffDataGridDiv">
      <@p.datagrid id="balanceDiffDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
	               isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	               checkOnSelect="true"   showFooter="true"	
		           rownumbers="true" singleSelect="false"
		            frozenColumns="[				           
	                 	{field : 't', checkbox:true, width : 30, notexport:true},
	                  	{field : 'id',hidden : true,title : 'id',align:'center',notexport:true,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'diffDtlId',
		                  			name:'id'
		                  		}
	                  	}},
                  		{field : 'rootDiffId',hidden : true,title : 'rootDiffId',align:'center',notexport:true,printable:false,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'rootDiffId',
		                  			name:'rootDiffId'
		                  		}
	                  	}},
	                  	{field : 'billNo',hidden : true,title : 'diffBillNo',align:'center',notexport:true,printable:false,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'diffbackBillNo',
		                  			name:'billNo'
		                  		}
	                  	}},	
	                  	{field : 'balanceNo',hidden : true,title : 'balanceNo',align:'center',notexport:true,printable:false,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'balanceNo',
		                  			name:'balanceNo'
		                  		}
	                  	}},	
	                 	{field : 'diffTypeCode',hidden:true, title : '差异编码', width : 130, notexport:true,printable:false,
	                 		editor:{
	                 			type:'hiddenbox',
	                 			options:{
	                 				id:'diffTypeCode',
	                 				name:'diffTypeCode'
	                 			}
	                 		}
	                 	},			                 
		                {field : 'diffTypeName', title : '差异类型', width : 100,align:'left',
		                	editor:{
		                		type:'combogrid',
		                		options:{
		                			id:'diffTypeName',
		                			name:'diffTypeName',
		                			idField:'name',
		                			textField:'name',
		                			noField:'code',
		                			inputNoField:'diffTypeCode',
		                			url:BasePath+'/mall_shopbalancedifftype/list.json?status=1',
		                			paramMap:[{name:'companyNo', field:'companyNo'}],
		                			columns:[[ 
		                				{field : 'code',title : '差异类型编码',width : 60, halign : 'center', align : 'left'},
	        	      					{field : 'name',title : '差异类型名称',width : 180, halign : 'center', align : 'left'}
		                			]],
		                			required:true
		                		}
		                	}
		                }, 
						{field : 'diffDate',title : '差异结算日',width:80,align:'center',
							editor:{
								type:'datebox',
								options:{
									disabled:false
								}
							}
						},	
						{field : 'brandNo',hidden : true,title : '品牌', align:'left',width : 80, notexport:true,printable:false,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandNo_',
												name:'brandNo'
											}
										}
				                  },
					   	{field : 'brandName', title : '品牌部名称', width : 80,halign:'center',
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
										{field : 'brandNo',title : '编码',width : 20, halign : 'center', align : 'left'},
										{field : 'brandName',title : '名称',width : 50, halign : 'center', align : 'left'}
									]],
									onShowPanel:function(){
											var url = BasePath+'/mall_shopbalance/shop_balance_brand.json?' 
						 	            		+ jQuery.param({balanceNo:$('#balanceNo').val(),shopNo:$('#shopNo').val()});
						 	            	$(this).combogrid('grid').datagrid('options').url= url;
						 	            	$(this).combogrid('grid').datagrid('load');
										},
								}
							}
						},
						]"
			   columnsJsonList="[
						{field : 'proStartDate',title : '活动起始日',width:80,align:'center',
							editor:{
								type:'fasdatetimebox',
								options:{
									id:'proStartDate',
									name:'proStartDate'
								}
							}
						},	
						{field : 'proEndDate',title : '活动终止日',width:80,align:'center',
							editor:{
								type:'fasdatetimebox',
								options:{
									id:'proEndDate',
									name:'proEndDate'
								}
							}
						},		
						{field : 'proName',title : '活动名称',width : 100,align:'left',								
		                  	editor:{
		                  		type:'combogrid',
		                  		options:{
		                  			id:'proName',
		                  			name:'proName',
		                  			panelWidth:500,
		                  			idField:'proName',
		                			textField:'proName',
		                			noField:'proNo',
		                			inputNoField:'proNo',
		                			url:BasePath+'/bill_shop_balance_pro_sum/list.json',
		                			columns:[[ 
		                				{field : 'proNo',title : '活动编号',width : 70, halign : 'center', align : 'left'},
	        	      					{field : 'discountCode',title : '活动代码',width : 70, halign : 'center', align : 'left'},
	        	      					{field : 'proName',title : '活动名称',width : 100, halign : 'center', align : 'left'},
	        	      					{field : 'proStartDate',title : '促销开始时间',width : 100, halign : 'center', align : 'left'},
	        	      					{field : 'proEndDate',title : '促销结束时间',width : 100, halign : 'center', align : 'left'},
	        	      					{field : 'discount',title : '扣率',width : 70, halign : 'center', align : 'left'},
	        	      					{field : 'saleAmount',title : '销售收入',width : 80, halign : 'center', align : 'left'}
		                			]],
		                			required:false,
		                			paramMap:[{name:'shopNo', field:'shopNo'},{name:'balanceNo', field:'balanceNo'}],
		                			callback:function(row) {
		                				$('#discountCode').val(row.discountCode);
		                				$('#proNo').val(row.proNo);
		                				$('#proName').combogrid('setValue',row.proName);
		                				$('#proStartDate').datebox('setValue',row.proStartDate);
		                				$('#proEndDate').datebox('setValue',row.proEndDate);
		                				$('#discountN').val(row.discount);
		                				$('#discount').numberbox('setValue',row.discount);
		                			},
		                			onChange:function(newValue, oldValue) {
		                				selectProNameEvent(newValue, oldValue);
		                			}
		                  		}
		                  	}
	                  	},	
	                  	{field : 'discountCode',title : '活动代码',width : 80,align:'center',
							editor:{
								type:'fastextbox',
								options:{
									id:'discountCode',
									name:'discountCode'
								}
							}
						},
						{field : 'discountN',title : '扣率%',width : 60,align:'center',
							editor:{
								type:'readonlybox',
								options:{
									id:'discountN',
									name:'discountN'
								}
							}
						},
						{field : 'discount',hidden:true,title : '扣率',width : 80,align:'right',	notexport:true,printable:false,							
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  			id:'discount',
		                  			name:'discount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
						{field : 'deductDiffAmount',title : '系统扣费',width : 80,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  			id:'deductDiffAmount',
		                  			name:'deductDiffAmount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                },	
		                {field : 'reportDiffAmount',title : '报数差异',width :80,align:'right',								
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },		                   
						{field : 'mallNumber',title : '商场报数(A)',width : 80,align:'right',								
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                 }, 	
						{field : 'salesAmount',title : '系统收入(B)',width : 80,align:'right',								
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			id:'salesAmount',
		                  			name:'salesAmount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
		                  {field : 'salesDiffamount',title : '差异金额',width :100,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
						{field : 'diffAmount',title : '扣费差异',width : 80,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  	    	id:'diffAmount',
		                  			name:'diffAmount',
		                  			required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
		                  {field : 'balanceDiffAmount',title : '结算差异',width :80,align:'right',								
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
		                 {field : 'diffAmountVal',title : '初始扣费差异的值',width : 80,align:'right',hidden:true,notexport:true,printable:false,							
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },				                  
						{field : 'diffReason',title : '差异原因',width : 100,align:'center',								
		                  	editor:{
		                  		type:'validatebox',
		                  		options:{
		                  			id:'diffReason',
		                  			name:'diffReason',
		                  			required:false
		                  		}
		                  	}
		                  },	
		                  {field : 'preDiffBalance',title : '上期差异余额',width : 100,align:'right',
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			id:'preDiffBalance',
		                  			name:'preDiffBalance',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'diffBalance',title : '本期差异余额',width : 100,align:'right',								
		                  	editor:{
		                  		id:'diffBalance',
		                  		name:'diffBalance',
		                  		type:'numberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'diffBalanceVal',title : '初始本期差异余额',width : 100,align:'right',hidden:true,notexport:true,printable:false,
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'diffBalanceSum',hidden:true, title : '累计差异余额',width : 80,align:'right',notexport:true,printable:false,								
		                  	editor:{
		                  		type:'readonlytext',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'diffBackAmount',title : '差异回款',width : 80,align:'right',								
		                  		editor:{
		                  			type:'diffback',
		                  			options:{
		                  				id:'diffBackAmount',
		                  				name:'diffBackAmount',
		                  				precision:2,
		                  			    validType:['maxLength[12]']
		                  			}
		                  		}
		                  },		              
		                   {field : 'diffBackAmountSum',hidden:true, title : '累计差异回款',width : 80,align:'right',notexport:true,printable:false,								
		                  	editor:{
		                  		type:'readonlytext',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
						{field : 'reason',title : '原因',width :100,align:'center',								
		                  	editor:{
		                  		type:'validatebox',
		                  		options:{
		                  			required:false
		                  		}
		                  	}
		                  },	
													
						{field : 'changeAmount',title : '调整金额',width : 80,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  			required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
						{field : 'changeReason',title : '调整原因',width : 100,align:'center',								
		                  	editor:{
		                  		type:'validatebox',
		                  		options:{
		                  			required:false
		                  		}
		                  	}
		                  },
	                    {field : 'changeDate',title : '调整日期',width : 80,align:'left',
							editor:{
								type:'datebox',
							 	options:{
								 	required:false
							 	}
							}
				     	},	
	                  	{field : 'status',title : '状态 ',width : 70,align:'left', formatter : balanceDiffEditor.formatStatus,printable:false,				                  	
		                  	editor:{				                     
		                  		type:'fascombobox',
		                  		options:{
		                  			id:'diffStatus',
		                  			name:'status',
		                  	    	data: [
		                  	    		{'value':'0', 'text': '未完成'},
		                  	    	 	{'value':'1', 'text':'已完成'}
		                  	    	 ], 
		                  			valueField: 'value', 
		                  			textField: 'text',
		                  			width:70,
		                  			required:false
		                  		}
		                  	}
		                  },	
	                    {field : 'balanceAmount',hidden:true,title : '本次结算金额',width : 100,align:'right',notexport:true,printable:false,editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			
		                  		}
		                  	}},	
	                  {field : 'billingCode',title : '结算码',width : 60,align:'center',
							editor:{
								type:'readonlybox',
								options:{
									id:'billingCode',
									name:'billingCode'
								}
							}
						},
	                  {field : 'proNo',title : '活动编码',width : 200,align:'center',printable:false,
							editor:{
								type:'fastextbox',
								options:{
									id:'proNo',
									name:'proNo'
								}
							}
						},
						{field : 'remark',title : '备注',width : 100,align:'right',
		                  	editor:{
		                  		type:'validatebox',
		                  		options:{
		                  		
		                  		}
		                  	}
	                  },
	                  {field : 'generateType',title : '生成方式',width : 100,align:'center',notexport:true,printable:false,		
						formatter: balanceDiffEditor.generateType, 						
						editor:{
							type:'readonlytext',
							options:{
							valueField: 'value',  
							textField: 'text',
							required:false,
							data: [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}]
							}
						}
					   }, 
	                    {field : 'createUser',title : '建档人',width : 60,align:'left',printable:false}, 
		                {field : 'createTime',title : '建档时间',width : 130,align:'center',printable:false},
		                {field : 'updateUser',title : '修改人',width : 60,align:'left',printable:false}, 
		                {field : 'updateTime',title : '修改时间',width : 130,align:'center',printable:false}	
				]" 
				jsonExtend="{				
					onDblClickRow:function(rowIndex, rowData){
						balanceDiffEditor.editRow(rowIndex, rowData);
		 			}
				}"/>
 		</div>
</div>
			