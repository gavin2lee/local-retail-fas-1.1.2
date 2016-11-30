<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>采购价目表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/factoryPriceMaintain.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()", "type":4}
           ]
		/>
	</div>

	<div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
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
						 		<th>商品编码：</th>
							 	<td>
							 		<input class="ipt easyui-item" name="itemCode" id="itemCodeCondition"
							 	    data-options="inputWidth:160,inputNoField:'itemNoCondition', inputCodeField:'itemCodeCondition'"/>
							  		<input type="hidden" name="itemNo" id="itemNoCondition"/>
							  	</td>
							 	<th>供应商：</th>
						 		<td>
							 		<input class="ipt easyui-supplier" name="itemName" id="supplierNameCondition"
							 	    data-options="inputWidth:160,inputNoField:'supplierNoCondition', inputNameField:'supplierNameCondition'"/>
							  		<input type="hidden" name="supplierNo" id="supplierNoCondition"/>
							  	</td>
							 	<th>生效时间：</th>
							 	<td>
							 		<input class="easyui-datebox ipt"  name="effectiveDateStart" id="effectiveDateStart" data-options="maxDate:'effectiveDateEnd'"/>
							 	</td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox ipt" name="effectiveDateEnd" id="effectiveDateEnd" data-options="minDate:'effectiveDateStart'"/>
								</td>
							</tr>
							<tr>
								<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
							 	<td>
							 		<input class="easyui-validatebox ipt easyui-brand" name="brandName" id="brandNameCondition" 
							 		data-options="inputWidth:160,inputNoField:'brandNoCondition', inputNameField:'brandNameCondition'" />
						  			<input type="hidden" name="brandNo" id="brandNoCondition" />
						  		</td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
			                  	  {field : 'id',hidden:true,notexport:true},
				                  {field : 'itemCode',title : '商品编码',width: 150,align:'left',halign:'center'},
				                  {field : 'itemName',title : '商品名称',width: 150,align:'left',halign:'center'},
				                  {field : 'supplierNo',title : '供应商编码',width: 100,align:'left',halign:'center'},
				                  {field : 'supplierName',title : '供应商名称',width: 180,align:'left',halign:'center'},
				                  {field : 'brandName',title : '品牌',width: 80,align:'center',halign:'center'},
				                  {field : 'effectiveDate',title : '生效日期',width: 100,align:'center',halign:'center'},
				                  {field : 'purchasePrice',title : '采购价',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'materialPrice',title : '物料价',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'factoryPrice',title : '厂进价',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'status',title : '状态',width: 80,align:'center',halign:'center',formatter: dialog.statusformatter},
				                  {field : 'createUser',title : '创建人',width: 80,align:'center',halign:'center'},
				                  {field : 'createTime',title : '创建时间',width: 150,align:'center',halign:'center'},
				                  {field : 'updateUser',title : '修改人',width: 80,align:'center',halign:'center'},
				                  {field : 'updateTime',title : '修改时间',width: 150,align:'center',halign:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>