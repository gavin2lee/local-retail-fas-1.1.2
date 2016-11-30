<!DOCTYPE html>
<head>
    <title>商场门店销售明细表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/cost_categoryset.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/accounting_subject.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/financial_account.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/mall_shopsorderd.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/initbalance.js?version=${version}"></script>
	
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/mall_shopsorderd/list.json" 
					  save_url="/mall_shopsorderd/post" 
					  update_url="/mall_shopsorderd/put" 
					  del_url="/mall_shopsorderd/save" 
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="1000" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":0}
	           ]
			/>
	
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="searchForm" id="searchForm" method="post">
		       		 	<table class="form-tb">
		       		 			<tr>
								<td align="right" width="110">公司编码：</td>
								<td align="left" width="140"><input class="easyui-validatebox ipt" name="companyNo" id="companyNoId"  readonly="true" />  </td>
								<td align="right" width="110">公司名称：</td>
								<td align="left" width="140"><input class="ipt"  name="companyName" id="companyNameId" data-options="required:true" /></td>							
							    
							    <td align="right" width="110">品牌：</td>
								<td align="left" width="140">
								<input class="easyui-combobox ipt"  name="brandNo" id="brandNo" data-options="required:true" /></td>							
							    </tr>
							    <tr>
								<td align="right" width="110">地区：</td>
								<td align="left" width="140"><input class="easyui-combobox ipt" name="zoneNo" id="zoneNo"  readonly="true" />  </td>
								<td align="right" width="110">城市：</td>
								<td align="left" width="140">
								<input class="easyui-combobox ipt"  name="bizCityNo" id="bizCityNo" data-options="required:true" /></td>
								<td align="right" width="110">商场：</td>
								<td align="left" width="140"><input class="easyui-combobox ipt" name="mallNo" id="mallNo"  readonly="true" />  </td>
								<td align="right" width="110">店铺：</td>
								<td align="left" width="140">
								<input class="easyui-combobox ipt"  name="shopNo" id="shopNo" data-options="required:true" /></td>				
							    </tr>
							    <tr>
		       		 			<td width="100" align="right">销售日期：</td>
		       		 			<td align="left"><input class="easyui-datebox ipt"  name="strbillDate" id="billDateCondition"/></td>
		       		 			<td width="100" align="right">至：</td>
		       		 			<td align="left"><input class="easyui-datebox ipt"  name="endbillDate" id="billDateCondition"/></td>  
		       		 			</tr>		 			
		       		 		</tr>		       		 		
		       		 	</table>
					</form>
				</div>
			</div>
			<!--列表-->
	        <div data-options="region:'center',bordRer:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" title=""
			              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="false" 
				           columnsJsonList="[
				               {field : 't',checkbox:true,hidden : 'true',width : 30},				           
				                {field : 'orderNo',title : '销售订单号',width : 100,align:'center'},	
				                {field : 'id',hidden : 'true',align:'center'},
				                {field : 'companyNo',title : '结算公司',width : 100,align:'center'},	
								{field : 'zoneNo',title : '经营区域',width : 100,align:'center'},	
								{field : 'bizCityNo',title : '经营城市',width : 100,align:'center'},	
								{field : 'bsgroupsNo',title : '商业集团',width : 100,align:'center'},	
								{field : 'regionNo',title : '片区',width : 100,align:'center'},	
								{field : 'mallNo',title : '商场',width : 100,align:'center'},	
								{field : 'shopNo',title : '店铺',width : 100,align:'center'},	
								{field : 'brandNo',title : '品牌',width : 100,align:'center'},								
								{field : 'saleDate',title : '销售日期',width : 100,align:'center'},	
								{field : 'retailType',title : '销售类型',width : 100,align:'center',formatter: cost_category.dataCostType},	
								{field : 'orderType',title : '单据名称',width : 100,align:'center',formatter: cost_category.dataCostDeductType},	
								{field : 'businessType',title : '订单类型',width : 100,align:'center',formatter: cost_category.dataCostPayType},	
								{field : 'skuNo',title : '商品SKUs',width : 100,align:'center'},	
								{field : 'itemNo',title : '商品内码',width : 100,align:'center'},	
								{field : 'sizeNo',title : '商品尺码',width : 100,align:'center'},	
								{field : 'quantity',title : '数量',width : 100,align:'center'},
								{field : 'tagPrice',title : '牌价',width : 100,align:'center'},
								{field : 'tagAmount',title : '牌价额',width : 100,align:'center'},
							   {field : 'salePrice',title : '现价',width : 100,align:'center'},
							   {field : 'saleAmount',title : '现价额',width : 100,align:'center'},
							   {field : 'discPrice',title : '折扣价',width : 100,align:'center'},
							   {field : 'discAmount',title : '折扣额',width : 100,align:'center'},
							   {field : 'settlePrice',title : '结算价',width : 100,align:'center'},
							   {field : 'settleAmount',title : '结算额',width : 100,align:'center'},
							  {field : 'reducePrice',title : '减价',width : 100,align:'center'},
							  {field : 'amount',title : '商品总金额,(结算价-减价)*数量',width : 100,align:'center'},
							  {field : 'prefPrice',title : '促销优惠单价',width : 100,align:'center'},
							  {field : 'proNo',title : '促销活动编号',width : 100,align:'center'},
							  {field : 'proName',title : '促销活动名称',width : 100,align:'center'},
							  {field : 'discount',title : '扣率',width : 100,align:'center'},
							  {field : 'vipDiscount',title : '会员折数',width : 100,align:'center'},
							  {field : 'baseScore',title : '基本积分',width : 100,align:'center'},
							  {field : 'proScore',title : '整单分摊积分',width : 100,align:'center'},
							  {field : 'affectFlag',title : '是否影响销售',width : 100,align:'center'},
							  {field : 'itemDiscount',title : '商品折数',width : 100,align:'center'},
							  {field : 'remark',title : '备注',width : 100,align:'center'},									               
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
					                   	  fas_common.loadDetail(rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
	                       
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true" > 
		<form  id="dataForm">		
		 <div id="dtl_detail">
			<div id="div1" class="easyui-panel" style="width:900px;"  title=" 费用登记" data-options="title:'费用登记',fieldset:true,cls:'mt10'">
				<table cellpadding="1" cellspacing="1" class="form-tb">
					<input type="hidden" id="id" name="id" />
					<tr>
					<td align="right" width="110">公司编码:</td>
					<td align="left" width="140"><input class="easyui-validatebox" name="companyNo" id="companyNoId"  readonly="true" />  </td>
					<td align="right" width="110">公司名称:</td>
					<td align="left" width="140"><input class="ipt"  name="companyName" id="companyNameId" data-options="required:true" /></td>							
				    </tr>
			     	<tr>
							
							<td width="110" align="right">经营区域：</td>
							<td width="140" align="left">
								<input class="easyui-validatebox" name="zoneNo" id="zoneNo" required="true" data-options="required:true" />
						 	</td>						
						     <td align="right" width="110">经营城市：</td>
						     <td align="left" width="140"><input class="easyui-validatebox"  name="bizCityNo" id="bizCityNoId" data-options="required:true" />
						    </td>
				    </tr>
			     	<tr>				    
				     <td align="right" width="110">商业集团：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="bsgroupsNo" id="bsgroupsNoId" data-options="required:true" /></td>
				     <td align="right" width="110">片区：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="regionNo" id="regionNoId" data-options="required:true" /></td>
				     <td align="right" width="110">商场：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="mallNo" id="mallNoId" data-options="required:true" /></td>
					</tr>
					<tr>
				     <td align="right" width="110">店铺：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="shopNo" id="shopNoId" data-options="required:true" /></td>
				
				     <td align="right" width="110">品牌：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="brandNo" id="brandNo" data-options="required:true" /></td>
				
				     <td align="right" width="110">所属期间：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="period" id="period" data-options="required:true" /></td>
					</tr>
					<tr >
				     <td align="right" width="110">费用性质：</td>
				     <td align="left" width="140">
				     <input class="easyui-combobox"  name="costType" id="costType" data-options="  
						valueField: 'label',
						textField: 'value',
						data: [{
							label: '1',
							value: '合同内'
						},{
							label: '2',
							value: '合同外'
						}]" />
				     </td>
				
				     <td align="right" width="110">费用扣取方式：</td>
				     <td align="left" width="140">
				     <input class="easyui-combobox"  name="costDeductType" id="costDeductType" data-options="  
						valueField: 'label',
						textField: 'value',
						data: [{
							label: '1',
							value: '票后'
						},{
							label: '2',
							value: '票前'
						}]" />
				     </td>
				
				     <td align="right" width="110">费用交款方式：</td>
				     <td align="left" width="140">
				     <input class="easyui-combobox"  name="costPayType" id="costPayType" data-options="  
						valueField: 'label',
						textField: 'value',
						data: [{
							label: '1',
							value: '帐扣'
						},{
							label: '2',
							value: '现金'
						}]" />
				     </td>
					</tr>
					<tr>
				     <td align="right" width="110">费用类别：</td>
				     <td align="left" width="140"><input class="easyui-combobox"  name="costCateNo" id="costCateNo" data-options="required:true" /></td>				
				     <td align="right" width="110">商场扣费：</td>
				     <td align="left" width="140"><input class="easyui-combobox"  name="costNo" id="costNo" data-options="required:true" /></td>				
				     <td align="right" width="110">业务日期：</td>
				     <td align="left" width="140"><input class="easyui-datebox"  name="billDate" id="billDate" data-options="required:true" /></td>
					</tr>
					<tr>
				     <td align="right" width="110">扣费金额：</td>
				     <td align="left" width="140"><input class="easyui-validatebox"  name="deductAmount" id="deductAmount" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
</body>
</html>