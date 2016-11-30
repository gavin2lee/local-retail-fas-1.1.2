<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客户-收款条款</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.1.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/customer_rece_rel/CustomerReceRel.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<body class="easyui-layout">
<@p.commonSetting search_url="/list.json?isHq=${isHq}" 
					  save_url="/insert" 
					  update_url="/update" 
					  export_url="/do_fas_export?isHq=${isHq}"
					  export_title="客户收款条款"
					  export_type="common"
					  enable_url="/do_enable"
					  unable_url="/do_unable"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="800" 
					  dialog_height="250"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
	         {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
	         {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"dialog.toAdd()", "type":1},
	         {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"dialog.toUpdate()","type":2},
	         {"id":"btn-add","title":"引用模板","iconCls":"icon-edit","action":"dialog.toAddByTemplate()","type":0},
	         {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action":"dialog.doEnable()","type":27},
	         {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action":"dialog.doUnable()","type":100},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}
	       ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
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
								    <td><input class="ipt" name="termNoLike"  style="width:140px"/></td>
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
			                            <!--<input class="easyui-company ipt" name="companyName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
			                            <input type="hidden" name="companyNo" id="companyNoCondition"/>
			                            -->
		                            </td>
		                            <th>客户：</th>
		                            <td>
			                            <input class="easyui-wholesale_zone_customer ipt" id="multiCustomerName" 
			                            data-options="multiple:'true',inputWidth:160,inputNoField:'multiCustomerNo',inputNameField:'multiCustomerName',valueField:'clientNo'"/>
			                            <input type="hidden" name="multiCustomerNo" id="multiCustomerNo"/>
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
		       <@p.subdatagrid id="dataGridDiv"  pagination="true" rownumbers="true"
						isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="10"
						columnsJsonList="[
							{field:'ck',checkbox:true,notexport:true},
							{field : 'companyName',title : '公司名称',width : 220,align : 'left',sortField:'company_name',sortable:true},
							{field : 'companyNo',title : '公司编码',width : 80,align : 'left',sortField:'company_no',sortable:true},
							{field : 'customerName',title : '客户名称',width : 220,align : 'left',sortField:'customer_name',sortable:true},
							{field : 'customerNo',title : '客户编码',width : 80,align : 'left',sortField:'customer_no',sortable:true},
							{field : 'termNo',title : '条款编码',width : 100, align : 'left',sortField:'term_no',sortable:true},
							{field : 'termName',title : '条款名称',width : 100, align : 'left',sortField:'term_name',sortable:true},
							{field : 'marginAmount',title : '保证金额度',width : 100,align : 'right',sortField:'margin_amount',sortable:true,exportType:'number'},
							{field : 'marginRemainderAmount',title : '保证金余额',width : 100,align : 'right',sortField:'margin_remainder_amount',sortable:true,exportType:'number'},
							{field : 'marginControlFlagText',title : '是否启用保证金控制',width : 120,align : 'center',sortField:'margin_control_flag',sortable:true},
							{field : 'createUser', title : '创建人', width : 90, align : 'left',sortField:'create_user',sortable:true},
			                {field : 'createTime', title : '创建时间', width : 140, align : 'center',sortField:'create_time',sortable:true},
			                {field : 'updateUser', title : '修改人', width : 90, align : 'left',sortField:'update_user',sortable:true},
			                {field : 'updateTime', title : '修改时间', width : 140, align : 'center',sortField:'update_time',sortable:true},
			                {field : 'statusName',title : '状态',width : 80,align : 'center',sortField:'status',sortable:true}
						]" 
						<#--update by tan.y
						  loadSubGridUrl="/customer_rece_rel_dtl/list.json"
				          subPagination="false"
				          subGridColumnsJsonList="[
							{field:'year',title:'年份',width:120,notexport:true},
							{field:'rebateAmount',title:'返利额度',width:120,notexport:true,exportType:'number'},
							{field:'hasRebateAmount',title:'已返利',width:120,notexport:true,exportType:'number'},
							{field:'remark',title:'描述',width:180,notexport:true}]"
							-->
						jsonExtend='{onDblClickRow:function(rowIndex, rowData){
							dialog.toUpdate(rowData);
					}}'/>
			</div>
			
			<div id="myFormPanel" class="easyui-dialog" data-options="closed:true" style="height:150px;"> 
	    		<div class="easyui-layout" data-options="fit:true,border:false" >
				   	<form name="dataForm" id="dataForm" method="post"  class="pd10">
		     			<div data-options="region:'north',border:false,height:160" class="pd15">
							<div class="easyui-panel" data-options="title:'客户-收款条款信息',fieldset:true,fit:true,collapsible:false">
								<table cellpadding="1" cellspacing="1" class="form-tb">
								   <input type="hidden" name="id" id="id">
		                           <tr>
		                           		<th width="110" align='right'>
		                           			<span class="ui-color-red">*</span>公司：
		                           		</th>
		                            	<td width="140" align='left'>
		                            	 <#if isHq==true>
									      	<input class="easyui-company ipt"  name="companyName" id="companyName" 
			                            	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160,callback:company_callback"/>
			                           		<input type="hidden" name="companyNo" id="companyNo"/>
									      </#if>
									      <#if isHq==null || isHq==''>
									      	<input class="easyui-company ipt"  name="companyName" id="companyName" 
			                            	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160,callback:company_callback"/>
			                           		<input type="hidden" name="companyNo" id="companyNo"/>
									      </#if>
		                            	<!-- 
		                                	<input class="easyui-company ipt"  name="companyName" id="companyName" data-options="required:true,inputWidth:200,inputNoField:'companyNo',inputNameField:'companyName',callback:company_callback" />
		                                	<input type="hidden" name="companyNo" id="companyNo"/>
		                                	-->
		                                </td>
		                                <th width="110" align='right'>
		                                	<span class="ui-color-red">*</span>客户：
		                                </th>
		                            	<td width="140" align='left'>
		                                	<input class="easyui-wholesale_zone_customer ipt" name="customerName" id="customerName" data-options="required:true,inputWidth:200,inputNoField:'customerNo',inputNameField:'customerName'"/>
		                                	<input type="hidden"  name="customerNo" id="customerNo"/>
		                                </td>
		                            </tr>
		                            <tr>
		                            	<th width="110" align='right'>
											<span class="ui-color-red">*</span>条款：
										</th>
		                            	<td width="140" align='left'>
		                            		<input class="ipt" name="termName" id="termName" data-options="required:true"/>
		                            		<input type="hidden" name="termNo" id="termNo" />
		                            	</td>
										<th width="110" align='right'>启用保证金控制：</th>
		                                <td width="140" align='left'>
		                                	<input type="checkbox" name="marginControlFlag" id="marginControlFlag" value="1"/>
		                                </td>
		                            </tr>
		                            <tr>
		                                <th width="110" align='right'>保证金额度：</th>
		                                <td width="140" align='left'><input class="easyui-numberbox ipt" style='width:190px' name="marginAmount" id="marginAmount" data-options="min:0,precision:2"/></td>
		                                <th width="110" align='right'>保证金余额：</th>
		                                <td width="140" align='left'><input class="easyui-numberbox ipt readonly" readonly="readonly" style='width:190px' name="marginRemainderAmount" id="marginRemainderAmount" data-options="min:0,precision:2"/></td>
		                            </tr>
								</table>
							</div>
						</div>
						<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;" hidden="true">
							<div class="easyui-layout" data-options="border:true,fit:true">
								<div data-options="region:'north',border:false">
									<@p.toolbar id="dtltoolbar" listData=[
											 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
								             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","action" : "editor.deleteRow()", "type":0}
								           ]
								  	/>
								</div>
								<div data-options="region:'center',border:false" id="dtlDataGrid">
									<@p.datagrid id="dtlDataGridDiv" emptyMsg = ""
										isHasToolBar="false"  onClickRowEdit="false"  pageSize="20" 
										columnsJsonList="[
										 	{field : 't', checkbox:true, width : 30, notexport:true},
											{field:'year',title:'年份',width:100, editor:{type:'combobox',options:{required:true, editable:false, data:yearData,valueField: 'value',textField: 'text'}}},
											{field:'rebateAmount',title:'返利额度',width:120,exportType:'number',editor:{type:'numberbox',options:{required:true}}},
											{field:'hasRebateAmount',title:'已返利',width:120,exportType:'number',editor:{type:'readonlytext'}},
											{field:'remark',title:'备注',width:200, editor:{type:'validatebox'}}
											]"
										jsonExtend='{
				                           onDblClickRow:function(rowIndex, rowData){
						                   	   editor.editRow(rowIndex, rowData);
						           		 }}'
									  />
								</div>
							</div>
						</div>
				</form>
	   		</div>
    </div>
      <#--include  "/WEB-INF/ftl/pages/customer_rece_rel/list_view.ftl" -->
      <#include  "/WEB-INF/ftl/pages/customer_rece_rel/select_customer_template.ftl" >
</body>
</html>