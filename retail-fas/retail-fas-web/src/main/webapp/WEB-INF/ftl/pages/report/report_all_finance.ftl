<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_all_finance.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"report.doExport()", "type":0}
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
									<th>品牌部： </th>
									<td><input class="ipt" multiSearch="brandUnit"/><input type="hidden" name="multiBrandUnitNo"></td>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
								</tr>	
								<tr>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/><input type="hidden" name="multiSupplierNo"></td>
									<th>结算公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole/><input type="hidden" name="multiHqCompanyNo"></td>
									<th>地区公司： </th>
									<td><input class="ipt" multiSearch="company"/><input type="hidden" name="multiAreaCompanyNo"></td>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
								</tr>
								<tr>
									<th>发出日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
									<th>业务类型：</th>
						    		<td><input class="ipt"  name="businessType" combobox="businessType" /></td>
						    		<th>商品： </th>
									<td><input class="ipt" multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		        <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="10"
			           rownumbers="true"  emptyMsg=""
			           columnsJsonList="[
			            				{field : 'supplierGroupName', title : '供应商类型', width : 80},
			            				{field : 'orderFlagName', title : '业务类型', width : 80},
			            				{field : 'brandUnitName', title : '品牌部', width : 80},
			           					{field : 'brandName', title : '品牌', width : 80},
						                {field : 'categoryName', title : '大类', width : 80},
						                {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
			           					{field : 'supplierName', title : '供应商', width : 250, align:'left'},
			           					{field : 'itemSupplierName', title : '对应供应商', width : 250, align:'left'},
			           					{field : 'hqCompanyName', title : '结算公司', width : 250, align:'left'},
						                {field : 'areaCompanyName', title : '地区公司', width : 250, align:'left'},
						                {field : 'organName', title : '管理城市', width : 80},
						                {field : 'itemCode', title : '商品编码', width : 120},
						                {field : 'sendQty', title : '数量', width : 100},
						                {field : 'purchasePrice', title : '采购价', width : 80, align:'right'},
						                {field : 'purchaseAmount', title : '采购额', width : 100, align:'right'},
										{field : 'materialPrice', title : '物料价', width : 80, align:'right'},
										{field : 'factoryPrice', title : '厂商价', width : 80, align:'right'},
										{field : 'factoryAmount', title : '厂商额', width : 100, align:'right'},
										{field : 'ppAddDiscount', title : '厂商价加折', width : 80, align:'right'},
										{field : 'ppAddPrice', title : '厂商价加价', width : 80, align:'right'},
										{field : 'headquarterCost', title : '总部价', width : 80, align:'right'},
										{field : 'hcmAddDiscount', title : '总部价加折', width : 80, align:'right'},
										{field : 'regionCostFrom', title : '结算公司价', width : 80, align:'right'},
										{field : 'regionFromAmount', title : '结算公司额', width : 100, align:'right'},
										{field : 'regionCost', title : '地区价', width : 80, align:'right'},
										{field : 'regionAmount', title : '地区额', width : 100, align:'right'},
										{field : 'diffAmount', title : '差额', width : 100, align:'right'},
										{field : 'addPrice', title : '加价', width : 80, align:'right'},
										{field : 'tagPrice', title : '牌价', width : 80, align:'right'},
										{field : 'tagDiscountRate', title : '牌价折扣率', width : 80},
										{field : 'discountTagPrice', title : '牌价折扣价', width : 80, align:'right'},
										{field : 'discountTagAmount', title : '牌价折扣额', width : 100, align:'right'},
										{field : 'differenceAmount1', title : '差额1', width : 100, align:'right'},
										{field : 'roundTagPrice', title : '取整价', width : 80, align:'right'},
										{field : 'roundTagAmount', title : '取整额', width : 100, align:'right'},
										{field : 'differenceAmount2', title : '差额2(取整)', width : 100, align:'right'},
						                {field : 'genderName', title : '性别', width : 80},
						                {field : 'yearsName', title : '年份', width : 80},
						                {field : 'seasonName', title : '季节', width : 80}]" 
                 />	
			</div>
		</div>
	</div>
	<div id="exportInterceptPanel" class="easyui-dialog" data-options="closed:true"> 
	     	<div>
				 <table class="form-tb">
            	    <col width="120px"/>
            	 	<col />
                    <tbody>
                       <tr>
						   <th>正在导出,请稍候...</th>
					   </tr>
					 </tbody>
				 </table>
			</div>
   </div>
</body>
</html>