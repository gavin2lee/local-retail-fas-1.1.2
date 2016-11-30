<!DOCTYPE html>
<head>
    <title>参考价格计算规则</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/cost_compute_rule.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "ruleSet.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "ruleSet.clear()","type":0},
	             {"id":"btn-enable11","title":"启用","iconCls":"icon-unlock","action":"ruleSet.doEnable()","type":27},
	             {"id":"btn-unable12","title":"停用","iconCls":"icon-lock","action":"ruleSet.doUnable()","type":100}
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
										data-options="inputNoField:'companyNo',inputNameField:'companyName',inputWidth:180"
									/>
									<input type="hidden" name="companyNo" id="companyNo"/>
								</td>									
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
			      <@p.datagrid id="costComputeRuleDg"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30,notexport:true},
				                  {field : 'id',hidden : 'true',align:'center',notexport:true},
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
								  {field : 'brandNo',hidden : true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandNo',
												name:'brandNo'
											}
										}
				                  },
				                  {field : 'brandName', title : '品牌', width : 100,halign:'center',
				                  	   editor:{
					                  		type:'brand',
					                  		options:{
					                  			inputNoField: 'brandNo',
					                  			required:true
				                  			}
					                  	}
				                  },
				                  {field : 'startYearCode',hidden : true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'startYearCode',
												name:'startYearCode'
											}
										}
				                  },
				                  {field : 'startYearName', title : '起始年份', align:'center',width : 100,halign:'center',
					                 editor:{
					                 	type:'fascombobox',
										options:{
											id:'startYearName',
											name:'startYearName',
											valueField:'itemname',
											textField:'itemname',
											url:BasePath+'/initCache/getLookupDtlsList?lookupcode=YEAR',
											width:100,
											onSelect:function(data){
												$('#startYearName').val(data.itemname);
												$('#startYearCode').val(data.itemvalue);
											}
										}
									}
				                  },
				                  {field : 'startSeasonCode',hidden : true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'startSeasonCode',
												name:'startSeasonCode'
											}
										}
				                  },
				                  {field : 'startSeasonName', title : '起始季节', width : 100,align:'center',halign:'center',
				                  	 editor:{
				                  		type:'fascombobox',
				                  			required:true,
				                  			options:{
												id:'startSeasonName',
												name:'startSeasonName',
												valueField:'itemname',
												textField:'itemname',
												url:BasePath+'/initCache/getLookupDtlsList?lookupcode=SEASON',
												width:100,
												onSelect:function(data){
													$('#startSeasonName').val(data.itemname);
													$('#startSeasonCode').val(data.itemvalue);
												}
											}
									    }
				                  },
				                  {field : 'endYearCode',hidden : true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'endYearCode',
												name:'endYearCode'
											}
										}
				                  },
				                  {field : 'endYearName', title : '终止年份',  align:'center',width : 100,align:'center',halign:'center',
				                  	editor:{
				                  			type:'fascombobox',
											options:{
												id:'endYearName',
												name:'endYearName',
												valueField:'itemname',
												textField:'itemname',
												url:BasePath+'/initCache/getLookupDtlsList?lookupcode=YEAR',
												width:100,
												onSelect:function(data){
													$('#endYearName').val(data.itemname);
													$('#endYearCode').val(data.itemvalue);
												}
											}
										}
				                  },
				                    {field : 'endSeasonCode',hidden : true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'endSeasonCode',
												name:'endSeasonCode'
											}
										}
				                  },
				                  {field : 'endSeasonName',title : '终止季节',width : 100,align:'center',halign:'center',
				                  	editor:{
				                  		type:'fascombobox',
				                  			required:true,
				                  			options:{
												id:'endSeasonName',
												name:'endSeasonName',
												valueField:'itemname',
												textField:'itemname',
												url:BasePath+'/initCache/getLookupDtlsList?lookupcode=SEASON',
												width:100,
												onSelect:function(data){
													$('#endSeasonName').val(data.itemname);
													$('#endSeasonCode').val(data.itemvalue);
												}
											}
									    }
				                  },		
				                  {field : 'computeRule', title : '计算规则',  align:'center',width : 100,halign:'center',
				                  	formatter: editor.computeRuleformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'computeRule',
				                  			data: [{'value':'1', 'text': '乘(*)'}, {'value':'2', 'text':'加(+)'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'weightedCostCoeff', title : '加权成本系数', align:'right',width : 100,halign:'center',
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			precision:2,
				                  			validType:'maxLength[32]'
				                  		}
				                  	}
				                  },
				                  {field : 'hqCostCoeff',title : '总部成本系数',width : 100,align:'right',halign:'center',
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			precision:2,
				                  			validType:'maxLength[18]'
				                  		}
				                  	}
				                  },
				                  {field : 'areaCostCoeff',title : '地区成本系数',width : 100,align:'right',halign:'center',
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			precision:2,
				                  			validType:'maxLength[60]'
				                  		}
				                  	}
				                  },		
				                  {field : 'status',title : '状态',width : 80,align:'center',halign:'center',
				                  	formatter:function(value,row,index){
				                  		if (row.status==1){
										return '启用';
										} else if(row.status==0){
											return '停用';
										}else{
											return '';
										}
				                  	},
				                    editor:{type:'checkbox',options:{on:'1',off:'0'}}
				                  },		                  
				                  {field : 'createUser',title : '创建人',width : 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'},
				                  {field : 'remark',title : '备注',width : 150,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  		validType:'maxLength[60]'
				                  		}
				                  	}
				                  }
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