<!DOCTYPE html>
<head>
    <title>查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
   <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/multiSelectInvoice.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="params" value="${params}"/>	
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"multiSelectInvoice.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"multiSelectInvoice.clear()", "type":0}
	           ]
			/>
	
	</div>
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="multiSelectInvoiceForm" id="multiSelectInvoiceForm" method="post">
		       		 	<table class="form-tb">
		       		 		<col width="80" />
						    <col />
						    <col width="80" />
						    <col />
						    <tbody>
		       		 		<tr>
		       		 			<th>单据编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="billNo" id="billNo"/></td>
		       		 		</tr>
		       		 		<tr>
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
			      <@p.datagrid id="multiSelectInvoiceDG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 'ck',checkbox:true,width : 30},
				                  {field : 'billNo',title : '单据编号',width : 120},
				                  {field : 'statusName',title : '单据状态',width : 100,align:'left'},
				                  {field : 'buyerName',title : '公司',width : 180},
				                  {field : 'salerName',title : '供应商',width : 180,align:'left'},
				                  {field : 'billDate',title : '单据日期',width : 100,align:'left'}]" 
					       jsonExtend="{onDblClickRow:function(rowIndex,rowData){
								multiSelectInvoice.dbClick(rowData);
						 	}}"/>
					       
			</div>
		 </div>
	</div>
	
</body>
</html>