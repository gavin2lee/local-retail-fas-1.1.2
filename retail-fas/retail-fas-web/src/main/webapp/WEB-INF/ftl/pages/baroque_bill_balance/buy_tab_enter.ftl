<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="enterTab" data-options="region:'center',border:false">
		<@p.datagrid id="buyEnterDataGrid"  showFooter="true" rownumbers="true" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
				{field : 'originalBillNo',title : '单据编号',width : 150},
				{field : 'sendDate',title : '发货日期',width : 120},
				{field : 'receiveDate',title : '验收日期',width : 120},	
				{field : 'brandName',title : '品牌',width : 80},
				{field : 'oneLevelCategoryName', title : '大类', width : 80},			
				{field : 'itemCode',title : '商品编码',width : 150},
				{field : 'itemName',title : '商品名称',width : 150,align:'left'},
				{field : 'tagPrice',title : '牌价',width : 100,align:'right'},	
				{field : 'receiveQty',title : '数量',width : 100},
				{field : 'cost',title : '采购价',width : 100,align:'right'},
				{field : 'purchaseAmount',title : '采购金额',width : 100,align:'right'},		
				{field : 'currencyName',title : '币别',width : 100,align:'right'},	
				{field : 'standardCurrencyName',title : '本位币',width : 100,align:'right'},	
				{field : 'exchangeRate',title : '汇率',width : 100,align:'right'},
				{field : 'standardPurchaseAmount', title : '本位币金额', width : 100,align:'center'},					
				{field : 'totalAmount', title : '总部价金额', width : 100,align:'center'},
		    	{field : 'supplierName', title : '供应商', width : 200,align:'left'},
                {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
                {field : 'categoryName', title : '三级大类', width : 80},
                {field : 'organName', title : '管理城市',  width : 80},
			    {field : 'orderUnitName', title : '货管单位',  width : 80},
			    {field : 'remark', title : '备注',  width : 120}]" 
			/>
	</div>
</div>