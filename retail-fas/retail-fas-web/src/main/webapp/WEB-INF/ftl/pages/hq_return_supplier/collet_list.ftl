<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>退供应商统计</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_return_supplier/hq_return_supplier.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.2.0.js?version=${version}"></script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	   <@p.toolbar id="toolbar_collect" listData=[
		 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"supplierCollet.search()", "type":0},
         {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"supplierCollet.clear()", "type":0},
         {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"supplierCollet.exportExcel()", "type":0}
       ]
       />
	</div>

	<div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true" id="subLayout">
		   <div data-options="region:'north',border:false">
	           <div class="search-div">
		      	<form name="searchForm" id="searchForm" method="post">
					<table class="form-tb">
						<col width="100" />
						<col />
						<col width="100" />
						<col />
						<col width="100" />
						<col />			
						<tbody>
							<tr>
								<th><span class="ui-color-red">*</span>发出地区： </th>
								<td >
									<input class="easyui-zonebox ipt"  style="width:160px;"
									data-options="required:true" name="zoneNo" id="zoneNo"/>
								</td>
								<th><span class="ui-color-red">*</span>发出公司： </th>
								<td>
								    <input class="easyui-company ipt" name="salerName" id="salerName" data-options="inputNoField:'salerNo', 
										inputNameField:'salerName',inputWidth:130,multiple:true,required:true"  />
									<input type="hidden" name="salerNo" id="salerNo"/>
								</td>
								<th>管理城市： </th>
								<td>
								    <input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:130,multiple:true"/>
							        <input type="hidden" name="organNo" id="organNo"/>
							    </td>
							</tr>
							<tr>
								<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
								<td>
									<input class="easyui-brand ipt" name="brandName" id="brandName" data-options="inputWidth:130,multiple:true"/>
									<input type="hidden" name="brandNo" id="brandNo"/>
								</td>
								<th>退货编号： </th>
								<td>
									<input class="easyui-validatebox ipt" name="orderNo" id="orderNo" />
								</td>
								<th><span class="ui-color-red">*</span>销售日期：</th>
					    		<td ><input class="easyui-validatebox easyui-datebox ipt"  name="outDateStart" id="outDateStart" data-options="required:true,maxDate:'outDateEnd'"/></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -- &nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="outDateEnd" id="outDateEnd" data-options="required:true,minDate:'outDateStart'"/></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center',border:false">		
				<div id="mainTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
					<div title="退残业务">
		<!--列表-->
		<div class="easyui-layout" data-options="fit:true,plain:true">
		<!--列表-->
    	<div data-options="region:'center',border:false">
	      <@p.datagrid id="returnSupplierCelletDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
				isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
				rownumbers="true"  singleSelect="true" 
		           columnsJsonList="[	
						{field : 'zoneName', title : '地区', width : 60}, 
						{field : 'organNameFrom', title : '管理城市 ', width : 80},                                                                                                                                                                                                                                      
						{field : 'salerName', title : '地区公司 ', width : 200},
						{field : 'brandUnitNo', title : '品牌 ', width : 80},                                                                                                                                                                                                                             
						{field : 'bizTypeName', title : '自营/批发', width : 80},
						{field : 'returnNo', title : '退残编号 ', width : 80,align:'left',halign:'center'}, 
						{field : 'reDate', title : '退残时间', width : 90, align:'right'}, 
						{field : 'totalQty01', title : '退残数量', width : 60, align:'right',exportType:'number'},   
						{field : 'totalAmount01', title : '退残金额', width : 90, align:'right',exportType:'number'}, 
						{field : 'sendDate', title : ' 确认时间', width : 90, align:'right'}, 
						{field : 'qty', title : '确认数量', width : 90, align:'right',exportType:'number'}, 
						{field : 'amount', title : '确认金额', width : 90, align:'right',exportType:'number'},
						{field : 'noAffirmDate', title : '不确认转销时间', width : 90, align:'right'},   
						{field : 'totalQty02', title : '未确认转销数量', width : 90, align:'right',exportType:'number'},
						{field : 'totalAmount02', title : '未确认转销金额 ', width : 90, align:'right',exportType:'number'},
						{field : 'applyDate', title : '给总部开票时间', width : 90, align:'right'},   
						{field : 'totalQty04', title : '给总部开票数量 ', width : 90, align:'right',exportType:'number'},
						{field : 'totalAmount04', title : '给总部开票金额', width : 90, align:'right',exportType:'number'},
						{field : 'totalQty03', title : '未转销数量 ', width : 90, align:'right',exportType:'number'},
						{field : 'totalAmount03', title : '未转销金额', width : 90, align:'right',exportType:'number'}]" 
             />
			</div>
		</div>
		</div>
		</div>		
		</div>
	 	</div>
	</div>
</body>
</html>