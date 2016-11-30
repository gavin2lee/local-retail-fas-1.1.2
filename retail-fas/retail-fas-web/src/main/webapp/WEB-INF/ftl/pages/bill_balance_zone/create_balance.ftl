<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>自定义结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/createBalance.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}" />
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="createBalanceForm" id="createBalanceForm" method="post">
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
									<th><span class="ui-color-red">*</span>公司：</th>
                                    <td>
                                    	<#if isHq==true>
									      	<input class="easyui-company ipt disableEdit"  name="salerName" id="salerName" 
				                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'salerNo',inputNameField:'salerName',inputWidth:160"/>
				                       		<input type="hidden" name="salerNo" id="salerNo"/>
									      </#if>
									      <#if isHq==null || isHq==''>
									      	<input class="easyui-company ipt disableEdit"  name="salerName" id="salerName" 
				                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNo',inputNameField:'salerName',inputWidth:160"/>
				                       		<input type="hidden" name="salerNo" id="salerNo"/>
									      </#if>
                                       <!--
                                    	<input class="ipt disableEdit" singleSearch="company"  name="salerName" data-options="required:true"/>
                                    	<input type="hidden" name="salerNo" />
                                    	-->
                                    </td>
                                    <th><span class="ui-color-red">*</span>客户：</th>
                                    <td><input class="ipt disableEdit" singleSearch="wholesale_zone_customer"  name="buyerName" data-options="required:true"/><input type="hidden" name="buyerNo" /></td>
                                    <th>品牌部：</th>
                                    <td><input class="ipt disableEdit" multiSearch="brandUnit"  id="multiBrandUnitName" /><input type="hidden" name="multiBrandUnitNo"/><input type="hidden" id="brandUnitNo" name="brandUnitNo"/><input type="hidden" id="brandUnitName" name="brandUnitName"/></td>
								</tr>	
								<tr>
									<th><span class="ui-color-red">*</span>币别：</th>
		                            <td><input class="ipt" combobox="currency" id="currency" name="currency"  data-options="required:true,editable:false"/></td> 
									<th><span class="ui-color-red">*</span>结算日： </th><td><input class="easyui-datebox ipt"  name="balanceDate"  id="balanceDate" defaultValue="currentDate" data-options="required:true"/></td>
									<th><span class="ui-color-red">*</span>结算期间：</th>
									<#if isHq==true>
									    <td><input class="easyui-datebox easyui-validatebox  ipt disableEdit"  id= "balanceStartDate1" name="balanceStartDate1" defaultValue="startDate" data-options="required:true, maxDate:'balanceEndDate1'" /></td>
										<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<td><input class="easyui-datebox easyui-validatebox  ipt disableEdit"  id= "balanceEndDate1" name="balanceEndDate1" defaultValue="endDate" data-options="required:true, minDate:'balanceStartDate1'"/></td>	
									</#if>
									<#if isHq==null || isHq==''>
									    <td><input class="easyui-datebox easyui-validatebox  ipt disableEdit"  id= "balanceStartDate1" name="balanceStartDate1" defaultValue="currentMonthFirstDayDate" data-options="required:true, maxDate:'balanceEndDate1'" /></td>
										<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<td><input class="easyui-datebox easyui-validatebox  ipt disableEdit"  id= "balanceEndDate1" name="balanceEndDate1" defaultValue="currentMonthLastDayDate" data-options="required:true, minDate:'balanceStartDate1'"/></td>	
									</#if>
								</tr>	
								<tr>
									<th>管理城市： </th>
									<td>
										<!--<input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNoFrom" />-->
										<input class="easyui-organ ipt" data-options="multiple:'true',inputNoField:'multiOrganNoFrom',inputNameField:'multiOrganNameFrom'"/> 
										<input type="hidden" name="organNameFrom" id="multiOrganNameFrom"/>
										<input type="hidden" name="organNoFrom" id="multiOrganNoFrom"/>
									</td>
									<th>货管单位： </th>
									<td>
										<input class="ipt" multiSearch="orderUnit"/> <input type="hidden" name="multiOrderUnitNoFrom" />
									</td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
        	   <div class="easyui-layout" data-options="fit:true,plain:true">
					<div data-options="region:'north',border:false" >
					  <@p.toolbar id="return_tool_bar" listData=[
					  		{"id":"foot_btn_load","title":"清空表单","iconCls":"icon-empty","action":"createBalance.clear()","type":0},
							{"id":"foot_btn_load","title":"加载明细","iconCls":"icon-import","action":"createBalance.loadDtl()","type":0}
						 ]/>
					</div>
					<div data-options="region:'center',border:false" >
						 <@p.datagrid id="createBalanceDG"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
			              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
				           rownumbers="true"    checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
				           columnsJsonList="[
				           		  {field : 'ck',checkbox:true,notexport:true},
				                  {field : 'sendDate', title : '单据日期', width : 100},
								  {field : 'originalBillNo', title : '单据编号', width : 150},
								  {field : 'brandUnitName', title : '品牌部', width : 100},
								  {field : 'brandName',title : '品牌',width : 120},
								  {field : 'organNameFrom', title : '管理城市',  width : 120},
			                      {field : 'orderUnitNameFrom', title : '货管单位',  width : 200,align:'left'},
			                      {field : 'oneLevelCategoryName', title : '一级大类', width : 100},		
				                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},
								  {field : 'itemCode', title : '商品编码', width : 150},
				                  {field : 'itemName', title : '商品名称', width : 200, align:'left'},
				                  {field : 'sendQty', title : '数量', width : 100},
				                  {field : 'cost', title : '销售价', width : 100, formatter: function(value,row,index){
										if(row.buyerName == '合计'){
											return '';
										}
										return value;
								  }},		
				                  {field : 'sendAmount', title : '金额', width : 100}]" 
	                 		/>
					</div>
		     	</div>
			</div>
	 	</div>
	</div>
</body>
</html>