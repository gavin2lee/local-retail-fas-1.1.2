<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部地区明细对账表</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/area_detail.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hqAreaReportForm/reconciliation_dtl.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<@p.toolbar id="toolbar" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "hqAreaDtl.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "hqAreaDtl.clear()","type":0}
		           	 {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "hqAreaDtl.doExport()","type":0}
		           ]
				/>
				
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
									<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
									<td>
										<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole',inputNoField:'salerNoCondition',inputNameField:'salerNameId',inputWidth:180"/>
										<input type="hidden" name="salerNo" id="salerNoCondition" />
									</td>
									<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
									<td>
										<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" data-options="inputNoField:'brandUnitNoId',inputNameField:'brandUnitNameId',inputWidth:160"/>
										<input type="hidden" name="brandUnitNo" id="brandUnitNoId"/>
									</td>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-categorybox"   name="oneLevelCategoryNo" id="oneLevelCategoryNo" data-options="width:160"/>
									</td>
									<th>类型：</th>
									<td>
										<select class="easyui-combobox"  name="bizType" id="bizType"
						        		data-options="editable:false,valueField:'code',textField:'name',url:BasePath + '/common_util/getReportBizType'"/>
									</td>
								</tr>
								<tr>
									<th>客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户： </th>
									<td>
										<input class="easyui-company ipt"  name="buyerName" id="buyerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'buyerNoCondition',inputNameField:'buyerNameId',inputWidth:180"/>
										<input type="hidden" name="buyerNo" id="buyerNoCondition" />
									</td>
									<th>商品编码： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:160"/>
										<input type="hidden" name="itemCode" id="itemCodeCondition" />
									</td>
									<th>日期范围： </th>
									<td>
										<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'" style="width: 150px;" readonly="true" />
									</td>
									<th>至： </th>
									<td>
										<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" style="width: 150px;" readonly="true"/>
									</td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<#--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="20" showFooter="true"
			           columnsJsonList="[
			                  {field : 'bizTypeName', title : '类型', width : 80,align:'center',halign:'center'},
			                  {field : 'salerName', title : '公司', width : 200,align:'left',halign:'center'},
			                  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},	
			                  {field : 'itemCode', title : '货号', width : 150,align:'left',halign:'center'},	
 							  {field : 'itemName', title : '名称', width : 200,align:'left',halign:'center'},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 80,align:'center'},	
			                  {field : 'years', title : '年份', width : 60,align:'center',halign:'center'},
			                  {field : 'season', title : '季节', width : 60,align:'center',halign:'center'},
 							  {field : 'orderfrom', title : '订货形式', width : 80,align:'center'},
			                  {field : 'zoneName', title : '大区', width : 60,align:'center',halign:'center'},
			                  {field : 'buyerName', title : '客户', width : 250,align:'left',halign:'center'},
			                  {field : 'orderUnitName', title : '货管单位', width : 120,align:'center',halign:'center'},
			                  {field : 'cost', title : '地区价', width : 80,align:'right',halign:'center'},
			                  {field : 'sendQty', title : '数量', width : 80,align:'right',halign:'center'},
			                  {field : 'purchaseAmount', title : '金额', width : 100,align:'right',halign:'center'}
		                  ]" 
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>