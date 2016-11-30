<!DOCTYPE html>
<head>
    <title>地区团购批发订单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchSaleOrderBill.js?version=${version}"></script>
    
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"searchSaleOrderBill.dosearchSaleOrderBill()","type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"searchSaleOrderBill.clearCondition()","type":0},
	             {"id":"btn-sure","title":"确定","iconCls":"icon-ok", "type":0},
	             {"id":"btn-cancel","title":"关闭","iconCls":"icon-close", "type":0}
	           ]
			/>
	
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="searchForm" id="searchForm" method="post">
		       		 	<table class="form-tb">
		       		 		 <col width="100" />
						    <col />
						    <col width="120" />
						    <col />
						    <tbody>
		       		 		<tr>
						 	<th>公司：</th>
								<td>
						     		<input class="easyui-validatebox ipt" iptSearch="company"  name="companyName" id="companyName"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />
								</td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="searchSaleOrderBillDG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="false" singleSelect="false" pageSize="20" 
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'originalBillNo',title : '订单编码',width : 150,align:'left'},
				                  {field : 'cost',title : '金额',width : 100,align:'left'},
				                  {field : 'salerNo',title : '卖方编码',width : 80,align:'left'},
				                  {field : 'salerName',title : '卖方名称',width : 160,align:'left'},
				                  {field : 'buyerNo',title : '买方编码',width : 120,align:'left'},
				                  {field : 'buyerName',title : '买方名称',width : 100,align:'left'}
				                 ]" 
					       />
			</div>
		 </div>
	</div>
	
</body>
</html>