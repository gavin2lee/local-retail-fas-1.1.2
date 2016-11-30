<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>休闲品牌对账报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/leisureBrand.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"leisureBrand.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"leisureBrand.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "leisureBrand.doExport()","type":4}
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
							<col width="100" />
							<col />
							<col width="100" />
							<col />	
							<col width="100" />
							<col />							
							<tbody>
								<tr>
									<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
									<td>
										<input class="easyui-supplier ipt"  name="salerName" id="supplierNameId" data-options="inputNoField:'supplierNoCondition',inputNameField:'supplierNameId',inputWidth:160"/>
										<input type="hidden" name="supplierNo" id="supplierNoCondition" />									
									</td>
									<th>商品编码： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:160"/>
										<input type="hidden" name="itemCode" id="itemCodeCondition" />
									</td>
									<th>单据日期： </th>
									<td>
										<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'" style="width: 150px;" readonly="true"/>
									</td>
									<th>至： </th>
									<td>
										<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" style="width: 150px;" readonly="true"/>
									</td>
								</tr>					
								<tr>
									<th>总部公司： </th>
									<td>
										<input class="easyui-company ipt"  name="buyerName" id="buyerNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" 
										data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:160"/>
										<input type="hidden" name="brandNoLike" id="brandNoId"/>
									</td>
									<th>货管单位：</th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitName" id="orderUnitNameId" 
										data-options="inputNoField:'orderUnitNoId',inputNameField:'orderUnitNameId',inputWidth:160"/>
										<input type="hidden" name="orderUnitNo" id="orderUnitNoId" />
									</td>
									<th></th>
									<td></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<#--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridJG" loadUrl="" saveUrl="" defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"  showFooter="true"
			           frozenColumns="[
 							  {field : 'ITEM_CODE', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'ITEM_NAME', title : '商品名称', width : 180,align:'left',halign:'center'},
			                  {field : 'MATERIAL_PRICE', title : '物料价', width : 60,align:'right',halign:'center'},	
			                  {field : 'PURCHASE_PRICE', title : '采购价', width : 60,align:'right',halign:'center'},	
			                  {field : 'PURCHASE_AMOUNT', title : '金额', width : 100,align:'right',halign:'center'},
		                  ]" 
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>