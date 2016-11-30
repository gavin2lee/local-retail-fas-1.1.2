<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MS-结算单跟踪表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shopbalance_Result.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
    <script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
    <script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body>
<#--tabs层-->
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />


<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.billToolBar type="shop_balance_ResultListBar"/>
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
				    	    <th align="right" width="110" >管理城市 ：</th>
							<td>
								<input class="ipt easyui-organ" data-options="multiple:true,inputNoField:'organNo_main',inputNameField:'organName_main'"  name="organNames" id="organName_main"/>
								<input type="hidden" name="organNos" id="organNo_main"/>
								</td>
								<th>店铺类别：</th>
								 <td>									    
								<input class="easyui-combobox  easyui-validate  ipt" name="retailType" id="retailTypeMain_" style="width:100px;"  
								data-options="multiple:false,valueField: 'label',textField: 'value',required:false,
								data: [{label: '0101',value: '商场店'},{label: '0102',value: '专卖店'},{label: '0103',value: '零售员购店'},
								{label: '0104',value: '零售团购店'},{label: '0105',value: '零售网购店'},{label: '0201',value: '批发店'},
								{label: '0301',value: '调货'},{label: '0302',value: '退货'},{label: '0303',value: '索赔'},{label: '0304',value: '其它'}]" />
							   </td>
							    <th align="right" width="110">店铺：</th>
								<td align="left" width="140">
									<input class="ipt easyui-shop" data-options="multiple:true,inputNoField:'shopNo_main',inputNameField:'shortName_main'"  name="shortNames" id="shortName_main"/>
									<input type="hidden" name="shopNos" id="shopNo_main"/>
								</td>
							   <th>品牌部：</th>
								<td>
					        		<input class="ipt easyui-brandunit" name="brandNames" id="brandNames_" 
					        			data-options="multiple: true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
								  	<input type="hidden" name="brandNos" id="brandNos_"/>
								</td>
							</tr>		
       		 		  	    <tr>
       		 		  	     <th align="right">结算月：</th>
				          	<td align="left" width="145"><input class="easyui-datebox" name="startMonth" id="startMonth" style="width:65px;"  data-options="multiple:true,dateFmt:'yyyyMM'" />
				          	<input class="easyui-datebox" name="endMonth" id="endMonth" style="width:65px;"  data-options="multiple:true,dateFmt:'yyyyMM'" /></td>
				          	<th align="right" width="110" >制单人：</th>
							<td align="left" width="140"><input class="ipt" name="createUser" id="createUser"  style="width:70px;"   data-options="required:true" /></td>
							<th>状态：</th>
								 <td>									    
								<input class="easyui-combobox  easyui-validate  ipt" name="status" id="statusMain_" style="width:80px;"  
								data-options="valueField: 'label',textField: 'value',required:false,
								data: [{label: '1',value: '制单'},{label: '2',value: '确认'},{label: '4',value: '已开票申请'}]" />
							   </td>
       		 		  		<th align="right" width="110" > 结算单号：</th>
							<td><input class="ipt" name="balanceNo" id="balanceNoMain"/></td>
						  	<th align="right" width="110">公司：</th>
						  	<td align="left" width="140">
							  	<input class="ipt easyui-company" data-options="multiple:true,inputNoField:'companyNos_',inputNameField:'companyName_'" name="companyNames" id="companyName_"/>
								<input type="hidden" name="companyNos" id="companyNos_"/>
							</td>
       		 		  	</tr>		
				    </tbody>
				</table>   
	        </form>
	   	</div>
    </div>
			<!--
			            序号  代号  名称	起始日	终止日	结算单单据	结算状态	商场报数	系统销售	销售数量	报数差异	合同扣率	实际扣率	公司开票额	账前费用	发票号码	开票日期	
			           应开发票	账后费用	应收费用合计	商场对帐单费用合计	费用差异	费用差异是否收回	应收扣费合计	商场对帐单扣费合计	扣费差异	扣费差异是否收回	
			           应收金额	应付款日	实际付款日	已开票额	未开票额	回款金额	未回款额	备注
	           -->    
 	<div data-options="region:'center',border:false">
  		<@p.datagrid id="shopBalanceResultDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
          		isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	           	checkOnSelect="true"
	           	rownumbers="true" singleSelect="false"  
	            frozenColumns="[
	                {field : 'organName',title : '管理城市',width : 60,align:'left'},	
	                {field : 'retailType',title : '店铺类别',width : 60,align:'left'},	               
	                {field : 'shopNo',title : '店铺编码',width : 60,align:'left'},			              
					{field : 'shortName',title : '店铺名称',width : 150,align:'left'},	
					 ]"
			        columnsJsonList="[
					{field : 'mallNo',hidden:true,width : 50,align:'left',notexport:true},					   							
					{field : 'mallName',title : '商场',width : 90,align:'left'},
					{field : 'brandName',title : '品牌',width : 80,align:'left'},																														
					{field : 'month',title : '结算月',width : 50,align:'left'},			           
					{field : 'balanceStartDate',title : '开始日期',width : 80,align:'left'},
					{field : 'balanceEndDate',title : '结束日期',width : 80,align:'left'},
					{field : 'balanceNo',title : '结算单编号',width : 160,align:'left'},	
					{field : 'balanceTypeName',title : '结算单类型',width : 80,align:'center'},		 				                       	
	                {field : 'billStatusName',title : '状态',width : 80,align:'center'},	
	                {field : 'invoiceApplyNo',title : '开票申请单号',width : 140,align:'center'},
					{field : 'mallNumberAmount',title : '商场报数',width : 90,align:'right',exportType:'number'},																
				    {field : 'systemSalesAmount',title : '系统收入',width : 90,align:'right',exportType:'number'},
				     {field : 'salesNum',title : '销售数量',width : 90,align:'right',exportType:'number'},
				    {field : 'salesDiffamount',title : '报数差异',width : 80,align:'right',exportType:'number'}, 
				    {field : 'contractRateName',title : '合同扣率%',width : 80,align:'right'},
				    {field : 'conpriceDeductAmount',title : '合同正价扣费',width : 90,align:'right',exportType:'number'},
				    {field : 'promDeductAmount',title : '促销扣费',width : 80,align:'right',exportType:'number'},
				    {field : 'promPlusbuckleAmount',title : '促销加扣',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceDeductAmount',title : '票前费用',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceDiffAmount',title : '结算差异',width : 80,align:'right',exportType:'number'}, 
				    {field : 'preAccountChar',title : '账前费用',width : 90,align:'right',exportType:'number'},
				    {field : 'mallDeductAmount',title : '扣费总额',width : 80,align:'right',exportType:'number'},
				    {field : 'actualRateName',title : '实际扣率%',width : 80,align:'right'},	
				    {field : 'mallBillingAmount',title : '商场开票金额',width : 90,align:'right',exportType:'number'},
				    {field : 'systemBillingAmount',title : '系统开票金额',width : 90,align:'right',exportType:'number'},							   
				    {field : 'differenceAmount',title : '收款差异',width : 80,align:'right',exportType:'number'},	
				    {field : 'returnedAmount',title : '应返款金额',width : 80,align:'right',exportType:'number'},
				    {field : 'estimateAmount',title : '预估差异',width : 80,align:'right',exportType:'number'},
				    {field : 'invoiceOpened',title : '应开发票',width : 80,align:'right',exportType:'number'},
				    {field : 'postAccountExpAmount',title : '账后费用',width : 80,align:'right',exportType:'number'},
				   	{field : 'costReceivTotal',title : ' 应收费用合计',width : 80,align:'right',exportType:'number'},
				    {field : 'mallCostTotal',title : '商场对帐单费用合计',width : 80,align:'right',exportType:'number'},
				    {field : 'costDiffAmount',title : '费用差异',width : 80,align:'right',exportType:'number'},
				    {field : 'costDifferenceTag',hidden:true,title : '费用差异是否收回',width : 80,align:'center'},
				   	{field : 'balanceDeduTotalAmount',title : '应收扣费合计',width : 80,align:'right',exportType:'number'},
				    {field : 'mallDeductTotal',title : '商场对帐单扣费合计',width : 80,align:'right',exportType:'number'},
				    {field : 'deductionDiffAmount',title : '扣费差异',width : 80,align:'right',exportType:'number'},
				    {field : 'deductionDiffTag',hidden:true,title : '扣费差异是否收回',width : 80,align:'center'},
			        {field : 'balanceAmount',title : ' 应收金额',width : 80,align:'right',exportType:'number'},
			        {field : 'accountsPayableDay',title : '应付款日',width : 80,align:'left'},
			        {field : 'actualPaymentDate',title : '实际付款日',width : 80,align:'left'},
			        {field : 'differenceAmount',title : '已开票额',width : 80,align:'right',exportType:'number'},
			        {field : 'differenceAmount',title : '未开票额',width : 80,align:'right',exportType:'number'},
			        {field : 'paymentAmount',title : '回款金额',width : 80,align:'right',exportType:'number'},
			        {field : 'noPaymentAmount',title : '未回款额',width : 80,align:'right',exportType:'number'},
			        {field : 'remark',title : '备注',width : 180,align:'left'},
	                {field : 'billDate',title : '单据日期',width : 80,align:'left',editor:'datebox'},
				    {field : 'invoiceApplyDate',title : '开票申请日期',width : 85,align:'center'},
				    {field : 'invoiceNo',title : '发票号',width : 120,align:'center'},
				    {field : 'companyNo',hidden:true,width : 50,align:'left',notexport:true},
				    {field : 'companyName',title : '公司',width : 180,align:'left'}				               
             	]" 
	          	jsonExtend='{
                   onDblClickRow:function(rowIndex, rowData){
                   	 bill.loadDetail(rowIndex,rowData);
                   }
     		}'/>
   	</div>
</div>
</body>
</html>