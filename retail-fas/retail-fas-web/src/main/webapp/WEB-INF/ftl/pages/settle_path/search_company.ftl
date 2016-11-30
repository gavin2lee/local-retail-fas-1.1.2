<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择结算公司</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/settle_path/SearchCompany.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"SearchCompany.search()","type":0},
		{"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"SearchCompany.clear()","type":0}
     ]/>
</div>
<div  data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true" id="subLayout">
        <div data-options="region:'north',border:false" >
        	<div class="search-div">
	            <form name="searchForm" id="searchForm" method="post">
	            	<input type="hidden" name="noFirstRow" value="${noFirstRow}"/>
	            	<input type="hidden" name="existCompanyNos" value="${existCompanyNos}"/>
	            	<table class="form-tb">
						<col width="80" />
						<col />
						<col width="80" />
						<col />
						<tbody>
							<tr>
								<th>公司编码： </th><td><input class="ipt"  name="dataNo" id="dataNo" /></td>
								<th>公司名称： </th><td><input class="ipt"  name="dataName" id="dataName" /></td>
							</tr>
						</tbody>
					</table>
	            </form>
	        </div>
	    </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="dataGridJG"></table>  
		</div>
	</div>
</div>
</body>
</html>