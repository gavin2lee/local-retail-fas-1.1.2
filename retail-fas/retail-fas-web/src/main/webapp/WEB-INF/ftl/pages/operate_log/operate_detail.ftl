<div class="easyui-layout" data-options="fit:true">
<#-- 
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <table width="100%">
				<tr class="datagrid-toolbar">
					<td>
					 <@p.toolbar id="supplierSearchTool" listData=[ 
		   				 {"id":"dgSelectorSearchBtn","title":"查询","iconCls":"icon-search","type":0},
		   				 {"id":"dgSelectorClearBtn","title":"清空","iconCls":"icon-empty","type":0}                      
					  ]/>
					<td>
				</tr>
			</table>			
	</div>
-->
	
	<div  data-options="region:'center',border:false">
	    <div class="easyui-layout" data-options="fit:true" id="subLayout">
	        <div data-options="region:'north',border:false" >
		        <form name="dialog_SarchForm" id="dialog_SarchForm" method="post">
		        	<input type="hidden" name="dataNo" id="dataNo" value=${dataNo}>
		        	<input type="hidden" name="moduleNo" id="moduleNo" value=${moduleNo}>
				</form>
	        </div>
		  
		  	<div data-options="region:'center',border:false">
		  		<@p.datagrid id="dialog_SearchDataGrid"  loadUrl="/operate_log/list.json?dataNo=${dataNo}&moduleNo=${moduleNo}" saveUrl=""  defaultColumn=""  
			       isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"    pagination="true"
			       rownumbers="true"
			       columnsJsonList="[
			                  {field : 'dataNo',title : '单据编码',width : 150},
			                  {field : 'statusName',title : '单据状态',width : 120},
			                  {field : 'createUser',title : '操作人',width : 100},
			                  {field : 'createTime',title : '操作时间',width : 140}
                   ]"   
      			 />
	 		</div>
	 		
	    </div>
	</div>
</div>
