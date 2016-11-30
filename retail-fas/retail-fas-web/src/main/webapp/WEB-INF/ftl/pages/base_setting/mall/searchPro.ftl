<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择促销活动</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchPro.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchPro.dosearchPro()","type":0} ,
		{"id":"btn-remove1","title":"清除","iconCls":"icon-remove","action":"searchPro.clearCondition()", "type":0}
     ]/>
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
			       		 		 <input type="hidden"  name="shopNo" id="shopNo" value="${shopNo}"/>
			       		 		  <input type="hidden"  name="balanceNo" id="balanceNo" value="${balanceNo}"/>
			       		 			<th>编码：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt" name="proNo" id="proNo"/></td>			       		 		
			       		 			<th>名称：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt" name="proName" id="proName"/></td>
			       		 		</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="searchProDG"></table>  
 		</div>
    </div>
</div>
</body>
</html>