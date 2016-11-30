<!DOCTYPE html>
<head>
    <title>查询店铺</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/multiSelectShop.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="params" value="${params}"/>	
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"multiSelectShop.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"multiSelectShop.clear()", "type":0}
	           ]
			/>
	
	</div>
	
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="multiSelectShopForm" id="multiSelectShopForm" method="post">
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
				 <@p.datagrid id="multiSelectShopDG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 'ck',checkbox:true,width : 30},
				                  {field : 'shopNo',title : '店铺编号',width : 120},
				                  {field : 'shopName',title : '店铺名称',width : 180},
				                  {field : 'mallNo',title : '商场编号',width : 180,align:'left'},
				                  {field : 'mallName',title : '商场名称',width : 100,align:'left'},
				                  {field : 'address',title : '地址',width : 100}]" 
					       jsonExtend="{onDblClickRow:function(rowIndex,rowData){
								multiSelectShop.dbClick(rowData);
				}}"/>					       
			</div>
		 </div>
	</div>
	
</body>
</html>