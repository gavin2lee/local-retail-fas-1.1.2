<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择管理城市</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchOrgan.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchOrgan.dosearchOrgan()","type":0} ,
		{"id":"btn-remove1","title":"清除","iconCls":"icon-remove","action":"searchOrgan.clearCondition()", "type":0}
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
						    <input type="hidden" name="organLevel"  value="1"/></td>
						    <col />
						    <tbody>
			       		 		<tr>			       		 			
			       		 			<th>编码：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt" name="organCode" id="organCode"/></td>
			       		 			<th>名称：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt"  name="name" id="name"/></td>
			       		 		</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="searchOrganDG"></table>  
 		</div>
    </div>
</div>
</body>
</html>