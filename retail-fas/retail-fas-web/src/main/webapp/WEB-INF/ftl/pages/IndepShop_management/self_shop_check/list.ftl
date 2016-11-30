<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>交易核对管理</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/credit_card_check/creditCard_check.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" >
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"check.searchEventBtn()" ,"type":0},
			 {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"check.exportEventBtn()" ,"type":4}
           ]/>
  	</div>
  	
  	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
    		<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
					<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
		                 <tbody>
							<tr>
								<th><span class="ui-color-red">*</span>公司	： </th>
								<td colspan="3">
									<input class="ipt easyui-company" name="companyName" id="companyName" 
							 		data-options="required:true,inputWidth:160,inputNoField:'companyNo',inputNameField:'companyName'"/>
			      					<input type="hidden" name="companyNo" id="companyNo"/>	
								</td>
								<th align="right">店铺名称：</th>
						     	<td align="left">
						     		<input id="shopName" style="width: 180px" class="easyui-shopCommon" disabled="disabled" data-options=""/>
									<input type="hidden" name="shopNo" id="shopNo"/>
						     	</td>
						     	<th align="right">存现账号：</th>
								<td>
						     		<input class="easyui-validatebox ipt" name="account" id="account"/>
						     	</td>
								<th><span class="ui-color-red">*</span>日期：</th>
					    		<td ><input class="easyui-validatebox easyui-datebox ipt"  name="startOutDate" id="startOutDate" data-options="required:true,maxDate:'endOutDate'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="endOutDate" id="endOutDate" data-options="required:true,minDate:'startOutDate'"/></td>
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