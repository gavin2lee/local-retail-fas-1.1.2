<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="receivableInfoDtl"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="true" singleSelect="true"  showFooter="true"
			columnsJsonList="[
				{field : 'billNo',title : '收款单号',width : 150,align:'left',halign:'center'},
				{field : 'balanceAmount',title : '应收金额',width : 100,align:'right',halign:'center'},
				{field : 'receivableAmount',title : '收款金额',width : 100,align:'right',halign:'center'},
				{field : 'overageAmount',title : '余额',width : 100,align:'right',halign:'center'},
				{field : 'billDate',title : '日期',width : 100,align:'center',halign:'center'},	
				{field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
			]" 
		 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
		 }}"/>
	</div>	
</div>	