<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>地区自购结算</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/balanceBillCommon.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_private_purchase/areaBuyBalance.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<body class="easyui-layout">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'单据明细'">
	    <div class="easyui-layout" data-options="fit:true">
			    <#--按钮-->
				<div data-options="region:'north',border:false" class="toolbar-region">
			 		 <@p.billToolBar type="area_purchase_balance"/>
                </div>
					
			    <div data-options="region:'center',border:false">
                   <div class="easyui-layout" data-options="fit:true" id="subLayout">
		            <#--主档信息 -->
		            <div data-options="region:'north',border:false">
					   <div class="search-div">
		                 <form name="mainDataForm" id="mainDataForm" method="post">
	                       <input type="hidden" name="askPaymentNo" id="askPaymentNo" />
	                       <input type="hidden" name="invoiceNo" id="invoiceNo"/>
	                       <input type="hidden" name="entryQty" id="entryQty" />
	                       <input type="hidden" name="id" />
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
										<td>
											<input class="easyui-validatebox ipt"  name="billNo" id="billNo" style="width: 190px;"/>
										</td>
										<th>单据状态：</th>
							    		<td>
							    			<input class="easyui-validatebox  ipt"  name="statusName" id="statusNameId"/>
							    			<input type="hidden"  name="status" id="statusId" />
							    		</td>
							    		<th>单据名称： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="billName" id="billName" />
										</td>
										<th>币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别： </th>
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
										<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
										<td>
											<input class="easyui-company ipt"  name="buyerName"  id="buyerNameId"  
											data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:200,isDefaultData : false"/>
											<input type="hidden" name="buyerNo" id="buyerNoId" />
										</td>
										<th>入库金额： </th>
										<td>
											<input class="easyui-validatebox  ipt" name="entryAmount" id="entryAmountId" />
										</td>
										<th>扣项金额： </th>
										<td>
											<input class="easyui-validatebox  ipt" name="deductionAmount" id="deductionAmountId" />
										</td>
										<th>应付金额： </th>
										<td>
											<input class="easyui-numberbox  ipt"  name="balanceAmount" id="balanceAmount" data-options="precision:2"/>
										</td>
									</tr>	
									<tr>
										<th><span class="ui-color-red">*</span>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
										<td>
											<input class="easyui-supplier  ipt"  name="salerName" id="supplierNameId" data-options="required:true,inputNoField:'salerNoId',inputNameField:'supplierNameId',inputWidth:200"/>
											<input type="hidden" name="salerNo"  id="salerNoId"/>
										</td>
										<th><span class="ui-color-red">*</span>结算期间： </th>
										<td>
											<input class="easyui-datebox easyui-validatebox ipt"  name="balanceStartDate" id="balanceStartDate" 
											 data-options="required:true,maxDate:'balanceEndDate'" />
										</td>
										<th><span class="ui-color-red">*</span>至：</th>
							    		<td>
							    			<input class="easyui-datebox easyui-validatebox ipt"  name="balanceEndDate" id="balanceEndDate" 
							    			 data-options="required:true,minDate:'balanceStartDate'"/>
							    		</td>
							    		<th><span class="ui-color-red">*</span>结&nbsp;&nbsp;算&nbsp;&nbsp;日：</th>
									    <td>
									    	<input class="easyui-datebox easyui-validatebox ipt" id="balanceDateId" 
									    	 name="balanceDate" data-options="required:true" />
									    </td>
									</tr>
									<tr>
										<th><span class="ui-color-red">*</span>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
										<td>
											<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" data-options="required:true,inputNoField:'brandUnitNo',inputNameField:'brandUnitNameId',inputWidth:160"/>
											<input type="hidden" name="brandUnitNo" id="brandUnitNo"/>
										</td>
										<th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</th>
										<td colspan="3">
											<input type="text"  name="remark" id="remarkId" style="width:360px;"/> 
										</td>
										<th></th>
							    		<td></td>
									</tr>										
								</tbody>
						</table>
					</form>
				</div>
			</div>
		
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
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
	
<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:350,height:300" > 
	<form  id="dataForm" method="post">
		<input type="hidden" id="id" name="id" />
		<table  class="form-tb">
			<col width="100" />
		    <col />
		    <tbody>
		    <tr>
				<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</th>
				<td>
					<input class="easyui-company ipt" id="buyerName" name="buyerName"  
					data-options="multiple:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'buyerNo',inputNameField:'buyerName',inputWidth:200,isDefaultData:false"/>
					<input type="hidden" name="buyerNo" id="buyerNo"/>
				</td>
			</tr>
			<tr>
				<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</th>
				<td>
					<input class="easyui-supplier ipt" name="salerName" id="supplierId" data-options="multiple:true,inputNoField:'supplierNoId',inputNameField:'supplierId',inputWidth:200" /> 
					<input type="hidden" name="salerNo" id="supplierNoId"/>
			    </td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
				<td>
					<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameCondition" 
					data-options="multiple:true,required:true,inputNoField:'brandUnitNoCondition',inputNameField:'brandUnitNameCondition',inputWidth:160"/>
					<input type="hidden" name="brandUnitNo" id="brandUnitNoCondition"/>
				</td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>结&nbsp;&nbsp;算&nbsp;&nbsp;日：</th>
				<td>
					<input class="easyui-datebox easyui-validatebox ipt" name="balanceDate" id="balanceDateBatch" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>结算起始日期：</th>
				<td>
					<input class="easyui-datebox easyui-validatebox ipt" name="balanceStartDate" id="balanceStartDateId" data-options="required:true,maxDate:'balanceEndDateId'"/>
				</td>
			</tr>
			<tr>
				<th><span class="ui-color-red">*</span>结算结束日期：</th>
				<td>
					<input class="easyui-datebox easyui-validatebox ipt"  name="balanceEndDate" id="balanceEndDateId" data-options="required:true,minDate:'balanceStartDateId'"/>
				</td>
			</tr>
			</tbody>
		</table>
	</form>
</div>
	
</body>
</html>