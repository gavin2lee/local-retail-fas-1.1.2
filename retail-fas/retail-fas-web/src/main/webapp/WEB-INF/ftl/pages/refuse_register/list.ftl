<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>拒付登记</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
  
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/refuse_register/refuseRegister.js?version=${version}"></script>
</head>
<body class="easyui-layout">
		<div data-options="region:'north',border:false">
		      <@p.toolbar id="toolbar" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog1.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog1.clear()","type":0},
            		 {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"dialog1.doImport()","type":6},
		             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "dialog1.exportExcel()","type":4}
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
							 	<th>公&nbsp;&nbsp;司： </th>
						 		<td>
						 			<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName"/>
						 			<input type="hidden" name="companyNo" id="companyNo"/>
						 		</td>
			        			<th>供应商： </th>
								<td>
									<input class="easyui-supplier  ipt"  name="salerName" id="supplierNameId" data-options="inputNoField:'salerNoId',inputNameField:'supplierNameId',inputWidth:150"/>
									<input type="hidden" name="salerNo"  id="salerNoId"/>
								</td>
							 	<th>&nbsp;&nbsp;日&nbsp;&nbsp;期：</th>
							 	<td>
							 		<input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/>
							 	</td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/>
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
            
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl="" defaultColumn=""   
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
			                  	  {field : 'companyNo', title : '公司编码', align:'center',width : 80, 
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
								  {field : 'supplierName',title : '供应商名称',width : 140,align : 'center',
								     editor:{
						                  type:'readonlybox',
						                  options:{
						                  	id:'supplierName_',
						                  	name:'supplierName',
						                  	required:true
						              }
						             }
								  },
								  {field : 'supplierNo',title : '供应商编码',width : 80,align : 'center',
								  	editor:{
				                  		type:'supplier',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'supplierNo_',
				                  			name:'supplierNo',
				                  			idField: 'supplierNo',
											textField: 'supplierNo',
											noField: 'shortName',
				                  			inputNoField:'supplierName_',
				                  			required:true
				                  		}
				                  	}
								  },
								  {field : 'paymentDate',title : '日期',width : 120,align : 'center',
								   editor:{
											 type:'datebox',
											 options:{
											 	required:true
											  }
											}
								  },
								  {field : 'paymentAmount',title : '拒付金额',width : 80,align : 'center',
								  	editor:{
				                  		type:'numberbox',
				                  		options:{
									        precision:2
									    }
				                  	}
								  },
						          {field : 'remark',title : '备注',width: 80,align:'left',halign:'center',
						          	editor:{
				                  		type:'validatebox'
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