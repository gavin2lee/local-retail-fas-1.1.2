<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区员购销售表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasEditorController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/member_order_summary/OrderDtlSummary.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action" : "_orderDtlSummary.summarySearch()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "_orderDtlSummary.clear()", "type":0},
             <!--{"id":"btn-confirm","title":"财务确认","iconCls":"icon-ok", "action" : "_orderDtlSummary.financeConfirm()", "type":0},
             {"id":"btn-anti-confirm","title":"财务反确认","iconCls":"icon-remove", "action" : "_orderDtlSummary.financeAntiConfirm()", "type":0},-->
           	 {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "_orderDtlSummary.exportShopExcel()", "type":0}
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
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>结算公司： </th>
									<td align="left" width="140">
										<input class="easyui-company  ipt"  name="companyName" id="companyNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json',required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:140"/>
										<input type="hidden" name="companyNo" id="companyNoId"/>
										<!--<input class="ipt" style="width:105px;" iptSearch="company"  name="companyName" id="companyName" />
										<input type="hidden" name="companyNo" id="companyNo"/>-->
									</td>	
									<th>日期： </th>
									<td><input class="easyui-datebox ipt"  name="startDate" id="startDate" data-options="maxDate:'endDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;---&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt"  name="endDate" id="endDate" data-options="minDate:'startDate'"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" showFooter="true"
			           columnsJsonList="[
			           		  {field : 't', checkbox:true, width : 30, notexport:true},
			           		  {field : 'shopNo', title : '店铺编码', width : 100, align : 'left'},
			                  {field : 'shopName', title : '店铺名称', width : 160, align : 'left'},
			                  {field : 'totalQty', title : '数量', width : 90, align : 'left'},
			                  {field : 'tagPriceAmount', title : '牌价额', width : 90, align : 'left'},
			                  {field : 'salePriceAmount', title : '现价额', width : 90, align : 'left'},
			                  {field : 'reducePriceAmount', title : '减价额', width : 90, align : 'left'},
			                  {field : 'settleAmount',title : '结算额',width : 90, align : 'left'},
			                  {field : 'allAmount',title : '总金额',width : 140, align : 'left'},
		  			  		  {field : 'couponAmount',title : '现金券',width : 90, align : 'left'},
				  			  {field : 'cashAmount',title : '现金',width : 90, align : 'left'},
				  			  <!--{field : 'financeConfirmFlag',title : '财务确认',width : 90, align : 'left', formatter : _orderDtlSummary.formatFinanceConfirm},-->
				  			  {field : 'invoiceNo',title : '发票号',width : 120, align : 'left', editor:{type:'validatebox', options:{}}},
				  			  {field : 'invoiceAmount',title : '发票金额',width : 90, align : 'left', editor:{type:'validatebox', options:{}}}
			           ]"
			           <!--jsonExtend='{
                           onDblClickRow:function(rowIndex, rowData){
		                	 _orderDtlSummary.edit(rowIndex, rowData);
		               }}'-->
                 />
			</div>
	 	</div>
	</div>
</body>
</html>