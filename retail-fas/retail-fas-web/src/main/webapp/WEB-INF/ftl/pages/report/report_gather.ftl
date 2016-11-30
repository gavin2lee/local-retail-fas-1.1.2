<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_gather.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_gather.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report_gather.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/report/export','总部厂商汇总对账表',{type:'gather'})", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
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
							<tbody>
								<tr>
									<th>供应商类型： </th>
									<td><input class="ipt" multiSearch="supplierGroup"/> <input type="hidden" name="multiSupplierGroupNo"></td>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
									<th>发出日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
								</tr>	
								<tr>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/><input type="hidden" name="multiSalerNo"></td>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>结算公司： </th>
									<td><input class="ipt" multiSearch="company" /><input type="hidden" name="multiBuyerNo"></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		        <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"  emptyMsg=""
			           columnsJsonList="[{field : 'supplierGroupName', title : '供应商类型', width : 80},
			           					{field : 'salerName', title : '供应商', width : 250, align:'left'},
			           					{field : 'buyerName', title : '结算公司', width : 250, align:'left'},
						                {field : 'brandName', title : '品牌', width : 80},
						                {field : 'oneLevelCategoryName', title : '一级大类', width : 80},
						                {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
						                {field : 'beforeMonthBalanceQty', title : '上月未结数量', width : 100},
						                {field : 'beforeMonthBalanceAmount', title : '上月未结金额', width : 100, align:'right', 
						                	formatter: function(value,row,index){
												return report_gather.linkFormat(value, row,'beforeBalancePanel','beforeBalanceDataGrid','余额信息');
											}
										},
										{field : 'currentMonthPaymentQty', title : '本月付款数量', width : 100},
						                {field : 'currentMonthPaymentAmount', title : '本月付款金额', width : 100, align:'right', 
						                	formatter: function(value,row,index){
												return report_gather.linkFormat(value, row,'currentPaymentPanel','currentPaymentDataGrid','付款信息');
											}
										},
										{field : 'currentMonthStartQty', title : '本月初余数', width : 100, align:'right'},
						                {field : 'currentMonthStartAmount', title : '本月初余额', width : 100, align:'right'},
										{field : 'sendQty', title : '进货数量', width : 100},
										{field : 'sendAmount', title : '进货金额',  width : 100, align:'right'},
										{field : 'returnQty', title : '退货数量', width : 100},
										{field : 'returnAmount', title : '退货金额',  width : 100, align:'right'},
										{field : 'customReturnQty', title : '客残数量', width : 100},
										{field : 'customReturnAmount', title : '客残金额',  width : 100, align:'right'},
										{field : 'deductionAmount', title : '其他扣项',  width : 100, align:'right', 
						                	formatter: function(value,row,index){
												return report_gather.linkFormat(value, row,'currentDeductionPanel','currentDeductionDataGrid','扣项信息');
											}
										},
										{field : 'balanceQty', title : '应付数量',  width : 100},
										{field : 'balanceAmount', title : '应付金额',  width : 100, align:'right'},
										{field : 'currentMonthEndQty', title : '本月末余数', width : 100, align:'right'},
										{field : 'currentMonthEndAmount', title : '本月末余额', width : 100, align:'right'}]" 
                 />	
			</div>
		</div>
	</div>
	
	<div id="beforeBalancePanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="beforeBalanceDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'buyerName', title : '公司', width : 200},
                     {field : 'salerName', title : '供应商', width : 200},
	                 {field : 'brandName', title : '品牌', width : 80},
                     {field : 'categoryName', title : '大类', width : 80},
	           	     {field : 'sendDateStart', title : '开始时间', width : 80},
                     {field : 'sendDateEnd', title : '结束时间', width : 80},
                     {field : 'sendQty', title : '进货数量', width : 80},	
	 				 {field : 'sendAmount',title:'进货金额',width:100,align:'right'},
	 				 {field : 'returnQty',title:'退货数量',width:80},
	 				 {field : 'returnAmount',title:'退货金额',width:100,align:'right'},
					 {field : 'customReturnQty',title:'客残数量',width:80},
	 				 {field : 'customReturnAmount',title:'客残金额',width:100,align:'right'},
	 				 {field : 'deductionAmount',title:'其他扣项',width:100,align:'right'},
					 {field : 'balanceQty',title:'应付数量',width:80},
					 {field : 'balanceAmount',title:'应付金额',width:100,align:'right'}]"	
         />
     </div>
     <div id="currentPaymentPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="currentPaymentDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	           		 {field : 'buyerName', title : '公司', width : 200},
                     {field : 'salerName', title : '供应商', width : 200},
                     {field : 'billNo', title : '单据编号', width : 150},
                     {field : 'billDate', title : '单据日期', width : 100},
                     {field : 'statusName', title : '单据状态', width : 100},
                     {field : 'refBillNo', title : '发票编号', width : 150},
                     {field : 'refQty', title : '发票数量', width : 100},
                     {field : 'refAmount', title : '发票金额', width : 100,align:'right'},	
	 				 {field : 'qty',title:'付款数量',width:100},
	 				 {field : 'amount',title:'付款金额',width:100,align:'right'},
	 				 {field : 'remark',title:'备注',width:200}]"	
         />
     </div>
     <div id="currentDeductionPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="currentDeductionDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'buyerName', title : '公司', width : 200},
                     {field : 'salerName', title : '供应商', width : 200},
                     {field : 'brandName', title : '品牌', width : 100},
                     {field : 'categoryName', title : '大类', width : 100},
                     {field : 'deductionDate', title : '扣项日期', width : 100},
                     {field : 'deductionAmount', title : '其他扣项', width : 100,align:'right'},
	 				 {field : 'remark',title:'备注',width:200}]"	
         />
     </div>
</body>
</html>