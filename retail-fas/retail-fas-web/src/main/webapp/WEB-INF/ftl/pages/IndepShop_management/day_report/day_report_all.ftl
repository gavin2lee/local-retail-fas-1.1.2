<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
				<@p.datagrid id="dataGridDiv" loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="10" showFooter="true"
			           columnsJsonList="" 
			           frozenColumns="[
							{field : 't',checkbox:true,width : 30,notexport:true,rowspan:'2'},
			                {field : 'id',hidden : 'true',align:'center',notexport:true,rowspan:'2'},
			                {field : 'shop_no',title : '店铺编码',width : 60,align:'left',rowspan:'2'},
							{field : 'shop_name',title : '店铺名称',width : 150,align:'left',rowspan:'2'},
							{field : 'company_no',title : '公司编码',width : 60,align:'left',rowspan:'2'},
							{field : 'company_name',title : '公司名称',width : 150,align:'left',rowspan:'2'},
							{field : 'out_date',title : '销售日期',width : 100,align:'center',rowspan:'2'}
						]"
			          jsonExtend='{
		             	onLoadSuccess:function(rowData){
		             		
						}
	             	}' 
                 />
        	</div>
</div>