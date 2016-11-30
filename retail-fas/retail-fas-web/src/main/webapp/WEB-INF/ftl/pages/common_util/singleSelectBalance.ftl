<!DOCTYPE html>
<head>
    <title>查询结算单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/singleSelectBalance.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="params" value="${params}"/>	
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"singleSelectBalance.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"singleSelectBalance.clear()", "type":0}
	           ]
			/>
	
	</div>
	
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="singleSelectBalanceForm" id="singleSelectBalanceForm" method="post">
		       		 	<table class="form-tb">
		       		 		<col width="120" />
						    <col />
						    <col width="120" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>结算类型：</th>
		       		 			<td><input class="easyui-combobox ipt" readonly="readonly"  data-options="valueField:'code',textField:'name',url:BasePath + '/common_util/getBalanceType?showType=HQ_VENDOR,AREA_AMONG,AREA_BUY,HQ_INSTEADOF_BUY'" name="balanceType" id="balanceType"/></td>
		       		 			<th>结算单号：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="billNo" id="billNo"/></td>
		       		 		</tr>
		       		 		<tr>
		       		 			<th>结算期：</th>
							    <td><input class="easyui-datebox  ipt"  id= "balanceStartDate" name="balanceStartDate" data-options="maxDate:'balanceEndDate'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-datebox  ipt"  id= "balanceEndDate" name="balanceEndDate" data-options="minDate:'balanceStartDate'"/></td>	
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
				<table id="singleSelectBalanceDG"></table>  					       
			</div>
		 </div>
	</div>
	
</body>
</html>