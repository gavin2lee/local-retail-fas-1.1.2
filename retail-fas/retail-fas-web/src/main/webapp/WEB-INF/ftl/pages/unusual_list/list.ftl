<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>异常单据列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/balance_date/balanceDate.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"balanceDate.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"balanceDate.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"balanceDate.add()", "type":0},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"balanceDate.del()","type":0}, 
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"balanceDate.save()","type":0},                
             {"id":"btn_import","title":"导入","iconCls":"icon-import","action":"balanceDate.importExcel","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"balanceDate.exportExcel()","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--列表-->
        	<div data-options="region:'center',border:false">
			      <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
					isHasToolBar="false" divToolbar="" height="187"    pageSize="10" title="异常单据列表"
					onClickRowAuto="false" rownumbers="true" 
					columnsJsonList="[
	 				{field:'ck',checkbox:true,notexport:true},
	 				{field:'unusualRemark',title:'异常原因',width:120},
	 				{field:'billNo',title:'单据编号',width:120},	 				
	 				{field:'billType',title:'单据类型',width:120},
	 				{field:'billDate',title:'单据日期',width:100},
	 				{field:'companyNo',title:'结算主体',width:100},
	 				{field:'supplier',title:'供应商',width:120},
	 				{field:'area',title:'收/退货地区',width:120},
	 				{field:'purchaseNo',title:'订单号',width:120},
	 				{field:'remark',title:'备注',width:180}]"		
					 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
					 }}"/>
			</div>
	 	</div>
	</div>
</body>
</html>