<!DOCTYPE html>
<html>
<head>
<title>供应商信息配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/supplier_rate_set.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	    <@p.billToolBar type="supplier_rate_set_setbar"/>
		 
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
	 			    <th>供应商编码：</th>
					<td>
						<input class="easyui-supplier ipt"  data-options="required:false" name="supplierName" id="supplierName"/>
						<input type="hidden" name="supplierNo" id="supplierNo"/>
					</td>									
	 				</tbody>							
       		 	</table>
			</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="dtlDataGrid">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
			      <@p.billToolBar type="supplier_rate_set_operaBar"/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30, notexport:true},
								  {field : 'supplierName', title : '供应商名称', align:'left',width : 200,halign:'center',
				                  	editor:{
					                    	type:'supplier',
					                    	options:{
					                    		id:'supplierName_',
					                    		name:'supplierName',
					                    		inputNoField:'supplierNo_',
					                  			required:true,
					                  			relationData:true,
					                    	 }
					                     }
				                  }, 
				                  {field : 'supplierNo', title : '供应商编码', align:'left',width : 80,halign:'center', 
					                     editor:{
					                  		type:'searchboxname',
					                  		options:{
					                  			id:'supplierNo_',
					                  			name:'supplierNo',
					                  			readonly:true
					                  		}
				                  		}
				                   },
								  {field:'vatRate',title:'增值税', width : 100,align:'left',
									  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			required:true,
				                  			 precision:4
				                  		}
				                	}
								  },
								  {field:'currencyName',title:'币种名称', width : 100,align:'left',
									  	editor:{
											type:'currency',
					                  		options:{
					                    		id:'currencyName_',
					                    		name:'currencyName',
					                    		inputNoField:'currencyCode_',
					                  			required:true,
					                  			relationData:true,
					                    	 }
					                  	}
								  },
								  {field:'currencyCode',title:'币种编码', width : 100,align:'left',
									  	editor:{
											type:'searchboxname',
					                  		options:{
					                    		id:'currencyCode_',
					                    		name:'currencyCode',
					                  			readonly:true
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
		                   	  		supplierRateSetEditor.editRow(rowIndex, rowData);
		                   	   }
			         }'/>
			</div>
		 </div>
	</div>
</body>