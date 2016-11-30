<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算单选单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_other_stock_out/billSelect.js?version=${version}"></script>
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
									<th><span class="ui-color-red">*</span>地区公司： </th>
									<td>
										<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
										data-options="required:true,inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160,isDefaultData:false"/>
										<input type="hidden" name="salerNo" id="salerNoId"/>
									</td>
									<th><span class="ui-color-red">*</span>结算期间： </th>
									<td>
										<input class="easyui-datebox easyui-validatebox ipt"  name="balanceStartDate" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"  readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>&nbsp;&nbsp;至： </th>
									<td>
										<input class="easyui-datebox easyui-validatebox ipt"   name="balanceEndDate" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>结算日： </th>
									<td>
										<input class="easyui-datebox easyui-validatebox ipt"   name="balanceDate" id="balanceDate" data-options="required:true" readonly="true"/>
									</td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>总部公司： </th>
									<td>
										<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole',required:true,inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
									<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
									<td>
   										<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" 
										data-options="multiple:true,inputNoField:'brandUnitNoId',inputNameField:'brandUnitNameId',inputWidth:130"/>
										<input type="hidden" name="brandUnitNo" id="brandUnitNoId"/>
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
									<th></th>
									<td></td>
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
			          		 	formatter:function(value,row){
			              		if(row.buyerName=='合计'){
			              			return '合计';
			              		}	
			              		    return value;
			              	  }},
			      			  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
			                  {field : 'brandName', title : '品牌', width : 100,align:'center',halign:'center'},	
			                  {field : 'oneLevelCategoryName', title : '大类', width : 60,align:'center'},	
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
 							  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
			                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},	
			                  {field : 'sendQty', title : '数量', width : 80,align:'right',halign:'center'},
			                  {field : 'sendAmount', title : '金额', width : 100,align:'right',halign:'center'},
			                  {field : 'sendDate', title : '单据日期', width : 100,align:'center',halign:'center'},
			                  {field : 'buyerName', title : '总部公司', width : 200,align:'left',halign:'center',
			          		 	formatter:function(value,row){
			              		if(row.buyerName=='合计'){
			              			return '';
			              		}	
			              		    return value;
			              	  }},
			                  {field : 'salerName', title : '地区公司', width : 200,align:'left',halign:'center'},
			                  {field : 'organNameFrom', title : '管理城市', width : 80,align:'center',halign:'center'},
  			                  {field : 'orderUnitNameFrom', title : '货管单位', width : 80,align:'center',halign:'center'},
			                  {field : 'billTypeName', title : '单据类型', width : 100,align:'center',halign:'center'}
		                 ]" 
                 />
				</div>
			</div>
			</div>
	 	</div>
	</div>
	
</body>
</html>