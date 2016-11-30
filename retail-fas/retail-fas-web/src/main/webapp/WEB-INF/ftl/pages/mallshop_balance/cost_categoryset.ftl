<!DOCTYPE html>
<head>
    <title>总账费用类别设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/cost_categoryset.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	     <@p.billToolBar type="cost_categoryset_setBar"/>
		 
			
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 		<tr height="25"> 
			       		 		<th>公司名称：</th>
									<td><input class="ipt easyui-company" name="companyName" id="companyName"/>
									<input type="hidden" name="companyNo" id="companyNo"/></td>	       		 		
	       		 			    <td width="100" align="right">类别编码：</td>
		       		 			<td align="left"><input class="easyui-validatebox ipt" name="codeLike" id="codeCodeCondition"/></td>
		       		 			<td width="100" align="right">总账费用名称：</td>
		       		 			<td align="left"><input class="easyui-validatebox ipt" name="nameLike" id="nameCondition"/></td>		       		 		
		       		 			<td width="50" align="right">状态：</td>
		       		 			<td align="left"><input class="easyui-statusbox ipt" name="status" id="statusCondition"/></td>		       		 			
		       		 			<td width="80" align="right">创建人：</td>
		       		 			<td align="left"><input class="easyui-validatebox ipt" name="createUser" id="createUser"/></td>
	       		 		</tr>
	       		 	</table>
				</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.billToolBar type="cost_categoryset_operaBar"/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="costCategoryDataGrid" loadUrl="" saveUrl="" defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30, notexport:true},
				                  {field : 'id',hidden : 'true',align:'left', notexport:true},
				                  {field : 'companyNo',hidden:true, title : '公司编码', align:'left',width : 80, editor:{type:'hiddenbox',options:{id:'companyNo_',name:'companyNo'}}},
								  {field : 'companyName', title : '公司名称', align:'left',width : 200, halign:'center',
				                  	editor:{
				                  		type:'company',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'companyName_',
				                  			name:'companyName',
				                  			inputNoField:'companyNo_',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'code',title : '类别编码',exportType:'number',width : 80,align:'left',
				                  	editor:{
				                  		type:'readonlybox',
				                  		options:{
				                  			required:true,
				                  			validType:'length[4,6]'
				                  		}
				                  	}
				                  },
				                  {field : 'name',title : '总账费用名称',width : 100,align:'left',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },				                  
								 {field : 'accountsNo',title : '会计科目编码',exportType:'number',width : 120,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:'length[4,50]'
				                  		}
				                  	}
				                  },
				                  {field : 'accountsName',hidden:true,title : '会计科目名称',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:false
				                  		}
				                  	}
				                  },
				                  {field : 'status',title : '状态',exportType:'number',width : 80,align:'left', notexport:true,
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
				                  {field : 'statusName',title : '状态',width : 100,align:'left',hidden:'true'}, 
				                  {field : 'remark',title : '备注',width : 200,align:'left',
				                  	editor:{
				                  		type:'validatebox'
				                  	}
				                  },
				                  {field : 'createUser',title : '建档人',width : 100,align:'left'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'left'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'left'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'left'}	
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

