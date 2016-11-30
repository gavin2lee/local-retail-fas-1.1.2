<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>参数设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/param_set.js?version=${version}"></script>
</head>
<body>
<@p.commonSetting search_url="/list.json" 
					  save_url="/save_all" 
					  update_url="/save_all" 
					  del_url="/do_delete"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="700" 
					  dialog_height="600"
					  primary_key="id"/>
					  
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'参数设置'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	 <@p.toolbar id="toolbar" listData=[
							 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
				             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
				             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"dialog.toAdd()", "type":1},
				             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"dialog.toUpdate()","type":2},
				             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"dialog.doDel()","type":3}
				           ]
						/>
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="searchForm" name="searchForm"  method="post">
		                        	 <table class="form-tb">
		                        	    <col width="100" />
		                        	 	<col />
		                        	 	<col width="100" />
		                        	 	<col />
		                                <tbody>
		                                    <tr>
		                                    	<th>参数编码： </th>
		                                    	<td>
		                                    		<input class="ipt"  name="paramCode" id="paramCodeCond" />
		                                    	</td>
		                                    	<th>参数名称： </th>
		                                    	<td>
		                                    		<input class="ipt"  name="paramName" id="paramNameCond" />
		                                    	</td>
		                                    </tr>
		                                </tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                <div data-options="region:'center',border:false" style="height:350px;">
		                	 <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   
						              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
							           rownumbers="true" singleSelect="true"  
							           columnsJsonList="[
							                  {field : 't', checkbox:true, width : 30, notexport:true},
							                  {field : 'paramCode', title : '参数编码', width : 150, align : 'left',halign:'center'},
							                  {field : 'paramName', title : '参数名称', width : 200, align : 'left',halign:'center'},
							                  {field : 'controlLevel', title : '控制级别', width : 80,formatter:controlFormatter},
							                  {field : 'remark', title : '备注', width : 150, align : 'left',halign:'center'},
							                  {field : 'createUser', title : '创建人', width : 100, align : 'center'},
							                  {field : 'createTime', title : '创建时间', width : 140, align : 'center'},
							                  {field : 'updateUser', title : '修改人', width : 100, align : 'center'},
							                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'}
							              ]" 
								          jsonExtend='{
					                           onDblClickRow:function(rowIndex, rowData){
							                   	   dialog.toUpdate(rowData);
							                   }
						                 }'
						                 loadSubGridUrl="/param_dtl/query_param_dtl"
						             	 subPagination="false"
						             	 subGridColumnsJsonList="[
						             	  	{field : 'dtlValue', title : '参数值编码', width : 80, align : 'left', notexport:true},
									      	{field : 'dtlName', title : '参数值名称', width : 100, align : 'left', notexport:true},
									      	{field : 'isvalid',title:'是否有效',width:80,formatter:isvalidFormatter},
									      	{field : 'updateUser', title : '修改人', width : 100, align : 'left', notexport:true},
							                {field : 'updateTime', title : '修改时间', width : 140, align : 'center',notexport:true}
						             	]" 
				                 />
		                </div>
		              </div>
             	</div>
             	
             	<#-- 修改页面 -->
             	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				    <div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataForm" id="dataForm" method="post"  class="pd10">
					     	 <div data-options="region:'north',border:false,height:140" class="pd15">
								<div class="easyui-panel" data-options="title:'参数信息',fieldset:true,fit:true,collapsible:false">
									<table  class="form-tb">
									   <input type="hidden" name="id" id="settleCategoryId">
										<col width="100" />
									    <col />
									    <col width="100" />
									    <col />
									    <tbody>
									    <tr>
									     <th><span class="ui-color-red">*</span>参数编码：</th>
									     <td>
									      	<input class="easyui-validatebox ipt"  name="paramCode" id="paramCode"
									      	 data-options="required:true,validType:['maxLength[25]']"/>
									     </td>
									     <th><span class="ui-color-red">*</span>参数名称：</th>
									     <td>
									      	<input class="easyui-validatebox ipt"  name="paramName" id="paramName" 
									      	data-options="required:true,validType:['unNormalData','maxLength[60]']"/>
									     </td>
									   </tr>
									   <tr height="35">
									     <th>控制级别：</th>
									     <td>
									     	 <input class="easyui-combobox ipt"  name="controlLevel" id="controlLevel" 
									     	 		data-options="
															valueField: 'label',
															textField: 'value',
															width:160,
															data: [{
																label: '0',
																value: '通用'
															},{
																label: '1',
																value: '大区'
															},{
																label: '2',
																value: '公司'
															},{
																label: '3',
																value: '店铺'
															}]" />
									     </td>
									   	 <th>备注：</th>
									     <td>
									      	<input class="easyui-validatebox ipt" name="remark" id="remark" 
									      	 data-options="validType:'maxLength[100]'"/>
									     </td>
									   </tr>
									   </tbody>
									</table>
								</div>
							</div>
							<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
							    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
							    	<div title="参数值明细">
							    		<div class="easyui-layout" data-options="fit:true">
								    		<div data-options="region:'north',border:false">
										    	<#-- 工具栏  -->
										  		<@p.toolbar id="dtltoolbar" listData=[
														 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "action" : "editor.insertRow()", "type":0},
											             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","action" : "editor.deleteRow()", "type":0}
											           ]
												  />
										  	</div>
									 		<div data-options="region:'center',border:false" id="dtlDataGrid">
											  <@p.datagrid id="dtlDataGridDiv"
									    			loadUrl="" saveUrl=""   defaultColumn="" 
										    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="false"
										    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
												    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
								           			columnsJsonList="[
											              {field : 'ck',checkbox:true,notexport:true},
										                  {field : 'dtlValue',title:'参数值',width:80, align : 'left',
										                  		editor:{type:'validatebox',
          															  options:{
          															  	required:true
          															  }
          														}
          												  },
										                  {field : 'dtlName',title:'参数值名称',width:150,
										                  		editor:{type:'validatebox',
          															  options:{
          															  	required:true
          															  }
          														}
										                  },
										                  {field : 'isvalid',title:'是否有效',width:100,
										                        formatter:isvalidFormatter,
										                  		editor:{
										                  			type:'combobox',
											                  		options:{
											                  			id:'isvalid',
											                  			data: [{'value':'1', 'text': '有效'}, {'value':'0', 'text':'无效'}], 
											                  			valueField: 'value', textField: 'text',
											                  			required:true
											                  		}
          														}
										                  }
									                 ]"   
										             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
														editor.editRow(rowIndex, rowData);
										             }}' 
									        	/>
									        </div>
							        </div>
								</div>
							</div>
						 </form>	
				   </div>
			   </div>
			   
			   <#-- 查看页面 -->
			   <div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
				    <div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataFormView" id="dataFormView" method="post"  class="pd10">
					     	 <div data-options="region:'north',border:false,height:140" class="pd15">
								<div class="easyui-panel" data-options="title:'结算大类信息',fieldset:true,fit:true,collapsible:false">
									<table cellpadding="1" cellspacing="1" class="form-tb">
									   <input type="hidden" name="id" id="settleCategoryId">
									   <tr height="40">
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	分类编码：
									      </td>
									      <td width="140" align='left'>
									      		<input class="ipt disabled" style="width:140px" name="settleCategoryNo" readonly="true"/>
									      </td>
									      <td width="110" align='right'>
									      	<span class="ui-color-red">*</span>
									      	分类名称：
									      </td>
									       <td width="140" align='left'>
									      		<input class="ipt disabled" style="width:140px" name="name" readonly="true"/>
									      </td>
									   </tr>
									   <tr height="40">
									   	  <td width="110" align='right'>备注：</td>
									      <td colspan="7">
									      		<input class="ipt disabled" name="remark" style="width:99%" readonly="true"/>
									      </td>
									   </tr>
									</table>
								</div>
							</div>
							<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
							    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
							    	<div title="结算分类明细">
							    		<div class="easyui-layout" data-options="fit:true">
							    			<div data-options="region:'north',border:false">
										    	<#-- 工具栏  -->
										  		<@p.toolbar id="dtltoolbarView" listData=[
														 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "action" : "", "type":0},
											             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","action" : "", "type":0}
											           ]
												  />
										  	</div>
									 		<div data-options="region:'center',border:false">
											  <@p.datagrid id="dtlDataGridDivView"
									    			loadUrl="" saveUrl=""   defaultColumn="" 
										    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="false"
										    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
												    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
								           			columnsJsonList="[
										                  {field : 'name',title:'大类名称',width:170, align : 'left'},
										                  {field : 'updateUser', title : '修改人', width : 100, align : 'left'},
										                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'}
									                 ]"   
									        	/>
									        </div>
							        </div>
								</div>
							</div>
						 </form>	
				   </div>
			   </div>
			   
            </div>
        </div>
</div>
</body>
</html>