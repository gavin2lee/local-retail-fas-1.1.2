<div class="easyui-layout" data-options="fit:true">
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
	
	<div  data-options="region:'center',border:false">
	    <div class="easyui-layout" data-options="fit:true" id="subLayout">
	        <div data-options="region:'north',border:false" >
	            <div class="search-div">
			        <form name="dialog_SarchForm" id="dialog_SarchForm" method="post">
			        	<input type="hidden" name="levelid" id="levelid" value=${levelid}>
			        	<input type="hidden" name="existCategoryNos" id="existCategoryNos" value=${existCategoryNos}>
						<table class="form-tb">
						   <col width="80" />
				           <col />
				           <col width="120" />
				           <tbody>
							<tr>
								<th>大类名称：</th>
                                <td><input class="ipt" name="name" id="name" /></td>
                            </tr>
							<tbody>
						</table>	
					</form>
	            </div>
	        </div>
		  
		  	<div data-options="region:'center',border:false">
		  		<@p.datagrid id="dialog_SearchDataGrid"  loadUrl="/category/filter_exist_category.json?levelid=${levelid}&existCategoryNos=${existCategoryNos}" saveUrl=""  defaultColumn=""  
			       isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"    pagination="true"
			       rownumbers="true"
			       columnsJsonList="[
			                  {field : 'categoryNo',title : '大类编码',width : 150},
			                  {field : 'name',title : '大类名称',width : 100}
                   ]"   
      			 />
	 		</div>
	    </div>
	</div>
</div>
