<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>发票统计表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/register_invoice/registerInvoice.js?version=${version}"></script>
</head>
<body class="easyui-layout">
					  
	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
		         {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
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
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
		                 <tbody>
			 				<tr> 
			 					<td align="right" >公司名称：</td>
								<td align="left">
									<input class="easyui-company"  name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>	
								</td>	
						     	<td align="right" width="110">销售日期：</td>
						     	<td >
						     		<input class="easyui-datebox"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/>
						     		&nbsp;&nbsp;-&nbsp;&nbsp;
						     	</td>
								<td><input class="easyui-datebox" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
						     </tr>		
						 </tbody>
						</table>
					</form>
				</div>
			</div>
					
				<!--列表-->  
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="发票统计"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
								{field : 'billNo',title : '单据编号',width : 150,align:'center'},
								{field : 'orderNo',title : '销售单号',width : 150,align:'center'},
				                {field : 'shopNo',title : '店铺编号',width : 100,align:'center'},
								{field : 'shopName',title : '店铺名称',width : 100,align:'left'},
								{field : 'terminalNumber',title : '终端号',width : 150,align:'center'},
								{field : 'shouldAmount',title : '应开票金额',width : 100,align:'right',exportType:'number'},	
								{field : 'actualAmount',title : '实际开票金额',width : 100,align:'right',exportType:'number'},	
								{field : 'diffAmount',title : '发票差异',width : 150,align:'center',exportType:'number'},
								{field : 'outDate',title : '销售日期',width : 100,align:'center'},	
								{field : 'remark',title : '发票差异原因',width : 180,align:'center'}
			              ]" 
			              
						 	<!--
						 	loadSubGridUrl="/register_invoice/findByBillNo.json"
			              subPagination="false"
			              subGridColumnsJsonList="[
			                  {field : 'orderNo', title : '销售订单号', width : 80,align:'left',align:'center'},
			                  {field : 'shopNo',title : '店铺编号',width : 50,align:'center'},
							  {field : 'shopName',title : '店铺名称',width : 80,align:'center'},	
							  {field : 'saleAmount',title : '销售金额',width : 50,align:'center'},
							  {field : 'outDate',title : '销售日期',width : 50,align:'center'}
							  ]"
						 	 -->
				          jsonExtend='{}'
                 />
			</div>
		</div>
	  </div>		
					  
</body>
</html>