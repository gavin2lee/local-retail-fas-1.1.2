<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区自购扣项调整</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/balanceAdjust.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"balanceAdjust.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"balanceAdjust.clear()", "type":0}
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
									<td>
										<input class="ipt readonly"   readonly="readonly" name="buyerName" id="buyerName"/>
										<input type="hidden" name="buyerNo" id="buyerNo" />
									</td>
									<th>供应商： </th>
									<td>
										<input class="ipt readonly"   readonly="readonly" name="salerName" id="salerName"/>
										<input type="hidden" name="salerNo" id="salerNo" />
									</td>
									<th>扣项日期：</th>
						    		<td>
						    			<input class="easyui-datebox"  name="balanceStartDate" id="balanceStartDate" data-options="maxDate:'balanceEndDate'"/>
						    		</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- </th>
									<td> 
										<input class="easyui-datebox"  name="balanceEndDate" id="balanceEndDate" data-options="minDate:'balanceStartDate'" />
									</td>
								</tr>	
								<tr>
									<th>品牌部： </th>
									<td>
										<input class="ipt" singleSearch="brandUnit"  name="brandUnitName" />
										<input type="hidden" name="brandUnitNo"/>
									</td>
								</tr>		
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="balanceAdjustDG"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"   emptyMsg = "" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			          columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true},
			                 {field : 'brandName',title:'品牌',width:100},
			 				 {field : 'deductionQty',title:'扣项数量',width:80,align:'right',halign:'center'},
							 {field : 'deductionAmount',title:'扣款金额',width:100,align:'right',halign:'center'},	
			 				 {field : 'deductionDate',title:'扣项日期',width:120},
			 				 {field : 'remark',title:'备注',width:180,align:'left',halign:'center'},
			 				 {field : 'balanceNo',title:'结算单号',width:150,align:'left',halign:'center'}
			 			]"	
                 />
			</div>
	 	</div>
	</div>
</body>
</html>