<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区其他入库明细表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/area_detail.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_other_stock_in/areaOtherStockIn.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
			<@p.billToolBar type="area_other_in_list"/>
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
									<th>商品编码： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:160"/>
										<input type="hidden" name="itemCode" id="itemCodeCondition" />
									</td>
								</tr>					
								<tr>
									<th>总部公司： </th>
									<td>
										<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole',inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160"/>
										<input type="hidden" name="salerNo" id="salerNoId"/>
									</td>
									<th>单据类型：</th>
						    		<td>
						    			<select class="easyui-combobox"  name="billType" id="billType" data-options="editable:false,valueField:'code',textField:'name',width:'auto',url:BasePath + '/area_balance_common/getBillType?showType=SALEOUT,BORROWOUT'"/>
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
									<th>地区公司： </th>
									<td>
										<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
									<th>管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organNoFromName" id="organNoFromNameId" 
										data-options="inputNoField:'organNoFromId',inputNameField:'organNoFromNameId',inputWidth:160"/>
										<input type="hidden" name="organNoFrom" id="organNoFromId" />
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
			                  {field : 'salerName', title : '总部公司', width : 200,align:'left',halign:'center'},
			                  {field : 'organNameFrom', title : '管理城市', width : 80,align:'center',halign:'center'},
			                  {field : 'orderUnitNameFrom', title : '货管单位', width : 80,align:'center',halign:'center'},
			                  {field : 'buyerName', title : '地区公司', width : 200,align:'left',halign:'center'},
			      			  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
			                  {field : 'brandName', title : '品牌', width : 100,align:'center',halign:'center'},	
			                  {field : 'oneLevelCategoryName', title : '一级大类', width : 60,align:'center'},	
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
 							  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
			                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},	
			                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},
			                  {field : 'receiveQty', title : '接收数量', width : 80,align:'right',halign:'center'},
			                  {field : 'sendAmount', title : '发出金额', width : 100,align:'right',halign:'center'},	
			                  {field : 'receiveAmount', title : '接收金额', width : 100,align:'right',halign:'center'},
			                  {field : 'sendDate', title : '发出日期', width : 100,align:'center',halign:'center',sortField:'send_date',sortable:true},
 							  {field : 'receiveDate', title : '接收日期', width : 100,align:'center',halign:'center',sortField:'receive_date',sortable:true},
 							  {field : 'billNo', title : '单据编号', width : 150,align:'left',halign:'center'},
			                  {field : 'createUser',title : '制单人',width : 80,align:'center',halign:'center'},
			                  {field : 'billTypeName', title : '单据类型', width : 100,align:'center',halign:'center'},
			                  {field : 'balanceNo', title : '结算单号', width : 150,align:'center',halign:'center', formatter: function(value,row,index){
								 return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AE-结算单确认');
							  }}
		                  ]" 
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>