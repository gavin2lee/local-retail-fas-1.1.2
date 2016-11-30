<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>已结算单据审核</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/top_sports/settledBillsAudit.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "billsAudit.search()", "type":0},
	         {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "billsAudit.clear()","type":0},
	         {"id":"btn-clear","title":"审核","iconCls":"icon-aduit", "action" : "billsAudit.audit()","type":0},
	         {"id":"btn-clear","title":"反审核","iconCls":"icon-aduit", "action" : "billsAudit.unAudit()","type":0},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "billsAudit.doExport('/settledBills_audit/export')","type":4}
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
							<th>总部公司： </th>
							<td>
								<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="buyerNo" id="buyerNoId"/>
							</td>
							<th>单据编号： </th>
							<td>
								<input class="easyui-validatebox ipt"   name="billNo" id="billNo" style="width: 150px;"/>
							</td>
							<th>结算号： </th>
							<td>
								<input class="easyui-validatebox ipt"   name="settlementNumber" id="settlementNumber" style="width: 150px;"/>
							</td>
							<th>结算单号： </th>
							<td>
								<input class="easyui-validatebox ipt"   name="balanceNo" id="balanceNo" style="width: 150px;" />
							</td>
						</tr>					
						<tr>
						    <th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
							<td>
								<input class="easyui-supplier ipt"  name="salerName" id="salerNameId" data-options="inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160"/>
								<input type="hidden" name="salerNo" id="salerNoId" />
							</td>
							<th>发出日期： </th>
							<td>
								<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="maxDate:'sendDateEnd'" style="width: 150px;" readonly="true"/>
							</td>
							<th>至： </th>
							<td>
								<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="minDate:'sendDateStart'" style="width: 150px;" readonly="true"/>
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
	  
      <div data-options="region:'center',border:false">
    	 <div class="easyui-tabs" id="mainTab" data-options="fit:true,plain:true,border:false" >
			<div title="单据汇总" >
				<@p.datagrid id="sumDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
				 onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
		           columnsJsonList="[
		              {field:'ck',checkbox:true,notexport:true},
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
	                  {field : 'billNo', title : '单据编码', width : 150,align:'left',halign:'center'},	
	                  {field : 'billTypeName', title : '单据类型', width : 80,align:'center'},	
	                  {field : 'orderTypeName', title : '订货类型', width : 100,align:'center',halign:'center'},
	                  {field : 'settlementNumber', title : '结算号', width : 100,align:'left',halign:'center'},	
	                  {field : 'auditStatusName', title : '审核状态', width : 80,align:'center',halign:'center'},
	                  {field : 'sendDate', title : '发出日期', width : 80,align:'center'},
					  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},
	                  {field : 'tagPriceAmount', title : '牌价额', width : 80,align:'right',halign:'center'},
	                  {field : 'supplierAmount', title : '厂商金额', width : 100,align:'right',halign:'center'},	
	                  {field : 'balanceAmount', title : '结算金额', width : 100,align:'right',halign:'center'},	
					  {field : 'diffAmount', title : '差异金额', width : 100,align:'right',halign:'center'},
	                  {field : 'brandName', title : '品牌', width : 80,align:'center',halign:'center'},
	                  {field : 'categoryName', title : '大类', width : 100,align:'center',halign:'center'}	,
	                  {field : 'supplierDiscount1', title : '厂商折扣1', width : 90,align:'right',halign:'center'},
	                  {field : 'supplierDiscount2', title : '厂商折扣2', width : 90,align:'right',halign:'center'},
	                  {field : 'balanceNo', title : '结算单号', width : 150,align:'left',halign:'center'},
					  {field : 'orderNo', title : '订单号', width : 125,align:'left',halign:'center'},
	                  {field : 'buyerName',title : '总部公司',width : 180,align:'left',halign:'center'}
                  ]" 
	             />
			</div>
		    
		    <div title="单据明细" >
			     <@p.datagrid id="dataDGridJG"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
			      onClickRowEdit="false" pagination="true"  rownumbers="true"   pageSize="20" showFooter="true"
		          	 columnsJsonList="[
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
	                  {field : 'billNo', title : '单据编码', width : 150,align:'left',halign:'center'},	
	                  {field : 'billTypeName', title : '单据类型', width : 80,align:'center'},	
	                  {field : 'orderTypeName', title : '订货类型', width : 80,align:'center',halign:'center'},
					  {field : 'orderNo', title : '订单号', width : 125,align:'left',halign:'center'},
	                  {field : 'settlementNumber', title : '结算号', width : 100,align:'left',halign:'center'},	
	                  {field : 'brandName', title : '品牌', width : 80,align:'center',halign:'center'},
	                  {field : 'categoryName', title : '大类', width : 100,align:'center',halign:'center'}	,
                      {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},	
					  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
	                  {field : 'sendDate', title : '发出日期', width : 90,align:'center'},
					  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},
	                  {field : 'tagPriceAmount', title : '牌价额', width : 100,align:'right',halign:'center'},
	                  {field : 'balanceAmount', title : '结算金额', width : 100,align:'right',halign:'center'},	
	                  {field : 'supplierDiscount1', title : '厂商折扣1', width : 90,align:'right',halign:'center'},
	                  {field : 'supplierDiscount2', title : '厂商折扣2', width : 90,align:'right',halign:'center'},
	                  {field : 'balanceNo', title : '结算单号', width : 150,align:'left',halign:'center'},
	                  {field : 'buyerName',title : '总部公司',width : 180,align:'left',halign:'center'}
	                ]" 
            	 />
			</div>
		     
	   	</div>
	  </div>
	  
  </div>
</div>
	
</body>
</html>