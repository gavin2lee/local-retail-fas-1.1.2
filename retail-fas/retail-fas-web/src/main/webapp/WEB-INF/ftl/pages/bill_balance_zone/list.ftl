<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>批发结算</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/BillBalanceZone.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>

</head>
<body>
<input type="hidden" id="isHq" name="isHq" value="${isHq}" />
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
            	<div data-options="region:'north',border:false">
                	<@p.toolbar id="top_bar" listData=[
                		{"id":"bill_btn-view","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
                		{"id":"bill-btn-add","title":"新增","iconCls":"icon-add","action":"bill.toAddBill()","type":1},
						{"id":"bill-btn-save","title":"保存","iconCls":"icon-save","action":"bill.saveBill()","type":7},
						{"id":"bill-btn-prev","title":"上单","iconCls":"icon-prev","action":"bill.upBill()","type":0} ,
						{"id":"bill-btn-next","title":"下单","iconCls":"icon-next","action":"bill.downBill()","type":0},
						{"id":"bill-btn-busconfirm","title":"业务确认","iconCls":"icon-aduit","action":"bill.confirm('0', '3')","type":74} ,
						{"id":"bill-btn-financeconfim","title":"财务确认","iconCls":"icon-aduit","action":"bill.confirm('0', '4')","type":79},
						{"id":"bill-btn-batch-add","title":"批量生成","iconCls":"icon-build-some","action":"bill.toBatchAddBill()","type":55},
						{"id":"top_btn_4","title":"选单","iconCls":"icon-add","action":"bill.toCreateBalance()","type":77},
						{"id":"bill-btn-del","title":"删除","iconCls":"icon-del","action":"bill.delBill()","type":3},
						{"id":"bill-btn-invalid","title":"打回","iconCls":"icon-cancel","action":"bill.confirm('0', '99')","type":73} ,
						{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20}
					 ]/>	
                </div>
                <div data-options="region:'center',border:false">
                	<div class="easyui-layout" data-options="fit:true" id="subLayout">
		               <div data-options="region:'north',border:false">
	                    <div class="search-div">
	                        <#-- 主档信息  -->
	                        <form id="mainDataForm"  method="post">
	                        	 <table class="form-tb">
	                        	    <col width="100"/>
	                        	 	<col />
	                        	 	<col width="100"/>
	                        	 	<col />
	                        	 	<col width="100"/>
	                        	 	<col />
	                        	 	<col width="100"/>
	                        	 	<col />
	                                <tbody>
	                                	 <tr>
	                                    	<th>单据编号：</th>
	                                        <td>
	                                        	 <input type="hidden" name="id" id="id" />
	                                        	<input class="easyui-validatebox  ipt"  name="billNo" id="billNo"/>
	                                        </td>
	                                        <th>单据状态：</th>
			                                <td>
			                                	<input class="easyui-validatebox  ipt"  name="statusName" id="statusName"/>
			                                	<input type="hidden" name="status" id="status"/>
			                                </td>
			                                <th>管理城市： </th>
			                                <td>
											<!--<input class="ipt" multiSearch="organ" name="organNameFrom" id="organNameFrom" /> <input type="hidden" name="organNoFrom" id="organNoFrom"/>-->
											<input class="easyui-organ ipt" name="organNameFrom" id="organNameFrom" data-options="multiple:'true',inputNoField:'organNoFrom',inputNameField:'organNameFrom'"/> 
											<input type="hidden" name="organNoFrom" id="organNoFrom"/>
											</td>
	                                        <th>品牌部：</th>
	                                        <td>
	                                        	<input class="easyui-brandunit ipt" id="brandUnitName" name="brandUnitName" data-options="multiple:'true', inputNoField:'brandUnitNo', inputNameField:'brandUnitName',inputWidth:160"/>
	                                        	<input type="hidden" id="brandUnitNo" name="brandUnitNo"/>
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                   		<th><span class="ui-color-red">*</span>公司名称：</th>
	                                        <td>
	                                        <#if isHq==true>
										      	<input class="easyui-company ipt"  name="salerName" id="salerName" 
					                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'salerNo',inputNameField:'salerName',inputWidth:160"/>
					                       		<input type="hidden" name="salerNo" id="salerNo"/>
										      </#if>
										      <#if isHq==null || isHq==''>
										      	<input class="easyui-company ipt"  name="salerName" id="salerName" 
					                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNo',inputNameField:'salerName',inputWidth:160"/>
					                       		<input type="hidden" name="salerNo" id="salerNo"/>
										      </#if>
	                                        <!-- 
	                                        	<input class="easyui-company ipt" id="salerName" name="salerName" 
	                                        		data-options="required:true,inputNoField:'salerNo', inputNameField:'salerName',inputWidth:160"/>
	                                        	<input type="hidden" name="salerNo" id="salerNo"/>
	                                        -->
	                                        </td>
	                                        <th><span class="ui-color-red">*</span>客户名称：</th>
	                                        <td>
	                                        	<input class="easyui-wholesale_zone_customer ipt" name="buyerName" id="buyerName" 
	                                        		data-options="required:true,inputNoField:'buyerNo', inputNameField:'buyerName',inputWidth:160"/>
	                                        	<input type="hidden" name="buyerNo" id="buyerNo"/>
	                                        </td>
	                                        <th><span class="ui-color-red">*</span>结算期：</th>
	                                        <#if isHq==true>
                                        	<td>
										    	<input class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="balanceStartDate" id="balanceStartDate" defaultValue="startDate" data-options="{maxDate:'balanceEndDate',required:true}"/>
										    </td>
											<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<td>
												<input class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="balanceEndDate" id="balanceEndDate" defaultValue="endDate" data-options="{minDate:'balanceStartDate',required:true}"/>
											</td>
	                                        </#if>
	                                        <#if isHq==null || isHq==''>
										    <td>
										    	<input class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="balanceStartDate" id="balanceStartDate" defaultValue="currentMonthFirstDayDate" data-options="{maxDate:'balanceEndDate',required:true}"/>
										    </td>
											<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<td>
												<input class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="balanceEndDate" id="balanceEndDate" defaultValue="currentMonthLastDayDate" data-options="{minDate:'balanceStartDate',required:true}"/>
											</td>
											</#if>
	                                    </tr>
	                                    <tr>
	                                    	<th>出库数量：</th>
	                                        <td><input class="readonly ipt" name="outQty" id="outQty"  data-options="{inputWidth:50}"/></td>
	                                        <th>出库金额：</th>
	                                        <td><input class="readonly ipt" name="outAmount" id="outAmount"/></td>
	                                        <th>返利：</th>
	                                        <td><input class="readonly ipt" name="sumRebateAmount" id="sumRebateAmount" data-options="{precision:2}" /></td>
	                                        <th>是否票后返利： </th>
									 		<td><input type="checkbox" name="isAfterBill" id="isAfterBill" onclick="selectChange()" /></td>
									 		<input type="hidden" name="rebateAmount" id="rebateAmount" />
									 		<input type="hidden" name="invoiceRebateAmount" id="invoiceRebateAmount" />
	                            
	                                    </tr>
	                                    <tr>
	                                    	<th>其他扣项：</th>
	                                        <td><input class="readonly ipt" name="deductionAmount" id="deductionAmount"/></td>
	                                    	<th>应收金额：</th>
	                                        <td><input class="readonly ipt" name="balanceAmount" id="balanceAmount"/>
	                                        <a href="javascript:void();"><span class="l-btn-text icon-xq l-btn-icon-left" id="systemBillingAmountTooltip">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
										    </td>
										    <th><span class="ui-color-red">*</span>币别：</th>
	                                        <td><input class="ipt" id="currency" combobox="currency" name="currency"  data-options="required:true, editable:false"/></td>
	                                    	<th><span class="ui-color-red">*</span>结算日：</th>
		                                        <td><input class="easyui-datebox easyui-validatebox  ipt"  id="balanceDate" name="balanceDate" defaultValue="currentDate" data-options="required:true"/></td>
	                                    </tr>
	                                    <tr>
	                                        <!-- 关闭其他费用
	                                         <th>其他费用：</th> -->
	                                         <td colspan='2'><input type='hidden' name="otherPrice" id="otherPrice"/></td>
	                                    	 <th>备注：</th>
	                                        <td colspan='9'><input class="easyui-validatebox  ipt"  id="remark" name="remark" style="width:80%"/></td>
	                                    </tr>
	                                </tbody>
	                            </table>
							 </form>
	                    </div>
	                </div>
	                <div data-options="region:'center',border:false" style="height:350px;">
	                	 <div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
	                	 	<div title="出库明细">
									<@p.datagrid id="balanceDtlDataGrid"  rownumbers="true" showFooter="true" 
										isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
										columnsJsonList="[
											{field : 'billNo',title :'单据编码',width : 150},
											{field : 'sendDate',title : '单据日期',width : 100},
											{field : 'itemCode',title : '商品编码',width : 150},				
											{field : 'itemName',title : '商品名称',width : 150},
											{field : 'sendQty',title : '数量',width : 90, align : 'right'},
											{field : 'cost',title : '单价',width : 90, align : 'right'},
											{field : 'otherDeductCost',title : '分摊金额 (票前返利+扣项-其他费用)',width : 180, align : 'right'},
											{field : 'sendAmount',title : '金额',width : 100, align : 'right', formatter:function(value,row,index){
													 return (row.sendAmount-row.otherDeductCost).toFixed(4);
											}},
											{field : 'organNameFrom',title : '管理城市',width : 100},
											{field : 'brandName',title : '品牌',width : 150},
											{field : 'oneLevelCategoryName',title : '一级大类',width : 150},
											{field : 'twoLevelCategoryName',title : '二级大类',width : 150},
											{field : 'tagPrice',title : '牌价',width : 100, align : 'right'},
											{field : 'billRebateDiscountStr',title : '返利后折扣',width : 100, align : 'right'},
											{field : 'discount',title : '扣率',width : 100, align : 'right', formatter:function(value,row,index){
												if(row.discount != null){
													 return row.discountStr;
												}
											}}
										]" 
									/>
							</div>
							<div title="品牌大类扣项汇总">
								<#include  "/WEB-INF/ftl/pages/bill_balance_zone/brandCategoryDeduction_tab.ftl">
							</div>
							<div title="发票信息">
					        	<#include  "/WEB-INF/ftl/pages/bill_balance_zone/invoice_tab.ftl">
					        </div>
					        <div title="其他扣项">
					        	<#include  "/WEB-INF/ftl/pages/bill_balance_zone/deduction_tab.ftl">
					        </div>
	                	 </div>
                	</div>
	              </div>
         	</div>
             	
         	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
			     <form name="dataForm" id="dataForm" method="post"  class="pd10">
			     	<div>
						 <table class="form-tb">
                    	    <col width="100px"/>
                    	 	<col />
                            <tbody>
							   <tr>
							        <th><span class="ui-color-red">*</span>公司名称：</th>
	                                <td>
                                	  <#if isHq==true>
								      	<input class="easyui-company ipt"  name="batchSalerName" id="batchSalerName" 
			                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'batchSalerNo',inputNameField:'batchSalerName',inputWidth:160"/>
			                       		<input type="hidden" name="batchSalerNo" id="batchSalerNo"/>
								      </#if>
								      <#if isHq==null || isHq==''>
								      	<input class="easyui-company ipt"  name="batchSalerName" id="batchSalerName" 
			                        	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'batchSalerNo',inputNameField:'batchSalerName',inputWidth:160"/>
			                       		<input type="hidden" name="batchSalerNo" id="batchSalerNo"/>
								      </#if>
	                                	<!-- 
	                                	<input class="easyui-company ipt" name="batchSalerName" id="batchSalerName" 
	                                		data-options="required:true,inputNoField:'batchSalerNo', inputNameField:'batchSalerName',multiple:true,inputWidth:260"/>
	                                	<input type="hidden" name="batchSalerNo" id="batchSalerNo" />
	                                	-->
	                                </td>
	                           </tr>
	                           <tr>
							        <th>客户名称：</th>
	                                <td>
	                                	<input class="easyui-wholesale_zone_customer ipt" name="batchBuyerName" id="batchBuyerName" 
	                                		data-options="inputNoField:'batchBuyerNo', inputNameField:'batchBuyerName',inputWidth:260,multiple:true"/>
	                                	<input type="hidden" name="batchBuyerNo" id="batchBuyerNo" />
	                                </td>
							   </tr>
							   <tr>
							        <th>品牌部名称：</th>
	                                <td>
	                                	<input class="easyui-brandunit ipt" name="batchBrandUnitName" id="batchBrandUnitName" 
	                                		data-options="required:false,inputNoField:'batchBrandUnitNo', inputNameField:'batchBrandUnitName',inputWidth:260,multiple:true"/>
	                                	<input type="hidden" name="batchBrandUnitNo" id="batchBrandUnitNo" />
	                                </td>
							   </tr>
							   <tr>
							   		<th>管理城市：</th>
							   		<td>
							   			<input class="easyui-organ ipt" data-options="required:false,multiple:'true',inputNoField:'multiOrganNoFrom',inputNameField:'multiOrganNameFrom',inputWidth:260"/> 
										<input type="hidden" name="organNoFrom" id="multiOrganNoFrom"/>
										<input type="hidden" name="organNameFrom" id="multiOrganNameFrom"/>
							   		</td>
							   </tr>
							   <#if isHq==true>
							   <tr>
							   		<th><span class="ui-color-red">*</span>开始时间：</th>
								    <td>
								    	<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="balanceStartDate" id="batchAddBalanceStartDate" defaultValue="startDate" data-options="{maxDate:'batchAddBalanceEndDate',required:true}"/>
								    </td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>结束时间：</th>
									<td>
										<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="balanceEndDate" id="batchAddBalanceEndDate" defaultValue="endDate" data-options="{minDate:'batchAddBalanceStartDate',required:true}"/>
									</td>
								</tr>
								</#if>
								<#if isHq==null || isHq==''>
							    <tr>
							   		<th><span class="ui-color-red">*</span>开始时间：</th>
								    <td>
								    	<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="balanceStartDate" id="batchAddBalanceStartDate" defaultValue="currentMonthFirstDayDate" data-options="{maxDate:'batchAddBalanceEndDate',required:true}"/>
								    </td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>结束时间：</th>
									<td>
										<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="balanceEndDate" id="batchAddBalanceEndDate" defaultValue="currentMonthLastDayDate" data-options="{minDate:'batchAddBalanceStartDate',required:true}"/>
									</td>
								</tr>
								</#if>
								<tr>
									<th>生成方式：</th>
									<td>
										<input name="brandUnitFlag" id="brandUnitFlag" type="radio" checked="checked" value="0"/> 品牌部合并
										<input name="brandUnitFlag" id="brandUnitFlag" type="radio" value="1" /> 品牌部分开
									</td>
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
</body>
</html>