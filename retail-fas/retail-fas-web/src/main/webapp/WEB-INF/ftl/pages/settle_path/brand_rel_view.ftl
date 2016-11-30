<div class="easyui-layout" data-options="fit:true">
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="brandDataGridDivView"  loadUrl="/settle_path_brand_rel/get_biz.json?pathNo=${pathNo}" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false" pagination="false" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
			                  {field : 'brandUnitNo', title : '品牌部编码', width : 120, align : 'left'},
			                  {field : 'brandUnitName', title : '品牌部名称', width : 160, align : 'left'}
			                 ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	 
			              }}'
                 />
			</div>
	 	</div>
	</div>
</div>
