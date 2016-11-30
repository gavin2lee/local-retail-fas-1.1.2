<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="transferOutDtl"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="true" singleSelect="true" showFooter="true" pageSize="20"
			columnsJsonList="[
				{field : 'originalBillNo',title : '单据编号',width : 150,align:'left',halign:'center'},
			    {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
				{field : 'brandName',title : '品牌',width : 100,align:'center'},
				{field : 'oneLevelCategoryName', title : '大类', width : 60,align:'center'},	
			    {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
				{field : 'itemCode',title : '商品编码',width : 135,align:'left',halign:'center'},	
				{field : 'itemName',title : '商品名称',width : 180,align:'left',halign:'center'},	
				{field : 'cost',title : '单价',width : 80,align:'right',halign:'center'},
				{field : 'sendQty',title : '数量',width : 60,align:'right',halign:'center'},				
				{field : 'sendAmount',title : '出库金额',width : 100,align:'right',halign:'center'},
				{field : 'sendDate',title : '发出日期',width : 100,align:'center'},	
                {field : 'organName', title : '调入管理城市', width : 100,align:'center',halign:'center'},
                {field : 'orderUnitName', title : '调入货管单位', width : 100,align:'center',halign:'center'},
                {field : 'receiveStoreName', title : '收货仓', width : 100,align:'left',halign:'center'},
                {field : 'organNameFrom', title : '调出管理城市', width : 100,align:'center',halign:'center'},
				{field : 'orderUnitNameFrom', title : '调出货管单位', width : 100,align:'center',halign:'center'},
                {field : 'sendStoreName', title : '发货仓', width : 100,align:'left',halign:'center'},
				{field : 'billTypeName',title : '单据类型',width : 80,align:'center'}
			]" 
		/>
		</div>
</div>
