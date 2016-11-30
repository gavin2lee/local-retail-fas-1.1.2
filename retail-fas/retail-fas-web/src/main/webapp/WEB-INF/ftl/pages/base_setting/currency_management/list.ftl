<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>币种管理</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
       <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	   <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/currency_management.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/currency_management/list.json" 
					  save_url="/base_setting/currency_management/insert" 
					  update_url="/base_setting/currency_management/update" 
					  del_url="/base_setting/currency_management/save" 
					  enable_url="/base_setting/currency_management/do_enable"
					  unable_url="/base_setting/currency_management/do_unable"
					  datagrid_id="dataGridJG" 
					  search_form_id="mainDataForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="200"
					  primary_key="id"/>

<#--最外层框-->
<div data-options="region:'center',border:false">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'币种管理'">
		<div id="subLayout" class="easyui-layout" data-options="fit:true,border:false">
			<#--按钮-->
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
				
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
					<div data-options="region:'north',border:false">
						 <#--表头-->
						<div class="search-div">
					     	 <#-- 主档信息  -->
		                       <form name="mainDataForm" id="mainDataForm" method="post">
		                        <input type="hidden" name="id" /></td>
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
										<th>币种编码：</th>
			       		 				<td>
			       		 					<input class="easyui-validatebox ipt" name="currencyCode" id="currencyCodeCondition"/>
			       		 				</td>
			       		 				<th>币种名称：</th>
			       		 				<td>
			       		 					<input class="easyui-validatebox ipt" name="currencyName" id="currencyNameCondition"/>
			       		 				</td>
			       		 				<th></th>
			       		 				<td></td>
			       		 				<th></th>
			       		 				<td></td>
			       		 				</tr>			
								</tbody>
								</table>
								</form>
						</div>
					</div>
						
					<#--列表-->
			        <div data-options="region:'center',border:false">
						<@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
					              isHasToolBar="false" onClickRowEdit="false" pagination="true"  checkOnSelect="true"
						           rownumbers="true" 
						           columnsJsonList="[
						                  {field : 't',checkbox:true,width : 30},
						                  {field : 'id',hidden : 'true',align:'center'},
						                  {field : 'currencyCode',title : '币种编码',width : 80,align:'left',halign:'center'},
						                  {field : 'currencyName',title : '币种名称',width : 100,align:'left',halign:'center'},
						                  {field : 'currencySymbol',title : '币种标识',width : 80,align:'center'},
						                  {field : 'standardMoney',title : '是否本位币',width : 80,align:'center',
						                  	formatter:function(val){
						                  		if(val==0){
						                  			return '否'
						                  		}else if(val==1){
						                  			return '是'
							                  		}
							                  	}
						                  },
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
		</div>
	</div>
</div>

	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:350,height:300" > 
		<form  id="dataForm" method="post">
			<input type="hidden" id="id" name="id" />
			<table  class="form-tb">
				<col width="100" />
				<col />
				<col width="100" />
				<col />
				<tbody>
				<tr>
					<th><span class="ui-color-red">*</span>币种编码：</th>
					<td>
						<input class="easyui-validatebox" name="currencyCode" id="currencyCodeId"  data-options="required:true,validType:['unNormalData','engNum','maxLength[3]']" />  
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>币种名称：</th>
					<td>
						<input class="easyui-validatebox"  name="currencyName" id="currencyNameId" data-options="required:true,validType:'unNormalData'" />
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>币种标识：</th>
					<td>
						<input class="easyui-validatebox" name="currencySymbol" id="currencySymbolId" data-options="required:true,validType:'unNormalData'"/>
					</td>
				</tr>
				<th>是否本位币：</th>
				<td>
				  <input type="radio"  name="standardMoney"  value="1"/>是
			      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          <input type="radio"  name="standardMoney"  value="0"/>否
			    </td>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>