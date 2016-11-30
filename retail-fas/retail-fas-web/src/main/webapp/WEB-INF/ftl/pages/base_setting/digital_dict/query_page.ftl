<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar-query" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.querySearch()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.queryClear()", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="querySearchForm" id="querySearchForm" method="post">
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
									<th>参数编码： </th>
									<td>
										<input class="ipt"  name="paramCode" id="queryPageCategoryNo" />
									</td>
                                	<th>参数名称： </th>
                                	<td>
                                		<input class="ipt"  name="paramName" id="queryPageName" />
                                	</td>
                                	<th></th>
                                	<td></td>
                                	<th></th>
                                	<td></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="queryDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="true"  
			           columnsJsonList="[
			                  {field : 'paramCode', title : '参数编码', width : 100, align : 'left',halign:'center'},
			                  {field : 'paramName', title : '参数名称', width : 200, align : 'left',halign:'center'},
			                  {field : 'controlLevel', title : '控制级别', width : 80,formatter:controlFormatter},
			                  {field : 'dtlValue', title : '参数值编码', width : 100, align : 'left',halign:'center'},
			                  {field : 'dtlName', title : '参数值名称', width : 100, align : 'left',halign:'center'},
			                  {field : 'isvalid',title : '是否有效',width : 80, align : 'center',formatter:isvalidFormatter},
			                  {field : 'createUser', title : '创建人', width : 100, align : 'center'},
							  {field : 'createTime', title : '创建时间', width : 140, align : 'center'},
			                  {field : 'updateUser', title : '修改人', width : 100, align : 'center'},
			                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'},
			              ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	 
			                   }
		                 }'
                 />
			</div>
	 	</div>
	</div>
</div>