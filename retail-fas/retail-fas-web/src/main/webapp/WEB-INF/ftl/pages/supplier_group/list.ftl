<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>供应商组维护</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/supplierGroupType.js?version=${version}"></script>
</head>
<body>
	<@p.commonSetting search_url="/supplier_group/list.json" 
					  save_url="/supplier_group/save_all" 
					  update_url="/supplier_group/save_all" 
					  export_url="/supplier_group/do_fas_export"
					  export_title="供应商组导出"
					  export_type="common"
					  audit_url="/supplier_group/do_audit?auditVal=1"
					  anti_audit_url="/supplier_group/do_audit?auditVal=0"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="700" 
					  dialog_height="600"
					  primary_key="id"/>
					  
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'供应商组设置'">
            <div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false" class="toolbar-region">
				     <@p.toolbar id="toolbar" listData=[
						 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
			             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
			             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":0},
			             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":0},
				         {"id":"btn-audit","title":"审核","iconCls":"icon-aduit","type":0},
				         {"id":"btn-anti-audit","title":"反审核","iconCls":"icon-aduit","type":0},
			             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":0},
			             {"id":"btn-checkout","title":"检出","iconCls":"icon-search","action":"supplierGroupType.showSupplierNoGroupDialog();","type":0}
			           ]
					/>
				</div>

				<div  data-options="region:'center',border:false">
			    	<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'north',border:false" >
							<div class="search-div">
						      	<form name="searchForm" id="searchForm" method="post">
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
											<tr height='33'>
										 		<th align='right'>供应商组编码：</th>
										 		<td><input class="easyui-validatebox ipt" style="width:150px" name="groupNoLike" id="groupNoCondition" /></td>
										 		<th align='right'>供应商组名称：</th>
										 		<td><input class="easyui-validatebox ipt" style="width:150px" name="groupNameLike" id="groupNameCondition" /></td>
									 		</tr>
										</tbody>
									</table>
								</form>
							</div>
						</div>
						<!--列表-->
			        	<div data-options="region:'center',border:false">
					      <@p.subdatagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" title=""
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
						           rownumbers="true" singleSelect="true"
						           columnsJsonList="[
						                 	  {field : 'ck',checkbox:true,notexport:true},
							                  {field : 'groupNo',title : '供应商组编码',width: 120,align:'center'},
							                  {field : 'groupName',title : '供应商组名称',width: 150,align:'center'},
							                  {field : 'enableTime',title : '启用日期',width: 100,align:'center'},
							                  {field : 'disableTime',title : '终止日期',width: 100,align:'center'},
							                  {field : 'auditStatusName',title : '审核状态',width: 80,align:'center'},
							                  {field : 'createUser',title : '建档人',width : 100,align:'center',halign:'center'},
											  {field : 'createTime',title : '建档时间',width : 150,align:'center',halign:'center'},
							                  {field : 'updateUser',title : '修改人',width: 100,align:'center'},
							                  {field : 'updateTime',title : '修改时间',width: 130,align:'center'},
							                  {field : 'remark',title : '备注',width: 150,align:'center'}
						                 ]" 
							          jsonExtend='{
				                           onDblClickRow:function(rowIndex, rowData){
						                	  //双击方法
						                   	   fas_common.loadDetail(rowData);
						                   }
					                 }'
					                 loadSubGridUrl="/supplier_group_rel/query_supplier_group_dtl"
					             	 subPagination="false"
					             	 subGridColumnsJsonList="[
					             	  	{field : 'supplierNo',title : '供应商编码', width : 120, align : 'left',notexport:true},
								      	{field : 'supplierName',title : '供应商名称', width : 120, align : 'left',notexport:true},
								      	{field : 'createUser',title : '创建人', width : 90, align : 'left',notexport:true},
						                {field : 'createTime',title : '创建时间', width : 140, align : 'left',notexport:true}
					             	]" 
			                 />
						</div>
				 	</div>
				</div>
	
				<!-- 新增修改-->
				<div id="myFormPanel" class="easyui-dialog" data-options="closed:true">
					<div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataForm" id="dataForm" method="post" class="pd10">
					     	<div data-options="region:'north',border:false,height:150" class="pd15">
								<div id="div1" class="easyui-panel" data-options="title:'供应商组信息',fieldset:true,fit:true,collapsible:false">
									<table cellpadding="1" cellspacing="1" class="form-tb">
										<input type="hidden" name="id" id="id">
										  <input type="hidden" id="organTypeNo" name="organTypeNo">
								   		<tr>
								   			<th width="110" align='right'>
												<span class="ui-color-red">*</span>
												供应商组编码：
											</th>
						        		    <td><input class="easyui-validatebox ipt" name="groupNo" id="groupNo" data-options="required:true,validType:'length[2,18]'" /></td>
						        		    <th width="110" align='right'>
						        		    	<span class="ui-color-red">*</span>
						        		    	供应商组名称：
						        		    </th>
						        		    <td><input class="easyui-validatebox ipt" name="groupName" id="groupName" data-options="required:true,validType:'length[2,100]'"/></td>
						        		</tr>
						        		<tr>
							         		<th width="110" align='right'>
						        		    	<span class="ui-color-red">*</span>
						        		    	启用日期：
						        		    </th>
						        		    <td><input class="easyui-validatebox easyui-datebox ipt" name="enableTime" id="enableTime" data-options="required:true,maxDate:'disableTime'" /></td>
						        		    <th width="110" align='right'>
						        		    	<span class="ui-color-red">*</span>
						        		    	终止日期：
						        		    </th>
						        		    <td><input class="easyui-validatebox easyui-datebox ipt" name="disableTime" id="disableTime" data-options="required:true,minDate:'enableTime'" /></td>
						        		</tr>
						        		<tr>
						        		    <th>备注：</th>
						        		    <td><input class="easyui-validatebox ipt" name="remark" id="remark" data-options="validType:'length[0,100]'" /></td>
						        		</tr>
									</table>
								</div>
							</div>
							<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
								<div class="easyui-layout" data-options="fit:true,border:false">
									<div data-options="region:'north',border:false" >
								    	<@p.toolbar id="dtltoolbar" listData=[
											 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
								             {"id":"btn-edit-detail","title":"修改行","iconCls":"icon-edit","action" : "fas_common_editor.editRow()", "type":0},
								             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","action" : "fas_common_editor.deleteRow()", "type":0},
								             {"id":"btn-import-detail","title":"导入","iconCls":"icon-import","action":"supplierGroupType.doImport()","type":0},
								              {"id":"btn-import-dtl","title":"引入未分组供应商","iconCls":"icon-import","action" : "supplierGroupType.importNoGroup()", "type":0}
								           ]
									  	/>
									</div>  	
							  		<div data-options="region:'center',border:false" >
								  	<@p.datagrid id="dtlDataGridDiv"
								    	loadUrl="" saveUrl=""   defaultColumn="" 
								    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false"
								    	    height="300" width="" onClickRowEdit="false" singleSelect="true" selectOnCheck="false"
										    pagination="true" rownumbers="true" enableHeaderClickMenu="false"
						           			columnsJsonList="[
									              {field : 'ck',checkbox:true,notexport:true},
									              {field : 'supplierNo',title:'供应商编码',width:170, editor:{type:'searchbox',
								                  															  options:{
								                  															  	id:'supplierNo',
								                  															  	name:'supplierNo',
								                  															  	textId:'supplierName',
								                  															  	valueField:'supplierNo',
								                  															  	textField:'fullName',
								                  															  	required:true,
								                  															  	title:'选择供应商',
								                  															  	url:'/base_setting/supplier/toSearchSupplier',
								                  															  	callback:supplierGroupType.IsSupplierAlreadyBelongTo
								                  															  }
								                  														}
								                  },
								                  {field : 'supplierName',title:'供应商名称',width:170, editor:{type:'searchboxname',options:{
								                  																				id:'supplierName',
								                  																				name:'supplierName',
								                  																				readonly:true
								                  																			}
								                  																		}
								                  },
								                  {field : 'createUser', title : '创建人', width : 90, align : 'left'},
								                  {field : 'createTime', title : '创建时间', width : 140, align : 'left'}
							                 ]"   
								             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
												fas_common_editor.editRow(rowIndex, rowData);
								             }}' 
							        	/>
							        	</div>
							    </div>
							</div>  
						 </form>	
					</div>
			   	</div>
		<#include  "/WEB-INF/ftl/pages/supplier_group/list_view.ftl" >
		   	</div>
		</div>
   	</div>
</body>
</html>
