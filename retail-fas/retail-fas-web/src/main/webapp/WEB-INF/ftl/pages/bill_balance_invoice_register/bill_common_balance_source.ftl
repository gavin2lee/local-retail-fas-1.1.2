<div class="easyui-layout" data-options="fit:true,plain:true">   
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="invoiceSourceDataGrid"  
		loadUrl="" saveUrl=""   defaultColumn="" 
		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		 checkOnSelect="true"  rownumbers="false" singleSelect="true"  
		columnsJsonList="[
			{field : 'billNo',title : '单号',width : 150,formatter: function(value,row,index){
					if(row.billNo){
						return row.billNo;
					}
					return row.orderNo;
			}},
			{field : 'amount',title : '金额',width : 100, formatter: function(value,row,index){
					if(row.amount){
						return row.amount;
					}
					return row.allAmount;
			}},
			{field : 'outDate',title : '日期',width : 100, formatter: function(value,row,index){
					if(row.outDate){
						return billCommonInvoiceRegister.formatterDate(row.outDate);
					}
					return billCommonInvoiceRegister.formatterDate(row.invoiceApplyDate);
			}}		
			]" 
		/>
 	</div>
</div>
			