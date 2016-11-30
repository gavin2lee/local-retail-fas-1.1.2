<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部应付-结算汇总表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/gatherList.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"gatherList.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"gatherList.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-remove","action":"gatherList.doExport()", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value="1" id="balanceType">
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
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
									<th>品牌部： </th>
									<td><input class="ipt" multiSearch="brandUnit"  /><input type="hidden" name="multiBrandUnitNo"></td>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category" /><input type="hidden" name="multiCategoryNo"></td>
								</tr>	
								<tr>
									<th>发出日期：</th>
						    		<td><input class="easyui-datebox ipt" defaultValue="startDate" name="balanceStartDate" id="balanceStartDate" data-options="maxDate:'balanceEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="balanceEndDate" id="balanceEndDate" data-options="minDate:'balanceStartDate'"/></td>
								</tr>									
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"
			           rownumbers="true"  emptyMsg = "" pageSize="20" showFooter="true"
			           columnsJsonList="[
			                  {field : 'buyerName', title : '公司', width : 200,align:'left'},
			                  {field : 'brandUnitName', title : '品牌部', width : 100},
			                  {field : 'categoryName', title : '大类', width : 100},
 							  {field : 'salerName', title : '供应商',  width : 200,align:'left'},
			                  {field : 'outQty', title : '进货数量', width : 100},
			                  {field : 'outAmount', title : '进货金额', width : 100},	
			                  {field : 'returnQty', title : '退货数量', width : 100},
			                  {field : 'returnAmount', title : '退货金额', width : 100},
			                  {field : 'customReturnQty', title : '客残数量', width : 100},
			                  {field : 'customReturnAmount', title : '客残金额', width : 100},
			                  {field : 'deductionAmount', title : '其他扣项', width : 100},	
			                  {field : 'balanceQty', title : '应付数量', width : 100},
			                  {field : 'balanceAmount', title : '应付金额', width : 100}]"
                 />
			</div>
	 	</div>
	</div>
</body>
</html>