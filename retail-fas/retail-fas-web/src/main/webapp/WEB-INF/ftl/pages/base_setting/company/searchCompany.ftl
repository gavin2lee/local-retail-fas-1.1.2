<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择结算公司</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchCompany.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchCompany.dosearchCompany()","type":0} ,
		{"id":"btn-remove1","title":"清空","iconCls":"icon-empty","action":"searchCompany.clearCondition()", "type":0}
     ]/>
</div>

<div  data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true" id="subLayout">
		<!--搜索start-->
        <div data-options="region:'north',border:false" >
            <div class="search-div">
                <form name="subForm" id="subForm" method="post">
                <input type="hidden" name="params" value="${params}" id="params"/>
		       		 	<table class="form-tb">
		       		 		<col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
		       		 		<tr height="25">
		       		 			<th>公司编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="companyNoLike" id="companyNoCodeCondition"/></td>
		       		 			<th>公司名称：</th>
		       		 			<td><input class="easyui-validatebox ipt"  name="nameLike" id="nameCondition"/></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="searchCompanyDG"></table>  
 		</div>
    </div>
</div>

</body>
</html>