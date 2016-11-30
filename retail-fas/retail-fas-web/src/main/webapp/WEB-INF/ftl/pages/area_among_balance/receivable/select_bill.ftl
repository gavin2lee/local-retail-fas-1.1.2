<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算单选单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_among/billSelect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceNoFlag" id="balanceNo" value="balanceNoFlag"/>
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
									<th><span class="ui-color-red">*</span>调出公司： </th>
									<td>
										<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="salerNo" id="salerNoId"/>
									</td>
									<th><span class="ui-color-red">*</span>结算期间： </th>
									<td>
										<input class="easyui-datebox easyui-validatebox ipt"  name="balanceStartDate" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"  readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>&nbsp;&nbsp;至： </th>
									<td>
										<input class="easyui-datebox easyui-validatebox ipt"   name="balanceEndDate" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'"  readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>结算日： </th>
									<td>
										<input class="easyui-datebox easyui-validatebox ipt"   name="balanceDate" id="balanceDate" data-options="required:true" readonly="true"/>
									</td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>调入公司： </th>
									<td>
										<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',required:true,inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
									<th>品牌部： </th>
									<td>
										<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" 
										data-options="multiple:true,inputNoField:'brandUnitNoId',inputNameField:'brandUnitNameId',inputWidth:130"/>
										<input type="hidden" name="brandUnitNo" id="brandUnitNoId"/>
									</td>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-combobox"   name="oneLevelCategoryName" id="oneLevelCategoryNameId" 
										    data-options="width:130,url: BasePath + '/category/get_biz?levelid=1&status=1',valueField : 'categoryNo',textField : 'name',multiple:true,
											onChange: function(rec){
												var val = $('#oneLevelCategoryNameId').combobox('getValues').join(',');
												$('#oneLevelCategoryNoId').val(val);
											}"/>
										<input type="hidden" name="oneLevelCategoryNo" id="oneLevelCategoryNoId"/>
									</td>
							    	<th>币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
									<td>
										 <input class="easyui-combobox"  name="currencyName" id="currencyNameId"  style="width:130px;"
											  data-options="valueField:'currencyCode',textField:'currencyName',width:'auto',url:BasePath + '/area_balance_common/getCurrency',
												   onSelect: function(rec){    
												   		 $('#currencyNameId').val(rec.name);   
												   		 $('#currencyId').val(rec.currencyCode);   
	       										 	}
       										 "/>
       									<input type="hidden" name="currency" id="currencyId"/>
									</td>
								</tr>		
								<tr>
									<th>调出管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organNoFromName" id="organNoFromNameId" 
										data-options="inputNoField:'organNoFromId',inputNameField:'organNoFromNameId',inputWidth:160"/>
										<input type="hidden" name="organNoFrom" id="organNoFromId" />
									</td>
								    <th>调出货管单位： </th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitNameFrom" id="orderUnitNameFromId" 
										data-options="inputNoField:'orderUnitNoFromId',inputNameField:'orderUnitNameFromId',inputWidth:130"/>
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromId"/>
									</td>
									<th>调入管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organName" id="organNameId" 
										data-options="inputNoField:'organNoId',inputNameField:'organNameId',inputWidth:130"/>
										<input type="hidden" name="organNo" id="organNoId" />
									</td>
									<th>调入货管单位：</th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitName" id="orderUnitNameId" 
										data-options="inputNoField:'orderUnitNoId',inputNameField:'orderUnitNameId',inputWidth:130"/>
										<input type="hidden" name="orderUnitNo" id="orderUnitNoId" />
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<#--列表-->
        	<div data-options="region:'center',border:false">
        		<div class="easyui-layout" data-options="fit:true,plain:true">
				 <div data-options="region:'north',border:false" >
				  <@p.toolbar id="toolbar" listData=[
						 {"id":"btn-clear","title":"清空表单","iconCls":"icon-empty","action" : "billSelect.clear()", "type":0},
						 {"id":"btn-search","title":"加载明细","iconCls":"icon-import","action" : "billSelect.search()", "type":0}
			           ]
					/>
				 </div>
				 
			  <div data-options="region:'center',border:false" >
		      <@p.datagrid id="selectBillDG"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"   pageSize="100" showFooter="true"
			           columnsJsonList="[
			              {field:'ck',checkbox:true,notexport:true},
			              {field : 'id',hidden:true},
						  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center',
						  		 formatter: function(value,row,index){
										if(row.zoneName == '合计'){
											return '合计';
										}
										return value;
								 }
						  },
						  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
		                  {field : 'brandName', title : '品牌', width : 100,align:'center'},	
		                  {field : 'oneLevelCategoryName', title : '大类', width : 60,align:'center'},	
						  {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
		                  {field : 'itemCode', title : '商品编码', width : 135,align:'left',halign:'center'},
		                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
		                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},
		                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},	
		                  {field : 'sendAmount', title : '出库金额', width : 100,align:'right',halign:'center'},
		                  {field : 'sendDate', title : '单据日期', width : 100,align:'center'},
		                  {field : 'zoneNameFrom', title : '调出大区', width : 80,align:'center',halign:'center'},
		                  {field : 'salerName', title : '调出公司', width : 200,align:'left',halign:'center'},
           				  {field : 'organNameFrom', title : '调出管理城市', width : 100,align:'center',halign:'center'},
		                  {field : 'orderUnitNameFrom', title : '调出货管单位', width : 100,align:'center',halign:'center'},
           				  {field : 'sendStoreName', title : '发货仓', width : 100,align:'left',halign:'center'},
		                  {field : 'zoneName', title : '调入大区', width : 80,align:'center',halign:'center',
		                  		formatter: function(value,row,index){
										if(row.zoneName == '合计'){
											return '';
										}
										return value;
								 }
		                  },
		                  {field : 'buyerName', title : '调入公司', width : 200,align:'left',halign:'center'},
             			  {field : 'organName', title : '调入管理城市', width : 100,align:'center',halign:'center'},
						  {field : 'orderUnitName', title : '调入货管单位', width : 100,align:'center',halign:'center'},
             			  {field : 'receiveStoreName', title : '收货仓', width : 100,align:'left',halign:'center'},
		                  {field : 'billTypeName', title : '单据类型', width : 80,align:'center'}
			           ]" 
                 />
			</div>
			</div>
			</div>
			
	 	</div>
	</div>
	
</body>
</html>