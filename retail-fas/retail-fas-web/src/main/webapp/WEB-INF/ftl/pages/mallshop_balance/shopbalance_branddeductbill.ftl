<div class="easyui-layout" id ="difffDiv" data-options="fit:true,plain:true">  
	<div data-options="region:'north',border:false">
    	<@p.billToolBar type="shop_balance_balance_diff_operaBar"/>
    </div>
		
   <div data-options="region:'center',border:false" id="balanceDiffDataGridDiv">
      <@p.datagrid id="balanceBrandDeductDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
	               isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	               checkOnSelect="true"   showFooter="true"	
		           rownumbers="true" singleSelect="false"
		           columnsJsonList="[				           
	                 	{field : 't', checkbox:true, width : 30, notexport:true},
	                  	{field : 'id',hidden : true,title : 'id',align:'center',notexport:true,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'diffDtlId',
		                  			name:'id'
		                  		}
	                  	}},
                  		{field : 'billNo',hidden : true,title : 'diffBillNo',align:'center',notexport:true,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'diffbackBillNo',
		                  			name:'billNo'
		                  		}
	                  	}},	
	                  	{field : 'balanceNo',hidden : true,title : 'balanceNo',align:'center',notexport:true,editor:{
		                  		type:'hiddenbox',
		                  		options:{
		                  			id:'balanceNo',
		                  			name:'balanceNo'
		                  		}
	                  	}},	
						{field : 'brandNo',hidden : true, title : '品牌编码', width : 130, notexport:true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandNo',
												name:'brandNo'
											}
										}
				        },
				        {field : 'brandName', title : '品牌', width : 100,halign:'center',
				                  	   editor:{
					                  		type:'brand',
					                  		options:{
					                  			inputNoField: 'brandNo',
					                  			required:true
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
		                {field : 'mallNumber',title : '商场报数',width : 80,align:'right',								
		                  	editor:{
		                  		type:'numberbox',
		                  		options:{
		                  			required:false,
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                 }, 
						{field : 'salesAmount',title : '系统收入',width : 80,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
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
		                  {field : 'reason',title : '原因',width :100,align:'center',								
		                  	editor:{
		                  		type:'validatebox',
		                  		options:{
		                  			required:false
		                  		}
		                  	}
		                  },		
		                {field : 'salesDiffamount',title : '实际扣费金额',width :100,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  },
		               {field : 'salesDiffamount',title : '扣费总额',width :100,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
		                  		options:{
		                  			precision:2,
		                  			validType:['maxLength[12]']
		                  		}
		                  	}
		                  }, 
		               {field : 'salesDiffamount',title : '开票金额',width :100,align:'right',								
		                  	editor:{
		                  		type:'fasnumberbox',
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
	                  },
	                  {field : 'createUser',title : '建档人',width : 60,align:'left'}, 
		                {field : 'createTime',title : '建档时间',width : 130,align:'center'},
		                {field : 'updateUser',title : '修改人',width : 60,align:'left'}, 
		                {field : 'updateTime',title : '修改时间',width : 130,align:'center'}	
				]" 
				jsonExtend="{				
					onDblClickRow:function(rowIndex, rowData){
						balanceDiffEditor.editRow(rowIndex, rowData);
		 			}
				}"/>
 		</div>
</div>
			