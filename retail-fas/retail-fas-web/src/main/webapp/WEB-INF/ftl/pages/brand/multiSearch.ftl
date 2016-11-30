<!DOCTYPE html>
<head>
    <title>品牌</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/brand.js?version=${version}"></script>
    
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/brand/list.json" 
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  dialog_width="350" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
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
				       <input type="hidden" name="params" value="${params}" id="params"/>
		       		 	<table class="form-tb">
		       		 		 <col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>品牌编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="brandNo" id="brandNoCodeCondition"/></td>
		       		 			<th>品牌名称：</th>
		       		 			<td><input class="easyui-validatebox ipt"  name="name" id="nameCondition"/></td>
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
			               checkOnSelect="true"  rownumbers="false" singleSelect="false" pageSize="20" 
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'brandNo',title : '品牌编码',width : 100,align:'left'},
				                  {field : 'name',title : '品牌名称',width : 150,align:'left'}
				                 ]" 
					       />
			</div>
		 </div>
	</div>
	
</body>
</html>