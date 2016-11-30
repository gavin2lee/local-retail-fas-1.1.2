<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>结算单(地区间结算应收)</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/balanceBillCommon.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_among/areaBalance.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<body class="easyui-layout">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false">
	<div data-options="title:'单据明细'">
		<div class="easyui-layout" data-options="fit:true,border:false">
		    <#--按钮-->
			<div data-options="region:'north',border:false" class="toolbar-region">
	        	<@p.billToolBar type="area_among_balance_listBar"/>
	        </div>
	        
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
				    <#--主档信息-->
					<div data-options="region:'north',border:false">
					  <div class="search-div">
	                     <form name="mainDataForm" id="mainDataForm" method="post">
	                        <input type="hidden" name="id" /></td>
	                        <input type="hidden" name="askPaymentNo" id="askPaymentNo" /></td>
	                        <input type="hidden" name="invoiceNo" id="invoiceNo"/></td>
	                        <input type="hidden" name="outQty" id="outQty" />
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
										<input class="easyui-validatebox ipt"  name="billNo" id="billNo"  style="width: 190px;"/>
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
										<th><span class="ui-color-red">*</span>调出公司： </th>
										<td>
											<input class="easyui-company  ipt"  name="salerName" id="salerNameId" 
											data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:200,isDefaultData : false"/>
											<input type="hidden" name="salerNo" id="salerNoId"/>
										</td>
										<th>出库金额： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="outAmount" id="outAmount" />
										</td>
										<th>扣项金额： </th>
										<td>
											<input class="easyui-validatebox  ipt" name="deductionAmount" id="deductionAmountId" />
										</td>
										<th>本期应收：</th>
										<td>
											<input class="easyui-numberbox  ipt"  name="balanceAmount" id="balanceAmount" data-options="precision:2"/>
										</td>
									</tr>	
									<tr>
										<th><span class="ui-color-red">*</span>调入公司： </th>
										<td>
											<input class="easyui-company  ipt"  name="buyerName"  id="buyerNameId"
											 data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',required:true,inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:200,isDefaultData : false"/>
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
									    <th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
										<td>
											<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId"
											 data-options="multiple:true,inputNoField:'brandUnitNo',inputNameField:'brandUnitNameId',inputWidth:200"/>
											<input type="hidden" name="brandUnitNo" id="brandUnitNo"/>
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
				<#--明细tab-->
				<div data-options="region:'center',border:false" >		
					<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
					</div>		
				</div>
				<#--页脚-->
			 	<div data-options="region:'south',border:false">
                    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
            	</div>
            </div>
		</div>
            	
	   </div>
	</div>
</div>
	
<#--弹出窗口-->
<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:350,height:300" > 
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
						data-options="multiple:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',required:true,inputNoField:'salerNo',inputNameField:'salerName',inputWidth:200,isDefaultData : false"
					/> 
					<input type="hidden" name="salerNo" id="salerNo"/>
			    </td>
			</tr>
			<tr>
				<th>调入公司：</th>
				<td>
					<input class="easyui-company ipt" id="buyerName" name="buyerName"
					  data-options="multiple:true,queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNo',inputNameField:'buyerName',inputWidth:200,isDefaultData : false"
					/>
					<input type="hidden" name="buyerNo" id="buyerNo"/>
				</td>
			</tr>
			<tr>
				<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
				<td>
					<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameCondition" 
					data-options="multiple:true,inputNoField:'brandUnitNoCondition',inputNameField:'brandUnitNameCondition',inputWidth:160"/>
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
			<tr>
				<th>生成方式：</th>
				<td>
				     <input type="radio" name="mergeFlag" id="mergeFlagZ" value="0" checked/>
    				  按品牌部合并生成
        			 <input type="radio" name="mergeFlag" id="mergeFlagO" value="1"  />
      			         按品牌部分开生成
				</td>
			</tr>
			</tbody>
		</table>
	</form>
</div>
	
<div style="display:none">
      <@p.datagrid id="exportExcelDG" 
	        columnsJsonList="[
			    {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
				{field : 'brandName',title : '品牌',width : 100,align:'center'},
				{field : 'oneLevelCategoryName', title : '大类', width : 80,align:'center'},	
			    {field : 'twoLevelCategoryName', title : '二级大类', width : 80,align:'center'},	
				{field : 'itemCode',title : '商品编码',width : 150,align:'left',halign:'center'},	
				{field : 'itemName',title : '商品名称',width : 180,align:'left',halign:'center'},	
				{field : 'cost',title : '单价',width : 80,align:'right',halign:'center'},
				{field : 'sendQty',title : '发出数量',width : 80,align:'right',halign:'center'},				
				{field : 'sendAmount',title : '出库金额',width : 100,align:'right',halign:'center'}
			]" 
         />
</div>

</body>
</html>