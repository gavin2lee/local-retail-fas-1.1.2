<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/ShopBalance.2.0.js?version=${version}"></script>
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.billToolBar type="shop_balance_listMainBar"/>
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
									<!--
									<input class="ipt easyui-shop" data-options="multiple:true,inputNoField:'shopNo_main',inputNameField:'shortName_main'"  name="shortNames" id="shortName_main"/>
									<input type="hidden" name="shopNos" id="shopNo_main"/>
								   -->
                                    <input id="shortName_main" />
							        <input type="hidden" name="shopNos" id="shopNo_main" />
								</td>
							
								   <th>品牌部：</th>
									<td>
						        		<input class="ipt easyui-brandunit" name="brandNames" id="brandNames_" 
						        			data-options="multiple: true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
									  	<input type="hidden" name="brandNos" id="brandNos_"/>
									</td>
									<!--
									    <th>品牌部： </th>
										 <td>
											<input class="easyui-brand ipt" name="brandNames" id="brandNames_" data-options="multiple:true,inputWidth:130"/>
											<input type="hidden" name="brandNos" id="brandNos_"/>
										</td>	
									-->
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
								data: [{label: '1',value: '制单'},{label: '5',value: '业务确认'},{label: '2',value: '财务确认'},{label: '4',value: '已开票申请'}]" />
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
  		<@p.datagrid id="shopBalanceDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
          		isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	           	checkOnSelect="true"
	           	rownumbers="true" singleSelect="false"  
	            frozenColumns="[
	                {field:'id',checkbox:true,notexport:true}, 	
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
					{field : 'isEqureTrueName',hidden:true,title : '是否已平',width : 80,align:'center',notexport:true},
					{field : 'balanceStartDate',title : '开始日期',width : 80,align:'left'},
					{field : 'balanceEndDate',title : '结束日期',width : 80,align:'left'},
					{field : 'balanceTypeName',title : '结算单类型',width : 80,align:'center'},		 				                       	
	                {field : 'billStatusName',title : '状态',width : 80,align:'center'},	
	                {field : 'invoiceApplyNo',title : '开票申请单号',width : 140,align:'center', formatter: function(value,row,index){
						return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AI-开票申请');
					}},
					{field : 'mallNumberAmount',title : '商场报数',width : 90,align:'right',exportType:'number'},																
				    {field : 'systemSalesAmount',title : '系统收入',width : 90,align:'right',exportType:'number'},
				    {field : 'salesDiffamount',title : '报数差异',width : 80,align:'right',exportType:'number'}, 
				    {field : 'contractRateName',title : '合同扣率%',width : 80,align:'right'},
				    {field : 'conpriceDeductAmount',title : '合同正价扣费',width : 90,align:'right',exportType:'number'},
				    {field : 'promDeductAmount',title : '促销扣费',width : 80,align:'right',exportType:'number'},
				    {field : 'promPlusbuckleAmount',title : '促销加扣',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceDeductAmount',title : '票前费用',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceDiffAmount',title : '结算差异',width : 80,align:'right',exportType:'number'}, 
				    {field : 'mallDeductAmount',title : '扣费总额',width : 80,align:'right',exportType:'number'},
				    {field : 'actualRateName',title : '实际扣率%',width : 80,align:'right'},	
				    {field : 'mallBillingAmount',title : '商场开票金额',width : 90,align:'right',exportType:'number'},
				    {field : 'systemBillingAmount',title : '系统开票金额',width : 90,align:'right',exportType:'number'},							   
				    {field : 'paymentAmount',title : '回款金额',width : 80,align:'right',exportType:'number'},
				    {field : 'differenceAmount',title : '收款差异',width : 80,align:'right',exportType:'number'},	
				    {field : 'returnedAmount',title : '应返款金额',width : 80,align:'right',exportType:'number'},
				    {field : 'estimateAmount',title : '预估差异',width : 80,align:'right',exportType:'number'},
				    {field : 'balanceNo',title : '结算单编号',width : 160,align:'left'},	
	                {field : 'billDate',title : '单据日期',width : 80,align:'left',editor:'datebox'},
				    {field : 'invoiceApplyDate',title : '开票申请日期',width : 85,align:'center'},
				    {field : 'invoiceNo',title : '发票号',width : 120,align:'center'},
				    {field : 'companyNo',hidden:true,width : 50,align:'left',notexport:true},
				    {field : 'companyName',title : '公司',width : 180,align:'left'},					   	    
				    {field : 'createUser',title : '建档人',width : 60,align:'left'}, 
	                {field : 'createTime',title : '建档时间',width : 130,align:'center'},
	                {field : 'updateUser',title : '修改人',width : 60,align:'left'}, 
	                {field : 'updateTime',title : '修改时间',width : 130,align:'center'}					               
             	]" 
	          	jsonExtend='{
                   onDblClickRow:function(rowIndex, rowData){
                   	 bill.loadDetail(rowIndex,rowData);
                   }
     		}'/>
   	</div>
   	
   	
   	<div id="printHBContrPanel" class="easyui-dialog" title="My Dialog" style="width:200px;height:200px;"   
	    data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	     <div align='center'>
	     	<form id="printHBForm">
		     		<select multiple id="printHBSelect" align="left" style="height:200px;width:100%">
					</select>
	     	</form>
	     </div>
	</div> 
	
<div id="printContrPanel" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	     <div align='center'>
	     	<form id="printForm">
		     	<div style="width:49%; float:left;">
		     		<select multiple id="printSelect" ondblclick="removeTo();" align="left" style="height:200px;width:100%">
					</select>
					</div><div style="width:50%; float:left;">
					<select multiple id="printSelect1" ondblclick="removeFrom();" align="right" style="height:200px;width:100%">
					</select>
				</div>
	     	</form>
	     </div>
</div>  
</div>