<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商场门店结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/initbalance.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shop_balance_diff.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/selectObjUtil.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shopbalance_deduct.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/balance_deduct.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/ShopBalance.1.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/PrintShopBalance.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shopbalance.diff.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/PrintDsShopBalance.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body>
<#--tabs层-->
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
	<div data-options="title:'单据明细'">
		<div class="easyui-layout" data-options="fit:true,border:true">
				<div data-options="region:'north',border:false">
					<@p.billToolBar type="shop_balance_listBar"/>
				 </div>
                <div data-options="region:'center',border:false">
                	<div class="easyui-layout" data-options="fit:true" id="subLayout">
		               <div data-options="region:'north',border:false">
						<#--表头-->
							<div class="search-div">
								<form name="mainDataForm" id="mainDataForm"  autocomplete="off" method="post" class="easyui-keyboard" data-options="type: 'form',lastFn: function(){$('#dtlTab').tabs('select', 1);balanceDiffEditor.insertRow();}">
									<table class="form-tb">
										<col width="100" />
										<col />
										<col width="100" />
										<col  />
										<col width="100" />
										<col />	
										<col width="100" />
										<col />							
										<tbody>
											<tr> 
											    <input type="hidden"  name="id" id="id" />
											    <input type="hidden"  name="status" id="status" />
											    <input type="hidden"  name="createUser" id="createUser" />
											    <input type="hidden"  name="invoiceApplyNo" id="invoiceApplyNo" />
											    <input type="hidden"  name="brandNo" id="brandNo1" />
											    <input type="hidden"  name="brandName" id="brandName1" />
											    <th><span class="ui-color-red">*</span>类型：</th>
											   	<td align="left">									    
											       <input class="easyui-validate ipt"  name="balanceType" id="balanceType" />
											     </td>
												<th>单据日期：</th>
												<td>
													<input class="easyui-validatebox ipt  readonly" name="billDate" id="billDate"   readonly="true"/> 
												</td>
												<th>单据状态：</th>
											    <td><input type="text" class="easyui-validatebox ipt readonly"   name="billStatusName" id="billStatusName"  readonly="true"/> </td>
											    <th>单据编码：</th>
												<td><input class="easyui-validatebox ipt readonly" name="balanceNo" id="balanceNo"  readonly="true" />  </td>
										    </tr>
											      
										    <tr> 	
										        <th><span class="ui-color-red">*</span>结算月：</th>
										     	<td>
										     		<input class="easyui-datebox easyui-validatebox ipt"  name="month" id="month" data-options="required:true,dateFmt:'yyyyMM'"  />
										     	</td>		
												<th>实际扣率：</th>
										     	<td><input class="ipt readonly"  name="actualRateName" id="actualRateName"/></td>
											    <th>合同扣率：</th>
										     	<td><input class="ipt"  name="contractRateName" id="contractRateName"/></td>
									         	<th>扣费总额：</th>
										     	<td>
										     		<input class="ipt"  name="mallDeductAmount" id="mallDeductAmount"/>
										     		<a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="mallDeductAmountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
										     	</td>
										     </tr>
											     
										     <tr> 
										       	<th><span class="ui-color-red">*</span>店铺：</th>
										     	<td>
											     	<input class="easyui-shop ipt" name="shortName" id="shortName" 
											     		data-options="inputNoField:'shopNo',inputNameField:'shortName',inputWidth:130,multiple:false,required:true,callback:selectMonthClickFn" />
											     	<input type="hidden"  name="shopNo" id="shopNo" />
											     </td>										     	 
											     <th>商场报数：</th>
											     <td><input class="easyui-numberbox ipt"  name="mallNumberAmount" id="mallNumberAmount" data-options="precision:2, validType:['maxLength[12]']"/></td>
											     <th>系统收入：</th>
											     <td><input class="ipt" name="systemSalesAmount" id="systemSalesAmount" /></td>
											     <th>报数差异：</th>
											     <td>
											     	<input class="ipt" name="salesDiffamount" id="salesDiffamount" />
											     	<a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="salesDiffamountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
											     </td>
									      	</tr>
										     <tr>	
								      			<th><span class="ui-color-red">*</span>公司：</th>
												<td>
													<input class="easyui-company ipt"  name="companyName" id="companyName" data-options="required:true,inputWidth:130" />
													<input type="hidden"  name="companyNo" id="companyNo" />
												</td>								     
										     	<th>商场开票金额：</th>
										     	<td><input class="easyui-numberbox ipt"  name="mallBillingAmount" id="mallBillingAmount" data-options="precision:2, validType:['maxLength[12]']"  /></td>
										      	<th>系统开票金额：</th>
										     	<td>
										     		<input class="ipt"  name="systemBillingAmount" id="systemBillingAmount"/>
										     		<a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="systemBillingAmountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
										     	</td>
											    <th>结算差异总额：</th>
										     	<td>
										     		<input class="ipt"  name="balanceDiffAmount" id="balanceDiffAmount" />
										     		<a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="balanceDiffAmountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
										     	</td> 
										     	
								            </tr>	
									            		                  		
								            <tr>
								                <th>结算期：</th>  
								                <td >
								                	<input type="hidden"  name="balanceStartDate" id="balanceStartDate"/>
							       		 			<input type="hidden"  name="balanceEndDate" id="balanceEndDate"/>
							       		 			<input class="ipt"    name="startEndDate" id="startEndDate" style="width:130px;"/>	
							       		 		</td>			
							       		 				     
										     	<th>收款差异：</th>
										     	<td><input class="ipt"  name="differenceAmount" id="differenceAmount"/>
										     	<a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="differenceAmountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a> </td>
										      	<th>预估差异：</th>
										     	<td ><input class="ipt"   name="estimateAmount" id="estimateAmount"/></td>
										     	<th>预收款金额：</th>
											    <td><input class="ipt readonly" name="prepaymentAmount" id="prepaymentAmount" /></td>
										     	<td id="showRateExpenseRemindTr" style="height:15px;"></td>
										     </tr>	
										     
										     <tr>
										     	<th>结算描述：</th>
										       	<td colspan="3"><input type="text"  class="ipt"   name="balanceDesc" id="balanceDesc"  style="width:94%;"/> </td>									      
										       	<input type="hidden"  name="remark" id="remark" />
										       	<th>应返款金额：</th>
										     	<td><input class="ipt readonly"  name="returnedAmount" id="returnedAmount"/>
										     	<a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="returnedAmountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td> 
										       	<th>冲销预收款：</th>
											    <td><input class="ipt readonly" name="usedPrepaymentAmount" id="usedPrepaymentAmount" /></td>
											</tr>
											<tr id="showWarnMsgTr" style="height:15px;">
											</tr>	
										</tbody>
									</table>
		         				</form>
		         			 </div>
	                	</div>
						<div data-options="region:'center',border:false" style="height:960px;" id="dtlTabDiv">		
							<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
								<div title="销售单查询">
									<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_dtlbill.ftl">
								</div>
								<div title="结算差异">
									<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_diffbill.ftl">
								</div>
								<div title="票前费用">
									<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_deductbill.ftl">
								</div>
								<div title="票后费用">
									<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_deductafterbill.ftl">
								</div>
								<div title="综合店结算">
									<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_brand.ftl">
								</div>
							</div>		
						</div>
		
		           <div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				     <form name="dataForm" id="dataForm" method="post"  >
				     	<div>
							 <table class="form-tb">	                    	   
	                            <tbody>
	                               <tr >
		                             <span class="ui-color-red" align:'center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请慎重使用该功能，尽可能缩小范围</span>
		                           </tr>
								   <th align="right" width="110"><span class="ui-color-red">*</span>公司名称：</th>
								      	<td align="left" width="140">
									  	<input class="ipt easyui-validate easyui-company" data-options="inputNoField:'companyNo_',inputNameField:'companyName',required:true" name="companyName" id="companyName_"/>
										<input type="hidden" name="companyNo" id="companyNo_"/>
								        </td>
								        <tr>
								          <th><span class="ui-color-red">*</span>类型：</th>
										   <td>									    
											<input class="easyui-combobox  easyui-validate  ipt" name="balanceType" style="width:80px;"  
										      data-options="valueField: 'label',textField: 'value',required:true,
											  data: [{label: '1',value: '正式'},{label: '2',value: '预估'}]" />
										   </td>
										 </tr>
								    <tr>
								        <th><span class="ui-color-red"></span>管理城市：</th>
		                                <td>
		                                	<input class="ipt easyui-validate  easyui-organ" name="organName" id="organName_"  data-options="inputNoField:'organNo_',inputNameField:'organName'" />
								            <input type="hidden" name="organNo" id="organNo_"/>
		                               </td>
								   </tr>
								   <tr>
									   	<th>商业集团：</th>
									    <td>
										 <input class="ipt easyui-bsgroups" data-options="inputNoField:'bsgroupsNo_',inputNameField:'bsgroupsName',required:false"  name="bsgroupsName" id="bsgroupsName_"/>
								         <input type="hidden" name="bsgroupsNo" id="bsgroupsNo_"/>
									    </td>
								   </tr>
								    <tr>
								        <th>商场：</th>
		                                <td>
		                                 <input class="easyui-validatebox ipt easyui-mall" data-options="inputNoField:'mallNo_',inputNameField:'mallName',required:false"  name="mallName" id="mallName_"/>
								         <input type="hidden" name="mallNo" id="mallNo_"/>
		                                </td>
								   </tr>
								    <tr>
									   	<th>店铺类型：</th>
										<td >									    
											<input class="ipt"  name="payType" id="payType" />
										</td>
								   </tr>
		                           <tr>
		                           <th><span class="ui-color-red">*</span>结算月：</th>
								   <td>
									    <input class="easyui-datebox ipt"  name="month" id="month" style="width:80px;" data-options="inputNoField:'month',inputNameField:'month',required:true,dateFmt:'yyyyMM'"  />
								   </td>  
								   </tr>
								   <tr>
								    <td align="right" width="110">店铺：</td>
									<td align="left" width="140"><input class="ipt easyui-shop"  data-options="inputNoField:'shopNo_',inputNameField:'shopName_',multiple:true,required:false"   name="shopName" id="shopName_" style="width:300px;"/>
									<input type="hidden" name="shopNo" id="shopNo_"/>
									</tr>									
								 </tbody>
							 </table>
						</div>
					 </form>	
			   </div>
			   
					 	<div data-options="region:'south',border:false">
		                	<#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
		             	</div>
		          </div>
		       </div>
		    </div>
		</div>
		</div>
	</div>
	
	<div id="printHBContrPanel" class="easyui-dialog" title="My Dialog" style="width:200px;height:200px;"   
	    data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	     <div align='center'>
	     	<form id="printHBForm">
		     		<select multiple id="printHBSelect" align="left" style="height:200px;width:100%">
					</select>
	     	</form>
	     </div>
	</div> 
	
<div id="printContrPanel" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	     <div align='center'>
	     	<form id="printForm">
		     	<div style="width:49%; float:left;">
		     		<select multiple id="printSelect" ondblclick="removeTo();" align="left" style="height:200px;width:100%">
					</select>
					</div><div style="width:50%; float:left;">
					<select multiple id="printSelect1" ondblclick="removeFrom();" align="right" style="height:200px;width:100%">
					</select>
				</div>
	     	</form>
	     </div>
</div>  
<div id="confirmBillGeneratePanel" class="easyui-dialog" data-options="iconCls:'icon-info',resizable:true,modal:true,closed:true">   
	    存在预估结算单，请选择该结算单的生成方式？
</div> 
</body>
</html>