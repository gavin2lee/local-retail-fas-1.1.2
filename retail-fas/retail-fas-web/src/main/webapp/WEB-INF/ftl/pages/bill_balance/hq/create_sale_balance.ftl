<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>自定义结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/createBalance.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="createBalanceForm" id="createBalanceForm" method="post">
			      		<input type="hidden" name="balanceType"  value="2">
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
									<th><span class="ui-color-red">*</span>公司：</th>
                                    <td><input class="ipt disableEdit" singleSearch="company"  name="salerName" data-options="required:true" notGroupLeadRole/><input type="hidden" name="salerNo" /></td>
                                    <th><span class="ui-color-red">*</span>客户：</th>
                                    <td><input class="ipt disableEdit" singleSearch="dataAccess_company"  name="buyerName" data-options="required:true"/><input type="hidden" name="buyerNo" /></td>
									<th>品牌部：</th>
                                    <td><input class="ipt disableEdit" singleSearch="brandUnit"  name="brandUnitName" /><input type="hidden" name="brandUnitNo"/></td>
                                	<th><span class="ui-color-red">*</span>一级大类：</th>
                                    <td><input class="ipt disableEdit" singleSearch="category"  name="categoryName"  data-options="required:true"/><input type="hidden" name="categoryNo"/></td>
								</tr>	
								<tr>
									<th><span class="ui-color-red">*</span>结算期间：</th>
								    <td><input class="easyui-datebox easyui-validatebox  ipt disableEdit" defaultValue="startDate" id= "balanceStartDate" name="balanceStartDate" data-options="required:true, maxDate:'balanceEndDate'" /></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox easyui-validatebox  ipt disableEdit" defaultValue="endDate" id= "balanceEndDate" name="balanceEndDate" data-options="required:true, minDate:'balanceStartDate'"/></td>	
									<th><span class="ui-color-red">*</span>币别：</th>
		                            <td><input class="ipt" combobox="currency" id="currency" name="currency"  data-options="required:true,editable:false"/></td> 
									<th><span class="ui-color-red">*</span>结算日： </th><td><input class="easyui-datebox ipt" defaultValue="currentDate" name="balanceDate"  id="balanceDate" data-options="required:true"/></td>
								</tr>
								<tr>
								    <th>单据编号： </th>
									<td><input class="ipt"  name="originalBillNo"/></td>
									<th>商品编码： </th>
									<td><input class="ipt" multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/> <input type="hidden" name="multiSupplierNo"></td>
									<th>管理城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
								</tr>	
								<tr>
                                	<th>二级大类：</th>
                                    <td><input class="ipt" multiSearch="twoLevelCategory" /><input type="hidden" name="multiTwoLevelCategoryNo"/></td>
									<th>三级大类：</th>
                                    <td><input class="ipt" multiSearch="threeLevelCategory" /><input type="hidden" name="multiThreeLevelCategoryNo"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
        	   <div class="easyui-layout" data-options="fit:true,plain:true">
					<div data-options="region:'north',border:false" >
					  <@p.toolbar id="return_tool_bar" listData=[
					  		{"id":"foot_btn_load","title":"清空表单","iconCls":"icon-empty","action":"createBalance.clear()","type":0},
							{"id":"foot_btn_load","title":"加载明细","iconCls":"icon-import","action":"createBalance.loadDtl()","type":0}
						 ]/>
					</div>
					<div data-options="region:'center',border:false" >
				      <@p.datagrid id="createBalanceDG"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="500"
					           rownumbers="true"    checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
					           columnsJsonList="[
					           		  {field:'ck',checkbox:true,notexport:true},
					           		  {field : 'billTypeName', title : '单据类型', width : 100},
					           		  {field : 'originalBillNo', title : '单据编号', width : 150},
					                  {field : 'sendDate', title : '单据日期', width : 100},
									  {field : 'brandName', title : '品牌', width : 100},
									  {field : 'oneLevelCategoryName', title : '大类', width : 80},	
					                  {field : 'itemCode', title : '商品编码', width : 150},
					                  {field : 'itemName', title : '商品名称', width : 150, align:'left'},
					                  {field : 'cost', title : '地区价', width : 80, formatter: function(value,row,index){
											if(row.buyerName == '合计'){
												return '';
											}
											return value;
									  },align:'right'},	
					                  {field : 'sendQty', title : '数量', width : 80},
					                  {field : 'sendAmount', title : '金额', width : 100,align:'right'},
					                  {field : 'supplierName', title : '供应商', width : 200, align:'left'},
					                  {field : 'organName', title : '管理城市',  width : 120},
					                  {field : 'orderUnitName', title : '货管单位',  width : 120},
					                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},	
					                  {field : 'categoryName', title : '三级大类', width : 100},	
					                  {field : 'orderNo', title : '采购订单号', width : 100}]" 
		                 />
		               </div>
		          </div>
			</div>
	 	</div>
	</div>
</body>
</html>