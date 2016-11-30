<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择结算单</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchShopBalanceBill.js?version=${version}"></script>
	    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchShopBalanceBill.searchShopBalance()","type":0} ,
		{"id":"btn-remove1","title":"清除","iconCls":"icon-remove","action":"searchShopBalanceBill.clearCondition()", "type":0}
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
			       		 		  <input type="hidden"  name="month" id="month" value="${month}"/>
			       		 		   <input type="hidden"  name="shopNo" id="shopNo" value="${shopNo}"/>							
							    <th>店铺：</th>
									<td colspan="3"><input class="easyui-validatebox ipt" multiSearch="shop"  name="shortName" id="shortName"  data-options="required:true" style="width:80px;"/>
								    <input type="hidden"  name="shopNo" id="shopNo" />
							   <th>结算月：</th>
								   <td>
							       <input class="easyui-datebox easyui-validatebox ipt"  name="month" id="month" data-options="required:true,dateFmt:'yyyyMM'"  />    							
								</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="shopBalanceBillDG"></table>  
 		</div>
    </div>
</div>

</body>
</html>