<!DOCTYPE html>
<head>
    <title>差异类型设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js"/>?version=${version}"></script> 
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/mall_balance_difftype.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	 <@p.billToolBar type="mall_balance_difftype_setBar"/>
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
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
	       		 		     <th>公司名称：</th>
									<td><input class="ipt easyui-company" name="companyName" id="companyName"/>
									<input type="hidden" name="companyNo" id="companyNo"/>
							</td>	
							       		 		
		       		 		<th>类别名称：</th>
		       		 		<td>
		       		 			<input class="easyui-validatebox ipt" name="name" id="naemCondition"/>
		       		 		</td>
		       		 		<th>状态：</th>
		       		 		<td>
		       		 			<input class="easyui-validatebox ipt" name="status" id="statusCondition"/>
		       		 		</td>
		       		 		<th>创建人：</th>
		       		 		<td>
		       		 			<input class="easyui-validatebox ipt" style="width:150px;" name="createUser" id="createUserCondition"/>
		       		 		</td>
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
			     <@p.billToolBar type="mall_balance_difftype_operaBar"/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="mallBalanceDiffTypeDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30,notexport:true},
				                  {field : 'id',hidden : 'true',align:'left',notexport:true},				                 
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
								 {field : 'code',title : '类别编码',width : 80,align:'left',halign:'center',
				                  	editor:{
				                  		type:'readonlytext',
				                  		options:{
				                  			required:true,
				                  			validType:'length[4,6]'
				                  		}
				                  	}
				                  },
				                  {field : 'name',title : '类别名称',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'status',title : '状态',width : 80,align:'center',halign:'center',
				                    formatter: dialog.statusFlagformatter,
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
				                   {field : 'remark',title : '备注',width : 200,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox'				                  	}
				                  },
				                   {field : 'createUser',title : '建档人',width : 100,align:'left',halign:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'left',halign:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'left',halign:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'left',halign:'center'}
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

