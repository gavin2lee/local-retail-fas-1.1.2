<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
<input type="hidden" id="isHq" name="isHq" value="${isHq}" />
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false">
		<@p.toolbar id="toolbar2" listData=[
		 	{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"bill-btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
			{"id":"bill-btn-clear","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
			{"id":"bill-btn-del","title":"删除","iconCls":"icon-del","action":"bill.batchDelBill()","type":3},	
			{"id":"bill-btn-busconfirm1","title":"业务确认","iconCls":"icon-aduit","action":"bill.confirm('1', '3')","type":74},
			{"id":"bill-btn-financeconfim1","title":"财务确认","iconCls":"icon-aduit","action":"bill.confirm('1', '4')","type":79},
			{"id":"mian_btn_build_biing","title":"生成开票申请","iconCls":"icon-build-some","type":78,"action":"invoiceApplyBalance.invoiceApply('balanceZoneDataGrid','7')"},	
			{"id":"bill-btn-batch-add","title":"批量生成","iconCls":"icon-build-some","action":"bill.toBatchAddBill()","type":55},
			{"id":"bill-btn-invalidq","title":"打回","iconCls":"icon-cancel","action":"bill.confirm('1', '99')","type":73},
			{"id":"bill-btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}	
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	            <table class="form-tb" >
				    <col width="100" />
				    <col  />
				    <col width="100" />
				    <col />
				    <col width="100" />
				    <col />
				    <col width="100"/>
				    <col />
				    <tbody>
				    	<tr>
				    		<th>单据编号：</th>
						    <td><input class="ipt" name="billNo" /></td>
							<th>公司名称：</th>
                            <td>
                            <#if isHq==true>
						      	<input class="easyui-company ipt"  name="salerNameCondition" id="salerNameCondition" 
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'salerNoCondition',inputNameField:'salerNameCondition',inputWidth:160"/>
						      </#if>
						      <#if isHq==null || isHq==''>
						      	<input class="easyui-company ipt"  name="salerNameCondition" id="companyNameCondition" 
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNoCondition',inputNameField:'salerNameCondition',inputWidth:160"/>
						      </#if>
                            	<!--
                            	<input class="easyui-company ipt"  name="salerNameCondition" id="salerNameCondition" 
                            			data-options="inputNoField:'salerNoCondition',inputNameField:'salerNameCondition'"/>
                            	-->
                            </td>
                            <th>客户名称：</th>
                            <td>
                            	<input class="easyui-wholesale_zone_customer ipt" id="multiBuyerName" data-options="multiple:'true',inputNoField:'multiBuyerNo',inputNameField:'multiBuyerName'"/>
                            	<input type="hidden" name="multiBuyerNo" id="multiBuyerNo"/>
                            </td>
                            <th>管理城市： </th>
                            <td>
							<!--<input class="ipt" multiSearch="organ" name="organNameFrom" id="organNameFrom" /> <input type="hidden" name="organNoFrom" id="organNoFrom"/>-->
							<input class="easyui-organ ipt" name="organNameFromCondition" id="organNameFromCondition" data-options="multiple:'true',inputNoField:'organNoFromCondition',inputNameField:'organNameFromCondition'"/> 
							<input type="hidden" name="organNoFromCondition" id="organNoFromCondition"/>
							</td>
						</tr>
						<tr>
							<th>单据状态：</th>
                            <td>
                            	<input class="easyui-statusbox ipt"  name="status" id="statusCondition" 
                            		data-options="data:[{'value':'0','text':'制单'},{'value':'3','text':'业务确认'},{'value':'4','text':'财务确认'},{'value':'6','text':'已开票申请'},{'value':'99','text':'打回'}]"/>
                            </td>
				    		<th>结算期：</th>
				    		<#if isHq==true>
							    <td>
							    	<input class="easyui-datebox readonly ipt" readonly="true" name="balanceStartDate" id="balanceStartDateCondition" defaultValue="startDate" data-options="maxDate:'balanceEndDateCondition'"/>
							    </td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox readonly ipt" readonly="true" name="balanceEndDate" id="balanceEndDateCondition" defaultValue="endDate" data-options="minDate:'balanceStartDateCondition'"/>
								</td>
							</#if>
							<#if isHq==null || isHq==''>
								<td>
							    	<input class="easyui-datebox readonly ipt" readonly="true" name="balanceStartDate" id="balanceStartDateCondition" defaultValue="currentMonthFirstDayDate"  data-options="maxDate:'balanceEndDateCondition'"/>
							    </td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox readonly ipt" readonly="true" name="balanceEndDate" id="balanceEndDateCondition" defaultValue="currentMonthLastDayDate"  data-options="minDate:'balanceStartDateCondition'"/>
								</td>
							</#if>
						</tr>
				    </tbody>
				</table>   
	        </form>
        </div>
     </div> 
	<#-- end -->
	
	<#if isHq==true>
		<#include  "/WEB-INF/ftl/pages/bill_balance_zone/balance_zone_tab_hq.ftl" >
	</#if>
	<#if isHq==null || isHq==''>
		<#include  "/WEB-INF/ftl/pages/bill_balance_zone/balance_zone_tab_area.ftl" >
	</#if>
	
</div>
