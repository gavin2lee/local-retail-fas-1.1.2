<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbarcopy" listData=[
			 {"id":"btn-copy-search","title":"查询","iconCls":"icon-search","action":"editor.searchCompanyCopy()", "type":0},
             {"id":"btn-copy-remove","title":"清空","iconCls":"icon-empty", "action":"editor.clearCompanyCopy()","type":0},
             {"id":"btn-copy-save","title":"确定","iconCls":"icon-save", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="companyCopySearchForm" id="companyCopySearchForm" method="post">
			      		<input type="hidden" name="existCompanyNos" value="${existCompanyNos}"/>
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>公司编码： </th><td><input class="ipt"  name="dataNo" id="dataNo" /></td>
									<th>公司名称： </th><td><input class="ipt"  name="dataName" id="dataName" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv1"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true},
			                  {field : 'companyNo', title : '公司编码', width : 100, align : 'left'},
			                  {field : 'name', title : '公司名称', width : 260, align : 'left'}
			                 ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	 
			              }}'
                 />
			</div>
	 	</div>
	</div>
</div>