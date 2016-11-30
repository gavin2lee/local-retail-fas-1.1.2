<!DOCTYPE HTML>
<html>
<head>
<title>销售明细表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/shop_sale_dtl/shopSale_dtl.js?version=${version}"/>"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/shop_sale_detail/list.json" 
					  del_url="/shop_sale_detail/save" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="800" 
					  dialog_height="400"
					  export_url="/shop_sale_detail/do_fas_export"
					  export_title="销售明细导出"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
		                 <tbody>
			 				<tr> 
						    	<td align="right" >公司名称：</td>
								<td align="left">
									<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:200,required:true"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>	
								</td>
						     	<td align="right" >店铺名称：</td>
								<td >
									<input id="shopName" style="width: 170px" class="easyui-shopCommon" disabled="disabled" />
									<input type="hidden" name="shopNo" id="shopNo"/>
								</td>
								<td align="right" >单据编号：</td>
								<td >
									<input type="text"  name="orderNo" id="orderNo" data-options=inputWidth:200" />
								</td>
						     	<td align="right" width="110">销售日期：</td>
					    		<td align="right">
					    			<input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
						     </tr>		
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="店铺信息"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopNo',title : '店铺编码',width : 80,align:'center'},	
				                {field : 'shopName',title : '店铺名称',width : 150,align:'left'},	
								{field : 'itemNo',title : '商品编码',width : 150,align:'center'},	
								{field : 'itemName',title : '商品名称',width : 180,align:'left'},	
								{field : 'qty',title : '数量',width : 50,align:'right', exportType:'number'},
								{field : 'tagPrice',title : '牌价',width : 100,align:'right', exportType:'number'},
								{field : 'tagPriceAmount',title : '牌价额',width : 100,align:'right', exportType:'number'},
								{field : 'salePrice',title : '现价',width : 80,align:'right', exportType:'number'},
								{field : 'salePriceAmount',title : '现价额',width : 80,align:'right', exportType:'number'},
								{field : 'settlePrice',title : '结算价',width : 100,align:'right', exportType:'number'},
								{field : 'dealAmount',title : '明细结算总金额',width : 100,align:'right', exportType:'number'},
								{field : 'amount',title : '整单结算总金额',width : 100,align:'right', exportType:'number'},
								{field : 'orderBillType',title : '单据类型',width : 100,align:'center'},	
								{field : 'outDate',title : '单据日期',width : 100,align:'center'},	
								{field : 'orderNo',title : '单据编号',width : 150,align:'center'},	
								{field : 'oldOrderNo',title : '原单号',width : 150,align:'center'},
								{field : 'remark',title : '备注',width : 200,align:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>