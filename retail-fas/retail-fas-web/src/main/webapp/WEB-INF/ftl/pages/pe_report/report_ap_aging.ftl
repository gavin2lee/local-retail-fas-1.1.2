<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>应付账龄分析</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pe_report/report_ap_aging.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_ap_aging.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"report_ap_aging.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('dtlDataGrid', '/pe_report/export_data?exportType=aging', '应付账龄分析')","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company"  notGroupLeadRole/><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
									<th>已结算：</th>
									<td><input type="checkbox" name="isBalance" id="isBalance" value="true"/></td>
									<th>单据截止日期： </th>
									<td ><input class="easyui-datebox ipt" defaultValue="currentDate" name="sendDateEnd" id="sendDateEnd" data-options="required:true"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""   rownumbers="true" showFooter="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = ""  pageSize="20" 
			           columnsJsonList="[
			           		  {field : 'buyerName', title : '公司', width : 240},
			                  {field : 'salerName', title : '供应商', width : 240},
			                  {field : 'allAmount', title : '应付账款', width : 120},
			                  {field : 'noDueAmount', title : '未到期', width : 100},
			                  {field : 'due30Amount', title : '0-30天', width : 100},
			                  {field : 'due60Amount', title : '31-60天', width : 100},
			                  {field : 'due90Amount', title : '61-90天', width : 100},
			                  {field : 'due91Amount', title : '90天以上', width : 100},
			                  {field : 'dueNULLAmount', title : '到期日为空', width : 100}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>