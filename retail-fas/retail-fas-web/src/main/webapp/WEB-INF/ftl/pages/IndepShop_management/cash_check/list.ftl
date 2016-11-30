<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>现金核对</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/common/shopManagement_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/cash_check/cashCheck.js?version=${version}"></script>
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
								<th>公司名称	： </th>
								<td colspan="3">
									<input class="easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />	
								</td>
								<td align="right" width="110">店铺名称：</td>
						     	<td align="left" width="140">
						     		<input class="easyui-validatebox ipt easyui-shop" name="shopName" id="shopName"  data-options="inputWidth:200"/>
						     		<input type="hidden"  name="shopNo" id="shopNo" />
						     	</td>
								<td align="right" width="110">存现日期：</td>
						     	<td >
						     		<input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/>
						     		&nbsp;&nbsp;-&nbsp;&nbsp;
						     	</td>
								<td><input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
	    	
	    	<div data-options="region:'center',border:false">
        	
        		<@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="存现核对"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
			           rownumbers="true" singleSelect="true"  
			           columnsJsonList="[
			                    {field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shortName',title : '店铺名称',width : 150,align:'left'},
								{field : 'depositCardNumber',title : '存现账号',width : 200,align:'left'},
								{field : 'startOutDate',title : '销售起始日期',width : 100,align:'center'},
								{field : 'endOutDate',title : '销售截止日期',width : 100,align:'center'},		
								{field : 'totalSysIncomeCash',title : '系统总现金收入',width : 150,align:'right',exportType:'number'},	
								{field : 'totalDepositAmount',title : '存现总金额',width : 150,align:'right',exportType:'number'},	
								{field : 'depositDiff',title : '存现差异',width : 150,align:'right',exportType:'number',styler:changeColor}
			              ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   }
		                 }'
		                 loadSubGridUrl="/cash_check/lookForChildList"
		             	 subPagination="false"
		             	 subGridColumnsJsonList="[
								{field : 'mallName',title : '商场名称',width : 100,align:'left'},
								{field : 'shortName',title : '店铺名称',width : 100,align:'left'},
								{field : 'depositCardNumber',title : '存现账号',width : 150,align:'left'},	
								{field : 'depositCashTime',title : '存现日期',width : 100,align:'center'},	
								{field : 'depositAmount',title : '存现单金额',width : 100,align:'right',exportType:'number'}	,
								{field : 'diffReason',title : '差异原因',width : 120,align:'left',exportType:'number',styler:changeColor}
		             	]" 
				   />
			</div>
	    	
	    	
	    	
    	</div>
    </div>	
</body>
