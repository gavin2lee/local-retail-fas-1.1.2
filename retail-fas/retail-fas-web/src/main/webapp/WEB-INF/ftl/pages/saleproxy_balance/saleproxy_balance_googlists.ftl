<!DOCTYPE html>
<head>
    <title>代销商品清单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/saleproxy_balance/saleproxy_balance_googlists.js?version=${version}"></script>
	
	<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
	<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
	<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	   <@p.billToolBar type="shop_sale_order_listBar"/>
	</div>
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div" style="border-bottom:0px;">
				     <form name="searchForm" id="searchForm" method="post">
		       		 	<table class="form-tb">
		       		 	<col width="100" />
					    <col />
					    <col width="100" />
					    <col />
					    <col width="100"/>
					    <col />
					    <col width="100"/>
					    <col />
		       		 		<tr>
			       		 		<th align="right"><span class="ui-color-red">*</span>公司名称：</th>
							  	<td align="left">
								  	<input class="ipt easyui-company" data-options="inputWidth:130,required:true,multiple:true,inputNoField:'companyNos_',inputNameField:'companyName_'" name="companyNames" id="companyName_"/>
									<input type="hidden" name="companyNos" id="companyNos_"/>
								</td>
							    <th>店铺：</th>
								<td>
									<input class="easyui-shopCommon" id="shopName"/>
									<input type="hidden" name="shopNos" id="shopNo"/>
								</td> 
								<th>品牌：</th>
								<td>
					        		<input class="ipt easyui-brand" name="brandNames" id="brandName_" 
					        			data-options="inputWidth:130, multiple: true, inputNoField:'brandNo_', inputNameField:'brandName_'"/>
								  	<input type="hidden" name="brandNos" id="brandNo_"/>
								</td>
								<th>大类：</th>
								<td>
									<input class="easyui-categorybox ipt" data-options="multiple:false,required:false"  name="categoryNo" id="categoryNo"/>
								</td> 
							</tr>	
		       		 		<tr>
		       		 			<th>销售订单号： </th>
								<td><input class="easyui-validatebox ipt" name="orderNo" id="orderNo" /></td> 
		       		 			<th align='right'>商品编码：</th>
						 		<td>
						  			<input class="easyui-itemCommon" id="itemName" />
									<input type="hidden" name="itemSql" id="itemNo"/>
						  		</td>
		       		 			<th>管理城市： </th>
								<td>
								    <input class="easyui-organ ipt"  data-options="inputNoField:'organNoTemp',inputNameField:'organName',multiple:true,inputWidth:130" name="organName" id="organName"/>
							        <input type="hidden" name="organNoTemp" id="organNoTemp"/>
								</td>
								<th>业务类型：</th>
								<td>
									<input type="text" id="orderBillType" name="orderBillType"/>
								</td>
							</tr>
							<tr>   		       		 		
						  		<th><span class="ui-color-red">*</span>销售日期：</th>
		       		 			<td>
		       		 				<input class="easyui-datebox easyui-validatebox ipt"  name="startOutDate" id="startDate" data-options="required:true,maxDate:'endDate'"/>
		       		 			</td> 
		       		 			<th> &nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </th>
								<td>
		       		 				<input class="easyui-datebox easyui-validatebox ipt"  name="endOutDate" id="endDate" data-options="required:true,minDate:'startDate'"/>
		       		 			</td> 
		       		 		   <th>数量：</th>
								<td>									    
								<input class="easyui-combobox  easyui-validate  ipt" name="qty" id="qty" style="width:80px;"  
								data-options="valueField: 'label',textField: 'value',required:false,
								data: [{label: '0',value: '等于0'},{label: '1',value: '不等于0'},{label: '2',value: '全部'}]" />
							    </td> 
							    <th>销售收入：</th>
								<td>									    
								<input class="easyui-combobox  easyui-validate  ipt" name="saleAmount" id="saleAmount" style="width:80px;"  
								data-options="valueField: 'label',textField: 'value',required:false,
								data: [{label: '0',value: '等于0'},{label: '1',value: '不等于0'},{label: '2',value: '全部'}]" />
							    </td> 
			       		   </tr>
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',bordRer:false">
	        	<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
					<div data-options="title:'明细单据'">
			      		<@p.datagrid id="shopSaleOrderDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
				              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="true" 
				              showFooter="true"		 
				           columnsJsonList="[
				                {field : 't',checkbox:true,hidden : 'true',width : 30,notexport:true},	
				                {field : 'id',hidden : 'true',align:'center',notexport:true},	
				                {field : 'zoneName',hidden : 'true',title : '经营区域',width : 80},	
				                {field : 'organName',title : '管理城市',width : 80},	
								{field : 'bizName',title : '经营城市',width : 80},	
								{field : 'bsgroupsName',title : '商业集团',width : 100,align:'left',halign:'center'},	
				                {field : 'shopNo',title : '店铺编码',width : 80,align:'left',halign:'center'},	
								{field : 'shopName',title : '店铺名称',width : 150,align:'left',halign:'center'},	
								{field : 'orderBillType',title : '业务类型',width : 80},		
								{field : 'oldOrderNo',title : '原订单号',width : 150,align:'left',halign:'center'},						
								{field : 'outDate',title : '销售日期',width : 80},		           
				                {field : 'orderNo',title : '销售订单号',width : 150,align:'left',halign:'center'},
								{field : 'sizeNo',title : '尺码',width : 50},	
								{field : 'itemCode',title : '商品编码',width : 120,align:'left',halign:'center'},	
								{field : 'itemName',title : '商品名称',width : 130,align:'left',halign:'center'},
								{field : 'categoryName',title : '大类',width : 50,align:'left',halign:'center'},
								{field : 'brandName',title : '品牌',width : 50,align:'left',halign:'center'},
								{field : 'brandUnitName',title : '品牌部',width : 50,align:'left',halign:'center'},
								{field : 'qty',title : '数量',width : 60,align:'right',halign:'center',exportType:'number'},
								{field : 'tagPrice',title : '牌价',width : 60,align:'right',halign:'center',exportType:'number'},
								{field : 'tagAmount',title : '牌价额',80 : 100,align:'right',halign:'center',exportType:'number'},
							    {field : 'salePrice',title : '现价',width : 60,align:'right',halign:'center',exportType:'number'},
							    {field : 'salePriceAmount',title : '现价额',80 : 100,align:'right',halign:'center',exportType:'number'},							   
							    {field : 'settlePrice',title : '结算价',width : 80,align:'right',halign:'center',exportType:'number'},
							    {field : 'amount',title : '结算额',width : 80,align:'right',halign:'center',exportType:'number'},
							    {field : 'saleAmount',title : '销售收入',width : 80,align:'right',halign:'center',exportType:'number'},
							    {field : 'mallDeductAmount',title : '扣费',width : 80,align:'right',halign:'center',exportType:'number'},
							    {field : 'reducePrice',title : '减价',width : 60,align:'right',halign:'center',exportType:'number'},							 
								{field : 'prefPrice',title : '促销优惠单价',width : 90,align:'right',halign:'center',exportType:'number'},
								{field : 'itemDiscountStr',title : '商品折数',width : 80,align:'right',halign:'center'},
								{field : 'proNo',title : '促销活动编号',width : 100,align:'left',halign:'center'},
								{field : 'proName',title : '促销活动名称',width : 150,align:'left',halign:'center'},
                                {field : 'discountStr',title : '扣率%',width : 60,align:'right',halign:'center'},
                                 {field : 'billingCode',title : '商场结算码',width : 80,align:'right',halign:'center'},
                                {field : 'assistantNo',hidden:'true', title : '营业员编码', width : 90,align:'left',halign:'center'},  
						        {field : 'assistantName',hidden:'true', title : '营业员名称', width : 90}, 
								 {field : 'launchTypeName',title : '活动来源',width : 60,align:'right',halign:'center'},
								 {field : 'discountCode',title : '扣率代码',width : 60,align:'right',halign:'center'},
								 {field : 'proStartDate',title : '促销开始日期',width : 90,align:'right',halign:'center'},
								 {field : 'proEndDate',title : '促销结束日期',width : 90,align:'right',halign:'center'},
								
								{field : 'vipDiscount',title : '会员折数',width : 80,align:'right',halign:'center',exportType:'number'},			 
								{field : 'headquarterCost',title : '总部成本',width : 80,align:'right',halign:'center'},
								{field : 'regionCost',title : '地区成本',width : 80,align:'right',halign:'center',exportType:'number'},
								{field : 'unitCost',title : '单位成本',width : 80,align:'right',halign:'center',exportType:'number'},
								{field : 'companyName',title : '结算公司',width : 180,align:'left',halign:'center'},	
								
								{field : 'regionNo',hidden:'true',title : '片区',width : 50,align:'left',notexport:true},	
								{field : 'mallName',title : '商场',width : 150,align:'left',halign:'center'},	
								{field : 'retailType',hidden:'true',title : '销售类型',width : 100,align:'left',notexport:true},	
								{field : 'orderType',hidden:'true',title : '单据名称',width : 100,align:'left',notexport:true},	
								{field : 'businessType',hidden:'true',title : '订单类型',width : 100,align:'left',notexport:true},
								{field : 'remark',title : '备注',width : 100,align:'right',halign:'center'},									               
				              ]" 
					        jsonExtend='{}'/>
					</div>
					<div data-options="title:'销售收入（按扣率统计）'">
						<@p.datagrid id="discountSumDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
							isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" rownumbers="true" 
							showFooter="true"
							frozenColumns="[
								{field:'shop_no',title:'店铺编号',width:80,sortable:true,align:'center',hidden:true},
								{field:'shop_name',title:'店铺',width:150,sortable:true,align:'center'},
								{field:'brand_no',title:'品牌编号',width:80,sortable:true,hidden:true},
								{field:'brand_name',title:'品牌',width:80,sortable:true},
								{field:'out_date',title:'日期',width:90,sortable:true},
								{field:'total',title:'合计',width:90,sortable:true,align:'right',halign:'center',exportType:'number'}
							]"
				        />
					</div>
				</div>
			</div>
		 </div>
	</div>
</body>
</html>