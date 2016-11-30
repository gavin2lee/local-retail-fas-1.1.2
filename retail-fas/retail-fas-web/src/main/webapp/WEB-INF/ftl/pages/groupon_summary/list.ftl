<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>团购销售汇总</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >

<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/groupon/grouponSummary.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action" : "_grouponSummary.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "_grouponSummary.clear()","type":0},
             {"id":"btn-print","title":"打印","iconCls":"icon-print", "type":20},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action" : "_grouponSummary.exportExcel()","type":4}
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
								<tr height="40">
							 		<th align='right'>结算公司：</th>
							 		<td>
							 			<input class="easyui-company  ipt"  name="companyName" id="companyNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json',required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:130"/>
										<input type="hidden" name="companyNo" id="companyNoId"/>
							 		</td>
							 		<th>团购活动日期：</th>
								 	<td><input class="easyui-datebox ipt" name="startDate" id="startDate" data-options="maxDate:'endDate'"/></td>
									<th>&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt" name="endDate" id="endDate" data-options="minDate:'startDate'"/></td>
								</tr>
								<tr height="40">
							 		<th>查询日期：</th>
								 	<td><input class="easyui-datebox ipt" name="ticketStartDate" id="ticketStartDate" data-options="maxDate:'ticketEndDate'"/></td>
									<th>&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt" name="ticketEndDate" id="ticketEndDate" data-options="minDate:'ticketStartDate'"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			                  	  <!--{field : 'ck',checkbox:true},-->
				                  {field : 'ticketName',title : '券名称/团购订单号',width: 150,align:'left',halign:'center'},
				                  {field : 'customerNo',title : '客户编码',width: 120,align:'left',halign:'center'},
				                  {field : 'customerName',title : '客户名称',width: 180,align:'left',halign:'center'},
				                  {field : 'sellQty',title : '购券数量',width: 80,align:'right',halign:'center'},
				                  {field : 'amount',title : '券面值',width: 80,align:'right',halign:'center'},
				                  {field : 'totalAmount',title : '券总金额',width: 80,align:'right',halign:'center'},
				                  {field : 'buyTotalAmount',title : '实收金额/预收金额',width: 120,align:'right',halign:'center'},
				                  {field : 'buyAmount',title : '实际购券单价',width: 120,align:'right',halign:'center'},
				                  {field : 'ticketAmount',title : '开票金额',width: 80,align:'right',halign:'center'},
				                  {field : 'useQty',title : '本期用券数量',width: 120,align:'right',halign:'center'},
				                  {field : 'useTicketAmount',title : '本期用券金额/本期出库金额',width: 160,align:'right',halign:'center'},
				                  {field : 'usedAmount',title : '本期实际用券金额',width: 120,align:'right',halign:'center'},
				                  {field : 'useTotalAmount',title : '累计用券金额',width: 120,align:'right',halign:'center'},
				                  {field : 'usedTotalAmount',title : '累计实际用券金额/累计出库金额',width: 200,align:'right',halign:'center'},
				                  {field : 'residueAmount',title : '剩余券面额',width: 120,align:'right',halign:'center'},
				                  {field : 'residueReciveAmount',title : '剩余实收金额',width: 120,align:'right',halign:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>