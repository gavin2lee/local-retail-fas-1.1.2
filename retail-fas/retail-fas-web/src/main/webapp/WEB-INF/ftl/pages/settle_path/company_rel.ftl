<div class="easyui-layout">
	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
		 		 {"title":"查询","iconCls":"icon-search","action":"company_rel.search()","type":0},
				 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
	             {"id":"btn-edit-detail","title":"修改行","iconCls":"icon-edit","action" : "fas_common_editor.editRow()", "type":0},
	             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","action" : "fas_common_editor.deleteRow()", "type":0},
	             {"id":"btn-copy","title":"主体复制","iconCls":"icon-copy","action" : "company_rel.companyCopy()","type":0},
	             {"id":"btn-save-detail","title":"保存","iconCls":"icon-save","type":7},
	             {"id":"btn-close","title":"关闭","iconCls":"icon-close","type":0}
	           ]
		  />
	</div>
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
					<form name="searchForm" id="searchForm" method="post">
						<input type="hidden" name="pathNo" id="pathNo" value="${pathNo}"/>
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="90" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>公司名称： </th><td><input class="ipt"  name="companyName" id="companyName_" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div data-options="region:'center',border:false">
		    	<@p.datagrid id="dataGridDiv"
			    	loadUrl="" saveUrl=""   defaultColumn="" 
			    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false" selectOnCheck="false"
			    	    height="300" width="" onClickRowEdit="false" singleSelect="true"
					    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
	           			columnsJsonList="[
				              {field : 'ck',checkbox:true,notexport:true},
				              {field : 'id',title:'ID',hidden:true},
			                  {field : 'pathOrder',title:'路径次序', width:70, editor:{type:'numberbox',options:{required:true,max:10}}},
			                  {field : 'companyNo',title:'公司编码',width:170, editor:{type:'searchbox',
			                  															  options:{
			                  															  	id:'companyNo',
			                  															  	name:'companyNo',
			                  															  	textId:'companyName',
			                  															  	valueField:'companyNo',
			                  															  	textField:'name',
			                  															  	title:'选择结算公司',
			                  															  	url:'/settle_path/search_company_view',
			                  															  	required:true
			                  															  }
			                  														}
			                  },
			                  {field : 'companyName',title:'公司名称',width:170, editor:{type:'searchboxname',options:{
			                  																			id:'companyName',
			                  																			name:'companyName', 
			                  																			readonly:true
			                  																		}
			                  																	}
			                  },
			                  {field : 'financialBasis',title :'结算依据', width:120, formatter:function(value, row){
																							return row.financialBasisText;
																					  },editor:{type:'fascombobox',
			                  																	options:{
			                  																		id:'financialBasis',
			                  																		name:'financialBasis',
																									valueField:'zoneCode',
																									textField:'name',
																									url:'/zone_info/get_biz',
																									width:100,
																									required:false
																								}
																							}
							}
		                 ]"   
			             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
							fas_common_editor.editRow(rowIndex, rowData);
			             }}' 
			        	/>
			 </div>
		</div> 
	</div>
</div>
