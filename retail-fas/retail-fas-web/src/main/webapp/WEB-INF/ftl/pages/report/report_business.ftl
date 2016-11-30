<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部业务报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_business.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_business.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"report_business.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/report/export','总部业务报表',{type:'business'})", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value = "1"/>
						<table class="form-tb">
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
									<th>订货类型： </th>
									<td><input class="ipt" combobox="orderType" name="orderfrom"/></td>
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"/><input type="hidden" name="multiSupplierNo"></td>
									<th>性别： </th>
									<td><input class="ipt" combobox="gender" name="gender"/></td>
								</tr>	
								<tr>
									<th>类别： </th>
									<td><input class="ipt" multiSearch="twoLevelCategory"/><input type="hidden" name="multiCategoryNo"></td>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"/><input type="hidden" name="multiBrandNo"></td>
									<th>发出日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		        <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
			           rownumbers="true"  emptyMsg=""
			           columnsJsonList="[{field : 'orderfromName', title : '订货类型', width : 80},
						                {field : 'buyerName', title : '公司', width : 180},
						                {field : 'salerName', title : '供应商', width : 180},
						                {field : 'genderName', title : '性别', width : 80},
						                {field : 'categoryName', title : '类别', width : 80},
						                {field : 'brandName', title : '品牌', width : 80},
										{field : 'sendQty', title : '进货数量', width : 80,align:'right'},
										{field : 'sendAmount', title : '进货金额',  width : 80,align:'right'},
										{field : 'returnQty', title : '退残数量',  width : 80,align:'right'},
										{field : 'returnAmount', title : '退残金额',  width : 80,align:'right'},
										{field : 'balanceQty', title : '应付数量',  width : 80,align:'right'},
										{field : 'balanceAmount', title : '应付金额',  width : 80,align:'right'},
										{field : 'totalSendQty', title : '本年累计进货数量',  width : 110,align:'right'},
										{field : 'totalSendAmount', title : '本年累计进货金额',  width : 110,align:'right'},
										{field : 'totalReturnQty', title : '本年累计退残数量',  width : 110,align:'right'},
										{field : 'totalReturnAmount', title : '本年累计退残金额',  width : 110,align:'right'},
										{field : 'totalBalanceQty', title : '本年累计应付数量',  width : 110,align:'right'},
										{field : 'totalBalanceAmount', title : '本年累计应付金额',  width : 110,align:'right'}]" 
                 />	
			</div>
		</div>
	</div>
</body>
</html>