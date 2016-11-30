<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MS-门店回款分析</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shopbalance_BackSectionSplit.js?version=${version}"></script>
<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body>
<#--tabs层-->
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />


<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.billToolBar type="shop_balance_BackSectionSplitBar"/> 
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
          
 	<div data-options="region:'center',border:false">
  		<@p.datagrid id="shopBalanceBackSplDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
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
					{field : 'balanceTypeName',title : '结算单类型',width : 80,align:'center'},		 				                       	
	                {field : 'billStatusName',title : '状态',width : 80,align:'center'},	
	                {field : 'invoiceApplyNo',title : '开票申请单号',width : 140,align:'center'},
					{field : 'mallNumberAmount',title : '商场报数',width : 90,align:'right',exportType:'number'},																
				    {field : 'systemSalesAmount',title : '系统收入',width : 90,align:'right',exportType:'number'},
				    {field : 'salesDiffamount',title : '报数差异',width : 80,align:'right',exportType:'number'}, 
				    {field : 'contractRateName',title : '合同扣率%',width : 80,align:'right'},
				    {field : 'conpriceDeductAmount',title : '合同正价扣费',width : 90,align:'right',exportType:'number'},
				    {field : 'noconpriceDeductAmount',title : '非正价扣费',width : 90,align:'right',exportType:'number'},
				    {field : 'promDeductAmount',title : '促销扣费',width : 80,align:'right',exportType:'number'},
				    {field : 'promPlusbuckleAmount',title : '促销加扣',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceDeductAmount',title : '票前费用',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceDiffAmount',title : '结算差异',width : 80,align:'right',exportType:'number'}, 
				   {field : 'mallDeductAmount',title : '扣费总额',width : 80,align:'right',exportType:'number'},
				   {field : 'actualRateName',title : '实际扣率%',width : 80,align:'right'},	
				   {field : 'invoiceAmount',title : '发票金额',width : 80,align:'right',exportType:'number'},
			       {field : 'isInvoiceDate',title : '应开票日期',width : 85,align:'center'},   
				   {field : 'invoiceApplyDate',title : '开票申请日期',width : 85,align:'center'},
				   {field : 'actualInvoiceDate',title : '实际开票日期',width : 85,align:'center'},
				   {field : 'invoiceIsOverdue',title : '发票超期天数',width : 85,align:'center'},
				   {field : 'invoiceDate',title : '发票应寄送日期',width : 85,align:'center'},
				   {field : 'invoiceActualDate',title : '实际寄送日期',width : 85,align:'center'},
				   {field : 'sendInvoiceOverdue',title : '发票寄送超期天数',width : 85,align:'center'},
				   {field : 'invoiceSentDate',title : '发票应送到日期',width : 85,align:'center'},
				   {field : 'actualinvDelivDate',title : '实际送到日期',width : 85,align:'center'},
				   {field : 'invoiceOverdue',title : '发票送达超期天数',width : 85,align:'center'},
				   {field : 'amountPayment',title : '应回款金额',width : 80,align:'right',exportType:'number'},
				   {field : 'theAmountPayment',title : '已回款金额',width : 80,align:'right',exportType:'number'},
				   {field : 'receivableBalance',title : '应收款余额',width : 80,align:'right',exportType:'number'},
				   {field : 'paymentAmount',title : '回款金额',width : 80,align:'right',exportType:'number'},
				   {field : 'paymentDate',title : '回款日期',width : 85,align:'center'},
				   {field : 'thePaymentDate',title : '应回款日期',width : 85,align:'center'},
				   {field : 'overduePaymentdue',title : '回款超期天数',width : 85,align:'center'},
				   {field : 'returnDifference',title : '回款差异',width : 80,align:'right',exportType:'number'},
				   {field : 'billAccount',title : '账扣回票',width : 80,align:'right',exportType:'number'},
				   {field : 'accountDedReturnDiff',title : '账扣回票差异',width : 80,align:'right',exportType:'number'},
				   {field : 'theCurrDiffAmountTotal',title : '本期差异金额合计',width : 80,align:'right',exportType:'number'},
			       {field : 'hasNotPaidBack',title : '累计未回款',width : 80,align:'right',exportType:'number'},
			       {field : 'cumulativeReturn',title : '累计未回票',width : 80,align:'right',exportType:'number'},	
			       {field : 'cumulativeTotalVariance',title : '累计差异金额合计',width : 80,align:'right',exportType:'number'},
			       {field : 'zoneName', title : '地区', width : 60,rowspan:'2'},
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