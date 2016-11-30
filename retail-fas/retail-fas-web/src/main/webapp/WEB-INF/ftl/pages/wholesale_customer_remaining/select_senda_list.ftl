<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_customer_remaining/SendaRemainingSelect.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<input type="hidden" name="queryCondition" id="queryCondition" value="${queryCondition}">
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"wholesaleSelectController.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"wholesaleSelectController.clear()","type":0},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"wholesaleSelectController.doExport('mainDataGrid','/wholesale_customer_remaining_sum/select_senda_export?isHq=${isHq}','森达客户余额明细导出')","type":0}			
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	            <table class="form-tb" >
				    <col width="100px" />
				    <col  />
				    <col width="100px" />
				    <col />
				  	<col width="100px" />
				    <col />
				    <col width="100px"/>
				    <col />
				    <tbody>
						<tr>
							<th>公司：</th>
                            <td>
                              <#if isHq==true>
						      	<input class="easyui-company ipt"  name="companyName" id="companyNameCondition" 
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
                           		<input type="hidden" name="companyNo" id="companyNoCondition"/>
						      </#if>
						      <#if isHq==null || isHq==''>
						      	<input class="easyui-company ipt"  name="companyName" id="companyNameCondition" 
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
                           		<input type="hidden" name="companyNo" id="companyNoCondition"/>
						      </#if>
                        	</td>
                            <th>客户：</th>
                            <td>
                            	<input class="easyui-wholesale_zone_customer ipt" id="multiCustomerName" data-options="multiple:'true',inputWidth:160,inputNoField:'multiCustomerNo',inputNameField:'multiCustomerName'"/> 
                            	<input type="hidden" name="multiCustomerNo" id="multiCustomerNo"/>
							</td>
							<th>日期：</th>
				    		<#if isHq==true>
							    <td>
							    	<input class="easyui-datebox readonly ipt" readonly="true" name="startDate" id="startDate" defaultValue="startDate" data-options="maxDate:'endDate'"/>
							    </td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox readonly ipt" readonly="true" name="endDate" id="endDate" defaultValue="endDate" data-options="minDate:'startDate'"/>
								</td>
							</#if>
							<#if isHq==null || isHq==''>
								<td>
							    	<input class="easyui-datebox readonly ipt" readonly="true" name="startDate" id="startDate" defaultValue="startDate"  data-options="maxDate:'endDate'"/>
							    </td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox readonly ipt" readonly="true" name="endDate" id="endDate" defaultValue="endDate"  data-options="minDate:'startDate'"/>
								</td>
							</#if>
                        </tr>
				    </tbody>
				</table>   
	        </form>
        </div>
     </div> 
	<#-- end -->
	
	<div data-options="region:'center',border:false">
		<@p.datagrid id="mainDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			rownumbers="true" showFooter="true" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			columnsJsonList="[
				{field : 'companyNo',title : '公司编号',width : 100, align:'left'},
				{field : 'companyName',title : '公司名称',width : 200, align:'left'},
				{field : 'customerNo',title : '客户编号',width : 100, align:'left'},
				{field : 'customerName',title : '客户名称',width : 150, align:'left'},
				{field : 'billDate',title : '日期',width : 100, align:'left'},
				{field : 'bizType',hidden: true, title : '业务类型',width : 100, align:'left'},
				{field : 'bizTypeName',title : '业务类型',width : 100, align:'left'},
				{field : 'billNo',title : '单据编码',width : 150, align:'left'},
				{field : 'firstRemaining',title : '期初客户余额',width : 100, align:'left'},
				{field : 'firstUninvoiced',title : '期初未开票金额',width : 100, align:'left'},
				{field : 'sendQty',title : '批发出库数量',width : 100, align:'left'},
				{field : 'sendAmount',title : '批发出库金额',width : 100, align:'left'},
				{field : 'returnAmount',title : '批发退货金额',width : 100, align:'left'},
				{field : 'deductionAmount',title : '扣项金额',width : 100, align:'left'},
				{field : 'receiveAmount',title : '收款金额',width : 100, align:'left'},
				{field : 'remainingAmount',title : '客户余额',width : 100, align:'left'},
				{field : 'invoicedAmount',title : '开票金额',width : 100, align:'left'},
				{field : 'totalUninvoiced',title : '累计未开票金额',width : 100, align:'left'}
				]"
			/>
	</div>
</div>

	
