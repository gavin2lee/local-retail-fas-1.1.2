<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<@p.datagrid id="dataGridDiv_order_brand"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" rownumbers="true" 
			showFooter="true"
			rowStyler="function(index,row){
					     if (row.shop_name.indexOf('小计') != -1){
					         return 'background-color:#FFFFE0;';
					     }
					  }"
			frozenColumns="[
				{field:'shop_no',title:'店铺编码',width:80,sortable:true,align:'center'},
				{field:'shop_name',title:'店铺名称',width:150,sortable:true,align:'left',halign:'center'},
				{field:'brand_no',title:'品牌编号',width:80,sortable:true,hidden:true},
				{field:'brand_name',title:'品牌名称',width:80,sortable:true},
				{field:'out_date',title:'销售日期',width:90,sortable:true,align:'center'},
				{field:'order_type',title:'订单类型',width:80,sortable:true,align:'center'},
				{field:'order_no',title:'订单编码',width:150,sortable:true,align:'left',halign:'center'},
				{field:'amount',title:'销售金额',width:80,align:'right',halign:'center',exportType:'number'}
			]"
        />
	</div>
</div>