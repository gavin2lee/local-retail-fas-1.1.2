<!DOCTYPE html>
<head>
    <title>差异回款设置表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/balanceDiffBack.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/bill_shop_balance_back/list.json" 
					  save_url="/bill_shop_balance_back/post" 
					  update_url="/bill_shop_balance_back/put" 
					  del_url="/bill_shop_balance_back/save" 
					  datagrid_id="balanceDiffBackDG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="300"
					  primary_key="id"/>

<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"balanceDiffBack.dobalanceDiffBack", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0}
	           ]
			/>
			
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">	 
	       		 	                <input type="hidden"  name="billNo" id="billNo" value="${dtlId}"/>
	       		   		 	       <input type="hidden"  name="diffBillNo" id="diffBillNo" value="${diffBillNo}"/>
			       		 		  <input type="hidden"  name="balanceNo" id="balanceNo" value="${balanceNo}"/>
			       		 			
			       		 			
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "balanceDiffBack.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "fas_common_editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "balanceDiffBack.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="balanceDiffBackDG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'diffBillNo',hidden : true,title : 'diffBillNo',align:'center',editor:{
				                  		type:'readonlytext',
				                  		options:{
				                  			required:false
				                  		}
				                  	}},	
				                  {field : 'balanceNo',hidden : true,title : 'balanceNo',align:'center',editor:{
				                  		type:'readonlytext',
				                  		options:{
				                  			required:false
				                  		}
				                  	}},	        
				                  {field : 'backDate',title : '回款日期',width:80,align:'center',editor:'datebox'},
				                  {field : 'backAmount',title : '回款金额',width : 80,align:'right',								
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			required:true,precision:2,validType:['maxLength[12]']
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

