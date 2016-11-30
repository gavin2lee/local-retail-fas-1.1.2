<!DOCTYPE html>
<head>
	<title>商场费用管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/ShopbalanceDeduct.js?version=${version}"></script>
	
	<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
	<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
	<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>

<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<@p.billToolBar type="shop_balance_deduct_operaBar"/>
			
		<#--搜索start-->
		<div class="search-div">
			<form name="searchForm" id="searchForm" method="post">
				<table class="form-tb">
					<col width="100" />
					<col  />
					<col width="100" />
					<col />	
					<col width="100" />
					<col />
					<col width="100" />
					<col />	
					<tr>
						<th>公司名称：</th>
						<td>
							<input class="ipt easyui-company" name="companyName" id="companyName"/>
							<input type="hidden" name="companyNo" id="companyNo"/>
							<input type="hidden" name="balanceNo" id="balanceNo"  value=''/>
						</td>									
						<th>管理城市： </th>
						<td>
							<input class="easyui-organ ipt"  data-options="inputNoField:'organNoTemp',inputNameField:'organName',multiple:true" name="organNames" id="organName"/>
							<input type="hidden" name="organNos" id="organNoTemp"/>
						</td>
						<th>商业集团：</th>
						<td>
							<input class="ipt easyui-bsgroups" name="bsgroupsName" id="bsgroupsName"/>
							<input type="hidden" name="bsgroupsNo" id="bsgroupsNo"/>
						</td>
						<th>商场：</th>
						<td>
							<input class="ipt easyui-mall" name="mallName" id="mallName"/>
							<input type="hidden" name="mallNo" id="mallNo"/>
						</td>
					</tr>
					<tr>
						<th>店铺：</th>
						<td>
							<input id="shortName"/>
							<input type="hidden" name="shopNos" id="shopNo"/>
						</td>
						<th>品牌部：</th>
						<td>
							<input class="ipt easyui-brandunit" name="brandNames" id="brandNames_" 
								data-options="multiple: true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
							<input type="hidden" name="brandNos" id="brandNos_"/>
						</td>						
						 <th>结算月：</th>
				          	<td align="left" width="145"><input class="easyui-datebox" name="startMonth" id="startMonth" style="width:65px;"  data-options="multiple:true,dateFmt:'yyyyMM'" />
				          	<input class="easyui-datebox" name="endMonth" id="endMonth" style="width:65px;"  data-options="multiple:true,dateFmt:'yyyyMM'" /></td>
						<th>制单人：</th>
						<td><input class="easyui-validatebox ipt" name="createUser" id="createUser" style="width:130px" /></td>
							
					</tr>
				</table>
			</form>
		</div>
	</div>
	 
	<div data-options="region:'center',border:false" style="height:260px;">		
		<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
			<div data-options="title:'票前费用'">
				<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_deduct_costbefore.ftl" >
			</div>
			<div data-options="title:'票后费用'">
				<#include  "/WEB-INF/ftl/pages/mallshop_balance/shopbalance_deduct_costafter.ftl" >
			</div>
		</div>		
	</div>
	
	<div id="createBalanceDeductPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="createBalanceDeductForm" id="createBalanceDeductForm" method="post"  class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:520px;"  title="筛选条件" data-options="title:'筛选条件',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="100" />
						<col  />
						<col width="100" />
						<col />	
						<tr>
							<th><span class="ui-color-red">*</span>公司名称：</th>
							<td>
								<input class="ipt easyui-company"  data-options="required:true,inputNoField:'companyNo_c',inputNameField:'companyName_c'" name="companyName" id="companyName_c"/>
								<input type="hidden" name="companyNo" id="companyNo_c"/>
								<input type="hidden" name="balanceNo" id="balanceNo_c"  value=''/>
							</td>									
							<th>管理城市： </th>
							<td>
								<input class="easyui-organ ipt"  data-options="inputNoField:'organNo_c',inputNameField:'organName_c',multiple:true" name="organNames" id="organName_c"/>
								<input type="hidden" name="organNos" id="organNo_c"/>
							</td>
						</tr>
						<tr>
							<th><span class="ui-color-red">*</span>结算月：</th>
							<td>
								<input class="easyui-datebox ipt"  name="month" id="month_c" data-options="required:true,width:130,dateFmt:'yyyyMM'"  />
							</td>
							<th>商业集团：</th>
							<td>
								<input class="ipt easyui-bsgroups" name="bsgroupsName" id="bsgroupsName_c"
									data-options="inputNoField:'bsgroupsNo_c', inputNameField:'bsgroupsName_c'"/>
								<input type="hidden" name="bsgroupsNo" id="bsgroupsNo_c"/>
							</td>
						</tr>
						<tr>
							<th>品牌：</th>
							<td>
								<input class="ipt easyui-brand" name="brandNames" id="brandNames_c" 
									data-options="multiple: true, inputNoField:'brandNos_c', inputNameField:'brandNames_c'"/>
								<input type="hidden" name="brandNos" id="brandNos_c"/>
							</td>
							<th>商场：</th>
							<td>
								<input class="ipt easyui-mall" name="mallName" id="mallName_c"
									data-options="inputNoField:'mallNo_c', inputNameField:'mallName_c'"/>
								<input type="hidden" name="mallNo" id="mallNo_c"/>
							</td>
						</tr>
						<tr>
							<th>店铺：</th>
							<td>
								<input id="shortName_c"/>
								<input type="hidden" name="shopNos" id="shopNo_c"/>
							</td>
							<th>扣取方式：</th>
							<td>									    
								<input class="easyui-combobox  easyui-validate  ipt" name="costDeductType" id="costDeductType_" style="width:80px;"  
								data-options="valueField: 'label',textField: 'value',required:false,
								data: [{label: ' ',value: ' '},{label: '1',value: '票前'},{label: '2',value: '票后'}]" />
							</td>				
						</tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
</body>
</html>