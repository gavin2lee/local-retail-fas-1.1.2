<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部其他入库明细列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/enterList.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"enterList.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"enterList.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/bill_balance/hq/export','总部其他入库导出',{type:'enter'})", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value="3">
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
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="dataAccess_company"  /><input type="hidden" name="multiSalerNo"></td>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"  /><input type="hidden" name="multiBrandNo"></td>
									<th>一级大类： </th>
									<td><input class="ipt" multiSearch="category" /><input type="hidden" name="multiCategoryNo"></td>
								</tr>	
								<tr>
									<th>单据编号： </th><td><input class="ipt"  name="originalBillNo"  /></td>
									<th>单据类型： </th><td><input class="ipt"  combobox="billType" showType="SALEOUT,BORROWOUT,TRANSFER_OUT"  name="billType"/></td>
									<th>商品编码： </th><td><input class="ipt"  singleSearch="item" name="itemCode"/></td>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNo"/></td>
								</tr>	
								<tr>
									<th>单据日期：</th>
						    		<td ><input class="easyui-datebox ipt"  name="balanceStartDate" id="balanceStartDate" data-options="maxDate:'balanceEndDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt"  name="balanceEndDate" id="balanceEndDate"  data-options="minDate:'balanceStartDate'"/></td>
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
			           rownumbers="true"  
			                columnsJsonList="[
			                  {field : 'buyerName', title : '公司', width : 200,align:'left', formatter: function(value,row,index){
									if(row.salerName == '合计'){
										return row.salerName;
									}
									return value;
							  }},	
			                  {field : 'buyerNo', title : '公司编码', width : 120, hidden:true},
			                  {field : 'sendDate', title : '单据日期', width : 100},
							  {field : 'billNo', title : '单据编号', width : 150},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
			                  {field : 'salerName', title : '供应商', width : 200,align:'left', formatter: function(value,row,index){
									if(row.salerName == '合计'){
										return '';
									}
									return value;
							  }},	
			                  {field : 'salerNo', title : '供应商编码', width : 120, hidden:true},
			                  {field : 'brandUnitName', title : '品牌部', width : 100},
			                  {field : 'brandName', title : '品牌', width : 100},
			                  {field : 'oneLevelCategoryName', title : '一级大类', width : 100},	
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},	
							  {field : 'itemCode', title : '商品编码', width : 150},
			                  {field : 'itemName', title : '商品名称',  width : 200,align:'left'},
			                  {field : 'cost', title : '单价', width : 100, formatter: function(value,row,index){
									if(row.salerName == '合计'){
										return '';
									}
									return value;
							  }},	
							  {field : 'sendQty', title : '入库数量', width : 100},
			                  {field : 'sendAmount', title : '金额', width : 100},
			                  {field : 'balanceNo', title : '结算单号', width : 150, formatter: function(value,row,index){
								     return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HE-结算单确认');
							  }}]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>