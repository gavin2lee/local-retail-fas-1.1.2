<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"wholesaleController.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"wholesaleController.clear()","type":0},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"wholesaleController.doExport('mainDataGrid','/wholesale_customer_remaining_sum/export?isHq=${isHq}','客户余额列表导出')","type":0}			
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
                            	<!-- <input class="easyui-company ipt" name="companyName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
                            	<input type="hidden" name="companyNo" id="companyNoCondition"/>
                            	-->
                        	</td>
							</td>
                            <th>客户：</th>
                            <td>
                            	<input class="easyui-wholesale_zone_customer ipt" id="multiCustomerName" data-options="multiple:'true',inputWidth:160,inputNoField:'multiCustomerNo',inputNameField:'multiCustomerName'"/> 
                            	<input type="hidden" name="multiCustomerNo" id="multiCustomerNo"/>
							</td>
							<th>截止日期：</th>
						    <td>
						    	<input class="easyui-datebox readonly ipt" readonly="true" name="billDate" id="billDate"/>
						    </td>
							<th>欠款：</th>
							<td>
                            	<input type="checkbox" name="isArrears" id="isArrears"/> 
							</td>
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
				{field : 'companyName',title : '公司名称',width : 300, align:'left'},
				{field : 'companyNo',title : '公司编码',width : 100, align:'center'},
				{field : 'customerName',title : '客户名称',width : 250, align:'left'},
				{field : 'customerNo',title : '客户编码',width : 100, align:'center'},
				{field : 'remainingAmount',title : '客户可用余额',width : 150, align:'right'},
				{field : 'frozenCustomerAmount',title : '客户冻结余额',width : 100, align:'right'},	
				{field : 'bookRemainingAmount',title : '客户账面余额',width : 100, align:'right'},	
				{field : 'marginAmount',title : '合同保证金',width : 100, align:'right'},	
				{field : 'marginRemainderAmount',title : '保证金余额',width : 100, align:'right'},
				
				{field : 'rebateAmount',title : '返利余额',width : 150, align:'right'},
				{field : 'creditAmount',title : '信贷额度',width : 150, align:'right'},
				{field : 'creditLimit',title : '信贷限定次数',width : 100, align:'right'},	
				{field : 'creditPerYear',title : '可用信贷次数(年)',width : 150, align:'right'}
				]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				wholesaleController.loadDetail(rowIndex,rowData);
		}}'/>
	</div>
</div>
		

