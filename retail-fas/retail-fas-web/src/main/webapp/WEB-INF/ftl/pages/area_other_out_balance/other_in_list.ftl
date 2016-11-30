<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部其他入库明细表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/area_detail.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_other_stock_in/otherStockIn.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
			<@p.billToolBar type="hq_other_list"/>
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
									<th>单据编号： </th>
									<td>
										<input class="easyui-validatebox ipt"   name="billNo" id="billNo" style="width: 150px;"/>
									</td>
									<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
									<td>
										<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" 
										data-options="multiple:true,inputNoField:'brandUnitNoId',inputNameField:'brandUnitNameId',inputWidth:160"/>
										<input type="hidden" name="brandUnitNoLike" id="brandUnitNoId"/>
									</td>
								    <th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" 
										data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:160"/>
										<input type="hidden" name="brandNoLike" id="brandNoId"/>
									</td>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-combobox"   name="oneLevelCategoryName" id="oneLevelCategoryNameId" 
										data-options="width:160,url: BasePath + '/category/get_biz?levelid=1&status=1',valueField : 'categoryNo',textField : 'name',multiple:true,
											onChange: function(rec){
												var val = $('#oneLevelCategoryNameId').combobox('getValues').join(',');
												$('#oneLevelCategoryNoId').val(val);
											}"/>
										<input type="hidden" name="oneLevelCategoryNo" id="oneLevelCategoryNoId"/>
									</td>
								</tr>					
								<tr>
									<th>地区公司： </th>
									<td>
										<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="salerNo" id="salerNoId"/>
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
									<th></th>
									<td></td>
									<th></th>
									<td></td>
								</tr>
								<tr>
									<th>总部公司： </th>
									<td>
										<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
									<th>地区管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organNoFromName" id="organNoFromNameId" 
										data-options="inputNoField:'organNoFromId',inputNameField:'organNoFromNameId',inputWidth:160"/>
										<input type="hidden" name="organNoFrom" id="organNoFromId" />
									</td>
									<th>总部管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organName" id="organNameId" 
										data-options="inputNoField:'organNoId',inputNameField:'organNameId',inputWidth:160"/>
										<input type="hidden" name="organNo" id="organNoId" />
									</td>
									<#if organTypeNo=='U010101'>
									<th>单据类型：</th>
						    		<td>
						    			<select class="easyui-combobox"  name="billType" id="billType" 
						    			data-options="editable:false,valueField:'code',textField:'name',width:'auto',url:BasePath + '/area_balance_common/getBillType?showType=SALEOUT,TRANSFER_OUT,BORROWOUT,SALEOUTZONE'"/>
						    		</td>
						    		</#if>
						    		<#if organTypeNo=='U010102'>
									<th>单据类型：</th>
						    		<td>
						    			<select class="easyui-combobox"  name="billType" id="billType" 
						    			data-options="editable:false,valueField:'code',textField:'name',width:'auto',url:BasePath + '/area_balance_common/getBillType?showType=ASN,RETURNOWN'"/>
						    		</td>
						    		</#if>
								</tr>
								<tr>
								    <th>地区货管单位： </th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitNameFrom" id="orderUnitNameFromId" 
										data-options="inputNoField:'orderUnitNoFromId',inputNameField:'orderUnitNameFromId',inputWidth:160"/>
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromId"/>
									</td>
									<th>总部货管单位：</th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitName" id="orderUnitNameId" 
										data-options="inputNoField:'orderUnitNoId',inputNameField:'orderUnitNameId',inputWidth:160"/>
										<input type="hidden" name="orderUnitNo" id="orderUnitNoId" />
									</td>
									<th>结算单号：</th>
									<td>
										<input class="easyui-validatebox ipt"   name="balanceNo" id="balanceNo" style="width: 150px;"/>
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
			           columnsJsonList="[
			                  {field : 'salerName', title : '地区公司', width : 200,align:'left',halign:'center'},
			      			  {field : 'brandUnitName', title : '品牌部', width : 100,align:'center'},
			                  {field : 'brandName', title : '品牌', width : 100,align:'center',halign:'center'},	
			                  {field : 'oneLevelCategoryName', title : '大类', width : 60,align:'center'},	
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
 							  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
			                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},	
			                  {field : 'sendQty', title : '数量', width : 80,align:'right',halign:'center'},
			                  {field : 'sendAmount', title : '金额', width : 100,align:'right',halign:'center'},	
			                  {field : 'sendDate', title : '单据日期', width : 100,align:'center',halign:'center',sortField:'send_date',sortable:true},
			                  {field : 'buyerName', title : '总部公司', width : 200,align:'left',halign:'center'},
			                  {field : 'organNameFrom', title : '地区管理城市', width : 100,align:'center',halign:'center'},
  			                  {field : 'orderUnitNameFrom', title : '地区货管单位', width : 100,align:'center',halign:'center'},
  			                  {field : 'organName', title : '总部管理城市', width : 100,align:'center',halign:'center'},
  			                  {field : 'orderUnitName', title : '总部货管单位', width : 100,align:'center',halign:'center'},
 							  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center'},
 							  {field : 'returnNo', title : '退货编号', width : 125,align:'left',halign:'center',organType:'U010102'},
 							  {field : 'claimNo', title : '索赔编号', width : 125,align:'left',halign:'center',organType:'U010102'},
			                  {field : 'createUser',title : '制单人',width : 80,align:'center',halign:'center'},
			                  {field : 'billTypeName', title : '单据类型', width : 135,align:'center',halign:'center'},
			                  {field : 'balanceNo', title : '结算单号', width : 150,align:'center',halign:'center', formatter: function(value,row,index){
								 return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HE-结算单确认');
							  }}
		                  ]" 
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>