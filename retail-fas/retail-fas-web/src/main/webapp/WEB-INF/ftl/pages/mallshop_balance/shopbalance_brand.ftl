<div class="easyui-layout" id ="difffDiv" data-options="fit:true,plain:true">  

<!--
	<div data-options="region:'north',border:false">
    	<@p.toolbar id="balanceBrandBar" listData=[
			{"id":"addBrandBtn","title":"新增行","iconCls":"icon-add-dtl","action":"balanceBrandEditor.insertRow()","type":0},
			{"id":"delBrandBtn","title":"删除行","iconCls":"icon-del-dtl","action":"balanceBrandEditor.deleteRow()","type":0}			
		 ]/>
    </div>
-->

   <div data-options="region:'center',border:false" id="balanceBrandDataGridDiv">
      <@p.datagrid id="balanceBrandDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
	               isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	               checkOnSelect="true"   showFooter="true"	
		           rownumbers="true" singleSelect="false"
		           columnsJsonList="[				           
	                 	{field : 't', checkbox:true, width : 30, notexport:true,printable:false},
	                  	{field : 'id',hidden : true,title : 'id',align:'center',notexport:true,printable:false,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'diffDtlId',
		                  			name:'id'
		                  		}
	                  	}},
	                  	{field : 'operate',hidden : true,title : '操作',width : 80,align:'center',printable:false,formatter: function(value,row,index){
				                	return balanceBrandEditor.operate(value,row,index);
				                },notexport:true},
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
						{field : 'brandNo',hidden : true,title : '品牌', align:'left',width : 80,printable:false, notexport:true,
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
						 	            		+ jQuery.param({balanceNo:$('#balanceNo').val()});
						 	            	$(this).combogrid('grid').datagrid('options').url= url;
						 	            	$(this).combogrid('grid').datagrid('load');
										},
								}
							}
						},
				           {field : 'mallNumber',title : '商场报数',width : 80,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  		    id:'mallNumber',
		                  			name:'mallNumber',
		                  			required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                 }, 
						{field : 'salesAmount',title : '系统收入',width : 80,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			id:'salesAmount',
		                  			name:'salesAmount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                 {field : 'reportDiffAmount',title : '报数差异',width :80,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                   {field : 'systemBillingAmount',title : '系统开票金额',width : 85,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
			                  		id:'systemBillingAmountBrand',
		                			name:'systemBillingAmount',
		                  			required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                 }, 
						{field : 'mallBillingAmount',title : '商场开票金额',width : 85,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			id:'mallBillingAmountBrand',
		                  			name:'mallBillingAmount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'balanceDiffAmount',title : '结算差异(差异.扣费差异+票前.扣费差异)',width : 100,align:'right',
		            		editor:{
				               type:'readonlybox',
				               options:{
			               			id:'balanceDiffAmountBrand',
				               		name:'balanceDiffAmount',
				               		required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
						          	}
						        }
						    }, 	
				          {field : 'deductDiffAmount',title : '系统扣费(差异.系统扣费)',width : 80,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			id:'deductDiffAmountBrand',
		                  			name:'deductDiffAmount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                },	
		                 {field : 'balanceDeductAmount',title : '票前费用(票前.系统扣费)',width :100,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  		   id:'balanceDeductAmountBrand',
		                  			name:'balanceDeductAmount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
		               {field : 'deductAllamount',title : '扣费总额',width :100,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  		   id:'deductAllamount',
		                  			name:'deductAllamount',
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  }, 
		                  {field : 'prepaymentAmount',title : '预收款金额',width :100,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'usedPrepaymentAmount',title : '冲销金额',width :100,align:'right',								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		                  {field : 'status',title : '状态 ',width : 70,align:'left', formatter : balanceBrandEditor.formatStatus,hidden : true,printable:false, notexport:true,			                  			                  	
		                  	editor:{				                     
		                  		type:'fascombobox',
		                  		options:{
		                  			id:'diffStatus',
		                  			name:'status',
		                  	    	data: [
		                  	    	 	{'value':'1', 'text':'未确认'},
		                  	    	 	{'value':'2', 'text':'已确认'},
		                  	    	 	{'value':'3', 'text':'已开票'}
		                  	    	 ], 
		                  			valueField: 'value', 
		                  			textField: 'text',
		                  			width:70,
		                  			required:false,
		                  			callback:balanceBrandEditor.checkEvent  
		                  		}
		                  	}
		                  },
		                  {field : 'salesDiffamount',title : '差异金额',width :100,align:'right',hidden : true,notexport:true,printable:false,								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },	
						{field : 'diffAmount',title : '扣费差异',width : 80,align:'right',hidden : true,notexport:true,printable:false,								
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  	    	id:'diffAmount',
		                  			name:'diffAmount',
		                  			required:false,
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
		                {field : 'actualAmount',title : '实际扣费金额',width :100,align:'right',	hidden : true,notexport:true,printable:false,							
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		               
		               {field : 'billingAmount',title : '开票金额',width :100,align:'right',	hidden : true,notexport:true,printable:false,							
		                  	editor:{
		                  		type:'readonlybox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },     				                  
	                   {field : 'remark',title : '备注',width : 100,align:'right',
		                  	editor:{
		                  		type:'validatebox',
		                  		options:{
		                  		
		                  		}
		                  	}
	                  }
				]" 
				jsonExtend="{				
					onDblClickRow:function(rowIndex, rowData){
						balanceBrandEditor.editRow(rowIndex, rowData);
		 			}
				}"/>
 		</div>
</div>
			