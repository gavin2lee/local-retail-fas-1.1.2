<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_inventory.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_inventory.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report_inventory.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/report/export','总部进销存报表',{type:'inventory'})", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
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
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole/> <input type="hidden" name="multiCompanyNo"></td>
								</tr>
								<tr>
								    <th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/> <input type="hidden" name="multiSupplierNo"></td>
									<th>商品： </th>
									<td><input class="ipt" multiSearch="item"/> <input type="hidden" name="multiItemCode"></td>
									<th>发货日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
									
								</tr>	
								<tr>
									<th>合计拆单数据：</th>
									<td><input type="checkbox" name="isSplit" id="isSplit" value="true"/></td>
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
			           				    {field : 'supplierGroupName', title : '供应商类型', width : 80},
			           					{field : 'brandName', title : '品牌', width : 80},
						                {field : 'categoryName', title : '大类', width : 80},
						                {field : 'companyName', title : '公司', width : 180, align:'left'},
			           					{field : 'storeName', title : '仓库', width : 120, align:'left'},
						                {field : 'itemCode', title : '商品编码', width : 150, align:'left'},
						                {field : 'itemName', title : '商品名称', width : 150},
						                {field : 'supplierName', title : '供应商', width : 180},
						                {field : 'purchasePrice', title : '采购价', width : 100},
						                {field : 'materialPrice', title : '物料价', width : 100},
						                {field : 'factoryPrice', title : '厂进价', width : 100},
										{field : 'startDiffQty', title : '期初数量', width : 100},
										{field : 'startDiffAmount', title : '期初金额', width : 120},
										{field : 'currentReceiveQty', title : '本期采购数量', width : 100},
										{field : 'currentReceiveAmount', title : '本期采购金额', width : 120},
										{field : 'currentSendQty', title : '本期销售数量', width : 100},
										{field : 'currentSendAmount', title : '本期销售金额', width : 120},
										{field : 'endDiffQty', title : '期末数量', width : 100},
										{field : 'endDiffAmount', title : '期末金额', width : 120}]" 
                 />	
			</div>
		</div>
	</div>
</body>
</html>