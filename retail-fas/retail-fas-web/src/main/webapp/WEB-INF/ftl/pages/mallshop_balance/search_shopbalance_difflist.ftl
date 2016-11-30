<!DOCTYPE html>
<head>
    <title>结算差异查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shopBalanceDiffSearch.js?version=${version}"></script>
    
    <link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
    <script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
    <script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	    <@p.billToolBar type="shop_balance_balance_diff_listBar"/>
			
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
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
	       		 	   <th>公司：</th>
						  	<td align="left" width="140">
							  	<input class="ipt easyui-company" data-options="multiple:true,inputNoField:'companyNos_',inputNameField:'companyName_'" name="companyNames" id="companyName_"/>
								<input type="hidden" name="companyNos" id="companyNos_"/>
								<!--<input type="hidden" name="noDiffAmount" id="noDiffAmount"  value="noDiffAmount"/>-->	
							</td>					
						</td>	
					    <th>店铺： </th>
							<td>
								<input class="easyui-shopCommon" id="shopName" />
							    <input type="hidden" name="shopNos" id="shopNo" />
						    </td>	    
					    <th>结算月：</th>
					    <td align="left" width="145">
					      <input class="easyui-datebox" name="startMonth" id="startMonth" style="width:65px;"  data-options="dateFmt:'yyyyMM'" />
				          <input class="easyui-datebox" name="endMonth" id="endMonth" style="width:65px;"  data-options="dateFmt:'yyyyMM'" />
				        </td>    							
						<th>品牌部：</th>
						<td>
					     	<input class="ipt easyui-brandunit" name="brandNames" id="brandNames_" 
					    		data-options="multiple: true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
					      	<input type="hidden" name="brandNos" id="brandNos_"/>
						</td>	
						</tr>		
						<tr>
						 <th>管理城市：</th>
						<td>
							<input class="ipt easyui-organ" name="organNames" id="organNames_"
							data-options="multiple: true, inputNoField:'organNos_', inputNameField:'organNames_'"/>
							<input type="hidden" name="organNos" id="organNos_"/>
						</td>	
						<th>状态：</th>
							<td>									    
							<input class="easyui-combobox  easyui-validate  ipt" name="status" id="statusMain_" style="width:80px;"  
							data-options="valueField: 'label',textField: 'value',required:false,
							data: [{label: '0',value: '未完成'},{label: '1',value: '已完成'}]" />
					   </td>
					   <th>扣费差异：</th>
							<td>									    
							<input class="easyui-combobox  easyui-validate  ipt" name="noDiffAmount" id="noDiffAmount" style="width:80px;"  
							data-options="valueField: 'label',textField: 'value',required:false,
							data: [{label: '0',value: '不等于0'},{label: '1',value: '等于0'},{label: '2',value: '全部'}]" />
					   </td> 
					   <th align="right" width="110" > 结算单号：</th>
							<td><input class="ipt" name="balanceNo" id="balanceNoMain"/></td>
					</tr>						
	       		 	</table>
				</form>
		</div>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="searchBillDiffdataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                {field : 't', checkbox:true, hidden : 'true',width : 30, notexport:true},
			                    {field : 'diffTypeCode',hidden:true, title : '差异编码', width : 130,notexport:true},
				                {field : 'companyName',title : '公司',width : 200,align:'left',halign:'center'},	
				                {field : 'shopNo',title : '店铺编码',width : 60,align:'left'},				              
								{field : 'shortName',title : '店铺',width : 180,align:'left',halign:'center'},
								{field : 'brandName',title : '品牌',width : 80,align:'left'},
								{field : 'month',title : '结算月',width : 80,align:'center',halign:'center'},	
								{field : 'balanceNo',title : '结算单编号',width : 160,align:'left'},	
				                {field : 'diffTypeName', title : '差异类型', width : 100,align:'left',halign:'center'}, 
								{field : 'diffDate',title : '差异结算日',width : 100,align:'center',halign:'center'},	
								{field : 'proNo',title : '活动编码',width : 150,align:'left',halign:'center'},
								{field : 'proName',title : '活动名称',width : 150,align:'left',halign:'center'},
								{field : 'proStartDate',title : '活动起始日',width : 100,align:'center',halign:'center'},	
								{field : 'proEndDate',title : '活动终止日',width : 100,align:'center',halign:'center'},	
								{field : 'discountCode',title : '扣率代码',width : 80,align:'center',halign:'center'},
								{field : 'discount',title : '扣率%',width : 80,align:'center',halign:'center',exportType:'number'},
								{field : 'deductDiffAmount',title : '系统扣费',width : 80,align:'right',halign:'center',exportType:'number'},
								{field : 'reportDiffAmount',title : '报数差异',width :80,align:'right',halign:'center',exportType:'number'},
								{field : 'balanceDiffAmount',title : '结算差异',width : 80,align:'right',halign:'center',exportType:'number'}, 
								{field : 'mallNumber',title : '商场报数(A)',width : 100,align:'right',halign:'center',exportType:'number'}, 	
								{field : 'salesAmount',title : '系统收入(B)',width : 80,align:'right',halign:'center',exportType:'number'},	
								{field : 'salesDiffamount',title : '差异金额(A-B)',width : 100,align:'right',halign:'center',exportType:'number'},	
								{field : 'diffAmount',title : '扣费差异',width : 80,align:'right',halign:'center',exportType:'number'},	                  
								{field : 'diffReason',title : '差异原因',width : 100,align:'left',halign:'center'},
								{field : 'preDiffBalance',title : '上期差异余额',width : 100,align:'right',halign:'center',exportType:'number'},
								{field : 'diffBalance',title : '本期差异余额',width : 100,align:'right',halign:'center',exportType:'number'},	
								{field : 'diffBackAmount',title : '差异回款',width : 80,align:'right',halign:'center',exportType:'number'},
								{field : 'reason',title : '原因',width :100,align:'left',halign:'center'},	
								{field : 'changeAmount',title : '调整金额',width : 70,align:'right',	halign:'center',exportType:'number'},	
								{field : 'changeReason',title : '调整原因',width : 150,align:'left',	halign:'center'},
								{field : 'changeDate',title : '调整日期',width : 80,align:'left',halign:'center'},
				                {field : 'status',title : '状态 ',width : 50,align:'center',halign:'center',formatter:dialog.balanceFlagformatter},	
				                {field : 'statusName',title : '状态',hidden:true,width : 90,align:'center',notexport:true},
				                {field : 'remark',title : '备注',width : 150,align:'left',halign:'center'}, 
				                {field : 'generateType',title : '生成方式',width : 100,align:'center',notexport:true,		
								formatter: dialog.generateType, 						
								editor:{
									type:'combobox',
									options:{
									valueField: 'value',  
									textField: 'text',
									required:false,
									data: [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}]
									}
								}
							   }, 
			                    {field : 'createUser',title : '建档人',width : 60,align:'left'}, 
				                {field : 'createTime',title : '建档时间',width : 130,align:'center'},
				                {field : 'updateUser',title : '修改人',width : 60,align:'left'}, 
				                {field : 'updateTime',title : '修改时间',width : 130,align:'center'}	 
				             ]" 
				          jsonExtend='{
		                           onDblClickRow:function(rowIndex, rowData){ }
			         	}'/>
			</div>
		 </div>
	</div>
</body>
</html>