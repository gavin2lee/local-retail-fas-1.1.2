<!DOCTYPE HTML>
<html>
<head>
    <title>地区调货结算期</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/transfer_balance_date/transferBalanceDate.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	     <#if organTypeNo=='U010102'>
		     <@p.toolbar id="toolbar" listData=[
				  	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog.clear()","type":0}, 
		             {"id":"main_btn_build","title":"批量生成","iconCls":"icon-build-some","type":55,"action":"dialog.batchAdd()"},
	             	 {"id":"btn-export","title":"导出","iconCls":"icon-export","action" : "dialog.exportExcel()","type":4}
	           ]
			 />
		 </#if>	   
		         
		 <#if organTypeNo=='U010101'>
		     <@p.toolbar id="toolbar2" listData=[
				  	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog.clear()","type":0}, 
	             	 {"id":"btn-export","title":"导出","iconCls":"icon-export","action" : "dialog.exportExcel()","type":4}
	           ]
			 />
		 </#if>	   
		   
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
       		 			 <tr>
							<th>调出公司：</th>
							<td>
								<input class="easyui-company ipt"  name="salerName" id="salerNameCon" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNoCon',inputNameField:'salerNameCon',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="salerNo" id="salerNoCon"/>
							</td>	
							<#if organTypeNo=='U010102'>
						    <th>调入公司： </th>
							<td>
								<input class="easyui-company ipt"   name="buyerName" id="buyerNameCon" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoCon',inputNameField:'buyerNameCon',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="buyerNo" id="buyerNoCon"/>
							</td>	
							</#if>	   
							<th>开票标志：</th>
							<td>
								<input class="easyui-combobox ipt" name="invoiceFlag" 
								data-options="valueField: 'label',textField: 'value',
									data: [{label: '1',value: '已开票'},{label: '0',value: '未开票'}]" />
							</td>
							<th>结算标志：</th>
							<td>
								<input class="easyui-combobox ipt" name="balanceFlag" 
								data-options="valueField: 'label',textField: 'value',
									data: [{label: '1',value: '已生成'},{label: '0',value: '未生成'}]" />
							</td>														
       		 			</tr>
       		 			<tr>
       		 				<th>结算月：</th>
							<td>
								<input class="easyui-datebox easyui-validatebox ipt" name="balanceMonth" id="balanceMonthCond"
								 data-options="dateFmt:'yyyyMM'" style="width:150px"/>
							</td>
							<th></th>
							<td></td>	
							<th></th>
							<td></td>	
							<th></th>
							<td></td>			
       		 			</tr>
	       		 	</table>
				</form>
		</div>
	</div>
			
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
				 	 {"id":"btn-insert","title":"新增行","iconCls":"icon-add-row", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-del-row", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7}				 
		           ]
				/>
            </div>
				           
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="balanceDateDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		               checkOnSelect="true" rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			                      {field : 't', checkbox:true, width : 30, notexport:true},
			                      {field : 'id',hidden : 'true',align:'center',notexport:true},
			                  	  {field : 'salerNo', title : '调出公司编码', align:'left',halign:'center',width : 100, 
			                  	  	editor:{
				                  		type:'company',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'companyNo_',
				                  			name:'salerNo',
				                  			idField: 'companyNo',
											textField: 'companyNo',
											noField: 'name',
				                  			inputNoField:'companyName_',
				                  			required:true
				                  		}
				                  	}
				                  },
			                      {field : 'salerName', title : '调出公司', align:'left',halign:'center',width : 250, 
					                  editor:{
						                  type:'readonlybox',
						                  options:{
						                  	id:'companyName_',
						                  	name:'salerName',
						                  	required:true
						                  }
						              }
				                  },
				                  {field : 'buyerNo', title : '调入公司编码', align:'left',halign:'center',width : 100,organType:'U010102', 
			                  	  	editor:{
				                  		type:'company',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'buyerNoId',
				                  			name:'buyerNo',
				                  			idField: 'companyNo',
											textField: 'companyNo',
											noField: 'name',
				                  			inputNoField:'buyerNameId',
				                  			url: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',
				                  			required:true
				                  		}
				                  	}
				                  },
			                      {field : 'buyerName', title : '调入公司', align:'left',halign:'center',width : 250,organType:'U010102', 
					                  editor:{
						                  type:'readonlybox',
						                  options:{
						                  	id:'buyerNameId',
						                  	name:'buyerName',
						                  	required:true
						                  }
						              }
				                  },
				                  {field : 'balanceMonth',title : '结算月',width : 100, align:'center',
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
				                  {field:'balanceStartDate',title:'起始日期',width:100,align:'center',
				                  	editor:{
				                  		type:'datebox',
										options:{
											required:true
										}
									}
								  },			
				                  {field:'balanceEndDate',title:'终止日期',width:100,align:'center',
									editor:{
				                  		type:'datebox',
										options:{
											required:true
										}
									}
								  },		
				                  {field : 'invoiceFlag',title : '开票标志 ',width : 80,notexport:true,align:'center',
				                    formatter: editor.transferFlagformatter,
				                 	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'invoiceFlag',
				                  			data: [{'value':'0', 'text': '未开票'}, {'value':'1', 'text':'已开票'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
								  },
								  {field : 'balanceFlag',title : '结算标志 ',width : 80,notexport:true,align:'center',organType:'U010102',
				                    formatter: editor.balanceFlagformatter,
				                 	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'balanceFlag',
				                  			data: [{'value':'0', 'text': '未生成'}, {'value':'1', 'text':'已生成'}], 
				                  			valueField: 'value', textField: 'text'
				                  		}
				                  	}
								  },
				                  {field : 'remark',title : '备注',width : 200,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox'
				                  	}
				                  }, 
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'},   
			                  ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex,rowData){
					              editor.editRow(rowIndex, rowData);
			              }}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>

<#--弹出窗口-->
<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:350,height:250" > 
	<form  id="dataForm" method="post">
		<input type="hidden" id="id" name="id" />
		<table  class="form-tb">
			<col width="100" />
		    <col />
		    <tbody>
			<tr>
				<th><span class="ui-color-red">*</span>调出公司：</th>
				<td>
					<input class="easyui-company ipt" id="salerName" name="salerName"
						data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'salerNo',inputNameField:'salerName',inputWidth:200,isDefaultData : false"
					/> 
					<input type="hidden" name="salerNo" id="salerNo"/>
			    </td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>调入公司：</th>
				<td>
					<input class="easyui-company ipt" id="buyerName" name="buyerName"
					  data-options="multiple:true,queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',required:true,inputNoField:'buyerNo',inputNameField:'buyerName',inputWidth:200,isDefaultData : false,pageSize:50"
					/>
					<input type="hidden" name="buyerNo" id="buyerNo"/>
				</td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>结&nbsp;&nbsp;算&nbsp;&nbsp;月：</th>
				<td>
					<input class="easyui-datebox easyui-validatebox ipt" name="balanceMonth" id="balanceDateBatch" data-options="required:true,dateFmt:'yyyyMM'"/>
				</td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>起始日期：</th>
				<td>
					<input class="easyui-datebox easyui-validatebox ipt" name="balanceStartDate" id="balanceStartDateId" data-options="required:true,maxDate:'balanceEndDateId'"/>
				</td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>终止日期：</th>
				<td>
					<input class="easyui-datebox easyui-validatebox ipt"  name="balanceEndDate" id="balanceEndDateId" data-options="required:true,minDate:'balanceStartDateId'"/>
				</td>
			</tr>
			</tbody>
		</table>
	</form>
</div>