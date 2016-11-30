<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>总部代采购结算(地区应付)</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/balanceBillCommon.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/hq_insteadOf_buy/areaInsteadBuyBalance.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<body class="easyui-layout">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'单据明细'">
		<div  class="easyui-layout" data-options="fit:true,border:false">
			 <#--按钮-->
			 <div data-options="region:'north',border:false">
	            <@p.billToolBar type="hq_insteadOf_area_query"/>
			 </div>
					
		  	<#--主档信息 -->
			<div data-options="region:'center',border:false" style="height:200px;">		
			   <div class="easyui-layout" data-options="fit:true" id="subLayout">
				  <div data-options="region:'north',border:false">
					 <div class="search-div">
		               <form name="mainDataForm" id="mainDataForm" method="post">
	                   <input type="hidden" name="askPaymentNo" id="askPaymentNo" />
	                   <input type="hidden" name="invoiceNo" id="invoiceNo"/></td>
	                   <input type="hidden" name="id" /></td>
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
									<input class="easyui-validatebox ipt"  name="billNo" id="billNo" style="width: 180px;"/>
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
									 data-options="valueField:'currencyCode',textField:'currencyName',width:'auto',url:BasePath + '/area_balance_common/getCurrency',hasDownArrow:false"/>
									 <input type="hidden" name="currency" id="currencyId"/>
								</td>
							</tr>	
							<tr>
								<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
								<td>
									<input class="easyui-validatebox  ipt"  name="buyerName"  id="buyerNameId" style="width: 180px;"/>
									<input type="hidden" name="buyerNo" id="buyerNoId" />
								</td>
								<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
								<td>
									<input class="easyui-validatebox ipt"   name="brandUnitName" id="brandUnitNameId" />
									<input type="hidden" name="brandUnitNo" id="brandUnitNo"/>
								</td>
								<th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</th>
								<td colspan="3">
									<input type="text"  name="remark" id="remarkId" style="width:360px;"/> 
								</td>
							</tr>	
							<tr>
								<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
								<td>
									<input class="easyui-validatebox  ipt"  name="salerName" id="supplierNameId" style="width: 180px;"/>
									<input type="hidden" name="salerNo"  id="salerNoId"/>
								</td>
								<th>结算期间： </th>
								<td>
									<input class="easyui-validatebox ipt"  name="balanceStartDate" id="balanceStartDate" />
								</td>
								<th>至：</th>
					    		<td>
					    			<input class="easyui-validatebox ipt"  name="balanceEndDate" id="balanceEndDate" />
					    		</td>
					    		<th>结&nbsp;&nbsp;算&nbsp;&nbsp;日：</th>
							    <td>
							    	<input class="easyui-validatebox ipt" name="balanceDate" id="balanceDateId" />
							    </td>
							</tr>
							<tr>
							    <th>入库金额： </th>
								<td>
									<input class="easyui-validatebox  ipt" name="entryAmount" id="entryAmountId" />
								</td>
								<th>退残金额： </th>
								<td>
									<input class="easyui-validatebox  ipt" name="returnAmount" id="returnAmountId" />
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
						</tbody>
					</table>
				  </form>
				</div>
			</div>
			<#--明细tab-->
			<div data-options="region:'center',border:false" style="height:200px;">		
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
</body>
</html>