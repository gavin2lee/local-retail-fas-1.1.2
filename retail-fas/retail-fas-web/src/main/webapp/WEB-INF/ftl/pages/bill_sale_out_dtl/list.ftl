<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>批发销售明细表11</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<#-- 
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_sale_out_dtl/bill_sale_out_dtl.js?version=${version}"></script>
-->
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json?bizType=2" 
					  export_url="/do_fas_export?bizType=2"
					  export_title="批发销售明细表信息"
					  export_type="common"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm"/>
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
	     	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasDialogController.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_fasDialogController.clear()","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"_fasDialogController.exportExcel()","type":0}
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
									<th>结算主体： </th><td><input class="ipt"  name="companyName" id="companyName" /></td>
									<th>品牌： </th><td><input class="ipt"  name="brandName" id="brandName" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
			           rownumbers="true" singleSelect="true"
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true},
			                  {field : 'companyName', title : '结算主体', width : 100, align : 'left'},
			                  {field : 'billNo', title : '单据编号', width : 100, align : 'left'},
			                  {field : 'bizType', title : '业务类型', width : 90, align : 'left', formatter : function(){return '批发出库单';}},
			                  {field : 'customerName', title : '客户', width : 100, align : 'left'},
			                  {field:'brandName',title : '品牌',width : 80, align : 'left'},
		  			  		  {field:'refBillNo',title : '批发订单号',width : 100, align : 'left'},
				  			  {field:'itemNo',title : '商品编码',width : 100, align : 'left'},
				  			  {field:'itemName',title : '商品名称',width : 100, align : 'left'},
				  			  {field:'sendOutQty',title : '发货数量',width : 80, align : 'left'},
				  			  {field:'cost',title : '单价',width : 90, align : 'left'},
				  			  {field:'costAmount',title : '金额',width : 100, align : 'left'},
				  			  {field:'billDate',title : '单据日期',width : 100, align : 'left'},
				  			  {field:'billStatus',title : '单据状态',width : 80, align : 'left'}
			                 ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  
			              }}'
                 />
			</div>
	 	</div>
	</div>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_sale_out_dtl/BillSaleOutDtl.js?version=${version}"></script>
</body>
</html>