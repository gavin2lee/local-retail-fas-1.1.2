<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" class="toolbar-region">
			  <table width="100%">
				<tr class="datagrid-toolbar">
					<td>
					 <@p.toolbar id="multiSupplierSearchTool" listData=[ 
		   				 {"id":"dgSelectorSearchBtn","title":"查询","iconCls":"icon-search","type":0},
		   				 {"id":"dgSelectorClearBtn","title":"清空","iconCls":"icon-remove","type":0}
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
					                 <col />
					                 <col width="80" />
					                 <col />
					                 <tbody>
								    		<tr>
						       		 			<input type="hidden"  name="companyNo" id="companyNo" value="${RequestParameters['companyNo']!''}"/>
						       		 			<th>管理城市：</th>
						       		 			<td align="left"><input class="easyui-organbox ipt" name="organNo" id="organNo" data-options="required:false"/></td>
						       		 			<th>经营城市：</th>
						       		 			<td align="left"><input class="easyui-organbox ipt" name="bizCityNo" id="bizCityNo" data-options="url:'',required:false,levelType:2"/></td>
						       		 			<th>店铺编码：</th>
						       		 			<td align="left"><input class="easyui-validatebox ipt" name="shopNo" id="shopNo"/></td>
						       		 		</tr>
						       		 		<tr>
						       		 			<th>店铺简称：</th>
						       		 			<td align="left"><input class="easyui-validatebox ipt"  name="shortName" id="shortName"/></td>
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
				                  		{field : 'shopNo',title : '店铺编码',width : 150,align:'left'},
				                  		{field : 'shortName',title : '店铺简称',width : 150,align:'left'},
				                  		{field : 'fullName',title : '店铺全称',width : 150,align:'left'}
					                 ]" 
								/>
			</div>
		</div>
	</div>
</div>