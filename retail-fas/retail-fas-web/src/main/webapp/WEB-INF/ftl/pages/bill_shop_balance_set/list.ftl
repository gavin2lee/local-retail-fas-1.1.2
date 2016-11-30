<!DOCTYPE html>
<html>
<head>
<title>门店结算差异生成方式配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_shop_balance_set/BillShopBalanceSet.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	    <@p.billToolBar type="bill_shop_balance_set_setBar"/>
		 
		<#--搜索start-->
		<div class="search-div">
		     <form name="searchForm" id="searchForm" method="post">
       		 	<table class="form-tb">
       		 		<col width="100" />
            	 	<col />
            	 	<col width="100" />
            	 	<col />
	 				<tr>
	 				<tbody>
	 			    <th>公司名称：</th>
					<td>
						<input class="easyui-validatebox ipt easyui-company"  data-options="required:false" name="companyName" id="companyName"/>
						<input type="hidden" name="companyNo" id="companyNo"/>
					</td>									
				    <th>店铺：</th>
					<td>
						<input id="shopName" />
						<input type="hidden" name="shopNo" id="shopNo"/>
					</td>				
	 				</tr>
	 				</tbody>							
       		 	</table>
			</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="dtlDataGrid">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
			      <@p.billToolBar type="bill_shop_balance_set_operaBar"/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30, notexport:true},
								  {field : 'shortName', title : '店铺名称', align:'left',width : 160, halign:'center',
				                  	editor:{
				                  		type:'shop',
				                  		options:{
				                  			id:'shortName_',
				                  			name:'shortName',
				                  			inputNoField:'shopNo_',
				                  			required:true,
				                  			relationData:true
				                  		}
				                  	}
				                  },
				                  {field : 'shopNo', hidden:true, title : '店铺编码', align:'left',width : 80,halign:'center', 
				                  	editor:{type:'hiddenbox',options:{id:'shopNo_',name:'shopNo'}}
				                  }, 
				                  {field : 'companyName', title : '公司', align:'left',width : 200,halign:'center',
				                   editor:{type:'searchboxname',options:{id:'companyName_',name:'companyName',readonly:true}}},
				                  {field : 'companyNo',hidden:true, title : '公司编码', align:'left',width : 80,
				                   editor:{type:'hiddenbox',options:{id:'companyNo_',name:'companyNo'}}},
				                  {field:'balanceDiffType',title:'结算差异方式',width:110,halign:'center',
				                  	notexport:true,formatter:function(value, row){
										return row.balanceDiffTypeName;
									}, editor:{type:'fascombobox',options:{
												id:'balanceDiffType',
												name:'balanceDiffType',
						            			data:[{'value':1,'text':'按明细'},{'value':2,'text':'按促销活动'},{'value':3,'text':'按销售'},{'value':4,'text':'按商场结算码'}],
						            			valueField : 'value',
												textField : 'text',
						            			width:100,
												required:true,
												onSelect : function(data) {
													$('#balanceDiffType').val(data.value);
													$('#balanceDiffTypeName').val(data.text);
												}
											}
										}
				                  },		
				                  {field:'balanceDiffTypeName',title:'结算差异方式名称', width:100,hidden:true,editor:{type:'hiddenbox',options:{
													id:'balanceDiffTypeName',
													name:'balanceDiffTypeName'
												}
											}
								  },		                  
				                  {field : 'createUser',title : '建档人',width : 100,align:'center',halign:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center',halign:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center',halign:'center'}
			                ]" 
				          	jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
		                   	  		editor.editRow(rowIndex, rowData);
		                   	   }
			         }'/>
			</div>
		 </div>
	</div>
</body>