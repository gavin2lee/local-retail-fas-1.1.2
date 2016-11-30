<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
		<@p.datagrid id="sumDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
		  onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
           columnsJsonList="[
                {field : 'billNo',title : '单据编号',width : 150},
				{field : 'balanceTypeName',title : '结算类型',width : 120},
				{field : 'brandUnitName',title : '品牌部',width : 80},	
				{field : 'categoryName',title : '商品大类',width : 80},				
				{field : 'balanceStartDate',title : '结算开始日期',width : 100},
				{field : 'balanceEndDate',title : '结算结束日期',width : 100},
				{field : 'outQty',title : '进货数量',width:80,align:'right',halign:'center',
				  formatter: function(value,row,index){
						if(row.outQty){
							return row.outQty;
						}
						return row.entryQty;
				}},
				{field : 'outAmount',title : '进货金额',width:100,align:'right',halign:'center',
				formatter: function(value,row,index){
					if(row.outAmount && row.outAmount!=0){
						return row.outAmount;
					}
					return row.entryAmount;
				}},
				{field : 'deductionAmount',title : '其他扣项',width : 100,align:'right',halign:'center'},
				{field : 'balanceQty',title : '应付数量',width : 80,align:'right',halign:'center'},
				{field : 'balanceAmount',title : '应付金额',width : 100,align:'right',halign:'center'}
          ]" 
         />
	</div>	
</div>	