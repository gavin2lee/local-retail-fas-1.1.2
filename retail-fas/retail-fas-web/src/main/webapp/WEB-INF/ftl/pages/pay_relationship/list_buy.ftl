<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部收购列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pay_relationship/payRelationshipBuy.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"payRelationshipBuy.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"payRelationshipBuy.clear()", "type":0},
             {"id":"btn-import","title":"按单据导入","iconCls":"icon-save","action":"payRelationshipBuy.doImportBuy(1)", "type":149},
             {"id":"btn-import","title":"按货号导入","iconCls":"icon-save","action":"payRelationshipBuy.doImportBuy(2)", "type":149},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"payRelationshipBuy.doExport()","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="searchType" id="searchType"/>
			      		<input type="hidden" name="multiBusinessBizType" id="multiBusinessBizType" value="(60)"/>
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
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier" /><input type="hidden" name="multiSupplierNo"></td>
									<th>地区公司： </th>
									<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiBuyerNo"></td>
									<th>单据编码： </th><td><input class="ipt"  name="businessBillNo"  /></td>	
								</tr>
								<tr>
									<th>品牌： </th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" 
										data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:140"/>
										<input type="hidden" name="brandNos" id="brandNoId"/>
									</td>
									<th>商品编码： </th><td><input class="ipt"  multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
									<th>单据日期：</th>
						    		<td ><input class="easyui-datebox ipt" name="sendDateStart" id="sendDateStart" defaultValue="startDate" data-options="maxDate:'sendDateEnd',required:true"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" name="sendDateEnd" id="sendDateEnd" defaultValue="endDate" data-options="minDate:'sendDateStart',required:true"/></td>
								</tr>
								<tr>
									<th>价格为零：</th>
									<td><input type="checkbox" name="isZero" id="isZero" value="true"/></td>
									<th>折扣为空：</th>
									<td><input type="checkbox" name="isBlankDiscount" id="isBlankDiscount" value="true"/></td>
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
								                  {field : 'supplierName', title : '供应商', width : 180},
								                  {field : 'businessBillNo', title : '单据编码', width : 150},
								                  {field : 'itemCode', title : '商品编码', width : 150},
								                  {field : 'itemName', title : '商品名称', width : 150},
								                  {field : 'brandName', title : '品牌', width : 100},
								                  {field : 'oneLevelCategoryName', title : '大类', width : 100},
								                  {field : 'sendQty', title : '发出量', width : 100},
								                  {field : 'tagPrice', title : '牌价', width : 100},
								                  {field : 'itemTagAmount', title : '牌价额', width : 100},
								                  {field : 'supplierTagDiscount', title : '厂商折扣', width : 100},
								                  {field : 'cost', title : '厂商价', width : 100},
								                  {field : 'amount', title : '厂商额', width : 100},
								                  {field : 'zoneTagDiscount', title : '地区折扣', width : 100},
								                  {field : 'zoneCost', title : '地区价', width : 100},
								                  {field : 'zoneAmount', title : '地区额', width : 100}, 
								                  {field : 'billDate', title : '发出日', width : 100},   
								                  {field : 'salerName', title : '总部结算公司', width : 180},  
								                  {field : 'buyerName', title : '地区结算公司', width : 180},  
								                  {field : 'orderUnitName', title : '货管单位', width : 150},  
								                  {field : 'storeName', title : '机构', width : 150},  
								                  {field : 'updateUser', title : '修改人', width : 150},  
								                  {field : 'updateTime', title : '修改时间', width : 150}  
											    ]" 
		                 	/>	
			</div>
	 	</div>
	</div>
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	                 {field : 'validateObj.settlementNumber', title : '结算号', width : 150},
			         {field : 'errorInfo',title:'错误信息',width:500,formatter : pe_util.showTips}]"	
         />
     </div>	
</body>
</html>