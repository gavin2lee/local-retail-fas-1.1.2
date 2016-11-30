<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>其他扣项</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/other_deduction/otherDeduction.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"otherDeduction.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"otherDeduction.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"otherDeduction.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"otherDeduction.del()","type":3}, 
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"otherDeduction.save()","type":7},  
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/other_deduction/export?isHq=${isHq}','其他扣项导出')","type":4},
             {"id":"btn-save","title":"更新客户余额","iconCls":"icon-save","action":"otherDeduction.distributeDeduction()","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" id="balanceType" name="balanceType" value="7">
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
									<td>
									  <#if isHq==true>
								      	<input class="ipt" multiSearch="company" data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole'"/>
										<input type="hidden" name="multiSalerNo">
								      </#if>
								      <#if isHq==null || isHq==''>
								      	<input class="ipt" multiSearch="company" data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole'"/>
										<input type="hidden" name="multiSalerNo">
		                           	  </#if>
										<!-- 
										<input class="ipt" multiSearch="company" />
										<input type="hidden" name="multiSalerNo">
										-->
									</td>
									<th>客户： </th>
									<td><input class="ipt" multiSearch="wholesale_zone_customer"  /><input type="hidden" name="multiBuyerNo"></td>
									<th>日期：</th>
								    <td><input class="easyui-datebox ipt"  name="deductionDateStart" id="deductionDateStart" data-options="maxDate:'deductionDateEnd'" /></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt"  name="deductionDateEnd" id="deductionDateEnd" data-options="minDate:'deductionDateStart'" /></td>	
								</tr>
								<tr>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNoLike" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
			<#if isHq==true>
				<#include  "/WEB-INF/ftl/pages/other_deduction/wholesale_list_hq.ftl" >
			</#if>
			<#if isHq==null || isHq==''>
				<#include  "/WEB-INF/ftl/pages/other_deduction/wholesale_list_area.ftl" >
			</#if>
	 	</div>
	</div>
</body>
</html>