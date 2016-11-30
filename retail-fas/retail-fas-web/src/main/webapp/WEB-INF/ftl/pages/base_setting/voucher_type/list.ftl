<!DOCTYPE html>
<head>
    <title>凭证类型</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/voucher_type.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/voucher_type/list.json" 
					  save_url="/base_setting/voucher_type/insert" 
					  update_url="/base_setting/voucher_type/update" 
					  del_url="/base_setting/voucher_type/save" 
					  enable_url="/base_setting/voucher_type/do_enable"
					  unable_url="/base_setting/voucher_type/do_unable"
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="260"
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
			<div data-options="region:'north',border:false">
				<#--搜索start-->
				<div class="search-div">
					     <form name="searchForm" id="searchForm" method="post">
			       		 	<table class="form-tb">
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
			       		 			<th>类型名称：</th>
			       		 			<td>
			       		 				<input class="easyui-validatebox ipt" name="vouchTypeName" id="vouchTypeNameCondition"/>
			       		 			</td>
			       		 		</tr>
			       		 		</tbody>
			       		 	</table>
						</form>
				</div>
			</div>
			
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="/base_setting/voucher_type/list.json" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			               checkOnSelect="true" rownumbers="true" 
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'vouchTypeCode',title : '类型编码',width : 80,align:'left',halign:'center'},
				                  {field : 'vouchTypeName',title : '类型名称',width : 100,align:'center'},
				                  {field : 'shortName',title : '简称',width : 80,align:'center'},
				                  {field : 'companyName',title : '公司名称',width : 200,align:'left',halign:'center'},
				                  {field : 'currencyName',title : '默认币种',width : 60,align:'center'},
				                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
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
					<th><span class="ui-color-red">*</span>类型编码：</th>
					<td>
						<input class="easyui-validatebox" name="vouchTypeCode" id="vouchTypeCodeId" 
						style="width:160px;" data-options="required:true,validType:['unNormalData','engNum','maxLength[4]']" /> 
				    </td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>类型名称：</th>
					<td>
						<input class="easyui-validatebox" name="vouchTypeName" id="vouchTypeNameId" 
						style="width:160px;"  data-options="required:true,validType:['unNormalData','maxLength[10]']" /> 
				    </td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</th>
					<td>
						<input class="easyui-validatebox"  name="shortName" id="shortNameId" 
						style="width:160px;" data-options="required:true,validType:['unNormalData','chinese','maxLength[1]']" />
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>公司名称：</th>
					<td>
						<input class="easyui-company ipt" readonly="true" name="companyName" id="companyNameId" 
						data-options="required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:160"/>
						<input type="hidden" name="companyNo" id="companyNoId"/>
					</td>
				</tr>
				<tr>
					<th>默认币种：</th>
					<td>
						<input class="easyui-currency ipt"  name="currencyCode" id="currencyCodeId" data-options="width:160"/>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>