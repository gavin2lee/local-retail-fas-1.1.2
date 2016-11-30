<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>出货统计表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pe_report/report_send.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_send.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"report_send.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('dtlDataGrid', '/pe_report/export_data?exportType=send', '出货统计表')","type":0}
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
									<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
									<th>单据日期：</th>
						    		<td ><input class="easyui-datebox ipt" name="sendDateStart" id="sendDateStart" defaultValue="startDate" data-options="maxDate:'sendDateEnd',required:true"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" name="sendDateEnd" id="sendDateEnd" defaultValue="endDate" data-options="minDate:'sendDateStart',required:true"/></td>
								</tr>
								<tr>
									<th>品牌： </th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" 
										data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:140"/>
										<input type="hidden" name="brandNos" id="brandNoId"/>
									</td>
									<th>是否结算： </th><td><input class="ipt" combobox="YesOrNo"  name="isBalance" /></td>
									<th>是否对账： </th><td><input class="ipt" combobox="YesOrNo"  name="balanceFlag" /></td>	
									<th>汇总： </th><td><input class="ipt" id="gatherBy"  name="gatherBy" /></td>	
								</tr>
								<tr>
									<th>业务类型： </th><td><input class="ipt" combobox="PeBizType"  name="peBizType" /></td>
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
			           		  {field : 'zoneName', title : '地区', width : 100},
			                  {field : 'buyerName', title : '地区公司', width : 240},
			                  {field : 'salerName', title : '供应商', width : 240},
			                  {field : 'brandUnitName', title : '品牌部', width : 100},
			                  {field : 'brandName', title : '品牌', width : 100},
			                  {field : 'shoesQty', title : '鞋数量', width : 100},
			                  {field : 'shoesAmount', title : '鞋金额', width : 100},
			                  {field : 'clothesQty', title : '服数量', width : 100},
			                  {field : 'clothesAmount', title : '服金额', width : 100},
			                  {field : 'partsQty', title : '配数量', width : 100},
			                  {field : 'partsAmount', title : '配金额', width : 100},
			                  {field : 'allQty', title : '数量合计', width : 100},
			                  {field : 'zoneAmount', title : '地区额', width : 100},
			                  {field : 'referAmount', title : '中间计算额', width : 100},
			                  {field : 'supplierAmount', title : '含税厂商额', width : 100},
			                  {field : 'diffAmount1', title : '返利额', width : 100},
			                  {field : 'diffAmount2', title : '盈亏额', width : 100},
			                  {field : 'remark', title : '备注', width : 180}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>