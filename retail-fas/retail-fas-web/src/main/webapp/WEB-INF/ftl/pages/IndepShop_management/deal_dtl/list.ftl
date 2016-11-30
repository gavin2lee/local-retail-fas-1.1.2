<!DOCTYPE HTML>
<html>
<head>
<title>交易明细管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/credit_card_deal_dtl/creditCardDealDtl.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/cash_deal_dtl/cash_deal_dtl.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'银联刷卡交易明细'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height:73px;" >
				<@p.toolbar id="toolbar" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action" : "creditCardDealDtl.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "creditCardDealDtl.clear()", "type":0},
		             {"id":"btn-del","title":"删除","iconCls":"icon-del", "action" : "creditCardDealDtl.del()", "type":3},
		             {"id":"btn-import-main","title":"导入","iconCls":"icon-import","action":"creditCardDealDtl.importOperation()","type":6},
		             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "creditCardDealDtl.exportOperation()", "type":4}
		           ]/>
		    	<div style="border-bottom: 1px solid #dddddd;overflow: auto;padding: 5px;">
			      	<form name="searchForm" id="creditCardSearchForm" method="post">
						<table class="form-tb">
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
		                 <tbody>
							<tr height='30'>
								<th>客户账号： </th>
								<td >
									<input class="easyui-validatebox ipt" name="cardNumber" id="cardNumber"/>
								</td>
								<th>终端号：</th>
						     	<td>
						     		<input class="easyui-validatebox ipt" name="terminalNumber" id="terminalNumber"/>
						     	</td>
						     	<th>流水号：</th>
								<td>
						     		<input class="easyui-validatebox ipt" name="seqNo" id="seqNo"/>
						     	</td>
								<th>交易日期：</th>
					    		<td>
						    		<input class="easyui-datebox ipt" name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/>-
									<input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/>
								</td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- 银联刷卡交易明细数据展示-->
			<div data-options="region:'center',border:false">
		      <@p.datagrid id="creditCardDealDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30, notexport : true},
				                {field : 'id',hidden : 'true',align:'center', notexport : true},
				                {field : 'terminalNumber',title : '终端号',width : 100,align:'center'},	
								{field : 'cardNumber',title : '客户账号',width : 130,align:'center'},	
								{field : 'dealTime',title : '交易时间',width : 150,align:'center'},	
								{field : 'seqNo',title : '流水号',width : 100,align:'center'},	
								{field : 'remark',title : '摘要',width : 100,align:'center'},	
								{field : 'amount',title : '消费金额',width : 80,align:'center', exportType:'number'},
								{field : 'actualIncomeAmount',title : '实收金额',width : 80,align:'center', exportType:'number'},		
								{field : 'rebateAmount',title : '回扣费',width : 80,align:'center', exportType:'number'},
								{field : 'givenBank',title : '发卡行',width : 120,align:'left'},
								{field : 'realityDealTime',title : '实际交易时间',width : 150,align:'center'},
								{field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                {field : 'createTime',title : '建档时间',width : 150,align:'center'}		               
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
		</div>
	</div>
	<div data-options="title:'现金交易明细'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar1" listData=[
				 {"id":"btn-search1","title":"查询","iconCls":"icon-search","action" : "cashDealDtl.search()", "type":0},
	             {"id":"btn-remove1","title":"清空","iconCls":"icon-remove","action" : "cashDealDtl.clear()",  "type":0},
	             {"id":"btn-del1","title":"删除","iconCls":"icon-del","action" : "cashDealDtl.del()", "type":0},
	             {"id":"btn-import-main1","title":"导入","iconCls":"icon-import","action":"cashDealDtl.importOperation()","type":6},
	             {"id":"btn-export1","title":"导出","iconCls":"icon-export","action" : "cashDealDtl.exportOperation()", "type":4}
	           ]/>
	           <div style="border-bottom: 1px solid #dddddd;overflow: auto;padding: 5px;">
			      	<form name="searchForm" id="cashDealSearchForm" method="post">
						<table class="">
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
		                 <tbody>
							<tr height='33'>
								<td align="right">商场名称：</td>
								<td align="left"><input class="easyui-validatebox ipt easyui-mall" name="mallName" id="mallName"  data-options="inputWidth:160"/>
								<input type="hidden"  name="mallNo" id="mallNo" 	/>
						     	<td align="right">存现账号： </td>
								<td>
									<input class="ipt"  iptSearch="card"  name="cardNumber" id="cardNumber" data-options="required:true" />
								</td>
								<td align="right">存现日期：</td>
					    		<td ><input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="cashDealDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
		                  	  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
								{field : 'cardNumber',title : '存现账号',width : 150,align:'center'},	
								{field : 'depositCashTime',title : '存现日期',width : 100,align:'center'},	
								{field : 'depositAmount',title : '存现金额',width : 100,align:'right',exportType:'number'},
								{field : 'depositSite',title : '存现地点',width : 200,align:'center'},	
								{field : 'mallName',title : '商场名称',width : 200,align:'left'}
			              ]" 
				          jsonExtend='{onDblClickRow:function(rowIndex, rowData){
			           	  		 //fas_common.loadDetail(rowData);
			             }}' 
                 />
			</div>
		</div>
	</div>
</div>

</body>