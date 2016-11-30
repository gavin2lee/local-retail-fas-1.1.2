<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_detail.js?version=${version}"/>"></script>
<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_detail.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report_detail.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"report_detail.doExport()", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value = "1"/>
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
									<th>品牌部： </th>
									<td><input class="ipt" multiSearch="brandUnit"/><input type="hidden" name="multiBrandUnitNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier" data-options="required:true"/><input type="hidden" name="multiSalerNo"></td>
									<th>发出日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
								</tr>	
								<tr>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
									<th>大区： </th>
									<td><input class="ipt" multiSearch="zone"/> <input type="hidden" name="multiZoneNo"></td>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
									<th>货管单位： </th>
									<td><input class="ipt" multiSearch="orderUnit"/> <input type="hidden" name="multiOrderUnitNo"></td>
								</tr>	
								<tr>
								    <th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>商品： </th>
									<td><input id="itemNo"/><input type="hidden" id="itemSql" name="itemSql"></td>
									<th>性别： </th>
									<td><input class="ipt" multiSearch="gender"/><input type="hidden" name="multiGenderNo"></td>
									<th>二级大类： </th>
									<td><input class="ipt" multiSearch="twoLevelCategory"/><input type="hidden" name="multiTwoCategoryNo"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
            	 	<div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
            	 		<div title="明细核对" data-options="selected : true,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="10"
					           rownumbers="true"  emptyMsg=""
					           columnsJsonList="[
								                {field : 'salerName', title : '供应商', width : 250, align:'left'},
								                {field : 'brandName', title : '品牌', width : 100},
								                {field : 'oneLevelCategoryName', title : '大类', width : 100},
												{field : 'itemCode', title : '商品编码', width : 180},
												{field : 'itemName', title : '商品名称',  width : 180, align:'left'},
												{field : 'purchasePrice', title : '采购价',  width : 80, align:'right'},
												{field : 'factoryPrice', title : '厂进价',  width : 80, align:'right'}]" 
		                 	/>	
            	 		</div>
            	 		<div title="汇总核对" data-options="selected : false,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid1"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					           columnsJsonList="[{field : 'salerName', title : '供应商', width : 250, align:'left'},
								                {field : 'brandName', title : '品牌', width : 100},
								                {field : 'oneLevelCategoryName', title : '大类', width : 100},
								                {field : 'twoLevelCategoryName', title : '二级大类', width : 100},
								                {field : 'categoryName', title : '三级大类', width : 100},
												{field : 'sendQty', title : '数量',  width : 100, align:'right'},
												{field : 'sendAmount', title : '金额',  width : 100, align:'right'},
												{field : 'cost', title : '平均单价',  width : 100, align:'right'}]" 
		                 	/>	
            	 		</div>
            	 		<div title="按天显示" data-options="selected : true,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid2"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					           columnsJsonList="[
								                {field : 'salerName', title : '供应商', width : 250, align:'left'},
								                {field : 'brandName', title : '品牌', width : 100},
								                {field : 'oneLevelCategoryName', title : '大类', width : 100},
												{field : 'itemCode', title : '商品编码', width : 180},
												{field : 'itemName', title : '商品名称',  width : 180, align:'left'},
												{field : 'sendDate', title : '发出日',  width : 120},
												{field : 'purchasePrice', title : '采购价',  width : 80, align:'right'},
												{field : 'factoryPrice', title : '厂进价',  width : 80, align:'right'}]" 
		                 	/>	
            	 		</div>
            		</div>
			</div>
		</div>
	</div>
</body>
</html>