<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="transferInDtl"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="true" singleSelect="true"  pageSize="20" showFooter="true"
			columnsJsonList="[
				{field : 'id',hidden:true},
				{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center',
					formatter: function(value,row,index){
						if(row.supplierName == '合计'){
							return '合计';
						}
						return value;
					}		
				},
			    {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
				{field : 'brandName',title : '品牌',width : 100,align:'center'},
				{field : 'oneLevelCategoryName', title : '大类', width : 60,align:'center'},	
			    {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
				{field : 'itemCode',title : '商品编码',width : 135,align:'left',halign:'center'},	
				{field : 'itemName',title : '商品名称',width : 180,align:'left',halign:'center'},	
				{field : 'cost',title : '单价',width : 80,align:'right',halign:'center'},
				{field : 'receiveQty',title : '数量',width : 80,align:'right',halign:'center'},				
				{field : 'receiveAmount',title : '金额',width : 100,align:'right',halign:'center'},
				{field : 'receiveDate',title : '单据日期',width : 100,align:'center'},
				{field : 'orderUnitName', title : '货管单位', width : 80,align:'center',halign:'center'},
			    {field : 'receiveStoreName', title : '收货仓', width : 120,align:'left',halign:'center'},
			    {field : 'organName', title : '管理城市', width : 80,align:'center',halign:'center'}	,
				{field : 'billTypeName',title : '单据类型',width : 80,align:'center'}
			]" 
		/>
		</div>
</div>
