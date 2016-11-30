<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" class="toolbar-region">
			  <table width="100%">
				<tr class="datagrid-toolbar">
					<td>
					 <@p.toolbar id="multiSupplierSearchTool" listData=[ 
		   				 {"id":"dgSelectorSearchBtn","title":"查询","iconCls":"icon-search","type":0},
		   				 {"id":"dgSelectorClearBtn","title":"清空","iconCls":"icon-remove","type":0},
		   				 {"id":"dgSelectorSureBtn","title":"确定","iconCls":"icon-ok", "type":0}
					  ]/>
					</td>
				</tr>
			</table>	
		</div>
		<div  data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true" id="subLayout">
				<div data-options="region:'north',border:false" >
					<div class="search-div">
							 <form name="dialog_SarchForm" id="dialog_SarchForm" method="post">
							 	<table class="form-tb">
								     <col width="80" />
					                 <col />
					                 <col width="80" />
					                 <tbody>
								    		<tr>
						       		 			<th>商场编码：</th>
						       		 			<td align="left"><input class="easyui-validatebox ipt" name="mallNo" id="mallNo"/></td>
						       		 			<th>商场名称：</th>
						       		 			<td align="left"><input class="easyui-validatebox ipt"  name="name" id="name"/></td>
						       		 		</tr>
								    </tbody>
							   </table>
							</form>
					</div>
			</div>
			<div data-options="region:'center',border:false">
				<@p.datagrid id="dialog_SearchDataGrid"  loadUrl="" saveUrl=""  defaultColumn="" 
				                   isHasToolBar="false" height="415" divToolbar="" onClickRowEdit="false" pagination="true"
				                   rownumbers="true" singleSelect = "true"
					               columnsJsonList="[
					               		{field : 't',checkbox:true,width : 30},
				                  		{field : 'mallNo',title : '商场编码',width : 150,align:'left'},
				                  		{field : 'name',title : '商场名称',width : 200,align:'left'}
					                 ]" 
								/>
			</div>
		</div>
	</div>
</div>