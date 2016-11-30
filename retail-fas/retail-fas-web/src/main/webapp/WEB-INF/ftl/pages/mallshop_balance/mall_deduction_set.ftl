<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>扣费类别</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/MallDeductionSet.js?version=${version}"></script>
</head>

<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	    <@p.billToolBar type="mall_deduction_setBar"/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
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
								<tr height="25">
								<th>公司名称：</th>
									<td><input class="ipt easyui-company" name="companyName" id="companyName"/>
									<input type="hidden" name="companyNo" id="companyNo"/></td>
		       		 			<th>扣费编码：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt" name="codeLike" id="codeCondition"/>
		       		 			</td>
		       		 			<th>扣费名称：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt" name="nameLike" id="nameCondition"/>
		       		 			</td>
		       		 			<th>状态：</th>
		       		 			<td>
		       		 				<input class="easyui-statusbox ipt" name="status" id="statusCondition" />
		       		 			</td>
		       		 		</tr>
		       		 		<tr>
		       		 			<th>建档人：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt" name="createUser" id="createUserCondition" style="width:130px;"/>
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
			 <@p.billToolBar type="mall_deduction_operaBar"/>
            </div>
				           
			<!--列表-->
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="mallDeductionDataGrid"  loadUrl="" saveUrl="" defaultColumn=""  
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			           rownumbers="false" emptyMsg = "" singleSelect="false"  
			           columnsJsonList="[
			                      {field : 't', checkbox:true, width : 30, notexport:true},
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
			                      {field : 'code',title : '扣费编码',exportType:'number',width : 100,align:'left',halign:'center',
				                  	editor:{
				                  		type:'readonlytext',
				                  	}
				                  },
				                  {field : 'name',title : '扣费名称',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  }, 	
				                  {field : 'costName', title : '总账费用名称', width : 120,halign:'center', 
									 editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			required:true,
				                  			id:'costName_',
				                  			name:'costName',
				                  			inputWidth:60,
				                  			idField:'costName',
				                  			textField:'name',
				                  			noField:'code',
				                  			inputNoField:'costCode',
				                  			url:BasePath+'/cost_category/list.json?status=1',
				                  			paramMap:[{name:'companyNo', field:'companyNo_'}],
				                  			columns:[[ 
				                  				{field : 'code',title : '总账费用编码',width : 100, halign : 'center', align : 'left'},
		        	     						{field : 'name',title : '总账费用名称',width : 150, halign : 'center', align : 'left'}
				                  			]],
				                  			callback:function(data){
				                  				if(data){
					                  				$('#costName_').combogrid('setValue',data.name);
					                  				$('#costCode_').val(data.code);
				                  				}
				                  			}
				                  		}
				                  	}
								  },
				                  {field : 'costCode',hidden:true, title : '总账费用编码', width : 100,halign:'center', notexport:true,
					                  editor:{
					                  		type:'hiddenbox',
					                  		options:{
					                  			id:'costCode_',
					                  			name:'costCode'
					                  		}
					                  	}
				                  },
				                  {field : 'debitedRental',title : '是否为场地经营费用',exportType:'number',width : 120,align:'center',notexport:true,halign:'center',
				                    formatter: editor.debitedRentalformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'type',
				                  			data: [{'value':'0', 'text': '否'}, {'value':'1', 'text':'是'}], 
				                  			valueField: 'value', 
				                  			textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                   {field : 'status',title : '状态',width : 80,align:'center',halign:'center',
				                    formatter: editor.statusformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'type',
				                  			data: [{'value':'0', 'text': '停用'}, {'value':'1', 'text':'启用'}], 
				                  			valueField: 'value', 
				                  			textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                   {field : 'remark',title : '备注',width : 200,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox'
				                  	}
				                  }, 
				                  {field : 'createUser',title : '建档人',width : 100,align:'center',halign:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center',halign:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center',halign:'center'},   
			                  ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex,data){
			                	  //双击方法
			                   	   editor.editRow(rowIndex, data);	
			              }}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>