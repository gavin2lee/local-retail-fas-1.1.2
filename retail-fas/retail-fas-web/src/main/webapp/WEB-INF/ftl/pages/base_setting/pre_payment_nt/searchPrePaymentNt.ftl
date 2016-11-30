<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>选择开票申请号</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/prePaymentNt.js?version=${version}"></script>
</head>
<body  class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <#-- 工具栏  -->
	<@p.toolbar id="toolbar" listData=[
		{"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"searchPrePaymentNt.dosearchPrePaymentNt()","type":0} ,
		{"id":"btn-remove1","title":"清空","iconCls":"icon-empty","action":"searchPrePaymentNt.clearCondition()", "type":0}
     ]/>
</div>

<div  data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true" id="subLayout">
		<#--搜索start-->
        <div data-options="region:'north',border:false" >
            <div class="search-div">
                <form name="subForm" id="subForm" method="post">
                <input type="hidden" name="buyerNo" id="buyerNo" value="${buyerNo}">
                <input type="hidden" name="salerNo" id="salerNo" value="${salerNo}">
		       		 	<table class="form-tb">
	       		 		 	<col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
			       		 		<tr>
			       		 			<th>开票申请号：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt" name="invoiceApplyNo" id="invoiceNoCondition"/></td>
			       		 		</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
            </div>
        </div>
	  
	  	<div data-options="region:'center',border:false">
	  		<table id="searchPrePaymentNtDG"></table>  
 		</div>
    </div>
</div>

</body>
</html>