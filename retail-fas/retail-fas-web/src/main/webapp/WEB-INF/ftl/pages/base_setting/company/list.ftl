<!DOCTYPE html>
<head>
    <title>结算公司查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/company.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/company/list.json" 
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  dialog_width="750" 
					  dialog_height="350"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0}
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
						    <tbody>
		       		 		<tr>
		       		 			<th>公司编码：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt" name="companyNo" id="companyNoCodeCondition"/>
		       		 			</td>
		       		 			<th>公司名称：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt"  name="nameLike" id="nameCondition"/>
		       		 			</td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"  rownumbers="true" singleSelect="true" pageSize="20"
				           columnsJsonList="[
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'companyNo',title : '公司编码',width : 80,align:'center',halign:'center'},
				                  {field : 'name',title : '公司名称',width : 250,align:'left',halign:'center'},
				                  {field : 'zoneName',title : '大区',width : 80,align:'center',halign:'center'},
				                  {field : 'status',title : '状态',width : 60,align:'center',
				                  	formatter:function(val){
				                  		if(val==0){
				                  			return '撤销'
				                  		}else if(val==1){
				                  			return '正常'
				                  		}
				                  	}
				                  },
				                  {field : 'bankName',title : '开户银行',width : 150,align:'left',halign:'center'},
				                  {field : 'bankAccount',title : '银行账号',width : 150,align:'left',halign:'center'},
				                  {field : 'bankAccountName',title : '银行账户名',width : 150,align:'left',halign:'center'},
				                  {field : 'legalPerson',title : '法人代表',width : 80,align:'center'}, 
				                  {field : 'identityCard',title : '营业证号',width : 150,align:'right',halign:'center'},
				                  {field : 'taxRegistryNo',title : '税务登记号',width : 150,align:'right',halign:'center'},
				                  {field : 'taxLevel',title : '纳税级别',width : 100,align:'center',halign:'center',
				                  	formatter:function(val){
				                  		if(val==0){
				                  			return '一般纳税人'
				                  		}else if(val==1){
				                  			return '小规模纳税人'
				                  		}
				                  	}
				                  },
				                  {field : 'contactName',title : '联系人',width : 80,align:'left',halign:'center'},
				                  {field : 'tel',title : '电话号码',width : 100,align:'right'},
				                  {field : 'email',title : '电子邮箱',width : 150,align:'center'},
				                  {field : 'fax',title : '传真号',width : 100,align:'right',halign:'center'},
				                  {field : 'remark',title : '备注',width : 150,align:'left',halign:'center'}
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){}
			         }'/>
			</div>
		 </div>
	</div>
	
</body>
</html>