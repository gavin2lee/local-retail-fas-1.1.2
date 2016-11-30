<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar-query" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"supplierGroupType.searchDtl()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"supplierGroupType.clearDtl()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"supplierGroupType.exportDtl()","type":0}
           ]
		/>
	</div>
	
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="dtlSearchForm" id="dtlSearchForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th>供应商组编码：</th>
							 		<td>
							 			<input class="easyui-validatebox ipt" style="width:130px" name="groupNoAlial" id="groupNoAlialCondition" />
							 		</td>
							 		<th>供应商组名称：</th>
							 		<td>
							 			<input class="easyui-validatebox ipt" style="width:130px" name="groupName" id="groupNameCondition" />
							 		</td>
							 		<th>供应商编码：</th>
							 		<td>
							 			<input class="easyui-validatebox ipt" style="width:130px" name="supplierNoAlial" id="supplierNoAlialCondition" />
							 		</td>
							 		<th>供应商名称：</th>
							 		<td>
							 			<input class="easyui-validatebox ipt" style="width:130px" name="supplierName" id="supplierNameCondition" />
							 		</td>
								</tr>
								<tr>
									<th>审核状态：</th>
							 		<td><input class="easyui-combobox ipt" style="width:130px" name="auditStatus" id="statusCondition" /></td>
								</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlSearchDataGrid" loadUrl="" saveUrl="" defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
				                  {field : 'groupNo',title : '供应商组编码',width: 100,align:'left',halign:'center'},
				                  {field : 'groupName',title : '供应商组名称',width: 100,align:'center'},
				                  {field : 'supplierNo',title : '供应商编码',width: 100,align:'left',halign:'center'},
				                  {field : 'supplierName',title : '供应商名称',width: 180,align:'left',halign:'center'},
				                  {field : 'enableTime',title : '启用日期',width: 100,align:'center'},
				                  {field : 'disableTime',title : '终止日期',width: 100,align:'center'},
				                  {field : 'auditStatusName',title : '审核状态',width: 80,align:'center'},
				                  {field : 'createUser',title : '创建人',width: 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width: 140,align:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</div>
