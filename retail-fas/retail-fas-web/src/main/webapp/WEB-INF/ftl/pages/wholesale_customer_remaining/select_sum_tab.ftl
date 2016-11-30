<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_customer_remaining/WholesaleCustomerRemainingSelect.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<input type="hidden" name="queryCondition" id="queryCondition" value="${queryCondition}">
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"wholesaleSelectController.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"wholesaleSelectController.clear()","type":0},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"wholesaleSelectController.doExport('mainDataGrid','/wholesale_customer_remaining_sum/select_sum_export?isHq=${isHq}','客户余额期间流水汇总导出')","type":0}			
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
							    	<input class="easyui-datebox readonly ipt" readonly="true" name="startDate" id="startDate" defaultValue="currentMonthFirstDayDate"  data-options="maxDate:'endDate'"/>
							    </td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox readonly ipt" readonly="true" name="endDate" id="endDate" defaultValue="currentMonthLastDayDate"  data-options="minDate:'startDate'"/>
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
				{field : 'id', hidden:true, title : '编号',width : 200, align:'left'},
				{field : 'companyName',title : '公司名称',width : 240, align:'left'},
				{field : 'companyNo',title : '公司编码',width : 100, align:'center'},
				{field : 'customerName',title : '客户名称',width : 240, align:'left'},
				{field : 'fullName',title : '客户全称',width : 240, align:'left'},
				{field : 'brandUnitNames',title : '品牌部',width : 240, align:'left'},
				{field : 'customerNo',title : '客户编码',width : 100, align:'center'},
				{field : 'periodFirstAmount',title : '期初可用余额',width : 100, align:'right'},
				{field : 'periodFirstFrozen',title : '期初冻结余额',width : 100, align:'right'},
				{field : 'periodFirstBookAmount',title : '期初账面余额',width : 100, align:'right'},
				{field : 'periodInAmount',title : '本期收款',width : 100, align:'right'},	
				{field : 'periodOutAmount',title : '本期出库',width : 100, align:'right'},
				{field : 'periodRebateAmount',title : '本期返利金额',width : 150, align:'right'},
				{field : 'periodOtherPrice',title : '本期其他费用',width : 100, align:'right'},
				{field : 'periodDeduction',title : '本期扣项',width : 100, align:'right'},
				{field : 'periodFrozenAmount',title : '本期冻结金额',width : 100, align:'right'},
				{field : 'periodUnfrozenAmount',title : '本期释放冻结金额',width : 150, align:'right'},
				{field : 'periodCredit',title : '本期使用信贷次数',width : 150, align:'right'},
				{field : 'periodLastAmount',title : '期末余额',width : 100, align:'right'},
				{field : 'periodLastFrozen',title : '期末冻结余额',width : 100, align:'right'},
				{field : 'periodLastBookAmount',title : '期末账面余额',width : 100, align:'right'},
				{field : 'periodLastMarginAmount',title : '期末保证金余额',width : 150, align:'right'},
				{field : 'periodLastCredit',title : '期末可用信贷次数',width : 150, align:'right'}
				]"
			/>
	</div>
</div>

	
