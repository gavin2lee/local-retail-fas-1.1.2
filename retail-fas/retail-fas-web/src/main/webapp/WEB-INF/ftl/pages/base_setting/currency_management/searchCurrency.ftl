<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择币种</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchCurrency.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchCurrency.dosearchCurrency()","type":0} ,
		{"id":"btn-remove1","title":"清空","iconCls":"icon-empty","action":"searchCurrency.clearCondition()", "type":0}
     ]/>
</div>

<div  data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true" id="subLayout">
		<!--搜索start-->
        <div data-options="region:'north',border:false" >
            <div class="search-div">
                <form name="subForm" id="subForm" method="post">
		       		 	<table class="form-tb">
		       		 		<tr height="25">
		       		 			<td width="100" align="right">币种编码：</td>
		       		 			<td align="left"><input class="easyui-validatebox ipt" name="currencyCode" id="currencyCodeCondition"/></td>
		       		 			<td width="100" align="right">币种名称：</td>
		       		 			<td align="left"><input class="easyui-validatebox ipt"  name="currencyName" id="currencyNameCondition"/></td>
		       		 		</tr>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="searchCurrencyDG"></table>  
 		</div>
    </div>
</div>

</body>
</html>