<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
	  <@p.datagrid id="clientDtlViewDataGrid"
			loadUrl="" saveUrl=""   defaultColumn="" 
    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="false"
    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
		    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
   			columnsJsonList="[
                {field : 'dtlType',title : '明细类型',hidden : 'true',align:'center',notexport:true},
			 	{field:'shopNo',title:'客户编码',  align:'left',width : 125,halign:'center'},
                {field : 'shopName', title : '客户名称', align:'left',width : 150,halign:'center'}]"
    	/>
    </div>
</div>