<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区团购明细</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/groupon/grouponDtl.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_orderDtlSummary.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"_orderDtlSummary.clear()", "type":0},
             {"id":"btn-confirm","title":"财务确认","iconCls":"icon-ok", "action" : "_orderDtlSummary.financeConfirm()", "type":79},
             {"id":"btn-anti-confirm","title":"财务反确认","iconCls":"icon-remove", "action" : "_orderDtlSummary.financeAntiConfirm()", "type":90},
             {"id":"btn-remove","title":"导出","iconCls":"icon-aduit","action":"_orderDtlSummary.exportExcel()", "type":4}
             <!-- {"id":"mian_btn_build_biing","title":"发票登记","iconCls":"icon-build-some", "action" : "_orderDtlSummary.toCreateInvoiceReg()", "type":0}
             {"id":"mian_btn_print","title":"打印","iconCls":"icon-print","action":"","type":0}-->
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="billFlag" value="0">
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
									<th>结算公司： </th>
									<td>
										<input class="easyui-company  ipt"  name="companyName" id="companyNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json',required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:140"/>
										<input type="hidden" name="companyNo" id="companyNoId"/>
									</td>
									<th>开始时间： </th><td><input class="easyui-datebox ipt"  name="startDate" id="startDate" data-options="maxDate:'endDate'"/></td>
									<th>结束时间： </th><td><input class="easyui-datebox ipt"  name="endDate" id="endDate" data-options="minDate:'startDate'"/></td>
								</tr>
								<tr>
									<th>管理城市： </th><td><input class="easyui-organ ipt"  name="organName" id="organNameId" 
										 data-options="queryUrl: BasePath + '/organ/list.json?status=1&organLevel=1',inputNoField:'organNoId',inputNameField:'organNameId',inputWidth:140,multiple:true"/>
										<input type="hidden" name="organNo" id="organNoId"/></td>
									<th>品牌： </th>
									<td>
										<input class="easyui-brand  ipt"  name="brandName" id="brandNameId" 
										 data-options="queryUrl: BasePath + '/brand/list.json?status=1',inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:140,multiple:true"/>
										<input type="hidden" name="brandNo" id="brandNoId"/>
									</td>
									<th>大类： </th>
									<td><input class="easyui-categorybox ipt" name="categoryNo" id="categoryNoId" data-options="inputWidth:140,multiple:true"/>
									</td>
									
									<th>确认状态： </th>
									<td><input class="ipt" name="confirmStatus" id="confirmStatus"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="dtlDataGrid" loadUrl="" saveUrl=""   defaultColumn=""   title=""
			           isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
					   rownumbers="true" singleSelect="true"  showFooter="true"
			           columnsJsonList="[
			           		  {field:'ck',checkbox:true,notexport:true},
			           		  {field : 'typeFlag', title : '标识', hidden : 'false'},
			                  {field : 'shopNo', title : '店铺编号/客户编号', width : 200, align : 'left'},
			                  {field : 'shopName', title : '店铺名称/客户名称', width : 200, align : 'left'},
			           		  {field : 'totalQty', title : '数量', width : 80, align : 'left',exportType:'number'},
			           		  {field : 'tagPriceAmount', title : '牌价额', width : 110, align : 'left',exportType:'number'},
			                  {field : 'salePriceAmount', title : '现价额', width : 110, align : 'left',exportType:'number'},
			                  {field : 'settleAmount',title : '结算额',width : 110, align : 'left',exportType:'number'},
			                  {field : 'allAmount',title : '总金额',width : 110, align : 'left',exportType:'number'},
				  			  {field : 'couponAmount',title : '现金券',width : 110, align : 'left',exportType:'number'},
				  			  {field : 'cashAmount',title : '现金',width : 110, align : 'left',exportType:'number'}
							  ]" 
			           loadSubGridUrl="/groupon_summary/order_query"
		               subPagination="true" 
		               subSingleSelect="false"
		               subGridColumnsJsonList="[
		                	  {field:'ck',checkbox:true,notexport:true},
		               		　{field : 'orderNo', title : '订单编号', width : 380, align : 'left'},
		               		  {field : 'financeConfirmFlagStr',title : '财务确认',width : 60, align : 'left'},
		               		  {field : 'bizTypeName',title : '业务类型',width : 150, align : 'left'},
			                  {field : 'itemCode',title : '商品编码',width : 350, align : 'left'},
		  			  		  {field : 'itemName',title : '商品名称',width : 350, align : 'left'},
				  			  {field : 'qty',title : '数量',width : 50, align : 'left',exportType:'number'},
				  			  {field : 'brandName',title : '品牌',width : 80, align : 'left'},
				  			  {field : 'categoryName',title : '大类',width : 80, align : 'left'},
				  			  {field : 'organName',title : '管理城市',width : 100, align : 'left'},
				  			  {field : 'tagPrice',title : '牌价',width : 90, align : 'left',exportType:'number'},
				  			  {field : 'salePrice',title : '现价',width : 90, align : 'left',exportType:'number'},
				  			  {field : 'settlePrice',title : '结算价',width : 90, align : 'left',exportType:'number'},
				  			  {field : 'amount', title : '总金额', width : 100,exportType:'number'},
				  			  {field : 'couponPrice', title : '现金券', width : 100,exportType:'number'},
				  			  {field : 'prefPrice', title : '现金', width : 100,exportType:'number'},
				  			  {field : 'invoiceNo',title : '开票号',width : 380, align : 'left'},
				  			  {field : 'invoiceDate',title : '开票日期',width : 140, align : 'left'},
				  			  {field : 'createUser',title : '制单人',width : 100, align : 'left'},
				  			  {field : 'createTime',title : '制单时间',width : 140, align : 'left'}
			            ]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>