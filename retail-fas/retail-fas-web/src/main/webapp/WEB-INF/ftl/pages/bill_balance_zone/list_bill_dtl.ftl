<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>批发销售明细表22</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/BillDtl.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
	     	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"extendClear()","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dataGridDiv','/bill_balance_zone/export_sale_dtl?isHq=${isHq}','批发出库明细导出',{type:'enter'})","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" id="balanceType" value="7"/>
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
									<!--<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiSalerNo"></td>-->
									<td>
									  <#if isHq==true>
								      	<input class="ipt" multiSearch="company" data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole'"/>
										<input type="hidden" name="multiSalerNo">
								      </#if>
								      <#if isHq==null || isHq==''>
								      	<input class="ipt" multiSearch="company" data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole'"/>
										<input type="hidden" name="multiSalerNo">
		                           	  </#if>
		                           	</td>
									<th>客户： </th>
									<td><input class="ipt" multiSearch="wholesale_zone_customer"  /><input type="hidden" name="multiBuyerNo"></td>
									<th>品牌： </th>
									<td>
										<input class="easyui-brand ipt" name="multiBrandName" id="multiBrandName" data-options="multiple:true,inputNoField:'multiBrandNo', inputNameField:'multiBrandName',inputWidth:130"/>
										<input type="hidden" name="multiBrandNo" id="multiBrandNo"/>
										<!--<input class="ipt" multiSearch="brand"  /><input type="hidden" name="multiBrandNo">-->
									</td>
									<th>一级大类： </th>
									<td><input class="ipt" multiSearch="category" /><input type="hidden" name="multiCategoryNo"></td>
								</tr>	
								<tr>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNoFrom"></td>
									<th>商品编码： </th><td><input class="easyui-itemCommon" id="itemName" /><input type="hidden" name="itemSql" id="itemNo"/></td>
									<th>业务类型： </th>
									<td><input class="ipt" name="bizType" id="bizType"/></td>
									<th>解冻状态： </th>
									<td><input class="ipt" name="unfrozenStatus" id="unfrozenStatus"/></td>
								</tr>	
								<tr>
									<th>单据日期：</th>
						    		<td ><input class="easyui-datebox ipt"  name="balanceStartDateN" id="balanceStartDateN" defaultValue="startDate" data-options="maxDate:'balanceEndDateN'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt"  name="balanceEndDateN" id="balanceEndDateN" defaultValue="endDate"  data-options="minDate:'balanceStartDateN'"/></td>
									<th>单据编号： </th><td><input class="ipt"  name="originalBillNo"  /></td>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNo"/></td>
								</tr>
								<tr>
									<th>退残日期：</th>
						    		<td ><input class="easyui-datebox ipt"  name="returnStartDate" id="returnStartDate" data-options="maxDate:'returnEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt"  name="returnEndDate" id="returnEndDate"  data-options="minDate:'returnStartDate'"/></td>
									<th>解冻日期：</th>
						    		<td ><input class="easyui-datebox ipt"  name="unfrozenStartDate" id="unfrozenStartDate" data-options="maxDate:'unfrozenEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt"  name="unfrozenEndDate" id="unfrozenEndDate"  data-options="minDate:'unfrozenStartDate'"/></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<#if isHq==true>
				<#include  "/WEB-INF/ftl/pages/bill_balance_zone/list_bill_dtl_hq.ftl" >
			</#if>
			<#if isHq==null || isHq==''>
				<#include  "/WEB-INF/ftl/pages/bill_balance_zone/list_bill_dtl_area.ftl" >
			</#if>
	 	</div>
	</div>
</body>
</html>