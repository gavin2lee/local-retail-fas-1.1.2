<!DOCTYPE html>
<head>
    <title>供应商查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/supplier.js?version=${version}"></script>
    
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/supplier/list.json" 
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  dialog_width="350" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
	             {"id":"btn-sure","title":"确定","iconCls":"icon-ok", "type":0},
	             {"id":"btn-cancel","title":"关闭","iconCls":"icon-close", "type":0}
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
		       		 		<tr>
		       		 			<th>供应商编码：</th>
		       		 			<td align="left"><input class="easyui-validatebox ipt" name="supplierNo" id="supplierNoCondition"/></td>
		       		 			<th>供应商名称：</th>
		       		 			<td align="left"><input class="easyui-validatebox ipt"  name="fullName" id="fullNameCondition"/></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'supplierNo',title : '供应商编码',width : 100,align:'left'},
				                  {field : 'fullName',title : '供应商名称',width : 200,align:'left'}
				                 ]" 
					       />
			</div>
		 </div>
	</div>
	
</body>
</html>