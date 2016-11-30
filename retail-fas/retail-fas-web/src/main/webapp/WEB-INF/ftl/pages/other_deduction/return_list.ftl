<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>关联客残销售单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/other_deduction/refReturn.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"refReturn.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"refReturn.clear()", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="salerNo" value="${salerNo}">
			      		<input type="hidden" name="buyerNo" value="${buyerNo}">
			      		<input type="hidden" name="billType" value="${billType}">
			      		<input type="hidden" name="balanceType" value="${balanceType}">
			      		<input type="hidden" name="parentId" value="${parentId}">
			      		<input type="hidden" id="balanceNo" value="${balanceNo}">
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
									<th>日期：</th>
								    <td><input class="easyui-datebox ipt"  name="balanceStartDate" id="balanceStartDate" data-options="maxDate:'balanceEndDate'" /></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt"  name="balanceEndDate" id="balanceEndDate" data-options="minDate:'balanceStartDate'" /></td>	
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  pageSize="20" showFooter="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = "" rownumbers="true"
		              checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			           columnsJsonList="[
		                	  {field : 'ck',checkbox:true,notexport:true},
			                  {field : 'sendDate', title : '单据日期', width : 100},
							  {field : 'originalBillNo', title : '单据编号', width : 150},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
							  {field : 'brandUnitName', title : '品牌部', width : 100},
							  {field : 'brandName',title : '品牌',width : 120},
		                      {field : 'oneLevelCategoryName', title : '一级大类', width : 100},		
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},
							  {field : 'itemCode', title : '商品编码', width : 150},
			                  {field : 'itemName', title : '商品名称', width : 200, align:'left'},
			                  {field : 'cost', title : '单价', width : 100},
			                  {field : 'sendQty', title : '数量', width : 100},
			                  {field : 'sendAmount', title : '金额', width : 100}]"	
                 />
			</div>
	 	</div>
	</div>
</body>
</html>