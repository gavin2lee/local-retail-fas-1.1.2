<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>测试插件</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.support.js?version=${version}"></script>


</head>
<body style="overflow:auto">
	<div>
      	货管单位：<input class="easyui-orderUnitForCheck ipt" name="orderUnitName" id="orderUnitName" data-options="required:true,inputNoField:'orderUnitNo', inputNameField:'orderUnitName'"/>
		<input type="hidden" name="orderUnitNo" id="orderUnitNo"/>
	</div>

	<div>
      	货管单位：<input class="easyui-orderUnitForCheck ipt" name="orderUnitName1" id="orderUnitName1" data-options="required:true,inputNoField:'orderUnitNo1', inputNameField:'orderUnitName1'"/>
		<input type="hidden" name="orderUnitNo1" id="orderUnitNo1"/>
	</div>

	<div>
      	店铺：<input class="easyui-shop ipt" name="shortName" id="shortName3_" data-options="multiple:false,required:true,inputWidth:200,inputNoField:'shortNo3_', inputNameField:'shortName3_'"/>
		<input type="hidden" name="shortNo" id="shortNo3_"/>
	</div>


	 <div id="left">
		商品：<input class="easyui-validatebox ipt easyui-item" name="itemName" id="itemName" data-options="required:true,inputWidth:200,multiple:true"/>
  		<input type="text" name="itemNo" id="itemNo"/>
  	</div>
    <div id="left">
		供应商：<input class="easyui-validatebox ipt easyui-supplier" name="supplierName" id="supplierName" data-options="multiple:true,required:true,inputWidth:200"/>
  		<input type="text" name="supplierNo" id="supplierNo"/>
  	</div>
  	 <div id="left">
		客户：<input class="easyui-validatebox ipt easyui-customer" name="customerName" id="customerName" data-options="required:true,inputWidth:200"/>
  		<input type="text" name="customerNo" id="customerNo"/>
  	</div>
  	<div id="left">
		公司：<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
  		<input type="text" name="companyNo" id="companyNo"/>
  	</div>
  	<div id="left">
		店铺：<input class="easyui-validatebox ipt easyui-shop" name="shopName" id="shopName" data-options="required:true,inputWidth:200,multiple:true"/>
  		<input type="text" name="shopNo" id="shopNo"/>
  	</div>
  	<div id="left">
		商场：<input class="easyui-validatebox ipt easyui-mall" name="mallName" id="mallName" data-options="required:true,inputWidth:200"/>
  		<input type="text" name="mallNo" id="mallNo"/>
  	</div>
  	<div id="left">
		品牌部：<input class="easyui-validatebox ipt easyui-brandunit" name="brandUnitName" id="brandUnitName" data-options="required:true,inputWidth:200"/>
  		<input type="text" name="brandUnitNo" id="brandUnitNo"/>
  	</div>
  	<div id="left">
		品牌：<input class="easyui-validatebox ipt easyui-brand" name="brandName" id="brandName" data-options="required:true,inputWidth:200"/>
  		<input type="text" name="brandNo" id="brandNo"/>
  	</div>
  	<div id="left">
		品牌部：<input class="easyui-validatebox ipt easyui-customerInvoiceInfo" name="clientName" id="clientName" data-options="required:true,inputWidth:200"/>
  		<input type="text" name="clientNo" id="clientNo"/>
  	</div>
</body>
</html>