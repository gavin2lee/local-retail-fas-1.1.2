<div class="easyui-layout" data-options="fit:true,plain:true">   
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="invoiceSourceDataGrid"  
		loadUrl="" saveUrl=""   defaultColumn="" 
		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		 checkOnSelect="true"  rownumbers="false" singleSelect="true"  
		columnsJsonList="[			
			<!--
			{field : 'billNo',title : '发票申请单号',width : 150, hidden:true,
				editor:{
				 type:'validatebox',				 
				 required:true
				}
			},
			{field : 'salerNo',title : '开票方编码',width : 100, hidden:true,
				editor:{
				 type:'validatebox',
				 required:true
				}
			},
			{field : 'salerName',title : '开票方名称',width : 100, hidden:true,
				editor:{
				 type:'validatebox',
				 required:true
				}
			},
			{field : 'buyerNo',title : '收票方编码',width : 100, hidden:true,
				editor:{
				 type:'validatebox',
				 required:true
				}
			},
			{field : 'buyerName',title : '收票方名称',width : 100, hidden:true,
				editor:{
				 type:'validatebox',
				 required:true
				}
			},
			-->
			{field : 'balanceTypeStr',title : '源单类型',width : 150,
				editor:{
				 type:'validatebox',
				 required:true
				}
			},
			{field : 'balanceNo',title : '源单编码',width : 150,
				editor:{
				 type:'validatebox',
				 required:true
				}
			},
			
			{field : 'amount',title : '开票金额',width : 100,
				editor:{
				 type:'numberbox',
				 required:true
				}
			}
			]" 
		/>
 </div>
</div>
			