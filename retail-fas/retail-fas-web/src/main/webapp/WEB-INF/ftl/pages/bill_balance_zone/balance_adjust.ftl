<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>扣项明细列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/balanceAdjust.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}" />
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
			      		<input type="hidden" name="balanceType" value="7">
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
									<td><input class="ipt readonly"   readonly="readonly" name="salerName" id="salerName"/><input type="hidden" name="salerNo" id="salerNo" /></td>
									<th>客户： </th>
									<td><input class="ipt readonly"   readonly="readonly" name="buyerName" id="buyerName"/><input type="hidden" name="buyerNo" id="buyerNo" /></td>
									<th>品牌部： </th>
									<td>
                                    	<input class="easyui-brandunit ipt" id="brandUnitName" name="brandUnitName" data-options="multiple:'true', inputNoField:'brandUnitNo', inputNameField:'brandUnitName',inputWidth:160"/>
                                    	<input type="hidden" id="brandUnitNo" name="brandUnitNo"/>
                                    </td>
									<th>一级大类： </th>
									<td><input class="ipt" singleSearch="category" name="categoryName" /><input type="hidden" name="categoryNo"/></td>
								</tr>	
								<tr>
									<th>扣项日期：</th>
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
		      <@p.datagrid id="balanceAdjustDG"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"   emptyMsg = "" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			          columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true},
			 				 {field : 'deductionDate',title:'扣项日期',width:120},
			 				 {field : 'brandName',title:'品牌',width:100},
			 				 {field : 'categoryName',title:'一级大类',width:100},
			 				 {field : 'deductionQty',title:'扣项数量',width:100},
							 {field : 'deductionAmount',title:'扣款金额',width:100},	
			 				 {field : 'remark',title:'备注',width:180},
			 				 {field : 'balanceNo',title:'结算单号',width:150}]"	
                 />
			</div>
	 	</div>
	</div>
</body>
</html>