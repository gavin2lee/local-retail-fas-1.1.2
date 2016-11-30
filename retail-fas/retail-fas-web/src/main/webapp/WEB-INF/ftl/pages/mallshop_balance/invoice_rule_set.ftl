<!DOCTYPE html>
<head>
    <title>开票申请规则设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/invoice_rule_set.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "dialog.clear()","type":0},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action" : "dialog.exportExcel()", "type":0},
	             {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action" : "dialog.doEnable()", "type":0},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action" : "dialog.doUnable()", "type":0}
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
						<col />
						<col width="100" />
						<col />
						<tbody>
	       		 		 <tr>
							<th>公司名称：</th>
							<td>
								<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName"/>
								<input type="hidden" name="companyNo" id="companyNo"/>
							</td>									
							<th>商场：</th>
							<td>
								<input class="easyui-validatebox ipt easyui-mall" name="mallName" id="mallName"/>
								<input type="hidden" name="mallNo" id="mallNo"/>
							</td>		
						 	<th>制单人：</th>
							<td>
								<input class="ipt" name="createUser" id="createUser" />
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
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="invoiceRuleSetDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30,notexport:true},
				                  {field : 'id',hidden : 'true',align:'center',notexport:true},
				                  {field : 'billingMethod',title : '开票方式 ',width : 100,align:'left',halign:'center',formatter: editor.dataBillingMethod,
					                  editor:{
					                  		type:'fascombobox',
					                  		options:{
					                  			id:'billingMethod',
					                  			data: [{'value' : '1','text' : '按商业集团开票'}, {'value' : '2','text' : '按商场开票'}, {'value' : '3','text' : '按商场+店铺开票'}], 
					                  			valueField: 'value', 
					                  			textField: 'text',
					                  			onSelect : function(data) {
					                  				$('#billingMethod').val(data.value);
							   						editor.changeBillingMethod(data);
							   					}
					                  		}
					                  	}
				                  },
				                  {field : 'shopGroupName', title : '店铺分组', width : 100,halign:'center', 
									 editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			required:true,
				                  			id:'shopGroupName',
				                  			name:'shopGroupName',
				                  			inputWidth:60,
				                  			idField:'shopGroupName',
				                  			textField:'shopGroupName',
				                  			noField:'shopGroupNo',
				                  			inputNoField:'shopGroupNo',
				                  			url:BasePath+'/shop_group/list.json?status=1',
				                  			columns:[[ 
				                  				{field : 'shopGroupNo',title : '店铺分组编码',width : 100, halign : 'center', align : 'left'},
		        	     						{field : 'shopGroupName',title : '店铺分组名称',width : 150, halign : 'center', align : 'left'},
		        	     						{field : 'companyNo',hidden:true, title : '公司编码',width : 80, halign : 'center', align : 'left'},
		        	     						{field : 'companyName',hidden:true, title : '公司名称',width : 80, halign : 'center', align : 'left'}
				                  			]],
				                  			callback:function(data){
				                  				if(data){
					                  				$('#shopGroupNo').val(data.shopGroupNo);
					                  				$('#shopGroupName').val(data.shopGroupName);
					                  				$('#companyNo_').val(data.companyNo);
					                  				$('#companyName_').val(data.companyName);
				                  				}
				                  			}
				                  		}
				                  	}
								  },
				                  {field : 'shopGroupNo',hidden:true, title : '店铺编码', width : 100,halign:'center', 
					                  editor:{
					                  		type:'hiddenbox',
					                  		options:{
					                  			id:'shopGroupNo',
					                  			name:'shopGroupNo'
					                  		}
					                  	}
				                  },
				                  {field : 'companyName', title : '公司', align:'left',width : 200,halign:'center',
				                  	 editor:{type:'searchboxname',options:{id:'companyName_',name:'companyName',readonly:true}}},
				                  {field : 'companyNo',hidden:true, title : '公司编码', align:'left',width : 80, halign:'center',
				                  	editor:{type:'hiddenbox',options:{id:'companyNo_',name:'companyNo'}}},
				                  {field : 'bsgroupsName', title : '商业集团名称',  align:'left',width : 200,halign:'center',
				                   editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			required:true,
				                  			id:'bsgroupsName',
				                  			name:'bsgroupsName',
				                  			inputWidth:60,
				                  			idField:'name',
				                  			textField:'name',
				                  			noField:'bsgroupsNo',
				                  			inputNoField:'bsgroupsNo',
				                  			inputNameField:'bsgroupsName',
				                  			url:BasePath+'/bsgroups/list.json',
				                  			columns:[[ 
				                  				{field : 'bsgroupsNo',title : '商业集团编码',width : 100, halign : 'center', align : 'left'},
		        	     						{field : 'name',title : '商业集团名称',width : 150, halign : 'center', align : 'left'}
				                  			]]
				                  		}
				                  	}
				                  },
				                  {field : 'bsgroupsNo',hidden:true, title : '商场集团编码',  align:'left',width : 80,halign:'center', 
									editor:{
				                  		type:'hiddenbox',
				                  		options:{
				                  			id:'bsgroupsNo',
				                  			name:'bsgroupsNo'
				                  		}
				                  	}
				                  },
				                  {field : 'mallName', title : '商场名称', align:'left',width : 160, halign:'center',
				                  	editor:{
				                  		type:'mall',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'mallName_',
				                  			name:'mallName',
				                  			inputNoField:'mallNo_',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'mallNo', hidden:true, title : '商场编码', align:'left',width : 80,halign:'center', 
					                  editor:{
						                  type:'hiddenbox',
						                  options:{
						                  	id:'mallNo_',
						                  	name:'mallNo'
						                  }
						              }
				                  },
				                  {field : 'invoiceTempName',title : '发票模板',width : 150,align:'left',halign:'center',
					                  editor:{
					                  		type:'combogrid',
					                  		options:{
					                  			required:true,
					                  			id:'name',
					                  			name:'name',
					                  			inputWidth:60,
					                  			idField:'name',
					                  			textField:'name',
					                  			noField:'invoiceTempNo',
					                  			inputNoField:'invoiceTempNo',
					                  			url:BasePath+'/invoice_template_set/list.json?status=1',
					                  			columns:[[ 
					                  				{field : 'invoiceTempNo',title : '模板编码',width : 100, halign : 'center', align : 'left'},
			        	     						{field : 'name',title : '模板名称',width : 150, halign : 'center', align : 'left'}
					                  			]],
					                  			callback:function(data){
					                  				if(data){
						                  				$('#invoiceTempNo').val(data.invoiceTempNo);
						                  				$('#invoiceTempName').val(data.name);
					                  				}
					                  			}
					                  		}
					                  	}
				                  },
				                  {field : 'invoiceTempNo',hidden:true, title : '发票模板', width : 100,align:'left',halign:'center', 
				                  	editor:{
					                  type:'hiddenbox',
					                  options:{
					                  	id:'invoiceTempNo',
					                  	name:'invoiceTempNo'
					                  }
					                }
				                  },
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
				                  {field : 'invoiceName',title : '开票名称',width : 160, align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },				                  
				                  {field : 'taxRegistryNo',title : '纳税人识别号',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'buyerAddress',title : '地址',width : 200,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },				                  
				                  {field : 'buyerTel',title : '电话',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },				                  
				                  {field : 'bankName',title : '开户行',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },				                  
				                  {field : 'bankAccount',title : '账号',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },	
				                  {field : 'mailingAddress',title : '发票邮寄地址',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },				                  
				                  {field : 'contactName',title : '收票联系人',width : 120,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },	
				                  {field : 'tel',title : '联系电话',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'remark',title : '备注',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		}
				                  	}
				                  }
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

