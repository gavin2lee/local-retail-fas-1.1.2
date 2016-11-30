<!DOCTYPE html>
<head>
    <title>查询结算单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/singleSelectInvoice.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="params" value="${params}"/>	
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"singleSelectInvoice.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"singleSelectInvoice.clear()", "type":0}
	           ]
			/>
	
	</div>
	
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="singleSelectInvoiceForm" id="singleSelectInvoiceForm" method="post">
		       		 	<table class="form-tb">
		       		 		<col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>单据编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="billNo" id="billNo"/></td>
		       		 			<th>单据日期：</th>
							    <td><input class="easyui-datebox  ipt"  id= "billDateStart" name="billDateStart" /></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-datebox  ipt"  id= "billDateEnd" name="billDateEnd" /></td>	
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
				<table id="singleSelectInvoiceDG"></table>  					       
			</div>
		 </div>
	</div>
	
</body>
</html>