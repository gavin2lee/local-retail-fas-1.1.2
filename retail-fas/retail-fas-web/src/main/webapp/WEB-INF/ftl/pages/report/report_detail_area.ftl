<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_detail_area.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_detail_area.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report_detail_area.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/report/export','总部地区明细对账表',{type:'detail_area'})", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value = "2"/>
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
									<th>供应商类型： </th>
									<td><input class="ipt" multiSearch="supplierGroup"/> <input type="hidden" name="multiSupplierGroupNo"></td>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
									<th>发出日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
								</tr>	
								<tr>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole/><input type="hidden" name="multiSalerNo"></td>
									<th>客户： </th>
									<td><input class="ipt" multiSearch="company"/><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/><input type="hidden" name="multiSupplierNo"></td>
								</tr>	
								<tr>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
									<th>货管单位： </th>
									<td><input class="ipt" multiSearch="orderUnit"/> <input type="hidden" name="multiOrderUnitNo"></td>
									<th>商品： </th>
									<td><input class="ipt" multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
									<input class="easyui-datebox ipt" hidden="true" defaultValue="endDate" name="queryDate" id="queryDate" />
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		        <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"  emptyMsg=""
			           columnsJsonList="[
			            				{field : 'supplierGroupName', title : '供应商类型', width : 100},
			           					{field : 'salerName', title : '公司', width : 250, align:'left'},
						                {field : 'buyerName', title : '客户', width : 250, align:'left'},
						                {field : 'itemCode', title : '商品编码', width : 120},
						                {field : 'itemName', title : '商品名称', width : 150},
						                {field : 'zoneName', title : '大区', width : 80},
						                {field : 'organName', title : '管理城市', width : 80},
						                {field : 'supplierName', title : '供应商', width : 180},
						                {field : 'brandName', title : '品牌', width : 80},
										{field : 'sendQty', title : '数量', width : 100},
										{field : 'purchasePrice', title : '采购价', width : 100},
										{field : 'materialPrice', title : '物料价', width : 100},
										{field : 'factoryPrice', title : '厂进价', width : 100},
										{field : 'headquarterAdd', title : '总部加价', width : 100},
										{field : 'headquarterCost', title : '总部成本', width : 100},
										{field : 'regionAdd', title : '地区加价', width : 100},
										{field : 'regionCost', title : '地区价', width : 100},
										{field : 'purchaseAmount', title : '采购金额', width : 100},
										{field : 'factoryAmount', title : '厂进金额', width : 100},
										{field : 'regionAmount', title : '地区额', width : 120},
										{field : 'oneLevelCategoryName', title : '一级大类', width : 100},
										{field : 'twoLevelCategoryName', title : '二级大类', width : 100},
						                {field : 'yearsName', title : '年份', width : 100},
						                {field : 'seasonName', title : '季节', width : 100}
										]" 
                 />	
			</div>
		</div>
	</div>
</body>
</html>