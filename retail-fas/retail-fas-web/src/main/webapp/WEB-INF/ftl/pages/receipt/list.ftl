<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>验收单列表</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
  	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/receipt/receipt.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "receipt.search()", "type":0},
	         {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "receipt.clear()","type":0},
			 {"id":"top_btn_back","title":"更新汇率","iconCls":"icon-prev","action":"receipt.exchangeRate()","type":0} ,
			 {"id":"top_btn_add","title":"生成结算单","iconCls":"icon-add","action":"receipt.showDialog()","type":0},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "receipt.doExport('/receipt/export')","type":4}
	       ]
		/>
	</div>
   
	<div data-options="region:'center',border:false" >
	  <div class="easyui-layout" data-options="fit:true" id="subLayout">
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
							<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
							<td>
								<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="buyerNo" id="buyerNoId"/>
							</td>
						    <th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
							<td>
								<input class="easyui-supplier ipt"  name="salerName" id="salerNameId" data-options="inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160"/>
								<input type="hidden" name="salerNo" id="salerNoId" />
							</td>
							<th>日期： </th>
							<td>
								<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'" style="width: 150px;" readonly="true"/>
							</td>
							<th>至： </th>
							<td>
								<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" style="width: 150px;" readonly="true"/>
							</td>
						</tr>		
						<tr>
							<th>验收单编码：</th>
                            <td><input class="easyui-validatebox  ipt"  name="billNo" id="billNo"/></td>
                            <th>采购订单号：</th>
                            <td><input class="easyui-validatebox  ipt"  name="extendStatusName" id="extendStatusName"/><input type="hidden" name="status" id="status"/><input type="hidden" name="extendStatus" id="extendStatus"/></td>
						</tr>			
					</tbody>
				</table>
			</form>
		 </div>
	  </div>
	  
      <div data-options="region:'center',border:false">
    	 <div class="easyui-tabs" id="mainTab" data-options="fit:true,plain:true,border:false" >
			<div title="单据明细" >
			     <@p.datagrid id="dataDGridJG"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
			      onClickRowEdit="false" pagination="true"  rownumbers="true"   pageSize="20" showFooter="true"
		          	 columnsJsonList="[
	           		  {field : 't', checkbox:true, width : 30, notexport:true,},
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
 	                  {field : 'billNo', title : '单据编码', width :180,align:'center'},	
 	                  {field : 'sendDate', title : '发货日期', width : 80,align:'center'},	
	                  {field : 'receiveDate', title : '验收日期', width : 90,align:'center'},
	                  {field : 'receiveQty', title : '数量', width : 100,align:'right',halign:'center'},	
	                  {field : 'purchaseAmount', title : '采购金额', width : 100,align:'right',halign:'center'},
					  {field : 'currencyName', title : '币别', width : 100,align:'right',halign:'center'},
	                  {field : 'standardCurrencyName', title : '本位币', width : 100,align:'right',halign:'center'},	
	                  {field : 'exchangeRate', title : '汇率', width : 150,align:'left',halign:'center'},	
	                  {field : 'totalAmount', title : '本位币金额', width : 100,halign:'center'},
	                  {field : 'companyName',title : '公司名称',width : 180,align:'left',halign:'center'},
	                  {field : 'orderUnitName', title : '货管单位', width : 80,align:'center'},	
	                  {field : 'organName', title : '管理城市', width : 100,halign:'center'},
	                  {field : 'orderNo',title : '采购订单号',width : 180,align:'left',halign:'center'},
	                  {field : 'balanceNo', title : '结算单号', width : 80,align:'center'}
	                
	                ]" 
            	 />
			</div>
			
			<div title="单据汇总" >
				<@p.datagrid id="sumDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
				 onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
		           columnsJsonList="[
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
 	                  {field : 'billNo', title : '单据编码', width : 180,align:'center'},	
 	                  {field : 'sendDate', title : '发货日期', width : 80,align:'center'},	
	                  {field : 'receiveDate', title : '验收日期', width : 90,align:'center'},
	                  {field : 'brandName', title : '品牌', width : 80,align:'center'},	
 	                  {field : 'buyerNo', title : '大类', width : 80,align:'center'},	
	                  {field : 'itemCode', title : '商品编码', width : 90,align:'center'},
                      {field : 'itemName', title : '商品名称', width : 80,align:'center'},	
 	                  {field : 'tagPrice', title : '牌价', width : 80,align:'center'},	
	                  {field : 'receiveQty', title : '数量', width : 100,align:'right',halign:'center'},	
	                  {field : 'purchasePrice', title : '采购价', width : 90,align:'center'},
	                  {field : 'purchaseAmount', title : '采购金额', width : 100,align:'right',halign:'center'},
					  {field : 'currencyName', title : '币别', width : 100,align:'right',halign:'center'},
	                  {field : 'currentPayableAmount', title : '本位币', width : 100,align:'right',halign:'center'},	
	                  {field : 'exchangeRate', title : '汇率', width : 150,align:'left',halign:'center'},	
	                  {field : 'totalAmount', title : '本位币金额', width : 100,halign:'center'},
	                  {field : 'companyName',title : '公司名称',width : 180,align:'left',halign:'center'},
	                  {field : 'orderUnitNameFrom', title : '货管单位', width : 80,align:'center'},	
	                  {field : 'organNameFrom', title : '管理城市', width : 100,halign:'center'},
	                  {field : 'orderNo',title : '采购订单号',width : 180,align:'left',halign:'center'},
	                  {field : 'twoLevelCategoryName',title : '二级大类',width : 180,align:'left',halign:'center'},
	                  {field : 'twoLevelCategoryName', title : '三级大类', width : 80,align:'center'},
	                  {field : 'balanceNo', title : '结算单号', width : 80,align:'center'}
                  ]" 
	             />
			</div>
	   	</div>
	  </div>
	  
	</div>
  </div>
</body>
</html>