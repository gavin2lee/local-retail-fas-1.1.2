<!DOCTYPE html>
<head>
    <title>查询店铺</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/singleSelectShop.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="params" value="${params}"/>	
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"singleSelectShop.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"singleSelectShop.clear()", "type":0}
	           ]
			/>
	
	</div>
	
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="singleSelectShopForm" id="singleSelectShopForm" method="post">
				     	<input type="hidden" name="queryCondition" value="${queryCondition}"/>
		       		 	<table class="form-tb">
		       		 		<col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>结算公司：</th>
		       		 			<td><input class="easyui-validatebox ipt" readonly="readonly" name="companyName" value="${companyName}"/><input type="hidden" name="companyNo" value="${companyNo}"/></td>
		       		 			<th>经营城市：</th>
		       		 			<td><input class="easyui-combobox ipt" readonly="readonly" name="bizCityNo" /></td>
		       		 		</tr>
		       		 		<tr>
		       		 			<th>商场：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="mallNo"/></td>
		       		 			<th>店铺：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="shopNo" /></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
				<table id="singleSelectShopDG"></table>  					       
			</div>
		 </div>
	</div>
	
</body>
</html>