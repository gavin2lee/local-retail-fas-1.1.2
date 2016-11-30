<!DOCTYPE html>
<head>
    <title>辅助项目类型</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/assist_project_type.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/assist_project_type/list.json" 
					  save_url="/base_setting/assist_project_type/insert" 
					  update_url="/base_setting/assist_project_type/update" 
					  del_url="/base_setting/assist_project_type/save" 
					  enable_url="/base_setting/assist_project_type/do_enable"
					  unable_url="/base_setting/assist_project_type/do_unable"
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="180"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
	             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":1},
	             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":2},
	             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":3},
	             {"id":"btn-enable","title":"启用","iconCls":"icon-lock","type":27},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-unlock","type":100}
	           ]
			/>
	</div>

	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
	     <#--搜索start-->
			<div data-options="region:'north',border:false" >
	     		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
				     	<col width="100" />
					    <col />
					    <col width="100" />
					    <col />
					    <tbody>
	       		 		<tr>
	       		 			<th>公司名称：</th>
	       		 			<td>
	    						<input class="easyui-company ipt" name="companyName" id="companyNameCondition"
	    							data-options="inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:180"
	    						/>
	       		 				<input type="hidden" name="companyNo" id="companyNoCondition"/>
	       		 			</td>
	       		 			<th>类型名称：</th>
	       		 			<td>
	       		 				<input class="easyui-validatebox ipt" name="typeName" id="typeNameCodeCondition"/>
	       		 			</td>
	       		 		</tr>
	       		 		</tbody>
	       		 	</table>
				</form>
			</div>
		</div>
		 
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="/base_setting/assist_project_type/list.json" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			               checkOnSelect="true"  rownumbers="true"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'typeCode',title : '类型编码',width : 100,align:'left',halign:'center'},
				                  {field : 'typeName',title : '类型名称',width : 100,align:'left',halign:'center'},
				               	  {field : 'companyName',title : '公司名称',width : 200,align:'left',halign:'center'},
				                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
				                  {field : 'createUser',title : '创建人',width : 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               fas_common.loadDetail(rowData);
					                   }
			         }'/>
			</div>
			
		 </div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:350,height:300" > 
		<form  id="dataForm" method="post">
			<input type="hidden" id="id" name="id" />
			<table  class="form-tb">
				<col width="100" />
			    <col />
			    <tbody>
				<tr>
					<th><span class="ui-color-red">*</span>类型编码：</th>
					<td>
						<input class="easyui-validatebox ipt" name="typeCode" id="typeCodeId"  data-options="required:true,validType:['unNormalData','engNum','maxLength[4]']" />  
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>类型名称：</th>
					<td >
						<input class="easyui-validatebox ipt"  name="typeName" id="typeNameId" data-options="required:true,validType:['unNormalData','chinese']" />
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>公司名称：</th>
					<td>
						<input class="easyui-company ipt" readonly="true" name="companyName" id="companyNameId"
						 data-options="required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:160"/>
						<input type="hidden" name="companyNo" id="companyNoId"/>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>