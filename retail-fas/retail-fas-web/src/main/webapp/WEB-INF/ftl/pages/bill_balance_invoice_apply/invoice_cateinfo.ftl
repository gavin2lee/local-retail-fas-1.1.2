<div class="easyui-layout" data-options="fit:true,plain:true">
	 <div data-options="region:'north',border:false">
    	<@p.toolbar id="invoice_cateinfo_bars" listData=[
			{"id":"foot_btn_pre","title":"新增行","iconCls":"icon-add-dtl","action":"editor.insertRow()","type":1} ,
			{"id":"foot_btn_next","title":"删除行","iconCls":"icon-del-dtl","action":"editor.deleteRow()","type":3},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"billBalanceInvoiceApply.exportInvoicDdtl()","type":4}
		 	<!--{"id":"foot_btn_next","title":"保存","iconCls":"icon-save-dtl","action":"editor.save()","type":0}-->
		 ]/>
		 <input class="easyui-combobox"  name="showType"  id="showType"/>
    </div>
        
	<div data-options="region:'center',border:false">
	     <@p.datagrid id="invoiceCateDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
	              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	               checkOnSelect="true" emptyMsg="" rownumbers="false"  singleSelect="false" showFooter="true"
		           columnsJsonList="[
			{field : 't', checkbox:true,width : 30,notexport:true},
			{field : 'balanceStartDate',title : '结算开始时间',width : 100,
				editor:{
				 type:'datebox',				 
				 options:{
				 		required:true
				 	}
				}
			},	
			{field : 'balanceEndDate',title : '结算结束时间',width : 100,
				editor:{
				 type:'datebox',				 
				 options:{
				 		required:true
				 	}
				}
			},
			{field : 'shopNo', title : '店铺编码', width : 120, hidden:true,printable:false,
				editor:{
					type:'hiddenbox',
					options:{
						id:'shopNo',
						name:'shopNo', 
						inputWidth:90,
						required:true, 
						relationData:false
					}
				}
			},
          	{field : 'shortName', title : '店铺简称', width : 160},
		  	{field : 'fullName', title : '店铺名称', hidden:true, width : 100, align:'left', notexport:true, printable:false,
		  		editor:{type:'hiddenbox',
		  			options:{
						id:'fullName',
						name:'fullName',
						readonly:true
					}
				}
		  	},
		  	{field : 'retailType', title : '店铺小类', width : 60, hidden:true,printable:false,
				editor:{
					type:'hiddenbox',
					options:{
						id:'retailType',
						name:'retailType', 
						inputWidth:90
					}
				}
			},
		  	{field : 'retailTypeName',title : '店铺小类名称',width : 100,
				editor:{
					type:'searchboxname',
				 	options:{
				 		id:'retailTypeName',
						name:'retailTypeName',
						readonly:true,
						width:'130px' 
				 	}
				}
			 },
			{field : 'brandName',title : '品牌',width : 130},
			{field : 'brandNo',title : '品牌编号',width : 100,
				editor:{
					type:'searchboxname',
				 	options:{
				 		id:'brandNo1',
						name:'brandNo1',
						readonly:true,
						width:'130px' 
				 	}
				}
			 },
			{field : 'categoryName',title : '大类',width : 140},
			{field : 'categoryNo',title:'大类编码',width:100, hidden:true,printable:false, 
				editor:{
					type:'hiddenbox',
					options:{
						id:'categoryNo',
						name:'categoryNo'
					}
				}
          	},
			{field : 'salerName',title : '商品开票名称',width : 140,
				editor:{
					type:'searchboxname',
				 	options:{
						id:'dtlSalerName',
						name:'dtlSalerName',
						width:'130px' 
				 	}
				}
			 },
          	{field : 'organName',title : '管理城市',width : 140},
          	{field : 'organNo',title:'管理城市编码',width:100, hidden:true, printable:false, 
				editor:{
					type:'hiddenbox',
					options:{
						id:'organNo',
						name:'organNo'
					}
				}
          	},
			{field : 'qty',title : '数量',width : 100,
				editor:{
				 type:'numberbox',
				 required:true
				}
			},
			{field : 'sendAmount',title : '金额',width : 100,
				editor:{
					type:'numberbox',
					options:{
					 	required:true,
					 	precision:2,
					 	onChange:function(newValue,oldValue){
					 	    if($('#invoiceType').combobox('getValue') == 1){
    					 		$('#noTaxAmount').val(parseFloat(newValue/(1+0.17)).toFixed(2));
					 			$('#taxAmount').val(parseFloat(newValue*0.17/(1+0.17)).toFixed(2));
					 	    }
					 		//billBalanceInvoiceApply.setAllAmount();
					 	}
					}
				}
			},
			{field : 'taxRate',title : '税率',width : 60,align:'right',halign:'center',
				                  	editor:{type:'numberbox',options:{required:true, precision:2,min:0,max:1}}},
			{field : 'taxAmount',title : '税额',width : 100,
				editor:{
					type:'searchboxname',
					options:{
						id:'taxAmount',
						name:'taxAmount', 
						readonly:true,
						width:'90px'
					}
				}
			},
			{field : 'noTaxAmount',title : '不含税金额',width : 100,
				editor:{
					type:'searchboxname',
					options:{
						id:'noTaxAmount',
						name:'noTaxAmount', 
						readonly:true,
						width:'90px'
					}
				}
			},
			{field : 'estimatedAmount',title : '预估成本',width : 100,printable:false,
				editor:{
				 type:'numberbox',
				 required:true
				}
			},
			{field : 'posEarningAmount',title : '终端收入金额',width : 100,printable:false,
				editor:{
				 type:'numberbox',
				 required:true
				}
			},
			{field : 'contractRate',title : '合同扣率',width : 100,printable:false,
				editor:{
				 type:'numberbox',
				 required:true
				}
			},
			{field : 'actualRate',title : '实际扣率',width : 100,printable:false,
				editor:{
				 type:'numberbox',
				 required:true
				}
			}
			]" 
		 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
		 	//双击方法
		    editor.editRow(rowIndex,rowData);
		 }}"/>
 </div>
</div>