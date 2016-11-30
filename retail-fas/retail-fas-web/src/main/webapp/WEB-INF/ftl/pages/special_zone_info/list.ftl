<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>内购结算期设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/specialZoneInfo.js?version=${version}"></script>
</head>
<script>
</script>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	     <@p.toolbar id="toolbar" listData=[
			  	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "dialog.clear()","type":0},                         
             	 {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action" : "dialog.doEnable()", "type":27},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action" : "dialog.doUnable()", "type":100},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":0}
           ]
		/>
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
	       		 			 <tr>
								<th align="right" width="110">大区名称：</th>
								<td align="left" width="140"><input class="easyui-validatebox ipt" name="nameLike" id="nameLike"/></td>									
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
				 	 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":1},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7}				 
		           ]
				/>
            </div>
				           
			<!--列表-->
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="specialZoneInfoDataGrid" loadUrl="" saveUrl="" defaultColumn=""  
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			           rownumbers="true" emptyMsg = "" singleSelect="true"  
			           columnsJsonList="[
			                      {field : 't', checkbox:true, width : 30, notexport:true},
			                      {field : 'id',hidden : 'true',align:'center',notexport:true},
			                      {field : 'zoneNo',title : '大区编号',width : 80,align:'left',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
					                  		required:true, 
					                  		validType:['english','maxLength[1]']
					                  	}
				                  	}
				                  }, 
				                  {field : 'zoneCode',title : '大区编码',width : 80,align:'left',
				                  	editor:{
					                  		type:'readonlybox',
					                  		options:{required:true,id:'zoneCode',name:'zoneCode'}
				                  		}
				                  }, 
				                   {field : 'name',title : '大区名称',width : 80,align:'left',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{required:true, validType:['rangeLength[1,10]']}
				                  	}
				                  }, 
				                  {field : 'status',title : '状态',width : 80,align:'left',
				                    formatter: editor.statusformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'type',
				                  			data: [{'value':'0', 'text': '停用'}, {'value':'1', 'text':'启用'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'remark',title : '备注',width : 200,align:'left',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{required:false, validType:['rangeLength[0,255]']}
				                  	}
				                  }, 
				                  {field : 'createUser',title : '建档人',width : 100,align:'left'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'left'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'left'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'left'},   
			                  ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex,rowData){
			                	  //双击方法
					              editor.editRow(rowIndex, rowData);
			              }}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>