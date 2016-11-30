<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客户订单余额</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_prepay_warn/prepayWarn.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>

</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"prepayWarn.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"prepayWarn.clear()", "type":0},
             {"id":"top_btn_5","title":"导出","iconCls":"icon-export","action":"prepayWarn.doExport('dtlDataGrid','/wholesale_prepay_warn/export_warn?isHq=${isHq}','客户订单余额导出')","type":4}	
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
							<tbody>
								<tr>
									<th>公司：</th>
		                            <td>
		                              <#if isHq==true>
								      	<input class="easyui-company ipt"  name="salerName" id="companyNameCondition" 
		                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
		                           		<input type="hidden" name="salerNo" id="companyNoCondition"/>
								      </#if>
								      <#if isHq==null || isHq==''>
								      	<input class="easyui-company ipt"  name="salerName" id="companyNameCondition" 
		                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
		                           		<input type="hidden" name="salerNo" id="companyNoCondition"/>
								      </#if>
		                              <!--
	                                	<input class="easyui-company ipt" name="salerName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
	                                	<input type="hidden" name="salerNo" id="companyNoCondition"/>
	                                	-->
                                	</td>
									</td>
		                            <th>客户：</th>
		                            <td>
	                                	<input class="easyui-wholesale_zone_customer ipt" id="multiBuyerName" data-options="multiple:'true',inputWidth:160,inputNoField:'multiBuyerNo',inputNameField:'multiBuyerName'"/>
	                                	<input type="hidden" name="multiBuyerNo" id="multiBuyerNo"/>
									</td>
									
									<th>订单日期：</th>
									<td>
								    	<input  class="easyui-datebox readonly ipt" readonly="true" name="billStartDate" id="billStartDate" defaultValue="currentMonthFirstDayDate" data-options="{maxDate:'billEndDate'}"/>
								    </td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input  class="easyui-datebox readonly ipt" readonly="true" name="billEndDate" id="billEndDate" defaultValue="currentMonthLastDayDate" data-options="{minDate:'billStartDate'}"/>
									</td>
								</tr>
								<tr>
								    <th>批发订单： </th><td><input class="ipt"  name="orderNo" id="orderNo" /></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" rownumbers="true"  
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" pageSize="20"
			                columnsJsonList="[
			                  {field : 'companyName', title : '公司名称', width : 180,align:'left'},	
			                  {field : 'companyNo', title : '公司编码', width : 80,align:'left'},	
			                  {field : 'customerName', title : '客户名称', width : 150,align:'left'},	
			                  {field : 'customerNo', title : '客户编码', width : 80,align:'left'},	
			                  {field : 'orderNo', title : '批发订单号', width : 150},
			                  {field : 'statusName', title : '订单状态', width : 150},
			                  {field : 'orderDate', title : '订单日期', width : 150},
			                  {field : 'wholesaleOrderTypeName', title : '订单类型', width : 150},
							  {field : 'amount', title : '订单金额', width : 100,align:'right'},
							  {field : 'advanceScale', title : '订金比例', width : 100,align:'right'},
							  {field : 'preOrderAmount', title : '应收订金', width : 100,align:'right'},
							  {field : 'paidAmount', title : '实收货款', width : 100,align:'right'},
							  
							  {field : 'frozenOrderAmount', title : '冻结金额', width : 100,align:'right'},
							  {field : 'unfreezeOrderAmount', title : '释放冻结金额', width : 100,align:'right'},
							  
							  {field : 'outAmount', title : '已出库金额', width : 100,align:'right'},
							  {field : 'outRebateAmount', title : '返利金额', width : 100,align:'right'},
                              {field : 'deductionAmount', title : '扣项金额', width : 100,align:'right'},
                              {field : 'remainingAmount', title : '客户订单余额', width : 100,align:'right'},
                              {field : 'orderRemainingBookAmount', title : '客户订单账面余额', width : 100,align:'right'},
                              {field : 'creditAmount', title : '信贷额度', width : 100,align:'right'},
							  {field : 'marginRemainderAmount', title : '保证金余额', width : 100,align:'right'},
							  {field : 'marginAmount', title : '合同保证金', width : 100,align:'right'},
							  {field : 'termName', title : '收款条款', width : 100}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>