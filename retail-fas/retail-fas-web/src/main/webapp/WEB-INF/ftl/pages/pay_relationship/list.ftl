<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部到货核对</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pay_relationship/payRelationship.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"payRelationship.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"payRelationship.clear()", "type":0},
             {"id":"btn-import","title":"导入厂商金额","iconCls":"icon-save","action":"payRelationship.doImport(1)", "type":148},
             {"id":"btn-import","title":"导入对账折扣","iconCls":"icon-save","action":"payRelationship.doImport(2)", "type":149},
             {"id":"btn-import","title":"导入牌价","iconCls":"icon-save","action":"payRelationship.doImport(3)", "type":149},
             {"id":"btn-import","title":"生成结算单","iconCls":"icon-save","action":"payRelationship.generateBalance(1)", "type":137},
             {"id":"btn-price","title":"更新折扣","iconCls":"icon-save","action":"payRelationship.updateDiscount()", "type":138},
             {"id":"btn-price","title":"更新价格","iconCls":"icon-save","action":"payRelationship.updateCost()", "type":139},
             {"id":"btn-price","title":"更新到期日","iconCls":"icon-save","action":"payRelationship.updateDueDate()", "type":140},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"payRelationship.doExport()","type":4}
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
			      		<input type="hidden" name="multiBusinessBizType" id="multiBusinessBizType" value="(0,1)"/>
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
									<th><span class="ui-color-red">*</span>供应商： </th>
									<td><input class="ipt" multiSearch="supplier" data-options="required:true"/><input type="hidden" name="multiSupplierNo"></td>
									<th>地区公司： </th>
									<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiBuyerNo"></td>
									<th>单据编码： </th><td><input class="ipt"  name="businessBillNo"  /></td>	
									<th>结算号： </th><td><input class="ipt"  name="settlementNumber"  /></td>	
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
									<th>是否结算： </th><td><input class="ipt" combobox="YesOrNo"  name="isBalance" /></td>
									<th>是否对账： </th><td><input class="ipt" combobox="YesOrNo"  name="balanceFlag" /></td>	
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
        			<div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
            	 		<div title="单据信息" data-options="selected : true,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""   rownumbers="true" showFooter="true"
				              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = ""  pageSize="20" 
			           			columnsJsonList="[
					                  {field : 'supplierName', title : '供应商', width : 180},
					                  {field : 'businessBillNo', title : '单据编码', width : 120},
					                  {field : 'settlementNumber', title : '结算号', width : 100},
					                  {field : 'balanceFlagName', title : '对账标记', width : 80},
					                  {field : 'qty', title : '发出数量', width : 100},
					                  {field : 'tagAmount', title : '牌价额', width : 100},
					                  {field : 'supplierAmount', title : '含税厂商金额', width : 100},
					                  {field : 'amount', title : '含税结算金额', width : 100},
					                  {field : 'diffAmount', title : '含税差异金额', width : 100},
					                  {field : 'supplierDiscount1', title : '厂商折扣1', width : 100},
					                  {field : 'supplierDiscount2', title : '厂商折扣2', width : 100},
					                  {field : 'balanceDiscount1', title : '对账折扣1', width : 100},
					                  {field : 'balanceDiscount2', title : '对账折扣2', width : 100},
					                  {field : 'notTaxSupplierAmount', title : '不含税厂商金额', width : 100},
					                  {field : 'notTaxAmount', title : '不含税结算金额', width : 100},
					                  {field : 'notTaxDiffAmount', title : '不含税差异金额', width : 100},
					                  {field : 'notTaxMultiDiffAmount', title : '不含税*1.17与含税差异金额', width : 120},
					                  {field : 'balanceNo', title : '结算单号', width : 150},
					                  {field : 'zonePriceBasisName', title : '地区定价依据', width : 100},
					                  {field : 'zoneOriginalDiscount1', title : '地区折扣1', width : 100},
					                  {field : 'zoneOriginalDiscount2', title : '地区折扣2', width : 100},
					                  {field : 'zoneAddPrice', title : '地区加价', width : 100},
					                  {field : 'zoneDiscount', title : '地区牌价折扣', width : 100},
					                  {field : 'zoneAmount', title : '地区额', width : 100}, 
					                  {field : 'orderNo', title : '订单号', width : 100},  
					                  {field : 'supplierSendDate', title : '供应商发货日', width : 100},    
					                  {field : 'billDate', title : '发出日', width : 100},   
					                  {field : 'dueDate', title : '到期日', width : 100},   
					                  {field : 'salerName', title : '总部结算公司', width : 180},  
					                  {field : 'buyerName', title : '地区结算公司', width : 180},  
					                  {field : 'orderUnitName', title : '货管单位', width : 150},  
					                  {field : 'storeName', title : '机构', width : 150},  
					                  {field : 'orderTypeName', title : '订货类型', width : 100},
					                  {field : 'updateUser', title : '修改人', width : 150},  
					                  {field : 'updateTime', title : '修改时间', width : 150}  
					                 ]" 
                			 />
            	 		</div>
            	 		<div title="明细信息" data-options="selected : false,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid1"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					            columnsJsonList="[
								                  {field : 'supplierName', title : '供应商', width : 180},
								                  {field : 'businessBillNo', title : '单据编码', width : 120},
								                  {field : 'itemCode', title : '商品编码', width : 150},
								                  {field : 'itemName', title : '商品名称', width : 150},
								                  {field : 'brandName', title : '品牌', width : 100},
								                  {field : 'oneLevelCategoryName', title : '大类', width : 100},
								                  {field : 'brandSeason', title : '品牌季节', width : 100},
								                  {field : 'orderTypeName', title : '订货类型', width : 100},
								                  {field : 'sendQty', title : '发出量', width : 100},
								                  {field : 'tagPrice', title : '牌价', width : 100},
								                  {field : 'itemTagAmount', title : '牌价额', width : 100},
								                  {field : 'supplierDiscount1', title : '厂商折扣1', width : 100},
								                  {field : 'supplierDiscount2', title : '厂商折扣2', width : 100},
								                  {field : 'balanceDiscount1', title : '对账折扣1', width : 100},
								                  {field : 'balanceDiscount2', title : '对账折扣2', width : 100},
								                  {field : 'cost', title : '结算价', width : 100},
								                  {field : 'amount', title : '发出金额', width : 100},
								                  {field : 'balanceNo', title : '结算单号', width : 100},
								                  {field : 'zonePriceBasisName', title : '地区定价依据', width : 100},
								                  {field : 'zoneOriginalDiscount1', title : '地区折扣1', width : 100},
								                  {field : 'zoneOriginalDiscount2', title : '地区折扣2', width : 100},
								                  {field : 'zoneAddPrice', title : '地区加价', width : 100},
								                  {field : 'zoneDiscount', title : '地区牌价折扣', width : 100},
								                  {field : 'zoneCost', title : '地区价', width : 100},
								                  {field : 'zoneAmount', title : '地区额', width : 100}, 
								                  {field : 'orderNo', title : '订单号', width : 100},  
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