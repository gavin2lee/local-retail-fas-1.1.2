<div class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false">
    	<@p.toolbar id="balanceDeductAfterBar" listData=[
			{"id":"addDeductBtn","title":"新增行","iconCls":"icon-add-dtl","action":"balanceDeductAfterEditor.insertRow()","type":0},
			{"id":"delDeductBtn","title":"删除行","iconCls":"icon-del-dtl","action":"balanceDeductAfterEditor.deleteRow()","type":0}
		 ]/>
    </div>
	<div data-options="region:'center',border:false" id="balanceDeductAfterDataGridDiv">
      <@p.datagrid id="balanceDeductAfterDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="true"  showFooter="true"
	           columnsJsonList="[
	                {field : 't', checkbox:true, width : 30, notexport:true,printable:false},
	                {field : 'id',hidden : 'true',align:'center',notexport:true,printable:false},
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
				 	            		+ jQuery.param({balanceNo:$('#balanceNo').val()});
				 	            	$(this).combogrid('grid').datagrid('options').url= url;
				 	            	$(this).combogrid('grid').datagrid('load');
								},
						}
					}
				},
	                {field : 'month',title:'结算月',width:70,align:'left'}, 
	                 {field : 'mallDeductionName',title : '商场扣费名称',width : 100,align:'left',								
			          editor:{
			             	type:'validatebox',
			             	options:{
			             	    id:'mallDeductionName',
	                			name:'mallDeductionName',
			            		required:false
			          		}
				       }
				     }, 
	                {field : 'deductionCode',hidden : 'true',title : '扣费编码',width : 70,align:'left',printable:false,
	                	editor:{
	                		type:'readonlybox',
	                		options:{
	                			id:'deductionCode',
	                			name:'deductionCode'
	                		}	
	                	}
	                },
	                {field : 'deductionName', title : '扣费类别', width : 110,align:'left',
	                	editor:{
	                		type:'combogrid',
	                		options:{
	                			id:'deductionName',
	                			name:'deductionName',
	                			idField:'name',
	                			textField:'name',
	                			noField:'code',
	                			inputNoField:'deductionCode',
	                			url:BasePath+'/mall_deduction_set/list.json?status=1',
	                			paramMap:[{name:'shopNo', field:'shopNo'}],
	                			required:true,
	                			columns:[[
					                {field : 'code',title : '扣费编码',width : 30, halign : 'center', align : 'left'},
					        	    {field : 'name',title : '扣费名称',width : 150, halign : 'center', align : 'left'}
					            ]]
	                		}
	                	}
	                },
	                {field : 'costType',title : '费用性质',width : 80,align:'left', formatter: balanceDeduct.dataCostType,printable:false,
	                	editor:{
	                		type:'combobox',
	                		options:{
	                			id:'costType',
	                			name:'costType',
	                			width:80,
	                			required:true,
	                			data:[{value:'1',text:'合同内'},{value:'2',text:'合同外'}]
	                		}
	                	}
	                },	
	                {field : 'costPayType',title : '交款方式 ',width : 70,align:'center', formatter: balanceDeduct.dataCostPayType,printable:false,
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
	                {field : 'deductAmount',title : '系统扣费金额',width : 110,align:'right',
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
					{field : 'actualAmount',title : '实际扣费金额',width : 110,align:'right',
	                  	editor:{
	                  		type:'fasnumberbox',
	                  		options:{
	                  			id:'actualAmount',
	                			name:'actualAmount',
	                  			required:false,
	                  			precision:2,
	                  			validType:['maxLength[12]']
	                  		}
	                  	}
	                  }, 
	                  {field : 'diffAmount',title : '扣费差异金额',width : 100,align:'right',
		            		editor:{
				               type:'readonlybox',
				               options:{
			               			id:'diffAmount',
				               		name:'diffAmount'
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
                  	 {field : 'deductDate',hidden : 'true',title : '扣费日期',width : 80,align:'left',printable:false,
						editor:{
						 	type:'datebox',
						 	options:{
							 	required:false
						 	}
						}
			     	  },	
			     	  {field : 'generateType',hidden : 'true',title : '生成方式',width : 100,align:'center',notexport:true,printable:false,							
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
	                  {field : 'processStatus',title : '处理状态 ',width : 70,align:'left',printable:false,
	                   	formatter: balanceDeduct.processStatus, 				                  	
	                  	editor:{				                     
	                  		type:'combobox',
	                  		options:{
	                  	    	data: [{'value':'1', 'text': '未完成'},
	                  	    	  {'value':'2', 'text':'已完成'}				                  	    	
	                  	    	 ], 
	                  			valueField: 'value', textField: 'text',
	                  			required:false
	                  		}
	                  	}
	                  },	
	                  {field : 'createUser',title : '建档人',width : 60,align:'left',printable:false}, 
		                {field : 'createTime',title : '建档时间',width : 130,align:'center',printable:false},
		                {field : 'updateUser',title : '修改人',width : 60,align:'left',printable:false}, 
		                {field : 'updateTime',title : '修改时间',width : 130,align:'center',printable:false}							               
	                 ]" 
		          	 jsonExtend='{
	                    onDblClickRow:function(rowIndex, rowData){
		                   	   balanceDeductAfterEditor.editRow(rowIndex, rowData);	
		                }
         }'/>
	</div>
</div>