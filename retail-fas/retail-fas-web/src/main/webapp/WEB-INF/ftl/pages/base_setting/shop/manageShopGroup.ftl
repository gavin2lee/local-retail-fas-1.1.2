<!DOCTYPE html>
<head>
    <title>开票申请店铺分组设置表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/manageShopGroup.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/invoice_rule_shop_set/list.json" 
					  save_url="/invoice_rule_shop_set/post" 
					  update_url="/invoice_rule_shop_set/put" 
					  del_url="/invoice_rule_shop_set/save" 
					  datagrid_id="manageShopGroupDG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"manageShopGroup.domanageShopGroup", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0}
	           ]
			/>
			
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">	 
	       		 	 <input type="hidden"  name="invoiceRuleNo" id="invoiceRuleNo" /> 
	       		 	 <input type="hidden"  name="mallNo" id="mallNo" value="${mallNo}"/>  
	       		 	 <input type="hidden"  name="bsgroupsNo" id="bsgroupsNo" /> 
	       		 	       <input type="hidden"  name="companyNo" id="companyNo" value="${companyNo}"/>
			       		 		  <input type="hidden"  name="payType" id="payType" value="${payType}"/>
			       		 			<th>编码：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt" name="shopNo" id="shopNo"/></td>
			       		 			<th>名称：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt"  name="shortName" id="shortName"/></td>
			       		 		</tr>    		 			
	       		 	</table>
				</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "fas_common_editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "manageShopGroup.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="manageShopGroupDG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'invoiceRuleNo',hidden : 'true',align:'center'},	        
				                  {field : 'shopNo', title : '编码', width : 130},
				                  {field : 'shortName', title : '简称', width : 130},				                                  
				                  {field : 'groupFlag',title : '分组标识',width : 80,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  }				                  
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
					                   	  fas_common_editor.editRow(rowIndex, rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
</body>
</html>

