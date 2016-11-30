<!DOCTYPE html>
<head>
    <title>到票确认</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/invoice_confirm.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action","invoiceConfirm.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action","invoiceConfirm.clear()","type":0},
	             {"id":"btn-confirm","title":"到票确认","iconCls":"icon-aduit", "action","invoiceConfirm.doConfirm()", "type":83},
	             {"id":"btn-reConfirm","title":"反确认","iconCls":"icon-aduit", "action","invoiceConfirm.doReConfirm()", "type":10},
	             {"id":"btn-create","title":"生成采购发票","iconCls":"icon-aduit", "action","invoiceConfirm.createInvoice()", "type":84},
	             {"id":"btn-unable","title":"导出","iconCls":"icon-export","action","invoiceConfirm.doExport('dataGridJG','/invoice_confirm/export','到票确认')","type":4}
	           ]
			/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="searchForm" id="searchForm" method="post">
				     	<input type="hidden" name="balanceTypeIn" value="(11)"/>
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
		       		 			<th>确认状态：</th>
		       		 			<td>
		       		 				<input class="easyui-combobox ipt"  name="useFlag" id="useFlagId" data-options="valueField:'id',textField:'text',data:comfirmData" style="width:150px"/>
		       		 			</td>
		       		 		    <th>发票登记号：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt" name="billNo" style="width:150px"/>
		       		 			</td>
		       		 			<th>出票方：</th>
		       		 			<td>
		       		 				<input class="easyui-company ipt"  name="salerName" id="salerNameCond" 
		       		 				data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNoCond',inputNameField:'salerNameCond',inputWidth:150,isDefaultData:false"/>
		       		 				<input type="hidden" name="salerNo" id="salerNoCond"/>
		       		 			</td>
		       		 			<th>收票方：</th>
		       		 			<td>
		       		 				<input class="easyui-company ipt"  name="buyerName" id="buyerNameCond" 
		       		 				data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'buyerNoCond',inputNameField:'buyerNameCond',inputWidth:150,isDefaultData:false"/>
		       		 				<input type="hidden" name="buyerNo" id="buyerNoCond"/>
		       		 			</td>
		       		 		</tr>
		       		 		<tr>
		       		 			<th>确认人：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt"  name="confirmUser"  style="width:140px"/>
		       		 			</td>
		       		 			<th>确认日期：</th>
							    <td>
							    	<input class="easyui-datebox"  name="confirmTimeStart"  readonly="true" id="confirmTimeStart" data-options="maxDate:'confirmTimeEnd'" style="width:150px"/>
							    </td>
								<th>至：</th>
							    <td>
							    	<input class="easyui-datebox"  name="confirmTimeEnd"  readonly="true" id="confirmTimeEnd" data-options="minDate:'confirmTimeStart'" style="width:150px"/>
							    </td>
							    <th></th>
							    <td></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.subdatagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
				           rownumbers="true"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,notexport:true},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'balanceType',hidden : 'true',align:'center'},
				                  {field : 'useFlagName',title : '确认状态',width:80,align:'center',
					                  formatter:function(value,row,index){
					                  	if (row.useFlag==1){
											return '已确认';
										} else if(row.useFlag==0){
											return '未确认';
										}else{
											return '';
										}
				                  	}
				                  },
				                  {field : 'invoiceNoFlag',title : '采购发票号',width : 150,align:'left',halign:'center'},
				                  {field : 'billNo',title : '发票登记号',width : 150,align:'left',halign:'center',halign:'center'},
				                  {field : 'salerNo',title : '出票方编码',width : 80,align:'left',halign:'center',halign:'center'},
				                  {field : 'salerName',title : '出票方名称',width : 200,align:'left',halign:'center',halign:'center'},
				                  {field : 'buyerNo',title : '收票方编码',width : 80,align:'left',halign:'center',halign:'center'},
				                  {field : 'buyerName',title : '收票方名称',width : 200,align:'left',halign:'center',halign:'center'},
				                  {field : 'amount',title : '发票金额合计',width : 100,align:'right',halign:'center'},
				                  {field : 'confirmUser',title : '确认人',width : 80,align:'center',halign:'center'},
								  {field : 'confirmTime',title : '确认日期',width : 100,align:'center',halign:'center'},	
				                  {field : 'balanceTypeName',title : '结算类型',width : 120,align:'center',halign:'center'}
				               ]" 
				                  loadSubGridUrl="/bill_common_register_dtl/list.json"
						            subPagination="false"
						            subGridColumnsJsonList="[
						                  {field : 'invoiceCode', title : '发票代码', width : 120,align:'left',halign:'center'},
						                  {field : 'invoiceNo',title : '发票号码',width : 100,align:'left',halign:'center',halign:'center'},
							              {field : 'invoiceDate',title : '发票日期',width : 100,align:'center',halign:'center'},
						                  {field : 'invoiceAmount',title : '发票金额',width : 100,align:'right',halign:'center'},
						                  {field : 'taxAmount',title : '含税金额',width : 100,align:'right',halign:'center'},
						                  {field : 'taxRate',title : '税率',width : 60,align:'right',halign:'center'},
						                  {field : 'noTaxAmount',title : '不含税金额',width : 100,align:'right',halign:'center'},
					                      {field : 'qty',title : '数量',width : 60,align:'right',halign:'center'},
						                  {field : 'deliveryDate',title : '快递日期',width : 100,align:'center',halign:'center'},
						                  {field : 'express',title : '快递公司',width : 120,align:'left',halign:'center',halign:'center'},
						                  {field : 'deliveryNo',title : '快递单号',width : 120,align:'left',halign:'center',halign:'center'},
								          {field : 'remark',title : '备注',width : 150,align:'left',halign:'center'},
								          {field : 'confirmUser',title : '确认人',width : 100,align:'center',halign:'center'},
										  {field : 'confirmTime',title : '确认日期',width : 100,align:'center',halign:'center'}	 
									 ]"
					        />
			</div>
		 </div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="dataForm" id="dataForm" method="post"  >
	     	<div>
				 <table class="form-tb">
            	    <col width="100px"/>
            	 	<col />
                    <tbody>
					   <tr>
                        	<th><span class="ui-color-red">*</span>确认日期：</th>
						    <td><input class="easyui-validatebox easyui-datebox ipt" name="confirmDate" id="confirmDateId"  data-options="required:true" /></td>
                       </tr>
					 </tbody>
				 </table>
			</div>
		 </form>	
  </div>
  
</body>
</html>