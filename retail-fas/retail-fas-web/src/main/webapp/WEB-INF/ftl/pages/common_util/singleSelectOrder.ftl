<!DOCTYPE html>
<head>
    <title>查询结算单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/singleSelectOrder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"singleSelectOrder.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"singleSelectOrder.clear()", "type":0}
	           ]
			/>
	
	</div>
	
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="singleSelectOrderForm" id="singleSelectOrderForm" method="post">
				     	<input type="hidden" id="queryCondition" name="queryCondition" value="${queryCondition}"/>	
		       		 	<table class="form-tb">
		       		 		<col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>单据编号：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="billNo" id="billNo"/></td>
		       		 			<th>单据日期：</th>
							    <td><input class="easyui-datebox  ipt"  id= "billDateStart" name="billDateStart" data-options="maxDate:'billDateEnd'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-datebox  ipt"  id= "billDateEnd" name="billDateEnd" data-options="minDate:'billDateStart'"/></td>	
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
				<table id="singleSelectOrderDG"></table>  					       
			</div>
		 </div>
	</div>
	
</body>
</html>