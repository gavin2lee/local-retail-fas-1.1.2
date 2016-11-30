<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>普通发票登记</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
 <!--<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
 <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>-->
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_register/bill_common_invoice_register.js?version=${version}"></script>
</head>
<body>
           <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
                    	   {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
							{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"billCommonInvoiceRegister.add()","type":1},							
							{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"billCommonInvoiceRegister.save()","type":7},
							{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"billCommonInvoiceRegister.del()","type":3},							
							{"id":"mian_btn_aduit","title":"审核","iconCls":"icon-aduit","action":"billCommonInvoiceRegister.operate(2)","type":31},	
							{"id":"top_btn_cancel","title":"反审核","iconCls":"icon-aduit","action":"billCommonInvoiceRegister.operate(1)","type":32},
							<!--{"id":"mian_btn_cancel","title":"作废","iconCls":"icon-cancel","action":"billCommonInvoiceRegister.operate(3)","type":0},-->		
						    {"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"exportExcel()","type":4}, 			
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"billCommonInvoiceRegister.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"billCommonInvoiceRegister.downBill()","type":0}
						 ]/>	
                    </div>
                    
                    
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <form id="mainDataForm"  method="post">
		                        	 <input type="hidden" name="id" id="id">
		                        	 <input type="hidden" name="useFlag" id="useFlag">
		                        	 <input type="hidden"  name="organNo" id="organNoId" />
	                         		 <input type="hidden"  name="organName" id="organNameId" />
	                         		 <input type="hidden"  name="month" id="month" />
		                        	  <!--<input type="hidden" name="billNo" id="billNo">-->		                        	  
		                        	   <table class="form-tb">
		                        	    <col width="120px"/>
		                        	 	<col />
		                        	 	<col width="120px"/>
		                        	 	<col />
		                        	 	<col width="120px"/>
		                        	 	<col />
		                        	 	<col width="120px"/>
		                        	 	<col />
		                                <tbody>
		                                <tr>
		                                	<th>单据编码：</th>
											<td><input class="easyui-validatebox ipt" name="billNo" id="billNoId" readonly="true" style="width:150px;"/>  </td>
											<th>单据状态：</th>
											<td><input class="easyui-validatebox ipt" name="status" id="status" disabled="true"/> </td>
			                                <th>登记日期：</th>
											<td><input class="easyui-datebox easyui-validatebox ipt " name="billDate" id="billDate" style="width:150px;"/> </td>
										    <th>发票总金额：</th>
											<td><input class="disableEdit ipt" name="amount" id="amount" readonly="true" style="width:150px;"/>
												<input type="hidden" name="totalAmount" id="totalAmount" />
											</td>
										</tr>
										<tr>	
											<th><span class="ui-color-red">*</span>结算类型：</th>
											<td>
												<input class="ipt" name="balanceType"  id="balanceTypeId"/>
											</td>
											<th><span class="ui-color-red">*</span>开票申请单号：</th>
											<td>
												<input class="easyui-validatebox ipt" name="sourceNo" iptSearch id="sourceNoId" data-options="required:true"/>
											</td>
											<th><span class="ui-color-red">*</span>公司名称：</th>
											<td>
												<input class="easyui-company ipt"  name="salerName" id="salerName" 
												data-options="required:true,inputWidth:160,inputNoField:'salerNo', inputNameField:'salerName'" /><!--,callback:billCommonInvoiceRegister.selectSalerCompany -->
												<input type="hidden" name="salerNo" id="salerNo"/>
											</td>									
											<th><span class="ui-color-red">*</span>客户名称：</th>
											<td>
												<input class="easyui-customerInvoiceInfo ipt"  name="buyerName" id="buyerName" 
												data-options="required:true,inputNoField:'buyerNo', inputNameField:'buyerName',inputWidth:160" />
												<input type="hidden" name="buyerNo" id="buyerNo"/>
											</td>
											
										</tr>
										<tr>										
											<th>预开票：</th>
											<td>									    
										       <input class="easyui-validatebox ipt"  name="preInvoice" id="preInvoice" data-options="required:false" />
										    </td>
										    <th >发票类型：</th>
										    <td>									    
										       <input class="easyui-validatebox ipt"  name="invoiceType" id="invoiceType" data-options="required:false" />
										    </td>
										    <!--<th>结算期：</th>
										    <td><input class="easyui-datebox"  name="balanceStartDate" id="balanceStartDate"  data-options="maxDate:'balanceEndDate'" style="width:160px;"/></td>
											<th>&nbsp;&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<td> <input class="easyui-datebox"  name="balanceEndDate" id="balanceEndDate" data-options="minDate:'balanceStartDate'" style="width:160px;"/></td>
										</tr>
										<tr>										
											<th>店铺：</th>
											<td>									    
										       <input class="easyui-validatebox easyui-shop ipt" name="shopName" id="shopName_" style="width:160px"
										       data-options="inputWidth:160,inputNoField:'shopNo_', inputNameField:'shopName_'" />
  											   <input type="hidden" name="shopNo" id="shopNo_"/>
										    </td>-->
										    <th>备注：</th>
											<td colspan ="3">
												<input class="easyui-validatebox ipt" name="remark" id="remark" style="width:99%"/> 
											</td>
										</tr>
		                                </tbody>
		                            </table>
								 </form>
				            </div>   
		          		</div>
          <#--列表-->
          <div  data-options="region:'center',border:false">
	     	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbarDtl" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "billCommonInvoiceRegister.addDtl()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "billCommonInvoiceRegister.delDtl()","type":0}
		             {"id":"btn-import","title":"明细导入","iconCls":"icon-import","action":"iptSearch.doImport('发票明细导入.xlsx','/bill_balance_invoice_register/do_detail_import',0,billCommonInvoiceRegister.importDetailCallBack)","type":0}
		           ]
				/>
            </div>
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridCommonInvoiceDtl"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" emptyMsg="" rownumbers="false"  singleSelect="false" showFooter="true"
				           columnsJsonList="[
				                  {field : 't', checkbox:true,width : 30,notexport:true},
				                  {field : 'id',title : '单据序号',hidden : 'true',align:'center',notexport:true},
				                  {field : 'billNo',title : '单据编码',hidden : 'true',align:'center',notexport:true},
				                  {field : 'invoiceCode', title : '发票代码', width : 100,align:'left',halign:'center',
				                  	editor:{type:'validatebox',options:{required:true,
				                  	validType:['rangeLength[1,18]']}}},
				                  {field : 'invoiceNo', title : '发票号码', width : 100,align:'left',halign:'center',
				                  	editor:{type:'validatebox',options:{required:true,
				                  	validType:['rangeLength[1,18]']}}},
				                  {field:'invoiceDate',title:'发票日期',width:100,editor:'datebox'},				                  
				                  {field : 'invoiceAmount', title : '发票金额', width : 80,align:'right',halign:'center',
				                  	editor:{type:'numberbox',options:{required:true,precision:2}}},
				                  {field : 'taxRate',title : '税率',width : 60,align:'right',halign:'center',
				                  	editor:{type:'numberbox',options:{required:true, precision:2,min:0,max:1}}},
				                  {field : 'noTaxAmount',title : '不含税金额',width : 90,align:'right',halign:'center',
				                  	editor:{type:'numberbox',options:{required:true, precision:2}}},
				                  {field : 'taxAmount',title : '税额',width : 90,align:'right',halign:'center',
				                  	editor:{type:'numberbox',options:{required:true, precision:2}}},
				                  {field : 'estimatedAmount',title : '预估成本',width : 80,align:'right',halign:'center',
				                  	editor:{type:'numberbox',options:{required:false,precision:2}}},
				                  {field : 'qty', title : '数量', width : 80,align:'right',halign:'center',
				                  	editor:{type:'numberbox'}},	
				                  {field : 'express',title : '快递公司',width : 150,
									editor:{type:'validatebox',options:{required:false}}},
								  {field : 'deliveryNo',title : '快递单号',width : 150,
									editor:{type:'validatebox',options:{required:false,validType:['rangeLength[1,18]']}}},	
								  {field : 'deliveryDate',title : '快递日期',width : 100,
									editor:{type:'datebox',options:{required:false}}},	
								  {field : 'confirmUser',title : '确认人',width : 100},
								  {field : 'confirmTime',title : '确认日期',width : 100},	                  			                  				                 
				                  {field : 'remark',title : '备注',width : 200,align:'center',
				                  	editor:{type:'validatebox',options:{required:false}}}	                 
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
							              	billCommonInvoiceRegister.editRow(rowIndex, rowData);	
					                   }
			         }'/>
			    </div>    
            </div>    
         </div> 
          </div>
         </div>
        </div>
        
        <div id="myDetailFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDetailDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	           		 {field : 'pass',title:'记录是否合法',width:90,formatter: function(value,row,index){
							if (value == 0){
								return '否';
							}
							return '是'
						}
			         },
			         {field : 'errorInfo',title:'错误信息',width:250,align:'left',halign:'center'},
	 				 {field : 'validateObj.invoiceCode',title:'发票编码',width:100},
	 				 {field : 'validateObj.invoiceNo',title:'发票号',width:100,align:'right'},
	 				 {field : 'validateObj.invoiceDate',title:'发票日期',width:100,align:'right'},
					 {field : 'validateObj.invoiceAmount',title:'发票金额',width:90,align:'right'},
					 {field : 'validateObj.taxRate', title : '税率', width : 90},
                     {field : 'validateObj.brandNo', title : '品牌编码', width : 100},	
	 				 {field : 'validateObj.categoryNo',title:'大类编码',width:100},
	 				 {field : 'validateObj.qty',title:'数量',width:80,align:'right'},
	 				 {field : 'validateObj.price',title:'价格',width:90,align:'right'},
					 {field : 'validateObj.express',title:'快递公司',width:150,align:'right'},
					 {field : 'validateObj.deliveryNo',title:'快递编号',width:100,align:'right'},
	 				 {field : 'validateObj.deliveryDate',title:'快递日期',width:100,align:'right'},
					 {field : 'validateObj.auditor',title:'确认人',width:100,align:'right'},
					 {field : 'validateObj.auditDate',title:'确认日期',width:100,align:'right'},
	 				 {field : 'validateObj.remark',title:'备注',width:180,align:'left'}]"	
         />
     </div>	
</body>
</html>