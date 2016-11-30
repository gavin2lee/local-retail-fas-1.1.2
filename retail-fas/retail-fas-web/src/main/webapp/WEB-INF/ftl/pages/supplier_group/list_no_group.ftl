<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>未分组供应商</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/supplier_group/list_no_group.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true">
			<!--列表-->
        	<div data-options="region:'center',border:false">
        	   <div class="easyui-layout" data-options="fit:true,plain:true">
					<div data-options="region:'center',border:false" >
						 <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
			              isHasToolBar="false"   onClickRowEdit="false" pagination="false" 
				           rownumbers="true"    checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
				           columnsJsonList="[
				              {field:'ck',checkbox:true,notexport:true, width : 30},
			                  {field : 'supplierNo', title : '供应商编码', width : 150},
			                  {field : 'fullName', title : '供应商名称', width : 250,align:'left'}]" 
	                 		/>
					</div>
		     	</div>
			</div>
	 	</div>
	</div>
</body>
</html>