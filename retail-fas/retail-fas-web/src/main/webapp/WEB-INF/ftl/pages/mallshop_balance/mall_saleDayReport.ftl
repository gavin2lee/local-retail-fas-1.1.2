<!DOCTYPE HTML>
<html>
<head>
<title>商场店日报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/mallStoreSaleDayReport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<input type="hidden" value="${type}" id="type">
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
								<td><input class="ipt easyui-company"  data-options="required:true" name="companyName" id="companyName"/>
								<input type="hidden" name="companyNo" id="companyNo"/></td>									
							   <th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt"  data-options="inputNoField:'organNoTemp',inputNameField:'organName',multiple:true" name="organName" id="organName"/>
								        <input type="hidden" name="organNoTemp" id="organNoTemp"/>
								    </td>
							   <td align="right" width="80">店铺：</td>
								<td align="left" width="140"><input id="shopName" />
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
					    		<td ><input class="easyui-validatebox easyui-datebox ipt" name="startOutDate" id="createTimeStart" style="width: 90px" data-options="required:true,maxDate:'createTimeEnd'"/>
					    		-
								<input class="easyui-validatebox easyui-datebox ipt" name="endOutDate" id="createTimeEnd" style="width: 90px" data-options="required:true,minDate:'createTimeStart'"/></td>
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
				<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false">
				
				</div>
        	</div>
	 	</div>
	</div>
	
</body>
</html>