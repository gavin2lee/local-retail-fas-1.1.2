<!DOCTYPE html>
<html>
<head>
<title>普通发票登记</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_register/invoce_register.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/bill_balance_invoice_register/list.json" 
					  save_url="/bill_balance_invoice_register/post" 
					  update_url="/bill_balance_invoice_register/put" 
					  del_url="/bill_balance_invoice_register/save" 
					  datagrid_id="invoiceRegisterdataGrid" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"invoiceRegister.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0}
	           ]
			/>
			
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 			  <tr>
	       		 			   <input type="hidden" name="balanceType" id="balanceType" value="${balanceType}">
								<td align="right" width="110">结算主体名称：</td>
									<td align="left" width="140">
										<input class="ipt"  iptSearch="company"  name="salerName" id="salerName" data-options="required:true" />
										<input type="hidden" name="salerNo"/>
									</td>									
								<td align="right" width="110">客户名称：</td>
								<td align="left" width="140"><input class="easyui-validatebox ipt" iptSearch="mall"   name="buyerName" id="buyerName"  />
								<input type="hidden"  name="buyerNo" id="buyerNo" 	/>	
							 	<td align="right" width="110">制单人：</td>
								<td align="left" width="140">
								<input class="ipt"   name="createUser" id="createUser" data-options="required:true" /></td>   
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "fas_common_editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "invoiceRegister.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="invoiceRegisterdataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'salerName',title : '结算主体名称', width : 180,editor:{type:'textbutton',options:invoiceRegister.selectCompany}},
				                  {field : 'salerNo',hidden:true, title : '结算主体编号', editor:{type:'textbutton'}},
				                  {field : 'buyerName', title : '客户名称', width : 150, editor:{type:'textbutton',options:invoiceRegister.selectMall}},
				                  {field : 'buyerNo',hidden:true, title : '客户编码', editor:{type:'textbutton'}},
				                  {field : 'invoiceNo', title : '发票号', width : 100,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{required:true}
				                  	}
				                  },
				                   {field:'invoiceDate',title:'发票日期',width:100,editor:'datebox'},				                  
				                  {field : 'amount', title : '发票金额', width : 100,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'estimatedAmount',title : '预估成本',width : 160,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'status',title : '单据状态 ',width : 80,align:'center',
				                    formatter: invoiceRegister.statusformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  	    	data: [
				                  	    	 {'value':'1', 'text':'创建'},
				                  	    	 {'value':'2', 'text': '审核'}
				                  	    	 ], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	  }
				                  	},
				                  	{field : 'preInvoice',title : '预开票',width : 80,align:'center',
				                  	 formatter: invoiceRegister.preInvoiceformatter, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  	    	data: [
				                  	    	 {'value':'1', 'text':'是'},
				                  	    	 {'value':'2', 'text': '否'}
				                  	    	 ], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	  }
				                  	},
				                   {field : 'remark',title : '备注',width : 150,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:false
				                  		}
				                  	}
				                  },
				                  {field : 'balanceType',title : '类型',width : 100,align:'center',
				                    formatter: invoiceRegister.balanceTypeformatter}, 
				                  {field : 'balanceNo',title : '结算单号',width : 100,align:'center'}, 
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'},  
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
					                   	  fas_common_editor.editRow(rowIndex, rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
</body>