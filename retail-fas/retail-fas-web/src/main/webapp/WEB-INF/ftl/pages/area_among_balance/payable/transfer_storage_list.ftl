<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>跨区调货入库明细表</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/area_detail.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_among/transferIn.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<#--最外层框-->
<div data-options="region:'center',border:false">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'跨区调货明细表'">
		<div id="subLayoutId" class="easyui-layout" data-options="fit:true,border:false">
			<#--按钮-->
			<div data-options="region:'north',border:false" class="toolbar-region">
			   	<@p.billToolBar type="area_among_transferList_yf"/>
			</div>
				
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
					<div data-options="region:'north',border:false">
					<#--表头-->
					<div class="search-div">
					   <#-- 主档信息  -->
		               <form name="mainForm" id="mainForm" method="post">
		                <input type="hidden" name="aToA" value="${aToA}" id="aToA"/>
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
										<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" data-options="multiple:true,inputNoField:'brandUnitNoId',inputNameField:'brandUnitNameId',inputWidth:160"/>
										<input type="hidden" name="brandUnitNoLike" id="brandUnitNoId"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:160"/>
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
									<th>调出公司： </th>
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
									<th>日期区间： </th>
									<td>
										<input class="easyui-datebox ipt"  name="receiveDateStart" id="receiveDateStart" data-options="required:true,maxDate:'receiveDateEnd'" style="width: 150px;" readonly="true"/>
									</td>
									<th>至： </th>
									<td>
										<input class="easyui-datebox ipt"   name="receiveDateEnd" id="receiveDateEnd" data-options="required:true,minDate:'receiveDateStart'" style="width: 150px;" readonly="true"/>
									</td>
								</tr>
								<tr>
								    <th>调入公司： </th>
									<td>
										<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
									<th>调出管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organNoFromName" id="organNoFromNameId" 
										data-options="inputNoField:'organNoFromId',inputNameField:'organNoFromNameId',inputWidth:160"/>
										<input type="hidden" name="organNoFrom" id="organNoFromId" />
									</td>
									<th>调入管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organName" id="organNameId" 
										data-options="inputNoField:'organNoId',inputNameField:'organNameId',inputWidth:160"/>
										<input type="hidden" name="organNo" id="organNoId" />
									</td>
									<th>结算单号：</th>
									<td><input class="easyui-validatebox ipt"   name="balanceNo" id="balanceNo" style="width: 150px;"/></td>
								</tr>
								<tr>
								    <th>调出货管单位： </th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitNameFrom" id="orderUnitNameFromId" 
										data-options="inputNoField:'orderUnitNoFromId',inputNameField:'orderUnitNameFromId',inputWidth:160"/>
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromId"/>
									</td>
									<th>调入货管单位：</th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitName" id="orderUnitNameId" 
										data-options="inputNoField:'orderUnitNoId',inputNameField:'orderUnitNameId',inputWidth:160"/>
										<input type="hidden" name="orderUnitNo" id="orderUnitNoId" />
									</td>
									<th></th>
									<td></td>
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
					<@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="20" showFooter="true"
			           rowStyler="function(index,row){
								     if (row.zoneNameFrom.indexOf('小计') != -1){
								         return 'background-color:#FFEFD5;';
								     }
					   }" 
			           frozenColumns="[
			           		  {field : 'zoneNameFrom', title : '调出大区', width : 80,align:'center',halign:'center'},
			                  {field : 'salerName', title : '调出公司', width : 200,align:'left',halign:'center'},
			           ]"
			           columnsJsonList="[
               				  {field : 'organNameFrom', title : '调出管理城市', width : 100,align:'center',halign:'center'},
			                  {field : 'orderUnitNameFrom', title : '调出货管单位', width : 100,align:'center',halign:'center'},
               				  {field : 'sendStoreName', title : '发货仓', width : 150,align:'left',halign:'center'},
							  {field : 'brandUnitName', title : '品牌部', width : 100,align:'center'},
			                  {field : 'brandName', title : '品牌', width : 100,align:'center'},	
			                  {field : 'oneLevelCategoryName', title : '大类', width : 80,align:'center'},	
							  {field : 'twoLevelCategoryName', title : '二级大类', width : 80,align:'center'},	
			                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
			                  {field : 'years', title : '年份', width : 80},
			                  {field : 'season', title : '季节', width : 80},
			                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},
  			                  {field : 'tagPrice', title : '牌价', width : 80,align:'right',halign:'center'},
  			                  {field : 'tagPriceAmount', title : '牌价金额', width : 80,align:'right',halign:'center',organType:'U010102'},
		                      {field : 'tsRebate', title : '折扣', width : 80,align:'right',halign:'center',organType:'U010102'},
			                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},	
			                  {field : 'receiveQty', title : '接收数量', width : 80,align:'right',halign:'center'},	
			                  {field : 'sendAmount', title : '出库金额', width : 100,align:'right',halign:'center'},
			                  {field : 'receiveAmount', title : '入库金额', width : 100,align:'right',halign:'center'},
			                  {field : 'sendDate', title : '发出日期', width : 100,align:'center'},
			                  {field : 'receiveDate', title : '接收日期', width : 100,align:'center'},
			                  {field : 'zoneName', title : '调入大区', width : 80,align:'center',halign:'center'},
			                  {field : 'buyerName', title : '调入公司', width : 200,align:'left',halign:'center'},
                 			  {field : 'organName', title : '调入管理城市', width : 100,align:'center',halign:'center'},
							  {field : 'orderUnitName', title : '调入货管单位', width : 100,align:'center',halign:'center'},
                 			  {field : 'receiveStoreName', title : '收货仓', width : 150,align:'left',halign:'center'},
			                  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center'},
			                  {field : 'billTypeName', title : '单据类型', width : 80,align:'center'},
			                  {field : 'createUser',title : '制单人',width : 100,align:'center'},
			                  {field : 'balanceNo', title : '结算单号', width : 150,align:'center',halign:'center', formatter: function(value,row,index){
									return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AB-结算单确认');
							  }}
			               ]" 
               		  />
					</div>
				</div>
				</div>
		</div>
	</div>
</div>

</body>
</html>