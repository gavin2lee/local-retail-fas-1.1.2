<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区员购销售表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/member_order_summary/OrderDtlSummary.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action" : "_orderDtlSummary.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "_orderDtlSummary.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "_orderDtlSummary.exportExcel()", "type":0},
           	 {"id":"mian_btn_build_biing","title":"发票登记","iconCls":"icon-build-some","type":0,"action":"_orderDtlSummary.toSelectBuyerName()"}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
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
									<td align="right" width="110">结算公司：</td>
									<td>
										<input class="easyui-company  ipt"  name="companyName" id="companyNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json',required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:140"/>
										<input type="hidden" name="companyNo" id="companyNoId"/>
									</td>
									<!--<td align="left" width="140">
										<input class="ipt" style="width:105px;" iptSearch="company"  name="companyName" id="companyName" />
										<input type="hidden" name="companyNo" id="companyNo"/>
									</td>-->	
									<!--<th>结算主体： </th><td><input class="ipt"  name="companyNo" id="companyNo" /></td>-->
									<th>日期： </th>
									<td><input class="easyui-datebox ipt"  name="startDate" id="startDate" data-options="maxDate:'endDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;---&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt"  name="endDate" id="endDate" data-options="minDate:'startDate'"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" showFooter="true"
			           columnsJsonList="[
			           		  {field : 't', checkbox:true, width : 30, notexport:true},
			           		  {field : 'billNo', title : '订单编号', width : 140, align : 'left'},
			           		  {field : 'shopNo', title : '店铺编号', width : 120, align : 'left'},
			                  {field : 'shopName', title : '店铺名称', width : 150, align : 'left'},
			                  {field : 'outDate', title : '单据日期', width : 100, align : 'left'},
			                  {field : 'totalQty', title : '数量', width : 60, align : 'left'},
			                  {field : 'tagPriceAmount', title : '牌价额', width : 80, align : 'left'},
			                  {field : 'salePriceAmount', title : '现价额', width : 80, align : 'left'},
			                  {field : 'settleAmount',title : '结算额',width : 80, align : 'left'},
			                  {field : 'reducePriceAmount', title : '减价额', width : 80, align : 'left'},
			                  {field : 'allAmount',title : '总金额',width : 80, align : 'left'},
		  			  		  {field : 'couponAmount',title : '现金券',width : 80, align : 'left'},
				  			  {field : 'cashAmount',title : '现金',width : 80, align : 'left'},
				  			  {field : 'invoiceNo',title : '发票号',width : 120, align : 'left'},
				  			  {field : 'invoiceDate',title : '发票日期',width : 100, align : 'left'}
			           ]"
			           loadSubGridUrl="/member_order_summary/order_query"
		               subPagination="true"
		               subGridColumnsJsonList="[
			                  {field : 'itemCode',title : '商品编码',width : 80, align : 'left'},
		  			  		  {field : 'itemName',title : '商品名称',width : 100, align : 'left'},
				  			  {field : 'qty',title : '数量',width : 80, align : 'left'},
				  			  {field : 'brandName',title : '品牌',width : 80, align : 'left'},
				  			  {field : 'categoryName',title : '大类',width : 80, align : 'left'},
				  			  {field : 'organName',title : '管理城市',width : 80, align : 'left'},
				  			  {field : 'tagPrice',title : '牌价',width : 80, align : 'left'},
				  			  {field : 'salePrice',title : '现价',width : 80, align : 'left'},
				  			  {field : 'settlePrice',title : '结算价',width : 80, align : 'left'},
				  			  {field : 'reducePrice',title : '减价',width : 80, align : 'left'},
				  			  {field : 'amount',title : '总价',width : 80, align : 'left'}
			            ]" 
                 />
			</div>
	 	</div>
	</div>
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true">
       <form name="showDataForm" id="showDataForm" method="post" >
           <table class="form-tb">
	          <col width="100">
	          <col/>
	          <tbody>
	        	  <tr>
                     <th>客户名称： </th>
                     <td><input class="easyui-validatebox easyui-customerAndCompany ipt" iptSearch name="buyerName" id="buyerName" 
								data-options="required:true,inputValueFeild:'buyerNo',inputNameFeild:'buyerName',inputWidth:134" />
								<input type="hidden" name="buyerNo" id="buyerNo"/>
					 </td>
                  </tr>
              </tbody>
           </table>
       </form>
	</div>
</body>
</html>