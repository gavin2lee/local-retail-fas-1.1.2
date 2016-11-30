<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择费用类别</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchCostCate.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchCostCate.dosearchCostCate()","type":0} ,
		{"id":"btn-remove1","title":"清除","iconCls":"icon-remove","action":"searchCostCate.clearCondition()", "type":0}
     ]/>
</div>

<div  data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true" id="subLayout">
		<!--搜索start-->
        <div data-options="region:'north',border:false" >
            <div class="search-div">
                <form name="subForm" id="subForm" method="post">
		       		 	<table class="form-tb">
	       		 		 	<col width="80" />
						    <col />
						    <col width="120" />
						    <col />
						    <tbody>
								 <td align="right" width="80"><span class="ui-color-red">*</span>公司：</td>
								<td align="left" width="110"><input class="easyui-validatebox ipt easyui-company"  data-options="required:true" name="companyName" id="companyName"/>
								<input type="hidden" name="companyNo" id="companyNo"/></td>	
									       		 		
			       		 			<th>总账费用类别：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt"  name="name" id="name"/></td>
			       		 		</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="searchCostCateDG"></table>  
 		</div>
    </div>
</div>
</body>
</html>