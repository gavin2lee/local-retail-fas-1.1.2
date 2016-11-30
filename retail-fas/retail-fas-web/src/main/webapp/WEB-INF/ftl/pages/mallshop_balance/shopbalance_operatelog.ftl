<!DOCTYPE html>
<head>
    <title>mallshop结算单log</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/ShopBalanceOperatelog.js?version=${version}"></script>
    
     <link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
     <script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
     <script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
     
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
             	{"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0}
           ]/>
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
						    <col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
						    <tr>
					    <th>公司：</th>
						  	<td align="left" width="140">
							  	<input class="ipt easyui-company" data-options="multiple:true,inputNoField:'companyNo_',inputNameField:'companyName_'" name="companyName" id="companyName_"/>
								<input type="hidden" name="companyNo" id="companyNo_"/>
							</td>					
						</td>									
					     <th>店铺： </th>
							<td>
								<input class="easyui-shopCommon" id="shopName" />
							    <input type="hidden" name="shopNos" id="shopNo" />
						    </td>
					    <th>结算月：</th>
					       <td><!--<input class="easyui-datebox ipt" name="queryMonthCondition" id="queryMonthCondition" style="width:90px;" data-options="dateFmt:'yyyyMM'"/>-->  
					        <input class="easyui-datebox" name="startMonth" id="startMonth" style="width:65px;"  data-options="dateFmt:'yyyyMM'" />
				            <input class="easyui-datebox" name="endMonth" id="endMonth" style="width:65px;"  data-options="dateFmt:'yyyyMM'" /></td>							
					    </tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.subdatagrid id="operateLogDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="false" 
			               checkOnSelect="false"
				           rownumbers="true" singleSelect="true" 
				           columnsJsonList="[
				                  {field : 'shopNo',title : '店铺编码',width : 70,align:'left'},
				                  {field : 'shortName',title : '店铺名称',width : 100,align:'left'},
				                  {field : 'month',title : '结算月',width : 70,align:'center'},
				                  {field : 'balanceStartDate',title : '结算期起',width : 80,align:'left'},
				                  {field : 'balanceEndDate',title : '结算期止',width : 80,align:'left',notexport:true},
				                  {field : 'balanceNo',title : '结算单编号',width : 160,align:'left'},
				                  {field : 'operateNo',title : '操作类型',width : 70,align:'left',formatter : dialog.formatOperate}, 
				                  {field : 'createUser',title : '操作人',width : 100,align:'center'},
				                  {field : 'createTime',title : '操作时间',width : 140,align:'left'},
				                  {field : 'companyNo',hidden:true, title : '公司编码', width : 160,align:'left'},
				                  {field : 'companyName', title : '公司', width : 200, align:'left'},
			                 ]" 
			       />
			</div>
		 </div>
	</div>
</body>
</html>