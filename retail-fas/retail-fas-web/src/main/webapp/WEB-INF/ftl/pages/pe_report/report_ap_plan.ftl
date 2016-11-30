<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>付款计划</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pe_report/report_ap_plan.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_ap_plan.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"report_ap_plan.clear()", "type":0},
             {"id":"btn-import","title":"节假日导入","iconCls":"icon-import","action":"pe_util.doImport('节假日设置.xlsx','/holiday/do_import',16,report_ap_plan.importCallBack)", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"report_ap_plan.doExport()","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="sumType" id="sumType"/>
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole/><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
									<th>到期日： </th>
									<td ><input class="easyui-datebox ipt" name="dueDateStart" id="dueDateStart" data-options="maxDate:'dueDateEnd',required:true"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" name="dueDateEnd" id="dueDateEnd" defaultValue="currentDate"  data-options="minDate:'dueDateStart',required:true"/></td>
								</tr>
								<tr>
									 <th>已结算：</th>
									 <td><input type="checkbox" name="isBalance" id="isBalance" value="true"/></td>
									 <th>显示原到期日：</th>
									 <td><input type="checkbox" name="isShowDue" id="isShowDue" value="true"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      	<div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
            	 		<div title="按天汇总" data-options="selected : true,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					           columnsJsonList="[
								                {field : 'buyerName', title : '公司', width : 200, align:'left'},
								                {field : 'salerName', title : '供应商', width : 200},
								                {field : 'amount', title : '应付金额', width : 100}]" 
		                 	/>	
            	 		</div>
            	 		<div title="按周汇总" data-options="selected : false,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid1"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					            columnsJsonList="[
								                {field : 'buyerName', title : '公司', width : 200, align:'left'},
								                {field : 'salerName', title : '供应商', width : 200},
								                {field : 'amount', title : '应付金额', width : 100}]" 
		                 	/>	
            	 		</div>
            	 		<div title="按月汇总" data-options="selected : true,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid2"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					            columnsJsonList="[
								                {field : 'buyerName', title : '公司', width : 200, align:'left'},
								                {field : 'salerName', title : '供应商', width : 200},
								                {field : 'amount', title : '应付金额', width : 100}]" 
		                 	/>	
            	 		</div>
            		</div>
			</div>
	 	</div>
	</div>
</body>
</html>