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
              {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"enterList.doExport()", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value="4">
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
										<input class="ipt" multiSearch="company" />
										<input type="hidden" name="multiBuyerNo">
									</td>
									<th>内部供应商： </th>
									<td>
										<input class="ipt" multiSearch="dataAccess_company" notGroupLeadRole />
										<input type="hidden" name="multiSalerNo">
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
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="ipt" multiSearch="brand"  />
										<input type="hidden" name="multiBrandNo">
									</td>
									<th>商品编码： </th>
									<td>
										<input class="ipt"  multiSearch="item"/>
										<input type="hidden" name="multiItemCode">
									</td>
									<th>接收日期：</th>
						    		<td>
						    			<input class="easyui-datebox ipt" defaultValue="startDate"  name="receiveDateStart" 
						    			id="receiveDateStart" data-options="maxDate:'receiveDateEnd'"/>
						    		</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> 
										<input class="easyui-datebox ipt" defaultValue="endDate" name="receiveDateEnd" 
										id="receiveDateEnd"  data-options="minDate:'receiveDateStart'"/>
									</td>
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
									<th>单据类型： </th><td><input class="ipt"  combobox="billType" showType="RECEIPT,RETURNOWN,TRANSFER_IN"  name="billType"/></td>
									<th>单据编号： </th><td><input class="ipt"  name="originalBillNo"  /></td>
									<th>供应商： </th>
									<td>
										<input class="ipt" multiSearch="supplier"/> 
										<input type="hidden" name="multiSupplierNo">
									</td>	
									<th>业务类型： </th>
									<td>
										<input class="ipt"  combobox="bizType" showType="FIRST_ORDER,REPLENISH_ORDER,ZONGBUPURCHASE,YISHOUFANHUO,YISHOUBUCHULI,SUOPEI"  name="bizType"/>
									</td>
								<tr>	
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
			                  {field : 'buyerName', title : '公司',  width : 200,align:'left',halign:'center', formatter: function(value,row,index){
									if(row.salerName == '合计'){
										return row.salerName;
									}
									return value;
							  }},	
							  {field : 'salerName', title : '内部供应商', width : 200,align:'left',halign:'center'},	
			                  {field : 'receiveDate', title : '接收日期', width : 100},
			                  {field : 'brandName', title : '品牌', width : 80},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 80},	
			                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称', width : 150,align:'left',halign:'center'},
			                  {field : 'cost', title : '地区价', width : 80,align:'right',halign:'center', formatter: function(value,row,index){
									if(row.salerName == '合计'){
										return '';
									}
									return value;
							  }},	
			                  {field : 'receiveQty', title : '接收数量', width : 80,align:'right',halign:'center'},
			                  {field : 'receiveAmount', title : '接收金额', width : 100,align:'right',halign:'center'},
			                  {field : 'organNameFrom', title : '总部管理城市',  width : 100},
			                  {field : 'orderUnitNameFrom', title : '总部货管单位',  width : 100},
			                  {field : 'organName', title : '地区管理城市',  width : 100},
			                  {field : 'orderUnitName', title : '地区货管单位',  width : 100},
							  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center'},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
			                  {field : 'bizTypeName', title : '业务类型', width : 100},
			                  {field : 'supplierName', title : '供应商',  width : 200,align:'left',halign:'center'},
			                  {field : 'supplierNo', title : '供应商编码',  width : 120, hidden:true},
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},	
			                  {field : 'orderNo', title : '采购订单号', width : 100}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>