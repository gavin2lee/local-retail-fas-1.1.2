<!DOCTYPE html>
<head>
    <title>期初应付金额</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_period_meet_money/hq_period_meet_money.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "periodMeetMondy.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "periodMeetMondy.clear()","type":0},
	             {"id":"btn-insert","title":"新增","iconCls":"icon-add", "action" : "editor.insertRow()", "type":1},
		         {"id":"btn-remove","title":"删除","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":3},
		         {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7},
		         {"id":"btn-save","title":"已付款","iconCls":"icon-save", "action" : "editor.optPayStatus(1)", "type":18},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"periodMeetMondy.doImport()","type":6},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"periodMeetMondy.exportExcel()","type":4}
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
								<th><span class="ui-color-red">*</span>供应商：</th>
	                            <td><input class="easyui-supplier ipt"  name="supplierName" id="supplierName" data-options="inputWidth:150"/>
	                            <input type="hidden" name="supplierNo" id="supplierNo"/>
	                            </td>
							</tr>
	       		 		 </tbody>
	       		 	</table>
				</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="periodMeetMoneyDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true" 
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
								  {field : 'supplierNo',title : '供应商编码 ',width : 80,align:'left',halign:'center',
					                  editor:{
					                  		type:'readonlybox',
					                  		options:{
				                  				id:'supplierNo_',
				                  				name:'supplierNo',
				                  				readonly:true
				                  			}
					                  	}
				                  },
				                  {field : 'supplierName', title : '供应商', align:'left',width : 200,halign:'center',
					                  editor:{
					                  		type:'combogrid',
					                  		options:{
				                  				required:true,
					                  			id:'supplierName_',
					                  			name:'supplierName',
					                  			inputWidth:200,
					                  			panelWidth : 400,
												panelHeight : 300,
					                  			idField : 'shortName',
												textField : 'shortName',
					                  			noField:'supplierNo',
					                  			inputNoField:'supplierName',
												url : BasePath + '/base_setting/supplier/list.json',
												columns:[[
											          {field : 'supplierNo',title : '供应商编码',width : 100,align:'left'},
										          	  {field : 'shortName',title : '供应商名称',width : 200,align:'left'}
									            ]],
									            callback:function(data){
									  				if(data){
									      				$('#supplierNo_').val(data.supplierNo);
									      				$('#supplierName_').val(data.shortName);
									  				}
									  			}
				                  			}
					                  	}
				                  },
				                  {field : 'initialDate',title : '日期',width : 120,align:'center',halign:'center',
					                  editor:{
					                  		type:'fasdatebox',
					                  		options:{
					                  			id:'initialDate',
					                  			name:'initialDate',
					                  			dateFmt:'yyyy-MM-dd',
					                  			required:false
					                  		}
					                  	}
				                  },		
				                  {field : 'qty', title : '期初应付数量',  align:'left',width : 90,halign:'center',
				                  	editor:{
				                  		type:'numberbox'
				                  	}
				                  },
				                  {field : 'amount', title : '期初应付金额', align:'left',width : 90,halign:'center',
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			required:true,precision:2
				                  		}
				                  	}
				                  },
				                  {field : 'cost',title : '期初牌价额',width : 90,align:'left',halign:'center',
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			required:true,precision:2
				                  		}
				                  	}
				                  },
				                  {field : 'dueDate',title : '到期日',width : 120,align:'left',halign:'center',
				                  	editor:{
				                  		type:'fasdatebox',
				                  		options:{
				                  			id:'dueDate',
				                  			name:'dueDate',
				                  			dateFmt:'yyyy-MM-dd',
				                  			required:false
				                  		}
				                  	}
				                  },
				                  {field : 'payStatusStr',title : '付款状态',width : 80,align:'center',halign:'center'},
				                  {field : 'payDate',title : '付款日期',width : 120,align:'center',halign:'center'},
				                  {field : 'createUser',title : '创建人',width : 100,align:'center',halign:'center'}, 
			              		  {field : 'createTime',title : '创建日期',width : 150,align:'center',halign:'center'},
			              		  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'}, 
			              		  {field : 'updateTime',title : '修改日期',width : 150,align:'center',halign:'center'}
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