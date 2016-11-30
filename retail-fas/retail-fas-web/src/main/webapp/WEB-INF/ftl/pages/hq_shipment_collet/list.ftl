<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>出货统计表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_shipment_collet/hq_shipment_collet.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>

</head>
<body class="easyui-layout">
		<div data-options="region:'north',border:false" class="toolbar-region">
		   <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"hqShipmentCollet.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"hqShipmentCollet.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"hqShipmentCollet.exportExcel()", "type":0}
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
									<th><span class="ui-color-red">*</span>地区公司 ： </th>
									<td>
										<input class="easyui-company ipt" name="buyerName" id="buyerName" data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',
										inputNoField:'buyerNo',inputNameField:'buyerName',inputWidth:130,multiple:true,required:true"  />
									    <input type="hidden" name="buyerNo" id="buyerNo"/>
									</td>
									<th><span class="ui-color-red">*</span>供应商：</th>
		                            <td><input class="easyui-supplier"  name="supplierName" id="supplierName" data-options="inputWidth:130,multiple:true,required:true"/>
		                            <input type="hidden" name="supplierNo" id="supplierNo"/>
		                            </td>
									<th>年月：</th>
						       		<td>
						       		 	<input class="easyui-validatebox easyui-datebox ipt" name="startDate" id="startDate" data-options="dateFmt:'yyyyMM'"/> 
						       		</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;</th>
						    		<td ><input class="easyui-datebox ipt" name="endDate" id="endDate" data-options="dateFmt:'yyyyMM'"/> </td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="hqShipmentColletDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
					rownumbers="true"  singleSelect="true" 
			           columnsJsonList="[			           
							{field : 'supplierName', title : '供应商', width : 200,align:'left'},               
							{field : 'zoneName', title : '地区', width : 100},                                                                                                                                                                                                                             
							{field : 'buyerName', title : '地区公司', width : 200}, 
							{field : 'yearMonths', title : '年月', width : 80},
							{field : 'totalQty01', title : '鞋-数量', width : 100,align:'left',halign:'center'},
							{field : 'totalAmount01', title : '鞋-金额', width : 100,align:'left',halign:'center'}, 
							{field : 'totalQty02', title : '服-数量', width : 100,align:'left',halign:'center'}, 
							{field : 'totalAmount02', title : '服-金额', width : 100}, 
							{field : 'totalQty03', title : '配-数量', width : 100},      
							{field : 'totalAmount03', title : '配-金额', width : 100},
							{field : 'totalQty04', title : '其他-数量', width : 100},      
							{field : 'totalAmount04', title : '其他-金额', width : 100},
							{field : 'qty', title : '数量合计', width : 80},
							{field : 'zoneAmount', title : '地区额', width : 80},
							{field : 'amount', title : '中间计算额', width : 80},                                                                                                                                                                                                                                
							{field : 'supplierAmount', title : '厂商额', width : 80,align:'left',halign:'center',hidden : 'true'},  
							{field : 'zoneAddAmount', title : '返利额', width : 80,align:'left',halign:'center'},
							{field : 'lossAmount', title : '盈亏额', width : 80,align:'left',halign:'center'}]" 
                 />
             
			</div>
		</div>
</div>
</body>
</html>