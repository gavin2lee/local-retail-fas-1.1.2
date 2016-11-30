<!DOCTYPE html>
<head>
    <title>刷卡统计</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/common/shopManagement_common.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/credit_card_census/creditCardCensus.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/self_shop_credit_card_deal_dtl/creditCard_census" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm"
					  export_url="/self_shop_credit_card_deal_dtl/do_fas_export"
					  export_title="刷卡统计导出"  
					  primary_key="id"/>


	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0}
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
						     	<td align="right" width="110">店铺名称：</td>
						     	<td align="left" width="140">
						     		<input class="easyui-shop" name="shopName" id="shopName"  
						     			data-options="inputWidth:200,multiple:true,required:true"/>
						     		<input type="hidden"  name="shopNo" id="shopNo" />
						     	</td>
						     	<th>日期：</th>
					    		<td ><input class="easyui-datebox"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/>&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;</td>
								<td><input class="easyui-datebox" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
						     </tr>		
						 </tbody>
						</table>
					</form>
				</div>
			</div>
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="刷卡统计"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopName',title : '店铺名称',width : 150,align:'center'},
				                {field : 'terminalNumber',title : '终端号',width : 150,align:'center'},	
				                {field : 'cardNumber',title : '客户账号',width : 200,align:'right'},	
								{field : 'times',title : '交易次数',width : 100,align:'right'},
								{field : 'totalAmount',title : '交易总金额',width : 100,align:'right'}	
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>