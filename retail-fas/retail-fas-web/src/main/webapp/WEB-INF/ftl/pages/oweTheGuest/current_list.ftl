<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>欠客销售跟踪明细表</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/oweTheGuest/oweTheGuest.js?version=${version}"></script>
	
	<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
	<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
	<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<#--最外层框-->
<div data-options="region:'center',border:false">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'本期欠客明细'">
		<div id="subLayoutId" class="easyui-layout" data-options="fit:true,border:false">
			<#--按钮-->
			<div data-options="region:'north',border:false" class="toolbar-region">
			   	  <@p.toolbar id="toolbar"  listData=[
			    	{"title":"查询","iconCls":"icon-search","action":"oweTheGuest.search()", "type":0},
			        {"title":"清空","iconCls":"icon-empty","action":"oweTheGuest.clear()", "type":0},
			        {"title":"导出","iconCls":"icon-export","action":"oweTheGuest.doExport('dataGridJG','/owe_the_guest/export','本期欠客明细表','current')", "type":4}
		   		  ]/>
			</div>
				
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
					<div data-options="region:'north',border:false">
					<div class="search-div">
		               <form name="mainForm" id="mainForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />	
							<col width="100" />
							<col />							
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>日期范围： </th>
									<td>
										<input class="easyui-datebox ipt"  name="dateStart" id="sendDateStartCond" data-options="required:true,maxDate:'sendDateEndCond'"  readonly="true"/>
									</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-color-red">*</span>至： </th>
									<td>
										<input class="easyui-datebox ipt"   name="dateEnd" id="sendDateEndCond" data-options="required:true,minDate:'sendDateStartCond'" readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>公司名称：</th>
									<td>
										<input class="easyui-company ipt" name="companyName" id="companyName"  
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:130,required:true"/>
										<input type="hidden" name="companyNo" id="companyNo"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandName"
										 data-options="multiple:true,inputNoField:'brandNo',inputNameField:'brandName',inputWidth:130"/>
										<input type="hidden" name="brandNo" id="brandNo"/>
									</td>
								</tr>	
								<tr>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th>
									<td>
										<input class="easyui-categorybox"   name="categoryNo" id="categoryNo" data-options="width:130"/>
									</td>
									<th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺：</th>
									<td>
                        				<input id="shopName"/>
                        				<input type="hidden" name="shopNo" id="shopNo"/>
									</td>
									<th>商品编码： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeId',inputNameField:'itemNameId',inputWidth:130"/>
										<input type="hidden" name="itemCode" id="itemCodeId" />
									</td>
									<th>销售单号：</th>
									<td>
										<input class="easyui-validatebox ipt" name="orderNo" id="orderNo" style="width:120px;"/>
									</td>
								</tr>				
							</tbody>
						</table>
					</form>
				</div>
			</div>
						
	        <div data-options="region:'center',border:false">
				<@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
	              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
		           rownumbers="true"   pageSize="20" showFooter="true"
			           columnsJsonList="[
			           		  {field : 'shopNo', title : '店铺编码', width : 80,align:'left',halign:'center'},
			           		  {field : 'shopName', title : '店铺名称', width : 135,align:'left',halign:'center'},
  			                  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center',halign:'center'},
			                  {field : 'brandName', title : '品牌', width : 80,align:'center',halign:'center'},
			                  {field : 'categoryName', title : '大类', width : 80,halign:'center'},
			                  {field : 'itemCode',title : '商品编码',width : 135,align:'left',halign:'center'},
			                  {field : 'itemName',title : '商品名称',width : 150,align:'left',halign:'center'},
			                  {field : 'sizeNo',title : '尺码',width : 60,align:'right',halign:'center'},
			                  {field : 'unitCost', title : '成本', width : 80,align:'right',halign:'center'},
               				  {field : 'saleDate', title : '销售日期', width : 100,align:'center',halign:'center'},
               				  {field : 'saleQty', title : '销售数量', width : 80,align:'right',halign:'center'},
           				      {field : 'unitCostSum', title : '成本总额', width : 100,align:'right',halign:'center'},
			                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},
							  {field : 'currPeriodOweQty', title : '本期欠客数量', width : 100,align:'right',halign:'center'},
			                  {field : 'organName', title : '管理城市', width : 80,align:'center'},
			                  {field : 'busiCityName', title : '经营城市', width : 80,align:'center'},
							  {field : 'companyName', title : '公司名称', width : 200,align:'left',halign:'center'},
							  {field : 'orderNo', title : '销售单号', width : 150,align:'left',halign:'center'},
							  {field : 'refundNo', title : '退款单号', width : 150,align:'left',halign:'center'}
			               ]" 
                    />
				</div>
			</div>
		  </div>
		</div>
	</div>
</div>

</body>
</html>