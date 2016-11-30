<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>商品价格查询(财务)</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/commodityPriceInquiry.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()", "type":0}
           ]
		/>
	</div>

	<div data-options="region:'center',border:false">
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
						 <col width="120"/>
						 <col/>
		                 <tbody>
						 	<tr height='40'>
						 		<th align='right'>商品编码：</th>
							 	<td>
							 		<input class="easyui-itemCommon" id="itemName" />
									<input type="hidden" name="itemSql" id="itemNo"/>
							  	</td>
							 	<th align='right'>供应商名称：</th>
						 		<td>
							 		<input class="ipt easyui-supplier" name="itemName" id="supplierNameCondition"
							 			 data-options="inputWidth:160,inputNoField:'supplierNoCondition', inputNameField:'supplierNameCondition'"/>
							  		<input type="hidden" name="supplierNo" id="supplierNoCondition"/>
							  	</td>
							  	<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
								 	<td>
								 		<input class="easyui-validatebox ipt easyui-brand" name="brandName" id="brandNameCondition" 
								 			data-options="inputWidth:160,inputNoField:'brandNoCondition', inputNameField:'brandNameCondition'" />
							  			<input type="hidden" name="brandNo" id="brandNoCondition" />
							  		</td>
							 	<th>生效时间：</th>
								<td><input class="easyui-datebox ipt" name="effectiveDateEnd" id="effectiveDateEnd" data-options="required:true"/></td>
							</tr>
							
							<tr>
								<th>牌价为0：</th>
								<td><input type="checkBox" name="isTrue"></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
			                  	  {field : 'id',hidden:true,notexport:true},
				                  {field : 'itemCode',title : '商品编码',width: 150,align:'left',halign:'center'},
				                  {field : 'itemName',title : '商品名称',width: 150,align:'left',halign:'center'},
				                  {field : 'supplierNo',title : '供应商编码',width: 120,align:'left',halign:'center'},
				                  {field : 'supplierName',title : '供应商名称',width: 180,align:'left',halign:'center'},
				                  {field : 'brandName',title : '品牌名称',width: 120,align:'left',halign:'center'},
				                  {field : 'suggestTagPrice',title : '建议牌价',width: 100,align:'right',halign:'center',exportType:'number'},
				                  {field : 'tagPrice',title : '全国统一牌价',width: 100,align:'right',halign:'center',exportType:'number'},
				                  {field : 'purchasePrice',title : '采购价',width: 100,align:'right',halign:'center',exportType:'number'},
				                  {field : 'materialPrice',title : '物料价',width: 100,align:'right',halign:'center',exportType:'number'},
				                  {field : 'factoryPrice',title : '厂进价',width: 100,align:'right',halign:'center',exportType:'number'},
				                  {field : 'headquarterAddPrice',title : '总部加价',width: 100,align:'right',halign:'center',exportType:'number',
				                 	formatter:function(value,row,index){
				                 		return (row.headquarterCost-row.factoryPrice).toFixed(2);
				                 	}
				                 	},
				                  {field : 'headquarterCost',title : '总部成本',width: 100,align:'right',halign:'center',exportType:'number'}
			              ]" 
				          jsonExtend='{}'
				          loadSubGridUrl="/region_cost_maintain/zoneRegionCostlist.json"
				          subPagination="false"
				          subGridColumnsJsonList="[
				          		{field:'zoneName',title:'地区',width:80},
								{field:'regionCost',title:'地区价',width:80},
								{field:'addRuleNo',title:'地区加价',width:80}]"
                 />
			</div>
	 	</div>
	</div>
</body>
</html>