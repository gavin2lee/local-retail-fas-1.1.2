<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客户保证金及预收款初始化数据</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_margin_init/select.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	    <#-- 工具栏  -->
		<@p.toolbar id="toolbar" listData=[
			{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"select.search()","type":0} ,
			{"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"select.clear()", "type":0}
	     ]/>
	</div>

	<div  data-options="region:'center',border:false">
	    <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
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
										<th>结算主体： </th><td><input class="ipt"  name="companyName" id="companyName_" /></td>
										<th>客户名称： </th><td><input class="ipt"  name="customerName" id="customerName_" /></td>
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