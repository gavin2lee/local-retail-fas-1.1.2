<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>总部其他出库-结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/balanceBillCommon.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_other_stock_out/hqOtherStockOutBalance.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
            		<#--按钮-->
                	<div data-options="region:'north',border:false">
						 <@p.billToolBar type="hq_other_out_balance"/>
                    </div>
                    
                    <div data-options="region:'center',border:false">
                       <div class="easyui-layout" data-options="fit:true" id="subLayout">
                          <#-- 主档信息  -->
			              <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <form id="mainDataForm"  method="post">
		                          <input type="hidden" name="id" id="id">
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
										<th>单据编号： </th>
										<td><input class="easyui-validatebox ipt"  name="billNo" id="billNo"  style="width: 190px;"/></td>
										<th>单据状态：</th>
							    		<td>
							    			<input class="easyui-validatebox  ipt"  name="statusName" id="statusNameId"/>
							    			<input type="hidden"  name="status" id="statusId" />
							    		</td>
										<th>单据名称： </th>
										<td><input class="easyui-validatebox  ipt"  name="billName" id="billName" /></td>
										<th>币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
										<td>
											 <input class="easyui-combobox"  name="currencyName" id="currencyNameId"  style="width:130px;"
											  data-options="valueField:'currencyCode',textField:'currencyName',width:'auto',url:BasePath + '/area_balance_common/getCurrency',
												   onSelect: function(rec){    
												   		 $('#currencyNameId').val(rec.name);   
												   		 $('#currencyId').val(rec.currencyCode);   
	       										 	}
       										 "/>
       										 <input type="hidden" name="currency" id="currencyId"/>
										</td>
									</tr>	
									<tr>
										<th><span class="ui-color-red">*</span>总部公司： </th>
										<td>
											<input class="easyui-company  ipt"  name="salerName" id="salerNameId" 
											 data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole',required:true,inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:200"/>
											<input type="hidden" name="salerNo" id="salerNoId"/>
										</td>
										<th>出库金额： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="outAmount" id="outAmount" />
										</td>
										<th>本期应收：</th>
										<td>
											<input class="easyui-numberbox  ipt"  name="balanceAmount" id="balanceAmount" data-options="precision:2"/>
										</td>
										<th></th>
										<td></td>
									</tr>	
									<tr>
										<th><span class="ui-color-red">*</span>地区公司： </th>
										<td>
											<input class="easyui-company  ipt"  name="buyerName"  id="buyerNameId" 
											data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:200"/>
											<input type="hidden" name="buyerNo" id="buyerNoId" />
										</td>
										<th><span class="ui-color-red">*</span>结算期间： </th>
										<td>
											<input class="easyui-datebox easyui-validatebox ipt"  name="balanceStartDate" data-options="required:true,maxDate:'balanceEndDate'"  id="balanceStartDate" />
										</td>
										<th><span class="ui-color-red">*</span>至：</th>
							    		<td>
							    			<input class="easyui-datebox easyui-validatebox ipt"  name="balanceEndDate" data-options="required:true,minDate:'balanceStartDate'"  id="balanceEndDate" />
							    		</td>
									    <th><span class="ui-color-red">*</span>结&nbsp;&nbsp;算&nbsp;&nbsp;日：</th>
									    <td>
									    	<input class="easyui-datebox easyui-validatebox ipt" id="balanceDateId" 
									    	 name="balanceDate" data-options="required:true" />
									    </td>
									</tr>											
									<tr>
										<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
										<td>
											 <input class="easyui-brand  ipt"  id="brandNameId" data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameTemp',inputWidth:200"/>
       										 <input type="hidden" name="brandNo" id="brandNoId"/>
       										 <input type="hidden" name="brandName" id="brandNameTemp"/>
										</td>
										<th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</th>
										<td colspan="3">
											<input class="easyui-validatebox  ipt" name="remark" id="remarkId" style="width:355px;"/>
										</td>
										<th>开票单号：</th>
							    		<td>
							    			<input class="easyui-validatebox  ipt" name="invoiceApplyNo" id="invoiceApplyNo" />
							    		</td>
									</tr>		
								</tbody>
						    </table>
						 </form>
                    </div>
                </div>
		    
             	<div id="dtlDiv" data-options="region:'center',border:false" style="height:350px;">
                	 <div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
                	 </div>
                </div>
             	
                <div data-options="region:'south',border:false">
                    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
                </div>
                
              </div>
         	</div>
                
       </div>
    </div>
</div>

<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
     <form name="dataForm" id="dataForm" method="post"  >
			 <table class="form-tb">
        	    <col width="100px"/>
        	 	<col />
                <tbody>
				   <tr>
				        <th><span class="ui-color-red">*</span>总部公司：</th>
                        <td>
                        	<input class="easyui-company ipt" id="salerName" name="salerName"
                        	 data-options="multiple:true,queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole',required:true,inputNoField:'salerNo',inputNameField:'salerName',inputWidth:200"/> 
							<input type="hidden" name="salerNo" id="salerNo"/>
                       </td>
				   </tr>
				   <tr>
				        <th><span class="ui-color-red">*</span>地区公司：</th>
                        <td>
                    		<input class="easyui-company ipt" id="buyerName" name="buyerName" 
                    		data-options="multiple:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'buyerNo',inputNameField:'buyerName',inputWidth:200" />
							<input type="hidden" name="buyerNo" id="buyerNo"/>
                        </td>
				   </tr>
				   <tr>
					   	<th><span class="ui-color-red">*</span>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
					    <td>
							 <input class="easyui-brand  ipt"  id="brandName" 
							  data-options="multiple:true,required:true,inputNoField:'brandNo',inputNameField:'brandNameCon',inputWidth:200"/>
							 <input type="hidden" name="brandNo" id="brandNo"/>
							 <input type="hidden" name="brandName" id="brandNameCon"/>
					    </td>
				   </tr>
                   <tr>
                   <th><span class="ui-color-red">*</span>结&nbsp;&nbsp;算&nbsp;&nbsp;日：</th>
				   <td>
					    <input class="easyui-datebox easyui-validatebox ipt" id="balanceDateBatch" 
					    	 name="balanceDate" data-options="required:true" />
				   </td>  
				   </tr>
				  <tr>
                    	<th><span class="ui-color-red">*</span>结算起始日期：</th>
					    <td><input class="easyui-validatebox easyui-datebox ipt" name="balanceStartDate" id="balanceStartDateBatch" data-options="required:true, maxDate:'balanceEndDateBatch'" /></td>
                   </tr>
                   <tr>
						<th><span class="ui-color-red">*</span>结算结束日期：</th>
						<td><input class="easyui-validatebox easyui-datebox ipt" name="balanceEndDate" id="balanceEndDateBatch" data-options="required:true, minDate:'balanceStartDateBatch'" /></td>		 
                   </tr>
				 </tbody>
			 </table>
	 </form>	
</div>

</body>
</html>