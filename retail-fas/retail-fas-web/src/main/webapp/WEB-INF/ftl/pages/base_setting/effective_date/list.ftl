<!DOCTYPE html>
<head>
    <title>采购价生效日期设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/priceEffectiveDate.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "paramSet.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "paramSet.clear()","type":0},
	             {"id":"btn-enable11","title":"启用","iconCls":"icon-unlock","action":"paramSet.doEnable()","type":27},
	             {"id":"btn-unable12","title":"停用","iconCls":"icon-lock","action":"paramSet.doUnable()","type":100}
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
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
						 <tbody>
	       		 		   <tr>
								<th>状态：</th>
								<td>
									<input class="easyui-combobox ipt" name="status" 
									    data-options="valueField: 'label',textField: 'value',
										data: [{label: '1',value: '启用'},{label: '0',value: '停用'}]" />
								</td>			
								<th></th>
								<td></td>									
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
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-del", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="paramSetDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30,notexport:true},
				                  {field : 'id',hidden : 'true',align:'center',notexport:true},
				                  {field : 'businessOrganType', title : '控制级别', width : 80,halign:'center',
				                	  formatter:editor.BizTypeFormatter
				                  },
				                  {field : 'paramCode',title : '参数编码 ',width : 100,align:'left',halign:'center'},
				                  {field : 'paramName', title : '参数名称', width : 125},
				                  {field : 'dtlValue', title : '参数值', align:'center',width : 100,halign:'center',
					                  editor:{
					                  		type:'datebox',
					                  		options:{
				                  				id:'dtlValueId',
				                  				name:'dtlValue',
				                  			    required:true
				                  			}
					                  	}
				                  },
				                  {field : 'dtlName',title : '参数值名称',width : 100,align:'center',halign:'center'},
				                  {field : 'status',title : '状态',width : 80,align:'center',halign:'center',
				                    formatter: editor.statusformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'status',
				                  			data: [{'value':'0', 'text': '停用'}, {'value':'1', 'text':'启用'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'createUser',title : '创建人',width : 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
				                 ]" 
			         		 jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                   	   editor.editRow(rowIndex, rowData);
		                   }
			         }'/>
			</div>
		 </div>
	</div>
</body>
</html>