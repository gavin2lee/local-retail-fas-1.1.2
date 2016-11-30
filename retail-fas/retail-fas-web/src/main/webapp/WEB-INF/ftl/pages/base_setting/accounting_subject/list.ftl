<!DOCTYPE html>
<head>
    <title>科目设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/accounting_subject.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/accounting_subject/list.json?sort=subject_level&order=asc" 
					  save_url="/base_setting/accounting_subject/insert" 
					  update_url="/base_setting/accounting_subject/update" 
					  del_url="/base_setting/accounting_subject/save" 
					  enable_url="/base_setting/accounting_subject/do_enable"
					  unable_url="/base_setting/accounting_subject/do_unable"
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="330" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
	             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":1},
	             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":2},
	             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":3},
	             {"id":"btn-enable","title":"启用","iconCls":"icon-lock","type":27},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-unlock","type":100}
	           ]
			/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
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
		       		 			<th>公司名称：</th>
		       		 			<td>
		    						<input class="easyui-company ipt" name="companyName" id="companyNameCondition"
		    							data-options="inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:180"
		    						/>
		       		 				<input type="hidden" name="companyNo" id="companyNoCondition"/>
		       		 			</td>
		       		 			<th>科目编码：</th>
		       		 			<td><input class="easyui-validatebox ipt" name="subjectCode" id="subjectCodeCodeCondition"/></td>
		       		 			<th>科目名称：</th>
		       		 			<td><input class="easyui-validatebox ipt"  name="subjectName" id="subjectNameCondition"/></td>
		       		 			<th>科目类型：</th>
		       		 			<td><input class="easyui-combobox ipt"  name="subjectType" id="subjectTypeCondition"/></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true"  
			               checkOnSelect="true"
				           rownumbers="true" 
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'subjectLevel',title : '科目级次',width : 80,align:'center'},
				                  {field : 'subjectCode',title : '科目编码',width : 100,align:'center'},
				                  {field : 'subjectName',title : '科目名称',width : 100,align:'center'},
				                  {field : 'subjectType',title : '科目类型',width : 80,align:'center',formatter:accountingSubject.formatSubjectType},
				                  {field : 'balanceOrient',title : '余额方向',width : 80,align:'center',
				                  formatter:function(value,row,index){
									if (row.balanceDirection==1){
										return '借';
									} else if(row.balanceDirection==2){
										return '贷';
									}else{
										return '';
									}
				                  }}, 
				                  {field : 'currency',title : '外币币种',width : 100,align:'left',halign:'center'},
				                  {field : 'companyName',title : '公司名称',width : 250,align:'left',halign:'center'},
				                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
				                  {field : 'createUser',title : '创建人',width : 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                              fas_common.loadDetail(rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:350,height:300" > 
		<form  id="dataForm" method="post">
			<input type="hidden" id="id" name="id" />
			<table  class="form-tb">
				<col width="100" />
			    <col />
			    <tbody>
				<tr>
					<th>
					 <span class="ui-color-red">*</span>科目编码：</th>
					<td>
						<input class="easyui-numberbox" name="subjectCode" id="subjectCodeId"  data-options="required:true,validType:['unNormalData','maxLength[12]']" /> 
				    </td>
				</tr>
				<tr>
					<th>
					<span class="ui-color-red">*</span>科目名称：</th>
					<td>
						<input class="easyui-validatebox"  name="subjectName" id="subjectNameId" data-options="required:true,validType:['unNormalData','chinese']" />
					</td>
				</tr>
				<tr>
					<th>
					<span class="ui-color-red">*</span>科目级次：</th>
					<td>
						<input class="easyui-numberbox" name="subjectLevel" id="subjectLevelId" data-options="required:true,max:4,min:1,validType:['unNormalData','maxLength[1]']"/>
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>科目类别：</th>
					<td>
						<input class="ipt"  name="subjectType" id="subjectTypeId" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>余额方向：</th>
					<td>
					    <input type="radio"  name="balanceDirection"  value="1"/>借
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio"  name="balanceDirection"  value="2"/>贷
					</td>
				</tr>
				<tr>
					<th>外币核算：</th>
				    <td>
						<input type="text" name="currency" id="currencyId"/>
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>公司名称：</th>
					<td>
						<input class="easyui-company ipt" readonly="true" name="companyName" id="companyNameId" 
						data-options="required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:130"/>
						<input type="hidden" name="companyNo" id="companyNoId"/>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>