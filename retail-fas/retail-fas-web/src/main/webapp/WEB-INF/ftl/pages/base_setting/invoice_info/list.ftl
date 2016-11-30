<!DOCTYPE html>
<head>
    <title>开票信息维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/invoice_info_set.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "invoiceSet.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "invoiceSet.clear()","type":0},
	             {"id":"btn-enable11","title":"首选","iconCls":"icon-unlock","action":"invoiceSet.doEnable()","type":27},
	             {"id":"btn-unable12","title":"备选","iconCls":"icon-lock","action":"invoiceSet.doUnable()","type":100},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"invoiceSet.doImport()","type":6}
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"invoiceSet.exportExcel()","type":4}
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
								<th>公司名称：</th>
								<td>
									<input class="easyui-company ipt" name="companyName" id="companyName"
										data-options="inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160"
									/>
									<input type="hidden" name="companyNo" id="companyNo"/>
								</td>									
								<th>客户类型：</th>
								<td>
									<input class="easyui-combobox ipt" name="clientType" id="clientTypeCond"/>
								</td>	
								<th>客户编码：</th>
								<td>
									<input class="easyui-validate ipt" name="clientNo" id="clientNoCond"/>
								</td>			
								<th>客户全称：</th>
								<td>
									<input class="easyui-validate ipt" name="clientName" id="clientNameCond" />
								</td>		
	       		 			</tr>
	       		 			<tr>
	       		 			    <th>开票名称：</th>
								<td>
									<input class="easyui-validate ipt" name="invoiceName" id="invoiceNameCond" />
								</td>	
	       		 				<th>NC客户档案：</th>
								<td>
									<input class="easyui-validate ipt" name="ncClientName" id="ncClientNameCond" style="width:150px;"/>
								</td>		
	       		 				<th>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</th>
								<td>
									<input class="easyui-combobox ipt" name="status" 
									data-options="valueField: 'label',textField: 'value',
										data: [{label: '1',value: '首选'},{label: '0',value: '备选'}]" />
								</td>										
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add-row", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-del-row", "action" : "editor.deleteRow()","type":0},
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
				                  
				                  {field : 'companyName', title : '公司名称', width : 200,align:'left',halign:'center',
									 editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			required:true,
				                  			id:'companyName_',
				                  			name:'companyName',
				                  			inputWidth:200,
				                  			panelWidth : 400,
											panelHeight : 300,
				                  			idField:'companyName',
				                  			textField:'name',
				                  			noField:'companyNo',
				                  			inputNoField:'companyName',
				                  			url:BasePath+'/base_setting/company/list.json?status=1',
				                  			columns:[[ 
		        	     						{field : 'companyNo',title : '公司编码',width : 80, halign : 'center', align : 'left'},
		        	     						{field : 'name',title : '公司名称',width : 200, halign : 'center', align : 'left'}
				                  			]],
				                  			callback:function(data){
				                  				if(data){
					                  				$('#companyNo_').val(data.companyNo);
					                  				$('#companyName_').combogrid('setValue',data.name);
				                  				}
				                  			}
				                  		}
				                  	}
								  },
				                  {field : 'clientType', title : '客户类型', width : 100,halign:'center',
				                	  formatter:editor.clientTypeFormatter,
				                  	   editor:{
					                  		type:'fascombobox',
					                  		options:{
					                  			width:100,
					                  			required:true,
					                  			id:'clientTypeId',
					                  			name:'clientType',
								       			valueField:'typeNo',
								       			textField:'typeName',
					                  			url:BasePath+'/base_setting/invoice_info_set/getClientType',
					                  			onChange:testChange
				                  			}
					                  	}
				                  },
				                 
				                  {field : 'clientName', title : '客户全称', align:'left',width : 200,align:'left',halign:'center',
				                  	 editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			required:true,
				                  			id:'clientName_',
				                  			name:'clientName',
				                  			inputWidth:200
				                  		}
				                  	}
				                  },
				                  {field : 'clientShortName', title : '客户简称', align:'left',width : 150,halign:'center',
					                  editor:{
					                  		type:'readonlybox',
					                  		options:{
				                  				id:'clientShortName_',
				                  				name:'clientShortName'
				                  			}
					                  	}
				                  },
				                  {field : 'invoiceType', title : '发票类型',  align:'left',width : 80,align:'center',halign:'center',
				                  	formatter: editor.invoiceTypeformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'invoiceType',
				                  			data: [{'value':'0', 'text': '普通发票'}, {'value':'1', 'text':'增值票'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'invoiceName',title : '开票名称',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'month',title : '有效月份',width : 80,align:'center',halign:'center',
					                  editor:{
					                  		type:'fasdatebox',
					                  		options:{
					                  			id:'fasDate',
					                  			name:'fasDate',
					                  			dateFmt:'yyyyMM',
					                  			required:false
					                  		}
					                  	}
				                  },		
				                  {field : 'status',title : '状态',width : 80,align:'center',halign:'center',
				                    formatter: editor.statusformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'status',
				                  			data: [{'value':'0', 'text': '备选'}, {'value':'1', 'text':'首选'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'ncClientNo', title : 'NC客户编码',  align:'left',width : 125,halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:'maxLength[20]'
				                  		}
				                  	}
				                  },
				                  {field : 'ncClientName', title : 'NC客户档案', align:'left',width : 150,halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:'maxLength[32]'
				                  		}
				                  	}
				                  },
				                  {field : 'taxpayerNo',title : '纳税人识别号',width : 160,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:'maxLength[20]'
				                  		}
				                  	}
				                  },
				                  {field : 'bankName',title : '开户行',width : 180,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[60]'
				                  		}
				                  	}
				                  },				                  
				                  {field : 'accountNo',title : '账号',width : 180,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[30]'
				                  		}
				                  	}
				                  },	
				                  {field : 'address',title : '地址',width : 250,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[255]'
				                  		}
				                  	}
				                  },				                  
				                  {field : 'telephoneNumber',title : '电话',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[20]'
				                  		}
				                  	}
				                  },				                  
				                  {field : 'postAddress',title : '发票邮寄地址',width : 250,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[255]'
				                  		}
				                  	}
				                  },				                  
				                  {field : 'contactPerson',title : '收票联系人',width : 100,align:'center',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[32]'
				                  		}
				                  	}
				                  },	
				                  {field : 'contactNumber',title : '联系电话',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[20]'
				                  		}
				                  	}
				                  },
				                  {field : 'companyNo',title : '公司编码 ',width : 80,align:'left',halign:'center',
					                  editor:{
					                  		type:'readonlybox',
					                  		options:{
				                  				id:'companyNo_',
				                  				name:'companyNo',
				                  				readonly:true
				                  			}
					                  	}
				                  },
				                   {field : 'clientNo', title : '客户编码', align:'left',width : 120,halign:'center',
					                  editor:{
					                  		type:'readonlybox',
					                  		options:{
				                  				id:'clientNo_',
				                  				name:'clientNo',
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