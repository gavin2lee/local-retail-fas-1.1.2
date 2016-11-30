<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算期交叉或者不连续</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/warnShopAbout.js?version=${version}"></script>
</head>
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<body class="easyui-layout">
<div id="mainDataGridDiv" data-options="region:'center',border:false">
	  <@p.datagrid id="mainDataGrid" loadUrl="" saveUrl="" defaultColumn="" title="结算期交叉或者不连续"
	          isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
	           rownumbers="true" singleSelect="false" pageSize="20"
	           columnsJsonList="[
				  {field : 'shopNo',title : '店铺编码',width : 80,align:'left',halign:'center'},
				  {field : 'shortName',title : '店铺',width : 180,align:'left',halign:'center'},
				  {field : 'companyNo',title : '公司编码',width : 80,align:'left',halign:'center'},
				  {field : 'companyName',title : '公司',width : 250,align:'left',halign:'center'},
				  {field : 'balanceStartDate',title : '起始日期(最初)',width : 120,align:'center',halign:'center'},
				  {field : 'balanceEndDate',title : '终止日期(最末)',width : 120,align:'center',halign:'center'},
				  {field : 'totalDays',title : '合计天数',width : 100,align:'center',halign:'center'},
				  {field : 'partDays',title : '实际天数',width : 100,align:'center',halign:'center'}
	              ]" />
</div>  
</body>
</html>