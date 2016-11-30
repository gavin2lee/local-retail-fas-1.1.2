<!DOCTYPE html>
<head>
    <title>价格生效日期</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/priceEffectiveDate.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "priceSet.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "priceSet.clear()","type":0}
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
								<th>采购价生效日期：</th>
								<td>
									<input class="easyui-datebox ipt" name="purPriceEffDate" id="purPriceEffDate"/>
								</td>									
								<th></th>
								<td></td>		
								<th></th>
								<td></td>		
								<th></th>
								<td></td>		
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add-row", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-del-row", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="priceDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30,notexport:true},
				                  {field : 'id',hidden : 'true',align:'center',notexport:true},
				                  {field : 'purPriceEffDate',title : '采购价生效日期',width : 125,align:'center',halign:'center',
				                  	editor:{
				                  		type:'datebox',
				                  		options:{
				                  			required:true
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