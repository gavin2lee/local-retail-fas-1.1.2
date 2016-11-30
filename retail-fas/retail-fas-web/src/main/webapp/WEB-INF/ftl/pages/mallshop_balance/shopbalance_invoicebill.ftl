<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="invoiceDataGrid"  
		loadUrl="" saveUrl=""   defaultColumn="" 
		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		 checkOnSelect="true"  rownumbers="true" singleSelect="true"   showFooter="true"
		columnsJsonList="[
			{field : 'billNo',hidden : 'true',title : '开票申请单号',align:'center',width : 150},
			{field : 'invoiceNo',title : '发票号',align:'left',width : 130},
			{field : 'invoiceAmount',title : '发票金额',align:'left',width : 90},
			{field : 'invoiceDate',title : '日期',align:'left',width : 100},
			{field : 'express',title : '快递公司',align:'left',width : 100},
			{field : 'deliveryNo',title : '快递单号',align:'left',width : 100},	
			{field : 'deliveryDate',title : '快递日期',align:'left',width : 100},	
			{field : 'auditDate',title : '接收日期',align:'left',width : 100},	
			{field : 'remark',title : '备注',align:'left',width : 100}
		]" 
		/>
 </div>
</div>