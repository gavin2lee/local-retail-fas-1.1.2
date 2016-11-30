<!DOCTYPE html>
<head>
    <title>NC库存商品导入</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/nc_finished_goods/ncFinishedGoods.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
	
	<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
	<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
	<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "ncFinishedGoods.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "ncFinishedGoods.clear()","type":0},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"ncFinishedGoods.doImport()","type":0},
	             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"ncFinishedGoods.deleteDate()","type":0}
	             
	           ]
			/>
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
						 <tbody>
	       		 		   <tr>
								<th>公司： </th>
								<td>
									<input class="easyui-company ipt" id="companyName" data-options="multiple:true,inputWidth:130"/>
								    <input type="hidden" name="companyNos" id="companyNo"/>
								</td>									
								<th>品牌部： </th>
								<td>
									<input class="easyui-brandunit ipt" id="brandName" data-options="multiple:true,inputWidth:130"/>
									<input type="hidden" name="brandUnitNos" id="brandUnitNo"/>
								</td>		
								<th>年份：</th>
						 		<td><input class="ipt" name="year" id="yearCondition" /></td>
						 		<th>月份：</th>
						 		<td><input class="ipt" name="month" id="monthCondition"/></td>			
	       		 			</tr>
	       		 		 </tbody>
	       		 	</table>
				</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="ncFinishedGoodsDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			           isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		               checkOnSelect="true" rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			           		  {field : 't',checkbox:true,width : 30,notexport:true},
				              {field : 'id',hidden : 'true',align:'center',notexport:true},
			                  {field : 'companyName', title : '公司名称', align:'left',width : 150 },
			                  {field : 'brandUnitName', title : '品牌部名称', align:'left',width : 100},
							  {field : 'categoryName',title : '大类',width : 90,align:'left'},
			                  {field : 'year',title : '年',width : 100,align:'left'},
			                  {field : 'month',title : '月',width : 100,align:'left'},
			                  {field : 'direction',title : '方向',width : 100,align:'left',formatter:rowDirFormatter},
			                  {field : 'qty',title : '数量',width : 100,align:'center'},
			                  {field : 'amount',title : '金额',width : 150,align:'center'}
			          ]" 
		         	/>
			</div>
		 </div>
	</div>
</body>
</html>