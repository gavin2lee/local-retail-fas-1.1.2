<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
           <div data-options="region:'north',border:false">
            	<div class="search-div">
	                <#-- 主档信息  -->
	                <form id="mainDataForm"  method="post">
	                	<input type="hidden" name="id" id="id">		                        	 
	                	<table class="form-tb">
	                	    <col width="100px"/>
	                	 	<col />
	                	 	<col width="100px"/>
	                	 	<col />
	                	 	<col width="100px"/>
	                	 	<col />	
	                	 	<col width="100px"/>
	                	 	<col />		                        	 	
	                        <tbody>
	                         <input type="hidden"  name="invoiceRegisterNo" id="invoiceRegisterNo" />
	                         <input type="hidden"  name="useFlag" id="useFlag" />
	                         <!--<input type="hidden"  name="status" id="status" />-->
	                         <input type="hidden"  name="currentUser" id="currentUser" />
	                         <input type="hidden"  name="organNo" id="organNoId" />
	                         <input type="hidden"  name="organName" id="organNameId" />
	                         <input type="hidden"  name="month" id="month" />
	                         <input type="hidden" name="invoiceInfoStatus" id="invoiceInfoStatus" />
	                         <input type="hidden"  name="checkRows" id="checkRows" />
	                         <tr>
								<th><span class="ui-color-red">*</span>结算类型：</th>
								<td>
									<input class="ipt" name="balanceType" id="searchbalanceType"/>
								</td>
								<th>结算单号：</th>
								<td>
									<input class="ipt" iptSearch name="balanceNo" id="balanceNoStr" data-options="required:true"/> 
									<input name="balanceAmount" id="balanceAmountStr" type="hidden"/> 
								</td>
		                        <th>开票申请号：</th>
								<td>
									<input class="easyui-validatebox ipt" name="billNo" id="billNo" readonly="true" style='width:140px' />  
								</td>	
			                    <th>单据状态：</th>
					            <td>
					            	<input class="easyui-validatebox ipt" name="status" id="status" disabled="true" />
					            </td>
						    </tr>
	                        <tr>	
	                         	<th><span class="ui-color-red">*</span>开票方名称：</th> 
								<td>
									<input class="easyui-company ipt" name="salerName" id="salerName" 
									data-options="required:true,inputNoField:'salerNo', inputNameField:'salerName',inputWidth:150,callback: billBalanceInvoiceApply.selectCompany" />
									<input type="hidden"  name="salerNo" id="salerNo" 	/>
								</td>
							    <th><span class="ui-color-red">*</span>申请开票日期：</th>
								<td>
									<input class="easyui-datebox ipt" name="invoiceApplyDate" id="invoiceApplyDate" data-options="required:true" style='width:140px' />  
								</td>
									<th><span class="ui-color-red">*</span>交票日期：</th>
							    <td>
							    	<input class="easyui-datebox ipt" name="postPayDate" id="postPayDate" data-options="required:true" style='width:140px' />
							    </td>
					            <th >预开票：</th>
								<td>
									<input class="ipt" name="preInvoice" id="preInvoice"/>
								</td>
							</tr>
	                        <tr>	
	                        	 <th><span class="ui-color-red">*</span>客户名称：</th>
							    <td>
							    	<input class="easyui-customerInvoiceInfo" name="buyerName" id="buyerName" 
							    	 data-options="required:true,inputNoField:'buyerNo', inputNameField:'buyerName',inputWidth:150,callback: billBalanceInvoiceApply.loadCustomer"  />
							     	<input type="hidden"  name="buyerNo" id="buyerNo" 	/>	
							    </td>			
								 <th>开票名称：</th>
					            <td>
					            	<input class="easyui-validatebox ipt" id="invoiceName" name ="invoiceName" style='width:140px'/>
					            </td>
	                         	  <th>纳税人识别号：</th>
								<td>
									<input class="easyui-validatebox ipt" name="taxRegistryNo" id="taxRegistryNo" style='width:140px' />  
								</td>												    										  
								<th>发票类型：</th>
								<td>
									<input class="ipt" name="invoiceType" id="invoiceType"/>
								</td>
						     </tr>	
						     <tr>
						     <th>品牌：</th>
								 <td>
								 	<input class="easyui-validatebox ipt" name="brandName" id="brandName" style='width:140px'/> 
								 	<input type="hidden" name="brandNo" id="brandNo"/>  
								 </td>		
							     <th>收票方电话：</th>
							     <td>
							     	<input class="easyui-validatebox ipt" name="buyerTel" id="buyerTel" style='width:140px' />
							     </td>
							     <th>联系人：</th>
							     <td>
							     	<input class="easyui-validatebox ipt" name="contactName" id="contactName" style='width:140px'/>
							     </td>
							     <th>联系人电话：</th>
							     <td>
							     	<input class="easyui-validatebox ipt" name="tel" id="tel" style='width:140px'/>
							     </td>
						     </tr>		    
						     <tr>
								 <th>开户行：</th>
								 <td>
								 	<input class="easyui-validatebox ipt" name="bankName" id="bankName" style='width:140px'/>  
								 </td>
								 <th>账号：</th>
							     <td>
							     	<input class="easyui-validatebox ipt" name="bankAccount" id="bankAccount" style='width:140px'/>
							     </td>
							     <th>开票金额：</th>
							     <td>
							     	<input class="easyui-numberbox ipt" name="amount" id="amountStr" data-options="precision:2" style='width:140px'/>
							     </td>
							     <th>币别：</th>
	                             <td>
	                              <input class="easyui-combobox"  name="currencyName" id="currencyName" style='width:150px'
										  data-options="valueField:'currencyCode',textField:'currencyName',width:'auto',url:BasePath + '/base_setting/currency_management/getCurrency?status=1',
											   onSelect: function(rec){    
											   		 $('#currencyName').val(rec.name);   
											   		 $('#currency').val(rec.currencyCode);   
	   										 	}
										 "/>
										 <input type="hidden" name="currency" id="currency" />
	                             </td>
						    </tr>  
						    <tr>
							     <th>收票方地址：</th>
							     <td colspan="3">
							     	<input class="easyui-validatebox"  name="buyerAddress" id="buyerAddress"  style="width:100%;" />
							     </td>
							     <th>邮寄地址：</th>
							     <td colspan="5">
							     	<input class="easyui-validatebox"  name="mailingAddress" id="mailingAddress" style="width:100%"/>
							     </td>
							     </tr>
							     <tr>
							     <th>备注：</th>
							     <td colspan="7">
							    	 <input type="text"  class="easyui-validatebox"   name="remark" id="remark" style="width:100%"/>
							      </td>
						     </tr>
	                        </tbody>
	                    </table>
					 </form>
            	</div> 
  			</div>
  			
		   	<div data-options="region:'center',border:false">		
				<div id="invoiceAppDtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
					<div title="按大类显示">
						<#include  "/WEB-INF/ftl/pages/bill_balance_invoice_apply/invoice_cateinfo.ftl">  
					</div>
					<div title="按明细显示">
						<#include  "/WEB-INF/ftl/pages/bill_balance_invoice_apply/invoice_dtlinfo.ftl">  
					</div>
				</div>		
   			</div>
   			
		</div>
	</div>
</div>
