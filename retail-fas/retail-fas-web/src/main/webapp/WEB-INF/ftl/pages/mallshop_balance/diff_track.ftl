<!DOCTYPE html>
<head>
    <title>结算差异跟踪</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/DiffTrack.js?version=${version}"></script>
    
     <link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
     <script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
     <script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
     
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
             	{"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
             	{"id":"btn-export","title":"导出列表","iconCls":"icon-export","action":"dialog.exportExcel()","type":0},
             	{"id":"btn-export","title":"导出明细","iconCls":"icon-export","action":"exportExcelDetails()","type":0}
           ]/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
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
								<!--   <input type="hidden" name="noDiffAmount" id="noDiffAmount"  value="noDiffAmount"/>   -->
							</td>					
						</td>									
					     <th>店铺： </th>
							<td>
								<input class="easyui-shopCommon" id="shopName" />
							    <input type="hidden" name="shopNos" id="shopNo" />
						    </td>
					    <th>结算月：</th>
					       <td><!--<input class="easyui-datebox ipt" name="queryMonthCondition" id="queryMonthCondition" style="width:90px;" data-options="dateFmt:'yyyyMM'"/>-->  
					        <input class="easyui-datebox" name="startMonth" id="startMonth" style="width:65px;"  data-options="dateFmt:'yyyyMM'" />
				            <input class="easyui-datebox" name="endMonth" id="endMonth" style="width:65px;"  data-options="dateFmt:'yyyyMM'" /></td>							
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
					    </tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="false" 
			               checkOnSelect="false"
				           rownumbers="true" singleSelect="true" 
				           columnsJsonList="[
				                  {field : 'shopNo',title : '店铺编码',width : 80,align:'left'},
				                  {field : 'shortName',title : '店铺名称',width : 100,align:'left'},
				                  {field : 'month',title : '结算月',width : 80,align:'center'},
				                  {field : 'balanceNo',title : '结算单编号',width : 160,align:'left'},	
				                  {field : 'brandName',title : '品牌',width : 80,align:'left'},
				                  {field : 'diffTypeCode',title : '差异编码',width : 80,align:'left',notexport:true},
				                  {field : 'diffTypeName',title : '差异类型',width : 80,align:'left'}, 
				                  {field : 'diffDate',title : '差异结算日',width : 100,align:'center'},
				                  {field : 'proNo',title : '活动编码',width : 80,align:'left'},
				                  {field : 'proName',title : '活动名称',width : 100,align:'left'},
				                  {field : 'discountN',title : '扣率',width : 80,align:'right'},
				                  {field : 'discountCode',title : '扣率代码',width : 80,align:'center',halign:'center'},
				                  {field : 'mallNumber',title : '商场报数',width : 80,align:'right',exportType:'number'},
				                  {field : 'salesAmount',title : '系统收入',width : 80,align:'right',exportType:'number'},
				                  {field : 'diffAmount',title : '扣费差异',width : 80,align:'right',exportType:'number'},
				                  {field : 'salesDiffamount',title : '差异金额',width : 80,align:'right',halign:'center',exportType:'number'},	
				                  {field : 'reportDiffAmount',title : '报数差异',width : 80,align:'right',halign:'center',exportType:'number'}, 
				                  {field : 'balanceDiffAmount',title : '结算差异',width : 80,align:'right',halign:'center',exportType:'number'},                  
				                  {field : 'diffReason',title : '差异原因',width : 100,align:'left'},
				                  {field : 'diffBalance',title : '差异余额',width : 80,align:'right',exportType:'number'},
				                  {field : 'status',title : '状态',width : 90,align:'center',formatter : dialog.formatStatus},
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
	                         	onDblClickRow:function(rowIndex, rowData){
	                               //双击方法
			                   	}
			                }'
			                loadSubGridUrl="/bill_shop_balance_diff/listDiffTrack.json"
			             	subPagination="false"  width ="120"
			             	subGridColumnsJsonList="[
			             	    {field : 'monthDiff',title : '结算月',width : 20,align:'center'},
			             		{field : 'balanceNoDiff', title : '结算单编码', width : 40, align : 'left'},
			             	  	{field : 'diffBillNoDiff', title : '差异单据编码', width : 40, align : 'left'},
				                {field : 'adjustType', title : '调整类型', width : 30, align : 'center',
				                	formatter:function(value){
				                		if(value == '1') {
				                			return '差异调整';
				                		}
				                		if(value == '2') {
				                			return '差异回款';
				                		}
				                		return '';
				                	}
				                },
						      	{field : 'adjustAmount', title : '调整金额', width : 20, align : 'right',exportType:'number'},
						      	{field : 'adjustDate', title : '调整日期', width : 20, align : 'center'},
				                {field : 'adjustReason', title : '备注/调整原因', width : 30, align : 'left'},
				                {field : 'statusDiff',title : '状态',width : 30,align:'center',formatter : dialog.formatStatus},
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
			       />
			</div>
		 </div>
	</div>
</body>
</html>