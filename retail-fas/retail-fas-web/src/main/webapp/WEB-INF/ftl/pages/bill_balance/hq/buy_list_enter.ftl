<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>入库明细列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/enterList.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"enterList.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"enterList.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"enterList.doExport();", "type":4},
             {"id":"btn-direct","title":"更新异常价格","iconCls":"icon-info","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" id="balanceType" name="balanceType" value="1">
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
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier" /><input type="hidden" name="multiSalerNo"></td>
									<th>供应商类型： </th>
									<td><input class="ipt" multiSearch="supplierGroup"/> <input type="hidden" name="multiSupplierGroupNo"></td>
									<th>品牌部： </th>
									<td><input class="ipt" multiSearch="brandUnit"  /><input type="hidden" name="multiBrandUnitNo"></td>
								</tr>	
								<tr>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
									<th>货管单位： </th>
									<td><input class="ipt" multiSearch="orderUnit"/> <input type="hidden" name="multiOrderUnitNo"></td>
									<th>单据类型： </th><td><input class="ipt"  combobox="billType" showType="ASN,RETURNOWN"  name="billType"/></td>
									<th>商品编码： </th><td><input class="ipt"  multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
								</tr>	
								<tr>
									<th>单据日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="balanceStartDate" id="balanceStartDate" data-options="required:true,maxDate:'balanceEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="balanceEndDate" id="balanceEndDate"  data-options="required:true,minDate:'balanceStartDate'"/></td>
									<th>单据编号： </th><td><input class="ipt"  name="originalBillNo"  /></td>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNo"/></td>
								</tr>	
								<tr>
									<th>一级大类： </th>
									<td><input class="ipt" multiSearch="category" /><input type="hidden" name="multiCategoryNo"></td>
									<th>二级大类： </th>
									<td><input class="ipt" multiSearch="twoLevelCategory"/> <input type="hidden" name="multiTwoLevelCategoryNo"></td>
								</tr>			
								<tr>
									<th>未结算：</th>
									<td><input type="checkbox" name="isNoBalance" id="isNoBalance" value="true"/></td>
									<th>单价为零：</th>
									<td><input type="checkbox" name="isZero" id="isZero" value="true"/></td>
									<th>核价状态：</th>
									<td><input class="ipt" combobox="pricingStatus" name="pricingStatus"/></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20" rownumbers="true"
			           rownumbers="true"  singleSelect="true" 
			           columnsJsonList="[
			                  {field : 'buyerName', title : '公司', width : 250, align:'left'},
			                  {field : 'salerName', title : '供应商', width : 250,align:'left'},
			                  {field : 'sendDate', title : '单据日期', width : 80},
			                  {field : 'brandName', title : '品牌', width : 80},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 80},	
			                  {field : 'itemCode', title : '商品编码', width : 120, align:'left'},
			                  {field : 'itemName', title : '商品名称', width : 150, align:'left'},
			                  {field : 'cost', title : '采购价/地区价', width : 90, formatter: function(value,row,index){
									if(row.buyerName == '合计'){
										return '';
									}
									return value;
							  },align:'right'},	
							  {field : 'factoryPrice', title : '厂进价', width : 70},
							  {field : 'aprice', title : '核定价', width : 70},
			                  {field : 'sendQty', title : '数量', width : 70},
			                  {field : 'sendAmount', title : '金额', width : 90,align:'right'},
			                  {field : 'supplierGroupName', title : '供应商类型', width : 80},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
							  {field : 'originalBillNo', title : '单据编号', width : 150},
			                  {field : 'organName', title : '管理城市',  width : 80},
			                  {field : 'orderUnitName', title : '货管单位',  width : 80},
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},	
			                  {field : 'categoryName', title : '三级大类', width : 80},	
			                  {field : 'genderName', title : '性别', width : 80},
			                  {field : 'orderNo', title : '采购订单号', width : 100},
			                  {field : 'balanceNo', title : '结算单号', width : 150, formatter: function(value,row,index){
								   return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HS-结算单');
							  }}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>