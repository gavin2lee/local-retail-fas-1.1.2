<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="brandGroupRelToolbar" listData=[
			 {"id":"btn-brand-group-search","title":"查询","iconCls":"icon-search","action":"editor.searchBrandGroup()", "type":0},
             {"id":"btn-brand-group-remove","title":"清空","iconCls":"icon-empty", "action":"editor.clearBrandGroup()","type":0},
             {"id":"btn-brand-group-save","title":"确定","iconCls":"icon-save", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="brandGroupSearchForm" id="brandGroupSearchForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>品牌组编码： </th><td><input class="ipt"  name="groupNo" id="groupNo" /></td>
									<th>品牌组名称： </th><td><input class="ipt"  name="name" id="name" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="brandGroupDataGridDiv"  loadUrl="/settle_brand_group/list.json?status=1" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true},
			                  {field : 'groupNo', title : '品牌组编码', width : 120, align : 'left'},
			                  {field : 'name', title : '品牌组名称', width : 180, align : 'left'}
			                 ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	 
			              }}'
                 />
			</div>
	 	</div>
	</div>
</div>