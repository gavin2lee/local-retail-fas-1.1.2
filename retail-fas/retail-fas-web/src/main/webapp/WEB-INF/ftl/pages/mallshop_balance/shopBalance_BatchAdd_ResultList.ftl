<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>批量生成门店结算单处理结果</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shopbalance_batchadd_resultlist.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
		      <@p.datagrid id="shopBalanceBatchAddResultListDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"   onClickRowEdit="false" pagination="false" pageSize="20"
			           rownumbers="true"   emptyMsg = "" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			           columnsJsonList="[
		                  {field : 'shopNo', title : '店号', width : 60, align : 'left'},
		                  {field : 'shortName', title : '门店', width : 120, align : 'left'},
		                  {field : 'month', title : '结算月', width : 60, align : 'left'},
		                  {field : 'balanceNo', title : '单据编码', width : 150, align : 'right'},
		                  {field : 'errorInfo', title : '结果信息', width : 700, align : 'left',
							  styler: function(value,row,index){
								if (value){
								 return 'background-color:#ffee00;color:red;';
								}
								 return '';
			            }}]" 	
                 />
	</div>
</body>
</html>