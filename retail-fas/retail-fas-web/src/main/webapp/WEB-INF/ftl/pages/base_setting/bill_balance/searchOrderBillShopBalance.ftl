<!DOCTYPE html>
<head>
    <title>开票申请的内购订单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/searchOrderBillBalance.js?version=${version}"></script>
    
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"searchOrderBillBalance.dosearchOrderBillBalance()","type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"searchOrderBillBalance.clearCondition()","type":0},
	             {"id":"btn-sure","title":"确定","iconCls":"icon-ok", "type":0},
	             {"id":"btn-cancel","title":"关闭","iconCls":"icon-close", "type":0}
	           ]
			/>
	
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="searchForm" id="searchForm" method="post">
		       		 	<table class="form-tb">
		       		 		 <col width="100" />
						    <col />
						    <col width="120" />
						    <col />
						    <tbody>
		       		 		<tr>
						 	<th>结算公司：</th>
								<td>
						     		<input class="easyui-company ipt" name="companyName" id="companyName"
						     		data-options="required:true,inputNoField:'searchCompanyNo', inputNameField:'companyName',inputWidth:150"/>
						     		<input type="hidden"  name="searchCompanyNo" id="searchCompanyNo" />
								</td>
					    	<th>日期：</th>
				       		 	<td>
				       		 	<input class="easyui-datebox ipt"  name="startDate" id="startDate" data-options="required:true,maxDate:'endDate'"//>
				       		    &nbsp;&nbsp;   至：  &nbsp;&nbsp;
				       		    </td>
			       		   		<td>
			       		 		<input class="easyui-datebox ipt"  name="endDate" id="endDate" data-options="required:true,minDate:'startDate'"/>
			       		  		</td>
			       		  		<td/>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',border:false">
	        <table id="searchOrderBillBalanceDG"></table>  
			</div>
		 </div>
	</div>
	
</body>
</html>