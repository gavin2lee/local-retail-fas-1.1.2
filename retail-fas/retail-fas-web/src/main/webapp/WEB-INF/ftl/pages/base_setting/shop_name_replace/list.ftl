<!DOCTYPE html>
<head>
    <title>店铺名称替换</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/shop_name_replace.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
	
	<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
	<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
	<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "shopReplace.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "shopReplace.clear()","type":0},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"shopReplace.doImport()","type":0}
	           ]
			/>
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
						 <tbody>
	       		 		   <tr>
								<th>公司： </th>
								<td>
									<input class="easyui-company ipt" id="companyName" data-options="multiple:true,inputWidth:130"/>
								    <input type="hidden" name="companyNos" id="companyNo"/>
								</td>									
								<th>店铺： </th>
								<td>
									<input class="easyui-shopCommon" id="shopName" data-options=""/>
									<input type="hidden" name="shopNos" id="shopNo"/>
								</td>	
								<th>品牌部： </th>
								<td>
									<input class="easyui-brandunit ipt" id="brandName" data-options="multiple:true,inputWidth:130"/>
									<input type="hidden" name="brandUnitNos" id="brandUnitNo"/>
								</td>		
								<th></th>
								<td>
								</td>			
	       		 			</tr>
	       		 		 </tbody>
	       		 	</table>
				</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-del", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="shopReplaceDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30,notexport:true},
				                  {field : 'id',hidden : 'true',align:'center',notexport:true},
				                  {field : 'shopNo', title : '店铺编码', align:'left',width : 90, 
				                  	editor:{type:'searchboxname',options:{id:'shopNo_',name:'shopNo',readonly:true}}
				                  },
				                  {field : 'shopName', title : '店铺名称', align:'left',width : 150,halign:'center',
										editor:{
											type:'shop',
											options:{
												id:'shopName_',
												name:'shopName',
												inputNoField:'shopNo_',
												required:true
											}
										}
									},
				                  {field : 'brandUnitNo', title : '品牌部编码', align:'left',width : 90, 
				                  	editor:{type:'searchboxname',options:{id:'brandUnitNo_',name:'brandUnitNo',readonly:true}}
				                  },
				                  {field : 'brandUnitName', title : '品牌部名称', align:'left',width : 150,halign:'center',
										editor:{
											type:'brandunit',
											options:{
												id:'brandUnitName_',
												name:'brandUnitName',
												inputNoField:'brandUnitNo_',
												required:true
											}
										}
									},
								  {field : 'replaceNo',title : '替换编码',width : 90,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[80]'
				                  		}
				                  	}
				                  },
				                  {field : 'replaceName',title : '替换名称',width : 200,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		required:true,
				                  		validType:'maxLength[80]'
				                  		}
				                  	}
				                  },
				                  {field : 'remark',title : '备注',width : 200,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[20]'
				                  		}
				                  	}
				                  },
				                  {field : 'createUser',title : '创建人',width : 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
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
</html>