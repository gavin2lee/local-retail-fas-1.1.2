<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>收款条款</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_rece_term/WholesaleReceTerm.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json?isHq=${isHq}" 
					  save_url="/save_all" 
					  update_url="/save_all" 
					  export_url="/do_fas_export?isHq=${isHq}"
					  export_title="收款条款信息"
					  enable_url="/do_enable"
					  unable_url="/do_unable"
					  export_type="common"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="600" 
					  dialog_height="600"
					  primary_key="id"/>
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"dialog.toAdd()", "type":1},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"dialog.toUpdate()","type":2},
             {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action":"dialog.doEnable()","type":27},
             {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action":"dialog.doUnable()","type":100},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}
           ]
		/>
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
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th>条款编码：</th>
								    <td><input class="ipt" name="termNoLike" style="width:140px"/></td>
									<th>条款名称：</th>
		                            <td><input class="ipt"  name="nameLike" style="width:140px"/></td>
		                            <th>公司：</th>
		                            <td>
								      <#if isHq==true>
								      	<input class="easyui-company ipt"  name="companyName" id="companyNameCondition" 
		                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
		                           		<input type="hidden" name="companyNo" id="companyNoCondition"/>
								      </#if>
								      <#if isHq==null || isHq==''>
								      	<input class="easyui-company ipt"  name="companyName" id="companyNameCondition" 
		                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
		                           		<input type="hidden" name="companyNo" id="companyNoCondition"/>
								      </#if>
		                           		<!--
				                            <input class="easyui-company ipt" name="companyName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
				                            <input type="hidden" name="companyNo" id="companyNoCondition"/>
		                            	 -->
		                             </td>
		                            <th>状态：</th>
		                            <td><input class="easyui-combobox ipt"  name="status" style="width:140px" data-options="valueField:'id',textField:'text',data:[{id:'0',text:'已停用'},{id:'1',text:'已启用'}]"/></td>
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
			                  {field : 'termNo', title : '条款编码', width : 140, align : 'left',sortField:'term_no',sortable:true},
			                  {field : 'name', title : '条款名称', width : 140, align : 'left',sortField:'name',sortable:true},
			                  {field : 'companyName', title : '公司名称', width : 260, align : 'left',sortField:'company_name',sortable:true},
			                  {field : 'companyNo', title : '公司编码', width : 80, align : 'left',sortField:'company_no',sortable:true},
			                  {field : 'createUser', title : '创建人', width : 90, align : 'left',sortField:'create_user',sortable:true},
			                  {field : 'createTime', title : '创建时间', width : 140, align : 'center',sortField:'create_time',sortable:true},
			                  {field : 'updateUser', title : '修改人', width : 90, align : 'left',sortField:'update_user',sortable:true},
			                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center',sortField:'update_time',sortable:true},
			                  {field:'statusName',title : '注销状态',width : 70, align : 'center',sortField:'status',sortable:true}
			              ]" 
			              loadSubGridUrl="/wholesale_rece_term_dtl/list.json"
				          subPagination="false"
				          subGridColumnsJsonList="[
								{field:'advanceTypeName',title:'预收类型',width:120, notexport:true},
								{field:'advanceScale',title:'预收比例（%）',width:120, notexport:true},
								{field:'controlPointName',title:'控制点',width:120, notexport:true},
								{field:'remark',title:'描述',width:120, notexport:true}]"
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   	   dialog.toUpdate(rowData);
			                   }
		                 }'
                 />
			</div>
	 	</div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<div class="easyui-layout" data-options="fit:true,border:false" >
	     <form name="dataForm" id="dataForm" method="post" class="pd10">
	     	 <div data-options="region:'north',border:false,height:160" class="pd15">
					<div class="easyui-panel" data-options="title:'基本信息',fieldset:true,fit:true,collapsible:false">
						<table cellpadding="1" cellspacing="1" class="form-tb">
						   <input type="hidden" name="id" id="id">
						   <tr height="40">
						      <td width="110" align='right'>
						      	<span class="ui-color-red">*</span>条款编码：
						      </td>
						      <td width="140" align='left'>
						      		<input class="easyui-validatebox ipt" style="width:140px" name="termNo" id="termNo" data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']"/>
						      </td>
						      <td width="110" align='right'>
						      	<span class="ui-color-red">*</span>条款名称：
						      </td>
						       <td width="140" align='left'>
						      		<input class="easyui-validatebox ipt" style="width:140px" name="name" id="name" data-options="required:true,validType:['unNormalData','maxLength[20]']"/>
						      </td>
						   </tr>
						   <tr height="40">
						   	  <td width="110" align='right'>
						   	  	<span class="ui-color-red">*</span>公司名称：
						   	  </td>
						      <td width="140" align='left'>
						      <#if isHq==true>
						      	<input class="easyui-company ipt"  name="companyName" id="companyName" 
	                        	data-options="required:true, queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160"/>
	                       		<input type="hidden" name="companyNo" id="companyNo"/>
						      </#if>
						      <#if isHq==null || isHq==''>
						      	<input class="easyui-company ipt"  name="companyName" id="companyName" 
	                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160"/>
	                       		<input type="hidden" name="companyNo" id="companyNo"/>
						      </#if>
						      <!-- 
						      	<input class="easyui-company ipt" name="companyName" id="companyName" 
						      		data-options="required:true, inputWidth:150"/>
						      	<input type="hidden" name="companyNo" id="companyNo"/>
						      	-->
						      </td>
						   </tr>
						</table>
					</div>
				</div>
				<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
				    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
				    	<div title="条款明細">
				    		<div class="easyui-layout" data-options="fit:true">
				    			<div data-options="region:'north',border:false">
									<@p.toolbar id="dtltoolbar" listData=[
											 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
								             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","action" : "editor.deleteRow()", "type":0}
								           ]
								  	/>
								  	</div>
							 		<div data-options="region:'center',border:false" id="dtlDataGrid">
									  <@p.datagrid id="dtlDataGridDiv"
							    			loadUrl="" saveUrl=""   defaultColumn="" 
								    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false" selectOnCheck="false"
								    	    height="300" width="" onClickRowEdit="false" singleSelect="true"
										    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
						           			columnsJsonList="[
									              {field : 'ck',checkbox:true,notexport:true},
									              {field : 'controlPoint',title:'控制点',width:100, formatter:function(value, row){return row.controlPointName;}, 
								                  	editor:{
								                  		type:'combobox', 
							                  			options:{
															id:'controlPoint',
															name:'controlPoint',
															data:[{value:'0', text:'订货'}, {value:'2', text:'补货'}, {value:'1', text:'发货'}],
															valueField:'value',
															textField:'text',
															width:100,
															required:true,
															onSelect:function(data){
																$('#controlPointName').val(data.text);
																if(data.value == '0' || data.value == '2') {
																	$('#advanceType').val(0);
																	$('#advanceTypeName').val('按比例');
																	$('#advanceScale').numberbox({
																    	required:true
																    });
																	$('#advanceScale').removeAttr('readonly').removeClass('disabled');
																	$('#advanceScale').numberbox('validate');
																}else if (data.value == '1') {
																	$('#advanceType').val(1);
																	$('#advanceTypeName').val('按余额发货');
																	$('#advanceScale').numberbox({
																    	required:false
																    });
																	$('#advanceScale').addClass('disabled').attr('readonly', true);
																	$('#advanceScale').numberbox('setValue','');
																	$('#advanceScale').numberbox('validate');
																} 
															}
														}
									              	}
									              },
									              {field : 'controlPointName',hidden : true, title:'控制点名称',width:120,
									              	editor:{
									              		type:'hiddenbox',
														options:{
															id:'controlPointName',
															name:'controlPointName'
														}
													}
							                      },
									              {field : 'advanceType', hidden : true, title:'预收类型', width:100, formatter:function(value, row){return row.advanceType;},
									              	editor:{
									              		type:'hiddenbox',
														options:{
															id:'advanceType',
															name:'advanceType'
														}
													}
							                      },
									              {field : 'advanceTypeName', title:'预收类型名称',width:120,
									              	editor:{
									              		type:'readonlybox',
														options:{
															id:'advanceTypeName',
															name:'advanceTypeName'
														}
													}
							                      },
								                  {field : 'advanceScale',title:'预收比例（%）',width:100, 
								                  	editor:{
								                  		type:'fasnumberbox', 
								                  		options:{
								                  		    required:true,
								                  			id:'advanceScale', 
								                  			name:'advanceScale', 
								                  			width:100,
								                  			min:0,
								                  			max:100
								                  		}
								                  	}
								                  },
								                  {field : 'remark',title:'描述',width:180,editor:{type:'validatebox'}}
							                 ]"   
								             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
												editor.editRow(rowIndex, rowData);
								             }}' 
							        	/>
						        </div>
				        <div>
					</div>
				</div>
		 	</form>	
   		</div>
   </div>
         <#include  "/WEB-INF/ftl/pages/wholesale_rece_term/list_view.ftl" >
</body>
</html>