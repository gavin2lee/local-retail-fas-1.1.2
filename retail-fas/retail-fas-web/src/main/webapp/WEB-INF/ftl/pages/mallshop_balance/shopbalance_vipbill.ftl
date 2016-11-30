<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="vipDataGrid"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="false" singleSelect="true"  
			columnsJsonList="[
				 {field : 't',checkbox:true,hidden : 'true',width : 30},				           
				                {field : 'id',hidden : 'true',align:'center'},				               
							   {field : 'orderNo',title : '商场VIP销售金额',width : 100,align:'center'},	
							 {field : 'orderNo',title : '商场VIP折扣金额',width : 100,align:'center'},	
							 {field : 'orderNo',title : '分摊规则',width : 100,align:'center'},	
							 {field : 'orderNo',title : '计算基数',width : 100,align:'center'},	
							 {field : 'orderNo',title : '我方承担比例',width : 100,align:'center'},	
							 {field : 'orderNo',title : '我方承担金额',width : 100,align:'center'},	
							 {field : 'orderNo',title : '商场返利金额',width : 100,align:'center'},	
							 {field : 'orderNo',title : '实际返利金额',width : 100,align:'center'},	
				]" 
			/>
			</div>
</div>
	