<!DOCTYPE html>
<head>
    <title>查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
   <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/multiSelect.js?version=${version}"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"multiSelect.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"multiSelect.clear()", "type":0}
	           ]
			/>
	
	</div>
	<input type="hidden" id="selectUrl" value="${selectUrl}"/>		
	<input type="hidden" id="queryCondition" value="${queryCondition}"/>		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="multiSelectForm" id="multiSelectForm" method="post">
				     	<input type="hidden" id="companyNo" name="companyNo" value="${companyNo}"/>
				     	<input type="hidden" id="params" name="params" value="${params}"/>
		       		 	<table class="form-tb">
		       		 		<col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>管理城市：</th>
		       		 			<td><input class="easyui-validatebox ipt" NAME="organName" id="organName"/></td>
		       		 			<th>编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="multiNo" id="multiNo"/></td>
		       		 			<th>名称：</th>
		       		 			<td><input class="easyui-validatebox ipt"  name="multiName" id="multiName"/></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="multiSelectDG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 'ck',checkbox:true,width : 30},
				                  {field : 'bankName',title : '管理城市',width : 120},
				                  {field : 'code',title : '编码',width : 120},
				                  {field : 'name',title : '名称',width : 250,align:'left'}
				                 ]" 
					       jsonExtend="{onDblClickRow:function(rowIndex,rowData){
								multiSelect.dbClick(rowData);
						 	}}"/>
					       
			</div>
		 </div>
	</div>
	
</body>
</html>