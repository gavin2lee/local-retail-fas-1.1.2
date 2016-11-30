<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>总部其他入库-结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/balanceBillCommon.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_other_stock_in/hqOtherStockInBalance.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<input type="hidden" id ="billNoMenu" name="billNoMenu" value="${billNoMenu}" />
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
     <div data-options="title:'单据明细'">
         <div class="easyui-layout" data-options="fit:true">
            	<div data-options="region:'north',border:false">
					  <@p.billToolBar type="hq_other_balance"/>
                </div>
                
                <div data-options="region:'center',border:false">
                    <div class="easyui-layout" data-options="fit:true" id="subLayout">
		              <#--主档信息 -->
		              <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <form id="mainDataForm"  method="post">
		                         <input type="hidden" name="id" id="id">
		                         <input type="hidden" name="askPaymentNo" id="askPaymentNo" />
		                         <input type="hidden" name="invoiceNo" id="invoiceNo"/>
		                         <input type="hidden"  name="invoiceApplyNo" id="invoiceApplyNo" />
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
											<input class="easyui-validatebox ipt"  name="billNo" id="billNo"  style="width: 180px;"/>
										</td>
										<th>单据状态：</th>
							    		<td>
							    			<input class="easyui-validatebox  ipt"  name="statusName" id="statusNameId"/>
							    			<input type="hidden"  name="extendStatus" id="statusId" />
							    		</td>
										<th>单据名称： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="billName" id="billName" />
										</td>
										<th>币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
										<td>
											 <input class="easyui-combobox"  name="currencyName" id="currencyNameId"  style="width:130px;"
											 data-options="valueField:'currencyCode',textField:'currencyName',width:'auto',url:BasePath + '/area_balance_common/getCurrency',hasDownArrow:false"/>
       										 <input type="hidden" name="currency" id="currencyId"/>
										</td>
									</tr>	
									<tr>
										<th>地区公司： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="salerName" id="salerNameId" style="width: 180px;"/>
											<input type="hidden" name="salerNo" id="salerNoId"/>
										</td>
										<th>出库金额： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="outAmount" id="outAmount" />
										</td>
										<th>本期应付：</th>
										<td>
											<input class="easyui-numberbox  ipt"  name="balanceAmount" id="balanceAmount" data-options="precision:2"/>
										</td>
										<th></th>
										<td></td>
									</tr>	
									<tr>
										<th>总部公司： </th>
										<td>
											<input class="easyui-validatebox  ipt"  name="buyerName"  id="buyerNameId" style="width: 180px;"/>
											<input type="hidden" name="buyerNo" id="buyerNoId" />
										</td>
										<th>结算期间： </th>
										<td>
											<input class="easyui-validatebox ipt"  name="balanceStartDate"  id="balanceStartDate" />
										</td>
										<th>至：</th>
							    		<td>
							    			<input class="easyui-validatebox ipt"  name="balanceEndDate" id="balanceEndDate" />
							    		</td>
									    <th>结&nbsp;&nbsp;算&nbsp;&nbsp;日：</th>
									    <td>
									    	<input class="easyui-validatebox ipt" id="balanceDateId" name="balanceDate" />
									    </td>
									</tr>											
									<tr>
										<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
										<td>
												<input class="easyui-validatebox ipt" name="brandUnitName" id="brandUnitNameId" style="width: 180px;"/>
												<input type="hidden" name="brandUnitNo" id="brandUnitNo"/>
										</td>
										<th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</th>
										<td colspan="3">
											<input class="easyui-validatebox  ipt" name="remark" id="remarkId" style="width:355px;"/>
										</td>
										<th></th>
							    		<td></td>
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
</body>
</html>