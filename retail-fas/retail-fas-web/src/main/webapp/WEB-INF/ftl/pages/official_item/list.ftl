<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>正式货号报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/official_item/officialItem.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"officialItem.searchData()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"officialItem.searchClear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid', '/official_item/export_data', '正式货号报表')","type":0},
             {"id":"btn-update","title":"手动同步数据","iconCls":"icon-export","action":"officialItem.updateData()","type":0}
             {"id":"btn-update1","title":"全量同步数据","iconCls":"icon-export","action":"officialItem.updateAllData()","type":0}
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
									<th>品牌部： </th>
									<td><input class="ipt" multiSearch="brandUnit" /><input type="hidden" name="multiBrandUnitNo"></td>
									<th>商品： </th>
									<td><input class="ipt" multiSearch="item"  /><input type="hidden" name="multiItemCode"></td>
									<th>报价单号： </th><td><input class="ipt"  name="quoteNo"  /></td>	
									<th>超额OA状态： </th><td><input class="ipt" id="excessStatus" name="excessStatus"  /></td>	
								</tr>
								<tr>
									<th>更新时间：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="updateStartDate" id="updateStartDate" data-options="maxDate:'updateEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="updateEndDate" id="updateEndDate"  data-options="minDate:'updateStartDate'"/></td>
									<th>同步起始时间：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="updateTimeStart" id="updateTimeStart" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""   rownumbers="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = ""  pageSize="20" 
			           columnsJsonList="[
			                  {field : 'brandUnitNo', title : '品牌部', width : 80},
			                  {field : 'brandNo', title : '品牌', width : 80},
			                  {field : 'itemCode', title : '商品编码', width : 150},
			                  {field : 'year', title : '年份', width : 80},
			                  {field : 'season', title : '季节', width : 80},
			                  {field : 'sysPrice', title : '进货价', width : 80},
			                  {field : 'qprice', title : '报价', width : 80},
			                  {field : 'aprice', title : '核定价', width : 80},
			                  {field : 'excessStatusName', title : '超额OA状态', width : 120},
			                  {field : 'supplierNo', title : '供应商编码', width : 80},
			                  {field : 'supplierName', title : '供应商名称', width : 180,align:'left'},
			                  {field : 'updateTime', title : '更新时间', width : 150}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>