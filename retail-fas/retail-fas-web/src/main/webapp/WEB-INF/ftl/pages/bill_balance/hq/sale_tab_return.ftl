<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="returnTab" data-options="region:'center',border:false">
		<@p.datagrid id="saleReturnDataGrid"  showFooter="true" rownumbers="true"
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
					{field : 'itemName',title : '商品名称',width : 200},
					{field : 'cost',title : '地区价',width : 100, formatter: function(value,row,index){
						if(row.buyerName == '合计'){
							return '';
						}
						return value;
					}},		
					{field : 'sendQty',title : '出库数量',width : 100},				
					{field : 'sendAmount',title : '金额',width : 100},
					{field : 'brandUnitName', title : '品牌部', width : 80},
					{field : 'brandName',title : '品牌',width : 80},
					{field : 'organName', title : '管理城市',  width : 80},
				    {field : 'orderUnitName', title : '货管单位',  width : 120,align:'left'},
	                {field : 'oneLevelCategoryName', title : '一级大类', width : 100},	
	                {field : 'twoLevelCategoryName', title : '二级大类', width : 100},
	                {field : 'categoryName', title : '三级大类', width : 100},
	                {field : 'genderName', title : '性别', width : 80}]" 
			/>
	</div>
</div>