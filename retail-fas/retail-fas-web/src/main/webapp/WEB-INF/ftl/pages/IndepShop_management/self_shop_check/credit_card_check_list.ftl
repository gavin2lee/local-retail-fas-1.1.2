<div class="easyui-layout" data-options="fit:true">
	<!-- 银联刷卡交易明细数据展示-->
	<div data-options="region:'center',border:false">
      <@p.subdatagrid id="creditCardCheckDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""
              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
	           rownumbers="false" singleSelect="false"  
	           columnsJsonList="[
              	    {field : 't',checkbox:true,width : 30,notexport:true},
	                {field : 'id',hidden : 'true',align:'center',notexport:true},
					{field : 'outDate',title : '销售日期',width : 100,align:'center'},	
					{field : 'terminalNumber',title : '终端号',width : 100,align:'center'},
					{field : 'incomeAmount',title : '银行卡-系统销售',width : 100,align:'center',exportType:'number'},	
					{field : 'onlineIncomeAmount',title : '网银交易金额',width : 120,align:'center',exportType:'number'},	
					{field : 'creditCardDiff',title : '刷卡差异',width : 100,align:'center',styler:changeColor,exportType:'number'}
					
              	]"
              	jsonExtend='{
               		onDblClickRow:function(rowIndex, rowData){
                	  //双击方法
                   	}
             	}'
             	loadSubGridUrl="/credit_card_check/list_child"
         	 	subPagination="false"
         	 	subGridColumnsJsonList="[
             	  	{field : 'shopNo', title : '店铺编码', width : 100, align : 'center',notexport:true, seq : 3},
	                {field : 'shopName', title : '店铺名称', width : 140, align : 'center',notexport:true, seq : 8},
			      	{field : 'brandUnitName', title : '品牌部', width : 100, align : 'center',notexport:true, seq : 4},
			      	{field : 'mallNo', title : '商场编码', width : 100, align : 'center',notexport:true, seq : 3},
	                {field : 'mallName', title : '商场名称', width : 140, align : 'center',notexport:true, seq : 8},
	                {field : 'incomeAmount',title : '银行卡-系统销售',width : 100,align:'center',exportType:'number'}
         		]" 
         />
	</div>
</div>