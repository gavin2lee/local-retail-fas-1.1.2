<!DOCTYPE HTML>
<html>
<head>
<title>店铺日报表</title>
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/day_report/day_report.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dayReport.searchEventBtn()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"dayReport.removeEventBtn()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dayReport.exportEventBtn()", "type":4}
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
								<th><span class="ui-color-red">*</span>公司名称： </th>
								<td>
									<input class="easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />	
								</td>
								<th>店铺名称	： </th>
								<td>
									<input id="shopName" style="width: 200px"  class="easyui-shopCommon" disabled="disabled" />
									<input type="hidden" name="shopNo" id="shopNo"/>
								</td>
								<th><span class="ui-color-red">*</span>销售日期：</th>
					    		<td ><input class="easyui-datebox ipt"  name="startOutDate" id="startOutDate" data-options="required:true,maxDate:'endOutDate'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="endOutDate" id="endOutDate" data-options="required:true,minDate:'startOutDate'"/></td>
							</tr>
							<tr>
								<th>品牌：</th>
							  	<td align="left" width="140">
					        		<input class="ipt easyui-brand" name="brandName" id="brandName" 
					        			data-options="inputWidth:200, multiple: true, inputNoField:'brandNo', inputNameField:'brandName'"/>
								  	<input type="hidden" name="brandNo" id="brandNo"/>
								</td>
								<th>店铺小类：</th>
							  	<td align="left" width="140">
									<input class="easyui-retailtype ipt"  id="retailType" name="retailType" data-options="multiple:true,inputWidth:200"/>
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