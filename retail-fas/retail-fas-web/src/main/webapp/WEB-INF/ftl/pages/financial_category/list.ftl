<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>财务大类设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/financial_category/FinancialCategory.js?version=${version}"></script>
</head>
<body>
<@p.commonSetting search_url="/list.json" 
					  save_url="/save_all" 
					  update_url="/save_all" 
					  del_url="/do_delete"
					  export_url="/do_fas_export"
					  export_title="财务大类信息导出"
					  export_type="common"
					  enable_url="/do_enable"
					  unable_url="/do_unable"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="750" 
					  dialog_height="600"
					  primary_key="id"/>
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'财务大类设置'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	 <@p.toolbar id="toolbar" listData=[
							 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
				             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
				             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"dialog.toAdd()", "type":1},
				             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"dialog.toUpdate()","type":2},
				             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"dialog.doDel()","type":3},
				             {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action":"dialog.doEnable()","type":27},
				             {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action":"dialog.doUnable()","type":100},
				             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}
				           ]
						/>
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="searchForm" name="searchForm"  method="post">
		                        	 <table class="form-tb">
		                        	    <col width="80" />
		                        	 	<col />
		                        	 	<col width="80" />
		                        	 	<col />
		                        	 	<col width="80" />
		                        	 	<col />
		                        	 	<col width="80" />
		                        	 	<col />
		                                <tbody>
		                                    <tr>
		                                    	<th>分类编码： </th><td><input class="ipt"  name="financialCategoryNoCondition" id="financialCategoryNoCondition" /></td>
		                                    	<th>分类名称： </th><td><input class="ipt"  name="name" id="name_" /></td>
												<th>状态： </th>
												<td>
													<input class="ipt easyui-statusbox"  name="status" id="status" />
												</td>
												<th>公司： </th>
												<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiCompanyNo"></td>
		                                    </tr>
		                                </tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                <div data-options="region:'center',border:false" style="height:350px;">
		                	 <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
						              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
							           rownumbers="true" singleSelect="true"  
							           columnsJsonList="[
							                  {field : 't', checkbox:true, width : 30, notexport:true},
							                  {field : 'financialCategoryNo', title : '分类编码', width : 100, align : 'left', sortField:'financial_category_no',sortable:true, seq : 1},
							                  {field : 'name', title : '分类名称', width : 120, align : 'left', sortField:'name',sortable:true, seq : 2},
							                  {field : 'companyName', title : '公司名称', width : 180, align : 'left', sortField:'company_name',sortable:true, seq : 3},
							                  {field : 'remark', title : '备注', width : 150, align : 'left', sortField:'remark',sortable:true, seq : 5},
							                  {field : 'updateUser', title : '修改人', width : 100, align : 'left', sortField:'update_user',sortable:true, seq : 6},
							                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center', sortField:'update_time',sortable:true, seq : 7},
							                  {field:'statusName',title : '状态',width : 100, align : 'center', sortField:'status',sortable:true, seq : 4}
							              ]" 
								          jsonExtend='{
					                           onDblClickRow:function(rowIndex, rowData){
							                	  //双击方法
							                   	   dialog.toUpdate(rowData);
							                   }
						                 }'
						                 loadSubGridUrl="/financial_category_dtl/query_financial_category_dtl"
						             	 subPagination="false"
						             	 subGridColumnsJsonList="[
						             	  	{field : 'categoryNo', title : '大类编码', width : 120, align : 'left', seq : 3,notexport:true},
									      	{field : 'categoryName', title : '大类名称', width : 120, align : 'left', seq : 4,notexport:true, 
									      				formatter : function(rowIndex,row,value){return row.name;}},
									      	{field : 'qtyControlFlag',title:'是否启用数量控制',width:120, formatter:editor.formatQtyControl,notexport:true},
									      	{field : 'updateUser', title : '修改人', width : 100, align : 'left', seq : 7,notexport:true},
							                {field : 'updateTime', title : '修改时间', width : 140, align : 'center', seq : 8,notexport:true}
						             	]" 
				                 />
		                </div>
		              </div>
             	</div>
             	<#-- 修改页面 -->
             	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				    <div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataForm" id="dataForm" method="post"  class="pd10">
					     	 <div data-options="region:'north',border:false,height:140" class="pd15">
								<div class="easyui-panel" data-options="title:'财务大类信息',fieldset:true,fit:true,collapsible:false">
									<table cellpadding="1" cellspacing="1" class="form-tb">
									   <input type="hidden" name="id" id="financialCategoryId">
									   <tr height="40">
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	分类编码：
									      </td>
									      <td width="140" align='left'>
									      		<input class="easyui-validatebox ipt" style="width:140px" name="financialCategoryNo" id="financialCategoryNo" data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']"/>
									      </td>
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	分类名称：
									      </td>
									      <td width="140" align='left'>
									      		<input class="easyui-validatebox ipt" style="width:140px" name="name" id="name" data-options="required:true,validType:['unNormalData','maxLength[10]']"/>
									      </td>
									   </tr>
									   <tr height="40">
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	公司名称：
									      </td>
									      <td width="140" align='left'>
									      		<input class="easyui-company ipt" name="companyName" id="companyName" data-options="required:true,inputWidth:150"/>
									      		<input type="hidden" name="companyNo" id="companyNo"/>
									      </td>
									        <#-- <td width="110" align='right'>
									      	设为默认值：
									      </td>
									      <td width="140" align='left'>
									      		<input type="checkbox" name="isDefault" id="isDefault" value="1"/>
									      </td>
									   </tr>
									   <tr height="40">
									   	  <td width="110" align='right'>备注：</td>
									      <td colspan="7">
									      		<input class="easyui-validatebox ipt" name="remark" id="remark" style="width:99%" data-options="validType:'maxLength[100]'"/>
									      </td>
									   </tr>
									   -->
									</table>
								</div>
							</div>
							<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
							    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
							    	<div title="结算分类明细">
							    		<div class="easyui-layout" data-options="fit:true">
								    		<div data-options="region:'north',border:false">
										    	<#-- 工具栏  -->
											  	<@p.toolbar id="dtltoolbar" listData=[
													 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "action" : "editor.insertRow()", "type":0},
										             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","action" : "editor.deleteRow()", "type":0},
										             {"id":"btn-add-batch","title":"批量新增","iconCls":"icon-build-some","action" : "editor.batchAddDtl()", "type":0}
										           ]
											  	/>
											  </div>
											  <div data-options="region:'center',border:false" id="dtlDataGrid">
												  <@p.datagrid id="dtlDataGridDiv"
										    			loadUrl="" saveUrl=""   defaultColumn="" 
											    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="false"
											    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
													    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
									           			columnsJsonList="[
												              {field : 'ck',checkbox:true,notexport:true},
											                  {field : 'name',title:'大类名称',width:130, editor:{}},
											                  {field : 'categoryNo',title:'大类编码',width:100, 
											                  	editor:{
											                  		type:'readonlybox',
											                  		options:{
																		id:'categoryNo',
																		name:'categoryNo',
																		width:'100px'
																	}
											                  	}
											                  },
											                  {field : 'qtyControlFlag',title:'是否启用数量控制',width:120, formatter:editor.formatQtyControl,
											                  	editor:{
											                  		type:'fascombobox',
											                  		options:{
																		id:'qtyControlFlag',
																		name:'qtyControlFlag',
																		data:[{value:'0', text:'否'}, {value:'1', text:'是'}],
																		width:120
																	}
																}
											                  },
											                  {field : 'updateUser', title : '修改人', width : 100, align : 'left'},
											                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'}
										                 ]"   
											             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
															editor.editRow(rowIndex, rowData);
											             }}' 
										        	/>
									        	</div>
							        </div>
								</div>
							</div>
						 </form>	
				   </div>
			   </div>
			   
			   <#-- 查看页面 -->
			   <div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
				    <div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataFormView" id="dataFormView" method="post"  class="pd10">
					     	 <div data-options="region:'north',border:false,height:140" class="pd15">
								<div class="easyui-panel" data-options="title:'财务大类信息',fieldset:true,fit:true,collapsible:false">
									<table cellpadding="1" cellspacing="1" class="form-tb">
									   <input type="hidden" name="id" id="financialCategoryId">
									   <tr height="40">
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	分类编码：
									      </td>
									      <td width="140" align='left'>
									      		<input class="ipt disabled" style="width:140px" name="financialCategoryNo" readonly="true"/>
									      </td>
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	分类名称：
									      </td>
									      <td width="140" align='left'>
									      		<input class="ipt disabled" style="width:140px" name="name" readonly="true"/>
									      </td>
									   </tr>
									   <tr height="40">
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	公司名称：
									      </td>
									      <td width="140" align='left'>
									      		<input class="easyui-company ipt" name="companyName" id="viewCompamyName" data-options="inputWidth:150,disabled:true" readonly="true"/>
									      </td>
									      <td width="110" align='right'>
									      	设为默认值：
									      </td>
									      <td width="140" align='left'>
									      		<input disabled type="checkbox" name="isDefault" value="1" readonly="true"/>
									      </td>
									   </tr>
									</table>
								</div>
							</div>
							<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
							    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
							    	<div title="结算分类明细">
							    		<div class="easyui-layout" data-options="fit:true">
								    		<div data-options="region:'north',border:false">
										    	<#-- 工具栏  -->
											  	<@p.toolbar id="dtltoolbarView" listData=[
													 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "action" : "", "type":0},
										             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","action" : "", "type":0},
										             {"id":"btn-add-batch","title":"批量新增","iconCls":"icon-build-some","action" : "", "type":0}
										           ]
											  	/>
											  </div>
											  <div data-options="region:'center',border:false" id="dtlDataGrid">
												  <@p.datagrid id="dtlDataGridDivView"
										    			loadUrl="" saveUrl=""   defaultColumn="" 
											    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="false"
											    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
													    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
									           			columnsJsonList="[
											                  {field : 'name',title:'大类名称',width:130, align : 'left'},
											                  {field : 'categoryNo',title:'大类编码',width:100, align : 'left'},
											                  {field : 'qtyControlFlag',title:'是否启用数量控制',width:120, formatter:editor.formatQtyControl},
											                  {field : 'updateUser', title : '修改人', width : 100, align : 'left'},
											                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'}
										                 ]"   
										        	/>
									        	</div>
							        </div>
								</div>
							</div>
						 </form>	
				   </div>
			   </div>
			   
            </div>
        </div>
</div>
</body>
</html>