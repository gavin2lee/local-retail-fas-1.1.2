<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>信息提示</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/show_error_info.js?version=${version}"></script>

</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',border:false">
	      <@p.datagrid id="showErrorInfoDG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
	              isHasToolBar="false" onClickRowEdit="false" pagination="false" selectOnCheck="false" 
	               checkOnSelect="true" pageSize="100" emptyMsg =""
		           singleSelect="false"
		             columnsJsonList="[
		             	  {field : 'billNo', title : '发票编号', width : 150, align : 'left',halign:'center'},
		                  {field : 'sourceNo', title : '开票申请号', width : 150, align : 'left',halign:'center'},
		                  {field : 'salerName', title : '开票方名称', width : 200, align : 'left',halign:'center'},
		                  {field : 'buyerName', title : '客户名称', width : 200, align : 'left',halign:'center'},
		                  {field : 'remark', title:'错误信息',width:250,align:'left',halign:'center',
							  styler: function(value,row,index){
								if (value){
								 return 'background-color:#ffee00;color:red;';
								}
								 return '';
		                  }}
	                  ]" />
     </div>
     
 </div>
</body>
</html>