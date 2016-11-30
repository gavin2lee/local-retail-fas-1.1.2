<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部结账日设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/headquarterPeriod.js?version=${version}"></script>
</head>
<body class="easyui-layout">
		<div data-options="region:'north',border:false">
		      <@p.toolbar id="toolbar" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog.clear()","type":0},
 		             {"id":"btn-build","title":"批量生成","iconCls":"icon-build-some", "action" : "headquarterPeriod.toBatchAdd()","type":55},
		             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "dialog.exportExcel()","type":4}
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
						 	<tr>
							 	<th>公司名称：</th>
						 		<td>
						 			<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName"/>
						 			<input type="hidden" name="companyNo" id="companyNo"/>
						 		</td>
							 	 <th>品牌部： </th>
								<td>
								<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameIdCond" data-options="inputNoField:'brandUnitNoCond',inputNameField:'brandUnitNameIdCond',inputWidth:130"/>
							    <input type="hidden" name="brandUnitNo" id="brandUnitNoCond"/>
								</td>
							</tr>
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add-row", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-del-row", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7}
		           ]
				/>
            </div>
            
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="settlePeriodDataGrid"  loadUrl="" saveUrl="" defaultColumn=""   
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			                  	  {field : 'ck',checkbox:true,notexport:true},
			                  	  {field : 'id',hidden:true,notexport:true},
			                  	  {field : 'companyName', title : '公司名称', align:'left',width : 180, 
					                  editor:{
						                  type:'readonlybox',
						                  options:{
						                  	id:'companyName_',
						                  	name:'companyName',
						                  	required:true
						                  }
						              }
				                  },
			                  	  {field : 'companyNo', title : '公司编码', align:'left',width : 80, 
			                  	  	editor:{
				                  		type:'company',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'companyNo_',
				                  			name:'companyNo',
				                  			idField: 'companyNo',
											textField: 'companyNo',
											noField: 'name',
				                  			inputNoField:'companyName_',
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
				                  {field : 'supplierSettleTime',title : '总部结账日',width: 100,align:'center',
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
   
   <div id="myFormPanel" class="easyui-dialog" data-options="closed:true" > 
		<form  id="dataForm" method="post">
			<table  class="form-tb">
				<col width="100" />
				<col />
				<col width="100" />
				<col />
				<tbody>
				<tr>
				<th>公司名称：</th>
			 		<td>
			 			<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyNames" data-options="required:true,multiple:true,inputNoField:'companyNos',inputNameField:'companyNames',inputWidth:150" />
			 			<input type="hidden"  name="companyNo" id="companyNos"/>
			 		</td>
				</tr>
				<tr>
				 	 <th>品牌部： </th>
					<td>
					<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameIdConds" data-options="required:true,multiple:true,inputNoField:'brandUnitNoConds',inputNameField:'brandUnitNameIdConds',inputWidth:150"/>
				    <input type="hidden" name="brandUnitNo" id="brandUnitNoConds"/>
					</td>
				</tr>
				<tr>
					<th>结账日：</th>
				    <td><input class="easyui-validatebox easyui-datebox ipt"  name="supplierSettleTime" id="supplierSettleTime" data-options="required:true" /></td>
				</tr>
			
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>