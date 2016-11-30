<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>现金存入明细</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/cash_deal_dtl/cash_deal_dtl.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<@p.commonSetting search_url="/cash_transaction_dtl/list.json" 
					  del_url="/cash_transaction_dtl/save" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/cash_transaction_dtl/do_fas_export"
					  export_title="现金存入明细导出"
					  primary_key="id"/>
	
<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":0},
             {"id":"btn-import-main","title":"导入","iconCls":"icon-import","action":"cashDealDtl.importOperation()","type":6},
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
							<tr height='33'>
								<td align="right" width="110">商场名称：</td>
								<td align="left" width="140"><input class="easyui-validatebox ipt easyui-mall" name="mallName" id="mallName"  data-options="inputWidth:200"/>
								<input type="hidden"  name="mallNo" id="mallNo" 	/>	
								<td align="right" width="110">店铺名称：</td>
						     	<td align="left" width="140">
						     		<input class="easyui-validatebox ipt easyui-shop" name="shopName" id="shopName"  data-options="inputWidth:200"/>
						     		<input type="hidden"  name="shopNo" id="shopNo" />
						     	</td>
						     	<td align="right" width="110">存现账号： </td>
								<td colspan="3">
									<input class="ipt"  iptSearch="card"  name="cardNumber" id="cardNumber" data-options="required:true" />
								</td>
								<td align="right" width="110">存现日期：</td>
					    		<td ><input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="现金存入明细"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	  {field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
								{field : 'mallName',title : '商场名称',width : 150,align:'left'},
								{field : 'shopName',title : '店铺名称',width : 150,align:'left'},
								{field : 'cardNumber',title : '存现账号',width : 200,align:'right'},	
								{field : 'depositAmount',title : '存现金额',width : 150,align:'right',exportType:'number'},	
								{field : 'depositCashTime',title : '存现日期',width : 100,align:'center'},	
				                {field : 'createUser',title : '建档人',width : 100,align:'left'}, 
				                {field : 'createTime',title : '建档时间',width : 150,align:'center'}
			              ]" 
				          jsonExtend='{onDblClickRow:function(rowIndex, rowData){
			           	  		 //fas_common.loadDetail(rowData);
			             }}' 
                 />
			</div>
	 	</div>
	</div>
   
</body>
</html>