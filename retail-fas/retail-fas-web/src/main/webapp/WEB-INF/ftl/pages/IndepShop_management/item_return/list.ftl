<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>退款汇总表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/common/shopManagement_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/item_return/ItemReturnRecords.js?version=${version}"></script>
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
							<tr height='33'>
								<th>公司	： </th>
								<td colspan="3">
									<input class="easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />	
								</td>
								<td align="right" width="110">日期：</td>
						     	<td >
						     		<input class="easyui-datebox"  name="startOutDate" id="startOutDate" data-options="required:true,maxDate:'endOutDate'"/>
						     		&nbsp;&nbsp;-&nbsp;&nbsp;
						     	</td>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="endOutDate" id="endOutDate" data-options="required:true,minDate:'startOutDate'"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
	    	
	    	<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopNo',title : '店铺编码',width : 70,align:'center'},
								{field : 'shopName',title : '店铺名称',width : 150,align:'center'},
								{field : 'mallName',title : '商场名称',width : 150,align:'center'},
								{field : 'brandUnitName',title : '品牌',width : 80,align:'center'},
								{field : 'oldOutDate',title : '消费日期',width : 100,align:'center'},
								{field : 'outDate',title : '系统退货日期',width : 100,align:'center'},	
								{field : 'creditCardAccount',title : '客户账号',width : 150,align:'center'},	
								{field : 'fullOpen',title : '全部/部分',width : 80,align:'center'},
								{field : 'oldAllAmount',title : '原订单总金额',width : 100,align:'center',exportType:'number'},	
								{field : 'allAmount',title : '总退款金额',width : 100,align:'center',exportType:'number'},
								{field : 'payName',title : '退款方式',width : 80,align:'center'},	
								{field : 'amount',title : '退款金额',width : 100,align:'center',exportType:'number'},
								{field : 'actualReturnAmount',title : '实际退款金额',width : 100,align:'center',exportType:'number'},
								{field : 'actualReturnTime',title : '实际退款日期',width : 100,align:'center'}	
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	    	
    	</div>
    </div>	
</body>