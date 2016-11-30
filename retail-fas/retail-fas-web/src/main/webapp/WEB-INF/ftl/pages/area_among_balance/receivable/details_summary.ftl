<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="itemDataDtl"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="true" singleSelect="true" showFooter="true" pageSize="20"
			columnsJsonList="[
				{field : 'id',hidden:true},
			    {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
				{field : 'brandName',title : '品牌',width : 100,align:'center'},
				{field : 'oneLevelCategoryName', title : '大类', width : 80,align:'center'},	
			    {field : 'twoLevelCategoryName', title : '二级大类', width : 80,align:'center'},	
				{field : 'itemCode',title : '商品编码',width : 150,align:'left',halign:'center'},	
				{field : 'itemName',title : '商品名称',width : 180,align:'left',halign:'center'},	
				{field : 'cost',title : '单价',width : 80,align:'right',halign:'center'},
				{field : 'sendQty',title : '发出数量',width : 80,align:'right',halign:'center'},				
				{field : 'sendAmount',title : '出库金额',width : 100,align:'right',halign:'center'}
				]" 
			/>
			</div>
</div>
