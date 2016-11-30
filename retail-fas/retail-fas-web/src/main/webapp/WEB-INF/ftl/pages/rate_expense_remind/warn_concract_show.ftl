<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>两个月内到期合同</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/warnShopAbout.js?version=${version}"></script>
</head>
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<body class="easyui-layout">
<div id="mainDataGridDiv" data-options="region:'center',border:false">
	  <@p.datagrid id="mainDataGrid" loadUrl="" saveUrl="" defaultColumn="" title="两个月内到期合同"
	          isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
	           rownumbers="true" singleSelect="false" pageSize="20"
	           columnsJsonList="[
	           	  {field : 'concractNo',title : '合同号',width : 80,align:'center',halign:'center'},
	           	  {field : 'zoneNo',title : '大区编码',width : 80,align:'center',halign:'center'},
				  {field : 'zoneName',title : '大区',width : 80,align:'center',halign:'center'},
				  {field : 'organNo',title : '机构编码',width : 80,align:'center',halign:'center'},
				  {field : 'organName',title : '机构',width : 80,align:'center',halign:'center'},
				  {field : 'shopNo',title : '店铺编码',width : 80,align:'center',halign:'center'},
				  {field : 'shopName',title : '店铺',width : 180,align:'center',halign:'center'},
				  {field : 'companyNo',title : '公司编码',width : 80,align:'center',halign:'center'},
				  {field : 'companyName',title : '公司',width : 180,align:'center',halign:'center'},
				  {field : 'startMonth',title : '起始结算月',width : 100,align:'center',halign:'center'},
				  {field : 'endMonth',title : '终止结算月',width : 100,align:'center',halign:'center'},
				  {field : 'totalGuaranteeAmount',title : '总保底计划',width : 100,align:'center',halign:'center'},
				  {field : 'createUser',title : '建档人',width : 100,align:'center',halign:'center'},
				  {field : 'createTime',title : '建档时间',width : 120,align:'center',halign:'center'},
				  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'},
				  {field : 'updateTime',title : '修改时间',width : 120,align:'center',halign:'center'},
				  {field : 'remark',title : '备注',width : 200,align:'center',halign:'center'}
	              ]" />
</div>  
</body>
</html>