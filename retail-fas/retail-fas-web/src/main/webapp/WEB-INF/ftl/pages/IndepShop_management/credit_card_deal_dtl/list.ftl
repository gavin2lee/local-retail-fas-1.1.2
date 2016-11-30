<!DOCTYPE HTML>
<html>
<head>
<title>银联刷卡交易明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/credit_card_deal_dtl/creditCardDealDtl.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<@p.commonSetting search_url="/self_shop_credit_card_deal_dtl/list.json" 
					  del_url="/self_shop_credit_card_deal_dtl/save" 
					  datagrid_id="dataGridDiv" 
					  upload_Form="uploadForm"
					  search_form_id="searchForm" 
					  import_url="/self_shop_credit_card_deal_dtl/upload"
					  import_btn="btn-import"
					  export_url="/self_shop_credit_card_deal_dtl/do_fas_export"
					  export_title="银联刷卡交易明细导出"
					  export_type="common"
					  primary_key="id"/>
					  
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":3},
             {"id":"btn-import-main","title":"导入","iconCls":"icon-import","action":"creditCardDealDtl.importOperation()","type":6},
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
								<th>客户账号： </th>
								<td >
									<input class="ipt"  iptSearch="card"  name="cardNumber" id="cardNumber" data-options="required:true" />
								</td>
								<th>终端号：</th>
						     	<td>
						     		<input class="ipt"  iptSearch="card"  name="terminalNumber" id="terminalNumber" data-options="required:true" />
						     	</td>
								<th>交易日期：</th>
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
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="银行卡交易明细"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30, notexport : true},
				                {field : 'id',hidden : 'true',align:'center', notexport : true},
				                {field : 'terminalNumber',title : '终端号',width : 100,align:'center'},	
								{field : 'cardNumber',title : '客户账号',width : 150,align:'right'},	
								{field : 'dealTime',title : '交易时间',width : 150,align:'center'},	
								{field : 'seqNo',title : '流水号',width : 100,align:'center'},	
								{field : 'remark',title : '摘要',width : 120,align:'center'},	
								{field : 'amount',title : '消费金额',width : 100,align:'right', exportType:'number'},
								{field : 'actualIncomeAmount',title : '实收金额',width : 100,align:'right', exportType:'number'},		
								{field : 'rebateAmount',title : '回扣费',width : 80,align:'right', exportType:'number'},
								{field : 'givenbank',title : '发卡行',width : 100,align:'left'},
								{field : 'realityDealTime',title : '实际交易时间',width : 150,align:'center'},
								{field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                {field : 'createTime',title : '建档时间',width : 150,align:'center'}		               
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
    	</div>
   </div>
</body>