<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>加价规则匹配异常</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/ruleMatchFail.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/rule_match_fail/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="600" 
					  dialog_height="260"
					  export_url="/rule_match_fail/do_exports"
					  export_title="加价规则匹配异常导出"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
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
									<th>商品编码：</th>
							 		<td>
									  <input class="ipt easyui-item" name="itemCode" id="itemCodeCondition"
									  data-options="inputWidth:160,inputNoField:'itemNoCondition', inputCodeField:'itemCodeCondition'"/>
									  <input type="hidden" name="itemNo" id="itemNoCondition"/>
									</td>
							 		<th>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：</th>
							 		<td>
							 			<input class="easyui-combobox ipt" name="zoneNo" id="zoneNoCondition" />
							 		</td>
							 		<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
								 	<td>
								 		<input class="easyui-validatebox ipt easyui-brand" name="brandName" id="brandNameCondition" 
								 		data-options="inputWidth:160,inputNoField:'brandNoCondition', inputNameField:'brandNameCondition'" />
							  			<input type="hidden" name="brandNo" id="brandNoCondition" />
							  		</td>
							 		<th>失败原因：</th>
							 		<td>
							 			<input class="easyui-combobox ipt" name="failReason" id="failReasonCondition" />
							 		</td>
							 	</tr>
							 	<tr>
							 		<th>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</th>
							 		<td>
							 			<input class="easyui-combobox ipt" name="status"  id="statusCondition" />
							 		</td>
							 		<th>匹配类型：</th>
							 		<td>
							 			<input class="easyui-combobox ipt" name="matchType" id="matchTypeCondition" />
							 		</td>
							 	</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn=""   
		              isHasToolBar="false" onClickRowEdit="false" pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
			                  	  {field : 'id',hidden:true,notexport:true},
				                  {field : 'itemCode',title : '商品编码',width: 135,align:'left',halign:'center'},
				                  {field : 'zoneNo',title : '地区',width: 80,align:'center', formatter : ruleMatchFail.formatterZone},
				                  {field : 'brandName',title : '品牌',width: 80,align:'center'},
				                  {field : 'status',title : '状态',width: 80,align:'center',notexport:true,formatter:ruleMatchFail.initStatusFormatter},
				                  {field : 'statusName',title : '状态',width: 80,align:'center',hidden:true},
				                  {field : 'matchType',title : '匹配类型',width: 100,align:'center',notexport:true,formatter:ruleMatchFail.initMatchTypeFormatter},
				                  {field : 'matchTypeName',title : '匹配类型',width: 80,align:'center',hidden:true},
				                  {field : 'failReason',title : '失败原因',width: 200,align:'left',halign:'center',notexport:true,formatter:ruleMatchFail.initFailReasonFormatter},
				                  {field : 'failReasonName',title : '失败原因',width: 80,align:'left',hidden:true},
				                  {field : 'createTime',title : '创建时间',width: 150,align:'center',sortField:'create_time',sortable:true}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>