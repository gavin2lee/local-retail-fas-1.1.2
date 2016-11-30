<!DOCTYPE html>
<head>
    <title>结算方式设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/settle_method.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/settle_method/list.json" 
					  save_url="/base_setting/settle_method/insert" 
					  update_url="/base_setting/settle_method/update" 
					  del_url="/base_setting/settle_method/save" 
					  enable_url="/base_setting/settle_method/do_enable"
					  unable_url="/base_setting/settle_method/do_unable"
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="580" 
					  dialog_height="220"
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
		       		 		<col width="120" />
						    <col />
						    <col width="120" />
						    <col />
						    <col width="120" />
						    <col />
						    <col width="120" />
						    <col />
						    <tbody>
			       		 		<tr>
			       		 			<th>结算方式编码：</th>
			       		 			<td>
			       		 				<input class="easyui-validatebox ipt" name="settleCode" id="settleCodeCondition"/>
			       		 			</td>
			       		 			<th>结算方式类型：</th>
			       		 			<td>
			       		 				<input class="easyui-combobox ipt"  name="settleType" id="settleTypeCondition"/>
			       		 			</td>
			       		 			<th>业务类型：</th>
			       		 			<td>
			       		 				<input class="easyui-combobox ipt" 
			       		 				data-options="valueField: 'value',textField: 'text',data:businessType" 
			       		 				name="businessType" id="businessTypeCondition"/>
			       		 			</td>
			       		 			<th>是否支付手续费：</th>
			       		 			<td>
			       		 				<input class="easyui-combobox ipt" 
			       		 				data-options="valueField: 'value',textField: 'text',data:payFeesFlag" 
			       		 				name="payFeesFlag" id="payFeesFlagCondition"/>
			       		 			</td>
			       		 		</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
			</div>
		</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			     <@p.datagrid id="dataGridJG"  loadUrl="/base_setting/settle_method/list.json" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true"   checkOnSelect="true"
				           rownumbers="true" 
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'settleCode',title : '结算方式编码',width : 120,align:'left',halign:'center'},
				                  {field : 'settleName',title : '结算方式名称',width : 120,align:'left',halign:'center'},
				                  {field : 'settleType',title : '结算方式类型',width : 100,align:'center',halign:'center',
				                  	formatter:settleMethod.formatSettleType  
				                  },
				                  {field : 'businessType',title : '业务类型',width : 100,align:'center',halign:'center',
				                  	formatter:settleMethod.businessTypeformatter
				                  },
				                  {field : 'payFeesFlag',title : '是否支付手续费',width : 100,align:'center',halign:'center',
				                  	formatter:settleMethod.payFeesFlagformatter,
				                  },
				                  {field : 'bearer',title : '承担方',width : 100,align:'left',halign:'center'},
				                  {field : 'paymentMode',title : '支付方式',width : 80,align:'left',halign:'center'},
				                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'},
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
			<table class="form-tb">
				<col width="100" />
		    	<col />
		    	<col width="100" />
		    	<col />
		   		<tbody>
				<tr>
					<th><span class="ui-color-red">*</span>结算方式编码：</th>
					<td >
						<input class="easyui-validatebox ipt" name="settleCode" id="settleCodeId"  data-options="required:true,validType:['unNormalData','engNum','maxLength[4]']" />
					</td>
					<th><span class="ui-color-red">*</span>结算方式名称：</th>
					<td >
						<input class="easyui-validatebox ipt"  name="settleName" id="settleNameId" data-options="required:true,validType:['unNormalData','chinese']" />
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>结算方式类型：</th>
					<td >
						<input class="easyui-combobox"  name="settleType" id="settleTypeId" data-options="required:true" />
					</td>
					<th><span class="ui-color-red">*</span>业务类型：</th>
					<td >
						<input class="easyui-combobox" name="businessType" id="businessTypeId" 	data-options="required:true"/>  
					</td>
				</tr>
				<tr>
					<th>是否支付手续费：</th>
					<td >
						<input class="easyui-combobox"  name="payFeesFlag" id="payFeesFlagId" />
					</td>
					<th>承&nbsp;&nbsp;担&nbsp;&nbsp;方：</th>
					<td >
						<input class="easyui-validatebox ipt"  name="bearer" id="bearerId"  data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<th>支付方式：</th>
					<td>
						<input class="easyui-validatebox ipt"  name="paymentMode" id="paymentModeId" data-options="validType:'maxLength[10]'"  />
					</td>
					<th></th>
			   		<td></td>
				</tr>
			</tbody>
			</table>
		</form>
	</div>
</body>
</html>