<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>分类汇总表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/sales_summary/salesSummary.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="organTypeNo">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"salesSummary.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"salesSummary.clear()", "type":0},
           	 {"title":"导出","iconCls":"icon-export","action":"salesSummary.exportTotal()","type":4}
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
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
		                 		<tr height='33'>
								<th><span class="ui-color-red">*</span>公司	： </th>
								<td>
									<input class="easyui-company ipt" name="companyName" id="companyName" data-options="multiple:true,required:true,inputWidth:130"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />
								</td>
								<th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
								<td>
									<input class="easyui-shopCommon" id="shopName"/>
						     		<input type="hidden"  name="shopNo" id="shopNo" />
								</td>
								<th><span class="ui-color-red">*</span>业务日期： </th>
									<td>
										<input class="easyui-datebox ipt"   name="saleStartDate" id="saleStartDate" data-options="maxDate:'saleEndDate',required:true"  />
									</td>
									<th>&nbsp;&nbsp;— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input class="easyui-datebox ipt" name="saleEndDate" id="saleEndDate" data-options="minDate:'saleStartDate',required:true"  />
								</td>
							</tr>
							<tr>
								<th>品牌： </th>
								<td>
									<input class="easyui-brand ipt"  name="brandName" id="brandName" data-options="inputWidth:130,multiple:true"/>
									<input type="hidden" name="brandNo" id="brandNo"/>
								</td>
								<th>业务类型： </th>
								<td>						
									<input class="easyui-combobox ipt" name="businessType" id="businessType" data-options="required:false,multiple:true" readonly/>		
								</td>
						<!--	<th>结算月：</th>
					       		<td>
					       		 	<input class="easyui-datebox ipt"  name="month" id="month" data-options="dateFmt:'yyyyMM'" /> 
					       		</td>-->	
					       		<th>&nbsp;&nbsp;&nbsp;&nbsp;商品编号： </th>
								<td>
									<input class="easyui-itemCommon" id="itemName" />
									<input type="hidden" name="itemSql" id="itemNo"/>
								</td>
								<th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:130,multiple:true"/>
								        <input type="hidden" name="organNo" id="organNo"/>
								    </td>
							</tr>
							<tr>
								<th>大类： </th>
								<td>
									<input class="easyui-combobox ipt" name="categoryNo" id="categoryNo" data-options="multiple:true,readonly:true"/>
								</td>
								<th>客户名称：</th>
	                            <td>
	                            	<input class="easyui-customer ipt" name="customerName" id="customerName" data-options="multiple:true,inputWidth:130"/>
	                            	<input type="hidden" name="customerNo" id="customerNo"/>
	                            </td>
							</tr>
						 </tbody>
					 </table>
				</form>
			</div>
		</div>
			<#--列表-->
			<div data-options="region:'center'">
				<@p.datagrid id="dtlDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
					rownumbers="true"  singleSelect="true" 
					columnsJsonList="[
						{field : 'companyName', title : '公司名称', width :180,align:'left',rowspan:'2'},
						{field : 'organName', title : '管理城市', width :90,align:'left',rowspan:'2'},
						{field : 'shopNo1', title : '店铺/客户编码', width : 90,rowspan:'2'},
						{field : 'shopNoReplace', title : '店铺替换编码', width : 100,rowspan:'2'},
						{field : 'shopName', title : '店铺/客户名称', width : 100,align:'left',halign:'center',rowspan:'2'},
						
						{field : 'bizType', title : '业务类型', width : 60,rowspan:'2'},
  						{field : 'brandUnitNo', title : '品牌部编码', width : 100,rowspan:'2'},
						{field : 'brandUnitName', title : '品牌部名称', width : 100,rowspan:'2'},
						{field : 'totalQty', title : '数量', width : 60, align:'right',rowspan:'2',exportType:'number'},
						{field : 'totalTagPrice', title : '牌价总金额', width : 80, align:'right',rowspan:'2',exportType:'number'},
						{field : 'totalAmount', title : '销售总金额', width : 80, align:'right',rowspan:'2',exportType:'number'},
						{field : 'totalAmountUnitCost', title : '单位成本总额',  align:'right',width : 90,rowspan:'2',exportType:'number'},
						{field : 'totalAmountRegionCost', title : '地区成本总额',  align:'right',width : 90,rowspan:'2',exportType:'number'},
						{field : 'totalAmountHeadquarterCost', title : '总部成本总额',  align:'right',width : 90,rowspan:'2',exportType:'number'}]"  />
			</div>
		</div>
	</div>
	
</body>
</html>