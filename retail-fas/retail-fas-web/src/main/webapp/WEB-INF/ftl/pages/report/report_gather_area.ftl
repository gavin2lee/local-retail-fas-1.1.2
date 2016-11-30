<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_gather_area.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_gather_area.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report_gather_area.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/report/export','总部地区汇总对账表',{type:'gather_area'})", "type":0}
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
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole/><input type="hidden" name="multiSalerNo"></td>
									<th>客户： </th>
									<td><input class="ipt" multiSearch="company"/><input type="hidden" name="multiBuyerNo"></td>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>一级大类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
								</tr>	
								<tr>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/><input type="hidden" name="multiSupplierNo"></td>
									<th>发货日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
								</tr>	
								<tr>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
									<th>货管单位： </th>
									<td><input class="ipt" multiSearch="orderUnit"/> <input type="hidden" name="multiOrderUnitNo"></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		        <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"  emptyMsg=""
			           columnsJsonList="[
			            				{field : 'salerName', title : '公司', width : 250, align:'left'},
						                {field : 'buyerName', title : '客户', width : 250, align:'left'},
						                {field : 'organName', title : '管理城市', width : 80},
						                {field : 'brandName', title : '品牌', width : 80},
						                {field : 'supplierName', title : '供应商', width : 180, align:'left'},
						                {field : 'categoryName', title : '大类', width : 100},
										{field : 'sendQty', title : '发货数量', width : 100},
										{field : 'sendAmount', title : '发货金额',  width : 120, align:'right'},
										{field : 'returnQty', title : '原残数量', width : 100},
										{field : 'returnAmount', title : '原残金额',  width : 120, align:'right'},
										{field : 'balanceQty', title : '应收数量',  width : 100},
										{field : 'balanceAmount', title : '应收金额',  width : 120, align:'right'}
										]" 
                 />	
			</div>
		</div>
	</div>
</body>
</html>