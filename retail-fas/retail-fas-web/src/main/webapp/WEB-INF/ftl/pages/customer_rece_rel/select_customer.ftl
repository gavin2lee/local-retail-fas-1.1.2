<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择收款条款-客户</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/customer_rece_rel/Select.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	    <#-- 工具栏  -->
		<@p.toolbar id="toolbar" listData=[
			{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"Select.search()","type":0} ,
			{"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"Select.clear()", "type":0}
	     ]/>
	</div>

	<div  data-options="region:'center',border:false">
	    <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
	        <div data-options="region:'north',border:false" >
	            <div class="search-div">
	                <form name="searchForm" id="searchForm" method="post">
	                	<#-- 只查询启用的数据 -->
	                	<input type="hidden" name="marginControlFlag" id="marginControlFlag" value="${marginControlFlag}"/>
		       		 	<table class="form-tb">
		       		 		<col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <tbody>
		       		 			<tr>
									<th>客户编码： </th><td><input class="ipt"  name="customerNo"/></td>
									<th>客户名称： </th><td><input class="ipt"  name="customerName"/></td>
								</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
	            </div>
	        </div>
		  
		  	<div data-options="region:'center',border:false">
		  		<table id="dataGridDiv"></table>  
	 		</div>
	    </div>
	</div>

</body>
</html>