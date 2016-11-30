<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>供应商款号关税设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/supplier_tariff_set/supplierTariffSet.js?version=${version}"></script>
</head>
<body class="easyui-layout">
		<div data-options="region:'north',border:false">
		      <@p.toolbar id="toolbar" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog1.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog1.clear()","type":0},
		             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "dialog1.exportExcel()","type":4}
    	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"dialog1.doImport()","type":0}
		           ]
				/>
			<!--搜索start-->
			<div class="search-div">
			      <form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
						 	<th>供应商编码：</th>
							<td>
								<input class="easyui-supplier ipt"  data-options="multiple:true,required:false" name="supplierName" id="supplierName"/>
								<input type="hidden" name="supplierNo" id="supplierNo"/>
							</td>	
							<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			
		<#--列表-->
		<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
				<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
            
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="supplierDataGrid"  loadUrl="" saveUrl="" defaultColumn=""   
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			                  	  {field : 'ck',checkbox:true,notexport:true},
			                  	  {field : 'id',hidden:true,notexport:true},
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
			                  	  {field : 'tariffRate', title : '关税率', align:'left',width : 100, 
			                  	  	editor:{
				                  		type:'numberbox',
				                  		options:{
					                  			required:true,
					                  			precision:4
					                  		}
				                  	}
				                  },
				                  {field : 'styleNo',title : '款号',width: 100,align:'left',
					                  editor:{
											 type:'styleNo',
											 options:{
											 	required:true,
						                  		validType:'maxLength[30]'
											  }
											}
								  },
				                  {field : 'effectiveDate',title : '生效日',width: 100,align:'center',
					                  editor:{
											 type:'datebox',
											 options:{
											 	required:true
											  }
											}
								  },
				                  {field : 'createUser',title : '创建人',width: 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width: 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width: 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width: 150,align:'center'}
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