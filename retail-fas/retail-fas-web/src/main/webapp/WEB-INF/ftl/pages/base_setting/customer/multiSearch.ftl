<!DOCTYPE html>
<head>
    <title>客户查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchCustomer.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"searchCustomer.dosearchCustomer()","type":0} ,
				 {"id":"btn-remove","title":"清除","iconCls":"icon-empty","action":"searchCustomer.clearCondition()", "type":0},
	             {"id":"btn-sure","title":"确定","iconCls":"icon-ok", "type":0},
	             {"id":"btn-cancel","title":"关闭","iconCls":"icon-close", "type":0}
	           ]
			/>
	
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="subForm" id="subForm" method="post">
		       		 	<table class="form-tb">
		       		 		 <col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>客户编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="customerNo" id="customerNo"/></td>
		       		 			<th>客户名称：</th>
		       		 			<td><input class="easyui-validatebox ipt"  name="fullName" id="fullName"/></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="searchCustomerDG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'customerNo',title : '客户编码',width : 100,align:'left'},
				                  {field : 'fullName',title : '客户名称',width : 200,align:'left'},
				                  {field : 'shortName',title : '客户简称',width : 100,align:'left'}
				                 ]" 
					       />
			</div>
		 </div>
	</div>
	
</body>
</html>