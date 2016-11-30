<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>库存货分类报表-体育</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/inventory_classification_summary/pe_inventory_classification_summary.js?version=${version}"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"exportExcel()","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
						 	<tr>
						 		<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</th>
							 	<td>
							  		<input class="ipt easyui-company" name="companyName" id="companyNameCondition" 
							 		data-options="inputWidth:160,required:true,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
			      					<input type="hidden" name="companyNo" id="companyNoCondition"/>
								</td>
								<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
							 	<td>
							 		<input class="easyui-validatebox ipt easyui-brand" name="brandName" id="brandNameCondition" 
							 			data-options="inputWidth:160,inputNoField:'brandNoCondition', inputNameField:'brandNameCondition'" />
						  			<input type="hidden" name="brandNo" id="brandNoCondition" />
						  		</td>
						 		<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th>
							 	<td>
							 		<input class="easyui-categorybox ipt" name="categoryNo" id="categoryNo" data-options="multiple:true,width: 160" />
							 	</td>
						  		
							</tr>
							<tr>
								<th><span class="ui-color-red">*</span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</th>
							 	<td>
							 		<input class="easyui-combobox"  data-options="required:true"   name="year" id="yearCondition"/>
							 	</td>
								<th><span class="ui-color-red">*</span>月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</th>
							 	<td>
							 		<input class="easyui-combobox" data-options="required:true"   name="month" id="monthCondition" />
							 	</td>
							 	<th>商品年份：</th>
							 	<td>
							 		<input class="easyui-combobox"  name="years" id="yearCondition1"/>
							 	</td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<#--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDivPe" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" pageSize="20" 
			           rowStyler="function(index,row){
								     if (row.sell_season =='zzzzzz'){
								         return 'background-color:#6293BB;color:#fff;font-weight:bold;';
								     }
								  }"
			           columnsJsonList="[
			                  	 {field : 'id', title : '编号', width : 250,hidden:true, align:'left'},
				                 {field : 'brandName', title : '品牌', width : 100},
				                 {field : 'year', title : '年份', width : 100},
								 {field : 'seasonName', title : '季节', width : 180},
								 {field : 'cateGoryName', title : '大类',  width : 180, align:'left'}
			              ]" 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>