<!DOCTYPE HTML>
<html>
<head>
<title>店铺月报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/saleMonthReport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
             {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()", "type":4}
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
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <tbody>
		                 	<tr>
								 <th><span class="ui-color-red">*</span>公司名称：</th>
								<td><input class="ipt easyui-company"  data-options="required:true" name="companyName" id="companyName"/>
								<input type="hidden" name="companyNo" id="companyNo"/></td>									
							   <th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt"  data-options="inputNoField:'organNoTemp',inputNameField:'organName',multiple:true" name="organName" id="organName"/>
								        <input type="hidden" name="organNoTemp" id="organNoTemp"/>
								    </td>
							   <td align="right" width="80">店铺：</td>
								<td>
									<input id="shopName" class="easyui-shopCommon" disabled="disabled" />
									<input type="hidden" name="shopNo" id="shopNo"/>
								</td>
								<input type="hidden" name="shopNoTemp" id="shopNoTemp"/>
								<th>品牌：</th>
								<td>
					        		<input class="ipt easyui-brand" name="brandNames" id="brandNames_" 
					        			data-options="multiple: true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
								  	<input type="hidden" name="brandNos" id="brandNos_"/>
								</td>
							</tr>
							<tr>
								<th><span class="ui-color-red">*</span>销售日期：</th>
					    		<td ><input class="easyui-validatebox easyui-datebox ipt" style="width:110px" name="startOutDate" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/>
					    		-
								<input class="easyui-validatebox easyui-datebox ipt" style="width:110px" name="endOutDate" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
								<th>结算期：</th>
					    		<td ><input class="easyui-validatebox easyui-datebox ipt" style="width:110px" name="balanceStartDate" id="balanceStartDate" data-options="maxDate:'balanceEndDate'"/>
					    		-
								<input class="easyui-validatebox easyui-datebox ipt" style="width:110px" name="balanceEndDate" id="balanceEndDate" data-options="minDate:'balanceStartDate'"/>
								</td>
								<th>
									<input type="checkbox" name="isCount" id="isCount" value="1"/>&nbsp;&nbsp;&nbsp; <br>
								<td>						
									<label for="isCount">显示小计</label><br>
								</td>
							</tr>
							
						 </tbody>
						 </table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv_month"  loadUrl="" saveUrl=""   defaultColumn="" title=""
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" rownumbers="true" 
					showFooter="true"
					rowStyler="function(index,row){
							     if (row.shop_name.indexOf('小计') != -1){
							         return 'background-color:#FFFFE0;';
							     }
							  }"
					frozenColumns="[
						{field:'shop_no',title:'店铺编码',width:80,sortable:true,align:'center'},
						{field:'shop_name',title:'店铺名称',width:150,sortable:true,align:'left',halign:'center'},
						{field : 'year',title : '年',width : 40,align:'center'},
						{field : 'month',title : '月',width : 30,align:'center'},
						{field : 'balance_date',title : '结算期',width : 150,align:'center'},
						{field:'brand_no',title:'品牌编号',width:80,sortable:true,hidden:true},
						{field:'brand_name',title:'品牌名称',width:80,sortable:true},
						{field:'total',title:'销售金额',width:80,align:'right',halign:'center',exportType:'number'},
						{field : 'order_amount',title : '订单金额',width : 80,align:'right',halign:'center',exportType:'number'},
						{field : 'return_amount',title : '退款额',width : 80,align:'right',halign:'center',exportType:'number'}
					]"
		        />
			</div>
	 	</div>
	</div>
	
</body>
</html>