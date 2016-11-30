<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="brandCategoryDeductionTab" data-options="region:'center',border:false" >
		<@p.datagrid id="brandCategoryDeductionDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" showFooter="true" 
			columnsJsonList="[
				{field : 'groupName',title : '汇总',width : 100},
				{field : 'brandName',title : '品牌',width : 100, formatter: function(value,row,index){
						if(row.brandName == '合计'){
							return '合计';
						}
						return value;
				}},		
				{field : 'oneLevelCategoryName',title : '大类',width : 100},	
				{field : 'sendQty',title : '数量',width : 100, align : 'right'},	
				{field : 'sendAmount',title : '金额',width : 100, align : 'right'},	
				{field : 'otherDeductCost',title : '分摊金额 (票前返利+扣项)',width : 180, align : 'right'},
				{field : 'invoiceAmount',title : '开票金额',width : 120, align : 'right'},
				{field : 'deductionAmount',title : '扣项',width : 120, align : 'right'},	
				{field : 'remark',title : '备注',width : 200}]" 
			/>
	</div>
</div>