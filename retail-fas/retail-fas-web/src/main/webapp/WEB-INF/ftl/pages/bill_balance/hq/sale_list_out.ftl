<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>出库明细列表</title>
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
			      		<input type="hidden" id="balanceType" name="balanceType" value="2">
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
										<input class="ipt" multiSearch="company" data-options="required:true" notGroupLeadRole />
										<input type="hidden" name="multiSalerNo">
									</td>
									<th>客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户： </th>
									<td>
										<input class="ipt" multiSearch="dataAccess_company"  />
										<input type="hidden" name="multiBuyerNo">
									</td>
									<th>品牌部： </th>
									<td>
										<input class="ipt" multiSearch="brandUnit"  />
										<input type="hidden" name="multiBrandUnitNo">
									</td>
									<th>一级大类： </th>
									<td>
										<input class="ipt" multiSearch="category" />
									    <input type="hidden" name="multiCategoryNo">
									</td>
								</tr>	
								<tr>
									<th>供应商类型： </th>
									<td>
										<input class="ipt" multiSearch="supplierGroup"/> <input type="hidden" name="multiSupplierGroupNo">
									</td>
									<th>供应商： </th>
									<td>
										<input class="ipt" multiSearch="supplier"/> 
										<input type="hidden" name="multiSupplierNo">
									</td>
									<th>单据类型： </th>
									<td>
										<input class="ipt"  combobox="billType" showType="ASN,RETURNOWN,TRANSFER_OUT"  name="billType"/>
									</td>
									<th>商品编码： </th>
									<td>
										<input class="ipt"  multiSearch="item"/><input type="hidden" name="multiItemCode">
									</td>
								</tr>	
								<tr>
									<th>单据日期：</th>
						    		<td >
						    			<input class="easyui-datebox ipt" defaultValue="startDate" name="balanceStartDate" id="balanceStartDate" data-options="required:true,maxDate:'balanceEndDate'"/>
						    		</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> 
										<input class="easyui-datebox ipt" defaultValue="endDate" name="balanceEndDate" id="balanceEndDate"  data-options="required:true,minDate:'balanceStartDate'"/>
									</td>
									<th>单据编号： </th><td><input class="ipt"  name="originalBillNo"  /></td>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNo"/></td>
								</tr>	
								<tr>
								    <th>地区管理城市： </th>
									<td>
										<input class="ipt" multiSearch="organ"/> 
										<input type="hidden" name="multiOrganNo">
									</td>
									<th>地区货管单位： </th>
									<td>
										<input class="ipt" multiSearch="orderUnit"/> 
									    <input type="hidden" name="multiOrderUnitNo">
									</td>
									<th>总部管理城市： </th>
									<td>
										<input class="ipt" multiSearch="organ"/> 
										<input type="hidden" name="multiOrganNoFrom">
									</td>
									<th>总部货管单位： </th>
									<td>
										<input class="ipt" multiSearch="orderUnit"/> 
									    <input type="hidden" name="multiOrderUnitNoFrom">
									</td>
								</tr>	
								<tr>
									<th>未结算：</th>
									<td>
										<input type="checkbox" name="isNoBalance" id="isNoBalance" value="true"/>
									</td>
									<th>单价为零：</th>
									<td><input type="checkbox" name="isZero" id="isZero" value="true"/></td>
								    <th>业务类型： </th>
									<td>
										<input class="ipt"  combobox="bizType" showType="FIRST_ORDER,REPLENISH_ORDER,ZONGBUPURCHASE"  name="bizType"/>
									</td>
									<th></th>
									<td></td>
								</tr>												
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"  emptyMsg = ""
			           columnsJsonList="[
			                  {field : 'salerName', title : '公司', width : 200,align:'left',halign:'center'},
			                  {field : 'buyerName', title : '客户', width : 200,align:'left',halign:'center'},
			                  {field : 'sendDate', title : '单据日期', width : 80},
			                  {field : 'brandUnitName', title : '品牌部', width : 80},
			                  {field : 'brandName', title : '品牌', width : 80},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 80},	
			                  {field : 'itemCode', title : '商品编码', width : 120,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称', width : 150,align:'left',halign:'center'},
			                  {field : 'purchasePrice', title : '采购价', width : 70, align:'right',halign:'center',organType:'U010101'},
			                  {field : 'cost', title : '地区价', width : 70, formatter: function(value,row,index){
									if(row.buyerName == '合计'){
										return '';
									}
									return value;
							  },align:'right',halign:'center'},	
			                  {field : 'sendQty', title : '数量', width : 70,align:'right',halign:'center'},
			                  {field : 'purchaseAmount', title : '采购额', width : 90,align:'right',halign:'center'},
			                  {field : 'sendAmount', title : '金额', width : 90,align:'right',halign:'center'},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
			                  {field : 'bizTypeName', title : '业务类型', width : 100},
							  {field : 'originalBillNo', title : '单据编号', width : 150},
							  {field : 'organNameFrom', title : '总部管理城市',  width : 100},
			                  {field : 'orderUnitNameFrom', title : '总部货管单位',  width : 100},
			                  {field : 'organName', title : '地区管理城市',  width : 100},
			                  {field : 'orderUnitName', title : '地区货管单位',  width : 100},
			                  {field : 'supplierGroupName', title : '供应商类型', width : 80},
			                  {field : 'supplierName', title : '供应商', width : 200,align:'left',halign:'center'},
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},	
			                  {field : 'categoryName', title : '三级大类', width : 80},	
			                  {field : 'genderName', title : '性别', width : 80},
			                  {field : 'orderNo', title : '采购订单号', width : 100},
			                  {field : 'balanceNo', title : '结算单号', width : 150, formatter: function(value,row,index){
								  return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HU-结算单');
							  }}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>