<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="enterTab" data-options="region:'center',border:false">
		<@p.datagrid id="buyEnterDataGrid"  showFooter="true" rownumbers="true" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
				{field : 'originalBillNo',title : '单据编号',width : 150, formatter: function(value,row,index){
					if(row.buyerName == '合计'){
						return '合计';
					}
					return value;
			    }},	
				{field : 'sendDate',title : '单据日期',width : 120},			
				{field : 'billTypeName',title : '单据类型',width : 120},		
				{field : 'itemCode',title : '商品编码',width : 150},
				{field : 'itemName',title : '商品名称',width : 150,align:'left'},
				{field : 'cost',title : '采购价',width : 100, formatter: function(value,row,index){
					if(row.buyerName == '合计'){
						return '';
					}
					return value;
				},align:'right'},		
				{field : 'sendQty',title : '入库数量',width : 100},				
				{field : 'sendAmount',title : '金额',width : 100,align:'right'},
				{field : 'supplierGroupName', title : '供应商类型', width : 80},
		    	{field : 'supplierName', title : '供应商', width : 200,align:'left'},
				{field : 'brandName',title : '品牌',width : 80},
				{field : 'oneLevelCategoryName', title : '大类', width : 80},	
                {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
                {field : 'categoryName', title : '三级大类', width : 80},
                {field : 'genderName', title : '性别', width : 80},
                {field : 'organName', title : '管理城市',  width : 80},
			    {field : 'orderUnitName', title : '货管单位',  width : 80}]" 
				
			/>
	</div>
</div>