<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算调整列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/balanceAdjustHq.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"balanceAdjust.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"balanceAdjust.clear()", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value="${balanceType}">
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
									<td><input class="ipt readonly"   readonly="readonly" name="buyerName" id="buyerName"/><input type="hidden" name="buyerNo" id="buyerNo" /></td>
									<th>供应商： </th>
									<td><input class="ipt readonly"   readonly="readonly" name="salerName" id="salerName"/><input type="hidden" name="salerNo" id="salerNo" /></td>
									<th>品牌部： </th>
									<td><input class="ipt" singleSearch="brandUnit" name="brandUnitName" /><input type="hidden" name="brandUnitNo"/></td>
									<th>大类： </th>
									<td><input class="ipt" singleSearch="category" name="categoryName" /><input type="hidden" name="categoryNo"/></td>
								</tr>	
								<tr>
									<th>日期：</th>
						    		<td ><input class="easyui-datebox"  name="balanceStartDate" id="balanceStartDate" data-options="maxDate:'balanceEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox"  name="balanceEndDate" id="balanceEndDate" data-options="minDate:'balanceStartDate'" /></td>
								</tr>		
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
    			<div data-options="region:'center',border:false" style="height:350px;">
            	 	<div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
            	 		<div title="其他扣项" data-options="selected : true,closable : false" >
            	 			 <@p.datagrid id="balanceAdjustDG"  loadUrl="" saveUrl=""   defaultColumn="" 
					              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
						           rownumbers="true"   emptyMsg = "" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
						          columnsJsonList="[
						                 {field : 't', checkbox:true, width : 30, notexport:true},
						 				 {field : 'deductionDate',title:'扣项日期',width:100},
						 				 {field : 'brandName',title:'品牌',width:80},
						 				 {field : 'categoryName',title:'大类',width:80},
										 {field : 'deductionAmount',title:'扣款金额',width:100},	
						 				 {field : 'remark',title:'备注',width:180},
						 				 {field : 'balanceNo',title:'结算单号',width:140}]"	
			                 />
            	 		</div>
            	 		<div title="残鞋明细" data-options="selected : false,closable : false" >
            	 			 <@p.datagrid id="balanceAdjustDG1"  loadUrl="" saveUrl=""   defaultColumn="" 
					              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
						           rownumbers="true"   emptyMsg = "" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
					           columnsJsonList="[
					           		{field : 't', checkbox:true, width : 30, notexport:true},
									{field : 'returnDate',title : '退货日期',width : 100},			
									{field : 'itemCode',title : '商品编码',width : 120},
									{field : 'itemName',title : '商品名称',width : 120,align:'left'},
									{field : 'purchasePrice',title : '采购价',width : 80,align:'right'},		
									{field : 'qty',title : '数量',width : 80},				
									{field : 'amount',title : '金额',width : 80,align:'right'},
									{field : 'brandName',title : '品牌',width : 80},
									{field : 'oneLevelCategoryName', title : '大类', width : 80},
									{field : 'reason',title : '质量原因',width : 120},
									{field : 'opinion', title : '处理意见', width : 120},
					                {field : 'balanceNo',title:'结算单号',width:140}]" 
		                 	/>	
            	 		</div>
            		</div>
            	</div>
			</div>
	 	</div>
	</div>
</body>
</html>