<div class="easyui-layout" data-options="fit:true" id="subLayout">	
 	<div data-options="region:'center',border:false">
		<@p.subdatagrid id="cashCheckDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""
          isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
           rownumbers="false" singleSelect="false"   
           columnsJsonList="[
                    {field : 't',checkbox:true,width : 30,notexport:true},
	                {field : 'id',hidden : 'true',align:'center',notexport:true},
					{field : 'depositAccount',title : '存现账号',width : 120,align:'center'},
					{field : 'depositDate',title : '存现日期',width : 120,align:'center'},
					{field : 'depositAmount',title : '存现金额',width : 80,align:'center',exportType:'number'},	
					{field : 'actualIncomeAmount',title : '到账金额',width : 100,align:'center',exportType:'number'},
					{field : 'depositDiff',title : '到账差异',width : 100,align:'center',exportType:'number',styler:changeColor}
              ]" 
	          jsonExtend='{
                   onDblClickRow:function(rowIndex, rowData){
                	  //双击方法
                   }
             }'
             loadSubGridUrl="/cash_check/lookForChildList"
         	 subPagination="false"
         	 subGridColumnsJsonList="[
     	 			{field : 'shopNo',title : '店铺编码',width : 60,align:'center'},
					{field : 'shopName',title : '店铺名称',width : 100,align:'center'},
					{field : 'mallName',title : '商场名称',width : 100,align:'center'},
					{field : 'incomeAmount',title : '系统现金销售',width : 100,align:'center',exportType:'number'},
					{field : 'depositAccount',title : '存现账号',width : 120,align:'center'},
					{field : 'depositDate',title : '存现日期',width : 120,align:'center'},
					{field : 'actualIncomeAmount',title : '存现单金额',width : 100,align:'center',exportType:'number'},
					{field : 'depositDiff',title : '到账差异',width : 100,align:'center',exportType:'number',styler:changeColor}
         	]" 
	   />
	</div>
</div>
