<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>银联交易核对</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/common/shopManagement_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/credit_card_check/creditCard_check.js?version=${version}"></script>
</head>
<body class="easyui-layout">
					  
	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
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
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
		                 <tbody>
							<tr height='33'>
								<th>公司	： </th>
								<td colspan="3">
									<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />	
								</td>
								<td align="right" width="110">店铺名称：</td>
						     	<td align="left" width="250">
						     		<input class="easyui-validatebox ipt easyui-shop" name="shopName" id="shopName"  data-options="multiple:true,inputWidth:200"/>
						     		<input type="hidden"  name="shopNo" id="shopNo" />
						     	</td>
								<th>销售日期：</th>
					    		<td ><input class="easyui-validatebox easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
	    	
	    	<!--列表-->
        	<div data-options="region:'center',border:false">
                 <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
			           rownumbers="true" singleSelect="true"  
			           columnsJsonList="[
			                    {field : 't',width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
								{field : 'sellDate',title : '销售日期',width : 100,align:'center'},	
								{field : 'terminalNumber',title : '刷卡终端号',width : 100,align:'center'},	
								{field : 'mallNameTemp',title : '商场名称',width : 100,align:'left'},
								{field : 'shortNameTemp',title : '店铺名称',width : 180,align:'left'},	
								{field : 'brandEnShortName',title : '品牌名称',width : 100,align:'left'},
								{field : 'sysIncomeAmountTemp',title : '系统总收入',width : 150,align:'right',exportType:'number'},	
								{field : 'returnAmount',title : '退款金额',width : 100,align:'right',exportType:'number'},
								{field : 'bankCardActualyIncomeAmount',title : '实际进账金额',width : 200,align:'right',exportType:'number'},	
								{field : 'creditCardDiff',title : '银联刷卡差异',width : 100,align:'center',styler:changeColor,exportType:'number'}
			              ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   }
		                 }'
		                 loadSubGridUrl="/credit_card_check/list_child"
		             	 subPagination="false"
		             	 subGridColumnsJsonList="[
					      	{field : 'mallName', title : '商场名称', width : 90, align : 'left', seq : 7},
		             	  	{field : 'shopNo', title : '店铺编码', width : 120, align : 'center', seq : 3},
			                {field : 'shortName', title : '店铺名称', width : 140, align : 'left', seq : 8},
					      	{field : 'brandEnShortName', title : '品牌名称', width : 120, align : 'left', seq : 4},
			                {field : 'sysIncomeAmount', title : '系统收入', width : 140, align : 'right', seq : 8,exportType:'number'}
		             	]" 
				   />
			</div>
	    	
    	</div>
    </div>	

</body>