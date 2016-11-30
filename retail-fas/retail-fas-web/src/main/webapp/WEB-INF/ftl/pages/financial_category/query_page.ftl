<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar-query" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.querySearch()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.queryClear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action":"dialog.queryExport()", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="querySearchForm" id="querySearchForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>分类编码： </th><td><input class="ipt"  name="financialCategoryNoCondition" id="financialCategoryNoCondition" /></td>
									<th>分类名称： </th><td><input class="ipt"  name="name" id="queryPageName" /></td>
									<th>状态： </th>
									<td>
										<input class="ipt easyui-statusbox"  name="status" id="status" />
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="queryDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="true"  
			           columnsJsonList="[
			                  {field : 'financialCategoryNo', title : '分类编码', width : 100, align : 'left',sortField:'financial_category_no',sortable:true},
			                  {field : 'name', title : '分类名称', width : 120, align : 'left',sortField:'name',sortable:true},
			                  {field : 'companyName', title : '公司名称', width : 180, align : 'left', sortField:'company_name',sortable:true},
			                  {field : 'categoryNo', title : '大类编码', width : 100, align : 'left',sortField:'category_no',sortable:true},
			                  {field : 'categoryName', title : '大类名称', width : 120, align : 'left',sortField:'category_name',sortable:true},
			                  {field : 'qtyControlFlagName',title:'是否启用数量控制',width:110},
			                  {field:'statusName',title : '状态',width : 90, align : 'center',sortField:'status',sortable:true},
			                  {field : 'updateUser', title : '修改人', width : 90, align : 'left',sortField:'update_user',sortable:true},
			                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center',sortField:'update_time',sortable:true},
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