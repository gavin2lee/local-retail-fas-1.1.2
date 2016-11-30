<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>测试插件</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.1.0.js?version=${version}"></script>
</head>
<body style="overflow:auto">
    <div id="left">
		商品(单选)：<input class="easyui-validatebox ipt easyui-item" readonly="true" name="itemName" id="itemName" data-options="required:true,inputWidth:200"/>
  		<input type="hidden" name="itemNo" id="itemNo"/>
  	</div>
  	<div id="middle">
		商品(多选)：<input class="easyui-validatebox ipt easyui-item" readonly="true" name="itemName" id="itemName_" 
  							data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'itemNo_', inputNameFeild:'itemName_'"/>
  		<input type="hidden" name="itemNo" id="itemNo_"/>
  	</div>
    <div id="left">
      	结算公司(单选)：<input class="easyui-validatebox ipt easyui-company" readonly="true" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
      	<input type="hidden" name="companyNo" id="companyNo"/>
     </div>
  	<div id="middle">
	  	结算公司(多选)：<input class="easyui-validatebox ipt easyui-company" readonly="true" name="companyName" id="companyName_" 
	  				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'companyNo_', inputNameFeild:'companyName_'"/>
	  	<input type="hidden" name="companyNo" id="companyNo_"/>
	</div>
	<div>
		<span>
	      	供应商(单选)：<input class="easyui-validatebox ipt easyui-supplier" readonly="true" name="supplierName" id="supplierName" data-options="required:true,inputWidth:200"/>
	      	<input type="hidden" name="supplierNo" id="supplierNo"/>
		  	供应商(多选)：<input class="easyui-validatebox ipt easyui-supplier" readonly="true" name="supplierName" id="supplierName_" 
		  				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'supplierNo_', inputNameFeild:'supplierName_'"/>
		  	<input type="hidden" name="supplierNo" id="supplierNo_"/>
		</span>
	</div>
	<div>
		<span>
	      	客户(单选)：<input class="easyui-validatebox ipt easyui-customer" readonly="true" name="customerName" id="customerName" data-options="required:true,inputWidth:200"/>
	      	<input type="hidden" name="customerNo" id="customerNo"/>
		  	客户(多选)：<input class="easyui-validatebox ipt easyui-customer" readonly="true" name="customerName" id="customerName_" 
		  				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'customerNo_', inputNameFeild:'customerName_'"/>
		  	<input type="hidden" name="customerNo" id="customerNo_"/>
		</span>
	</div>
	<div>
		<span>
	      	商场(单选)：<input class="easyui-validatebox ipt easyui-mall" readonly="true" name="mallName" id="mallName" data-options="required:true,inputWidth:200"/>
	      	<input type="hidden" name="mallNo" id="mallNo"/>
	      	商场(多选)：<input class="easyui-validatebox ipt easyui-mall" readonly="true" name="mallName" id="mallName_" 
	      				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'mallNo_', inputNameFeild:'mallName_'"/>
	      	<input type="hidden" name="mallNo_" id="mallNo_"/>
		</span>
	</div>
	<div>
		<span>
	      	店铺(单选)：<input class="easyui-validatebox ipt easyui-shop" readonly="true" name="shopName" id="shopName" data-options="required:true,inputWidth:200"/>
	      	<input type="hidden" name="shopNo" id="shopNo"/>
	      	店铺(多选)：<input class="easyui-validatebox ipt easyui-shop" readonly="true" name="shopName" id="shopName_" 
	      				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'shopNo_', inputNameFeild:'shopName_'"/>
	      	<input type="hidden" name="shopNo_" id="shopNo_"/>
		</span>
	</div>
	<div>
      	品牌弹出框(单选)：<input class="easyui-validatebox ipt easyui-brandsearch" readonly="true" name="brandName" id="brandName" data-options="required:true,inputWidth:200"/>
      	<input type="hidden" name="brandNo" id="brandNo"/>
	</div>
	<div>
	  	品牌(多选)：<input class="easyui-validatebox ipt easyui-brandsearch" readonly="true" name="brandName" id="brandName_" 
	  				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'brandNo_', inputNameFeild:'brandName_'"/>
	  	<input type="hidden" name="brandNo" id="brandNo_"/>
	</div>
	<div>
      	品牌部弹出框(单选)：<input class="easyui-validatebox ipt easyui-brandunitsearch" readonly="true" name="brandUnitName" id="brandUnitName" data-options="required:true,inputWidth:200"/>
      	<input type="hidden" name="brandUnitNo" id="brandUnitNo"/>
	</div>
	<div>
	  	品牌部(多选)：<input class="easyui-validatebox ipt easyui-brandunitsearch" readonly="true" name="brandUnitName" id="brandUnitName_" 
	  				data-options="required:true,inputWidth:300, multiple: true, inputValueFeild:'brandUnitNo_', inputNameFeild:'brandUnitName_'"/>
	  	<input type="hidden" name="brandUnitNo" id="brandUnitNo_"/>
	</div>
	<div>
      	品牌下拉框：<input class="easyui-brandbox ipt" name="brandNo" id="brandNo_" data-options="required:true,multiple:true"/>
	</div>
	<div>
      	区域下拉框：<input class="easyui-zonebox ipt" name="zoneNo" id="zoneNo" data-options="required:true,multiple:true"/>
	</div>
	<div>
      	季节下拉框：<input class="easyui-seasonbox ipt" name="seasonNo" id="seasonNo" data-options="required:true,multiple:true,addAll:true"/>
	</div>
	<div>
      	年份下拉框：<input class="easyui-yearbox ipt" name="yearNo" id="yearNo" data-options="required:true,multiple:true"/>
	</div>
	<div>
      	大类下拉框：<input class="easyui-categorybox ipt" name="categoryNo" id="categoryNo" data-options="required:true,multiple:true"/>
	</div>
	<div>
      	供应商组下拉框：<input class="easyui-suppliergroupbox ipt" name="supplierGroupNo" id="supplierGroupNo" data-options="required:true"/>
	</div>
	<div>
      	客户或公司弹出框：<input class="easyui-customerOrCompany ipt" name="customerName" id="customerName2_" data-options="required:true,inputWidth:200,inputValueFeild:'customerNo2_', inputNameFeild:'customerName2_'"/>
		<input type="hidden" name="customerNo" id="customerNo2_"/>
	</div>
	<div>
      	客户(多数据源)：<input class="easyui-customerMultiDataSource ipt" name="customerName" id="customerName3_" data-options="required:true,inputWidth:200,inputValueFeild:'customerNo3_', inputNameFeild:'customerName3_'"/>
		<input type="hidden" name="customerNo" id="customerNo3_"/>
	</div>
</body>
</html>