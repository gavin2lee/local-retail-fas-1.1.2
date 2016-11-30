<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>发票模板设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/invoice_template_set.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/invoice_template_set/list.json" 
					  save_url="/invoice_template_set/save_all" 
					  update_url="/invoice_template_set/save_all" 
					  export_url="/invoice_template_set/do_fas_export"
					  export_title="发票模板设置信息导出"
					  export_type="fix"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="750" 
					  dialog_height="650"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.billToolBar type="invoice_template_set_listBar"/>
	</div>

	<div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<tbody>
								<tr>
								 <th>公司：</th>
								<td>
									<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName_" data-options="inputNoField:'companyNo_',inputNameField:'companyName_'"/>
									<input type="hidden" name="companyNo" id="companyNo_" style="margin-right:30px;"/>
								</td>
									<th>模板编号： </th>
									<td>
										<input class="ipt"  name="invoiceTempNo" id="invoiceTempNo_"  style="margin-right:30px;"/>
									</td>	
									
									<th>模板名称： </th>
									<td>
										<input class="ipt"  name="name" id="name_" />
									</td>							
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">            	    	
		      <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
			           rownumbers="true" singleSelect="true"  
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true},
			                  {field : 'companyName', title : '公司', width : 200, align : 'left'},
			                   {field : 'invoiceTempNo', title : '模板编号', width : 160, align : 'left'},		
			                  {field : 'name', title : '模板名称', width : 100, align : 'left'},	
			                  {field : 'createUser', title : '创建人', width : 90, align : 'left', notexport:true},
			                  {field : 'createTime', title : '创建时间', width : 140, align : 'left', notexport:true},		                 
			                  {field : 'updateUser', title : '修改人', width : 90, align : 'left', notexport:true},
			                  {field : 'updateTime', title : '修改时间', width : 140, align : 'left', notexport:true}
			              ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   	   fas_common.loadDetail(rowData);
			                   }
		                 }'
		                 loadSubGridUrl="/invoice_template_set_dtl/query_invoice_template_set_dtl"
		             	 subPagination="false"
		             	 subGridColumnsJsonList="[
		             	  	{field : 'categoryNo', title : '大类编码', width : 80, align : 'left'},
					      	{field : 'categoryName', title : '大类名称', width : 80, align : 'left'},	
					      	{field : 'typeModel', title : '规格型号', width : 80, align : 'left'},				      				
					      	{field : 'invoiceName', title : '商品开票名称', width : 120, align : 'left'},
					      	{field : 'qtyControlFlag',title:'是否启用数量控制',width:80, formatter:invoice_template_set.statusformatter},
					        {field : 'remark', title : '备注', width : 100, align : 'left'}						     
		             	]" 
                 />
			</div>
	 	</div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<div class="easyui-layout" data-options="fit:true,border:false" >
	     <form name="dataForm" id="dataForm" method="post"  class="pd10">
	     	 <div data-options="region:'north',border:false,height:90" class="pd15">
				<div class="easyui-panel" data-options="title:'发票模板设置',fieldset:true,fit:true,collapsible:false">
					<table cellpadding="1" cellspacing="1" class="form-tb">
					   <input type="hidden" name="id" id="invoiceTempNoId">
					   <input type="hidden" name="invoiceTempNo" id="invoiceTempNo"> 
					   <tr>
					      <th>公司：</th>
								<td>
									<input class="ipt easyui-company" name="companyName" id="companyName"  data-options="required:true" />
									<input type="hidden" name="companyNo" id="companyNo"/>
								</td>
					      <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模板名称：</td>
					       <td>
					      		<input class="easyui-validatebox ipt" style="width:140px" name="name" id="name"/>
					      </td>
					  
					   	  <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注：</td>
					      <td >
					      		<input class="ipt" style="width:140px" name="remark" id="remark" />
					      </td>
					   </tr>
					</table>
				</div>
			</div>
			<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
			    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
			    	<div title="大类对应关系">
			    		<div class="easyui-layout" data-options="fit:true">
				    		<div data-options="region:'north',border:false">
						    	<#-- 工具栏  -->
						    	<@p.billToolBar type="invoice_template_set_operaBar"/>
						    </div>
						    <div data-options="region:'center',border:false" id="dtlDataGrid">
							  <@p.datagrid id="dtlDataGridDiv"
					    			loadUrl="" saveUrl=""   defaultColumn=""
						    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false" selectOnCheck="false"
						    	    height="500" width="" onClickRowEdit="false" singleSelect="true"  fitColumns="false"
								    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
				           			columnsJsonList="[
							              {field : 'ck',checkbox:true,notexport:true},
							              {field : 'categoryNo',title:'大类编码',width:80, editor:{type:'searchbox',
						                  															  options:{
						                  															  	id:'categoryNo',
						                  															  	name:'categoryNo',
						                  															  	textId:'categoryName',
						                  															  	valueField:'categoryNo',
						                  															  	textField:'name',
						                  															  	title:'选择大类',
						                  															  	width:50,
						                  															  	isFrame:false,
						                  															  	required:true,
						                  															  	url:'/category/select?levelid=1',
						                  															  	queryUrl:'/category/list.json'
						                  															  }
						                  														}
						                  },
						                   {field : 'categoryName',title:'大类名称',width:70, editor:{type:'searchboxname',options:{
						                  																				id:'categoryName',
						                  																				name:'categoryName',
						                  																				width:'60px',
						                  																				readonly:true
						                  																			}
						                  																		}
						                  },
						                  {field : 'typeModel', title : '规格型号', width :70, editor:{type:'text',options:{
						                                                                                           width:70,
						                  																				id:'invoiceName',
						                  																				name:'invoiceName'
						                  																			}
						                  																		}
						                  },				                  
						                  {field : 'invoiceName',title:'商品开票名称',width:120, editor:{type:'text',options:{
						                                                                                                required:true,
						                                                                                                width:120, 
						                  																				id:'invoiceName',
						                  																				name:'invoiceName'
						                  																			}
						                  																		}
						                  },
						                  {field : 'qtyControlFlag',title:'是否启用数量控制',width:120, formatter:invoice_template_set.statusformatter,
													                  	editor:{
													                  		type:'combobox',
													                  		options:{
																				id:'qtyControlFlag',
																				width:110, 
																				name:'qtyControlFlag',
																				data:[{value:'0', text:'否'}, {value:'1', text:'是'}]																		
																			}
																		}
													                  },
						                  {field : 'remark',title:'备注',width:120, editor:{type:'text',options:{
						                  																				id:'remark',
						                  																				width:120, 
						                  																				name:'remark'
						                  																			}
						                  																		}
						                  },
						                 
					                 ]"   
						             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
										fas_common_editor.editRow(rowIndex, rowData);
						             }}' 
					        	/>
					        </div>
					     </div>
			        <div>
				</div>
			</div>
		 </form>	
   </div>
   </div>
</body>
</html>