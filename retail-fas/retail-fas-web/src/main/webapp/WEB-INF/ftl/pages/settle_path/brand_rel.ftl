<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="brandGroupToolbar" listData=[
             {"id":"btn-brand-group","title":"品牌组","iconCls":"icon-save", "action":"editor.brandGroupRel()", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
		      	<form name="brandSearchForm" id="brandSearchForm" method="post">
		      		<input type="hidden" name="pathNo" id="pathNo" value="${pathNo}"/>
		      		<table class="form-tb">
                	    <col width="80" />
                	 	<col />
                	 	<col width="80" />
                	 	<col />
                        <tbody>
                            <tr>
                            	<th>品牌部编码：</th><td><input class="ipt" name="brandUnitNo" id="brandUnitNoCondition"/></td>
                            	<td>
                            		<input type="button" name="queryDtlBtn" id="queryDtlBtn" value="查&nbsp;&nbsp;询" onclick="editor.searchBrandUnit()"/>
                            	</td>
                            </tr>
                        </tbody>
                    </table>
				</form>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="brandDataGridDiv"  loadUrl="/brand_unit/get_biz.json" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false" pagination="false" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  onLoadSuccess="editor.brandDataOnloadSuccess"
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true},
			                  {field : 'brandUnitNo', title : '品牌部编码', width : 120, align : 'left'},
			                  {field : 'name', title : '品牌部名称', width : 160, align : 'left'}
			                 ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	 
			              }}'
                 />
			</div>
	 	</div>
	</div>
</div>
