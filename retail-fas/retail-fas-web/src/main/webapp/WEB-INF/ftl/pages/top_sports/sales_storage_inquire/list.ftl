<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品销存查询</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/top_sports/salesStorageInquire.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "salesInquire.search()", "type":0},
	         {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "salesInquire.clear()","type":0},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "salesInquire.doExport()","type":4}
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
						    <th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
							<td>
								<input class="easyui-supplier ipt"  name="salerName" id="supplierNameId" 
								data-options="inputNoField:'supplierNoId',inputNameField:'supplierNameId',inputWidth:160"/>
								<input type="hidden" name="salerNo" id="supplierNoId" />
							</td>
							<th>发出日期： </th>
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
					</tbody>
				</table>
			</form>
		 </div>
	  </div>
	  
      <div data-options="region:'center',border:false">
    	 <div class="easyui-tabs" id="mainTab" data-options="fit:true,plain:true,border:false" >
			<div title="明细" >
			     <@p.datagrid id="dataDGridJG"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
			      onClickRowEdit="false" pagination="true"  rownumbers="true"   pageSize="20" showFooter="true"
		          	 columnsJsonList="[
	                  {field : 'billTypeName', title : '单据类型', width : 100,align:'center',halign:'center'},	
  	                  {field : 'billNo', title : '单据编码', width : 150,align:'left',halign:'center'},	
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
	                  {field : 'sendDate', title : '日期', width : 80,align:'center'},	
	                  {field : 'zoneName', title : '地区', width : 100,align:'center',halign:'center'},	
					  {field : 'organName', title : '管理城市', width : 100,align:'center',halign:'center'},
	                  {field : 'orderUnitName', title : '货管单位', width : 100,align:'center',halign:'center'},
                      {field : 'receiveStoreName', title : '机构', width : 150,align:'left',halign:'center'},	
					  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
	                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
	                  {field : 'brandName', title : '品牌', width : 80,align:'center',halign:'center'}	,
					  {field : 'categoryName', title : '大类', width : 80,align:'center',halign:'center'},
	                  {field : 'sendQty', title : '数量', width : 80,align:'right',halign:'center'},
	                  {field : 'tagPriceAmount', title : '牌价额', width : 100,align:'right',halign:'center'},
	                  {field : 'balanceNo', title : '折扣1', width : 80,align:'right',halign:'center'},
	                  {field : 'balanceNo', title : '折扣2', width : 80,align:'right',halign:'center'},
	                  {field : 'balanceNo', title : '指定价', width : 100,align:'right',halign:'center'},
					  {field : 'balanceNo', title : '结算金额', width : 100,align:'right',halign:'center'},
	                  {field : 'balanceNo', title : '终端销售金额', width : 150,align:'right',halign:'center'}
	                ]" 
            	 />
			</div>
			
			<div title="汇总" >
				<@p.datagrid id="sumDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
				 onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
		           columnsJsonList="[
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
	                  {field : 'sendDate', title : '年月', width : 80,align:'center'},	
	                  {field : 'skuNo', title : '期初数量', width : 100,align:'right',halign:'center'},	
	                  {field : 'skuNo', title : '期初牌价额', width : 100,align:'right',halign:'center'},	
					  {field : 'skuNo', title : '折扣', width : 100,align:'right',halign:'center'},
	                  {field : 'skuNo', title : '期初结算额', width : 100,align:'center',halign:'center'},
                      {field : 'skuNo', title : '到货数量', width : 150,align:'left',halign:'center'},	
					  {field : 'skuNo', title : '到货牌价额', width : 180,align:'left',halign:'center'},
	                  {field : 'skuNo', title : '折扣', width : 80,align:'center',halign:'center'},
	                  {field : 'skuNo', title : '到货结算金额', width : 100,align:'center',halign:'center'}	,
					  {field : 'skuNo', title : '销售数量', width : 100,align:'right',halign:'center'},
	                  {field : 'skuNo', title : '销售牌价额', width : 80,align:'center',halign:'center'},
	                  {field : 'skuNo', title : '折扣', width : 200,align:'right',halign:'center'},
	                  {field : 'skuNo', title : '销售结算金额', width : 80,align:'right',halign:'center'},
	                  {field : 'skuNo', title : '盘差数量', width : 80,align:'right',halign:'center'},
	                  {field : 'skuNo', title : '盘差牌价额', width : 120,align:'left',halign:'center'},
					  {field : 'skuNo', title : '折扣', width : 150,align:'left',halign:'center'},
	                  {field : 'skuNo', title : '盘差结算金额', width : 80,align:'center'},
	                  {field : 'skuNo',title : '扣项金额',width : 180,align:'left',halign:'center'},
	                  {field : 'skuNo', title : '结存数量', width : 100,align:'center',halign:'center'},
	                  {field : 'skuNo', title : '结存牌价额', width : 100,align:'center',halign:'center'},
	                  {field : 'skuNo', title : '结存结算金额', width : 150,align:'center',halign:'center'}
                  ]" 
	             />
			</div>
		
	   	</div>
	  </div>
	  
  </div>
</div>
	
</body>
</html>