<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="otherDeductionDtl"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="true" singleSelect="true"  showFooter="true"
			columnsJsonList="[
				{field : 'brandName',title : '品牌',width : 100,
					formatter: function(value,row,index){
						if(row.buyerName == '合计'){
							return '合计';
						}
						return value;
					}
				},	
				{field : 'deductionDate',title : '日期',width : 100},
				{field : 'deductionAmount',title : '扣项金额',width : 100,align:'right',halign:'center'},								
				{field : 'deductionQty',title : '扣项数量',width : 80,align:'right',halign:'center'},								
				{field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
			]" 
		 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
		 }}"/>
	</div>	
</div>	