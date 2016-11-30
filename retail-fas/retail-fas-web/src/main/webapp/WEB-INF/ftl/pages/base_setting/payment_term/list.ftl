<!DOCTYPE html>
<head>
    <title>付款条款</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/payment_term.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" >
		  <#--按钮-->
		<@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog.clear()","type":0},
	             {"id":"btn-enable11","title":"启用","iconCls":"icon-unlock","action":"dialog.doEnable()","type":27},
	             {"id":"btn-unable12","title":"停用","iconCls":"icon-lock","action":"dialog.doUnable()","type":100}
	           ]
			/>
		<div data-options="region:'north',border:false" >
			<#--搜索start-->
			<div class="search-div">
					     <form name="searchForm" id="searchForm" method="post">
			       		 	<table class="form-tb">
				       		 	<col width="100" />
							    <col />
							    <tbody>
				       		 		<tr height="25">
				       		 			<th>编&nbsp;&nbsp;&nbsp;&nbsp;码：</th>
				       		 			<td>
				       		 				<input class="easyui-validatebox ipt" name="paymentNo" id="paymentNoCondition"/>
				       		 			</td>
				       		 		</tr>
			       		 		</tbody>
			       		 	</table>
						</form>
				</div>
			</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="dataGridJG"  loadUrl="/base_setting/payment_term/list.json" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"   
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'paymentNo',title : '编码',width : 80,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['unNormalData','engNum','maxLength[4]']
				                  		}
				                  	}
				                  },
				                  {field : 'expressed',title : '描述',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:'unNormalData'
				                  		}
				                  	}
				                  },
				                  {field : 'discount',title : '折率',width : 80,align:'right',halign:'center',
			                  		editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			min:0,max:1,precision:2,
				                  			validType:['decimalData','maxLength[4]']
				                  		}
				                  	}
				                  },
				                 {field : 'creditDay',title : '信用天数',width : 80,align:'center',
				                  		editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			required:true,
				                  			
				                  		}
				                      	}
				                  },
				                  {field : 'status',title : '启用状态',width : 80,align:'center',halign:'center',
				                     formatter: editor.statusformatter, 
				                  	 editor:{
				                  		type:'checkbox',
				                  		options:{
				                  			on:1,
				                  			off:0
				                  		}
				                  	}
				                  },
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
			                               editor.editRow(rowIndex, rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
	
</body>
</html>