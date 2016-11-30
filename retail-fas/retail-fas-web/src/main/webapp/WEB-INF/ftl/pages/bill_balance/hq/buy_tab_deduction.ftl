<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="deductionTab" data-options="region:'center',border:false">
		<@p.datagrid id="buyDeductionDataGrid"  rownumbers="true" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
				{field : 'returnDate',title : '退货日期',width : 120},			
				{field : 'itemCode',title : '商品编码',width : 150},
				{field : 'itemName',title : '商品名称',width : 150,align:'left'},
				{field : 'purchasePrice',title : '采购价',width : 100,align:'right'},		
				{field : 'qty',title : '数量',width : 100},				
				{field : 'amount',title : '金额',width : 100,align:'right'},
				{field : 'brandName',title : '品牌',width : 80},
				{field : 'oneLevelCategoryName', title : '大类', width : 80},	
                {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
                {field : 'categoryName', title : '三级大类', width : 80},
                {field : 'genderName', title : '性别', width : 80}]" 
			/>
	</div>
</div>