<!DOCTYPE HTML>
<html>
<head>
<title>开票基础表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/invoiceBaseReport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<input type="hidden" value="${type}" id="type">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
             {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"searchData()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"exportExcel()", "type":4}
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
						 <col width="100"/>
						 <col width="220"/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
		                 	<tr>
								 <th><span class="ui-color-red">*</span>公司名称：</th>
								<td><input class="ipt easyui-company"  data-options="required:true,multiple:true,inputWidth:150" id="companyName"/>
								<input type="hidden" name="companyNos" id="companyNo"/></td>									
							   <th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt"  data-options="inputNoField:'organNo',inputNameField:'organName',multiple:true" id="organName"/>
								        <input type="hidden" name="organNos" id="organNo"/>
								    </td>
							   <td align="right" width="80">店铺：</td>
								<td align="left" width="140"><input id="shopName" />
								<input type="hidden" name="shopNos" id="shopNo"/>
								<th>品牌：</th>
								<td>
					        		<input class="ipt easyui-brand" id="brandName" 
					        			data-options="multiple: true, inputNoField:'brandNo', inputNameField:'brandName'"/>
								  	<input type="hidden" name="brandNos" id="brandNo"/>
								</td>
							</tr>
							<tr>
								<th><span class="ui-color-red">*</span>结算月：</th>
					    		<td ><input class="easyui-validatebox easyui-datebox ipt" name="startMonth" id="createTimeStart" style="width: 60px" data-options="required:true,maxDate:'createTimeEnd',dateFmt:'yyyyMM'"/>
					    		-
								<input class="easyui-validatebox easyui-datebox ipt" name="endMonth" id="createTimeEnd" style="width: 60px" data-options="required:true,minDate:'createTimeStart',dateFmt:'yyyyMM'"/></td>
							</tr>
							
						 </tbody>
						 </table>
					</form>
				</div>
			</div>
			<!--列表-->
			<div data-options="region:'center',border:false">
				<@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" title=""
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" rownumbers="true" 
					showFooter="true"
					frozenColumns="[
						{field:'organ_name',title:'管理城市',halign:'center',align:'center',width:80},
						{field:'shop_no',title:'店铺编号',halign:'center',align:'center',width:80},
						{field:'short_name',title:'店铺名称',halign:'center',align:'left',width:150},
						{field:'balance_date',title:'结算分段时间',halign:'center',align:'center',width:100},
						{field:'rel_balance_date',title:'结算期',halign:'center',align:'center',width:100}
					]"
		        />
        	</div>
	 	</div>
	</div>
	
</body>
</html>