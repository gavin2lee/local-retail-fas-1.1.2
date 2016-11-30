<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>存货所属(主体变更)买卖跟踪表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/settlement_body_change_record/SettlementBodyChangeRecord.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog1.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog1.clear()", "type":0},
             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"dialog1.doImport()","type":6},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog1.exportExcel()","type":4}
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
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
		                 <tbody>
						 	<tr height='40'>
						 		<th align='right'>商品编码：</th>
							 	<td>
							 		<input class="easyui-itemCommon" id="itemName" />
									<input type="hidden" name="itemSql" id="itemNo"/>
							  	</td>
							  	<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
								<td>
								 		<input class="easyui-validatebox ipt easyui-brand" name="brandName" id="brandNameCondition" 
								 			data-options="inputWidth:160,inputNoField:'brandNoCondition', inputNameField:'brandNameCondition'" />
							  			<input type="hidden" name="brandNo" id="brandNoCondition" />
							  	</td>
							  	<th>存货属性：</th>
								<td>
								 	<input class="text" id="stockType" name="stockType" style="width:140px" >
							  	</td>
							</tr>
							<tr>
								<th align='right'>来源公司：</th>
							 	<td>
							 		<input class="ipt easyui-company" name="originalCompanyName" id="companyNameCondition" 
								 		data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
				      					<input type="hidden" name="originalCompanyNo" id="companyNoCondition"/>
							  	</td>
							  	
							  	<th align='right'>目的公司：</th>
							 	<td>
							 		<input class="ipt easyui-company" name="targetCompanyName" id="companyNameCondition_" 
								 		data-options="inputWidth:160,inputNoField:'companyNoCondition_',inputNameField:'companyNameCondition_'"/>
				      					<input type="hidden" name="targetCompanyNo" id="companyNoCondition_"/>
							  	</td>
							  	<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th>
							 	<td>
							 		<input class="easyui-categorybox ipt" name="categoryNo" id="categoryNo" data-options="width: 160" />
							 	</td>
							</tr>
							
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
			                  	  {field : 'id',hidden:true,notexport:true},
			                  	  {field : 'itemCode',title : '商品编码',width: 150,align:'left',halign:'center'},
			                  	  {field : 'itemName',title : '商品名称',width: 150,align:'left',halign:'center'},
				                  {field : 'brandName',title : '品牌',width: 150,align:'left',halign:'center'},
				                  {field : 'brandUnitName',title : '品牌部',width: 180,align:'left',halign:'center'},
				                  {field : 'firstLevelCategoryName',title : '一级大类',width: 120,align:'left',halign:'center'},
				                  {field : 'secondLevelCategoryName',title : '二级大类',width: 120,align:'left',halign:'center'},
				                  {field : 'categoryName',title : '商品大类',width: 120,align:'left',halign:'center'},
				                  {field : 'qty',title : '数量',width: 120,align:'left',halign:'center',exportType:'number'},
				                  {field : 'stockType',title : '存货属性',width: 120,align:'left',halign:'center',notexport:true,
					                  formatter:function(value,row,index){
				 				 		if(value == 0){
				 				 			return '正品';
				 				 		}else if(value == 1){
				 				 			return '次品';
				 				 		}else if(value == 2){
				 				 			return '原残';
				 				 		}else if(value == 3){
				 				 			return '客残';
				 				 		}
				 						}
			 				 	  },
			 				 	  {field : 'stockTypeName',title : '存货属性',width: 120,align:'left',halign:'center',hidden:true},
				                  {field : 'stockCost',title : '存货成本',width: 120,align:'left',halign:'center',exportType:'number'},
				                  {field : 'changePrice',title : '变更价格',width: 120,align:'left',halign:'center',exportType:'number'},
				                  {field : 'stockCostSum',title : '总成本',width: 120,align:'left',halign:'center',exportType:'number'},
				                  {field : 'changePriceSum',title : '总价格',width: 120,align:'left',halign:'center',exportType:'number'},
				                  {field : 'originalCompanyName',title : '来源公司',width: 120,align:'left',halign:'center'},
				                  {field : 'originalOrderUnitName',title : '来源货管单位',width: 120,align:'left',halign:'center'},
				                  {field : 'originalStoreName',title : '来源机构',width: 120,align:'left',halign:'center'},
				                  {field : 'targetCompanyName',title : '目的公司',width: 120,align:'left',halign:'center'},
				                  {field : 'targetOrderUnitName',title : '目的货管单位',width: 120,align:'left',halign:'center'},
				                  {field : 'targetStoreName',title : '目的机构',width: 120,align:'left',halign:'center'},
				                  <!-- {field : 'targetPrice',title : '目的价格',width: 120,align:'left',halign:'center',exportType:'number'},-->
				                  {field : 'changeDate',title : '变更日期',width: 120,align:'left',halign:'center'},
				                  {field : 'remark',title : '备注',width: 120,align:'left',halign:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>